package org.hibernate.search.backend.jgroups.logging.impl;

import java.util.Locale;
import java.io.Serializable;
import org.hibernate.search.exception.AssertionFailure;
import org.jgroups.Address;
import org.jboss.logging.DelegatingBasicLogger;
import org.hibernate.search.exception.SearchException;
import java.lang.String;
import org.jboss.logging.Logger;
import java.util.Properties;
import org.jgroups.View;
import java.lang.InterruptedException;
import java.lang.Exception;
import org.jboss.logging.BasicLogger;
import java.lang.Throwable;
import org.hibernate.search.util.logging.impl.BaseHibernateSearchLogger;
import java.lang.Object;
import java.util.Arrays;
import org.hibernate.search.spi.IndexedTypeIdentifier;


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
    @Override
    public final void jgroupsSuspectingPeer(final Address sender) {
        super.log.logf(FQCN, WARN, null, jgroupsSuspectingPeer$str(), sender);
    }
    private static final String jgroupsSuspectingPeer = "HSEARCH200001: Remote JGroups peer '%1$s' is suspected to have left '";
    protected String jgroupsSuspectingPeer$str() {
        return jgroupsSuspectingPeer;
    }
    private static final String jgroupsRemoteException = "HSEARCH200003: Exception reported from remote JGroups node '%1$s' : '%2$s'";
    protected String jgroupsRemoteException$str() {
        return jgroupsRemoteException;
    }
    @Override
    public final SearchException jgroupsRemoteException(final Address sender, final Throwable exception, final Throwable cause) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), jgroupsRemoteException$str(), sender, exception), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToSendWorkViaJGroups = "HSEARCH200004: Unable to send Lucene update work via JGroups cluster";
    protected String unableToSendWorkViaJGroups$str() {
        return unableToSendWorkViaJGroups;
    }
    @Override
    public final SearchException unableToSendWorkViaJGroups(final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToSendWorkViaJGroups$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void receivedEmptyLuceneWorksInMessage() {
        super.log.logf(FQCN, WARN, null, receivedEmptyLuceneWorksInMessage$str());
    }
    private static final String receivedEmptyLuceneWorksInMessage = "HSEARCH200005: Received null or empty Lucene works list in message.";
    protected String receivedEmptyLuceneWorksInMessage$str() {
        return receivedEmptyLuceneWorksInMessage;
    }
    @Override
    public final void jGroupsReceivedNewClusterView(final Object view) {
        super.log.logf(FQCN, INFO, null, jGroupsReceivedNewClusterView$str(), view);
    }
    private static final String jGroupsReceivedNewClusterView = "HSEARCH200006: Received new cluster view: %1$s";
    protected String jGroupsReceivedNewClusterView$str() {
        return jGroupsReceivedNewClusterView;
    }
    private static final String missingJGroupsMuxId = "HSEARCH200007: Configured JGroups channel is a Muxer! MuxId option is required: define '%s'.";
    protected String missingJGroupsMuxId$str() {
        return missingJGroupsMuxId;
    }
    @Override
    public final SearchException missingJGroupsMuxId(final String muxId) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), missingJGroupsMuxId$str(), muxId));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void jGroupsStartingChannelProvider() {
        super.log.logf(FQCN, DEBUG, null, jGroupsStartingChannelProvider$str());
    }
    private static final String jGroupsStartingChannelProvider = "HSEARCH200008: Starting JGroups ChannelProvider";
    protected String jGroupsStartingChannelProvider$str() {
        return jGroupsStartingChannelProvider;
    }
    private static final String jGroupsMuxIdAlreadyTaken = "HSEARCH200009: MuxId '%1$d' configured on the JGroups was already taken. Can't register handler!";
    protected String jGroupsMuxIdAlreadyTaken$str() {
        return jGroupsMuxIdAlreadyTaken;
    }
    @Override
    public final SearchException jGroupsMuxIdAlreadyTaken(final short n) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), jGroupsMuxIdAlreadyTaken$str(), n));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void jGroupsFlushNotPresentInStack() {
        super.log.logf(FQCN, WARN, null, jGroupsFlushNotPresentInStack$str());
    }
    private static final String jGroupsFlushNotPresentInStack = "HSEARCH200010: FLUSH is not present in your JGroups stack! FLUSH is needed to ensure messages are not dropped while new nodes join the cluster. Will proceed, but inconsistencies may arise!";
    protected String jGroupsFlushNotPresentInStack$str() {
        return jGroupsFlushNotPresentInStack;
    }
    @Override
    public final void jgroupsFullConfiguration(final String printProtocolSpecAsXML) {
        super.log.logf(FQCN, DEBUG, null, jgroupsFullConfiguration$str(), printProtocolSpecAsXML);
    }
    private static final String jgroupsFullConfiguration = "HSEARCH200011: Using JGroups channel having configuration '%1$s'";
    protected String jgroupsFullConfiguration$str() {
        return jgroupsFullConfiguration;
    }
    @Override
    public final void jGroupsClosingChannelError(final Exception toLog) {
        super.log.logf(FQCN, ERROR, toLog, jGroupsClosingChannelError$str());
    }
    private static final String jGroupsClosingChannelError = "HSEARCH200012: Problem closing channel; setting it to null";
    protected String jGroupsClosingChannelError$str() {
        return jGroupsClosingChannelError;
    }
    private static final String jGroupsChannelInjectionError = "HSEARCH200013: Object injected for JGroups channel in %1$s is of an unexpected type %2$s (expecting org.jgroups.JChannel)";
    protected String jGroupsChannelInjectionError$str() {
        return jGroupsChannelInjectionError;
    }
    @Override
    public final SearchException jGroupsChannelInjectionError(final String channelInject, final Exception e, final Class<?> actualType) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), jGroupsChannelInjectionError$str(), channelInject, new org.hibernate.search.util.logging.impl.ClassFormatter(actualType)), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void startingJGroupsChannel(final Object cfg) {
        super.log.logf(FQCN, DEBUG, null, startingJGroupsChannel$str(), cfg);
    }
    private static final String startingJGroupsChannel = "HSEARCH200014: Starting JGroups channel using configuration '%1$s'";
    protected String startingJGroupsChannel$str() {
        return startingJGroupsChannel;
    }
    private static final String jGroupsChannelCreationUsingFileError = "HSEARCH200015: Error while trying to create a channel using config file: %1$s";
    protected String jGroupsChannelCreationUsingFileError$str() {
        return jGroupsChannelCreationUsingFileError;
    }
    @Override
    public final SearchException jGroupsChannelCreationUsingFileError(final String configuration, final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), jGroupsChannelCreationUsingFileError$str(), configuration), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void jGroupsConfigurationNotFoundInProperties(final Properties props) {
        super.log.logf(FQCN, INFO, null, jGroupsConfigurationNotFoundInProperties$str(), props);
    }
    private static final String jGroupsConfigurationNotFoundInProperties = "HSEARCH200016: Unable to use any JGroups configuration mechanisms provided in properties %1$s. Using default JGroups configuration file!";
    protected String jGroupsConfigurationNotFoundInProperties$str() {
        return jGroupsConfigurationNotFoundInProperties;
    }
    @Override
    public final void jGroupsDisconnectingAndClosingChannel(final String clusterName) {
        super.log.logf(FQCN, INFO, null, jGroupsDisconnectingAndClosingChannel$str(), clusterName);
    }
    private static final String jGroupsDisconnectingAndClosingChannel = "HSEARCH200017: Disconnecting and closing JGroups Channel to cluster '%1$s'";
    protected String jGroupsDisconnectingAndClosingChannel$str() {
        return jGroupsDisconnectingAndClosingChannel;
    }
    @Override
    public final void jGroupsDefaultConfigurationFileNotFound() {
        super.log.logf(FQCN, WARN, null, jGroupsDefaultConfigurationFileNotFound$str());
    }
    private static final String jGroupsDefaultConfigurationFileNotFound = "HSEARCH200018: Default JGroups configuration file was not found. Attempt to start JGroups channel with default configuration!";
    protected String jGroupsDefaultConfigurationFileNotFound$str() {
        return jGroupsDefaultConfigurationFileNotFound;
    }
    private static final String unableToStartJGroupsChannel = "HSEARCH200019: Unable to start JGroups channel";
    protected String unableToStartJGroupsChannel$str() {
        return unableToStartJGroupsChannel;
    }
    @Override
    public final SearchException unableToStartJGroupsChannel(final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableToStartJGroupsChannel$str()), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void jGroupsConnectedToCluster(final String clusterName, final Object address) {
        super.log.logf(FQCN, INFO, null, jGroupsConnectedToCluster$str(), clusterName, address);
    }
    private static final String jGroupsConnectedToCluster = "HSEARCH200020: Connected to cluster [ %1$s ]. The local Address is %2$s";
    protected String jGroupsConnectedToCluster$str() {
        return jGroupsConnectedToCluster;
    }
    private static final String unableConnectingToJGroupsCluster = "HSEARCH200021: Unable to connect to: [%1$s] JGroups channel";
    protected String unableConnectingToJGroupsCluster$str() {
        return unableConnectingToJGroupsCluster;
    }
    @Override
    public final SearchException unableConnectingToJGroupsCluster(final String clusterName, final Throwable e) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), unableConnectingToJGroupsCluster$str(), clusterName), e);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void jgroupsBlockWaitingForAck(final String indexName, final boolean block) {
        super.log.logf(FQCN, DEBUG, null, jgroupsBlockWaitingForAck$str(), indexName, block);
    }
    private static final String jgroupsBlockWaitingForAck = "HSEARCH200022: JGroups backend configured for index '%1$s' using block_for_ack '%2$s'";
    protected String jgroupsBlockWaitingForAck$str() {
        return jgroupsBlockWaitingForAck;
    }
    private static final String legacyJGroupsConfigurationDefined = "HSEARCH200023: JGroups channel configuration should be specified in the global section [hibernate.search.services.jgroups.], not as an IndexManager property for index '%1$s'. See http://docs.jboss.org/hibernate/search/5.0/reference/en-US/html_single/#jgroups-backend";
    protected String legacyJGroupsConfigurationDefined$str() {
        return legacyJGroupsConfigurationDefined;
    }
    @Override
    public final SearchException legacyJGroupsConfigurationDefined(final String indexName) {
        final SearchException result = new SearchException(String.format(getLoggingLocale(), legacyJGroupsConfigurationDefined$str(), indexName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void muxIdPropertyIsIgnored() {
        super.log.logf(FQCN, WARN, null, muxIdPropertyIsIgnored$str());
    }
    private static final String muxIdPropertyIsIgnored = "HSEARCH200024: The configuration property 'hibernate.search.services.jgroups.mux_id' is now ignored: JGroups 4 no longer supports Mux Channels. Use the FORK protocol instead.";
    protected String muxIdPropertyIsIgnored$str() {
        return muxIdPropertyIsIgnored;
    }
    @Override
    public final void acceptingNewClusterView(final View view, final Address masterAddress, final String indexName) {
        super.log.logf(FQCN, INFO, null, acceptingNewClusterView$str(), view, masterAddress, indexName);
    }
    private static final String acceptingNewClusterView = "HSEARCH200025: JGroups election: accepting new cluster view [%s]. Master is now %s for index '%s'";
    protected String acceptingNewClusterView$str() {
        return acceptingNewClusterView;
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
