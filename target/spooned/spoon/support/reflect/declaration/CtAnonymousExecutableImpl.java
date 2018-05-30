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


public class CtAnonymousExecutableImpl extends spoon.support.reflect.declaration.CtExecutableImpl<java.lang.Void> implements spoon.reflect.declaration.CtAnonymousExecutable {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.MODIFIER)
    private spoon.support.reflect.CtModifierHandler modifierHandler = new spoon.support.reflect.CtModifierHandler(this);

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtAnonymousExecutable(this);
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModifiable> T addModifier(spoon.reflect.declaration.ModifierKind modifier) {
        modifierHandler.addModifier(modifier);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModifiable> T removeModifier(spoon.reflect.declaration.ModifierKind modifier) {
        modifierHandler.removeModifier(modifier);
        return ((T) (this));
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.ModifierKind> getModifiers() {
        return modifierHandler.getModifiers();
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
    public boolean hasModifier(spoon.reflect.declaration.ModifierKind modifier) {
        return modifierHandler.hasModifier(modifier);
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModifiable> T setModifiers(java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers) {
        modifierHandler.setModifiers(modifiers);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModifiable> T setVisibility(spoon.reflect.declaration.ModifierKind visibility) {
        modifierHandler.setVisibility(visibility);
        return ((T) (this));
    }

    @java.lang.Override
    public java.util.Set<spoon.support.reflect.CtExtendedModifier> getExtendedModifiers() {
        return this.modifierHandler.getExtendedModifiers();
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModifiable> T setExtendedModifiers(java.util.Set<spoon.support.reflect.CtExtendedModifier> extendedModifiers) {
        this.modifierHandler.setExtendedModifiers(extendedModifiers);
        return ((T) (this));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.List<spoon.reflect.declaration.CtParameter<?>> getParameters() {
        return spoon.support.reflect.declaration.CtElementImpl.emptyList();
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public spoon.reflect.declaration.CtExecutable setParameters(java.util.List list) {
        // unsettable property
        return this;
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public spoon.reflect.declaration.CtExecutable addParameter(spoon.reflect.declaration.CtParameter parameter) {
        // unsettable property
        return this;
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public boolean removeParameter(spoon.reflect.declaration.CtParameter parameter) {
        return false;
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> getThrownTypes() {
        return spoon.support.reflect.declaration.CtElementImpl.emptySet();
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public spoon.reflect.declaration.CtExecutable setThrownTypes(java.util.Set thrownTypes) {
        // unsettable property
        return this;
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public spoon.reflect.declaration.CtExecutable addThrownType(spoon.reflect.reference.CtTypeReference throwType) {
        // unsettable property
        return this;
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public boolean removeThrownType(spoon.reflect.reference.CtTypeReference throwType) {
        // unsettable property
        return false;
    }

    @java.lang.Override
    public java.lang.String getSimpleName() {
        return "";
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <T extends spoon.reflect.declaration.CtNamedElement> T setSimpleName(java.lang.String simpleName) {
        // unsettable property
        return ((T) (this));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public spoon.reflect.reference.CtTypeReference<java.lang.Void> getType() {
        return factory.Type().VOID_PRIMITIVE;
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtTypedElement> C setType(spoon.reflect.reference.CtTypeReference<java.lang.Void> type) {
        // unsettable property
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtAnonymousExecutable clone() {
        return ((spoon.reflect.declaration.CtAnonymousExecutable) (super.clone()));
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

