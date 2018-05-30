package spoon.test.main;


import spoon.Launcher;
import spoon.test.main.MainTest;

import static spoon.test.main.MainTest.checkParentConsistency;


public class MainTest {
    static Launcher launcher;

    static spoon.reflect.declaration.CtPackage rootPackage;

    @org.junit.BeforeClass
    public static void loadModel() {
        java.lang.StringBuilder classpath = new java.lang.StringBuilder();
        for (String classpathEntry : java.lang.System.getProperty("java.class.path").split(java.io.File.pathSeparator)) {
            if (!(classpathEntry.contains("test-classes"))) {
                classpath.append(classpathEntry);
                classpath.append(java.io.File.pathSeparator);
            }
        }
        String systemClassPath = classpath.substring(0, ((classpath.length()) - 1));
        MainTest.launcher = new Launcher();
        MainTest.launcher.run(new String[]{ "-i", "src/main/java", "-o", "target/spooned", "--destination", "target/spooned-build", "--source-classpath", systemClassPath, "--compile", "--compliance", "8", "--level", "OFF", "--enable-comments" });
        MainTest.rootPackage = MainTest.launcher.getFactory().Package().getRootPackage();
    }

    @org.junit.Test
    public void testMain_checkGenericContracts() {
        checkGenericContracts(MainTest.rootPackage);
    }

    @org.junit.Test
    public void testMain_checkShadow() {
        checkShadow(MainTest.rootPackage);
    }

    @org.junit.Test
    public void testMain_checkParentConsistency() {
        checkParentConsistency(MainTest.rootPackage);
    }

    @org.junit.Test
    public void testMain_checkModifiers() {
        for (spoon.reflect.declaration.CtModifiable modifiable : MainTest.rootPackage.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtModifiable.class))) {
            for (spoon.support.reflect.CtExtendedModifier modifier : modifiable.getExtendedModifiers()) {
                if (modifier.isImplicit()) {
                    continue;
                }
                spoon.reflect.cu.SourcePosition position = modifier.getPosition();
                spoon.reflect.cu.CompilationUnit compilationUnit = position.getCompilationUnit();
                String originalSourceCode = compilationUnit.getOriginalSourceCode();
                org.junit.Assert.assertEquals(modifier.getKind().toString(), originalSourceCode.substring(position.getSourceStart(), ((position.getSourceEnd()) + 1)));
            }
        }
    }

    public void checkGenericContracts(spoon.reflect.declaration.CtPackage pack) {
        spoon.test.parent.ParentTest.checkParentContract(pack);
        MainTest.checkAssignmentContracts(pack);
        checkContractCtScanner(pack);
        checkBoundAndUnboundTypeReference(pack);
    }

    private void checkBoundAndUnboundTypeReference(spoon.reflect.declaration.CtPackage pack) {
        new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public void visitCtTypeParameterReference(spoon.reflect.reference.CtTypeParameterReference ref) {
                spoon.reflect.declaration.CtTypeParameter declaration = ref.getDeclaration();
                if (declaration != null) {
                    org.junit.Assert.assertEquals(ref.getSimpleName(), declaration.getSimpleName());
                }
                super.visitCtTypeParameterReference(ref);
            }
        }.scan(pack);
    }

    private void checkShadow(spoon.reflect.declaration.CtPackage pack) {
        new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public void scan(spoon.reflect.declaration.CtElement element) {
                if ((element != null) && (spoon.reflect.declaration.CtShadowable.class.isAssignableFrom(element.getClass()))) {
                    org.junit.Assert.assertFalse(((spoon.reflect.declaration.CtShadowable) (element)).isShadow());
                }
                super.scan(element);
            }

            @java.lang.Override
            public <T> void visitCtTypeReference(spoon.reflect.reference.CtTypeReference<T> reference) {
                org.junit.Assert.assertNotNull(reference);
                if ((spoon.reflect.reference.CtTypeReference.NULL_TYPE_NAME.equals(reference.getSimpleName())) || ("?".equals(reference.getSimpleName()))) {
                    super.visitCtTypeReference(reference);
                    return;
                }
                final spoon.reflect.declaration.CtType<T> typeDeclaration = reference.getTypeDeclaration();
                org.junit.Assert.assertNotNull(typeDeclaration);
                org.junit.Assert.assertEquals(reference.getSimpleName(), typeDeclaration.getSimpleName());
                org.junit.Assert.assertEquals(reference.getQualifiedName(), typeDeclaration.getQualifiedName());
                if ((reference.getDeclaration()) == null) {
                    org.junit.Assert.assertTrue(typeDeclaration.isShadow());
                }
                super.visitCtTypeReference(reference);
            }

            @java.lang.Override
            public <T> void visitCtExecutableReference(spoon.reflect.reference.CtExecutableReference<T> reference) {
                super.visitCtExecutableReference(reference);
                org.junit.Assert.assertNotNull(reference);
                if (isLanguageExecutable(reference)) {
                    return;
                }
                final spoon.reflect.declaration.CtExecutable<T> executableDeclaration = reference.getExecutableDeclaration();
                org.junit.Assert.assertNotNull(("cannot find decl for " + (reference.toString())), executableDeclaration);
                org.junit.Assert.assertEquals(reference.getSimpleName(), executableDeclaration.getSimpleName());
                for (int i = 0; i < (reference.getParameters().size()); i++) {
                    if ((reference.getParameters().get(i)) instanceof spoon.reflect.reference.CtTypeParameterReference) {
                        return;
                    }
                    if (((reference.getParameters().get(i)) instanceof spoon.reflect.reference.CtArrayTypeReference) && ((((spoon.reflect.reference.CtArrayTypeReference) (reference.getParameters().get(i))).getComponentType()) instanceof spoon.reflect.reference.CtTypeParameterReference)) {
                        return;
                    }
                    if (executableDeclaration instanceof spoon.reflect.code.CtLambda) {
                        return;
                    }
                    org.junit.Assert.assertEquals(reference.getParameters().get(i).getQualifiedName(), executableDeclaration.getParameters().get(i).getType().getQualifiedName());
                }
                if ((((reference.getActualTypeArguments().size()) == 0) && (executableDeclaration instanceof spoon.reflect.declaration.CtMethod)) && ((((spoon.reflect.declaration.CtMethod) (executableDeclaration)).getFormalCtTypeParameters().size()) != 0)) {
                    org.junit.Assert.assertEquals(reference.getSignature(), executableDeclaration.getSignature());
                }
                if ((((reference.getActualTypeArguments().size()) == 0) && (executableDeclaration instanceof spoon.reflect.declaration.CtConstructor)) && ((((spoon.reflect.declaration.CtConstructor) (executableDeclaration)).getFormalCtTypeParameters().size()) != 0)) {
                    org.junit.Assert.assertEquals(reference.getSignature(), executableDeclaration.getSignature());
                }
                if (((reference.getDeclaration()) == null) && (spoon.reflect.declaration.CtShadowable.class.isAssignableFrom(executableDeclaration.getClass()))) {
                    org.junit.Assert.assertTrue(((spoon.reflect.declaration.CtShadowable) (executableDeclaration)).isShadow());
                }
            }

            private <T> boolean isLanguageExecutable(spoon.reflect.reference.CtExecutableReference<T> reference) {
                return "values".equals(reference.getSimpleName());
            }

            @java.lang.Override
            public <T> void visitCtFieldReference(spoon.reflect.reference.CtFieldReference<T> reference) {
                org.junit.Assert.assertNotNull(reference);
                if ((isLanguageField(reference)) || (isDeclaredInSuperClass(reference))) {
                    super.visitCtFieldReference(reference);
                    return;
                }
                final spoon.reflect.declaration.CtField<T> fieldDeclaration = reference.getFieldDeclaration();
                org.junit.Assert.assertNotNull(fieldDeclaration);
                org.junit.Assert.assertEquals(reference.getSimpleName(), fieldDeclaration.getSimpleName());
                org.junit.Assert.assertEquals(reference.getType().getQualifiedName(), fieldDeclaration.getType().getQualifiedName());
                if ((reference.getDeclaration()) == null) {
                    org.junit.Assert.assertTrue(fieldDeclaration.isShadow());
                }
                super.visitCtFieldReference(reference);
            }

            private <T> boolean isLanguageField(spoon.reflect.reference.CtFieldReference<T> reference) {
                return ("class".equals(reference.getSimpleName())) || ("length".equals(reference.getSimpleName()));
            }

            private <T> boolean isDeclaredInSuperClass(spoon.reflect.reference.CtFieldReference<T> reference) {
                final spoon.reflect.declaration.CtType<?> typeDeclaration = reference.getDeclaringType().getTypeDeclaration();
                return (typeDeclaration != null) && ((typeDeclaration.getField(reference.getSimpleName())) == null);
            }
        }.visitCtPackage(pack);
    }

    @org.junit.Test
    public void test() throws java.lang.Exception {
        final Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{ "--output-type", "nooutput" });
        spoon.addInputResource("./src/test/java/spoon/test/main/testclasses");
        spoon.addInputResource("./src/main/java/spoon/template/Parameter.java");
        spoon.getEnvironment().setNoClasspath(true);
        spoon.run();
        checkShadow(spoon.getFactory().Package().getRootPackage());
        checkParentConsistency(spoon.getFactory().Package().getRootPackage());
    }

    private void checkContractCtScanner(spoon.reflect.declaration.CtPackage pack) {
        class Counter {
            int scan;

            int enter;

            int exit = 0;
        }
        final Counter counter = new Counter();
        final Counter counterInclNull = new Counter();
        new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public void scan(spoon.reflect.declaration.CtElement element) {
                (counterInclNull.scan)++;
                if (element != null) {
                    (counter.scan)++;
                }
                super.scan(element);
            }

            @java.lang.Override
            public void enter(spoon.reflect.declaration.CtElement element) {
                (counter.enter)++;
                super.enter(element);
            }

            @java.lang.Override
            public void exit(spoon.reflect.declaration.CtElement element) {
                (counter.exit)++;
                super.exit(element);
            }
        }.scan(pack);
        org.junit.Assert.assertTrue(((counter.enter) == (counter.exit)));
        org.junit.Assert.assertTrue(((counter.enter) == (counter.scan)));
        Counter counterBiScan = new Counter();
        class ActualCounterScanner extends spoon.reflect.visitor.CtBiScannerDefault {
            @java.lang.Override
            public void biScan(spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other) {
                (counterBiScan.scan)++;
                if (element == null) {
                    if (other != null) {
                        org.junit.Assert.fail("element can't be null if other isn't null.");
                    }
                }else
                    if (other == null) {
                        org.junit.Assert.fail("other can't be null if element isn't null.");
                    }else {
                        org.junit.Assert.assertEquals(element, other);
                        org.junit.Assert.assertFalse((element == other));
                    }

                super.biScan(element, other);
            }
        }
        final ActualCounterScanner actual = new ActualCounterScanner();
        actual.biScan(pack, pack.clone());
        org.junit.Assert.assertEquals(counterInclNull.scan, counterBiScan.scan);
        Counter counterBiScan2 = new Counter();
        new spoon.reflect.visitor.CtBiScannerDefault() {
            @java.lang.Override
            public void biScan(spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other) {
                (counterBiScan2.scan)++;
                org.junit.Assert.assertSame(element, other);
                super.biScan(element, other);
            }
        }.biScan(pack, pack);
        org.junit.Assert.assertEquals(counterInclNull.scan, counterBiScan2.scan);
    }

    public static void checkAssignmentContracts(spoon.reflect.declaration.CtElement pack) {
        for (spoon.reflect.code.CtAssignment assign : pack.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtAssignment>(spoon.reflect.code.CtAssignment.class))) {
            spoon.reflect.code.CtExpression assigned = assign.getAssigned();
            if (!(((assigned instanceof spoon.reflect.code.CtFieldWrite) || (assigned instanceof spoon.reflect.code.CtVariableWrite)) || (assigned instanceof spoon.reflect.code.CtArrayWrite))) {
                throw new java.lang.AssertionError(((((("AssignmentContract error:" + (assign.getPosition())) + "\n") + (assign.toString())) + "\nAssigned is ") + (assigned.getClass())));
            }
        }
    }

    public static void checkParentConsistency(spoon.reflect.declaration.CtElement ele) {
        final java.util.Set<spoon.reflect.declaration.CtElement> inconsistentParents = new java.util.HashSet<>();
        new spoon.reflect.visitor.CtScanner() {
            private java.util.Deque<spoon.reflect.declaration.CtElement> previous = new java.util.ArrayDeque();

            @java.lang.Override
            protected void enter(spoon.reflect.declaration.CtElement e) {
                if (e != null) {
                    if (!(previous.isEmpty())) {
                        try {
                            if ((e.getParent()) != (previous.getLast())) {
                                inconsistentParents.add(e);
                            }
                        } catch (spoon.reflect.declaration.ParentNotInitializedException ignore) {
                            inconsistentParents.add(e);
                        }
                    }
                    previous.add(e);
                }
                super.enter(e);
            }

            @java.lang.Override
            protected void exit(spoon.reflect.declaration.CtElement e) {
                if (e == null) {
                    return;
                }
                if (e.equals(previous.getLast())) {
                    previous.removeLast();
                }else {
                    throw new java.lang.RuntimeException("Inconsistent stack");
                }
                super.exit(e);
            }
        }.scan(ele);
        org.junit.Assert.assertEquals("All parents have to be consistent", 0, inconsistentParents.size());
    }

    @org.junit.Test
    public void checkModelIsTree() {
        java.lang.Exception dummyException = new java.lang.Exception("STACK");
        spoon.reflect.visitor.PrinterHelper problems = new spoon.reflect.visitor.PrinterHelper(MainTest.rootPackage.getFactory().getEnvironment());
        java.util.Map<spoon.reflect.declaration.CtElement, java.lang.Exception> allElements = new java.util.IdentityHashMap<>();
        MainTest.rootPackage.filterChildren(null).forEach((spoon.reflect.declaration.CtElement ele) -> {
            java.lang.Exception secondStack = dummyException;
            java.lang.Exception firstStack = allElements.put(ele, secondStack);
            if (firstStack != null) {
                if (firstStack == dummyException) {
                    org.junit.Assert.fail((((("The Spoon model is not a tree. The " + (ele.getClass().getSimpleName())) + ":") + (ele.toString())) + " is shared"));
                }
                problems.write(("The element " + (ele.getClass().getSimpleName()))).writeln().incTab().write(ele.toString()).writeln().write("Is linked by these stacktraces").writeln().write(("1) " + (getStackTrace(firstStack)))).writeln().write(("2) " + (getStackTrace(secondStack)))).writeln().decTab();
            }
        });
        String report = problems.toString();
        if ((report.length()) > 0) {
            org.junit.Assert.fail(report);
        }
    }

    private String getStackTrace(java.lang.Exception e) {
        java.io.StringWriter sw = new java.io.StringWriter();
        e.printStackTrace(new java.io.PrintWriter(sw));
        return sw.toString();
    }

    @org.junit.Test
    public void testMyRoleInParent() {
        MainTest.rootPackage.accept(new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public void scan(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement element) {
                if (element != null) {
                    org.junit.Assert.assertSame(role, element.getRoleInParent());
                }
                super.scan(role, element);
            }
        });
    }

    @org.junit.Test
    public void testSourcePositionTreeIsCorrectlyOrdered() {
        java.util.List<spoon.reflect.declaration.CtType> types = MainTest.rootPackage.filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtType.class)).filterChildren((spoon.reflect.declaration.CtType t) -> t.isTopLevel()).list();
        int totalCount = 0;
        boolean hasComment = false;
        for (spoon.reflect.declaration.CtType type : types) {
            spoon.reflect.cu.SourcePosition sp = type.getPosition();
            totalCount += assertSourcePositionTreeIsCorrectlyOrder(sp.getCompilationUnit().getRootSourceFragment());
            hasComment = hasComment || ((type.getComments().size()) > 0);
        }
        org.junit.Assert.assertTrue((totalCount > 1000));
        org.junit.Assert.assertTrue(hasComment);
    }

    private int assertSourcePositionTreeIsCorrectlyOrder(spoon.reflect.visitor.printer.change.SourceFragment sourceFragment) {
        int nr = 0;
        int pos = 0;
        while (sourceFragment != null) {
            nr++;
            org.junit.Assert.assertTrue((pos <= (sourceFragment.getStart())));
            org.junit.Assert.assertTrue(((sourceFragment.getStart()) <= (sourceFragment.getEnd())));
            pos = sourceFragment.getEnd();
            nr += assertSourcePositionTreeIsCorrectlyOrder(sourceFragment.getFirstChild());
            sourceFragment = sourceFragment.getNextSibling();
        } 
        return nr;
    }

    @org.junit.Test
    public void testElementToPathToElementEquivalency() {
        MainTest.rootPackage.accept(new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public void scan(spoon.reflect.declaration.CtElement element) {
                if (element != null) {
                    spoon.reflect.path.CtPath path = element.getPath();
                    String pathStr = path.toString();
                    try {
                        spoon.reflect.path.CtPath pathRead = new spoon.reflect.path.CtPathStringBuilder().fromString(pathStr);
                        java.util.Collection<spoon.reflect.declaration.CtElement> returnedElements = pathRead.evaluateOn(MainTest.rootPackage);
                        org.junit.Assert.assertEquals(returnedElements.size(), 1);
                        spoon.reflect.declaration.CtElement actualElement = ((spoon.reflect.declaration.CtElement) (returnedElements.toArray()[0]));
                        org.junit.Assert.assertSame(element, actualElement);
                    } catch (spoon.reflect.path.CtPathException e) {
                        org.junit.Assert.fail("Path is either incorrectly generated or incorrectly read");
                    }
                }
                super.scan(element);
            }
        });
    }

    @org.junit.Test
    public void testElementIsContainedInAttributeOfItsParent() {
        MainTest.rootPackage.accept(new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public void scan(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement element) {
                if (element != null) {
                    spoon.reflect.declaration.CtElement parent = element.getParent();
                    java.lang.Object attributeOfParent = parent.getValueByRole(role);
                    if (attributeOfParent instanceof spoon.reflect.declaration.CtElement) {
                        org.junit.Assert.assertSame(((((("Element of type " + (element.getClass().getName())) + " is not the value of attribute of role ") + (role.name())) + " of parent type ") + (parent.getClass().getName())), element, attributeOfParent);
                    }else
                        if (attributeOfParent instanceof java.util.Collection) {
                            org.junit.Assert.assertTrue(((((("Element of type " + (element.getClass().getName())) + " not found in Collection value of attribute of role ") + (role.name())) + " of parent type ") + (parent.getClass().getName())), ((java.util.Collection<spoon.reflect.declaration.CtElement>) (attributeOfParent)).stream().anyMatch(( e) -> e == element));
                        }else
                            if (attributeOfParent instanceof java.util.Map) {
                                org.junit.Assert.assertTrue(((((("Element of type " + (element.getClass().getName())) + " not found in Map#values of attribute of role ") + (role.name())) + " of parent type ") + (parent.getClass().getName())), ((java.util.Map<String, ?>) (attributeOfParent)).values().stream().anyMatch(( e) -> e == element));
                            }else {
                                org.junit.Assert.fail((("Attribute of Role " + role) + " not checked"));
                            }


                }
                super.scan(role, element);
            }
        });
    }

    @org.junit.Test
    public void testTest() throws java.lang.Exception {
        Launcher launcher = new Launcher();
        launcher.run(new String[]{ "-i", "src/test/java", "-o", "target/spooned", "--noclasspath", "--compliance", "8", "--level", "OFF" });
        checkGenericContracts(launcher.getFactory().Package().getRootPackage());
        for (spoon.reflect.declaration.CtType t : launcher.getFactory().getModel().getAllTypes()) {
            if ((t.getPackage().getQualifiedName().equals("spoon.metamodel")) || (t.getPackage().getQualifiedName().startsWith("spoon.generating"))) {
                continue;
            }
            org.junit.Assert.assertTrue(((t.getQualifiedName()) + " is not clearly a test class, it should contain 'test' either in its package name or class name"), t.getQualifiedName().matches("(?i:.*test.*)"));
        }
    }

    @org.junit.Test
    public void testResourcesCopiedInTargetDirectory() throws java.lang.Exception {
        java.lang.StringBuilder classpath = new java.lang.StringBuilder();
        for (String classpathEntry : java.lang.System.getProperty("java.class.path").split(java.io.File.pathSeparator)) {
            if (!(classpathEntry.contains("test-classes"))) {
                classpath.append(classpathEntry);
                classpath.append(java.io.File.pathSeparator);
            }
        }
        String systemClassPath = classpath.substring(0, ((classpath.length()) - 1));
        Launcher.main(new String[]{ "-i", "src/test/resources/no-copy-resources/", "-o", "target/spooned-with-resources", "--destination", "target/spooned-build", "--source-classpath", systemClassPath, "--compile" });
        org.junit.Assert.assertTrue(new java.io.File("src/test/resources/no-copy-resources/package.html").exists());
        org.junit.Assert.assertTrue(new java.io.File("target/spooned-with-resources/package.html").exists());
        org.junit.Assert.assertTrue(new java.io.File("src/test/resources/no-copy-resources/fr/package.html").exists());
        org.junit.Assert.assertTrue(new java.io.File("target/spooned-with-resources/fr/package.html").exists());
        org.junit.Assert.assertTrue(new java.io.File("src/test/resources/no-copy-resources/fr/inria/package.html").exists());
        org.junit.Assert.assertTrue(new java.io.File("target/spooned-with-resources/fr/inria/package.html").exists());
    }

    @org.junit.Test
    public void testResourcesNotCopiedInTargetDirectory() throws java.lang.Exception {
        java.lang.StringBuilder classpath = new java.lang.StringBuilder();
        for (String classpathEntry : java.lang.System.getProperty("java.class.path").split(java.io.File.pathSeparator)) {
            if (!(classpathEntry.contains("test-classes"))) {
                classpath.append(classpathEntry);
                classpath.append(java.io.File.pathSeparator);
            }
        }
        String systemClassPath = classpath.substring(0, ((classpath.length()) - 1));
        Launcher.main(new String[]{ "-i", "src/test/resources/no-copy-resources", "-o", "target/spooned-without-resources", "--destination", "target/spooned-build", "--source-classpath", systemClassPath, "--compile", "-r" });
        org.junit.Assert.assertTrue(new java.io.File("src/test/resources/no-copy-resources/package.html").exists());
        org.junit.Assert.assertFalse(new java.io.File("target/spooned-without-resources/package.html").exists());
        org.junit.Assert.assertTrue(new java.io.File("src/test/resources/no-copy-resources/fr/package.html").exists());
        org.junit.Assert.assertFalse(new java.io.File("target/spooned-without-resources/fr/package.html").exists());
        org.junit.Assert.assertTrue(new java.io.File("src/test/resources/no-copy-resources/fr/inria/package.html").exists());
        org.junit.Assert.assertFalse(new java.io.File("target/spooned-without-resources/fr/inria/package.html").exists());
    }

    @org.junit.Rule
    public final org.junit.contrib.java.lang.system.ExpectedSystemExit exit = org.junit.contrib.java.lang.system.ExpectedSystemExit.none();

    private final java.io.ByteArrayOutputStream errContent = new java.io.ByteArrayOutputStream();

    @org.junit.Test
    public void testLauncherWithoutArgumentsExitWithSystemExit() throws java.lang.Exception {
        exit.expectSystemExit();
        final java.io.PrintStream oldErr = java.lang.System.err;
        java.lang.System.setErr(new java.io.PrintStream(errContent));
        exit.checkAssertionAfterwards(new org.junit.contrib.java.lang.system.Assertion() {
            @java.lang.Override
            public void checkAssertion() throws java.lang.Exception {
                org.junit.Assert.assertTrue(errContent.toString().contains("Usage: java <launcher name> [option(s)]"));
                java.lang.System.setErr(oldErr);
            }
        });
        new Launcher().run(new String[]{  });
    }
}

