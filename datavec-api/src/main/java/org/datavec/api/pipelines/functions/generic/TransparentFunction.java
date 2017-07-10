package org.datavec.api.pipelines.functions.generic;

import lombok.extern.slf4j.Slf4j;
import org.datavec.api.pipelines.api.Function;
import org.datavec.api.pipelines.functions.abstracts.AbstractFunction;

/**
 * @author raver119@gmail.com
 */
@Slf4j
public class TransparentFunction<IN> extends AbstractFunction<IN> implements Function<IN> {
    @Override
    public IN call(IN input) {
        log.info("Calling TransparentFunction");
        return input;
    }
}
