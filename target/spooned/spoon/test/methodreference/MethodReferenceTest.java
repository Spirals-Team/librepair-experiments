package spoon.test.methodreference;


public class MethodReferenceTest {
    private static final java.lang.String TEST_CLASS = "spoon.test.methodreference.testclasses.Foo.";

    private spoon.reflect.declaration.CtClass<?> foo;

    @org.junit.Before
    public void setUp() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        final spoon.reflect.factory.Factory factory = launcher.getFactory();
        launcher.getEnvironment().setComplianceLevel(8);
        final spoon.SpoonModelBuilder compiler = launcher.createCompiler(factory);
        launcher.setSourceOutputDirectory("./target/spooned/");
        compiler.addInputSource(new java.io.File("./src/test/java/spoon/test/methodreference/testclasses/"));
        compiler.build();
        compiler.generateProcessedSourceFiles(spoon.OutputType.CLASSES);
        foo = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.methodreference.testclasses.Foo.class)));
    }

    @org.junit.Test
    public void testReferenceToAStaticMethod() throws java.lang.Exception {
        final java.lang.String methodReference = (spoon.test.methodreference.MethodReferenceTest.TEST_CLASS) + "Person::compareByAge";
        final spoon.reflect.code.CtExecutableReferenceExpression<?, ?> reference = getCtExecutableReferenceExpression(methodReference);
        assertTypedBy(java.util.Comparator.class, reference.getType());
        assertTargetedBy(((spoon.test.methodreference.MethodReferenceTest.TEST_CLASS) + "Person"), reference.getTarget());
        org.junit.Assert.assertTrue(((reference.getTarget()) instanceof spoon.reflect.code.CtTypeAccess));
        assertExecutableNamedBy("compareByAge", reference.getExecutable());
        assertIsWellPrinted(methodReference, reference);
    }

    @org.junit.Test
    public void testReferenceToAnInstanceMethodOfAParticularObject() throws java.lang.Exception {
        final java.lang.String methodReference = "myComparisonProvider::compareByName";
        final spoon.reflect.code.CtExecutableReferenceExpression<?, ?> reference = getCtExecutableReferenceExpression(methodReference);
        assertTypedBy(java.util.Comparator.class, reference.getType());
        assertTargetedBy("myComparisonProvider", reference.getTarget());
        org.junit.Assert.assertTrue(((reference.getTarget()) instanceof spoon.reflect.code.CtVariableRead));
        assertExecutableNamedBy("compareByName", reference.getExecutable());
        assertIsWellPrinted(methodReference, reference);
    }

    @org.junit.Test
    public void testReferenceToAnInstanceMethodOfMultiParticularObject() throws java.lang.Exception {
        final java.lang.String methodReference = "tarzan.phone::compareByNumbers";
        final spoon.reflect.code.CtExecutableReferenceExpression<?, ?> reference = getCtExecutableReferenceExpression(methodReference);
        assertTypedBy(java.util.Comparator.class, reference.getType());
        assertTargetedBy("tarzan.phone", reference.getTarget());
        org.junit.Assert.assertTrue(((reference.getTarget()) instanceof spoon.reflect.code.CtFieldRead));
        assertExecutableNamedBy("compareByNumbers", reference.getExecutable());
        assertIsWellPrinted(methodReference, reference);
    }

    @org.junit.Test
    public void testReferenceToAnInstanceMethodOfAnArbitraryObjectOfAParticularType() throws java.lang.Exception {
        final java.lang.String methodReference = "java.lang.String::compareToIgnoreCase";
        final spoon.reflect.code.CtExecutableReferenceExpression<?, ?> reference = getCtExecutableReferenceExpression(methodReference);
        assertTypedBy(java.util.Comparator.class, reference.getType());
        assertTargetedBy("java.lang.String", reference.getTarget());
        org.junit.Assert.assertTrue(((reference.getTarget()) instanceof spoon.reflect.code.CtTypeAccess));
        assertExecutableNamedBy("compareToIgnoreCase", reference.getExecutable());
        assertIsWellPrinted(methodReference, reference);
    }

    @org.junit.Test
    public void testReferenceToAConstructor() throws java.lang.Exception {
        final java.lang.String methodReference = (spoon.test.methodreference.MethodReferenceTest.TEST_CLASS) + "Person::new";
        final spoon.reflect.code.CtExecutableReferenceExpression<?, ?> reference = getCtExecutableReferenceExpression(methodReference);
        assertTypedBy(java.util.function.Supplier.class, reference.getType());
        assertTargetedBy(((spoon.test.methodreference.MethodReferenceTest.TEST_CLASS) + "Person"), reference.getTarget());
        org.junit.Assert.assertTrue(((reference.getTarget()) instanceof spoon.reflect.code.CtTypeAccess));
        assertIsConstructorReference(reference.getExecutable());
        assertIsWellPrinted(methodReference, reference);
    }

    @org.junit.Test
    public void testReferenceToAClassParametrizedConstructor() throws java.lang.Exception {
        final java.lang.String methodReference = (spoon.test.methodreference.MethodReferenceTest.TEST_CLASS) + "Type<java.lang.String>::new";
        final spoon.reflect.code.CtExecutableReferenceExpression<?, ?> reference = getCtExecutableReferenceExpression(methodReference);
        assertTypedBy(java.util.function.Supplier.class, reference.getType());
        assertTargetedBy(((spoon.test.methodreference.MethodReferenceTest.TEST_CLASS) + "Type<java.lang.String>"), reference.getTarget());
        org.junit.Assert.assertTrue(((reference.getTarget()) instanceof spoon.reflect.code.CtTypeAccess));
        assertIsConstructorReference(reference.getExecutable());
        assertIsWellPrinted(methodReference, reference);
    }

    @org.junit.Test
    public void testReferenceToAJavaUtilClassConstructor() throws java.lang.Exception {
        final java.lang.String methodReference = ("java.util.HashSet<" + (spoon.test.methodreference.MethodReferenceTest.TEST_CLASS)) + "Person>::new";
        final spoon.reflect.code.CtExecutableReferenceExpression<?, ?> reference = getCtExecutableReferenceExpression(methodReference);
        assertTypedBy(java.util.function.Supplier.class, reference.getType());
        assertTargetedBy((("java.util.HashSet<" + (spoon.test.methodreference.MethodReferenceTest.TEST_CLASS)) + "Person>"), reference.getTarget());
        org.junit.Assert.assertTrue(((reference.getTarget()) instanceof spoon.reflect.code.CtTypeAccess));
        assertIsConstructorReference(reference.getExecutable());
        assertIsWellPrinted(methodReference, reference);
    }

    @org.junit.Test
    public void testCompileMethodReferenceGeneratedBySpoon() throws java.lang.Exception {
        spoon.testing.utils.ModelUtils.canBeBuilt(new java.io.File("./target/spooned/spoon/test/methodreference/testclasses/"), 8);
    }

    @org.junit.Test
    public void testNoClasspathExecutableReferenceExpression() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.run(new java.lang.String[]{ "-i", "./src/test/resources/executable-reference-expression/Bar.java", "-o", "./target/spooned", "--noclasspath" });
        final spoon.reflect.code.CtExecutableReferenceExpression<?, ?> element = spoon.reflect.visitor.Query.getElements(launcher.getFactory(), new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtExecutableReferenceExpression<?, ?>>(spoon.reflect.code.CtExecutableReferenceExpression.class)).get(0);
        org.junit.Assert.assertEquals("isInstance", element.getExecutable().getSimpleName());
        org.junit.Assert.assertNotNull(element.getExecutable().getDeclaringType());
        org.junit.Assert.assertEquals("Tacos", element.getExecutable().getDeclaringType().getSimpleName());
        org.junit.Assert.assertEquals("elemType::isInstance", element.toString());
    }

    @org.junit.Test
    public void testNoClasspathSuperExecutable() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("src/test/resources/noclasspath/superclass/UnknownSuperClass.java");
        launcher.buildModel();
        final spoon.reflect.CtModel model = launcher.getModel();
        final spoon.reflect.reference.CtTypeReference overrideRef = launcher.getFactory().Annotation().createReference(java.lang.Override.class);
        org.junit.Assert.assertEquals(1, model.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "a")).size());
        org.junit.Assert.assertEquals(1, model.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "b")).size());
        org.junit.Assert.assertEquals(1, model.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "toString")).size());
        final spoon.reflect.declaration.CtMethod bMethod = model.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "b")).get(0);
        org.junit.Assert.assertNotNull(bMethod.getAnnotation(overrideRef));
        org.junit.Assert.assertNull(bMethod.getReference().getOverridingExecutable());
        final spoon.reflect.declaration.CtMethod toStringMethod = model.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "toString")).get(0);
        org.junit.Assert.assertNotNull(toStringMethod.getAnnotation(overrideRef));
        org.junit.Assert.assertNotNull(toStringMethod.getReference().getOverridingExecutable());
    }

    @org.junit.Test
    public void testGetGenericMethodFromReference() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> classCloud = spoon.testing.utils.ModelUtils.buildClass(spoon.test.methodreference.testclasses.Cloud.class);
        spoon.reflect.declaration.CtMethod<?> ctMethod = classCloud.getMethodsByName("method").get(0);
        spoon.reflect.reference.CtExecutableReference<?> execRef = ctMethod.getReference();
        java.lang.reflect.Method method = execRef.getActualMethod();
        org.junit.Assert.assertNotNull(method);
        org.junit.Assert.assertEquals("method", method.getName());
        spoon.reflect.declaration.CtClass<?> classSun = classCloud.getFactory().Class().get("spoon.test.methodreference.testclasses.Sun");
        spoon.reflect.reference.CtExecutableReference<?> execRef2 = classSun.filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtInvocation.class)).select((spoon.reflect.code.CtInvocation i) -> i.getExecutable().getSimpleName().equals("method")).map((spoon.reflect.code.CtInvocation i) -> i.getExecutable()).first();
        org.junit.Assert.assertNotNull(execRef2);
        java.lang.reflect.Method method2 = execRef2.getActualMethod();
        org.junit.Assert.assertNotNull(method2);
        org.junit.Assert.assertEquals("method", method2.getName());
    }

    @org.junit.Test
    public void testGetGenericExecutableReference() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> classCloud = spoon.testing.utils.ModelUtils.buildClass(spoon.test.methodreference.testclasses.Cloud.class);
        java.util.List<spoon.reflect.declaration.CtMethod<?>> methods = classCloud.getMethodsByName("method");
        org.junit.Assert.assertThat(methods.size(), org.hamcrest.CoreMatchers.is(3));
        int n = 0;
        for (spoon.reflect.declaration.CtMethod<?> method1 : classCloud.getMethodsByName("method")) {
            spoon.reflect.reference.CtExecutableReference<?> execRef = method1.getReference();
            java.lang.reflect.Method method = execRef.getActualMethod();
            org.junit.Assert.assertNotNull(method);
            org.junit.Assert.assertEquals("method", method.getName());
            java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters = method1.getParameters();
            org.junit.Assert.assertThat(parameters.size(), org.hamcrest.CoreMatchers.is(2));
            for (int i = 0; i < (parameters.size()); i++) {
                spoon.reflect.reference.CtTypeReference<?> paramTypeRef = parameters.get(i).getType();
                java.lang.Class<?> paramClass = paramTypeRef.getTypeErasure().getActualClass();
                org.junit.Assert.assertSame(paramClass, method.getParameterTypes()[i]);
                spoon.reflect.declaration.CtType<?> paramType = paramTypeRef.getDeclaration();
                org.junit.Assert.assertNotNull(paramType);
                spoon.reflect.reference.CtTypeReference otherParamTypeRef = paramType.getReference();
                org.junit.Assert.assertEquals(paramTypeRef, otherParamTypeRef);
                org.junit.Assert.assertSame(paramType, paramType.getReference().getDeclaration());
                n++;
            }
            org.junit.Assert.assertSame(method1, execRef.getDeclaration());
        }
        org.junit.Assert.assertThat(n, org.hamcrest.CoreMatchers.is((2 * 3)));
    }

    private void assertTypedBy(java.lang.Class<?> expected, spoon.reflect.reference.CtTypeReference<?> type) {
        org.junit.Assert.assertEquals("Method reference must be typed.", expected, type.getActualClass());
    }

    private void assertTargetedBy(java.lang.String expected, spoon.reflect.code.CtExpression<?> target) {
        org.junit.Assert.assertNotNull("Method reference must have a target expression.", target);
        org.junit.Assert.assertEquals("Target reference correspond to the enclosing class.", expected, target.toString());
    }

    private void assertIsConstructorReference(spoon.reflect.reference.CtExecutableReference<?> executable) {
        assertExecutableNamedBy("<init>", executable);
    }

    private void assertExecutableNamedBy(java.lang.String expected, spoon.reflect.reference.CtExecutableReference<?> executable) {
        org.junit.Assert.assertNotNull("Method reference must reference an executable.", executable);
        org.junit.Assert.assertEquals("Method reference must reference the right executable.", expected, executable.getSimpleName());
    }

    private void assertIsWellPrinted(java.lang.String methodReference, spoon.reflect.code.CtExecutableReferenceExpression<?, ?> reference) {
        org.junit.Assert.assertEquals("Method reference must be well printed", methodReference, reference.toString());
    }

    private spoon.reflect.code.CtExecutableReferenceExpression<?, ?> getCtExecutableReferenceExpression(final java.lang.String methodReference) {
        return foo.getElements(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtExecutableReferenceExpression<?, ?>>(spoon.reflect.code.CtExecutableReferenceExpression.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtExecutableReferenceExpression<?, ?> element) {
                return methodReference.equals(element.toString());
            }
        }).get(0);
    }

    @org.junit.Test
    public void testReferenceBuilderWithComplexGenerics() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> classCloud = spoon.testing.utils.ModelUtils.buildClass(spoon.test.methodreference.testclasses.AssertJ.class);
        java.util.List<spoon.reflect.declaration.CtMethod<?>> methods = classCloud.getMethodsByName("assertThat");
        org.junit.Assert.assertThat(methods.size(), org.hamcrest.CoreMatchers.is(1));
        spoon.reflect.declaration.CtMethod method1 = methods.get(0);
        spoon.reflect.reference.CtExecutableReference<?> execRef = method1.getReference();
        java.lang.reflect.Method method = execRef.getActualMethod();
        org.junit.Assert.assertNotNull(method);
        org.junit.Assert.assertEquals("assertThat", method.getName());
        java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters = method1.getParameters();
        org.junit.Assert.assertThat(parameters.size(), org.hamcrest.CoreMatchers.is(1));
        spoon.reflect.reference.CtTypeReference<?> paramTypeRef = parameters.get(0).getType();
        java.lang.Class<?> paramClass = paramTypeRef.getTypeErasure().getActualClass();
        org.junit.Assert.assertSame(paramClass, method.getParameterTypes()[0]);
        org.junit.Assert.assertSame(method1, execRef.getDeclaration());
    }
}

