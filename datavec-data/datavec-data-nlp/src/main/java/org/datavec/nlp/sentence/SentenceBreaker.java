package org.datavec.nlp.sentence;

import java.util.Collection;

/**
 * This interface describes Sentence Boundary Detector
 *
 * @author raver119@gmail.com
 */
public interface SentenceBreaker {

    /**
     * This method splits given input string into sentences
     *
     * @param input Raw text input
     * @return Collection of string sentences
     */
    Collection<String> split(String input);
}
