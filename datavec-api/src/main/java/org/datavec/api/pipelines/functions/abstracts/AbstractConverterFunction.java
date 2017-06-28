package org.datavec.api.pipelines.functions.abstracts;

import org.datavec.api.pipelines.api.ConverterFunction;
import org.datavec.api.pipelines.iterators.AbstractIterator;

import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public abstract class AbstractConverterFunction<T, O> implements ConverterFunction<T, O> {

    @Override
    public abstract O call(T input);

    @Override
    public Iterator<O> call(final Iterator<T> input) {
        return new AbstractIterator<O>() {
            @Override
            public boolean hasNext() {
                return input.hasNext();
            }

            @Override
            public O next() {
                return call(input.next());
            }
        };
    }
}
