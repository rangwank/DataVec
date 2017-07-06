package org.datavec.api.pipelines.engine;

import org.datavec.api.pipelines.api.Function;

import java.io.Serializable;

/**
 * This class provides execution functionality for Pipeline Streams
 *
 * @author raver119@gmail.com
 */
public class BaseEngine implements Engine {
    protected String id;


    protected BaseEngine() {
        // used for serialization
    }

    @Override
    public void attach(Function<?> function) {

    }
}
