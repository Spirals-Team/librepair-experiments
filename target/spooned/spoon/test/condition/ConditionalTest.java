package spoon.test.condition;


public class ConditionalTest {
    @org.junit.Test
    public void testConditional() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.condition.testclasses.Foo> aFoo = spoon.testing.utils.ModelUtils.buildClass(spoon.test.condition.testclasses.Foo.class);
        final spoon.reflect.code.CtConditional aConditional = aFoo.getMethod("m2").getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtConditional>(spoon.reflect.code.CtConditional.class)).get(0);
        org.junit.Assert.assertEquals("return a == 18 ? true : false", aConditional.getParent().toString());
    }

    @org.junit.Test
    public void testConditionalWithAssignment() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.condition.testclasses.Foo> aFoo = spoon.testing.utils.ModelUtils.buildClass(spoon.test.condition.testclasses.Foo.class);
        final spoon.reflect.code.CtConditional aConditional = aFoo.getMethod("m").getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtConditional>(spoon.reflect.code.CtConditional.class)).get(0);
        org.junit.Assert.assertEquals("x = (a == 18) ? true : false", aConditional.getParent().toString());
    }

    @org.junit.Test
    public void testBlockInConditionAndLoop() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.condition.testclasses.Foo> aFoo = spoon.testing.utils.ModelUtils.buildClass(spoon.test.condition.testclasses.Foo.class);
        final java.util.List<spoon.reflect.code.CtIf> conditions = aFoo.getMethod("m3").getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtIf>(spoon.reflect.code.CtIf.class));
        org.junit.Assert.assertEquals(4, conditions.size());
        for (spoon.reflect.code.CtIf condition : conditions) {
            org.junit.Assert.assertTrue(((condition.getThenStatement()) instanceof spoon.reflect.code.CtBlock));
            if (((condition.getElseStatement()) != null) && (!((condition.getElseStatement()) instanceof spoon.reflect.code.CtIf))) {
                org.junit.Assert.assertTrue(((condition.getElseStatement()) instanceof spoon.reflect.code.CtBlock));
            }
        }
    }

    @org.junit.Test
    public void testNoBlockInConditionAndLoop() throws java.lang.Exception {
        java.lang.String newLine = java.lang.System.getProperty("line.separator");
        final spoon.reflect.declaration.CtType<spoon.test.condition.testclasses.Foo> aFoo = spoon.testing.utils.ModelUtils.buildClass(spoon.test.condition.testclasses.Foo.class);
        spoon.reflect.declaration.CtMethod<java.lang.Object> method = aFoo.getMethod("m3");
        final java.util.List<spoon.reflect.code.CtIf> conditions = method.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtIf>(spoon.reflect.code.CtIf.class));
        for (int i = 0; i < (conditions.size()); i++) {
            spoon.reflect.code.CtIf ctIf = conditions.get(i);
            spoon.reflect.code.CtStatement then = ((spoon.reflect.code.CtBlock) (ctIf.getThenStatement())).getStatement(0);
            ctIf.setThenStatement(then);
            if ((ctIf.getElseStatement()) != null) {
                spoon.reflect.code.CtStatement elseStatement = ((spoon.reflect.code.CtBlock) (ctIf.getElseStatement())).getStatement(0);
                ctIf.setElseStatement(elseStatement);
            }
        }
        org.junit.Assert.assertEquals(((((((((((("if (true)" + newLine) + "    java.lang.System.out.println();") + newLine) + "else if (true)") + newLine) + "    java.lang.System.out.println();") + newLine) + "else") + newLine) + "    java.lang.System.out.println();") + newLine), method.getBody().getStatement(0).toString());
    }
}

