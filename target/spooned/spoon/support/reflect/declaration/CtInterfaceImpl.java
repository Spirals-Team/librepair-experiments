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
package spoon.support.reflect.declaration;


public class CtInterfaceImpl<T> extends spoon.support.reflect.declaration.CtTypeImpl<T> implements spoon.reflect.declaration.CtInterface<T> {
    private static final long serialVersionUID = 1L;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtInterface(this);
    }

    @java.lang.Override
    public boolean isSubtypeOf(spoon.reflect.reference.CtTypeReference<?> type) {
        return getReference().isSubtypeOf(type);
    }

    @java.lang.Override
    public boolean isInterface() {
        return true;
    }

    @java.lang.Override
    public java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> getDeclaredExecutables() {
        java.util.Set<spoon.reflect.reference.CtTypeReference<?>> superInterfaces = getSuperInterfaces();
        if (superInterfaces.isEmpty()) {
            return super.getDeclaredExecutables();
        }
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> l = new java.util.ArrayList<>(super.getDeclaredExecutables());
        for (spoon.reflect.reference.CtTypeReference<?> sup : superInterfaces) {
            l.addAll(sup.getAllExecutables());
        }
        return java.util.Collections.unmodifiableList(l);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtInterface<T> clone() {
        return ((spoon.reflect.declaration.CtInterface<T>) (super.clone()));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtType<T>> C setSuperclass(spoon.reflect.reference.CtTypeReference<?> superClass) {
        // unsettable property
        return ((C) (this));
    }
}

