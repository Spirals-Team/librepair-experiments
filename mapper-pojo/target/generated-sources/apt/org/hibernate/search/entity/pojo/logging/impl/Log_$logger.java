package org.hibernate.search.entity.pojo.logging.impl;

import org.hibernate.search.entity.pojo.model.spi.PojoGenericTypeModel;
import org.hibernate.search.entity.pojo.model.path.PojoModelPathValueNode;
import java.io.Serializable;
import org.hibernate.search.entity.pojo.model.spi.PojoTypeModel;
import javax.annotation.Generated;
import java.util.Set;
import org.jboss.logging.DelegatingBasicLogger;
import org.hibernate.search.entity.pojo.bridge.ValueBridge;
import org.hibernate.search.entity.pojo.mapping.impl.PojoContainedTypeManager;
import org.hibernate.search.util.SearchException;
import java.lang.String;
import org.jboss.logging.Logger;
import java.lang.Exception;
import java.lang.reflect.Type;
import org.jboss.logging.BasicLogger;
import java.lang.Throwable;
import java.lang.Class;
import java.util.Arrays;
import org.hibernate.search.entity.pojo.model.spi.PojoRawTypeModel;
import org.hibernate.search.entity.pojo.mapping.impl.PojoIndexedTypeManager;

/**
 * Warning this class consists of generated code.
 */
@Generated(value = "org.jboss.logging.processor.generator.model.MessageLoggerImplementor", date = "2018-06-01T10:38:58+0200")
public class Log_$logger extends DelegatingBasicLogger implements Log,BasicLogger,Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FQCN = Log_$logger.class.getName();
    public Log_$logger(final Logger log) {
        super(log);
    }
    private static final String unableToResolveDefaultIdentifierBridgeFromSourceType = "HSEARCH-POJO000001: Unable to find a default identifier bridge implementation for type '%1$s'";
    protected String unableToResolveDefaultIdentifierBridgeFromSourceType$str() {
        return unableToResolveDefaultIdentifierBridgeFromSourceType;
    }
    @Override
    public final SearchException unableToResolveDefaultIdentifierBridgeFromSourceType(final Class<? extends Object> sourceType) {
        final SearchException result = new SearchException(String.format(unableToResolveDefaultIdentifierBridgeFromSourceType$str(), sourceType));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToResolveDefaultValueBridgeFromSourceType = "HSEARCH-POJO000002: Unable to find a default value bridge implementation for type '%1$s'";
    protected String unableToResolveDefaultValueBridgeFromSourceType$str() {
        return unableToResolveDefaultValueBridgeFromSourceType;
    }
    @Override
    public final SearchException unableToResolveDefaultValueBridgeFromSourceType(final Class<? extends Object> sourceType) {
        final SearchException result = new SearchException(String.format(unableToResolveDefaultValueBridgeFromSourceType$str(), sourceType));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String missingBuilderReferenceInBridgeMapping = "HSEARCH-POJO000003: Annotation type '%1$s' is annotated with @PropertyBridgeMapping, but the bridge builder reference is empty.";
    protected String missingBuilderReferenceInBridgeMapping$str() {
        return missingBuilderReferenceInBridgeMapping;
    }
    @Override
    public final SearchException missingBuilderReferenceInBridgeMapping(final Class<? extends java.lang.annotation.Annotation> annotationType) {
        final SearchException result = new SearchException(String.format(missingBuilderReferenceInBridgeMapping$str(), annotationType));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String missingBuilderReferenceInMarkerMapping = "HSEARCH-POJO000004: Annotation type '%1$s' is annotated with @MarkerMapping, but the marker builder reference is empty.";
    protected String missingBuilderReferenceInMarkerMapping$str() {
        return missingBuilderReferenceInMarkerMapping;
    }
    @Override
    public final SearchException missingBuilderReferenceInMarkerMapping(final Class<? extends java.lang.annotation.Annotation> annotationType) {
        final SearchException result = new SearchException(String.format(missingBuilderReferenceInMarkerMapping$str(), annotationType));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidFieldDefiningBothBridgeReferenceAndBridgeBuilderReference = "HSEARCH-POJO000005: Annotation @Field on property '%1$s' defines both valueBridge and valueBridgeBuilder. Only one of those can be defined, not both.";
    protected String invalidFieldDefiningBothBridgeReferenceAndBridgeBuilderReference$str() {
        return invalidFieldDefiningBothBridgeReferenceAndBridgeBuilderReference;
    }
    @Override
    public final SearchException invalidFieldDefiningBothBridgeReferenceAndBridgeBuilderReference(final String property) {
        final SearchException result = new SearchException(String.format(invalidFieldDefiningBothBridgeReferenceAndBridgeBuilderReference$str(), property));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidDocumentIdDefiningBothBridgeReferenceAndBridgeBuilderReference = "HSEARCH-POJO000006: Annotation @DocumentId on property '%1$s' defines both identifierBridge and identifierBridgeBuilder. Only one of those can be defined, not both.";
    protected String invalidDocumentIdDefiningBothBridgeReferenceAndBridgeBuilderReference$str() {
        return invalidDocumentIdDefiningBothBridgeReferenceAndBridgeBuilderReference;
    }
    @Override
    public final SearchException invalidDocumentIdDefiningBothBridgeReferenceAndBridgeBuilderReference(final String property) {
        final SearchException result = new SearchException(String.format(invalidDocumentIdDefiningBothBridgeReferenceAndBridgeBuilderReference$str(), property));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotSearchOnEmptyTarget = "HSEARCH-POJO000007: Cannot query on an empty target. If you want to target all indexes, put Object.class in the collection of target types, or use the method of the same name, but without Class<?> parameters.";
    protected String cannotSearchOnEmptyTarget$str() {
        return cannotSearchOnEmptyTarget;
    }
    @Override
    public final SearchException cannotSearchOnEmptyTarget() {
        final SearchException result = new SearchException(String.format(cannotSearchOnEmptyTarget$str()));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToInferValueBridgeInputType = "HSEARCH-POJO000008: Could not auto-detect the input type for value bridge %1$s; make sure the bridge uses generics.";
    protected String unableToInferValueBridgeInputType$str() {
        return unableToInferValueBridgeInputType;
    }
    @Override
    public final SearchException unableToInferValueBridgeInputType(final ValueBridge<? extends Object, ? extends Object> bridge) {
        final SearchException result = new SearchException(String.format(unableToInferValueBridgeInputType$str(), bridge));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String unableToInferValueBridgeIndexFieldType = "HSEARCH-POJO000009: Could not auto-detect the return type for value bridge %1$s; make sure the bridge uses generics or configure the field explicitly in the bridge's bind() method.";
    protected String unableToInferValueBridgeIndexFieldType$str() {
        return unableToInferValueBridgeIndexFieldType;
    }
    @Override
    public final SearchException unableToInferValueBridgeIndexFieldType(final ValueBridge<? extends Object, ? extends Object> bridge) {
        final SearchException result = new SearchException(String.format(unableToInferValueBridgeIndexFieldType$str(), bridge));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidInputTypeForValueBridge = "HSEARCH-POJO000010: Value bridge %1$s cannot be applied to input type %2$s.";
    protected String invalidInputTypeForValueBridge$str() {
        return invalidInputTypeForValueBridge;
    }
    @Override
    public final SearchException invalidInputTypeForValueBridge(final ValueBridge<? extends Object, ? extends Object> bridge, final PojoTypeModel<? extends Object> typeModel) {
        final SearchException result = new SearchException(String.format(invalidInputTypeForValueBridge$str(), bridge, typeModel));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String missingFieldNameForGeoPointBridgeOnType = "HSEARCH-POJO000011: Missing field name for GeoPointBridge on type %1$s. The field name is mandatory when the bridge is applied on an type, optional when applied on a property.";
    protected String missingFieldNameForGeoPointBridgeOnType$str() {
        return missingFieldNameForGeoPointBridgeOnType;
    }
    @Override
    public final SearchException missingFieldNameForGeoPointBridgeOnType(final String typeName) {
        final SearchException result = new SearchException(String.format(missingFieldNameForGeoPointBridgeOnType$str(), typeName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotRequestTypeParameterOfUnparameterizedType = "HSEARCH-POJO000012: Requested type argument %3$s to type %2$s in implementing type %1$s, but %2$s doesn't declare any type parameter";
    protected String cannotRequestTypeParameterOfUnparameterizedType$str() {
        return cannotRequestTypeParameterOfUnparameterizedType;
    }
    @Override
    public final SearchException cannotRequestTypeParameterOfUnparameterizedType(final Type type, final Class<? extends Object> rawSuperType, final int typeArgumentIndex) {
        final SearchException result = new SearchException(String.format(cannotRequestTypeParameterOfUnparameterizedType$str(), type, rawSuperType, typeArgumentIndex));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String typeParameterIndexOutOfBound = "HSEARCH-POJO000013: Requested type argument %3$s to type %2$s in implementing type %1$s, but %2$s only declares %4$s type parameter(s)";
    protected String typeParameterIndexOutOfBound$str() {
        return typeParameterIndexOutOfBound;
    }
    @Override
    public final SearchException typeParameterIndexOutOfBound(final Type type, final Class<? extends Object> rawSuperType, final int typeArgumentIndex, final int typeParametersLength) {
        final SearchException result = new SearchException(String.format(typeParameterIndexOutOfBound$str(), type, rawSuperType, typeArgumentIndex, typeParametersLength));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidTypeParameterIndex = "HSEARCH-POJO000014: Requested type argument index %3$s to type %2$s in implementing type %1$s should be 0 or greater";
    protected String invalidTypeParameterIndex$str() {
        return invalidTypeParameterIndex;
    }
    @Override
    public final SearchException invalidTypeParameterIndex(final Type type, final Class<? extends Object> rawSuperType, final int typeArgumentIndex) {
        final SearchException result = new SearchException(String.format(invalidTypeParameterIndex$str(), type, rawSuperType, typeArgumentIndex));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String couldNotInferContainerValueExtractorClassTypePattern = "HSEARCH-POJO000015: Could not interpret the type arguments to the ContainerValueExtractor interface in  implementation '%1$s'. Only the following implementations of ContainerValueExtractor are valid:  1) implementations setting both type parameters to *raw* types, e.g. class MyExtractor implements ContainerValueExtractor<MyBean, String>; 2) implementations setting the first type parameter to an array of an unbounded type variable, and setting the second parameter to the same type variable, e.g. MyExtractor<T> implements ContainerValueExtractor<T[], T> 3) implementations setting the first type parameter to a parameterized type with one argument set to an unbounded type variable and the other to unbounded wildcards, and setting the second type parameter to the same type variable, e.g. MyExtractor<T> implements ContainerValueExtractor<MyParameterizedBean<?, T, ?>, T>";
    protected String couldNotInferContainerValueExtractorClassTypePattern$str() {
        return couldNotInferContainerValueExtractorClassTypePattern;
    }
    @Override
    public final SearchException couldNotInferContainerValueExtractorClassTypePattern(final Class<? extends Object> extractorClass) {
        final SearchException result = new SearchException(String.format(couldNotInferContainerValueExtractorClassTypePattern$str(), extractorClass));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String invalidContainerValueExtractorForType = "HSEARCH-POJO000016: Could not apply the requested container value extractor '%1$s' to type '%2$s'";
    protected String invalidContainerValueExtractorForType$str() {
        return invalidContainerValueExtractorForType;
    }
    @Override
    public final SearchException invalidContainerValueExtractorForType(final Class<? extends org.hibernate.search.entity.pojo.extractor.ContainerValueExtractor> extractorClass, final PojoGenericTypeModel<? extends Object> extractedType) {
        final SearchException result = new SearchException(String.format(invalidContainerValueExtractorForType$str(), extractorClass, extractedType));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void createdPojoIndexedTypeManager(final PojoIndexedTypeManager<? extends Object, ? extends Object, ? extends Object> typeManager) {
        super.log.logf(FQCN, org.jboss.logging.Logger.Level.DEBUG, null, createdPojoIndexedTypeManager$str(), new org.hibernate.search.util.impl.common.logging.ToStringTreeAppendableMultilineFormatter(typeManager));
    }
    private static final String createdPojoIndexedTypeManager = "HSEARCH-POJO000017: Created POJO indexed type manager: %1$s";
    protected String createdPojoIndexedTypeManager$str() {
        return createdPojoIndexedTypeManager;
    }
    @Override
    public final void detectedEntityTypes(final Set<PojoRawTypeModel<? extends Object>> entityTypes) {
        super.log.logf(FQCN, org.jboss.logging.Logger.Level.DEBUG, null, detectedEntityTypes$str(), entityTypes);
    }
    private static final String detectedEntityTypes = "HSEARCH-POJO000018: Detected entity types: %1$s";
    protected String detectedEntityTypes$str() {
        return detectedEntityTypes;
    }
    @Override
    public final void createdPojoContainedTypeManager(final PojoContainedTypeManager<? extends Object> typeManager) {
        super.log.logf(FQCN, org.jboss.logging.Logger.Level.DEBUG, null, createdPojoContainedTypeManager$str(), new org.hibernate.search.util.impl.common.logging.ToStringTreeAppendableMultilineFormatter(typeManager));
    }
    private static final String createdPojoContainedTypeManager = "HSEARCH-POJO000019: Created POJO contained type manager: %1$s";
    protected String createdPojoContainedTypeManager$str() {
        return createdPojoContainedTypeManager;
    }
    private static final String cannotInvertAssociation = "HSEARCH-POJO000020: Could not find the inverse side of the association '%3$s' from type '%2$s' on type '%1$s'";
    protected String cannotInvertAssociation$str() {
        return cannotInvertAssociation;
    }
    @Override
    public final SearchException cannotInvertAssociation(final PojoRawTypeModel<? extends Object> inverseSideTypeModel, final PojoRawTypeModel<? extends Object> typeModel, final PojoModelPathValueNode associationPath) {
        final SearchException result = new SearchException(String.format(cannotInvertAssociation$str(), inverseSideTypeModel, typeModel, associationPath));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String cannotApplyInvertAssociationPath = "HSEARCH-POJO000021: Could not apply the path of the inverse association '%2$s' to type '%1$s'. Association on the original side (which was inverted) was '%4$s' on type '%3$s'. Error was: '%5$s'";
    protected String cannotApplyInvertAssociationPath$str() {
        return cannotApplyInvertAssociationPath;
    }
    @Override
    public final SearchException cannotApplyInvertAssociationPath(final PojoRawTypeModel<? extends Object> inverseSideTypeModel, final PojoModelPathValueNode inverseSideAssociationPath, final PojoRawTypeModel<? extends Object> originalSideTypeModel, final PojoModelPathValueNode originalSideAssociationPath, final String errorMessage, final Exception cause) {
        final SearchException result = new SearchException(String.format(cannotApplyInvertAssociationPath$str(), inverseSideTypeModel, inverseSideAssociationPath, originalSideTypeModel, originalSideAssociationPath, errorMessage), cause);
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String incorrectTargetTypeForInverseAssociation = "HSEARCH-POJO000022: The inverse association targets type '%1$s', but a supertype or subtype of '%2$s' was expected.";
    protected String incorrectTargetTypeForInverseAssociation$str() {
        return incorrectTargetTypeForInverseAssociation;
    }
    @Override
    public final SearchException incorrectTargetTypeForInverseAssociation(final PojoRawTypeModel<? extends Object> inverseAssociationTargetType, final PojoRawTypeModel<? extends Object> entityType) {
        final SearchException result = new SearchException(String.format(incorrectTargetTypeForInverseAssociation$str(), inverseAssociationTargetType, entityType));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String missingInversePathInAssociationInverseSideMapping = "HSEARCH-POJO000023: Property '%2$s' from type '%1$s' is annotated with @AnnotationInverseSide, but the inverse path is empty.";
    protected String missingInversePathInAssociationInverseSideMapping$str() {
        return missingInversePathInAssociationInverseSideMapping;
    }
    @Override
    public final SearchException missingInversePathInAssociationInverseSideMapping(final PojoRawTypeModel<? extends Object> typeModel, final String propertyName) {
        final SearchException result = new SearchException(String.format(missingInversePathInAssociationInverseSideMapping$str(), typeModel, propertyName));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    private static final String infiniteRecursionForAssociationEmbeddeds = "HSEARCH-POJO000024: Found an infinite embedded recursion involving path '%2$s' on type '%1$s'";
    protected String infiniteRecursionForAssociationEmbeddeds$str() {
        return infiniteRecursionForAssociationEmbeddeds;
    }
    @Override
    public final SearchException infiniteRecursionForAssociationEmbeddeds(final PojoRawTypeModel<? extends Object> typeModel, final PojoModelPathValueNode path) {
        final SearchException result = new SearchException(String.format(infiniteRecursionForAssociationEmbeddeds$str(), typeModel, path));
        final StackTraceElement[] st = result.getStackTrace();
        result.setStackTrace(Arrays.copyOfRange(st, 1, st.length));
        return result;
    }
    @Override
    public final void cannotAccessRepeateableContainingAnnotationValue(final Class<? extends Object> containingAnnotationType, final Throwable e) {
        super.log.logf(FQCN, org.jboss.logging.Logger.Level.INFO, e, cannotAccessRepeateableContainingAnnotationValue$str(), containingAnnotationType);
    }
    private static final String cannotAccessRepeateableContainingAnnotationValue = "HSEARCH-POJO000025: Cannot access the value of containing annotation '%1$s'. Ignoring annotation.";
    protected String cannotAccessRepeateableContainingAnnotationValue$str() {
        return cannotAccessRepeateableContainingAnnotationValue;
    }
}
