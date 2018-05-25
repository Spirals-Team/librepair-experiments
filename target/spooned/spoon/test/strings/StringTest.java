package spoon.test.strings;


public class StringTest {
    @org.junit.Test
    public void testModelBuildingInitializer() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.strings", "Main");
        org.junit.Assert.assertEquals("Main", type.getSimpleName());
        spoon.reflect.code.CtBinaryOperator<?> op = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtBinaryOperator<?>>(spoon.reflect.code.CtBinaryOperator.class)).get(0);
        org.junit.Assert.assertEquals("(\"a\" + \"b\")", op.toString());
        org.junit.Assert.assertEquals(spoon.reflect.code.BinaryOperatorKind.PLUS, op.getKind());
        org.junit.Assert.assertTrue(((op.getLeftHandOperand()) instanceof spoon.reflect.code.CtLiteral));
        org.junit.Assert.assertTrue(((op.getRightHandOperand()) instanceof spoon.reflect.code.CtLiteral));
    }
}

