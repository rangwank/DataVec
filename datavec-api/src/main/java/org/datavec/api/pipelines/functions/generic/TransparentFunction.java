package org.datavec.api.pipelines.functions.generic;

import lombok.extern.slf4j.Slf4j;
import org.datavec.api.pipelines.Pipeline;
import org.datavec.api.pipelines.api.Function;
import org.datavec.api.pipelines.functions.abstracts.AbstractFunction;

import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
@Slf4j
public class TransparentFunction<IN> extends AbstractFunction<IN> implements Function<IN> {
    @Override
    public void registerPipeline(Pipeline<IN> pipeline) {

    }

    @Override
    public IN call(IN input) {
        log.info("Calling TransparentFunction");
        return input;
    }

    @Override
    public IN accumulate(Iterator<IN> input) {
        throw new UnsupportedOperationException();
    }
}
