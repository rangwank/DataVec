package org.datavec.api.pipelines.iterators;

import org.datavec.api.pipelines.api.ConverterFunction;

import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public class ConverterIterator<IN, OUT> implements Iterator<OUT>{
    protected Iterator<IN> inputIterator;
    protected ConverterFunction<IN, OUT> converterFunction;

    public ConverterIterator(Iterator<IN> inputIterator, ConverterFunction<IN, OUT> converterFunction) {
        this.inputIterator = inputIterator;
        this.converterFunction = converterFunction;
    }

    @Override
    public boolean hasNext() {
        return inputIterator.hasNext();
    }

    @Override
    public OUT next() {
        return (OUT) converterFunction.convert(inputIterator.next());
    }

    @Override
    public void remove() {
        inputIterator.remove();
    }
}
