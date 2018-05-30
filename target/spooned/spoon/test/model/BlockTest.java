package spoon.test.model;


public class BlockTest {
    @org.junit.Test
    public void testIterationStatements() {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("" + ((("class X {" + "public void foo() {") + " int x=0;int y=0;") + "}};"))).compile();
        spoon.reflect.declaration.CtMethod<?> foo = ((spoon.reflect.declaration.CtMethod<?>) (clazz.getMethods().toArray()[0]));
        spoon.reflect.code.CtBlock<?> body = foo.getBody();
        org.junit.Assert.assertEquals(2, body.getStatements().size());
        java.util.List<spoon.reflect.code.CtStatement> l = new java.util.ArrayList<spoon.reflect.code.CtStatement>();
        for (spoon.reflect.code.CtStatement s : body) {
            l.add(s);
        }
        org.junit.Assert.assertTrue(body.getStatements().equals(l));
    }

    @org.junit.Test
    public void testAddEmptyBlock() {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("" + ((("class X {" + "public void foo() {") + " ") + "}};"))).compile();
        spoon.reflect.declaration.CtMethod<?> foo = ((spoon.reflect.declaration.CtMethod<?>) (clazz.getMethods().toArray()[0]));
        spoon.reflect.code.CtBlock<?> body = foo.getBody();
        spoon.reflect.code.CtCodeSnippetStatement snippet = factory.Core().createCodeSnippetStatement();
        java.util.List<spoon.reflect.code.CtStatement> statements = body.getStatements();
        statements.add(snippet);
        org.junit.Assert.assertEquals(snippet, body.getStatement(0));
        spoon.reflect.code.CtCodeSnippetStatement snippet2 = factory.Core().createCodeSnippetStatement();
        body.getStatements().add(snippet2);
        org.junit.Assert.assertEquals(snippet2, body.getStatement(1));
        org.junit.Assert.assertEquals(2, body.getStatements().size());
        spoon.reflect.code.CtCodeSnippetStatement snippet3 = factory.Core().createCodeSnippetStatement();
        statements.add(snippet3);
        org.junit.Assert.assertEquals(snippet3, body.getStatement(2));
        org.junit.Assert.assertEquals(3, body.getStatements().size());
    }
}

