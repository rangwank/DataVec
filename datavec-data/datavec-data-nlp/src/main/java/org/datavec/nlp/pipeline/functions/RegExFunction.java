package org.datavec.nlp.pipeline.functions;

import lombok.*;
import org.datavec.api.berkeley.Pair;
import org.datavec.api.pipelines.api.PassthroughFunction;
import org.datavec.api.pipelines.functions.abstracts.AbstractPassthroughFunction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This function provides static replacements functionality using regular expressions
 * @author raver119@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegExFunction extends AbstractPassthroughFunction<String> implements PassthroughFunction<String> {
    protected List<Pair<String, String>> replacements;

    @Override
    public String call(String input) {
        // TODO: use buffer for regex, instead of strings here
        StringBuffer buffer = new StringBuffer(input);
        for (Pair<String, String> replacement: replacements) {
            input = input.replaceAll(replacement.getFirst(), replacement.getSecond());
        }

        return input;
    }

    public static class Builder {
        protected List<Pair<String, String>> replacements = new ArrayList<>();

        public Builder() {
            //
        }

        public Builder addRule(String needle, @NonNull String replacement) {
            replacements.add(new Pair<String, String>(needle, replacement));
            return this;
        }

        public RegExFunction build() {
            return new RegExFunction(replacements);
        }
    }
}
