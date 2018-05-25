package spoon.test.fieldaccesses;


public class FieldAccessTest {
    @org.junit.Test
    public void testModelBuildingFieldAccesses() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.fieldaccesses", "Mouse");
        org.junit.Assert.assertEquals("Mouse", type.getSimpleName());
        spoon.reflect.declaration.CtMethod<?> meth1 = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "meth1")).get(0);
        spoon.reflect.declaration.CtMethod<?> meth1b = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "meth1b")).get(0);
        org.junit.Assert.assertEquals(3, meth1.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtFieldAccess<?>>(spoon.reflect.code.CtFieldAccess.class)).size());
        org.junit.Assert.assertEquals(2, meth1b.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtFieldAccess<?>>(spoon.reflect.code.CtFieldAccess.class)).size());
        spoon.reflect.declaration.CtMethod<?> meth2 = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "meth2")).get(0);
        org.junit.Assert.assertEquals(2, meth2.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtFieldAccess<?>>(spoon.reflect.code.CtFieldAccess.class)).size());
        spoon.reflect.declaration.CtMethod<?> meth3 = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "meth3")).get(0);
        org.junit.Assert.assertEquals(3, meth3.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtFieldAccess<?>>(spoon.reflect.code.CtFieldAccess.class)).size());
        spoon.reflect.declaration.CtMethod<?> meth4 = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "meth4")).get(0);
        org.junit.Assert.assertEquals(1, meth4.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtFieldAccess<?>>(spoon.reflect.code.CtFieldAccess.class)).size());
    }

    @org.junit.Test
    public void testBCUBug20140402() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.fieldaccesses", "BCUBug20140402");
        org.junit.Assert.assertEquals("BCUBug20140402", type.getSimpleName());
        spoon.reflect.code.CtLocalVariable<?> var = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtLocalVariable<?>>(spoon.reflect.code.CtLocalVariable.class)).get(0);
        spoon.reflect.code.CtFieldAccess<?> expr = ((spoon.reflect.code.CtFieldAccess<?>) (var.getDefaultExpression()));
        org.junit.Assert.assertEquals("length", expr.getVariable().toString());
        org.junit.Assert.assertEquals("int", expr.getType().getSimpleName());
        spoon.reflect.code.CtFieldAccess<?> fa = expr.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtFieldAccess<?>>(spoon.reflect.code.CtFieldAccess.class)).get(1);
        org.junit.Assert.assertEquals("data", fa.getVariable().toString());
        org.junit.Assert.assertEquals("java.lang.Object[]", fa.getType().toString());
        var.setAssignment(null);
        org.junit.Assert.assertEquals(null, var.getAssignment());
        org.junit.Assert.assertEquals("int a", var.toString());
        spoon.reflect.declaration.CtField<?> field = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtField<?>>(spoon.reflect.declaration.CtField.class)).get(0);
        org.junit.Assert.assertNotNull(field.getAssignment());
        field.setAssignment(null);
        org.junit.Assert.assertEquals(null, field.getAssignment());
        org.junit.Assert.assertEquals("java.lang.Object[] data;", field.toString());
    }

    @org.junit.Test
    public void testBUG20160112() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.fieldaccesses", "BUG20160112");
        org.junit.Assert.assertEquals("BUG20160112", type.getSimpleName());
        spoon.reflect.code.CtOperatorAssignment<?, ?> ass = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtOperatorAssignment<?, ?>>(spoon.reflect.code.CtOperatorAssignment.class)).get(0);
        org.junit.Assert.assertNotNull("z+=a.us", ass);
        spoon.reflect.code.CtExpression<?> righthand = ass.getAssignment();
        org.junit.Assert.assertTrue("a.us should be CtFieldRead", (righthand instanceof spoon.reflect.code.CtFieldRead));
    }

    @org.junit.Test
    public void testTargetedAccessPosition() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.fieldaccesses", "TargetedAccessPosition");
        java.util.List<spoon.reflect.code.CtFieldAccess<?>> vars = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtFieldAccess<?>>(spoon.reflect.code.CtFieldAccess.class));
        org.junit.Assert.assertEquals(2, vars.size());
        org.junit.Assert.assertEquals(vars.get(1), vars.get(0).getTarget());
        org.junit.Assert.assertEquals(6, ((vars.get(0).getPosition().getSourceEnd()) - (vars.get(0).getPosition().getSourceStart())));
        org.junit.Assert.assertEquals(3, ((vars.get(0).getTarget().getPosition().getSourceEnd()) - (vars.get(0).getTarget().getPosition().getSourceStart())));
        org.junit.Assert.assertEquals(0, ((((spoon.reflect.code.CtFieldAccess<?>) (vars.get(0).getTarget())).getTarget().getPosition().getSourceEnd()) - (((spoon.reflect.code.CtFieldAccess<?>) (vars.get(0).getTarget())).getTarget().getPosition().getSourceStart())));
    }

    @org.junit.Test
    public void testFieldAccessInLambda() throws java.lang.Exception {
        spoon.reflect.factory.Factory build = null;
        try {
            build = spoon.testing.utils.ModelUtils.build(spoon.test.fieldaccesses.MyClass.class);
        } catch (java.lang.NullPointerException ignore) {
            org.junit.Assert.fail();
        }
        final spoon.reflect.code.CtFieldAccess logFieldAccess = spoon.reflect.visitor.Query.getElements(build, new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldAccess.class)).get(0);
        org.junit.Assert.assertEquals(java.util.logging.Logger.class, logFieldAccess.getType().getActualClass());
        org.junit.Assert.assertEquals("LOG", logFieldAccess.getVariable().getSimpleName());
        org.junit.Assert.assertEquals(spoon.test.fieldaccesses.MyClass.class, logFieldAccess.getVariable().getDeclaringType().getActualClass());
        java.lang.String expectedLambda = ((("() -> {" + (java.lang.System.lineSeparator())) + "    spoon.test.fieldaccesses.MyClass.LOG.info(\"bla\");") + (java.lang.System.lineSeparator())) + "}";
        org.junit.Assert.assertEquals(expectedLambda, logFieldAccess.getParent(spoon.reflect.code.CtLambda.class).toString());
    }

    @org.junit.Test
    public void testFieldAccessInAnonymousClass() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.fieldaccesses.testclasses.Panini.class);
        final spoon.reflect.declaration.CtType<spoon.test.fieldaccesses.testclasses.Panini> panini = factory.Type().get(spoon.test.fieldaccesses.testclasses.Panini.class);
        final spoon.reflect.code.CtFieldRead fieldInAnonymous = panini.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldRead.class)).get(0);
        org.junit.Assert.assertEquals("ingredient", fieldInAnonymous.getTarget().toString());
        org.junit.Assert.assertEquals("next", fieldInAnonymous.getVariable().getSimpleName());
        org.junit.Assert.assertEquals("ingredient.next", fieldInAnonymous.toString());
    }

    @org.junit.Test
    public void testFieldAccessNoClasspath() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("src/test/resources/import-resources/fr/inria/");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        spoon.reflect.declaration.CtType<?> ctType = launcher.getFactory().Class().get("FooNoClassPath");
        spoon.reflect.code.CtFieldAccess ctFieldAccess = ctType.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldAccess.class)).get(0);
        org.junit.Assert.assertEquals("(game.board.width)", ctFieldAccess.toString());
        spoon.reflect.reference.CtFieldReference ctFieldReferenceWith = ctFieldAccess.getVariable();
        org.junit.Assert.assertEquals("width", ctFieldReferenceWith.getSimpleName());
        spoon.reflect.code.CtFieldAccess ctFieldAccessBoard = ((spoon.reflect.code.CtFieldAccess) (ctFieldAccess.getTarget()));
        org.junit.Assert.assertEquals("game.board", ctFieldAccessBoard.toString());
        spoon.reflect.reference.CtFieldReference ctFieldReferenceBoard = ctFieldAccessBoard.getVariable();
        org.junit.Assert.assertEquals("board", ctFieldReferenceBoard.getSimpleName());
        spoon.reflect.code.CtFieldAccess ctFieldAccessGame = ((spoon.reflect.code.CtFieldAccess) (ctFieldAccessBoard.getTarget()));
        org.junit.Assert.assertEquals("game.board", ctFieldAccessBoard.toString());
        spoon.reflect.reference.CtFieldReference ctFieldReferenceGame = ctFieldAccessGame.getVariable();
        org.junit.Assert.assertEquals("game", ctFieldReferenceGame.getSimpleName());
    }

    @org.junit.Test
    public void testIncrementationOnAVarIsAUnaryOperator() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.fieldaccesses.testclasses.Panini> aMole = spoon.testing.utils.ModelUtils.buildClass(spoon.test.fieldaccesses.testclasses.Panini.class);
        final spoon.reflect.declaration.CtMethod<?> make = aMole.getMethodsByName("make").get(0);
        final java.util.List<spoon.reflect.code.CtUnaryOperator<?>> unaryOperators = make.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtUnaryOperator<?>>(spoon.reflect.code.CtUnaryOperator.class));
        final spoon.reflect.code.CtFieldWrite<java.lang.Object> fieldRead = aMole.getFactory().Core().createFieldWrite();
        fieldRead.setTarget(aMole.getFactory().Code().createThisAccess(aMole.getReference(), true));
        final spoon.reflect.reference.CtFieldReference fieldReference = aMole.getField("i").getReference();
        fieldRead.setVariable(fieldReference);
        org.junit.Assert.assertEquals(2, unaryOperators.size());
        final spoon.reflect.code.CtUnaryOperator<?> first = unaryOperators.get(0);
        org.junit.Assert.assertEquals(spoon.reflect.code.UnaryOperatorKind.POSTINC, first.getKind());
        org.junit.Assert.assertEquals(fieldRead, first.getOperand());
        org.junit.Assert.assertEquals("(i)++", first.toString());
        final spoon.reflect.code.CtUnaryOperator<?> second = unaryOperators.get(1);
        org.junit.Assert.assertEquals(spoon.reflect.code.UnaryOperatorKind.PREINC, second.getKind());
        org.junit.Assert.assertEquals(fieldRead, second.getOperand());
        org.junit.Assert.assertEquals("++(i)", second.toString());
    }

    @org.junit.Test
    public void testFieldWriteWithPlusEqualsOperation() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.fieldaccesses.testclasses.Panini> aPanini = spoon.testing.utils.ModelUtils.buildClass(spoon.test.fieldaccesses.testclasses.Panini.class);
        final spoon.reflect.declaration.CtMethod<?> prepare = aPanini.getMethodsByName("prepare").get(0);
        final java.util.List<spoon.reflect.code.CtFieldWrite<?>> fields = prepare.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldWrite.class));
        org.junit.Assert.assertEquals(1, fields.size());
        org.junit.Assert.assertEquals(aPanini.getField("i").getReference(), fields.get(0).getVariable());
        org.junit.Assert.assertEquals("i += 0", fields.get(0).getParent().toString());
        org.junit.Assert.assertEquals("i", fields.get(0).toString());
        final java.util.List<spoon.reflect.code.CtVariableWrite<?>> variables = prepare.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtVariableWrite.class));
        org.junit.Assert.assertEquals(2, variables.size());
        org.junit.Assert.assertEquals("j += 0", variables.get(1).getParent().toString());
        org.junit.Assert.assertEquals("j", variables.get(1).toString());
        final java.util.List<spoon.reflect.code.CtArrayWrite<?>> arrays = prepare.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtArrayWrite.class));
        org.junit.Assert.assertEquals(1, arrays.size());
        org.junit.Assert.assertEquals("array[0] += 0", arrays.get(0).getParent().toString());
        org.junit.Assert.assertEquals("array[0]", arrays.get(0).toString());
    }

    @org.junit.Test
    public void testTypeDeclaredInAnonymousClass() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.fieldaccesses.testclasses.Pozole> aPozole = spoon.testing.utils.ModelUtils.buildClass(spoon.test.fieldaccesses.testclasses.Pozole.class);
        final java.util.List<spoon.reflect.declaration.CtField> elements = aPozole.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtField.class));
        org.junit.Assert.assertEquals(1, elements.size());
        org.junit.Assert.assertTrue(elements.get(0).getType().getDeclaringType().isAnonymous());
        spoon.testing.Assert.assertThat(elements.get(0)).isEqualTo("private final Test test = new Test();");
    }

    @org.junit.Test
    public void testFieldAccessDeclaredInADefaultClass() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/fieldaccesses/testclasses/Tacos.java");
        launcher.addInputResource("./src/test/java/spoon/test/fieldaccesses/testclasses/internal/Foo.java");
        launcher.addInputResource("./src/test/java/spoon/test/fieldaccesses/testclasses/internal/Bar.java");
        launcher.run();
        final spoon.reflect.declaration.CtType<java.lang.Object> aTacos = launcher.getFactory().Type().get(spoon.test.fieldaccesses.testclasses.Tacos.class);
        final spoon.reflect.declaration.CtType<java.lang.Object> aFoo = launcher.getFactory().Type().get("spoon.test.fieldaccesses.testclasses.internal.Foo");
        final spoon.reflect.code.CtTypeAccess<java.lang.Object> aFooAccess = launcher.getFactory().Code().createTypeAccess(aFoo.getReference());
        final spoon.reflect.declaration.CtType<java.lang.Object> aSubInner = launcher.getFactory().Type().get("spoon.test.fieldaccesses.testclasses.internal.Bar$Inner$SubInner");
        aFoo.addNestedType(aSubInner);
        final spoon.reflect.code.CtTypeAccess<java.lang.Object> aSubInnerAccess = launcher.getFactory().Code().createTypeAccess(aSubInner.getReference());
        final spoon.reflect.declaration.CtType<java.lang.Object> aKnowOrder = launcher.getFactory().Type().get("spoon.test.fieldaccesses.testclasses.internal.Bar$Inner$KnownOrder");
        aFoo.addNestedType(aKnowOrder);
        final spoon.reflect.code.CtTypeAccess<java.lang.Object> aKnownOrderAccess = launcher.getFactory().Code().createTypeAccess(aKnowOrder.getReference());
        final spoon.reflect.declaration.CtMethod<java.lang.Object> aMethod = aTacos.getMethod("m");
        final java.util.List<spoon.reflect.code.CtInvocation<?>> invs = aMethod.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtInvocation.class));
        org.junit.Assert.assertEquals(aFooAccess, ((spoon.reflect.code.CtFieldAccess) (invs.get(0).getArguments().get(0))).getTarget());
        org.junit.Assert.assertEquals("inv(spoon.test.fieldaccesses.testclasses.internal.Foo.i)", invs.get(0).toString());
        org.junit.Assert.assertEquals(aFooAccess, ((spoon.reflect.code.CtFieldAccess) (invs.get(1).getArguments().get(0))).getTarget());
        org.junit.Assert.assertEquals("inv(spoon.test.fieldaccesses.testclasses.internal.Foo.i)", invs.get(1).toString());
        org.junit.Assert.assertEquals(aSubInnerAccess, ((spoon.reflect.code.CtFieldAccess) (invs.get(2).getArguments().get(0))).getTarget());
        org.junit.Assert.assertEquals("inv(spoon.test.fieldaccesses.testclasses.internal.Foo.SubInner.j)", invs.get(2).toString());
        org.junit.Assert.assertEquals(aSubInnerAccess, ((spoon.reflect.code.CtFieldAccess) (invs.get(3).getArguments().get(0))).getTarget());
        org.junit.Assert.assertEquals("inv(spoon.test.fieldaccesses.testclasses.internal.Foo.SubInner.j)", invs.get(3).toString());
        org.junit.Assert.assertEquals(aKnownOrderAccess, ((spoon.reflect.code.CtFieldAccess) (invs.get(4).getArguments().get(0))).getTarget());
        org.junit.Assert.assertEquals("runIteratorTest(spoon.test.fieldaccesses.testclasses.internal.Foo.KnownOrder.KNOWN_ORDER)", invs.get(4).toString());
        org.junit.Assert.assertEquals(aKnownOrderAccess, ((spoon.reflect.code.CtFieldAccess) (invs.get(5).getArguments().get(0))).getTarget());
        org.junit.Assert.assertEquals("runIteratorTest(spoon.test.fieldaccesses.testclasses.internal.Foo.KnownOrder.KNOWN_ORDER)", invs.get(5).toString());
        final spoon.reflect.declaration.CtParameter<?> aKnownOrderParameter = aTacos.getMethod("runIteratorTest", aKnowOrder.getReference()).getParameters().get(0);
        org.junit.Assert.assertEquals(aKnowOrder.getReference(), aKnownOrderParameter.getType());
        org.junit.Assert.assertEquals("spoon.test.fieldaccesses.testclasses.internal.Foo.KnownOrder knownOrder", aKnownOrderParameter.toString());
        final spoon.reflect.declaration.CtParameter<?> aSubInnerParameter = aTacos.getMethod("inv", aSubInner.getReference()).getParameters().get(0);
        org.junit.Assert.assertEquals(aSubInner.getReference(), aSubInnerParameter.getType());
        org.junit.Assert.assertEquals("spoon.test.fieldaccesses.testclasses.internal.Foo.SubInner foo", aSubInnerParameter.toString());
    }

    @org.junit.Test
    public void testTypeOfFieldAccess() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<spoon.test.fieldaccesses.testclasses.Panini> aPanini = spoon.testing.utils.ModelUtils.buildClass(spoon.test.fieldaccesses.testclasses.Panini.class);
        java.util.List<spoon.reflect.code.CtFieldAccess> fieldAccesses = aPanini.getMethod("prepare").getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldAccess.class));
        org.junit.Assert.assertEquals(1, fieldAccesses.size());
        org.junit.Assert.assertNotNull(fieldAccesses.get(0).getType());
        org.junit.Assert.assertEquals(fieldAccesses.get(0).getVariable().getType(), fieldAccesses.get(0).getType());
    }

    @org.junit.Test
    public void testFieldAccessWithoutAnyImport() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/fieldaccesses/testclasses/Kuu.java");
        launcher.addInputResource("./src/test/java/spoon/test/fieldaccesses/testclasses/Mole.java");
        launcher.run();
        final spoon.reflect.declaration.CtType<spoon.test.fieldaccesses.testclasses.Kuu> aType = launcher.getFactory().Type().get(spoon.test.fieldaccesses.testclasses.Kuu.class);
        final spoon.reflect.visitor.DefaultJavaPrettyPrinter printer = new spoon.reflect.visitor.DefaultJavaPrettyPrinter(aType.getFactory().getEnvironment());
        org.junit.Assert.assertEquals(0, printer.computeImports(aType).size());
        org.junit.Assert.assertEquals("spoon.test.fieldaccesses.testclasses.Mole.Delicious delicious", aType.getMethodsByName("m").get(0).getParameters().get(0).toString());
    }

    @org.junit.Test
    public void testFieldAccessOnUnknownType() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/FieldAccessRes.java");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.buildModel();
        class CounterScanner extends spoon.reflect.visitor.CtScanner {
            private int visited = 0;

            @java.lang.Override
            public <T> void visitCtFieldWrite(spoon.reflect.code.CtFieldWrite<T> fieldWrite) {
                (visited)++;
                org.junit.Assert.assertEquals("array", ((spoon.reflect.code.CtVariableWrite) (fieldWrite.getTarget())).getVariable().getSimpleName());
                org.junit.Assert.assertEquals("length", fieldWrite.getVariable().getSimpleName());
            }
        }
        CounterScanner scanner = new CounterScanner();
        launcher.getFactory().Class().get("FieldAccessRes").accept(scanner);
        org.junit.Assert.assertEquals(1, scanner.visited);
    }

    @org.junit.Test
    public void testGetReference() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setShouldCompile(true);
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/fieldaccesses/testclasses/");
        launcher.getEnvironment().setAutoImports(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<spoon.test.fieldaccesses.testclasses.B> aClass = launcher.getFactory().Class().get(spoon.test.fieldaccesses.testclasses.B.class);
        org.junit.Assert.assertEquals("A.myField", aClass.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldWrite.class)).get(0).toString());
        org.junit.Assert.assertEquals("finalField", aClass.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldWrite.class)).get(1).toString());
    }

    @org.junit.Test
    public void testFieldAccessAutoExplicit() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass mouse = ((spoon.reflect.declaration.CtClass) (spoon.testing.utils.ModelUtils.buildClass(spoon.test.fieldaccesses.Mouse.class)));
        spoon.reflect.declaration.CtMethod method = mouse.filterChildren((spoon.reflect.declaration.CtMethod m) -> "meth1".equals(m.getSimpleName())).first();
        spoon.reflect.reference.CtFieldReference ageFR = method.filterChildren((spoon.reflect.reference.CtFieldReference fr) -> "age".equals(fr.getSimpleName())).first();
        org.junit.Assert.assertEquals("age", ageFR.getParent().toString());
        method.getBody().insertBegin(((spoon.reflect.code.CtStatement) (mouse.getFactory().createCodeSnippetStatement("int age = 1").compile())));
        org.junit.Assert.assertEquals("this.age", ageFR.getParent().toString());
    }
}

