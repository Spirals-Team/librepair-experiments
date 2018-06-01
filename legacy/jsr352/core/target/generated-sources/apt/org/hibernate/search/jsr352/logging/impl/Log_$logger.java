package org.hibernate.search.jsr352.logging.impl;

import java.util.Locale;
import java.io.Serializable;
import org.hibernate.search.exception.AssertionFailure;
import org.jboss.logging.DelegatingBasicLogger;
import org.hibernate.search.exception.SearchException;
import java.lang.String;
import org.jboss.logging.Logger;
import java.lang.InterruptedException;
import java.lang.Exception;
import java.lang.Number;
import org.jboss.logging.BasicLogger;
import java.lang.Long;
import java.lang.Throwable;
import org.hibernate.search.util.logging.impl.BaseHibernateSearchLogger;
import java.lang.Object;
import java.util.Arrays;
import org.hibernate.search.spi.IndexedTypeIdentifier;


import static org.jboss.logging.Logger.Level.TRACE;
import static org.jboss.logging.Logger.Level.ERROR;
import static org.jboss.logging.Logger.Level.INFO;
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
    private static final String entityManagerFactoryReferenceIsEmpty = "HSEARCH500001: An 'entityManagerFactoryNamespace' parameter was defined, but the 'entityManagerFactoryReference' parameter is empty. Please also set the 'entityManagerFactoryReference' parameter to select an entity manager factory, or do not set the 'entityManagerFactoryNamespace' parameter to try to use a default entity manager factory.";
    protected String entityManagerFactoryReferenceIsEmpty$str() {
        return entityManagerFactoryReferenceIsEmpty;
    }
    @Override
    public final SearchException entityManagerFactoryReferenceIsEmpty() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), entityManagerFactoryReferenceIsEmpty$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String noAvailableEntityManagerFactoryInCDI = "HSEARCH500002: No entity manager factory available in the CDI context with this bean name: '%1$s'. Make sure your entity manager factory is a named bean.";
    protected String noAvailableEntityManagerFactoryInCDI$str() {
        return noAvailableEntityManagerFactoryInCDI;
    }
    @Override
    public final SearchException noAvailableEntityManagerFactoryInCDI(final String reference) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), noAvailableEntityManagerFactoryInCDI$str(), reference));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownEntityManagerFactoryNamespace = "HSEARCH500003: Unknown entity manager factory namespace: '%1$s'. Please use a supported namespace.";
    protected String unknownEntityManagerFactoryNamespace$str() {
        return unknownEntityManagerFactoryNamespace;
    }
    @Override
    public final SearchException unknownEntityManagerFactoryNamespace(final String namespace) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unknownEntityManagerFactoryNamespace$str(), namespace));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotRetrieveEntityManagerFactoryInJsr352 = "HSEARCH500004: Exception while retrieving the EntityManagerFactory using @PersistenceUnit. This generally happens either because the persistence wasn't configured properly or because there are multiple persistence units.";
    protected String cannotRetrieveEntityManagerFactoryInJsr352$str() {
        return cannotRetrieveEntityManagerFactoryInJsr352;
    }
    @Override
    public final SearchException cannotRetrieveEntityManagerFactoryInJsr352() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotRetrieveEntityManagerFactoryInJsr352$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String ambiguousEntityManagerFactoryInJsr352 = "HSEARCH500005: Multiple entity manager factories have been registered in the CDI context. Please provide the bean name for the selected entity manager factory to the batch indexing job through the 'entityManagerFactoryReference' parameter.";
    protected String ambiguousEntityManagerFactoryInJsr352$str() {
        return ambiguousEntityManagerFactoryInJsr352;
    }
    @Override
    public final SearchException ambiguousEntityManagerFactoryInJsr352() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), ambiguousEntityManagerFactoryInJsr352$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotFindEntityManagerFactoryByPUName = "HSEARCH500006: No entity manager factory has been created with this persistence unit name yet: '%1$s'. Make sure you use the JPA API to create your entity manager factory (use a 'persistence.xml' file) and that the entity manager factory has already been created and wasn't closed before you launch the job.";
    protected String cannotFindEntityManagerFactoryByPUName$str() {
        return cannotFindEntityManagerFactoryByPUName;
    }
    @Override
    public final SearchException cannotFindEntityManagerFactoryByPUName(final String persistentUnitName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotFindEntityManagerFactoryByPUName$str(), persistentUnitName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotFindEntityManagerFactoryByName = "HSEARCH500007: No entity manager factory has been created with this name yet: '%1$s'. Make sure your entity manager factory is named (for instance by setting the 'hibernate.session_factory_name' option) and that the entity manager factory has already been created and wasn't closed before you launch the job.";
    protected String cannotFindEntityManagerFactoryByName$str() {
        return cannotFindEntityManagerFactoryByName;
    }
    @Override
    public final SearchException cannotFindEntityManagerFactoryByName(final String entityManagerFactoryName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotFindEntityManagerFactoryByName$str(), entityManagerFactoryName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String noEntityManagerFactoryCreated = "HSEARCH500008: No entity manager factory has been created yet. Make sure that the entity manager factory has already been created and wasn't closed before you launched the job.";
    protected String noEntityManagerFactoryCreated$str() {
        return noEntityManagerFactoryCreated;
    }
    @Override
    public final SearchException noEntityManagerFactoryCreated() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), noEntityManagerFactoryCreated$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String tooManyActiveEntityManagerFactories = "HSEARCH500009: Multiple entity manager factories are currently active. Please provide the name of the selected persistence unit to the batch indexing job through the 'entityManagerFactoryReference' parameter (you may also use the 'entityManagerFactoryNamespace' parameter for more referencing options).";
    protected String tooManyActiveEntityManagerFactories$str() {
        return tooManyActiveEntityManagerFactories;
    }
    @Override
    public final SearchException tooManyActiveEntityManagerFactories() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), tooManyActiveEntityManagerFactories$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void analyzeIndexProgress(final String progress) {
        super.log.logf(FQCN, INFO, null, analyzeIndexProgress$str(), progress);
    }
    private static final String analyzeIndexProgress = "HSEARCH500010: %1$s";
    protected String analyzeIndexProgress$str() {
        return analyzeIndexProgress;
    }
    @Override
    public final void startOptimization() {
        super.log.logf(FQCN, INFO, null, startOptimization$str());
    }
    private static final String startOptimization = "HSEARCH500015: Optimizing all entities ...";
    protected String startOptimization$str() {
        return startOptimization;
    }
    @Override
    public final void criteriaSize(final int size) {
        super.log.logf(FQCN, DEBUG, null, criteriaSize$str(), size);
    }
    private static final String criteriaSize = "HSEARCH500016: %1$d criteria found.";
    protected String criteriaSize$str() {
        return criteriaSize;
    }
    @Override
    public final void checkpointReached(final String entityName, final Object checkpointInfo) {
        super.log.logf(FQCN, DEBUG, null, checkpointReached$str(), entityName, checkpointInfo);
    }
    private static final String checkpointReached = "HSEARCH500017: Checkpoint reached. Sending checkpoint ID to batch runtime... (entity='%1$s', checkpointInfo='%2$s')";
    protected String checkpointReached$str() {
        return checkpointReached;
    }
    @Override
    public final void openingReader(final String partitionId, final String entityName) {
        super.log.logf(FQCN, DEBUG, null, openingReader$str(), partitionId, entityName);
    }
    private static final String openingReader = "HSEARCH500018: Opening EntityReader of partitionId='%1$s', entity='%2$s'.";
    protected String openingReader$str() {
        return openingReader;
    }
    @Override
    public final void closingReader(final String partitionId, final String entityName) {
        super.log.logf(FQCN, DEBUG, null, closingReader$str(), partitionId, entityName);
    }
    private static final String closingReader = "HSEARCH500019: Closing EntityReader of partitionId='%1$s', entity='%2$s'.";
    protected String closingReader$str() {
        return closingReader;
    }
    @Override
    public final void readingEntity() {
        super.log.logf(FQCN, TRACE, null, readingEntity$str());
    }
    private static final String readingEntity = "HSEARCH500021: Reading entity...";
    protected String readingEntity$str() {
        return readingEntity;
    }
    @Override
    public final void noMoreResults() {
        super.log.logf(FQCN, INFO, null, noMoreResults$str());
    }
    private static final String noMoreResults = "HSEARCH500022: No more results, read ends.";
    protected String noMoreResults$str() {
        return noMoreResults;
    }
    @Override
    public final void processEntity(final Object entityId) {
        super.log.logf(FQCN, TRACE, null, processEntity$str(), entityId);
    }
    private static final String processEntity = "HSEARCH500023: Processing entity with id: '%1$s'";
    protected String processEntity$str() {
        return processEntity;
    }
    @Override
    public final void openingDocWriter(final String partitionId, final String entityName) {
        super.log.logf(FQCN, DEBUG, null, openingDocWriter$str(), partitionId, entityName);
    }
    private static final String openingDocWriter = "HSEARCH500024: Opening LuceneDocWriter of partitionId='%1$s', entity='%2$s'.";
    protected String openingDocWriter$str() {
        return openingDocWriter;
    }
    @Override
    public final void closingDocWriter(final String partitionId, final String entityName) {
        super.log.logf(FQCN, DEBUG, null, closingDocWriter$str(), partitionId, entityName);
    }
    private static final String closingDocWriter = "HSEARCH500025: Closing LuceneDocWriter of partitionId='%1$s', entity='%2$s'.";
    protected String closingDocWriter$str() {
        return closingDocWriter;
    }
    @Override
    public final void partitionsPlan(final int partitionSize, final int threadSize) {
        super.log.logf(FQCN, INFO, null, partitionsPlan$str(), partitionSize, threadSize);
    }
    private static final String partitionsPlan = "HSEARCH500026: %1$d partitions, %2$d threads.";
    protected String partitionsPlan$str() {
        return partitionsPlan;
    }
    @Override
    public final void rowsToIndex(final String entityName, final Long rowsToIndex) {
        super.log.logf(FQCN, INFO, null, rowsToIndex$str(), entityName, rowsToIndex);
    }
    private static final String rowsToIndex = "HSEARCH500027: entityName: '%1$s', rowsToIndex: %2$d";
    protected String rowsToIndex$str() {
        return rowsToIndex;
    }
    private static final String failedToSerializeJobParameter = "HSEARCH500028: Failed to serialize job parameter of type %1$s";
    protected String failedToSerializeJobParameter$str() {
        return failedToSerializeJobParameter;
    }
    @Override
    public final SearchException failedToSerializeJobParameter(final Class<?> type, final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), failedToSerializeJobParameter$str(), new org.hibernate.search.util.logging.impl.ClassFormatter(type)), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToParseJobParameter = "HSEARCH500029: Unable to parse value '%2$s' for job parameter '%1$s'.";
    protected String unableToParseJobParameter$str() {
        return unableToParseJobParameter;
    }
    @Override
    public final SearchException unableToParseJobParameter(final String parameterName, final Object parameterValue, final Exception e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToParseJobParameter$str(), parameterName, parameterValue), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String illegalCheckpointInterval = "HSEARCH500030: The value of parameter 'checkpointInterval' (value=%1$d) should be equal to or less than the value of parameter 'rowsPerPartition' (value=%2$d).";
    protected String illegalCheckpointInterval$str() {
        return illegalCheckpointInterval;
    }
    @Override
    public final SearchException illegalCheckpointInterval(final int checkpointInterval, final int rowsPerPartition) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), illegalCheckpointInterval$str(), checkpointInterval, rowsPerPartition));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String negativeValueOrZero = "HSEARCH500031: The value of parameter '%1$s' (value=%2$d) should be greater than 0.";
    protected String negativeValueOrZero$str() {
        return negativeValueOrZero;
    }
    @Override
    public final SearchException negativeValueOrZero(final String parameterName, final Number parameterValue) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), negativeValueOrZero$str(), parameterName, parameterValue));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String failingEntityTypes = "HSEARCH500032: The following selected entity types aren't indexable: %1$s. Please check if the annotation '@Indexed' has been added to each of them.";
    protected String failingEntityTypes$str() {
        return failingEntityTypes;
    }
    @Override
    public final SearchException failingEntityTypes(final String failingEntityNames) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), failingEntityTypes$str(), failingEntityNames));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String illegalSessionClearInterval = "HSEARCH500033: The value of parameter 'sessionClearInterval' (value=%1$d) should be equal to or less than the value of parameter 'checkpointInterval' (value=%2$d).";
    protected String illegalSessionClearInterval$str() {
        return illegalSessionClearInterval;
    }
    @Override
    public final SearchException illegalSessionClearInterval(final int sessionClearInterval, final int checkpointInterval) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), illegalSessionClearInterval$str(), sessionClearInterval, checkpointInterval));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
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
