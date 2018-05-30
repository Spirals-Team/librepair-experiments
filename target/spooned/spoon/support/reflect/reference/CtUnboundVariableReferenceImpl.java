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
package spoon.support.reflect.reference;


/**
 * represents a reference to an unbound field (used when no full classpath is available
 */
public class CtUnboundVariableReferenceImpl<T> extends spoon.support.reflect.reference.CtVariableReferenceImpl<T> implements spoon.reflect.reference.CtUnboundVariableReference<T> {
    private static final long serialVersionUID = -932423216089690817L;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtUnboundVariableReference(this);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtUnboundVariableReference<T> clone() {
        return ((spoon.reflect.reference.CtUnboundVariableReference<T>) (super.clone()));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.List<spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation>> getAnnotations() {
        return spoon.support.reflect.declaration.CtElementImpl.emptyList();
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <E extends spoon.reflect.declaration.CtElement> E setAnnotations(java.util.List<spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation>> annotations) {
        return ((E) (this));
    }
}

