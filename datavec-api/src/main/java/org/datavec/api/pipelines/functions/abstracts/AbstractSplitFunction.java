package org.datavec.api.pipelines.functions.abstracts;

import lombok.extern.slf4j.Slf4j;
import org.datavec.api.pipelines.api.SplitFunction;
import org.datavec.api.pipelines.iterators.AbstractIterator;

import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
@Slf4j
public abstract class AbstractSplitFunction<IN> extends AbstractFunction<IN> implements SplitFunction<IN> {

    @Override
    public IN execute(IN input) {
        log.info("Trying to split on input [{}]", input);
        Iterator<IN> iterator = split(input);

        if (isGreedyFunction()) {
            // it is possible ONLY if nextFunction != null, because split function can't be accumulator

            return nextFunction.execute(iterator);
        } else {
            if (nextFunction != null) {
                IN ret = nextFunction.execute(iterator.next());
                while (iterator.hasNext())
                    nextFunction.store(iterator.next());

                return ret;
            } else {
                while (iterator.hasNext())
                    queue.add(iterator.next());

                return queue.poll();
            }
        }
    }


    @Override
    public abstract Iterator<IN> split(IN input);


    @Override
    public IN call(IN input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<IN> call(final Iterator<IN> input) {
        return input.hasNext() ? split(input.next()) : null;
    }
}
