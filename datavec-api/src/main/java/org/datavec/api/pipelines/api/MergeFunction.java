package org.datavec.api.pipelines.api;

/**
 * @author raver119@gmail.com
 */
public interface MergeFunction<IN> extends PipelineFunction {
    IN call(IN... inputs);
}
