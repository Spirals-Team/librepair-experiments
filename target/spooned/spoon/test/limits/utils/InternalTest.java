package spoon.test.limits.utils;


public class InternalTest {
    @org.junit.Test
    public void testInternalClasses() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.limits.utils", "ContainInternalClass");
        org.junit.Assert.assertEquals("ContainInternalClass", type.getSimpleName());
        java.util.List<spoon.reflect.declaration.CtClass<?>> classes = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtClass<?>>(spoon.reflect.declaration.CtClass.class));
        org.junit.Assert.assertEquals(4, classes.size());
        spoon.reflect.declaration.CtClass<?> c1 = classes.get(1);
        org.junit.Assert.assertEquals("InternalClass", c1.getSimpleName());
        org.junit.Assert.assertEquals("spoon.test.limits.utils.ContainInternalClass$InternalClass", c1.getQualifiedName());
        org.junit.Assert.assertEquals("spoon.test.limits.utils", c1.getPackage().getQualifiedName());
        org.junit.Assert.assertEquals(spoon.test.limits.utils.ContainInternalClass.InternalClass.class, c1.getActualClass());
        spoon.reflect.declaration.CtClass<?> c2 = classes.get(2);
        org.junit.Assert.assertEquals("InsideInternalClass", c2.getSimpleName());
        org.junit.Assert.assertEquals("spoon.test.limits.utils.ContainInternalClass$InternalClass$InsideInternalClass", c2.getQualifiedName());
        org.junit.Assert.assertEquals(spoon.test.limits.utils.ContainInternalClass.InternalClass.InsideInternalClass.class, c2.getActualClass());
    }

    @org.junit.Test
    public void testStaticFinalFieldInAnonymousClass() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.limits.utils", "ContainInternalClass");
        java.util.List<spoon.reflect.declaration.CtClass<?>> classes = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtClass<?>>(spoon.reflect.declaration.CtClass.class));
        spoon.reflect.declaration.CtClass<?> c3 = classes.get(3);
        java.util.List<spoon.reflect.declaration.CtNamedElement> fields = c3.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtNamedElement.class, "serialVersionUID"));
        org.junit.Assert.assertEquals(1, fields.size());
    }
}

