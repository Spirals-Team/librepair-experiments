package spoon.testing;


public class Assert {
    private Assert() {
    }

    public static spoon.testing.AbstractFileAssert<?> assertThat(java.lang.String actual) {
        return spoon.testing.Assert.assertThat(new java.io.File(actual));
    }

    public static spoon.testing.AbstractFileAssert<?> assertThat(java.io.File actual) {
        spoon.testing.utils.Check.assertNotNull(actual);
        spoon.testing.utils.Check.assertExists(actual);
        return new spoon.testing.FileAssert(actual);
    }

    public static spoon.testing.AbstractCtElementAssert<?> assertThat(spoon.reflect.declaration.CtElement actual) {
        spoon.testing.utils.Check.assertNotNull(actual);
        return new spoon.testing.CtElementAssert(actual);
    }

    public static spoon.testing.AbstractCtPackageAssert<?> assertThat(spoon.reflect.declaration.CtPackage actual) {
        spoon.testing.utils.Check.assertNotNull(actual);
        return new spoon.testing.CtPackageAssert(actual);
    }
}

