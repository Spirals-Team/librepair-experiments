package spoon.test.executable;


public class ExecutableRefTest {
    @org.junit.Test
    public void methodTest() throws java.lang.Exception {
        spoon.reflect.code.CtAbstractInvocation<?> ctAbstractInvocation = this.getInvocationFromMethod("testMethod");
        org.junit.Assert.assertTrue((ctAbstractInvocation instanceof spoon.reflect.code.CtInvocation<?>));
        spoon.reflect.reference.CtExecutableReference<?> executableReference = ctAbstractInvocation.getExecutable();
        org.junit.Assert.assertNotNull(executableReference);
        java.lang.reflect.Method method = executableReference.getActualMethod();
        org.junit.Assert.assertNotNull(method);
        org.junit.Assert.assertEquals("Hello World", method.invoke(null, ((spoon.reflect.code.CtLiteral<?>) (ctAbstractInvocation.getArguments().get(0))).getValue()));
    }

    @org.junit.Test
    public void constructorTest() throws java.lang.Exception {
        spoon.reflect.code.CtAbstractInvocation<?> ctAbstractInvocation = this.getInvocationFromMethod("testConstructor");
        org.junit.Assert.assertTrue((ctAbstractInvocation instanceof spoon.reflect.code.CtConstructorCall<?>));
        spoon.reflect.reference.CtExecutableReference<?> executableReference = ctAbstractInvocation.getExecutable();
        org.junit.Assert.assertNotNull(executableReference);
        java.lang.reflect.Constructor<?> constructor = executableReference.getActualConstructor();
        org.junit.Assert.assertNotNull(constructor);
        org.junit.Assert.assertEquals("Hello World", constructor.newInstance(((spoon.reflect.code.CtLiteral<?>) (ctAbstractInvocation.getArguments().get(0))).getValue()));
    }

    @org.junit.Test
    public void testGetActualClassTest() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.executable.testclasses.ExecutableRefTestSource.class, spoon.test.executable.testclasses.MyIntf.class);
        spoon.reflect.declaration.CtMethod<?> method = factory.Class().get(spoon.test.executable.testclasses.ExecutableRefTestSource.class).getMethod("myMethod");
        spoon.reflect.reference.CtExecutableReference<?> ref = method.getReference();
        java.lang.reflect.Method m = ref.getActualMethod();
        org.junit.Assert.assertEquals("myMethod", m.getName());
        org.junit.Assert.assertEquals(0, m.getExceptionTypes().length);
    }

    @org.junit.Test
    public void testSameTypeInConstructorCallBetweenItsObjectAndItsExecutable() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/test/resources/executable/CmiContext_1.2.java");
        launcher.setSourceOutputDirectory("./target/executable");
        launcher.run();
        final spoon.reflect.declaration.CtClass<java.lang.Object> aClass = launcher.getFactory().Class().get("org.objectweb.carol.jndi.spi.CmiContext");
        final java.util.List<spoon.reflect.code.CtConstructorCall> ctConstructorCalls = aClass.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtConstructorCall>(spoon.reflect.code.CtConstructorCall.class));
        for (spoon.reflect.code.CtConstructorCall constructorCall : ctConstructorCalls) {
            org.junit.Assert.assertNotNull(constructorCall.getExecutable());
        }
        spoon.testing.utils.ModelUtils.canBeBuilt("./target/executable", 8, true);
    }

    private spoon.reflect.code.CtAbstractInvocation<?> getInvocationFromMethod(java.lang.String methodName) throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.executable.testclasses.ExecutableRefTestSource.class, spoon.test.executable.testclasses.MyIntf.class);
        spoon.reflect.declaration.CtClass<spoon.test.executable.testclasses.ExecutableRefTestSource> clazz = factory.Class().get(spoon.test.executable.testclasses.ExecutableRefTestSource.class);
        org.junit.Assert.assertNotNull(clazz);
        java.util.List<spoon.reflect.declaration.CtMethod<?>> methods = clazz.getMethodsByName(methodName);
        org.junit.Assert.assertEquals(1, methods.size());
        spoon.reflect.declaration.CtMethod<?> ctMethod = methods.get(0);
        spoon.reflect.code.CtBlock<?> ctBody = ((spoon.reflect.code.CtBlock<?>) (ctMethod.getBody()));
        org.junit.Assert.assertNotNull(ctBody);
        java.util.List<spoon.reflect.code.CtStatement> ctStatements = ctBody.getStatements();
        org.junit.Assert.assertEquals(1, ctStatements.size());
        spoon.reflect.code.CtStatement ctStatement = ctStatements.get(0);
        org.junit.Assert.assertTrue((ctStatement instanceof spoon.reflect.code.CtAbstractInvocation<?>));
        return ((spoon.reflect.code.CtAbstractInvocation<?>) (ctStatement));
    }

    @org.junit.Test
    public void testOverridingMethod() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.executable.testclasses.Pozole> aPozole = spoon.testing.utils.ModelUtils.buildClass(spoon.test.executable.testclasses.Pozole.class);
        final spoon.reflect.reference.CtExecutableReference<?> run = aPozole.getMethodsByName("run").get(0).getReference();
        final java.util.List<spoon.reflect.code.CtInvocation<?>> elements = spoon.reflect.visitor.Query.getElements(run.getFactory(), new spoon.reflect.visitor.filter.InvocationFilter(run));
        org.junit.Assert.assertEquals(1, elements.size());
        org.junit.Assert.assertEquals(run, elements.get(0).getExecutable());
    }
}

