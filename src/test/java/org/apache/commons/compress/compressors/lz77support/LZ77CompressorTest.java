/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.commons.compress.compressors.lz77support;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class LZ77CompressorTest {

    private static final byte[] BLA, SAM, ONE_TO_TEN;

    static {
        try {
            /*
             * Example from "An Explanation of the Deflate Algorithm" by "Antaeus Feldspar".
             * @see "http://zlib.net/feldspar.html"
             */
            BLA = "Blah blah blah blah blah!".getBytes("ASCII");

            /*
             * Example from Wikipedia article about LZSS.
             * Note the example uses indices instead of offsets.
             * @see "https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Storer%E2%80%93Szymanski"
             */
            SAM = ("I am Sam\n"
                   + "\n"
                   + "Sam I am\n"
                   + "\n"
                   + "That Sam-I-am!\n"
                   + "That Sam-I-am!\n"
                   + "I do not like\n"
                   + "that Sam-I-am!\n"
                   + "\n"
                   + "Do you like green eggs and ham?\n"
                   + "\n"
                   + "I do not like them, Sam-I-am.\n"
                   + "I do not like green eggs and ham.").getBytes("ASCII");
        } catch (IOException ex) {
            throw new RuntimeException("ASCII not supported");
        }
        ONE_TO_TEN = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    }

    private List<LZ77Compressor.Block> compress(Parameters params, byte[]... chunks) throws IOException {
        final List<LZ77Compressor.Block> blocks = new ArrayList<>();
        LZ77Compressor c = new LZ77Compressor(params, new LZ77Compressor.Callback() {
                @Override
                public void accept(LZ77Compressor.Block block) {
                    //System.err.println(block);
                    if (block instanceof LZ77Compressor.LiteralBlock) {
                        // replace with a real copy of data so tests
                        // can see the results as they've been when
                        // the callback has been called
                        LZ77Compressor.LiteralBlock b = (LZ77Compressor.LiteralBlock) block;
                        int len = b.getLength();
                        block = new LZ77Compressor.LiteralBlock(
                            Arrays.copyOfRange(b.getData(), b.getOffset(), b.getOffset() + len),
                            0, len);
                    }
                    blocks.add(block);
                }
            });
        for (byte[] chunk : chunks) {
            c.compress(chunk);
        }
        c.finish();
        return blocks;
    }

    @Test
    public void nonCompressableWithLengthSmallerThanLiteralMax() throws IOException {
        List<LZ77Compressor.Block> blocks = compress(newParameters(128), ONE_TO_TEN);
        assertSize(2, blocks);
        assertLiteralBlock(ONE_TO_TEN, blocks.get(0));
    }

    @Test
    public void nonCompressableWithLengthGreaterThanLiteralMaxButLessThanTwiceWindowSize() throws IOException {
        List<LZ77Compressor.Block> blocks = compress(newParameters(8), ONE_TO_TEN);
        assertSize(3, blocks);
        assertLiteralBlock(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 }, blocks.get(0));
        assertLiteralBlock(new byte[] { 9, 10 }, blocks.get(1));
    }

    @Test
    public void nonCompressableWithLengthThatForcesWindowSlide() throws IOException {
        List<LZ77Compressor.Block> blocks = compress(newParameters(4), ONE_TO_TEN);
        assertSize(4, blocks);
        assertLiteralBlock(new byte[] { 1, 2, 3, 4, }, blocks.get(0));
        assertLiteralBlock(new byte[] { 5, 6, 7, 8 }, blocks.get(1));
        assertLiteralBlock(new byte[] { 9, 10 }, blocks.get(2));
    }

    @Test
    public void nonCompressableSentAsSingleBytes() throws IOException {
        List<LZ77Compressor.Block> blocks = compress(newParameters(8), stagger(ONE_TO_TEN));
        assertSize(3, blocks);
        assertLiteralBlock(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 }, blocks.get(0));
        assertLiteralBlock(new byte[] { 9, 10 }, blocks.get(1));
    }

    @Test
    public void blaExampleWithFullArrayAvailableForCompression()
        throws IOException {
        List<LZ77Compressor.Block> blocks = compress(newParameters(128), BLA);
        assertSize(4, blocks);
        assertLiteralBlock("Blah b", blocks.get(0));
        assertBackReference(5, 18, blocks.get(1));
        assertLiteralBlock("!", blocks.get(2));
    }

    @Test
    public void blaExampleWithShorterBackReferenceLength() throws IOException {
        List<LZ77Compressor.Block> blocks = compress(newParameters(128, 3, 5, 0, 0), BLA);
        assertSize(7, blocks);
        assertLiteralBlock("Blah b", blocks.get(0));
        assertBackReference(5, 5, blocks.get(1));
        assertBackReference(5, 5, blocks.get(2));
        assertBackReference(5, 5, blocks.get(3));
        assertBackReference(5, 3, blocks.get(4));
        assertLiteralBlock("!", blocks.get(5));
    }

    @Test
    public void blaExampleSmallerWindowSize() throws IOException {
        List<LZ77Compressor.Block> blocks = compress(newParameters(8), BLA);
        assertSize(6, blocks);
        assertLiteralBlock("Blah b", blocks.get(0));
        assertBackReference(5, 7, blocks.get(1));
        assertBackReference(5, 3, blocks.get(2));
        assertBackReference(5, 7, blocks.get(3));
        assertLiteralBlock("h!", blocks.get(4));
    }

    @Test
    public void blaExampleWithSingleByteWrites() throws IOException {
        List<LZ77Compressor.Block> blocks = compress(newParameters(128), stagger(BLA));
        assertEquals(9, blocks.size());
        assertLiteralBlock("Blah b", blocks.get(0));
        assertBackReference(5, 3, blocks.get(1));
        assertBackReference(5, 3, blocks.get(2));
        assertBackReference(5, 3, blocks.get(3));
        assertBackReference(5, 3, blocks.get(4));
        assertBackReference(5, 3, blocks.get(5));
        assertBackReference(5, 3, blocks.get(6));
        assertLiteralBlock("!", blocks.get(7));
    }

    @Test
    public void samIAmExampleWithFullArrayAvailableForCompression() throws IOException {
        List<LZ77Compressor.Block> blocks = compress(newParameters(1024), SAM);
        assertEquals(21, blocks.size());
        assertLiteralBlock("I am Sam\n\n", blocks.get(0));
        assertBackReference(5, 3, blocks.get(1));
        assertLiteralBlock(" ", blocks.get(2));
        assertBackReference(14, 4, blocks.get(3));
        assertLiteralBlock("\n\nThat", blocks.get(4));
        assertBackReference(20, 4, blocks.get(5));
        assertLiteralBlock("-I-am!", blocks.get(6));
        assertBackReference(15, 16, blocks.get(7));
        assertLiteralBlock("I do not like\nt", blocks.get(8));
        assertBackReference(29, 14, blocks.get(9));
        assertLiteralBlock("\nDo you", blocks.get(10));
        assertBackReference(28, 5, blocks.get(11));
        assertLiteralBlock(" green eggs and ham?\n", blocks.get(12));
        assertBackReference(63, 14, blocks.get(13));
        assertLiteralBlock(" them,", blocks.get(14));
        assertBackReference(64, 9, blocks.get(15));
        assertLiteralBlock(".", blocks.get(16));
        assertBackReference(30, 15, blocks.get(17));
        assertBackReference(65, 18, blocks.get(18));
        assertLiteralBlock(".", blocks.get(19));
    }

    @Test
    public void blaExampleWithPrefill() throws IOException {
        final List<LZ77Compressor.Block> blocks = new ArrayList<>();
        LZ77Compressor c = new LZ77Compressor(newParameters(128), new LZ77Compressor.Callback() {
                @Override
                public void accept(LZ77Compressor.Block block) {
                    //System.err.println(block);
                    if (block instanceof LZ77Compressor.LiteralBlock) {
                        // replace with a real copy of data so tests
                        // can see the results as they've been when
                        // the callback has been called
                        LZ77Compressor.LiteralBlock b = (LZ77Compressor.LiteralBlock) block;
                        int len = b.getLength();
                        block = new LZ77Compressor.LiteralBlock(
                            Arrays.copyOfRange(b.getData(), b.getOffset(), b.getOffset() + len),
                            0, len);
                    }
                    blocks.add(block);
                }
            });
        c.prefill(Arrays.copyOfRange(BLA, 0, 6));
        c.compress(Arrays.copyOfRange(BLA, 6, BLA.length));
        c.finish();
        assertSize(3, blocks);
        assertBackReference(5, 18, blocks.get(0));
        assertLiteralBlock("!", blocks.get(1));
    }

    @Test
    public void blaExampleWithShortPrefill() throws IOException {
        final List<LZ77Compressor.Block> blocks = new ArrayList<>();
        LZ77Compressor c = new LZ77Compressor(newParameters(128), new LZ77Compressor.Callback() {
                @Override
                public void accept(LZ77Compressor.Block block) {
                    //System.err.println(block);
                    if (block instanceof LZ77Compressor.LiteralBlock) {
                        // replace with a real copy of data so tests
                        // can see the results as they've been when
                        // the callback has been called
                        LZ77Compressor.LiteralBlock b = (LZ77Compressor.LiteralBlock) block;
                        int len = b.getLength();
                        block = new LZ77Compressor.LiteralBlock(
                            Arrays.copyOfRange(b.getData(), b.getOffset(), b.getOffset() + len),
                            0, len);
                    }
                    blocks.add(block);
                }
            });
        c.prefill(Arrays.copyOfRange(BLA, 0, 2));
        c.compress(Arrays.copyOfRange(BLA, 2, BLA.length));
        c.finish();
        assertSize(4, blocks);
        assertLiteralBlock("ah b", blocks.get(0));
        assertBackReference(5, 18, blocks.get(1));
        assertLiteralBlock("!", blocks.get(2));
    }

    @Test
    public void blaExampleWithPrefillBiggerThanWindowSize() throws IOException {
        final List<LZ77Compressor.Block> blocks = new ArrayList<>();
        LZ77Compressor c = new LZ77Compressor(newParameters(4), new LZ77Compressor.Callback() {
                @Override
                public void accept(LZ77Compressor.Block block) {
                    //System.err.println(block);
                    if (block instanceof LZ77Compressor.LiteralBlock) {
                        // replace with a real copy of data so tests
                        // can see the results as they've been when
                        // the callback has been called
                        LZ77Compressor.LiteralBlock b = (LZ77Compressor.LiteralBlock) block;
                        int len = b.getLength();
                        block = new LZ77Compressor.LiteralBlock(
                            Arrays.copyOfRange(b.getData(), b.getOffset(), b.getOffset() + len),
                            0, len);
                    }
                    blocks.add(block);
                }
            });
        c.prefill(Arrays.copyOfRange(BLA, 0, 6));
        c.compress(Arrays.copyOfRange(BLA, 6, BLA.length));
        c.finish();
        assertSize(6, blocks);
        assertLiteralBlock("lah ", blocks.get(0));
        assertLiteralBlock("blah", blocks.get(1));
        assertLiteralBlock(" bla", blocks.get(2));
        assertLiteralBlock("h bl", blocks.get(3));
        assertLiteralBlock("ah!", blocks.get(4));
    }

    @Test(expected = IllegalStateException.class)
    public void cantPrefillTwice() {
        LZ77Compressor c = new LZ77Compressor(newParameters(128), new LZ77Compressor.Callback() {
                @Override
                public void accept(LZ77Compressor.Block block) {
                }
            });
        c.prefill(Arrays.copyOfRange(BLA, 0, 2));
        c.prefill(Arrays.copyOfRange(BLA, 2, 4));
    }

    @Test(expected = IllegalStateException.class)
    public void cantPrefillAfterCompress() throws IOException {
        LZ77Compressor c = new LZ77Compressor(newParameters(128), new LZ77Compressor.Callback() {
                @Override
                public void accept(LZ77Compressor.Block block) {
                }
            });
        c.compress(Arrays.copyOfRange(BLA, 0, 2));
        c.prefill(Arrays.copyOfRange(BLA, 2, 4));
    }

    private static final void assertSize(int expectedSize, List<LZ77Compressor.Block> blocks) {
        assertEquals(expectedSize, blocks.size());
        assertEquals(LZ77Compressor.EOD.class, blocks.get(expectedSize - 1).getClass());
    }

    private static final void assertLiteralBlock(String expectedContent, LZ77Compressor.Block block)
        throws IOException {
        assertLiteralBlock(expectedContent.getBytes("ASCII"), block);
    }

    private static final void assertLiteralBlock(byte[] expectedContent, LZ77Compressor.Block block) {
        assertEquals(LZ77Compressor.LiteralBlock.class, block.getClass());
        assertArrayEquals(expectedContent, ((LZ77Compressor.LiteralBlock) block).getData());
    }

    private static final void assertBackReference(int expectedOffset, int expectedLength, LZ77Compressor.Block block) {
        assertEquals(LZ77Compressor.BackReference.class, block.getClass());
        LZ77Compressor.BackReference b = (LZ77Compressor.BackReference) block;
        assertEquals(expectedOffset, b.getOffset());
        assertEquals(expectedLength, b.getLength());
    }

    private static final byte[][] stagger(byte[] data) {
        byte[][] r = new byte[data.length][1];
        for (int i = 0; i < data.length; i++) {
            r[i][0] = data[i];
        }
        return r;
    }

    private static Parameters newParameters(int windowSize) {
        return Parameters.builder(windowSize).build();
    }

    private static Parameters newParameters(int windowSize, int minBackReferenceLength, int maxBackReferenceLength,
        int maxOffset, int maxLiteralLength) {
        return Parameters.builder(windowSize)
            .withMinBackReferenceLength(minBackReferenceLength)
            .withMaxBackReferenceLength(maxBackReferenceLength)
            .withMaxOffset(maxOffset)
            .withMaxLiteralLength(maxLiteralLength)
            .tunedForCompressionRatio()
            .build();
    }
}
