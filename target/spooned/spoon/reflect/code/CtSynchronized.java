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
 * This code element defines a <code>synchronized</code> statement.
 *
 * Example:
 * <pre>
 *    java.util.List l = new java.util.ArrayList();
 *    synchronized(l) {
 *     	System.out.println("foo");
 *    }
 * </pre>
 */
public interface CtSynchronized extends spoon.reflect.code.CtStatement {
    /**
     * Gets the expression that defines the monitored object if any.
     *
     * @return the monitored object if defined, null otherwise
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<?> getExpression();

    /**
     * Sets the expression that defines the monitored.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <T extends spoon.reflect.code.CtSynchronized> T setExpression(spoon.reflect.code.CtExpression<?> expression);

    /**
     * Gets the synchronized block.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.BODY)
    spoon.reflect.code.CtBlock<?> getBlock();

    /**
     * Sets the synchronized block.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.BODY)
    <T extends spoon.reflect.code.CtSynchronized> T setBlock(spoon.reflect.code.CtBlock<?> block);

    @java.lang.Override
    spoon.reflect.code.CtSynchronized clone();
}

