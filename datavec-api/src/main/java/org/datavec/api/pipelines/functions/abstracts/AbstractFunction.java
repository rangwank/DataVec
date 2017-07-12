package org.datavec.api.pipelines.functions.abstracts;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.datavec.api.pipelines.api.Function;
import org.datavec.api.pipelines.api.PipelineFunction;
import org.datavec.api.pipelines.iterators.ConverterIterator;
import org.datavec.api.pipelines.iterators.PassthroughIterator;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * @author raver119@gmail.com
 */
@Slf4j
public abstract class AbstractFunction<IN> implements PipelineFunction<IN>, Function<IN> {
    protected PipelineFunction<IN> prevFunction;
    protected PipelineFunction<IN> nextFunction;
    protected Queue<IN> queue = new LinkedTransferQueue<>();

    @Override
    public boolean isGreedyFunction() {
        if (nextFunction == null) {
            log.info("Null next");
            return false;
        } else {
            boolean result = nextFunction.isGreedyFunction();
            log.info("NexT result: {}", result);
            return result;
        }
    }

    @Override
    public IN execute(IN input) {
        log.info("Execute on input [{}]", input);
        if (nextFunction != null)
            return nextFunction.execute(call(input));
        else return call(input);
    }

    public IN execute(Iterator<IN> input) {
        if (nextFunction != null)
            return nextFunction.execute(new PassthroughIterator<IN>(input, this));
        else
            return accumulate(input);
    }

    @Override
    public IN accumulate(Iterator<IN> input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IN execute() {
        if (queue.isEmpty()) {
            if (nextFunction != null)
                nextFunction.execute();

            return null;
        } else {
            IN que = queue.poll();
            log.info("Queued element: [{}]", que);
            return execute(que);
        }
    }

    @Override
    public void store(IN input) {
        log.info("Storing value [{}]", input);
        queue.add(input);
    }

    @Override
    public IN poll() {
        // TODO: maybe something better then typecast here?

        /*
            1) check out queue
            2) check parent queue
            3) return null
         */
        if (!queue.isEmpty())
            return queue.poll();

        if (prevFunction !=null)
            return prevFunction.poll();

        return null;
    }

    @Override
    public void attachPrev(@NonNull PipelineFunction function) {
        this.prevFunction = function;
    }

    @Override
    public void attachNext(@NonNull PipelineFunction function) {
        this.nextFunction = function;
        function.attachPrev(this);
    }

    /**
     * This method goes backwards
     * @return
     */
    @Override
    public boolean hasNext() {
        if (!queue.isEmpty())
            return true;

        return prevFunction == null ? false : prevFunction.hasNext();
    }
}
