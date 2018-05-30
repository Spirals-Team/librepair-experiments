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
 * This abstract element defines a super-type for classes and interfaces, which
 * can declare methods.
 *
 * The type parameter T refers to the actual class that this type represents.
 */
public interface CtType<T> extends spoon.reflect.declaration.CtFormalTypeDeclarer , spoon.reflect.declaration.CtNamedElement , spoon.reflect.declaration.CtShadowable , spoon.reflect.declaration.CtTypeInformation , spoon.reflect.declaration.CtTypeMember {
    /**
     * The string separator in a Java innertype qualified name.
     */
    java.lang.String INNERTTYPE_SEPARATOR = "$";

    /**
     * Used in no classpath when we don't have any information to build the name of the type.
     */
    java.lang.String NAME_UNKNOWN = "<unknown>";

    /**
     * Returns the simple (unqualified) name of this element.
     * Following the compilation convention, if the type is a local type,
     * the name starts with a numeric prefix (e.g. local class Foo has simple name 1Foo).
     */
    @java.lang.Override
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.NAME)
    java.lang.String getSimpleName();

    /**
     * Returns the types used by this type.
     *
     * @param includeSamePackage
     * 		set to true if the method should return also the types located
     * 		in the same package as the current type
     */
    java.util.Set<spoon.reflect.reference.CtTypeReference<?>> getUsedTypes(boolean includeSamePackage);

    /**
     * Returns the actual runtime class if exists.
     *
     * @return the runtime class, null if is not accessible or does not exist
     */
    @spoon.support.DerivedProperty
    java.lang.Class<T> getActualClass();

    /**
     * Gets a field from its name.
     *
     * @return null if does not exit
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.FIELD)
    spoon.reflect.declaration.CtField<?> getField(java.lang.String name);

    /**
     * Returns the fields that are directly declared by this class or interface.
     * Includes enum constants.
     *
     * Derived from {@link #getTypeMembers()}
     */
    @spoon.support.DerivedProperty
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.FIELD)
    java.util.List<spoon.reflect.declaration.CtField<?>> getFields();

    /**
     * Gets a nested type from its name.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.NESTED_TYPE)
    <N extends spoon.reflect.declaration.CtType<?>> N getNestedType(java.lang.String name);

    /**
     * Returns the declarations of the nested classes and interfaces that are
     * directly declared by this class or interface.
     */
    @spoon.support.DerivedProperty
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.NESTED_TYPE)
    java.util.Set<spoon.reflect.declaration.CtType<?>> getNestedTypes();

    /**
     * Gets the package where this type is declared.
     */
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtPackage getPackage();

    /**
     * Returns the corresponding type reference.
     *
     * Overrides the return type.
     */
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtTypeReference<T> getReference();

    /**
     * Returns true if this type is top-level (declared as the main type in a
     * file).
     */
    boolean isTopLevel();

    /**
     * Adds a field at the top of the type (before static block).
     * Note that the position of these field will be negative to be written at the top of the type.
     *
     * @param field
     * 		
     * @return <tt>true</tt> if the field is added.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FIELD)
    <F, C extends spoon.reflect.declaration.CtType<T>> C addFieldAtTop(spoon.reflect.declaration.CtField<F> field);

    /**
     * add a field at the end of the field list.
     *
     * @param field
     * 		
     * @return <tt>true</tt> if this element changed as a result of the call
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FIELD)
    <F, C extends spoon.reflect.declaration.CtType<T>> C addField(spoon.reflect.declaration.CtField<F> field);

    /**
     * add a field at a given position.
     *
     * @param field
     * 		
     * @return <tt>true</tt> if this element changed as a result of the call
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FIELD)
    <F, C extends spoon.reflect.declaration.CtType<T>> C addField(int index, spoon.reflect.declaration.CtField<F> field);

    /**
     * Sets all fields in the type.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FIELD)
    <C extends spoon.reflect.declaration.CtType<T>> C setFields(java.util.List<spoon.reflect.declaration.CtField<?>> fields);

    /**
     * remove a Field
     *
     * @param field
     * 		
     * @return <tt>true</tt> if this element changed as a result of the call
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FIELD)
    <F> boolean removeField(spoon.reflect.declaration.CtField<F> field);

    /**
     * Add a nested type.
     *
     * @param nestedType
     * 		
     * @return <tt>true</tt> if this element changed as a result of the call
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.NESTED_TYPE)
    <N, C extends spoon.reflect.declaration.CtType<T>> C addNestedType(spoon.reflect.declaration.CtType<N> nestedType);

    /**
     * Remove a nested type.
     *
     * @param nestedType
     * 		
     * @return <tt>true</tt> if this element changed as a result of the call
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.NESTED_TYPE)
    <N> boolean removeNestedType(spoon.reflect.declaration.CtType<N> nestedType);

    /**
     * Sets all nested types.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.NESTED_TYPE)
    <C extends spoon.reflect.declaration.CtType<T>> C setNestedTypes(java.util.Set<spoon.reflect.declaration.CtType<?>> nestedTypes);

    /**
     * Compiles and replace all the code snippets that are found in this type.
     *
     * @see CtCodeSnippet
     * @see spoon.reflect.code.CtCodeSnippetExpression
     * @see spoon.reflect.code.CtCodeSnippetStatement
     */
    void compileAndReplaceSnippets();

    /**
     * Return all the methods that can be called on an instance of this type.
     * It recursively collects all methods (both concrete and abstract) from all super-classes and all super-interfaces.
     * It deduplicates methods with the same signature, which are defined several times somewhere in the type hierarchy.
     *
     * Warning: this method can be really slow due to signature deduplication.
     *
     * It includes all methods: the methods of types whose source code is in the Spoon model,
     * the methods of types from the JDK and from libraries present in the classpath,
     * the methods of java.lang.Object (for all CtClass objects).
     * However, in noclasspath mode, it does not include methods from unknown types.
     * If methods are overridden somewhere in the type hierarchy, it returns only top methods (ie method definitions).
     */
    @spoon.support.DerivedProperty
    java.util.Set<spoon.reflect.declaration.CtMethod<?>> getAllMethods();

    /**
     * Gets a method from its return type, name, and parameter types.
     *
     * @return null if does not exit
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.METHOD)
    <R> spoon.reflect.declaration.CtMethod<R> getMethod(spoon.reflect.reference.CtTypeReference<R> returnType, java.lang.String name, spoon.reflect.reference.CtTypeReference<?>... parameterTypes);

    /**
     * Gets a method from its name and parameter types.
     *
     * @return null if does not exit
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.METHOD)
    <R> spoon.reflect.declaration.CtMethod<R> getMethod(java.lang.String name, spoon.reflect.reference.CtTypeReference<?>... parameterTypes);

    /**
     * Returns the methods that are directly declared by this class or
     * interface.
     *
     * Derived from {@link #getTypeMembers()}
     */
    @spoon.support.DerivedProperty
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.METHOD)
    java.util.Set<spoon.reflect.declaration.CtMethod<?>> getMethods();

    /**
     * Returns the methods that are directly declared by this class or
     * interface and annotated with one of the given annotations.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.METHOD)
    java.util.Set<spoon.reflect.declaration.CtMethod<?>> getMethodsAnnotatedWith(spoon.reflect.reference.CtTypeReference<?>... annotationTypes);

    /**
     * Returns the methods that are directly declared by this class or
     * interface and that have the given name.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.METHOD)
    java.util.List<spoon.reflect.declaration.CtMethod<?>> getMethodsByName(java.lang.String name);

    /**
     * Searches in the type for the given method.
     * Super classes and implemented interfaces are considered.
     * The matching criterion is that the signatures are identical.
     *
     * @param method
     * 		The method to search for in the class.
     * @return True: the type has the given method. False otherwise.
     */
    boolean hasMethod(spoon.reflect.declaration.CtMethod<?> method);

    /**
     * Sets the methods of this type.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.METHOD)
    <C extends spoon.reflect.declaration.CtType<T>> C setMethods(java.util.Set<spoon.reflect.declaration.CtMethod<?>> methods);

    /**
     * Adds a method to this type.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.METHOD)
    <M, C extends spoon.reflect.declaration.CtType<T>> C addMethod(spoon.reflect.declaration.CtMethod<M> method);

    /**
     * Removes a method from this type.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.METHOD)
    <M> boolean removeMethod(spoon.reflect.declaration.CtMethod<M> method);

    /**
     * Sets the superclass type.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.SUPER_TYPE)
    <C extends spoon.reflect.declaration.CtType<T>> C setSuperclass(spoon.reflect.reference.CtTypeReference<?> superClass);

    /**
     * Sets the super interfaces of this type.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.INTERFACE)
    <C extends spoon.reflect.declaration.CtType<T>> C setSuperInterfaces(java.util.Set<spoon.reflect.reference.CtTypeReference<?>> interfaces);

    /**
     *
     *
     * @param interfac
     * 		
     * @return <tt>true</tt> if this element changed as a result of the call
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.INTERFACE)
    <S, C extends spoon.reflect.declaration.CtType<T>> C addSuperInterface(spoon.reflect.reference.CtTypeReference<S> interfac);

    /**
     *
     *
     * @param interfac
     * 		
     * @return <tt>true</tt> if this element changed as a result of the call
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.INTERFACE)
    <S> boolean removeSuperInterface(spoon.reflect.reference.CtTypeReference<S> interfac);

    /**
     * Gets all type members of the type like fields, methods, anonymous block, etc.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.TYPE_MEMBER)
    java.util.List<spoon.reflect.declaration.CtTypeMember> getTypeMembers();

    /**
     * Adds a type member at the end of all type member of the type.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_MEMBER)
    <C extends spoon.reflect.declaration.CtType<T>> C addTypeMember(spoon.reflect.declaration.CtTypeMember member);

    /**
     * Adds a type member at a given position. Think to use this method if the order is
     * important for you.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_MEMBER)
    <C extends spoon.reflect.declaration.CtType<T>> C addTypeMemberAt(int position, spoon.reflect.declaration.CtTypeMember member);

    /**
     * Removes the type member.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_MEMBER)
    boolean removeTypeMember(spoon.reflect.declaration.CtTypeMember member);

    /**
     * Removes all types members with these new members.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_MEMBER)
    <C extends spoon.reflect.declaration.CtType<T>> C setTypeMembers(java.util.List<spoon.reflect.declaration.CtTypeMember> members);

    @java.lang.Override
    spoon.reflect.declaration.CtType<T> clone();

    /**
     * Copy the type, where copy means cloning + porting all the references in the clone from the old type to the new type.
     *
     * The copied type is added to the same package (and this to the factory as well).
     *
     * A new unique method name is given for each copy, and this method can be called several times.
     */
    spoon.reflect.declaration.CtType<?> copyType();
}

