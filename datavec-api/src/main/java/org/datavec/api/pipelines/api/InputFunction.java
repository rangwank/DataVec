package org.datavec.api.pipelines.api;

import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public interface InputFunction<IN> extends Iterator<IN> {

    /**
     * This method adds given Iterator as input source
     *
     * @param source
     */
    void addDataSource(Iterator<IN> source);

    /**
     * This method adds single DataSample to the Input
     * @param sample
     */
    void addDataSample(IN sample);
}
