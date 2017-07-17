package org.datavec.api.pipelines.api;

import org.datavec.api.pipelines.Pipeline;

import java.util.Collection;
import java.util.List;

/**
 * @author raver119@gmail.com
 */
public interface MergeFunction<IN> extends PipelineFunction<IN> {

    void attachPipelines(Pipeline<IN>...pipelines);

    IN merge(List<IN> inputs);
}
