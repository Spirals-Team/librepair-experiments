package spoon.support.reflect.reference;


public class CtFieldReferenceImpl<T> extends spoon.support.reflect.reference.CtVariableReferenceImpl<T> implements spoon.reflect.reference.CtFieldReference<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.DECLARING_TYPE)
    spoon.reflect.reference.CtTypeReference<?> declaringType;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.IS_FINAL)
    boolean fina = false;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.IS_STATIC)
    boolean stat = false;

    public CtFieldReferenceImpl() {
        super();
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtFieldReference(this);
    }

    @java.lang.Override
    public java.lang.reflect.Member getActualField() {
        try {
            if (getDeclaringType().getActualClass().isAnnotation()) {
                return getDeclaringType().getActualClass().getDeclaredMethod(getSimpleName());
            }
            return getDeclaringType().getActualClass().getDeclaredField(getSimpleName());
        } catch (java.lang.Exception e) {
            spoon.Launcher.LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    @java.lang.Override
    protected java.lang.reflect.AnnotatedElement getActualAnnotatedElement() {
        return ((java.lang.reflect.AnnotatedElement) (getActualField()));
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public spoon.reflect.declaration.CtField<T> getDeclaration() {
        return fromDeclaringType();
    }

    private spoon.reflect.declaration.CtField<T> fromDeclaringType() {
        if ((declaringType) == null) {
            return null;
        }
        spoon.reflect.declaration.CtType<?> type = declaringType.getDeclaration();
        if (type != null) {
            return ((spoon.reflect.declaration.CtField<T>) (type.getField(getSimpleName())));
        }
        return null;
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtField<T> getFieldDeclaration() {
        if ((declaringType) == null) {
            return null;
        }
        spoon.reflect.declaration.CtType<?> type = declaringType.getTypeDeclaration();
        if (type != null) {
            final spoon.reflect.declaration.CtField<T> ctField = ((spoon.reflect.declaration.CtField<T>) (type.getField(getSimpleName())));
            if ((ctField == null) && (type instanceof spoon.reflect.declaration.CtEnum)) {
                return ((spoon.reflect.declaration.CtEnum) (type)).getEnumValue(getSimpleName());
            }
            return ctField;
        }
        return null;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> getDeclaringType() {
        return declaringType;
    }

    @java.lang.Override
    public java.lang.String getQualifiedName() {
        return ((getDeclaringType().getQualifiedName()) + "#") + (getSimpleName());
    }

    @java.lang.Override
    public boolean isFinal() {
        return fina;
    }

    @java.lang.Override
    public boolean isStatic() {
        return stat;
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtFieldReference<T>> C setDeclaringType(spoon.reflect.reference.CtTypeReference<?> declaringType) {
        if (declaringType != null) {
            declaringType.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.DECLARING_TYPE, declaringType, this.declaringType);
        this.declaringType = declaringType;
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtFieldReference<T>> C setFinal(boolean fina) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.IS_FINAL, fina, this.fina);
        this.fina = fina;
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtFieldReference<T>> C setStatic(boolean stat) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.IS_STATIC, stat, this.stat);
        this.stat = stat;
        return ((C) (this));
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.ModifierKind> getModifiers() {
        spoon.reflect.declaration.CtVariable<?> v = getDeclaration();
        if (v != null) {
            return v.getModifiers();
        }
        java.lang.reflect.Member m = getActualField();
        if (m != null) {
            return spoon.support.util.RtHelper.getModifiers(m.getModifiers());
        }
        return java.util.Collections.emptySet();
    }

    @java.lang.Override
    public spoon.reflect.reference.CtFieldReference<T> clone() {
        return ((spoon.reflect.reference.CtFieldReference<T>) (super.clone()));
    }
}

