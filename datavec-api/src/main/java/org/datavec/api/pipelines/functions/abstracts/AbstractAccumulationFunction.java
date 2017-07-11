package org.datavec.api.pipelines.functions.abstracts;

import org.datavec.api.pipelines.api.AccumulationFunction;

import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public abstract class AbstractAccumulationFunction<IN> extends AbstractFunction<IN> implements AccumulationFunction<IN> {
    @Override
    public IN call(IN input) {
        return null;
    }


    @Override
    public Iterator<IN> call(Iterator<IN> input) {
        return null;
    }

    @Override
    public Iterator<IN> call(IN[] input) {
        return null;
    }

    @Override
    public boolean isGreedyFunction() {
        return true;
    }
}
