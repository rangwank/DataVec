package org.datavec.api.pipelines.functions.abstracts;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.datavec.api.pipelines.api.ConverterFunction;
import org.datavec.api.pipelines.api.InputFunction;
import org.datavec.api.pipelines.iterators.AbstractIterator;
import org.datavec.api.pipelines.iterators.ConverterIterator;

import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
@Slf4j
public abstract class AbstractConverterFunction<IN, OUT> extends AbstractFunction<IN> implements ConverterFunction<IN, OUT> {
    protected InputFunction<OUT> nextInputFunction;

    @Override
    public void attachInputFunction(@NonNull InputFunction<OUT> inputFunction) {
        nextInputFunction = inputFunction;
    }

    @Override
    public IN execute(IN input) {
        log.info("Trying to execute on converter");

        nextInputFunction.addDataSample(convert(input));
        // this line is irrelevant, output of last function won't be used anywhere
        return input;
    }

    @Override
    public IN call(IN input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<OUT> call(final Iterator<IN> input) {
        return new ConverterIterator<IN, OUT>(input, this);
    }
}
