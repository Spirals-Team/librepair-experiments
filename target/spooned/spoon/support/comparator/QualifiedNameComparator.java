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
 * compares based on names (with a preference for qualified names if available)
 */
public class QualifiedNameComparator implements java.io.Serializable , java.util.Comparator<spoon.reflect.declaration.CtElement> {
    private static final long serialVersionUID = 1L;

    public static final spoon.support.comparator.QualifiedNameComparator INSTANCE = new spoon.support.comparator.QualifiedNameComparator();

    @java.lang.Override
    public int compare(spoon.reflect.declaration.CtElement o1, spoon.reflect.declaration.CtElement o2) {
        try {
            // qualified names if available
            // note: there is no common interface between
            // CtPackage.getQualifiedName and CtTypeInformation.getQualifiedName
            if ((o1 instanceof spoon.reflect.declaration.CtTypeInformation) && (o2 instanceof spoon.reflect.declaration.CtTypeInformation)) {
                return ((spoon.reflect.declaration.CtTypeInformation) (o1)).getQualifiedName().compareTo(((spoon.reflect.declaration.CtTypeInformation) (o2)).getQualifiedName());
            }
            if ((o1 instanceof spoon.reflect.declaration.CtPackage) && (o2 instanceof spoon.reflect.declaration.CtPackage)) {
                return ((spoon.reflect.declaration.CtPackage) (o1)).getQualifiedName().compareTo(((spoon.reflect.declaration.CtPackage) (o2)).getQualifiedName());
            }
            // otherwise names
            // note: there is no common interface between
            // CtReference.getSimpleName and CtTNamedElement.getSimpleName
            if ((o1 instanceof spoon.reflect.reference.CtReference) && (o2 instanceof spoon.reflect.reference.CtReference)) {
                return ((spoon.reflect.reference.CtReference) (o1)).getSimpleName().compareTo(((spoon.reflect.reference.CtReference) (o2)).getSimpleName());
            }
            if ((o1 instanceof spoon.reflect.declaration.CtNamedElement) && (o2 instanceof spoon.reflect.declaration.CtNamedElement)) {
                return ((spoon.reflect.declaration.CtNamedElement) (o1)).getSimpleName().compareTo(((spoon.reflect.declaration.CtNamedElement) (o2)).getSimpleName());
            }
            throw new java.lang.IllegalArgumentException();
        } catch (java.lang.NullPointerException e) {
            // when o1 or o2 is null, or no name are available
            return -1;
        }
    }
}

