package io.dropwizard.logging.json.layout;

import ch.qos.logback.classic.pattern.ThrowableHandlingConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Builds JSON messages from logging events of the type {@link ILoggingEvent}.
 */
public class EventJsonLayout extends AbstractJsonLayout<ILoggingEvent> {

    private boolean includeLevel = true;
    private boolean includeThreadName = true;
    private boolean includeMDC = true;
    private boolean includeLoggerName = true;
    private boolean includeMessage = true;
    private boolean includeException = true;
    private boolean includeContextName = false;
    private boolean includeTimestamp = true;

    @Nullable
    private String jsonProtocolVersion;

    private final ThrowableHandlingConverter throwableProxyConverter;
    private final TimestampFormatter timestampFormatter;
    private final Map<String, Object> additionalFields;
    private final Map<String, String> customFieldNames;

    public EventJsonLayout(JsonFormatter jsonFormatter, TimestampFormatter timestampFormatter,
                           ThrowableHandlingConverter throwableProxyConverter, Map<String, String> customFieldNames,
                           Map<String, Object> additionalFields) {
        super(jsonFormatter);
        this.timestampFormatter = timestampFormatter;
        this.additionalFields = additionalFields;
        this.customFieldNames = customFieldNames;
        this.throwableProxyConverter = throwableProxyConverter;
    }

    @Override
    public void start() {
        throwableProxyConverter.start();
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        throwableProxyConverter.stop();
    }

    @Override
    protected Map<String, Object> toJsonMap(ILoggingEvent event) {
        return new MapBuilder(timestampFormatter, customFieldNames, additionalFields, 16)
            .addTimestamp("timestamp", includeTimestamp, event.getTimeStamp())
            .add("level", includeLevel, String.valueOf(event.getLevel()))
            .add("thread", includeThreadName, event.getThreadName())
            .add("mdc", includeMDC, event.getMDCPropertyMap())
            .add("logger", includeLoggerName, event.getLoggerName())
            .add("message", includeMessage, event.getFormattedMessage())
            .add("context", includeContextName, event.getLoggerContextVO().getName())
            .add("version", jsonProtocolVersion != null, jsonProtocolVersion)
            .add("exception", includeException && event.getThrowableProxy() != null, throwableProxyConverter.convert(event))
            .build();
    }

    public boolean isIncludeLevel() {
        return includeLevel;
    }

    public void setIncludeLevel(boolean includeLevel) {
        this.includeLevel = includeLevel;
    }

    public boolean isIncludeThreadName() {
        return includeThreadName;
    }

    public void setIncludeThreadName(boolean includeThreadName) {
        this.includeThreadName = includeThreadName;
    }

    public boolean isIncludeMDC() {
        return includeMDC;
    }

    public void setIncludeMDC(boolean includeMDC) {
        this.includeMDC = includeMDC;
    }

    public boolean isIncludeLoggerName() {
        return includeLoggerName;
    }

    public void setIncludeLoggerName(boolean includeLoggerName) {
        this.includeLoggerName = includeLoggerName;
    }

    public boolean isIncludeMessage() {
        return includeMessage;
    }

    public void setIncludeMessage(boolean includeMessage) {
        this.includeMessage = includeMessage;
    }

    public boolean isIncludeException() {
        return includeException;
    }

    public void setIncludeException(boolean includeException) {
        this.includeException = includeException;
    }

    public boolean isIncludeContextName() {
        return includeContextName;
    }

    public void setIncludeContextName(boolean includeContextName) {
        this.includeContextName = includeContextName;
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
