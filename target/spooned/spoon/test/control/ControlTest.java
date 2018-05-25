package spoon.test.control;


public class ControlTest {
    @org.junit.Test
    public void testModelBuildingFor() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.control.testclasses", "Fors");
        org.junit.Assert.assertEquals("Fors", type.getSimpleName());
        java.util.List<spoon.reflect.code.CtFor> fors = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtFor>(spoon.reflect.code.CtFor.class));
        org.junit.Assert.assertEquals(4, fors.size());
        spoon.reflect.declaration.CtMethod<?> normalFor = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "normalFor")).get(0);
        spoon.reflect.code.CtFor firstFor = ((spoon.reflect.code.CtFor) (normalFor.getBody().getStatements().get(0)));
        org.junit.Assert.assertEquals("int i = 0", firstFor.getForInit().get(0).toString());
        org.junit.Assert.assertEquals("i < 2", firstFor.getExpression().toString());
        org.junit.Assert.assertEquals("i++", firstFor.getForUpdate().get(0).toString());
        spoon.reflect.declaration.CtMethod<?> empty1 = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "empty1")).get(0);
        spoon.reflect.code.CtFor empty1For = ((spoon.reflect.code.CtFor) (empty1.getBody().getStatements().get(1)));
        org.junit.Assert.assertEquals("i = 0", empty1For.getForInit().get(0).toString());
        org.junit.Assert.assertNull(empty1For.getExpression());
        org.junit.Assert.assertEquals("i++", empty1For.getForUpdate().get(0).toString());
    }

    @org.junit.Test
    public void testModelBuildingDoWhile() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.control.testclasses", "DoWhile");
        org.junit.Assert.assertEquals("DoWhile", type.getSimpleName());
        spoon.reflect.declaration.CtMethod<?> meth = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "methode")).get(0);
        java.util.List<spoon.reflect.code.CtStatement> stmts = meth.getBody().getStatements();
        org.junit.Assert.assertEquals(2, stmts.size());
        org.junit.Assert.assertTrue(((stmts.get(1)) instanceof spoon.reflect.code.CtDo));
        org.junit.Assert.assertEquals("i++", ((spoon.reflect.code.CtBlock) (((spoon.reflect.code.CtDo) (stmts.get(1))).getBody())).getStatement(0).toString());
    }
}

