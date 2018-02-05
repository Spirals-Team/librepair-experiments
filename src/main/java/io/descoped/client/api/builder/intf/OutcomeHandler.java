package io.descoped.client.api.builder.intf;

import io.descoped.client.http.internal.apiBuilder.OutcomeHandlerImpl;

import java.util.List;
import java.util.Map;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 23/11/2017
 */
public interface OutcomeHandler {

    static OutcomeHandler create(int code, byte[] bytes, int contentLength, Map<String, List<String>> headers) {
        return new OutcomeHandlerImpl(code, bytes, contentLength, headers);
    }

    byte[] getReceivedBytes();

    void setReceivedBytes(byte[] bytes);

    String getContent();

    int getStatusCode();

    void setStatusCode(int code);

    int getContentLength();

    void setContentLength(int contentLength);

    Map<String, List<String>> getHeaders();

    void setResponseHeaders(Map<String, List<String>> headers);

    boolean ok();
}
