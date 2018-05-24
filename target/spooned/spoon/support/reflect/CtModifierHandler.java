package spoon.support.reflect;


public class CtModifierHandler implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private java.util.Set<spoon.support.reflect.CtExtendedModifier> modifiers = spoon.support.reflect.declaration.CtElementImpl.emptySet();

    private spoon.reflect.declaration.CtElement element;

    public CtModifierHandler(spoon.reflect.declaration.CtElement element) {
        this.element = element;
    }

    public spoon.reflect.factory.Factory getFactory() {
        return element.getFactory();
    }

    public java.util.Set<spoon.support.reflect.CtExtendedModifier> getExtendedModifiers() {
        return this.modifiers;
    }

    public spoon.support.reflect.CtModifierHandler setExtendedModifiers(java.util.Set<spoon.support.reflect.CtExtendedModifier> extendedModifiers) {
        if ((extendedModifiers != null) && ((extendedModifiers.size()) > 0)) {
            getFactory().getEnvironment().getModelChangeListener().onSetDeleteAll(element, spoon.reflect.path.CtRole.MODIFIER, this.modifiers, new java.util.HashSet<>(this.modifiers));
            if ((this.modifiers) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.support.reflect.CtExtendedModifier>emptySet())) {
                this.modifiers = new java.util.HashSet<>();
            }else {
                this.modifiers.clear();
            }
            for (spoon.support.reflect.CtExtendedModifier extendedModifier : extendedModifiers) {
                getFactory().getEnvironment().getModelChangeListener().onSetAdd(element, spoon.reflect.path.CtRole.MODIFIER, this.modifiers, extendedModifier.getKind());
                this.modifiers.add(extendedModifier);
            }
        }
        return this;
    }

    public java.util.Set<spoon.reflect.declaration.ModifierKind> getModifiers() {
        return modifiers.stream().map(spoon.support.reflect.CtExtendedModifier::getKind).collect(java.util.stream.Collectors.toSet());
    }

    public boolean hasModifier(spoon.reflect.declaration.ModifierKind modifier) {
        return getModifiers().contains(modifier);
    }

    public spoon.support.reflect.CtModifierHandler setModifiers(java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers) {
        if (modifiers == null) {
            modifiers = java.util.Collections.emptySet();
        }
        getFactory().getEnvironment().getModelChangeListener().onSetDeleteAll(element, spoon.reflect.path.CtRole.MODIFIER, this.modifiers, new java.util.HashSet<>(this.modifiers));
        this.modifiers.clear();
        for (spoon.reflect.declaration.ModifierKind modifier : modifiers) {
            addModifier(modifier);
        }
        return this;
    }

    public spoon.support.reflect.CtModifierHandler addModifier(spoon.reflect.declaration.ModifierKind modifier) {
        if ((this.modifiers) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.support.reflect.CtExtendedModifier>emptySet())) {
            this.modifiers = new java.util.HashSet<>();
        }
        getFactory().getEnvironment().getModelChangeListener().onSetAdd(element, spoon.reflect.path.CtRole.MODIFIER, this.modifiers, modifier);
        modifiers.remove(new spoon.support.reflect.CtExtendedModifier(modifier, true));
        modifiers.add(new spoon.support.reflect.CtExtendedModifier(modifier));
        return this;
    }

    public spoon.support.reflect.CtModifierHandler removeModifier(spoon.reflect.declaration.ModifierKind modifier) {
        if ((this.modifiers) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.support.reflect.CtExtendedModifier>emptySet())) {
            return this;
        }
        getFactory().getEnvironment().getModelChangeListener().onSetDelete(element, spoon.reflect.path.CtRole.MODIFIER, modifiers, modifier);
        modifiers.remove(new spoon.support.reflect.CtExtendedModifier(modifier));
        modifiers.remove(new spoon.support.reflect.CtExtendedModifier(modifier, true));
        return this;
    }

    public spoon.support.reflect.CtModifierHandler setVisibility(spoon.reflect.declaration.ModifierKind visibility) {
        if (((visibility != (spoon.reflect.declaration.ModifierKind.PUBLIC)) && (visibility != (spoon.reflect.declaration.ModifierKind.PROTECTED))) && (visibility != (spoon.reflect.declaration.ModifierKind.PRIVATE))) {
            throw new spoon.SpoonException(("setVisibility could only be called with a private, public or protected argument value. Given argument: " + visibility));
        }
        if (hasModifier(visibility)) {
            return this;
        }
        if (isPublic()) {
            removeModifier(spoon.reflect.declaration.ModifierKind.PUBLIC);
        }
        if (isProtected()) {
            removeModifier(spoon.reflect.declaration.ModifierKind.PROTECTED);
        }
        if (isPrivate()) {
            removeModifier(spoon.reflect.declaration.ModifierKind.PRIVATE);
        }
        addModifier(visibility);
        return this;
    }

    public spoon.reflect.declaration.ModifierKind getVisibility() {
        if (isPublic()) {
            return spoon.reflect.declaration.ModifierKind.PUBLIC;
        }
        if (isProtected()) {
            return spoon.reflect.declaration.ModifierKind.PROTECTED;
        }
        if (isPrivate()) {
            return spoon.reflect.declaration.ModifierKind.PRIVATE;
        }
        return null;
    }

    public boolean isPublic() {
        return getModifiers().contains(spoon.reflect.declaration.ModifierKind.PUBLIC);
    }

    public boolean isProtected() {
        return getModifiers().contains(spoon.reflect.declaration.ModifierKind.PROTECTED);
    }

    public boolean isPrivate() {
        return getModifiers().contains(spoon.reflect.declaration.ModifierKind.PRIVATE);
    }

    public boolean isAbstract() {
        return getModifiers().contains(spoon.reflect.declaration.ModifierKind.ABSTRACT);
    }

    public boolean isStatic() {
        return getModifiers().contains(spoon.reflect.declaration.ModifierKind.STATIC);
    }

    public boolean isFinal() {
        return getModifiers().contains(spoon.reflect.declaration.ModifierKind.FINAL);
    }

    @java.lang.Override
    public int hashCode() {
        return getModifiers().hashCode();
    }

    @java.lang.Override
    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof spoon.support.reflect.CtModifierHandler)) {
            return false;
        }
        final spoon.support.reflect.CtModifierHandler other = ((spoon.support.reflect.CtModifierHandler) (obj));
        if ((getVisibility()) == null) {
            if ((other.getVisibility()) != null) {
                return false;
            }
        }else
            if ((other.getVisibility()) == null) {
                return false;
            }else
                if (!(getVisibility().equals(other.getVisibility()))) {
                    return false;
                }


        if ((getModifiers().size()) != (other.getModifiers().size())) {
            return false;
        }
        return getModifiers().containsAll(other.getModifiers());
    }
}

