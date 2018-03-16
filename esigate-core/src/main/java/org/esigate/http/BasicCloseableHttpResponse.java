package org.esigate.http;

import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;

public final class BasicCloseableHttpResponse implements CloseableHttpResponse {
    private final HttpResponse httpResponse;

    public static CloseableHttpResponse adapt(HttpResponse response) {
        if (response instanceof CloseableHttpResponse) {
            return (CloseableHttpResponse) response;
        } else {
            return new BasicCloseableHttpResponse(response);
        }
    }

    private BasicCloseableHttpResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    @Override
    public void close() {
        // Nothing to do
    }

    @Override
    public StatusLine getStatusLine() {
        return httpResponse.getStatusLine();
    }

    @Override
    public void setStatusLine(StatusLine statusline) {
        httpResponse.setStatusLine(statusline);
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return httpResponse.getProtocolVersion();
    }

    @Override
    public void setStatusLine(ProtocolVersion ver, int code) {
        httpResponse.setStatusLine(ver, code);
    }

    @Override
    public boolean containsHeader(String name) {
        return httpResponse.containsHeader(name);
    }

    @Override
    public void setStatusLine(ProtocolVersion ver, int code, String reason) {
        httpResponse.setStatusLine(ver, code, reason);
    }

    @Override
    public Header[] getHeaders(String name) {
        return httpResponse.getHeaders(name);
    }

    @Override
    public void setStatusCode(int code) {
        httpResponse.setStatusCode(code);
    }

    @Override
    public Header getFirstHeader(String name) {
        return httpResponse.getFirstHeader(name);
    }

    @Override
    public Header getLastHeader(String name) {
        return httpResponse.getLastHeader(name);
    }

    @Override
    public void setReasonPhrase(String reason) {
        httpResponse.setReasonPhrase(reason);
    }

    @Override
    public Header[] getAllHeaders() {
        return httpResponse.getAllHeaders();
    }

    @Override
    public HttpEntity getEntity() {
        return httpResponse.getEntity();
    }

    @Override
    public void addHeader(Header header) {
        httpResponse.addHeader(header);
    }

    @Override
    public void addHeader(String name, String value) {
        httpResponse.addHeader(name, value);
    }

    @Override
    public void setEntity(HttpEntity entity) {
        httpResponse.setEntity(entity);
    }

    @Override
    public void setHeader(Header header) {
        httpResponse.setHeader(header);
    }

    @Override
    public void setHeader(String name, String value) {
        httpResponse.setHeader(name, value);
    }

    @Override
    public Locale getLocale() {
        return httpResponse.getLocale();
    }

    @Override
    public void setHeaders(Header[] headers) {
        httpResponse.setHeaders(headers);
    }

    @Override
    public void removeHeader(Header header) {
        httpResponse.removeHeader(header);
    }

    @Override
    public void setLocale(Locale loc) {
        httpResponse.setLocale(loc);
    }

    @Override
    public void removeHeaders(String name) {
        httpResponse.removeHeaders(name);
    }

    @Override
    public HeaderIterator headerIterator() {
        return httpResponse.headerIterator();
    }

    @Override
    public HeaderIterator headerIterator(String name) {
        return httpResponse.headerIterator(name);
    }

    @SuppressWarnings("deprecation")
    @Override
    public org.apache.http.params.HttpParams getParams() {
        return httpResponse.getParams();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setParams(org.apache.http.params.HttpParams params) {
        httpResponse.setParams(params);
    }

    @Override
    public String toString() {
        return httpResponse.toString();
    }

}
