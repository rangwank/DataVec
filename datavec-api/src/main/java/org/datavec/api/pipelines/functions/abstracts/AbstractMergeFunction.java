package org.datavec.api.pipelines.functions.abstracts;

import org.datavec.api.pipelines.Pipeline;
import org.datavec.api.pipelines.api.MergeFunction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author raver119@gmail.com
 */
public abstract class AbstractMergeFunction<IN> extends AbstractFunction<IN> implements MergeFunction<IN> {
    protected Pipeline<IN>[] pipelines;
    protected Iterator<IN>[] iterators;

    @Override
    public void attachPipelines(Pipeline<IN>... pipelines) {
        this.pipelines = pipelines;
        iterators = new Iterator[pipelines.length];

        int cnt = 0;
        for (Pipeline<IN> pipeline: pipelines) {
            iterators[cnt++] = pipeline.iterator();
        }
    }

    @Override
    public IN call(IN input) {
        List<IN> inputs = new ArrayList<>();
        inputs.add(input);
        for (Iterator<IN> iterator: iterators) {
            if (!iterator.hasNext())
                throw new NoSuchElementException();

            inputs.add(iterator.next());
        }
        return merge(inputs);
    }
}
