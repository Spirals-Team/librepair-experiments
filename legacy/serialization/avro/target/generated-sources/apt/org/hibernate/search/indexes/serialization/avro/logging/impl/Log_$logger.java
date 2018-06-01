package org.hibernate.search.indexes.serialization.avro.logging.impl;

import java.util.Locale;
import java.io.Serializable;
import org.hibernate.search.exception.AssertionFailure;
import org.jboss.logging.DelegatingBasicLogger;
import org.hibernate.search.exception.SearchException;
import java.lang.String;
import org.jboss.logging.Logger;
import java.lang.InterruptedException;
import java.lang.Exception;
import org.jboss.logging.BasicLogger;
import org.hibernate.search.util.logging.impl.BaseHibernateSearchLogger;
import java.util.Arrays;
import org.hibernate.search.spi.IndexedTypeIdentifier;


import static org.jboss.logging.Logger.Level.ERROR;
import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.WARN;

/**
 * Warning this class consists of generated code.
 */
public class Log_$logger extends DelegatingBasicLogger implements Log, BaseHibernateSearchLogger, BasicLogger, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FQCN = Log_$logger.class.getName();
    public Log_$logger(final Logger log) {
        super(log);
    }
    private static final Locale LOCALE = Locale.ROOT;
    protected Locale getLoggingLocale() {
        return LOCALE;
    }
    @Override
    public final void serializationProtocol(final int major, final int minor) {
        super.log.logf(FQCN, DEBUG, null, serializationProtocol$str(), major, minor);
    }
    private static final String serializationProtocol = "HSEARCH000079: Serialization protocol version %1$d.%2$d initialized";
    protected String serializationProtocol$str() {
        return serializationProtocol;
    }
    private static final String incompatibleProtocolVersion = "HSEARCH000098: Unable to parse message from protocol version %1$d.%2$d. Current protocol version: %3$d.%4$d";
    protected String incompatibleProtocolVersion$str() {
        return incompatibleProtocolVersion;
    }
    @Override
    public final SearchException incompatibleProtocolVersion(final int messageMajor, final int messageMinor, final int currentMajor, final int currentMinor) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), incompatibleProtocolVersion$str(), messageMajor, messageMinor, currentMajor, currentMinor));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToLoadAvroSchema = "HSEARCH300001: Unable to find Avro schema '%s'";
    protected String unableToLoadAvroSchema$str() {
        return unableToLoadAvroSchema;
    }
    @Override
    public final SearchException unableToLoadAvroSchema(final String avroSchemaFile) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToLoadAvroSchema$str(), avroSchemaFile));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void unexpectedMinorProtocolVersion(final int majorVersion, final int minorVersion, final int latestKnownMinor) {
        super.log.logf(FQCN, WARN, null, unexpectedMinorProtocolVersion$str(), majorVersion, minorVersion, latestKnownMinor);
    }
    private static final String unexpectedMinorProtocolVersion = "HSEARCH300002: Parsing message from a future protocol version. Some feature might not be propagated. Message version: %1$d.%2$d. Current protocol version: %1$d.%3$d";
    protected String unexpectedMinorProtocolVersion$str() {
        return unexpectedMinorProtocolVersion;
    }
    @Override
    public final void interruptedWhileWaitingForIndexActivity(final String arg0, final InterruptedException arg1) {
        super.log.logf(FQCN, WARN, arg1, interruptedWhileWaitingForIndexActivity$str(), arg0);
    }
    private static final String interruptedWhileWaitingForIndexActivity = "HSEARCH000049: '%s' was interrupted while waiting for index activity to finish. Index might be inconsistent or have a stale lock";
    protected String interruptedWhileWaitingForIndexActivity$str() {
        return interruptedWhileWaitingForIndexActivity;
    }
    @Override
    public final void illegalObjectRetrievedFromMessage(final Exception arg0) {
        super.log.logf(FQCN, ERROR, arg0, illegalObjectRetrievedFromMessage$str());
    }
    private static final String illegalObjectRetrievedFromMessage = "HSEARCH000069: Illegal object retrieved from message";
    protected String illegalObjectRetrievedFromMessage$str() {
        return illegalObjectRetrievedFromMessage;
    }
    private static final String unableToLoadResource = "HSEARCH000114: Could not load resource: '%1$s'";
    protected String unableToLoadResource$str() {
        return unableToLoadResource;
    }
    @Override
    public final SearchException unableToLoadResource(final String arg0) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToLoadResource$str(), arg0));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownResolution = "HSEARCH000140: Unknown Resolution: %1$s";
    protected String unknownResolution$str() {
        return unknownResolution;
    }
    @Override
    public final AssertionFailure unknownResolution(final String arg0) {
        final AssertionFailure result = new AssertionFailure(String.format(getLoggingLocale(), unknownResolution$str(), arg0));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedFacetRangeParameter = "HSEARCH000266: '%s' is not a valid type for a facet range request. Numbers (byte, short, int, long, float, double and their wrappers) as well as dates are supported";
    protected String unsupportedFacetRangeParameter$str() {
        return unsupportedFacetRangeParameter;
    }
    @Override
    public final SearchException unsupportedFacetRangeParameter(final String arg0) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedFacetRangeParameter$str(), arg0));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String projectingFieldWithoutTwoWayFieldBridge = "HSEARCH000324: The fieldBridge for field '%1$s' is an instance of '%2$s', which does not implement TwoWayFieldBridge. Projected fields must have a TwoWayFieldBridge.";
    protected String projectingFieldWithoutTwoWayFieldBridge$str() {
        return projectingFieldWithoutTwoWayFieldBridge;
    }
    @Override
    public final SearchException projectingFieldWithoutTwoWayFieldBridge(final String arg0, final Class<?> arg1) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), projectingFieldWithoutTwoWayFieldBridge$str(), arg0, arg1));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedNullTokenType = "HSEARCH000327: Unsupported indexNullAs token type '%3$s' on field '%2$s' of entity '%1$s'.";
    protected String unsupportedNullTokenType$str() {
        return unsupportedNullTokenType;
    }
    @Override
    public final SearchException unsupportedNullTokenType(final IndexedTypeIdentifier arg0, final String arg1, final Class<?> arg2) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedNullTokenType$str(), new org.hibernate.search.util.logging.impl.IndexedTypeIdentifierFormatter(arg0), arg1, arg2));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidLuceneAnalyzerDefinitionProvider = "HSEARCH000329: Property 'hibernate.search.lucene.analysis_definition_provider' set to value '%1$s' is invalid. The value must be the fully-qualified name of a class with a public, no-arg constructor in your classpath. Also, the class must either implement LuceneAnalyzerDefinitionProvider or expose a public, @Factory-annotated method returning a LuceneAnalyzerDefinitionProvider.";
    protected String invalidLuceneAnalyzerDefinitionProvider$str() {
        return invalidLuceneAnalyzerDefinitionProvider;
    }
    @Override
    public final SearchException invalidLuceneAnalyzerDefinitionProvider(final String arg0, final Exception arg1) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), invalidLuceneAnalyzerDefinitionProvider$str(), arg0), arg1);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
}
