package spoon.support.reflect.declaration;


public class CtParameterImpl<T> extends spoon.support.reflect.declaration.CtNamedElementImpl implements spoon.reflect.declaration.CtParameter<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TYPE)
    spoon.reflect.reference.CtTypeReference<T> type;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.IS_VARARGS)
    boolean varArgs = false;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.MODIFIER)
    private spoon.support.reflect.CtModifierHandler modifierHandler = new spoon.support.reflect.CtModifierHandler(this);

    public CtParameterImpl() {
        super();
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor v) {
        v.visitCtParameter(this);
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public spoon.reflect.code.CtExpression<T> getDefaultExpression() {
        return null;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtParameterReference<T> getReference() {
        return getFactory().Executable().createParameterReference(this);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<T> getType() {
        return type;
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtVariable<T>> C setDefaultExpression(spoon.reflect.code.CtExpression<T> defaultExpression) {
        return ((C) (this));
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
    public boolean isVarArgs() {
        return varArgs;
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtParameter<T>> C setVarArgs(boolean varArgs) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.IS_VARARGS, varArgs, this.varArgs);
        this.varArgs = varArgs;
        return ((C) (this));
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
        modifierHandler.getVisibility();
        return null;
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtExecutable<?> getParent() {
        return ((spoon.reflect.declaration.CtExecutable<?>) (super.getParent()));
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
    public spoon.reflect.declaration.CtParameter<T> clone() {
        return ((spoon.reflect.declaration.CtParameter<T>) (super.clone()));
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

