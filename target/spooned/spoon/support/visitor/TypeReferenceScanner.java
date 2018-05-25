package spoon.support.visitor;


public class TypeReferenceScanner extends spoon.reflect.visitor.CtScanner {
    java.util.Set<spoon.reflect.reference.CtTypeReference<?>> references;

    public TypeReferenceScanner() {
        references = new java.util.HashSet<>();
    }

    public TypeReferenceScanner(java.util.HashSet<spoon.reflect.reference.CtTypeReference<?>> references) {
        this.references = references;
    }

    public java.util.Set<spoon.reflect.reference.CtTypeReference<?>> getReferences() {
        return references;
    }

    private <T> boolean addReference(spoon.reflect.reference.CtTypeReference<T> ref) {
        return references.add(ref);
    }

    @java.lang.Override
    public <T> void visitCtFieldRead(spoon.reflect.code.CtFieldRead<T> fieldRead) {
        super.visitCtFieldRead(fieldRead);
        enter(fieldRead);
        scan(fieldRead.getVariable());
        scan(fieldRead.getAnnotations());
        scan(fieldRead.getTypeCasts());
        scan(fieldRead.getVariable());
        scan(fieldRead.getTarget());
        exit(fieldRead);
    }

    @java.lang.Override
    public <T> void visitCtFieldWrite(spoon.reflect.code.CtFieldWrite<T> fieldWrite) {
        enter(fieldWrite);
        scan(fieldWrite.getVariable());
        scan(fieldWrite.getAnnotations());
        scan(fieldWrite.getTypeCasts());
        scan(fieldWrite.getVariable());
        scan(fieldWrite.getTarget());
        exit(fieldWrite);
    }

    @java.lang.Override
    public <T> void visitCtFieldReference(spoon.reflect.reference.CtFieldReference<T> reference) {
        enter(reference);
        scan(reference.getDeclaringType());
        exit(reference);
    }

    @java.lang.Override
    public <T> void visitCtExecutableReference(spoon.reflect.reference.CtExecutableReference<T> reference) {
        enter(reference);
        scan(reference.getDeclaringType());
        scan(reference.getActualTypeArguments());
        exit(reference);
    }

    @java.lang.Override
    public <T> void visitCtTypeReference(spoon.reflect.reference.CtTypeReference<T> reference) {
        if (!(reference instanceof spoon.reflect.reference.CtArrayTypeReference)) {
            addReference(reference);
        }
        super.visitCtTypeReference(reference);
    }

    @java.lang.Override
    public <A extends java.lang.annotation.Annotation> void visitCtAnnotationType(spoon.reflect.declaration.CtAnnotationType<A> annotationType) {
        addReference(annotationType.getReference());
        super.visitCtAnnotationType(annotationType);
    }

    @java.lang.Override
    public <T extends java.lang.Enum<?>> void visitCtEnum(spoon.reflect.declaration.CtEnum<T> ctEnum) {
        addReference(ctEnum.getReference());
        super.visitCtEnum(ctEnum);
    }

    @java.lang.Override
    public <T> void visitCtInterface(spoon.reflect.declaration.CtInterface<T> intrface) {
        addReference(intrface.getReference());
        for (spoon.reflect.declaration.CtTypeMember typeMember : intrface.getTypeMembers()) {
            if (typeMember instanceof spoon.reflect.declaration.CtType) {
                addReference(((spoon.reflect.declaration.CtType) (typeMember)).getReference());
            }
        }
        super.visitCtInterface(intrface);
    }

    @java.lang.Override
    public <T> void visitCtClass(spoon.reflect.declaration.CtClass<T> ctClass) {
        addReference(ctClass.getReference());
        for (spoon.reflect.declaration.CtTypeMember typeMember : ctClass.getTypeMembers()) {
            if (typeMember instanceof spoon.reflect.declaration.CtType) {
                addReference(((spoon.reflect.declaration.CtType) (typeMember)).getReference());
            }
        }
        super.visitCtClass(ctClass);
    }
}

