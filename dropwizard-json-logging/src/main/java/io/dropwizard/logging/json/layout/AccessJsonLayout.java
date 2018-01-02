package io.dropwizard.logging.json.layout;

import ch.qos.logback.access.spi.IAccessEvent;
import com.google.common.net.HttpHeaders;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Builds JSON messages from access log events as {@link IAccessEvent}.
 */
public class AccessJsonLayout extends AbstractJsonLayout<IAccessEvent> {

    private boolean includeContentLength = true;
    private boolean includeLocalPort = false;
    private boolean includeMethod = true;
    private boolean includeProtocol = true;
    private boolean includeRemoteAddr = true;
    private boolean includeRemoteUser = true;
    private boolean includeRequestContent = false;
    private boolean includeRequestHeaders = false;
    private boolean includeRequestParameters = true;
    private boolean includeRequestTime = true;
    private boolean includeRequestURI = true;
    private boolean includeRequestURL = false;
    private boolean includeRemoteHost = false;
    private boolean includeResponseContent = false;
    private boolean includeResponseHeaders = false;
    private boolean includeServerName = false;
    private boolean includeStatusCode = true;
    private boolean includeTimestamp = true;
    private boolean includeUserAgent = true;

    @Nullable
    private String jsonProtocolVersion;

    private final TimestampFormatter timestampFormatter;
    private final Map<String, Object> additionalFields;
    private final Map<String, String> customFieldNames;

    public AccessJsonLayout(JsonFormatter jsonFormatter, TimestampFormatter timestampFormatter,
                            Map<String, String> customFieldNames, Map<String, Object> additionalFields) {
        super(jsonFormatter);
        this.timestampFormatter = timestampFormatter;
        this.additionalFields = additionalFields;
        this.customFieldNames = customFieldNames;
    }

    @Override
    protected Map<String, Object> toJsonMap(IAccessEvent event) {
        return new MapBuilder(timestampFormatter, customFieldNames, additionalFields, 20)
            .add("port", includeLocalPort, event.getLocalPort())
            .add("contentLength", includeContentLength, event.getContentLength())
            .addTimestamp("timestamp", includeTimestamp, event.getTimeStamp())
            .add("method", includeMethod, event.getMethod())
            .add("protocol", includeProtocol, event.getProtocol())
            .add("requestContent", includeRequestContent, event.getRequestContent())
            .add("remoteAddress", includeRemoteAddr, event.getRemoteAddr())
            .add("remoteUser", includeRemoteUser, event.getRemoteUser())
            .add("headers", includeRequestHeaders, event.getRequestHeaderMap())
            .add("params", includeRequestParameters, event.getRequestParameterMap())
            .add("requestTime", includeRequestTime, event.getElapsedTime())
            .add("uri", includeRequestURI, event.getRequestURI())
            .add("url", includeRequestURL, event.getRequestURL())
            .add("remoteHost", includeRemoteHost, event.getRemoteHost())
            .add("responseContent", includeResponseContent, event.getResponseContent())
            .add("responseHeaders", includeResponseHeaders, event.getResponseHeaderMap())
            .add("serverName", includeServerName, event.getServerName())
            .add("status", includeStatusCode, event.getStatusCode())
            .add("userAgent", includeUserAgent, event.getRequestHeader(HttpHeaders.USER_AGENT))
            .add("version", jsonProtocolVersion != null, jsonProtocolVersion)
            .build();
    }

    public boolean isIncludeContentLength() {
        return includeContentLength;
    }

    public void setIncludeContentLength(boolean includeContentLength) {
        this.includeContentLength = includeContentLength;
    }

    public boolean isIncludeLocalPort() {
        return includeLocalPort;
    }

    public void setIncludeLocalPort(boolean includeLocalPort) {
        this.includeLocalPort = includeLocalPort;
    }

    public boolean isIncludeMethod() {
        return includeMethod;
    }

    public void setIncludeMethod(boolean includeMethod) {
        this.includeMethod = includeMethod;
    }

    public boolean isIncludeProtocol() {
        return includeProtocol;
    }

    public void setIncludeProtocol(boolean includeProtocol) {
        this.includeProtocol = includeProtocol;
    }

    public boolean isIncludeRemoteAddr() {
        return includeRemoteAddr;
    }

    public void setIncludeRemoteAddr(boolean includeRemoteAddr) {
        this.includeRemoteAddr = includeRemoteAddr;
    }

    public boolean isIncludeRemoteUser() {
        return includeRemoteUser;
    }

    public void setIncludeRemoteUser(boolean includeRemoteUser) {
        this.includeRemoteUser = includeRemoteUser;
    }

    public boolean isIncludeRequestContent() {
        return includeRequestContent;
    }

    public void setIncludeRequestContent(boolean includeRequestContent) {
        this.includeRequestContent = includeRequestContent;
    }

    public boolean isIncludeRequestHeaders() {
        return includeRequestHeaders;
    }

    public void setIncludeRequestHeaders(boolean includeRequestHeaders) {
        this.includeRequestHeaders = includeRequestHeaders;
    }

    public boolean isIncludeRequestParameters() {
        return includeRequestParameters;
    }

    public void setIncludeRequestParameters(boolean includeRequestParameters) {
        this.includeRequestParameters = includeRequestParameters;
    }

    public boolean isIncludeRequestTime() {
        return includeRequestTime;
    }

    public void setIncludeRequestTime(boolean includeRequestTime) {
        this.includeRequestTime = includeRequestTime;
    }

    public boolean isIncludeRequestURI() {
        return includeRequestURI;
    }

    public void setIncludeRequestURI(boolean includeRequestURI) {
        this.includeRequestURI = includeRequestURI;
    }

    public boolean isIncludeRequestURL() {
        return includeRequestURL;
    }

    public void setIncludeRequestURL(boolean includeRequestURL) {
        this.includeRequestURL = includeRequestURL;
    }

    public boolean isIncludeRemoteHost() {
        return includeRemoteHost;
    }

    public void setIncludeRemoteHost(boolean includeRemoteHost) {
        this.includeRemoteHost = includeRemoteHost;
    }

    public boolean isIncludeResponseContent() {
        return includeResponseContent;
    }

    public void setIncludeResponseContent(boolean includeResponseContent) {
        this.includeResponseContent = includeResponseContent;
    }

    public boolean isIncludeResponseHeaders() {
        return includeResponseHeaders;
    }

    public void setIncludeResponseHeaders(boolean includeResponseHeaders) {
        this.includeResponseHeaders = includeResponseHeaders;
    }

    public boolean isIncludeServerName() {
        return includeServerName;
    }

    public void setIncludeServerName(boolean includeServerName) {
        this.includeServerName = includeServerName;
    }

    public boolean isIncludeStatusCode() {
        return includeStatusCode;
    }

    public void setIncludeStatusCode(boolean includeStatusCode) {
        this.includeStatusCode = includeStatusCode;
    }

    public boolean isIncludeUserAgent() {
        return includeUserAgent;
    }

    public void setIncludeUserAgent(boolean includeUserAgent) {
        this.includeUserAgent = includeUserAgent;
    }

    public boolean isIncludeTimestamp() {
        return includeTimestamp;
    }

    public void setIncludeTimestamp(boolean includeTimestamp) {
        this.includeTimestamp = includeTimestamp;
    }

    @Nullable
    public String getJsonProtocolVersion() {
        return jsonProtocolVersion;
    }

    public void setJsonProtocolVersion(@Nullable String jsonProtocolVersion) {
        this.jsonProtocolVersion = jsonProtocolVersion;
    }
}
