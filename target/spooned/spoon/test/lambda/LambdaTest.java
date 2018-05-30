package spoon.test.lambda;


public class LambdaTest {
    private spoon.Launcher launcher;

    private spoon.reflect.factory.Factory factory;

    private spoon.reflect.declaration.CtType<spoon.test.lambda.testclasses.Foo> foo;

    private spoon.reflect.declaration.CtType<spoon.test.lambda.testclasses.Bar> bar;

    private spoon.reflect.declaration.CtType<java.lang.Object> panini;

    private spoon.reflect.declaration.CtType<java.lang.Object> tacos;

    private spoon.reflect.declaration.CtType<spoon.test.lambda.testclasses.LambdaRxJava> lambdaRxJava;

    private spoon.SpoonModelBuilder compiler;

    @org.junit.Before
    public void setUp() throws java.lang.Exception {
        launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        this.factory = launcher.getFactory();
        factory.getEnvironment().setComplianceLevel(8);
        compiler = launcher.createCompiler(this.factory);
        compiler.addInputSource(new java.io.File("./src/test/java/spoon/test/lambda/testclasses/"));
        compiler.build();
        foo = factory.Type().get(spoon.test.lambda.testclasses.Foo.class);
        bar = factory.Type().get(spoon.test.lambda.testclasses.Bar.class);
        panini = factory.Type().get(spoon.test.lambda.testclasses.Panini.class);
        tacos = factory.Type().get(spoon.test.lambda.testclasses.Tacos.class);
        lambdaRxJava = factory.Type().get(spoon.test.lambda.testclasses.LambdaRxJava.class);
    }

    @org.junit.Test
    public void testLambdaExpressionWithExpressionBodyAndWithoutParameter() throws java.lang.Exception {
        final spoon.reflect.code.CtLambda<?> lambda = getLambdaInFooByNumber(0);
        assertTypedBy(spoon.test.lambda.testclasses.Foo.Check.class, lambda.getType());
        assertParametersSizeIs(0, lambda.getParameters());
        assertHasExpressionBody(lambda);
        assertIsWellPrinted("((spoon.test.lambda.testclasses.Foo.Check) (() -> false))", lambda);
    }

    @org.junit.Test
    public void testTypeAccessInLambdaNoClassPath() {
        final spoon.Launcher runLaunch = new spoon.Launcher();
        runLaunch.getEnvironment().setNoClasspath(true);
        runLaunch.addInputResource("./src/test/resources/noclasspath/lambdas/TypeAccessInLambda.java");
        runLaunch.buildModel();
        org.junit.Assert.assertEquals("The token 'Strings' has not been parsed as CtTypeAccess", 1, runLaunch.getModel().getElements(new spoon.reflect.visitor.Filter<spoon.reflect.code.CtTypeAccess>() {
            @java.lang.Override
            public boolean matches(final spoon.reflect.code.CtTypeAccess element) {
                return element.getAccessedType().getSimpleName().equals("Strings");
            }
        }).size());
    }

    @org.junit.Test
    public void testFieldAccessInLambdaNoClassPath() {
        final spoon.Launcher runLaunch = new spoon.Launcher();
        runLaunch.getEnvironment().setNoClasspath(true);
        runLaunch.addInputResource("./src/test/resources/noclasspath/lambdas/FieldAccessInLambda.java");
        runLaunch.addInputResource("./src/test/resources/noclasspath/lambdas/imported/SeparateInterfaceWithField.java");
        runLaunch.buildModel();
        final java.util.List<spoon.reflect.code.CtFieldAccess> fieldAccesses = runLaunch.getModel().getElements(new spoon.reflect.visitor.Filter<spoon.reflect.code.CtFieldAccess>() {
            @java.lang.Override
            public boolean matches(final spoon.reflect.code.CtFieldAccess element) {
                final java.lang.String name = element.getVariable().getSimpleName();
                return (((((((name.equals("localField")) || (name.equals("pathSeparator"))) || (name.equals("fieldInSeparateInterface"))) || (name.equals("fieldInClassBase"))) || (name.equals("fieldInClass"))) || (name.equals("fieldInInterfaceBase"))) || (name.equals("fieldInInterface"))) || (name.equals("iAmToLazyForAnotherFieldName"));
            }
        });
        org.junit.Assert.assertEquals(8, fieldAccesses.size());
    }

    @org.junit.Test
    public void testFieldAccessInLambdaNoClassPathExternal1Example() {
        final spoon.Launcher runLaunch = new spoon.Launcher();
        runLaunch.getEnvironment().setNoClasspath(true);
        runLaunch.addInputResource("./src/test/resources/noclasspath/lambdas/external1");
        runLaunch.buildModel();
        org.junit.Assert.assertEquals(3, runLaunch.getModel().getElements(new spoon.reflect.visitor.Filter<spoon.reflect.code.CtFieldAccess>() {
            @java.lang.Override
            public boolean matches(final spoon.reflect.code.CtFieldAccess element) {
                return element.getVariable().getSimpleName().equals("DEFAULT_RATING");
            }
        }).size());
    }

    @org.junit.Test
    public void testLambdaExpressionWithExpressionBodyAndWithoutTypeForParameter() throws java.lang.Exception {
        final spoon.reflect.code.CtLambda<?> lambda = getLambdaInFooByNumber(1);
        assertTypedBy(java.util.function.Predicate.class, lambda.getType());
        assertParametersSizeIs(1, lambda.getParameters());
        final spoon.reflect.declaration.CtParameter<?> parameter = ((spoon.reflect.declaration.CtParameter<?>) (lambda.getParameters().get(0)));
        assertParameterTypedBy(spoon.test.lambda.testclasses.Foo.Person.class, parameter);
        assertParameterIsNamedBy("p", parameter);
        assertHasExpressionBody(lambda);
        assertIsWellPrinted("((java.util.function.Predicate<spoon.test.lambda.testclasses.Foo.Person>) (( p) -> (p.age) > 10))", lambda);
    }

    @org.junit.Test
    public void testLambdaExpressionWithExpressionBodyAndWithMultiParameters() throws java.lang.Exception {
        final spoon.reflect.code.CtLambda<?> lambda = getLambdaInFooByNumber(2);
        assertTypedBy(spoon.test.lambda.testclasses.Foo.CheckPersons.class, lambda.getType());
        assertParametersSizeIs(2, lambda.getParameters());
        final spoon.reflect.declaration.CtParameter<?> parameter1 = ((spoon.reflect.declaration.CtParameter<?>) (lambda.getParameters().get(0)));
        assertParameterTypedBy(spoon.test.lambda.testclasses.Foo.Person.class, parameter1);
        assertParameterIsNamedBy("p1", parameter1);
        final spoon.reflect.declaration.CtParameter<?> parameter2 = ((spoon.reflect.declaration.CtParameter<?>) (lambda.getParameters().get(1)));
        assertParameterTypedBy(spoon.test.lambda.testclasses.Foo.Person.class, parameter2);
        assertParameterIsNamedBy("p2", parameter2);
        assertHasExpressionBody(lambda);
        assertIsWellPrinted("((spoon.test.lambda.testclasses.Foo.CheckPersons) (( p1, p2) -> ((p1.age) - (p2.age)) > 0))", lambda);
    }

    @org.junit.Test
    public void testLambdaExpressionWithExpressionBodyAndWithParameterTyped() throws java.lang.Exception {
        final spoon.reflect.code.CtLambda<?> lambda = getLambdaInFooByNumber(3);
        assertTypedBy(java.util.function.Predicate.class, lambda.getType());
        assertParametersSizeIs(1, lambda.getParameters());
        final spoon.reflect.declaration.CtParameter<?> parameter = ((spoon.reflect.declaration.CtParameter<?>) (lambda.getParameters().get(0)));
        assertParameterTypedBy(spoon.test.lambda.testclasses.Foo.Person.class, parameter);
        assertParameterIsNamedBy("p", parameter);
        assertHasExpressionBody(lambda);
        assertIsWellPrinted("((java.util.function.Predicate<spoon.test.lambda.testclasses.Foo.Person>) ((spoon.test.lambda.testclasses.Foo.Person p) -> (p.age) > 10))", lambda);
    }

    @org.junit.Test
    public void testLambdaExpressionWithExpressionBodyAndWithMultiParametersTyped() throws java.lang.Exception {
        final spoon.reflect.code.CtLambda<?> lambda = getLambdaInFooByNumber(4);
        assertTypedBy(spoon.test.lambda.testclasses.Foo.CheckPersons.class, lambda.getType());
        assertParametersSizeIs(2, lambda.getParameters());
        final spoon.reflect.declaration.CtParameter<?> parameter1 = ((spoon.reflect.declaration.CtParameter<?>) (lambda.getParameters().get(0)));
        assertParameterTypedBy(spoon.test.lambda.testclasses.Foo.Person.class, parameter1);
        assertParameterIsNamedBy("p1", parameter1);
        final spoon.reflect.declaration.CtParameter<?> parameter2 = ((spoon.reflect.declaration.CtParameter<?>) (lambda.getParameters().get(1)));
        assertParameterTypedBy(spoon.test.lambda.testclasses.Foo.Person.class, parameter2);
        assertParameterIsNamedBy("p2", parameter2);
        assertHasExpressionBody(lambda);
        assertIsWellPrinted("((spoon.test.lambda.testclasses.Foo.CheckPersons) ((spoon.test.lambda.testclasses.Foo.Person p1,spoon.test.lambda.testclasses.Foo.Person p2) -> ((p1.age) - (p2.age)) > 0))", lambda);
    }

    @org.junit.Test
    public void testLambdaExpressionWithStatementBodyAndWithoutParameters() throws java.lang.Exception {
        final spoon.reflect.code.CtLambda<?> lambda = getLambdaInFooByNumber(5);
        assertTypedBy(spoon.test.lambda.testclasses.Foo.Check.class, lambda.getType());
        assertParametersSizeIs(0, lambda.getParameters());
        assertStatementBody(lambda);
        assertIsWellPrinted((((((("((spoon.test.lambda.testclasses.Foo.Check) (() -> {" + (java.lang.System.lineSeparator())) + "    java.lang.System.err.println(\"\");") + (java.lang.System.lineSeparator())) + "    return false;") + (java.lang.System.lineSeparator())) + "}))"), lambda);
    }

    @org.junit.Test
    public void testLambdaExpressionWithStatementBodyAndWithParameter() throws java.lang.Exception {
        final spoon.reflect.code.CtLambda<?> lambda = getLambdaInFooByNumber(6);
        assertTypedBy(java.util.function.Predicate.class, lambda.getType());
        assertParametersSizeIs(1, lambda.getParameters());
        final spoon.reflect.declaration.CtParameter<?> parameter = ((spoon.reflect.declaration.CtParameter<?>) (lambda.getParameters().get(0)));
        assertParameterTypedBy(spoon.test.lambda.testclasses.Foo.Person.class, parameter);
        assertParameterIsNamedBy("p", parameter);
        assertStatementBody(lambda);
        assertIsWellPrinted((((((("((java.util.function.Predicate<spoon.test.lambda.testclasses.Foo.Person>) (( p) -> {" + (java.lang.System.lineSeparator())) + "    p.doSomething();") + (java.lang.System.lineSeparator())) + "    return (p.age) > 10;") + (java.lang.System.lineSeparator())) + "}))"), lambda);
    }

    @org.junit.Test
    public void testLambdaExpressionInIfConditional() throws java.lang.Exception {
        final spoon.reflect.code.CtLambda<?> lambda = getLambdaInFooByNumber(7);
        assertTypedBy(java.util.function.Predicate.class, lambda.getType());
        assertParametersSizeIs(1, lambda.getParameters());
        final spoon.reflect.declaration.CtParameter<?> parameter = ((spoon.reflect.declaration.CtParameter<?>) (lambda.getParameters().get(0)));
        assertParameterTypedBy(spoon.test.lambda.testclasses.Foo.Person.class, parameter);
        assertParameterIsNamedBy("p", parameter);
        assertHasExpressionBody(lambda);
        final spoon.reflect.declaration.CtMethod<?> method = foo.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "m8")).get(0);
        final spoon.reflect.code.CtIf condition = method.getElements(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtIf>(spoon.reflect.code.CtIf.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtIf element) {
                return true;
            }
        }).get(0);
        final java.lang.String expected = ((("if (((java.util.function.Predicate<spoon.test.lambda.testclasses.Foo.Person>) (( p) -> (p.age) > 18)).test(new spoon.test.lambda.testclasses.Foo.Person(10))) {" + (java.lang.System.lineSeparator())) + "    java.lang.System.err.println(\"Enjoy, you have more than 18.\");") + (java.lang.System.lineSeparator())) + "}";
        org.junit.Assert.assertEquals("Condition must be well printed", expected, condition.toString());
    }

    @org.junit.Test
    public void testCompileLambdaGeneratedBySpoon() throws java.lang.Exception {
        launcher.setSourceOutputDirectory(new java.io.File("./target/spooned/"));
        launcher.getModelBuilder().generateProcessedSourceFiles(spoon.OutputType.CLASSES);
        spoon.testing.utils.ModelUtils.canBeBuilt(new java.io.File("./target/spooned/spoon/test/lambda/testclasses/"), 8);
    }

    @org.junit.Test
    public void testTypeParameterOfLambdaWithoutType() throws java.lang.Exception {
        final spoon.reflect.code.CtLambda<?> lambda1 = bar.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtLambda<?>>(spoon.reflect.code.CtLambda.class)).get(0);
        org.junit.Assert.assertEquals(1, lambda1.getParameters().size());
        final spoon.reflect.declaration.CtParameter<?> ctParameterFirstLambda = lambda1.getParameters().get(0);
        org.junit.Assert.assertEquals("s", ctParameterFirstLambda.getSimpleName());
        org.junit.Assert.assertTrue(ctParameterFirstLambda.getType().isImplicit());
        org.junit.Assert.assertEquals("", ctParameterFirstLambda.getType().toString());
        org.junit.Assert.assertEquals("SingleSubscriber", ctParameterFirstLambda.getType().getSimpleName());
    }

    @org.junit.Test
    public void testTypeParameterOfLambdaWithoutType2() throws java.lang.Exception {
        final spoon.reflect.code.CtLambda<?> lambda2 = bar.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtLambda<?>>(spoon.reflect.code.CtLambda.class)).get(1);
        org.junit.Assert.assertEquals(2, lambda2.getParameters().size());
        final spoon.reflect.declaration.CtParameter<?> ctParameterSecondLambda = lambda2.getParameters().get(0);
        org.junit.Assert.assertEquals("v", ctParameterSecondLambda.getSimpleName());
        org.junit.Assert.assertTrue(ctParameterSecondLambda.getType().isImplicit());
        org.junit.Assert.assertEquals("", ctParameterSecondLambda.getType().toString());
        org.junit.Assert.assertEquals("?", ctParameterSecondLambda.getType().getSimpleName());
    }

    @org.junit.Test
    public void testTypeParameterWithImplicitArrayType() throws java.lang.Exception {
        final spoon.reflect.code.CtLambda<?> lambda = panini.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtLambda<?>>(spoon.reflect.code.CtLambda.class)).get(0);
        org.junit.Assert.assertEquals(1, lambda.getParameters().size());
        final spoon.reflect.declaration.CtParameter<?> ctParameter = lambda.getParameters().get(0);
        org.junit.Assert.assertEquals("a", ctParameter.getSimpleName());
        org.junit.Assert.assertTrue(ctParameter.getType().isImplicit());
        org.junit.Assert.assertEquals("", ctParameter.getType().toString());
        org.junit.Assert.assertEquals("Object[]", ctParameter.getType().getSimpleName());
        final spoon.reflect.reference.CtArrayTypeReference typeParameter = ((spoon.reflect.reference.CtArrayTypeReference) (ctParameter.getType()));
        org.junit.Assert.assertTrue(typeParameter.getComponentType().isImplicit());
        org.junit.Assert.assertEquals("", typeParameter.getComponentType().toString());
        org.junit.Assert.assertEquals("Object", typeParameter.getComponentType().getSimpleName());
    }

    @org.junit.Test
    public void testLambdaWithPrimitiveParameter() throws java.lang.Exception {
        final spoon.reflect.code.CtLambda<?> lambda = tacos.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtLambda<?>>(spoon.reflect.code.CtLambda.class)).get(0);
        org.junit.Assert.assertEquals(2, lambda.getParameters().size());
        final spoon.reflect.declaration.CtParameter<?> firstParam = lambda.getParameters().get(0);
        org.junit.Assert.assertEquals("rs", firstParam.getSimpleName());
        org.junit.Assert.assertTrue(firstParam.getType().isImplicit());
        org.junit.Assert.assertEquals("", firstParam.getType().toString());
        org.junit.Assert.assertEquals("ResultSet", firstParam.getType().getSimpleName());
        final spoon.reflect.declaration.CtParameter<?> secondParam = lambda.getParameters().get(1);
        org.junit.Assert.assertEquals("i", secondParam.getSimpleName());
        org.junit.Assert.assertTrue(secondParam.getType().isImplicit());
        org.junit.Assert.assertEquals("", secondParam.getType().toString());
        org.junit.Assert.assertEquals("int", secondParam.getType().getSimpleName());
    }

    @org.junit.Test
    public void testBuildExecutableReferenceFromLambda() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.lambda.testclasses.Kuu> aType = spoon.testing.utils.ModelUtils.buildClass(spoon.test.lambda.testclasses.Kuu.class);
        final spoon.reflect.code.CtLambda<?> aLambda = aType.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtLambda<?>>(spoon.reflect.code.CtLambda.class)).get(0);
        java.util.List<? extends spoon.reflect.reference.CtParameterReference<?>> collect = null;
        try {
            collect = aLambda.getParameters().stream().map(spoon.reflect.declaration.CtParameter::getReference).collect(java.util.stream.Collectors.toList());
        } catch (java.lang.ClassCastException e) {
            org.junit.Assert.fail();
        }
        org.junit.Assert.assertNotNull(collect);
        org.junit.Assert.assertEquals(1, collect.size());
    }

    @org.junit.Test
    public void testEqualsLambdaParameterRef() throws java.lang.Exception {
        spoon.reflect.code.CtLambda<?> lambda = getLambdaInFooByNumber(8);
        spoon.reflect.declaration.CtParameter<?> param = ((spoon.reflect.declaration.CtParameter<?>) (lambda.getParameters().get(0)));
        spoon.reflect.reference.CtParameterReference paramRef1 = param.getReference();
        spoon.reflect.reference.CtParameterReference paramRef2 = lambda.filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.reference.CtParameterReference.class)).first();
        org.junit.Assert.assertTrue(paramRef1.equals(paramRef2));
    }

    @org.junit.Test
    public void testLambdaMethod() throws java.lang.Exception {
        spoon.reflect.code.CtLambda<?> lambda = getLambdaInFooByNumber(8);
        spoon.reflect.declaration.CtMethod<?> method = lambda.getOverriddenMethod();
        spoon.reflect.reference.CtTypeReference<?> iface = lambda.getType();
        org.junit.Assert.assertEquals(java.util.function.Consumer.class.getName(), iface.getQualifiedName());
        org.junit.Assert.assertEquals(iface.getTypeDeclaration().getMethodsByName("accept").get(0), method);
    }

    @org.junit.Test
    public void testGetOverriddenMethodWithFunction() throws java.lang.Exception {
        java.util.List<spoon.reflect.code.CtLambda<?>> allLambdas = lambdaRxJava.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtLambda<?>>(spoon.reflect.code.CtLambda.class));
        org.junit.Assert.assertEquals(1, allLambdas.size());
        spoon.reflect.code.CtLambda<?> lambda = allLambdas.get(0);
        spoon.reflect.declaration.CtMethod<?> method = lambda.getOverriddenMethod();
        spoon.reflect.reference.CtTypeReference<?> iface = lambda.getType();
        org.junit.Assert.assertEquals(spoon.test.lambda.testclasses.LambdaRxJava.NbpOperator.class.getName(), iface.getQualifiedName());
    }

    @org.junit.Test
    public void testLambdaFilter() throws java.lang.Exception {
        java.util.List<java.lang.String> methodNames = foo.filterChildren(new spoon.reflect.visitor.filter.LambdaFilter(((spoon.reflect.declaration.CtInterface<?>) (foo.getNestedType("CheckPerson"))))).map((spoon.reflect.code.CtLambda l) -> l.getParent(spoon.reflect.declaration.CtMethod.class).getSimpleName()).list();
        assertHasStrings(methodNames);
        methodNames = foo.filterChildren(new spoon.reflect.visitor.filter.LambdaFilter(foo.getNestedType("Check").getReference())).map((spoon.reflect.code.CtLambda l) -> l.getParent(spoon.reflect.declaration.CtMethod.class).getSimpleName()).list();
        assertHasStrings(methodNames, "m", "m6");
        methodNames = foo.filterChildren(new spoon.reflect.visitor.filter.LambdaFilter().addImplementingInterface(((spoon.reflect.declaration.CtInterface<?>) (foo.getNestedType("CheckPersons"))))).map((spoon.reflect.code.CtLambda l) -> l.getParent(spoon.reflect.declaration.CtMethod.class).getSimpleName()).list();
        assertHasStrings(methodNames, "m3", "m5");
        methodNames = foo.filterChildren(new spoon.reflect.visitor.filter.LambdaFilter().addImplementingInterface(factory.createCtTypeReference(java.util.function.Predicate.class))).map((spoon.reflect.code.CtLambda l) -> l.getParent(spoon.reflect.declaration.CtMethod.class).getSimpleName()).list();
        assertHasStrings(methodNames, "m2", "m4", "m7", "m8");
    }

    private void assertHasStrings(java.util.List<java.lang.String> methodNames, java.lang.String... strs) {
        for (java.lang.String str : strs) {
            org.junit.Assert.assertTrue((("List should contain " + str) + " but it is missing."), methodNames.remove(str));
        }
        if ((methodNames.size()) > 0) {
            org.junit.Assert.fail(("List should't contain " + methodNames));
        }
    }

    private void assertTypedBy(java.lang.Class<?> expectedType, spoon.reflect.reference.CtTypeReference<?> type) {
        org.junit.Assert.assertEquals("Lambda must be typed", expectedType, type.getActualClass());
    }

    private void assertParametersSizeIs(int nbParameters, java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters) {
        if (nbParameters == 0) {
            org.junit.Assert.assertEquals("Lambda hasn't parameters", nbParameters, parameters.size());
        }else {
            org.junit.Assert.assertEquals("Lambda has parameters", nbParameters, parameters.size());
        }
    }

    private void assertParameterTypedBy(java.lang.Class<?> expectedType, spoon.reflect.declaration.CtParameter<?> parameter) {
        org.junit.Assert.assertNotNull("Lambda has a parameter typed", parameter.getType());
        org.junit.Assert.assertEquals("Lambda has a parameter typed by", expectedType, parameter.getType().getActualClass());
    }

    private void assertHasExpressionBody(spoon.reflect.code.CtLambda<?> lambda) {
        org.junit.Assert.assertNotNull("Lambda has an expression for its body.", lambda.getExpression());
        org.junit.Assert.assertNull("Lambda don't have a list of statements (body) for its body", lambda.getBody());
    }

    private void assertStatementBody(spoon.reflect.code.CtLambda<?> lambda) {
        org.junit.Assert.assertNotNull("Lambda has a body with statements.", lambda.getBody());
        org.junit.Assert.assertNull("Lambda don't have an expression for its body", lambda.getExpression());
    }

    private void assertParameterIsNamedBy(java.lang.String name, spoon.reflect.declaration.CtParameter<?> parameter) {
        org.junit.Assert.assertEquals("Lambda has a parameter with a name", name, parameter.getSimpleName());
    }

    private void assertIsWellPrinted(java.lang.String expected, spoon.reflect.code.CtLambda<?> lambda) {
        org.junit.Assert.assertEquals("Lambda must be well printed", expected, lambda.toString());
    }

    private spoon.reflect.code.CtLambda<?> getLambdaInFooByNumber(int number) {
        return foo.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtLambda<?>>(spoon.reflect.code.CtLambda.class)).get(number);
    }

    @org.junit.Test
    public void testGetDeclarationOnTypeParameterFromLambda() {
        java.util.List<spoon.reflect.reference.CtTypeParameterReference> listCtTPR = launcher.getModel().getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.reference.CtTypeParameterReference.class));
        for (spoon.reflect.reference.CtTypeParameterReference typeParameterReference : listCtTPR) {
            if ((!(typeParameterReference instanceof spoon.reflect.reference.CtWildcardReference)) && ((typeParameterReference.getDeclaration()) == null)) {
                java.lang.System.err.println(((((typeParameterReference.getSimpleName()) + " from parent ") + (typeParameterReference.getParent(spoon.reflect.declaration.CtClass.class).getPosition())) + "  has null declaration"));
                typeParameterReference.getDeclaration();
                org.junit.Assert.fail();
            }
        }
    }
}

