package io.descoped.client.http.internal;

import io.descoped.client.http.RequestBodyProcessor;
import sun.net.NetProperties;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestProcessors {

    /**
     * Allocated buffer size. Must never be higher than 16K. But can be lower
     * if smaller allocation units preferred. HTTP/2 mandates that all
     * implementations support frame payloads of at least 16K.
     */
    public static final int DEFAULT_BUFSIZE = 16 * 1024;

    public static final int BUFSIZE = getIntegerNetProperty(
            "jdk.httpclient.bufsize", DEFAULT_BUFSIZE
    );


    public static int getIntegerNetProperty(String name, int defaultValue) {
        return AccessController.doPrivileged((PrivilegedAction<Integer>) () ->
                NetProperties.getInteger(name, defaultValue));
    }

    static class AbstractProcessor {
    }

    public static class EmptyProcessor extends AbstractProcessor implements RequestBodyProcessor {
        @Override
        public byte[] body() {
            return new byte[0];
        }

        @Override
        public long contentLength() {
            return 0;
        }
    }

    public static class ByteArrayProcessor extends AbstractProcessor implements RequestBodyProcessor {
        private final int length;
        private final byte[] content;
        private final int offset;

        public ByteArrayProcessor(byte[] content) {
            this(content, 0, content.length);
        }

        ByteArrayProcessor(byte[] content, int offset, int length) {
            this.content = content;
            this.offset = offset;
            this.length = length;
        }

        List<ByteBuffer> copy(byte[] content, int offset, int length) {
            List<ByteBuffer> bufs = new ArrayList<>();
            while (length > 0) {
                ByteBuffer b = ByteBuffer.allocate(Math.min(BUFSIZE, length));
                int max = b.capacity();
                int tocopy = Math.min(max, length);
                b.put(content, offset, tocopy);
                offset += tocopy;
                length -= tocopy;
                b.flip();
                bufs.add(b);
            }
            return bufs;
        }

        @Override
        public byte[] body() {
            return content;
        }

        @Override
        public long contentLength() {
            return length;
        }
    }

    public static class StringProcessor extends ByteArrayProcessor {
        public StringProcessor(String body, Charset charset) {
            super(body.getBytes(charset));
        }
    }

    public static class InputStreamProcessor extends AbstractProcessor implements RequestBodyProcessor {
        private final BufferedInputStream inputStream;
        private byte[] result;

        public InputStreamProcessor(InputStream inputStream) {
            this.inputStream = new BufferedInputStream(inputStream);
            readBuffer();
        }

        private void readBuffer() {
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                while (true) {
                    int r = inputStream.read(buffer);
                    if (r == -1) break;
                    out.write(buffer, 0, r);
                }
                result = out.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public byte[] body() {
            return result;
        }

        @Override
        public long contentLength() {
            return Optional.ofNullable(result).orElse(new byte[0]).length;
        }
    }

    public static class FileProcessor extends InputStreamProcessor implements RequestBodyProcessor {

        private final File file;

        public FileProcessor(Path name) {
            super(create(name));
            file = name.toFile();
        }

        static FileInputStream create(Path name) {
            try {
                return new FileInputStream(name.toFile());
            } catch (FileNotFoundException e) {
                throw new UncheckedIOException(e);
            }
        }
        @Override
        public long contentLength() {
            return file.length();
        }
    }


}
