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
 * This code element represents an <code>if</code> statement.
 * Example:
 * <pre>
 *     if (1==0) {
 *     	System.out.println("foo");
 *     } else {
 *     	System.out.println("bar");
 *     }
 * </pre>
 */
public interface CtIf extends spoon.reflect.code.CtStatement , spoon.template.TemplateParameter<java.lang.Void> {
    /**
     * Gets the boolean expression that represents the <code>if</code>'s
     * condition.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.CONDITION)
    spoon.reflect.code.CtExpression<java.lang.Boolean> getCondition();

    /**
     * Gets the statement executed when the condition is false.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ELSE)
    <S extends spoon.reflect.code.CtStatement> S getElseStatement();

    /**
     * Gets the statement executed when the condition is true.
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.THEN)
    <S extends spoon.reflect.code.CtStatement> S getThenStatement();

    /**
     * Sets the boolean expression that represents the <code>if</code>'s
     * condition.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CONDITION)
    <T extends spoon.reflect.code.CtIf> T setCondition(spoon.reflect.code.CtExpression<java.lang.Boolean> expression);

    /**
     * Sets the statement executed when the condition is false.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ELSE)
    <T extends spoon.reflect.code.CtIf> T setElseStatement(spoon.reflect.code.CtStatement elseStatement);

    /**
     * Sets the statement executed when the condition is true.
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.THEN)
    <T extends spoon.reflect.code.CtIf> T setThenStatement(spoon.reflect.code.CtStatement thenStatement);

    @java.lang.Override
    spoon.reflect.code.CtIf clone();
}

