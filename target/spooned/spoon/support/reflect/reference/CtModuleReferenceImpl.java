package spoon.support.reflect.reference;


public class CtModuleReferenceImpl extends spoon.support.reflect.reference.CtReferenceImpl implements spoon.reflect.reference.CtModuleReference {
    public CtModuleReferenceImpl() {
        super();
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtModule getDeclaration() {
        return this.getFactory().Module().getOrCreate(this.getSimpleName());
    }

    @java.lang.Override
    protected java.lang.reflect.AnnotatedElement getActualAnnotatedElement() {
        return null;
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtModuleReference(this);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtModuleReference clone() {
        return ((spoon.reflect.reference.CtModuleReference) (super.clone()));
    }
}

