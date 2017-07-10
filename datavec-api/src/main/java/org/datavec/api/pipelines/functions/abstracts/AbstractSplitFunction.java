package org.datavec.api.pipelines.functions.abstracts;

import org.datavec.api.pipelines.api.SplitFunction;
import org.datavec.api.pipelines.iterators.AbstractIterator;

import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public abstract class AbstractSplitFunction<IN> extends AbstractFunction<IN> implements SplitFunction<IN> {

    @Override
    public abstract Iterator<IN> split(IN input);

    @Override
    public Iterator<IN> call(final Iterator<IN> input) {
        return input.hasNext() ? split(input.next()) : null;
    }
}
