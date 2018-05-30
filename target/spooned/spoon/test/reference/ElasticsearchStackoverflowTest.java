package spoon.test.reference;


public class ElasticsearchStackoverflowTest {
    private class Scanner extends spoon.reflect.visitor.CtScanner {
        @java.lang.Override
        public <T> void visitCtExecutableReference(spoon.reflect.reference.CtExecutableReference<T> reference) {
            super.visitCtExecutableReference(reference);
            reference.getDeclaration();
        }
    }

    @org.junit.Test
    public void testStackOverflow() {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/elasticsearch-stackoverflow");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.buildModel();
        spoon.reflect.CtModel model = launcher.getModel();
        spoon.test.reference.ElasticsearchStackoverflowTest.Scanner scanner = new spoon.test.reference.ElasticsearchStackoverflowTest.Scanner();
        scanner.scan(model.getRootPackage());
        java.util.List<spoon.reflect.reference.CtExecutableReference> executables = launcher.getModel().getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.reference.CtExecutableReference>(spoon.reflect.reference.CtExecutableReference.class));
        org.junit.Assert.assertFalse(executables.isEmpty());
        boolean result = false;
        for (spoon.reflect.reference.CtExecutableReference execRef : executables) {
            if (execRef.getSimpleName().equals("setParentTask")) {
                spoon.reflect.reference.CtTypeReference typeRef = execRef.getDeclaringType();
                org.junit.Assert.assertTrue((typeRef instanceof spoon.reflect.reference.CtTypeParameterReference));
                org.junit.Assert.assertEquals("ShardRequest", typeRef.getSimpleName());
                spoon.reflect.declaration.CtType typeRefDecl = typeRef.getDeclaration();
                org.junit.Assert.assertEquals("BroadcastShardRequest", typeRefDecl.getSuperclass().getSimpleName());
                org.junit.Assert.assertNull(execRef.getDeclaration());
                result = true;
            }
        }
        org.junit.Assert.assertTrue(result);
    }
}

