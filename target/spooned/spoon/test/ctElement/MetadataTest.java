package spoon.test.ctElement;


public class MetadataTest {
    @org.junit.Test
    public void testMetadata() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.ctElement.testclasses.Returner.class);
        final spoon.reflect.declaration.CtClass<spoon.test.ctElement.testclasses.Returner> returnerClass = factory.Class().get(spoon.test.ctElement.testclasses.Returner.class);
        final spoon.reflect.declaration.CtMethod<?> staticMethod = returnerClass.getMethodsByName("get").get(0);
        final spoon.reflect.code.CtReturn<java.lang.Integer> ret = staticMethod.getBody().getLastStatement();
        org.junit.Assert.assertNotNull(ret.getMetadataKeys());
        final spoon.reflect.declaration.CtMethod<?> staticMethod2 = returnerClass.getMethodsByName("get2").get(0);
        final spoon.reflect.code.CtReturn<java.lang.Integer> ret2 = staticMethod2.getBody().getLastStatement();
        ret.putMetadata("foo", "bar");
        ret.putMetadata("fiz", 1);
        org.junit.Assert.assertNotNull(ret.getMetadata("fiz"));
        org.junit.Assert.assertNull(ret2.getMetadata("fiz"));
        org.junit.Assert.assertEquals(1, ret.getMetadata("fiz"));
        org.junit.Assert.assertEquals("bar", ret.getMetadata("foo"));
    }
}

