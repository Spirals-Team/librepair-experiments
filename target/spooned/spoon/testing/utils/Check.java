package spoon.testing.utils;


public final class Check {
    private Check() {
        throw new java.lang.AssertionError();
    }

    public static <T> T assertNotNull(T reference) {
        if (reference == null) {
            throw new java.lang.AssertionError("Your parameter can't be null.");
        }
        return reference;
    }

    public static <T extends java.io.File> T assertExists(T file) {
        if (!(file.exists())) {
            throw new java.lang.AssertionError("You should specify an existing file.");
        }
        return file;
    }

    public static <A extends spoon.reflect.declaration.CtElement, E extends spoon.reflect.declaration.CtElement> A assertIsSame(A actual, E expected) {
        spoon.testing.utils.Check.assertNotNull(actual);
        spoon.testing.utils.Check.assertNotNull(expected);
        if (!(actual.getClass().equals(expected.getClass()))) {
            throw new java.lang.AssertionError(java.lang.String.format("Actual value is typed by %1$s and expected is typed by %2$s, these objects should be the same type.", actual.getClass().getName(), expected.getClass().getName()));
        }
        return actual;
    }
}

