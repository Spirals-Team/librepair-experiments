package spoon.test.prettyprinter;


public class DefaultPrettyPrinterTest {
    private static final java.lang.String nl = java.lang.System.lineSeparator();

    @org.junit.Test
    public void printerCanPrintInvocationWithoutException() throws java.lang.Exception {
        java.lang.String packageName = "spoon.test.subclass.prettyprinter";
        java.lang.String className = "DefaultPrettyPrinterExample";
        java.lang.String qualifiedName = (packageName + ".") + className;
        spoon.SpoonModelBuilder comp = new spoon.Launcher().createCompiler();
        java.util.List<spoon.compiler.SpoonResource> fileToBeSpooned = spoon.compiler.SpoonResourceHelper.resources((("./src/test/resources/printer-test/" + (qualifiedName.replace('.', '/'))) + ".java"));
        org.junit.Assert.assertEquals(1, fileToBeSpooned.size());
        comp.addInputSources(fileToBeSpooned);
        java.util.List<spoon.compiler.SpoonResource> classpath = spoon.compiler.SpoonResourceHelper.resources("./src/test/resources/printer-test/DefaultPrettyPrinterDependency.jar");
        org.junit.Assert.assertEquals(1, classpath.size());
        comp.setSourceClasspath(classpath.get(0).getPath());
        comp.build();
        spoon.reflect.factory.Factory factory = comp.getFactory();
        spoon.reflect.declaration.CtType<?> theClass = factory.Type().get(qualifiedName);
        java.util.List<spoon.reflect.code.CtInvocation<?>> elements = spoon.reflect.visitor.Query.getElements(theClass, new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtInvocation<?>>(spoon.reflect.code.CtInvocation.class));
        org.junit.Assert.assertEquals(3, elements.size());
        spoon.reflect.code.CtInvocation<?> mathAbsInvocation = elements.get(1);
        org.junit.Assert.assertEquals("java.lang.Math.abs(message.length())", mathAbsInvocation.toString());
    }

    @org.junit.Test
    public void superInvocationWithEnclosingInstance() throws java.lang.Exception {
        java.lang.String sourcePath = "./src/test/resources/spoon/test/prettyprinter/NestedSuperCall.java";
        java.util.List<spoon.compiler.SpoonResource> files = spoon.compiler.SpoonResourceHelper.resources(sourcePath);
        org.junit.Assert.assertEquals(1, files.size());
        spoon.SpoonModelBuilder comp = new spoon.Launcher().createCompiler();
        comp.addInputSources(files);
        comp.build();
        spoon.reflect.factory.Factory factory = comp.getFactory();
        spoon.reflect.declaration.CtType<?> theClass = factory.Type().get("spoon.test.prettyprinter.NestedSuperCall");
        org.junit.Assert.assertTrue(theClass.toString().contains("nc.super(\"a\")"));
    }

    @org.junit.Test
    public void testPrintAClassWithImports() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        final spoon.reflect.factory.Factory factory = launcher.getFactory();
        factory.getEnvironment().setAutoImports(true);
        final spoon.SpoonModelBuilder compiler = launcher.createCompiler();
        compiler.addInputSource(new java.io.File("./src/test/java/spoon/test/prettyprinter/testclasses/"));
        compiler.build();
        final java.lang.String expected = ((((((((((((((("public class AClass {" + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "    public List<?> aMethod() {") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "        return new ArrayList<>();") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "    }") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "    public List<? extends ArrayList> aMethodWithGeneric() {") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "        return new ArrayList<>();") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "    }") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "}";
        final spoon.reflect.declaration.CtClass<?> aClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.prettyprinter.testclasses.AClass.class)));
        org.junit.Assert.assertEquals(expected, aClass.toString());
        final spoon.reflect.code.CtConstructorCall<?> constructorCall = aClass.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtConstructorCall<?>>(spoon.reflect.code.CtConstructorCall.class)).get(0);
        final spoon.reflect.reference.CtTypeReference<?> ctTypeReference = constructorCall.getType().getActualTypeArguments().get(0);
        org.junit.Assert.assertTrue(ctTypeReference.isImplicit());
        org.junit.Assert.assertEquals("Object", ctTypeReference.getSimpleName());
    }

    @org.junit.Test
    public void testPrintAMethodWithImports() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        final spoon.reflect.factory.Factory factory = launcher.getFactory();
        factory.getEnvironment().setAutoImports(true);
        final spoon.SpoonModelBuilder compiler = launcher.createCompiler();
        compiler.addInputSource(new java.io.File("./src/test/java/spoon/test/prettyprinter/testclasses/"));
        compiler.build();
        final java.lang.String expected = ((("public List<?> aMethod() {" + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "    return new ArrayList<>();") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "}";
        final spoon.reflect.declaration.CtClass<?> aClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.prettyprinter.testclasses.AClass.class)));
        org.junit.Assert.assertEquals(expected, aClass.getMethodsByName("aMethod").get(0).toString());
        final spoon.reflect.code.CtConstructorCall<?> constructorCall = aClass.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtConstructorCall<?>>(spoon.reflect.code.CtConstructorCall.class)).get(0);
        final spoon.reflect.reference.CtTypeReference<?> ctTypeReference = constructorCall.getType().getActualTypeArguments().get(0);
        org.junit.Assert.assertTrue(ctTypeReference.isImplicit());
        org.junit.Assert.assertEquals("Object", ctTypeReference.getSimpleName());
    }

    @org.junit.Test
    public void testPrintAMethodWithGeneric() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        final spoon.reflect.factory.Factory factory = launcher.getFactory();
        factory.getEnvironment().setAutoImports(true);
        final spoon.SpoonModelBuilder compiler = launcher.createCompiler();
        compiler.addInputSource(new java.io.File("./src/test/java/spoon/test/prettyprinter/testclasses/"));
        compiler.build();
        final spoon.reflect.declaration.CtClass<?> aClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.prettyprinter.testclasses.AClass.class)));
        final java.lang.String expected = ((("public List<? extends ArrayList> aMethodWithGeneric() {" + (java.lang.System.lineSeparator())) + "    return new ArrayList<>();") + (java.lang.System.lineSeparator())) + "}";
        org.junit.Assert.assertEquals(expected, aClass.getMethodsByName("aMethodWithGeneric").get(0).toString());
        final spoon.reflect.code.CtConstructorCall<?> constructorCall = aClass.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtConstructorCall<?>>(spoon.reflect.code.CtConstructorCall.class)).get(0);
        final spoon.reflect.reference.CtTypeReference<?> ctTypeReference = constructorCall.getType().getActualTypeArguments().get(0);
        org.junit.Assert.assertTrue(ctTypeReference.isImplicit());
        org.junit.Assert.assertEquals("Object", ctTypeReference.getSimpleName());
    }

    @org.junit.Test
    public void autoImportUsesFullyQualifiedNameWhenImportedNameAlreadyPresent() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        final spoon.reflect.factory.Factory factory = launcher.getFactory();
        factory.getEnvironment().setAutoImports(true);
        final spoon.SpoonModelBuilder compiler = launcher.createCompiler();
        compiler.addInputSource(new java.io.File("./src/test/java/spoon/test/prettyprinter/testclasses/sub/TypeIdentifierCollision.java"));
        compiler.addInputSource(new java.io.File("./src/test/java/spoon/test/prettyprinter/testclasses/TypeIdentifierCollision.java"));
        compiler.build();
        final spoon.reflect.declaration.CtClass<?> aClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.prettyprinter.testclasses.TypeIdentifierCollision.class)));
        java.lang.String expected = ((("public void setFieldUsingExternallyDefinedEnumWithSameNameAsLocal() {" + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "    localField = spoon.test.prettyprinter.testclasses.sub.TypeIdentifierCollision.ENUM.E1.ordinal();") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "}";
        java.lang.String computed = aClass.getMethodsByName("setFieldUsingExternallyDefinedEnumWithSameNameAsLocal").get(0).toString();
        org.junit.Assert.assertEquals("We use FQN for E1", expected, computed);
        expected = ((("public void setFieldUsingLocallyDefinedEnum() {" + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "    localField = TypeIdentifierCollision.ENUM.E1.ordinal();") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "}";
        computed = aClass.getMethodsByName("setFieldUsingLocallyDefinedEnum").get(0).toString();
        org.junit.Assert.assertEquals(expected, computed);
        expected = ((("public void setFieldOfClassWithSameNameAsTheCompilationUnitClass() {" + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "    spoon.test.prettyprinter.testclasses.sub.TypeIdentifierCollision.globalField = localField;") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "}";
        computed = aClass.getMethodsByName("setFieldOfClassWithSameNameAsTheCompilationUnitClass").get(0).toString();
        org.junit.Assert.assertEquals("The static field of an external type with the same identifier as the compilation unit is printed with FQN", expected, computed);
        expected = ((((("public void referToTwoInnerClassesWithTheSameName() {" + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "    TypeIdentifierCollision.Class0.ClassA.VAR0 = TypeIdentifierCollision.Class0.ClassA.getNum();") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "    TypeIdentifierCollision.Class1.ClassA.VAR1 = TypeIdentifierCollision.Class1.ClassA.getNum();") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "}";
        computed = aClass.getMethodsByName("referToTwoInnerClassesWithTheSameName").get(0).toString();
        org.junit.Assert.assertEquals("where inner types have the same identifier only one may be shortened and the other should be fully qualified", expected, computed);
        expected = ((((((((((((((((("public enum ENUM {" + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "    E1(spoon.test.prettyprinter.testclasses.sub.TypeIdentifierCollision.globalField,spoon.test.prettyprinter.testclasses.sub.TypeIdentifierCollision.ENUM.E1);") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "    final int NUM;") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "    final Enum<?> e;") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "    private ENUM(int num, Enum<?> e) {") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "        NUM = num;") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "        this.e = e;") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "    }") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "}";
        computed = aClass.getNestedType("ENUM").toString();
        org.junit.Assert.assertEquals(expected, computed);
    }

    @org.junit.Test
    public void useFullyQualifiedNamesInCtElementImpl_toString() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.prettyprinter.testclasses.AClass.class);
        factory.getEnvironment().setAutoImports(false);
        final spoon.reflect.declaration.CtClass<?> aClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.prettyprinter.testclasses.AClass.class)));
        java.lang.String computed = aClass.getMethodsByName("aMethod").get(0).toString();
        final java.lang.String expected = ((("public java.util.List<?> aMethod() {" + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "    return new java.util.ArrayList<>();") + (spoon.test.prettyprinter.DefaultPrettyPrinterTest.nl)) + "}";
        org.junit.Assert.assertEquals("the toString method of CtElementImpl should not shorten type names as it has no context or import statements", expected, computed);
    }

    @org.junit.Test
    public void printClassCreatedWithSpoon() throws java.lang.Exception {
        final java.lang.String nl = java.lang.System.getProperty("line.separator");
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setSourceOutputDirectory(java.io.File.createTempFile("foo", "").getParentFile());
        launcher.buildModel();
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        spoon.reflect.declaration.CtClass<java.lang.Object> ctClass = factory.Class().create("foo.Bar");
        spoon.support.JavaOutputProcessor jop = launcher.createOutputWriter();
        jop.setFactory(factory);
        jop.createJavaFile(ctClass);
        java.lang.String pathname = (java.lang.System.getProperty("java.io.tmpdir")) + "/foo/Bar.java";
        java.io.File javaFile = new java.io.File(pathname);
        org.junit.Assert.assertTrue(javaFile.exists());
        org.junit.Assert.assertEquals((((((("package foo;" + nl) + nl) + nl) + "class Bar {}") + nl) + nl), org.apache.commons.io.IOUtils.toString(new java.io.FileInputStream(javaFile), "UTF-8"));
    }

    @org.junit.Test
    public void importsFromMultipleTypesSupported() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/prettyprinter/testclasses/A.java");
        launcher.run();
        spoon.compiler.Environment env = launcher.getEnvironment();
        env.setAutoImports(true);
        spoon.reflect.visitor.DefaultJavaPrettyPrinter printer = new spoon.reflect.visitor.DefaultJavaPrettyPrinter(env);
        printer.calculate(null, java.util.Arrays.asList(launcher.getFactory().Class().get("spoon.test.prettyprinter.testclasses.A"), launcher.getFactory().Class().get("spoon.test.prettyprinter.testclasses.B")));
        org.junit.Assert.assertTrue(printer.getResult().contains("import java.util.ArrayList;"));
    }

    @org.junit.Test
    public void testTernaryParenthesesOnLocalVariable() {
        spoon.Launcher launcher = new spoon.Launcher();
        spoon.reflect.code.CtCodeSnippetStatement snippet = launcher.getFactory().Code().createCodeSnippetStatement("final int foo = (new Object() instanceof Object ? new Object().equals(null) : new Object().equals(new Object())) ? 0 : new Object().hashCode();");
        spoon.reflect.code.CtStatement compile = snippet.compile();
        snippet = launcher.getFactory().Code().createCodeSnippetStatement(compile.toString());
        org.junit.Assert.assertEquals(compile, snippet.compile());
    }

    @org.junit.Test
    public void testIssue1501() {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/orwall/PreferencesActivity.java");
        launcher.addInputResource("./src/test/resources/noclasspath/orwall/BackgroundProcess.java");
        launcher.setSourceOutputDirectory("./target/issue1501");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        org.junit.Assert.assertFalse(launcher.getModel().getAllTypes().isEmpty());
    }
}

