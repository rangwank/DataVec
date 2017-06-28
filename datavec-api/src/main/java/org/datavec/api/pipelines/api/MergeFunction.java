package org.datavec.api.pipelines.api;

/**
 * @author raver119@gmail.com
 */
public interface MergeFunction<T> {
    T call(T... inputs);
}
