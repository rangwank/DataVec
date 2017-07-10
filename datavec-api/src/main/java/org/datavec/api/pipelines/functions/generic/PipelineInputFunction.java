package org.datavec.api.pipelines.functions.generic;

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
public class PipelineInputFunction<IN> implements InputFunction<IN> {
    protected transient Queue<IN> queue = new LinkedTransferQueue<>();
    protected transient AtomicInteger queuePosition = new AtomicInteger(0);

    public PipelineInputFunction(Pipeline pipeline) {
        // we'll be typecasting here probably, because pipeline can have any Input type :/

    }

    @Override
    public void addDataSource(Iterator<IN> source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addDataSample(IN sample) {
        queue.add(sample);
        queuePosition.incrementAndGet();
    }

    @Override
    public boolean hasNext() {
        return queuePosition.get() > 0;
    }

    @Override
    public IN next() {
        return queue.poll();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
