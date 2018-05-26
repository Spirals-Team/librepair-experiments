package spoon.test.parent;


import spoon.Launcher;
import spoon.compiler.SpoonResourceHelper;


public class ParentTest {
    spoon.reflect.factory.Factory factory;

    @org.junit.Before
    public void setup() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{ "--output-type", "nooutput" });
        factory = spoon.createFactory();
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/parent/Foo.java")).build();
    }

    @org.junit.Test
    public void testParent() throws java.lang.Exception {
        try {
            spoon.reflect.code.CtLiteral<java.lang.Object> literal = factory.Core().createLiteral();
            literal.setValue(1);
            spoon.reflect.code.CtBinaryOperator<?> minus = factory.Core().createBinaryOperator();
            minus.setKind(spoon.reflect.code.BinaryOperatorKind.MINUS);
            minus.setRightHandOperand(literal);
            minus.setLeftHandOperand(literal);
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail();
        }
    }

    @org.junit.Test
    public void testParentSet() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> foo = factory.Package().get("spoon.test.parent").getType("Foo");
        spoon.reflect.declaration.CtMethod<?> fooMethod = foo.getMethodsByName("foo").get(0);
        org.junit.Assert.assertEquals("foo", fooMethod.getSimpleName());
        spoon.reflect.code.CtLocalVariable<?> localVar = ((spoon.reflect.code.CtLocalVariable<?>) (fooMethod.getBody().getStatements().get(0)));
        spoon.reflect.code.CtAssignment<?, ?> assignment = ((spoon.reflect.code.CtAssignment<?, ?>) (fooMethod.getBody().getStatements().get(1)));
        spoon.reflect.code.CtLiteral<?> newLit = factory.Code().createLiteral(0);
        localVar.setDefaultExpression(((spoon.reflect.code.CtExpression) (newLit)));
        org.junit.Assert.assertEquals(localVar, newLit.getParent());
        spoon.reflect.code.CtLiteral<?> newLit2 = factory.Code().createLiteral(1);
        assignment.setAssignment(((spoon.reflect.code.CtExpression) (newLit2)));
        org.junit.Assert.assertEquals(assignment, newLit2.getParent());
    }

    @org.junit.Test
    public void testParentPackage() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> clazz = factory.Core().createClass();
        clazz.setSimpleName("Foo");
        spoon.reflect.declaration.CtPackage pack = factory.Core().createPackage();
        pack.setSimpleName("bar");
        pack.addType(clazz);
        org.junit.Assert.assertTrue(pack.getTypes().contains(clazz));
        org.junit.Assert.assertEquals(pack, clazz.getParent());
    }

    @org.junit.Test
    public void testParentOfCtPackageReference() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "--output-type", "nooutput" });
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/test/resources/reference-package");
        launcher.run();
        final spoon.reflect.declaration.CtType<java.lang.Object> panini = launcher.getFactory().Type().get("Panini");
        spoon.reflect.declaration.CtElement topLevelParent = panini.getPackage().getParent();
        org.junit.Assert.assertNotNull(topLevelParent);
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtPackage.TOP_LEVEL_PACKAGE_NAME, panini.getPackage().getSimpleName());
        spoon.reflect.declaration.CtPackage pack1 = factory.Package().getRootPackage();
        org.junit.Assert.assertNotEquals(factory, launcher.getFactory());
        org.junit.Assert.assertNotEquals(pack1, topLevelParent);
        final spoon.reflect.reference.CtTypeReference<?> burritos = panini.getElements(new spoon.reflect.visitor.filter.ReferenceTypeFilter<spoon.reflect.reference.CtTypeReference<?>>(spoon.reflect.reference.CtTypeReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtTypeReference<?> reference) {
                return ("Burritos".equals(reference.getSimpleName())) && (super.matches(reference));
            }
        }).get(0);
        org.junit.Assert.assertNotNull(burritos.getPackage().getParent());
        org.junit.Assert.assertEquals("com.awesome", burritos.getPackage().getSimpleName());
        org.junit.Assert.assertEquals(burritos, burritos.getPackage().getParent());
    }

    @org.junit.Test
    public void testParentOfCtVariableReference() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.replace.testclasses.Tacos.class);
        final spoon.reflect.declaration.CtType<spoon.test.replace.testclasses.Tacos> aTacos = factory.Type().get(spoon.test.replace.testclasses.Tacos.class);
        final spoon.reflect.code.CtInvocation inv = aTacos.getMethodsByName("m3").get(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtInvocation.class)).get(0);
        final spoon.reflect.code.CtVariableRead<?> variableRead = ((spoon.reflect.code.CtVariableRead<?>) (inv.getArguments().get(0)));
        final spoon.reflect.reference.CtParameterReference<?> aParameterReference = ((spoon.reflect.reference.CtParameterReference<?>) (variableRead.getVariable()));
        org.junit.Assert.assertNotNull(aParameterReference.getParent());
        org.junit.Assert.assertEquals(variableRead, aParameterReference.getParent());
    }

    @org.junit.Test
    public void testParentOfCtExecutableReference() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.replace.testclasses.Tacos.class);
        final spoon.reflect.declaration.CtType<spoon.test.replace.testclasses.Tacos> aTacos = factory.Type().get(spoon.test.replace.testclasses.Tacos.class);
        final spoon.reflect.code.CtInvocation inv = aTacos.getMethodsByName("m3").get(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtInvocation.class)).get(0);
        final spoon.reflect.reference.CtExecutableReference oldExecutable = inv.getExecutable();
        org.junit.Assert.assertNotNull(oldExecutable.getParent());
        org.junit.Assert.assertEquals(inv, oldExecutable.getParent());
    }

    @org.junit.Test
    public void testParentOfGenericInTypeReference() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.replace.testclasses.Tacos.class);
        final spoon.reflect.reference.CtTypeReference referenceWithGeneric = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.ReferenceTypeFilter<spoon.reflect.reference.CtTypeReference>(spoon.reflect.reference.CtTypeReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtTypeReference reference) {
                return ((reference.getActualTypeArguments().size()) > 0) && (super.matches(reference));
            }
        }).get(0);
        final spoon.reflect.reference.CtTypeReference<?> generic = referenceWithGeneric.getActualTypeArguments().get(0);
        org.junit.Assert.assertNotNull(generic.getParent());
        org.junit.Assert.assertEquals(referenceWithGeneric, generic.getParent());
    }

    @org.junit.Test
    public void testParentOfPrimitiveReference() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.replace.testclasses.Tacos.class);
        final spoon.reflect.declaration.CtType<spoon.test.replace.testclasses.Tacos> aTacos = factory.Type().get(spoon.test.replace.testclasses.Tacos.class);
        final spoon.reflect.declaration.CtMethod<?> aMethod = aTacos.getMethodsByName("m").get(0);
        org.junit.Assert.assertNotNull(aMethod.getType().getParent());
        org.junit.Assert.assertEquals(factory.Type().INTEGER_PRIMITIVE, aMethod.getType());
        org.junit.Assert.assertEquals(aMethod, aMethod.getType().getParent());
    }

    public static void checkParentContract(spoon.reflect.declaration.CtPackage pack) {
        for (spoon.reflect.declaration.CtElement elem : pack.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtElement.class))) {
            org.junit.Assert.assertNotNull(((("no parent for " + (elem.getClass())) + "-") + (elem.getPosition())), elem.getParent());
        }
        new spoon.reflect.visitor.CtScanner() {
            java.util.Deque<spoon.reflect.declaration.CtElement> elementStack = new java.util.ArrayDeque<spoon.reflect.declaration.CtElement>();

            @java.lang.Override
            public void scan(spoon.reflect.declaration.CtElement e) {
                if (e == null) {
                    return;
                }
                if (e instanceof spoon.reflect.reference.CtReference) {
                    return;
                }
                if (!(elementStack.isEmpty())) {
                    org.junit.Assert.assertEquals(elementStack.peek(), e.getParent());
                }
                elementStack.push(e);
                e.accept(this);
                elementStack.pop();
            }
        }.scan(pack);
    }

    @org.junit.Test
    public void testGetParentWithFilter() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<spoon.test.parent.Foo> clazz = ((spoon.reflect.declaration.CtClass<spoon.test.parent.Foo>) (factory.Class().getAll().get(0)));
        spoon.reflect.declaration.CtMethod<java.lang.Object> m = clazz.getMethod("m");
        spoon.reflect.code.CtExpression statement = ((spoon.reflect.code.CtAssignment) (((spoon.reflect.code.CtAssignment) (m.getBody().getStatement(3))).getAssignment())).getAssignment();
        spoon.reflect.declaration.CtPackage ctPackage = statement.getParent(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtPackage>(spoon.reflect.declaration.CtPackage.class));
        org.junit.Assert.assertEquals(spoon.test.parent.Foo.class.getPackage().getName(), ctPackage.getQualifiedName());
        spoon.reflect.code.CtStatement ctStatement = statement.getParent(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtStatement>(spoon.reflect.code.CtStatement.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtStatement element) {
                return ((element.getParent()) instanceof spoon.reflect.code.CtStatementList) && (super.matches(element));
            }
        });
        org.junit.Assert.assertEquals(m.getBody().getStatement(3), ctStatement);
        m = clazz.getMethod("internalClass");
        spoon.reflect.code.CtStatement ctStatement1 = m.getElements(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtStatement>(spoon.reflect.code.CtStatement.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtStatement element) {
                return (element instanceof spoon.reflect.code.CtLocalVariable) && (super.matches(element));
            }
        }).get(0);
        ctStatement1.getParent(spoon.reflect.declaration.CtType.class);
        spoon.reflect.declaration.CtType parent = ctStatement1.getParent(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.declaration.CtType>(spoon.reflect.declaration.CtType.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtType element) {
                return ((!(element.isAnonymous())) && (element.isTopLevel())) && (super.matches(element));
            }
        });
        org.junit.Assert.assertEquals(clazz, parent);
        org.junit.Assert.assertNotEquals(ctStatement1.getParent(spoon.reflect.declaration.CtType.class), parent);
        spoon.reflect.code.CtWhile ctWhile = ctStatement1.getParent(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtWhile>(spoon.reflect.code.CtWhile.class));
        org.junit.Assert.assertEquals(null, ctWhile);
        spoon.reflect.code.CtStatement statementParent = statement.getParent(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtStatement>(spoon.reflect.code.CtStatement.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtStatement element) {
                return true;
            }
        });
        org.junit.Assert.assertNotEquals(statement, statementParent);
    }

    @org.junit.Test
    public void testHasParent() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/resources/reference-package/Panini.java");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        try {
            final spoon.reflect.declaration.CtType<java.lang.Object> aPanini = launcher.getFactory().Type().get("Panini");
            org.junit.Assert.assertNotNull(aPanini);
            org.junit.Assert.assertFalse(aPanini.hasParent(aPanini.getFactory().Core().createAnnotation()));
            org.junit.Assert.assertTrue(aPanini.getMethod("m").hasParent(aPanini));
        } catch (java.lang.NullPointerException e) {
            org.junit.Assert.fail();
        }
    }

    @org.junit.Test
    @org.junit.Ignore
    public void testParentSetInSetter() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        final spoon.reflect.factory.Factory factory = launcher.getFactory();
        launcher.setArgs(new String[]{ "--output-type", "nooutput" });
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/main/java/spoon/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/reflect/reference");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/reference");
        launcher.addInputResource("./src/test/java/spoon/reflect/ast/");
        launcher.buildModel();
        new spoon.test.intercession.IntercessionScanner(launcher.getFactory()) {
            @java.lang.Override
            protected boolean isToBeProcessed(spoon.reflect.declaration.CtMethod<?> candidate) {
                return (((((candidate.getSimpleName().startsWith("set")) || (candidate.getSimpleName().startsWith("add"))) && (candidate.hasModifier(spoon.reflect.declaration.ModifierKind.PUBLIC))) && (takeSetterForCtElement(candidate))) && (avoidInterfaces(candidate))) && (avoidThrowUnsupportedOperationException(candidate));
            }

            @java.lang.Override
            public void process(spoon.reflect.declaration.CtMethod<?> element) {
                if ((element.getAnnotation(spoon.support.UnsettableProperty.class)) != null) {
                    return;
                }
                if (element.getSimpleName().startsWith("add")) {
                    checkAddStrategy(element);
                }else {
                    checkSetStrategy(element);
                }
            }

            private void checkAddStrategy(spoon.reflect.declaration.CtMethod<?> element) {
                final spoon.reflect.code.CtStatement statement = element.getBody().getStatement(0);
                if (!(statement instanceof spoon.reflect.code.CtIf)) {
                    org.junit.Assert.fail(((("First statement should be an if to check the parameter of the setter." + (element.getSignature())) + " declared in ") + (element.getDeclaringType().getQualifiedName())));
                }
                if (!(createCheckNull(element.getParameters().get(0)).equals(((spoon.reflect.code.CtIf) (statement)).getCondition()))) {
                    org.junit.Assert.fail(((((("Condition should test if the parameter is null. The condition was " + (((spoon.reflect.code.CtIf) (statement)).getCondition())) + "in ") + (element.getSignature())) + " declared in ") + (element.getDeclaringType().getQualifiedName())));
                }
            }

            private void checkSetStrategy(spoon.reflect.declaration.CtMethod<?> element) {
                final spoon.reflect.reference.CtTypeReference<?> type = element.getParameters().get(0).getType();
                if ((!(COLLECTIONS.contains(type))) && (!(type instanceof spoon.reflect.reference.CtArrayTypeReference))) {
                    spoon.reflect.code.CtInvocation<?> setParent = searchSetParent(element.getBody());
                    if (setParent == null) {
                        return;
                    }
                    try {
                        if ((setParent.getParent(spoon.reflect.code.CtIf.class)) == null) {
                            org.junit.Assert.fail(((("Missing condition in " + (element.getSignature())) + " declared in the class ") + (element.getDeclaringType().getQualifiedName())));
                        }
                    } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
                        org.junit.Assert.fail(((("Missing parent condition in " + (element.getSignature())) + " declared in the class ") + (element.getDeclaringType().getQualifiedName())));
                    }
                }
            }

            private spoon.reflect.code.CtBinaryOperator<java.lang.Boolean> createCheckNull(spoon.reflect.declaration.CtParameter<?> ctParameter) {
                final spoon.reflect.code.CtLiteral nullLiteral = factory.Code().createLiteral(null);
                nullLiteral.setType(factory.Type().NULL_TYPE.clone());
                final spoon.reflect.code.CtBinaryOperator<java.lang.Boolean> operator = factory.Code().createBinaryOperator(factory.Code().createVariableRead(ctParameter.getReference(), true), nullLiteral, spoon.reflect.code.BinaryOperatorKind.EQ);
                operator.setType(factory.Type().BOOLEAN_PRIMITIVE);
                return operator;
            }

            private spoon.reflect.code.CtInvocation<?> searchSetParent(spoon.reflect.code.CtBlock<?> body) {
                final java.util.List<spoon.reflect.code.CtInvocation<?>> ctInvocations = body.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtInvocation<?>>(spoon.reflect.code.CtInvocation.class) {
                    @java.lang.Override
                    public boolean matches(spoon.reflect.code.CtInvocation<?> element) {
                        return ("setParent".equals(element.getExecutable().getSimpleName())) && (super.matches(element));
                    }
                });
                return (ctInvocations.size()) > 0 ? ctInvocations.get(0) : null;
            }
        }.scan(launcher.getModel().getRootPackage());
    }
}

