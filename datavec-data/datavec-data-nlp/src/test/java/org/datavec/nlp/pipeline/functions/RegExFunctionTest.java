package org.datavec.nlp.pipeline.functions;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author raver119@gmail.com
 */
@Slf4j
public class RegExFunctionTest {

    @Test
    public void testRegEx1() throws Exception {
        RegExFunction function = new RegExFunction.Builder()
                .addRule("111", "222")
                .build();

        String result = function.call("111 111 222");
        assertEquals("222 222 222", result);
    }
}