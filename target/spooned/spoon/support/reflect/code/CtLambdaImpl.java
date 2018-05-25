package spoon.support.reflect.code;


public class CtLambdaImpl<T> extends spoon.support.reflect.code.CtExpressionImpl<T> implements spoon.reflect.code.CtLambda<T> {
    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.NAME)
    java.lang.String simpleName = "";

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<T> expression;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.BODY)
    spoon.reflect.code.CtBlock<?> body;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.PARAMETER)
    java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.THROWN)
    java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes = spoon.support.reflect.declaration.CtElementImpl.emptySet();

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtLambda(this);
    }

    @java.lang.Override
    public java.lang.String getSimpleName() {
        return simpleName;
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtNamedElement> C setSimpleName(java.lang.String simpleName) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.NAME, simpleName, this.simpleName);
        this.simpleName = simpleName;
        return ((C) (this));
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public spoon.reflect.code.CtBlock<T> getBody() {
        return ((spoon.reflect.code.CtBlock<T>) (body));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtBodyHolder> C setBody(spoon.reflect.code.CtStatement statement) {
        if (statement != null) {
            spoon.reflect.code.CtBlock<?> body = getFactory().Code().getOrCreateCtBlock(statement);
            getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.BODY, body, this.body);
            if (((expression) != null) && (body != null)) {
                throw new spoon.SpoonException("A lambda can't have two bodys.");
            }
            if (body != null) {
                body.setParent(this);
            }
            this.body = body;
        }else {
            getFactory().getEnvironment().getModelChangeListener().onObjectDelete(this, spoon.reflect.path.CtRole.BODY, this.body);
            this.body = null;
        }
        return ((C) (this));
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public <R> spoon.reflect.declaration.CtMethod<R> getOverriddenMethod() {
        spoon.reflect.reference.CtTypeReference<T> lambdaTypeRef = getType();
        if (lambdaTypeRef == null) {
            return null;
        }
        spoon.reflect.declaration.CtType<T> lambdaType = lambdaTypeRef.getTypeDeclaration();
        if ((lambdaType.isInterface()) == false) {
            throw new spoon.SpoonException((("The lambda can be based on interface only. But type " + (lambdaTypeRef.getQualifiedName())) + " is not an interface"));
        }
        java.util.Set<spoon.reflect.declaration.CtMethod<?>> lambdaTypeMethods = lambdaType.getAllMethods();
        spoon.reflect.declaration.CtMethod<?> lambdaExecutableMethod = null;
        if ((lambdaTypeMethods.size()) == 1) {
            lambdaExecutableMethod = lambdaTypeMethods.iterator().next();
        }else {
            for (spoon.reflect.declaration.CtMethod<?> method : lambdaTypeMethods) {
                if (((method.isDefaultMethod()) || (method.hasModifier(spoon.reflect.declaration.ModifierKind.PRIVATE))) || (method.hasModifier(spoon.reflect.declaration.ModifierKind.STATIC))) {
                    continue;
                }
                if (lambdaExecutableMethod != null) {
                    throw new spoon.SpoonException(((((("The lambda can be based on interface, which has only one method. But " + (lambdaTypeRef.getQualifiedName())) + " has at least two: ") + (lambdaExecutableMethod.getSignature())) + " and ") + (method.getSignature())));
                }
                lambdaExecutableMethod = method;
            }
        }
        if (lambdaExecutableMethod == null) {
            throw new spoon.SpoonException((("The lambda can be based on interface, which has one method. But " + (lambdaTypeRef.getQualifiedName())) + " has no one"));
        }
        return ((spoon.reflect.declaration.CtMethod<R>) (lambdaExecutableMethod));
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.declaration.CtParameter<?>> getParameters() {
        return spoon.support.reflect.declaration.CtElementImpl.unmodifiableList(parameters);
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtExecutable<T>> C setParameters(java.util.List<spoon.reflect.declaration.CtParameter<?>> params) {
        if ((params == null) || (params.isEmpty())) {
            this.parameters = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((C) (this));
        }
        if ((this.parameters) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtParameter<?>>emptyList())) {
            this.parameters = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.PARAMETERS_CONTAINER_DEFAULT_CAPACITY);
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.PARAMETER, this.parameters, new java.util.ArrayList<>(this.parameters));
        this.parameters.clear();
        for (spoon.reflect.declaration.CtParameter<?> p : params) {
            addParameter(p);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtExecutable<T>> C addParameter(spoon.reflect.declaration.CtParameter<?> parameter) {
        if (parameter == null) {
            return ((C) (this));
        }
        if ((parameters) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtParameter<?>>emptyList())) {
            parameters = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.PARAMETERS_CONTAINER_DEFAULT_CAPACITY);
        }
        parameter.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.PARAMETER, this.parameters, parameter);
        parameters.add(parameter);
        return ((C) (this));
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
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtExecutable<T>> C setThrownTypes(java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes) {
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtExecutable<T>> C addThrownType(spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable> throwType) {
        if (throwType == null) {
            return ((C) (this));
        }
        if ((thrownTypes) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>>emptySet())) {
            thrownTypes = new spoon.support.util.QualifiedNameBasedSortedSet<>();
        }
        throwType.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onSetAdd(this, spoon.reflect.path.CtRole.THROWN, this.thrownTypes, throwType);
        thrownTypes.add(throwType);
        return ((C) (this));
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
    public spoon.reflect.reference.CtExecutableReference<T> getReference() {
        return getFactory().Executable().createReference(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<T> getExpression() {
        return expression;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtLambda<T>> C setExpression(spoon.reflect.code.CtExpression<T> expression) {
        if (((body) != null) && (expression != null)) {
            throw new spoon.SpoonException("A lambda can't have two bodies.");
        }else {
            if (expression != null) {
                expression.setParent(this);
            }
            getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.EXPRESSION, expression, this.expression);
            this.expression = expression;
        }
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtLambda<T> clone() {
        return ((spoon.reflect.code.CtLambda<T>) (super.clone()));
    }
}

