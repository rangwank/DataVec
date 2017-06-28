package org.datavec.api.pipelines.api;

import java.io.Serializable;

/**
 * @author raver119@gmail.com
 */
public interface Function<T> extends Serializable {
    T call(T input);
}
