package io.descoped.client.http.internal.apiBuilder;

import io.descoped.client.api.builder.intf.OutcomeHandler;
import io.descoped.client.exception.APIClientException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 24/11/2017
 */
public class OutcomeHandlerImpl implements OutcomeHandler {

    private byte[] bytes;
    private int code;
    private int contentLength;
    private Map<String, List<String>> headers;

    public OutcomeHandlerImpl(int code, byte[] bytes, int contentLength, Map<String, List<String>> headers) {
        this.code = code;
        this.bytes = bytes;
        this.contentLength = contentLength;
        this.headers = headers;
    }

    @Override
    public byte[] getReceivedBytes() {
        return bytes;
    }

    @Override
    public void setReceivedBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String getContent() {
        try {
            return new String(bytes, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new APIClientException(e);
        }
    }

    @Override
    public int getStatusCode() {
        return code;
    }

    @Override
    public void setStatusCode(int code) {
        this.code = code;
    }

    @Override
    public int getContentLength() {
        return contentLength;
    }

    @Override
    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    @Override
    public void setResponseHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    @Override
    public boolean ok() {
        return getStatusCode() == HTTP_OK;
    }

    @Override
    public String toString() {
        return "HttpBinOutcome{" +
                "content=" + getContent() +
                ", code=" + code +
                ", contentLength=" + contentLength +
                ", headers=" + headers +
                '}';
    }
}
