package spoon.support.reflect.reference;


public class CtExecutableReferenceImpl<T> extends spoon.support.reflect.reference.CtReferenceImpl implements spoon.reflect.reference.CtExecutableReference<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.IS_STATIC)
    boolean stat = false;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TYPE_ARGUMENT)
    java.util.List<spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TYPE)
    spoon.reflect.reference.CtTypeReference<?> declaringType;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TYPE)
    spoon.reflect.reference.CtTypeReference<T> type;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.ARGUMENT_TYPE)
    java.util.List<spoon.reflect.reference.CtTypeReference<?>> parameters = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    public CtExecutableReferenceImpl() {
        super();
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtExecutableReference(this);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.reference.CtTypeReference<?>> getActualTypeArguments() {
        return actualTypeArguments;
    }

    @java.lang.Override
    public boolean isConstructor() {
        return getSimpleName().equals(spoon.reflect.reference.CtExecutableReference.CONSTRUCTOR_NAME);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public spoon.reflect.declaration.CtExecutable<T> getDeclaration() {
        final spoon.reflect.reference.CtTypeReference<?> typeRef = getDeclaringType();
        if ((typeRef == null) || ((typeRef.getDeclaration()) == null)) {
            return null;
        }
        return getCtExecutable(typeRef.getDeclaration());
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtExecutable<T> getExecutableDeclaration() {
        return getCtExecutable(getDeclaringType().getTypeDeclaration());
    }

    private spoon.reflect.declaration.CtExecutable<T> getCtExecutable(spoon.reflect.declaration.CtType<?> typeDecl) {
        if (typeDecl == null) {
            return null;
        }
        spoon.reflect.declaration.CtExecutable<T> method = typeDecl.getMethod(getSimpleName(), parameters.toArray(new spoon.support.reflect.reference.CtTypeReferenceImpl<?>[parameters.size()]));
        if (((method == null) && (typeDecl instanceof spoon.reflect.declaration.CtClass)) && (getSimpleName().equals(spoon.reflect.reference.CtExecutableReference.CONSTRUCTOR_NAME))) {
            try {
                return ((spoon.reflect.declaration.CtExecutable<T>) (((spoon.reflect.declaration.CtClass<?>) (typeDecl)).getConstructor(parameters.toArray(new spoon.support.reflect.reference.CtTypeReferenceImpl<?>[parameters.size()]))));
            } catch (java.lang.ClassCastException e) {
                spoon.Launcher.LOGGER.error(e.getMessage(), e);
            }
        }else
            if ((method == null) && (getSimpleName().startsWith(spoon.reflect.reference.CtExecutableReference.LAMBDA_NAME_PREFIX))) {
                final java.util.List<spoon.reflect.code.CtLambda> elements = ((java.util.List<spoon.reflect.code.CtLambda>) (typeDecl.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.code.CtLambda.class, getSimpleName()))));
                if ((elements.size()) == 0) {
                    return null;
                }
                return elements.get(0);
            }

        return method;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> getDeclaringType() {
        return declaringType;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<T> getType() {
        return type;
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.reference.CtTypeReference<?>> getParameters() {
        return spoon.support.reflect.declaration.CtElementImpl.unmodifiableList(parameters);
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtExecutableReference<T>> C setParameters(java.util.List<spoon.reflect.reference.CtTypeReference<?>> parameters) {
        if ((parameters == null) || (parameters.isEmpty())) {
            this.parameters = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((C) (this));
        }
        if ((this.parameters) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptyList())) {
            this.parameters = new java.util.ArrayList<>();
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.ARGUMENT_TYPE, this.parameters, new java.util.ArrayList<>(this.parameters));
        this.parameters.clear();
        for (spoon.reflect.reference.CtTypeReference<?> parameter : parameters) {
            addParameter(parameter);
        }
        return ((C) (this));
    }

    private boolean addParameter(spoon.reflect.reference.CtTypeReference<?> parameter) {
        if (parameter == null) {
            return false;
        }
        parameter.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.ARGUMENT_TYPE, this.parameters, parameter);
        return this.parameters.add(parameter);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public <S extends T> spoon.reflect.reference.CtExecutableReference<S> getOverridingExecutable(spoon.reflect.reference.CtTypeReference<?> subType) {
        if ((subType == null) || (subType.equals(getDeclaringType()))) {
            return null;
        }
        spoon.reflect.declaration.CtType<?> t = subType.getDeclaration();
        if (t == null) {
            return null;
        }
        if (!(t instanceof spoon.reflect.declaration.CtClass)) {
            return null;
        }
        spoon.reflect.declaration.CtClass<?> c = ((spoon.reflect.declaration.CtClass<?>) (t));
        for (spoon.reflect.declaration.CtMethod<?> m : c.getMethods()) {
            if (m.getReference().isOverriding(this)) {
                return ((spoon.reflect.reference.CtExecutableReference<S>) (m.getReference()));
            }
        }
        return getOverridingExecutable(c.getSuperclass());
    }

    @java.lang.Override
    public boolean isOverriding(spoon.reflect.reference.CtExecutableReference<?> executable) {
        spoon.reflect.declaration.CtExecutable<?> exec = executable.getExecutableDeclaration();
        spoon.reflect.declaration.CtExecutable<?> thisExec = getExecutableDeclaration();
        if ((exec == null) || (thisExec == null)) {
            final boolean isSame = ((getSimpleName().equals(executable.getSimpleName())) && (getParameters().equals(executable.getParameters()))) && (getActualTypeArguments().equals(executable.getActualTypeArguments()));
            if (!isSame) {
                return false;
            }
            if (!(getDeclaringType().isSubtypeOf(executable.getDeclaringType()))) {
                return false;
            }
            return true;
        }
        if ((exec instanceof spoon.reflect.declaration.CtMethod<?>) && (thisExec instanceof spoon.reflect.declaration.CtMethod<?>)) {
            return new spoon.support.visitor.ClassTypingContext(((spoon.reflect.declaration.CtTypeMember) (thisExec)).getDeclaringType()).isOverriding(((spoon.reflect.declaration.CtMethod<?>) (thisExec)), ((spoon.reflect.declaration.CtMethod<?>) (exec)));
        }
        return exec == (getDeclaration());
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtActualTypeContainer> C setActualTypeArguments(java.util.List<? extends spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments) {
        if ((actualTypeArguments == null) || (actualTypeArguments.isEmpty())) {
            this.actualTypeArguments = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((C) (this));
        }
        if ((this.actualTypeArguments) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptyList())) {
            this.actualTypeArguments = new java.util.ArrayList<>();
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.TYPE_ARGUMENT, this.actualTypeArguments, new java.util.ArrayList<>(this.actualTypeArguments));
        this.actualTypeArguments.clear();
        for (spoon.reflect.reference.CtTypeReference<?> actualTypeArgument : actualTypeArguments) {
            addActualTypeArgument(actualTypeArgument);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtExecutableReference<T>> C setDeclaringType(spoon.reflect.reference.CtTypeReference<?> declaringType) {
        if (declaringType != null) {
            declaringType.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.DECLARING_TYPE, declaringType, this.declaringType);
        this.declaringType = declaringType;
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtExecutableReference<T>> C setType(spoon.reflect.reference.CtTypeReference<T> type) {
        if (type != null) {
            type.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.TYPE, type, this.type);
        this.type = type;
        return ((C) (this));
    }

    @java.lang.Override
    protected java.lang.reflect.AnnotatedElement getActualAnnotatedElement() {
        if (isConstructor()) {
            return getActualConstructor();
        }else {
            return getActualMethod();
        }
    }

    @java.lang.Override
    public java.lang.reflect.Method getActualMethod() {
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> parameters = this.getParameters();
        method_loop : for (java.lang.reflect.Method m : getDeclaringType().getActualClass().getDeclaredMethods()) {
            if ((!(m.getDeclaringClass().isSynthetic())) && (m.isSynthetic())) {
                continue;
            }
            if (!(m.getName().equals(getSimpleName()))) {
                continue;
            }
            if ((m.getParameterTypes().length) != (parameters.size())) {
                continue;
            }
            for (int i = 0; i < (parameters.size()); i++) {
                java.lang.Class<?> methodParameterType = m.getParameterTypes()[i];
                java.lang.Class<?> currentParameterType = parameters.get(i).getActualClass();
                if (methodParameterType != currentParameterType) {
                    continue method_loop;
                }
            }
            return m;
        }
        return null;
    }

    public java.lang.reflect.Constructor<?> getActualConstructor() {
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> parameters = this.getParameters();
        constructor_loop : for (java.lang.reflect.Constructor<?> c : getDeclaringType().getActualClass().getDeclaredConstructors()) {
            if ((c.getParameterTypes().length) != (parameters.size())) {
                continue;
            }
            for (int i = 0; i < (parameters.size()); i++) {
                if ((c.getParameterTypes()[i]) != (parameters.get(i).getActualClass())) {
                    continue constructor_loop;
                }
            }
            return c;
        }
        return null;
    }

    public boolean isStatic() {
        return stat;
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtExecutableReference<T>> C setStatic(boolean stat) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.IS_STATIC, stat, this.stat);
        this.stat = stat;
        return ((C) (this));
    }

    @java.lang.Override
    public boolean isFinal() {
        spoon.reflect.declaration.CtExecutable<T> e = getDeclaration();
        if (e != null) {
            if (e instanceof spoon.reflect.declaration.CtMethod) {
                return ((spoon.reflect.declaration.CtMethod<T>) (e)).hasModifier(spoon.reflect.declaration.ModifierKind.FINAL);
            }else
                if (e instanceof spoon.reflect.declaration.CtConstructor) {
                    return ((spoon.reflect.declaration.CtConstructor<T>) (e)).hasModifier(spoon.reflect.declaration.ModifierKind.FINAL);
                }

            return false;
        }
        java.lang.reflect.Method m = getActualMethod();
        return (m != null) && (java.lang.reflect.Modifier.isFinal(m.getModifiers()));
    }

    public java.util.Set<spoon.reflect.declaration.ModifierKind> getModifiers() {
        spoon.reflect.declaration.CtExecutable<T> e = getDeclaration();
        if (e != null) {
            if (e instanceof spoon.reflect.declaration.CtMethod) {
                return ((spoon.reflect.declaration.CtMethod<T>) (e)).getModifiers();
            }else
                if (e instanceof spoon.reflect.declaration.CtConstructor) {
                    return ((spoon.reflect.declaration.CtConstructor<T>) (e)).getModifiers();
                }

            return spoon.support.reflect.declaration.CtElementImpl.emptySet();
        }
        java.lang.reflect.Method m = getActualMethod();
        if (m != null) {
            return spoon.support.util.RtHelper.getModifiers(m.getModifiers());
        }
        java.lang.reflect.Constructor<?> c = getActualConstructor();
        if (c != null) {
            return spoon.support.util.RtHelper.getModifiers(c.getModifiers());
        }
        return java.util.Collections.emptySet();
    }

    @java.lang.Override
    public spoon.reflect.reference.CtExecutableReference<?> getOverridingExecutable() {
        spoon.reflect.reference.CtTypeReference<?> st = getDeclaringType().getSuperclass();
        spoon.reflect.reference.CtTypeReference<java.lang.Object> objectType = getFactory().Type().OBJECT;
        if (st == null) {
            return getOverloadedExecutable(objectType, objectType);
        }
        return getOverloadedExecutable(st, objectType);
    }

    private spoon.reflect.reference.CtExecutableReference<?> getOverloadedExecutable(spoon.reflect.reference.CtTypeReference<?> t, spoon.reflect.reference.CtTypeReference<java.lang.Object> objectType) {
        if (t == null) {
            return null;
        }
        for (spoon.reflect.reference.CtExecutableReference<?> e : t.getDeclaredExecutables()) {
            if (this.isOverriding(e)) {
                return e;
            }
        }
        if (t.equals(objectType)) {
            return null;
        }
        spoon.reflect.reference.CtTypeReference<?> st = t.getSuperclass();
        if (st == null) {
            return getOverloadedExecutable(objectType, objectType);
        }
        return getOverloadedExecutable(t.getSuperclass(), objectType);
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtActualTypeContainer> C addActualTypeArgument(spoon.reflect.reference.CtTypeReference<?> actualTypeArgument) {
        if (actualTypeArgument == null) {
            return ((C) (this));
        }
        if ((actualTypeArguments) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptyList())) {
            actualTypeArguments = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.METHOD_TYPE_PARAMETERS_CONTAINER_DEFAULT_CAPACITY);
        }
        actualTypeArgument.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.TYPE_ARGUMENT, this.actualTypeArguments, actualTypeArgument);
        actualTypeArguments.add(actualTypeArgument);
        return ((C) (this));
    }

    @java.lang.Override
    public boolean removeActualTypeArgument(spoon.reflect.reference.CtTypeReference<?> actualTypeArgument) {
        if ((actualTypeArguments) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptyList())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.TYPE_ARGUMENT, actualTypeArguments, actualTypeArguments.indexOf(actualTypeArgument), actualTypeArgument);
        return actualTypeArguments.remove(actualTypeArgument);
    }

    @java.lang.Override
    public java.lang.String getSignature() {
        final spoon.support.visitor.SignaturePrinter pr = new spoon.support.visitor.SignaturePrinter();
        pr.scan(this);
        return pr.getSignature();
    }

    @java.lang.Override
    public spoon.reflect.reference.CtExecutableReference<T> clone() {
        return ((spoon.reflect.reference.CtExecutableReference<T>) (super.clone()));
    }
}

