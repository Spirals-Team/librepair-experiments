package spoon.test.api;


import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.factory.Factory;
import spoon.reflect.factory.TypeFactory;
import spoon.test.api.testclasses.Bar;


public class APITest {
    @org.junit.Test
    public void testBasicAPIUsage() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{ "--compile", "--output-type", "compilationunits" });
        spoon.addInputResource("src/test/resources/spoon/test/api");
        spoon.run();
        Factory factory = spoon.getFactory();
        for (spoon.reflect.declaration.CtPackage p : factory.Package().getAll()) {
            spoon.getEnvironment().debugMessage(("package: " + (p.getQualifiedName())));
        }
        for (spoon.reflect.declaration.CtType<?> s : factory.Class().getAll()) {
            spoon.getEnvironment().debugMessage(("class: " + (s.getQualifiedName())));
        }
    }

    @org.junit.Test
    public void testOverrideOutputWriter() throws java.lang.Exception {
        final java.util.List<java.lang.Object> l = new java.util.ArrayList<java.lang.Object>();
        Launcher spoon = new Launcher() {
            @java.lang.Override
            public spoon.support.JavaOutputProcessor createOutputWriter() {
                return new spoon.support.JavaOutputProcessor() {
                    @java.lang.Override
                    public void process(spoon.reflect.declaration.CtNamedElement e) {
                        l.add(e);
                    }

                    @java.lang.Override
                    public void init() {
                    }
                };
            }
        };
        spoon.run(new String[]{ "-i", "src/test/resources/spoon/test/api/", "-o", "fancy/fake/apitest" });
        org.junit.Assert.assertEquals(3, l.size());
    }

    @org.junit.Test
    public void testDuplicateEntry() throws java.lang.Exception {
        try {
            String duplicateEntry = "src/test/resources/spoon/test/api/Foo.java";
            org.junit.Assert.assertTrue(new java.io.File(duplicateEntry).getCanonicalFile().equals(new java.io.File(("./" + duplicateEntry)).getCanonicalFile()));
            Launcher.main(new String[]{ "-i", ((duplicateEntry + (java.io.File.pathSeparator)) + "./") + duplicateEntry, "-o", "target/spooned/apitest" });
        } catch (java.lang.IllegalArgumentException e) {
            org.junit.Assert.fail();
        }
    }

    @org.junit.Test
    public void testDuplicateFolder() throws java.lang.Exception {
        try {
            String duplicateEntry = "src/test/resources/spoon/test/api/";
            Launcher.main(new String[]{ "-i", ((duplicateEntry + (java.io.File.pathSeparator)) + "./") + duplicateEntry, "-o", "target/spooned/apitest" });
        } catch (java.lang.IllegalArgumentException e) {
            org.junit.Assert.fail();
        }
    }

    @org.junit.Test
    public void testDuplicateFilePlusFolder() throws java.lang.Exception {
        try {
            Launcher.main(new String[]{ "-i", ("src/test/resources/spoon/test/api/" + (java.io.File.pathSeparator)) + "src/test/resources/spoon/test/api/Foo.java", "-o", "target/spooned/apitest" });
        } catch (java.lang.IllegalArgumentException e) {
            org.junit.Assert.fail();
        }
    }

    @org.junit.Test(expected = java.lang.Exception.class)
    public void testNotValidInput() throws java.lang.Exception {
        String invalidEntry = "does/not/exists//Foo.java";
        Launcher.main(new String[]{ "-i", invalidEntry, "-o", "target/spooned/apitest" });
    }

    @org.junit.Test
    public void testAddProcessorMethodInSpoonAPI() throws java.lang.Exception {
        final spoon.SpoonAPI launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/api/testclasses");
        launcher.setSourceOutputDirectory("./target/spooned");
        final spoon.test.api.AwesomeProcessor processor = new spoon.test.api.AwesomeProcessor();
        launcher.addProcessor(processor);
        launcher.run();
        org.junit.Assert.assertEquals(1, processor.getElements().size());
        final CtClass<Bar> actual = processor.getElements().get(0);
        org.junit.Assert.assertEquals(2, actual.getMethods().size());
        org.junit.Assert.assertNotNull(actual.getMethodsByName("prepareMojito").get(0));
        org.junit.Assert.assertNotNull(actual.getMethodsByName("makeMojito").get(0));
    }

    @org.junit.Test
    public void testOutputOfSpoon() throws java.lang.Exception {
        final java.io.File sourceOutput = new java.io.File("./target/spoon/test/output/");
        final spoon.SpoonAPI launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/api/testclasses");
        launcher.setSourceOutputDirectory(sourceOutput);
        launcher.run();
        org.junit.Assert.assertTrue(sourceOutput.exists());
    }

    @org.junit.Test
    public void testDestinationOfSpoon() throws java.lang.Exception {
        final java.io.File binaryOutput = new java.io.File("./target/spoon/test/binary/");
        final Launcher launcher = new Launcher();
        launcher.getEnvironment().setShouldCompile(true);
        launcher.addInputResource("./src/test/java/spoon/test/api/testclasses");
        launcher.setSourceOutputDirectory("./target/spooned");
        launcher.setBinaryOutputDirectory(binaryOutput);
        launcher.run();
        org.junit.Assert.assertTrue(binaryOutput.exists());
    }

    @org.junit.Test
    public void testPrintNotAllSourcesWithFilter() throws java.lang.Exception {
        final java.io.File target = new java.io.File("./target/print-not-all/default");
        final spoon.SpoonAPI launcher = new Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/main/java/spoon/template/");
        launcher.setSourceOutputDirectory(target);
        launcher.setOutputFilter(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.declaration.CtType<?>>(spoon.reflect.declaration.CtType.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtType<?> element) {
                return ("spoon.template.Parameter".equals(element.getQualifiedName())) || ("spoon.template.AbstractTemplate".equals(element.getQualifiedName()));
            }
        });
        launcher.run();
        java.util.List<java.io.File> list = new java.util.ArrayList<>(org.apache.commons.io.FileUtils.listFiles(target, new String[]{ "java" }, true));
        final java.util.List<String> filesName = list.stream().map(java.io.File::getName).sorted().collect(java.util.stream.Collectors.<String>toList());
        org.junit.Assert.assertEquals(2, filesName.size());
        org.junit.Assert.assertEquals("AbstractTemplate.java", filesName.get(0));
        org.junit.Assert.assertEquals("Parameter.java", filesName.get(1));
    }

    @org.junit.Test
    public void testPrintNotAllSourcesWithNames() throws java.lang.Exception {
        final java.io.File target = new java.io.File("./target/print-not-all/array");
        final spoon.SpoonAPI launcher = new Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/main/java/spoon/template/");
        launcher.setSourceOutputDirectory(target);
        launcher.setOutputFilter("spoon.template.Parameter", "spoon.template.AbstractTemplate");
        launcher.run();
        java.util.List<java.io.File> list = new java.util.ArrayList<>(org.apache.commons.io.FileUtils.listFiles(target, new String[]{ "java" }, true));
        final java.util.List<String> filesName = list.stream().map(java.io.File::getName).sorted().collect(java.util.stream.Collectors.<String>toList());
        org.junit.Assert.assertEquals(2, filesName.size());
        org.junit.Assert.assertEquals("AbstractTemplate.java", filesName.get(0));
        org.junit.Assert.assertEquals("Parameter.java", filesName.get(1));
    }

    @org.junit.Test
    public void testPrintNotAllSourcesInCommandLine() throws java.lang.Exception {
        final java.io.File target = new java.io.File("./target/print-not-all/command");
        final spoon.SpoonAPI launcher = new Launcher();
        launcher.run(new String[]{ "-i", "./src/main/java", "-o", "./target/print-not-all/command", "-f", "spoon.Launcher:spoon.template.AbstractTemplate", "--noclasspath" });
        java.util.List<java.io.File> list = new java.util.ArrayList<>(org.apache.commons.io.FileUtils.listFiles(target, new String[]{ "java" }, true));
        final java.util.List<String> filesName = list.stream().map(java.io.File::getName).sorted().collect(java.util.stream.Collectors.<String>toList());
        org.junit.Assert.assertEquals(2, filesName.size());
        org.junit.Assert.assertEquals("AbstractTemplate.java", filesName.get(0));
        org.junit.Assert.assertEquals("Launcher.java", filesName.get(1));
    }

    @org.junit.Test
    public void testInvalidateCacheOfCompiler() throws java.lang.Exception {
        final Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/api/testclasses/Bar.java");
        spoon.setSourceOutputDirectory("./target/api");
        spoon.getEnvironment().setNoClasspath(true);
        spoon.run();
        org.junit.Assert.assertTrue(spoon.getModelBuilder().compile());
        final CtClass<Bar> aClass = spoon.getFactory().Class().get(Bar.class);
        final CtMethod aMethod = spoon.getFactory().Core().createMethod();
        aMethod.setSimpleName("foo");
        aMethod.setType(spoon.getFactory().Type().BOOLEAN_PRIMITIVE);
        aMethod.setBody(spoon.getFactory().Core().createBlock());
        aClass.addMethod(aMethod);
        org.junit.Assert.assertFalse(spoon.getModelBuilder().compile());
        aClass.removeMethod(aMethod);
        org.junit.Assert.assertTrue(spoon.getModelBuilder().compile());
    }

    @org.junit.Test
    public void testSetterInNodes() throws java.lang.Exception {
        class SetterMethodWithoutCollectionsFilter extends spoon.reflect.visitor.filter.TypeFilter<CtMethod<?>> {
            private final java.util.List<spoon.reflect.reference.CtTypeReference<?>> collections = new java.util.ArrayList<>(4);

            public SetterMethodWithoutCollectionsFilter(Factory factory) {
                super(CtMethod.class);
                for (java.lang.Class<?> aCollectionClass : java.util.Arrays.asList(java.util.Collection.class, java.util.List.class, java.util.Map.class, java.util.Set.class)) {
                    collections.add(factory.Type().createReference(aCollectionClass));
                }
            }

            @java.lang.Override
            public boolean matches(CtMethod<?> element) {
                boolean isSetter = isSetterMethod(element);
                boolean isNotSubType = !(isSubTypeOfCollection(element));
                boolean doesNotHaveUnsettableAnnotation = doesNotHaveUnsettableAnnotation(element);
                boolean isNotSetterForADerivedProperty = isNotSetterForADerivedProperty(element);
                boolean superMatch = super.matches(element);
                return (((isSetter && doesNotHaveUnsettableAnnotation) && isNotSetterForADerivedProperty) && isNotSubType) && superMatch;
            }

            private boolean isNotSetterForADerivedProperty(CtMethod<?> method) {
                String methodName = method.getSimpleName();
                String getterName = methodName.replace("set", "get");
                if (getterName.equals(methodName)) {
                    return false;
                }
                spoon.reflect.declaration.CtType<?> zeClass = ((spoon.reflect.declaration.CtType) (method.getParent()));
                java.util.List<CtMethod<?>> getterMethods = zeClass.getMethodsByName(getterName);
                if ((getterMethods.size()) != 1) {
                    return false;
                }
                CtMethod<?> getterMethod = getterMethods.get(0);
                return (getterMethod.getAnnotation(spoon.support.DerivedProperty.class)) == null;
            }

            private boolean doesNotHaveUnsettableAnnotation(CtMethod<?> element) {
                return (element.getAnnotation(spoon.support.UnsettableProperty.class)) == null;
            }

            private boolean isSubTypeOfCollection(CtMethod<?> element) {
                final java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters = element.getParameters();
                if ((parameters.size()) != 1) {
                    return false;
                }
                final spoon.reflect.reference.CtTypeReference<?> type = parameters.get(0).getType();
                for (spoon.reflect.reference.CtTypeReference<?> aCollectionRef : collections) {
                    if ((type.isSubtypeOf(aCollectionRef)) || (type.equals(aCollectionRef))) {
                        return true;
                    }
                }
                return false;
            }

            private boolean isSetterMethod(CtMethod<?> element) {
                final java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters = element.getParameters();
                if ((parameters.size()) != 1) {
                    return false;
                }
                final spoon.reflect.reference.CtTypeReference<?> typeParameter = parameters.get(0).getType();
                final spoon.reflect.reference.CtTypeReference<spoon.reflect.declaration.CtElement> ctElementRef = element.getFactory().Type().createReference(spoon.reflect.declaration.CtElement.class);
                boolean isSubtypeof = typeParameter.isSubtypeOf(ctElementRef);
                if (!isSubtypeof) {
                    return false;
                }
                return ((element.getSimpleName().startsWith("set")) && (element.getDeclaringType().getSimpleName().startsWith("Ct"))) && ((element.getBody()) != null);
            }
        }
        class CheckNotNullToSetParentMatcher extends spoon.support.reflect.declaration.CtElementImpl {
            public spoon.template.TemplateParameter<spoon.reflect.code.CtVariableAccess<?>> _parameter_access_;

            public void matcher() {
                if ((_parameter_access_.S()) != null) {
                    _parameter_access_.S().setParent(this);
                }
            }

            @java.lang.Override
            @spoon.template.Local
            public void accept(spoon.reflect.visitor.CtVisitor visitor) {
            }
        }
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "--output-type", "nooutput" });
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/main/java/spoon/support/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/reference");
        launcher.addInputResource((("./src/test/java/" + (this.getClass().getCanonicalName().replace(".", "/"))) + ".java"));
        launcher.addInputResource("./src/main/java/spoon/reflect/");
        launcher.buildModel();
        CtClass<CheckNotNullToSetParentMatcher> matcherCtClass = launcher.getFactory().Class().get(CheckNotNullToSetParentMatcher.class);
        spoon.reflect.code.CtIf templateRoot = matcherCtClass.getMethod("matcher").getBody().getStatement(0);
        final java.util.List<CtMethod<?>> setters = spoon.reflect.visitor.Query.getElements(launcher.getFactory(), new SetterMethodWithoutCollectionsFilter(launcher.getFactory()));
        org.junit.Assert.assertTrue("Number of setters found null", ((setters.size()) > 0));
        for (spoon.reflect.code.CtStatement statement : setters.stream().map(((java.util.function.Function<CtMethod<?>, spoon.reflect.code.CtStatement>) (( ctMethod) -> ctMethod.getBody().getStatement(0)))).collect(java.util.stream.Collectors.toList())) {
            org.junit.Assert.assertTrue(((("Check the method " + (statement.getParent(CtMethod.class).getSignature())) + " in the declaring class ") + (statement.getParent(spoon.reflect.declaration.CtType.class).getQualifiedName())), (statement instanceof spoon.reflect.code.CtIf));
            spoon.reflect.code.CtIf ifCondition = ((spoon.reflect.code.CtIf) (statement));
            spoon.template.TemplateMatcher matcher = new spoon.template.TemplateMatcher(templateRoot);
            org.junit.Assert.assertEquals(((("Check the number of if in method " + (statement.getParent(CtMethod.class).getSignature())) + " in the declaring class ") + (statement.getParent(spoon.reflect.declaration.CtType.class).getQualifiedName())), 1, matcher.find(ifCondition).size());
        }
    }

    @org.junit.Test
    public void testOneLinerIntro() {
        CtClass<?> l = Launcher.parseClass("class A { void m() { System.out.println(\"yeah\");} }");
        org.junit.Assert.assertEquals("A", l.getSimpleName());
        org.junit.Assert.assertEquals(1, l.getMethods().size());
        org.junit.Assert.assertEquals("m", l.getMethodsByName("m").get(0).getSimpleName());
        org.junit.Assert.assertEquals("System.out.println(\"yeah\")", l.getMethodsByName("m").get(0).getBody().getStatement(0).toString());
    }

    @org.junit.Test
    public void testSourceClasspathDoesNotAcceptDotClass() {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/api/testclasses/Bar.java");
        launcher.setBinaryOutputDirectory("./target/spoon-setscp");
        launcher.getEnvironment().setShouldCompile(true);
        launcher.run();
        final Launcher launcher2 = new Launcher();
        try {
            launcher2.getEnvironment().setSourceClasspath(new String[]{ "./target/spoon-setscp/spoon/test/api/testclasses/Bar.class" });
            org.junit.Assert.fail();
        } catch (java.lang.Exception e) {
            org.junit.Assert.assertTrue((e instanceof spoon.compiler.InvalidClassPathException));
            org.junit.Assert.assertTrue(e.getMessage().contains(".class files are not accepted in source classpath."));
        }
    }

    @org.junit.Test
    public void testOutputDestinationHandler() throws java.io.IOException {
        final java.io.File outputDest = java.nio.file.Files.createTempDirectory("spoon").toFile();
        final spoon.support.OutputDestinationHandler outputDestinationHandler = new spoon.support.OutputDestinationHandler() {
            @java.lang.Override
            public java.nio.file.Path getOutputPath(spoon.reflect.declaration.CtModule module, spoon.reflect.declaration.CtPackage pack, spoon.reflect.declaration.CtType type) {
                String path = "";
                if (module != null) {
                    path += (module.getSimpleName()) + "_";
                }
                if (pack != null) {
                    path += (pack.getQualifiedName()) + "_";
                }
                if (type != null) {
                    path += (type.getSimpleName()) + ".java";
                }
                return new java.io.File(outputDest, path).toPath();
            }

            @java.lang.Override
            public java.io.File getDefaultOutputDirectory() {
                return outputDest;
            }
        };
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/api/testclasses/Bar.java");
        launcher.getEnvironment().setOutputDestinationHandler(outputDestinationHandler);
        launcher.run();
        java.io.File generatedFile = new java.io.File(outputDest, "unnamed module_spoon.test.api.testclasses_Bar.java");
        org.junit.Assert.assertTrue(generatedFile.exists());
    }

    @org.junit.Test
    public void testOutputDestinationHandlerWithCUFactory() throws java.io.IOException {
        final java.io.File outputDest = java.nio.file.Files.createTempDirectory("spoon").toFile();
        final spoon.support.OutputDestinationHandler outputDestinationHandler = new spoon.support.OutputDestinationHandler() {
            @java.lang.Override
            public java.nio.file.Path getOutputPath(spoon.reflect.declaration.CtModule module, spoon.reflect.declaration.CtPackage pack, spoon.reflect.declaration.CtType type) {
                String path = "";
                if (module != null) {
                    path += (module.getSimpleName()) + "_";
                    if ((pack == null) && (type == null)) {
                        path += "module-info.java";
                    }
                }
                if (pack != null) {
                    path += (pack.getQualifiedName()) + "_";
                    if (type == null) {
                        path += "package-info.java";
                    }
                }
                if (type != null) {
                    path += (type.getSimpleName()) + ".java";
                }
                return new java.io.File(outputDest, path).toPath();
            }

            @java.lang.Override
            public java.io.File getDefaultOutputDirectory() {
                return outputDest;
            }
        };
        final Launcher launcher = new Launcher();
        launcher.getEnvironment().setComplianceLevel(9);
        launcher.getEnvironment().setOutputDestinationHandler(outputDestinationHandler);
        Factory factory = launcher.getFactory();
        spoon.reflect.declaration.CtModule module = factory.Module().getOrCreate("simplemodule");
        spoon.reflect.cu.CompilationUnit cuModule = factory.CompilationUnit().getOrCreate(module);
        spoon.reflect.declaration.CtPackage ctPackage = factory.Package().getOrCreate("my.beautiful.pack");
        module.setRootPackage(factory.Package().get("my"));
        spoon.reflect.declaration.CtType ctType = factory.Class().create("my.beautiful.pack.SuperClass");
        spoon.reflect.cu.CompilationUnit cuClass = factory.CompilationUnit().getOrCreate(ctType);
        spoon.reflect.cu.CompilationUnit cuPackage = factory.CompilationUnit().getOrCreate(ctPackage);
        java.io.File moduleFile = new java.io.File(outputDest.getCanonicalPath(), "simplemodule_module-info.java");
        java.io.File packageFile = new java.io.File(outputDest.getCanonicalPath(), "simplemodule_my.beautiful.pack_package-info.java");
        java.io.File classFile = new java.io.File(outputDest.getCanonicalPath(), "simplemodule_my.beautiful.pack_SuperClass.java");
        org.junit.Assert.assertEquals(moduleFile, cuModule.getFile());
        org.junit.Assert.assertEquals(packageFile, cuPackage.getFile());
        org.junit.Assert.assertEquals(classFile, cuClass.getFile());
        java.util.Set<String> units = launcher.getFactory().CompilationUnit().getMap().keySet();
        org.junit.Assert.assertEquals(3, units.size());
        org.junit.Assert.assertTrue(((("Module file not contained (" + (moduleFile.getCanonicalPath())) + "). \nContent: ") + (org.apache.commons.lang3.StringUtils.join(units, "\n"))), units.contains(moduleFile.getCanonicalPath()));
        org.junit.Assert.assertTrue(((("Package file not contained (" + (packageFile.getCanonicalPath())) + "). \nContent: ") + (org.apache.commons.lang3.StringUtils.join(units, "\n"))), units.contains(packageFile.getCanonicalPath()));
        org.junit.Assert.assertTrue(((("Class file not contained (" + (classFile.getCanonicalPath())) + "). \nContent: ") + (org.apache.commons.lang3.StringUtils.join(units, "\n"))), units.contains(classFile.getCanonicalPath()));
    }

    @org.junit.Test
    public void testOutputWithNoOutputProduceNoFolder() {
        String destPath = "./target/nooutput_" + (java.util.UUID.randomUUID().toString());
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/api/testclasses/Bar.java");
        launcher.setSourceOutputDirectory(destPath);
        launcher.getEnvironment().setOutputType(spoon.OutputType.NO_OUTPUT);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setCommentEnabled(true);
        launcher.run();
        java.io.File outputDir = new java.io.File(destPath);
        java.lang.System.out.println(destPath);
        org.junit.Assert.assertFalse(("Output dir should not exist: " + (outputDir.getAbsolutePath())), outputDir.exists());
    }
}

