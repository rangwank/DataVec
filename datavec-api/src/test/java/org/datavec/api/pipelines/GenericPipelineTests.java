package org.datavec.api.pipelines;

import lombok.extern.slf4j.Slf4j;
import org.datavec.api.pipelines.api.Function;
import org.datavec.api.pipelines.api.InputFunction;
import org.datavec.api.pipelines.api.PipelineFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractConverterFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractFunction;
import org.datavec.api.pipelines.functions.generic.IteratorInputFunction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author raver119@gmail.com
 */
@Slf4j
public class GenericPipelineTests {

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

}