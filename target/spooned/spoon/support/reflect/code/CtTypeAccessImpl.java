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


public class CtTypeAccessImpl<A> extends spoon.support.reflect.code.CtExpressionImpl<java.lang.Void> implements spoon.reflect.code.CtTypeAccess<A> {
    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.ACCESSED_TYPE)
    private spoon.reflect.reference.CtTypeReference<A> type;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtTypeAccess(this);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<A> getAccessedType() {
        return type;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtTypeAccess<A>> C setAccessedType(spoon.reflect.reference.CtTypeReference<A> accessedType) {
        if (accessedType != null) {
            accessedType.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.ACCESSED_TYPE, accessedType, this.type);
        type = accessedType;
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<java.lang.Void> getType() {
        return ((spoon.reflect.reference.CtTypeReference<java.lang.Void>) (getFactory().Type().VOID_PRIMITIVE.clone().<spoon.reflect.code.CtTypeAccess>setParent(this)));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtTypedElement> C setType(spoon.reflect.reference.CtTypeReference<java.lang.Void> type) {
        // type is used in setAccessedType now.
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtTypeAccess<A> clone() {
        return ((spoon.reflect.code.CtTypeAccess<A>) (super.clone()));
    }
}

