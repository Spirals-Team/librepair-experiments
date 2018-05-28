package spoon.test.trycatch.testclasses;


public class Bar {
    public spoon.test.trycatch.testclasses.Statement foobar(spoon.test.trycatch.testclasses.Statement base) {
        try {
            java.io.File f = new java.io.File("/tmp/foobar");
            throw new java.lang.Exception("machin");
        } catch (final java.lang.Exception e) {
            return new spoon.test.trycatch.testclasses.Statement() {
                @java.lang.Override
                public void evaluate() throws java.lang.Throwable {
                    throw new java.lang.RuntimeException("Invalid parameters for Timeout", e);
                }
            };
        }
    }
}

