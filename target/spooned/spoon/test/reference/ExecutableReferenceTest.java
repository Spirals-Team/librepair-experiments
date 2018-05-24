package spoon.test.reference;


public class ExecutableReferenceTest {
    @org.junit.Test
    public void testCallMethodOfClassNotPresent() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.run(new java.lang.String[]{ "-i", "./src/test/resources/executable-reference", "--output-type", "nooutput", "--noclasspath" });
        final java.util.List<spoon.reflect.reference.CtExecutableReference<?>> references = spoon.reflect.visitor.Query.getReferences(launcher.getFactory(), new spoon.reflect.visitor.filter.ReferenceTypeFilter<spoon.reflect.reference.CtExecutableReference<?>>(spoon.reflect.reference.CtExecutableReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtExecutableReference<?> reference) {
                return (!(reference.isConstructor())) && (super.matches(reference));
            }
        });
        final java.util.List<spoon.reflect.code.CtInvocation<?>> invocations = spoon.reflect.visitor.Query.getElements(launcher.getFactory(), new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtInvocation<?>>(spoon.reflect.code.CtInvocation.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtInvocation<?> element) {
                return (!(element.getExecutable().isConstructor())) && (super.matches(element));
            }
        });
        org.junit.Assert.assertEquals(4, references.size());
        org.junit.Assert.assertEquals(4, invocations.size());
        final spoon.reflect.reference.CtExecutableReference<?> executableZeroParameter = references.get(0);
        org.junit.Assert.assertNotNull(executableZeroParameter.getDeclaringType());
        org.junit.Assert.assertNull(executableZeroParameter.getType());
        org.junit.Assert.assertEquals(0, executableZeroParameter.getParameters().size());
        org.junit.Assert.assertEquals("m()", executableZeroParameter.toString());
        org.junit.Assert.assertEquals("new Bar().m()", invocations.get(0).toString());
        final spoon.reflect.reference.CtExecutableReference<?> executableOneParameter = references.get(1);
        org.junit.Assert.assertNotNull(executableOneParameter.getDeclaringType());
        org.junit.Assert.assertNotNull(executableOneParameter.getType());
        org.junit.Assert.assertEquals(1, executableOneParameter.getParameters().size());
        org.junit.Assert.assertNotEquals(executableZeroParameter, executableOneParameter);
        org.junit.Assert.assertEquals("m(int)", executableOneParameter.toString());
        org.junit.Assert.assertEquals("bar.m(1)", invocations.get(1).toString());
        final spoon.reflect.reference.CtExecutableReference<?> executableTwoParameters = references.get(2);
        org.junit.Assert.assertNotNull(executableTwoParameters.getDeclaringType());
        org.junit.Assert.assertNull(executableTwoParameters.getType());
        org.junit.Assert.assertEquals(2, executableTwoParameters.getParameters().size());
        org.junit.Assert.assertNotEquals(executableTwoParameters, executableZeroParameter);
        org.junit.Assert.assertNotEquals(executableTwoParameters, executableOneParameter);
        org.junit.Assert.assertEquals("m(int,java.lang.String)", executableTwoParameters.toString());
        org.junit.Assert.assertEquals("new Bar().m(1, \"5\")", invocations.get(2).toString());
        final spoon.reflect.reference.CtExecutableReference<?> staticExecutable = references.get(3);
        org.junit.Assert.assertNotNull(staticExecutable.getDeclaringType());
        org.junit.Assert.assertNull(staticExecutable.getType());
        org.junit.Assert.assertEquals(1, staticExecutable.getParameters().size());
        org.junit.Assert.assertNotEquals(staticExecutable, executableZeroParameter);
        org.junit.Assert.assertNotEquals(staticExecutable, executableOneParameter);
        org.junit.Assert.assertEquals("m(java.lang.String)", staticExecutable.toString());
        org.junit.Assert.assertEquals("Bar.m(\"42\")", invocations.get(3).toString());
    }

    @org.junit.Test
    public void testSuperClassInGetAllExecutables() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/reference/testclasses/");
        launcher.setSourceOutputDirectory("./target/spoon-test");
        launcher.run();
        final spoon.reflect.declaration.CtClass<spoon.test.reference.testclasses.Burritos> aBurritos = launcher.getFactory().Class().get(spoon.test.reference.testclasses.Burritos.class);
        final spoon.reflect.declaration.CtMethod<?> aMethod = aBurritos.getMethodsByName("m").get(0);
        try {
            aMethod.getType().getAllExecutables();
        } catch (java.lang.NullPointerException e) {
            org.junit.Assert.fail("We shoudn't have a NullPointerException when we call getAllExecutables.");
        }
    }

    @org.junit.Test
    public void testSpecifyGetAllExecutablesMethod() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/reference/testclasses");
        launcher.run();
        final spoon.reflect.declaration.CtInterface<spoon.test.reference.testclasses.Foo> foo = launcher.getFactory().Interface().get(spoon.test.reference.testclasses.Foo.class);
        final java.util.List<spoon.reflect.reference.CtExecutableReference<?>> fooExecutables = foo.getAllExecutables().stream().collect(java.util.stream.Collectors.toList());
        org.junit.Assert.assertEquals(1, fooExecutables.size());
        org.junit.Assert.assertEquals(foo.getSuperInterfaces().stream().findFirst().get().getTypeDeclaration().getMethod("m").getReference(), launcher.getFactory().Interface().get(spoon.test.reference.testclasses.SuperFoo.class).getMethod("m").getReference());
        final spoon.reflect.declaration.CtClass<spoon.test.reference.testclasses.Bar> bar = launcher.getFactory().Class().get(spoon.test.reference.testclasses.Bar.class);
        final java.util.List<spoon.reflect.reference.CtExecutableReference<?>> barExecutables = bar.getAllExecutables().stream().collect(java.util.stream.Collectors.toList());
        org.junit.Assert.assertEquals((12 + 1), barExecutables.size());
        final spoon.reflect.declaration.CtInterface<spoon.test.reference.testclasses.Kuu> kuu = launcher.getFactory().Interface().get(spoon.test.reference.testclasses.Kuu.class);
        final java.util.List<spoon.reflect.reference.CtExecutableReference<?>> kuuExecutables = kuu.getAllExecutables().stream().collect(java.util.stream.Collectors.toList());
        org.junit.Assert.assertEquals(1, kuuExecutables.size());
        org.junit.Assert.assertEquals(kuu.getMethod("m").getReference(), kuuExecutables.get(0));
    }

    @org.junit.Test
    public void testCreateReferenceForAnonymousExecutable() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("src/test/resources/noclasspath/Foo4.java");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setComplianceLevel(8);
        launcher.buildModel();
        launcher.getModel().getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtExecutable<?>>(spoon.reflect.declaration.CtExecutable.class) {
            @java.lang.Override
            public boolean matches(final spoon.reflect.declaration.CtExecutable<?> exec) {
                try {
                    exec.getReference();
                } catch (java.lang.ClassCastException ex) {
                    org.junit.Assert.fail(ex.getMessage());
                }
                return super.matches(exec);
            }
        });
    }

    @org.junit.Test
    public void testInvokeEnumMethod() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/reference/Enum.java");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setComplianceLevel(8);
        launcher.buildModel();
        spoon.reflect.code.CtInvocation invocation = launcher.getModel().getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtInvocation>(spoon.reflect.code.CtInvocation.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtInvocation element) {
                return (super.matches(element)) && (element.getExecutable().getSimpleName().equals("valueOf"));
            }
        }).get(0);
        org.junit.Assert.assertNotNull(invocation.getExecutable().getExecutableDeclaration());
    }

    @org.junit.Test
    public void testLambdaNoClasspath() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/org/elasticsearch/action/admin/cluster/node/tasks");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.buildModel();
    }

    @org.junit.Test
    public void testHashcodeWorksWithReference() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/reference/testclasses/EnumValue.java");
        launcher.buildModel();
        spoon.reflect.declaration.CtClass enumValue = launcher.getFactory().Class().get(spoon.test.reference.testclasses.EnumValue.class);
        spoon.reflect.declaration.CtMethod firstMethod = ((spoon.reflect.declaration.CtMethod) (enumValue.getMethodsByName("asEnum").get(0)));
        spoon.reflect.declaration.CtMethod secondMethod = ((spoon.reflect.declaration.CtMethod) (enumValue.getMethodsByName("unwrap").get(0)));
        org.junit.Assert.assertNotNull(firstMethod);
        org.junit.Assert.assertNotNull(secondMethod);
        org.junit.Assert.assertNotEquals(firstMethod, secondMethod);
        org.junit.Assert.assertNotEquals(firstMethod.getReference(), secondMethod.getReference());
        int hashCode1 = firstMethod.hashCode();
        int hashCode2 = secondMethod.hashCode();
        org.junit.Assert.assertNotEquals(hashCode1, hashCode2);
        hashCode1 = firstMethod.getReference().hashCode();
        hashCode2 = secondMethod.getReference().hashCode();
        org.junit.Assert.assertNotEquals(hashCode1, hashCode2);
    }
}

