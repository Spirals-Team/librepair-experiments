package spoon.test.comparison;


public class EqualTest {
    @org.junit.Test
    public void testEqualsEmptyException() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = new spoon.Launcher().createFactory();
        java.lang.String realParam1 = "\"\"";
        java.lang.String content = (((("" + (("class X {" + "public Object foo() {") + " Integer.getInteger(")) + realParam1) + ");") + " return \"\";") + "}};";
        spoon.SpoonModelBuilder builder = new spoon.support.compiler.jdt.JDTSnippetCompiler(factory, content);
        try {
            builder.build();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            org.junit.Assert.fail("Unable create model");
        }
        spoon.reflect.declaration.CtClass<?> clazz1 = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().getAll().get(0)));
        spoon.reflect.declaration.CtMethod<?> method = ((spoon.reflect.declaration.CtMethod<?>) (clazz1.getMethods().toArray()[0]));
        spoon.reflect.code.CtInvocation<?> invo = ((spoon.reflect.code.CtInvocation<?>) (method.getBody().getStatement(0)));
        spoon.reflect.code.CtLiteral<?> argument1 = ((spoon.reflect.code.CtLiteral<?>) (invo.getArguments().get(0)));
        org.junit.Assert.assertEquals(realParam1, argument1.toString());
        spoon.reflect.code.CtReturn<?> returnStatement = ((spoon.reflect.code.CtReturn<?>) (method.getBody().getStatement(1)));
        spoon.reflect.code.CtLiteral<?> returnExp = ((spoon.reflect.code.CtLiteral<?>) (returnStatement.getReturnedExpression()));
        org.junit.Assert.assertEquals(realParam1, returnExp.toString());
        try {
            org.junit.Assert.assertEquals(argument1, returnExp);
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @org.junit.Test
    public void testEqualsComment() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = new spoon.Launcher().createFactory();
        spoon.reflect.code.CtLocalVariable<?> var = factory.Code().createCodeSnippetStatement("int i=0").compile();
        spoon.reflect.code.CtLocalVariable<?> var2 = var.clone();
        var2.addComment(factory.Code().createComment("foo", spoon.reflect.code.CtComment.CommentType.INLINE));
        org.junit.Assert.assertNotEquals(1, var.getComments().size());
        org.junit.Assert.assertNotEquals(var2, var);
    }

    @org.junit.Test
    public void testEqualsMultitype() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = new spoon.Launcher().createFactory();
        spoon.reflect.code.CtTry var = factory.Code().createCodeSnippetStatement("try{}catch(RuntimeException | AssertionError e){}").compile();
        spoon.reflect.code.CtTry var2 = var.clone();
        org.junit.Assert.assertEquals(2, var2.getCatchers().get(0).getParameter().getMultiTypes().size());
        var2.getCatchers().get(0).getParameter().getMultiTypes().remove(0);
        org.junit.Assert.assertEquals(1, var2.getCatchers().get(0).getParameter().getMultiTypes().size());
        org.junit.Assert.assertNotEquals(var2, var);
    }

    @org.junit.Test
    public void testEqualsActualTypeRef() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = new spoon.Launcher().createFactory();
        spoon.reflect.code.CtLocalVariable var = factory.Code().createCodeSnippetStatement("java.util.List<String> l ").compile();
        spoon.reflect.code.CtLocalVariable var2 = factory.Code().createCodeSnippetStatement("java.util.List<Object> l ").compile();
        org.junit.Assert.assertNotEquals(var2, var);
    }

    @org.junit.Test
    public void testEqualsDetails() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = new spoon.Launcher().createFactory();
        spoon.reflect.code.CtTry var = factory.Code().createCodeSnippetStatement("try{}catch(RuntimeException | AssertionError e){}").compile();
        spoon.reflect.code.CtTry var2 = var.clone();
        org.junit.Assert.assertEquals(2, var2.getCatchers().get(0).getParameter().getMultiTypes().size());
        var2.getCatchers().get(0).getParameter().getMultiTypes().remove(0);
        org.junit.Assert.assertEquals(1, var2.getCatchers().get(0).getParameter().getMultiTypes().size());
        spoon.support.visitor.equals.EqualsVisitor ev = new spoon.support.visitor.equals.EqualsVisitor();
        org.junit.Assert.assertFalse(ev.checkEquals(var2, var));
        org.junit.Assert.assertSame(var2.getCatchers().get(0).getParameter().getMultiTypes(), ev.getNotEqualElement());
        org.junit.Assert.assertSame(var.getCatchers().get(0).getParameter().getMultiTypes(), ev.getNotEqualOther());
        org.junit.Assert.assertSame(spoon.reflect.path.CtRole.MULTI_TYPE, ev.getNotEqualRole());
    }
}

