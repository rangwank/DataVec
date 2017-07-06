package org.datavec.api.pipelines.functions.generic;

import org.datavec.api.pipelines.api.AccumulationFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractFunction;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public class ConcatenateFunction extends AbstractFunction implements AccumulationFunction<INDArray> {
    @Override
    public INDArray call(INDArray input) {
        return null;
    }

    @Override
    public Iterator<INDArray> call(Iterator<INDArray> input) {
        return null;
    }

    @Override
    public Iterator<INDArray> call(INDArray... input) {
        return null;
    }
}
