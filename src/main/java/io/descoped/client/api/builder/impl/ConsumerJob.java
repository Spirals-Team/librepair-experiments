package io.descoped.client.api.builder.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ConsumerJob {
    private String url;
    private List<String> params;

    public ConsumerJob(String url, String... params) {
        this.url = url;
        this.params = Arrays.asList(params);
    }

    public String getUrl() {
        return url;
    }

    public Stream<String> getParams() {
        return Stream.of(params.toArray(new String[params.size()]));
    }
}
