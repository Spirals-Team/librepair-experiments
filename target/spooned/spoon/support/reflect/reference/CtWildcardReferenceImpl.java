package spoon.support.reflect.reference;


public class CtWildcardReferenceImpl extends spoon.support.reflect.reference.CtTypeParameterReferenceImpl implements spoon.reflect.reference.CtWildcardReference {
    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtWildcardReference(this);
    }

    public CtWildcardReferenceImpl() {
        simplename = "?";
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <T extends spoon.reflect.reference.CtReference> T setSimpleName(java.lang.String simplename) {
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.reference.CtWildcardReference clone() {
        return ((spoon.reflect.reference.CtWildcardReference) (super.clone()));
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtType<java.lang.Object> getTypeDeclaration() {
        return getFactory().Type().get(java.lang.Object.class);
    }
}

