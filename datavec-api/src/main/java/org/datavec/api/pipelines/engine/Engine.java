package org.datavec.api.pipelines.engine;

import org.datavec.api.pipelines.api.Function;

import java.io.Serializable;

/**
 * @author raver119@gmail.com
 */
public interface Engine extends Serializable {

    void attach(Function<?> function);
}
