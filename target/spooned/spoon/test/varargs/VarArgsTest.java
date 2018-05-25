package spoon.test.varargs;


public class VarArgsTest {
    @org.junit.Test
    public void testModelBuildingInitializer() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<spoon.test.trycatch.Main> type = spoon.testing.utils.ModelUtils.build("spoon.test.varargs", "VarArgsSample");
        org.junit.Assert.assertEquals("VarArgsSample", type.getSimpleName());
        spoon.reflect.declaration.CtMethod<?> m = type.getMethodsByName("foo").get(0);
        spoon.reflect.declaration.CtParameter<?> param0 = m.getParameters().get(0);
        org.junit.Assert.assertEquals(false, param0.isVarArgs());
        spoon.reflect.declaration.CtParameter<?> param1 = m.getParameters().get(1);
        org.junit.Assert.assertEquals(true, param1.isVarArgs());
        org.junit.Assert.assertEquals("java.lang.String[]", param1.getType().toString());
        org.junit.Assert.assertEquals("String[]", param1.getType().getSimpleName());
        org.junit.Assert.assertEquals("java.lang.String[]", param1.getType().getQualifiedName());
        org.junit.Assert.assertEquals("java.lang.String", ((spoon.reflect.reference.CtArrayTypeReference<?>) (param1.getType())).getComponentType().toString());
        org.junit.Assert.assertEquals((("void foo(int arg0, java.lang.String... args) {" + (spoon.reflect.visitor.DefaultJavaPrettyPrinter.LINE_SEPARATOR)) + "}"), m.toString());
    }
}

