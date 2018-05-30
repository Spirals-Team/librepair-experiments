package spoon.test.intercession;


public class IntercessionTest {
    spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();

    @org.junit.Test
    public void testInsertBegin() {
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("" + (((("class X {" + "public void foo() {") + " int x=0;") + "}") + "};"))).compile();
        spoon.reflect.declaration.CtMethod<?> foo = ((spoon.reflect.declaration.CtMethod<?>) (clazz.getMethods().toArray()[0]));
        spoon.reflect.code.CtBlock<?> body = foo.getBody();
        org.junit.Assert.assertEquals(1, body.getStatements().size());
        spoon.reflect.code.CtReturn<java.lang.Object> returnStmt = factory.Core().createReturn();
        body.insertBegin(returnStmt);
        org.junit.Assert.assertEquals(2, body.getStatements().size());
        org.junit.Assert.assertSame(returnStmt, body.getStatements().get(0));
    }

    @org.junit.Test
    public void testInsertEnd() {
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("" + ((((("class X {" + "public void foo() {") + " int x=0;") + " String foo=\"toto\";") + "}") + "};"))).compile();
        spoon.reflect.declaration.CtMethod<?> foo = ((spoon.reflect.declaration.CtMethod<?>) (clazz.getMethods().toArray()[0]));
        spoon.reflect.declaration.CtMethod<?> fooClone = foo.clone();
        org.junit.Assert.assertEquals(foo, fooClone);
        spoon.reflect.code.CtBlock<?> body = foo.getBody();
        org.junit.Assert.assertEquals(2, body.getStatements().size());
        spoon.reflect.code.CtReturn<java.lang.Object> returnStmt = factory.Core().createReturn();
        body.insertEnd(returnStmt);
        org.junit.Assert.assertEquals(3, body.getStatements().size());
        org.junit.Assert.assertSame(returnStmt, body.getStatements().get(2));
        org.junit.Assert.assertNotEquals(foo, fooClone);
    }

    @org.junit.Test
    public void testEqualConstructor() {
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("" + "class X { public X() {} };")).compile();
        spoon.reflect.declaration.CtConstructor<?> foo = ((spoon.reflect.declaration.CtConstructor<?>) (clazz.getConstructors().toArray()[0]));
        spoon.reflect.declaration.CtConstructor<?> fooClone = foo.clone();
        org.junit.Assert.assertEquals(foo, fooClone);
        spoon.reflect.code.CtBlock<?> body = foo.getBody();
        org.junit.Assert.assertEquals(1, body.getStatements().size());
        org.junit.Assert.assertEquals("super()", body.getStatements().get(0).toString());
        spoon.reflect.code.CtStatement stmt = factory.Core().createCodeSnippetStatement();
        body.insertEnd(stmt);
        org.junit.Assert.assertEquals(2, body.getStatements().size());
        org.junit.Assert.assertNotEquals(foo, fooClone);
    }

    @org.junit.Test
    public void test_setThrownExpression() {
        spoon.reflect.code.CtThrow throwStmt = factory.Core().createThrow();
        spoon.reflect.code.CtExpression<java.lang.Exception> exp = factory.Code().createCodeSnippetExpression("e");
        throwStmt.setThrownExpression(exp);
        org.junit.Assert.assertEquals("throw e", throwStmt.toString());
    }

    @org.junit.Test
    public void testInsertIfIntercession() {
        java.lang.String ifCode = "if (1 == 0)\n" + ((("    return 1;\n" + "else\n") + "    return 0;\n") + "");
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement((((("" + ("class X {" + "public int bar() {")) + ifCode) + "}") + "};")).compile();
        spoon.reflect.declaration.CtMethod<?> foo = ((spoon.reflect.declaration.CtMethod<?>) (clazz.getMethods().toArray()[0]));
        spoon.reflect.code.CtBlock<?> body = foo.getBody();
        org.junit.Assert.assertEquals(1, body.getStatements().size());
        spoon.reflect.code.CtIf ifStmt = ((spoon.reflect.code.CtIf) (foo.getBody().getStatements().get(0)));
        java.lang.String s = ifStmt.toString().replace("\r", "");
        org.junit.Assert.assertEquals(ifCode, s);
        spoon.reflect.code.CtBlock<?> r1 = ifStmt.getThenStatement();
        spoon.reflect.code.CtBlock<?> r2 = ifStmt.getElseStatement();
        org.junit.Assert.assertTrue(r1.isImplicit());
        org.junit.Assert.assertTrue(r2.isImplicit());
        ifStmt.setThenStatement(r2);
        org.junit.Assert.assertSame(r2, ifStmt.getThenStatement());
        ifStmt.setElseStatement(r1);
        org.junit.Assert.assertSame(r1, ifStmt.getElseStatement());
        s = ifStmt.toString().replace("\r", "");
        java.lang.String ifCodeNew = "if (1 == 0)\n" + ((("    return 0;\n" + "else\n") + "    return 1;\n") + "");
        org.junit.Assert.assertEquals(ifCodeNew, s);
    }

    @org.junit.Test
    public void testInsertAfter() {
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("" + (((((("class X {" + "public void foo() {") + " int x=0;") + " int y=0;") + " int z=x+y;") + "}") + "};"))).compile();
        spoon.reflect.declaration.CtMethod<?> foo = ((spoon.reflect.declaration.CtMethod<?>) (clazz.getMethods().toArray()[0]));
        spoon.reflect.code.CtBlock<?> body = foo.getBody();
        org.junit.Assert.assertEquals(3, body.getStatements().size());
        spoon.reflect.code.CtStatement s = body.getStatements().get(2);
        org.junit.Assert.assertEquals("int z = x + y", s.toString());
        spoon.reflect.code.CtCodeSnippetStatement stmt = factory.Core().createCodeSnippetStatement();
        stmt.setValue("System.out.println(x);");
        s.insertAfter(stmt);
        org.junit.Assert.assertEquals(4, body.getStatements().size());
        org.junit.Assert.assertSame(stmt, body.getStatements().get(3));
    }

    @org.junit.Test
    public void testSettersAreAllGood() throws java.lang.Exception {
        java.util.ArrayList classpath = new java.util.ArrayList();
        for (java.lang.String classpathEntry : java.lang.System.getProperty("java.class.path").split(java.io.File.pathSeparator)) {
            if (!(classpathEntry.contains("test-classes"))) {
                classpath.add(classpathEntry);
            }
        }
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/main/java/spoon/reflect/");
        launcher.addInputResource("./src/main/java/spoon/support/");
        launcher.getModelBuilder().setSourceClasspath(((java.lang.String[]) (classpath.toArray(new java.lang.String[]{  }))));
        launcher.buildModel();
        final spoon.reflect.factory.Factory factory = launcher.getFactory();
        final java.util.List<spoon.reflect.declaration.CtMethod<?>> setters = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.declaration.CtMethod<?>>(spoon.reflect.declaration.CtMethod.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtMethod<?> element) {
                spoon.reflect.declaration.CtType<?> declaringType = element.getDeclaringType();
                if (((declaringType.getPackage()) != null) && ((declaringType.getPackage().getQualifiedName().startsWith("spoon.support.visitor")) || (declaringType.getPackage().getQualifiedName().startsWith("spoon.reflect.visitor")))) {
                    return false;
                }
                return ((declaringType.isInterface()) && (declaringType.getSimpleName().startsWith("Ct"))) && ((element.getSimpleName().startsWith("set")) || (element.getSimpleName().startsWith("add")));
            }
        });
        for (spoon.reflect.declaration.CtMethod<?> setter : setters) {
            final java.lang.String methodLog = ((setter.getSimpleName()) + " in ") + (setter.getDeclaringType().getSimpleName());
            if ((setter.getFormalCtTypeParameters().size()) <= 0) {
                org.junit.Assert.fail((("Your setter " + methodLog) + " don't have a generic type for its return type."));
            }
            boolean isMatch = false;
            for (spoon.reflect.declaration.CtTypeParameter typeParameter : setter.getFormalCtTypeParameters()) {
                if (setter.getType().getSimpleName().equals(typeParameter.getSimpleName())) {
                    isMatch = true;
                    if ((setter.getAnnotation(java.lang.Override.class)) != null) {
                        continue;
                    }
                    if (!(setter.getDeclaringType().getSimpleName().equals(typeParameter.getSuperclass().getSimpleName()))) {
                        org.junit.Assert.fail(((("Your setter " + methodLog) + " has a type reference who don't extends ") + (setter.getDeclaringType().getSimpleName())));
                    }
                }
            }
            org.junit.Assert.assertTrue((("The type of " + methodLog) + " don't match with generic types."), isMatch);
        }
    }

    @org.junit.Test
    @org.junit.Ignore
    public void testResetCollectionInSetters() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        final spoon.reflect.factory.Factory factory = launcher.getFactory();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/main/java/spoon/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/reflect/reference");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/reference");
        launcher.buildModel();
        new spoon.test.intercession.IntercessionScanner(factory) {
            @java.lang.Override
            protected boolean isToBeProcessed(spoon.reflect.declaration.CtMethod<?> candidate) {
                return ((((candidate.getSimpleName().startsWith("set")) && (candidate.hasModifier(spoon.reflect.declaration.ModifierKind.PUBLIC))) && (takeSetterCollection(candidate))) && (avoidInterfaces(candidate))) && (avoidThrowUnsupportedOperationException(candidate));
            }

            private boolean takeSetterCollection(spoon.reflect.declaration.CtMethod<?> candidate) {
                final spoon.reflect.reference.CtTypeReference<?> type = candidate.getParameters().get(0).getType();
                final java.util.List<spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments = type.getActualTypeArguments();
                return ((COLLECTIONS.contains(type)) && ((actualTypeArguments.size()) == 1)) && (actualTypeArguments.get(0).isSubtypeOf(CTELEMENT_REFERENCE));
            }

            @java.lang.Override
            protected void process(spoon.reflect.declaration.CtMethod<?> element) {
                if ((element.getAnnotation(spoon.support.UnsettableProperty.class)) != null) {
                    return;
                }
                final spoon.reflect.code.CtStatement statement = element.getBody().getStatement(0);
                if (!(statement instanceof spoon.reflect.code.CtIf)) {
                    org.junit.Assert.fail(log(element, "First statement should be an if to check the parameter of the setter"));
                }
                final spoon.reflect.code.CtIf anIf = ((spoon.reflect.code.CtIf) (statement));
                if (!(createCheckNull(element.getParameters().get(0)).equals(anIf.getCondition()))) {
                    org.junit.Assert.fail(log(element, ("Condition should test if the parameter is null.\nThe condition was " + (anIf.getCondition()))));
                }
                if (!((anIf.getThenStatement()) instanceof spoon.reflect.code.CtBlock)) {
                    org.junit.Assert.fail(log(element, "Should have a block in the if condition to have the initialization and the return."));
                }
                if (element.getParameters().get(0).getType().equals(SET_REFERENCE)) {
                    if (!(hasCallEmptyInv(anIf.getThenStatement(), SET_REFERENCE))) {
                        org.junit.Assert.fail(log(element, "Should initilize the list with CtElementImpl#emptySet()."));
                    }
                }else {
                    if (!(hasCallEmptyInv(anIf.getThenStatement(), LIST_REFERENCE))) {
                        org.junit.Assert.fail(log(element, "Should initilize the list with CtElementImpl#emptyList()."));
                    }
                }
            }

            private boolean hasCallEmptyInv(spoon.reflect.code.CtBlock thenStatement, spoon.reflect.reference.CtTypeReference<? extends java.util.Collection> collectionReference) {
                if (!((thenStatement.getStatement(0)) instanceof spoon.reflect.code.CtAssignment)) {
                    return false;
                }
                final spoon.reflect.code.CtExpression assignment = ((spoon.reflect.code.CtAssignment) (thenStatement.getStatement(0))).getAssignment();
                if (!(assignment instanceof spoon.reflect.code.CtInvocation)) {
                    return false;
                }
                final spoon.reflect.code.CtInvocation inv = ((spoon.reflect.code.CtInvocation) (assignment));
                if (collectionReference.equals(SET_REFERENCE)) {
                    if (!(inv.getExecutable().getSimpleName().equals("emptySet"))) {
                        return false;
                    }
                }else
                    if (collectionReference.equals(LIST_REFERENCE)) {
                        if (!(inv.getExecutable().getSimpleName().equals("emptyList"))) {
                            return false;
                        }
                    }

                return true;
            }

            private spoon.reflect.code.CtBinaryOperator<java.lang.Boolean> createCheckNull(spoon.reflect.declaration.CtParameter<?> ctParameter) {
                final spoon.reflect.code.CtVariableAccess<?> variableRead = factory.Code().createVariableRead(ctParameter.getReference(), true);
                final spoon.reflect.code.CtLiteral nullLiteral = factory.Code().createLiteral(null);
                nullLiteral.setType(factory.Type().nullType());
                final spoon.reflect.code.CtBinaryOperator<java.lang.Boolean> checkNull = factory.Code().createBinaryOperator(variableRead, nullLiteral, spoon.reflect.code.BinaryOperatorKind.EQ);
                checkNull.setType(factory.Type().BOOLEAN_PRIMITIVE);
                final spoon.reflect.declaration.CtMethod<java.lang.Boolean> isEmptyMethod = ctParameter.getType().getTypeDeclaration().getMethod(factory.Type().booleanPrimitiveType(), "isEmpty");
                final spoon.reflect.code.CtInvocation<java.lang.Boolean> isEmpty = factory.Code().createInvocation(variableRead, isEmptyMethod.getReference());
                final spoon.reflect.code.CtBinaryOperator<java.lang.Boolean> condition = factory.Code().createBinaryOperator(checkNull, isEmpty, spoon.reflect.code.BinaryOperatorKind.OR);
                return condition.setType(factory.Type().booleanPrimitiveType());
            }

            private java.lang.String log(spoon.reflect.declaration.CtMethod<?> element, java.lang.String message) {
                return (((message + "\nin ") + (element.getSignature())) + "\ndeclared in ") + (element.getDeclaringType().getQualifiedName());
            }
        }.scan(factory.getModel().getUnnamedModule());
    }
}

