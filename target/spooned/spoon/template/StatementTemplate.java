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
 * This class represents a template parameter that defines a statement list
 * directly expressed in Java (no returns).
 *
 * <p>
 * To define a new statement list template parameter, you must subclass this
 * class and implement the {@link #statement()} method, which actually defines
 * the Java statements. It corresponds to a
 * {@link spoon.reflect.code.CtStatementList}.
 */
public abstract class StatementTemplate extends spoon.template.AbstractTemplate<spoon.reflect.code.CtStatement> {
    /**
     * Creates a new statement list template parameter.
     */
    public StatementTemplate() {
    }

    public spoon.reflect.code.CtStatement apply(spoon.reflect.declaration.CtType<?> targetType) {
        spoon.reflect.declaration.CtClass<?> c = spoon.template.Substitution.getTemplateCtClass(targetType, this);
        // we substitute the first statement of method statement
        spoon.reflect.code.CtStatement result = c.getMethod("statement").getBody().getStatements().get(0).clone();
        java.util.List<spoon.reflect.code.CtStatement> statements = new spoon.support.template.SubstitutionVisitor(c.getFactory(), targetType, this).substitute(result);
        if ((statements.size()) > 1) {
            throw new spoon.SpoonException("StatementTemplate cannot return more then one statement");
        }
        return statements.isEmpty() ? null : statements.get(0);
    }

    public java.lang.Void S() {
        return null;
    }

    /**
     * This method must be implemented to define the template statement list.
     */
    public abstract void statement() throws java.lang.Throwable;
}

