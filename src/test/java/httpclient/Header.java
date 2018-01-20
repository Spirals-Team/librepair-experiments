package httpclient;

import java.util.List;

import static java.util.stream.Collectors.joining;

public class Header extends ValueType {
    public static final String CONTENT_LENGTH_KEY = "Content-Length";
    public static final String CONTENT_TYPE_KEY = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String X_TARGET = "X-Target";
    public static final String X_SOURCE = "X-Source";

    public final String key;
    public final String value;

    public Header(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Header(String key, List<String> values) {
        this.key = key;
        this.value = values.stream().collect(joining(","));
    }

    @Override
    public String toString() {
        return key + ": " + value;
    }
}
