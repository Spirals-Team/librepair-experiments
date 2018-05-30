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


public abstract class AbstractCtElementAssert<T extends spoon.testing.AbstractCtElementAssert<T>> extends spoon.testing.AbstractAssert<T, spoon.reflect.declaration.CtElement> {
    protected AbstractCtElementAssert(spoon.reflect.declaration.CtElement actual, java.lang.Class<?> selfType) {
        super(actual, selfType);
    }

    /**
     * Verifies that the actual value is equal to the given one.
     *
     * @param expected
     * 		The expected element.
     * @return {@code this} assertion object.
     */
    public T isEqualTo(spoon.reflect.declaration.CtElement expected) {
        spoon.testing.utils.Check.assertNotNull(expected);
        spoon.testing.utils.Check.assertIsSame(actual, expected);
        spoon.testing.utils.ProcessorUtils.process(actual.getFactory(), processors);
        if (!(actual.equals(expected))) {
            throw new java.lang.AssertionError();
        }
        return this.myself;
    }

    /**
     * Verifies that the actual value is equal to the given one.
     *
     * @param expected
     * 		The expected render of the element.
     * @return {@code this} assertion object.
     */
    public T isEqualTo(java.lang.String expected) {
        spoon.testing.utils.Check.assertNotNull(expected);
        spoon.testing.utils.ProcessorUtils.process(actual.getFactory(), processors);
        if (!(actual.toString().equals(expected))) {
            throw new java.lang.AssertionError(java.lang.String.format("%1$s and %2$s aren't equals.", actual.getShortRepresentation(), expected));
        }
        return this.myself;
    }
}

