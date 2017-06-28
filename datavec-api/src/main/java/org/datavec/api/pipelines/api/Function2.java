package org.datavec.api.pipelines.api;

import java.io.Serializable;

/**
 * @author raver119@gmail.com
 */
public interface Function2<T, O> extends Serializable {
    O call(T input);
}
