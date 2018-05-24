package spoon.reflect.declaration;


public interface CtAnnotation<A extends java.lang.annotation.Annotation> extends spoon.reflect.code.CtExpression<A> , spoon.reflect.declaration.CtShadowable {
    @spoon.support.DerivedProperty
    A getActualAnnotation();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ANNOTATION_TYPE)
    spoon.reflect.reference.CtTypeReference<A> getAnnotationType();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.VALUE)
    <T extends spoon.reflect.code.CtExpression> T getValue(java.lang.String key);

    @spoon.support.DerivedProperty
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.VALUE)
    <T extends spoon.reflect.code.CtExpression> T getWrappedValue(java.lang.String key);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.VALUE)
    java.util.Map<java.lang.String, spoon.reflect.code.CtExpression> getValues();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ANNOTATION_TYPE)
    <T extends spoon.reflect.declaration.CtAnnotation<A>> T setAnnotationType(spoon.reflect.reference.CtTypeReference<? extends java.lang.annotation.Annotation> type);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <T extends spoon.reflect.declaration.CtAnnotation<A>> T setElementValues(java.util.Map<java.lang.String, java.lang.Object> values);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <T extends spoon.reflect.declaration.CtAnnotation<A>> T setValues(java.util.Map<java.lang.String, spoon.reflect.code.CtExpression> values);

    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtElement getAnnotatedElement();

    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtAnnotatedElementType getAnnotatedElementType();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <T extends spoon.reflect.declaration.CtAnnotation<A>> T addValue(java.lang.String elementName, java.lang.Object value);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <T extends spoon.reflect.declaration.CtAnnotation<A>> T addValue(java.lang.String elementName, spoon.reflect.code.CtLiteral<?> value);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <T extends spoon.reflect.declaration.CtAnnotation<A>> T addValue(java.lang.String elementName, spoon.reflect.code.CtNewArray<? extends spoon.reflect.code.CtExpression> value);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <T extends spoon.reflect.declaration.CtAnnotation<A>> T addValue(java.lang.String elementName, spoon.reflect.code.CtFieldAccess<?> value);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <T extends spoon.reflect.declaration.CtAnnotation<A>> T addValue(java.lang.String elementName, spoon.reflect.declaration.CtAnnotation<?> value);

    @java.lang.Override
    spoon.reflect.declaration.CtAnnotation<A> clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.code.CtExpression<A>> C setTypeCasts(java.util.List<spoon.reflect.reference.CtTypeReference<?>> types);

    static spoon.reflect.declaration.CtAnnotatedElementType getAnnotatedElementTypeForCtElement(spoon.reflect.declaration.CtElement element) {
        if (element == null) {
            return null;
        }
        if (element instanceof spoon.reflect.declaration.CtMethod) {
            return spoon.reflect.declaration.CtAnnotatedElementType.METHOD;
        }
        if ((element instanceof spoon.reflect.declaration.CtAnnotation) || (element instanceof spoon.reflect.declaration.CtAnnotationType)) {
            return spoon.reflect.declaration.CtAnnotatedElementType.ANNOTATION_TYPE;
        }
        if (element instanceof spoon.reflect.declaration.CtType) {
            return spoon.reflect.declaration.CtAnnotatedElementType.TYPE;
        }
        if (element instanceof spoon.reflect.declaration.CtField) {
            return spoon.reflect.declaration.CtAnnotatedElementType.FIELD;
        }
        if (element instanceof spoon.reflect.declaration.CtConstructor) {
            return spoon.reflect.declaration.CtAnnotatedElementType.CONSTRUCTOR;
        }
        if (element instanceof spoon.reflect.declaration.CtParameter) {
            return spoon.reflect.declaration.CtAnnotatedElementType.PARAMETER;
        }
        if (element instanceof spoon.reflect.code.CtLocalVariable) {
            return spoon.reflect.declaration.CtAnnotatedElementType.LOCAL_VARIABLE;
        }
        if (element instanceof spoon.reflect.declaration.CtPackage) {
            return spoon.reflect.declaration.CtAnnotatedElementType.PACKAGE;
        }
        if (element instanceof spoon.reflect.reference.CtTypeParameterReference) {
            return spoon.reflect.declaration.CtAnnotatedElementType.TYPE_PARAMETER;
        }
        if (element instanceof spoon.reflect.reference.CtTypeReference) {
            return spoon.reflect.declaration.CtAnnotatedElementType.TYPE_USE;
        }
        return null;
    }
}

