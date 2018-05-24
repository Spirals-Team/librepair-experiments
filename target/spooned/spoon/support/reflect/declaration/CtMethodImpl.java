package spoon.support.reflect.declaration;


public class CtMethodImpl<T> extends spoon.support.reflect.declaration.CtExecutableImpl<T> implements spoon.reflect.declaration.CtMethod<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TYPE)
    spoon.reflect.reference.CtTypeReference<T> returnType;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.IS_DEFAULT)
    boolean defaultMethod = false;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TYPE_PARAMETER)
    java.util.List<spoon.reflect.declaration.CtTypeParameter> formalCtTypeParameters = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.MODIFIER)
    private spoon.support.reflect.CtModifierHandler modifierHandler = new spoon.support.reflect.CtModifierHandler(this);

    public CtMethodImpl() {
        super();
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor v) {
        v.visitCtMethod(this);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<T> getType() {
        return returnType;
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtTypedElement> C setType(spoon.reflect.reference.CtTypeReference<T> type) {
        if (type != null) {
            type.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.TYPE, type, this.returnType);
        this.returnType = type;
        return ((C) (this));
    }

    @java.lang.Override
    public boolean isDefaultMethod() {
        return defaultMethod;
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtMethod<T>> C setDefaultMethod(boolean defaultMethod) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.IS_DEFAULT, defaultMethod, this.defaultMethod);
        this.defaultMethod = defaultMethod;
        return ((C) (this));
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.declaration.CtTypeParameter> getFormalCtTypeParameters() {
        return formalCtTypeParameters;
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtFormalTypeDeclarer> C setFormalCtTypeParameters(java.util.List<spoon.reflect.declaration.CtTypeParameter> formalTypeParameters) {
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.TYPE_PARAMETER, this.formalCtTypeParameters, new java.util.ArrayList<>(this.formalCtTypeParameters));
        if ((formalTypeParameters == null) || (formalTypeParameters.isEmpty())) {
            this.formalCtTypeParameters = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((C) (this));
        }
        if ((this.formalCtTypeParameters) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtTypeParameter>emptyList())) {
            this.formalCtTypeParameters = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.TYPE_TYPE_PARAMETERS_CONTAINER_DEFAULT_CAPACITY);
        }
        this.formalCtTypeParameters.clear();
        for (spoon.reflect.declaration.CtTypeParameter formalTypeParameter : formalTypeParameters) {
            addFormalCtTypeParameter(formalTypeParameter);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtFormalTypeDeclarer> C addFormalCtTypeParameter(spoon.reflect.declaration.CtTypeParameter formalTypeParameter) {
        if (formalTypeParameter == null) {
            return ((C) (this));
        }
        if ((formalCtTypeParameters) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtTypeParameter>emptyList())) {
            formalCtTypeParameters = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.TYPE_TYPE_PARAMETERS_CONTAINER_DEFAULT_CAPACITY);
        }
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.TYPE_PARAMETER, this.formalCtTypeParameters, formalTypeParameter);
        formalTypeParameter.setParent(this);
        formalCtTypeParameters.add(formalTypeParameter);
        return ((C) (this));
    }

    @java.lang.Override
    public boolean removeFormalCtTypeParameter(spoon.reflect.declaration.CtTypeParameter formalTypeParameter) {
        if ((formalCtTypeParameters) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtTypeParameter>emptyList())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.TYPE_PARAMETER, formalCtTypeParameters, formalCtTypeParameters.indexOf(formalTypeParameter), formalTypeParameter);
        return formalCtTypeParameters.remove(formalTypeParameter);
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.ModifierKind> getModifiers() {
        return modifierHandler.getModifiers();
    }

    @java.lang.Override
    public boolean hasModifier(spoon.reflect.declaration.ModifierKind modifier) {
        return getModifiers().contains(modifier);
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtModifiable> C setModifiers(java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers) {
        modifierHandler.setModifiers(modifiers);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtModifiable> C addModifier(spoon.reflect.declaration.ModifierKind modifier) {
        modifierHandler.addModifier(modifier);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtModifiable> C removeModifier(spoon.reflect.declaration.ModifierKind modifier) {
        modifierHandler.removeModifier(modifier);
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtModifiable> C setVisibility(spoon.reflect.declaration.ModifierKind visibility) {
        modifierHandler.setVisibility(visibility);
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.declaration.ModifierKind getVisibility() {
        return modifierHandler.getVisibility();
    }

    @java.lang.Override
    public java.util.Set<spoon.support.reflect.CtExtendedModifier> getExtendedModifiers() {
        return this.modifierHandler.getExtendedModifiers();
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtModifiable> C setExtendedModifiers(java.util.Set<spoon.support.reflect.CtExtendedModifier> extendedModifiers) {
        this.modifierHandler.setExtendedModifiers(extendedModifiers);
        return ((C) (this));
    }

    @java.lang.Override
    public boolean isOverriding(spoon.reflect.declaration.CtMethod<?> superMethod) {
        return new spoon.support.visitor.ClassTypingContext(getDeclaringType()).isOverriding(this, superMethod);
    }

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.IS_SHADOW)
    boolean isShadow;

    @java.lang.Override
    public boolean isShadow() {
        return isShadow;
    }

    @java.lang.Override
    public <E extends spoon.reflect.declaration.CtShadowable> E setShadow(boolean isShadow) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.IS_SHADOW, isShadow, this.isShadow);
        this.isShadow = isShadow;
        return ((E) (this));
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtMethod<T> clone() {
        return ((spoon.reflect.declaration.CtMethod<T>) (super.clone()));
    }

    @java.lang.Override
    public java.util.Collection<spoon.reflect.declaration.CtMethod<?>> getTopDefinitions() {
        java.util.List<spoon.reflect.declaration.CtMethod<?>> s = new java.util.ArrayList<>();
        spoon.support.visitor.ClassTypingContext context = new spoon.support.visitor.ClassTypingContext(this.getDeclaringType());
        getDeclaringType().map(new spoon.reflect.visitor.filter.AllTypeMembersFunction(spoon.reflect.declaration.CtMethod.class)).forEach((spoon.reflect.declaration.CtMethod<?> m) -> {
            if ((m != (this)) && (context.isOverriding(this, m))) {
                s.add(m);
            }
        });
        java.util.List<spoon.reflect.declaration.CtMethod<?>> finalMeths = new java.util.ArrayList<>(s);
        for (spoon.reflect.declaration.CtMethod m1 : s) {
            boolean m1IsIntermediate = false;
            for (spoon.reflect.declaration.CtMethod m2 : s) {
                if (context.isOverriding(m1, m2)) {
                    m1IsIntermediate = true;
                }
            }
            if (!m1IsIntermediate) {
                finalMeths.add(m1);
            }
        }
        return finalMeths;
    }

    @java.lang.Override
    public boolean isPublic() {
        return this.modifierHandler.isPublic();
    }

    @java.lang.Override
    public boolean isPrivate() {
        return this.modifierHandler.isPrivate();
    }

    @java.lang.Override
    public boolean isProtected() {
        return this.modifierHandler.isProtected();
    }

    @java.lang.Override
    public boolean isFinal() {
        return this.modifierHandler.isFinal();
    }

    @java.lang.Override
    public boolean isStatic() {
        return this.modifierHandler.isStatic();
    }

    @java.lang.Override
    public boolean isAbstract() {
        return this.modifierHandler.isAbstract();
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtMethod<?> copyMethod() {
        return spoon.refactoring.Refactoring.copyMethod(this);
    }
}

