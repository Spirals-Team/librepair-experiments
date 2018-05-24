package spoon.test.eval;


public class EvalTest {
    @org.junit.Test
    public void testStringConcatenation() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.eval", "ToEvaluate");
        org.junit.Assert.assertEquals("ToEvaluate", type.getSimpleName());
        spoon.reflect.code.CtBlock<?> b = type.getMethodsByName("testStrings").get(0).getBody();
        org.junit.Assert.assertEquals(4, b.getStatements().size());
        b = b.partiallyEvaluate();
        b = type.getMethodsByName("testInts").get(0).getBody();
        org.junit.Assert.assertEquals(1, b.getStatements().size());
        b = b.partiallyEvaluate();
        org.junit.Assert.assertEquals("// if removed", b.getStatements().get(0).toString());
    }

    @org.junit.Test
    public void testArrayLength() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.eval", "ToEvaluate");
        org.junit.Assert.assertEquals("ToEvaluate", type.getSimpleName());
        spoon.reflect.code.CtBlock<?> b = type.getMethodsByName("testArray").get(0).getBody();
        org.junit.Assert.assertEquals(1, b.getStatements().size());
        b = b.partiallyEvaluate();
        org.junit.Assert.assertEquals("// if removed", b.getStatements().get(0).toString());
    }

    @org.junit.Test
    public void testDoNotSimplify() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.eval", "ToEvaluate");
        org.junit.Assert.assertEquals("ToEvaluate", type.getSimpleName());
        spoon.reflect.code.CtBlock<?> b = type.getMethodsByName("testDoNotSimplify").get(0).getBody();
        org.junit.Assert.assertEquals(1, b.getStatements().size());
        b = b.partiallyEvaluate();
        org.junit.Assert.assertEquals("java.lang.System.out.println((((\"enter: \" + className) + \" - \") + methodName))", b.getStatements().get(0).toString());
    }

    @org.junit.Test
    public void testDoNotSimplifyCasts() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.eval", "ToEvaluate");
        org.junit.Assert.assertEquals("ToEvaluate", type.getSimpleName());
        spoon.reflect.code.CtBlock<?> b = type.getMethodsByName("testDoNotSimplifyCasts").get(0).getBody();
        org.junit.Assert.assertEquals(1, b.getStatements().size());
        b = b.partiallyEvaluate();
        org.junit.Assert.assertEquals("return ((U) ((java.lang.Object) (spoon.test.eval.ToEvaluate.castTarget(element).getClass())))", b.getStatements().get(0).toString());
    }

    @org.junit.Test
    public void testTryCatchAndStatement() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.eval", "ToEvaluate");
        org.junit.Assert.assertEquals("ToEvaluate", type.getSimpleName());
        spoon.reflect.code.CtBlock<?> b = type.getMethodsByName("tryCatchAndStatement").get(0).getBody();
        org.junit.Assert.assertEquals(2, b.getStatements().size());
        b = b.partiallyEvaluate();
        org.junit.Assert.assertEquals(2, b.getStatements().size());
    }

    @org.junit.Test
    public void testDoNotSimplifyToExpressionWhenStatementIsExpected() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.eval", "ToEvaluate");
        org.junit.Assert.assertEquals("ToEvaluate", type.getSimpleName());
        spoon.reflect.code.CtBlock<?> b = type.getMethodsByName("simplifyOnlyWhenPossible").get(0).getBody();
        org.junit.Assert.assertEquals(3, b.getStatements().size());
        b = b.partiallyEvaluate();
        org.junit.Assert.assertEquals("spoon.test.eval.ToEvaluate.class.getName()", b.getStatements().get(0).toString());
        org.junit.Assert.assertEquals("java.lang.System.out.println(spoon.test.eval.ToEvaluate.getClassLoader())", b.getStatements().get(1).toString());
        org.junit.Assert.assertEquals("return \"spoon.test.eval.ToEvaluate\"", b.getStatements().get(2).toString());
    }

    @org.junit.Test
    public void testVisitorPartialEvaluator_binary() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        {
            spoon.reflect.code.CtCodeElement el = launcher.getFactory().Code().createCodeSnippetExpression("0+1").compile();
            spoon.support.reflect.eval.VisitorPartialEvaluator eval = new spoon.support.reflect.eval.VisitorPartialEvaluator();
            spoon.reflect.declaration.CtElement elnew = eval.evaluate(el);
            org.junit.Assert.assertEquals("1", elnew.toString());
        }
        {
            spoon.reflect.code.CtCodeElement el = launcher.getFactory().Code().createCodeSnippetExpression("(0+1)*3").compile();
            spoon.support.reflect.eval.VisitorPartialEvaluator eval = new spoon.support.reflect.eval.VisitorPartialEvaluator();
            spoon.reflect.declaration.CtElement elnew = eval.evaluate(el);
            org.junit.Assert.assertEquals("3", elnew.toString());
        }
        {
            spoon.reflect.code.CtCodeElement el = launcher.getFactory().Code().createCodeSnippetExpression("(0+1)*3>0").compile();
            spoon.support.reflect.eval.VisitorPartialEvaluator eval = new spoon.support.reflect.eval.VisitorPartialEvaluator();
            spoon.reflect.declaration.CtElement elnew = eval.evaluate(el);
            org.junit.Assert.assertEquals("true", elnew.toString());
        }
        {
            spoon.reflect.code.CtCodeElement el = launcher.getFactory().Code().createCodeSnippetExpression("(0+3-1)*3<=0").compile();
            spoon.support.reflect.eval.VisitorPartialEvaluator eval = new spoon.support.reflect.eval.VisitorPartialEvaluator();
            spoon.reflect.declaration.CtElement elnew = eval.evaluate(el);
            org.junit.Assert.assertEquals("false", elnew.toString());
        }
    }

    @org.junit.Test
    public void testVisitorPartialEvaluator_if() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        {
            spoon.reflect.code.CtCodeElement el = launcher.getFactory().Code().createCodeSnippetStatement("if (false) {System.out.println(\"foo\");} else {System.out.println(\"bar\");} ").compile();
            spoon.support.reflect.eval.VisitorPartialEvaluator eval = new spoon.support.reflect.eval.VisitorPartialEvaluator();
            spoon.reflect.declaration.CtElement elnew = eval.evaluate(el);
            org.junit.Assert.assertEquals((((("{" + (java.lang.System.lineSeparator())) + "    java.lang.System.out.println(\"bar\");") + (java.lang.System.lineSeparator())) + "}"), elnew.toString());
        }
    }

    @org.junit.Test
    public void testVisitorPartialEvaluatorScanner() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("src/test/java/spoon/test/eval/Foo.java");
        launcher.buildModel();
        spoon.support.reflect.eval.VisitorPartialEvaluator eval = new spoon.support.reflect.eval.VisitorPartialEvaluator();
        spoon.reflect.declaration.CtType<?> foo = launcher.getFactory().Type().get(((java.lang.Class<?>) (spoon.test.eval.Foo.class)));
        foo.accept(new spoon.support.reflect.eval.InlinePartialEvaluator(eval));
        org.junit.Assert.assertEquals("false", foo.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtLocalVariable.class)).get(0).getDefaultExpression().toString());
        org.junit.Assert.assertEquals(0, foo.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtIf.class)).size());
    }
}

