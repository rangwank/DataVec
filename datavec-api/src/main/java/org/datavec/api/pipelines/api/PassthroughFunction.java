package org.datavec.api.pipelines.api;

import java.util.Iterator;

/**
 * Passthrough function is function that modifies input somehow, but number of inputs equals to number of outputs.
 *
 * @author raver119@gmail.com
 */
public interface PassthroughFunction<IN> extends Function<IN> {

    Iterator<IN> call(Iterator<IN> input);

    Iterator<IN> call(IN... input);
}
