package org.datavec.api.pipelines.functions.generic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.datavec.api.pipelines.Pipeline;
import org.datavec.api.pipelines.api.AccumulationFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractFunction;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Collection;
import java.util.Iterator;

/**
 * Typical output function draft.
 *
 * @author raver119@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchFunction extends AbstractFunction<INDArray> implements AccumulationFunction<INDArray> {
    @Builder.Default protected int batchSize = 8;


    @Override
    public void registerPipeline(Pipeline<INDArray> pipeline) {
        //
    }

    @Override
    public INDArray call(INDArray input) {
        return input;
    }

    @Override
    public Iterator<INDArray> call(Iterator<INDArray> input) {
        return null;
    }

    @Override
    public Iterator<INDArray> call(INDArray... input) {
        return null;
    }

    @Override
    public INDArray accumulate(Iterator<INDArray> input) {
        return null;
    }
}
