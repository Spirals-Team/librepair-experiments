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


public class CtOperatorAssignmentImpl<T, A extends T> extends spoon.support.reflect.code.CtAssignmentImpl<T, A> implements spoon.reflect.code.CtOperatorAssignment<T, A> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.OPERATOR_KIND)
    spoon.reflect.code.BinaryOperatorKind kind;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtOperatorAssignment(this);
    }

    @java.lang.Override
    public spoon.reflect.code.BinaryOperatorKind getKind() {
        return kind;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtOperatorAssignment<T, A>> C setKind(spoon.reflect.code.BinaryOperatorKind kind) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.OPERATOR_KIND, kind, this.kind);
        this.kind = kind;
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtOperatorAssignment<T, A> clone() {
        return ((spoon.reflect.code.CtOperatorAssignment<T, A>) (super.clone()));
    }
}

