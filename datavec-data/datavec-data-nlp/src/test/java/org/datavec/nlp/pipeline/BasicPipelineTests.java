package org.datavec.nlp.pipeline;

import lombok.extern.slf4j.Slf4j;
import org.datavec.api.pipelines.Pipeline;
import org.datavec.api.pipelines.api.ConverterFunction;
import org.datavec.api.pipelines.api.Function;
import org.datavec.api.pipelines.api.InputFunction;
import org.datavec.api.pipelines.api.PipelineFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractConverterFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractSplitFunction;
import org.datavec.api.pipelines.functions.generic.IteratorInputFunction;
import org.datavec.nlp.pipeline.functions.SentenceBreakerFunction;
import org.datavec.nlp.sentence.SimpleSentenceBreaker;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * This class holds basic syntax/graph building
 * @author raver119@gmail.com
 */
@Slf4j
public class BasicPipelineTests {

    @Test
    public void testBasicBuilder1() throws Exception {
        Pipeline<String> pipeline = new Pipeline.Builder<String>(new IteratorInputFunction<String>())
                .build();

        // we expect input will be defined when execution starts
        pipeline.split(new SentenceBreakerFunction(new SimpleSentenceBreaker()));

        Iterator<String> iterator = pipeline.iterator();

        // since we hadn't loaded anything into pipeline - we should have false here
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testBasicBuilder2() throws Exception {

        InputFunction<String> inputFunction = new IteratorInputFunction<>();
        Pipeline<String> pipeline = new Pipeline.Builder<String>(inputFunction)
                .build();

        String document = "Sentence one. Sentence two.";

        List<String> exp = new ArrayList<>();
        exp.add("Sentence one.");
        exp.add("Sentence two.");

        // feeding single sample here
        inputFunction.addDataSample(document);

        // we expect input will be defined when execution starts
        pipeline.split(new SentenceBreakerFunction(new SimpleSentenceBreaker()));

        Iterator<String> iterator = pipeline.iterator();

        // basically we expect that we'll pass single String as input, and we'll have 2 Strings as output
        int cnt = 0;
        while (iterator.hasNext()) {
            String output = iterator.next();

            assertEquals(exp.get(cnt), output);
            cnt++;
        }

        assertEquals(2, cnt);
    }

    @Test
    public void testBasicBuilder3() throws Exception {

        InputFunction<String> inputFunction = new IteratorInputFunction<>();
        Pipeline<String> pipeline = new Pipeline.Builder<String>(inputFunction)
                .build();

        String document = "Sentence1 one. Sentence2 two.";

        List<String> exp = new ArrayList<>();
        exp.add("sentence1");
        exp.add("one.");
        exp.add("sentence2");
        exp.add("two.");

        // feeding single sample here
        inputFunction.addDataSample(document);

        // we expect input will be defined when execution starts
        pipeline.split(new SentenceBreakerFunction(new SimpleSentenceBreaker())).split(new AbstractSplitFunction<String>() {
            @Override
            public Iterator<String> split(String input) {
                return Arrays.asList(input.split(" ")).iterator();
            }
        }).map(new AbstractFunction<String>() {
            @Override
            public String call(String input) {
                return input.toLowerCase();
            }
        });

        Iterator<String> iterator = pipeline.iterator();

        // basically we expect that we'll pass single String as input, and we'll have 2 Strings as output
        int cnt = 0;
        while (iterator.hasNext()) {
            String output = iterator.next();

            assertEquals(exp.get(cnt), output);
            cnt++;
        }

        assertEquals(4, cnt);
    }
}
