package org.datavec.api.pipelines.iterators;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This utility class implements simple Iterator pattern for single element
 *
 * @author raver119@gmail.com
 */
public class SingleIterator<T> implements Iterator<T> {
    protected AtomicBoolean consumed = new AtomicBoolean(false);
    protected T input;

    public SingleIterator(T input) {
        this.input = input;
    }

    @Override
    public boolean hasNext() {
        return !consumed.get();
    }

    @Override
    public T next() {
        consumed.set(true);
        return input;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
