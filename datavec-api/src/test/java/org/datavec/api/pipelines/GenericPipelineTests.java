package org.datavec.api.pipelines;

import lombok.extern.slf4j.Slf4j;
import org.datavec.api.pipelines.api.Function;
import org.datavec.api.pipelines.api.InputFunction;
import org.datavec.api.pipelines.api.PipelineFunction;
import org.datavec.api.pipelines.functions.abstracts.*;
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
        List<String> list = new ArrayList<>();
        list.add("3331");
        list.add("333110");
        list.add("33311010");


        InputFunction<String> inputFunction = new IteratorInputFunction<>();
        inputFunction.addDataSample("3 3 3 1");
        inputFunction.addDataSample("3 3 3 1 10");
        inputFunction.addDataSample("3 3 3 1 10 10");

        Pipeline<String> pipeline = new Pipeline.Builder<String>(inputFunction)
                .build();

        Iterator<String> iterator = pipeline.split(new AbstractSplitFunction<String>() {
            @Override
            public Iterator<String> split(String input) {
                return Arrays.asList(input.split(" ")).iterator();
            }
        }).accumulate(new AbstractAccumulationFunction<String>() {
            @Override
            public String accumulate(Iterator<String> input) {
                StringBuilder builder = new StringBuilder();
                int accumulatedValue = 0;
                while (input.hasNext())
                    builder.append(input.next());

                return builder.toString();
            }
        }).iterator();

        int cnt = 0;
        while (iterator.hasNext()) {
            log.info("Trying {}", cnt);
            String curr = iterator.next();
            assertEquals("Failed at " + cnt, list.get(cnt), curr);
            cnt++;
        }
    }

    @Test
    public void testAccumumation2() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("3A3A3A1A");
        list.add("3A3A3A1A10A");
        list.add("3A3A3A1A10A10A");


        InputFunction<String> inputFunction = new IteratorInputFunction<>();
        inputFunction.addDataSample("3 3 3 1");
        inputFunction.addDataSample("3 3 3 1 10");
        inputFunction.addDataSample("3 3 3 1 10 10");

        Pipeline<String> pipeline = new Pipeline.Builder<String>(inputFunction)
                .build();

        Iterator<String> iterator = pipeline.split(new AbstractSplitFunction<String>() {
            @Override
            public Iterator<String> split(String input) {
                return Arrays.asList(input.split(" ")).iterator();
            }
        }).map(new AbstractFunction<String>() {
            @Override
            public String call(String input) {
                return input + "A";
            }
        }).accumulate(new AbstractAccumulationFunction<String>() {
            @Override
            public String accumulate(Iterator<String> input) {
                StringBuilder builder = new StringBuilder();
                int accumulatedValue = 0;
                while (input.hasNext())
                    builder.append(input.next());

                return builder.toString();
            }
        }).iterator();

        int cnt = 0;
        while (iterator.hasNext()) {
            log.info("Trying {}", cnt);
            String curr = iterator.next();
            assertEquals("Failed at " + cnt, list.get(cnt), curr);
            cnt++;
        }
    }

    @Test
    public void testAccumumation3() throws Exception {
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
                log.info("Calling for accumulate");
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
            assertNotNull("Failed at " + cnt, curr);
            assertEquals("Failed at " + cnt, list.get(cnt).intValue(), curr.intValue());
            cnt++;
        }
    }

    @Test
    public void testMerge1() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(((1+1) * 3) * 3);
        list.add(((2+2) * 3) * 3);
        list.add(((3+3) * 3) * 3);


        InputFunction<Integer> inputFunction1 = new IteratorInputFunction<>();
        inputFunction1.addDataSample(1);
        inputFunction1.addDataSample(2);
        inputFunction1.addDataSample(3);

        InputFunction<Integer> inputFunction2 = new IteratorInputFunction<>();
        inputFunction2.addDataSample(1);
        inputFunction2.addDataSample(2);
        inputFunction2.addDataSample(3);

        InputFunction<Integer> inputFunction3 = new IteratorInputFunction<>();
        inputFunction3.addDataSample(1);
        inputFunction3.addDataSample(2);
        inputFunction3.addDataSample(3);

        Pipeline<Integer> pipeline1 = new Pipeline<>(inputFunction1).map(new AbstractFunction<Integer>() {
            @Override
            public Integer call(Integer input) {
                return input + input;
            }
        });
        Pipeline<Integer> pipeline2 = new Pipeline<>(inputFunction2).map(new AbstractFunction<Integer>() {
            @Override
            public Integer call(Integer input) {
                return input + input;
            }
        });

        Pipeline<Integer> pipeline3 = new Pipeline<>(inputFunction3).map(new AbstractFunction<Integer>() {
            @Override
            public Integer call(Integer input) {
                return input+input;
            }
        });


        pipeline1.merge(new AbstractMergeFunction<Integer>() {
            @Override
            public Integer merge(List<Integer> inputs) {
                return (inputs.get(0) + inputs.get(1) + inputs.get(2)) * 3;
            }


        }, pipeline2, pipeline3);

        assertTrue(pipeline1.lastFunction instanceof  AbstractMergeFunction);

        Iterator<Integer> iterator = pipeline1.iterator();
        int cnt = 0;
        while (iterator.hasNext()) {
            log.info("Trying {}", cnt);
            Integer curr = iterator.next();
            assertNotNull("Failed at " + cnt, curr);
            assertEquals("Failed at " + cnt, list.get(cnt).intValue(), curr.intValue());
            cnt++;
        }
    }
}