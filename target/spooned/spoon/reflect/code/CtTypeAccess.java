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
package spoon.reflect.code;


/**
 * This code element represents a type reference usable as an expression.
 * It is used in particular for static accesses, Java 8 method references, instanceof binary expressions and ".class".
 * <pre>
 *     // access to static field
 *     java.io.PrintStream ps = System.out;
 * </pre>
 * <pre>
 *     // call to static method
 *     Class.forName("Foo")
 * </pre>
 * <pre>
 *     // method reference
 *     java.util.function.Supplier p =
 *       Object::new;
 * </pre>
 * <pre>
 *     // instanceof test
 *     boolean x = new Object() instanceof Integer // Integer is represented as an access to type Integer
 * </pre>
 * <pre>
 *     // fake field "class"
 *     Class x = Number.class
 * </pre>
 *
 * @param <A>
 * 		Access type of the expression.
 */
public interface CtTypeAccess<A> extends spoon.reflect.code.CtExpression<java.lang.Void> {
    /**
     * Returns type represented and contained in the type access.
     *
     * @return CtTypeReference.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ACCESSED_TYPE)
    spoon.reflect.reference.CtTypeReference<A> getAccessedType();

    /**
     * Set the accessed type.
     *
     * @param accessedType
    		CtTypeReference.
     * 		
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ACCESSED_TYPE)
    <C extends spoon.reflect.code.CtTypeAccess<A>> C setAccessedType(spoon.reflect.reference.CtTypeReference<A> accessedType);

    /**
     * Returns always VOID.
     *
     * @see #getAccessedType() to get the accessed type.
     */
    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtTypeReference<java.lang.Void> getType();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtTypedElement> C setType(spoon.reflect.reference.CtTypeReference<java.lang.Void> type);

    @java.lang.Override
    spoon.reflect.code.CtTypeAccess<A> clone();
}

