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


public abstract class CtExpressionImpl<T> extends spoon.support.reflect.code.CtCodeElementImpl implements spoon.reflect.code.CtExpression<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TYPE)
    spoon.reflect.reference.CtTypeReference<T> type;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.CAST)
    java.util.List<spoon.reflect.reference.CtTypeReference<?>> typeCasts = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    public spoon.reflect.reference.CtTypeReference<T> getType() {
        return type;
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.reference.CtTypeReference<?>> getTypeCasts() {
        return typeCasts;
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtTypedElement> C setType(spoon.reflect.reference.CtTypeReference<T> type) {
        if (type != null) {
            type.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.TYPE, type, this.type);
        this.type = type;
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtExpression<T>> C setTypeCasts(java.util.List<spoon.reflect.reference.CtTypeReference<?>> casts) {
        if ((casts == null) || (casts.isEmpty())) {
            this.typeCasts = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((C) (this));
        }
        if ((this.typeCasts) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptyList())) {
            this.typeCasts = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.CASTS_CONTAINER_DEFAULT_CAPACITY);
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.CAST, this.typeCasts, new java.util.ArrayList<>(this.typeCasts));
        this.typeCasts.clear();
        for (spoon.reflect.reference.CtTypeReference<?> cast : casts) {
            addTypeCast(cast);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtExpression<T>> C addTypeCast(spoon.reflect.reference.CtTypeReference<?> type) {
        if (type == null) {
            return ((C) (this));
        }
        if ((typeCasts) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptyList())) {
            typeCasts = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.CASTS_CONTAINER_DEFAULT_CAPACITY);
        }
        type.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.CAST, this.typeCasts, type);
        typeCasts.add(type);
        return ((C) (this));
    }

    @java.lang.Override
    public T S() {
        return null;
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<T> clone() {
        return ((spoon.reflect.code.CtExpression<T>) (super.clone()));
    }
}

