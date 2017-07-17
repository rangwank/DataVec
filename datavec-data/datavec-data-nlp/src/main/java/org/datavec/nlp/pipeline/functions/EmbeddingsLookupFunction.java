package org.datavec.nlp.pipeline.functions;

import org.datavec.api.pipelines.api.ConverterFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractConverterFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractFunction;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public class EmbeddingsLookupFunction extends AbstractConverterFunction<String, INDArray> implements ConverterFunction<String, INDArray> {

    @Override
    public Iterator<INDArray> call(Iterator<String> input) {
        return null;
    }

    @Override
    public INDArray convert(String input) {
        return null;
    }
}
