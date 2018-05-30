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
 * Returns information that can be obtained both at compile-time and run-time
 *
 * For CtElement, the compile-time information is given
 *
 * For CtTypeReference, the runtime information is given (using the Reflection API)
 */
public interface CtTypeInformation {
    /**
     * Returns the interface types directly implemented by this class or
     * extended by this interface.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.INTERFACE)
    java.util.Set<spoon.reflect.reference.CtTypeReference<?>> getSuperInterfaces();

    /**
     * Returns the fully qualified name of this type declaration.
     */
    @spoon.support.DerivedProperty
    java.lang.String getQualifiedName();

    /**
     * Gets modifiers of this type.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.MODIFIER)
    java.util.Set<spoon.reflect.declaration.ModifierKind> getModifiers();

    /**
     * Return {@code true} if the referenced type is a primitive type (int,
     * double, boolean...).
     */
    boolean isPrimitive();

    /**
     * Return {@code true} if the referenced type is a anonymous type
     */
    boolean isAnonymous();

    /**
     * Return {@code true} if the referenced type is declared in an executable.
     * e.g. a type declared in a method or a lambda.
     *
     * This corresponds to <code>isLocalClass</code> of <code>java.lang.Class</code>.
     *
     * <pre>
     *     // Type declared in a method.
     *     public void make() {
     *         class Cook {
     *         }
     *     }
     *     // Type declared in a lambda.
     *     s -&gt; {
     *         class Cook {
     *         }
     *     }
     * </pre>
     */
    boolean isLocalType();

    /**
     * Returns true if this type is a class. Returns false for others (enum, interface, generics, annotation).
     */
    boolean isClass();

    /**
     * Returns true if this type is an interface.
     */
    boolean isInterface();

    /**
     * Returns true if this type is an enum.
     */
    boolean isEnum();

    /**
     * Returns true if this type is an annotation type.
     */
    boolean isAnnotationType();

    /**
     * Returns true if it refers to a type parameter (ie not a concrete class, eg "T foo"). It can refer to it directly (eg T), or indirectly (eg List&lt;T&gt;, or Set&lt;List&lt;T&gt;&gt;).
     */
    @spoon.support.DerivedProperty
    boolean isGenerics();

    /**
     * Returns true if the referenced type is a sub-type of the given type.
     * Returns true is type is self, it means: typeX.isSubtypeOf(typeX) is true too
     */
    boolean isSubtypeOf(spoon.reflect.reference.CtTypeReference<?> type);

    /**
     * Returns the class type directly extended by this class.
     *
     * getSuperClass().getDeclaration()/getTypeDeclaration() returns the corresponding CtType (if in the source folder of Spoon).
     *
     * @return the class type directly extended by this class, or null if there
     * is none or if the super class is not in the classpath (in noclasspath mode)
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.SUPER_TYPE)
    spoon.reflect.reference.CtTypeReference<?> getSuperclass();

    /**
     * Gets the fields declared by this type.
     */
    @spoon.support.DerivedProperty
    java.util.Collection<spoon.reflect.reference.CtFieldReference<?>> getDeclaredFields();

    /**
     * Gets the fields declared by this type and by all its supertypes if
     * applicable.
     */
    @spoon.support.DerivedProperty
    java.util.Collection<spoon.reflect.reference.CtFieldReference<?>> getAllFields();

    /**
     * Gets a field from its name.
     *
     * @return null if does not exit
     */
    spoon.reflect.reference.CtFieldReference<?> getDeclaredField(java.lang.String name);

    /**
     * Gets a field from this type or any super type or any implemented interface by field name.
     *
     * @return null if does not exit
     */
    spoon.reflect.reference.CtFieldReference<?> getDeclaredOrInheritedField(java.lang.String fieldName);

    /**
     * Gets the executables declared by this type if applicable.
     */
    @spoon.support.DerivedProperty
    java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> getDeclaredExecutables();

    /**
     * Gets the executables declared by this type and by all its supertypes (static/instance methods, constructors, anonymous static blocks) if
     * applicable. This method returns:
     *
     * <ul>
     *     <li>static, instance and default methods</li>
     *     <li>constructors</li>
     * </ul>
     *
     * If a method is overridden twice in the hierarchy, it counts for two different elements.
     * The method can be abstract.
     */
    @spoon.support.DerivedProperty
    java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> getAllExecutables();

    /**
     *
     *
     * @return the type erasure, which is computed by the java compiler to ensure that no new classes are created for parametrized types so that generics incur no runtime overhead.
     * See https://docs.oracle.com/javase/tutorial/java/generics/erasure.html
     */
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtTypeReference<?> getTypeErasure();
}

