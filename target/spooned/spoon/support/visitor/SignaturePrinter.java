/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.support.visitor;


/**
 * Responsible for computing signatures for elements where a signature exists
 * (CtType, CtMethod and CtPackage). Otherwise returns the empty string.
 */
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

    /**
     * writes only the name and parameters' types
     */
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
                clearLast();// ","

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

    /**
     * For methods, this implementation of signature contains the return type, which corresponds
     * to what the Java compile and virtual machine call a "descriptor".
     *
     * See chapter "8.4.2 Method Signature" of the Java specification
     */
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

