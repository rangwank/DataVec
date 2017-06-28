package org.datavec.api.pipelines.iterators;

import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public abstract class AbstractIterator<T> implements Iterator<T> {
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
