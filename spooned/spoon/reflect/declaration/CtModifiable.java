package spoon.reflect.declaration;


public interface CtModifiable extends spoon.reflect.declaration.CtElement {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.MODIFIER)
    java.util.Set<spoon.reflect.declaration.ModifierKind> getModifiers();

    boolean hasModifier(spoon.reflect.declaration.ModifierKind modifier);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MODIFIER)
    <T extends spoon.reflect.declaration.CtModifiable> T setModifiers(java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MODIFIER)
    <T extends spoon.reflect.declaration.CtModifiable> T addModifier(spoon.reflect.declaration.ModifierKind modifier);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MODIFIER)
    <T extends spoon.reflect.declaration.CtModifiable> T removeModifier(spoon.reflect.declaration.ModifierKind modifier);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MODIFIER)
    <T extends spoon.reflect.declaration.CtModifiable> T setVisibility(spoon.reflect.declaration.ModifierKind visibility);

    @spoon.support.DerivedProperty
    spoon.reflect.declaration.ModifierKind getVisibility();

    java.util.Set<spoon.support.reflect.CtExtendedModifier> getExtendedModifiers();

    <T extends spoon.reflect.declaration.CtModifiable> T setExtendedModifiers(java.util.Set<spoon.support.reflect.CtExtendedModifier> extendedModifiers);

    @spoon.support.DerivedProperty
    boolean isPublic();

    @spoon.support.DerivedProperty
    boolean isFinal();

    @spoon.support.DerivedProperty
    boolean isStatic();

    @spoon.support.DerivedProperty
    boolean isProtected();

    @spoon.support.DerivedProperty
    boolean isPrivate();

    @spoon.support.DerivedProperty
    boolean isAbstract();
}

