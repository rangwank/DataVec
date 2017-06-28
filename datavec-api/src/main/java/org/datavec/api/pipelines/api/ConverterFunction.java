package org.datavec.api.pipelines.api;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public interface ConverterFunction<T, O> extends Function2<T, O> {

    Iterator<O> call(Iterator<T> input);
}
