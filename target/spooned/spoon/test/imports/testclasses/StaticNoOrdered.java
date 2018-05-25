package spoon.test.imports.testclasses;


public class StaticNoOrdered {
    public void testMachin() {
        org.junit.Assert.assertEquals("bla", "truc");
        org.junit.Test test = new org.junit.Test() {
            @java.lang.Override
            public java.lang.Class<? extends java.lang.annotation.Annotation> annotationType() {
                return null;
            }

            @java.lang.Override
            public java.lang.Class<? extends java.lang.Throwable> expected() {
                return null;
            }

            @java.lang.Override
            public long timeout() {
                return 0;
            }
        };
    }

    public void anotherStaticImoport() {
        java.nio.charset.Charset charset = java.nio.charset.Charset.forName("utf-8");
    }
}

