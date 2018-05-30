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


/**
 * Entry point for assertion methods for different data types.
 * Each method in this class is a static factory for the type-specific
 * assertion objects. The purpose of this class is to make test code
 * more readable.
 */
public class Assert {
    private Assert() {
    }

    /**
     * Create a new instance of <code>{@link FileAssert}</code>.
     *
     * @param actual
     * 		The actual value.
     * @return the created assertion object.
     */
    public static spoon.testing.AbstractFileAssert<?> assertThat(java.lang.String actual) {
        return spoon.testing.Assert.assertThat(new java.io.File(actual));
    }

    /**
     * Create a new instance of <code>{@link FileAssert}</code>.
     *
     * @param actual
     * 		The actual value.
     * @return the created assertion object.
     */
    public static spoon.testing.AbstractFileAssert<?> assertThat(java.io.File actual) {
        spoon.testing.utils.Check.assertNotNull(actual);
        spoon.testing.utils.Check.assertExists(actual);
        return new spoon.testing.FileAssert(actual);
    }

    /**
     * Create a new instance of <code>{@link CtElementAssert}</code>.
     * Note that a package typed by CtElement will call this method and
     * not {@link Assert#assertThat(CtPackage)}.
     *
     * @param actual
     * 		The actual value.
     * @return the created assertion object.
     */
    public static spoon.testing.AbstractCtElementAssert<?> assertThat(spoon.reflect.declaration.CtElement actual) {
        spoon.testing.utils.Check.assertNotNull(actual);
        return new spoon.testing.CtElementAssert(actual);
    }

    /**
     * Create a new instance of <code>{@link CtPackageAssert}</code>.
     * Note that this assert will be make a deep equals with its content
     * (all types).
     *
     * @param actual
     * 		The actual value.
     * @return the created assertion object.
     */
    public static spoon.testing.AbstractCtPackageAssert<?> assertThat(spoon.reflect.declaration.CtPackage actual) {
        spoon.testing.utils.Check.assertNotNull(actual);
        return new spoon.testing.CtPackageAssert(actual);
    }
}

