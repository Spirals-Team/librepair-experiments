package spoon.test.visitor;


public class VisitorTest {
    static class MyVisitor extends spoon.reflect.visitor.CtScanner {
        private int expected;

        private int actual;

        public boolean equals = false;

        public MyVisitor(int expected) {
            this.expected = expected;
        }

        @java.lang.Override
        public <T> void visitCtMethod(spoon.reflect.declaration.CtMethod<T> m) {
            actual = 0;
            super.visitCtMethod(m);
            equals = (expected) == (actual);
        }

        @java.lang.Override
        public void visitCtIf(spoon.reflect.code.CtIf ifElement) {
            (actual)++;
            super.visitCtIf(ifElement);
        }
    }

    @org.junit.Test
    public void testRecursiveDescent() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/spoon/test/visitor/Foo.java");
        launcher.buildModel();
        final spoon.test.visitor.VisitorTest.MyVisitor visitor = new spoon.test.visitor.VisitorTest.MyVisitor(2);
        visitor.scan(launcher.getFactory().Package().getRootPackage());
        org.junit.Assert.assertTrue(visitor.equals);
    }
}

