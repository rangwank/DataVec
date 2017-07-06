package org.datavec.api.pipelines.functions.abstracts;

import org.datavec.api.pipelines.api.SplitFunction;
import org.datavec.api.pipelines.iterators.AbstractIterator;

import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public abstract class AbstractSplitFunction<T> extends AbstractFunction implements SplitFunction<T> {

    @Override
    public T call(T input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public abstract Iterator<T> split(T input);

    @Override
    public Iterator<T> call(final Iterator<T> input) {
        return input.hasNext() ? split(input.next()) : null;
    }
}
