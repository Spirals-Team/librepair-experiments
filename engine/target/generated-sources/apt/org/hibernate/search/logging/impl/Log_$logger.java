package org.hibernate.search.logging.impl;

import java.io.Serializable;
import javax.annotation.Generated;
import org.jboss.logging.DelegatingBasicLogger;
import org.hibernate.search.entity.model.spi.MappableTypeModel;
import org.hibernate.search.util.SearchException;
import java.lang.String;
import org.jboss.logging.Logger;
import java.lang.Exception;
import org.hibernate.search.spatial.GeoPoint;
import org.jboss.logging.BasicLogger;
import java.lang.Object;
import java.lang.Class;
import java.util.List;
import java.util.Arrays;
import java.lang.IllegalArgumentException;

/**
 * Warning this class consists of generated code.
 */
@Generated(value = "org.jboss.logging.processor.generator.model.MessageLoggerImplementor", date = "2018-06-01T10:38:53+0200")
public class Log_$logger extends DelegatingBasicLogger implements Log,BasicLogger,Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FQCN = Log_$logger.class.getName();
    public Log_$logger(final Logger log) {
        super(log);
    }
    private static final String unableToConvertConfigurationProperty = "HSEARCH000001: Unable to convert configuration property '%1$s' with value '%2$s': %3$s";
    protected String unableToConvertConfigurationProperty$str() {
        return unableToConvertConfigurationProperty;
    }
    @Override
    public final SearchException unableToConvertConfigurationProperty(final String key, final Object rawValue, final String errorMessage, final Exception cause) {
        final SearchException result = new SearchException(String.format(unableToConvertConfigurationProperty$str(), key, rawValue, errorMessage), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidPropertyValue = "HSEARCH000002: Invalid value: expected either an instance of '%1$s' or a parsable String.";
    protected String invalidPropertyValue$str() {
        return invalidPropertyValue;
    }
    @Override
    public final SearchException invalidPropertyValue(final Class<? extends Object> expectedType, final Exception cause) {
        final SearchException result = new SearchException(String.format(invalidPropertyValue$str(), expectedType), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidBooleanPropertyValue = "HSEARCH000003: Invalid boolean value: expected either a Boolean, the String 'true' or the String 'false'.";
    protected String invalidBooleanPropertyValue$str() {
        return invalidBooleanPropertyValue;
    }
    @Override
    public final SearchException invalidBooleanPropertyValue(final Exception cause) {
        final SearchException result = new SearchException(String.format(invalidBooleanPropertyValue$str()), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidIntegerPropertyValue = "HSEARCH000004: %1$s";
    protected String invalidIntegerPropertyValue$str() {
        return invalidIntegerPropertyValue;
    }
    @Override
    public final SearchException invalidIntegerPropertyValue(final String errorMessage, final Exception cause) {
        final SearchException result = new SearchException(String.format(invalidIntegerPropertyValue$str(), errorMessage), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidLongPropertyValue = "HSEARCH000005: %1$s";
    protected String invalidLongPropertyValue$str() {
        return invalidLongPropertyValue;
    }
    @Override
    public final SearchException invalidLongPropertyValue(final String errorMessage, final Exception cause) {
        final SearchException result = new SearchException(String.format(invalidLongPropertyValue$str(), errorMessage), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidMultiPropertyValue = "HSEARCH000006: Invalid multi value: expected either a Collection or a String.";
    protected String invalidMultiPropertyValue$str() {
        return invalidMultiPropertyValue;
    }
    @Override
    public final SearchException invalidMultiPropertyValue() {
        final SearchException result = new SearchException(String.format(invalidMultiPropertyValue$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotAddMultiplePredicatesToQueryRoot = "HSEARCH000007: Cannot add multiple predicates to the query root; use an explicit boolean predicate instead.";
    protected String cannotAddMultiplePredicatesToQueryRoot$str() {
        return cannotAddMultiplePredicatesToQueryRoot;
    }
    @Override
    public final SearchException cannotAddMultiplePredicatesToQueryRoot() {
        final SearchException result = new SearchException(String.format(cannotAddMultiplePredicatesToQueryRoot$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotAddMultiplePredicatesToNestedPredicate = "HSEARCH000009: Cannot add multiple predicates to a nested predicate; use an explicit boolean predicate instead.";
    protected String cannotAddMultiplePredicatesToNestedPredicate$str() {
        return cannotAddMultiplePredicatesToNestedPredicate;
    }
    @Override
    public final SearchException cannotAddMultiplePredicatesToNestedPredicate() {
        final SearchException result = new SearchException(String.format(cannotAddMultiplePredicatesToNestedPredicate$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String matchPredicateCannotMatchNullValue = "HSEARCH000011: Invalid value: the value to match in match predicates must be non-null. Null value was passed to match predicate on fields %1$s";
    protected String matchPredicateCannotMatchNullValue$str() {
        return matchPredicateCannotMatchNullValue;
    }
    @Override
    public final SearchException matchPredicateCannotMatchNullValue(final List<String> strings) {
        final SearchException result = new SearchException(String.format(matchPredicateCannotMatchNullValue$str(), strings));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String rangePredicateCannotMatchNullValue = "HSEARCH000012: Invalid value: at least one bound in range predicates must be non-null. Null bounds were passed to range predicate on fields %1$s";
    protected String rangePredicateCannotMatchNullValue$str() {
        return rangePredicateCannotMatchNullValue;
    }
    @Override
    public final SearchException rangePredicateCannotMatchNullValue(final List<String> strings) {
        final SearchException result = new SearchException(String.format(rangePredicateCannotMatchNullValue$str(), strings));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotMapAbstractTypeToIndex = "HSEARCH000013: Cannot map type '%1$s' to index '%2$s', because this type is abstract. Index mappings are not inherited: they apply to exact instances of a given type. As a result, mapping an abstract type to an index does not make sense, since the index would always be empty.";
    protected String cannotMapAbstractTypeToIndex$str() {
        return cannotMapAbstractTypeToIndex;
    }
    @Override
    public final SearchException cannotMapAbstractTypeToIndex(final MappableTypeModel typeModel, final String indexName) {
        final SearchException result = new SearchException(String.format(cannotMapAbstractTypeToIndex$str(), typeModel, indexName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String relativeFieldNameCannotBeNullOrEmpty = "HSEARCH000014: Field name '%1$s' is invalid: field names cannot be null or empty.";
    protected String relativeFieldNameCannotBeNullOrEmpty$str() {
        return relativeFieldNameCannotBeNullOrEmpty;
    }
    @Override
    public final SearchException relativeFieldNameCannotBeNullOrEmpty(final String relativeFieldName) {
        final SearchException result = new SearchException(String.format(relativeFieldNameCannotBeNullOrEmpty$str(), relativeFieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String relativeFieldNameCannotContainDot = "HSEARCH000015: Field name '%1$s' is invalid: field names cannot contain a dot ('.'). Remove the dot from your field name, or if you are declaring the field in a bridge and want a tree of fields, declare an object field using the objectField() method.";
    protected String relativeFieldNameCannotContainDot$str() {
        return relativeFieldNameCannotContainDot;
    }
    @Override
    public final SearchException relativeFieldNameCannotContainDot(final String relativeFieldName) {
        final SearchException result = new SearchException(String.format(relativeFieldNameCannotContainDot$str(), relativeFieldName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidGeoPolygonFirstPointNotIdenticalToLastPoint = "HSEARCH000016: Invalid polygon: the first point '%1$s' should be identical to the last point '%2$s' to properly close the polygon.";
    protected String invalidGeoPolygonFirstPointNotIdenticalToLastPoint$str() {
        return invalidGeoPolygonFirstPointNotIdenticalToLastPoint;
    }
    @Override
    public final IllegalArgumentException invalidGeoPolygonFirstPointNotIdenticalToLastPoint(final GeoPoint firstPoint, final GeoPoint lastPoint) {
        final IllegalArgumentException result = new IllegalArgumentException(String.format(invalidGeoPolygonFirstPointNotIdenticalToLastPoint$str(), firstPoint, lastPoint));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
}
