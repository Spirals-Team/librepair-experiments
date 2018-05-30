/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.reflect.declaration;


/**
 * This element represents an annotation on an element.
 *
 * <pre>
 *     // statement annotated by annotation @SuppressWarnings
 *     &#64;SuppressWarnings("unchecked")
 *     java.util.List&lt;?&gt; x = new java.util.ArrayList&lt;&gt;()
 * </pre>
 *
 * @param <A>
 * 		type of represented annotation
 */
public interface CtAnnotation<A extends java.lang.annotation.Annotation> extends spoon.reflect.code.CtExpression<A> , spoon.reflect.declaration.CtShadowable {
    /**
     * Returns the actual annotation (a dynamic proxy for this element).
     *
     * <p>
     * NOTE: before using an annotation proxy, you have to make sure that all
     * the types referenced by the annotation have been compiled and are in the
     * classpath so that accessed values can be converted into the actual types.
     */
    @spoon.support.DerivedProperty
    A getActualAnnotation();

    /**
     * Returns the annotation type of this annotation.
     *
     * @return a reference to the type of this annotation
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ANNOTATION_TYPE)
    spoon.reflect.reference.CtTypeReference<A> getAnnotationType();

    /**
     * Gets a value for a given key without any conversion.
     * Note that this value type does not necessarily corresponds to the annotation
     * type member. For example, in case the annotation type expect an array of Object,
     * and a single value is given, Spoon will return only the object without the CtNewArray.
     * If you want to get a type closer to the annotation type one, see {@link #getWrappedValue(String)}.
     *
     * @param key
     * 		Name of searched value.
     * @return the value expression or null if not found.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.VALUE)
    <T extends spoon.reflect.code.CtExpression> T getValue(java.lang.String key);

    /**
     * Gets a value for a given key and try to fix its type based on the
     * annotation type. For example, if the annotation type member expects an array of String,
     * and it can be resolved, this method will return a CtNewArray instead of a CtLiteral.
     *
     * Warning: the returned element might be detached from the model
     *
     * @param key
     * 		Name of searched value.
     * @return the value expression or null if not found.
     */
    @spoon.support.DerivedProperty
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.VALUE)
    <T extends spoon.reflect.code.CtExpression> T getWrappedValue(java.lang.String key);

    /**
     * Returns this annotation's elements and their values. This is returned in
     * the form of a map that associates element names with their corresponding
     * values. If you iterate over the map with entrySet(), the iteration order
     * complies with the order of annotation values in the source code.
     *
     * @return this annotation's element names and their values, or an empty map
     * if there are none
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.VALUE)
    java.util.Map<java.lang.String, spoon.reflect.code.CtExpression> getValues();

    /**
     * Sets the annotation's type.
     *
     * @param type
     * 		reference to the type of this annotation
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ANNOTATION_TYPE)
    <T extends spoon.reflect.declaration.CtAnnotation<A>> T setAnnotationType(spoon.reflect.reference.CtTypeReference<? extends java.lang.annotation.Annotation> type);

    /**
     * Set's this annotation's element names and their values. This is in the
     * form of a map that associates element names with their corresponding
     * values. Note that type values are stored as
     * {@link spoon.reflect.reference.CtTypeReference}.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <T extends spoon.reflect.declaration.CtAnnotation<A>> T setElementValues(java.util.Map<java.lang.String, java.lang.Object> values);

    /**
     * Set's this annotation's element names and their values. This is in the
     * form of a map that associates element names with their corresponding
     * values.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <T extends spoon.reflect.declaration.CtAnnotation<A>> T setValues(java.util.Map<java.lang.String, spoon.reflect.code.CtExpression> values);

    /**
     * Returns the element which is annotated by this annotation.
     *
     * @return annotated {@link spoon.reflect.declaration.CtElement}
     */
    // the annotation is contained by the element not the other way around
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtElement getAnnotatedElement();

    /**
     * Returns the type of the element which is annotated by this annotation.
     *
     * @return {@link spoon.reflect.declaration.CtAnnotatedElementType}
     */
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtAnnotatedElementType getAnnotatedElementType();

    /**
     * Adds a new key-value pair for this annotation
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <T extends spoon.reflect.declaration.CtAnnotation<A>> T addValue(java.lang.String elementName, java.lang.Object value);

    /**
     * Adds a new key-literal pair for this annotation.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <T extends spoon.reflect.declaration.CtAnnotation<A>> T addValue(java.lang.String elementName, spoon.reflect.code.CtLiteral<?> value);

    /**
     * Adds a new key-array pair for this annotation.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <T extends spoon.reflect.declaration.CtAnnotation<A>> T addValue(java.lang.String elementName, spoon.reflect.code.CtNewArray<? extends spoon.reflect.code.CtExpression> value);

    /**
     * Adds a new key-field access pair for this annotation.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VALUE)
    <T extends spoon.reflect.declaration.CtAnnotation<A>> T addValue(java.lang.String elementName, spoon.reflect.code.CtFieldAccess<?> value);

    /**
     * Adds a new key-annotation pair for this annotation.
     */
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

