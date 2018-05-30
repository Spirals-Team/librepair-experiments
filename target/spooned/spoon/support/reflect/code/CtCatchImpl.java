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


public class CtCatchImpl extends spoon.support.reflect.code.CtCodeElementImpl implements spoon.reflect.code.CtCatch {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.BODY)
    spoon.reflect.code.CtBlock<?> body;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.PARAMETER)
    spoon.reflect.code.CtCatchVariable<? extends java.lang.Throwable> parameter;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtCatch(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtBlock<?> getBody() {
        return body;
    }

    @java.lang.Override
    public spoon.reflect.code.CtCatchVariable<? extends java.lang.Throwable> getParameter() {
        return parameter;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtBodyHolder> T setBody(spoon.reflect.code.CtStatement statement) {
        if (statement != null) {
            spoon.reflect.code.CtBlock<?> body = getFactory().Code().getOrCreateCtBlock(statement);
            getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.BODY, body, this.body);
            if (body != null) {
                body.setParent(this);
            }
            this.body = body;
        }else {
            getFactory().getEnvironment().getModelChangeListener().onObjectDelete(this, spoon.reflect.path.CtRole.BODY, this.body);
            this.body = null;
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtCatch> T setParameter(spoon.reflect.code.CtCatchVariable<? extends java.lang.Throwable> parameter) {
        if (parameter != null) {
            parameter.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.PARAMETER, parameter, this.parameter);
        this.parameter = parameter;
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtCatch clone() {
        return ((spoon.reflect.code.CtCatch) (super.clone()));
    }
}

