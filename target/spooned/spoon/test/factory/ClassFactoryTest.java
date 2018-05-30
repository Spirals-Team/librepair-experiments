package spoon.test.factory;


public class ClassFactoryTest {
    @org.junit.Test
    public void testDeclaringClass() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        final spoon.reflect.declaration.CtClass<java.lang.Object> declaringClass = factory.Core().createClass();
        declaringClass.setSimpleName("DeclaringClass");
        final spoon.reflect.declaration.CtClass<java.lang.Object> inner = factory.Class().create(declaringClass, "Inner");
        org.junit.Assert.assertEquals("Inner", inner.getSimpleName());
        org.junit.Assert.assertEquals(declaringClass, inner.getDeclaringType());
    }

    @org.junit.Test
    public void testTopLevelClass() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        final spoon.reflect.declaration.CtPackage aPackage = factory.Core().createPackage();
        aPackage.setSimpleName("spoon");
        final spoon.reflect.declaration.CtClass<java.lang.Object> topLevel = factory.Class().create(aPackage, "TopLevel");
        org.junit.Assert.assertEquals("TopLevel", topLevel.getSimpleName());
        org.junit.Assert.assertEquals(aPackage, topLevel.getPackage());
    }
}

