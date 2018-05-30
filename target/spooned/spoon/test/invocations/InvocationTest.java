package spoon.test.invocations;


public class InvocationTest {
    @org.junit.Test
    public void testTypeOfStaticInvocation() throws java.lang.Exception {
        spoon.SpoonAPI launcher = new spoon.Launcher();
        launcher.run(new java.lang.String[]{ "-i", "./src/test/java/spoon/test/invocations/testclasses/", "-o", "./target/spooned/" });
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        spoon.reflect.declaration.CtClass<?> aClass = factory.Class().get(spoon.test.invocations.testclasses.Foo.class);
        final java.util.List<spoon.reflect.code.CtInvocation<?>> elements = aClass.getElements(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtInvocation<?>>(spoon.reflect.code.CtInvocation.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtInvocation<?> element) {
                return (element.getTarget()) != null;
            }
        });
        org.junit.Assert.assertEquals(2, elements.size());
        org.junit.Assert.assertTrue(((elements.get(0).getTarget()) instanceof spoon.reflect.code.CtTypeAccess));
        org.junit.Assert.assertTrue(((elements.get(1).getTarget()) instanceof spoon.reflect.code.CtTypeAccess));
    }

    @org.junit.Test
    public void testTargetNullForStaticMethod() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.invocations.testclasses.Bar.class);
        final spoon.reflect.declaration.CtClass<spoon.test.invocations.testclasses.Bar> barClass = factory.Class().get(spoon.test.invocations.testclasses.Bar.class);
        final spoon.reflect.declaration.CtMethod<?> staticMethod = barClass.getMethodsByName("staticMethod").get(0);
        final spoon.reflect.reference.CtExecutableReference<?> reference = factory.Method().createReference(staticMethod);
        try {
            final spoon.reflect.code.CtInvocation<?> invocation = factory.Code().createInvocation(null, reference);
            org.junit.Assert.assertNull(invocation.getTarget());
        } catch (java.lang.NullPointerException e) {
            org.junit.Assert.fail();
        }
    }

    @org.junit.Test
    public void testIssue1753() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/test/resources/noclasspath/elasticsearch1753");
        final spoon.reflect.CtModel model = launcher.buildModel();
        final java.util.List<spoon.reflect.declaration.CtExecutable> executables = model.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtExecutable.class)).stream().filter(( i) -> (i.getPosition().getLine()) == 190).collect(java.util.stream.Collectors.toList());
        org.junit.Assert.assertEquals(1, executables.size());
        final spoon.reflect.declaration.CtExecutable exe = executables.get(0);
        org.junit.Assert.assertNotNull(exe.getReference().getDeclaration());
    }
}

