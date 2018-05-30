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
 * This element represents an executable element such as a method, a
 * constructor, or an anonymous block.
 */
public interface CtExecutable<R> extends spoon.reflect.code.CtBodyHolder , spoon.reflect.declaration.CtNamedElement , spoon.reflect.declaration.CtTypedElement<R> {
    /**
     * The separator for a string representation of an executable.
     */
    java.lang.String EXECUTABLE_SEPARATOR = "#";

    /* (non-Javadoc)

    @see spoon.reflect.declaration.CtNamedElement#getReference()
     */
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtExecutableReference<R> getReference();

    /**
     * Gets the body expression.
     */
    @java.lang.Override
    spoon.reflect.code.CtBlock<R> getBody();

    /**
     * Gets the parameters list.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.PARAMETER)
    java.util.List<spoon.reflect.declaration.CtParameter<?>> getParameters();

    /**
     * Sets the parameters.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.PARAMETER)
    <T extends spoon.reflect.declaration.CtExecutable<R>> T setParameters(java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters);

    /**
     * Add a parameter for this executable
     *
     * @param parameter
     * 		
     * @return <tt>true</tt> if this element changed as a result of the call
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.PARAMETER)
    <T extends spoon.reflect.declaration.CtExecutable<R>> T addParameter(spoon.reflect.declaration.CtParameter<?> parameter);

    /**
     * Remove a parameter for this executable
     *
     * @param parameter
     * 		
     * @return <tt>true</tt> if this element changed as a result of the call
     */
    boolean removeParameter(spoon.reflect.declaration.CtParameter<?> parameter);

    /**
     * Returns the exceptions and other throwables listed in this method or
     * constructor's <tt>throws</tt> clause.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.THROWN)
    java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> getThrownTypes();

    /**
     * Sets the thrown types.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.THROWN)
    <T extends spoon.reflect.declaration.CtExecutable<R>> T setThrownTypes(java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes);

    /**
     * add a thrown type.
     *
     * @param throwType
     * 		
     * @return <tt>true</tt> if this element changed as a result of the call
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.THROWN)
    <T extends spoon.reflect.declaration.CtExecutable<R>> T addThrownType(spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable> throwType);

    /**
     * remove a thrown type.
     *
     * @param throwType
     * 		
     * @return <tt>true</tt> if this element changed as a result of the call
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.THROWN)
    boolean removeThrownType(spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable> throwType);

    /**
     * Gets the signature of this method or constructor.
     * The signature is composed of the method name and the parameter types, all fully-qualified, eg "int foo(java.lang.String)".
     * The core contract is that in a type, there cannot be two methods with the same signature.
     *
     * Note that the concept of method signature in Java is not well defined (see chapter "8.4.2 Method Signature" of the Java specification, which defines what relations between signatures but not what a signature is exactly).
     *
     * Note also that the signature of a method reference is the same as the signature of the corresponding method if and only if the method parameters does not involve generics in their types. Otherwise, one has eg m(String) (reference) and m(T) (declaration)
     *
     * Reference: "In the Java programming language, a method signature is the method name and the number and type of its parameters. Return types and thrown exceptions are not considered to be a part of the method signature."
     * see https://stackoverflow.com/questions/16149285/does-a-methods-signature-in-java-include-its-return-type
     * see https://en.wikipedia.org/wiki/Type_signature
     */
    java.lang.String getSignature();

    @java.lang.Override
    spoon.reflect.declaration.CtExecutable<R> clone();
}

