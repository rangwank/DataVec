package org.datavec.nlp.pipeline.functions;

import org.datavec.nlp.sentence.SimpleSentenceBreaker;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author raver119@gmail.com
 */
public class SentenceBreakerFunctionTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSentenceBreaker1() throws Exception {
        SimpleSentenceBreaker breaker = new SimpleSentenceBreaker();
        SentenceBreakerFunction function = new SentenceBreakerFunction(breaker);

        List<String> exp = new ArrayList<>();
        exp.add("Sentence one.");
        exp.add("Sentence two.");

        Iterator<String> sentences = function.split("Sentence one. Sentence two.");

        int cnt = 0;
        while (sentences.hasNext()) {
            String sentence = sentences.next();

            assertEquals(exp.get(cnt), sentence);
            cnt++;
        }

        assertEquals(2, cnt);
    }
}