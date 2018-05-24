package spoon.support.reflect.reference;


public class CtPackageReferenceImpl extends spoon.support.reflect.reference.CtReferenceImpl implements spoon.reflect.reference.CtPackageReference {
    private static final long serialVersionUID = 1L;

    public CtPackageReferenceImpl() {
        super();
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtPackage getDeclaration() {
        return getFactory().Package().get(getSimpleName());
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtPackageReference(this);
    }

    @java.lang.Override
    public java.lang.Package getActualPackage() {
        return java.lang.Package.getPackage(getSimpleName());
    }

    @java.lang.Override
    protected java.lang.reflect.AnnotatedElement getActualAnnotatedElement() {
        return getActualPackage();
    }

    @java.lang.Override
    public spoon.reflect.reference.CtPackageReference clone() {
        return ((spoon.reflect.reference.CtPackageReference) (super.clone()));
    }

    @java.lang.Override
    public java.lang.String getQualifiedName() {
        return this.getSimpleName();
    }

    @java.lang.Override
    public boolean isUnnamedPackage() {
        return getSimpleName().isEmpty();
    }
}

