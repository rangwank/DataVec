package org.datavec.api.pipelines.api;

import java.io.Serializable;

/**
 * @author raver119@gmail.com
 */
public interface PipelineFunction<IN> extends Serializable {

    void attachPrev(PipelineFunction<IN> function);
    void attachNext(PipelineFunction<IN> function);

    boolean hasNext();

    IN call(IN input);

    IN poll();

    IN execute(IN input);
}
