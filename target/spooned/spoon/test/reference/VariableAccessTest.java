package spoon.test.reference;


public class VariableAccessTest {
    @org.junit.Test
    public void testVariableAccessDeclarationInAnonymousClass() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.reference", "FooBar");
        org.junit.Assert.assertEquals("FooBar", type.getSimpleName());
        final spoon.reflect.reference.CtParameterReference<?> ref = type.getElements(new spoon.reflect.visitor.filter.AbstractReferenceFilter<spoon.reflect.reference.CtParameterReference<?>>(spoon.reflect.reference.CtParameterReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtParameterReference<?> reference) {
                return "myArg".equals(reference.getSimpleName());
            }
        }).get(0);
        org.junit.Assert.assertNotNull("Parameter can't be null", ref.getDeclaration());
        org.junit.Assert.assertNotNull("Declaring method reference can't be null", ref.getDeclaringExecutable());
        org.junit.Assert.assertNotNull("Declaring type of the method can't be null", ref.getDeclaringExecutable().getDeclaringType());
        org.junit.Assert.assertNotNull("Declaration of declaring type of the method can't be null", ref.getDeclaringExecutable().getDeclaringType().getDeclaration());
        org.junit.Assert.assertNotNull("Declaration of root class can't be null", ref.getDeclaringExecutable().getDeclaringType().getDeclaringType().getDeclaration());
    }

    @org.junit.Test
    public void testDeclarationArray() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.reference.testclasses.Pozole> aPozole = spoon.testing.utils.ModelUtils.buildClass(spoon.test.reference.testclasses.Pozole.class);
        final spoon.reflect.declaration.CtMethod<java.lang.Object> m2 = aPozole.getMethod("m2");
        final spoon.reflect.code.CtArrayWrite<?> ctArrayWrite = m2.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtArrayWrite<?>>(spoon.reflect.code.CtArrayWrite.class)).get(0);
        final spoon.reflect.code.CtLocalVariable expected = m2.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtLocalVariable>(spoon.reflect.code.CtLocalVariable.class)).get(0);
        org.junit.Assert.assertEquals(expected, ((spoon.reflect.code.CtVariableAccess) (ctArrayWrite.getTarget())).getVariable().getDeclaration());
    }

    @org.junit.Test
    public void testParameterReferenceInConstructorNoClasspath() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/org/elasticsearch/indices/analysis/HunspellService.java");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.buildModel();
    }

    @org.junit.Test
    public void testDeclarationOfVariableReference() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/Foo2.java");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.buildModel();
        launcher.getModel().getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.reference.CtVariableReference>(spoon.reflect.reference.CtVariableReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtVariableReference element) {
                try {
                    element.clone().getDeclaration();
                } catch (java.lang.NullPointerException e) {
                    org.junit.Assert.fail(((("Fail with " + (element.getSimpleName())) + " declared in ") + (element.getParent().getShortRepresentation())));
                }
                return super.matches(element);
            }
        });
    }

    @org.junit.Test
    public void testDeclaringTypeOfALambdaReferencedByParameterReference() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("src/test/resources/noclasspath/Foo3.java");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setComplianceLevel(8);
        launcher.buildModel();
        launcher.getModel().getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtExecutable<?>>(spoon.reflect.declaration.CtExecutable.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtExecutable<?> exec) {
                final java.util.List<spoon.reflect.reference.CtParameterReference<?>> guiParams = exec.getParameters().stream().map(spoon.reflect.declaration.CtParameter::getReference).collect(java.util.stream.Collectors.toList());
                if ((guiParams.size()) != 1) {
                    return false;
                }
                final spoon.reflect.reference.CtParameterReference<?> param = guiParams.get(0);
                exec.getBody().getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.reference.CtParameterReference<?>>(spoon.reflect.reference.CtParameterReference.class) {
                    @java.lang.Override
                    public boolean matches(spoon.reflect.reference.CtParameterReference<?> p) {
                        org.junit.Assert.assertEquals(p.getSimpleName(), param.getSimpleName());
                        return super.matches(p);
                    }
                });
                return super.matches(exec);
            }
        });
    }

    @org.junit.Test
    public void testGetDeclarationAfterClone() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/test/resources/noclasspath/A2.java");
        launcher.buildModel();
        final spoon.reflect.declaration.CtClass<java.lang.Object> a2 = launcher.getFactory().Class().get("A2");
        final spoon.reflect.declaration.CtClass<java.lang.Object> a2Cloned = a2.clone();
        org.junit.Assert.assertEquals(a2, a2Cloned);
        final spoon.reflect.declaration.CtMethod<java.lang.Object> methodA2 = getMethod(launcher, a2);
        final spoon.reflect.declaration.CtMethod<java.lang.Object> methodA2Cloned = getMethod(launcher, a2Cloned);
        final spoon.reflect.code.CtLocalVariable declaration = methodA2.getBody().getStatement(0);
        final spoon.reflect.code.CtLocalVariable declarationCloned = methodA2Cloned.getBody().getStatement(0);
        final spoon.reflect.reference.CtLocalVariableReference localVarRef = getLocalVariableRefF1(methodA2);
        final spoon.reflect.reference.CtLocalVariableReference localVarRefCloned = getLocalVariableRefF1(methodA2Cloned);
        org.junit.Assert.assertEquals(localVarRef.getDeclaration(), declaration);
        org.junit.Assert.assertSame(localVarRef.getDeclaration(), declaration);
        org.junit.Assert.assertEquals(localVarRefCloned.getDeclaration(), declarationCloned);
        org.junit.Assert.assertSame(localVarRefCloned.getDeclaration(), declarationCloned);
    }

    @org.junit.Test
    public void testReferences() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.reference.testclasses.Tortillas> aTortillas = spoon.testing.utils.ModelUtils.buildClass(spoon.test.reference.testclasses.Tortillas.class);
        final spoon.reflect.declaration.CtMethod<java.lang.Object> make = aTortillas.getMethod("make", aTortillas.getFactory().Type().stringType());
        final spoon.reflect.code.CtLocalVariable localVar = make.getBody().getStatement(0);
        final spoon.reflect.code.CtLocalVariable localVarCloned = localVar.clone();
        final spoon.reflect.reference.CtLocalVariableReference localVarRef = localVar.getReference();
        final spoon.reflect.reference.CtLocalVariableReference localVarRefCloned = localVarCloned.getReference();
        org.junit.Assert.assertEquals(localVarRef.getDeclaration(), localVar);
        org.junit.Assert.assertSame(localVarRef.getDeclaration(), localVar);
        org.junit.Assert.assertEquals(localVar.getReference().getDeclaration(), localVar);
        org.junit.Assert.assertSame(localVar.getReference().getDeclaration(), localVar);
        org.junit.Assert.assertEquals(localVarRefCloned.getDeclaration(), localVarCloned);
        org.junit.Assert.assertSame(localVarRefCloned.getDeclaration(), localVarCloned);
        org.junit.Assert.assertEquals(localVarCloned.getReference().getDeclaration(), localVarCloned);
        org.junit.Assert.assertSame(localVarCloned.getReference().getDeclaration(), localVarCloned);
    }

    @org.junit.Test
    public void testReferencesInInitExpression() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.reference.testclasses.Tortillas> aTortillas = spoon.testing.utils.ModelUtils.buildClass(spoon.test.reference.testclasses.Tortillas.class);
        final spoon.reflect.declaration.CtMethod<java.lang.Object> make = aTortillas.getMethod("make", aTortillas.getFactory().Type().stringType());
        final spoon.reflect.code.CtLocalVariable localVarNumber = make.getBody().getStatement(1);
        java.util.List<spoon.reflect.reference.CtLocalVariableReference<?>> refs = localVarNumber.map(new spoon.reflect.visitor.filter.LocalVariableReferenceFunction()).list();
        org.junit.Assert.assertEquals(1, refs.size());
        org.junit.Assert.assertSame(localVarNumber, refs.get(0).getParent(spoon.reflect.code.CtLocalVariable.class));
    }

    @org.junit.Test
    public void testReferenceToLocalVariableDeclaredInLoop() {
        final class CtLocalVariableReferenceScanner extends spoon.reflect.visitor.CtScanner {
            @java.lang.Override
            public <T> void visitCtLocalVariableReference(final spoon.reflect.reference.CtLocalVariableReference<T> reference) {
                org.junit.Assert.assertNotNull(reference.getDeclaration());
                org.junit.Assert.assertEquals(reference.getDeclaration().getSimpleName(), reference.getSimpleName());
                org.junit.Assert.assertEquals(reference.getDeclaration().getType(), reference.getType());
                super.visitCtLocalVariableReference(reference);
            }
        }
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("src/test/resources/reference-test/ChangeScanner.java");
        launcher.buildModel();
        new CtLocalVariableReferenceScanner().scan(launcher.getModel().getRootPackage());
    }

    @org.junit.Test
    public void testMultipleDeclarationsOfLocalVariable() {
        final class CtLocalVariableReferenceScanner extends spoon.reflect.visitor.CtScanner {
            @java.lang.Override
            public <T> void visitCtLocalVariableReference(final spoon.reflect.reference.CtLocalVariableReference<T> reference) {
                org.junit.Assert.assertNotNull(reference.getDeclaration());
                final spoon.reflect.code.CtLocalVariable decl = reference.getDeclaration();
                org.junit.Assert.assertEquals(decl.getPosition().getLine(), 7);
                org.junit.Assert.assertTrue(((decl.getDefaultExpression()) instanceof spoon.reflect.code.CtLiteral));
                final spoon.reflect.code.CtLiteral literal = ((spoon.reflect.code.CtLiteral) (decl.getDefaultExpression()));
                org.junit.Assert.assertEquals(literal.getValue(), 42);
                super.visitCtLocalVariableReference(reference);
            }
        }
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("src/test/resources/reference-test/MultipleDeclarationsOfLocalVariable.java");
        launcher.buildModel();
        new CtLocalVariableReferenceScanner().scan(launcher.getModel().getRootPackage());
    }

    private spoon.reflect.declaration.CtMethod<java.lang.Object> getMethod(spoon.Launcher launcher, spoon.reflect.declaration.CtClass<java.lang.Object> a2) {
        return a2.getMethod("b", launcher.getFactory().Type().integerPrimitiveType());
    }

    private spoon.reflect.reference.CtLocalVariableReference getLocalVariableRefF1(spoon.reflect.declaration.CtMethod<java.lang.Object> method) {
        return method.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.reference.CtLocalVariableReference>(spoon.reflect.reference.CtLocalVariableReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtLocalVariableReference element) {
                return ("f1".equals(element.getSimpleName())) && (super.matches(element));
            }
        }).get(0);
    }

    @org.junit.Test
    public void testSuperAccess() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.reference.testclasses", "SuperAccess");
        spoon.reflect.declaration.CtMethod<?> method = type.getMethodsByName("method").get(0);
        spoon.reflect.code.CtInvocation<?> invocation = method.getBody().getStatement(0);
        spoon.reflect.code.CtSuperAccess<?> superAccess = ((spoon.reflect.code.CtSuperAccess<?>) (invocation.getTarget()));
        org.junit.Assert.assertNotNull(superAccess.getType());
        org.junit.Assert.assertEquals("spoon.test.reference.testclasses.Parent", superAccess.getType().getQualifiedName());
    }
}

