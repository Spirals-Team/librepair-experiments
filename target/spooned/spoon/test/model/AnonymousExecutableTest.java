package spoon.test.model;


public class AnonymousExecutableTest {
    @org.junit.Test
    public void testStatements() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.model", "AnonymousExecutableClass");
        spoon.reflect.declaration.CtAnonymousExecutable anonexec = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtAnonymousExecutable>(spoon.reflect.declaration.CtAnonymousExecutable.class)).get(0);
        java.util.List<spoon.reflect.code.CtStatement> stats = anonexec.getBody().getStatements();
        org.junit.Assert.assertEquals(1, stats.size());
    }
}

