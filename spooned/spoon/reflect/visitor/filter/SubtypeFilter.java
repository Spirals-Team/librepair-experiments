package spoon.reflect.visitor.filter;


public class SubtypeFilter extends spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.declaration.CtType<?>> {
    private spoon.reflect.reference.CtTypeReference<?> superType;

    private java.lang.String superTypeQualifiedName;

    public SubtypeFilter(spoon.reflect.reference.CtTypeReference<?> superType) {
        this.superType = superType;
    }

    public spoon.reflect.visitor.filter.SubtypeFilter includingSelf(boolean includingSelf) {
        if (includingSelf) {
            superTypeQualifiedName = null;
        }else {
            superTypeQualifiedName = superType.getQualifiedName();
        }
        return this;
    }

    @java.lang.Override
    public boolean matches(spoon.reflect.declaration.CtType<?> mayBeSubType) {
        if (((superTypeQualifiedName) != null) && (superTypeQualifiedName.equals(mayBeSubType.getQualifiedName()))) {
            return false;
        }
        return mayBeSubType.isSubtypeOf(superType);
    }
}

