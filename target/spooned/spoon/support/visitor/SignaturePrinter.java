package spoon.support.visitor;


public class SignaturePrinter extends spoon.reflect.visitor.CtScanner {
    private final java.lang.StringBuilder signature = new java.lang.StringBuilder();

    public SignaturePrinter() {
    }

    public java.lang.String getSignature() {
        return signature.toString();
    }

    @java.lang.Override
    public <T> void visitCtArrayTypeReference(spoon.reflect.reference.CtArrayTypeReference<T> reference) {
        scan(reference.getComponentType());
        write("[]");
    }

    @java.lang.Override
    public <T> void visitCtExecutableReference(spoon.reflect.reference.CtExecutableReference<T> reference) {
        writeNameAndParameters(reference);
    }

    public <T> void writeNameAndParameters(spoon.reflect.reference.CtExecutableReference<T> reference) {
        if (reference.isConstructor()) {
            write(reference.getDeclaringType().getQualifiedName());
        }else {
            write(reference.getSimpleName());
        }
        write("(");
        if ((reference.getParameters().size()) > 0) {
            for (spoon.reflect.reference.CtTypeReference<?> param : reference.getParameters()) {
                if ((param != null) && (!("null".equals(param.getSimpleName())))) {
                    scan(param);
                }else {
                    write(spoon.reflect.reference.CtExecutableReference.UNKNOWN_TYPE);
                }
                write(",");
            }
            if ((reference.getParameters().size()) > 0) {
                clearLast();
            }
        }
        write(")");
    }

    @java.lang.Override
    public <T> void visitCtTypeReference(spoon.reflect.reference.CtTypeReference<T> reference) {
        write(reference.getQualifiedName());
    }

    @java.lang.Override
    public void visitCtTypeParameterReference(spoon.reflect.reference.CtTypeParameterReference ref) {
        write(ref.getQualifiedName());
        if ((!(ref.isDefaultBoundingType())) || (!(ref.getBoundingType().isImplicit()))) {
            if (ref.isUpper()) {
                write(" extends ");
            }else {
                write(" super ");
            }
            scan(ref.getBoundingType());
        }
    }

    @java.lang.Override
    public <T> void visitCtIntersectionTypeReference(spoon.reflect.reference.CtIntersectionTypeReference<T> reference) {
        for (spoon.reflect.reference.CtTypeReference<?> bound : reference.getBounds()) {
            scan(bound);
            write(",");
        }
        clearLast();
    }

    @java.lang.Override
    public <T> void visitCtConstructor(spoon.reflect.declaration.CtConstructor<T> c) {
        if ((c.getDeclaringType()) != null) {
            write(c.getDeclaringType().getQualifiedName());
        }
        write("(");
        for (spoon.reflect.declaration.CtParameter<?> p : c.getParameters()) {
            scan(p.getType());
            write(",");
        }
        if ((c.getParameters().size()) > 0) {
            clearLast();
        }
        write(")");
    }

    @java.lang.Override
    public <T> void visitCtAnnotationMethod(spoon.reflect.declaration.CtAnnotationMethod<T> annotationMethod) {
        visitCtMethod(annotationMethod);
    }

    @java.lang.Override
    public <T> void visitCtMethod(spoon.reflect.declaration.CtMethod<T> m) {
        write(m.getSimpleName());
        write("(");
        for (spoon.reflect.declaration.CtParameter<?> p : m.getParameters()) {
            scan(p.getType());
            write(",");
        }
        if (!(m.getParameters().isEmpty())) {
            clearLast();
        }
        write(")");
    }

    private spoon.support.visitor.SignaturePrinter clearLast() {
        signature.deleteCharAt(((signature.length()) - 1));
        return this;
    }

    protected spoon.support.visitor.SignaturePrinter write(java.lang.String value) {
        signature.append(value);
        return this;
    }
}

