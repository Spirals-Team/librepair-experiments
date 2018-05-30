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


public class CtWildcardReferenceImpl extends spoon.support.reflect.reference.CtTypeParameterReferenceImpl implements spoon.reflect.reference.CtWildcardReference {
    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtWildcardReference(this);
    }

    public CtWildcardReferenceImpl() {
        simplename = "?";
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <T extends spoon.reflect.reference.CtReference> T setSimpleName(java.lang.String simplename) {
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.reference.CtWildcardReference clone() {
        return ((spoon.reflect.reference.CtWildcardReference) (super.clone()));
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtType<java.lang.Object> getTypeDeclaration() {
        return getFactory().Type().get(java.lang.Object.class);
    }
}

