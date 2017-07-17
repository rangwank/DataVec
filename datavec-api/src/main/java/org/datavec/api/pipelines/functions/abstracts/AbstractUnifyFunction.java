package org.datavec.api.pipelines.functions.abstracts;

import org.datavec.api.pipelines.api.Function;

/**
 * This function should not be ever used manually.
 *
 * @author raver119@gmail.com
 */
public final class AbstractUnifyFunction<IN> extends AbstractFunction<IN> implements Function<IN> {

    /**
     * This method will pass data forward from multiple inputs to form dataset
     *
     * @param input
     * @return
     */
    @Override
    public IN call(IN input) {
        return null;
    }
}
