package org.datavec.api.pipelines.api;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;

/**
 * @author raver119@gmail.com
 */
public interface DataSetOutputFunction {

    DataSet call(INDArray features, INDArray labels, INDArray featuresMask, INDArray labelsMask);
}
