package org.hibernate.search.backend.elasticsearch.logging.impl;

import java.io.Serializable;
import javax.annotation.Generated;
import org.hibernate.search.util.AssertionFailure;
import org.jboss.logging.DelegatingBasicLogger;
import org.hibernate.search.util.SearchException;
import org.hibernate.search.backend.elasticsearch.index.impl.ElasticsearchIndexManager;
import org.hibernate.search.backend.elasticsearch.util.impl.URLEncodedString;
import java.lang.String;
import org.hibernate.search.search.SearchPredicate;
import org.jboss.logging.Logger;
import org.hibernate.search.backend.index.spi.IndexSearchTargetBuilder;
import java.lang.Exception;
import org.hibernate.search.backend.elasticsearch.document.model.impl.ElasticsearchIndexSchemaFieldNode;
import org.jboss.logging.BasicLogger;
import java.util.Collection;
import org.hibernate.search.backend.elasticsearch.document.model.impl.esnative.DataType;
import java.lang.Object;
import java.lang.Class;
import java.util.Arrays;
import java.util.Map;
import org.hibernate.search.search.SearchSort;
import org.hibernate.search.backend.spi.BackendImplementor;

/**
 * Warning this class consists of generated code.
 */
@Generated(value = "org.jboss.logging.processor.generator.model.MessageLoggerImplementor", date = "2018-06-01T10:38:55+0200")
public class Log_$logger extends DelegatingBasicLogger implements Log,BasicLogger,Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FQCN = Log_$logger.class.getName();
    public Log_$logger(final Logger log) {
        super(log);
    }
    @Override
    public final void usingPasswordOverHttp(final String serverUris) {
        super.log.logf(FQCN, org.jboss.logging.Logger.Level.WARN, null, usingPasswordOverHttp$str(), serverUris);
    }
    private static final String usingPasswordOverHttp = "HSEARCH-ES000073: Hibernate Search will connect to Elasticsearch server '%1$s' with authentication over plain HTTP (not HTTPS). The password will be sent in clear text over the network.";
    protected String usingPasswordOverHttp$str() {
        return usingPasswordOverHttp;
    }
    @Override
    public final void executedRequest(final String method, final String path, final Map<String, String> getParameters, final long timeInMs, final int responseStatusCode, final String responseStatusMessage) {
        super.log.logf(FQCN, org.jboss.logging.Logger.Level.DEBUG, null, executedRequest6$str(), method, path, getParameters, timeInMs, responseStatusCode, responseStatusMessage);
    }
    private static final String executedRequest6 = "HSEARCH-ES000082: Executed Elasticsearch HTTP %s request to path '%s' with query parameters %s in %dms. Response had status %d '%s'.";
    protected String executedRequest6$str() {
        return executedRequest6;
    }
    private static final String failedToParseElasticsearchResponse = "HSEARCH-ES000089: Failed to parse Elasticsearch response. Status code was '%1$d', status phrase was '%2$s'.";
    protected String failedToParseElasticsearchResponse$str() {
        return failedToParseElasticsearchResponse;
    }
    @Override
    public final SearchException failedToParseElasticsearchResponse(final int statusCode, final String statusPhrase, final Exception cause) {
        final SearchException result = new SearchException(String.format(failedToParseElasticsearchResponse$str(), statusCode, statusPhrase), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void executedRequest(final String method, final String path, final Map<String, String> getParameters, final long timeInMs, final int responseStatusCode, final String responseStatusMessage, final String requestBodyParts, final String responseBody) {
        super.log.logf(FQCN, org.jboss.logging.Logger.Level.TRACE, null, executedRequest8$str(), method, path, getParameters, timeInMs, responseStatusCode, responseStatusMessage, requestBodyParts, responseBody);
    }
    private static final String executedRequest8 = "HSEARCH-ES000093: Executed Elasticsearch HTTP %s request to path '%s' with query parameters %s in %dms. Response had status %d '%s'. Request body: <%s>. Response body: <%s>";
    protected String executedRequest8$str() {
        return executedRequest8;
    }
    private static final String cannotMixElasticsearchSearchTargetWithOtherType = "HSEARCH-ES000502: A search query cannot target both an Elasticsearch index and other types of index. First target was: '%1$s', other target was: '%2$s'";
    protected String cannotMixElasticsearchSearchTargetWithOtherType$str() {
        return cannotMixElasticsearchSearchTargetWithOtherType;
    }
    @Override
    public final SearchException cannotMixElasticsearchSearchTargetWithOtherType(final IndexSearchTargetBuilder firstTarget, final ElasticsearchIndexManager otherTarget) {
        final SearchException result = new SearchException(String.format(cannotMixElasticsearchSearchTargetWithOtherType$str(), firstTarget, otherTarget));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotMixElasticsearchSearchTargetWithOtherBackend = "HSEARCH-ES000503: A search query cannot target multiple Elasticsearch backends. First target was: '%1$s', other target was: '%2$s'";
    protected String cannotMixElasticsearchSearchTargetWithOtherBackend$str() {
        return cannotMixElasticsearchSearchTargetWithOtherBackend;
    }
    @Override
    public final SearchException cannotMixElasticsearchSearchTargetWithOtherBackend(final IndexSearchTargetBuilder firstTarget, final ElasticsearchIndexManager otherTarget) {
        final SearchException result = new SearchException(String.format(cannotMixElasticsearchSearchTargetWithOtherBackend$str(), firstTarget, otherTarget));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownFieldForSearch = "HSEARCH-ES000504: Unknown field '%1$s' in indexes %2$s.";
    protected String unknownFieldForSearch$str() {
        return unknownFieldForSearch;
    }
    @Override
    public final SearchException unknownFieldForSearch(final String absoluteFieldPath, final Collection<URLEncodedString> indexNames) {
        final SearchException result = new SearchException(String.format(unknownFieldForSearch$str(), absoluteFieldPath, indexNames));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String conflictingFieldTypesForSearch = "HSEARCH-ES000505: Multiple conflicting types for field '%1$s': '%2$s' in index '%3$s', but '%4$s' in index '%5$s'.";
    protected String conflictingFieldTypesForSearch$str() {
        return conflictingFieldTypesForSearch;
    }
    @Override
    public final SearchException conflictingFieldTypesForSearch(final String absoluteFieldPath, final ElasticsearchIndexSchemaFieldNode schemaNode1, final URLEncodedString indexName1, final ElasticsearchIndexSchemaFieldNode schemaNode2, final URLEncodedString indexName2) {
        final SearchException result = new SearchException(String.format(conflictingFieldTypesForSearch$str(), absoluteFieldPath, schemaNode1, indexName1, schemaNode2, indexName2));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String elasticsearchExtensionOnUnknownType = "HSEARCH-ES000506: The Elasticsearch extension can only be applied to objects derived from the Elasticsearch backend. Was applied to '%1$s' instead.";
    protected String elasticsearchExtensionOnUnknownType$str() {
        return elasticsearchExtensionOnUnknownType;
    }
    @Override
    public final SearchException elasticsearchExtensionOnUnknownType(final Object context) {
        final SearchException result = new SearchException(String.format(elasticsearchExtensionOnUnknownType$str(), context));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownProjectionForSearch = "HSEARCH-ES000507: Unknown projections %1$s in indexes %2$s.";
    protected String unknownProjectionForSearch$str() {
        return unknownProjectionForSearch;
    }
    @Override
    public final SearchException unknownProjectionForSearch(final Collection<String> projections, final Collection<URLEncodedString> indexNames) {
        final SearchException result = new SearchException(String.format(unknownProjectionForSearch$str(), projections, indexNames));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotMixElasticsearchSearchQueryWithOtherPredicates = "HSEARCH-ES000508: An Elasticsearch query cannot include search predicates built using a non-Elasticsearch search target. Given predicate was: '%1$s'";
    protected String cannotMixElasticsearchSearchQueryWithOtherPredicates$str() {
        return cannotMixElasticsearchSearchQueryWithOtherPredicates;
    }
    @Override
    public final SearchException cannotMixElasticsearchSearchQueryWithOtherPredicates(final SearchPredicate predicate) {
        final SearchException result = new SearchException(String.format(cannotMixElasticsearchSearchQueryWithOtherPredicates$str(), predicate));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String nonObjectFieldForNestedQuery = "HSEARCH-ES000509: Field '%2$s' is not an object field in index '%1$s'.";
    protected String nonObjectFieldForNestedQuery$str() {
        return nonObjectFieldForNestedQuery;
    }
    @Override
    public final SearchException nonObjectFieldForNestedQuery(final URLEncodedString indexName, final String absoluteFieldPath) {
        final SearchException result = new SearchException(String.format(nonObjectFieldForNestedQuery$str(), indexName, absoluteFieldPath));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String nonNestedFieldForNestedQuery = "HSEARCH-ES000510: Object field '%2$s' is not stored as nested in index '%1$s'.";
    protected String nonNestedFieldForNestedQuery$str() {
        return nonNestedFieldForNestedQuery;
    }
    @Override
    public final SearchException nonNestedFieldForNestedQuery(final URLEncodedString indexName, final String absoluteFieldPath) {
        final SearchException result = new SearchException(String.format(nonNestedFieldForNestedQuery$str(), indexName, absoluteFieldPath));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotMixElasticsearchSearchSortWithOtherSorts = "HSEARCH-ES000511: An Elasticsearch query cannot include search sorts built using a non-Elasticsearch search target. Given sort was: '%1$s'";
    protected String cannotMixElasticsearchSearchSortWithOtherSorts$str() {
        return cannotMixElasticsearchSearchSortWithOtherSorts;
    }
    @Override
    public final SearchException cannotMixElasticsearchSearchSortWithOtherSorts(final SearchSort sort) {
        final SearchException result = new SearchException(String.format(cannotMixElasticsearchSearchSortWithOtherSorts$str(), sort));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotUseAnalyzerOnFieldType = "HSEARCH-ES000512: An analyzer was set on field '%1$s' of type '%2$s', but fields of this type cannot be analyzed.";
    protected String cannotUseAnalyzerOnFieldType$str() {
        return cannotUseAnalyzerOnFieldType;
    }
    @Override
    public final SearchException cannotUseAnalyzerOnFieldType(final String absoluteFieldPath, final DataType fieldType) {
        final SearchException result = new SearchException(String.format(cannotUseAnalyzerOnFieldType$str(), absoluteFieldPath, fieldType));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotUseNormalizerOnFieldType = "HSEARCH-ES000513: A normalizer was set on field '%1$s' of type '%2$s', but fields of this type cannot be analyzed.";
    protected String cannotUseNormalizerOnFieldType$str() {
        return cannotUseNormalizerOnFieldType;
    }
    @Override
    public final SearchException cannotUseNormalizerOnFieldType(final String absoluteFieldPath, final DataType fieldType) {
        final SearchException result = new SearchException(String.format(cannotUseNormalizerOnFieldType$str(), absoluteFieldPath, fieldType));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String multiTenancyRequiredButNotSupportedByBackend = "HSEARCH-ES000514: Index '%2$s' requires multi-tenancy but backend '%1$s' does not support it in its current configuration.";
    protected String multiTenancyRequiredButNotSupportedByBackend$str() {
        return multiTenancyRequiredButNotSupportedByBackend;
    }
    @Override
    public final SearchException multiTenancyRequiredButNotSupportedByBackend(final String backendName, final String indexName) {
        final SearchException result = new SearchException(String.format(multiTenancyRequiredButNotSupportedByBackend$str(), backendName, indexName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownMultiTenancyStrategyConfiguration = "HSEARCH-ES000515: Unknown multi-tenancy strategy '%1$s'.";
    protected String unknownMultiTenancyStrategyConfiguration$str() {
        return unknownMultiTenancyStrategyConfiguration;
    }
    @Override
    public final SearchException unknownMultiTenancyStrategyConfiguration(final String multiTenancyStrategy) {
        final SearchException result = new SearchException(String.format(unknownMultiTenancyStrategyConfiguration$str(), multiTenancyStrategy));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String tenantIdProvidedButMultiTenancyDisabled = "HSEARCH-ES000516: Tenant identifier '%2$s' is provided, but multi-tenancy is disabled for the backend '%1$s'.";
    protected String tenantIdProvidedButMultiTenancyDisabled$str() {
        return tenantIdProvidedButMultiTenancyDisabled;
    }
    @Override
    public final SearchException tenantIdProvidedButMultiTenancyDisabled(final BackendImplementor<? extends Object> backend, final String tenantId) {
        final SearchException result = new SearchException(String.format(tenantIdProvidedButMultiTenancyDisabled$str(), backend, tenantId));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String multiTenancyEnabledButNoTenantIdProvided = "HSEARCH-ES000517: Backend '%1$s' has multi-tenancy enabled, but no tenant identifier is provided.";
    protected String multiTenancyEnabledButNoTenantIdProvided$str() {
        return multiTenancyEnabledButNoTenantIdProvided;
    }
    @Override
    public final SearchException multiTenancyEnabledButNoTenantIdProvided(final BackendImplementor<? extends Object> backend) {
        final SearchException result = new SearchException(String.format(multiTenancyEnabledButNoTenantIdProvided$str(), backend));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String clientUnwrappingWithUnkownType = "HSEARCH-ES000518: Attempt to unwrap the Elasticsearch low-level client to %1$s, but the client can only be unwrapped to %2$s.";
    protected String clientUnwrappingWithUnkownType$str() {
        return clientUnwrappingWithUnkownType;
    }
    @Override
    public final SearchException clientUnwrappingWithUnkownType(final Class<? extends Object> requestedClass, final Class<? extends Object> actualClass) {
        final SearchException result = new SearchException(String.format(clientUnwrappingWithUnkownType$str(), requestedClass, actualClass));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String backendUnwrappingWithUnknownType = "HSEARCH-ES000519: Attempt to unwrap an Elasticsearch backend to %1$s, but this backend can only be unwrapped to %2$s.";
    protected String backendUnwrappingWithUnknownType$str() {
        return backendUnwrappingWithUnknownType;
    }
    @Override
    public final SearchException backendUnwrappingWithUnknownType(final Class<? extends Object> requestedClass, final Class<? extends Object> actualClass) {
        final SearchException result = new SearchException(String.format(backendUnwrappingWithUnknownType$str(), requestedClass, actualClass));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String indexSchemaNodeNameConflict = "HSEARCH-ES000520: The index schema node '%2$s' was added twice at path '%1$s'. Multiple bridges may be trying to access the same index field,  or two indexed-embeddeds may have prefixes that lead to conflicting field names, or you may have declared multiple conflicting mappings. In any case, there is something wrong with your mapping and you should fix it.";
    protected String indexSchemaNodeNameConflict$str() {
        return indexSchemaNodeNameConflict;
    }
    @Override
    public final SearchException indexSchemaNodeNameConflict(final String absolutePath, final String name) {
        final SearchException result = new SearchException(String.format(indexSchemaNodeNameConflict$str(), absolutePath, name));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String rangePredicatesNotSupportedByGeoPoint = "HSEARCH-ES000523: Range predicates are not supported by the GeoPoint type of field '%1$s', use spatial predicates instead.";
    protected String rangePredicatesNotSupportedByGeoPoint$str() {
        return rangePredicatesNotSupportedByGeoPoint;
    }
    @Override
    public final SearchException rangePredicatesNotSupportedByGeoPoint(final String absoluteFieldPath) {
        final SearchException result = new SearchException(String.format(rangePredicatesNotSupportedByGeoPoint$str(), absoluteFieldPath));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String matchPredicatesNotSupportedByGeoPoint = "HSEARCH-ES000524: Match predicates are not supported by the GeoPoint type of field '%1$s', use spatial predicates instead.";
    protected String matchPredicatesNotSupportedByGeoPoint$str() {
        return matchPredicatesNotSupportedByGeoPoint;
    }
    @Override
    public final SearchException matchPredicatesNotSupportedByGeoPoint(final String absoluteFieldPath) {
        final SearchException result = new SearchException(String.format(matchPredicatesNotSupportedByGeoPoint$str(), absoluteFieldPath));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidParentDocumentObjectState = "HSEARCH-ES000525: Invalid parent object for this field accessor; expected path '%1$s', got '%2$s'.";
    protected String invalidParentDocumentObjectState$str() {
        return invalidParentDocumentObjectState;
    }
    @Override
    public final SearchException invalidParentDocumentObjectState(final String expectedPath, final String actualPath) {
        final SearchException result = new SearchException(String.format(invalidParentDocumentObjectState$str(), expectedPath, actualPath));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String elasticsearchResponseMissingData = "HSEARCH-ES000526: Expected data was missing in the Elasticsearch response.";
    protected String elasticsearchResponseMissingData$str() {
        return elasticsearchResponseMissingData;
    }
    @Override
    public final AssertionFailure elasticsearchResponseMissingData() {
        final AssertionFailure result = new AssertionFailure(String.format(elasticsearchResponseMissingData$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String spatialPredicatesNotSupportedByFieldType = "HSEARCH-ES000527: Spatial predicates are not supported by the type of field '%1$s'.";
    protected String spatialPredicatesNotSupportedByFieldType$str() {
        return spatialPredicatesNotSupportedByFieldType;
    }
    @Override
    public final SearchException spatialPredicatesNotSupportedByFieldType(final String absoluteFieldPath) {
        final SearchException result = new SearchException(String.format(spatialPredicatesNotSupportedByFieldType$str(), absoluteFieldPath));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String distanceOperationsNotSupportedByFieldType = "HSEARCH-ES000528: Distance related operations are not supported by the type of field '%1$s'.";
    protected String distanceOperationsNotSupportedByFieldType$str() {
        return distanceOperationsNotSupportedByFieldType;
    }
    @Override
    public final SearchException distanceOperationsNotSupportedByFieldType(final String absoluteFieldPath) {
        final SearchException result = new SearchException(String.format(distanceOperationsNotSupportedByFieldType$str(), absoluteFieldPath));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
}
