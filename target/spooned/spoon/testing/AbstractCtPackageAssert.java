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
package spoon.testing;


public abstract class AbstractCtPackageAssert<T extends spoon.testing.AbstractCtPackageAssert<T>> extends spoon.testing.AbstractAssert<T, spoon.reflect.declaration.CtPackage> {
    protected AbstractCtPackageAssert(spoon.reflect.declaration.CtPackage actual, java.lang.Class<?> selfType) {
        super(actual, selfType);
    }

    /**
     * Verifies that the actual value is equal to the given one.
     *
     * @param expected
     * 		The expected package.
     * @return {@code this} assertion object.
     */
    public T isEqualTo(spoon.reflect.declaration.CtPackage expected) {
        spoon.testing.utils.Check.assertNotNull(expected);
        if (!(actual.getSimpleName().equals(expected.getSimpleName()))) {
            throw new java.lang.AssertionError(java.lang.String.format("The actual package named %1$s isn't equals to the expected package named %2$s", actual.getSimpleName(), expected.getSimpleName()));
        }
        if (((processors) != null) && (!(processors.isEmpty()))) {
            spoon.testing.utils.ProcessorUtils.process(actual.getFactory(), processors);
        }
        class TypeComparator implements java.util.Comparator<spoon.reflect.declaration.CtType<?>> {
            @java.lang.Override
            public int compare(spoon.reflect.declaration.CtType<?> o1, spoon.reflect.declaration.CtType<?> o2) {
                return o1.getSimpleName().compareTo(o2.getSimpleName());
            }
        }
        final java.util.List<spoon.reflect.declaration.CtType<?>> actualTypes = new java.util.ArrayList<>(actual.getTypes());
        java.util.Collections.sort(actualTypes, new TypeComparator());
        final java.util.List<spoon.reflect.declaration.CtType<?>> expectedTypes = new java.util.ArrayList<>(expected.getTypes());
        java.util.Collections.sort(expectedTypes, new TypeComparator());
        for (int i = 0; i < (actual.getTypes().size()); i++) {
            final spoon.reflect.declaration.CtType<?> actualType = actualTypes.get(i);
            final spoon.reflect.declaration.CtType<?> expectedType = expectedTypes.get(i);
            if (!(actualType.toString().equals(expectedType.toString()))) {
                throw new java.lang.AssertionError(java.lang.String.format("%1$s and %2$s aren't equals.", actualType.getShortRepresentation(), expectedType.getShortRepresentation()));
            }
        }
        class PackageComparator implements java.util.Comparator<spoon.reflect.declaration.CtPackage> {
            @java.lang.Override
            public int compare(spoon.reflect.declaration.CtPackage o1, spoon.reflect.declaration.CtPackage o2) {
                return o1.getSimpleName().compareTo(o2.getSimpleName());
            }
        }
        final java.util.List<spoon.reflect.declaration.CtPackage> actualPackages = new java.util.ArrayList<>(actual.getPackages());
        java.util.Collections.sort(actualPackages, new PackageComparator());
        final java.util.List<spoon.reflect.declaration.CtPackage> expectedPackages = new java.util.ArrayList<>(expected.getPackages());
        java.util.Collections.sort(expectedPackages, new PackageComparator());
        for (int i = 0; i < (actualPackages.size()); i++) {
            final spoon.reflect.declaration.CtPackage actualPackage = actualPackages.get(i);
            final spoon.reflect.declaration.CtPackage expectedPackage = expectedPackages.get(i);
            spoon.testing.Assert.assertThat(actualPackage).isEqualTo(expectedPackage);
        }
        return this.myself;
    }
}

