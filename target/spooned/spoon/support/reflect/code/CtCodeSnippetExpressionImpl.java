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
package spoon.support.reflect.code;


public class CtCodeSnippetExpressionImpl<T> extends spoon.support.reflect.code.CtExpressionImpl<T> implements spoon.reflect.code.CtCodeSnippetExpression<T> {
    private static final long serialVersionUID = 1L;

    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtCodeSnippetExpression(this);
    }

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.SNIPPET)
    java.lang.String value;

    @java.lang.Override
    public java.lang.String getValue() {
        return value;
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtCodeSnippet> C setValue(java.lang.String value) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.SNIPPET, value, this.value);
        this.value = value;
        return ((C) (this));
    }

    @java.lang.SuppressWarnings("unchecked")
    public <E extends spoon.reflect.code.CtExpression<T>> E compile() throws spoon.support.compiler.SnippetCompilationError {
        return ((E) (spoon.support.compiler.SnippetCompilationHelper.compileExpression(this)));
    }

    @java.lang.Override
    public spoon.reflect.code.CtCodeSnippetExpression<T> clone() {
        return ((spoon.reflect.code.CtCodeSnippetExpression<T>) (super.clone()));
    }
}

