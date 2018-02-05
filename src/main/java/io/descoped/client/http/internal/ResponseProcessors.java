package io.descoped.client.http.internal;

import io.descoped.client.http.Headers;
import io.descoped.client.http.ResponseBodyProcessor;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ResponseProcessors {

    public static abstract class AbstractProcessor<T> implements ResponseBodyProcessor<T> {
        public abstract void open();

        public abstract void write(byte[] bytes);

        public abstract void complete();
    }

    public static int remaining(List<ByteBuffer> bufs) {
        int remain = 0;
        for (ByteBuffer buf : bufs) {
            remain += buf.remaining();
        }
        return remain;
    }

    public static byte[] join(List<ByteBuffer> bytes) {
        int size = remaining(bytes);
        byte[] res = new byte[size];
        int from = 0;
        for (ByteBuffer b : bytes) {
            int l = b.remaining();
            b.get(res, from, l);
            from += l;
        }
        return res;
    }

    public static void close(Closeable... closeables) {
        for (Closeable c : closeables) {
            try {
                c.close();
            } catch (IOException ignored) {
            }
        }
    }

    public static Charset charsetFrom(Optional<Headers> headers) {
        String encoding = headers.orElse(new HeadersImpl()).firstValue("Content-encoding")
                .orElse("UTF_8");
        try {
            return Charset.forName(encoding);
        } catch (IllegalArgumentException e) {
            return StandardCharsets.UTF_8;
        }
    }


    public static class PathProcessor extends AbstractProcessor<Path> {
        private final Path file;
        private FileChannel out;
        private final OpenOption[] options;

        public PathProcessor(Path file, OpenOption... options) {
            this.file = file;
            this.options = options;
        }

        @Override
        public void open() {
            try {
                out = FileChannel.open(file, options);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void write(byte[] bytes) {
            try {
                out.write(ByteBuffer.wrap(bytes));
            } catch (IOException ex) {
                close(out);
            }
        }

        @Override
        public void complete() {
            close(out);
        }

        @Override
        public Optional<Path> getBody() {
            return Optional.of(file);
        }
    }


    public static class ByteArrayProcessor<T> extends AbstractProcessor<T> {
        private Function<byte[], T> finisher;
        private List<ByteBuffer> received;
        private T result;

        public ByteArrayProcessor(Function<byte[], T> finisher) {
            this.finisher = finisher;
        }

        @Override
        public void open() {
            received = new ArrayList<>();
        }

        @Override
        public void write(byte[] bytes) {
            received.add(ByteBuffer.wrap(bytes));
        }


        @Override
        public void complete() {
            result = finisher.apply(join(received));
            received.clear();
        }

        @Override
        public Optional<T> getBody() {
            return Optional.ofNullable(result);
        }

    }

}
