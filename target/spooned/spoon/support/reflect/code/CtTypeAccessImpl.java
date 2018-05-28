package spoon.support.reflect.code;


public class CtTypeAccessImpl<A> extends spoon.support.reflect.code.CtExpressionImpl<java.lang.Void> implements spoon.reflect.code.CtTypeAccess<A> {
    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.ACCESSED_TYPE)
    private spoon.reflect.reference.CtTypeReference<A> type;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtTypeAccess(this);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<A> getAccessedType() {
        return type;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtTypeAccess<A>> C setAccessedType(spoon.reflect.reference.CtTypeReference<A> accessedType) {
        if (accessedType != null) {
            accessedType.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.ACCESSED_TYPE, accessedType, this.type);
        type = accessedType;
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<java.lang.Void> getType() {
        return ((spoon.reflect.reference.CtTypeReference<java.lang.Void>) (getFactory().Type().VOID_PRIMITIVE.clone().<spoon.reflect.code.CtTypeAccess>setParent(this)));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtTypedElement> C setType(spoon.reflect.reference.CtTypeReference<java.lang.Void> type) {
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtTypeAccess<A> clone() {
        return ((spoon.reflect.code.CtTypeAccess<A>) (super.clone()));
    }
}

