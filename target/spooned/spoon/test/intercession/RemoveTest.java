package spoon.test.intercession;


public class RemoveTest {
    @org.junit.Test
    public void testRemoveAllStatements() {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("" + ((("class X {" + "public void foo() {") + " int x=0;int y=0;") + "}};"))).compile();
        spoon.reflect.declaration.CtMethod<?> foo = ((spoon.reflect.declaration.CtMethod<?>) (clazz.getMethods().toArray()[0]));
        spoon.reflect.code.CtBlock<?> body = foo.getBody();
        org.junit.Assert.assertEquals(2, body.getStatements().size());
        for (spoon.reflect.code.CtStatement s : body) {
            body.removeStatement(s);
        }
        org.junit.Assert.assertEquals(0, body.getStatements().size());
    }
}

