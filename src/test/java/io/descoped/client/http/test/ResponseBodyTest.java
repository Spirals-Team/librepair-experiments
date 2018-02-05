package io.descoped.client.http.test;

import io.descoped.client.http.ResponseBodyHandler;
import io.descoped.client.http.internal.HeadersImpl;
import io.descoped.client.http.internal.ResponseProcessors;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;

public class ResponseBodyTest {

    private static Logger log = LoggerFactory.getLogger(ResponseBodyTest.class);

    @Test
    public void test() {
        byte[] a1 = "Foo".getBytes();
        byte[] a2 = "Bar".getBytes();

        List<ByteBuffer> byteBuffers = new ArrayList<>();
        byteBuffers.add(ByteBuffer.wrap(a1));
        byteBuffers.add(ByteBuffer.wrap(a2));
        log.trace("size: {}", ResponseProcessors.remaining(byteBuffers));
    }

    @Test
    public void testBodyProcessorAsBytes() {
        byte[] a1 = "Foo".getBytes();
        byte[] a2 = "Bar".getBytes();

        ResponseProcessors.ByteArrayProcessor<byte[]> byteArrayProcessor = new ResponseProcessors.ByteArrayProcessor(bytes -> bytes);
        byteArrayProcessor.open();
        byteArrayProcessor.write(a1);
        byteArrayProcessor.write(a2);
        byteArrayProcessor.complete();
        byte[] s = byteArrayProcessor.getBody().get();

        log.trace("s: {}", s);
    }

    @Test
    public void testBodyProcessorAsString() {
        byte[] a1 = "Foo".getBytes();
        byte[] a2 = "Bar".getBytes();

        ResponseProcessors.ByteArrayProcessor<String> byteArrayProcessor = new ResponseProcessors.ByteArrayProcessor(bytes -> new String((byte[]) bytes, StandardCharsets.UTF_8));
        byteArrayProcessor.open();
        byteArrayProcessor.write(a1);
        byteArrayProcessor.write(a2);
        byteArrayProcessor.complete();
        String s = byteArrayProcessor.getBody().get();

        log.trace("s: {}", s);

        ResponseBodyHandler.asString(null);
    }

    @Test
    public void testHandler() {
        byte[] a1 = "Foo".getBytes();
        byte[] a2 = "Bar".getBytes();

        ResponseProcessors.AbstractProcessor<String> processor = (ResponseProcessors.AbstractProcessor<String>) ResponseBodyHandler.asString().apply(HTTP_OK, new HeadersImpl());
        processor.open();
        processor.write(a1);
        processor.write(a2);
        processor.complete();
        log.trace("handler s: {}", processor.getBody());
    }

    @Test
    public void testPathProcessor() throws Exception {
        Path tempFile = Files.createTempFile("tempfile", ".tmp");
        ResponseProcessors.PathProcessor processor = new ResponseProcessors.PathProcessor(tempFile, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        processor.open();
        processor.write("foo".getBytes());
        processor.complete();
        log.trace("wrote: {}", processor.getBody().toString());
    }

}
