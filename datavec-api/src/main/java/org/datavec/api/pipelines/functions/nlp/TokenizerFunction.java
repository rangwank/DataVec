package org.datavec.api.pipelines.functions.nlp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.datavec.api.pipelines.api.SplitFunction;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author raver119@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenizerFunction implements SplitFunction<String> {


    @Override
    public String call(String input) {
        return null;
    }

    @Override
    public Iterator<String> call(Iterator<String> input) {
        return null;
    }
}
