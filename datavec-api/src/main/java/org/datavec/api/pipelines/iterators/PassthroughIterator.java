package org.datavec.api.pipelines.iterators;

import org.datavec.api.pipelines.api.ConverterFunction;
import org.datavec.api.pipelines.api.PipelineFunction;

import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public class PassthroughIterator<IN> implements Iterator<IN>{
    protected Iterator<IN> inputIterator;
    protected PipelineFunction<IN> function;

    public PassthroughIterator(Iterator<IN> inputIterator, PipelineFunction<IN> function) {
        this.inputIterator = inputIterator;
        this.function = function;
    }

    @Override
    public boolean hasNext() {
        return inputIterator.hasNext();
    }

    @Override
    public IN next() {
        return (IN) function.call(inputIterator.next());
    }

    @Override
    public void remove() {
        inputIterator.remove();
    }
}
