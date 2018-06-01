package org.hibernate.search.backend.lucene.logging.impl;

import java.nio.file.Path;
import java.io.Serializable;
import javax.annotation.Generated;
import org.apache.lucene.search.Query;
import org.jboss.logging.DelegatingBasicLogger;
import org.hibernate.search.backend.lucene.index.impl.LuceneIndexManager;
import org.hibernate.search.util.SearchException;
import java.lang.String;
import java.io.IOException;
import org.hibernate.search.search.SearchPredicate;
import org.jboss.logging.Logger;
import org.hibernate.search.backend.index.spi.IndexSearchTargetBuilder;
import java.lang.Exception;
import org.jboss.logging.BasicLogger;
import java.util.Collection;
import java.lang.Object;
import java.lang.Class;
import java.util.Arrays;
import org.hibernate.search.backend.lucene.document.model.impl.LuceneIndexSchemaFieldNode;
import org.hibernate.search.search.SearchSort;
import org.hibernate.search.backend.spi.BackendImplementor;

/**
 * Warning this class consists of generated code.
 */
@Generated(value = "org.jboss.logging.processor.generator.model.MessageLoggerImplementor", date = "2018-06-01T10:38:57+0200")
public class Log_$logger extends DelegatingBasicLogger implements Log,BasicLogger,Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FQCN = Log_$logger.class.getName();
    public Log_$logger(final Logger log) {
        super(log);
    }
    @Override
    public final void couldNotCloseResource(final Exception e) {
        super.log.logf(FQCN, org.jboss.logging.Logger.Level.WARN, e, couldNotCloseResource$str());
    }
    private static final String couldNotCloseResource = "HSEARCH-LUCENE000035: Could not close resource.";
    protected String couldNotCloseResource$str() {
        return couldNotCloseResource;
    }
    @Override
    public final void unableToCloseIndexReader(final BackendImplementor<? extends Object> backend, final String indexName, final Exception e) {
        super.log.logf(FQCN, org.jboss.logging.Logger.Level.WARN, e, unableToCloseIndexReader$str(), backend, indexName);
    }
    private static final String unableToCloseIndexReader = "HSEARCH-LUCENE000055: Unable to close the index reader for index '%2$s' of backend '%1$s'.";
    protected String unableToCloseIndexReader$str() {
        return unableToCloseIndexReader;
    }
    private static final String ioExceptionOnMultiReaderRefresh = "HSEARCH-LUCENE000284: An IOException happened while opening multiple indexes %1$s.";
    protected String ioExceptionOnMultiReaderRefresh$str() {
        return ioExceptionOnMultiReaderRefresh;
    }
    @Override
    public final SearchException ioExceptionOnMultiReaderRefresh(final Collection<String> indexNames, final IOException e) {
        final SearchException result = new SearchException(String.format(ioExceptionOnMultiReaderRefresh$str(), indexNames), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String couldNotNormalizeField = "HSEARCH-LUCENE000320: Could not normalize value for field '%1$s'.";
    protected String couldNotNormalizeField$str() {
        return couldNotNormalizeField;
    }
    @Override
    public final SearchException couldNotNormalizeField(final String absoluteFieldPath, final Exception cause) {
        final SearchException result = new SearchException(String.format(couldNotNormalizeField$str(), absoluteFieldPath), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void multipleTermsDetectedDuringNormalization(final String absoluteFieldPath) {
        super.log.logf(FQCN, org.jboss.logging.Logger.Level.WARN, null, multipleTermsDetectedDuringNormalization$str(), absoluteFieldPath);
    }
    private static final String multipleTermsDetectedDuringNormalization = "HSEARCH-LUCENE000321: The analysis of field '%1$s' produced multiple tokens. Tokenization or term generation (synonyms) should not be used on sortable fields or range queries. Only the first token will be considered.";
    protected String multipleTermsDetectedDuringNormalization$str() {
        return multipleTermsDetectedDuringNormalization;
    }
    private static final String unknownFieldForSearch = "HSEARCH-LUCENE000500: Unknown field '%1$s' in indexes %2$s.";
    protected String unknownFieldForSearch$str() {
        return unknownFieldForSearch;
    }
    @Override
    public final SearchException unknownFieldForSearch(final String absoluteFieldPath, final Collection<String> indexNames) {
        final SearchException result = new SearchException(String.format(unknownFieldForSearch$str(), absoluteFieldPath, indexNames));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String localDirectoryBackendRootDirectoryNotWritableDirectory = "HSEARCH-LUCENE000501: Root directory '%2$s' of backend '%1$s' exists but is not a writable directory.";
    protected String localDirectoryBackendRootDirectoryNotWritableDirectory$str() {
        return localDirectoryBackendRootDirectoryNotWritableDirectory;
    }
    @Override
    public final SearchException localDirectoryBackendRootDirectoryNotWritableDirectory(final String backendName, final Path rootDirectory) {
        final SearchException result = new SearchException(String.format(localDirectoryBackendRootDirectoryNotWritableDirectory$str(), backendName, rootDirectory));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToCreateRootDirectoryForLocalDirectoryBackend = "HSEARCH-LUCENE000502: Unable to create root directory '%2$s' for backend '%1$s'.";
    protected String unableToCreateRootDirectoryForLocalDirectoryBackend$str() {
        return unableToCreateRootDirectoryForLocalDirectoryBackend;
    }
    @Override
    public final SearchException unableToCreateRootDirectoryForLocalDirectoryBackend(final String backendName, final Path rootDirectory, final Exception e) {
        final SearchException result = new SearchException(String.format(unableToCreateRootDirectoryForLocalDirectoryBackend$str(), backendName, rootDirectory), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String undefinedLuceneDirectoryProvider = "HSEARCH-LUCENE000503: Undefined Lucene directory provider for backend '%1$s'.";
    protected String undefinedLuceneDirectoryProvider$str() {
        return undefinedLuceneDirectoryProvider;
    }
    @Override
    public final SearchException undefinedLuceneDirectoryProvider(final String backendName) {
        final SearchException result = new SearchException(String.format(undefinedLuceneDirectoryProvider$str(), backendName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unrecognizedLuceneDirectoryProvider = "HSEARCH-LUCENE000504: Unrecognized Lucene directory provider '%2$s' for backend '%1$s'.";
    protected String unrecognizedLuceneDirectoryProvider$str() {
        return unrecognizedLuceneDirectoryProvider;
    }
    @Override
    public final SearchException unrecognizedLuceneDirectoryProvider(final String backendName, final String backendType) {
        final SearchException result = new SearchException(String.format(unrecognizedLuceneDirectoryProvider$str(), backendName, backendType));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String luceneExtensionOnUnknownType = "HSEARCH-LUCENE000505: The Lucene extension can only be applied to objects derived from the Lucene backend. Was applied to '%1$s' instead.";
    protected String luceneExtensionOnUnknownType$str() {
        return luceneExtensionOnUnknownType;
    }
    @Override
    public final SearchException luceneExtensionOnUnknownType(final Object context) {
        final SearchException result = new SearchException(String.format(luceneExtensionOnUnknownType$str(), context));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotUseAnalyzerOnFieldType = "HSEARCH-LUCENE000506: An analyzer was set on field '%1$s', but fields of this type cannot be analyzed.";
    protected String cannotUseAnalyzerOnFieldType$str() {
        return cannotUseAnalyzerOnFieldType;
    }
    @Override
    public final SearchException cannotUseAnalyzerOnFieldType(final String relativeFieldName) {
        final SearchException result = new SearchException(String.format(cannotUseAnalyzerOnFieldType$str(), relativeFieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotUseNormalizerOnFieldType = "HSEARCH-LUCENE000507: A normalizer was set on field '%1$s', but fields of this type cannot be analyzed.";
    protected String cannotUseNormalizerOnFieldType$str() {
        return cannotUseNormalizerOnFieldType;
    }
    @Override
    public final SearchException cannotUseNormalizerOnFieldType(final String relativeFieldName) {
        final SearchException result = new SearchException(String.format(cannotUseNormalizerOnFieldType$str(), relativeFieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotMixLuceneSearchQueryWithOtherPredicates = "HSEARCH-LUCENE000510: A Lucene query cannot include search predicates built using a non-Lucene search target. Given predicate was: '%1$s'";
    protected String cannotMixLuceneSearchQueryWithOtherPredicates$str() {
        return cannotMixLuceneSearchQueryWithOtherPredicates;
    }
    @Override
    public final SearchException cannotMixLuceneSearchQueryWithOtherPredicates(final SearchPredicate predicate) {
        final SearchException result = new SearchException(String.format(cannotMixLuceneSearchQueryWithOtherPredicates$str(), predicate));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String conflictingFieldTypesForSearch = "HSEARCH-LUCENE000511: Multiple conflicting types for field '%1$s': '%2$s' in index '%3$s', but '%4$s' in index '%5$s'.";
    protected String conflictingFieldTypesForSearch$str() {
        return conflictingFieldTypesForSearch;
    }
    @Override
    public final SearchException conflictingFieldTypesForSearch(final String absoluteFieldPath, final LuceneIndexSchemaFieldNode<? extends Object> schemaNode1, final String indexName1, final LuceneIndexSchemaFieldNode<? extends Object> schemaNode2, final String indexName2) {
        final SearchException result = new SearchException(String.format(conflictingFieldTypesForSearch$str(), absoluteFieldPath, schemaNode1, indexName1, schemaNode2, indexName2));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String nonObjectFieldForNestedQuery = "HSEARCH-LUCENE000512: Field '%2$s' is not an object field in index '%1$s'.";
    protected String nonObjectFieldForNestedQuery$str() {
        return nonObjectFieldForNestedQuery;
    }
    @Override
    public final SearchException nonObjectFieldForNestedQuery(final String indexName, final String absoluteFieldPath) {
        final SearchException result = new SearchException(String.format(nonObjectFieldForNestedQuery$str(), indexName, absoluteFieldPath));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String nonNestedFieldForNestedQuery = "HSEARCH-LUCENE000513: Object field '%2$s' is not stored as nested in index '%1$s'.";
    protected String nonNestedFieldForNestedQuery$str() {
        return nonNestedFieldForNestedQuery;
    }
    @Override
    public final SearchException nonNestedFieldForNestedQuery(final String indexName, final String absoluteFieldPath) {
        final SearchException result = new SearchException(String.format(nonNestedFieldForNestedQuery$str(), indexName, absoluteFieldPath));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotMixLuceneSearchSortWithOtherSorts = "HSEARCH-LUCENE000514: A Lucene query cannot include search sorts built using a non-Lucene search target. Given sort was: '%1$s'";
    protected String cannotMixLuceneSearchSortWithOtherSorts$str() {
        return cannotMixLuceneSearchSortWithOtherSorts;
    }
    @Override
    public final SearchException cannotMixLuceneSearchSortWithOtherSorts(final SearchSort sort) {
        final SearchException result = new SearchException(String.format(cannotMixLuceneSearchSortWithOtherSorts$str(), sort));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToCreateIndexWriter = "HSEARCH-LUCENE000515: Unable to create the IndexWriter for backend '%1$s', index '%2$s'.";
    protected String unableToCreateIndexWriter$str() {
        return unableToCreateIndexWriter;
    }
    @Override
    public final SearchException unableToCreateIndexWriter(final BackendImplementor<? extends Object> backend, final String indexName, final Exception e) {
        final SearchException result = new SearchException(String.format(unableToCreateIndexWriter$str(), backend, indexName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToIndexEntry = "HSEARCH-LUCENE000516: Unable to index entry '%3$s' for index '%1$s' and tenant identifier '%2$s'.";
    protected String unableToIndexEntry$str() {
        return unableToIndexEntry;
    }
    @Override
    public final SearchException unableToIndexEntry(final String indexName, final String tenantId, final String id, final Exception e) {
        final SearchException result = new SearchException(String.format(unableToIndexEntry$str(), indexName, tenantId, id), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToDeleteEntryFromIndex = "HSEARCH-LUCENE000517: Unable to delete entry '%3$s' from index '%1$s' and tenant identifier '%2$s'.";
    protected String unableToDeleteEntryFromIndex$str() {
        return unableToDeleteEntryFromIndex;
    }
    @Override
    public final SearchException unableToDeleteEntryFromIndex(final String indexName, final String tenantId, final String id, final Exception e) {
        final SearchException result = new SearchException(String.format(unableToDeleteEntryFromIndex$str(), indexName, tenantId, id), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToFlushIndex = "HSEARCH-LUCENE000518: Unable to flush index '%1$s'.";
    protected String unableToFlushIndex$str() {
        return unableToFlushIndex;
    }
    @Override
    public final SearchException unableToFlushIndex(final String indexName, final Exception e) {
        final SearchException result = new SearchException(String.format(unableToFlushIndex$str(), indexName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToCommitIndex = "HSEARCH-LUCENE000519: Unable to commit index '%1$s'.";
    protected String unableToCommitIndex$str() {
        return unableToCommitIndex;
    }
    @Override
    public final SearchException unableToCommitIndex(final String indexName, final Exception e) {
        final SearchException result = new SearchException(String.format(unableToCommitIndex$str(), indexName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String localDirectoryIndexRootDirectoryNotWritableDirectory = "HSEARCH-LUCENE000520: Index directory '%2$s' of backend '%1$s' exists but is not a writable directory.";
    protected String localDirectoryIndexRootDirectoryNotWritableDirectory$str() {
        return localDirectoryIndexRootDirectoryNotWritableDirectory;
    }
    @Override
    public final SearchException localDirectoryIndexRootDirectoryNotWritableDirectory(final BackendImplementor<? extends Object> backend, final Path indexDirectory) {
        final SearchException result = new SearchException(String.format(localDirectoryIndexRootDirectoryNotWritableDirectory$str(), backend, indexDirectory));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToCreateIndexRootDirectoryForLocalDirectoryBackend = "HSEARCH-LUCENE000521: Unable to create index root directory '%2$s' for backend '%1$s'.";
    protected String unableToCreateIndexRootDirectoryForLocalDirectoryBackend$str() {
        return unableToCreateIndexRootDirectoryForLocalDirectoryBackend;
    }
    @Override
    public final SearchException unableToCreateIndexRootDirectoryForLocalDirectoryBackend(final BackendImplementor<? extends Object> backend, final Path indexDirectory, final Exception e) {
        final SearchException result = new SearchException(String.format(unableToCreateIndexRootDirectoryForLocalDirectoryBackend$str(), backend, indexDirectory), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToCreateIndexReader = "HSEARCH-LUCENE000522: Could not open an index reader for index '%2$s' of backend '%1$s'.";
    protected String unableToCreateIndexReader$str() {
        return unableToCreateIndexReader;
    }
    @Override
    public final SearchException unableToCreateIndexReader(final BackendImplementor<? extends Object> backend, final String indexName, final Exception e) {
        final SearchException result = new SearchException(String.format(unableToCreateIndexReader$str(), backend, indexName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotMixLuceneSearchTargetWithOtherType = "HSEARCH-LUCENE000524: A search query cannot target both a Lucene index and other types of index. First target was: '%1$s', other target was: '%2$s'";
    protected String cannotMixLuceneSearchTargetWithOtherType$str() {
        return cannotMixLuceneSearchTargetWithOtherType;
    }
    @Override
    public final SearchException cannotMixLuceneSearchTargetWithOtherType(final IndexSearchTargetBuilder firstTarget, final LuceneIndexManager otherTarget) {
        final SearchException result = new SearchException(String.format(cannotMixLuceneSearchTargetWithOtherType$str(), firstTarget, otherTarget));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotMixLuceneSearchTargetWithOtherBackend = "HSEARCH-LUCENE000525: A search query cannot target multiple Lucene backends. First target was: '%1$s', other target was: '%2$s'";
    protected String cannotMixLuceneSearchTargetWithOtherBackend$str() {
        return cannotMixLuceneSearchTargetWithOtherBackend;
    }
    @Override
    public final SearchException cannotMixLuceneSearchTargetWithOtherBackend(final IndexSearchTargetBuilder firstTarget, final LuceneIndexManager otherTarget) {
        final SearchException result = new SearchException(String.format(cannotMixLuceneSearchTargetWithOtherBackend$str(), firstTarget, otherTarget));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownProjectionForSearch = "HSEARCH-LUCENE000526: Unknown projections %1$s in indexes %2$s.";
    protected String unknownProjectionForSearch$str() {
        return unknownProjectionForSearch;
    }
    @Override
    public final SearchException unknownProjectionForSearch(final Collection<String> projections, final Collection<String> indexNames) {
        final SearchException result = new SearchException(String.format(unknownProjectionForSearch$str(), projections, indexNames));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String ioExceptionOnQueryExecution = "HSEARCH-LUCENE000527: An IOException happened while executing the query '%1$s' on indexes %2$s.";
    protected String ioExceptionOnQueryExecution$str() {
        return ioExceptionOnQueryExecution;
    }
    @Override
    public final SearchException ioExceptionOnQueryExecution(final Query luceneQuery, final Collection<String> indexNames, final IOException e) {
        final SearchException result = new SearchException(String.format(ioExceptionOnQueryExecution$str(), luceneQuery, indexNames), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotUseSortableOnFieldType = "HSEARCH-LUCENE000528: Cannot define field '%1$s' as sortable: fields of this type cannot be sortable.";
    protected String cannotUseSortableOnFieldType$str() {
        return cannotUseSortableOnFieldType;
    }
    @Override
    public final SearchException cannotUseSortableOnFieldType(final String relativeFieldName) {
        final SearchException result = new SearchException(String.format(cannotUseSortableOnFieldType$str(), relativeFieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String multiTenancyRequiredButNotSupportedByBackend = "HSEARCH-LUCENE000529: Index '%2$s' requires multi-tenancy but backend '%1$s' does not support it in its current configuration.";
    protected String multiTenancyRequiredButNotSupportedByBackend$str() {
        return multiTenancyRequiredButNotSupportedByBackend;
    }
    @Override
    public final SearchException multiTenancyRequiredButNotSupportedByBackend(final BackendImplementor<? extends Object> backend, final String indexName) {
        final SearchException result = new SearchException(String.format(multiTenancyRequiredButNotSupportedByBackend$str(), backend, indexName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownMultiTenancyStrategyConfiguration = "HSEARCH-LUCENE000530: Unknown multi-tenancy strategy '%1$s'.";
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
    private static final String tenantIdProvidedButMultiTenancyDisabled = "HSEARCH-LUCENE000531: Tenant identifier '%2$s' is provided, but multi-tenancy is disabled for the backend '%1$s'.";
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
    private static final String multiTenancyEnabledButNoTenantIdProvided = "HSEARCH-LUCENE000532: Backend '%1$s' has multi-tenancy enabled, but no tenant identifier is provided.";
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
    private static final String backendUnwrappingWithUnknownType = "HSEARCH-LUCENE000533: Attempt to unwrap a Lucene backend to %1$s, but this backend can only be unwrapped to %2$s.";
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
    private static final String indexSchemaNodeNameConflict = "HSEARCH-LUCENE000534: The index schema node '%2$s' was added twice at path '%1$s'. Multiple bridges may be trying to access the same index field,  or two indexed-embeddeds may have prefixes that lead to conflicting field names, or you may have declared multiple conflicting mappings. In any case, there is something wrong with your mapping and you should fix it.";
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
    private static final String rangePredicatesNotSupportedByGeoPoint = "HSEARCH-LUCENE000537: Range predicates are not supported by the GeoPoint type of field '%1$s', use spatial predicates instead.";
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
    private static final String matchPredicatesNotSupportedByGeoPoint = "HSEARCH-LUCENE000538: Match predicates are not supported by the GeoPoint type of field '%1$s', use spatial predicates instead.";
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
    private static final String invalidParentDocumentObjectState = "HSEARCH-LUCENE000539: Invalid parent object for this field accessor; expected path '%1$s', got '%2$s'.";
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
    private static final String spatialPredicatesNotSupportedByFieldType = "HSEARCH-LUCENE000540: Spatial predicates are not supported by the type of field '%1$s'.";
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
    private static final String distanceOperationsNotSupportedByFieldType = "HSEARCH-LUCENE000541: Distance related operations are not supported by the type of field '%1$s'.";
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
    private static final String traditionalSortNotSupportedByGeoPoint = "HSEARCH-LUCENE000542: Traditional sorting operations are not supported by the GeoPoint type of field '%1$s', use distance sorting instead.";
    protected String traditionalSortNotSupportedByGeoPoint$str() {
        return traditionalSortNotSupportedByGeoPoint;
    }
    @Override
    public final SearchException traditionalSortNotSupportedByGeoPoint(final String absoluteFieldPath) {
        final SearchException result = new SearchException(String.format(traditionalSortNotSupportedByGeoPoint$str(), absoluteFieldPath));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String descendingOrderNotSupportedByDistanceSort = "HSEARCH-LUCENE000543: Descending order is not supported by distance sort for field '%1$s'.";
    protected String descendingOrderNotSupportedByDistanceSort$str() {
        return descendingOrderNotSupportedByDistanceSort;
    }
    @Override
    public final SearchException descendingOrderNotSupportedByDistanceSort(final String absoluteFieldPath) {
        final SearchException result = new SearchException(String.format(descendingOrderNotSupportedByDistanceSort$str(), absoluteFieldPath));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
}
