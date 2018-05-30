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
 * This interface is the root interface for the metamodel elements (any program
 * element).
 */
@spoon.reflect.visitor.Root
public interface CtElement extends java.lang.Cloneable , spoon.processing.FactoryAccessor , spoon.reflect.visitor.CtVisitable , spoon.reflect.visitor.chain.CtQueryable {
    /**
     * Searches for an annotation of the given class that annotates the
     * current element.
     *
     * When used with a shadow element, this method might return an empty list even on an annotated element
     * because annotations without a RUNTIME retention policy are lost after compilation.
     *
     * WARNING: this method uses a class loader proxy, which is costly.
     * Use {@link #getAnnotation(CtTypeReference)} preferably.
     *
     * <p>
     * NOTE: before using an annotation proxy, you have to make sure that all
     * the types referenced by the annotation have been compiled and are in the
     * classpath so that accessed values can be converted into the actual types.
     *
     * @param <A>
     * 		the annotation's type
     * @param annotationType
     * 		the annotation's class
     * @return if found, returns a proxy for this annotation
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ANNOTATION)
    <A extends java.lang.annotation.Annotation> A getAnnotation(java.lang.Class<A> annotationType);

    /**
     * Gets the annotation element for a given annotation type.
     *
     * When used with a shadow element, this method might return an empty list even on an annotated element
     * because annotations without a RUNTIME retention policy are lost after compilation.
     *
     * @param annotationType
     * 		the annotation type
     * @return the annotation if this element is annotated by one annotation of
     * the given type
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ANNOTATION)
    <A extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotation<A> getAnnotation(spoon.reflect.reference.CtTypeReference<A> annotationType);

    /**
     *
     *
     * @return true if the element is annotated by the given annotation type.
     * @param annotationType
     * 		the annotation type
     */
    @spoon.support.DerivedProperty
    <A extends java.lang.annotation.Annotation> boolean hasAnnotation(java.lang.Class<A> annotationType);

    /**
     * Returns the annotations that are present on this element.
     *
     * For sake of encapsulation, the returned list is unmodifiable.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ANNOTATION)
    java.util.List<spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation>> getAnnotations();

    /**
     * Returns the text of the documentation ("javadoc") comment of this
     * element. It contains the text of Javadoc together with the tags.
     *
     * If one only wants only the text without the tag, one can call `getComments().get(0).getContent()`
     *
     * If one wants to analyze the tags, one can call `getComments().get(0).asJavaDoc().getTags()`
     *
     * See also {@link #getComments()}.and {@link spoon.reflect.code.CtJavaDoc}
     */
    @spoon.support.DerivedProperty
    java.lang.String getDocComment();

    /**
     * Build a short representation of any element.
     */
    @spoon.support.DerivedProperty
    java.lang.String getShortRepresentation();

    /**
     * Gets the position of this element in input source files
     *
     * @return Source file and line number of this element.
     * It never returns null. Use {@link SourcePosition#isValidPosition()}
     * to detect whether return instance contains start/end indexes.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.POSITION)
    spoon.reflect.cu.SourcePosition getPosition();

    /**
     * Replaces this element by another one.
     */
    void replace(spoon.reflect.declaration.CtElement element);

    /**
     * Replaces this element by several elements.
     * If `elements` contains one single element, it is equivalent to {@link #replace(CtElement)}.
     * If `elements` is empty, it is equivalent to {@link #delete()}.
     */
    <E extends spoon.reflect.declaration.CtElement> void replace(java.util.Collection<E> elements);

    /**
     * Add an annotation for this element
     *
     * @param annotation
     * 		
     * @return <tt>true</tt> if this element changed as a result of the call
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ANNOTATION)
    <E extends spoon.reflect.declaration.CtElement> E addAnnotation(spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> annotation);

    /**
     * Remove an annotation for this element
     *
     * @param annotation
     * 		
     * @return <tt>true</tt> if this element changed as a result of the call
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ANNOTATION)
    boolean removeAnnotation(spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> annotation);

    /**
     * Sets the text of the documentation ("javadoc") comment of this
     * declaration. This API will set the content of the first javadoc
     * {@link CtComment} or create a new  javadoc {@link CtComment} if
     * no javadoc {@link CtComment} is available on this object.
     */
    <E extends spoon.reflect.declaration.CtElement> E setDocComment(java.lang.String docComment);

    /**
     * Sets the position in the Java source file. Note that this information is
     * used to feed the line numbers in the generated bytecode if any (which is
     * useful for debugging).
     *
     * @param position
     * 		of this element in the input source files
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.POSITION)
    <E extends spoon.reflect.declaration.CtElement> E setPosition(spoon.reflect.cu.SourcePosition position);

    /**
     * Gets the child elements annotated with the given annotation type's
     * instances.
     *
     * @param <E>
     * 		the element's type
     * @param annotationType
     * 		the annotation type
     * @return all the child elements annotated with an instance of the given
     * annotation type
     */
    <E extends spoon.reflect.declaration.CtElement> java.util.List<E> getAnnotatedChildren(java.lang.Class<? extends java.lang.annotation.Annotation> annotationType);

    /**
     * Returns true if this element is not present in the code (automatically added by the
     * Java compiler or inferred when the model is built).
     * Consequently, implicit elements are not pretty-printed and have no position.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.IS_IMPLICIT)
    boolean isImplicit();

    /**
     * Sets this element to be implicit.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.IS_IMPLICIT)
    <E extends spoon.reflect.declaration.CtElement> E setImplicit(boolean b);

    /**
     * Calculates and returns the set of all the types referenced by this
     * element (and sub-elements in the AST).
     */
    @spoon.support.DerivedProperty
    java.util.Set<spoon.reflect.reference.CtTypeReference<?>> getReferencedTypes();

    /**
     * Returns all the children elements recursively matching the filter.
     * If the receiver (this) matches the filter, it is also returned
     */
    <E extends spoon.reflect.declaration.CtElement> java.util.List<E> getElements(spoon.reflect.visitor.Filter<E> filter);

    /**
     * Sets the position of this element and all its children element. Note that
     * this information is used to feed the line numbers in the generated
     * bytecode if any (which is useful for debugging).
     *
     * @param position
     * 		of this element and all children in the input source file
     */
    <E extends spoon.reflect.declaration.CtElement> E setPositions(spoon.reflect.cu.SourcePosition position);

    /**
     * Sets the annotations for this element.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ANNOTATION)
    <E extends spoon.reflect.declaration.CtElement> E setAnnotations(java.util.List<spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation>> annotation);

    /**
     * Gets the parent of current reference.
     *
     * @throws ParentNotInitializedException
     * 		when the parent of this element is not initialized
     */
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtElement getParent() throws spoon.reflect.declaration.ParentNotInitializedException;

    /**
     * Gets the first parent that matches the given type.
     */
    <P extends spoon.reflect.declaration.CtElement> P getParent(java.lang.Class<P> parentType) throws spoon.reflect.declaration.ParentNotInitializedException;

    /**
     * Gets the first parent that matches the filter.
     * If the receiver (this) matches the filter, it is also returned
     */
    <E extends spoon.reflect.declaration.CtElement> E getParent(spoon.reflect.visitor.Filter<E> filter) throws spoon.reflect.declaration.ParentNotInitializedException;

    /**
     * Manually sets the parent element of the current element.
     *
     * @param parent
     * 		parent reference.
     */
    <E extends spoon.reflect.declaration.CtElement> E setParent(E parent);

    /**
     * Tells if this parent has been initialized.
     */
    boolean isParentInitialized();

    /**
     * Tells if the given element is a direct or indirect parent.
     */
    boolean hasParent(spoon.reflect.declaration.CtElement candidate);

    /**
     * Calculates and sets all the parents below this element. This function can
     * be called to check and fix parents after manipulating the model.
     */
    void updateAllParentsBelow();

    /**
     *
     *
     * @return the {@link CtRole} of the parent's attribute where this element is used.
     * Returns null if parent doesn't contain this element as direct children or if this element has no parent.
     */
    spoon.reflect.path.CtRole getRoleInParent();

    /**
     * Deletes the element. For instance, delete a statement from its containing block. Warning: it may result in an incorrect AST, use at your own risk.
     */
    void delete();

    /**
     * Saves metadata inside an Element.
     */
    <E extends spoon.reflect.declaration.CtElement> E putMetadata(java.lang.String key, java.lang.Object val);

    /**
     * Retrieves metadata stored in an element. Returns null if it does not exist.
     */
    java.lang.Object getMetadata(java.lang.String key);

    /**
     * Returns the metadata keys stored in an element.
     */
    java.util.Set<java.lang.String> getMetadataKeys();

    /**
     * Set the comment list
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT)
    <E extends spoon.reflect.declaration.CtElement> E setComments(java.util.List<spoon.reflect.code.CtComment> comments);

    /**
     * The list of comments
     *
     * @return the list of comment
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.COMMENT)
    java.util.List<spoon.reflect.code.CtComment> getComments();

    /**
     * Add a comment to the current element
     * <code>element.addComment(element.getFactory().Code().createComment("comment", CtComment.CommentType.INLINE)</code>
     *
     * @param comment
     * 		the comment
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT)
    <E extends spoon.reflect.declaration.CtElement> E addComment(spoon.reflect.code.CtComment comment);

    /**
     * Remove a comment
     *
     * @param comment
     * 		the comment to remove
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT)
    <E extends spoon.reflect.declaration.CtElement> E removeComment(spoon.reflect.code.CtComment comment);

    /**
     * Clone the element which calls this method in a new object.
     *
     * Note that that references are kept as is, and thus, so if you clone whole classes
     * or methods, some parts of the cloned element (eg executable references) may still point to the initial element.
     * In this case, consider using methods {@link spoon.refactoring.Refactoring#copyType(CtType)} and {@link spoon.refactoring.Refactoring#copyMethod(CtMethod)} instead which does additional work beyond cloning.
     */
    spoon.reflect.declaration.CtElement clone();

    /**
     *
     *
     * @return a a single value (eg a CtElement), List, Set or Map depending on this `element` and `role`. Returned collections are read-only.
     * @param role
     * 		the role of the returned attribute with respect to this element.
     * 		
     * 		For instance, "klass.getValueByRole(CtRole.METHOD)" returns a list of methods.
     * 		
     * 		See {@link spoon.reflect.meta.impl.RoleHandlerHelper} for more advanced methods.
     */
    <T> T getValueByRole(spoon.reflect.path.CtRole role);

    /**
     * Sets a field according to a role.
     *
     * @param role
     * 		the role of the field to be set
     * @param value
     * 		to be assigned to this field.
     */
    <E extends spoon.reflect.declaration.CtElement, T> E setValueByRole(spoon.reflect.path.CtRole role, T value);

    /**
     * Return the path from the model root to this CtElement, eg `.spoon.test.path.Foo.foo#body#statement[index=0]`
     */
    spoon.reflect.path.CtPath getPath();

    /**
     * Returns an iterator over this CtElement's descendants.
     *
     * @return An iterator over this CtElement's descendants.
     */
    java.util.Iterator<spoon.reflect.declaration.CtElement> descendantIterator();

    /**
     * Returns an Iterable instance of this CtElement, allowing for dfs traversal of its descendants.
     *
     * @return an Iterable object that allows iterating through this CtElement's descendants.
     */
    java.lang.Iterable<spoon.reflect.declaration.CtElement> asIterable();
}

