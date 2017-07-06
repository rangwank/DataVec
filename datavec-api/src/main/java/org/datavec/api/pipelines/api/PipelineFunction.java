package org.datavec.api.pipelines.api;

import java.io.Serializable;

/**
 * @author raver119@gmail.com
 */
public interface PipelineFunction extends Serializable {

    void attach(PipelineFunction function);
}
