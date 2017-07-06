package org.datavec.api.pipelines.functions.abstracts;

import org.datavec.api.pipelines.api.PipelineFunction;

/**
 * @author raver119@gmail.com
 */
public class AbstractFunction implements PipelineFunction {
    protected PipelineFunction nextFunction;

    @Override
    public void attach(PipelineFunction function) {
        this.nextFunction = function;
    }
}
