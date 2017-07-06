package org.datavec.api.pipelines.functions.generic;

import org.datavec.api.pipelines.functions.abstracts.AbstractFunction;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;

/**
 * @author raver119@gmail.com
 */
public class DataSetOutputFunction extends AbstractFunction implements org.datavec.api.pipelines.api.DataSetOutputFunction {
    @Override
    public DataSet call(INDArray features, INDArray labels, INDArray featuresMask, INDArray labelsMask) {
        return new org.nd4j.linalg.dataset.DataSet(features, labels, featuresMask, labelsMask);
    }
}
