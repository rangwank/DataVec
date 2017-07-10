package org.datavec.api.pipelines.api;

import java.io.Serializable;

/**
 * @author raver119@gmail.com
 */
public interface Function2<IN, OUT> extends PipelineFunction<IN> {
    OUT convert(IN input);
}
