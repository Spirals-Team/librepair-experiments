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


/**
 * The implementation for {@link spoon.reflect.declaration.CtExecutable}.
 *
 * @author Renaud Pawlak
 */
public abstract class CtExecutableImpl<R> extends spoon.support.reflect.declaration.CtNamedElementImpl implements spoon.reflect.declaration.CtExecutable<R> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.BODY)
    spoon.reflect.code.CtBlock<?> body;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.PARAMETER)
    java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.THROWN)
    java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes = spoon.support.reflect.declaration.CtElementImpl.emptySet();

    public CtExecutableImpl() {
        super();
    }

    public spoon.reflect.declaration.CtType<?> getDeclaringType() {
        return ((spoon.reflect.declaration.CtType<?>) (parent));
    }

    public <T> spoon.reflect.declaration.CtType<T> getTopLevelType() {
        return getDeclaringType().getTopLevelType();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public spoon.reflect.code.CtBlock<R> getBody() {
        return ((spoon.reflect.code.CtBlock<R>) (body));
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
    public java.util.List<spoon.reflect.declaration.CtParameter<?>> getParameters() {
        return parameters;
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtExecutable<R>> T setParameters(java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters) {
        if ((parameters == null) || (parameters.isEmpty())) {
            this.parameters = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((T) (this));
        }
        if ((this.parameters) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtParameter<?>>emptyList())) {
            this.parameters = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.PARAMETERS_CONTAINER_DEFAULT_CAPACITY);
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.PARAMETER, this.parameters, new java.util.ArrayList<>(this.parameters));
        this.parameters.clear();
        for (spoon.reflect.declaration.CtParameter<?> p : parameters) {
            addParameter(p);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtExecutable<R>> T addParameter(spoon.reflect.declaration.CtParameter<?> parameter) {
        if (parameter == null) {
            return ((T) (this));
        }
        if ((parameters) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtParameter<?>>emptyList())) {
            parameters = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.PARAMETERS_CONTAINER_DEFAULT_CAPACITY);
        }
        parameter.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.PARAMETER, this.parameters, parameter);
        parameters.add(parameter);
        return ((T) (this));
    }

    @java.lang.Override
    public boolean removeParameter(spoon.reflect.declaration.CtParameter<?> parameter) {
        if ((parameters) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtParameter<?>>emptyList())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.PARAMETER, parameters, parameters.indexOf(parameter), parameter);
        return parameters.remove(parameter);
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> getThrownTypes() {
        return thrownTypes;
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtExecutable<R>> T setThrownTypes(java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes) {
        if ((thrownTypes == null) || (thrownTypes.isEmpty())) {
            this.thrownTypes = spoon.support.reflect.declaration.CtElementImpl.emptySet();
            return ((T) (this));
        }
        if ((this.thrownTypes) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>>emptySet())) {
            this.thrownTypes = new spoon.support.util.QualifiedNameBasedSortedSet<>();
        }
        getFactory().getEnvironment().getModelChangeListener().onSetDeleteAll(this, spoon.reflect.path.CtRole.THROWN, this.thrownTypes, new java.util.HashSet<java.lang.Object>(this.thrownTypes));
        this.thrownTypes.clear();
        for (spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable> thrownType : thrownTypes) {
            addThrownType(thrownType);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtExecutable<R>> T addThrownType(spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable> throwType) {
        if (throwType == null) {
            return ((T) (this));
        }
        if ((thrownTypes) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>>emptySet())) {
            thrownTypes = new spoon.support.util.QualifiedNameBasedSortedSet<>();
        }
        throwType.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onSetAdd(this, spoon.reflect.path.CtRole.THROWN, this.thrownTypes, throwType);
        thrownTypes.add(throwType);
        return ((T) (this));
    }

    @java.lang.Override
    public boolean removeThrownType(spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable> throwType) {
        if ((thrownTypes) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>>emptySet())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onSetDelete(this, spoon.reflect.path.CtRole.THROWN, thrownTypes, throwType);
        return thrownTypes.remove(throwType);
    }

    @java.lang.Override
    public java.lang.String getSignature() {
        final spoon.support.visitor.SignaturePrinter pr = new spoon.support.visitor.SignaturePrinter();
        pr.scan(this);
        return pr.getSignature();
    }

    @java.lang.Override
    public spoon.reflect.reference.CtExecutableReference<R> getReference() {
        return getFactory().Executable().createReference(this);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtExecutable<R> clone() {
        return ((spoon.reflect.declaration.CtExecutable<R>) (super.clone()));
    }
}

