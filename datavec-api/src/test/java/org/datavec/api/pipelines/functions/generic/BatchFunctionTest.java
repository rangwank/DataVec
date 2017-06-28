package org.datavec.api.pipelines.functions.generic;

import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author raver119@gmail.com
 */
public class BatchFunctionTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testBatch1() throws Exception {
        BatchFunction function = new BatchFunction(8);

        List<INDArray> inputs =  new ArrayList<>();
        for (int x = 0; x < 16; x++) {
            inputs.add(Nd4j.create(3,5).assign(x));
        }

        Iterator<INDArray> batches = function.call(inputs.iterator());
        assertNotNull(batches);

        while (batches.hasNext()) {
            INDArray batch = batches.next();

            assertNotNull(batch);
        }
    }

}