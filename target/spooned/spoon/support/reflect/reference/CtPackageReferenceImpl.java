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

