package cn.edu.tsinghua.tsfile.encoding.encoder;

import cn.edu.tsinghua.tsfile.common.utils.BytesUtils;
import cn.edu.tsinghua.tsfile.file.metadata.enums.TSEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <p>
 * DeltaBinaryEncoder is a encoder for compressing data in type of integer and long.We adapt a
 * hypothesis that contiguous data points have similar values. Thus the difference value of two
 * adjacent points is smaller than those two point values. One integer in java takes 32-bits. If an
 * positive number is less than 2^m, the bits of this integer which index from m to 31 are all 0.
 * Given an array which length is n, if all values in input data array are all positive and less
 * than 2^m, we need actually m*n, but not 32*n bits to store the array.
 * </p>
 * <p>
 * DeltaBinaryEncoder calculates difference between two adjacent points and record the minimum of
 * those difference values firstly. Then it save two_diff value that difference minus minimum of
 * them, to make sure all two_diff values are positive. Then it statistics the longest bit length
 * {@code m} it takes for each two_diff value, which means the bit length that maximum two_diff
 * value takes. Only the low m bits are saved into result byte array for all two_diff values.
 * </p>
 *
 * @author kangrong
 */
abstract public class DeltaBinaryEncoder extends Encoder {
    protected static final int BLOCK_DEFAULT_SIZE = 128;
    private static final Logger LOG = LoggerFactory.getLogger(DeltaBinaryEncoder.class);
    protected ByteArrayOutputStream out;
    protected int blockSize;
    // input value is stored in deltaBlackBuffer temporarily
    protected byte[] encodingBlockBuffer;

    protected int writeIndex = -1;
    protected int writeWidth = 0;

    /**
     * @param size - the number how many numbers to be packed into a block.
     */
    public DeltaBinaryEncoder(int size) {
        super(TSEncoding.TS_2DIFF);
        blockSize = size;
    }

    protected abstract void writeHeader() throws IOException;

    protected abstract void writeValueToBytes(int i);

    protected abstract void calcTwoDiff(int i);

    protected abstract void reset();

    protected abstract int calculateBitWidthsForDeltaBlockBuffer();


    /**
     * writeTo all data into {@code encodingBlockBuffer}.
     */
    private void writeDataWithMinWidth() {
        for (int i = 0; i < writeIndex; i++) {
            writeValueToBytes(i);
        }
        int encodingLength = (int) Math.ceil((double) (writeIndex * writeWidth) / 8.0);
        out.write(encodingBlockBuffer, 0, encodingLength);
    }

    private void writeHeaderToBytes() throws IOException {
        out.write(BytesUtils.intToBytes(writeIndex));
        out.write(BytesUtils.intToBytes(writeWidth));
        writeHeader();
    }


    private void flushBlockBuffer(ByteArrayOutputStream out) throws IOException {
        if (writeIndex == -1)
            return;
        // since we store the min delta, the deltas will be converted to be the
        // difference to min delta and all positive
        this.out = out;
        for (int i = 0; i < writeIndex; i++) {
            calcTwoDiff(i);
        }
        writeWidth = calculateBitWidthsForDeltaBlockBuffer();
        // System.out.println("writeTo width:"+writeWidth);
        writeHeaderToBytes();
        writeDataWithMinWidth();

        reset();
        writeIndex = -1;
    }


    /**
     * calling this method to flush all values which haven't encoded to result byte array
     */
    @Override
    public void flush(ByteArrayOutputStream out) {
        try {
            flushBlockBuffer(out);
        } catch (IOException e) {
            LOG.error("flush data to stream failed!");
        }
    }

    static public class IntDeltaEncoder extends DeltaBinaryEncoder {
        private int[] deltaBlockBuffer;
        private int firstValue;
        private int previousValue;
        private int minDeltaBase;

        public IntDeltaEncoder() {
            this(BLOCK_DEFAULT_SIZE);
        }

        public IntDeltaEncoder(int size) {
            super(size);
            deltaBlockBuffer = new int[this.blockSize];
            encodingBlockBuffer = new byte[blockSize * 4];
            reset();
        }

        protected int calculateBitWidthsForDeltaBlockBuffer() {
            int width = 0;
            for (int i = 0; i < writeIndex; i++) {
                width = Math.max(width, getValueWidth(deltaBlockBuffer[i]));
            }
            return width;
        }

        private void calcDelta(Integer value) {
            Integer delta = value - previousValue;// calculate delta
            if (delta < minDeltaBase) {
                minDeltaBase = delta;
            }
            deltaBlockBuffer[writeIndex++] = delta;

        }

        /**
         * input a integer
         *
         * @param value value to encode
         * @param out the ByteArrayOutputStream which data encode into
         */
        public void encodeValue(int value, ByteArrayOutputStream out) {
            if (writeIndex == -1) {
                writeIndex++;
                firstValue = value;
                previousValue = firstValue;
                return;
            }
            calcDelta(value);
            previousValue = value;
            if (writeIndex == blockSize) {
                flush(out);
            }
        }

        @Override
        protected void reset() {
            firstValue = 0;
            previousValue = 0;
            minDeltaBase = Integer.MAX_VALUE;
            for (int i = 0; i < blockSize; i++) {
                encodingBlockBuffer[i] = 0;
                deltaBlockBuffer[i] = 0;
            }
        }


        private int getValueWidth(int v) {
            return 32 - Integer.numberOfLeadingZeros(v);
        }

        @Override
        protected void writeValueToBytes(int i) {
            BytesUtils.intToBytes(deltaBlockBuffer[i], encodingBlockBuffer, writeWidth * i, writeWidth);
        }

        @Override
        protected void calcTwoDiff(int i) {
            deltaBlockBuffer[i] = deltaBlockBuffer[i] - minDeltaBase;
        }

        @Override
        protected void writeHeader() throws IOException {
            out.write(BytesUtils.intToBytes(minDeltaBase));
            out.write(BytesUtils.intToBytes(firstValue));
        }

        @Override
        public void encode(int value, ByteArrayOutputStream out) {
            encodeValue(value, out);
        }

        @Override
        public int getOneItemMaxSize() {
            return 4;
        }

        @Override
        public long getMaxByteSize() {
            // The meaning of 24 is: index(4)+width(4)+minDeltaBase(4)+firstValue(4)
            return 24 + writeIndex * 4;
        }
    }

    static public class LongDeltaEncoder extends DeltaBinaryEncoder {
        private long[] deltaBlockBuffer;
        private long firstValue;
        private long previousValue;
        private long minDeltaBase;

        public LongDeltaEncoder() {
            this(BLOCK_DEFAULT_SIZE);
        }

        public LongDeltaEncoder(int size) {
            super(size);
            deltaBlockBuffer = new long[this.blockSize];
            encodingBlockBuffer = new byte[blockSize * 8];
            reset();
        }

        private void calcDelta(Long value) {
            Long delta = value - previousValue;// calculate delta
            if (delta < minDeltaBase) {
                minDeltaBase = delta;
            }
            deltaBlockBuffer[writeIndex++] = delta;
        }

        @Override
        protected void reset() {
            firstValue = 0l;
            previousValue = 0l;
            minDeltaBase = Long.MAX_VALUE;
            for (int i = 0; i < blockSize; i++) {
                encodingBlockBuffer[i] = 0;
                deltaBlockBuffer[i] = 0l;
            }
        }

        private int getValueWidth(Long v) {
            return 64 - Long.numberOfLeadingZeros(v);
        }

        @Override
        protected void writeValueToBytes(int i) {
            BytesUtils.longToBytes(deltaBlockBuffer[i], encodingBlockBuffer, writeWidth * i, writeWidth);
        }

        @Override
        protected void calcTwoDiff(int i) {
            deltaBlockBuffer[i] = deltaBlockBuffer[i] - minDeltaBase;
        }

        @Override
        protected void writeHeader() throws IOException {
            out.write(BytesUtils.longToBytes(minDeltaBase));
            out.write(BytesUtils.longToBytes(firstValue));
        }

        @Override
        public void encode(long value, ByteArrayOutputStream out) {
            encodeValue(value, out);
        }

        @Override
        public int getOneItemMaxSize() {
            return 8;
        }

        @Override
        public long getMaxByteSize() {
            // The meaning of 24 is: index(4)+width(4)+minDeltaBase(8)+firstValue(8)
            return 24 + writeIndex * 8;
        }

        /**
         * input a integer or long value.
         *
         * @param value value to encode
         * @param out   - the ByteArrayOutputStream which data encode into
         */
        public void encodeValue(long value, ByteArrayOutputStream out) {
            if (writeIndex == -1) {
                writeIndex++;
                firstValue = value;
                previousValue = firstValue;
                return;
            }
            calcDelta(value);
            previousValue = value;
            if (writeIndex == blockSize) {
                flush(out);
            }
        }

        protected int calculateBitWidthsForDeltaBlockBuffer() {
            int width = 0;
            for (int i = 0; i < writeIndex; i++) {
                width = Math.max(width, getValueWidth(deltaBlockBuffer[i]));
            }
            return width;
        }
    }
}
