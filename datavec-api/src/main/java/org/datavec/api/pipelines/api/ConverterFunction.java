package org.datavec.api.pipelines.api;

import java.util.Collection;
import java.util.Iterator;

/**
 * This interface describes conversions between different inputs. I.e. String -> INDArray.
 * Number of output entries will be the same, as number of input entries
 *
 * @author raver119@gmail.com
 */
public interface ConverterFunction<IN, OUT> extends Function2<IN, OUT> {

    void attachInputFunction(InputFunction<OUT> inputFunction);

    Iterator<OUT> call(Iterator<IN> input);
}
