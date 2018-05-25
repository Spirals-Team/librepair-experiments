package spoon.test.filters;


public class CUFilterTest {
    @org.junit.Test
    public void testWithoutFilters() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/same-package");
        launcher.buildModel();
        final spoon.reflect.CtModel model = launcher.getModel();
        org.junit.Assert.assertEquals(2, model.getAllTypes().size());
        org.junit.Assert.assertEquals("spoon.test.same.B", model.getAllTypes().iterator().next().getMethod("createB").getType().getQualifiedName());
    }

    @org.junit.Test
    public void testSingleExcludeWithFilter() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/test/resources/noclasspath/same-package");
        launcher.getModelBuilder().addCompilationUnitFilter(new spoon.support.compiler.jdt.CompilationUnitFilter() {
            @java.lang.Override
            public boolean exclude(final java.lang.String path) {
                return path.endsWith("B.java");
            }
        });
        launcher.buildModel();
        final spoon.reflect.CtModel model = launcher.getModel();
        org.junit.Assert.assertEquals(1, model.getAllTypes().size());
        org.junit.Assert.assertEquals("A", model.getAllTypes().iterator().next().getSimpleName());
        final spoon.reflect.code.CtReturn ctReturn = model.getAllTypes().iterator().next().getMethod("createB").getBody().getStatement(0);
        final spoon.reflect.code.CtConstructorCall ctConstructorCall = ((spoon.reflect.code.CtConstructorCall) (ctReturn.getReturnedExpression()));
        org.junit.Assert.assertEquals("spoon.test.same.B", ctConstructorCall.getType().getQualifiedName());
    }

    @org.junit.Test
    public void testSingleExcludeWithoutFilter() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/test/resources/noclasspath/same-package/A.java");
        launcher.buildModel();
        final spoon.reflect.CtModel model = launcher.getModel();
        org.junit.Assert.assertEquals(1, model.getAllTypes().size());
        org.junit.Assert.assertEquals("A", model.getAllTypes().iterator().next().getSimpleName());
        final spoon.reflect.code.CtReturn ctReturn = model.getAllTypes().iterator().next().getMethod("createB").getBody().getStatement(0);
        final spoon.reflect.code.CtConstructorCall ctConstructorCall = ((spoon.reflect.code.CtConstructorCall) (ctReturn.getReturnedExpression()));
        org.junit.Assert.assertEquals("spoon.test.same.B", ctConstructorCall.getType().getQualifiedName());
    }
}

