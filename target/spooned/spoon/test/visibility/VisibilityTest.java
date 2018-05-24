package spoon.test.visibility;


public class VisibilityTest {
    @org.junit.Test
    public void testMethodeWithNonAccessibleTypeArgument() throws java.lang.Exception {
        spoon.reflect.factory.Factory f = spoon.testing.utils.ModelUtils.build(spoon.test.visibility.MethodeWithNonAccessibleTypeArgument.class, spoon.test.visibility.packageprotected.AccessibleClassFromNonAccessibleInterf.class, java.lang.Class.forName("spoon.test.visibility.packageprotected.NonAccessibleInterf"));
        spoon.reflect.declaration.CtClass<?> type = f.Class().get(spoon.test.visibility.MethodeWithNonAccessibleTypeArgument.class);
        org.junit.Assert.assertEquals("MethodeWithNonAccessibleTypeArgument", type.getSimpleName());
        spoon.reflect.declaration.CtMethod<?> m = type.getMethodsByName("method").get(0);
        org.junit.Assert.assertEquals("new spoon.test.visibility.packageprotected.AccessibleClassFromNonAccessibleInterf().method(new spoon.test.visibility.packageprotected.AccessibleClassFromNonAccessibleInterf())", m.getBody().getStatement(0).toString());
    }

    @org.junit.Test
    public void testVisibilityOfClassesNamedByClassesInJavaLangPackage() throws java.lang.Exception {
        final java.io.File sourceOutputDir = new java.io.File("target/spooned/spoon/test/visibility_package/testclasses");
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setSourceOutputDirectory(sourceOutputDir);
        final spoon.reflect.factory.Factory factory = launcher.getFactory();
        final spoon.SpoonModelBuilder compiler = launcher.createCompiler();
        compiler.addInputSource(new java.io.File("./src/test/java/spoon/test/visibility/testclasses/"));
        compiler.setSourceOutputDirectory(sourceOutputDir);
        compiler.build();
        compiler.generateProcessedSourceFiles(spoon.OutputType.CLASSES);
        final spoon.reflect.declaration.CtClass<?> aDouble = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.visibility.testclasses.internal.Double.class)));
        org.junit.Assert.assertNotNull(aDouble);
        org.junit.Assert.assertEquals(spoon.test.visibility.testclasses.internal.Double.class, aDouble.getActualClass());
        final spoon.reflect.declaration.CtClass<?> aFloat = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.visibility.testclasses.Float.class)));
        org.junit.Assert.assertNotNull(aFloat);
        org.junit.Assert.assertEquals(spoon.test.visibility.testclasses.Float.class, aFloat.getActualClass());
        spoon.testing.utils.ModelUtils.canBeBuilt(new java.io.File("./target/spooned/spoon/test/visibility_package/testclasses/"), 7);
    }

    @org.junit.Test
    public void testFullyQualifiedNameOfTypeReferenceWithGeneric() throws java.lang.Exception {
        final java.lang.String target = "./target/spooned/spoon/test/visibility_generics/testclasses/";
        final spoon.SpoonAPI launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/visibility/testclasses/A.java");
        launcher.addInputResource("./src/test/java/spoon/test/visibility/testclasses/A2.java");
        launcher.addInputResource("./src/test/java/spoon/test/visibility/testclasses/Foo.java");
        launcher.setSourceOutputDirectory(target);
        launcher.run();
        final spoon.reflect.declaration.CtClass<spoon.test.visibility.testclasses.A> aClass = launcher.getFactory().Class().get(spoon.test.visibility.testclasses.A.class);
        spoon.reflect.declaration.CtType<?> nestedB = aClass.getNestedType("B");
        java.util.List<spoon.reflect.code.CtFieldAccess> elements = nestedB.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldAccess.class));
        org.junit.Assert.assertEquals(1, elements.size());
        org.junit.Assert.assertEquals("(spoon.test.visibility.testclasses.A.B.i)", elements.get(0).toString());
        spoon.reflect.declaration.CtMethod<?> instanceOf = aClass.getMethodsByName("instanceOf").get(0);
        java.util.List<spoon.reflect.code.CtBinaryOperator> elements1 = instanceOf.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtBinaryOperator.class));
        org.junit.Assert.assertEquals(1, elements1.size());
        org.junit.Assert.assertEquals("spoon.test.visibility.testclasses.A.B", elements1.get(0).getRightHandOperand().toString());
        spoon.reflect.declaration.CtMethod<?> returnType = aClass.getMethodsByName("returnType").get(0);
        org.junit.Assert.assertEquals("spoon.test.visibility.testclasses.A<T>.C<T>", returnType.getType().toString());
        final spoon.reflect.declaration.CtClass<spoon.test.visibility.testclasses.A2> secondClass = launcher.getFactory().Class().get(spoon.test.visibility.testclasses.A2.class);
        nestedB = secondClass.getNestedType("B");
        elements = nestedB.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldAccess.class));
        org.junit.Assert.assertEquals(1, elements.size());
        org.junit.Assert.assertEquals("(spoon.test.visibility.testclasses.A2.B.i)", elements.get(0).toString());
        instanceOf = secondClass.getMethodsByName("instanceOf").get(0);
        elements1 = instanceOf.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtBinaryOperator.class));
        org.junit.Assert.assertEquals(1, elements1.size());
        org.junit.Assert.assertEquals("spoon.test.visibility.testclasses.A2.B", elements1.get(0).getRightHandOperand().toString());
        returnType = secondClass.getMethodsByName("returnType").get(0);
        org.junit.Assert.assertEquals("spoon.test.visibility.testclasses.A2.C<java.lang.String>", returnType.getType().toString());
        returnType = secondClass.getMethodsByName("returnType2").get(0);
        org.junit.Assert.assertEquals("spoon.test.visibility.testclasses.Foo<java.lang.String>.Bar<java.lang.String>", returnType.getType().toString());
        spoon.testing.utils.ModelUtils.canBeBuilt(target, 8);
    }

    @org.junit.Test
    public void testName() throws java.lang.Exception {
        final spoon.SpoonAPI launcher = new spoon.Launcher();
        launcher.run(new java.lang.String[]{ "-i", "./src/test/java/spoon/test/visibility/testclasses/Tacos.java", "-o", "./target/spooned/visibility" });
        final java.util.List<spoon.reflect.reference.CtFieldReference<?>> references = spoon.reflect.visitor.Query.getElements(launcher.getFactory(), new spoon.reflect.visitor.filter.AbstractReferenceFilter<spoon.reflect.reference.CtFieldReference<?>>(spoon.reflect.reference.CtFieldReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtFieldReference<?> reference) {
                return "x".equals(reference.getSimpleName());
            }
        });
        org.junit.Assert.assertEquals(1, references.size());
        final spoon.reflect.reference.CtFieldReference<?> field = references.get(0);
        org.junit.Assert.assertNotNull(field.getDeclaration());
        final spoon.reflect.declaration.CtClass<?> tacos = launcher.getFactory().Class().get("spoon.test.visibility.testclasses.Tacos");
        org.junit.Assert.assertEquals(tacos, field.getDeclaringType().getDeclaration());
        org.junit.Assert.assertEquals(tacos.getFields().get(0), field.getDeclaration());
    }

    @org.junit.Test
    public void testInvocationVisibilityInFieldDeclaration() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/test/resources/noclasspath/Solver.java");
        launcher.setSourceOutputDirectory("./target/spooned");
        launcher.buildModel();
        final spoon.reflect.declaration.CtType<java.lang.Object> aSolver = launcher.getFactory().Type().get("org.sat4j.minisat.core.Solver");
        final spoon.reflect.declaration.CtField<?> lbdTimerField = aSolver.getField("lbdTimer");
        final spoon.reflect.code.CtInvocation<?> ctInvocation = lbdTimerField.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtInvocation<?>>(spoon.reflect.code.CtInvocation.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtInvocation<?> element) {
                return ("bound".equals(element.getExecutable().getSimpleName())) && (super.matches(element));
            }
        }).get(0);
        org.junit.Assert.assertNotNull(ctInvocation.getTarget());
        org.junit.Assert.assertTrue(ctInvocation.getTarget().isImplicit());
        org.junit.Assert.assertEquals("bound()", ctInvocation.toString());
    }
}

