package spoon.test.ctCase;


@java.lang.SuppressWarnings({ "unchecked", "rawtypes" })
public class SwitchCaseTest {
    @org.junit.Test
    public void insertAfterStatementInSwitchCaseWithoutException() throws java.lang.Exception {
        java.lang.String packageName = "spoon.test.ctCase";
        java.lang.String className = "ClassWithSwitchExample";
        spoon.reflect.factory.Factory factory = factoryFor(packageName, className);
        java.util.List<spoon.reflect.code.CtCase> elements = elementsOfType(spoon.reflect.code.CtCase.class, factory);
        org.junit.Assert.assertEquals(3, elements.size());
        spoon.reflect.code.CtCase firstCase = elements.get(0);
        java.util.List<spoon.reflect.code.CtStatement> statements = firstCase.getStatements();
        org.junit.Assert.assertEquals(2, statements.size());
        spoon.reflect.code.CtStatement newStatement = factory.Code().createCodeSnippetStatement("result = 0");
        statements.get(0).insertAfter(newStatement);
        statements = firstCase.getStatements();
        org.junit.Assert.assertEquals(3, statements.size());
        org.junit.Assert.assertTrue(((statements.get(1)) == newStatement));
    }

    @org.junit.Test
    public void insertBeforeStatementInSwitchCaseWithoutException() throws java.lang.Exception {
        java.lang.String packageName = "spoon.test.ctCase";
        java.lang.String className = "ClassWithSwitchExample";
        spoon.reflect.factory.Factory factory = factoryFor(packageName, className);
        java.util.List<spoon.reflect.code.CtCase> elements = elementsOfType(spoon.reflect.code.CtCase.class, factory);
        org.junit.Assert.assertEquals(3, elements.size());
        spoon.reflect.code.CtCase firstCase = elements.get(0);
        java.util.List<spoon.reflect.code.CtStatement> statements = firstCase.getStatements();
        org.junit.Assert.assertEquals(2, statements.size());
        spoon.reflect.code.CtStatement newStatement = factory.Code().createCodeSnippetStatement("result = 0");
        statements.get(0).insertBefore(newStatement);
        statements = firstCase.getStatements();
        org.junit.Assert.assertEquals(3, statements.size());
        org.junit.Assert.assertTrue(((statements.get(0)) == newStatement));
    }

    private <T extends spoon.reflect.declaration.CtElement> java.util.List<T> elementsOfType(java.lang.Class<T> type, spoon.reflect.factory.Factory factory) {
        return ((java.util.List) (spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.TypeFilter<>(type))));
    }

    private spoon.reflect.factory.Factory factoryFor(java.lang.String packageName, java.lang.String className) throws java.lang.Exception {
        return spoon.testing.utils.ModelUtils.build(packageName, className).getFactory();
    }
}

