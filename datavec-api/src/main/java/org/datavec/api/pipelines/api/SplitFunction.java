package org.datavec.api.pipelines.api;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public interface SplitFunction<IN> extends Function<IN>  {

    Iterator<IN> call(Iterator<IN> input);

    Iterator<IN> split(IN input);
}
