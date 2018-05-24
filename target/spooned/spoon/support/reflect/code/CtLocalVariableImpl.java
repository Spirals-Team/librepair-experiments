package spoon.support.reflect.code;


public class CtLocalVariableImpl<T> extends spoon.support.reflect.code.CtStatementImpl implements spoon.reflect.code.CtLocalVariable<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.DEFAULT_EXPRESSION)
    spoon.reflect.code.CtExpression<T> defaultExpression;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.NAME)
    java.lang.String name = "";

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TYPE)
    spoon.reflect.reference.CtTypeReference<T> type;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.MODIFIER)
    private spoon.support.reflect.CtModifierHandler modifierHandler = new spoon.support.reflect.CtModifierHandler(this);

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtLocalVariable(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<T> getDefaultExpression() {
        return defaultExpression;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtLocalVariableReference<T> getReference() {
        return getFactory().Code().createLocalVariableReference(this);
    }

    @java.lang.Override
    public java.lang.String getSimpleName() {
        return name;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<T> getType() {
        return type;
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtVariable<T>> C setDefaultExpression(spoon.reflect.code.CtExpression<T> defaultExpression) {
        if (defaultExpression != null) {
            defaultExpression.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.DEFAULT_EXPRESSION, defaultExpression, this.defaultExpression);
        this.defaultExpression = defaultExpression;
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtNamedElement> C setSimpleName(java.lang.String simpleName) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.NAME, simpleName, this.name);
        this.name = simpleName;
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
    public <T extends spoon.reflect.declaration.CtModifiable> T setExtendedModifiers(java.util.Set<spoon.support.reflect.CtExtendedModifier> extendedModifiers) {
        this.modifierHandler.setExtendedModifiers(extendedModifiers);
        return ((T) (this));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public spoon.reflect.code.CtExpression<T> getAssignment() {
        return getDefaultExpression();
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.code.CtRHSReceiver<T>> C setAssignment(spoon.reflect.code.CtExpression<T> assignment) {
        setDefaultExpression(assignment);
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtLocalVariable<T> clone() {
        return ((spoon.reflect.code.CtLocalVariable<T>) (super.clone()));
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

