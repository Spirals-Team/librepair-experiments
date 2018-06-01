package org.hibernate.search.elasticsearch.logging.impl;

import java.util.Locale;
import org.hibernate.search.elasticsearch.client.impl.ElasticsearchResponse;
import java.io.Serializable;
import org.hibernate.search.exception.AssertionFailure;
import org.apache.lucene.search.Query;
import org.jboss.logging.DelegatingBasicLogger;
import org.hibernate.search.exception.SearchException;
import com.google.gson.JsonObject;
import org.hibernate.search.elasticsearch.client.impl.ElasticsearchRequest;
import org.hibernate.search.elasticsearch.schema.impl.ElasticsearchSchemaValidationException;
import java.lang.String;
import org.jboss.logging.Logger;
import java.lang.InterruptedException;
import java.lang.Exception;
import org.apache.lucene.search.SortField.Type;
import org.hibernate.search.analyzer.spi.AnalyzerReference;
import com.google.gson.JsonElement;
import org.jboss.logging.BasicLogger;
import org.hibernate.search.util.logging.impl.BaseHibernateSearchLogger;
import java.lang.Object;
import org.hibernate.search.spi.IndexedTypeIdentifier;
import java.util.Arrays;
import java.lang.IllegalArgumentException;
import java.lang.UnsupportedOperationException;


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
    private static final String cannotRunEsQueryTargetingEntityIndexedWithNonEsIndexManager = "HSEARCH400001: Cannot execute query '%2$s', as targeted entity type '%1$s' is not mapped to an Elasticsearch index";
    protected String cannotRunEsQueryTargetingEntityIndexedWithNonEsIndexManager$str() {
        return cannotRunEsQueryTargetingEntityIndexedWithNonEsIndexManager;
    }
    @Override
    public final SearchException cannotRunEsQueryTargetingEntityIndexedWithNonEsIndexManager(final IndexedTypeIdentifier entityType, final String query) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotRunEsQueryTargetingEntityIndexedWithNonEsIndexManager$str(), new org.hibernate.search.util.logging.impl.IndexedTypeIdentifierFormatter(entityType), query));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotTransformLuceneQueryIntoEsQuery = "HSEARCH400002: Lucene query '%1$s' cannot be transformed into equivalent Elasticsearch query";
    protected String cannotTransformLuceneQueryIntoEsQuery$str() {
        return cannotTransformLuceneQueryIntoEsQuery;
    }
    @Override
    public final SearchException cannotTransformLuceneQueryIntoEsQuery(final Query query) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotTransformLuceneQueryIntoEsQuery$str(), query));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotSendRangeDefinitionOrderToElasticsearchBackend = "HSEARCH400004: The sort order RANGE_DEFINITION_ORDER cant not be sent used with Elasticsearch";
    protected String cannotSendRangeDefinitionOrderToElasticsearchBackend$str() {
        return cannotSendRangeDefinitionOrderToElasticsearchBackend;
    }
    @Override
    public final SearchException cannotSendRangeDefinitionOrderToElasticsearchBackend() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotSendRangeDefinitionOrderToElasticsearchBackend$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotUseThisSortTypeWithNullSortFieldName = "HSEARCH400005: The SortType '%1$s' cannot be used with a null sort field name";
    protected String cannotUseThisSortTypeWithNullSortFieldName$str() {
        return cannotUseThisSortTypeWithNullSortFieldName;
    }
    @Override
    public final SearchException cannotUseThisSortTypeWithNullSortFieldName(final Type sortType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotUseThisSortTypeWithNullSortFieldName$str(), sortType));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotQueryOnEmptyPhraseQuery = "HSEARCH400006: Empty phrase queries are not supported";
    protected String cannotQueryOnEmptyPhraseQuery$str() {
        return cannotQueryOnEmptyPhraseQuery;
    }
    @Override
    public final SearchException cannotQueryOnEmptyPhraseQuery() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotQueryOnEmptyPhraseQuery$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String elasticsearchRequestFailed = "HSEARCH400007: Elasticsearch request failed.\nRequest: %1$s\nResponse: %2$s";
    protected String elasticsearchRequestFailed$str() {
        return elasticsearchRequestFailed;
    }
    @Override
    public final SearchException elasticsearchRequestFailed(final ElasticsearchRequest request, final ElasticsearchResponse response, final Exception cause) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), elasticsearchRequestFailed$str(), new ElasticsearchRequestFormatter(request), new ElasticsearchResponseFormatter(response)), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String elasticsearchBulkedRequestFailed = "HSEARCH400008: Elasticsearch bulked request failed.\nRequest metadata: %1$sResponse: %2$s";
    protected String elasticsearchBulkedRequestFailed$str() {
        return elasticsearchBulkedRequestFailed;
    }
    @Override
    public final SearchException elasticsearchBulkedRequestFailed(final JsonObject requestMetadata, final JsonObject response, final Exception cause) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), elasticsearchBulkedRequestFailed$str(), new ElasticsearchJsonObjectFormatter(requestMetadata), new ElasticsearchJsonObjectFormatter(response)), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void analyzerIsNotElasticsearch(final IndexedTypeIdentifier entityType, final String fieldName, final AnalyzerReference analyzerReference) {
        super.log.logf(FQCN, WARN, null, analyzerIsNotElasticsearch$str(), new org.hibernate.search.util.logging.impl.IndexedTypeIdentifierFormatter(entityType), fieldName, analyzerReference);
    }
    private static final String analyzerIsNotElasticsearch = "HSEARCH400009: Field '%2$s' in '%1$s' requires an Elasticsearch analyzer reference (got '%3$s' instead). The analyzer will be ignored.";
    protected String analyzerIsNotElasticsearch$str() {
        return analyzerIsNotElasticsearch;
    }
    private static final String elasticsearchRequestTimeout = "HSEARCH400010: Elasticsearch connection time-out; check the cluster status, it should be 'green'";
    protected String elasticsearchRequestTimeout$str() {
        return elasticsearchRequestTimeout;
    }
    @Override
    public final SearchException elasticsearchRequestTimeout() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), elasticsearchRequestTimeout$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedProjectionOfNonJsonPrimitiveFields = "HSEARCH400011: Projection of non-JSON-primitive field values is not supported: '%1$s'";
    protected String unsupportedProjectionOfNonJsonPrimitiveFields$str() {
        return unsupportedProjectionOfNonJsonPrimitiveFields;
    }
    @Override
    public final SearchException unsupportedProjectionOfNonJsonPrimitiveFields(final JsonElement value) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedProjectionOfNonJsonPrimitiveFields$str(), value));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String interruptedWhileWaitingForRequestCompletion = "HSEARCH400012: Interrupted while waiting for requests to be processed.";
    protected String interruptedWhileWaitingForRequestCompletion$str() {
        return interruptedWhileWaitingForRequestCompletion;
    }
    @Override
    public final SearchException interruptedWhileWaitingForRequestCompletion(final Exception cause) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), interruptedWhileWaitingForRequestCompletion$str()), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String filterFactoryMethodReturnsUnsupportedType = "HSEARCH400013: @Factory method does not return a Filter class or an ElasticsearchFilter class: %1$s.%2$s";
    protected String filterFactoryMethodReturnsUnsupportedType$str() {
        return filterFactoryMethodReturnsUnsupportedType;
    }
    @Override
    public final SearchException filterFactoryMethodReturnsUnsupportedType(final String implementorName, final String factoryMethodName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), filterFactoryMethodReturnsUnsupportedType$str(), implementorName, factoryMethodName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String filterFactoryMethodInaccessible = "HSEARCH400014: Unable to access @Factory method: %1$s.%2$s";
    protected String filterFactoryMethodInaccessible$str() {
        return filterFactoryMethodInaccessible;
    }
    @Override
    public final SearchException filterFactoryMethodInaccessible(final String implementorName, final String factoryMethodName, final Exception cause) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), filterFactoryMethodInaccessible$str(), implementorName, factoryMethodName), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String filterHasUnsupportedType = "HSEARCH400015: Filter implementation does not implement the Filter interface or the ElasticsearchFilter interface: %1$s";
    protected String filterHasUnsupportedType$str() {
        return filterHasUnsupportedType;
    }
    @Override
    public final SearchException filterHasUnsupportedType(final String actualClassName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), filterHasUnsupportedType$str(), actualClassName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String documentExtractorTopDocsUnsupported = "HSEARCH400016: TopDocs not available when using Elasticsearch";
    protected String documentExtractorTopDocsUnsupported$str() {
        return documentExtractorTopDocsUnsupported;
    }
    @Override
    public final UnsupportedOperationException documentExtractorTopDocsUnsupported() {
        final UnsupportedOperationException result = new UnsupportedOperationException(String.format(getLoggingLocale(), documentExtractorTopDocsUnsupported$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String hsQueryLuceneQueryUnsupported = "HSEARCH400017: Cannot use Lucene query with Elasticsearch";
    protected String hsQueryLuceneQueryUnsupported$str() {
        return hsQueryLuceneQueryUnsupported;
    }
    @Override
    public final UnsupportedOperationException hsQueryLuceneQueryUnsupported() {
        final UnsupportedOperationException result = new UnsupportedOperationException(String.format(getLoggingLocale(), hsQueryLuceneQueryUnsupported$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unexpectedNumericEncodingType = "HSEARCH400018: Unexpected numeric encoding type for field '%2$s' on entity type '%1$s'. If you used a custom field bridge, make sure it implements MetadataProvidingFieldBridge and provides metadata for this field.";
    protected String unexpectedNumericEncodingType$str() {
        return unexpectedNumericEncodingType;
    }
    @Override
    public final SearchException unexpectedNumericEncodingType(final IndexedTypeIdentifier entityType, final String fieldName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unexpectedNumericEncodingType$str(), new org.hibernate.search.util.logging.impl.IndexedTypeIdentifierFormatter(entityType), fieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String elasticsearchMappingCreationFailed = "HSEARCH400020: Could not create mapping for entity type %1$s";
    protected String elasticsearchMappingCreationFailed$str() {
        return elasticsearchMappingCreationFailed;
    }
    @Override
    public final SearchException elasticsearchMappingCreationFailed(final Object entityType, final Exception cause) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), elasticsearchMappingCreationFailed$str(), entityType), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unexpectedFieldType = "HSEARCH400021: Unexpected field type for field '%2$s': %1$s";
    protected String unexpectedFieldType$str() {
        return unexpectedFieldType;
    }
    @Override
    public final SearchException unexpectedFieldType(final String fieldType, final String fieldName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unexpectedFieldType$str(), fieldType, fieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unexpectedIndexStatusString = "HSEARCH400022: Unexpected index status string: '%1$s'. Specify one of 'green', 'yellow' or 'red'.";
    protected String unexpectedIndexStatusString$str() {
        return unexpectedIndexStatusString;
    }
    @Override
    public final SearchException unexpectedIndexStatusString(final String status) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unexpectedIndexStatusString$str(), status));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String negativeTimeoutValue = "HSEARCH400023: Positive timeout value expected, but it was: %1$s";
    protected String negativeTimeoutValue$str() {
        return negativeTimeoutValue;
    }
    @Override
    public final SearchException negativeTimeoutValue(final int timeout) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), negativeTimeoutValue$str(), timeout));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unexpectedIndexStatus = "HSEARCH400024: Timed out while waiting for for index '%1$s' to reach status '%2$s'; status was still '%3$s' after %4$s.";
    protected String unexpectedIndexStatus$str() {
        return unexpectedIndexStatus;
    }
    @Override
    public final SearchException unexpectedIndexStatus(final String indexName, final String expected, final String actual, final String timeoutAndUnit) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unexpectedIndexStatus$str(), indexName, expected, actual, timeoutAndUnit));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String indexManagerReaderProviderUnsupported = "HSEARCH400025: With an Elasticsearch backend it is not possible to get a ReaderProvider or an IndexReader";
    protected String indexManagerReaderProviderUnsupported$str() {
        return indexManagerReaderProviderUnsupported;
    }
    @Override
    public final UnsupportedOperationException indexManagerReaderProviderUnsupported() {
        final UnsupportedOperationException result = new UnsupportedOperationException(String.format(getLoggingLocale(), indexManagerReaderProviderUnsupported$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String facetingRequestHasUnsupportedType = "HSEARCH400026: Faceting request of type %1$s not supported";
    protected String facetingRequestHasUnsupportedType$str() {
        return facetingRequestHasUnsupportedType;
    }
    @Override
    public final SearchException facetingRequestHasUnsupportedType(final String facetingRequestType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), facetingRequestHasUnsupportedType$str(), facetingRequestType));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForBoolean = "HSEARCH400027: The 'indexNullAs' property for Boolean fields must represent a Boolean ('true' or 'false').";
    protected String invalidNullMarkerForBoolean$str() {
        return invalidNullMarkerForBoolean;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForBoolean() {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForBoolean$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForCalendarAndDate = "HSEARCH400028: The 'indexNullAs' property for Calendar and Date fields must represent a date/time in ISO-8601 format (yyyy-MM-dd'T'HH:mm:ssZ).";
    protected String invalidNullMarkerForCalendarAndDate$str() {
        return invalidNullMarkerForCalendarAndDate;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForCalendarAndDate(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForCalendarAndDate$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedOffsettedScrolling = "HSEARCH400029: Cannot use an offset ('from', 'firstResult') when scrolling through Elasticsearch results";
    protected String unsupportedOffsettedScrolling$str() {
        return unsupportedOffsettedScrolling;
    }
    @Override
    public final UnsupportedOperationException unsupportedOffsettedScrolling() {
        final UnsupportedOperationException result = new UnsupportedOperationException(String.format(getLoggingLocale(), unsupportedOffsettedScrolling$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedBackwardTraversal = "HSEARCH400030: Cannot scroll backward through Elasticsearch results. Previously accessed index was %1$s, requested index is %2$s.";
    protected String unsupportedBackwardTraversal$str() {
        return unsupportedBackwardTraversal;
    }
    @Override
    public final UnsupportedOperationException unsupportedBackwardTraversal(final int lastRequestedIndex, final int index) {
        final UnsupportedOperationException result = new UnsupportedOperationException(String.format(getLoggingLocale(), unsupportedBackwardTraversal$str(), lastRequestedIndex, index));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String backtrackingWindowOverflow = "HSEARCH400031: Cannot scroll backward more than %1$s positions through Elasticsearch results. First index still in memory is %2$s, requested index is %3$s.";
    protected String backtrackingWindowOverflow$str() {
        return backtrackingWindowOverflow;
    }
    @Override
    public final SearchException backtrackingWindowOverflow(final int backtrackingLimit, final int windowStartIndex, final int requestedIndex) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), backtrackingWindowOverflow$str(), backtrackingLimit, windowStartIndex, requestedIndex));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void unsupportedDynamicBoost(final Class<?> boostStrategyType, final IndexedTypeIdentifier entityType, final String fieldPath) {
        super.log.logf(FQCN, WARN, null, unsupportedDynamicBoost$str(), boostStrategyType, new org.hibernate.search.util.logging.impl.IndexedTypeIdentifierFormatter(entityType), fieldPath);
    }
    private static final String unsupportedDynamicBoost = "HSEARCH400032: @DynamicBoost is not supported with Elasticsearch. Ignoring boost strategy '%1$s' for entity '%2$s' (field path '%3$s').";
    protected String unsupportedDynamicBoost$str() {
        return unsupportedDynamicBoost;
    }
    private static final String schemaValidationFailed = "HSEARCH400033: An Elasticsearch schema validation failed: %1$s";
    protected String schemaValidationFailed$str() {
        return schemaValidationFailed;
    }
    @Override
    public final ElasticsearchSchemaValidationException schemaValidationFailed(final String message) {
        final ElasticsearchSchemaValidationException result = new ElasticsearchSchemaValidationException(String.format(getLoggingLocale(), schemaValidationFailed$str(), message));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String elasticsearchMappingRetrievalForValidationFailed = "HSEARCH400034: Could not retrieve the mappings from Elasticsearch for validation";
    protected String elasticsearchMappingRetrievalForValidationFailed$str() {
        return elasticsearchMappingRetrievalForValidationFailed;
    }
    @Override
    public final SearchException elasticsearchMappingRetrievalForValidationFailed(final Exception cause) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), elasticsearchMappingRetrievalForValidationFailed$str()), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String schemaUpdateFailed = "HSEARCH400035: Could not update mappings in index '%1$s'";
    protected String schemaUpdateFailed$str() {
        return schemaUpdateFailed;
    }
    @Override
    public final SearchException schemaUpdateFailed(final Object indexName, final Exception cause) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), schemaUpdateFailed$str(), indexName), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String fieldIsBothCompositeAndConcrete = "HSEARCH400036: Mapping conflict detected for field '%2$s' on entity '%1$s'. The current mapping would require the field to be mapped to both a composite field ('object' datatype) and a \"concrete\" field ('integer', 'date', etc.) holding a value, which Elasticsearch does not allow. If you're seeing this issue, you probably added both an @IndexedEmbedded annotation and a @Field (or similar) annotation on the same property: if that's the case, please set either @IndexedEmbedded.prefix or @Field.name to a custom value different from the default to resolve the conflict.";
    protected String fieldIsBothCompositeAndConcrete$str() {
        return fieldIsBothCompositeAndConcrete;
    }
    @Override
    public final SearchException fieldIsBothCompositeAndConcrete(final IndexedTypeIdentifier entityType, final String fieldPath) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), fieldIsBothCompositeAndConcrete$str(), new org.hibernate.search.util.logging.impl.IndexedTypeIdentifierFormatter(entityType), fieldPath));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForPeriod = "HSEARCH400037: The 'indexNullAs' property for Period fields must represent a date interval in ISO-8601 format (for instance P3Y2M1D for 3 years, 2 months and 1 day).";
    protected String invalidNullMarkerForPeriod$str() {
        return invalidNullMarkerForPeriod;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForPeriod(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForPeriod$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForDuration = "HSEARCH400038: The 'indexNullAs' property for Duration fields must represent a duration in ISO-8601 format (for instance P1DT2H3M4.007S for 1 day, 2 hours, 3 minutes, 4 seconds and 7 miliseconds).";
    protected String invalidNullMarkerForDuration$str() {
        return invalidNullMarkerForDuration;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForDuration(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForDuration$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForInstant = "HSEARCH400039: The 'indexNullAs' property for Instant fields must represent a date/time in ISO-8601 format (yyyy-MM-dd'T'HH:mm:ssZ[ZZZ]).";
    protected String invalidNullMarkerForInstant$str() {
        return invalidNullMarkerForInstant;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForInstant(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForInstant$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForLocalDateTime = "HSEARCH400040: The 'indexNullAs' property for LocalDateTime fields must represent a local date/time in ISO-8601 format (yyyy-MM-dd'T'HH:mm:ss).";
    protected String invalidNullMarkerForLocalDateTime$str() {
        return invalidNullMarkerForLocalDateTime;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForLocalDateTime(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForLocalDateTime$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForLocalDate = "HSEARCH400041: The 'indexNullAs' property for LocalDate fields must represent a local date in ISO-8601 format (yyyy-MM-dd).";
    protected String invalidNullMarkerForLocalDate$str() {
        return invalidNullMarkerForLocalDate;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForLocalDate(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForLocalDate$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForLocalTime = "HSEARCH400042: The 'indexNullAs' property for LocalTime fields must represent a local time in ISO-8601 format (HH:mm:ss).";
    protected String invalidNullMarkerForLocalTime$str() {
        return invalidNullMarkerForLocalTime;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForLocalTime(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForLocalTime$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForOffsetDateTime = "HSEARCH400043: The 'indexNullAs' property for OffsetDateTime fields must represent an offset date/time in ISO-8601 format (yyyy-MM-dd'T'HH:mm:ssZ).";
    protected String invalidNullMarkerForOffsetDateTime$str() {
        return invalidNullMarkerForOffsetDateTime;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForOffsetDateTime(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForOffsetDateTime$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForOffsetTime = "HSEARCH400044: The 'indexNullAs' property for OffsetTime fields must represent an offset time in ISO-8601 format (HH:mm:ssZ).";
    protected String invalidNullMarkerForOffsetTime$str() {
        return invalidNullMarkerForOffsetTime;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForOffsetTime(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForOffsetTime$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForZonedDateTime = "HSEARCH400045: The 'indexNullAs' property for ZonedDateTime fields must represent a zoned date/time in ISO-8601 format (yyyy-MM-dd'T'HH:mm:ss[ZZZ]).";
    protected String invalidNullMarkerForZonedDateTime$str() {
        return invalidNullMarkerForZonedDateTime;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForZonedDateTime(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForZonedDateTime$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForZonedTime = "HSEARCH400046: The 'indexNullAs' property for ZonedTime fields must represent a zoned time in ISO-8601 format (HH:mm:ss[ZZZ]).";
    protected String invalidNullMarkerForZonedTime$str() {
        return invalidNullMarkerForZonedTime;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForZonedTime(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForZonedTime$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForYear = "HSEARCH400047: The 'indexNullAs' property for Year fields must represent a year in ISO-8601 format (for instance 2014).";
    protected String invalidNullMarkerForYear$str() {
        return invalidNullMarkerForYear;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForYear(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForYear$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForYearMonth = "HSEARCH400048: The 'indexNullAs' property for YearMonth fields must represent a year/month in ISO-8601 format (yyyy-MM-dd).";
    protected String invalidNullMarkerForYearMonth$str() {
        return invalidNullMarkerForYearMonth;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForYearMonth(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForYearMonth$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForMonthDay = "HSEARCH400049: The 'indexNullAs' property for MonthDay fields must represent a month/day in ISO-8601 format (--MM-dd).";
    protected String invalidNullMarkerForMonthDay$str() {
        return invalidNullMarkerForMonthDay;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForMonthDay(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForMonthDay$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String indexMissing = "HSEARCH400050: The index '%1$s' does not exist in the Elasticsearch cluster.";
    protected String indexMissing$str() {
        return indexMissing;
    }
    @Override
    public final SearchException indexMissing(final Object indexName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), indexMissing$str(), indexName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedSearchAPIPayloadAttributes = "HSEARCH400051: The given payload contains unsupported attributes: %1$s. Only 'query' is supported.";
    protected String unsupportedSearchAPIPayloadAttributes$str() {
        return unsupportedSearchAPIPayloadAttributes;
    }
    @Override
    public final SearchException unsupportedSearchAPIPayloadAttributes(final java.util.List<String> invalidAttributes) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedSearchAPIPayloadAttributes$str(), invalidAttributes));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidSearchAPIPayload = "HSEARCH400052: The given payload is not a valid JSON object.";
    protected String invalidSearchAPIPayload$str() {
        return invalidSearchAPIPayload;
    }
    @Override
    public final SearchException invalidSearchAPIPayload(final Exception e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), invalidSearchAPIPayload$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void executingElasticsearchQuery(final String path, final java.util.Map<String, String> parameters, final String bodyParts) {
        super.log.logf(FQCN, DEBUG, null, executingElasticsearchQuery$str(), path, parameters, bodyParts);
    }
    private static final String executingElasticsearchQuery = "HSEARCH400053: Executing Elasticsearch query on '%s' with parameters '%s': <%s>";
    protected String executingElasticsearchQuery$str() {
        return executingElasticsearchQuery;
    }
    private static final String indexedEmbeddedPrefixBypass = "HSEARCH400054: Invalid field path detected for field '%2$s' on entity '%1$s': the field name is not prefixed with '%3$s' as it should. This probably means that the field was created with a custom field bridge which added fields with an arbitrary name, not taking the name passed as a parameter into account. This is not supported with the Elasticsearch indexing service: please only add suffixes to the name passed as a parameter to the various bridge methods and never ignore this name.";
    protected String indexedEmbeddedPrefixBypass$str() {
        return indexedEmbeddedPrefixBypass;
    }
    @Override
    public final SearchException indexedEmbeddedPrefixBypass(final IndexedTypeIdentifier entityType, final String fieldPath, final String expectedParent) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), indexedEmbeddedPrefixBypass$str(), new org.hibernate.search.util.logging.impl.IndexedTypeIdentifierFormatter(entityType), fieldPath, expectedParent));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String tokenizerNamingConflict = "HSEARCH400055: The same tokenizer name '%1$s' is assigned to multiple definitions. The tokenizer names must be unique. If you used the @TokenizerDef annotation and this name was automatically generated, you may override this name by using @TokenizerDef.name.";
    protected String tokenizerNamingConflict$str() {
        return tokenizerNamingConflict;
    }
    @Override
    public final SearchException tokenizerNamingConflict(final String remoteName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), tokenizerNamingConflict$str(), remoteName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String charFilterNamingConflict = "HSEARCH400056: The same char filter name '%1$s' is assigned to multiple definitions. The char filter names must be unique. If you used the @CharFilterDef annotation and this name was automatically generated, you may override this name by using @CharFilterDef.name.";
    protected String charFilterNamingConflict$str() {
        return charFilterNamingConflict;
    }
    @Override
    public final SearchException charFilterNamingConflict(final String remoteName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), charFilterNamingConflict$str(), remoteName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String tokenFilterNamingConflict = "HSEARCH400057: The same token filter name '%1$s' is assigned to multiple definitions. The token filter names must be unique. If you used the @TokenFilterDef annotation and this name was automatically generated, you may override this name by using @TokenFilterDef.name.";
    protected String tokenFilterNamingConflict$str() {
        return tokenFilterNamingConflict;
    }
    @Override
    public final SearchException tokenFilterNamingConflict(final String remoteName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), tokenFilterNamingConflict$str(), remoteName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedCharFilterFactory = "HSEARCH400058: The char filter factory '%1$s' is not supported with Elasticsearch. Please only use builtin Lucene factories that have a builtin equivalent in Elasticsearch.";
    protected String unsupportedCharFilterFactory$str() {
        return unsupportedCharFilterFactory;
    }
    @Override
    public final SearchException unsupportedCharFilterFactory(final Class<?> factoryType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedCharFilterFactory$str(), new org.hibernate.search.util.logging.impl.ClassFormatter(factoryType)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedTokenizerFactory = "HSEARCH400059: The tokenizer factory '%1$s' is not supported with Elasticsearch. Please only use builtin Lucene factories that have a builtin equivalent in Elasticsearch.";
    protected String unsupportedTokenizerFactory$str() {
        return unsupportedTokenizerFactory;
    }
    @Override
    public final SearchException unsupportedTokenizerFactory(final Class<?> factoryType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedTokenizerFactory$str(), new org.hibernate.search.util.logging.impl.ClassFormatter(factoryType)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedTokenFilterFactory = "HSEARCH400060: The token filter factory '%1$s' is not supported with Elasticsearch. Please only use builtin Lucene factories that have a builtin equivalent in Elasticsearch.";
    protected String unsupportedTokenFilterFactory$str() {
        return unsupportedTokenFilterFactory;
    }
    @Override
    public final SearchException unsupportedTokenFilterFactory(final Class<?> factoryType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedTokenFilterFactory$str(), new org.hibernate.search.util.logging.impl.ClassFormatter(factoryType)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedAnalysisFactoryParameter = "HSEARCH400061: The parameter '%2$s' is not supported for the factory '%1$s' with Elasticsearch.";
    protected String unsupportedAnalysisFactoryParameter$str() {
        return unsupportedAnalysisFactoryParameter;
    }
    @Override
    public final SearchException unsupportedAnalysisFactoryParameter(final Class<?> factoryType, final String parameter) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedAnalysisFactoryParameter$str(), new org.hibernate.search.util.logging.impl.ClassFormatter(factoryType), parameter));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedAnalysisFactoryTokenizerClassNameParameter = "HSEARCH400062: The parameter '%2$s' for the factory '%1$s' refers to the class '%3$s', which cannot be converted to a builtin Elasticsearch tokenizer type.";
    protected String unsupportedAnalysisFactoryTokenizerClassNameParameter$str() {
        return unsupportedAnalysisFactoryTokenizerClassNameParameter;
    }
    @Override
    public final SearchException unsupportedAnalysisFactoryTokenizerClassNameParameter(final Class<?> factoryClass, final String parameterName, final String tokenizerClass) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedAnalysisFactoryTokenizerClassNameParameter$str(), new org.hibernate.search.util.logging.impl.ClassFormatter(factoryClass), parameterName, tokenizerClass));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedAnalysisDefinitionParameterValue = "HSEARCH400063: The parameter '%2$s' for the factory '%1$s' has an unsupported value: '%3$s' is unsupported with Elasticsearch.";
    protected String unsupportedAnalysisDefinitionParameterValue$str() {
        return unsupportedAnalysisDefinitionParameterValue;
    }
    @Override
    public final SearchException unsupportedAnalysisDefinitionParameterValue(final Class<?> factoryClass, final String parameterName, final String parameterValue) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedAnalysisDefinitionParameterValue$str(), new org.hibernate.search.util.logging.impl.ClassFormatter(factoryClass), parameterName, parameterValue));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedAnalyzerImplementation = "HSEARCH400064: The analyzer implementation '%1$s' is not supported with Elasticsearch. Please only use builtin Lucene analyzers that have a builtin equivalent in Elasticsearch.";
    protected String unsupportedAnalyzerImplementation$str() {
        return unsupportedAnalyzerImplementation;
    }
    @Override
    public final SearchException unsupportedAnalyzerImplementation(final Class<?> luceneClass) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedAnalyzerImplementation$str(), new org.hibernate.search.util.logging.impl.ClassFormatter(luceneClass)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidAnalysisDefinitionJsonStringParameter = "HSEARCH400065: The parameter '%2$s' for the factory '%1$s' could not be parsed as a JSON string: %3$s";
    protected String invalidAnalysisDefinitionJsonStringParameter$str() {
        return invalidAnalysisDefinitionJsonStringParameter;
    }
    @Override
    public final SearchException invalidAnalysisDefinitionJsonStringParameter(final Class<?> factoryClass, final String parameterName, final String causeMessage, final Exception cause) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), invalidAnalysisDefinitionJsonStringParameter$str(), new org.hibernate.search.util.logging.impl.ClassFormatter(factoryClass), parameterName, causeMessage), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidAnalysisDefinitionJsonParameter = "HSEARCH400066: The parameter '%2$s' for the factory '%1$s' could not be parsed as JSON: %3$s";
    protected String invalidAnalysisDefinitionJsonParameter$str() {
        return invalidAnalysisDefinitionJsonParameter;
    }
    @Override
    public final SearchException invalidAnalysisDefinitionJsonParameter(final Class<?> factoryClass, final String parameterName, final String causeMessage, final Exception cause) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), invalidAnalysisDefinitionJsonParameter$str(), new org.hibernate.search.util.logging.impl.ClassFormatter(factoryClass), parameterName, causeMessage), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String elasticsearchSettingsUpdateFailed = "HSEARCH400067: Could not update settings for index '%1$s'";
    protected String elasticsearchSettingsUpdateFailed$str() {
        return elasticsearchSettingsUpdateFailed;
    }
    @Override
    public final SearchException elasticsearchSettingsUpdateFailed(final Object indexName, final Exception e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), elasticsearchSettingsUpdateFailed$str(), indexName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String elasticsearchIndexSettingsRetrievalForValidationFailed = "HSEARCH400068: Could not retrieve the index settings from Elasticsearch for validation";
    protected String elasticsearchIndexSettingsRetrievalForValidationFailed$str() {
        return elasticsearchIndexSettingsRetrievalForValidationFailed;
    }
    @Override
    public final SearchException elasticsearchIndexSettingsRetrievalForValidationFailed(final Exception cause) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), elasticsearchIndexSettingsRetrievalForValidationFailed$str()), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void closedIndex(final Object indexName) {
        super.log.logf(FQCN, INFO, null, closedIndex$str(), indexName);
    }
    private static final String closedIndex = "HSEARCH400069: Closed Elasticsearch index '%1$s' automatically.";
    protected String closedIndex$str() {
        return closedIndex;
    }
    @Override
    public final void openedIndex(final Object indexName) {
        super.log.logf(FQCN, INFO, null, openedIndex$str(), indexName);
    }
    private static final String openedIndex = "HSEARCH400070: Opened Elasticsearch index '%1$s' automatically.";
    protected String openedIndex$str() {
        return openedIndex;
    }
    @Override
    public final void failedToOpenIndex(final Object indexName) {
        super.log.logf(FQCN, ERROR, null, failedToOpenIndex$str(), indexName);
    }
    private static final String failedToOpenIndex = "HSEARCH400071: Failed to open Elasticsearch index '%1$s' ; see the stack trace below.";
    protected String failedToOpenIndex$str() {
        return failedToOpenIndex;
    }
    private static final String elasticsearch2RequestDeleteByQueryNotFound = "HSEARCH400072: DeleteByQuery request to Elasticsearch failed with 404 result code.\nPlease check that 1. you installed the delete-by-query plugin on your Elasticsearch nodes and 2. the targeted index exists";
    protected String elasticsearch2RequestDeleteByQueryNotFound$str() {
        return elasticsearch2RequestDeleteByQueryNotFound;
    }
    @Override
    public final SearchException elasticsearch2RequestDeleteByQueryNotFound() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), elasticsearch2RequestDeleteByQueryNotFound$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void usingPasswordOverHttp(final String serverUris) {
        super.log.logf(FQCN, WARN, null, usingPasswordOverHttp$str(), serverUris);
    }
    private static final String usingPasswordOverHttp = "HSEARCH400073: Hibernate Search will connect to Elasticsearch server '%1$s' with authentication over plain HTTP (not HTTPS). The password will be sent in clear text over the network.";
    protected String usingPasswordOverHttp$str() {
        return usingPasswordOverHttp;
    }
    private static final String analyzerNamingConflict = "HSEARCH400074: The same analyzer name '%1$s' is assigned to multiple definitions. The analyzer names must be unique.";
    protected String analyzerNamingConflict$str() {
        return analyzerNamingConflict;
    }
    @Override
    public final SearchException analyzerNamingConflict(final String remoteName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), analyzerNamingConflict$str(), remoteName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidElasticsearchAnalyzerDefinitionProvider = "HSEARCH400075: Property 'hibernate.search.elasticsearch.analysis_definition_provider' set to value '%1$s' is invalid. The value must be the fully-qualified name of a class with a public, no-arg constructor in your classpath. Also, the class must either implement ElasticsearchAnalyzerDefinitionProvider or expose a public, @Factory-annotated method returning a ElasticsearchAnalyzerDefinitionProvider.";
    protected String invalidElasticsearchAnalyzerDefinitionProvider$str() {
        return invalidElasticsearchAnalyzerDefinitionProvider;
    }
    @Override
    public final SearchException invalidElasticsearchAnalyzerDefinitionProvider(final String providerClassName, final Exception e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), invalidElasticsearchAnalyzerDefinitionProvider$str(), providerClassName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidElasticsearchAnalyzerDefinition = "HSEARCH400076: Invalid analyzer definition for name '%1$s'. Analyzer definitions must at least define the tokenizer.";
    protected String invalidElasticsearchAnalyzerDefinition$str() {
        return invalidElasticsearchAnalyzerDefinition;
    }
    @Override
    public final SearchException invalidElasticsearchAnalyzerDefinition(final String name) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), invalidElasticsearchAnalyzerDefinition$str(), name));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidElasticsearchTokenizerDefinition = "HSEARCH400077: Invalid tokenizer definition for name '%1$s'. Tokenizer definitions must at least define the tokenizer type.";
    protected String invalidElasticsearchTokenizerDefinition$str() {
        return invalidElasticsearchTokenizerDefinition;
    }
    @Override
    public final SearchException invalidElasticsearchTokenizerDefinition(final String name) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), invalidElasticsearchTokenizerDefinition$str(), name));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidElasticsearchCharFilterDefinition = "HSEARCH400078: Invalid char filter definition for name '%1$s'. Char filter definitions must at least define the char filter type.";
    protected String invalidElasticsearchCharFilterDefinition$str() {
        return invalidElasticsearchCharFilterDefinition;
    }
    @Override
    public final SearchException invalidElasticsearchCharFilterDefinition(final String name) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), invalidElasticsearchCharFilterDefinition$str(), name));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidElasticsearchTokenFilterDefinition = "HSEARCH400079: Invalid token filter definition for name '%1$s'. Token filter definitions must at least define the token filter type.";
    protected String invalidElasticsearchTokenFilterDefinition$str() {
        return invalidElasticsearchTokenFilterDefinition;
    }
    @Override
    public final SearchException invalidElasticsearchTokenFilterDefinition(final String name) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), invalidElasticsearchTokenFilterDefinition$str(), name));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String failedToDetectElasticsearchVersion = "HSEARCH400080: Failed to detect the Elasticsearch version running on the cluster.";
    protected String failedToDetectElasticsearchVersion$str() {
        return failedToDetectElasticsearchVersion;
    }
    @Override
    public final SearchException failedToDetectElasticsearchVersion(final Exception e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), failedToDetectElasticsearchVersion$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedElasticsearchVersion = "HSEARCH400081: An unsupported Elasticsearch version runs on the Elasticsearch cluster: '%s'. Please refer to the documentation to know which versions are supported.";
    protected String unsupportedElasticsearchVersion$str() {
        return unsupportedElasticsearchVersion;
    }
    @Override
    public final SearchException unsupportedElasticsearchVersion(final String name) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedElasticsearchVersion$str(), name));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void executedRequest(final String method, final String path, final java.util.Map<String, String> getParameters, final long timeInMs, final int responseStatusCode, final String responseStatusMessage) {
        super.log.logf(FQCN, DEBUG, null, executedRequest6$str(), method, path, getParameters, timeInMs, responseStatusCode, responseStatusMessage);
    }
    private static final String executedRequest6 = "HSEARCH400082: Executed Elasticsearch HTTP %s request to path '%s' with query parameters %s in %dms. Response had status %d '%s'.";
    protected String executedRequest6$str() {
        return executedRequest6;
    }
    private static final String unableToOverrideQueryAnalyzerWithMoreThanOneAnalyzersForSimpleQueryStringQueries = "HSEARCH400083: For simple query string queries, Elasticsearch does not support overriding fields with more than one different analyzers: %1$s.";
    protected String unableToOverrideQueryAnalyzerWithMoreThanOneAnalyzersForSimpleQueryStringQueries$str() {
        return unableToOverrideQueryAnalyzerWithMoreThanOneAnalyzersForSimpleQueryStringQueries;
    }
    @Override
    public final SearchException unableToOverrideQueryAnalyzerWithMoreThanOneAnalyzersForSimpleQueryStringQueries(final java.util.Collection<String> analyzers) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToOverrideQueryAnalyzerWithMoreThanOneAnalyzersForSimpleQueryStringQueries$str(), analyzers));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidAnalysisFactoryParameter = "HSEARCH400084: The parameter '%2$s' must have value '%3$s' for the factory '%1$s' with Elasticsearch. Current value '%4$s' is invalid.";
    protected String invalidAnalysisFactoryParameter$str() {
        return invalidAnalysisFactoryParameter;
    }
    @Override
    public final SearchException invalidAnalysisFactoryParameter(final Class<?> factoryType, final String parameter, final String expectedValue, final String actualValue) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), invalidAnalysisFactoryParameter$str(), new org.hibernate.search.util.logging.impl.ClassFormatter(factoryType), parameter, expectedValue, actualValue));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void unexpectedElasticsearchVersion(final String name) {
        super.log.logf(FQCN, WARN, null, unexpectedElasticsearchVersion$str(), name);
    }
    private static final String unexpectedElasticsearchVersion = "HSEARCH400085: Hibernate Search may not work correctly, because an unknown Elasticsearch version runs on the Elasticsearch cluster: '%s'.";
    protected String unexpectedElasticsearchVersion$str() {
        return unexpectedElasticsearchVersion;
    }
    private static final String normalizerNamingConflict = "HSEARCH400086: The same normalizer name '%1$s' is assigned to multiple definitions. The analyzer names must be unique.";
    protected String normalizerNamingConflict$str() {
        return normalizerNamingConflict;
    }
    @Override
    public final SearchException normalizerNamingConflict(final String remoteName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), normalizerNamingConflict$str(), remoteName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String analyzerNormalizerNamingConflict = "HSEARCH400087: The same name '%1$s' is assigned to a normalizer definition and an analyzer definition. This is not possible on Elasticsearch 5.1 and below, since normalizers are translated to analyzers under the hood.";
    protected String analyzerNormalizerNamingConflict$str() {
        return analyzerNormalizerNamingConflict;
    }
    @Override
    public final SearchException analyzerNormalizerNamingConflict(final String remoteName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), analyzerNormalizerNamingConflict$str(), remoteName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotUseNormalizerImpl = "HSEARCH400088: You cannot use @Normalizer(impl = \"%1$s\") on entities mapped to Elasticsearch: there are no built-in normalizers in Elasticsearch. Use @Normalizer(definition = \"...\") instead.";
    protected String cannotUseNormalizerImpl$str() {
        return cannotUseNormalizerImpl;
    }
    @Override
    public final SearchException cannotUseNormalizerImpl(final Class<?> analyzerType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotUseNormalizerImpl$str(), new org.hibernate.search.util.logging.impl.ClassFormatter(analyzerType)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String failedToParseElasticsearchResponse = "HSEARCH400089: Failed to parse Elasticsearch response. Status code was '%1$d', status phrase was '%2$s'.";
    protected String failedToParseElasticsearchResponse$str() {
        return failedToParseElasticsearchResponse;
    }
    @Override
    public final SearchException failedToParseElasticsearchResponse(final int statusCode, final String statusPhrase, final Exception cause) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), failedToParseElasticsearchResponse$str(), statusCode, statusPhrase), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String elasticsearchResponseIndicatesFailure = "HSEARCH400090: Elasticsearch response indicates a failure.";
    protected String elasticsearchResponseIndicatesFailure$str() {
        return elasticsearchResponseIndicatesFailure;
    }
    @Override
    public final SearchException elasticsearchResponseIndicatesFailure() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), elasticsearchResponseIndicatesFailure$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String threadInterruptedWhileSubmittingChangeset = "HSEARCH400091: The thread was interrupted while a changeset was being submitted to '%1$s'. The changeset has been discarded.";
    protected String threadInterruptedWhileSubmittingChangeset$str() {
        return threadInterruptedWhileSubmittingChangeset;
    }
    @Override
    public final SearchException threadInterruptedWhileSubmittingChangeset(final String orchestratorName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), threadInterruptedWhileSubmittingChangeset$str(), orchestratorName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String orchestratorShutDownBeforeSubmittingChangeset = "HSEARCH400092: A changeset was submitted after Hibernate Search shutdown was requested to '%1$s'. The changeset has been discarded.";
    protected String orchestratorShutDownBeforeSubmittingChangeset$str() {
        return orchestratorShutDownBeforeSubmittingChangeset;
    }
    @Override
    public final SearchException orchestratorShutDownBeforeSubmittingChangeset(final String orchestratorName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), orchestratorShutDownBeforeSubmittingChangeset$str(), orchestratorName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void executedRequest(final String method, final String path, final java.util.Map<String, String> getParameters, final long timeInMs, final int responseStatusCode, final String responseStatusMessage, final String requestBodyParts, final String responseBody) {
        super.log.logf(FQCN, TRACE, null, executedRequest8$str(), method, path, getParameters, timeInMs, responseStatusCode, responseStatusMessage, requestBodyParts, responseBody);
    }
    private static final String executedRequest8 = "HSEARCH400093: Executed Elasticsearch HTTP %s request to path '%s' with query parameters %s in %dms. Response had status %d '%s'. Request body: <%s>. Response body: <%s>";
    protected String executedRequest8$str() {
        return executedRequest8;
    }
    private static final String clientUnwrappingWithUnknownType = "HSEARCH400094: Attempt to unwrap the Elasticsearch low-level client to %1$s, but the client can only be unwrapped to %2$s.";
    protected String clientUnwrappingWithUnknownType$str() {
        return clientUnwrappingWithUnknownType;
    }
    @Override
    public final SearchException clientUnwrappingWithUnknownType(final Class<?> requestedClass, final Class<?> actualClass) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), clientUnwrappingWithUnknownType$str(), requestedClass, actualClass));
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
