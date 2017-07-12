package org.datavec.api.pipelines.functions.generic;

import lombok.extern.slf4j.Slf4j;
import org.datavec.api.pipelines.Pipeline;
import org.datavec.api.pipelines.api.InputFunction;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This InputFunction uses existing Pipeline as Input source.
 * Basically - it will be receiving data as invidiviual samples most of time.
 *
 * In future this class should be removed, and replaced by IteratorInputFunction
 * @author raver119@gmail.com
 */
@Slf4j
public class PipelineInputFunction<IN> extends IteratorInputFunction<IN> implements InputFunction<IN> {
    protected Pipeline<IN> pipeline;

    public PipelineInputFunction(Pipeline<IN> pipeline) {
        super();
        // we'll be typecasting here probably, because pipeline can have any Input type :/
        this.pipeline = pipeline;
    }

    @Override
    public boolean hasGreedyConsumers() {
        return pipeline.hasGreedyFunctions();
    }
}
