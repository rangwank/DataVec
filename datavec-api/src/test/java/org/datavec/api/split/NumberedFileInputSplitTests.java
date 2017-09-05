package org.datavec.api.split;

import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;

public class NumberedFileInputSplitTests {
    @Test
    public void testNumberedFileInputSplitBasic() {
        String baseString = "/path/to/files/prefix%d.suffix";
        int minIdx = 0;
        int maxIdx = 10;
        runNumberedFileInputSplitTest(baseString, minIdx, maxIdx);
    }

    @Test
    public void testNumberedFileInputSplitVaryIndeces() {
        String baseString = "/path/to/files/prefix-%d.suffix";
        int minIdx = 3;
        int maxIdx = 27;
        runNumberedFileInputSplitTest(baseString, minIdx, maxIdx);
    }

    @Test
    public void testNumberedFileInputSplitBasicNoPrefix() {
        String baseString = "/path/to/files/%d.suffix";
        int minIdx = 0;
        int maxIdx = 10;
        runNumberedFileInputSplitTest(baseString, minIdx, maxIdx);
    }

    @Test
    public void testNumberedFileInputSplitWithLeadingZeroes() {
        String baseString = "/path/to/files/prefix-%07d.suffix";
        int minIdx = 0;
        int maxIdx = 10;
        runNumberedFileInputSplitTest(baseString, minIdx, maxIdx);
    }

    @Test
    public void testNumberedFileInputSplitWithLeadingZeroesNoSuffix() {
        String baseString = "/path/to/files/prefix-%d";
        int minIdx = 0;
        int maxIdx = 10;
        runNumberedFileInputSplitTest(baseString, minIdx, maxIdx);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberedFileInputSplitWithLeadingSpaces() {
        String baseString = "/path/to/files/prefix-%5d.suffix";
        int minIdx = 0;
        int maxIdx = 10;
        runNumberedFileInputSplitTest(baseString, minIdx, maxIdx);
    }

    private static void runNumberedFileInputSplitTest(String baseString, int minIdx, int maxIdx) {
        NumberedFileInputSplit split = new NumberedFileInputSplit(baseString, minIdx, maxIdx);
        URI[] locs = split.locations();
        assertEquals(locs.length, (maxIdx - minIdx) + 1);
        int j = 0;
        for (int i = minIdx; i <= maxIdx; i++) {
            assertEquals(String.format(baseString, i), locs[j++].getPath());
        }
    }
}
