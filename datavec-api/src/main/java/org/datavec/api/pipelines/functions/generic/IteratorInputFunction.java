package org.datavec.api.pipelines.functions.generic;

import lombok.NonNull;
import org.datavec.api.pipelines.api.InputFunction;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author raver119@gmail.com
 */
public class IteratorInputFunction<T> implements InputFunction<T> {
    protected transient List<Iterator<T>> iterators = new CopyOnWriteArrayList<>();
    protected transient Queue<T> queue = new LinkedTransferQueue<>();
    protected transient AtomicInteger iterPosition = new AtomicInteger(0);
    protected transient AtomicInteger queuePosition = new AtomicInteger(0);


    public IteratorInputFunction() {
        //
    }

    public IteratorInputFunction(Iterator<T> source) {
        addDataSource(source);
    }

    @Override
    public void addDataSource(@NonNull Iterator<T> source) {
        iterators.add(source);
    }

    @Override
    public void addDataSample(@NonNull T sample) {
        queue.add(sample);
        queuePosition.incrementAndGet();
    }

    @Override
    public boolean hasNext() {
        if (queuePosition.get() > 0)
            return true;

        return iterPosition.get() < iterators.size() - 1 || (iterPosition.get() < iterators.size() && iterators.get(iterPosition.get()).hasNext());
    }

    @Override
    public T next() {
        if (queuePosition.get() > 0) {
            queuePosition.decrementAndGet();
            return queue.poll();
        } else {
            if (!iterators.get(iterPosition.get()).hasNext())
                iterPosition.getAndIncrement();

            return iterators.get(iterPosition.get()).next();
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }


    @Override
    public boolean hasGreedyConsumers() {
        return false;
    }
}
