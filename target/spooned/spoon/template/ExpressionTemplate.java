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
package spoon.template;


/**
 * This class represents an expression template parameter expressed in Java.
 *
 * <p>
 * To define a new expression template parameter, you must subclass this class
 * and implement the {@link #expression()} method, which actually defines the
 * Java expression. It corresponds to a {@link spoon.reflect.code.CtExpression}.
 */
public abstract class ExpressionTemplate<T> extends spoon.template.AbstractTemplate<spoon.reflect.code.CtExpression<T>> {
    /**
     * Returns the expression.
     */
    @java.lang.SuppressWarnings("unchecked")
    public static <T> spoon.reflect.code.CtExpression<T> getExpression(spoon.reflect.declaration.CtClass<? extends spoon.template.ExpressionTemplate<?>> p) {
        spoon.reflect.code.CtBlock<?> b = spoon.template.ExpressionTemplate.getExpressionBlock(p);
        return ((spoon.reflect.code.CtReturn<T>) (b.getStatements().get(0))).getReturnedExpression();
    }

    private static spoon.reflect.code.CtBlock<?> getExpressionBlock(spoon.reflect.declaration.CtClass<? extends spoon.template.ExpressionTemplate<?>> p) {
        spoon.reflect.code.CtBlock<?> b = p.getMethod("expression").getBody();
        return b;
    }

    /**
     * Creates a new expression template parameter.
     */
    public ExpressionTemplate() {
    }

    /**
     * This method must be implemented to define the template expression. The
     * convention is that the defined expression corresponds to the expression
     * returned by the return statement of the method.
     */
    public abstract T expression() throws java.lang.Throwable;

    @java.lang.SuppressWarnings("unchecked")
    public spoon.reflect.code.CtExpression<T> apply(spoon.reflect.declaration.CtType<?> targetType) {
        spoon.reflect.declaration.CtClass<? extends spoon.template.ExpressionTemplate<?>> c = spoon.template.Substitution.getTemplateCtClass(targetType, this);
        spoon.reflect.code.CtBlock<?> b = spoon.template.Substitution.substitute(targetType, this, spoon.template.ExpressionTemplate.getExpressionBlock(c));
        return ((spoon.reflect.code.CtReturn<T>) (b.getStatements().get(0))).getReturnedExpression();
    }

    public T S() {
        return null;
    }
}

