package org.datavec.api.pipelines.functions.abstracts;

import org.datavec.api.pipelines.api.PassthroughFunction;
import org.datavec.api.pipelines.iterators.AbstractIterator;

import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public abstract class AbstractPassthroughFunction<T> extends AbstractFunction implements PassthroughFunction<T> {

    @Override
    public abstract T call(T input);

    @Override
    public Iterator<T> call(final Iterator<T> input) {
        return new AbstractIterator<T>() {
            @Override
            public boolean hasNext() {
                return input.hasNext();
            }

            @Override
            public T next() {
                return call(input.next());
            }
        };
    }


    @Override
    public Iterator<T> call(T[] input) {
        return null;
    }
}
