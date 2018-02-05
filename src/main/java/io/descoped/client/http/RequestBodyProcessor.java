package io.descoped.client.http;

import io.descoped.client.http.internal.RequestProcessors;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public interface RequestBodyProcessor {

    byte[] body();

    long contentLength();

    static RequestBodyProcessor noBody() {
        return new RequestProcessors.EmptyProcessor();
    }

    static RequestBodyProcessor fromString(String body) {
        return fromString(body, StandardCharsets.UTF_8);
    }

    static RequestBodyProcessor fromString(String body, Charset charset) {
        return new RequestProcessors.StringProcessor(body, charset);
    }

    static RequestBodyProcessor fromByteArray(byte[] bytes) {
        return new RequestProcessors.ByteArrayProcessor(bytes);
    }

    static RequestBodyProcessor fromInputStream(InputStream inputStream) {
        return new RequestProcessors.InputStreamProcessor(inputStream);
    }

    static RequestBodyProcessor fromFile(Path name) {
        return new RequestProcessors.FileProcessor(name);
    }

}
