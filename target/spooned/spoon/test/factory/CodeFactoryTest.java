package spoon.test.factory;


public class CodeFactoryTest {
    @org.junit.Test
    public void testThisAccess() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        final spoon.reflect.reference.CtTypeReference<java.lang.Object> type = factory.Type().createReference("fr.inria.Test");
        final spoon.reflect.code.CtThisAccess<java.lang.Object> thisAccess = factory.Code().createThisAccess(type);
        org.junit.Assert.assertNotNull(thisAccess.getTarget());
        org.junit.Assert.assertTrue(((thisAccess.getTarget()) instanceof spoon.reflect.code.CtTypeAccess));
        org.junit.Assert.assertEquals(type, ((spoon.reflect.code.CtTypeAccess) (thisAccess.getTarget())).getAccessedType());
    }
}

