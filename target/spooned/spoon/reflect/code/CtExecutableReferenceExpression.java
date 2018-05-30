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
 * This abstract code element defines an expression which represents an executable reference.
 *
 * * Example:
 * <pre>
 *     java.util.function.Supplier p =
 *       Object::new;
 * </pre>
 *
 * @param <T>
 * 		Each executable references are typed by an interface with one method. This generic type
 * 		correspond to this concept.
 * @param <E>
 * 		Correspond of <code>Type</code> in <code>Type::method</code>.
 */
public interface CtExecutableReferenceExpression<T, E extends spoon.reflect.code.CtExpression<?>> extends spoon.reflect.code.CtTargetedExpression<T, E> {
    /**
     * Gets the executable referenced by the expression.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXECUTABLE_REF)
    spoon.reflect.reference.CtExecutableReference<T> getExecutable();

    /**
     * Sets the executable will be referenced by the expression.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXECUTABLE_REF)
    <C extends spoon.reflect.code.CtExecutableReferenceExpression<T, E>> C setExecutable(spoon.reflect.reference.CtExecutableReference<T> executable);

    @java.lang.Override
    spoon.reflect.code.CtExecutableReferenceExpression<T, E> clone();
}

