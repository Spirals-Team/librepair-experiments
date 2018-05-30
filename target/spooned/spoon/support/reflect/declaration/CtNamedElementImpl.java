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


public abstract class CtNamedElementImpl extends spoon.support.reflect.declaration.CtElementImpl implements spoon.reflect.declaration.CtNamedElement {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.NAME)
    java.lang.String simpleName = "";

    @java.lang.Override
    public spoon.reflect.reference.CtReference getReference() {
        return null;
    }

    @java.lang.Override
    public java.lang.String getSimpleName() {
        return simpleName;
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtNamedElement> T setSimpleName(java.lang.String simpleName) {
        spoon.reflect.factory.Factory factory = getFactory();
        if (factory == null) {
            this.simpleName = simpleName;
            return ((T) (this));
        }
        if (factory instanceof spoon.reflect.factory.FactoryImpl) {
            simpleName = ((spoon.reflect.factory.FactoryImpl) (factory)).dedup(simpleName);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.NAME, simpleName, this.simpleName);
        this.simpleName = simpleName;
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtNamedElement clone() {
        return ((spoon.reflect.declaration.CtNamedElement) (super.clone()));
    }
}

