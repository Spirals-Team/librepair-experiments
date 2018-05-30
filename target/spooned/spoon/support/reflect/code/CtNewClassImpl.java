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


public class CtNewClassImpl<T> extends spoon.support.reflect.code.CtConstructorCallImpl<T> implements spoon.reflect.code.CtNewClass<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.NESTED_TYPE)
    spoon.reflect.declaration.CtClass<?> anonymousClass;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtNewClass(this);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtClass<?> getAnonymousClass() {
        return anonymousClass;
    }

    @java.lang.Override
    public <N extends spoon.reflect.code.CtNewClass> N setAnonymousClass(spoon.reflect.declaration.CtClass<?> anonymousClass) {
        if (anonymousClass != null) {
            anonymousClass.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.NESTED_TYPE, anonymousClass, this.anonymousClass);
        this.anonymousClass = anonymousClass;
        return ((N) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtNewClass<T> clone() {
        return ((spoon.reflect.code.CtNewClass<T>) (super.clone()));
    }
}

