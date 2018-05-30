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
package spoon.support.comparator;


/**
 * Compares executables (method, executable-references) based on a signature.
 */
public class SignatureComparator implements java.io.Serializable , java.util.Comparator<spoon.reflect.declaration.CtElement> {
    private static final long serialVersionUID = 1L;

    @java.lang.Override
    public int compare(spoon.reflect.declaration.CtElement o1, spoon.reflect.declaration.CtElement o2) {
        spoon.support.visitor.SignaturePrinter signaturePrinter1 = new spoon.support.visitor.SignaturePrinter();
        spoon.support.visitor.SignaturePrinter signaturePrinter2 = new spoon.support.visitor.SignaturePrinter();
        signaturePrinter1.scan(o1);
        signaturePrinter2.scan(o2);
        return signaturePrinter1.getSignature().compareTo(signaturePrinter2.getSignature());
    }
}

