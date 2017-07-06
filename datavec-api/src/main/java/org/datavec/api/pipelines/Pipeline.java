package org.datavec.api.pipelines;

import com.sun.istack.internal.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.datavec.api.pipelines.api.*;
import org.datavec.api.pipelines.engine.Engine;
import org.datavec.api.writable.Writable;
import org.nd4j.linalg.dataset.api.MultiDataSet;
import org.nd4j.linalg.dataset.api.MultiDataSetPreProcessor;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public class Pipeline<IN> implements Serializable {
    @Getter(AccessLevel.PROTECTED)
    protected Engine engine;


    protected PipelineFunction firstFunction;
    protected PipelineFunction lastFunction;

    protected Pipeline() {
        // no-op, for internal use only
    }

    protected <I> Pipeline(Pipeline<I> pipeline) {

    }

    public <I> Pipeline(Builder<I> builder) {
        //
    }


    public Pipeline<IN> split(SplitFunction<IN> function) {
        // if this func is first in pipeline, do not attach it anywhere
        if (firstFunction == null) {
            firstFunction = function;
            lastFunction = function;
        } else {
            // if this is not-a-first function - attach it to last function
            lastFunction.attach(function);
            lastFunction = function;
        }

        return this;
    }

    public Pipeline<IN> map(Function<IN> function) {

        return this;
    }

    public <OUT> Pipeline<OUT> convert(ConverterFunction<IN, OUT> function) {
        // here we want to create new Pipeline, out of this one
        return new Pipeline<OUT>();
    }

    public Pipeline<IN> accumulate(AccumulationFunction<IN> function) {

        return this;
    }

    /**
     * This method should be used at the end of pipeline
     * @return
     */
    public Iterator<IN> iterator() {
        // TODO: despite what's written in javadoc, it would be nice if this method would be transparent
        /**
         * Plan will be simple - we'll build DAG of ops,
         */
        return new Iterator<IN>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public IN next() {
                return null;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
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
