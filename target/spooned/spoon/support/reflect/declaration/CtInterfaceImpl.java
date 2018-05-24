package spoon.support.reflect.declaration;


public class CtInterfaceImpl<T> extends spoon.support.reflect.declaration.CtTypeImpl<T> implements spoon.reflect.declaration.CtInterface<T> {
    private static final long serialVersionUID = 1L;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtInterface(this);
    }

    @java.lang.Override
    public boolean isSubtypeOf(spoon.reflect.reference.CtTypeReference<?> type) {
        return getReference().isSubtypeOf(type);
    }

    @java.lang.Override
    public boolean isInterface() {
        return true;
    }

    @java.lang.Override
    public java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> getDeclaredExecutables() {
        java.util.Set<spoon.reflect.reference.CtTypeReference<?>> superInterfaces = getSuperInterfaces();
        if (superInterfaces.isEmpty()) {
            return super.getDeclaredExecutables();
        }
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> l = new java.util.ArrayList<>(super.getDeclaredExecutables());
        for (spoon.reflect.reference.CtTypeReference<?> sup : superInterfaces) {
            l.addAll(sup.getAllExecutables());
        }
        return java.util.Collections.unmodifiableList(l);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtInterface<T> clone() {
        return ((spoon.reflect.declaration.CtInterface<T>) (super.clone()));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtType<T>> C setSuperclass(spoon.reflect.reference.CtTypeReference<?> superClass) {
        return ((C) (this));
    }
}

