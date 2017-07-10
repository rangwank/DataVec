package org.datavec.api.pipelines.functions.abstracts;

import org.datavec.api.pipelines.api.ConverterFunction;
import org.datavec.api.pipelines.iterators.AbstractIterator;
import org.datavec.api.pipelines.iterators.ConverterIterator;

import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public abstract class AbstractConverterFunction<IN, OUT> extends AbstractFunction<IN> implements ConverterFunction<IN, OUT> {

    @Override
    public IN call(IN input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<OUT> call(final Iterator<IN> input) {
        return new ConverterIterator<IN, OUT>(input, this);
    }
}
