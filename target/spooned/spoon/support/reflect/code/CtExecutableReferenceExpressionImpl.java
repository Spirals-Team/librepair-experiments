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


public class CtExecutableReferenceExpressionImpl<T, E extends spoon.reflect.code.CtExpression<?>> extends spoon.support.reflect.code.CtTargetedExpressionImpl<T, E> implements spoon.reflect.code.CtExecutableReferenceExpression<T, E> {
    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.EXECUTABLE_REF)
    spoon.reflect.reference.CtExecutableReference<T> executable;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtExecutableReferenceExpression(this);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtExecutableReference<T> getExecutable() {
        return executable;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtExecutableReferenceExpression<T, E>> C setExecutable(spoon.reflect.reference.CtExecutableReference<T> executable) {
        if (executable != null) {
            executable.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.EXECUTABLE_REF, executable, this.executable);
        this.executable = executable;
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtExecutableReferenceExpression<T, E> clone() {
        return ((spoon.reflect.code.CtExecutableReferenceExpression<T, E>) (super.clone()));
    }
}

