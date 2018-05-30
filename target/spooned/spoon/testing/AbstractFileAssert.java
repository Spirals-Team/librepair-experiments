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


public abstract class AbstractFileAssert<T extends spoon.testing.AbstractFileAssert<T>> extends spoon.testing.AbstractAssert<T, java.io.File> {
    public AbstractFileAssert(java.io.File actual, java.lang.Class<?> selfType) {
        super(actual, selfType);
    }

    /**
     * Verifies that the actual value is equal to the given one.
     *
     * @param expected
     * 		The expected location of source code.
     * @return {@code this} assertion object.
     */
    public T isEqualTo(java.lang.String expected) {
        return isEqualTo(new java.io.File(expected));
    }

    /**
     * Verifies that the actual value is equal to the given one.
     *
     * @param expected
     * 		The expected location of source code.
     * @return {@code this} assertion object.
     */
    public T isEqualTo(java.io.File expected) {
        spoon.testing.utils.Check.assertNotNull(expected);
        spoon.testing.utils.Check.assertExists(expected);
        final spoon.reflect.factory.Factory actualFactory = spoon.testing.utils.ModelUtils.build(actual);
        final spoon.reflect.factory.Factory expectedFactory = spoon.testing.utils.ModelUtils.build(expected);
        spoon.testing.utils.ProcessorUtils.process(actualFactory, processors);
        final java.util.List<spoon.reflect.declaration.CtType<?>> allActual = actualFactory.Type().getAll();
        final java.util.List<spoon.reflect.declaration.CtType<?>> allExpected = expectedFactory.Type().getAll();
        for (int i = 0; i < (allActual.size()); i++) {
            final spoon.reflect.declaration.CtType<?> currentActual = allActual.get(i);
            final spoon.reflect.declaration.CtType<?> currentExpected = allExpected.get(i);
            if (!(currentActual.equals(currentExpected))) {
                throw new java.lang.AssertionError(java.lang.String.format("%1$s and %2$s aren't equals.", currentActual.getQualifiedName(), currentExpected.getQualifiedName()));
            }
        }
        return this.myself;
    }
}

