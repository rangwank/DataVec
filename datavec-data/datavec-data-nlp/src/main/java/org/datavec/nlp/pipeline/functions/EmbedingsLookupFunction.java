package org.datavec.nlp.pipeline.functions;

import org.datavec.api.pipelines.api.ConverterFunction;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public class EmbedingsLookupFunction implements ConverterFunction<String, INDArray> {
    @Override
    public INDArray call(String input) {
        return null;
    }

    @Override
    public Iterator<INDArray> call(Iterator<String> input) {
        return null;
    }
}
