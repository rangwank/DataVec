package org.datavec.nlp.pipeline.functions;

import org.datavec.api.pipelines.api.SplitFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractSplitFunction;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
public class SentenceBreakerFunction extends AbstractSplitFunction<String> implements SplitFunction<String> {

    @Override
    public Iterator<String> split(String input) {
        return null;
    }
}
