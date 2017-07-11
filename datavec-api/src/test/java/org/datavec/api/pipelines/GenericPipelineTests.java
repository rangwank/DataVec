package org.datavec.api.pipelines;

import lombok.extern.slf4j.Slf4j;
import org.datavec.api.pipelines.api.Function;
import org.datavec.api.pipelines.api.InputFunction;
import org.datavec.api.pipelines.api.PipelineFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractAccumulationFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractConverterFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractSplitFunction;
import org.datavec.api.pipelines.functions.generic.IteratorInputFunction;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @author raver119@gmail.com
 */
@Slf4j
public class GenericPipelineTests {

    @Test
    public void testAccumulatedValues1() throws Exception {
        AbstractFunction<Integer> function1 = new AbstractFunction<Integer>() {
            @Override
            public Integer call(Integer input) {
                return input + 1;
            }
        };

        AbstractFunction<Integer> function2 = new AbstractFunction<Integer>() {
            @Override
            public Integer call(Integer input) {
                return input + 2;
            }
        };

        AbstractFunction<Integer> function3 = new AbstractFunction<Integer>() {
            @Override
            public Integer call(Integer input) {
                return input + 3;
            }
        };

        function1.attachNext(function2);
        function2.attachNext(function3);

        // functions have nothing within
        assertFalse(function3.hasNext());

        // now we're adding something into function2, just to mimic previous split function for example
        function2.store(5);

        assertTrue(function3.hasNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testBasicBuilder0() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        InputFunction<Integer> inputFunction = new IteratorInputFunction<>(list.iterator());
        Pipeline<Integer> pipeline = new Pipeline.Builder<Integer>(inputFunction)
                .build();


        Iterator<Integer> iterator = pipeline.iterator();
    }

    @Test
    public void testBasicBuilder1() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        InputFunction<Integer> inputFunction = new IteratorInputFunction<>(list.iterator());
        Pipeline<Integer> pipeline = new Pipeline.Builder<Integer>(inputFunction)
                .build();


        Iterator<Integer> iterator = pipeline.map(new AbstractFunction<Integer>() {
            @Override
            public Integer call(Integer input) {
                return input * 2;
            }
        }).iterator();

        int cnt = 0;
        while (iterator.hasNext()) {
            Integer curr = iterator.next();
            assertEquals(list.get(cnt) * 2, curr.intValue());
            cnt++;
        }

        assertEquals(3, cnt);
    }

    @Test
    public void testBasicBuilder3() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        InputFunction<Integer> inputFunction = new IteratorInputFunction<>(list.iterator());
        Pipeline<Integer> pipeline = new Pipeline.Builder<Integer>(inputFunction)
                .build();


        Iterator<Integer> iterator = pipeline.map(new AbstractFunction<Integer>() {
            @Override
            public Integer call(Integer input) {
                return input * 2;
            }
        }).map(new AbstractFunction<Integer>() {
            @Override
            public Integer call(Integer input) {
                return input + 3;
            }
        }).iterator();

        int cnt = 0;
        while (iterator.hasNext()) {
            Integer curr = iterator.next();
            assertEquals((list.get(cnt) * 2) + 3, curr.intValue());
            cnt++;
        }

        assertEquals(3, cnt);
    }


    @Test
    public void testBasicConversion1() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        InputFunction<Integer> inputFunction = new IteratorInputFunction<>(list.iterator());
        Pipeline<Integer> pipeline = new Pipeline.Builder<Integer>(inputFunction)
                .build();


        Iterator<String> iterator = pipeline.map(new AbstractFunction<Integer>() {
            @Override
            public Integer call(Integer input) {
                return input * 2;
            }
        }).convert(new AbstractConverterFunction<Integer, String>() {

            @Override
            public String convert(Integer input) {
                return String.valueOf(input);
            }
        }).iterator();

        int cnt = 0;
        while (iterator.hasNext()) {
            String curr = iterator.next();
            assertEquals(String.valueOf(list.get(cnt) * 2),curr);
            cnt++;
        }

        assertEquals(3, cnt);
    }

    @Test
    public void testBasicConversion2() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        InputFunction<Integer> inputFunction = new IteratorInputFunction<>(list.iterator());
        Pipeline<Integer> pipeline = new Pipeline.Builder<Integer>(inputFunction)
                .build();


        Iterator<String> iterator = pipeline.map(new AbstractFunction<Integer>() {
            @Override
            public Integer call(Integer input) {
                return input * 2;
            }
        }).convert(new AbstractConverterFunction<Integer, String>() {

            @Override
            public String convert(Integer input) {
                log.info("Convert called");
                return String.valueOf(input);
            }
        }).map(new AbstractFunction<String>() {
            @Override
            public String call(String input) {
                return "String is: " + input;
            }
        }).iterator();

        int cnt = 0;
        while (iterator.hasNext()) {
            String curr = iterator.next();
            assertEquals("Failed at " + cnt, "String is: " + String.valueOf(list.get(cnt) * 2),curr);
            cnt++;
        }

        assertEquals(3, cnt);
    }

    @Test
    public void testSplitFunction1() throws Exception {
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(1));
        list.add(String.valueOf(2));
        list.add(String.valueOf(3));

        InputFunction<String> inputFunction = new IteratorInputFunction<>();
        inputFunction.addDataSample("1 2 3");


        Pipeline<String> pipeline = new Pipeline.Builder<String>(inputFunction)
                .build();

        Iterator<String> iterator = pipeline.split(new AbstractSplitFunction<String>() {
            @Override
            public Iterator<String> split(String input) {
                return Arrays.asList(input.split(" ")).iterator();
            }
        }).iterator();


        int cnt = 0;
        while (iterator.hasNext()) {
            String curr = iterator.next();
            assertEquals("Failed at " + cnt, list.get(cnt), curr);
            cnt++;
        }

        assertEquals(3, cnt);
    }

    @Test
    public void testSplitFunction2() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        InputFunction<String> inputFunction = new IteratorInputFunction<>();
        inputFunction.addDataSample("1 2 3");


        Pipeline<String> pipeline = new Pipeline.Builder<String>(inputFunction)
                .build();

        Iterator<Integer> iterator = pipeline.split(new AbstractSplitFunction<String>() {
            @Override
            public Iterator<String> split(String input) {
                return Arrays.asList(input.split(" ")).iterator();
            }
        }).convert(new AbstractConverterFunction<String, Integer>() {

            @Override
            public Integer convert(String input) {
                Integer result = Integer.valueOf(input);
                log.info("Trying to convert [{}]; Result: {}", input, result);
                return result;
            }
        }).iterator();


        int cnt = 0;
        while (iterator.hasNext()) {
            log.info("Trying {}", cnt);
            Integer curr = iterator.next();
            assertEquals("Failed at " + cnt, list.get(cnt).intValue(), curr.intValue());
            cnt++;
        }

        assertEquals(3, cnt);
    }

    @Test
    public void testAccumumation1() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);


        InputFunction<String> inputFunction = new IteratorInputFunction<>();
        inputFunction.addDataSample("3 3 3 1");
        inputFunction.addDataSample("3 3 3 1 10");
        inputFunction.addDataSample("3 3 3 1 10 10");

        Pipeline<String> pipeline = new Pipeline.Builder<String>(inputFunction)
                .build();

        Iterator<Integer> iterator = pipeline.split(new AbstractSplitFunction<String>() {
            @Override
            public Iterator<String> split(String input) {
                return Arrays.asList(input.split(" ")).iterator();
            }
        }).convert(new AbstractConverterFunction<String, Integer>() {

            @Override
            public Integer convert(String input) {
                return Integer.valueOf(input);
            }
        }).accumulate(new AbstractAccumulationFunction<Integer>() {
            @Override
            public Integer accumulate(Iterator<Integer> input) {
                int accumulatedValue = 0;
                while (input.hasNext())
                    accumulatedValue += input.next();

                return accumulatedValue;
            }
        }).iterator();

        int cnt = 0;
        while (iterator.hasNext()) {
            log.info("Trying {}", cnt);
            Integer curr = iterator.next();
            assertEquals("Failed at " + cnt, list.get(cnt).intValue(), curr.intValue());
            cnt++;
        }
    }
}