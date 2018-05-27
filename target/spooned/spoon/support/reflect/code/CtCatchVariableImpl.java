package spoon.support.reflect.code;


public class CtCatchVariableImpl<T> extends spoon.support.reflect.code.CtCodeElementImpl implements spoon.reflect.code.CtCatchVariable<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.NAME)
    java.lang.String name = "";

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.MULTI_TYPE)
    java.util.List<spoon.reflect.reference.CtTypeReference<?>> types = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.MODIFIER)
    private spoon.support.reflect.CtModifierHandler modifierHandler = new spoon.support.reflect.CtModifierHandler(this);

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtCatchVariable(this);
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public spoon.reflect.code.CtExpression<T> getDefaultExpression() {
        return null;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtCatchVariableReference<T> getReference() {
        return getFactory().Code().createCatchVariableReference(this);
    }

    @java.lang.Override
    public java.lang.String getSimpleName() {
        return name;
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    @spoon.support.DerivedProperty
    public spoon.reflect.reference.CtTypeReference<T> getType() {
        if (types.isEmpty()) {
            return null;
        }else
            if ((types.size()) == 1) {
                return ((spoon.reflect.reference.CtTypeReference<T>) (types.get(0)));
            }

        java.util.List<spoon.reflect.reference.CtTypeReference<?>> superTypesOfFirst = types.get(0).map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingInterfaces(false).includingSelf(true).returnTypeReferences(true)).list();
        if (superTypesOfFirst.isEmpty()) {
            return null;
        }
        int commonSuperTypeIdx = 0;
        int throwableIdx = (superTypesOfFirst.size()) - 2;
        for (int i = 1; (i < (types.size())) && (commonSuperTypeIdx != throwableIdx); i++) {
            spoon.reflect.reference.CtTypeReference<?> nextException = types.get(i);
            while (commonSuperTypeIdx < throwableIdx) {
                if (nextException.isSubtypeOf(superTypesOfFirst.get(commonSuperTypeIdx))) {
                    break;
                }
                commonSuperTypeIdx++;
            } 
        }
        return ((spoon.reflect.reference.CtTypeReference<T>) (superTypesOfFirst.get(commonSuperTypeIdx)));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtVariable<T>> C setDefaultExpression(spoon.reflect.code.CtExpression<T> defaultExpression) {
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtNamedElement> C setSimpleName(java.lang.String simpleName) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.NAME, simpleName, this.name);
        this.name = simpleName;
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtTypedElement> C setType(spoon.reflect.reference.CtTypeReference<T> type) {
        setMultiTypes((type == null ? spoon.support.reflect.declaration.CtElementImpl.emptyList() : java.util.Collections.singletonList(type)));
        return ((C) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtMultiTypedElement> T addMultiType(spoon.reflect.reference.CtTypeReference<?> type) {
        if (type == null) {
            return ((T) (this));
        }
        if ((types) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptyList())) {
            types = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.CATCH_VARIABLE_MULTI_TYPES_CONTAINER_DEFAULT_CAPACITY);
        }
        type.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.MULTI_TYPE, this.types, type);
        types.add(type);
        return ((T) (this));
    }

    @java.lang.Override
    public boolean removeMultiType(spoon.reflect.reference.CtTypeReference<?> ref) {
        if ((this.types) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptyList())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.MULTI_TYPE, types, types.indexOf(ref), ref);
        return types.remove(ref);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.reference.CtTypeReference<?>> getMultiTypes() {
        return types;
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtMultiTypedElement> T setMultiTypes(java.util.List<spoon.reflect.reference.CtTypeReference<?>> types) {
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.MULTI_TYPE, this.types, new java.util.ArrayList<>(this.types));
        if ((types == null) || (types.isEmpty())) {
            this.types = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((T) (this));
        }
        if ((this.types) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptyList())) {
            this.types = new java.util.ArrayList<>();
        }
        this.types.clear();
        for (spoon.reflect.reference.CtTypeReference<?> t : types) {
            addMultiType(t);
        }
        return ((T) (this));
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
        if (getModifiers().contains(spoon.reflect.declaration.ModifierKind.PUBLIC)) {
            return spoon.reflect.declaration.ModifierKind.PUBLIC;
        }
        if (getModifiers().contains(spoon.reflect.declaration.ModifierKind.PROTECTED)) {
            return spoon.reflect.declaration.ModifierKind.PROTECTED;
        }
        if (getModifiers().contains(spoon.reflect.declaration.ModifierKind.PRIVATE)) {
            return spoon.reflect.declaration.ModifierKind.PRIVATE;
        }
        return null;
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
    public spoon.reflect.code.CtCatchVariable<T> clone() {
        return ((spoon.reflect.code.CtCatchVariable<T>) (super.clone()));
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
}

