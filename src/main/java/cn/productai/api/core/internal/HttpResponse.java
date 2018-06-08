package cn.productai.api.core.internal;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Created by Thinkpad on 2017/7/3.
 */
public class HttpResponse {
    private String responseString;
    private byte[] responseBytes;
    private Integer statusCode;
    private Map<String, List<String>> headers;

    public String getResponseString() {
        if (this.responseBytes != null && this.responseBytes.length > 0)
            return new String(this.responseBytes, Charset.forName("UTF-8"));

        return null;
    }

    public byte[] getResponseBytes() {
        return responseBytes;
    }

    public void setResponseBytes(byte[] responseBytes) {
        this.responseBytes = responseBytes;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }
}
