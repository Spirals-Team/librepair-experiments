package spoon.test.type;


public class TypeTest {
    @org.junit.Test
    public void testTypeAccessForDotClass() throws java.lang.Exception {
        final java.lang.String target = "./target/type";
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/type/testclasses");
        launcher.setSourceOutputDirectory(target);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<spoon.test.type.testclasses.Pozole> aPozole = launcher.getFactory().Class().get(spoon.test.type.testclasses.Pozole.class);
        final spoon.reflect.declaration.CtMethod<?> make = aPozole.getMethodsByName("make").get(0);
        final java.util.List<spoon.reflect.code.CtFieldRead<?>> fieldClasses = make.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtFieldRead<?>>(spoon.reflect.code.CtFieldRead.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtFieldRead<?> element) {
                return ("class".equals(element.getVariable().getSimpleName())) && (super.matches(element));
            }
        });
        org.junit.Assert.assertEquals(4, fieldClasses.size());
        for (spoon.reflect.code.CtFieldRead<?> fieldClass : fieldClasses) {
            org.junit.Assert.assertTrue(((fieldClass.getTarget()) instanceof spoon.reflect.code.CtTypeAccess));
        }
        spoon.testing.utils.ModelUtils.canBeBuilt(target, 8, true);
    }

    @org.junit.Test
    public void testTypeAccessOnPrimitive() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("class X {" + ((("public void foo() {" + " Class klass=null;") + "  boolean x= (klass == short.class);") + "}};"))).compile();
        spoon.reflect.declaration.CtMethod<?> foo = ((spoon.reflect.declaration.CtMethod<?>) (clazz.getMethods().toArray()[0]));
        spoon.reflect.code.CtBlock<?> body = foo.getBody();
        spoon.reflect.code.CtLocalVariable<?> ass = body.getStatement(1);
        spoon.reflect.code.CtBinaryOperator<?> op = ((spoon.reflect.code.CtBinaryOperator<?>) (ass.getDefaultExpression()));
        org.junit.Assert.assertEquals("Class", op.getLeftHandOperand().getType().getSimpleName());
        org.junit.Assert.assertFalse(op.getLeftHandOperand().getType().isPrimitive());
        org.junit.Assert.assertEquals("Class", op.getRightHandOperand().getType().getSimpleName());
        org.junit.Assert.assertTrue(((op.getRightHandOperand()) instanceof spoon.reflect.code.CtFieldRead));
        org.junit.Assert.assertFalse(op.getRightHandOperand().getType().isPrimitive());
    }

    @org.junit.Test
    public void testTypeAccessForTypeAccessInInstanceOf() throws java.lang.Exception {
        final java.lang.String target = "./target/type";
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/type/testclasses");
        launcher.setSourceOutputDirectory(target);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<spoon.test.type.testclasses.Pozole> aPozole = launcher.getFactory().Class().get(spoon.test.type.testclasses.Pozole.class);
        final spoon.reflect.declaration.CtMethod<?> eat = aPozole.getMethodsByName("eat").get(0);
        final java.util.List<spoon.reflect.code.CtTypeAccess<?>> typeAccesses = eat.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtTypeAccess<?>>(spoon.reflect.code.CtTypeAccess.class));
        org.junit.Assert.assertEquals(2, typeAccesses.size());
        org.junit.Assert.assertTrue(((typeAccesses.get(0).getParent()) instanceof spoon.reflect.code.CtBinaryOperator));
        org.junit.Assert.assertEquals(spoon.reflect.code.BinaryOperatorKind.INSTANCEOF, ((spoon.reflect.code.CtBinaryOperator) (typeAccesses.get(0).getParent())).getKind());
        org.junit.Assert.assertEquals("a instanceof java.lang.String", typeAccesses.get(0).getParent().toString());
        org.junit.Assert.assertTrue(((typeAccesses.get(1).getParent()) instanceof spoon.reflect.code.CtBinaryOperator));
        org.junit.Assert.assertEquals(spoon.reflect.code.BinaryOperatorKind.INSTANCEOF, ((spoon.reflect.code.CtBinaryOperator) (typeAccesses.get(1).getParent())).getKind());
        org.junit.Assert.assertEquals("a instanceof java.util.Collection<?>", typeAccesses.get(1).getParent().toString());
    }

    @org.junit.Test
    public void testTypeAccessOfArrayObjectInFullyQualifiedName() throws java.lang.Exception {
        final java.lang.String target = "./target/type";
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/type/testclasses");
        launcher.setSourceOutputDirectory(target);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<spoon.test.type.testclasses.Pozole> aPozole = launcher.getFactory().Class().get(spoon.test.type.testclasses.Pozole.class);
        final spoon.reflect.declaration.CtMethod<?> season = aPozole.getMethodsByName("season").get(0);
        final java.util.List<spoon.reflect.code.CtTypeAccess<?>> typeAccesses = season.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtTypeAccess<?>>(spoon.reflect.code.CtTypeAccess.class));
        org.junit.Assert.assertEquals(2, typeAccesses.size());
        org.junit.Assert.assertTrue(((typeAccesses.get(0).getParent()) instanceof spoon.reflect.code.CtBinaryOperator));
        org.junit.Assert.assertEquals(spoon.reflect.code.BinaryOperatorKind.INSTANCEOF, ((spoon.reflect.code.CtBinaryOperator) (typeAccesses.get(0).getParent())).getKind());
        org.junit.Assert.assertEquals((("a instanceof java.lang.@spoon.test.annotation.testclasses.TypeAnnotation(integer = 1)" + (java.lang.System.lineSeparator())) + "Object[]"), typeAccesses.get(0).getParent().toString());
        org.junit.Assert.assertTrue(((typeAccesses.get(1).getParent()) instanceof spoon.reflect.code.CtBinaryOperator));
        org.junit.Assert.assertEquals(spoon.reflect.code.BinaryOperatorKind.INSTANCEOF, ((spoon.reflect.code.CtBinaryOperator) (typeAccesses.get(1).getParent())).getKind());
        org.junit.Assert.assertEquals("a instanceof java.lang.Object[]", typeAccesses.get(1).getParent().toString());
        spoon.testing.utils.ModelUtils.canBeBuilt(target, 8, true);
    }

    @org.junit.Test
    public void test() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/TorIntegration.java");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.buildModel();
        spoon.reflect.declaration.CtType<?> ctType = launcher.getFactory().Class().getAll().get(0);
        java.util.List<spoon.reflect.code.CtNewClass> elements = ctType.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtNewClass.class));
        org.junit.Assert.assertEquals(4, elements.size());
        for (int i = 0; i < (elements.size()); i++) {
            spoon.reflect.code.CtNewClass ctNewClass = elements.get(i);
            org.junit.Assert.assertEquals("android.content.DialogInterface$OnClickListener", ctNewClass.getAnonymousClass().getSuperclass().getQualifiedName());
        }
    }

    @org.junit.Test
    public void testIntersectionTypeReferenceInGenericsAndCasts() throws java.lang.Exception {
        final java.lang.String target = "./target/type";
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/type/testclasses");
        launcher.setSourceOutputDirectory(target);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<spoon.test.type.testclasses.Pozole> aPozole = launcher.getFactory().Class().get(spoon.test.type.testclasses.Pozole.class);
        final spoon.reflect.declaration.CtMethod<?> prepare = aPozole.getMethodsByName("prepare").get(0);
        final java.util.List<spoon.reflect.declaration.CtClass> localTypes = prepare.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtClass.class));
        org.junit.Assert.assertEquals(1, localTypes.size());
        final spoon.reflect.declaration.CtTypeParameter typeParameter = localTypes.get(0).getFormalCtTypeParameters().get(0);
        org.junit.Assert.assertNotNull(typeParameter);
        org.junit.Assert.assertEquals("T", typeParameter.getSimpleName());
        assertIntersectionTypeForPozolePrepareMethod(aPozole, typeParameter.getSuperclass());
        final java.util.List<spoon.reflect.code.CtLambda<?>> lambdas = prepare.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtLambda<?>>(spoon.reflect.code.CtLambda.class));
        org.junit.Assert.assertEquals(1, lambdas.size());
        org.junit.Assert.assertEquals(1, lambdas.get(0).getTypeCasts().size());
        org.junit.Assert.assertTrue(((lambdas.get(0).getTypeCasts().get(0)) instanceof spoon.reflect.reference.CtIntersectionTypeReference));
        final spoon.reflect.reference.CtIntersectionTypeReference<?> intersectionType = lambdas.get(0).getTypeCasts().get(0).asCtIntersectionTypeReference();
        org.junit.Assert.assertEquals("java.lang.Runnable & java.io.Serializable", intersectionType.toString());
        org.junit.Assert.assertEquals(aPozole.getFactory().Type().createReference(java.lang.Runnable.class), intersectionType.getBounds().stream().collect(java.util.stream.Collectors.toList()).get(0));
        org.junit.Assert.assertEquals(aPozole.getFactory().Type().createReference(java.io.Serializable.class), intersectionType.getBounds().stream().collect(java.util.stream.Collectors.toList()).get(1));
        spoon.testing.utils.ModelUtils.canBeBuilt(target, 8, true);
    }

    private void assertIntersectionTypeForPozolePrepareMethod(spoon.reflect.declaration.CtClass<spoon.test.type.testclasses.Pozole> aPozole, spoon.reflect.reference.CtTypeReference<?> boundingType) {
        org.junit.Assert.assertNotNull(boundingType);
        org.junit.Assert.assertTrue((boundingType instanceof spoon.reflect.reference.CtIntersectionTypeReference));
        org.junit.Assert.assertEquals("java.lang.Runnable & java.io.Serializable", boundingType.toString());
        final spoon.reflect.reference.CtIntersectionTypeReference<?> superType = boundingType.asCtIntersectionTypeReference();
        org.junit.Assert.assertEquals(aPozole.getFactory().Type().createReference(java.lang.Runnable.class), superType.getBounds().stream().collect(java.util.stream.Collectors.toList()).get(0));
        org.junit.Assert.assertEquals(aPozole.getFactory().Type().createReference(java.io.Serializable.class), superType.getBounds().stream().collect(java.util.stream.Collectors.toList()).get(1));
    }

    @org.junit.Test
    public void testTypeReferenceInGenericsAndCasts() throws java.lang.Exception {
        final java.lang.String target = "./target/type";
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/type/testclasses");
        launcher.setSourceOutputDirectory(target);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<spoon.test.type.testclasses.Pozole> aPozole = launcher.getFactory().Class().get(spoon.test.type.testclasses.Pozole.class);
        final spoon.reflect.declaration.CtMethod<?> prepare = aPozole.getMethodsByName("finish").get(0);
        final java.util.List<spoon.reflect.declaration.CtClass> localTypes = prepare.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtClass.class));
        org.junit.Assert.assertEquals(1, localTypes.size());
        final spoon.reflect.declaration.CtTypeParameter typeParameter = localTypes.get(0).getFormalCtTypeParameters().get(0);
        org.junit.Assert.assertNotNull(typeParameter);
        org.junit.Assert.assertEquals("T", typeParameter.getSimpleName());
        assertIntersectionTypeForPozoleFinishMethod(aPozole, typeParameter.getSuperclass());
        final java.util.List<spoon.reflect.code.CtLambda<?>> lambdas = prepare.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtLambda<?>>(spoon.reflect.code.CtLambda.class));
        org.junit.Assert.assertEquals(1, lambdas.size());
        org.junit.Assert.assertEquals(1, lambdas.get(0).getTypeCasts().size());
        org.junit.Assert.assertEquals("java.lang.Runnable", lambdas.get(0).getTypeCasts().get(0).toString());
        org.junit.Assert.assertEquals(aPozole.getFactory().Type().createReference(java.lang.Runnable.class), lambdas.get(0).getTypeCasts().get(0));
        spoon.testing.utils.ModelUtils.canBeBuilt(target, 8, true);
    }

    private void assertIntersectionTypeForPozoleFinishMethod(spoon.reflect.declaration.CtClass<spoon.test.type.testclasses.Pozole> aPozole, spoon.reflect.reference.CtTypeReference<?> boundingType) {
        org.junit.Assert.assertNotNull(boundingType);
        org.junit.Assert.assertEquals("java.lang.Runnable", boundingType.toString());
        org.junit.Assert.assertEquals(aPozole.getFactory().Type().createReference(java.lang.Runnable.class), boundingType);
    }

    @org.junit.Test
    public void testIntersectionTypeOnTopLevelType() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.type.testclasses.Mole> aMole = spoon.testing.utils.ModelUtils.buildClass(spoon.test.type.testclasses.Mole.class);
        org.junit.Assert.assertEquals(1, aMole.getFormalCtTypeParameters().size());
        final spoon.reflect.declaration.CtTypeParameter typeParameter = aMole.getFormalCtTypeParameters().get(0);
        assertIntersectionTypeForMole(aMole, typeParameter.getSuperclass());
    }

    private void assertIntersectionTypeForMole(spoon.reflect.declaration.CtType<spoon.test.type.testclasses.Mole> aMole, spoon.reflect.reference.CtTypeReference<?> boundingType) {
        org.junit.Assert.assertNotNull(boundingType);
        org.junit.Assert.assertTrue((boundingType instanceof spoon.reflect.reference.CtIntersectionTypeReference));
        org.junit.Assert.assertEquals(2, boundingType.asCtIntersectionTypeReference().getBounds().size());
        org.junit.Assert.assertEquals(java.lang.Number.class, boundingType.asCtIntersectionTypeReference().getBounds().stream().collect(java.util.stream.Collectors.toList()).get(0).getActualClass());
        org.junit.Assert.assertEquals(java.lang.Comparable.class, boundingType.asCtIntersectionTypeReference().getBounds().stream().collect(java.util.stream.Collectors.toList()).get(1).getActualClass());
        org.junit.Assert.assertEquals("public class Mole<NUMBER extends java.lang.Number & java.lang.Comparable<NUMBER>> {}", aMole.toString());
    }

    @org.junit.Test
    public void testUnboxingTypeReference() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        final spoon.reflect.reference.CtTypeReference<java.lang.Object> aReference = factory.Type().createReference("fr.inria.Spoon");
        try {
            final spoon.reflect.reference.CtTypeReference<?> unbox = aReference.unbox();
            org.junit.Assert.assertEquals(aReference, unbox);
        } catch (spoon.support.SpoonClassNotFoundException e) {
            org.junit.Assert.fail("Should never throw a SpoonClassNotFoundException.");
        }
    }

    @org.junit.Test
    public void testDeclarationCreatedByFactory() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        org.junit.Assert.assertNotNull(factory.Interface().create("fr.inria.ITest").getReference().getDeclaration());
        org.junit.Assert.assertNotNull(factory.Enum().create("fr.inria.ETest").getReference().getDeclaration());
    }

    @org.junit.Test
    public void testPolyTypBindingInTernaryExpression() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/ternary-bug");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.buildModel();
        spoon.reflect.declaration.CtType<java.lang.Object> aType = launcher.getFactory().Type().get("de.uni_bremen.st.quide.persistence.transformators.IssueTransformator");
        spoon.reflect.code.CtConstructorCall ctConstructorCall = aType.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtConstructorCall>(spoon.reflect.code.CtConstructorCall.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtConstructorCall element) {
                return ("TOIssue".equals(element.getExecutable().getType().getSimpleName())) && (super.matches(element));
            }
        }).get(0);
        org.junit.Assert.assertEquals(launcher.getFactory().Type().objectType(), ctConstructorCall.getExecutable().getParameters().get(9));
    }

    @org.junit.Test
    public void testShadowType() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.buildModel();
        final spoon.reflect.declaration.CtClass<java.lang.Object> objectCtClass = launcher.getFactory().Class().get(java.lang.Object.class);
        final spoon.reflect.declaration.CtClass<java.lang.Object> objectCtClass1 = launcher.getFactory().Class().get(java.lang.Object.class);
        org.junit.Assert.assertSame(objectCtClass, objectCtClass1);
        org.junit.Assert.assertSame(launcher.getFactory().Class(), objectCtClass.getFactory().Class());
        org.junit.Assert.assertSame(launcher.getFactory(), objectCtClass.getFactory());
        org.junit.Assert.assertSame(launcher.getFactory().Class(), objectCtClass1.getFactory().Class());
        org.junit.Assert.assertSame(launcher.getFactory(), objectCtClass1.getFactory());
        org.junit.Assert.assertSame(objectCtClass.getFactory().Class().get(objectCtClass.getActualClass()), objectCtClass);
        org.junit.Assert.assertSame(objectCtClass.getFactory().Class().get(java.lang.Object.class), objectCtClass);
        org.junit.Assert.assertSame(objectCtClass1.getFactory().Class().get(objectCtClass1.getActualClass()), objectCtClass1);
        org.junit.Assert.assertSame(objectCtClass1.getFactory().Class().get(java.lang.Object.class), objectCtClass1);
        org.junit.Assert.assertTrue(objectCtClass.isShadow());
        org.junit.Assert.assertEquals("java.lang.Object", objectCtClass.getQualifiedName());
        final spoon.reflect.declaration.CtType<java.lang.Object> objectCtType = launcher.getFactory().Type().get(java.lang.Object.class);
        final spoon.reflect.declaration.CtType<java.lang.Object> objectCtType1 = launcher.getFactory().Type().get(java.lang.Object.class);
        org.junit.Assert.assertSame(objectCtType, objectCtType1);
        org.junit.Assert.assertSame(launcher.getFactory().Type(), objectCtType.getFactory().Type());
        org.junit.Assert.assertSame(launcher.getFactory(), objectCtType.getFactory());
        org.junit.Assert.assertSame(launcher.getFactory().Type(), objectCtType1.getFactory().Type());
        org.junit.Assert.assertSame(launcher.getFactory(), objectCtType1.getFactory());
        org.junit.Assert.assertSame(objectCtType.getFactory().Type().get(objectCtType.getActualClass()), objectCtType);
        org.junit.Assert.assertSame(objectCtType.getFactory().Type().get(java.lang.Object.class), objectCtType);
        org.junit.Assert.assertSame(objectCtType1.getFactory().Type().get(objectCtType1.getActualClass()), objectCtType1);
        org.junit.Assert.assertSame(objectCtType1.getFactory().Type().get(java.lang.Object.class), objectCtType1);
        org.junit.Assert.assertTrue(objectCtClass.isShadow());
        org.junit.Assert.assertEquals("java.lang.Object", objectCtClass.getQualifiedName());
        final java.util.List<java.lang.String> methodNameList = java.util.Arrays.asList(java.lang.Object.class.getDeclaredMethods()).stream().map(java.lang.reflect.Method::getName).collect(java.util.stream.Collectors.toList());
        for (spoon.reflect.declaration.CtMethod<?> ctMethod : objectCtClass.getMethods()) {
            org.junit.Assert.assertTrue(methodNameList.contains(ctMethod.getSimpleName()));
            org.junit.Assert.assertTrue(ctMethod.getBody().getStatements().isEmpty());
        }
    }

    @org.junit.Test
    public void testTypeMemberOrder() throws java.lang.Exception {
        final java.lang.String target = "./target/type";
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/type/testclasses/TypeMembersOrder.java");
        launcher.setSourceOutputDirectory(target);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        spoon.reflect.factory.Factory f = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> aTypeMembersOrder = f.Class().get(spoon.test.type.testclasses.TypeMembersOrder.class);
        {
            java.util.List<java.lang.String> typeMemberNames = new java.util.ArrayList<>();
            for (spoon.reflect.declaration.CtTypeMember typeMember : aTypeMembersOrder.getTypeMembers()) {
                typeMemberNames.add(typeMember.getSimpleName());
            }
            org.junit.Assert.assertEquals(java.util.Arrays.asList("<init>", "method1", "field2", "TypeMembersOrder", "method4", "field5", "", "nestedType6", "field7", "method8"), typeMemberNames);
        }
        {
            f.createMethod(aTypeMembersOrder, java.util.Collections.singleton(spoon.reflect.declaration.ModifierKind.PUBLIC), f.Type().voidType(), "method9", java.util.Collections.emptyList(), java.util.Collections.emptySet());
            java.util.List<java.lang.String> typeMemberNames = new java.util.ArrayList<>();
            for (spoon.reflect.declaration.CtTypeMember typeMember : aTypeMembersOrder.getTypeMembers()) {
                typeMemberNames.add(typeMember.getSimpleName());
            }
            org.junit.Assert.assertEquals(java.util.Arrays.asList("<init>", "method1", "field2", "TypeMembersOrder", "method4", "field5", "", "nestedType6", "field7", "method8", "method9"), typeMemberNames);
        }
    }
}

