package org.datavec.api.pipelines.functions.generic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author raver119@gmail.com
 */
public class IteratorInputFunctionTest {

    @Test
    public void testQueueRoll1() throws Exception {
        IteratorInputFunction<Integer> iif = new IteratorInputFunction<Integer>();

        for (int x = 0; x < 10; x++)
            iif.addDataSample(x);

        // we should have 10 elements available within
        int cnt = 0;
        while (iif.hasNext()) {
            Integer x = iif.next();

            assertNotNull(x);
            assertEquals(cnt++, x.intValue());
        }

        assertEquals(10, cnt);
    }


    @Test
    public void testIteratorRoll1() throws Exception {
        IteratorInputFunction<Integer> iif = new IteratorInputFunction<Integer>();

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int x = 0; x < 10; x++) {
            list1.add(x);
            list2.add(x+10);
        }

        iif.addDataSource(list1.iterator());
        iif.addDataSource(list2.iterator());

        // we should have 10 elements available within
        int cnt = 0;
        while (iif.hasNext()) {
            Integer x = iif.next();

            assertNotNull(x);
            assertEquals(cnt++, x.intValue());
        }

        assertEquals(20, cnt);
    }



    @Test
    public void testMixedRoll1() throws Exception {
        IteratorInputFunction<Integer> iif = new IteratorInputFunction<Integer>();

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int x = 0; x < 10; x++) {
            list1.add(x);
            list2.add(x+10);
        }

        iif.addDataSource(list1.iterator());
        iif.addDataSource(list2.iterator());

        iif.addDataSample(117);
        iif.addDataSample(119);

        // individual samples go first, that's the Rule :)
        assertEquals(true, iif.hasNext());
        assertEquals(117, iif.next().intValue());

        assertEquals(true, iif.hasNext());
        assertEquals(119, iif.next().intValue());

        // we should have 20 elements available within
        int cnt = 0;
        while (iif.hasNext()) {
            Integer x = iif.next();

            assertNotNull(x);
            assertEquals(cnt++, x.intValue());
        }

        assertEquals(20, cnt);
    }

    @Test
    public void testMixedRoll2() throws Exception {
        IteratorInputFunction<Integer> iif = new IteratorInputFunction<Integer>();

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int x = 0; x < 10; x++) {
            list1.add(x);
            list2.add(x+10);
        }

        iif.addDataSample(117);
        iif.addDataSample(119);

        iif.addDataSource(list1.iterator());
        iif.addDataSource(list2.iterator());

        // individual samples go first, that's the Rule :)
        assertEquals(true, iif.hasNext());
        assertEquals(117, iif.next().intValue());

        assertEquals(true, iif.hasNext());
        assertEquals(119, iif.next().intValue());

        // we should have 20 elements available within
        int cnt = 0;
        while (iif.hasNext()) {
            Integer x = iif.next();

            assertNotNull(x);
            assertEquals(cnt++, x.intValue());
        }

        assertEquals(20, cnt);
    }
}