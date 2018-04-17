package com.aliyuncs.fc.utils;

import java.net.URI;

import static com.google.common.base.Strings.isNullOrEmpty;

public class UriBuilder {
    private String scheme;
    private String host;
    private int port;
    private String path;
    private StringBuilder query;
    private String fragment;

    private UriBuilder(URI uri) {
        this(uri.getScheme(), uri.getHost(), uri.getPort(), uri.getPath(), uri.getQuery(), uri.getFragment());
    }

    private UriBuilder(String scheme, String host, int port, String path, String query, String fragment) {
        this.scheme = scheme;
        this.host = host;
        this.port = port;
        this.path = path;
        this.query = query == null ? new StringBuilder() : new StringBuilder(query);
        this.fragment = fragment;
    }

    public static UriBuilder fromUri(URI uri) {
        return new UriBuilder(uri);
    }

    public UriBuilder queryParam(String name, String value) {
        if (query.length() != 0) {
            query.append("&");
        }

        query.append(name).append("=").append(value);

        return this;
    }

    public String build() {
        StringBuilder builder = new StringBuilder();

        if ( ! isNullOrEmpty(scheme) ) {
            builder.append(scheme).append("://");
        }

        if ( ! isNullOrEmpty(host) ) {
            builder.append(host);
        }

        if ( port != -1 ) {
            builder.append(":").append(port);
        }

        if ( ! isNullOrEmpty(path) ) {
            builder.append(path);
        }

        if ( query.length() != 0 ) {
            builder.append("?").append(query.toString());
        }

        if ( ! isNullOrEmpty(fragment) ) {
            builder.append("#").append(fragment);
        }

        return builder.toString();
    }
}