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
 * Responsible for computing CtElement.hashCode().
 * Version that is fast and compatible with EqualVisitor
 */
public class HashcodeVisitor extends spoon.reflect.visitor.CtInheritanceScanner {
    private int hashCode = 0;

    @java.lang.Override
    public void scanCtNamedElement(spoon.reflect.declaration.CtNamedElement e) {
        if ((e.getSimpleName()) != null) {
            hashCode += e.getSimpleName().hashCode();
        }
    }

    @java.lang.Override
    public void scanCtReference(spoon.reflect.reference.CtReference e) {
        hashCode += e.getSimpleName().hashCode();
    }

    @java.lang.Override
    public void visitCtImport(spoon.reflect.declaration.CtImport e) {
        if ((e.getReference()) != null) {
            scanCtReference(e.getReference());
        }
    }

    @java.lang.Override
    public void scan(spoon.reflect.declaration.CtElement element) {
        hashCode += 1;
        super.scan(element);
    }

    public int getHasCode() {
        return hashCode;
    }
}

