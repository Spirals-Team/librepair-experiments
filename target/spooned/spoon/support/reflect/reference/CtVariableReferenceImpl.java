package spoon.support.reflect.reference;


public abstract class CtVariableReferenceImpl<T> extends spoon.support.reflect.reference.CtReferenceImpl implements spoon.reflect.reference.CtVariableReference<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TYPE)
    spoon.reflect.reference.CtTypeReference<T> type;

    public CtVariableReferenceImpl() {
        super();
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<T> getType() {
        return type;
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtVariableReference<T>> C setType(spoon.reflect.reference.CtTypeReference<T> type) {
        if (type != null) {
            type.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.TYPE, type, this.type);
        this.type = type;
        return ((C) (this));
    }

    @java.lang.Override
    protected java.lang.reflect.AnnotatedElement getActualAnnotatedElement() {
        return null;
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtVariable<T> getDeclaration() {
        return null;
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.ModifierKind> getModifiers() {
        spoon.reflect.declaration.CtVariable<T> v = getDeclaration();
        if (v != null) {
            return v.getModifiers();
        }
        return java.util.Collections.emptySet();
    }

    @java.lang.Override
    public spoon.reflect.reference.CtVariableReference<T> clone() {
        return ((spoon.reflect.reference.CtVariableReference<T>) (super.clone()));
    }
}

