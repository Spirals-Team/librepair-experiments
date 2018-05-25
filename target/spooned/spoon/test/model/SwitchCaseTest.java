package spoon.test.model;


public class SwitchCaseTest {
    @org.junit.Test
    public void testIterationStatements() {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("" + (((((((("class X {" + "public void foo() {") + " int x=0;") + "switch(x) {") + "case 0: x=x+1;break;") + "case 1: x=0;") + "default: x=-1;") + "}") + "}};"))).compile();
        spoon.reflect.declaration.CtMethod<?> foo = ((spoon.reflect.declaration.CtMethod<?>) (clazz.getMethods().toArray()[0]));
        spoon.reflect.code.CtSwitch<?> sw = foo.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtSwitch<?>>(spoon.reflect.code.CtSwitch.class)).get(0);
        org.junit.Assert.assertEquals(3, sw.getCases().size());
        spoon.reflect.code.CtCase<?> c = ((spoon.reflect.code.CtCase<?>) (sw.getCases().get(0)));
        org.junit.Assert.assertEquals(0, ((spoon.reflect.code.CtLiteral<?>) (c.getCaseExpression())).getValue());
        org.junit.Assert.assertEquals(2, c.getStatements().size());
        java.util.List<spoon.reflect.code.CtStatement> l = new java.util.ArrayList<spoon.reflect.code.CtStatement>();
        for (spoon.reflect.code.CtStatement s : c) {
            l.add(s);
        }
        org.junit.Assert.assertTrue(c.getStatements().equals(l));
    }

    @org.junit.Test
    public void testSwitchStatementOnAString() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> clazz = spoon.testing.utils.ModelUtils.build("spoon.test.model", "SwitchStringClass");
        spoon.reflect.declaration.CtMethod<?> method = ((spoon.reflect.declaration.CtMethod<?>) (clazz.getMethods().toArray()[0]));
        spoon.reflect.code.CtSwitch<?> ctSwitch = method.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtSwitch<?>>(spoon.reflect.code.CtSwitch.class)).get(0);
        org.junit.Assert.assertEquals(java.lang.String.class, ctSwitch.getSelector().getType().getActualClass());
        for (spoon.reflect.code.CtCase<?> aCase : ctSwitch.getCases()) {
            if ((aCase.getCaseExpression()) == null) {
                continue;
            }
            org.junit.Assert.assertEquals(java.lang.String.class, aCase.getCaseExpression().getType().getActualClass());
        }
    }
}

