package spoon.test.compilation;


public class CompilationTest {
    @org.junit.Test
    public void compileCommandLineTest() throws java.lang.Exception {
        java.lang.String sourceFile = "./src/test/resources/noclasspath/Simple.java";
        java.lang.String compiledFile = "./spooned-classes/Simple.class";
        new java.io.File(compiledFile).delete();
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.run(new java.lang.String[]{ "-i", sourceFile, "-o", "target/spooned", "--compile", "--compliance", "7", "--level", "OFF" });
        org.junit.Assert.assertEquals(true, launcher.getEnvironment().shouldCompile());
        org.junit.Assert.assertEquals(true, new java.io.File(compiledFile).exists());
    }

    @org.junit.Test
    public void compileTest() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/Simple.java");
        java.io.File outputBinDirectory = new java.io.File("./target/class-simple");
        if (!(outputBinDirectory.exists())) {
            outputBinDirectory.mkdirs();
        }
        launcher.setBinaryOutputDirectory(outputBinDirectory);
        launcher.getEnvironment().setShouldCompile(true);
        launcher.buildModel();
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        spoon.reflect.factory.CoreFactory core = factory.Core();
        spoon.reflect.factory.CodeFactory code = factory.Code();
        spoon.reflect.declaration.CtClass simple = factory.Class().get("Simple");
        spoon.reflect.declaration.CtMethod method = core.createMethod();
        method.addModifier(spoon.reflect.declaration.ModifierKind.PUBLIC);
        method.setType(factory.Type().integerPrimitiveType());
        method.setSimpleName("m");
        spoon.reflect.code.CtBlock block = core.createBlock();
        spoon.reflect.code.CtReturn aReturn = core.createReturn();
        spoon.reflect.code.CtBinaryOperator binaryOperator = code.createBinaryOperator(code.createLiteral(10), code.createLiteral(32), spoon.reflect.code.BinaryOperatorKind.PLUS);
        aReturn.setReturnedExpression(binaryOperator);
        block.addStatement(aReturn);
        method.setBody(block);
        simple.addMethod(method);
        launcher.getModelBuilder().compile();
        final java.net.URLClassLoader urlClassLoader = new java.net.URLClassLoader(new java.net.URL[]{ outputBinDirectory.toURL() });
        java.lang.Class<?> aClass = urlClassLoader.loadClass("Simple");
        java.lang.reflect.Method m = aClass.getMethod("m");
        org.junit.Assert.assertEquals(42, m.invoke(aClass.newInstance()));
    }

    @org.junit.Test
    public void testNewInstanceFromExistingClass() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<spoon.test.compilation.testclasses.Bar> barCtType = ((spoon.reflect.declaration.CtClass<spoon.test.compilation.testclasses.Bar>) (spoon.testing.utils.ModelUtils.buildClass(spoon.test.compilation.testclasses.Bar.class)));
        spoon.reflect.code.CtReturn<java.lang.Integer> m = barCtType.getMethod("m").getBody().getStatement(0);
        spoon.test.compilation.testclasses.IBar bar = barCtType.newInstance();
        int value = bar.m();
        org.junit.Assert.assertEquals(1, value);
        m.setReturnedExpression(m.getFactory().Code().createLiteral(2));
        bar = barCtType.newInstance();
        value = bar.m();
        org.junit.Assert.assertEquals(2, value);
        m.replace(m.getFactory().Code().createCodeSnippetStatement("throw new FooEx()"));
        try {
            bar = barCtType.newInstance();
            value = bar.m();
            org.junit.Assert.fail();
        } catch (java.lang.Exception ignore) {
        }
    }

    @org.junit.Test
    public void testNewInstance() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = new spoon.Launcher().getFactory();
        spoon.reflect.declaration.CtClass<spoon.test.compilation.Ifoo> c = factory.Code().createCodeSnippetStatement("class X implements spoon.test.compilation.Ifoo { public int foo() {int i=0; return i;} }").compile();
        c.addModifier(spoon.reflect.declaration.ModifierKind.PUBLIC);
        spoon.reflect.code.CtBlock body = c.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtBlock.class)).get(1);
        spoon.test.compilation.Ifoo o = c.newInstance();
        org.junit.Assert.assertEquals(0, o.foo());
        for (int i = 1; i <= 10; i++) {
            body.getStatement(0).replace(factory.Code().createCodeSnippetStatement((("int i = " + i) + ";")));
            o = c.newInstance();
            org.junit.Assert.assertEquals(i, o.foo());
        }
    }

    @org.junit.Test
    public void testFilterResourcesFile() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher() {
            @java.lang.Override
            public spoon.SpoonModelBuilder createCompiler() {
                return new spoon.support.compiler.jdt.JDTBasedSpoonCompiler(getFactory()) {
                    @java.lang.Override
                    protected spoon.support.compiler.jdt.JDTBatchCompiler createBatchCompiler() {
                        return new spoon.support.compiler.jdt.JDTBatchCompiler(this) {
                            @java.lang.Override
                            public org.eclipse.jdt.internal.compiler.batch.CompilationUnit[] getCompilationUnits() {
                                java.util.List<org.eclipse.jdt.internal.compiler.batch.CompilationUnit> units = new java.util.ArrayList<>();
                                for (org.eclipse.jdt.internal.compiler.batch.CompilationUnit u : super.getCompilationUnits()) {
                                    if (new java.lang.String(u.getMainTypeName()).contains("Foo")) {
                                        units.add(u);
                                    }
                                }
                                return units.toArray(new org.eclipse.jdt.internal.compiler.batch.CompilationUnit[0]);
                            }
                        };
                    }
                };
            }
        };
        launcher.addInputResource("./src/test/java/spoon/test/imports");
        launcher.buildModel();
        int n = 0;
        for (spoon.reflect.declaration.CtType<?> t : launcher.getFactory().getModel().getAllTypes()) {
            n++;
            org.junit.Assert.assertTrue(t.getPosition().getFile().getAbsolutePath().contains("Foo"));
        }
        org.junit.Assert.assertTrue((n >= 2));
    }

    @org.junit.Test
    public void testFilterResourcesDir() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher() {
            @java.lang.Override
            public spoon.SpoonModelBuilder createCompiler() {
                return new spoon.support.compiler.jdt.JDTBasedSpoonCompiler(getFactory()) {
                    @java.lang.Override
                    protected spoon.support.compiler.jdt.JDTBatchCompiler createBatchCompiler() {
                        return new spoon.support.compiler.jdt.JDTBatchCompiler(this) {
                            @java.lang.Override
                            public org.eclipse.jdt.internal.compiler.batch.CompilationUnit[] getCompilationUnits() {
                                java.util.List<org.eclipse.jdt.internal.compiler.batch.CompilationUnit> units = new java.util.ArrayList<>();
                                for (org.eclipse.jdt.internal.compiler.batch.CompilationUnit u : super.getCompilationUnits()) {
                                    if (new java.lang.String(u.getFileName()).replace('\\', '/').contains("/reference/")) {
                                        units.add(u);
                                    }
                                }
                                return units.toArray(new org.eclipse.jdt.internal.compiler.batch.CompilationUnit[0]);
                            }
                        };
                    }
                };
            }
        };
        launcher.addInputResource("./src/test/java/spoon/test");
        launcher.buildModel();
        int n = 0;
        for (spoon.reflect.declaration.CtType<?> t : launcher.getModel().getAllTypes()) {
            n++;
            org.junit.Assert.assertTrue(t.getQualifiedName().contains("reference"));
        }
        org.junit.Assert.assertTrue((n >= 2));
    }

    @org.junit.Test
    public void testPrecompile() {
        spoon.Launcher l = new spoon.Launcher();
        l.setArgs(new java.lang.String[]{ "--noclasspath", "-i", "src/test/resources/compilation/" });
        l.buildModel();
        spoon.reflect.declaration.CtClass klass = l.getFactory().Class().get("compilation.Bar");
        try {
            klass.getSuperInterfaces().toArray(new spoon.reflect.reference.CtTypeReference[0])[0].getActualClass();
            org.junit.Assert.fail();
        } catch (spoon.support.SpoonClassNotFoundException ignore) {
        }
        spoon.Launcher l2 = new spoon.Launcher();
        l2.setArgs(new java.lang.String[]{ "--precompile", "--noclasspath", "-i", "src/test/resources/compilation/" });
        l2.buildModel();
        spoon.reflect.declaration.CtClass klass2 = l2.getFactory().Class().get("compilation.Bar");
        java.lang.Class actualClass = klass2.getSuperInterfaces().toArray(new spoon.reflect.reference.CtTypeReference[0])[0].getActualClass();
        org.junit.Assert.assertNotNull(actualClass);
        org.junit.Assert.assertEquals("IBar", actualClass.getSimpleName());
        spoon.Launcher l3 = new spoon.Launcher();
        l3.setArgs(new java.lang.String[]{ "--precompile", "--noclasspath", "-i", "src/test/resources/compilation/", "-p", "compilation.SimpleProcessor" });
        l3.run();
    }

    @org.junit.Test
    public void testClassLoader() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        try {
            java.lang.Class.forName("spoontest.a.ClassA");
            org.junit.Assert.fail();
        } catch (java.lang.ClassNotFoundException expected) {
        }
        try {
            launcher.getEnvironment().getInputClassLoader().loadClass("spoontest.a.ClassA");
            org.junit.Assert.fail();
        } catch (java.lang.ClassNotFoundException expected) {
        }
        launcher.getEnvironment().setSourceClasspath(new java.lang.String[]{ "src/test/resources/reference-test-2/ReferenceTest2.jar" });
        java.lang.Class c = launcher.getEnvironment().getInputClassLoader().loadClass("spoontest.a.ClassA");
        org.junit.Assert.assertEquals("spoontest.a.ClassA", c.getName());
    }

    @org.junit.Test
    public void testSingleClassLoader() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource(new spoon.support.compiler.FileSystemFolder("./src/test/resources/classloader-test"));
        java.io.File outputBinDirectory = new java.io.File("./target/classloader-test");
        if (!(outputBinDirectory.exists())) {
            outputBinDirectory.mkdirs();
        }
        launcher.setBinaryOutputDirectory(outputBinDirectory);
        launcher.getModelBuilder().build();
        spoon.reflect.reference.CtTypeReference<?> mIFoo = launcher.getFactory().Type().createReference("spoontest.IFoo");
        spoon.reflect.reference.CtTypeReference<?> mFoo = launcher.getFactory().Type().createReference("spoontest.Foo");
        org.junit.Assert.assertTrue("Foo subtype of IFoo", mFoo.isSubtypeOf(mIFoo));
        launcher.getModelBuilder().compile(spoon.SpoonModelBuilder.InputType.FILES);
        launcher = new spoon.Launcher();
        try {
            java.lang.Class.forName("spoontest.IFoo");
            org.junit.Assert.fail();
        } catch (java.lang.ClassNotFoundException expected) {
        }
        try {
            launcher.getEnvironment().getInputClassLoader().loadClass("spoontest.IFoo");
            org.junit.Assert.fail();
        } catch (java.lang.ClassNotFoundException expected) {
        }
        launcher.getEnvironment().setSourceClasspath(new java.lang.String[]{ outputBinDirectory.getAbsolutePath() });
        mIFoo = launcher.getFactory().Type().createReference("spoontest.IFoo");
        mFoo = launcher.getFactory().Type().createReference("spoontest.Foo");
        org.junit.Assert.assertTrue("Foo subtype of IFoo", mFoo.isSubtypeOf(mIFoo));
        java.lang.Class<?> ifoo = launcher.getEnvironment().getInputClassLoader().loadClass("spoontest.IFoo");
        java.lang.Class<?> foo = launcher.getEnvironment().getInputClassLoader().loadClass("spoontest.Foo");
        org.junit.Assert.assertTrue(ifoo.isAssignableFrom(foo));
        org.junit.Assert.assertTrue(((ifoo.getClassLoader()) == (foo.getClassLoader())));
    }

    @org.junit.Test
    public void testExoticClassLoader() throws java.lang.Exception {
        final java.util.List<java.lang.String> l = new java.util.ArrayList<>();
        class MyClassLoader extends java.lang.ClassLoader {
            @java.lang.Override
            protected java.lang.Class<?> loadClass(java.lang.String name, boolean resolve) throws java.lang.ClassNotFoundException {
                l.add(name);
                return super.loadClass(name, resolve);
            }
        }
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setInputClassLoader(new MyClassLoader());
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("src/test/resources/reference-test/Foo.java");
        launcher.buildModel();
        launcher.getModel().getRootPackage().accept(new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public <T> void visitCtTypeReference(spoon.reflect.reference.CtTypeReference<T> reference) {
                try {
                    reference.getTypeDeclaration();
                } catch (spoon.support.SpoonClassNotFoundException ignore) {
                }
            }
        });
        org.junit.Assert.assertEquals(3, l.size());
        org.junit.Assert.assertTrue(l.contains("KJHKY"));
        org.junit.Assert.assertEquals(MyClassLoader.class, launcher.getEnvironment().getInputClassLoader().getClass());
    }

    @org.junit.Test
    public void testURLClassLoader() throws java.lang.Exception {
        java.lang.String expected = "target/classes/";
        java.io.File f = new java.io.File(expected);
        java.net.URL[] urls = new java.net.URL[]{ f.toURL() };
        java.net.URLClassLoader urlClassLoader = new java.net.URLClassLoader(urls);
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setInputClassLoader(urlClassLoader);
        java.lang.String[] sourceClassPath = launcher.getEnvironment().getSourceClasspath();
        org.junit.Assert.assertEquals(1, sourceClassPath.length);
        java.lang.String tail = sourceClassPath[0].substring(((sourceClassPath[0].length()) - (expected.length())));
        org.junit.Assert.assertEquals(expected, tail);
    }

    @org.junit.Test
    public void testURLClassLoaderWithOtherResourcesThanOnlyFiles() throws java.lang.Exception {
        java.lang.String file = "target/classes/";
        java.lang.String distantJar = "http://central.maven.org/maven2/fr/inria/gforge/spoon/spoon-core/5.8.0/spoon-core-5.8.0.jar";
        java.io.File f = new java.io.File(file);
        java.net.URL url = new java.net.URL(distantJar);
        java.net.URL[] urls = new java.net.URL[]{ f.toURL(), url };
        java.net.URLClassLoader urlClassLoader = new java.net.URLClassLoader(urls);
        spoon.Launcher launcher = new spoon.Launcher();
        try {
            launcher.getEnvironment().setInputClassLoader(urlClassLoader);
            org.junit.Assert.fail();
        } catch (spoon.SpoonException e) {
            org.junit.Assert.assertTrue(e.getMessage().contains("Spoon does not support a URLClassLoader containing other resources than local file."));
        }
    }

    @org.junit.Test
    public void testCompilationInEmptyDir() throws java.lang.Exception {
        java.lang.String userDir = java.lang.System.getProperty("user.dir");
        java.io.File testFile = new java.io.File("src/test/resources/compilation/compilation-tests/IBar.java");
        java.lang.String absoluteTestPath = testFile.getAbsolutePath();
        java.nio.file.Path tempDirPath = java.nio.file.Files.createTempDirectory("test_compilation");
        java.lang.System.setProperty("user.dir", tempDirPath.toFile().getAbsolutePath());
        spoon.SpoonModelBuilder compiler = new spoon.Launcher().createCompiler();
        compiler.addInputSource(new java.io.File(absoluteTestPath));
        compiler.setBinaryOutputDirectory(tempDirPath.toFile());
        compiler.compile(spoon.SpoonModelBuilder.InputType.FILES);
        java.lang.System.setProperty("user.dir", userDir);
        org.junit.Assert.assertThat(tempDirPath.toFile().listFiles().length, org.hamcrest.CoreMatchers.not(0));
    }
}

