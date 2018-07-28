package cn.edu.tsinghua.tsfile.compress;

import cn.edu.tsinghua.tsfile.common.exception.CompressionTypeNotSupportedException;
import cn.edu.tsinghua.tsfile.file.metadata.enums.CompressionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xerial.snappy.Snappy;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * uncompress data according to type in metadata
 */
public abstract class UnCompressor {
    public static UnCompressor getUnCompressor(CompressionType name) {
        if (name == null) {
            throw new CompressionTypeNotSupportedException("NULL");
        }
        switch (name) {
            case UNCOMPRESSED:
                return new NoUnCompressor();
            case SNAPPY:
                return new SnappyUnCompressor();
            default:
                throw new CompressionTypeNotSupportedException(name.toString());
        }
    }

    public abstract byte[] uncompress(byte[] byteArray);

    /**
     * if the data is large, using this function is better.
     * @param compressed MUST be DirectByteBuffer
     * @param uncompressed MUST be DirectByteBuffer
     * @return
     */
    public abstract int uncompress(ByteBuffer compressed, ByteBuffer uncompressed);

//    /**
//     * if the data is large, using this function is better.
//     * @param compressed MUST be DirectByteBuffer
//     * @return
//     */
//    public abstract ByteBuffer uncompress(ByteBuffer compressed);

    public abstract CompressionType getCodecName();

    static public class NoUnCompressor extends UnCompressor {

        @Override
        public byte[] uncompress(byte[] byteArray) {
            return byteArray;
        }

        @Override
        public int uncompress(ByteBuffer compressed, ByteBuffer uncompressed) {
            int pos=uncompressed.position();
            uncompressed.put(compressed);
            return uncompressed.position()-pos;
        }

//        @Override
//        public ByteBuffer uncompress(ByteBuffer compressed) {
//            ByteBuffer uncompressed=ByteBuffer.allocate(compressed.remaining());
//            uncompressed.put(compressed);
//            return uncompressed;
//        }

        @Override
        public CompressionType getCodecName() {
            return CompressionType.UNCOMPRESSED;
        }
    }

    static public class SnappyUnCompressor extends UnCompressor {
        private static final Logger LOGGER = LoggerFactory.getLogger(SnappyUnCompressor.class);

        @Override
        public byte[] uncompress(byte[] bytes) {
            if (bytes == null) {
                return null;
            }

            try {
                return Snappy.uncompress(bytes);
            } catch (IOException e) {
                LOGGER.error(
                        "tsfile-compression SnappyUnCompressor: errors occurs when uncompress input byte, bytes is {}",
                        bytes, e);
            }
            return null;
        }


        @Override
        public int uncompress(ByteBuffer compressed, ByteBuffer uncompressed) {
            if (compressed == null || !compressed.hasRemaining()) {
                return 0;
            }

            try {
                return Snappy.uncompress(compressed, uncompressed);
            } catch (IOException e) {
                LOGGER.error(
                        "tsfile-compression SnappyUnCompressor: errors occurs when uncompress input byte, bytes is {}",
                        compressed.array(), e);
            }
            return 0;
        }
//        @Override
//        public ByteBuffer uncompress(ByteBuffer compressed) {
//            if (compressed == null || !compressed.hasRemaining()) {
//                return null;
//            }
//
//            try {
//                ByteBuffer uncompressed= ByteBuffer.allocateDirect(Snappy.uncompressedLength(compressed));
//                Snappy.uncompress(compressed, uncompressed);
//                return uncompressed;
//            } catch (IOException e) {
//                LOGGER.error(
//                        "tsfile-compression SnappyUnCompressor: errors occurs when uncompress input byte, bytes is {}",
//                        compressed.array(), e);
//            }
//            return null;
//        }

        @Override
        public CompressionType getCodecName() {
            return CompressionType.SNAPPY;
        }
    }
}
