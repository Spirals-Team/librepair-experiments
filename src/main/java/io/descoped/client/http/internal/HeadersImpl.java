package io.descoped.client.http.internal;

import io.descoped.client.http.Headers;

import java.util.*;

public class HeadersImpl implements Headers {

    private final TreeMap<String, List<String>> headers;

    public HeadersImpl() {
        headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public HeadersImpl(Map<String, List<String>> mapHeaders) {
        this();
        for (Map.Entry<String, List<String>> e : mapHeaders.entrySet()) {
            if (e.getKey() != null) {
                headers.put(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public Optional<String> firstValue(String name) {
        List<String> l = headers.get(name);
        return Optional.ofNullable(l == null ? null : l.get(0));
    }

    @Override
    public OptionalLong firstValueLong(String name) {
        List<String> l = headers.get(name);
        if (l == null) {
            return OptionalLong.empty();
        } else {
            String v = l.get(0);
            return OptionalLong.of(Long.parseLong(v));
        }
    }

    @Override
    public List<String> allValues(String name) {
        return headers.get(name);
    }

    @Override
    public Map<String, List<String>> map() {
        return Collections.unmodifiableMap(headers);
    }

    public void addHeader(String name, String value) {
        headers.computeIfAbsent(name, k -> new ArrayList<>(1))
                .add(value);
    }

    public void setHeader(String name, String value) {
        List<String> values = new ArrayList<>(1); // most headers has one value
        values.add(value);
        headers.put(name, values);
    }

    public static Map<String,String> asFlatMap(Map<String, List<String>> headers) {
        Map<String,String> headerMap = new HashMap<>();
        for(Map.Entry<String,List<String>> entry : headers.entrySet()) {
            String value = (entry.getValue().size() == 1 ? entry.getValue().get(0) : Arrays.asList(entry.getValue()).toString());
            headerMap.put(entry.getKey(), value);
        }
        return headerMap;
    }
}
