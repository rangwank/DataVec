package org.datavec.nlp.pipeline.functions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.datavec.api.pipelines.api.SplitFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractSplitFunction;
import org.datavec.nlp.tokenization.tokenizerfactory.TokenizerFactory;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenizerFunction extends AbstractSplitFunction<String> implements SplitFunction<String> {
    protected TokenizerFactory tokenizerFactory;

    @Override
    public Iterator<String> split(String input) {
        return tokenizerFactory.create(input).getTokens().iterator();
    }
}
