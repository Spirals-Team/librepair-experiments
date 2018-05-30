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
 * This code element defines a <code>throw</code> statement.
 *
 * Example:
 * <pre>
 *     throw new RuntimeException("oops")
 * </pre>
 */
public interface CtThrow extends spoon.reflect.code.CtCFlowBreak , spoon.template.TemplateParameter<java.lang.Void> {
    /**
     * Returns the thrown expression (must be a throwable).
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<? extends java.lang.Throwable> getThrownExpression();

    /**
     * Sets the thrown expression (must be a throwable).
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <T extends spoon.reflect.code.CtThrow> T setThrownExpression(spoon.reflect.code.CtExpression<? extends java.lang.Throwable> thrownExpression);

    @java.lang.Override
    spoon.reflect.code.CtThrow clone();
}

