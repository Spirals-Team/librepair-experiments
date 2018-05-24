package spoon.test.imports;


import java.util.List;
import spoon.Launcher;
import spoon.SpoonModelBuilder;
import spoon.compiler.SpoonResourceHelper;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtImport;
import spoon.reflect.declaration.CtImportKind;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.NamedElementFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.test.imports.testclasses.Reflection;
import spoon.test.imports.testclasses.SubClass;


public class ImportTest {
    @org.junit.Test
    public void testImportOfAnInnerClassInASuperClassPackageAutoImport() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.getEnvironment().setShouldCompile(true);
        spoon.getEnvironment().setAutoImports(true);
        spoon.addInputResource("./src/test/java/spoon/test/imports/testclasses/internal/SuperClass.java");
        spoon.addInputResource("./src/test/java/spoon/test/imports/testclasses/internal/ChildClass.java");
        spoon.addInputResource("./src/test/java/spoon/test/imports/testclasses/internal/PublicInterface2.java");
        spoon.addInputResource("./src/test/java/spoon/test/imports/testclasses/ClientClass.java");
        spoon.setBinaryOutputDirectory("./target/spoon/super_imports/bin");
        spoon.setSourceOutputDirectory("./target/spoon/super_imports/src");
        spoon.run();
        final List<CtClass> classes = Query.getElements(spoon.getFactory(), new NamedElementFilter<>(CtClass.class, "ClientClass"));
        final CtType<?> innerClass = classes.get(0).getNestedType("InnerClass");
        String expected = "spoon.test.imports.testclasses.ClientClass.InnerClass";
        org.junit.Assert.assertEquals(expected, innerClass.getReference().toString());
        org.junit.Assert.assertEquals("spoon.test.imports.testclasses.internal.ChildClass.InnerClassProtected", innerClass.getSuperclass().toString());
        org.junit.Assert.assertEquals("spoon.test.imports.testclasses.internal.SuperClass.InnerClassProtected", innerClass.getSuperclass().getTypeDeclaration().getReference().toString());
        org.junit.Assert.assertEquals("InnerClassProtected", innerClass.getSuperclass().getSimpleName());
        org.junit.Assert.assertEquals("SuperClass", innerClass.getSuperclass().getDeclaringType().getSimpleName());
        org.junit.Assert.assertEquals(spoon.getFactory().Class().get("spoon.test.imports.testclasses.internal.SuperClass$InnerClassProtected"), innerClass.getSuperclass().getDeclaration());
    }

    @org.junit.Test
    public void testImportOfAnInnerClassInASuperClassPackageFullQualified() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.getEnvironment().setShouldCompile(true);
        spoon.getEnvironment().setAutoImports(false);
        spoon.addInputResource("./src/test/java/spoon/test/imports/testclasses/internal/SuperClass.java");
        spoon.addInputResource("./src/test/java/spoon/test/imports/testclasses/internal/ChildClass.java");
        spoon.addInputResource("./src/test/java/spoon/test/imports/testclasses/ClientClass.java");
        spoon.setBinaryOutputDirectory("./target/spoon/super_imports/bin");
        spoon.setSourceOutputDirectory("./target/spoon/super_imports/src");
        spoon.run();
        final List<CtClass> classes = Query.getElements(spoon.getFactory(), new NamedElementFilter<>(CtClass.class, "ClientClass"));
        final CtType<?> innerClass = classes.get(0).getNestedType("InnerClass");
        org.junit.Assert.assertEquals("spoon.test.imports.testclasses.ClientClass$InnerClass", innerClass.getQualifiedName());
        String expected = "spoon.test.imports.testclasses.ClientClass.InnerClass";
        org.junit.Assert.assertEquals(expected, innerClass.getReference().toString());
        org.junit.Assert.assertEquals("spoon.test.imports.testclasses.internal.SuperClass$InnerClassProtected", innerClass.getSuperclass().getQualifiedName());
        expected = "spoon.test.imports.testclasses.internal.ChildClass.InnerClassProtected";
        org.junit.Assert.assertEquals(expected, innerClass.getSuperclass().toString());
        org.junit.Assert.assertEquals("SuperClass", innerClass.getSuperclass().getDeclaringType().getSimpleName());
        org.junit.Assert.assertEquals(spoon.getFactory().Class().get("spoon.test.imports.testclasses.internal.SuperClass$InnerClassProtected"), innerClass.getSuperclass().getDeclaration());
    }

    @org.junit.Test
    public void testImportOfAnInnerClassInASuperClassAvailableInLibrary() throws java.lang.Exception {
        SpoonModelBuilder comp = new Launcher().createCompiler();
        List<spoon.compiler.SpoonResource> fileToBeSpooned = SpoonResourceHelper.resources("./src/test/resources/visibility/YamlRepresenter.java");
        org.junit.Assert.assertEquals(1, fileToBeSpooned.size());
        comp.addInputSources(fileToBeSpooned);
        List<spoon.compiler.SpoonResource> classpath = SpoonResourceHelper.resources("./src/test/resources/visibility/snakeyaml-1.9.jar");
        org.junit.Assert.assertEquals(1, classpath.size());
        comp.setSourceClasspath(classpath.get(0).getPath());
        comp.build();
        Factory factory = comp.getFactory();
        CtType<?> theClass = factory.Type().get("visibility.YamlRepresenter");
        final CtClass<?> innerClass = theClass.getNestedType("RepresentConfigurationSection");
        String expected = "visibility.YamlRepresenter.RepresentConfigurationSection";
        org.junit.Assert.assertEquals(expected, innerClass.getReference().toString());
        expected = "org.yaml.snakeyaml.representer.Representer.RepresentMap";
        org.junit.Assert.assertEquals(expected, innerClass.getSuperclass().toString());
    }

    @org.junit.Test
    public void testImportOfAnInnerClassInAClassPackage() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{ "--output-type", "nooutput" });
        Factory factory = spoon.createFactory();
        SpoonModelBuilder compiler = spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/imports/testclasses/internal/PublicSuperClass.java", "./src/test/java/spoon/test/imports/testclasses/DefaultClientClass.java"));
        compiler.build();
        final CtClass<?> client = ((CtClass<?>) (factory.Type().get("spoon.test.imports.testclasses.DefaultClientClass")));
        final CtMethod<?> methodVisit = client.getMethodsByName("visit").get(0);
        final CtType<java.lang.Object> innerClass = factory.Type().get("spoon.test.imports.testclasses.DefaultClientClass$InnerClass");
        org.junit.Assert.assertEquals("Type of the method must to be InnerClass accessed via DefaultClientClass.", innerClass, methodVisit.getType().getDeclaration());
    }

    @org.junit.Test
    public void testNewInnerClassDefinesInItsClassAndSuperClass() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{ "--output-type", "nooutput" });
        Factory factory = spoon.createFactory();
        SpoonModelBuilder compiler = spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/imports/testclasses/SuperClass.java", "./src/test/java/spoon/test/imports/testclasses/SubClass.java"));
        compiler.build();
        final CtClass<?> subClass = ((CtClass<?>) (factory.Type().get(SubClass.class)));
        final CtConstructorCall<?> ctConstructorCall = subClass.getElements(new TypeFilter<CtConstructorCall<?>>(CtConstructorCall.class)).get(0);
        org.junit.Assert.assertEquals("new spoon.test.imports.testclasses.SubClass.Item(\"\")", ctConstructorCall.toString());
        final String expected = (((((((((((((((((("public class SubClass extends spoon.test.imports.testclasses.SuperClass {" + (java.lang.System.lineSeparator())) + "    public void aMethod() {") + (java.lang.System.lineSeparator())) + "        new spoon.test.imports.testclasses.SubClass.Item(\"\");") + (java.lang.System.lineSeparator())) + "    }") + (java.lang.System.lineSeparator())) + (java.lang.System.lineSeparator())) + "    public static class Item extends spoon.test.imports.testclasses.SuperClass.Item {") + (java.lang.System.lineSeparator())) + "        public Item(java.lang.String s) {") + (java.lang.System.lineSeparator())) + "            super(1, s);") + (java.lang.System.lineSeparator())) + "        }") + (java.lang.System.lineSeparator())) + "    }") + (java.lang.System.lineSeparator())) + "}";
        org.junit.Assert.assertEquals(expected, subClass.toString());
    }

    @org.junit.Test
    public void testMissingImport() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{ "--output-type", "nooutput" });
        Factory factory = spoon.createFactory();
        factory.getEnvironment().setNoClasspath(true);
        factory.getEnvironment().setLevel("OFF");
        SpoonModelBuilder compiler = spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/resources/import-resources/fr/inria/MissingImport.java"));
        compiler.build();
        CtTypeReference<?> type = factory.Class().getAll().get(0).getFields().get(0).getType();
        org.junit.Assert.assertEquals("Abcd", type.getSimpleName());
        org.junit.Assert.assertEquals("fr.inria.internal", type.getPackage().getSimpleName());
    }

    @org.junit.Test
    public void testAnotherMissingImport() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{ "--output-type", "nooutput" });
        Factory factory = spoon.createFactory();
        factory.getEnvironment().setNoClasspath(true);
        factory.getEnvironment().setLevel("OFF");
        SpoonModelBuilder compiler = spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/resources/import-resources/fr/inria/AnotherMissingImport.java"));
        compiler.build();
        List<CtMethod> methods = factory.getModel().getElements(new NamedElementFilter<>(CtMethod.class, "doSomething"));
        List<CtParameter> parameters = methods.get(0).getParameters();
        CtTypeReference<?> type = parameters.get(0).getType();
        org.junit.Assert.assertEquals("SomeType", type.getSimpleName());
        org.junit.Assert.assertEquals("externallib", type.getPackage().getSimpleName());
        CtMethod<?> mainMethod = factory.Class().getAll().get(0).getMethodsByName("main").get(0);
        List<spoon.reflect.code.CtStatement> statements = mainMethod.getBody().getStatements();
        spoon.reflect.code.CtStatement invocationStatement = statements.get(1);
        org.junit.Assert.assertTrue((invocationStatement instanceof CtInvocation));
        CtInvocation invocation = ((CtInvocation) (invocationStatement));
        CtExecutableReference executableReference = invocation.getExecutable();
        org.junit.Assert.assertEquals("doSomething(externallib.SomeType)", executableReference.getSignature());
        org.junit.Assert.assertSame(methods.get(0), executableReference.getDeclaration());
    }

    @org.junit.Test
    public void testSpoonWithImports() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.run(new String[]{ "-i", "./src/test/java/spoon/test/imports/testclasses", "--output-type", "nooutput", "--with-imports" });
        final CtClass<spoon.test.imports.ImportTest> aClass = launcher.getFactory().Class().get(spoon.test.imports.testclasses.internal.ChildClass.class);
        final CtClass<spoon.test.imports.ImportTest> anotherClass = launcher.getFactory().Class().get(spoon.test.imports.testclasses.ClientClass.class);
        final CtClass<spoon.test.imports.ImportTest> classWithInvocation = launcher.getFactory().Class().get(spoon.test.imports.testclasses.ClassWithInvocation.class);
        spoon.reflect.visitor.ImportScanner importScanner = new spoon.reflect.visitor.ImportScannerImpl();
        importScanner.computeImports(aClass);
        org.junit.Assert.assertEquals(2, importScanner.getAllImports().size());
        importScanner = new spoon.reflect.visitor.ImportScannerImpl();
        importScanner.computeImports(anotherClass);
        org.junit.Assert.assertEquals(2, importScanner.getAllImports().size());
        org.junit.Assert.assertTrue(((anotherClass.toString().indexOf("InnerClass extends ChildClass.InnerClassProtected")) > 0));
        importScanner = new spoon.reflect.visitor.ImportScannerImpl();
        importScanner.computeImports(classWithInvocation);
        org.junit.Assert.assertEquals("Spoon ignores the arguments of CtInvocations", 3, importScanner.getAllImports().size());
    }

    @org.junit.Test
    public void testStaticImportForInvocationInNoClasspath() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.run(new String[]{ "-i", "./src/test/resources/import-static", "--output-type", "nooutput", "--noclasspath" });
        final List<CtInvocation<?>> elements = new spoon.support.util.SortedList(new spoon.support.comparator.CtLineElementComparator());
        elements.addAll(Query.getElements(launcher.getFactory(), new TypeFilter<CtInvocation<?>>(CtInvocation.class) {
            @java.lang.Override
            public boolean matches(CtInvocation<?> element) {
                return (!(element.getExecutable().isConstructor())) && (super.matches(element));
            }
        }));
        assertCorrectInvocation(new spoon.test.imports.ImportTest.Expected().name("staticMethod").target("A").declaringType("A").typeIsNull(true), elements.get(0));
        assertCorrectInvocation(new spoon.test.imports.ImportTest.Expected().name("staticMethod").target("pack1.A").declaringType("A").typeIsNull(true), elements.get(1));
        assertCorrectInvocation(new spoon.test.imports.ImportTest.Expected().name("staticMethod").target("A").declaringType("A").typeIsNull(false), elements.get(2));
        assertCorrectInvocation(new spoon.test.imports.ImportTest.Expected().name("staticMethod").target("pack1.A").declaringType("A").typeIsNull(false), elements.get(3));
        assertCorrectInvocation(new spoon.test.imports.ImportTest.Expected().name("makeBurritos").target("Tacos.Burritos").declaringType("Burritos").typeIsNull(false), elements.get(4));
        assertCorrectInvocation(new spoon.test.imports.ImportTest.Expected().name("makeBurritos").target("Tacos.Burritos").declaringType("Burritos").typeIsNull(true), elements.get(5));
        assertCorrectInvocation(new spoon.test.imports.ImportTest.Expected().name("makeBurritos").target("Tacos.Burritos").declaringType("Burritos").typeIsNull(false), elements.get(6));
        assertCorrectInvocation(new spoon.test.imports.ImportTest.Expected().name("makeBurritos").target("Tacos.Burritos").declaringType("Burritos").typeIsNull(false), elements.get(7));
        assertCorrectInvocation(new spoon.test.imports.ImportTest.Expected().name("staticD").target("C.D").declaringType("D").typeIsNull(true), elements.get(8));
        assertCorrectInvocation(new spoon.test.imports.ImportTest.Expected().name("staticD").target("pack2.C.D").declaringType("D").typeIsNull(true), elements.get(9));
        assertCorrectInvocation(new spoon.test.imports.ImportTest.Expected().name("staticD").target("C.D").declaringType("D").typeIsNull(false), elements.get(10));
        assertCorrectInvocation(new spoon.test.imports.ImportTest.Expected().name("staticD").target("pack2.C.D").declaringType("D").typeIsNull(false), elements.get(11));
        assertCorrectInvocation(new spoon.test.imports.ImportTest.Expected().name("staticE").target("pack3.E.E").declaringType("E").typeIsNull(true), elements.get(12));
        assertCorrectInvocationWithLimit(new spoon.test.imports.ImportTest.Expected().name("staticE").typeIsNull(true), elements.get(13));
        assertCorrectInvocation(new spoon.test.imports.ImportTest.Expected().name("staticE").target("pack3.E.E").declaringType("E").typeIsNull(false), elements.get(14));
        assertCorrectInvocationWithLimit(new spoon.test.imports.ImportTest.Expected().name("staticE").typeIsNull(false), elements.get(15));
    }

    @org.junit.Test
    public void testImportOfInvocationOfPrivateClass() throws java.lang.Exception {
        java.lang.System.setProperty("line.separator", "\n");
        final Factory factory = getFactory("./src/test/java/spoon/test/imports/testclasses/internal2/Chimichanga.java", "./src/test/java/spoon/test/imports/testclasses/Mole.java");
        spoon.reflect.visitor.ImportScanner importContext = new spoon.reflect.visitor.ImportScannerImpl();
        importContext.computeImports(factory.Class().get(spoon.test.imports.testclasses.Mole.class));
        java.util.Collection<CtImport> imports = importContext.getAllImports();
        org.junit.Assert.assertEquals(1, imports.size());
        org.junit.Assert.assertEquals("import spoon.test.imports.testclasses.internal2.Chimichanga;\n", imports.toArray()[0].toString());
    }

    @org.junit.Test
    public void testNotImportExecutableType() throws java.lang.Exception {
        final Factory factory = getFactory("./src/test/java/spoon/test/imports/testclasses/internal3/Foo.java", "./src/test/java/spoon/test/imports/testclasses/internal3/Bar.java", "./src/test/java/spoon/test/imports/testclasses/NotImportExecutableType.java");
        spoon.reflect.visitor.ImportScanner importContext = new spoon.reflect.visitor.ImportScannerImpl();
        importContext.computeImports(factory.Class().get(spoon.test.imports.testclasses.NotImportExecutableType.class));
        java.util.Collection<CtImport> imports = importContext.getAllImports();
        org.junit.Assert.assertEquals(3, imports.size());
        java.util.Set<String> expectedImports = new java.util.HashSet<>(java.util.Arrays.asList("spoon.test.imports.testclasses.internal3.Foo", "java.io.File", "java.lang.Object"));
        java.util.Set<String> actualImports = imports.stream().map(CtImport::getReference).map(spoon.reflect.reference.CtReference::toString).collect(java.util.stream.Collectors.toSet());
        org.junit.Assert.assertEquals(expectedImports, actualImports);
    }

    @org.junit.Test
    public void testImportOfInvocationOfStaticMethod() throws java.lang.Exception {
        java.lang.System.setProperty("line.separator", "\n");
        final Factory factory = getFactory("./src/test/java/spoon/test/imports/testclasses/internal2/Menudo.java", "./src/test/java/spoon/test/imports/testclasses/Pozole.java");
        spoon.reflect.visitor.ImportScanner importContext = new spoon.reflect.visitor.ImportScannerImpl();
        importContext.computeImports(factory.Class().get(spoon.test.imports.testclasses.Pozole.class));
        java.util.Collection<CtImport> imports = importContext.getAllImports();
        org.junit.Assert.assertEquals(1, imports.size());
        org.junit.Assert.assertEquals("import spoon.test.imports.testclasses.internal2.Menudo;\n", imports.toArray()[0].toString());
    }

    @org.junit.Test
    public void testImportStaticAndFieldAccess() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/imports/testclasses/internal4/");
        launcher.addInputResource("./src/test/java/spoon/test/imports/testclasses/Tacos.java");
        launcher.buildModel();
        final CtType<java.lang.Object> aTacos = launcher.getFactory().Type().get(spoon.test.imports.testclasses.Tacos.class);
        final spoon.reflect.code.CtStatement assignment = aTacos.getMethod("m").getBody().getStatement(0);
        org.junit.Assert.assertTrue((assignment instanceof spoon.reflect.code.CtLocalVariable));
        org.junit.Assert.assertEquals("spoon.test.imports.testclasses.internal4.Constants.CONSTANT.foo", ((spoon.reflect.code.CtLocalVariable) (assignment)).getAssignment().toString());
    }

    @org.junit.Test
    public void testImportStaticAndFieldAccessWithImport() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "--output-type", "nooutput", "--with-imports" });
        launcher.addInputResource("./src/test/java/spoon/test/imports/testclasses/internal4/");
        launcher.addInputResource("./src/test/java/spoon/test/imports/testclasses/Tacos.java");
        launcher.buildModel();
        final CtType<java.lang.Object> aTacos = launcher.getFactory().Type().get(spoon.test.imports.testclasses.Tacos.class);
        final spoon.reflect.code.CtStatement assignment = aTacos.getMethod("m").getBody().getStatement(0);
        org.junit.Assert.assertTrue((assignment instanceof spoon.reflect.code.CtLocalVariable));
        org.junit.Assert.assertEquals("Constants.CONSTANT.foo", ((spoon.reflect.code.CtLocalVariable) (assignment)).getAssignment().toString());
    }

    @org.junit.Test
    public void testFullQualifiedNameImport() throws java.lang.Exception {
        String newLine = java.lang.System.getProperty("line.separator");
        Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.imports.testclasses.A.class);
        factory.getEnvironment().setAutoImports(true);
        CtClass<java.lang.Object> aClass = factory.Class().get(spoon.test.imports.testclasses.A.class);
        org.junit.Assert.assertEquals((((("public class A {" + newLine) + "    public class ArrayList extends java.util.ArrayList {}") + newLine) + "}"), aClass.toString());
    }

    @org.junit.Test
    public void testAccessToNestedClass() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "-i", "./src/test/java/spoon/test/imports/testclasses", "--with-imports" });
        launcher.buildModel();
        final CtClass<spoon.test.imports.ImportTest> aClass = launcher.getFactory().Class().get(((spoon.test.imports.testclasses.ClientClass.class.getName()) + "$InnerClass"));
        org.junit.Assert.assertEquals(((spoon.test.imports.testclasses.ClientClass.class.getName()) + "$InnerClass"), aClass.getQualifiedName());
        final CtTypeReference<?> parentClass = aClass.getSuperclass();
        org.junit.Assert.assertEquals("spoon.test.imports.testclasses.internal.SuperClass$InnerClassProtected", parentClass.getQualifiedName());
        java.lang.Class<?> actualClass = parentClass.getActualClass();
        org.junit.Assert.assertEquals("spoon.test.imports.testclasses.internal.SuperClass$InnerClassProtected", actualClass.getName());
    }

    @org.junit.Test
    public void testAccessType() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "-i", "./src/test/java/spoon/test/imports/testclasses", "--with-imports" });
        launcher.buildModel();
        final CtClass<spoon.test.imports.ImportTest> aInnerClass = launcher.getFactory().Class().get(((spoon.test.imports.testclasses.ClientClass.class.getName()) + "$InnerClass"));
        final CtClass<spoon.test.imports.ImportTest> aSuperClass = launcher.getFactory().Class().get("spoon.test.imports.testclasses.internal.SuperClass");
        org.junit.Assert.assertEquals(((spoon.test.imports.testclasses.ClientClass.class.getName()) + "$InnerClass"), aInnerClass.getQualifiedName());
        org.junit.Assert.assertEquals(spoon.test.imports.testclasses.ClientClass.class.getName(), aInnerClass.getReference().getAccessType().getQualifiedName());
        final CtTypeReference<?> innerClassProtectedByGetSuperClass = aInnerClass.getSuperclass();
        final CtTypeReference<?> innerClassProtectedByQualifiedName = launcher.getFactory().Class().get("spoon.test.imports.testclasses.internal.SuperClass$InnerClassProtected").getReference();
        org.junit.Assert.assertEquals("spoon.test.imports.testclasses.internal.SuperClass$InnerClassProtected", innerClassProtectedByGetSuperClass.getQualifiedName());
        org.junit.Assert.assertEquals("spoon.test.imports.testclasses.internal.SuperClass$InnerClassProtected", innerClassProtectedByQualifiedName.getQualifiedName());
        org.junit.Assert.assertEquals("spoon.test.imports.testclasses.internal.ChildClass", innerClassProtectedByGetSuperClass.getAccessType().getQualifiedName());
        org.junit.Assert.assertEquals("spoon.test.imports.testclasses.internal.SuperClass", innerClassProtectedByQualifiedName.getAccessType().getQualifiedName());
        org.junit.Assert.assertEquals("spoon.test.imports.testclasses.internal.ChildClass.InnerClassProtected", innerClassProtectedByGetSuperClass.toString());
        org.junit.Assert.assertEquals("spoon.test.imports.testclasses.internal.SuperClass.InnerClassProtected", innerClassProtectedByQualifiedName.toString());
    }

    @org.junit.Test
    public void testCanAccess() throws java.lang.Exception {
        class Checker {
            final Launcher launcher;

            final CtTypeReference<?> aClientClass;

            final CtTypeReference<?> anotherClass;

            Checker() {
                launcher = new Launcher();
                launcher.setArgs(new String[]{ "-i", "./src/test/java/spoon/test/imports/testclasses", "--with-imports" });
                launcher.buildModel();
                aClientClass = launcher.getFactory().Class().get(spoon.test.imports.testclasses.ClientClass.class).getReference();
                anotherClass = launcher.getFactory().Class().get(spoon.test.imports.testclasses.Tacos.class).getReference();
            }

            void checkCanAccess(String aClassName, boolean isInterface, boolean canAccessClientClass, boolean canAccessAnotherClass, String clientAccessType, String anotherAccessType) {
                CtTypeReference<?> target;
                if (isInterface) {
                    target = launcher.getFactory().Interface().create(aClassName).getReference();
                }else {
                    target = launcher.getFactory().Class().get(aClassName).getReference();
                }
                boolean isNested = (target.getDeclaringType()) != null;
                CtTypeReference<?> accessType;
                target.setParent(aClientClass.getTypeDeclaration());
                if (canAccessClientClass) {
                    org.junit.Assert.assertTrue((("ClientClass should have access to " + aClassName) + " but it has not"), aClientClass.canAccess(target));
                }else {
                    org.junit.Assert.assertFalse((("ClientClass should have NO access to " + aClassName) + " but it has"), aClientClass.canAccess(target));
                }
                if (isNested) {
                    accessType = target.getAccessType();
                    if (clientAccessType != null) {
                        org.junit.Assert.assertEquals(clientAccessType, accessType.getQualifiedName());
                    }else
                        if (accessType != null) {
                            org.junit.Assert.fail(((("ClientClass should have NO accessType to " + aClassName) + " but it has ") + (accessType.getQualifiedName())));
                        }

                }
                target.setParent(anotherClass.getTypeDeclaration());
                if (canAccessAnotherClass) {
                    org.junit.Assert.assertTrue((("Tacos class should have access to " + aClassName) + " but it has not"), anotherClass.canAccess(target));
                }else {
                    org.junit.Assert.assertFalse((("Tacos class should have NO access to " + aClassName) + " but it has"), anotherClass.canAccess(target));
                }
                if (isNested) {
                    if (anotherAccessType != null) {
                        accessType = target.getAccessType();
                        org.junit.Assert.assertEquals(anotherAccessType, accessType.getQualifiedName());
                    }else {
                        try {
                            accessType = target.getAccessType();
                        } catch (spoon.SpoonException e) {
                            if ((e.getMessage().indexOf("Cannot compute access path to type: ")) == (-1)) {
                                throw e;
                            }
                            accessType = null;
                        }
                        if (accessType != null) {
                            org.junit.Assert.fail(((("Tacos class should have NO accessType to " + aClassName) + " but it has ") + (accessType.getQualifiedName())));
                        }
                    }
                }
            }
        }
        Checker c = new Checker();
        c.checkCanAccess("spoon.test.imports.testclasses.ClientClass", false, true, true, null, null);
        c.checkCanAccess("spoon.test.imports.testclasses.ClientClass$InnerClass", false, true, false, "spoon.test.imports.testclasses.ClientClass", "spoon.test.imports.testclasses.ClientClass");
        c.checkCanAccess("spoon.test.imports.testclasses.internal.ChildClass", false, true, true, null, null);
        c.checkCanAccess("spoon.test.imports.testclasses.internal.PublicInterface2", true, true, true, null, null);
        c.checkCanAccess("spoon.test.imports.testclasses.internal.PublicInterface2$NestedInterface", true, true, true, "spoon.test.imports.testclasses.internal.PublicInterface2", "spoon.test.imports.testclasses.internal.PublicInterface2");
        c.checkCanAccess("spoon.test.imports.testclasses.internal.PublicInterface2$NestedClass", true, true, true, "spoon.test.imports.testclasses.internal.PublicInterface2", "spoon.test.imports.testclasses.internal.PublicInterface2");
        c.checkCanAccess("spoon.test.imports.testclasses.internal.SuperClass$PublicInterface", true, true, true, "spoon.test.imports.testclasses.internal.ChildClass", null);
        c.checkCanAccess("spoon.test.imports.testclasses.internal.SuperClass$PackageProtectedInterface", true, false, false, "spoon.test.imports.testclasses.internal.ChildClass", null);
        c.checkCanAccess("spoon.test.imports.testclasses.internal.SuperClass$ProtectedInterface", true, true, false, "spoon.test.imports.testclasses.internal.ChildClass", null);
        c.checkCanAccess("spoon.test.imports.testclasses.internal.SuperClass$ProtectedInterface$NestedOfProtectedInterface", true, true, true, "spoon.test.imports.testclasses.internal.SuperClass$ProtectedInterface", null);
        c.checkCanAccess("spoon.test.imports.testclasses.internal.SuperClass$ProtectedInterface$NestedPublicInterface", true, true, true, "spoon.test.imports.testclasses.internal.SuperClass$ProtectedInterface", null);
        c.checkCanAccess("spoon.test.imports.testclasses.internal.SuperClass$PublicInterface", true, true, true, "spoon.test.imports.testclasses.internal.ChildClass", null);
        c.checkCanAccess("spoon.test.imports.testclasses.internal.SuperClass$PublicInterface$NestedOfPublicInterface", true, true, true, "spoon.test.imports.testclasses.internal.SuperClass$PublicInterface", "spoon.test.imports.testclasses.internal.SuperClass$PublicInterface");
        c.checkCanAccess("spoon.test.imports.testclasses.internal.SuperClass$PublicInterface$NestedPublicInterface", true, true, true, "spoon.test.imports.testclasses.internal.SuperClass$PublicInterface", "spoon.test.imports.testclasses.internal.SuperClass$PublicInterface");
    }

    @org.junit.Test
    public void testNestedAccessPathWithTypedParameter() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "-i", "./src/test/resources/spoon/test/imports/testclasses2/AbstractMapBasedMultimap.java" });
        launcher.buildModel();
        launcher.prettyprint();
        try {
            launcher.getModelBuilder().compile();
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
        CtClass<?> mm = launcher.getFactory().Class().get("spoon.test.imports.testclasses2.AbstractMapBasedMultimap");
        CtClass<?> mmwli = launcher.getFactory().Class().get("spoon.test.imports.testclasses2.AbstractMapBasedMultimap$WrappedList$WrappedListIterator");
        org.junit.Assert.assertEquals("private class WrappedListIterator extends spoon.test.imports.testclasses2.AbstractMapBasedMultimap<K, V>.WrappedCollection.WrappedIterator {}", mmwli.toString());
        org.junit.Assert.assertTrue(((mm.toString().indexOf("AbstractMapBasedMultimap<K, V>.WrappedCollection.WrappedIterator")) >= 0));
        CtClass<?> mmwliother = launcher.getFactory().Class().get("spoon.test.imports.testclasses2.AbstractMapBasedMultimap$OtherWrappedList$WrappedListIterator");
        org.junit.Assert.assertEquals("private class WrappedListIterator extends spoon.test.imports.testclasses2.AbstractMapBasedMultimap<K, V>.OtherWrappedList.WrappedIterator {}", mmwliother.toString());
    }

    @org.junit.Test
    public void testNestedAccessPathWithTypedParameterWithImports() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "-i", "./src/test/resources/spoon/test/imports/testclasses2/AbstractMapBasedMultimap.java", "--with-imports" });
        launcher.buildModel();
        launcher.prettyprint();
        try {
            launcher.getModelBuilder().compile();
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
        CtClass<?> mm = launcher.getFactory().Class().get("spoon.test.imports.testclasses2.AbstractMapBasedMultimap");
        CtClass<?> mmwli = launcher.getFactory().Class().get("spoon.test.imports.testclasses2.AbstractMapBasedMultimap$WrappedList$WrappedListIterator");
        org.junit.Assert.assertEquals("private class WrappedListIterator extends AbstractMapBasedMultimap<K, V>.WrappedCollection.WrappedIterator {}", mmwli.toString());
        org.junit.Assert.assertTrue(((mm.toString().indexOf("AbstractMapBasedMultimap<K, V>.WrappedCollection.WrappedIterator")) >= 0));
        CtClass<?> mmwliother = launcher.getFactory().Class().get("spoon.test.imports.testclasses2.AbstractMapBasedMultimap$OtherWrappedList$WrappedListIterator");
        org.junit.Assert.assertEquals("private class WrappedListIterator extends AbstractMapBasedMultimap<K, V>.OtherWrappedList.WrappedIterator {}", mmwliother.toString());
    }

    @org.junit.Test
    public void testNestedStaticPathWithTypedParameter() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "-i", "./src/test/resources/spoon/test/imports/testclasses2/Interners.java" });
        launcher.buildModel();
        launcher.prettyprint();
        try {
            launcher.getModelBuilder().compile();
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
        CtClass<?> mm = launcher.getFactory().Class().get("spoon.test.imports.testclasses2.Interners");
        org.junit.Assert.assertTrue(((mm.toString().indexOf("java.util.List<spoon.test.imports.testclasses2.Interners.WeakInterner.Dummy> list;")) >= 0));
    }

    @org.junit.Test
    public void testNestedStaticPathWithTypedParameterWithImports() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "-i", "./src/test/resources/spoon/test/imports/testclasses2/Interners.java", "--with-imports" });
        launcher.buildModel();
        launcher.prettyprint();
        try {
            launcher.getModelBuilder().compile();
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
        CtClass<?> mm = launcher.getFactory().Class().get("spoon.test.imports.testclasses2.Interners");
        org.junit.Assert.assertTrue(((mm.toString().indexOf("List<Interners.WeakInterner.Dummy> list;")) >= 0));
    }

    @org.junit.Test
    public void testDeepNestedStaticPathWithTypedParameter() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "-i", "./src/test/resources/spoon/test/imports/testclasses2/StaticWithNested.java" });
        launcher.buildModel();
        launcher.prettyprint();
        try {
            launcher.getModelBuilder().compile();
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
        CtClass<?> mm = launcher.getFactory().Class().get("spoon.test.imports.testclasses2.StaticWithNested");
        org.junit.Assert.assertTrue("new spoon.test.imports.testclasses2.StaticWithNested.StaticNested.StaticNested2<K>();", ((mm.toString().indexOf("new spoon.test.imports.testclasses2.StaticWithNested.StaticNested.StaticNested2<K>();")) >= 0));
    }

    @org.junit.Test
    public void testDeepNestedStaticPathWithTypedParameterWithImports() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "-i", "./src/test/resources/spoon/test/imports/testclasses2/StaticWithNested.java", "--with-imports" });
        launcher.buildModel();
        launcher.prettyprint();
        try {
            launcher.getModelBuilder().compile();
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
        CtClass<?> mm = launcher.getFactory().Class().get("spoon.test.imports.testclasses2.StaticWithNested");
        org.junit.Assert.assertTrue("new StaticWithNested.StaticNested.StaticNested2<K>();", ((mm.toString().indexOf("new StaticWithNested.StaticNested.StaticNested2<K>();")) >= 0));
    }

    private Factory getFactory(String... inputs) {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "--output-type", "nooutput" });
        for (String input : inputs) {
            launcher.addInputResource(input);
        }
        launcher.run();
        return launcher.getFactory();
    }

    private void assertCorrectInvocation(spoon.test.imports.ImportTest.Expected expected, CtInvocation<?> ctInvocation) {
        org.junit.Assert.assertEquals(1, ctInvocation.getArguments().size());
        org.junit.Assert.assertNotNull(ctInvocation.getTarget());
        org.junit.Assert.assertTrue(((ctInvocation.getTarget()) instanceof spoon.reflect.code.CtTypeAccess));
        org.junit.Assert.assertEquals(expected.target, ctInvocation.getTarget().toString());
        org.junit.Assert.assertNotNull(ctInvocation.getExecutable());
        org.junit.Assert.assertEquals(expected.name, ctInvocation.getExecutable().getSimpleName());
        org.junit.Assert.assertNotNull(ctInvocation.getExecutable().getDeclaringType());
        org.junit.Assert.assertEquals(expected.declaringType, ctInvocation.getExecutable().getDeclaringType().getSimpleName());
        org.junit.Assert.assertEquals(expected.isNull, ((ctInvocation.getExecutable().getType()) == null));
        org.junit.Assert.assertEquals(1, ctInvocation.getExecutable().getParameters().size());
    }

    private void assertCorrectInvocationWithLimit(spoon.test.imports.ImportTest.Expected expected, CtInvocation<?> ctInvocation) {
        org.junit.Assert.assertEquals(1, ctInvocation.getArguments().size());
        org.junit.Assert.assertTrue(((ctInvocation.getTarget()) instanceof spoon.reflect.code.CtThisAccess));
        org.junit.Assert.assertNotNull(ctInvocation.getExecutable());
        org.junit.Assert.assertEquals(expected.name, ctInvocation.getExecutable().getSimpleName());
        org.junit.Assert.assertNull(ctInvocation.getExecutable().getDeclaringType());
        org.junit.Assert.assertEquals(expected.isNull, ((ctInvocation.getExecutable().getType()) == null));
        org.junit.Assert.assertEquals(1, ctInvocation.getExecutable().getParameters().size());
    }

    private class Expected {
        String name;

        String target;

        String declaringType;

        boolean isNull;

        public spoon.test.imports.ImportTest.Expected name(String name) {
            this.name = name;
            return this;
        }

        public spoon.test.imports.ImportTest.Expected target(String target) {
            this.target = target;
            return this;
        }

        public spoon.test.imports.ImportTest.Expected declaringType(String declaringType) {
            this.declaringType = declaringType;
            return this;
        }

        public spoon.test.imports.ImportTest.Expected typeIsNull(boolean isNull) {
            this.isNull = isNull;
            return this;
        }
    }

    @org.junit.Test
    public void testWithInnerEnumDoesNotImportStaticInnerMethods() {
        final Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        String outputDir = "./target/spooned-innerenum";
        launcher.addInputResource("./src/test/java/spoon/test/imports/testclasses/StaticImportsFromEnum.java");
        launcher.setSourceOutputDirectory(outputDir);
        launcher.run();
        spoon.reflect.visitor.PrettyPrinter prettyPrinter = launcher.createPrettyPrinter();
        CtType element = launcher.getFactory().Class().getAll().get(0);
        List<CtType<?>> toPrint = new java.util.ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String output = prettyPrinter.getResult();
        org.junit.Assert.assertTrue("The file should not contain a static import to the inner enum method values", (!(output.contains("import static spoon.test.imports.testclasses.StaticImportsFromEnum$DataElement.values;"))));
        org.junit.Assert.assertTrue("The file should not contain a static import to the inner enum method values of a distinct interface", (!(output.contains("import static spoon.test.imports.testclasses.ItfWithEnum$Bar.values;"))));
        org.junit.Assert.assertTrue("The file should not contain a static import to the inner enum value", (!(output.contains("import static spoon.test.imports.testclasses.ItfWithEnum$Bar.Lip;"))));
        spoon.testing.utils.ModelUtils.canBeBuilt(outputDir, 7);
    }

    @org.junit.Test
    public void testShouldNotCreateAutoreference() {
        final Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(false);
        String outputDir = "./target/spooned-autoref";
        launcher.addInputResource("./src/test/java/spoon/test/imports/testclasses/ShouldNotAutoreference.java");
        launcher.setSourceOutputDirectory(outputDir);
        launcher.run();
        spoon.reflect.visitor.PrettyPrinter prettyPrinter = launcher.createPrettyPrinter();
        CtType element = launcher.getFactory().Class().getAll().get(0);
        List<CtType<?>> toPrint = new java.util.ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String output = prettyPrinter.getResult();
        org.junit.Assert.assertTrue("The file should not contain a static import for NOFOLLOW_LINKS", (!(output.contains("import static java.nio.file.LinkOption.NOFOLLOW_LINKS;"))));
        spoon.testing.utils.ModelUtils.canBeBuilt(outputDir, 7);
    }

    @org.junit.Test
    public void testAccessPath() {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/imports/testclasses/TransportIndicesShardStoresAction.java");
        String outputDir = "./target/spooned-accessPath";
        launcher.setSourceOutputDirectory(outputDir);
        launcher.run();
        CtType element = launcher.getFactory().Class().getAll().get(0);
        spoon.reflect.visitor.PrettyPrinter prettyPrinter = launcher.createPrettyPrinter();
        List<CtType<?>> toPrint = new java.util.ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String output = prettyPrinter.getResult();
        spoon.testing.utils.ModelUtils.canBeBuilt(outputDir, 7);
    }

    @org.junit.Test
    public void testSuperInheritanceHierarchyFunction() throws java.lang.Exception {
        CtType<?> clientClass = ((CtClass<?>) (spoon.testing.utils.ModelUtils.buildClass(spoon.test.imports.testclasses.ClientClass.class)));
        CtTypeReference<?> childClass = clientClass.getSuperclass();
        CtTypeReference<?> superClass = childClass.getSuperclass();
        List<String> result = clientClass.map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingSelf(true)).map(( e) -> {
            org.junit.Assert.assertTrue((e instanceof CtType));
            return ((CtType) (e)).getQualifiedName();
        }).list();
        org.junit.Assert.assertTrue(result.contains(clientClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(childClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(superClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(java.lang.Object.class.getName()));
        result = clientClass.map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingSelf(false)).map(( e) -> {
            org.junit.Assert.assertTrue((e instanceof CtType));
            return ((CtType) (e)).getQualifiedName();
        }).list();
        org.junit.Assert.assertFalse(result.contains(clientClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(childClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(superClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(java.lang.Object.class.getName()));
        result = clientClass.map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingSelf(true).returnTypeReferences(true)).map(( e) -> {
            org.junit.Assert.assertTrue((e instanceof CtTypeReference));
            return ((CtTypeReference) (e)).getQualifiedName();
        }).list();
        org.junit.Assert.assertTrue(result.contains(clientClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(childClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(superClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(java.lang.Object.class.getName()));
        result = clientClass.getReference().map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingSelf(true).returnTypeReferences(true)).map(( e) -> {
            org.junit.Assert.assertTrue((e instanceof CtTypeReference));
            return ((CtTypeReference) (e)).getQualifiedName();
        }).list();
        org.junit.Assert.assertTrue(result.contains(clientClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(childClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(superClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(java.lang.Object.class.getName()));
        List<CtTypeReference<?>> typeResult = clientClass.getFactory().Type().OBJECT.map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingSelf(false).returnTypeReferences(true)).list();
        org.junit.Assert.assertEquals(0, typeResult.size());
        typeResult = clientClass.getFactory().Type().OBJECT.map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingSelf(true).returnTypeReferences(true)).list();
        org.junit.Assert.assertEquals(1, typeResult.size());
        org.junit.Assert.assertEquals(clientClass.getFactory().Type().OBJECT, typeResult.get(0));
    }

    @org.junit.Test
    public void testSuperInheritanceHierarchyFunctionListener() throws java.lang.Exception {
        CtType<?> clientClass = ((CtClass<?>) (spoon.testing.utils.ModelUtils.buildClass(spoon.test.imports.testclasses.ClientClass.class)));
        CtTypeReference<?> childClass = clientClass.getSuperclass();
        CtTypeReference<?> superClass = childClass.getSuperclass();
        List<String> result = clientClass.map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingSelf(true).setListener(new spoon.reflect.visitor.chain.CtScannerListener() {
            @java.lang.Override
            public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.declaration.CtElement element) {
                org.junit.Assert.assertTrue((element instanceof CtTypeReference));
                return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
            }

            @java.lang.Override
            public void exit(spoon.reflect.declaration.CtElement element) {
                org.junit.Assert.assertTrue((element instanceof CtTypeReference));
            }
        })).map(( e) -> {
            org.junit.Assert.assertTrue((e instanceof CtType));
            return ((CtType) (e)).getQualifiedName();
        }).list();
        org.junit.Assert.assertTrue(result.contains(clientClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(childClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(superClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(java.lang.Object.class.getName()));
        result = clientClass.map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingSelf(true).setListener(new spoon.reflect.visitor.chain.CtScannerListener() {
            @java.lang.Override
            public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.declaration.CtElement element) {
                org.junit.Assert.assertTrue((element instanceof CtTypeReference));
                if (superClass.getQualifiedName().equals(((CtTypeReference<?>) (element)).getQualifiedName())) {
                    return spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL;
                }
                return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
            }

            @java.lang.Override
            public void exit(spoon.reflect.declaration.CtElement element) {
                org.junit.Assert.assertTrue((element instanceof CtTypeReference));
            }
        })).map(( e) -> {
            org.junit.Assert.assertTrue((e instanceof CtType));
            return ((CtType) (e)).getQualifiedName();
        }).list();
        org.junit.Assert.assertTrue(result.contains(clientClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(childClass.getQualifiedName()));
        org.junit.Assert.assertFalse(result.contains(superClass.getQualifiedName()));
        org.junit.Assert.assertFalse(result.contains(java.lang.Object.class.getName()));
        result = clientClass.map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingSelf(true).setListener(new spoon.reflect.visitor.chain.CtScannerListener() {
            @java.lang.Override
            public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.declaration.CtElement element) {
                org.junit.Assert.assertTrue((element instanceof CtTypeReference));
                if (superClass.getQualifiedName().equals(((CtTypeReference<?>) (element)).getQualifiedName())) {
                    return spoon.reflect.visitor.chain.ScanningMode.SKIP_CHILDREN;
                }
                return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
            }

            @java.lang.Override
            public void exit(spoon.reflect.declaration.CtElement element) {
                org.junit.Assert.assertTrue((element instanceof CtTypeReference));
            }
        })).map(( e) -> {
            org.junit.Assert.assertTrue((e instanceof CtType));
            return ((CtType) (e)).getQualifiedName();
        }).list();
        org.junit.Assert.assertTrue(result.contains(clientClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(childClass.getQualifiedName()));
        org.junit.Assert.assertTrue(result.contains(superClass.getQualifiedName()));
        org.junit.Assert.assertFalse(result.contains(java.lang.Object.class.getName()));
    }

    @org.junit.Test
    public void testSuperInheritanceHierarchyFunctionNoClasspath() {
        final Launcher launcher = new Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("src/test/resources/noclasspath/superclass/UnknownSuperClass.java");
        launcher.buildModel();
        final spoon.reflect.CtModel model = launcher.getModel();
        CtClass<?> classUSC = launcher.getFactory().Class().get("UnknownSuperClass");
        List<CtType> types = classUSC.map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingSelf(true)).list();
        org.junit.Assert.assertEquals(2, types.size());
        org.junit.Assert.assertEquals("UnknownSuperClass", types.get(0).getQualifiedName());
        org.junit.Assert.assertEquals("java.lang.Object", types.get(1).getQualifiedName());
        List<CtTypeReference> typeRefs = classUSC.map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingSelf(true).returnTypeReferences(true)).list();
        org.junit.Assert.assertEquals(3, typeRefs.size());
        org.junit.Assert.assertEquals("UnknownSuperClass", typeRefs.get(0).getQualifiedName());
        org.junit.Assert.assertEquals("NotInClasspath", typeRefs.get(1).getQualifiedName());
        org.junit.Assert.assertEquals("java.lang.Object", typeRefs.get(2).getQualifiedName());
        typeRefs = classUSC.getSuperclass().map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingSelf(true).returnTypeReferences(true)).list();
        org.junit.Assert.assertEquals(1, typeRefs.size());
        org.junit.Assert.assertEquals("NotInClasspath", typeRefs.get(0).getQualifiedName());
        types = classUSC.getSuperclass().map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction().includingSelf(true)).list();
        org.junit.Assert.assertEquals(0, types.size());
    }

    @org.junit.Test
    public void testJavaLangIsConsideredAsImported() {
        final Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(false);
        String outputDir = "./target/spooned-javalang";
        launcher.addInputResource("./src/test/resources/spoon/test/imports/testclasses2/JavaLangConflict.java");
        launcher.setSourceOutputDirectory(outputDir);
        launcher.run();
        spoon.testing.utils.ModelUtils.canBeBuilt(outputDir, 7);
    }

    @org.junit.Test
    public void testJavaLangIsConsideredAsImportedButNotForSubPackages() {
        final Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        String outputDir = "./target/spooned-javalang-sub";
        launcher.addInputResource("./src/test/java/spoon/test/imports/testclasses/Reflection.java");
        launcher.setSourceOutputDirectory(outputDir);
        launcher.run();
        spoon.testing.utils.ModelUtils.canBeBuilt(outputDir, 7);
    }

    @org.junit.Test
    public void testmportInCu() throws java.lang.Exception {
        String[] options = new String[]{ "--output-type", "compilationunits", "--output", "target/testmportInCu", "--with-imports" };
        String path = "spoon/test/prettyprinter/testclasses/A.java";
        final Launcher launcher = new Launcher();
        launcher.setArgs(options);
        launcher.addInputResource(("./src/test/java/" + path));
        launcher.run();
        java.io.File output = new java.io.File(("target/testmportInCu/" + path));
        String code = org.apache.commons.io.IOUtils.toString(new java.io.FileReader(output));
        org.junit.Assert.assertTrue(code.contains("import java.util.ArrayList"));
        org.junit.Assert.assertFalse(code.contains("new java.util.ArrayList"));
        org.junit.Assert.assertTrue(code.contains("ArrayList<String> list = new ArrayList<>()"));
        output.delete();
    }

    @org.junit.Test
    public void testMultipleCU() throws java.io.IOException {
        final Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        String outputDir = "./target/spooned-multiplecu";
        launcher.addInputResource("./src/test/java/spoon/test/imports/testclasses/multiplecu/");
        launcher.setSourceOutputDirectory(outputDir);
        launcher.run();
        spoon.testing.utils.ModelUtils.canBeBuilt(outputDir, 7);
        String pathA = "spoon/test/imports/testclasses/multiplecu/A.java";
        String pathB = "spoon/test/imports/testclasses/multiplecu/B.java";
        java.io.File outputA = new java.io.File(((outputDir + "/") + pathA));
        String codeA = org.apache.commons.io.IOUtils.toString(new java.io.FileReader(outputA));
        org.junit.Assert.assertThat(codeA, org.hamcrest.CoreMatchers.containsString("import java.util.List;"));
        java.io.File outputB = new java.io.File(((outputDir + "/") + pathB));
        String codeB = org.apache.commons.io.IOUtils.toString(new java.io.FileReader(outputB));
        org.junit.Assert.assertThat(codeB, org.hamcrest.CoreMatchers.containsString("import java.awt.List;"));
    }

    @org.junit.Test
    public void testStaticMethodWithDifferentClassSameNameJava7NoCollision() {
        final Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        String outputDir = "./target/spooned-staticmethod";
        launcher.addInputResource("./src/test/resources/spoon/test/imports/testclasses2/apachetestsuite/staticmethod/");
        launcher.addInputResource("./src/test/resources/spoon/test/imports/testclasses2/apachetestsuite/enums/");
        launcher.addInputResource("./src/test/resources/spoon/test/imports/testclasses2/apachetestsuite/enum2/");
        launcher.addInputResource("./src/test/resources/spoon/test/imports/testclasses2/apachetestsuite/LangTestSuite.java");
        launcher.setSourceOutputDirectory(outputDir);
        launcher.getEnvironment().setComplianceLevel(7);
        launcher.run();
        spoon.reflect.visitor.PrettyPrinter prettyPrinter = launcher.createPrettyPrinter();
        CtType element = launcher.getFactory().Class().get("spoon.test.imports.testclasses2.apachetestsuite.staticmethod.AllLangTestSuiteStaticMethod");
        List<CtType<?>> toPrint = new java.util.ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String output = prettyPrinter.getResult();
        org.junit.Assert.assertTrue("The file should contain a static import ", output.contains("import static spoon.test.imports.testclasses2.apachetestsuite.enums.EnumTestSuite.suite;"));
        org.junit.Assert.assertTrue("The call to the last EnumTestSuite should be in FQN", output.contains("suite.addTest(suite());"));
        spoon.testing.utils.ModelUtils.canBeBuilt(outputDir, 7);
    }

    @org.junit.Test
    public void testStaticMethodWithDifferentClassSameNameJava3NoCollision() {
        final Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        String outputDir = "./target/spooned-staticjava3";
        launcher.addInputResource("./src/test/resources/spoon/test/imports/testclasses2/apachetestsuite/staticjava3/");
        launcher.addInputResource("./src/test/resources/spoon/test/imports/testclasses2/apachetestsuite/enums/");
        launcher.addInputResource("./src/test/resources/spoon/test/imports/testclasses2/apachetestsuite/enum2/");
        launcher.addInputResource("./src/test/resources/spoon/test/imports/testclasses2/apachetestsuite/LangTestSuite.java");
        launcher.setSourceOutputDirectory(outputDir);
        launcher.getEnvironment().setComplianceLevel(3);
        launcher.run();
        spoon.reflect.visitor.PrettyPrinter prettyPrinter = launcher.createPrettyPrinter();
        CtType element = launcher.getFactory().Class().get("spoon.test.imports.testclasses2.apachetestsuite.staticjava3.AllLangTestJava3");
        List<CtType<?>> toPrint = new java.util.ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String output = prettyPrinter.getResult();
        org.junit.Assert.assertFalse("The file should not contain a static import ", output.contains("import static"));
        org.junit.Assert.assertTrue("The call to the last EnumTestSuite should be in FQN", output.contains("suite.addTest(spoon.test.imports.testclasses2.apachetestsuite.enums.EnumTestSuite.suite());"));
        spoon.testing.utils.ModelUtils.canBeBuilt(outputDir, 3);
    }

    @org.junit.Test
    public void testStaticMethodWithDifferentClassSameNameCollision() {
        final Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        String outputDir = "./target/spooned-apache";
        launcher.addInputResource("./src/test/resources/spoon/test/imports/testclasses2/apachetestsuite/staticcollision/");
        launcher.addInputResource("./src/test/resources/spoon/test/imports/testclasses2/apachetestsuite/enums/");
        launcher.addInputResource("./src/test/resources/spoon/test/imports/testclasses2/apachetestsuite/enum2/");
        launcher.addInputResource("./src/test/resources/spoon/test/imports/testclasses2/apachetestsuite/LangTestSuite.java");
        launcher.setSourceOutputDirectory(outputDir);
        launcher.getEnvironment().setComplianceLevel(3);
        launcher.run();
        spoon.reflect.visitor.PrettyPrinter prettyPrinter = launcher.createPrettyPrinter();
        CtType element = launcher.getFactory().Class().get("spoon.test.imports.testclasses2.apachetestsuite.staticcollision.AllLangTestSuite");
        List<CtType<?>> toPrint = new java.util.ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String output = prettyPrinter.getResult();
        org.junit.Assert.assertTrue("The file should not contain a static import ", (!(output.contains("import static spoon.test.imports.testclasses2.apachetestsuite.enum2.EnumTestSuite.suite;"))));
        org.junit.Assert.assertTrue("The call to the last EnumTestSuite should be in FQN", output.contains("suite.addTest(spoon.test.imports.testclasses2.apachetestsuite.enum2.EnumTestSuite.suite());"));
        spoon.testing.utils.ModelUtils.canBeBuilt(outputDir, 3);
    }

    @org.junit.Test
    public void testSortingOfImports() {
        final Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        String outputDir = "./target/spooned";
        launcher.addInputResource("./src/main/java/spoon/reflect/visitor/DefaultJavaPrettyPrinter.java");
        launcher.setSourceOutputDirectory(outputDir);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        spoon.reflect.visitor.PrettyPrinter prettyPrinter = launcher.createPrettyPrinter();
        CtType element = launcher.getFactory().Class().get(spoon.reflect.visitor.DefaultJavaPrettyPrinter.class);
        List<CtType<?>> toPrint = new java.util.ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String output = prettyPrinter.getResult();
        java.util.StringTokenizer st = new java.util.StringTokenizer(output, java.lang.System.getProperty("line.separator"));
        String lastImport = null;
        int countOfImports = 0;
        while (st.hasMoreTokens()) {
            String line = st.nextToken();
            if (line.startsWith("import")) {
                countOfImports++;
                if (lastImport != null) {
                    org.junit.Assert.assertTrue(((lastImport + " should be before ") + line), ((lastImport.compareTo(line)) < 0));
                }
                lastImport = line;
            }else {
                if (lastImport != null) {
                    break;
                }
            }
        } 
        org.junit.Assert.assertTrue((countOfImports > 10));
    }

    @org.junit.Test
    public void testSortImportPutStaticImportAfterTypeImport() {
        final Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setShouldCompile(true);
        String outputDir = "./target/spoon-sort-import";
        launcher.addInputResource("./src/test/java/spoon/test/imports/testclasses/StaticNoOrdered.java");
        launcher.setSourceOutputDirectory(outputDir);
        launcher.run();
        spoon.reflect.visitor.PrettyPrinter prettyPrinter = launcher.createPrettyPrinter();
        CtType element = launcher.getFactory().Class().get(spoon.test.imports.testclasses.StaticNoOrdered.class);
        List<CtType<?>> toPrint = new java.util.ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String output = prettyPrinter.getResult();
        java.util.StringTokenizer st = new java.util.StringTokenizer(output, java.lang.System.getProperty("line.separator"));
        int countImports = 0;
        int nbStaticImports = 2;
        int nbStandardImports = 4;
        boolean startStatic = false;
        while (st.hasMoreTokens()) {
            String line = st.nextToken();
            if (line.startsWith("import static")) {
                if (!startStatic) {
                    org.junit.Assert.assertEquals((("Static import should start after exactly " + nbStandardImports) + " standard imports"), nbStandardImports, countImports);
                }else {
                    org.junit.Assert.assertTrue((("It will normally have only " + nbStaticImports) + " static imports"), (countImports <= (nbStandardImports + nbStaticImports)));
                }
                startStatic = true;
                org.junit.Assert.assertTrue("Static import should be after normal import", (countImports >= nbStandardImports));
            }
            if (line.startsWith("import")) {
                countImports++;
            }
        } 
        org.junit.Assert.assertEquals(((("Exactly " + nbStandardImports) + nbStaticImports) + " should have been counted."), (nbStandardImports + nbStaticImports), countImports);
    }

    @org.junit.Test
    public void testImportStarredPackageWithNonVisibleClass() throws java.io.IOException {
        final Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setShouldCompile(true);
        launcher.addInputResource("./src/test/java/spoon/test/imports/testclasses/internal/");
        launcher.addInputResource("./src/test/java/spoon/test/imports/testclasses/DumbClassUsingInternal.java");
        launcher.run();
        java.io.File f = new java.io.File("./src/test/java/spoon/test/imports/testclasses/DumbClassUsingInternal.java");
        spoon.reflect.cu.CompilationUnit cu = launcher.getFactory().CompilationUnit().getMap().get(f.getCanonicalPath());
        org.junit.Assert.assertNotNull(cu);
        org.junit.Assert.assertEquals(1, cu.getImports().size());
        org.junit.Assert.assertEquals(CtImportKind.ALL_TYPES, cu.getImports().iterator().next().getImportKind());
    }

    @org.junit.Test
    public void testImportWithGenerics() throws java.io.IOException {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/resources/import-with-generics/TestWithGenerics.java");
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setShouldCompile(true);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.setSourceOutputDirectory("./target/import-with-generics");
        launcher.run();
        spoon.reflect.visitor.PrettyPrinter prettyPrinter = launcher.createPrettyPrinter();
        CtType element = launcher.getFactory().Class().get("spoon.test.imports.testclasses.TestWithGenerics");
        List<CtType<?>> toPrint = new java.util.ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String output = prettyPrinter.getResult();
        org.junit.Assert.assertTrue(output.contains("import spoon.test.imports.testclasses.withgenerics.Target;"));
    }

    @org.junit.Test
    public void testEqualsImports() {
        final Launcher launcher = new Launcher();
        CtType typeA = launcher.getFactory().Type().get(spoon.test.imports.testclasses.A.class);
        CtImport importsA1 = launcher.getFactory().createImport(typeA.getReference());
        CtImport importsA2 = launcher.getFactory().createImport(typeA.getReference());
        org.junit.Assert.assertEquals(importsA1, importsA2);
        org.junit.Assert.assertEquals(importsA1.hashCode(), importsA2.hashCode());
        CtType typeB = launcher.getFactory().Type().get(spoon.test.imports.testclasses.Pozole.class);
        CtImport importsB = launcher.getFactory().createImport(typeB.getReference());
        org.junit.Assert.assertNotEquals(importsA1, importsB);
        org.junit.Assert.assertNotEquals(importsA1.hashCode(), importsB.hashCode());
    }

    @org.junit.Test
    public void testGetImportKindReturnRightValue() {
        final Launcher spoon = new Launcher();
        CtType aType = spoon.getFactory().Type().get(Reflection.class);
        CtImport ctImport = spoon.getFactory().createImport(aType.getReference());
        org.junit.Assert.assertEquals(CtImportKind.TYPE, ctImport.getImportKind());
        ctImport = spoon.getFactory().createImport(spoon.getFactory().Type().createWildcardStaticTypeMemberReference(aType.getReference()));
        org.junit.Assert.assertEquals(CtImportKind.ALL_STATIC_MEMBERS, ctImport.getImportKind());
        ctImport = spoon.getFactory().createImport(((CtMethod) (aType.getAllMethods().iterator().next())).getReference());
        org.junit.Assert.assertEquals(CtImportKind.METHOD, ctImport.getImportKind());
        ctImport = spoon.getFactory().createImport(((CtField) (aType.getFields().get(0))).getReference());
        org.junit.Assert.assertEquals(CtImportKind.FIELD, ctImport.getImportKind());
        ctImport = spoon.getFactory().createImport(aType.getPackage().getReference());
        org.junit.Assert.assertEquals(CtImportKind.ALL_TYPES, ctImport.getImportKind());
    }
}

