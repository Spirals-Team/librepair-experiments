package org.hibernate.search.entity.orm.logging.impl;

import java.io.Serializable;
import javax.annotation.Generated;
import java.util.Set;
import org.jboss.logging.DelegatingBasicLogger;
import org.hibernate.property.access.spi.Getter;
import org.jboss.logging.BasicLogger;
import org.hibernate.search.util.SearchException;
import java.util.Collection;
import java.lang.Class;
import java.lang.String;
import org.jboss.logging.Logger;
import java.util.Arrays;

/**
 * Warning this class consists of generated code.
 */
@Generated(value = "org.jboss.logging.processor.generator.model.MessageLoggerImplementor", date = "2018-06-01T10:39:01+0200")
public class Log_$logger extends DelegatingBasicLogger implements Log,BasicLogger,Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FQCN = Log_$logger.class.getName();
    public Log_$logger(final Logger log) {
        super(log);
    }
    private static final String hibernateSearchNotInitialized = "HSEARCH-ORM000001: Hibernate Search was not initialized.";
    protected String hibernateSearchNotInitialized$str() {
        return hibernateSearchNotInitialized;
    }
    @Override
    public final SearchException hibernateSearchNotInitialized() {
        final SearchException result = new SearchException(String.format(hibernateSearchNotInitialized$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unexpectedSearchHitType = "HSEARCH-ORM000002: Unexpected entity type for a query hit: %1$s. Expected one of %2$s.";
    protected String unexpectedSearchHitType$str() {
        return unexpectedSearchHitType;
    }
    @Override
    public final SearchException unexpectedSearchHitType(final Class<? extends Object> entityType, final Collection<? extends Class<? extends Object>> expectedTypes) {
        final SearchException result = new SearchException(String.format(unexpectedSearchHitType$str(), entityType, expectedTypes));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownIndexingMode = "HSEARCH-ORM000003: Unknown indexing mode: %1$s";
    protected String unknownIndexingMode$str() {
        return unknownIndexingMode;
    }
    @Override
    public final SearchException unknownIndexingMode(final String indexingMode) {
        final SearchException result = new SearchException(String.format(unknownIndexingMode$str(), indexingMode));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unknownPropertyForGetter = "HSEARCH-ORM000004: Could not retrieve metadata for type %1$s, property '%2$s' accessed through getter '%3$s'";
    protected String unknownPropertyForGetter$str() {
        return unknownPropertyForGetter;
    }
    @Override
    public final SearchException unknownPropertyForGetter(final Class<? extends Object> entityType, final String propertyName, final Getter getter) {
        final SearchException result = new SearchException(String.format(unknownPropertyForGetter$str(), entityType, propertyName, getter));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void configurationPropertyTrackingDisabled() {
        super.log.logf(FQCN, org.jboss.logging.Logger.Level.INFO, null, configurationPropertyTrackingDisabled$str());
    }
    private static final String configurationPropertyTrackingDisabled = "HSEARCH-ORM000005: Configuration property tracking is disabled; unused properties will not be logged.";
    protected String configurationPropertyTrackingDisabled$str() {
        return configurationPropertyTrackingDisabled;
    }
    @Override
    public final void configurationPropertyTrackingUnusedProperties(final Set<String> propertyKeys) {
        super.log.logf(FQCN, org.jboss.logging.Logger.Level.WARN, null, configurationPropertyTrackingUnusedProperties$str(), propertyKeys);
    }
    private static final String configurationPropertyTrackingUnusedProperties = "HSEARCH-ORM000006: Some properties in the Hibernate Search configuration were not used; there might be misspelled property keys in your configuration. Unused properties were: %1$s. To disable this warning, set 'hibernate.search.enable_configuration_property_tracking' to false.";
    protected String configurationPropertyTrackingUnusedProperties$str() {
        return configurationPropertyTrackingUnusedProperties;
    }
}
