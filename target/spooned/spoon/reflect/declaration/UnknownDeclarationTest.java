package spoon.reflect.declaration;


public class UnknownDeclarationTest {
    private static class ExecutableReferenceVisitor extends spoon.reflect.visitor.CtScanner {
        int referenceCounter = 0;

        @java.lang.Override
        public <T> void visitCtExecutableReference(final spoon.reflect.reference.CtExecutableReference<T> reference) {
            final spoon.reflect.declaration.CtExecutable executable = reference.getDeclaration();
            org.junit.Assert.assertNull(executable);
            (referenceCounter)++;
        }
    }

    @org.junit.Test
    public void testUnknownCalls() {
        final spoon.Launcher runLaunch = new spoon.Launcher();
        runLaunch.getEnvironment().setNoClasspath(true);
        runLaunch.addInputResource("./src/test/resources/noclasspath/UnknownCalls.java");
        runLaunch.buildModel();
        final spoon.reflect.declaration.CtPackage rootPackage = runLaunch.getFactory().Package().getRootPackage();
        final spoon.reflect.declaration.UnknownDeclarationTest.ExecutableReferenceVisitor visitor = new spoon.reflect.declaration.UnknownDeclarationTest.ExecutableReferenceVisitor();
        visitor.scan(rootPackage);
        org.junit.Assert.assertEquals(3, visitor.referenceCounter);
    }
}

