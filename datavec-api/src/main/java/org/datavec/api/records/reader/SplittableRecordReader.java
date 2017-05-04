package org.datavec.api.records.reader;

import org.datavec.api.writable.Writable;

import java.util.List;

/**
 * This interface describes RecordReader interface which supports independent parallel iterative data feeding.
 *
 * PLEASE NOTE: All implementations of this interface will be considered thread-safe with different split numbers given.
 * PLEASE NOTE: Thread safety within same splitIndex is NOT assumed, and won't be used in iterators using implementations of this class.
 *
 * @author raver119@gmail.com
 */
public interface SplittableRecordReader extends RecordReader {

    /**
     * This method ensures specified number of independent splits will be available
     *
     * @param numParts
     */
    void configureSplit(int numParts);


    /**
     * This method returns next example from specified split
     *
     * @param splitIndex
     */
    List<Writable> nextFor(int splitIndex);

    /**
     * This method returns specified number of examples from specified split
     *
     * @param splitIndex
     * @param numExamples
     */
    List<Writable> nextFor(int splitIndex, int numExamples);


    /**
     * This method checks, if specified split has anything else availabe in queue
     *
     * @param splitIndex
     */
    boolean hasNextFor(int splitIndex);
}
