package org.datavec.nlp.pipeline.functions;

import org.datavec.api.pipelines.api.ConverterFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractFunction;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public class EmbedingsLookupFunction extends AbstractFunction implements ConverterFunction<String, INDArray> {
    @Override
    public INDArray call(String input) {
        return null;
    }

    @Override
    public Iterator<INDArray> call(Iterator<String> input) {
        return null;
    }
}
