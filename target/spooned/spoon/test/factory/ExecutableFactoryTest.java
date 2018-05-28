package spoon.test.factory;


public class ExecutableFactoryTest {
    @org.junit.Test
    public void testCreateReference() {
        spoon.reflect.factory.Factory f = spoon.testing.utils.ModelUtils.createFactory();
        spoon.reflect.factory.ExecutableFactory ef = f.Executable();
        java.lang.String signature = "boolean Object#equals(Object)";
        spoon.reflect.reference.CtExecutableReference<java.lang.Object> eref = ef.createReference(signature);
        java.lang.String type = eref.getType().getQualifiedName();
        java.lang.String decltype = eref.getDeclaringType().getQualifiedName();
        java.lang.String name = eref.getSimpleName();
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> params = eref.getParameters();
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> atas = eref.getActualTypeArguments();
        org.junit.Assert.assertEquals("boolean", type);
        org.junit.Assert.assertEquals("Object", decltype);
        org.junit.Assert.assertEquals("equals", name);
        org.junit.Assert.assertEquals(1, params.size());
        org.junit.Assert.assertEquals(0, atas.size());
    }
}

