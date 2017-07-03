package org.datavec.api.pipelines;

import org.datavec.api.pipelines.api.InputFunction;
import org.datavec.api.writable.Writable;
import org.nd4j.linalg.dataset.api.MultiDataSet;
import org.nd4j.linalg.dataset.api.MultiDataSetPreProcessor;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;

import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public class Pipeline<I> implements Iterator<MultiDataSet> {

    protected Pipeline() {
        // no-op
    }

    public Pipeline(Builder builder) {
        //
    }


    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public MultiDataSet next() {
        return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public static class Builder<I> {
        protected InputFunction<I>[] inputFunctions;
        protected String[] inputNames;

        public Builder(InputFunction<I>... inputs) {
            if (inputs == null || inputs.length == 0)
                throw new IllegalStateException("You should have at least one InputFunction defined");

            this.inputFunctions = inputs;
        }

        public Builder<I> setInputNames(String... inputs) {
            if (inputs == null || inputs.length != inputFunctions.length)
                throw new IllegalStateException("Number of InputNames should match number of InputFunctions");

            this.inputNames = inputs;
            return this;
        }

        public Pipeline build() {
            return new Pipeline<I>(this);
        }
    }
}
