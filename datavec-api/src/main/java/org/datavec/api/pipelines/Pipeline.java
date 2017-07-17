package org.datavec.api.pipelines;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.datavec.api.pipelines.api.*;
import org.datavec.api.pipelines.engine.Engine;
import org.datavec.api.pipelines.functions.abstracts.AbstractFunction;
import org.datavec.api.pipelines.functions.generic.PipelineInputFunction;
import org.datavec.api.pipelines.functions.generic.TransparentFunction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is backbone of DataVec Pipeline API
 *
 * @author raver119@gmail.com
 */
@Slf4j
public class Pipeline<IN> implements Serializable {
    @Getter(AccessLevel.PROTECTED)
    protected Engine engine;

    protected transient InputFunction<IN> inputFunction;

    protected IN finalOne;

    protected PipelineFunction<IN> firstFunction;
    protected PipelineFunction<IN> lastFunction;

    // connected output pipelines
    protected List<Pipeline> postPipelines = new ArrayList<>();

    // connected input pipelines
    protected List<Pipeline> prePipelines = new ArrayList<>();

    protected Pipeline() {
        // no-op, for internal use only
    }

    protected Pipeline(@NonNull InputFunction<IN> inputFunction) {
        this.inputFunction = inputFunction;
    }

    protected <I> Pipeline(@NonNull Pipeline<I> pipeline) {
        prePipelines.add(pipeline);
        inputFunction = new PipelineInputFunction<>(this);

        firstFunction = new TransparentFunction<>();
        lastFunction = firstFunction;
    }

    public Pipeline(@NonNull Builder<IN> builder) {
        this.inputFunction = builder.inputFunctions[0];
    }


    /**
     * This function will be invoked to split input into separate elements.
     * I.e. if you have NLP pipeline and your input is sentences before calling for split, after split it can be tokens
     *
     * @param function
     * @return
     */
    public Pipeline<IN> split(@NonNull SplitFunction<IN> function) {
        function.registerPipeline(this);
        // if this func is first in pipeline, do not attachNext it anywhere
        if (firstFunction == null) {
            firstFunction = function;
            lastFunction = function;
        } else {
            // if this is not-a-first function - attachNext it to last function
            lastFunction.attachNext(function);
            lastFunction = function;
        }

        return this;
    }

    /**
     * This function merges multiple separate pipelines of the same time, producing single element
     *
     * @param function
     * @param pipelines
     * @return
     */
    public Pipeline<IN> merge(MergeFunction<IN> function, Pipeline<IN>... pipelines) {
        function.registerPipeline(this);
        if (firstFunction == null) {
            firstFunction = function;
            lastFunction = function;
        } else {
            // if this is not-a-first function - attachNext it to last function
            lastFunction.attachNext(function);
            lastFunction = function;
        }
        function.attachPipelines(pipelines);
        return this;
    }

    /**
     * This function makes all subsequent functions to process inputs from all pipelines passed here.
     * I.e. if at some point you have 2 independent pipelines, you can unify them, so their output will be eventually included in the same MultiDataSet
     *
     * Typically you want this function somewhere at the end of pipelines, when all pipelines have INDArrays as output type
     *
     * @param pipelines
     * @return
     */
    public Pipeline<IN> unify(Pipeline<IN>... pipelines) {


        return this;
    }

    /**
     * This function transforms given input in some way, without any type change
     *
     * @param function
     * @return
     */
    public Pipeline<IN> map(@NonNull Function<IN> function) {
        function.registerPipeline(this);
        if (firstFunction == null) {
            firstFunction = function;
            lastFunction = function;
        } else {
            // if this is not-a-first function - attachNext it to last function
            lastFunction.attachNext(function);
            lastFunction = function;
        }

        return this;
    }

    /**
     * This function transforms given input to something else, with type change
     * I.e. if you have NLP pipeline, you can use convert() to change tokens into vectors.
     *
     * @param function
     * @param <OUT>
     * @return
     */
    public <OUT> Pipeline<OUT> convert(@NonNull ConverterFunction<IN, OUT> function) {
        function.registerPipeline(this);
        if (firstFunction == null) {
            firstFunction = function;
            lastFunction = function;
        } else {
            // if this is not-a-first function - attachNext it to last function
            lastFunction.attachNext(function);
            lastFunction = function;
        }

        // here we want to create new Pipeline, out of this one
        Pipeline<OUT> nextPipe = new Pipeline<OUT>(this);

        // output of convert function is input of next pipeline
        function.attachInputFunction(nextPipe.inputFunction);
        postPipelines.add(nextPipe);

        return nextPipe;
    }

    /**
     * This function accumulates previously split values (if any) into single element.
     *
     * @param function
     * @return
     */
    public Pipeline<IN> accumulate(@NonNull AccumulationFunction<IN> function) {
        function.registerPipeline(this);
        if (firstFunction == null) {
            firstFunction = function;
            lastFunction = function;
        } else {
            // if this is not-a-first function - attachNext it to last function
            lastFunction.attachNext(function);
            lastFunction = function;
        }

        return this;
    }


    protected boolean hasAccumulated() {
        if (lastFunction == null)
            return false;

        return lastFunction.hasNext();
    }

    /**
     * This method will be used at the end of pipeline
     * @return
     */
    public Iterator<IN> iterator() {
        // TODO: despite what's written in javadoc, it would be nice if this method would be transparent
        /**
         * Plan will be simple - we have DAG of ops, and when calling iterator() on the last pipeline element - we're activating whole pipeline, that'll eventually produce output
         */
        if ((firstFunction == null || lastFunction == null) && prePipelines.size() == 0)
            throw new IllegalStateException("Pipeline has no functions or previous pipelines defined, nothing to do here");

        return new PipelineIterator(this);
    }


    protected IN passthrough(IN sample) {
        log.info("Execution passthrough: {}", firstFunction.getClass().getCanonicalName());
        if (postPipelines.size() == 0) {
            return firstFunction.execute(sample);
        } else {
            // we need to pass data through this pipeline
            firstFunction.execute(sample);

            // and return output of next pipeline
            //postPipelines.get(0).firstFunction.execute(sample);
            postPipelines.get(0).passthrough();
            return null;
        }
    }

    protected void passthrough() {
        if (hasAccumulated()) {
            finalOne = firstFunction.execute();

            if (postPipelines.size() > 0)
                postPipelines.get(0).passthrough();

            log.info("Accumulated final one: {}", finalOne);
            // FIXME: this instanceOf is bad. We need better way to find out how to execute this pipeline: one by one, or all at once
        } else if (inputFunction instanceof PipelineInputFunction && hasGreedyFunctions()) {
            finalOne = firstFunction.execute(inputFunction);
        } else {
            finalOne = firstFunction.execute(inputFunction.next());
        }
    }


    public boolean hasGreedyFunctions() {
        if (postPipelines.size() == 0) {
            return firstFunction.isGreedyFunction();
        } else {
            return postPipelines.get(0).hasGreedyFunctions();
        }
    }

    protected class PipelineIterator implements Iterator<IN> {
        protected Pipeline<IN> pipeline;
        protected PipelineIterator parentIterator;


        protected PipelineIterator(@NonNull Pipeline<IN> pipeline) {
            this.pipeline = pipeline;

            if (pipeline.prePipelines.size() > 0) {
                parentIterator = (PipelineIterator) pipeline.prePipelines.get(0).iterator();
            }
        }

        @Override
        public boolean hasNext() {
            /*
             *  We should pass this request to the bottom of pipeline, because each function will be deciding, when one batch ends, and other begins
             */
            // in any case own accumulated blocks are served first
            if (pipeline.hasAccumulated())
                return true;

            if (pipeline.prePipelines.size() == 0) {
                // if we're on first or only pipeline, we just go directly here
                log.info("Pipeline: {}", pipeline);
                log.info("Input function: {}", pipeline.inputFunction);
                boolean res = pipeline.hasAccumulated() || pipeline.inputFunction.hasNext();

                log.info("Own result: {}", res);

                return res;
            } else {
                // otherwise we check parent pipeline for accumulations
                if (pipeline.prePipelines.get(0).hasAccumulated()) {
                    return true;
                }

                // and check parent pipeline's iterator
                boolean res = parentIterator.hasNext();
                log.info("Parent result: {}", res);
                return res;
            }
        }

        @Override
        public IN next() {
            if (pipeline.hasAccumulated())
                return pipeline.lastFunction.poll();

            // if there's something accumulated in functions - consume it
            if (pipeline.prePipelines.size() == 0) {

                // we're passing next() block of data through functions of this pipeline
                return pipeline.passthrough(pipeline.inputFunction.next());
            } else {
                if (pipeline.prePipelines.get(0).hasAccumulated()) {
                    pipeline.prePipelines.get(0).passthrough();

                    log.info("Going to return: {}", finalOne);
                    return finalOne;
                } else {
                    // we're invoking pipeline from the very beginning till the very end
                    pipeline.prePipelines.get(0).passthrough(pipeline.prePipelines.get(0).inputFunction.next());

                    // we just extract next element
                    return finalOne; //pipeline.passthrough(pipeline.inputFunction.next());
                }
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Builder<I> {
        protected InputFunction<I>[] inputFunctions;
        protected String[] inputNames;
        protected boolean allowParallelism = false;

        // we probably don't need this field
        protected String[] outputNames;

        public Builder(@NonNull InputFunction<I> input) {
            this.inputFunctions = new InputFunction[]{input};
        }

        @Deprecated
        public Builder(@NonNull InputFunction<I>... inputs) {
            if (inputs == null || inputs.length == 0)
                throw new IllegalStateException("You should have at least one InputFunction defined");

            this.inputFunctions = inputs;
        }

        @Deprecated
        public Builder<I> setInputNames(String... inputs) {
            if (inputs == null || inputs.length != inputFunctions.length)
                throw new IllegalStateException("Number of InputNames should match number of InputFunctions");

            this.inputNames = inputs;
            return this;
        }

        /**
         *
         * Default value: false
         *
         * @param allowParallel
         * @return
         */
        public Builder<I> allowParallelProcessing(boolean allowParallel) {
            this.allowParallelism = allowParallel;
            return this;
        }

        public Pipeline<I> build() {
            return new Pipeline<I>(this);
        }
    }
}
