package org.datavec.api.pipelines.api;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public interface AccumulationFunction<T> extends Function<T> {

    Iterator<T> call(Iterator<T> input);

    Iterator<T> call(T... input);
}
