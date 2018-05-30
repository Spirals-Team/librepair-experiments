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
package spoon.reflect.visitor.filter;


/**
 * Matches all CtType elements, which are sub type of {@link #superType}
 * Matches the input `superType` too.
 * Call {@link #includingSelf(boolean)} with value false, if instance of {@link #superType} should no match this {@link Filter}
 */
public class SubtypeFilter extends spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.declaration.CtType<?>> {
    private spoon.reflect.reference.CtTypeReference<?> superType;

    private java.lang.String superTypeQualifiedName;

    public SubtypeFilter(spoon.reflect.reference.CtTypeReference<?> superType) {
        this.superType = superType;
    }

    /**
     *
     *
     * @param includingSelf
     * 		if false then element which is equal to to #superType is not matching
     */
    public spoon.reflect.visitor.filter.SubtypeFilter includingSelf(boolean includingSelf) {
        if (includingSelf) {
            superTypeQualifiedName = null;
        }else {
            superTypeQualifiedName = superType.getQualifiedName();
        }
        return this;
    }

    @java.lang.Override
    public boolean matches(spoon.reflect.declaration.CtType<?> mayBeSubType) {
        if (((superTypeQualifiedName) != null) && (superTypeQualifiedName.equals(mayBeSubType.getQualifiedName()))) {
            // we should not accept superType
            return false;
        }
        return mayBeSubType.isSubtypeOf(superType);
    }
}

