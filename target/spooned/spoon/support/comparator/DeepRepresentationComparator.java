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
 * Compares based on a toString representation.
 */
public class DeepRepresentationComparator implements java.io.Serializable , java.util.Comparator<spoon.reflect.declaration.CtElement> {
    @java.lang.Override
    public int compare(spoon.reflect.declaration.CtElement o1, spoon.reflect.declaration.CtElement o2) {
        if ((o1.getPosition().isValidPosition()) == false) {
            return 1;
        }
        if ((o2.getPosition().isValidPosition()) == false) {
            return -1;
        }
        java.lang.String current = getDeepRepresentation(o1);
        java.lang.String other = getDeepRepresentation(o2);
        if (((current.length()) <= 0) || ((other.length()) <= 0)) {
            throw new java.lang.ClassCastException("Unable to compare elements");
        }
        return current.compareTo(other);
    }

    private java.lang.String getDeepRepresentation(spoon.reflect.declaration.CtElement elem) {
        return elem.toString();
    }
}

