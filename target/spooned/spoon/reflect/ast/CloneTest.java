package spoon.reflect.ast;


public class CloneTest {
    @org.junit.Test
    public void testCloneMethodsDeclaredInAST() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/main/java/spoon/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/reflect/reference");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/reference");
        launcher.run();
        new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public <T> void visitCtClass(spoon.reflect.declaration.CtClass<T> ctClass) {
                if (!(ctClass.getSimpleName().startsWith("Ct"))) {
                    return;
                }
                final spoon.reflect.declaration.CtMethod<java.lang.Object> clone = ctClass.getMethod("clone");
                org.junit.Assert.assertNotNull(((ctClass.getQualifiedName()) + " hasn't clone method."), clone);
                org.junit.Assert.assertTrue(((ctClass.getQualifiedName()) + " hasn't Override annotation on clone method."), clone.getAnnotations().stream().map(( ctAnnotation) -> ctAnnotation.getActualAnnotation().annotationType()).collect(java.util.stream.Collectors.toList()).contains(java.lang.Override.class));
            }

            @java.lang.Override
            public <T> void visitCtInterface(spoon.reflect.declaration.CtInterface<T> intrface) {
                if (!(intrface.getSimpleName().startsWith("Ct"))) {
                    return;
                }
                final spoon.reflect.declaration.CtMethod<java.lang.Object> clone = intrface.getMethod("clone");
                if (hasConcreteImpl(intrface)) {
                    org.junit.Assert.assertNotNull(((intrface.getQualifiedName()) + " hasn't clone method."), clone);
                    if (!(isRootDeclaration(intrface))) {
                        org.junit.Assert.assertTrue(((intrface.getQualifiedName()) + " hasn't Override annotation on clone method."), clone.getAnnotations().stream().map(( ctAnnotation) -> ctAnnotation.getActualAnnotation().annotationType()).collect(java.util.stream.Collectors.toList()).contains(java.lang.Override.class));
                    }
                }
            }

            private <T> boolean hasConcreteImpl(spoon.reflect.declaration.CtInterface<T> intrface) {
                return (spoon.reflect.visitor.Query.getElements(intrface.getFactory(), new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtClass<?>>(spoon.reflect.declaration.CtClass.class) {
                    @java.lang.Override
                    public boolean matches(spoon.reflect.declaration.CtClass<?> element) {
                        return (super.matches(element)) && (element.getSuperInterfaces().contains(intrface.getReference()));
                    }
                }).size()) > 0;
            }

            private <T> boolean isRootDeclaration(spoon.reflect.declaration.CtInterface<T> intrface) {
                return "CtElement".equals(intrface.getSimpleName());
            }
        }.scan(launcher.getModel().getRootPackage());
    }

    @org.junit.Test
    public void testCloneCastConditional() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/test/resources/spoon/test/visitor/ConditionalRes.java");
        launcher.addProcessor(new spoon.processing.AbstractProcessor<spoon.reflect.code.CtConditional<?>>() {
            @java.lang.Override
            public void process(spoon.reflect.code.CtConditional<?> conditional) {
                spoon.reflect.code.CtConditional clone = conditional.clone();
                org.junit.Assert.assertEquals(0, conditional.getTypeCasts().size());
                org.junit.Assert.assertEquals(0, clone.getTypeCasts().size());
                org.junit.Assert.assertEquals(conditional, clone);
                conditional.addTypeCast(getFactory().Type().bytePrimitiveType());
                org.junit.Assert.assertEquals(1, conditional.getTypeCasts().size());
                org.junit.Assert.assertNotEquals(conditional, clone);
                clone = conditional.clone();
                org.junit.Assert.assertEquals(conditional, clone);
                org.junit.Assert.assertEquals(1, clone.getTypeCasts().size());
            }
        });
        launcher.run();
    }

    @org.junit.Test
    public void testCloneListener() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(new java.io.File("./src/main/java/spoon/reflect/visitor/DefaultJavaPrettyPrinter.java"));
        spoon.reflect.declaration.CtType<?> cloneSource = factory.Type().get(spoon.reflect.visitor.DefaultJavaPrettyPrinter.class);
        class CloneListener extends spoon.support.visitor.equals.CloneHelper {
            java.util.Map<spoon.reflect.declaration.CtElement, spoon.reflect.declaration.CtElement> sourceToTarget = new java.util.IdentityHashMap<>();

            @java.lang.Override
            public <T extends spoon.reflect.declaration.CtElement> T clone(T source) {
                if (source == null) {
                    return null;
                }
                T target = super.clone(source);
                onCloned(source, target);
                return target;
            }

            private void onCloned(spoon.reflect.declaration.CtElement source, spoon.reflect.declaration.CtElement target) {
                spoon.reflect.declaration.CtElement previousTarget = sourceToTarget.put(source, target);
                org.junit.Assert.assertNull(previousTarget);
            }
        }
        CloneListener cl = new CloneListener();
        spoon.reflect.declaration.CtType<?> cloneTarget = cl.clone(cloneSource);
        cloneSource.filterChildren(null).forEach(( sourceElement) -> {
            spoon.reflect.declaration.CtElement targetElement = cl.sourceToTarget.remove(sourceElement);
            org.junit.Assert.assertNotNull(("Missing target for sourceElement\n" + sourceElement), targetElement);
            org.junit.Assert.assertEquals("Source and Target are not equal", sourceElement, targetElement);
        });
        org.junit.Assert.assertTrue(cl.sourceToTarget.isEmpty());
    }

    @org.junit.Test
    public void testCopyMethod() throws java.lang.Exception {
        spoon.Launcher l = new spoon.Launcher();
        l.getEnvironment().setNoClasspath(true);
        l.addInputResource("./src/test/resources/noclasspath/A2.java");
        l.buildModel();
        spoon.reflect.declaration.CtClass<java.lang.Object> klass = l.getFactory().Class().get("A2");
        spoon.reflect.declaration.CtMethod<?> method = klass.getMethodsByName("c").get(0);
        java.util.List<spoon.reflect.reference.CtExecutableReference> elements = method.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.reference.CtExecutableReference.class));
        spoon.reflect.reference.CtExecutableReference methodRef = elements.get(0);
        org.junit.Assert.assertSame(method, methodRef.getDeclaration());
        org.junit.Assert.assertEquals("A2", methodRef.getDeclaringType().toString());
        spoon.reflect.declaration.CtMethod<?> methodClone = method.copyMethod();
        org.junit.Assert.assertEquals("cCopy", methodClone.getSimpleName());
        methodClone.getBody().insertBegin(l.getFactory().createCodeSnippetStatement("// debug info"));
        spoon.reflect.reference.CtExecutableReference reference = methodClone.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.reference.CtExecutableReference.class)).get(0);
        org.junit.Assert.assertEquals("cCopy", reference.getSimpleName());
        org.junit.Assert.assertSame(methodClone, reference.getDeclaration());
        org.junit.Assert.assertEquals("A2", methodClone.getDeclaringType().getQualifiedName());
        spoon.refactoring.Refactoring.changeMethodName(methodClone, "foo");
        org.junit.Assert.assertEquals("foo", methodClone.getSimpleName());
        org.junit.Assert.assertEquals("foo", reference.getSimpleName());
        org.junit.Assert.assertSame(methodClone, reference.getDeclaration());
        org.junit.Assert.assertEquals("A2", methodClone.getDeclaringType().getQualifiedName());
        methodClone = spoon.refactoring.Refactoring.copyMethod(method);
        org.junit.Assert.assertEquals("cCopy", methodClone.getSimpleName());
        methodClone = spoon.refactoring.Refactoring.copyMethod(method);
        org.junit.Assert.assertEquals("cCopyX", methodClone.getSimpleName());
    }

    @org.junit.Test
    public void testCopyType() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(new java.io.File("./src/main/java/spoon/reflect/visitor/DefaultJavaPrettyPrinter.java"));
        spoon.reflect.declaration.CtType<?> intialElement = factory.Type().get(spoon.reflect.visitor.DefaultJavaPrettyPrinter.class);
        spoon.reflect.declaration.CtType<?> cloneTarget = intialElement.copyType();
        org.junit.Assert.assertEquals("spoon.reflect.visitor.DefaultJavaPrettyPrinterCopy", cloneTarget.getQualifiedName());
        for (spoon.reflect.reference.CtReference reference : cloneTarget.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.reference.CtReference.class))) {
            spoon.reflect.declaration.CtElement declaration = reference.getDeclaration();
            if (declaration == null) {
                continue;
            }
            if (declaration.hasParent(intialElement)) {
                org.junit.Assert.fail();
            }
        }
    }
}

