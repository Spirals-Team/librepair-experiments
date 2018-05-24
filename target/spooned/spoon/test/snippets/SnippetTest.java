package spoon.test.snippets;


import spoon.Launcher;
import spoon.compiler.SpoonResource;
import spoon.support.compiler.VirtualFile;


public class SnippetTest {
    spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();

    @org.junit.Test
    public void testSnippetFullClass() {
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("" + (((("class X {" + "public void foo() {") + " int x=0;") + "}") + "};"))).compile();
        spoon.reflect.declaration.CtMethod<?> foo = ((spoon.reflect.declaration.CtMethod<?>) (clazz.getMethods().toArray()[0]));
        org.junit.Assert.assertEquals(1, foo.getBody().getStatements().size());
    }

    @org.junit.Test
    public void testSnippetWihErrors() {
        try {
            factory.Code().createCodeSnippetStatement(("" + (((("class X {" + "public void foo() {") + " int x=0 sdfsdf;") + "}") + "};"))).compile();
            org.junit.Assert.fail();
        } catch (java.lang.Exception e) {
        }
    }

    @org.junit.Test
    public void testCompileSnippetSeveralTimes() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        final spoon.reflect.code.CtCodeSnippetExpression<java.lang.Object> snippet = factory.Code().createCodeSnippetExpression("1 > 2");
        final spoon.reflect.code.CtExpression<java.lang.Object> compile = snippet.compile();
        final spoon.reflect.code.CtExpression<java.lang.Object> secondCompile = snippet.compile();
        org.junit.Assert.assertTrue((compile instanceof spoon.reflect.code.CtBinaryOperator));
        org.junit.Assert.assertEquals("1 > 2", compile.toString());
        org.junit.Assert.assertTrue((secondCompile instanceof spoon.reflect.code.CtBinaryOperator));
        org.junit.Assert.assertEquals("1 > 2", secondCompile.toString());
        snippet.setValue("1 > 3");
        final spoon.reflect.code.CtExpression<java.lang.Object> thirdCompile = snippet.compile();
        org.junit.Assert.assertTrue((thirdCompile instanceof spoon.reflect.code.CtBinaryOperator));
        org.junit.Assert.assertEquals("1 > 3", thirdCompile.toString());
    }

    @org.junit.Test
    public void testCompileSnippetWithContext() throws java.lang.Exception {
        try {
            factory.Class().create("AClass");
            factory.Code().createCodeSnippetStatement("int i = 1;").compile();
        } catch (java.lang.ClassCastException e) {
            org.junit.Assert.fail();
        }
    }

    @org.junit.Test
    public void testCompileStatementWithReturn() throws java.lang.Exception {
        spoon.reflect.declaration.CtElement el = spoon.support.compiler.SnippetCompilationHelper.compileStatement(factory.Code().createCodeSnippetStatement("return 3"), factory.Type().INTEGER);
        org.junit.Assert.assertTrue(spoon.reflect.code.CtReturn.class.isAssignableFrom(el.getClass()));
        org.junit.Assert.assertEquals("return 3", el.toString());
    }

    @org.junit.Test
    public void testIssue981() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.getEnvironment().setNoClasspath(true);
        SpoonResource input = new VirtualFile("package foo.bar; class X {}");
        spoon.addInputResource(input);
        spoon.buildModel();
        org.junit.Assert.assertEquals("foo.bar", spoon.getFactory().Type().get("foo.bar.X").getPackage().getQualifiedName());
    }
}

