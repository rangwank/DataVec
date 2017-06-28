package org.datavec.nlp.sentence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * This class implements SentenceBreaker interface, using Java BreakIterator.
 *
 * PLEASE NOTE: Thread-safety is provided via synchronized
 * @author raver119@gmail.com
 */
@Data
@Slf4j
public class SimpleSentenceBreaker implements SentenceBreaker {
    protected BreakIterator iterator;

    public SimpleSentenceBreaker() {
        this(Locale.getDefault());
    }

    public SimpleSentenceBreaker(Locale locale) {
        iterator = BreakIterator.getSentenceInstance(locale);
    }

    @Override
    public synchronized Collection<String> split(String input) {
        iterator.setText(input);
        int start = iterator.first();
        int end = iterator.next();
        int lineLength = 0;

        List<String> sentences = new ArrayList<>();
        while (end != BreakIterator.DONE) {
            String sentence = input.substring(start,end);
            sentences.add(sentence.trim());
            start = end;
            end = iterator.next();
        }

        return sentences;
    }
}
