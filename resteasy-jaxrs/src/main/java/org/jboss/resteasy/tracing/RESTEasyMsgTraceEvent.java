package org.jboss.resteasy.tracing;

/**
 * Common tracing events.
 */
public enum RESTEasyMsgTraceEvent implements RESTEasyTracingEvent {

    /**
     * {@link javax.ws.rs.ext.ReaderInterceptor} invocation before a call to {@code context.proceed()}.
     * TODO: implement this
     */
    RI_BEFORE(RESTEasyTracingLevel.TRACE, "RI", "%s BEFORE context.proceed()"),
    /**
     * {@link javax.ws.rs.ext.ReaderInterceptor} invocation after a call to {@code context.proceed()}.
     * TODO: implement this
     */
    RI_AFTER(RESTEasyTracingLevel.TRACE, "RI", "%s AFTER context.proceed()"),
    /**
     * {@link javax.ws.rs.ext.ReaderInterceptor} invocation summary.
     * TODO: implement this
     */
    RI_SUMMARY(RESTEasyTracingLevel.SUMMARY, "RI", "ReadFrom summary: %s interceptors"),
    /**
     * {@link javax.ws.rs.ext.MessageBodyReader} lookup.
     * TODO: implement this
     */
    MBR_FIND(RESTEasyTracingLevel.TRACE, "MBR", "Find MBR for type=[%s] genericType=[%s] mediaType=[%s] annotations=%s"),
    /**
     * {@link javax.ws.rs.ext.MessageBodyReader#isReadable} returned {@code false}.
     * TODO: implement this
     */
    MBR_NOT_READABLE(RESTEasyTracingLevel.VERBOSE, "MBR", "%s is NOT readable"),
    /**
     * {@link javax.ws.rs.ext.MessageBodyReader} selected.
     * TODO: implement this
     */
    MBR_SELECTED(RESTEasyTracingLevel.TRACE, "MBR", "%s IS readable"),
    /**
     * {@link javax.ws.rs.ext.MessageBodyReader} skipped as higher-priority reader has been selected already.
     * TODO: implement this
     */
    MBR_SKIPPED(RESTEasyTracingLevel.VERBOSE, "MBR", "%s is skipped"),
    /**
     * {@link javax.ws.rs.ext.MessageBodyReader#readFrom} invoked.
     * TODO: implement this
     */
    MBR_READ_FROM(RESTEasyTracingLevel.TRACE, "MBR", "ReadFrom by %s"),
    /**
     * {@link javax.ws.rs.ext.MessageBodyWriter} lookup.
     * TODO: implement this
     */
    MBW_FIND(RESTEasyTracingLevel.TRACE, "MBW", "Find MBW for type=[%s] genericType=[%s] mediaType=[%s] annotations=%s"),
    /**
     * {@link javax.ws.rs.ext.MessageBodyWriter#isWriteable} returned {@code false}.
     * TODO: implement this
     */
    MBW_NOT_WRITEABLE(RESTEasyTracingLevel.VERBOSE, "MBW", "%s is NOT writeable"),
    /**
     * {@link javax.ws.rs.ext.MessageBodyWriter#isWriteable} selected.
     * TODO: implement this
     * progress: ServerWriterInterceptorContext traced
     * ClientWriterInterceptorContext not yet traced
     */
    MBW_SELECTED(RESTEasyTracingLevel.TRACE, "MBW", "%s IS writeable"),
    /**
     * {@link javax.ws.rs.ext.MessageBodyWriter} skipped as higher-priority writer has been selected already.
     * TODO: implement this
     */
    MBW_SKIPPED(RESTEasyTracingLevel.VERBOSE, "MBW", "%s is skipped"),
    /**
     * {@link javax.ws.rs.ext.MessageBodyWriter#writeTo} invoked.
     * TODO: implement this
     */
    MBW_WRITE_TO(RESTEasyTracingLevel.TRACE, "MBW", "WriteTo by %s"),
    /**
     * {@link javax.ws.rs.ext.WriterInterceptor} invocation before a call to {@code context.proceed()}.
     * TODO: implement this
     */
    WI_BEFORE(RESTEasyTracingLevel.TRACE, "WI", "%s BEFORE context.proceed()"),
    /**
     * {@link javax.ws.rs.ext.WriterInterceptor} invocation after a call to {@code context.proceed()}.
     * TODO: implement this
     */
    WI_AFTER(RESTEasyTracingLevel.TRACE, "WI", "%s AFTER context.proceed()"),
    /**
     * {@link javax.ws.rs.ext.ReaderInterceptor} invocation summary.
     * TODO: implement this
     */
    WI_SUMMARY(RESTEasyTracingLevel.SUMMARY, "WI", "WriteTo summary: %s interceptors");

    private final RESTEasyTracingLevel level;
    private final String category;
    private final String messageFormat;

    private RESTEasyMsgTraceEvent(RESTEasyTracingLevel level, String category, String messageFormat) {
        this.level = level;
        this.category = category;
        this.messageFormat = messageFormat;
    }

    @Override
    public String category() {
        return category;
    }

    @Override
    public RESTEasyTracingLevel level() {
        return level;
    }

    @Override
    public String messageFormat() {
        return messageFormat;
    }

}
