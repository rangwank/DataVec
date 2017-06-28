package org.datavec.nlp.pipeline.functions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.datavec.api.pipelines.api.SplitFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractSplitFunction;
import org.datavec.nlp.sentence.SentenceBreaker;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
@AllArgsConstructor
@Data
public class SentenceBreakerFunction extends AbstractSplitFunction<String> implements SplitFunction<String> {
    protected SentenceBreaker breaker;

    @Override
    public Iterator<String> split(String input) {
        return breaker.split(input).iterator();
    }
}
