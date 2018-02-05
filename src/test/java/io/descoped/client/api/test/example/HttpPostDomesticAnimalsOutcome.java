package io.descoped.client.api.test.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.descoped.client.api.builder.intf.OutcomeHandler;
import io.descoped.client.exception.APIClientException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 23/11/2017
 */
public class HttpPostDomesticAnimalsOutcome implements OutcomeHandler {

    private byte[] bytes;
    private int code;
    private int contentLength;
    private Map<String, List<String>> headers;

    public HttpPostDomesticAnimalsOutcome() {
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

    public static String prettyPrint(String payload) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(payload);
        String json = gson.toJson(je);
        return json;
    }
    @Override
    public String toString() {
        return "HttpPostDomesticAnimalsOutcome{" +
                "content=" + getContent() +
                ", code=" + code +
                ", contentLength=" + contentLength +
                ", headers=" + headers +
                '}';
    }

}
