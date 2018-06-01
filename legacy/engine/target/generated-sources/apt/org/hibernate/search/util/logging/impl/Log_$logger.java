package org.hibernate.search.util.logging.impl;

import java.util.Locale;
import java.lang.IllegalStateException;
import java.io.Serializable;
import org.apache.lucene.search.Query;
import org.jboss.logging.DelegatingBasicLogger;
import org.hibernate.search.exception.SearchException;
import java.lang.String;
import org.jboss.logging.Logger;
import org.hibernate.annotations.common.reflection.XClass;
import java.lang.InterruptedException;
import org.hibernate.search.backend.spi.DeletionQuery;
import org.hibernate.search.analyzer.impl.LuceneAnalyzerReference;
import org.jboss.logging.BasicLogger;
import org.hibernate.annotations.common.reflection.XMember;
import org.hibernate.search.spi.IndexedTypeIdentifier;
import java.lang.IllegalArgumentException;
import org.hibernate.search.exception.AssertionFailure;
import org.hibernate.search.backend.spi.WorkType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.CorruptIndexException;
import org.hibernate.search.exception.EmptyQueryException;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.lang.Exception;
import org.apache.lucene.search.SortField.Type;
import org.hibernate.search.analyzer.spi.AnalyzerReference;
import org.hibernate.search.analyzer.impl.RemoteAnalyzerReference;
import java.lang.Runnable;
import java.lang.ClassCastException;
import java.lang.Throwable;
import org.hibernate.search.util.logging.impl.BaseHibernateSearchLogger;
import java.lang.Object;
import java.util.Arrays;
import java.io.File;


import static org.jboss.logging.Logger.Level.TRACE;
import static org.jboss.logging.Logger.Level.ERROR;
import static org.jboss.logging.Logger.Level.INFO;
import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.WARN;

/**
 * Warning this class consists of generated code.
 */
public class Log_$logger extends DelegatingBasicLogger implements Log, org.hibernate.search.util.logging.impl.BaseHibernateSearchLogger, BasicLogger, Serializable {
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
    public final void initializedBlackholeBackend() {
        super.log.logf(FQCN, WARN, null, initializedBlackholeBackend$str());
    }
    private static final String initializedBlackholeBackend = "HSEARCH000001: initialized \"blackhole\" backend. Index changes will be prepared but discarded!";
    protected String initializedBlackholeBackend$str() {
        return initializedBlackholeBackend;
    }
    @Override
    public final void closedBlackholeBackend() {
        super.log.logf(FQCN, INFO, null, closedBlackholeBackend$str());
    }
    private static final String closedBlackholeBackend = "HSEARCH000002: closed \"blackhole\" backend.";
    protected String closedBlackholeBackend$str() {
        return closedBlackholeBackend;
    }
    @Override
    public final void incorrectMessageType(final Class<?> messageType) {
        super.log.logf(FQCN, ERROR, null, incorrectMessageType$str(), new ClassFormatter(messageType));
    }
    private static final String incorrectMessageType = "HSEARCH000016: Incorrect message type: %1$s";
    protected String incorrectMessageType$str() {
        return incorrectMessageType;
    }
    @Override
    public final void interruptedWorkError(final Runnable r) {
        super.log.logf(FQCN, ERROR, null, interruptedWorkError$str(), r);
    }
    private static final String interruptedWorkError = "HSEARCH000017: Work discarded, thread was interrupted while waiting for space to schedule: %1$s";
    protected String interruptedWorkError$str() {
        return interruptedWorkError;
    }
    @Override
    public final void skippingDirectorySynchronization(final String indexName) {
        super.log.logf(FQCN, INFO, null, skippingDirectorySynchronization$str(), indexName);
    }
    private static final String skippingDirectorySynchronization = "HSEARCH000018: Skipping directory synchronization, previous work still in progress: %1$s";
    protected String skippingDirectorySynchronization$str() {
        return skippingDirectorySynchronization;
    }
    @Override
    public final void unableToRemovePreviousMarker(final String indexName, final Exception e) {
        super.log.logf(FQCN, WARN, e, unableToRemovePreviousMarker$str(), indexName);
    }
    private static final String unableToRemovePreviousMarker = "HSEARCH000019: Unable to remove previous marker file from source of %1$s";
    protected String unableToRemovePreviousMarker$str() {
        return unableToRemovePreviousMarker;
    }
    @Override
    public final void unableToCreateCurrentMarker(final String indexName, final Exception e) {
        super.log.logf(FQCN, WARN, e, unableToCreateCurrentMarker$str(), indexName);
    }
    private static final String unableToCreateCurrentMarker = "HSEARCH000020: Unable to create current marker in source of %1$s";
    protected String unableToCreateCurrentMarker$str() {
        return unableToCreateCurrentMarker;
    }
    @Override
    public final void unableToSynchronizeSource(final String indexName, final Exception e) {
        super.log.logf(FQCN, ERROR, e, unableToSynchronizeSource$str(), indexName);
    }
    private static final String unableToSynchronizeSource = "HSEARCH000021: Unable to synchronize source of %1$s";
    protected String unableToSynchronizeSource$str() {
        return unableToSynchronizeSource;
    }
    @Override
    public final void unableToDetermineCurrentInSourceDirectory() {
        super.log.logf(FQCN, WARN, null, unableToDetermineCurrentInSourceDirectory$str());
    }
    private static final String unableToDetermineCurrentInSourceDirectory = "HSEARCH000022: Unable to determine current in source directory, will try again during the next synchronization";
    protected String unableToDetermineCurrentInSourceDirectory$str() {
        return unableToDetermineCurrentInSourceDirectory;
    }
    @Override
    public final void unableToCompareSourceWithDestinationDirectory(final String source, final String destination) {
        super.log.logf(FQCN, ERROR, null, unableToCompareSourceWithDestinationDirectory$str(), source, destination);
    }
    private static final String unableToCompareSourceWithDestinationDirectory = "HSEARCH000023: Unable to compare %1$s with %2$s.";
    protected String unableToCompareSourceWithDestinationDirectory$str() {
        return unableToCompareSourceWithDestinationDirectory;
    }
    @Override
    public final void idCannotBeExtracted(final String affectedOwnerEntityName) {
        super.log.logf(FQCN, WARN, null, idCannotBeExtracted$str(), affectedOwnerEntityName);
    }
    private static final String idCannotBeExtracted = "HSEARCH000024: Unable to reindex entity on collection change, id cannot be extracted: %1$s";
    protected String idCannotBeExtracted$str() {
        return idCannotBeExtracted;
    }
    @Override
    public final void serviceProviderNotReleased(final Class<?> class1) {
        super.log.logf(FQCN, WARN, null, serviceProviderNotReleased$str(), new ClassFormatter(class1));
    }
    private static final String serviceProviderNotReleased = "HSEARCH000025: Service provider has been used but not released: %1$s";
    protected String serviceProviderNotReleased$str() {
        return serviceProviderNotReleased;
    }
    @Override
    public final void stopServiceFailed(final Class<?> class1, final Exception e) {
        super.log.logf(FQCN, ERROR, e, stopServiceFailed$str(), new ClassFormatter(class1));
    }
    private static final String stopServiceFailed = "HSEARCH000026: Fail to properly stop service: %1$s";
    protected String stopServiceFailed$str() {
        return stopServiceFailed;
    }
    @Override
    public final void indexingEntities(final long count) {
        super.log.logf(FQCN, INFO, null, indexingEntities$str(), count);
    }
    private static final String indexingEntities = "HSEARCH000027: Going to reindex %d entities";
    protected String indexingEntities$str() {
        return indexingEntities;
    }
    @Override
    public final void indexingEntitiesCompleted(final long nbrOfEntities) {
        super.log.logf(FQCN, INFO, null, indexingEntitiesCompleted$str(), nbrOfEntities);
    }
    private static final String indexingEntitiesCompleted = "HSEARCH000028: Reindexed %1$d entities";
    protected String indexingEntitiesCompleted$str() {
        return indexingEntitiesCompleted;
    }
    @Override
    public final void indexingCompletedAndMBeanUnregistered(final long nbrOfEntities) {
        super.log.logf(FQCN, INFO, null, indexingCompletedAndMBeanUnregistered$str(), nbrOfEntities);
    }
    private static final String indexingCompletedAndMBeanUnregistered = "HSEARCH000029: Indexing completed. Reindexed %1$d entities. Unregistering MBean from server";
    protected String indexingCompletedAndMBeanUnregistered$str() {
        return indexingCompletedAndMBeanUnregistered;
    }
    @Override
    public final void indexingDocumentsCompleted(final long doneCount, final long elapsedMs) {
        super.log.logf(FQCN, INFO, null, indexingDocumentsCompleted$str(), doneCount, elapsedMs);
    }
    private static final String indexingDocumentsCompleted = "HSEARCH000030: %1$d documents indexed in %2$d ms";
    protected String indexingDocumentsCompleted$str() {
        return indexingDocumentsCompleted;
    }
    @Override
    public final void indexingSpeed(final float estimateSpeed, final float estimatePercentileComplete) {
        super.log.logf(FQCN, INFO, null, indexingSpeed$str(), estimateSpeed, estimatePercentileComplete);
    }
    private static final String indexingSpeed = "HSEARCH000031: Indexing speed: %1$f documents/second; progress: %2$.2f%%";
    protected String indexingSpeed$str() {
        return indexingSpeed;
    }
    @Override
    public final void notDeleted(final File file) {
        super.log.logf(FQCN, WARN, null, notDeleted$str(), file);
    }
    private static final String notDeleted = "HSEARCH000032: Could not delete %1$s";
    protected String notDeleted$str() {
        return notDeleted;
    }
    @Override
    public final void version(final String versionString) {
        super.log.logf(FQCN, INFO, null, version$str(), versionString);
    }
    private static final String version = "HSEARCH000034: Hibernate Search %1$s";
    protected String version$str() {
        return version;
    }
    @Override
    public final void couldNotCloseResource(final Exception e) {
        super.log.logf(FQCN, WARN, e, couldNotCloseResource$str());
    }
    private static final String couldNotCloseResource = "HSEARCH000035: Could not close resource.";
    protected String couldNotCloseResource$str() {
        return couldNotCloseResource;
    }
    @Override
    public final void cannotGuessTransactionStatus(final Exception e) {
        super.log.logf(FQCN, WARN, e, cannotGuessTransactionStatus$str());
    }
    private static final String cannotGuessTransactionStatus = "HSEARCH000036: Cannot guess the Transaction Status: not starting a JTA transaction";
    protected String cannotGuessTransactionStatus$str() {
        return cannotGuessTransactionStatus;
    }
    @Override
    public final void unableToCloseSearcherDuringQuery(final String query, final Exception e) {
        super.log.logf(FQCN, WARN, e, unableToCloseSearcherDuringQuery$str(), query);
    }
    private static final String unableToCloseSearcherDuringQuery = "HSEARCH000037: Unable to properly close searcher during lucene query: %1$s";
    protected String unableToCloseSearcherDuringQuery$str() {
        return unableToCloseSearcherDuringQuery;
    }
    @Override
    public final void forceToUseDocumentExtraction() {
        super.log.logf(FQCN, WARN, null, forceToUseDocumentExtraction$str());
    }
    private static final String forceToUseDocumentExtraction = "HSEARCH000038: Forced to use Document extraction to workaround FieldCache bug in Lucene";
    protected String forceToUseDocumentExtraction$str() {
        return forceToUseDocumentExtraction;
    }
    @Override
    public final void unableToCloseSearcherInScrollableResult(final Exception e) {
        super.log.logf(FQCN, WARN, e, unableToCloseSearcherInScrollableResult$str());
    }
    private static final String unableToCloseSearcherInScrollableResult = "HSEARCH000039: Unable to properly close searcher in ScrollableResults";
    protected String unableToCloseSearcherInScrollableResult$str() {
        return unableToCloseSearcherInScrollableResult;
    }
    @Override
    public final void unexpectedValueMissingFromFieldCache() {
        super.log.logf(FQCN, WARN, null, unexpectedValueMissingFromFieldCache$str());
    }
    private static final String unexpectedValueMissingFromFieldCache = "HSEARCH000040: Unexpected: value is missing from FieldCache. This is likely a bug in the FieldCache implementation, Hibernate Search might have to workaround this by slightly inaccurate faceting values or reduced performance.";
    protected String unexpectedValueMissingFromFieldCache$str() {
        return unexpectedValueMissingFromFieldCache;
    }
    @Override
    public final void indexDirectoryNotFoundCreatingNewOne(final String absolutePath) {
        super.log.logf(FQCN, INFO, null, indexDirectoryNotFoundCreatingNewOne$str(), absolutePath);
    }
    private static final String indexDirectoryNotFoundCreatingNewOne = "HSEARCH000041: Index directory not found, creating: '%1$s'";
    protected String indexDirectoryNotFoundCreatingNewOne$str() {
        return indexDirectoryNotFoundCreatingNewOne;
    }
    @Override
    public final void noCurrentMarkerInSourceDirectory() {
        super.log.logf(FQCN, WARN, null, noCurrentMarkerInSourceDirectory$str());
    }
    private static final String noCurrentMarkerInSourceDirectory = "HSEARCH000042: No current marker in source directory. Has the master being started already?";
    protected String noCurrentMarkerInSourceDirectory$str() {
        return noCurrentMarkerInSourceDirectory;
    }
    @Override
    public final void foundCurrentMarker() {
        super.log.logf(FQCN, INFO, null, foundCurrentMarker$str());
    }
    private static final String foundCurrentMarker = "HSEARCH000043: Found current marker in source directory - initialization succeeded";
    protected String foundCurrentMarker$str() {
        return foundCurrentMarker;
    }
    @Override
    public final void abstractClassesCannotInsertDocuments(final String clazz) {
        super.log.logf(FQCN, WARN, null, abstractClassesCannotInsertDocuments$str(), clazz);
    }
    private static final String abstractClassesCannotInsertDocuments = "HSEARCH000044: Abstract classes cannot be indexed directly. Only concrete subclasses can be indexed. @Indexed on '%s' is superfluous and should be removed.";
    protected String abstractClassesCannotInsertDocuments$str() {
        return abstractClassesCannotInsertDocuments;
    }
    @Override
    public final void containedInPointsToProvidedId(final Class<?> objectClass) {
        super.log.logf(FQCN, WARN, null, containedInPointsToProvidedId$str(), new ClassFormatter(objectClass));
    }
    private static final String containedInPointsToProvidedId = "HSEARCH000045: @ContainedIn is pointing to an entity having @ProvidedId: %1$s. This is not supported, indexing of contained in entities will be skipped. Indexed data of the embedded object might become out of date in objects of type ";
    protected String containedInPointsToProvidedId$str() {
        return containedInPointsToProvidedId;
    }
    @Override
    public final void cannotExtractValueForIdentifier(final Class<?> beanClass) {
        super.log.logf(FQCN, WARN, null, cannotExtractValueForIdentifier$str(), new ClassFormatter(beanClass));
    }
    private static final String cannotExtractValueForIdentifier = "HSEARCH000046: FieldCache was enabled on class %1$s but for this type of identifier we can't extract values from the FieldCache: cache disabled";
    protected String cannotExtractValueForIdentifier$str() {
        return cannotExtractValueForIdentifier;
    }
    @Override
    public final void unableToCloseJmsConnection(final String jmsQueueName, final Exception e) {
        super.log.logf(FQCN, WARN, e, unableToCloseJmsConnection$str(), jmsQueueName);
    }
    private static final String unableToCloseJmsConnection = "HSEARCH000047: Unable to close JMS connection for %1$s";
    protected String unableToCloseJmsConnection$str() {
        return unableToCloseJmsConnection;
    }
    @Override
    public final void unableToRetrieveNamedAnalyzer(final String value) {
        super.log.logf(FQCN, WARN, null, unableToRetrieveNamedAnalyzer$str(), value);
    }
    private static final String unableToRetrieveNamedAnalyzer = "HSEARCH000048: Unable to retrieve named analyzer: %1$s";
    protected String unableToRetrieveNamedAnalyzer$str() {
        return unableToRetrieveNamedAnalyzer;
    }
    @Override
    public final void pushedChangesOutOfTransaction() {
        super.log.logf(FQCN, WARN, null, pushedChangesOutOfTransaction$str());
    }
    private static final String pushedChangesOutOfTransaction = "HSEARCH000050: It appears changes are being pushed to the index out of a transaction. Register the IndexWorkFlushEventListener listener on flush to correctly manage Collections!";
    protected String pushedChangesOutOfTransaction$str() {
        return pushedChangesOutOfTransaction;
    }
    @Override
    public final void forcingReleaseIndexWriterLock() {
        super.log.logf(FQCN, WARN, null, forcingReleaseIndexWriterLock$str());
    }
    private static final String forcingReleaseIndexWriterLock = "HSEARCH000052: Going to force release of the IndexWriter lock";
    protected String forcingReleaseIndexWriterLock$str() {
        return forcingReleaseIndexWriterLock;
    }
    @Override
    public final void readersNotProperlyClosedInReaderProvider() {
        super.log.logf(FQCN, WARN, null, readersNotProperlyClosedInReaderProvider$str());
    }
    private static final String readersNotProperlyClosedInReaderProvider = "HSEARCH000054: ReaderProvider contains readers not properly closed at destroy time";
    protected String readersNotProperlyClosedInReaderProvider$str() {
        return readersNotProperlyClosedInReaderProvider;
    }
    @Override
    public final void unableToCloseLuceneIndexReader(final Exception e) {
        super.log.logf(FQCN, WARN, e, unableToCloseLuceneIndexReader$str());
    }
    private static final String unableToCloseLuceneIndexReader = "HSEARCH000055: Unable to close Lucene IndexReader";
    protected String unableToCloseLuceneIndexReader$str() {
        return unableToCloseLuceneIndexReader;
    }
    @Override
    public final void unableToUnregisterExistingMBean(final String name, final Exception e) {
        super.log.logf(FQCN, WARN, e, unableToUnregisterExistingMBean$str(), name);
    }
    private static final String unableToUnregisterExistingMBean = "HSEARCH000056: Unable to un-register existing MBean: %1$s";
    protected String unableToUnregisterExistingMBean$str() {
        return unableToUnregisterExistingMBean;
    }
    @Override
    public final void exceptionOccurred(final String errorMsg, final Throwable exceptionThatOccurred) {
        super.log.logf(FQCN, ERROR, exceptionThatOccurred, exceptionOccurred$str(), errorMsg);
    }
    private static final String exceptionOccurred = "HSEARCH000058: %1$s";
    protected String exceptionOccurred$str() {
        return exceptionOccurred;
    }
    @Override
    public final void workerException(final Exception e) {
        super.log.logf(FQCN, ERROR, e, workerException$str());
    }
    private static final String workerException = "HSEARCH000059: Worker raises an exception on close()";
    protected String workerException$str() {
        return workerException;
    }
    @Override
    public final void interruptedBatchIndexing() {
        super.log.logf(FQCN, ERROR, null, interruptedBatchIndexing$str());
    }
    private static final String interruptedBatchIndexing = "HSEARCH000062: Batch indexing was interrupted";
    protected String interruptedBatchIndexing$str() {
        return interruptedBatchIndexing;
    }
    @Override
    public final void errorRollingBackTransaction(final String message, final Exception e1) {
        super.log.logf(FQCN, ERROR, e1, errorRollingBackTransaction$str(), message);
    }
    private static final String errorRollingBackTransaction = "HSEARCH000065: Error while rolling back transaction after %1$s";
    protected String errorRollingBackTransaction$str() {
        return errorRollingBackTransaction;
    }
    @Override
    public final void failedSlaveDirectoryProviderInitialization(final String indexName, final Exception re) {
        super.log.logf(FQCN, ERROR, re, failedSlaveDirectoryProviderInitialization$str(), indexName);
    }
    private static final String failedSlaveDirectoryProviderInitialization = "HSEARCH000066: Failed to initialize SlaveDirectoryProvider %1$s";
    protected String failedSlaveDirectoryProviderInitialization$str() {
        return failedSlaveDirectoryProviderInitialization;
    }
    @Override
    public final void unableToCloseLuceneDirectory(final Object directory, final Exception e) {
        super.log.logf(FQCN, ERROR, e, unableToCloseLuceneDirectory$str(), directory);
    }
    private static final String unableToCloseLuceneDirectory = "HSEARCH000067: Unable to properly close Lucene directory %1$s";
    protected String unableToCloseLuceneDirectory$str() {
        return unableToCloseLuceneDirectory;
    }
    @Override
    public final void unableToRetrieveObjectFromMessage(final Class<?> messageClass, final Exception e) {
        super.log.logf(FQCN, ERROR, e, unableToRetrieveObjectFromMessage$str(), new ClassFormatter(messageClass));
    }
    private static final String unableToRetrieveObjectFromMessage = "HSEARCH000068: Unable to retrieve object from message: %1$s";
    protected String unableToRetrieveObjectFromMessage$str() {
        return unableToRetrieveObjectFromMessage;
    }
    @Override
    public final void cannotOpenIndexWriterCausePreviousError() {
        super.log.logf(FQCN, ERROR, null, cannotOpenIndexWriterCausePreviousError$str());
    }
    private static final String cannotOpenIndexWriterCausePreviousError = "HSEARCH000072: Couldn't open the IndexWriter because of previous error: operation skipped, index ouf of sync!";
    protected String cannotOpenIndexWriterCausePreviousError$str() {
        return cannotOpenIndexWriterCausePreviousError;
    }
    @Override
    public final void backendError(final Exception e) {
        super.log.logf(FQCN, ERROR, e, backendError$str());
    }
    private static final String backendError = "HSEARCH000073: Error in backend";
    protected String backendError$str() {
        return backendError;
    }
    @Override
    public final void recommendConfiguringLuceneVersion() {
        super.log.logf(FQCN, WARN, null, recommendConfiguringLuceneVersion$str());
    }
    private static final String recommendConfiguringLuceneVersion = "HSEARCH000075: Configuration setting hibernate.search.lucene_version was not specified: using LUCENE_CURRENT.";
    protected String recommendConfiguringLuceneVersion$str() {
        return recommendConfiguringLuceneVersion;
    }
    private static final String cantOpenCorruptedIndex = "HSEARCH000076: Could not open Lucene index: index data is corrupted. index name: '%1$s'";
    protected String cantOpenCorruptedIndex$str() {
        return cantOpenCorruptedIndex;
    }
    @Override
    public final SearchException cantOpenCorruptedIndex(final CorruptIndexException e, final String indexName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cantOpenCorruptedIndex$str(), indexName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String ioExceptionOnIndex = "HSEARCH000077: An IOException happened while accessing the Lucene index '%1$s'";
    protected String ioExceptionOnIndex$str() {
        return ioExceptionOnIndex;
    }
    @Override
    public final SearchException ioExceptionOnIndex(final IOException e, final String indexName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), ioExceptionOnIndex$str(), indexName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void unableToShutdownAsynchronousIndexingByTimeout(final String indexName) {
        super.log.logf(FQCN, ERROR, null, unableToShutdownAsynchronousIndexingByTimeout$str(), indexName);
    }
    private static final String unableToShutdownAsynchronousIndexingByTimeout = "HSEARCH000078: Timed out waiting to flush all operations to the backend of index %1$s";
    protected String unableToShutdownAsynchronousIndexingByTimeout$str() {
        return unableToShutdownAsynchronousIndexingByTimeout;
    }
    @Override
    public final void warnSuspiciousBackendDirectoryCombination(final String indexName) {
        super.log.logf(FQCN, WARN, null, warnSuspiciousBackendDirectoryCombination$str(), indexName);
    }
    private static final String warnSuspiciousBackendDirectoryCombination = "HSEARCH000081: The index '%1$s' is using a non-recommended combination of backend and directoryProvider implementations";
    protected String warnSuspiciousBackendDirectoryCombination$str() {
        return warnSuspiciousBackendDirectoryCombination;
    }
    private static final String unableToSerializeLuceneWorks = "HSEARCH000083: Unable to serialize List<LuceneWork>";
    protected String unableToSerializeLuceneWorks$str() {
        return unableToSerializeLuceneWorks;
    }
    @Override
    public final SearchException unableToSerializeLuceneWorks(final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToSerializeLuceneWorks$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToReadSerializedLuceneWorks = "HSEARCH000084: Unable to read serialized List<LuceneWork>";
    protected String unableToReadSerializedLuceneWorks$str() {
        return unableToReadSerializedLuceneWorks;
    }
    @Override
    public final SearchException unableToReadSerializedLuceneWorks(final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToReadSerializedLuceneWorks$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String attributeNotRecognizedNorSerializable = "HSEARCH000085: Attribute type is not recognized and not serializable: %1$s";
    protected String attributeNotRecognizedNorSerializable$str() {
        return attributeNotRecognizedNorSerializable;
    }
    @Override
    public final SearchException attributeNotRecognizedNorSerializable(final Class<?> attributeType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), attributeNotRecognizedNorSerializable$str(), new ClassFormatter(attributeType)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownAttributeSerializedRepresentation = "HSEARCH000086: Unknown attribute serialized representation: %1$s";
    protected String unknownAttributeSerializedRepresentation$str() {
        return unknownAttributeSerializedRepresentation;
    }
    @Override
    public final SearchException unknownAttributeSerializedRepresentation(final String name) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unknownAttributeSerializedRepresentation$str(), name));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToReadTokenStream = "HSEARCH000087: Unable to read TokenStream";
    protected String unableToReadTokenStream$str() {
        return unableToReadTokenStream;
    }
    @Override
    public final SearchException unableToReadTokenStream() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToReadTokenStream$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToConvertSerializableStoreToLuceneStore = "HSEARCH000090: Unable to convert serializable Store to Lucene Store: %1$s";
    protected String unableToConvertSerializableStoreToLuceneStore$str() {
        return unableToConvertSerializableStoreToLuceneStore;
    }
    @Override
    public final SearchException unableToConvertSerializableStoreToLuceneStore(final String store) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToConvertSerializableStoreToLuceneStore$str(), store));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownNumericFieldType = "HSEARCH000091: Unknown NumericField type: %1$s";
    protected String unknownNumericFieldType$str() {
        return unknownNumericFieldType;
    }
    @Override
    public final SearchException unknownNumericFieldType(final String dataType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unknownNumericFieldType$str(), dataType));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String conversionFromReaderToStringNotYetImplemented = "HSEARCH000092: Conversion from Reader to String not yet implemented";
    protected String conversionFromReaderToStringNotYetImplemented$str() {
        return conversionFromReaderToStringNotYetImplemented;
    }
    @Override
    public final SearchException conversionFromReaderToStringNotYetImplemented() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), conversionFromReaderToStringNotYetImplemented$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownFieldType = "HSEARCH000093: Unknown Field type: %1$s";
    protected String unknownFieldType$str() {
        return unknownFieldType;
    }
    @Override
    public final SearchException unknownFieldType(final Class<?> fieldType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unknownFieldType$str(), new ClassFormatter(fieldType)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotSerializeCustomField = "HSEARCH000094: Cannot serialize custom Fieldable '%1$s'. Must be NumericField, Field or a Serializable Fieldable implementation.";
    protected String cannotSerializeCustomField$str() {
        return cannotSerializeCustomField;
    }
    @Override
    public final SearchException cannotSerializeCustomField(final Class<?> fieldType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotSerializeCustomField$str(), new ClassFormatter(fieldType)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String failToSerializeObject = "HSEARCH000095: Fail to serialize object of type %1$s";
    protected String failToSerializeObject$str() {
        return failToSerializeObject;
    }
    @Override
    public final SearchException failToSerializeObject(final Class<?> type, final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), failToSerializeObject$str(), new ClassFormatter(type)), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String failToDeserializeObject = "HSEARCH000096: Fail to deserialize object";
    protected String failToDeserializeObject$str() {
        return failToDeserializeObject;
    }
    @Override
    public final SearchException failToDeserializeObject(final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), failToDeserializeObject$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToDeserializeAvroStream = "HSEARCH000099: Unable to deserialize Avro stream";
    protected String unableToDeserializeAvroStream$str() {
        return unableToDeserializeAvroStream;
    }
    @Override
    public final SearchException unableToDeserializeAvroStream(final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToDeserializeAvroStream$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotDeserializeOperation = "HSEARCH000100: Cannot deserialize operation %1$s, unknown operation.";
    protected String cannotDeserializeOperation$str() {
        return cannotDeserializeOperation;
    }
    @Override
    public final SearchException cannotDeserializeOperation(final String schema) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotDeserializeOperation$str(), schema));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotDeserializeField = "HSEARCH000101: Cannot deserialize field type %1$s, unknown field type.";
    protected String cannotDeserializeField$str() {
        return cannotDeserializeField;
    }
    @Override
    public final SearchException cannotDeserializeField(final String schema) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotDeserializeField$str(), schema));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToSerializeInAvro = "HSEARCH000102: Unable to serialize Lucene works in Avro";
    protected String unableToSerializeInAvro$str() {
        return unableToSerializeInAvro;
    }
    @Override
    public final SearchException unableToSerializeInAvro(final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToSerializeInAvro$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToInitializeIndexManager = "HSEARCH000103: Unable to initialize IndexManager named '%1$s'";
    protected String unableToInitializeIndexManager$str() {
        return unableToInitializeIndexManager;
    }
    @Override
    public final SearchException unableToInitializeIndexManager(final String indexName, final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToInitializeIndexManager$str(), indexName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void ignoringBackendOptionForIndex(final String indexName, final String forcedBackend) {
        super.log.logf(FQCN, WARN, null, ignoringBackendOptionForIndex$str(), indexName, forcedBackend);
    }
    private static final String ignoringBackendOptionForIndex = "HSEARCH000104: Ignoring backend option for index '%1$s', configured IndexManager requires using '%2$s' instead.";
    protected String ignoringBackendOptionForIndex$str() {
        return ignoringBackendOptionForIndex;
    }
    private static final String cannotGetResultSizeWithCriteriaAndRestriction = "HSEARCH000105: Cannot safely compute getResultSize() when a Criteria with restriction is used. Use query.list().size() or query.getResultList().size(). Criteria at stake: %1$s";
    protected String cannotGetResultSizeWithCriteriaAndRestriction$str() {
        return cannotGetResultSizeWithCriteriaAndRestriction;
    }
    @Override
    public final SearchException cannotGetResultSizeWithCriteriaAndRestriction(final String criteria) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotGetResultSizeWithCriteriaAndRestriction$str(), criteria));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String fieldLooksBinaryButDecompressionFailed = "HSEARCH000106: Field %1$s looks like binary but couldn't be decompressed";
    protected String fieldLooksBinaryButDecompressionFailed$str() {
        return fieldLooksBinaryButDecompressionFailed;
    }
    @Override
    public final SearchException fieldLooksBinaryButDecompressionFailed(final String fieldName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), fieldLooksBinaryButDecompressionFailed$str(), fieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String requestedIndexNotDefined = "HSEARCH000107: Index names %1$s is not defined";
    protected String requestedIndexNotDefined$str() {
        return requestedIndexNotDefined;
    }
    @Override
    public final SearchException requestedIndexNotDefined(final String indexName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), requestedIndexNotDefined$str(), indexName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void shuttingDownBackend(final String indexName) {
        super.log.logf(FQCN, DEBUG, null, shuttingDownBackend$str(), indexName);
    }
    private static final String shuttingDownBackend = "HSEARCH000108: Shutting down backend for IndexManager '%1$s'";
    protected String shuttingDownBackend$str() {
        return shuttingDownBackend;
    }
    private static final String notAnIndexedType = "HSEARCH000109: %1$s is not an indexed type";
    protected String notAnIndexedType$str() {
        return notAnIndexedType;
    }
    @Override
    public final IllegalArgumentException notAnIndexedType(final String entityName) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), notAnIndexedType$str(), entityName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String nullIsInvalidIndexedType = "HSEARCH000110: 'null' is not a valid indexed type";
    protected String nullIsInvalidIndexedType$str() {
        return nullIsInvalidIndexedType;
    }
    @Override
    public final IllegalArgumentException nullIsInvalidIndexedType() {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), nullIsInvalidIndexedType$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String needAtLeastOneIndexName = "HSEARCH000111: At least one index name must be provided: can't open an IndexReader on nothing";
    protected String needAtLeastOneIndexName$str() {
        return needAtLeastOneIndexName;
    }
    @Override
    public final IllegalArgumentException needAtLeastOneIndexName() {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), needAtLeastOneIndexName$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String needAtLeastOneIndexedEntityType = "HSEARCH000112: At least one entity type must be provided: can't open an IndexReader on nothing";
    protected String needAtLeastOneIndexedEntityType$str() {
        return needAtLeastOneIndexedEntityType;
    }
    @Override
    public final IllegalArgumentException needAtLeastOneIndexedEntityType() {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), needAtLeastOneIndexedEntityType$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String nullIsInvalidIndexName = "HSEARCH000113: 'null' is not a valid index name";
    protected String nullIsInvalidIndexName$str() {
        return nullIsInvalidIndexName;
    }
    @Override
    public final IllegalArgumentException nullIsInvalidIndexName() {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), nullIsInvalidIndexName$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownFullTextFilter = "HSEARCH000115: Unknown @FullTextFilter: '%1$s'";
    protected String unknownFullTextFilter$str() {
        return unknownFullTextFilter;
    }
    @Override
    public final SearchException unknownFullTextFilter(final String filterName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unknownFullTextFilter$str(), filterName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String massIndexerUnexpectedErrorMessage = "HSEARCH000116: Unexpected error during MassIndexer operation";
    protected String massIndexerUnexpectedErrorMessage$str() {
        return massIndexerUnexpectedErrorMessage;
    }
    @Override
    public final String massIndexerUnexpectedErrorMessage() {
        return String.format(getLoggingLocale(), massIndexerUnexpectedErrorMessage$str());
    }
    private static final String ioExceptionOnIndexWriter = "HSEARCH000117: IOException on the IndexWriter";
    protected String ioExceptionOnIndexWriter$str() {
        return ioExceptionOnIndexWriter;
    }
    @Override
    public final String ioExceptionOnIndexWriter() {
        return String.format(getLoggingLocale(), ioExceptionOnIndexWriter$str());
    }
    private static final String exceptionDuringIndexMergeOperation = "HSEARCH000118: Exception during index Merge operation";
    protected String exceptionDuringIndexMergeOperation$str() {
        return exceptionDuringIndexMergeOperation;
    }
    @Override
    public final String exceptionDuringIndexMergeOperation() {
        return String.format(getLoggingLocale(), exceptionDuringIndexMergeOperation$str());
    }
    @Override
    public final void optimizationSkippedStillBusy(final String indexName) {
        super.log.logf(FQCN, DEBUG, null, optimizationSkippedStillBusy$str(), indexName);
    }
    private static final String optimizationSkippedStillBusy = "HSEARCH000119: Skipping optimization on index %1$s as it is already being optimized";
    protected String optimizationSkippedStillBusy$str() {
        return optimizationSkippedStillBusy;
    }
    @Override
    public final void inconsistentFieldConfiguration(final String className, final String fieldName) {
        super.log.logf(FQCN, WARN, null, inconsistentFieldConfiguration$str(), className, fieldName);
    }
    private static final String inconsistentFieldConfiguration = "HSEARCH000120: There are multiple properties indexed against the same field name '%1$s.%2$s', but with different indexing settings. The behaviour is undefined.";
    protected String inconsistentFieldConfiguration$str() {
        return inconsistentFieldConfiguration;
    }
    @Override
    public final void forceSkipIndexOperationViaInterception(final IndexedTypeIdentifier entityType, final WorkType type) {
        super.log.logf(FQCN, TRACE, null, forceSkipIndexOperationViaInterception$str(), new IndexedTypeIdentifierFormatter(entityType), type);
    }
    private static final String forceSkipIndexOperationViaInterception = "HSEARCH000125: Interceptor enforces skip index operation %2$s on instance of class %1$s";
    protected String forceSkipIndexOperationViaInterception$str() {
        return forceSkipIndexOperationViaInterception;
    }
    @Override
    public final void forceRemoveOnIndexOperationViaInterception(final IndexedTypeIdentifier entityType, final WorkType type) {
        super.log.logf(FQCN, TRACE, null, forceRemoveOnIndexOperationViaInterception$str(), new IndexedTypeIdentifierFormatter(entityType), type);
    }
    private static final String forceRemoveOnIndexOperationViaInterception = "HSEARCH000126: Interceptor enforces removal of index data instead of index operation %2$s on instance of class %1$s";
    protected String forceRemoveOnIndexOperationViaInterception$str() {
        return forceRemoveOnIndexOperationViaInterception;
    }
    @Override
    public final void forceUpdateOnIndexOperationViaInterception(final IndexedTypeIdentifier entityType, final WorkType type) {
        super.log.logf(FQCN, TRACE, null, forceUpdateOnIndexOperationViaInterception$str(), new IndexedTypeIdentifierFormatter(entityType), type);
    }
    private static final String forceUpdateOnIndexOperationViaInterception = "HSEARCH000128: Interceptor enforces update of index data instead of index operation %2$s on instance of class %1$s";
    protected String forceUpdateOnIndexOperationViaInterception$str() {
        return forceUpdateOnIndexOperationViaInterception;
    }
    private static final String targetedFieldNotSpatial = "HSEARCH000131: The field '%1$s#%2$s' used for the spatial query is not configured as spatial field. Check the proper use of @Spatial respectively SpatialFieldBridge";
    protected String targetedFieldNotSpatial$str() {
        return targetedFieldNotSpatial;
    }
    @Override
    public final SearchException targetedFieldNotSpatial(final String className, final String fieldName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), targetedFieldNotSpatial$str(), className, fieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String noFieldBridgeInterfaceImplementedByClassBridge = "HSEARCH000133: @ClassBridge implementation '%1$s' should implement either org.hibernate.search.bridge.FieldBridge, org.hibernate.search.bridge.TwoWayStringBridge or org.hibernate.search.bridge.StringBridge";
    protected String noFieldBridgeInterfaceImplementedByClassBridge$str() {
        return noFieldBridgeInterfaceImplementedByClassBridge;
    }
    @Override
    public final SearchException noFieldBridgeInterfaceImplementedByClassBridge(final String implName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), noFieldBridgeInterfaceImplementedByClassBridge$str(), implName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotInstantiateClassBridgeOfType = "HSEARCH000134: Unable to instantiate ClassBridge of type %1$s defined on %2$s";
    protected String cannotInstantiateClassBridgeOfType$str() {
        return cannotInstantiateClassBridgeOfType;
    }
    @Override
    public final SearchException cannotInstantiateClassBridgeOfType(final String implName, final String className, final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotInstantiateClassBridgeOfType$str(), implName, className), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToGuessFieldBridge = "HSEARCH000135: Unable to guess FieldBridge for %2$s in %1$s";
    protected String unableToGuessFieldBridge$str() {
        return unableToGuessFieldBridge;
    }
    @Override
    public final SearchException unableToGuessFieldBridge(final String className, final String fieldName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToGuessFieldBridge$str(), className, fieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToInstantiateSpatial = "HSEARCH000136: Unable to instantiate Spatial defined on %1$s";
    protected String unableToInstantiateSpatial$str() {
        return unableToInstantiateSpatial;
    }
    @Override
    public final SearchException unableToInstantiateSpatial(final String className, final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToInstantiateSpatial$str(), className), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String noImplementationClassInFieldBridge = "HSEARCH000137: @FieldBridge with no implementation class defined in: %1$s";
    protected String noImplementationClassInFieldBridge$str() {
        return noImplementationClassInFieldBridge;
    }
    @Override
    public final SearchException noImplementationClassInFieldBridge(final String className) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), noImplementationClassInFieldBridge$str(), className));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String noFieldBridgeInterfaceImplementedByFieldBridge = "HSEARCH000138: @FieldBridge implementation implements none of the field bridge interfaces: %1$s in %2$s";
    protected String noFieldBridgeInterfaceImplementedByFieldBridge$str() {
        return noFieldBridgeInterfaceImplementedByFieldBridge;
    }
    @Override
    public final SearchException noFieldBridgeInterfaceImplementedByFieldBridge(final String implName, final String appliedOnName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), noFieldBridgeInterfaceImplementedByFieldBridge$str(), implName, appliedOnName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToInstantiateFieldBridge = "HSEARCH000139: Unable to instantiate FieldBridge for %1$s of class %2$s";
    protected String unableToInstantiateFieldBridge$str() {
        return unableToInstantiateFieldBridge;
    }
    @Override
    public final SearchException unableToInstantiateFieldBridge(final String appliedOnName, final String appliedOnTypeName, final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToInstantiateFieldBridge$str(), appliedOnName, appliedOnTypeName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String fieldBridgeNotAnInstanceof = "HSEARCH000144: FieldBridge passed in is not an instance of %1$s";
    protected String fieldBridgeNotAnInstanceof$str() {
        return fieldBridgeNotAnInstanceof;
    }
    @Override
    public final SearchException fieldBridgeNotAnInstanceof(final String className) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), fieldBridgeNotAnInstanceof$str(), className));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String queryWithNoTermsAfterAnalysis = "HSEARCH000146: The query string '%2$s' applied on field '%1$s' has no meaningful tokens to be matched. Validate the query input against the Analyzer applied on this field.";
    protected String queryWithNoTermsAfterAnalysis$str() {
        return queryWithNoTermsAfterAnalysis;
    }
    @Override
    public final EmptyQueryException queryWithNoTermsAfterAnalysis(final String field, final String searchTerm) {
        final EmptyQueryException result = new EmptyQueryException(String.format(getLoggingLocale(), queryWithNoTermsAfterAnalysis$str(), field, searchTerm));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToDetermineClassBridge = "HSEARCH000149: Unable to determine a ClassBridge configured on type %1$s";
    protected String unableToDetermineClassBridge$str() {
        return unableToDetermineClassBridge;
    }
    @Override
    public final SearchException unableToDetermineClassBridge(final String className) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToDetermineClassBridge$str(), className));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToGetInputStreamFromBlob = "HSEARCH000150: Unable to get input stream from blob data";
    protected String unableToGetInputStreamFromBlob$str() {
        return unableToGetInputStreamFromBlob;
    }
    @Override
    public final SearchException unableToGetInputStreamFromBlob(final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToGetInputStreamFromBlob$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedTikaBridgeType = "HSEARCH000151: Unable to get input stream from object of type %1$s";
    protected String unsupportedTikaBridgeType$str() {
        return unsupportedTikaBridgeType;
    }
    @Override
    public final SearchException unsupportedTikaBridgeType(final Class<?> objectType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedTikaBridgeType$str(), new ClassFormatter(objectType)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String fileDoesNotExist = "HSEARCH000152: File %1$s does not exist";
    protected String fileDoesNotExist$str() {
        return fileDoesNotExist;
    }
    @Override
    public final SearchException fileDoesNotExist(final String fileName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), fileDoesNotExist$str(), fileName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String fileIsADirectory = "HSEARCH000153: %1$s is a directory and not a file";
    protected String fileIsADirectory$str() {
        return fileIsADirectory;
    }
    @Override
    public final SearchException fileIsADirectory(final String fileName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), fileIsADirectory$str(), fileName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String fileIsNotReadable = "HSEARCH000154: File %1$s is not readable";
    protected String fileIsNotReadable$str() {
        return fileIsNotReadable;
    }
    @Override
    public final SearchException fileIsNotReadable(final String fileName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), fileIsNotReadable$str(), fileName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToConfigureTikaBridge = "HSEARCH000155: Unable to configure %1$s";
    protected String unableToConfigureTikaBridge$str() {
        return unableToConfigureTikaBridge;
    }
    @Override
    public final SearchException unableToConfigureTikaBridge(final String bridgeName, final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToConfigureTikaBridge$str(), bridgeName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotReadFieldForClass = "HSEARCH000156: Cannot read %1$s field from a %2$s object: does getter exist and is it public ?";
    protected String cannotReadFieldForClass$str() {
        return cannotReadFieldForClass;
    }
    @Override
    public final SearchException cannotReadFieldForClass(final String fieldName, final String className) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotReadFieldForClass$str(), fieldName, className));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotExtractCoordinateFromObject = "HSEARCH000157: Class %1$s does not implement the Coordinates interface";
    protected String cannotExtractCoordinateFromObject$str() {
        return cannotExtractCoordinateFromObject;
    }
    @Override
    public final SearchException cannotExtractCoordinateFromObject(final String className) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotExtractCoordinateFromObject$str(), className));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotHaveTwoSpatialsWithDefaultOrSameName = "HSEARCH000158: Class %1$s cannot have two @Spatial using default/same name";
    protected String cannotHaveTwoSpatialsWithDefaultOrSameName$str() {
        return cannotHaveTwoSpatialsWithDefaultOrSameName;
    }
    @Override
    public final SearchException cannotHaveTwoSpatialsWithDefaultOrSameName(final String className) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotHaveTwoSpatialsWithDefaultOrSameName$str(), className));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotFindCoordinatesNorLatLongForSpatial = "HSEARCH000159: Cannot find a Coordinates interface nor @Latitude/@Longitude annotations bound to the @Spatial name '%1$s' for class %2$s. It might be a typo (or a lapse) in @Latitude.of / @Longitude.of attributes.";
    protected String cannotFindCoordinatesNorLatLongForSpatial$str() {
        return cannotFindCoordinatesNorLatLongForSpatial;
    }
    @Override
    public final SearchException cannotFindCoordinatesNorLatLongForSpatial(final String spatialName, final String className) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotFindCoordinatesNorLatLongForSpatial$str(), spatialName, className));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String ambiguousLatitudeDefinition = "HSEARCH000160: @Latitude definition for class '%1$s' is ambiguous: specified on both fields '%2$s' and '%3$s'";
    protected String ambiguousLatitudeDefinition$str() {
        return ambiguousLatitudeDefinition;
    }
    @Override
    public final SearchException ambiguousLatitudeDefinition(final String beanXClassName, final String firstField, final String secondField) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), ambiguousLatitudeDefinition$str(), beanXClassName, firstField, secondField));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String ambiguousLongitudeDefinition = "HSEARCH000161: @Longitude definition for class '%1$s' is ambiguous: specified on both fields '%2$s' and '%3$s'";
    protected String ambiguousLongitudeDefinition$str() {
        return ambiguousLongitudeDefinition;
    }
    @Override
    public final SearchException ambiguousLongitudeDefinition(final String beanXClassName, final String firstField, final String secondField) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), ambiguousLongitudeDefinition$str(), beanXClassName, firstField, secondField));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToOpenJMSConnection = "HSEARCH000162: Unable to open JMS connection on queue '%2$s' for index '%1$s'";
    protected String unableToOpenJMSConnection$str() {
        return unableToOpenJMSConnection;
    }
    @Override
    public final SearchException unableToOpenJMSConnection(final String indexName, final String jmsQueueName, final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToOpenJMSConnection$str(), indexName, jmsQueueName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToSendJMSWork = "HSEARCH000163: Unable to send Search work to JMS queue '%2$s' for index '%1$s'";
    protected String unableToSendJMSWork$str() {
        return unableToSendJMSWork;
    }
    @Override
    public final SearchException unableToSendJMSWork(final String indexName, final String jmsQueueName, final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToSendJMSWork$str(), indexName, jmsQueueName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String jmsQueueLookupException = "HSEARCH000164: Unable to lookup Search queue '%1$s' for index '%2$s'";
    protected String jmsQueueLookupException$str() {
        return jmsQueueLookupException;
    }
    @Override
    public final SearchException jmsQueueLookupException(final String jmsQueueName, final String indexName, final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), jmsQueueLookupException$str(), jmsQueueName, indexName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void indexManagerAliasResolved(final String alias, final Class<?> im) {
        super.log.logf(FQCN, DEBUG, null, indexManagerAliasResolved$str(), alias, new ClassFormatter(im));
    }
    private static final String indexManagerAliasResolved = "HSEARCH000166: IndexManager factory resolved alias '%1$s' to '%2$s'.";
    protected String indexManagerAliasResolved$str() {
        return indexManagerAliasResolved;
    }
    private static final String duplicateDocumentIdFound = "HSEARCH000167: More than one @DocumentId specified on entity '%1$s'";
    protected String duplicateDocumentIdFound$str() {
        return duplicateDocumentIdFound;
    }
    @Override
    public final SearchException duplicateDocumentIdFound(final String beanXClassName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), duplicateDocumentIdFound$str(), beanXClassName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void indexManagerUsesSerializationService(final String indexName, final String serializerDescription) {
        super.log.logf(FQCN, INFO, null, indexManagerUsesSerializationService$str(), indexName, serializerDescription);
    }
    private static final String indexManagerUsesSerializationService = "HSEARCH000168: Serialization service %2$s being used for index '%1$s'";
    protected String indexManagerUsesSerializationService$str() {
        return indexManagerUsesSerializationService;
    }
    private static final String fieldBridgeNotTwoWay = "HSEARCH000169: FieldBridge '%1$s' does not have a objectToString method: field '%2$s' in '%3$s' The FieldBridge must be a TwoWayFieldBridge or you have to enable the ignoreFieldBridge option when defining a Query";
    protected String fieldBridgeNotTwoWay$str() {
        return fieldBridgeNotTwoWay;
    }
    @Override
    public final SearchException fieldBridgeNotTwoWay(final Class<? extends org.hibernate.search.bridge.FieldBridge> bridgeClass, final String fieldName, final String typeName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), fieldBridgeNotTwoWay$str(), new ClassFormatter(bridgeClass), fieldName, typeName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToParseDocument = "HSEARCH000176: Document could not be parsed";
    protected String unableToParseDocument$str() {
        return unableToParseDocument;
    }
    @Override
    public final SearchException unableToParseDocument(final Throwable cause) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToParseDocument$str()), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String noDocumentIdFoundException = "HSEARCH000177: Unable to find a valid document id for entity '%1$s'";
    protected String noDocumentIdFoundException$str() {
        return noDocumentIdFoundException;
    }
    @Override
    public final SearchException noDocumentIdFoundException(final String entityName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), noDocumentIdFoundException$str(), entityName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String getNullSessionPassedToFullTextSessionCreationException = "HSEARCH000178: Unable to create a FullTextSession from a null Session";
    protected String getNullSessionPassedToFullTextSessionCreationException$str() {
        return getNullSessionPassedToFullTextSessionCreationException;
    }
    @Override
    public final IllegalArgumentException getNullSessionPassedToFullTextSessionCreationException() {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), getNullSessionPassedToFullTextSessionCreationException$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String getNullEntityManagerPassedToFullEntityManagerCreationException = "HSEARCH000179: Unable to create a FullTextEntityManager from a null EntityManager";
    protected String getNullEntityManagerPassedToFullEntityManagerCreationException$str() {
        return getNullEntityManagerPassedToFullEntityManagerCreationException;
    }
    @Override
    public final IllegalArgumentException getNullEntityManagerPassedToFullEntityManagerCreationException() {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), getNullEntityManagerPassedToFullEntityManagerCreationException$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String getUnableToNarrowFieldDescriptorException = "HSEARCH000180: Unable to cast %s of type %s to %s";
    protected String getUnableToNarrowFieldDescriptorException$str() {
        return getUnableToNarrowFieldDescriptorException;
    }
    @Override
    public final ClassCastException getUnableToNarrowFieldDescriptorException(final String actualDescriptorType, final String type, final String expectedType) {
        final ClassCastException result = new ClassCastException(String.format(getLoggingLocale(), getUnableToNarrowFieldDescriptorException$str(), actualDescriptorType, type, expectedType));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String getPropertyNameCannotBeNullException = "HSEARCH000181: 'null' is not a valid property name";
    protected String getPropertyNameCannotBeNullException$str() {
        return getPropertyNameCannotBeNullException;
    }
    @Override
    public final IllegalArgumentException getPropertyNameCannotBeNullException() {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), getPropertyNameCannotBeNullException$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String getFieldNameCannotBeNullException = "HSEARCH000182: 'null' is not a valid field name";
    protected String getFieldNameCannotBeNullException$str() {
        return getFieldNameCannotBeNullException;
    }
    @Override
    public final IllegalArgumentException getFieldNameCannotBeNullException() {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), getFieldNameCannotBeNullException$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String massIndexerUnableToIndexInstance = "HSEARCH000183: Unable to index instance of type %s while batch indexing: %s";
    protected String massIndexerUnableToIndexInstance$str() {
        return massIndexerUnableToIndexInstance;
    }
    @Override
    public final String massIndexerUnableToIndexInstance(final String clazz, final String value) {
        return String.format(getLoggingLocale(), massIndexerUnableToIndexInstance$str(), clazz, value);
    }
    private static final String entityWithNoShard = "HSEARCH000184: Cannot define an entity with 0 shard on '%1$s'";
    protected String entityWithNoShard$str() {
        return entityWithNoShard;
    }
    @Override
    public final SearchException entityWithNoShard(final IndexedTypeIdentifier type) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), entityWithNoShard$str(), new IndexedTypeIdentifierFormatter(type)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String assertionFailureCannotCastToWorkerBuilderContext = "HSEARCH000186: [AssertionFailure: open a bug report] SearchFactory from entityIndexBinding is not assignable to WorkerBuilderContext. Actual class is %1$s";
    protected String assertionFailureCannotCastToWorkerBuilderContext$str() {
        return assertionFailureCannotCastToWorkerBuilderContext;
    }
    @Override
    public final SearchException assertionFailureCannotCastToWorkerBuilderContext(final Class<?> type) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), assertionFailureCannotCastToWorkerBuilderContext$str(), new ClassFormatter(type)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String getNoSessionFactoryInContextException = "HSEARCH000190: Unable to start HibernateSessionFactoryServiceProvider. There is no session factory in the context. Are you sure you have Hibernate ORM enabled?";
    protected String getNoSessionFactoryInContextException$str() {
        return getNoSessionFactoryInContextException;
    }
    @Override
    public final SearchException getNoSessionFactoryInContextException() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), getNoSessionFactoryInContextException$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String getInvalidShardCountException = "HSEARCH000191: The number of shards must be >= 1. %s is an illegal value.";
    protected String getInvalidShardCountException$str() {
        return getInvalidShardCountException;
    }
    @Override
    public final SearchException getInvalidShardCountException(final int value) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), getInvalidShardCountException$str(), value));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String getInvalidIntegerValueException = "HSEARCH000192: %s";
    protected String getInvalidIntegerValueException$str() {
        return getInvalidIntegerValueException;
    }
    @Override
    public final SearchException getInvalidIntegerValueException(final String msg, final Throwable throwable) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), getInvalidIntegerValueException$str(), msg), throwable);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void idHashShardingWithSingleShard() {
        super.log.logf(FQCN, INFO, null, idHashShardingWithSingleShard$str());
    }
    private static final String idHashShardingWithSingleShard = "HSEARCH000193: Selected sharding strategy is IdHashShardingStrategy. However, there is only 1 shard configured. Have you set the 'nbr_of_shards' property?";
    protected String idHashShardingWithSingleShard$str() {
        return idHashShardingWithSingleShard;
    }
    private static final String getUnableToLoadShardingStrategyClassException = "HSEARCH000194: Unable to load configured class '%s' as 'sharding_strategy'";
    protected String getUnableToLoadShardingStrategyClassException$str() {
        return getUnableToLoadShardingStrategyClassException;
    }
    @Override
    public final SearchException getUnableToLoadShardingStrategyClassException(final String className) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), getUnableToLoadShardingStrategyClassException$str(), className));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String getMultipleServiceImplementationsException = "HSEARCH000195: Multiple service implementations detected for service '%1$s': '%2$s'";
    protected String getMultipleServiceImplementationsException$str() {
        return getMultipleServiceImplementationsException;
    }
    @Override
    public final SearchException getMultipleServiceImplementationsException(final String service, final String foundServices) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), getMultipleServiceImplementationsException$str(), service, foundServices));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String getNoServiceImplementationFoundException = "HSEARCH000196: No service implementations for service '%1$s' can be found";
    protected String getNoServiceImplementationFoundException$str() {
        return getNoServiceImplementationFoundException;
    }
    @Override
    public final SearchException getNoServiceImplementationFoundException(final String service) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), getNoServiceImplementationFoundException$str(), service));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String getUnableToCreateJGroupsBackendException = "HSEARCH000197: Unable to create JGroups backend. Are you sure you have the JGroups dependencies on the classpath?";
    protected String getUnableToCreateJGroupsBackendException$str() {
        return getUnableToCreateJGroupsBackendException;
    }
    @Override
    public final SearchException getUnableToCreateJGroupsBackendException(final Throwable throwable) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), getUnableToCreateJGroupsBackendException$str()), throwable);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String getUnexpectedServiceStatusException = "HSEARCH000198: Unexpected status '%s' for service '%s'. Check for circular dependencies or unreleased resources in your services.";
    protected String getUnexpectedServiceStatusException$str() {
        return getUnexpectedServiceStatusException;
    }
    @Override
    public final SearchException getUnexpectedServiceStatusException(final String status, final String service) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), getUnexpectedServiceStatusException$str(), status, service));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String configurationPropertyCantBeEmpty = "HSEARCH000199: Configuration property '%s' should not be empty: illegal format.";
    protected String configurationPropertyCantBeEmpty$str() {
        return configurationPropertyCantBeEmpty;
    }
    @Override
    public final SearchException configurationPropertyCantBeEmpty(final String key) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), configurationPropertyCantBeEmpty$str(), key));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String incorrectEditDistance = "HSEARCH000201: The edit distance must be either 1 or 2";
    protected String incorrectEditDistance$str() {
        return incorrectEditDistance;
    }
    @Override
    public final SearchException incorrectEditDistance() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), incorrectEditDistance$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String entityWithIdNotFound = "HSEARCH000202: Unable to find entity $1%s with id $2%s";
    protected String entityWithIdNotFound$str() {
        return entityWithIdNotFound;
    }
    @Override
    public final SearchException entityWithIdNotFound(final IndexedTypeIdentifier entityType, final String id) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), entityWithIdNotFound$str(), new IndexedTypeIdentifierFormatter(entityType), id));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String noFieldCompatibleForMoreLikeThis = "HSEARCH000203: No field from %s can be used for More Like This queries. They are neither stored or including the term vectors.";
    protected String noFieldCompatibleForMoreLikeThis$str() {
        return noFieldCompatibleForMoreLikeThis;
    }
    @Override
    public final SearchException noFieldCompatibleForMoreLikeThis(final IndexedTypeIdentifier entityType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), noFieldCompatibleForMoreLikeThis$str(), new IndexedTypeIdentifierFormatter(entityType)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String ioExceptionOnIndexOfEntity = "HSEARCH000205: An IOException happened while accessing the Lucene indexes related to '%1$s'";
    protected String ioExceptionOnIndexOfEntity$str() {
        return ioExceptionOnIndexOfEntity;
    }
    @Override
    public final SearchException ioExceptionOnIndexOfEntity(final IOException e, final IndexedTypeIdentifier entityType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), ioExceptionOnIndexOfEntity$str(), new IndexedTypeIdentifierFormatter(entityType)), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String requireTFIDFSimilarity = "HSEARCH000206: MoreLikeThis queries require a TFIDFSimilarity for entity '$1%s'";
    protected String requireTFIDFSimilarity$str() {
        return requireTFIDFSimilarity;
    }
    @Override
    public final SearchException requireTFIDFSimilarity(final IndexedTypeIdentifier entityType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), requireTFIDFSimilarity$str(), new IndexedTypeIdentifierFormatter(entityType)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String fieldNotStoredNorTermVectorCannotBeUsedInMoreLikeThis = "HSEARCH000207: Field %s of entity %s cannot be used in a MoreLikeThis query: the term vector (preferred) or the value itself need to be stored.";
    protected String fieldNotStoredNorTermVectorCannotBeUsedInMoreLikeThis$str() {
        return fieldNotStoredNorTermVectorCannotBeUsedInMoreLikeThis;
    }
    @Override
    public final SearchException fieldNotStoredNorTermVectorCannotBeUsedInMoreLikeThis(final String fieldName, final IndexedTypeIdentifier entityType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), fieldNotStoredNorTermVectorCannotBeUsedInMoreLikeThis$str(), fieldName, new IndexedTypeIdentifierFormatter(entityType)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String classLoaderServiceContainedInProvidedServicesException = "HSEARCH000208: ClassLoaderService cannot be provided via SearchConfiguration#getProvidedServices. Use SearchConfiguration#getClassLoaderService!";
    protected String classLoaderServiceContainedInProvidedServicesException$str() {
        return classLoaderServiceContainedInProvidedServicesException;
    }
    @Override
    public final SearchException classLoaderServiceContainedInProvidedServicesException() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), classLoaderServiceContainedInProvidedServicesException$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String serviceRequestedAfterReleasedAllWasCalled = "HSEARCH000209: It is not allowed to request further services after ServiceManager#releaseAll has been called.";
    protected String serviceRequestedAfterReleasedAllWasCalled$str() {
        return serviceRequestedAfterReleasedAllWasCalled;
    }
    @Override
    public final IllegalStateException serviceRequestedAfterReleasedAllWasCalled() {
        final IllegalStateException result = new IllegalStateException(String.format(getLoggingLocale(), serviceRequestedAfterReleasedAllWasCalled$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String providedServicesCannotImplementStartableOrStoppable = "HSEARCH000210: Provided service '%s' implements '%s'. Provided services are not allowed to implement either Startable or Stoppable.";
    protected String providedServicesCannotImplementStartableOrStoppable$str() {
        return providedServicesCannotImplementStartableOrStoppable;
    }
    @Override
    public final SearchException providedServicesCannotImplementStartableOrStoppable(final String service, final String implementedInterface) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), providedServicesCannotImplementStartableOrStoppable$str(), service, implementedInterface));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String massIndexerExceptionWhileFetchingIds = "HSEARCH000211: An exception occurred while the MassIndexer was fetching the primary identifiers list";
    protected String massIndexerExceptionWhileFetchingIds$str() {
        return massIndexerExceptionWhileFetchingIds;
    }
    @Override
    public final String massIndexerExceptionWhileFetchingIds() {
        return String.format(getLoggingLocale(), massIndexerExceptionWhileFetchingIds$str());
    }
    private static final String massIndexerExceptionWhileTransformingIds = "HSEARCH000212: An exception occurred while the MassIndexer was transforming identifiers to Lucene Documents";
    protected String massIndexerExceptionWhileTransformingIds$str() {
        return massIndexerExceptionWhileTransformingIds;
    }
    @Override
    public final String massIndexerExceptionWhileTransformingIds() {
        return String.format(getLoggingLocale(), massIndexerExceptionWhileTransformingIds$str());
    }
    private static final String fieldIdCannotBeUsedInMoreLikeThis = "HSEARCH000213: Field %s of entity %s cannot be used in a MoreLikeThis query. Ids and embedded ids are excluded.";
    protected String fieldIdCannotBeUsedInMoreLikeThis$str() {
        return fieldIdCannotBeUsedInMoreLikeThis;
    }
    @Override
    public final SearchException fieldIdCannotBeUsedInMoreLikeThis(final String fieldName, final IndexedTypeIdentifier entityType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), fieldIdCannotBeUsedInMoreLikeThis$str(), fieldName, new IndexedTypeIdentifierFormatter(entityType)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String numericFieldCannotBeUsedInMoreLikeThis = "HSEARCH000214: Field %s of entity %s cannot be used in a MoreLikeThis query. Numeric fields are not considered for the moment.";
    protected String numericFieldCannotBeUsedInMoreLikeThis$str() {
        return numericFieldCannotBeUsedInMoreLikeThis;
    }
    @Override
    public final SearchException numericFieldCannotBeUsedInMoreLikeThis(final String fieldName, final IndexedTypeIdentifier entityType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), numericFieldCannotBeUsedInMoreLikeThis$str(), fieldName, new IndexedTypeIdentifierFormatter(entityType)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String multipleMatchingFieldBridges = "HSEARCH000215: Multiple matching FieldBridges found for %s of return type %s: %s";
    protected String multipleMatchingFieldBridges$str() {
        return multipleMatchingFieldBridges;
    }
    @Override
    public final SearchException multipleMatchingFieldBridges(final XMember member, final XClass memberType, final String listOfFieldBridges) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), multipleMatchingFieldBridges$str(), member, memberType, listOfFieldBridges));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidIncludePathConfiguration = "HSEARCH000216: Found invalid @IndexedEmbedded->paths elements configured for member '%s' of class '%s'. The invalid paths are [%s]";
    protected String invalidIncludePathConfiguration$str() {
        return invalidIncludePathConfiguration;
    }
    @Override
    public final SearchException invalidIncludePathConfiguration(final String member, final String clazz, final String invalidPaths) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), invalidIncludePathConfiguration$str(), member, clazz, invalidPaths));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidPropertyValue = "HSEARCH000217: Invalid value '%s' for setting '%s'. Check the documentation for allowed values.";
    protected String invalidPropertyValue$str() {
        return invalidPropertyValue;
    }
    @Override
    public final SearchException invalidPropertyValue(final String value, final String property) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), invalidPropertyValue$str(), value, property));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownFieldNameForMoreLikeThisQuery = "HSEARCH000218: More like this query cannot be created, because the index does not contain a field '%s' for the type '%s";
    protected String unknownFieldNameForMoreLikeThisQuery$str() {
        return unknownFieldNameForMoreLikeThisQuery;
    }
    @Override
    public final SearchException unknownFieldNameForMoreLikeThisQuery(final String field, final IndexedTypeIdentifier type) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unknownFieldNameForMoreLikeThisQuery$str(), field, new IndexedTypeIdentifierFormatter(type)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String jmsInitialContextException = "HSEARCH000219: Could not lookup initial JNDI context for the JMS ConnectionFactory named '%s' for the index '%s";
    protected String jmsInitialContextException$str() {
        return jmsInitialContextException;
    }
    @Override
    public final SearchException jmsInitialContextException(final String jmsConnectionFactoryName, final String indexName, final Exception e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), jmsInitialContextException$str(), jmsConnectionFactoryName, indexName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String jmsQueueFactoryLookupException = "HSEARCH000220: Could not lookup JMS ConnectionFactory named '%1s' for the index '%2s";
    protected String jmsQueueFactoryLookupException$str() {
        return jmsQueueFactoryLookupException;
    }
    @Override
    public final SearchException jmsQueueFactoryLookupException(final String jmsConnectionFactoryName, final String indexName, final Exception e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), jmsQueueFactoryLookupException$str(), jmsConnectionFactoryName, indexName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String detectInfiniteTypeLoopInIndexedEmbedded = "HSEARCH000221: Circular reference. Entity %1s was already encountered, and was encountered again in entity %2s at path '%3s'. Set the @IndexedEmbedded.depth value explicitly to fix the problem.";
    protected String detectInfiniteTypeLoopInIndexedEmbedded$str() {
        return detectInfiniteTypeLoopInIndexedEmbedded;
    }
    @Override
    public final SearchException detectInfiniteTypeLoopInIndexedEmbedded(final String elementClass, final String parentEntity, final String path) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), detectInfiniteTypeLoopInIndexedEmbedded$str(), elementClass, parentEntity, path));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String searchIntegratorNotInitialized = "HSEARCH000222: The SearchFactory was not initialized";
    protected String searchIntegratorNotInitialized$str() {
        return searchIntegratorNotInitialized;
    }
    @Override
    public final SearchException searchIntegratorNotInitialized() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), searchIntegratorNotInitialized$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String searchFactoryReferenceServiceNotFound = "HSEARCH000223: The Service org.hibernate.search.hcore.impl.SearchFactoryReference was not found in the Hibernate ORM Service Registry. This might be caused by the Hibernate ORM classloader not having visibility on Hibernate Search";
    protected String searchFactoryReferenceServiceNotFound$str() {
        return searchFactoryReferenceServiceNotFound;
    }
    @Override
    public final SearchException searchFactoryReferenceServiceNotFound() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), searchFactoryReferenceServiceNotFound$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String parametersShouldNotBeNull = "HSEARCH000224: Non optional parameter named '%s' was null";
    protected String parametersShouldNotBeNull$str() {
        return parametersShouldNotBeNull;
    }
    @Override
    public final AssertionFailure parametersShouldNotBeNull(final String parameterName) {
        final AssertionFailure result = new AssertionFailure(String.format(getLoggingLocale(), parametersShouldNotBeNull$str(), parameterName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void lockingFailureDuringInitialization(final String directoryDescription) {
        super.log.logf(FQCN, WARN, null, lockingFailureDuringInitialization$str(), directoryDescription);
    }
    private static final String lockingFailureDuringInitialization = "HSEARCH000225: An index locking error occurred during initialization of Directory '%s'. This might indicate a concurrent initialization; If you experience errors on this index you might need to remove the lock, or rebuild the index.";
    protected String lockingFailureDuringInitialization$str() {
        return lockingFailureDuringInitialization;
    }
    @Override
    public final void logInfoStreamMessage(final String componentName, final String message) {
        super.log.logf(FQCN, TRACE, null, logInfoStreamMessage$str(), componentName, message);
    }
    private static final String logInfoStreamMessage = "HSEARCH000226: %s: %s";
    protected String logInfoStreamMessage$str() {
        return logInfoStreamMessage;
    }
    private static final String booleanQueryWithoutClauses = "HSEARCH000227: A BooleanQuery is not valid without at least one clause. Use at least one of should(Query) or must(Query).";
    protected String booleanQueryWithoutClauses$str() {
        return booleanQueryWithoutClauses;
    }
    @Override
    public final SearchException booleanQueryWithoutClauses() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), booleanQueryWithoutClauses$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String illegalLuceneVersionFormat = "HSEARCH000228: Property hibernate.search.lucene_version set to value '%s' is not in a valid format to express a Lucene version: %s";
    protected String illegalLuceneVersionFormat$str() {
        return illegalLuceneVersionFormat;
    }
    @Override
    public final SearchException illegalLuceneVersionFormat(final String property, final String luceneErrorMessage) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), illegalLuceneVersionFormat$str(), property, luceneErrorMessage));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String flushIntervalNeedsToBePositive = "HSEARCH000229: Property index_flush_intervalfor the index '%s' needs to be positive.";
    protected String flushIntervalNeedsToBePositive$str() {
        return flushIntervalNeedsToBePositive;
    }
    @Override
    public final SearchException flushIntervalNeedsToBePositive(final String indexName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), flushIntervalNeedsToBePositive$str(), indexName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void startingSyncConsumerThread(final String indexName) {
        super.log.logf(FQCN, DEBUG, null, startingSyncConsumerThread$str(), indexName);
    }
    private static final String startingSyncConsumerThread = "HSEARCH000230: Starting sync consumer thread for index '%s'";
    protected String startingSyncConsumerThread$str() {
        return startingSyncConsumerThread;
    }
    @Override
    public final void stoppingSyncConsumerThread(final String indexName) {
        super.log.logf(FQCN, DEBUG, null, stoppingSyncConsumerThread$str(), indexName);
    }
    private static final String stoppingSyncConsumerThread = "HSEARCH000231: Stopping sync consumer thread for index '%s'";
    protected String stoppingSyncConsumerThread$str() {
        return stoppingSyncConsumerThread;
    }
    private static final String stringEncodedFieldsAreTargetedWithNumericQuery = "HSEARCH000232: The specified query '%s' contains a numeric sub query which targets the string encoded field(s) '%s'. Check your query or try limiting the targeted entities.";
    protected String stringEncodedFieldsAreTargetedWithNumericQuery$str() {
        return stringEncodedFieldsAreTargetedWithNumericQuery;
    }
    @Override
    public final SearchException stringEncodedFieldsAreTargetedWithNumericQuery(final String query, final String numericFields) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), stringEncodedFieldsAreTargetedWithNumericQuery$str(), query, numericFields));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String numericEncodedFieldsAreTargetedWithStringQuery = "HSEARCH000233: The specified query '%s' contains a string based sub query which targets the numeric encoded field(s) '%s'. Check your query or try limiting the targeted entities.";
    protected String numericEncodedFieldsAreTargetedWithStringQuery$str() {
        return numericEncodedFieldsAreTargetedWithStringQuery;
    }
    @Override
    public final SearchException numericEncodedFieldsAreTargetedWithStringQuery(final String query, final String numericFields) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), numericEncodedFieldsAreTargetedWithStringQuery$str(), query, numericFields));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String targetedEntityTypesNotIndexed = "HSEARCH000234: None of the specified entity types ('%s') or any of their subclasses are indexed.";
    protected String targetedEntityTypesNotIndexed$str() {
        return targetedEntityTypesNotIndexed;
    }
    @Override
    public final IllegalArgumentException targetedEntityTypesNotIndexed(final String targetedEntities) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), targetedEntityTypesNotIndexed$str(), targetedEntities));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void luceneBackendInitializedSynchronously(final String indexName) {
        super.log.logf(FQCN, DEBUG, null, luceneBackendInitializedSynchronously$str(), indexName);
    }
    private static final String luceneBackendInitializedSynchronously = "HSEARCH000235: Backend for index '%s' started: using a Synchronous batching backend.";
    protected String luceneBackendInitializedSynchronously$str() {
        return luceneBackendInitializedSynchronously;
    }
    @Override
    public final void luceneBackendInitializedAsynchronously(final String indexName) {
        super.log.logf(FQCN, DEBUG, null, luceneBackendInitializedAsynchronously$str(), indexName);
    }
    private static final String luceneBackendInitializedAsynchronously = "HSEARCH000236: Backend for index '%s' started: using an Asynchronous backend with periodic commits.";
    protected String luceneBackendInitializedAsynchronously$str() {
        return luceneBackendInitializedAsynchronously;
    }
    private static final String rangeQueryWithNullToAndFromValue = "HSEARCH000237: Cannot create numeric range query for field '%s', since from and to values are null";
    protected String rangeQueryWithNullToAndFromValue$str() {
        return rangeQueryWithNullToAndFromValue;
    }
    @Override
    public final SearchException rangeQueryWithNullToAndFromValue(final String fieldName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), rangeQueryWithNullToAndFromValue$str(), fieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String numericRangeQueryWithNonNumericToAndFromValues = "HSEARCH000238: Cannot create numeric range query for field '%s', since values are not numeric (Date, int, long, short or double)";
    protected String numericRangeQueryWithNonNumericToAndFromValues$str() {
        return numericRangeQueryWithNonNumericToAndFromValues;
    }
    @Override
    public final SearchException numericRangeQueryWithNonNumericToAndFromValues(final String fieldName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), numericRangeQueryWithNonNumericToAndFromValues$str(), fieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownEncodingType = "HSEARCH000239: Unknown field encoding type: %1$s";
    protected String unknownEncodingType$str() {
        return unknownEncodingType;
    }
    @Override
    public final AssertionFailure unknownEncodingType(final String encoding) {
        final AssertionFailure result = new AssertionFailure(String.format(getLoggingLocale(), unknownEncodingType$str(), encoding));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidStringDateFieldInDocument = "HSEARCH000240: Unable to parse value '%2$s' of field '%1$s' into a Date";
    protected String invalidStringDateFieldInDocument$str() {
        return invalidStringDateFieldInDocument;
    }
    @Override
    public final SearchException invalidStringDateFieldInDocument(final String fieldName, final String value) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), invalidStringDateFieldInDocument$str(), fieldName, value));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String multipleFactoryMethodsInClass = "HSEARCH000241: Multiple @Factory methods defined in %s";
    protected String multipleFactoryMethodsInClass$str() {
        return multipleFactoryMethodsInClass;
    }
    @Override
    public final SearchException multipleFactoryMethodsInClass(final String className) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), multipleFactoryMethodsInClass$str(), className));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String noPublicNoArgConstructor = "HSEARCH000242: Hibernate Search failed to initialize component '%1$s' as class '%2$s' doesn't have a public no-arguments constructor";
    protected String noPublicNoArgConstructor$str() {
        return noPublicNoArgConstructor;
    }
    @Override
    public final SearchException noPublicNoArgConstructor(final String componentName, final Class<?> clazz) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), noPublicNoArgConstructor$str(), componentName, new ClassFormatter(clazz)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String factoryMethodsMustReturnAnObject = "HSEARCH000244: Factory methods must return an object. '%1$s#%2$s' does not";
    protected String factoryMethodsMustReturnAnObject$str() {
        return factoryMethodsMustReturnAnObject;
    }
    @Override
    public final SearchException factoryMethodsMustReturnAnObject(final String className, final String methodName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), factoryMethodsMustReturnAnObject$str(), className, methodName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToAccessMethod = "HSEARCH000245: Unable to access method '%1$s#%2$s'";
    protected String unableToAccessMethod$str() {
        return unableToAccessMethod;
    }
    @Override
    public final SearchException unableToAccessMethod(final String className, final String methodName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToAccessMethod$str(), className, methodName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String exceptionDuringFactoryMethodExecution = "HSEARCH000246: An exception occurred while invoking '%1$s#%2$s'";
    protected String exceptionDuringFactoryMethodExecution$str() {
        return exceptionDuringFactoryMethodExecution;
    }
    @Override
    public final SearchException exceptionDuringFactoryMethodExecution(final Exception e, final String className, final String methodName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), exceptionDuringFactoryMethodExecution$str(), className, methodName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String fieldTriesToOverrideIdFieldSettings = "HSEARCH000247: An indexed field defined on '%1$s:%2$s' tries to override the id field settings. The document id field settings cannot be modified. Use a different field name.";
    protected String fieldTriesToOverrideIdFieldSettings$str() {
        return fieldTriesToOverrideIdFieldSettings;
    }
    @Override
    public final SearchException fieldTriesToOverrideIdFieldSettings(final String className, final String propertyName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), fieldTriesToOverrideIdFieldSettings$str(), className, propertyName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void workListShouldNeverBeEmpty(final String stackTrace) {
        super.log.logf(FQCN, TRACE, null, workListShouldNeverBeEmpty$str(), stackTrace);
    }
    private static final String workListShouldNeverBeEmpty = "HSEARCH000248: WorkList should never be empty. Stacktrace below \n %s";
    protected String workListShouldNeverBeEmpty$str() {
        return workListShouldNeverBeEmpty;
    }
    @Override
    public final void singleTermDeleteDisabled(final String indexName) {
        super.log.logf(FQCN, INFO, null, singleTermDeleteDisabled$str(), indexName);
    }
    private static final String singleTermDeleteDisabled = "HSEARCH000249: Cannot do fast deletes on index '%s'. Entities in this index are conflicting or the index can accept unknown entities.";
    protected String singleTermDeleteDisabled$str() {
        return singleTermDeleteDisabled;
    }
    private static final String unsupportedErrorHandlerConfigurationValueType = "HSEARCH000250: Unsupported value type for configuration property hibernate.search.error_handler: %1$s";
    protected String unsupportedErrorHandlerConfigurationValueType$str() {
        return unsupportedErrorHandlerConfigurationValueType;
    }
    @Override
    public final SearchException unsupportedErrorHandlerConfigurationValueType(final Class<?> errorHandlerValueType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedErrorHandlerConfigurationValueType$str(), new ClassFormatter(errorHandlerValueType)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToSetFilterParameter = "HSEARCH000251: Unable to set filter parameter '%2$s' on filter class %1$s";
    protected String unableToSetFilterParameter$str() {
        return unableToSetFilterParameter;
    }
    @Override
    public final SearchException unableToSetFilterParameter(final Class<?> filterClass, final String parameterName, final Exception e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToSetFilterParameter$str(), new ClassFormatter(filterClass), parameterName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotInitializeDirectoryProvider = "HSEARCH000252: Unable to initialize directory provider %1$s for index %2$s";
    protected String cannotInitializeDirectoryProvider$str() {
        return cannotInitializeDirectoryProvider;
    }
    @Override
    public final SearchException cannotInitializeDirectoryProvider(final Class<? extends org.hibernate.search.store.DirectoryProvider> directoryProviderType, final String indexName, final Exception e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotInitializeDirectoryProvider$str(), new ClassFormatter(directoryProviderType), indexName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String indexBasePathRequiredForLockingStrategy = "HSEARCH000253: To use '%1$s' as a locking strategy, an indexBase path must be set";
    protected String indexBasePathRequiredForLockingStrategy$str() {
        return indexBasePathRequiredForLockingStrategy;
    }
    @Override
    public final SearchException indexBasePathRequiredForLockingStrategy(final String strategy) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), indexBasePathRequiredForLockingStrategy$str(), strategy));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownIndexingMode = "HSEARCH000254: Unknown indexing mode: %1$s";
    protected String unknownIndexingMode$str() {
        return unknownIndexingMode;
    }
    @Override
    public final SearchException unknownIndexingMode(final String indexingMode) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unknownIndexingMode$str(), indexingMode));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownDocValuesTypeType = "HSEARCH000255: Unknown DocValues type: %1$s";
    protected String unknownDocValuesTypeType$str() {
        return unknownDocValuesTypeType;
    }
    @Override
    public final SearchException unknownDocValuesTypeType(final String docValuesType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unknownDocValuesTypeType$str(), docValuesType));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unexpectedBinaryDocValuesTypeType = "HSEARCH000256: '%1$s' is an unexpected type for a binary doc value";
    protected String unexpectedBinaryDocValuesTypeType$str() {
        return unexpectedBinaryDocValuesTypeType;
    }
    @Override
    public final SearchException unexpectedBinaryDocValuesTypeType(final String docValuesType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unexpectedBinaryDocValuesTypeType$str(), docValuesType));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unexpectedNumericDocValuesTypeType = "HSEARCH000257: '%1$s' is an unexpected type for a numeric doc value";
    protected String unexpectedNumericDocValuesTypeType$str() {
        return unexpectedNumericDocValuesTypeType;
    }
    @Override
    public final SearchException unexpectedNumericDocValuesTypeType(final String docValuesType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unexpectedNumericDocValuesTypeType$str(), docValuesType));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void loadingNonExistentField(final String name) {
        super.log.logf(FQCN, DEBUG, null, loadingNonExistentField$str(), name);
    }
    private static final String loadingNonExistentField = "HSEARCH000258: Attempting to load a field named '%s' from the Lucene Document. This Document instance doesn't have such a field.";
    protected String loadingNonExistentField$str() {
        return loadingNonExistentField;
    }
    private static final String unableToDeleteByQuery = "HSEARCH000259: Unable to delete all %s matching Query: %s";
    protected String unableToDeleteByQuery$str() {
        return unableToDeleteByQuery;
    }
    @Override
    public final SearchException unableToDeleteByQuery(final IndexedTypeIdentifier entityType, final DeletionQuery deletionQuery, final Exception e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToDeleteByQuery$str(), new IndexedTypeIdentifierFormatter(entityType), deletionQuery), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void givenCriteriaObjectCannotBeApplied() {
        super.log.logf(FQCN, WARN, null, givenCriteriaObjectCannotBeApplied$str());
    }
    private static final String givenCriteriaObjectCannotBeApplied = "HSEARCH000260: A criteria for loading query results has been specified via FullTextQuery#setCriteriaQuery(), but query results originate from several id spaces. The given criteria object can therefore not be be applied.";
    protected String givenCriteriaObjectCannotBeApplied$str() {
        return givenCriteriaObjectCannotBeApplied;
    }
    private static final String unknownDeletionQueryKeySpecified = "HSEARCH000261: An unknown DeletionQuery key was specified during de-serialization of a message from another node: %d";
    protected String unknownDeletionQueryKeySpecified$str() {
        return unknownDeletionQueryKeySpecified;
    }
    @Override
    public final SearchException unknownDeletionQueryKeySpecified(final int queryKey) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unknownDeletionQueryKeySpecified$str(), queryKey));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String numericFieldAnnotationWithoutMatchingField = "HSEARCH000262: @NumericField annotation is used on %1$s#%2$s without a matching @Field annotation";
    protected String numericFieldAnnotationWithoutMatchingField$str() {
        return numericFieldAnnotationWithoutMatchingField;
    }
    @Override
    public final SearchException numericFieldAnnotationWithoutMatchingField(final IndexedTypeIdentifier entityType, final String memberName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), numericFieldAnnotationWithoutMatchingField$str(), new IndexedTypeIdentifierFormatter(entityType), memberName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String facetAnnotationWithoutMatchingField = "HSEARCH000263: @Facet annotation is used on %1$s#%2$s without a matching @Field annotation";
    protected String facetAnnotationWithoutMatchingField$str() {
        return facetAnnotationWithoutMatchingField;
    }
    @Override
    public final SearchException facetAnnotationWithoutMatchingField(final String className, final String memberName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), facetAnnotationWithoutMatchingField$str(), className, memberName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedFieldTypeForFaceting = "HSEARCH000264: @Facet is not supported for type '%1$s'. See %2$s#%3$s";
    protected String unsupportedFieldTypeForFaceting$str() {
        return unsupportedFieldTypeForFaceting;
    }
    @Override
    public final SearchException unsupportedFieldTypeForFaceting(final String valueType, final String className, final String memberName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedFieldTypeForFaceting$str(), valueType, className, memberName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String errorDuringFacetingIndexing = "HSEARCH000265: Unable to build Lucene Document due to facet indexing error";
    protected String errorDuringFacetingIndexing$str() {
        return errorDuringFacetingIndexing;
    }
    @Override
    public final SearchException errorDuringFacetingIndexing(final Exception e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), errorDuringFacetingIndexing$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String numericDateFacetForNonNumericField = "HSEARCH000267: Unable to index date facet '%1$s' for field '%2$s', since the matching field is not using a numeric field bridge";
    protected String numericDateFacetForNonNumericField$str() {
        return numericDateFacetForNonNumericField;
    }
    @Override
    public final SearchException numericDateFacetForNonNumericField(final String facetName, final String fieldName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), numericDateFacetForNonNumericField$str(), facetName, fieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownFieldNameForFaceting = "HSEARCH000268: Facet request '%1$s' tries to facet on  field '%2$s' which either does not exist or is not configured for faceting (via @Facet). Check your configuration.";
    protected String unknownFieldNameForFaceting$str() {
        return unknownFieldNameForFaceting;
    }
    @Override
    public final SearchException unknownFieldNameForFaceting(final String facetName, final String facetFieldName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unknownFieldNameForFaceting$str(), facetName, facetFieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedParameterTypeForRangeFaceting = "HSEARCH000269: '%1$s' is not a supported type for a range faceting request parameter. Supported types are: '%2$s'";
    protected String unsupportedParameterTypeForRangeFaceting$str() {
        return unsupportedParameterTypeForRangeFaceting;
    }
    @Override
    public final SearchException unsupportedParameterTypeForRangeFaceting(final String facetRangeParameterType, final String supportedTypes) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedParameterTypeForRangeFaceting$str(), facetRangeParameterType, supportedTypes));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String noStartOrEndSpecifiedForRangeQuery = "HSEARCH000270: At least of of the facets ranges in facet request '%1$s' contains neither start nor end value";
    protected String noStartOrEndSpecifiedForRangeQuery$str() {
        return noStartOrEndSpecifiedForRangeQuery;
    }
    @Override
    public final SearchException noStartOrEndSpecifiedForRangeQuery(final String facetRequestName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), noStartOrEndSpecifiedForRangeQuery$str(), facetRequestName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String rangeDefinitionOrderRequestedForDiscreteFacetRequest = "HSEARCH000271: RANGE_DEFINITION_ORDER is not a valid sort order for a discrete faceting request.";
    protected String rangeDefinitionOrderRequestedForDiscreteFacetRequest$str() {
        return rangeDefinitionOrderRequestedForDiscreteFacetRequest;
    }
    @Override
    public final SearchException rangeDefinitionOrderRequestedForDiscreteFacetRequest() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), rangeDefinitionOrderRequestedForDiscreteFacetRequest$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String attemptToCreateFacetingRequestForUnindexedEntity = "HSEARCH000272: Entity '%1$s' is not an indexed entity. Unable to create faceting request";
    protected String attemptToCreateFacetingRequestForUnindexedEntity$str() {
        return attemptToCreateFacetingRequestForUnindexedEntity;
    }
    @Override
    public final SearchException attemptToCreateFacetingRequestForUnindexedEntity(final String entityName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), attemptToCreateFacetingRequestForUnindexedEntity$str(), entityName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String attemptToFacetOnAnalyzedField = "HSEARCH000273: The indexed field '%1$s' in '%2$s' is analyzed and marked for faceting. Only un-analyzed fields can be faceted.";
    protected String attemptToFacetOnAnalyzedField$str() {
        return attemptToFacetOnAnalyzedField;
    }
    @Override
    public final SearchException attemptToFacetOnAnalyzedField(final String fieldName, final String entityName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), attemptToFacetOnAnalyzedField$str(), fieldName, entityName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void executingLuceneQuery(final Query luceneQuery) {
        super.log.logf(FQCN, DEBUG, null, executingLuceneQuery$str(), luceneQuery);
    }
    private static final String executingLuceneQuery = "HSEARCH000274: Executing Lucene query '%s'";
    protected String executingLuceneQuery$str() {
        return executingLuceneQuery;
    }
    private static final String serializationProviderNotFoundException = "HSEARCH000275: SerializationProvider service not found on the classpath. You should check that an implementation exists and it's correctly registered as a service. If that's not the case, you can also create a custom implementation or add \"org.hibernate:hibernate-search-serialization-avro\" on the classpath";
    protected String serializationProviderNotFoundException$str() {
        return serializationProviderNotFoundException;
    }
    @Override
    public final SearchException serializationProviderNotFoundException(final Exception cause) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), serializationProviderNotFoundException$str()), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String transactionNotActiveWhileProducingIdsForBatchIndexing = "HSEARCH000276: No transaction is active while indexing entity type '%1$s'; Consider increasing the connection time-out";
    protected String transactionNotActiveWhileProducingIdsForBatchIndexing$str() {
        return transactionNotActiveWhileProducingIdsForBatchIndexing;
    }
    @Override
    public final SearchException transactionNotActiveWhileProducingIdsForBatchIndexing(final IndexedTypeIdentifier entityType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), transactionNotActiveWhileProducingIdsForBatchIndexing$str(), new IndexedTypeIdentifierFormatter(entityType)));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String backendNonTransactional = "HSEARCH000277: Worker configured to be enlisted in transaction but the backend %1$s is not transactional for index %2$s";
    protected String backendNonTransactional$str() {
        return backendNonTransactional;
    }
    @Override
    public final SearchException backendNonTransactional(final String indexName, final String backend) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), backendNonTransactional$str(), indexName, backend));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cantQueryUnindexedType = "HSEARCH000278: Can't build query for type '%1$s' which is neither indexed nor has any indexed sub-types.";
    protected String cantQueryUnindexedType$str() {
        return cantQueryUnindexedType;
    }
    @Override
    public final SearchException cantQueryUnindexedType(final String canonicalEntityName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cantQueryUnindexedType$str(), canonicalEntityName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String assertionNotLoadingUTF8Charset = "HSEARCH000279: Unable to load the UTF-8 Charset!";
    protected String assertionNotLoadingUTF8Charset$str() {
        return assertionNotLoadingUTF8Charset;
    }
    @Override
    public final AssertionFailure assertionNotLoadingUTF8Charset(final UnsupportedEncodingException e) {
        final AssertionFailure result = new AssertionFailure(String.format(getLoggingLocale(), assertionNotLoadingUTF8Charset$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String sourceDirectoryNotExisting = "HSEARCH000280: Source directory does not exist: '%1$s";
    protected String sourceDirectoryNotExisting$str() {
        return sourceDirectoryNotExisting;
    }
    @Override
    public final SearchException sourceDirectoryNotExisting(final String directory) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), sourceDirectoryNotExisting$str(), directory));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String directoryIsNotReadable = "HSEARCH000281: Unable to read directory: '%1$s";
    protected String directoryIsNotReadable$str() {
        return directoryIsNotReadable;
    }
    @Override
    public final SearchException directoryIsNotReadable(final String directory) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), directoryIsNotReadable$str(), directory));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String distanceSortRequiresSpatialField = "HSEARCH000282: Distance sort can only be used with spatial fields: '%1$s' is not spatial";
    protected String distanceSortRequiresSpatialField$str() {
        return distanceSortRequiresSpatialField;
    }
    @Override
    public final SearchException distanceSortRequiresSpatialField(final String field) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), distanceSortRequiresSpatialField$str(), field));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String sortRequiresIndexedField = "HSEARCH000283: Sorting using '%1$s' requires an indexed field: '%2$s' is not valid";
    protected String sortRequiresIndexedField$str() {
        return sortRequiresIndexedField;
    }
    @Override
    public final SearchException sortRequiresIndexedField(final Class<?> sortFieldType, final String field) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), sortRequiresIndexedField$str(), new ClassFormatter(sortFieldType), field));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String ioExceptionOnMultiReaderRefresh = "HSEARCH000284: An IOException happened while opening multiple indexes";
    protected String ioExceptionOnMultiReaderRefresh$str() {
        return ioExceptionOnMultiReaderRefresh;
    }
    @Override
    public final SearchException ioExceptionOnMultiReaderRefresh(final IOException e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), ioExceptionOnMultiReaderRefresh$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String couldNotCreateUninvertingReader = "HSEARCH000285: Could not create uninverting reader for reader %s";
    protected String couldNotCreateUninvertingReader$str() {
        return couldNotCreateUninvertingReader;
    }
    @Override
    public final SearchException couldNotCreateUninvertingReader(final DirectoryReader reader, final IOException e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), couldNotCreateUninvertingReader$str(), reader), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void readerTypeUnsupportedForInverting(final Class<? extends org.apache.lucene.index.IndexReader> readerClass) {
        super.log.logf(FQCN, WARN, null, readerTypeUnsupportedForInverting$str(), new ClassFormatter(readerClass));
    }
    private static final String readerTypeUnsupportedForInverting = "HSEARCH000286: Could not create uninverting reader for reader of type %s; Only directory readers are supported";
    protected String readerTypeUnsupportedForInverting$str() {
        return readerTypeUnsupportedForInverting;
    }
    private static final String sortFieldTypeUnsupported = "HSEARCH000287: Unsupported sort type for field %1$s: %2$s";
    protected String sortFieldTypeUnsupported$str() {
        return sortFieldTypeUnsupported;
    }
    @Override
    public final SearchException sortFieldTypeUnsupported(final String fieldName, final Type type) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), sortFieldTypeUnsupported$str(), fieldName, type));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void deprecatedConfigurationPropertyIsIgnored(final String string) {
        super.log.logf(FQCN, WARN, null, deprecatedConfigurationPropertyIsIgnored$str(), string);
    }
    private static final String deprecatedConfigurationPropertyIsIgnored = "HSEARCH000288: The configuration property '%s' no longer applies and will be ignored.";
    protected String deprecatedConfigurationPropertyIsIgnored$str() {
        return deprecatedConfigurationPropertyIsIgnored;
    }
    @Override
    public final void uncoveredSortsRequested(final IndexedTypeIdentifier entityType, final String indexName, final String uncoveredSorts) {
        super.log.logf(FQCN, WARN, null, uncoveredSortsRequested$str(), new IndexedTypeIdentifierFormatter(entityType), indexName, uncoveredSorts);
    }
    private static final String uncoveredSortsRequested = "HSEARCH000289: Requested sort field(s) %3$s are not configured for entity type %1$s mapped to index %2$s, thus an uninverting reader must be created. You should declare the missing sort fields using @SortableField.";
    protected String uncoveredSortsRequested$str() {
        return uncoveredSortsRequested;
    }
    private static final String invalidNullMarkerForDouble = "HSEARCH000290: The 'indexNullAs' property for fields indexed as Doubles must represent a Double number.";
    protected String invalidNullMarkerForDouble$str() {
        return invalidNullMarkerForDouble;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForDouble(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForDouble$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForFloat = "HSEARCH000291: The 'indexNullAs' property for fields indexed as Floats must represent a Float number.";
    protected String invalidNullMarkerForFloat$str() {
        return invalidNullMarkerForFloat;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForFloat(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForFloat$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForInteger = "HSEARCH000292: The 'indexNullAs' property for fields indexed as Integer must represent an Integer number.";
    protected String invalidNullMarkerForInteger$str() {
        return invalidNullMarkerForInteger;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForInteger(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForInteger$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidNullMarkerForLong = "HSEARCH000293: The 'indexNullAs' property for fields indexed as Longs must represent a Long number.";
    protected String invalidNullMarkerForLong$str() {
        return invalidNullMarkerForLong;
    }
    @Override
    public final IllegalArgumentException invalidNullMarkerForLong(final Exception e) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), invalidNullMarkerForLong$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToSearchOnNullValueWithoutFieldBridge = "HSEARCH000294: Unable to search for null token on field '%1$s' if field bridge is ignored.";
    protected String unableToSearchOnNullValueWithoutFieldBridge$str() {
        return unableToSearchOnNullValueWithoutFieldBridge;
    }
    @Override
    public final SearchException unableToSearchOnNullValueWithoutFieldBridge(final String fieldName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToSearchOnNullValueWithoutFieldBridge$str(), fieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String parseException = "HSEARCH000295: String '$1%s' cannot be parsed into a '$2%s'";
    protected String parseException$str() {
        return parseException;
    }
    @Override
    public final SearchException parseException(final String text, final Class<?> readerClass, final Exception e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), parseException$str(), text, new ClassFormatter(readerClass)), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String valueTooLargeForConversionException = "HSEARCH000297:  Value of '%2$s' for type '%1$s' is too big for the conversion";
    protected String valueTooLargeForConversionException$str() {
        return valueTooLargeForConversionException;
    }
    @Override
    public final SearchException valueTooLargeForConversionException(final Class<?> type, final Object duration, final Exception ae) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), valueTooLargeForConversionException$str(), new ClassFormatter(type), duration), ae);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String inconsistentSortableFieldConfigurationForSharedIndex = "HSEARCH000298: Inconsisent configuration of sort fields %2$s for index '%1$s'. Make sure they are configured using @SortableField for all entities mapped to this index.";
    protected String inconsistentSortableFieldConfigurationForSharedIndex$str() {
        return inconsistentSortableFieldConfigurationForSharedIndex;
    }
    @Override
    public final SearchException inconsistentSortableFieldConfigurationForSharedIndex(final String indexName, final String requestedSortFields) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), inconsistentSortableFieldConfigurationForSharedIndex$str(), indexName, requestedSortFields));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String sortableFieldRefersToUndefinedField = "HSEARCH000299: @SortableField declared on %s#%s references to undeclared field '%s'";
    protected String sortableFieldRefersToUndefinedField$str() {
        return sortableFieldRefersToUndefinedField;
    }
    @Override
    public final SearchException sortableFieldRefersToUndefinedField(final IndexedTypeIdentifier entityType, final String property, final String sortedFieldName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), sortableFieldRefersToUndefinedField$str(), new IndexedTypeIdentifierFormatter(entityType), property, sortedFieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String severalNumericFieldAnnotationsForSameField = "HSEARCH000300: Several @NumericField annotations used on %1$s#%2$s refer to the same field";
    protected String severalNumericFieldAnnotationsForSameField$str() {
        return severalNumericFieldAnnotationsForSameField;
    }
    @Override
    public final SearchException severalNumericFieldAnnotationsForSameField(final IndexedTypeIdentifier entityType, final String memberName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), severalNumericFieldAnnotationsForSameField$str(), new IndexedTypeIdentifierFormatter(entityType), memberName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String uncoveredSortsRequestedWithUninvertingNotAllowed = "HSEARCH000301: Requested sort field(s) %3$s are not configured for entity type %1$s mapped to index %2$s, thus an uninverting reader must be created. You should declare the missing sort fields using @SortableField.";
    protected String uncoveredSortsRequestedWithUninvertingNotAllowed$str() {
        return uncoveredSortsRequestedWithUninvertingNotAllowed;
    }
    @Override
    public final SearchException uncoveredSortsRequestedWithUninvertingNotAllowed(final IndexedTypeIdentifier entityType, final String indexName, final String uncoveredSorts) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), uncoveredSortsRequestedWithUninvertingNotAllowed$str(), new IndexedTypeIdentifierFormatter(entityType), indexName, uncoveredSorts));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotRunLuceneQueryTargetingEntityIndexedWithNonLuceneIndexManager = "HSEARCH000302: Cannot execute query '%2$s', as targeted entity type '%1$s' is not mapped to an embedded Lucene index.";
    protected String cannotRunLuceneQueryTargetingEntityIndexedWithNonLuceneIndexManager$str() {
        return cannotRunLuceneQueryTargetingEntityIndexedWithNonLuceneIndexManager;
    }
    @Override
    public final SearchException cannotRunLuceneQueryTargetingEntityIndexedWithNonLuceneIndexManager(final IndexedTypeIdentifier entityType, final String query) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotRunLuceneQueryTargetingEntityIndexedWithNonLuceneIndexManager$str(), new IndexedTypeIdentifierFormatter(entityType), query));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void timedOutWaitingShutdown(final String indexName) {
        super.log.logf(FQCN, WARN, null, timedOutWaitingShutdown$str(), indexName);
    }
    private static final String timedOutWaitingShutdown = "HSEARCH000303: Timeout while waiting for indexing resources to properly flush and close on shut down ofindexing backend for index '%s'. Some pending index writes might have been lost.";
    protected String timedOutWaitingShutdown$str() {
        return timedOutWaitingShutdown;
    }
    @Override
    public final void closingIndexWriter(final String indexName) {
        super.log.logf(FQCN, DEBUG, null, closingIndexWriter$str(), indexName);
    }
    private static final String closingIndexWriter = "HSEARCH000304: Closing index writer for IndexManager '%1$s'";
    protected String closingIndexWriter$str() {
        return closingIndexWriter;
    }
    private static final String analyzerReferenceNotInitialized = "HSEARCH000305: Analyzer reference with name '%2$s' not initialized for field '%1$s'";
    protected String analyzerReferenceNotInitialized$str() {
        return analyzerReferenceNotInitialized;
    }
    @Override
    public final SearchException analyzerReferenceNotInitialized(final String fieldName, final String name) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), analyzerReferenceNotInitialized$str(), fieldName, name));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String indexManagerTypeRequiredToBuildFieldBridge = "HSEARCH000306: Index manager type is required to build a field bridge for %2$s in %1$s";
    protected String indexManagerTypeRequiredToBuildFieldBridge$str() {
        return indexManagerTypeRequiredToBuildFieldBridge;
    }
    @Override
    public final SearchException indexManagerTypeRequiredToBuildFieldBridge(final String className, final String fieldName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), indexManagerTypeRequiredToBuildFieldBridge$str(), className, fieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String sortTypeDoesNotMatchFieldType = "HSEARCH000307: Sort type %1$s is not compatible with %2$s type of field '%3$s'.";
    protected String sortTypeDoesNotMatchFieldType$str() {
        return sortTypeDoesNotMatchFieldType;
    }
    @Override
    public final SearchException sortTypeDoesNotMatchFieldType(final String sortType, final String fieldType, final String fieldName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), sortTypeDoesNotMatchFieldType$str(), sortType, fieldType, fieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String analyzerReferenceIsNotRemote = "HSEARCH000308: Analyzer reference %1$s is not a remote analyzer reference.";
    protected String analyzerReferenceIsNotRemote$str() {
        return analyzerReferenceIsNotRemote;
    }
    @Override
    public final SearchException analyzerReferenceIsNotRemote(final AnalyzerReference analyzerReference) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), analyzerReferenceIsNotRemote$str(), analyzerReference));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String analyzerReferenceIsNotLucene = "HSEARCH000309: Analyzer reference %1$s is not a Lucene analyzer reference.";
    protected String analyzerReferenceIsNotLucene$str() {
        return analyzerReferenceIsNotLucene;
    }
    @Override
    public final SearchException analyzerReferenceIsNotLucene(final AnalyzerReference analyzerReference) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), analyzerReferenceIsNotLucene$str(), analyzerReference));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String lazyLuceneAnalyzerReferenceNotInitialized = "HSEARCH000312: Lazy Lucene analyzer reference %1$s is not initialized.";
    protected String lazyLuceneAnalyzerReferenceNotInitialized$str() {
        return lazyLuceneAnalyzerReferenceNotInitialized;
    }
    @Override
    public final SearchException lazyLuceneAnalyzerReferenceNotInitialized(final LuceneAnalyzerReference reference) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), lazyLuceneAnalyzerReferenceNotInitialized$str(), reference));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String remoteAnalyzerNotInitialized = "HSEARCH000313: Remote analyzer reference %1$s is not initialized.";
    protected String remoteAnalyzerNotInitialized$str() {
        return remoteAnalyzerNotInitialized;
    }
    @Override
    public final SearchException remoteAnalyzerNotInitialized(final RemoteAnalyzerReference analyzerReference) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), remoteAnalyzerNotInitialized$str(), analyzerReference));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String lazyRemoteAnalyzerReferenceNotInitialized = "HSEARCH000315: Lazy remote analyzer reference %1$s is not initialized.";
    protected String lazyRemoteAnalyzerReferenceNotInitialized$str() {
        return lazyRemoteAnalyzerReferenceNotInitialized;
    }
    @Override
    public final SearchException lazyRemoteAnalyzerReferenceNotInitialized(final RemoteAnalyzerReference reference) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), lazyRemoteAnalyzerReferenceNotInitialized$str(), reference));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unexpectedProjectionConstant = "HSEARCH000317: Projection constant '%s' is not supported for this query.";
    protected String unexpectedProjectionConstant$str() {
        return unexpectedProjectionConstant;
    }
    @Override
    public final SearchException unexpectedProjectionConstant(final String constantName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unexpectedProjectionConstant$str(), constantName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void deprecatedBackendName() {
        super.log.logf(FQCN, WARN, null, deprecatedBackendName$str());
    }
    private static final String deprecatedBackendName = "HSEARCH000318: The backend name 'lucene' is deprecated. Use 'local' instead";
    protected String deprecatedBackendName$str() {
        return deprecatedBackendName;
    }
    @Override
    public final void usingSerializationService(final String serializerDescription) {
        super.log.logf(FQCN, INFO, null, usingSerializationService$str(), serializerDescription);
    }
    private static final String usingSerializationService = "HSEARCH000319: Using serialization service %1$s";
    protected String usingSerializationService$str() {
        return usingSerializationService;
    }
    private static final String couldNotAnalyzeSortableField = "HSEARCH000320: Could not analyze sortable field '%1$s'.";
    protected String couldNotAnalyzeSortableField$str() {
        return couldNotAnalyzeSortableField;
    }
    @Override
    public final SearchException couldNotAnalyzeSortableField(final String fieldName, final Exception cause) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), couldNotAnalyzeSortableField$str(), fieldName), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void multipleTermsInAnalyzedSortableField(final String fieldName) {
        super.log.logf(FQCN, WARN, null, multipleTermsInAnalyzedSortableField$str(), fieldName);
    }
    private static final String multipleTermsInAnalyzedSortableField = "HSEARCH000321: The analysis of field '%1$s' produced multiple tokens. Tokenization or term generation (synonyms) should not be used on sortable fields. Only the first token will be indexed.";
    protected String multipleTermsInAnalyzedSortableField$str() {
        return multipleTermsInAnalyzedSortableField;
    }
    @Override
    public final void timedOutWaitingShutdownOfReaderProvider(final String indexName) {
        super.log.logf(FQCN, DEBUG, null, timedOutWaitingShutdownOfReaderProvider$str(), indexName);
    }
    private static final String timedOutWaitingShutdownOfReaderProvider = "HSEARCH000322: Timeout while waiting for background index reader resources to shut downgracefully for index '%s'.";
    protected String timedOutWaitingShutdownOfReaderProvider$str() {
        return timedOutWaitingShutdownOfReaderProvider;
    }
    private static final String projectingNonStoredField = "HSEARCH000323: The field '%1$s' is not stored.";
    protected String projectingNonStoredField$str() {
        return projectingNonStoredField;
    }
    @Override
    public final SearchException projectingNonStoredField(final String fieldName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), projectingNonStoredField$str(), fieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String nullMarkerInvalidFormat = "HSEARCH000325: The 'indexNullAs' property for field '%2$s', with value '%1$s', has invalid format: %3$s Could not parse '%1$s'.";
    protected String nullMarkerInvalidFormat$str() {
        return nullMarkerInvalidFormat;
    }
    @Override
    public final SearchException nullMarkerInvalidFormat(final String proposedTokenValue, final String fieldName, final String invalidFormatMessage, final Exception e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), nullMarkerInvalidFormat$str(), proposedTokenValue, fieldName, invalidFormatMessage), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String indexNamesCollisionDetected = "HSEARCH000326: Index names collision detected. Different index managers will point to the same actual index: %1$s";
    protected String indexNamesCollisionDetected$str() {
        return indexNamesCollisionDetected;
    }
    @Override
    public final SearchException indexNamesCollisionDetected(final String string) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), indexNamesCollisionDetected$str(), string));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotCreateBridgeDefinedField = "HSEARCH000328: Cannot create context for class: %1$s";
    protected String cannotCreateBridgeDefinedField$str() {
        return cannotCreateBridgeDefinedField;
    }
    @Override
    public final SearchException cannotCreateBridgeDefinedField(final Class<?> backend, final Exception e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotCreateBridgeDefinedField$str(), new ClassFormatter(backend)), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String analyzerDefinitionNamingConflict = "HSEARCH000330: Multiple analyzer definitions with the same name: '%1$s'.";
    protected String analyzerDefinitionNamingConflict$str() {
        return analyzerDefinitionNamingConflict;
    }
    @Override
    public final SearchException analyzerDefinitionNamingConflict(final String analyzerDefinitionName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), analyzerDefinitionNamingConflict$str(), analyzerDefinitionName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cantQueryUnconfiguredType = "HSEARCH000331: Can't build query for type '%1$s' which is neither configured nor has any configured sub-types.";
    protected String cantQueryUnconfiguredType$str() {
        return cantQueryUnconfiguredType;
    }
    @Override
    public final SearchException cantQueryUnconfiguredType(final String canonicalEntityName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cantQueryUnconfiguredType$str(), canonicalEntityName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String targetedEntityTypesNotConfigured = "HSEARCH000332: None of the specified entity types ('%s') or any of their subclasses are configured.";
    protected String targetedEntityTypesNotConfigured$str() {
        return targetedEntityTypesNotConfigured;
    }
    @Override
    public final IllegalArgumentException targetedEntityTypesNotConfigured(final String targetedEntities) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), targetedEntityTypesNotConfigured$str(), targetedEntities));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String queryWithNoIndexedType = "HSEARCH000333: Cannot query: there aren't any mapped entity. Don't forget to add @Indexed to at least one class.";
    protected String queryWithNoIndexedType$str() {
        return queryWithNoIndexedType;
    }
    @Override
    public final SearchException queryWithNoIndexedType() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), queryWithNoIndexedType$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String simpleQueryParserDoesNotSupportNullQueries = "HSEARCH000334: The simple query parser does not support null queries.";
    protected String simpleQueryParserDoesNotSupportNullQueries$str() {
        return simpleQueryParserDoesNotSupportNullQueries;
    }
    @Override
    public final SearchException simpleQueryParserDoesNotSupportNullQueries() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), simpleQueryParserDoesNotSupportNullQueries$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void fileDeleteFailureIgnored(final IOException e) {
        super.log.logf(FQCN, DEBUG, e, fileDeleteFailureIgnored$str());
    }
    private static final String fileDeleteFailureIgnored = "HSEARCH000336: A file could not be deleted: likely lock contention. Not a problem for index replications as it will be attempted again in the future.";
    protected String fileDeleteFailureIgnored$str() {
        return fileDeleteFailureIgnored;
    }
    private static final String conflictingParameterDefined = "HSEARCH000337: Conflicting usage of @Parameter annotation for parameter name: '%1$s'. Can't assign both value '%2$s' and '%3$s'";
    protected String conflictingParameterDefined$str() {
        return conflictingParameterDefined;
    }
    @Override
    public final SearchException conflictingParameterDefined(final String name, final String value1, final String value2) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), conflictingParameterDefined$str(), name, value1, value2));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String incompleteEntityInfo = "HSEARCH000338: Incomplete entity information in a document retrieved from the index: the entity type ('%1$s') or identifier ('%2$s') was missing.";
    protected String incompleteEntityInfo$str() {
        return incompleteEntityInfo;
    }
    @Override
    public final SearchException incompleteEntityInfo(final IndexedTypeIdentifier entityType, final Object id) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), incompleteEntityInfo$str(), new IndexedTypeIdentifierFormatter(entityType), id));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String beanResolverContainedInProvidedServicesException = "HSEARCH000339: BeanResolver cannot be provided via SearchConfiguration#getProvidedServices. Use SearchConfiguration#getBeanResolver!";
    protected String beanResolverContainedInProvidedServicesException$str() {
        return beanResolverContainedInProvidedServicesException;
    }
    @Override
    public final SearchException beanResolverContainedInProvidedServicesException() {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), beanResolverContainedInProvidedServicesException$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String fullTextFilterDefinitionNamingConflict = "HSEARCH000340: Multiple full-text filter definitions with the same name: '%1$s'.";
    protected String fullTextFilterDefinitionNamingConflict$str() {
        return fullTextFilterDefinitionNamingConflict;
    }
    @Override
    public final SearchException fullTextFilterDefinitionNamingConflict(final String filterDefinitionName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), fullTextFilterDefinitionNamingConflict$str(), filterDefinitionName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String normalizerDefinitionNamingConflict = "HSEARCH000341: Multiple normalizer definitions with the same name: '%1$s'.";
    protected String normalizerDefinitionNamingConflict$str() {
        return normalizerDefinitionNamingConflict;
    }
    @Override
    public final SearchException normalizerDefinitionNamingConflict(final String normalizerDefinitionName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), normalizerDefinitionNamingConflict$str(), normalizerDefinitionName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotReferenceAnalyzerAndNormalizer = "HSEARCH000342: Field '%2$s' on entity '%1$s' refers to both an analyzer and a normalizer.";
    protected String cannotReferenceAnalyzerAndNormalizer$str() {
        return cannotReferenceAnalyzerAndNormalizer;
    }
    @Override
    public final SearchException cannotReferenceAnalyzerAndNormalizer(final IndexedTypeIdentifier entityType, final String relativeFieldPath) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), cannotReferenceAnalyzerAndNormalizer$str(), new IndexedTypeIdentifierFormatter(entityType), relativeFieldPath));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidEmptyNormalizerDefinition = "HSEARCH000343: Normalizer definition for '%s' must define at least a char filter or a token filter (or both).";
    protected String invalidEmptyNormalizerDefinition$str() {
        return invalidEmptyNormalizerDefinition;
    }
    @Override
    public final SearchException invalidEmptyNormalizerDefinition(final String name) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), invalidEmptyNormalizerDefinition$str(), name));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void normalizerProducedMultipleTokens(final String normalizerName, final int token) {
        super.log.logf(FQCN, WARN, null, normalizerProducedMultipleTokens$str(), normalizerName, token);
    }
    private static final String normalizerProducedMultipleTokens = "HSEARCH000344: The normalizer for definition '%s' produced %d tokens. Normalizers should never produce more than one token. The tokens have been concatenated by Hibernate Search, but you should fix your normalizer definition.";
    protected String normalizerProducedMultipleTokens$str() {
        return normalizerProducedMultipleTokens;
    }
    @Override
    public final void sortableFieldWithNonNormalizerAnalyzer(final IndexedTypeIdentifier entityType, final String absoluteFieldPath) {
        super.log.logf(FQCN, WARN, null, sortableFieldWithNonNormalizerAnalyzer$str(), new IndexedTypeIdentifierFormatter(entityType), absoluteFieldPath);
    }
    private static final String sortableFieldWithNonNormalizerAnalyzer = "HSEARCH000345: Field '%2$s' on entity '%1$s' is marked as sortable and will be analyzed, but is assigned an Analyzer instead of a Normalizer. Sortable fields should be assigned normalizers in order to avoid problems with tokenization.";
    protected String sortableFieldWithNonNormalizerAnalyzer$str() {
        return sortableFieldWithNonNormalizerAnalyzer;
    }
    @Override
    public final void usingDeprecatedNameForRamDirectoryProvider() {
        super.log.logf(FQCN, WARN, null, usingDeprecatedNameForRamDirectoryProvider$str());
    }
    private static final String usingDeprecatedNameForRamDirectoryProvider = "HSEARCH000346: The 'ram' directory provider is deprecated and will be removed in a future version. You should use 'local-heap' instead. Also, this should not be used in a production environment. Please refer to the official documentation for more information.";
    protected String usingDeprecatedNameForRamDirectoryProvider$str() {
        return usingDeprecatedNameForRamDirectoryProvider;
    }
    private static final String incompatibleEntityManagerFactory = "HSEARCH000347: The EntityManagerFactory of type '%s' is not an Hibernate based EntityManagerFactory, or not compatible with this version of Hibernate Search.";
    protected String incompatibleEntityManagerFactory$str() {
        return incompatibleEntityManagerFactory;
    }
    @Override
    public final SearchException incompatibleEntityManagerFactory(final String emfClassName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), incompatibleEntityManagerFactory$str(), emfClassName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String someTargetedEntityTypesNotConfigured = "HSEARCH000348: Some of the specified entity types ('%s') are not configured, nor is any of their subclasses.";
    protected String someTargetedEntityTypesNotConfigured$str() {
        return someTargetedEntityTypesNotConfigured;
    }
    @Override
    public final IllegalArgumentException someTargetedEntityTypesNotConfigured(final String targetedEntities) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), someTargetedEntityTypesNotConfigured$str(), targetedEntities));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String someTargetedEntityTypesNotIndexed = "HSEARCH000349: Some of the specified entity types ('%s') are not indexed, nor is any of their subclasses.";
    protected String someTargetedEntityTypesNotIndexed$str() {
        return someTargetedEntityTypesNotIndexed;
    }
    @Override
    public final IllegalArgumentException someTargetedEntityTypesNotIndexed(final String targetedEntities) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(getLoggingLocale(), someTargetedEntityTypesNotIndexed$str(), targetedEntities));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void interruptedWhileWaitingForIndexActivity(final String name, final InterruptedException e) {
        super.log.logf(FQCN, WARN, e, interruptedWhileWaitingForIndexActivity$str(), name);
    }
    private static final String interruptedWhileWaitingForIndexActivity = "HSEARCH000049: '%s' was interrupted while waiting for index activity to finish. Index might be inconsistent or have a stale lock";
    protected String interruptedWhileWaitingForIndexActivity$str() {
        return interruptedWhileWaitingForIndexActivity;
    }
    @Override
    public final void illegalObjectRetrievedFromMessage(final Exception e) {
        super.log.logf(FQCN, ERROR, e, illegalObjectRetrievedFromMessage$str());
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
    public final SearchException unableToLoadResource(final String fileName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToLoadResource$str(), fileName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownResolution = "HSEARCH000140: Unknown Resolution: %1$s";
    protected String unknownResolution$str() {
        return unknownResolution;
    }
    @Override
    public final AssertionFailure unknownResolution(final String resolution) {
        final AssertionFailure result = new AssertionFailure(String.format(getLoggingLocale(), unknownResolution$str(), resolution));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedFacetRangeParameter = "HSEARCH000266: '%s' is not a valid type for a facet range request. Numbers (byte, short, int, long, float, double and their wrappers) as well as dates are supported";
    protected String unsupportedFacetRangeParameter$str() {
        return unsupportedFacetRangeParameter;
    }
    @Override
    public final SearchException unsupportedFacetRangeParameter(final String type) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedFacetRangeParameter$str(), type));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String projectingFieldWithoutTwoWayFieldBridge = "HSEARCH000324: The fieldBridge for field '%1$s' is an instance of '%2$s', which does not implement TwoWayFieldBridge. Projected fields must have a TwoWayFieldBridge.";
    protected String projectingFieldWithoutTwoWayFieldBridge$str() {
        return projectingFieldWithoutTwoWayFieldBridge;
    }
    @Override
    public final SearchException projectingFieldWithoutTwoWayFieldBridge(final String fieldName, final Class<?> fieldBridgeClass) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), projectingFieldWithoutTwoWayFieldBridge$str(), fieldName, fieldBridgeClass));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unsupportedNullTokenType = "HSEARCH000327: Unsupported indexNullAs token type '%3$s' on field '%2$s' of entity '%1$s'.";
    protected String unsupportedNullTokenType$str() {
        return unsupportedNullTokenType;
    }
    @Override
    public final SearchException unsupportedNullTokenType(final IndexedTypeIdentifier entityName, final String fieldName, final Class<?> tokenType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unsupportedNullTokenType$str(), new IndexedTypeIdentifierFormatter(entityName), fieldName, tokenType));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidLuceneAnalyzerDefinitionProvider = "HSEARCH000329: Property 'hibernate.search.lucene.analysis_definition_provider' set to value '%1$s' is invalid. The value must be the fully-qualified name of a class with a public, no-arg constructor in your classpath. Also, the class must either implement LuceneAnalyzerDefinitionProvider or expose a public, @Factory-annotated method returning a LuceneAnalyzerDefinitionProvider.";
    protected String invalidLuceneAnalyzerDefinitionProvider$str() {
        return invalidLuceneAnalyzerDefinitionProvider;
    }
    @Override
    public final SearchException invalidLuceneAnalyzerDefinitionProvider(final String providerClassName, final Exception e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), invalidLuceneAnalyzerDefinitionProvider$str(), providerClassName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
}
