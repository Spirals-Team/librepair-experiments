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
 * This code element defines a for loop.
 * Example:
 * <pre>
 *     // a for statement
 *     for(int i=0; i&lt;10; i++) {
 *     	System.out.println("foo");
 *     }
 * </pre>
 */
public interface CtFor extends spoon.reflect.code.CtLoop {
    /**
     * Gets the end-loop test expression.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<java.lang.Boolean> getExpression();

    /**
     * Sets the end-loop test expression.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <T extends spoon.reflect.code.CtFor> T setExpression(spoon.reflect.code.CtExpression<java.lang.Boolean> expression);

    /**
     * Gets the <i>init</i> statements.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.FOR_INIT)
    java.util.List<spoon.reflect.code.CtStatement> getForInit();

    /**
     * Adds an <i>init</i> statement.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FOR_INIT)
    <T extends spoon.reflect.code.CtFor> T addForInit(spoon.reflect.code.CtStatement statement);

    /**
     * Sets the <i>init</i> statements.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FOR_INIT)
    <T extends spoon.reflect.code.CtFor> T setForInit(java.util.List<spoon.reflect.code.CtStatement> forInit);

    /**
     * Removes an <i>init</i> statement.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FOR_INIT)
    boolean removeForInit(spoon.reflect.code.CtStatement statement);

    /**
     * Gets the <i>update</i> statements.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.FOR_UPDATE)
    java.util.List<spoon.reflect.code.CtStatement> getForUpdate();

    /**
     * Adds an <i>update</i> statement.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FOR_UPDATE)
    <T extends spoon.reflect.code.CtFor> T addForUpdate(spoon.reflect.code.CtStatement statement);

    /**
     * Sets the <i>update</i> statements.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FOR_UPDATE)
    <T extends spoon.reflect.code.CtFor> T setForUpdate(java.util.List<spoon.reflect.code.CtStatement> forUpdate);

    /**
     * Removes an <i>update</i> statement.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FOR_UPDATE)
    boolean removeForUpdate(spoon.reflect.code.CtStatement statement);

    @java.lang.Override
    spoon.reflect.code.CtFor clone();
}

