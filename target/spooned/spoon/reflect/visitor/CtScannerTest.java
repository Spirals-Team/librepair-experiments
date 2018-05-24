package spoon.reflect.visitor;


public class CtScannerTest {
    @org.junit.Test
    public void testScannerContract() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/main/java/spoon/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/reflect/reference");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/reference");
        launcher.addInputResource("./src/main/java/spoon/reflect/visitor/CtScanner.java");
        launcher.buildModel();
        launcher.getModel().processWith(new spoon.reflect.visitor.processors.CheckScannerTestProcessor());
    }

    class SimpleSignature extends spoon.reflect.visitor.CtScanner {
        java.lang.String signature = "";

        @java.lang.Override
        public <T> void visitCtParameter(spoon.reflect.declaration.CtParameter<T> parameter) {
            signature += (parameter.getType().getQualifiedName()) + ", ";
            super.visitCtParameter(parameter);
        }

        @java.lang.Override
        public <T> void visitCtMethod(spoon.reflect.declaration.CtMethod<T> m) {
            signature += (m.getSimpleName()) + "(";
            super.visitCtMethod(m);
            signature += ")";
        }
    }

    class SimpleSignatureComparator implements java.util.Comparator<spoon.reflect.declaration.CtMethod<?>> {
        @java.lang.Override
        public int compare(spoon.reflect.declaration.CtMethod<?> o1, spoon.reflect.declaration.CtMethod<?> o2) {
            return computeSimpleSignature(o1).compareTo(computeSimpleSignature(o2));
        }
    }

    private java.lang.String computeSimpleSignature(spoon.reflect.declaration.CtMethod<?> m) {
        spoon.reflect.visitor.CtScannerTest.SimpleSignature sc1 = new spoon.reflect.visitor.CtScannerTest.SimpleSignature();
        sc1.visitCtMethod(m);
        return sc1.signature;
    }

    @org.junit.Test
    public void testScannerCallsAllProperties() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/main/java/spoon/reflect/");
        launcher.run();
        spoon.reflect.reference.CtTypeReference<?> ctElementRef = launcher.getFactory().createCtTypeReference(spoon.reflect.declaration.CtElement.class);
        spoon.reflect.reference.CtTypeReference<?> ctRefRef = launcher.getFactory().createCtTypeReference(spoon.reflect.reference.CtReference.class);
        spoon.reflect.declaration.CtClass<?> scannerCtClass = ((spoon.reflect.declaration.CtClass<?>) (launcher.getFactory().Type().get(spoon.reflect.visitor.CtScanner.class)));
        java.util.List<java.lang.String> problems = new java.util.ArrayList<>();
        java.util.Set<java.lang.String> ignoredInvocations = new java.util.HashSet(java.util.Arrays.asList("scan", "enter", "exit"));
        spoon.test.metamodel.SpoonMetaModel metaModel = new spoon.test.metamodel.SpoonMetaModel(new java.io.File("./src/main/java"));
        java.util.Map<java.lang.String, spoon.reflect.declaration.CtMethod<?>> scannerVisitMethodsByName = new java.util.HashMap<>();
        scannerCtClass.getAllMethods().forEach(( m) -> {
            if (m.getSimpleName().startsWith("visit")) {
                scannerVisitMethodsByName.put(m.getSimpleName(), m);
            }
        });
        class Counter {
            int nbChecks = 0;
        }
        Counter c = new Counter();
        for (spoon.test.metamodel.MetamodelConcept leafConcept : metaModel.getConcepts()) {
            if ((leafConcept.getKind()) != (spoon.test.metamodel.MMTypeKind.LEAF)) {
                continue;
            }
            spoon.reflect.declaration.CtMethod<?> visitMethod = scannerVisitMethodsByName.remove(("visit" + (leafConcept.getName())));
            org.junit.Assert.assertNotNull(((("CtScanner#" + "visit") + (leafConcept.getName())) + "(...) not found"), visitMethod);
            java.util.Set<java.lang.String> calledMethods = new java.util.HashSet<>();
            java.util.Set<java.lang.String> checkedMethods = new java.util.HashSet<>();
            leafConcept.getRoleToProperty().forEach(( role, mmField) -> {
                if (mmField.isDerived()) {
                    return;
                }
                if ((mmField.getItemValueType().isSubtypeOf(ctElementRef)) == false) {
                    return;
                }
                spoon.test.metamodel.MMMethod getter = mmField.getMethod(spoon.test.metamodel.MMMethodKind.GET);
                checkedMethods.add(getter.getSignature());
                spoon.reflect.code.CtInvocation invocation = visitMethod.filterChildren(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtInvocation>(spoon.reflect.code.CtInvocation.class) {
                    @java.lang.Override
                    public boolean matches(spoon.reflect.code.CtInvocation element) {
                        if (ignoredInvocations.contains(element.getExecutable().getSimpleName())) {
                            return false;
                        }
                        calledMethods.add(element.getExecutable().getSignature());
                        return (super.matches(element)) && (element.getExecutable().getSimpleName().equals(getter.getName()));
                    }
                }).first();
                if ((getter.getName().equals("getComments")) && (leafConcept.getModelInterface().isSubtypeOf(ctRefRef))) {
                    return;
                }
                if (invocation == null) {
                    problems.add(((("no " + (getter.getSignature())) + " in ") + visitMethod));
                }else {
                    (c.nbChecks)++;
                    spoon.reflect.path.CtRole expectedRole = metaModel.getRoleOfMethod(((spoon.reflect.declaration.CtMethod<?>) (invocation.getExecutable().getDeclaration())));
                    spoon.reflect.code.CtInvocation<?> scanInvocation = invocation.getParent(spoon.reflect.code.CtInvocation.class);
                    java.lang.String realRoleName = ((spoon.reflect.code.CtFieldRead<?>) (scanInvocation.getArguments().get(0))).getVariable().getSimpleName();
                    if ((expectedRole.name().equals(realRoleName)) == false) {
                        problems.add(((("Wrong role " + realRoleName) + " used in ") + (scanInvocation.getPosition())));
                    }
                }
            });
            calledMethods.removeAll(checkedMethods);
            if ((calledMethods.size()) > 0) {
                problems.add(((("CtScanner " + (visitMethod.getPosition())) + " calls unexpected methods: ") + calledMethods));
            }
        }
        if ((scannerVisitMethodsByName.isEmpty()) == false) {
            problems.add(("These CtScanner visit methods were not checked: " + (scannerVisitMethodsByName.keySet())));
        }
        if ((problems.size()) > 0) {
            org.junit.Assert.fail(java.lang.String.join("\n", problems));
        }
        org.junit.Assert.assertTrue("not enough checks", ((c.nbChecks) >= 200));
    }

    @org.junit.Test
    public void testScan() throws java.lang.Exception {
        spoon.Launcher launcher;
        launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("src/test/resources/noclasspath/draw2d");
        launcher.buildModel();
        class Counter {
            int nEnter = 0;

            int nExit = 0;

            int nObject = 0;

            int nElement = 0;

            java.util.Deque<spoon.reflect.visitor.CtScannerTest.CollectionContext> contexts = new java.util.ArrayDeque<>();
        }
        Counter counter = new Counter();
        launcher.getModel().getRootPackage().accept(new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public void scan(java.lang.Object o) {
                (counter.nObject)++;
                super.scan(o);
            }

            @java.lang.Override
            public void scan(spoon.reflect.declaration.CtElement o) {
                (counter.nElement)++;
                super.scan(o);
            }

            @java.lang.Override
            public void enter(spoon.reflect.declaration.CtElement o) {
                (counter.nEnter)++;
                super.enter(o);
            }

            @java.lang.Override
            public void exit(spoon.reflect.declaration.CtElement o) {
                (counter.nExit)++;
                super.exit(o);
            }
        });
        org.junit.Assert.assertEquals(0, counter.nObject);
        org.junit.Assert.assertEquals(3616, counter.nElement);
        org.junit.Assert.assertEquals(2396, counter.nEnter);
        org.junit.Assert.assertEquals(2396, counter.nExit);
        Counter counter2 = new Counter();
        launcher.getModel().getRootPackage().accept(new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public void scan(java.lang.Object o) {
                (counter2.nObject)++;
                super.scan(o);
            }

            @java.lang.Override
            public void scan(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement o) {
                if (o == null) {
                    org.junit.Assert.assertNull(counter2.contexts.peek().col);
                }else {
                    spoon.reflect.meta.RoleHandler rh = spoon.reflect.meta.impl.RoleHandlerHelper.getRoleHandler(o.getParent().getClass(), role);
                    if ((rh.getContainerKind()) == (spoon.reflect.meta.ContainerKind.SINGLE)) {
                        org.junit.Assert.assertNull(counter2.contexts.peek().col);
                    }else {
                        counter2.contexts.peek().assertRemoveSame(o);
                    }
                }
                (counter2.nElement)++;
                super.scan(o);
            }

            @java.lang.Override
            public void scan(spoon.reflect.path.CtRole role, java.util.Collection<? extends spoon.reflect.declaration.CtElement> elements) {
                counter2.contexts.peek().initCollection(elements);
                super.scan(role, elements);
                counter2.contexts.peek().assertCollectionIsEmpty();
            }

            @java.lang.Override
            public void scan(spoon.reflect.path.CtRole role, java.util.Map<java.lang.String, ? extends spoon.reflect.declaration.CtElement> elements) {
                counter2.contexts.peek().initCollection(elements.values());
                super.scan(role, elements);
                counter2.contexts.peek().assertCollectionIsEmpty();
            }

            @java.lang.Override
            public void enter(spoon.reflect.declaration.CtElement o) {
                (counter2.nEnter)++;
                counter2.contexts.push(new spoon.reflect.visitor.CtScannerTest.CollectionContext());
            }

            @java.lang.Override
            public void exit(spoon.reflect.declaration.CtElement o) {
                (counter2.nExit)++;
                counter2.contexts.peek().assertCollectionIsEmpty();
                counter2.contexts.pop();
            }
        });
        org.junit.Assert.assertEquals(counter.nObject, counter2.nObject);
        org.junit.Assert.assertEquals(counter.nElement, counter2.nElement);
        org.junit.Assert.assertEquals(counter.nEnter, counter2.nEnter);
        org.junit.Assert.assertEquals(counter.nExit, counter2.nExit);
    }

    private static class CollectionContext {
        java.util.Collection<spoon.reflect.declaration.CtElement> col;

        void assertCollectionIsEmpty() {
            org.junit.Assert.assertTrue((((col) == null) || (col.isEmpty())));
            col = null;
        }

        public void initCollection(java.util.Collection<? extends spoon.reflect.declaration.CtElement> elements) {
            assertCollectionIsEmpty();
            col = new java.util.ArrayList<>(elements);
            org.junit.Assert.assertFalse(col.contains(null));
        }

        public void assertRemoveSame(spoon.reflect.declaration.CtElement o) {
            org.junit.Assert.assertNotNull(col);
            for (java.util.Iterator iter = col.iterator(); iter.hasNext();) {
                spoon.reflect.declaration.CtElement ctElement = ((spoon.reflect.declaration.CtElement) (iter.next()));
                if (o == ctElement) {
                    iter.remove();
                    return;
                }
            }
            org.junit.Assert.fail();
        }
    }
}

