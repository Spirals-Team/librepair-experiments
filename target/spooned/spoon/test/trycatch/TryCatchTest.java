package spoon.test.trycatch;


public class TryCatchTest {
    @org.junit.Test
    public void testModelBuildingInitializer() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<spoon.test.trycatch.Main> type = spoon.testing.utils.ModelUtils.build("spoon.test.trycatch", "Main");
        org.junit.Assert.assertEquals("Main", type.getSimpleName());
        spoon.reflect.declaration.CtMethod<java.lang.Void> m = type.getMethod("test");
        org.junit.Assert.assertNotNull(m);
        org.junit.Assert.assertEquals(2, m.getBody().getStatements().size());
        org.junit.Assert.assertTrue(((m.getBody().getStatements().get(0)) instanceof spoon.reflect.code.CtTry));
        org.junit.Assert.assertTrue(((m.getBody().getStatements().get(1)) instanceof spoon.reflect.code.CtTryWithResource));
        spoon.reflect.code.CtTryWithResource t2 = m.getBody().getStatement(1);
        org.junit.Assert.assertNotNull(t2.getResources());
    }

    @org.junit.Test
    public void testFullyQualifiedException() {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("" + ((("class X {" + "public void foo() {") + " try{}catch(java.lang.RuntimeException e){}") + "}};"))).compile();
        spoon.reflect.code.CtTry tryStmt = ((spoon.reflect.code.CtTry) (clazz.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtTry.class)).get(0)));
        org.junit.Assert.assertEquals(1, tryStmt.getCatchers().size());
    }

    @org.junit.Test
    public void testCatchOrder() {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("" + ((((("class X {" + "public void foo() {") + " try{}catch(RuntimeException e){java.lang.System.exit(0);}") + "      catch(Exception e){}") + "}") + "};"))).compile();
        spoon.reflect.code.CtTry tryStmt = ((spoon.reflect.code.CtTry) (clazz.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtTry.class)).get(0)));
        org.junit.Assert.assertEquals(java.lang.RuntimeException.class, tryStmt.getCatchers().get(0).getParameter().getType().getActualClass());
        org.junit.Assert.assertEquals("java.lang.System.exit(0)", tryStmt.getCatchers().get(0).getBody().getStatement(0).toString());
        org.junit.Assert.assertEquals(java.lang.Exception.class, tryStmt.getCatchers().get(1).getParameter().getType().getActualClass());
    }

    @org.junit.Test
    public void testExceptionJava7() {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("" + (((("class X {" + "public void foo() {") + " try{}catch(RuntimeException | Error e){System.exit(0);}") + "}") + "};"))).compile();
        spoon.reflect.code.CtTry tryStmt = ((spoon.reflect.code.CtTry) (clazz.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtTry.class)).get(0)));
        java.util.List<spoon.reflect.code.CtCatch> catchers = tryStmt.getCatchers();
        org.junit.Assert.assertEquals(1, catchers.size());
        org.junit.Assert.assertEquals(java.lang.Throwable.class, catchers.get(0).getParameter().getType().getActualClass());
        org.junit.Assert.assertEquals(2, catchers.get(0).getParameter().getMultiTypes().size());
        org.junit.Assert.assertEquals(java.lang.RuntimeException.class, catchers.get(0).getParameter().getMultiTypes().get(0).getActualClass());
        org.junit.Assert.assertEquals(java.lang.Error.class, catchers.get(0).getParameter().getMultiTypes().get(1).getActualClass());
        org.junit.Assert.assertEquals("java.lang.System.exit(0)", catchers.get(0).getBody().getStatement(0).toString());
    }

    @org.junit.Test
    public void testRethrowingExceptionsJava7() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> clazz = spoon.testing.utils.ModelUtils.build("spoon.test.trycatch", "RethrowingClass");
        spoon.reflect.declaration.CtMethod<?> method = ((spoon.reflect.declaration.CtMethod<?>) (clazz.getMethods().toArray()[0]));
        java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes = method.getThrownTypes();
        org.junit.Assert.assertEquals(2, thrownTypes.size());
        spoon.reflect.code.CtTry ctTry = clazz.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtTry>(spoon.reflect.code.CtTry.class)).get(0);
        java.lang.Class<? extends spoon.reflect.reference.CtCatchVariableReference> exceptionClass = ctTry.getCatchers().get(0).getParameter().getReference().getClass();
        for (spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable> thrownType : thrownTypes) {
            org.junit.Assert.assertNotEquals(thrownType.getClass(), exceptionClass);
        }
    }

    @org.junit.Test
    public void testTryWithOneResource() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> clazz = spoon.testing.utils.ModelUtils.build("spoon.test.trycatch", "TryCatchResourceClass");
        spoon.reflect.declaration.CtMethod<?> method = clazz.getMethodsByName("readFirstLineFromFile").get(0);
        spoon.reflect.code.CtTryWithResource ctTryWithResource = method.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtTryWithResource>(spoon.reflect.code.CtTryWithResource.class)).get(0);
        org.junit.Assert.assertTrue(((ctTryWithResource.getResources().size()) == 1));
    }

    @org.junit.Test
    public void testTryWithResources() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> clazz = spoon.testing.utils.ModelUtils.build("spoon.test.trycatch", "TryCatchResourceClass");
        spoon.reflect.declaration.CtMethod<?> method = clazz.getMethodsByName("writeToFileZipFileContents").get(0);
        spoon.reflect.code.CtTryWithResource ctTryWithResource = method.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtTryWithResource>(spoon.reflect.code.CtTryWithResource.class)).get(0);
        org.junit.Assert.assertTrue(((ctTryWithResource.getResources().size()) > 1));
    }

    @org.junit.Test
    public void testMultiTryCatchWithCustomExceptions() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        final spoon.SpoonModelBuilder compiler = launcher.createCompiler();
        compiler.addInputSource(new java.io.File("./src/test/java/spoon/test/trycatch/testclasses/"));
        compiler.build();
        spoon.reflect.factory.Factory factory = compiler.getFactory();
        final spoon.reflect.declaration.CtClass<?> foo = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.trycatch.testclasses.Foo.class)));
        final spoon.reflect.code.CtCatch ctCatch = foo.getElements(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtCatch>(spoon.reflect.code.CtCatch.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtCatch element) {
                return true;
            }
        }).get(0);
        final java.lang.String expected = ("catch (spoon.test.trycatch.testclasses.internal.MyException | spoon.test.trycatch.testclasses.internal.MyException2 ignore) {" + (java.lang.System.lineSeparator())) + "}";
        org.junit.Assert.assertEquals(expected, ctCatch.toString());
    }

    @org.junit.Test
    public void testCompileMultiTryCatchWithCustomExceptions() throws java.lang.Exception {
        spoon.Launcher.main(new java.lang.String[]{ "-i", "src/test/java/spoon/test/trycatch/testclasses", "-o", "target/spooned" });
        final spoon.Launcher launcher = new spoon.Launcher();
        final spoon.SpoonModelBuilder newCompiler = launcher.createCompiler();
        newCompiler.addInputSource(new java.io.File("./target/spooned/spoon/test/trycatch/testclasses/"));
        try {
            org.junit.Assert.assertTrue(newCompiler.build());
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @org.junit.Test
    public void testTryCatchVariableGetType() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("" + (((("class X {" + "public void foo() {") + " try{}catch(RuntimeException e){System.exit(0);}") + "}") + "};"))).compile();
        spoon.reflect.code.CtTry tryStmt = ((spoon.reflect.code.CtTry) (clazz.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtTry.class)).get(0)));
        java.util.List<spoon.reflect.code.CtCatch> catchers = tryStmt.getCatchers();
        org.junit.Assert.assertEquals(1, catchers.size());
        spoon.reflect.code.CtCatchVariable<?> catchVariable = catchers.get(0).getParameter();
        org.junit.Assert.assertEquals(java.lang.RuntimeException.class, catchVariable.getType().getActualClass());
        org.junit.Assert.assertEquals(1, catchVariable.getMultiTypes().size());
        org.junit.Assert.assertEquals(java.lang.RuntimeException.class, catchVariable.getMultiTypes().get(0).getActualClass());
        catchVariable.setType(((spoon.reflect.reference.CtTypeReference) (factory.Type().createReference(java.lang.IllegalArgumentException.class))));
        org.junit.Assert.assertEquals(java.lang.IllegalArgumentException.class, catchVariable.getType().getActualClass());
        org.junit.Assert.assertEquals(1, catchVariable.getMultiTypes().size());
        org.junit.Assert.assertEquals(java.lang.IllegalArgumentException.class, catchVariable.getMultiTypes().get(0).getActualClass());
        catchVariable.setMultiTypes(java.util.Collections.singletonList(((spoon.reflect.reference.CtTypeReference) (factory.Type().createReference(java.lang.UnsupportedOperationException.class)))));
        org.junit.Assert.assertEquals(java.lang.UnsupportedOperationException.class, catchVariable.getType().getActualClass());
        org.junit.Assert.assertEquals(1, catchVariable.getMultiTypes().size());
        org.junit.Assert.assertEquals(java.lang.UnsupportedOperationException.class, catchVariable.getMultiTypes().get(0).getActualClass());
        catchVariable.setMultiTypes(java.util.Arrays.asList(factory.Type().createReference(java.lang.UnsupportedOperationException.class), factory.Type().createReference(java.lang.IllegalArgumentException.class)));
        org.junit.Assert.assertEquals(2, catchVariable.getMultiTypes().size());
        org.junit.Assert.assertEquals(java.lang.UnsupportedOperationException.class, catchVariable.getMultiTypes().get(0).getActualClass());
        org.junit.Assert.assertEquals(java.lang.IllegalArgumentException.class, catchVariable.getMultiTypes().get(1).getActualClass());
        org.junit.Assert.assertEquals(java.lang.RuntimeException.class, catchVariable.getType().getActualClass());
    }

    @org.junit.Test
    public void testCatchWithExplicitFinalVariable() throws java.io.IOException {
        java.lang.String inputResource = "./src/test/java/spoon/test/trycatch/testclasses/Bar.java";
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource(inputResource);
        launcher.getEnvironment().setComplianceLevel(5);
        launcher.buildModel();
        spoon.reflect.code.CtTry tryStmt = launcher.getModel().getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtTry.class)).get(0);
        java.util.List<spoon.reflect.code.CtCatch> catchers = tryStmt.getCatchers();
        org.junit.Assert.assertEquals(1, catchers.size());
        spoon.reflect.code.CtCatchVariable<?> catchVariable = catchers.get(0).getParameter();
        org.junit.Assert.assertTrue(catchVariable.hasModifier(spoon.reflect.declaration.ModifierKind.FINAL));
        java.util.Set<spoon.support.reflect.CtExtendedModifier> extendedModifierSet = catchVariable.getExtendedModifiers();
        org.junit.Assert.assertEquals(1, extendedModifierSet.size());
        org.junit.Assert.assertEquals(new spoon.support.reflect.CtExtendedModifier(spoon.reflect.declaration.ModifierKind.FINAL, false), extendedModifierSet.iterator().next());
        launcher = new spoon.Launcher();
        launcher.addInputResource(inputResource);
        launcher.setSourceOutputDirectory("./target/spoon-trycatch");
        launcher.getEnvironment().setShouldCompile(true);
        launcher.getEnvironment().setComplianceLevel(5);
        launcher.run();
        java.io.File f = new java.io.File("target/spoon-trycatch/spoon/test/trycatch/testclasses/Bar.java");
        java.lang.String content = org.apache.commons.lang3.StringUtils.join(java.nio.file.Files.readAllLines(f.toPath()), "\n");
        org.junit.Assert.assertTrue(content.contains("catch (final java.lang.Exception e)"));
    }

    @org.junit.Test
    public void testCatchWithUnknownExceptions() {
        java.lang.String inputResource = "./src/test/resources/spoon/test/noclasspath/exceptions/Foo.java";
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource(inputResource);
        launcher.getEnvironment().setNoClasspath(true);
        spoon.reflect.CtModel model = launcher.buildModel();
        java.util.List<spoon.reflect.code.CtCatch> catches = model.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtCatch>(spoon.reflect.code.CtCatch.class));
        org.junit.Assert.assertNotNull(catches.get(0).getParameter().getType());
        org.junit.Assert.assertNull(catches.get(1).getParameter().getType());
        org.junit.Assert.assertNull(catches.get(2).getParameter().getType());
    }
}

