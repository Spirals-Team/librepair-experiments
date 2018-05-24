package spoon.test.executable;


public class ExecutableTest {
    @org.junit.Test
    public void testInfoInsideAnonymousExecutable() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/executable/testclasses/AnonymousExecutableSample.java");
        launcher.run();
        final java.util.List<spoon.reflect.declaration.CtAnonymousExecutable> anonymousExecutables = spoon.reflect.visitor.Query.getElements(launcher.getFactory(), new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtAnonymousExecutable>(spoon.reflect.declaration.CtAnonymousExecutable.class));
        org.junit.Assert.assertEquals(2, anonymousExecutables.size());
        for (spoon.reflect.declaration.CtAnonymousExecutable anonymousExecutable : anonymousExecutables) {
            org.junit.Assert.assertEquals("", anonymousExecutable.getSimpleName());
            org.junit.Assert.assertEquals(launcher.getFactory().Type().VOID_PRIMITIVE, anonymousExecutable.getType());
            org.junit.Assert.assertEquals(0, anonymousExecutable.getParameters().size());
            org.junit.Assert.assertEquals(0, anonymousExecutable.getThrownTypes().size());
        }
    }

    @org.junit.Test
    public void testBlockInExecutable() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.executable.testclasses.Pozole> aPozole = spoon.testing.utils.ModelUtils.buildClass(spoon.test.executable.testclasses.Pozole.class);
        org.junit.Assert.assertTrue(((aPozole.getMethod("m").getBody().getStatement(1)) instanceof spoon.reflect.code.CtBlock));
    }

    @org.junit.Test
    public void testGetReference() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.executable.testclasses.A> aClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.executable.testclasses.A.class);
        java.lang.String methodName = "getInt1";
        spoon.reflect.reference.CtExecutableReference<?> methodRef = aClass.getMethod(methodName).getReference();
        org.junit.Assert.assertEquals(false, methodRef.isFinal());
        org.junit.Assert.assertEquals(true, methodRef.isStatic());
        org.junit.Assert.assertEquals(aClass.getFactory().Type().integerPrimitiveType(), methodRef.getType());
        org.junit.Assert.assertEquals(aClass.getMethod(methodName), methodRef.getDeclaration());
        methodName = "getInt2";
        methodRef = aClass.getMethod(methodName).getReference();
        org.junit.Assert.assertEquals(true, methodRef.isFinal());
        org.junit.Assert.assertEquals(true, methodRef.isStatic());
        org.junit.Assert.assertEquals(aClass.getFactory().Type().integerPrimitiveType(), methodRef.getType());
        org.junit.Assert.assertEquals(aClass.getMethod(methodName), methodRef.getDeclaration());
        methodName = "getInt3";
        methodRef = aClass.getMethod(methodName).getReference();
        org.junit.Assert.assertEquals(true, methodRef.isFinal());
        org.junit.Assert.assertEquals(false, methodRef.isStatic());
        org.junit.Assert.assertEquals(aClass.getFactory().Type().integerPrimitiveType(), methodRef.getType());
        org.junit.Assert.assertEquals(aClass.getMethod(methodName), methodRef.getDeclaration());
        methodName = "getInt4";
        methodRef = aClass.getMethod(methodName).getReference();
        org.junit.Assert.assertEquals(false, methodRef.isFinal());
        org.junit.Assert.assertEquals(false, methodRef.isStatic());
        org.junit.Assert.assertEquals(aClass.getFactory().Type().integerPrimitiveType(), methodRef.getType());
        org.junit.Assert.assertEquals(aClass.getMethod(methodName), methodRef.getDeclaration());
    }
}

