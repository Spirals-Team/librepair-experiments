package spoon.test.targeted;


public class TargetedExpressionTest {
    @org.junit.Test
    public void testCtSuperAccess() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.targeted.testclasses.InternalSuperCall.class);
        final spoon.reflect.declaration.CtClass<?> ctClass = factory.Class().get(spoon.test.targeted.testclasses.InternalSuperCall.class);
        spoon.reflect.declaration.CtMethod<?> method = ctClass.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "methode")).get(0);
        org.junit.Assert.assertEquals("spoon.test.targeted.testclasses.InternalSuperCall.super.toString()", method.getBody().getStatements().get(0).toString());
        org.junit.Assert.assertNotNull(method.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtSuperAccess.class)).get(0).getTarget());
        spoon.reflect.declaration.CtMethod<?> toStringMethod = ctClass.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "toString")).get(0);
        org.junit.Assert.assertEquals("return super.toString()", toStringMethod.getBody().getStatements().get(0).toString());
        org.junit.Assert.assertNull(toStringMethod.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtSuperAccess.class)).get(0).getTarget());
    }

    @org.junit.Test
    public void testCtThisAccess() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.targeted.testclasses", "InnerClassThisAccess");
        org.junit.Assert.assertEquals("InnerClassThisAccess", type.getSimpleName());
        spoon.reflect.declaration.CtMethod<?> meth1 = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "method2")).get(0);
        org.junit.Assert.assertEquals("this.method()", meth1.getBody().getStatements().get(0).toString());
        spoon.reflect.declaration.CtClass<?> c = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtClass.class, "1InnerClass")).get(0);
        org.junit.Assert.assertEquals("1InnerClass", c.getSimpleName());
        spoon.reflect.declaration.CtConstructor<?> ctr = c.getConstructor(type.getFactory().Type().createReference(boolean.class));
        org.junit.Assert.assertEquals("this.b = b", ctr.getBody().getLastStatement().toString());
    }

    @org.junit.Test
    public void testTargetOfFieldAccess() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.targeted.testclasses.Foo.class, spoon.test.targeted.testclasses.Bar.class, spoon.test.targeted.testclasses.SuperClass.class);
        final spoon.reflect.declaration.CtClass<java.lang.Object> type = factory.Class().get(spoon.test.targeted.testclasses.Foo.class);
        spoon.reflect.declaration.CtConstructor<?> constructor = type.getConstructors().toArray(new spoon.reflect.declaration.CtConstructor<?>[0])[0];
        final java.util.List<spoon.reflect.code.CtFieldAccess<?>> elements = constructor.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtFieldAccess<?>>(spoon.reflect.code.CtFieldAccess.class));
        org.junit.Assert.assertEquals(2, elements.size());
        org.junit.Assert.assertEquals("Target is CtThisAccessImpl if there is a 'this' explicit.", spoon.support.reflect.code.CtThisAccessImpl.class, elements.get(0).getTarget().getClass());
        org.junit.Assert.assertNotNull("Target isn't null if there is a 'this' explicit.", elements.get(1).getTarget());
        org.junit.Assert.assertTrue(elements.get(1).getTarget().isImplicit());
    }

    @org.junit.Test
    public void testNotTargetedExpression() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.targeted.testclasses.Foo.class, spoon.test.targeted.testclasses.Bar.class, spoon.test.targeted.testclasses.SuperClass.class);
        spoon.reflect.declaration.CtClass<java.lang.Object> fooClass = factory.Class().get(spoon.test.targeted.testclasses.Foo.class);
        spoon.reflect.declaration.CtField<?> iField = fooClass.getField("i");
        spoon.reflect.code.CtFieldAccess<?> fieldAccess = factory.Core().createFieldRead();
        fieldAccess.setVariable(((spoon.reflect.reference.CtFieldReference) (iField.getReference())));
        fieldAccess.setTarget(factory.Code().createThisAccess(fooClass.getReference()));
        org.junit.Assert.assertEquals("this.i", fieldAccess.toString());
        fieldAccess.setTarget(null);
        org.junit.Assert.assertEquals("i", fieldAccess.toString());
    }

    @org.junit.Test
    public void testCastWriteWithGenerics() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.targeted.testclasses.Pozole.class);
        final spoon.reflect.declaration.CtClass<java.lang.Object> aPozole = factory.Class().get(spoon.test.targeted.testclasses.Pozole.class);
        final spoon.reflect.declaration.CtConstructor<java.lang.Object> aConstructor = aPozole.getConstructor(aPozole.getReference());
        final java.util.List<spoon.reflect.code.CtFieldRead> elements = aConstructor.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldRead.class));
        org.junit.Assert.assertEquals(1, elements.size());
        org.junit.Assert.assertEquals("((spoon.test.targeted.testclasses.Pozole<T>) (v1))", elements.get(0).getTarget().toString());
    }

    @org.junit.Test
    public void testTargetsOfFieldAccess() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.targeted.testclasses.Foo.class, spoon.test.targeted.testclasses.Bar.class, spoon.test.targeted.testclasses.SuperClass.class);
        final spoon.reflect.declaration.CtClass<spoon.test.targeted.testclasses.Foo> type = factory.Class().get(spoon.test.targeted.testclasses.Foo.class);
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.Foo> expectedType = type.getReference();
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.Bar> expectedBarType = factory.Class().<spoon.test.targeted.testclasses.Bar>get(spoon.test.targeted.testclasses.Bar.class).getReference();
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.SuperClass> expectedSuperClassType = factory.Class().<spoon.test.targeted.testclasses.SuperClass>get(spoon.test.targeted.testclasses.SuperClass.class).getReference();
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.Foo.Fii.Fuu> expectedFuuType = factory.Class().<spoon.test.targeted.testclasses.Foo.Fii.Fuu>get(spoon.test.targeted.testclasses.Foo.Fii.Fuu.class).getReference();
        final spoon.reflect.declaration.CtMethod<?> fieldMethod = type.getMethodsByName("field").get(0);
        final spoon.reflect.code.CtThisAccess<spoon.test.targeted.testclasses.Foo> expectedThisAccess = type.getFactory().Code().createThisAccess(expectedType);
        final java.util.List<spoon.reflect.code.CtFieldAccess<?>> elements = fieldMethod.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtFieldAccess<?>>(spoon.reflect.code.CtFieldAccess.class));
        org.junit.Assert.assertEquals(10, elements.size());
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(expectedThisAccess).result("this.i"), elements.get(0));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(expectedThisAccess).result("i"), elements.get(1));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedBarType).target(elements.get(3)).result("this.bar.i"), elements.get(2));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(expectedThisAccess).result("this.bar"), elements.get(3));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedBarType).target(elements.get(5)).result("bar.i"), elements.get(4));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(expectedThisAccess).result("bar"), elements.get(5));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedSuperClassType).target(expectedThisAccess).result("this.o"), elements.get(6));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedSuperClassType).target(expectedThisAccess).result("o"), elements.get(7));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedFuuType).target(elements.get(9)).result("fuu.p"), elements.get(8));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(expectedThisAccess).result("fuu"), elements.get(9));
    }

    @org.junit.Test
    public void testTargetsOfStaticFieldAccess() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.targeted.testclasses.Foo.class, spoon.test.targeted.testclasses.Bar.class, spoon.test.targeted.testclasses.SuperClass.class);
        final spoon.reflect.declaration.CtClass<spoon.test.targeted.testclasses.Foo> type = factory.Class().get(spoon.test.targeted.testclasses.Foo.class);
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.Foo> expectedType = type.getReference();
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.Bar> expectedBarType = factory.Class().<spoon.test.targeted.testclasses.Bar>get(spoon.test.targeted.testclasses.Bar.class).getReference();
        final spoon.reflect.declaration.CtMethod<?> constructor = type.getMethodsByName("m").get(0);
        final spoon.reflect.code.CtThisAccess<spoon.test.targeted.testclasses.Foo> expectedThisAccess = type.getFactory().Code().createThisAccess(expectedType);
        final spoon.reflect.code.CtTypeAccess<spoon.test.targeted.testclasses.Foo> expectedTypeAccess = type.getFactory().Code().createTypeAccess(expectedType);
        final spoon.reflect.code.CtTypeAccess<spoon.test.targeted.testclasses.Bar> expectedBarTypeAccess = type.getFactory().Code().createTypeAccess(expectedBarType);
        final java.util.List<spoon.reflect.code.CtFieldAccess<?>> elements = constructor.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtFieldAccess<?>>(spoon.reflect.code.CtFieldAccess.class));
        org.junit.Assert.assertEquals(10, elements.size());
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().type(spoon.reflect.code.CtFieldRead.class).declaringType(expectedType).target(expectedThisAccess).result("this.k"), elements.get(0));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().type(spoon.reflect.code.CtFieldRead.class).declaringType(expectedType).target(expectedTypeAccess).result("spoon.test.targeted.testclasses.Foo.k"), elements.get(1));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().type(spoon.reflect.code.CtFieldRead.class).declaringType(expectedType).target(expectedTypeAccess).result("spoon.test.targeted.testclasses.Foo.k"), elements.get(2));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().type(spoon.reflect.code.CtFieldWrite.class).declaringType(expectedType).target(expectedThisAccess).result("this.k"), elements.get(3));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().type(spoon.reflect.code.CtFieldWrite.class).declaringType(expectedType).target(expectedTypeAccess).result("spoon.test.targeted.testclasses.Foo.k"), elements.get(4));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().type(spoon.reflect.code.CtFieldWrite.class).declaringType(expectedType).target(expectedTypeAccess).result("spoon.test.targeted.testclasses.Foo.k"), elements.get(5));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().type(spoon.reflect.code.CtFieldRead.class).declaringType(expectedBarType).target(expectedBarTypeAccess).result("spoon.test.targeted.testclasses.Bar.FIELD"), elements.get(6));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().type(spoon.reflect.code.CtFieldRead.class).declaringType(expectedBarType).target(expectedBarTypeAccess).result("spoon.test.targeted.testclasses.Bar.FIELD"), elements.get(7));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().type(spoon.reflect.code.CtFieldWrite.class).declaringType(expectedBarType).target(expectedBarTypeAccess).result("spoon.test.targeted.testclasses.Bar.FIELD"), elements.get(8));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().type(spoon.reflect.code.CtFieldWrite.class).declaringType(expectedBarType).target(expectedBarTypeAccess).result("spoon.test.targeted.testclasses.Bar.FIELD"), elements.get(9));
        final spoon.reflect.declaration.CtAnonymousExecutable staticInit = type.getAnonymousExecutables().get(0);
        final java.util.List<spoon.reflect.code.CtFieldAccess<?>> staticElements = staticInit.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldAccess.class));
        org.junit.Assert.assertEquals(1, staticElements.size());
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().type(spoon.reflect.code.CtFieldWrite.class).declaringType(expectedType).target(expectedTypeAccess).result("p"), staticElements.get(0));
    }

    @org.junit.Test
    public void testTargetsOfFieldAccessInInnerClass() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.targeted.testclasses.Foo.class, spoon.test.targeted.testclasses.Bar.class, spoon.test.targeted.testclasses.SuperClass.class);
        final spoon.reflect.declaration.CtClass<spoon.test.targeted.testclasses.Foo> type = factory.Class().get(spoon.test.targeted.testclasses.Foo.class);
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.Foo> expectedType = type.getReference();
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.SuperClass> expectedSuperClassType = factory.Class().<spoon.test.targeted.testclasses.SuperClass>get(spoon.test.targeted.testclasses.SuperClass.class).getReference();
        final spoon.reflect.declaration.CtType<?> innerClass = type.getNestedType("InnerClass");
        final spoon.reflect.reference.CtTypeReference<?> expectedInnerClass = innerClass.getReference();
        final spoon.reflect.declaration.CtType<?> nestedTypeScanner = type.getNestedType("1NestedTypeScanner");
        final spoon.reflect.reference.CtTypeReference<?> expectedNested = nestedTypeScanner.getReference();
        final spoon.reflect.code.CtTypeAccess<spoon.test.targeted.testclasses.Foo> fooTypeAccess = factory.Code().createTypeAccess(expectedType);
        final spoon.reflect.code.CtThisAccess<spoon.test.targeted.testclasses.Foo> expectedThisAccess = factory.Code().createThisAccess(expectedType);
        final spoon.reflect.code.CtThisAccess<?> expectedInnerClassAccess = factory.Code().createThisAccess(expectedInnerClass);
        final spoon.reflect.code.CtThisAccess expectedNestedAccess = factory.Code().createThisAccess(expectedNested);
        final spoon.reflect.declaration.CtMethod<?> innerInvMethod = innerClass.getMethodsByName("innerField").get(0);
        final java.util.List<spoon.reflect.code.CtFieldAccess<?>> elements = innerInvMethod.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldAccess.class));
        org.junit.Assert.assertEquals(6, elements.size());
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedInnerClass).target(expectedInnerClassAccess).result("this.i"), elements.get(0));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedInnerClass).target(expectedInnerClassAccess).result("i"), elements.get(1));
        org.junit.Assert.assertEquals(true, elements.get(1).getTarget().isImplicit());
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(expectedThisAccess).result("this.i"), elements.get(2));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(fooTypeAccess).result("spoon.test.targeted.testclasses.Foo.k"), elements.get(3));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedSuperClassType).target(expectedThisAccess).result("this.o"), elements.get(4));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedSuperClassType).target(expectedThisAccess).result("o"), elements.get(5));
        final java.util.List<spoon.reflect.code.CtFieldAccess<?>> newElements = nestedTypeScanner.getMethodsByName("checkField").get(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldAccess.class));
        org.junit.Assert.assertEquals(2, newElements.size());
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedNested).target(expectedNestedAccess).result("this.type").isLocal(), newElements.get(0));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedNested).target(expectedNestedAccess).result("type").isLocal(), newElements.get(1));
    }

    @org.junit.Test
    public void testTargetsOfFieldInAnonymousClass() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.targeted.testclasses.Foo.class, spoon.test.targeted.testclasses.Bar.class, spoon.test.targeted.testclasses.SuperClass.class);
        final spoon.reflect.declaration.CtClass<spoon.test.targeted.testclasses.Foo> type = factory.Class().get(spoon.test.targeted.testclasses.Foo.class);
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.Foo> expectedType = type.getReference();
        final spoon.reflect.declaration.CtClass<?> anonymousClass = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtClass>(spoon.reflect.declaration.CtClass.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtClass element) {
                return (element.isAnonymous()) && (super.matches(element));
            }
        }).get(0);
        final spoon.reflect.reference.CtTypeReference<?> expectedAnonymousType = anonymousClass.getReference();
        final spoon.reflect.code.CtThisAccess<spoon.test.targeted.testclasses.Foo> expectedThisAccess = factory.Code().createThisAccess(expectedType);
        final spoon.reflect.code.CtThisAccess expectedAnonymousThisAccess = factory.Code().createThisAccess(expectedAnonymousType);
        final spoon.reflect.declaration.CtMethod<?> method = anonymousClass.getMethodsByName("invStatic").get(0);
        final java.util.List<spoon.reflect.code.CtFieldAccess> elements = method.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldAccess.class));
        org.junit.Assert.assertEquals(3, elements.size());
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(expectedThisAccess).result("this.i"), elements.get(0));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedAnonymousType).target(expectedAnonymousThisAccess).result("this.i"), elements.get(1));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedAnonymousType).target(expectedAnonymousThisAccess).result("i"), elements.get(2));
    }

    @org.junit.Test
    public void testStaticTargetsOfFieldAccessNoClasspath() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/test/resources/spoon/test/noclasspath/targeted/Foo.java");
        launcher.setSourceOutputDirectory("./target/noclasspath");
        launcher.run();
        final spoon.reflect.reference.CtTypeReference<java.lang.Object> expectedFoo = launcher.getFactory().Class().createReference("Foo");
        final spoon.reflect.reference.CtTypeReference<java.lang.Object> expectedBar = launcher.getFactory().Class().createReference("Bar");
        final spoon.reflect.reference.CtTypeReference<java.lang.Object> expectedFiiFuu = launcher.getFactory().Class().create("Fii.Fuu").getReference();
        final spoon.reflect.code.CtThisAccess<java.lang.Object> expectedThisAccess = launcher.getFactory().Code().createThisAccess(expectedFoo);
        final spoon.reflect.code.CtTypeAccess<java.lang.Object> expectedTypeAccess = launcher.getFactory().Code().createTypeAccess(expectedFoo);
        final spoon.reflect.code.CtTypeAccess<java.lang.Object> expectedBarTypeAccess = launcher.getFactory().Code().createTypeAccess(expectedBar);
        final spoon.reflect.declaration.CtMethod<?> fieldMethod = launcher.getFactory().Class().get("Foo").getMethodsByName("field").get(0);
        final java.util.List<spoon.reflect.code.CtFieldAccess<?>> elements = fieldMethod.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldAccess.class));
        org.junit.Assert.assertEquals(10, elements.size());
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedFoo).target(spoon.support.reflect.code.CtConstructorCallImpl.class).result("new Foo().i"), elements.get(0));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedFoo).target(elements.get(2)).result("foo.i"), elements.get(1));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedFoo).target(expectedThisAccess).result("foo"), elements.get(2));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedFoo).target(expectedThisAccess).result("this.i"), elements.get(3));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedFoo).target(expectedThisAccess).result("foo"), elements.get(4));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedFoo).target(expectedTypeAccess.toString()).result("Foo.staticField"), elements.get(5));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().result("staticField"), elements.get(6));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedBar).target(expectedBarTypeAccess).result("Bar.staticFieldBar"), elements.get(7));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedBar).target(expectedBarTypeAccess).result("Bar.staticFieldBar"), elements.get(8));
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedFiiFuu).target(launcher.getFactory().Code().createTypeAccess(expectedFiiFuu)).result("Fii.Fuu.i"), elements.get(9));
    }

    @org.junit.Test
    public void testTargetsOfInv() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.targeted.testclasses.Foo.class, spoon.test.targeted.testclasses.Bar.class, spoon.test.targeted.testclasses.SuperClass.class);
        final spoon.reflect.declaration.CtClass<spoon.test.targeted.testclasses.Foo> type = factory.Class().get(spoon.test.targeted.testclasses.Foo.class);
        final spoon.reflect.declaration.CtClass<spoon.test.targeted.testclasses.Foo.Fii.Fuu> fuu = factory.Class().<spoon.test.targeted.testclasses.Foo.Fii.Fuu>get(spoon.test.targeted.testclasses.Foo.Fii.Fuu.class);
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.Foo> expectedType = type.getReference();
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.Bar> expectedBarType = factory.Class().<spoon.test.targeted.testclasses.Bar>get(spoon.test.targeted.testclasses.Bar.class).getReference();
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.SuperClass> expectedSuperClassType = factory.Class().<spoon.test.targeted.testclasses.SuperClass>get(spoon.test.targeted.testclasses.SuperClass.class).getReference();
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.Foo.Fii.Fuu> expectedFuuType = fuu.getReference();
        final spoon.reflect.code.CtThisAccess<spoon.test.targeted.testclasses.Foo> expectedThisAccess = factory.Code().createThisAccess(expectedType);
        final spoon.reflect.code.CtTypeAccess<spoon.test.targeted.testclasses.Foo> fooTypeAccess = factory.Code().createTypeAccess(expectedType);
        final spoon.reflect.code.CtTypeAccess<spoon.test.targeted.testclasses.SuperClass> superClassTypeAccess = factory.Code().createTypeAccess(expectedSuperClassType);
        final spoon.reflect.code.CtThisAccess<spoon.test.targeted.testclasses.Foo> expectedSuperThisAccess = factory.Code().createThisAccess(expectedType);
        expectedSuperThisAccess.setTarget(superClassTypeAccess);
        final java.util.List<spoon.reflect.code.CtInvocation<?>> elements = type.getMethodsByName("inv").get(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtInvocation<?>>(spoon.reflect.code.CtInvocation.class));
        org.junit.Assert.assertEquals(7, elements.size());
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(spoon.support.reflect.code.CtConstructorCallImpl.class).result("new spoon.test.targeted.testclasses.Foo(0, 0).method()"), elements.get(0));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(spoon.support.reflect.code.CtFieldReadImpl.class).result("foo.method()"), elements.get(1));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(expectedThisAccess).result("this.method()"), elements.get(2));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(expectedThisAccess).result("method()"), elements.get(3));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedBarType).target(spoon.support.reflect.code.CtFieldReadImpl.class).result("bar.methodBar()"), elements.get(4));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedFuuType).target(spoon.support.reflect.code.CtFieldReadImpl.class).result("fuu.method()"), elements.get(5));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedSuperClassType).target(expectedSuperThisAccess).result("superMethod()"), elements.get(6));
        org.junit.Assert.assertEquals(fooTypeAccess.getType().getQualifiedName(), ((spoon.reflect.code.CtThisAccess) (elements.get(2).getTarget())).getTarget().getType().getQualifiedName());
        org.junit.Assert.assertEquals(fooTypeAccess.getType().getQualifiedName(), ((spoon.reflect.code.CtThisAccess) (elements.get(3).getTarget())).getTarget().getType().getQualifiedName());
    }

    @org.junit.Test
    public void testStaticTargetsOfInv() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.targeted.testclasses.Foo.class, spoon.test.targeted.testclasses.Bar.class, spoon.test.targeted.testclasses.SuperClass.class);
        final spoon.reflect.declaration.CtClass<spoon.test.targeted.testclasses.Foo> type = factory.Class().get(spoon.test.targeted.testclasses.Foo.class);
        final spoon.reflect.declaration.CtClass<spoon.test.targeted.testclasses.Foo.Fii.Fuu> fuu = factory.Class().<spoon.test.targeted.testclasses.Foo.Fii.Fuu>get(spoon.test.targeted.testclasses.Foo.Fii.Fuu.class);
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.Foo> expectedType = type.getReference();
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.Bar> expectedBarType = factory.Class().<spoon.test.targeted.testclasses.Bar>get(spoon.test.targeted.testclasses.Bar.class).getReference();
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.Foo.Fii.Fuu> expectedFuuType = fuu.getReference();
        final spoon.reflect.code.CtThisAccess<spoon.test.targeted.testclasses.Foo> expectedThisAccess = type.getFactory().Code().createThisAccess(expectedType);
        final spoon.reflect.code.CtTypeAccess<spoon.test.targeted.testclasses.Foo> expectedTypeAccess = type.getFactory().Code().createTypeAccess(expectedType);
        final spoon.reflect.code.CtTypeAccess<spoon.test.targeted.testclasses.Bar> expectedBarTypeAccess = type.getFactory().Code().createTypeAccess(expectedBarType);
        final spoon.reflect.declaration.CtMethod<?> invMethod = type.getMethodsByName("invStatic").get(0);
        final java.util.List<spoon.reflect.code.CtInvocation<?>> elements = invMethod.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtInvocation<?>>(spoon.reflect.code.CtInvocation.class));
        org.junit.Assert.assertEquals(8, elements.size());
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(spoon.support.reflect.code.CtConstructorCallImpl.class).result("new spoon.test.targeted.testclasses.Foo(0, 0).staticMethod()"), elements.get(0));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(spoon.support.reflect.code.CtFieldReadImpl.class).result("foo.staticMethod()"), elements.get(1));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(expectedThisAccess).result("this.staticMethod()"), elements.get(2));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(expectedTypeAccess).result("spoon.test.targeted.testclasses.Foo.staticMethod()"), elements.get(3));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(expectedTypeAccess).result("spoon.test.targeted.testclasses.Foo.staticMethod()"), elements.get(4));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedBarType).target(expectedBarTypeAccess).result("spoon.test.targeted.testclasses.Bar.staticMethodBar()"), elements.get(5));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedBarType).target(expectedBarTypeAccess).result("spoon.test.targeted.testclasses.Bar.staticMethodBar()"), elements.get(6));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedFuuType).target(factory.Code().createTypeAccess(expectedFuuType)).result("spoon.test.targeted.testclasses.Foo.Fii.Fuu.m()"), elements.get(7));
    }

    @org.junit.Test
    public void testTargetsOfInvInInnerClass() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.targeted.testclasses.Foo.class, spoon.test.targeted.testclasses.Bar.class, spoon.test.targeted.testclasses.SuperClass.class);
        final spoon.reflect.declaration.CtClass<spoon.test.targeted.testclasses.Foo> type = factory.Class().get(spoon.test.targeted.testclasses.Foo.class);
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.Foo> expectedType = type.getReference();
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.SuperClass> expectedSuperClassType = factory.Class().<spoon.test.targeted.testclasses.SuperClass>get(spoon.test.targeted.testclasses.SuperClass.class).getReference();
        final spoon.reflect.declaration.CtType<?> innerClass = type.getNestedType("InnerClass");
        final spoon.reflect.reference.CtTypeReference<?> expectedInnerClass = innerClass.getReference();
        final spoon.reflect.declaration.CtType<?> nestedTypeScanner = type.getNestedType("1NestedTypeScanner");
        final spoon.reflect.reference.CtTypeReference<?> expectedNested = nestedTypeScanner.getReference();
        final spoon.reflect.code.CtTypeAccess<spoon.test.targeted.testclasses.Foo> fooTypeAccess = factory.Code().createTypeAccess(expectedType);
        final spoon.reflect.code.CtThisAccess expectedThisAccess = factory.Code().createThisAccess(expectedType);
        final spoon.reflect.code.CtThisAccess expectedSuperThisAccess = factory.Code().createThisAccess(expectedSuperClassType);
        final spoon.reflect.code.CtThisAccess<?> expectedInnerClassAccess = factory.Code().createThisAccess(expectedInnerClass);
        final spoon.reflect.code.CtThisAccess expectedNestedAccess = factory.Code().createThisAccess(expectedNested);
        final spoon.reflect.declaration.CtMethod<?> innerInvMethod = innerClass.getMethodsByName("innerInv").get(0);
        final java.util.List<spoon.reflect.code.CtInvocation<?>> elements = innerInvMethod.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtInvocation<?>>(spoon.reflect.code.CtInvocation.class));
        org.junit.Assert.assertEquals(8, elements.size());
        expectedThisAccess.setType(expectedInnerClass);
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(expectedThisAccess).result("inv()"), elements.get(0));
        expectedThisAccess.setType(expectedType);
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(expectedThisAccess).result("this.inv()"), elements.get(1));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(fooTypeAccess).result("spoon.test.targeted.testclasses.Foo.staticMethod()"), elements.get(2));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(fooTypeAccess).result("spoon.test.targeted.testclasses.Foo.staticMethod()"), elements.get(3));
        expectedSuperThisAccess.setType(expectedInnerClass);
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedSuperClassType).target(expectedSuperThisAccess).result("superMethod()"), elements.get(4));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedSuperClassType).target(expectedThisAccess).result("this.superMethod()"), elements.get(5));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedInnerClass).target(expectedInnerClassAccess).result("method()"), elements.get(6));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedInnerClass).target(expectedInnerClassAccess).result("this.method()"), elements.get(7));
        final java.util.List<spoon.reflect.code.CtInvocation> newElements = nestedTypeScanner.getMethodsByName("checkType").get(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtInvocation.class));
        org.junit.Assert.assertEquals(1, newElements.size());
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedNested).target(expectedNestedAccess).result("this.checkType(type)"), newElements.get(0));
    }

    @org.junit.Test
    public void testTargetsOfInvInAnonymousClass() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.targeted.testclasses.Foo.class, spoon.test.targeted.testclasses.Bar.class, spoon.test.targeted.testclasses.SuperClass.class);
        final spoon.reflect.declaration.CtClass<spoon.test.targeted.testclasses.Foo> type = factory.Class().get(spoon.test.targeted.testclasses.Foo.class);
        final spoon.reflect.reference.CtTypeReference<spoon.test.targeted.testclasses.Foo> expectedType = type.getReference();
        final spoon.reflect.declaration.CtClass<?> anonymousClass = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtClass>(spoon.reflect.declaration.CtClass.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtClass element) {
                return (element.isAnonymous()) && (super.matches(element));
            }
        }).get(0);
        final spoon.reflect.reference.CtTypeReference<?> expectedAnonymousType = anonymousClass.getReference();
        final spoon.reflect.code.CtThisAccess<spoon.test.targeted.testclasses.Foo> expectedThisAccess = factory.Code().createThisAccess(expectedType);
        final spoon.reflect.code.CtThisAccess expectedAnonymousThisAccess = factory.Code().createThisAccess(expectedAnonymousType);
        final spoon.reflect.declaration.CtMethod<?> method = anonymousClass.getMethodsByName("m").get(0);
        final java.util.List<spoon.reflect.code.CtInvocation> elements = method.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtInvocation.class));
        org.junit.Assert.assertEquals(2, elements.size());
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedType).target(expectedThisAccess).result("this.invStatic()"), elements.get(0));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedAnonymousType).target(expectedAnonymousThisAccess).result("this.invStatic()"), elements.get(1));
    }

    @org.junit.Test
    public void testStaticTargetsOfInvNoClasspath() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/test/resources/spoon/test/noclasspath/targeted/Foo.java");
        launcher.setSourceOutputDirectory("./target/noclasspath");
        launcher.run();
        final spoon.reflect.reference.CtTypeReference<java.lang.Object> foo = launcher.getFactory().Class().createReference("Foo");
        final spoon.reflect.reference.CtTypeReference<java.lang.Object> bar = launcher.getFactory().Class().createReference("Bar");
        final spoon.reflect.code.CtThisAccess<java.lang.Object> expectedThisAccess = launcher.getFactory().Code().createThisAccess(foo);
        final spoon.reflect.code.CtTypeAccess<java.lang.Object> expectedTypeAccess = launcher.getFactory().Code().createTypeAccess(foo);
        final spoon.reflect.code.CtTypeAccess<java.lang.Object> expectedBarTypeAccess = launcher.getFactory().Code().createTypeAccess(bar);
        final spoon.reflect.code.CtTypeAccess<java.lang.Object> fiiFuuTypeAccess = launcher.getFactory().Code().createTypeAccess(launcher.getFactory().Type().createReference("Fii.Fuu"));
        final spoon.reflect.declaration.CtMethod<?> invMethod = launcher.getFactory().Class().get("Foo").getMethodsByName("inv").get(0);
        final java.util.List<spoon.reflect.code.CtInvocation<?>> elements = invMethod.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtInvocation.class));
        org.junit.Assert.assertEquals(8, elements.size());
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().target(spoon.support.reflect.code.CtConstructorCallImpl.class).result("new Foo(0, 0).staticMethod()"), elements.get(0));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().target(spoon.support.reflect.code.CtFieldReadImpl.class).result("foo.staticMethod()"), elements.get(1));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().target(expectedThisAccess).result("this.staticMethod()"), elements.get(2));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().target(expectedTypeAccess).result("Foo.staticMethod()"), elements.get(3));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().target(expectedThisAccess).result("staticMethod()"), elements.get(4));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(bar).target(expectedBarTypeAccess).result("Bar.staticMethodBar()"), elements.get(5));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(bar).target(expectedBarTypeAccess).result("Bar.staticMethodBar()"), elements.get(6));
        assertEqualsInvocation(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(launcher.getFactory().Class().create("Fii.Fuu").getReference()).target(fiiFuuTypeAccess).result("Fii.Fuu.m()"), elements.get(7));
    }

    @org.junit.Test
    public void testInitializeFieldAccessInNoclasspathMode() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/test/resources/spoon/test/noclasspath/targeted/Foo.java");
        launcher.setSourceOutputDirectory("./target/noclasspath");
        launcher.run();
        final spoon.reflect.reference.CtTypeReference<java.lang.Object> expectedFoo = launcher.getFactory().Class().createReference("Foo");
        final spoon.reflect.code.CtThisAccess<java.lang.Object> expectedThisAccess = launcher.getFactory().Code().createThisAccess(expectedFoo);
        final java.util.List<spoon.reflect.code.CtFieldAccess<?>> elements = launcher.getFactory().Class().get("Foo").getConstructor().getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldAccess.class));
        org.junit.Assert.assertEquals(1, elements.size());
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(expectedFoo).target(expectedThisAccess).result("this.bar"), elements.get(0));
    }

    @org.junit.Test
    public void testClassDeclaredInALambda() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.targeted.testclasses.Tapas> type = spoon.testing.utils.ModelUtils.buildClass(spoon.test.targeted.testclasses.Tapas.class);
        final java.util.List<spoon.reflect.code.CtFieldAccess> elements = new spoon.support.util.SortedList(new spoon.support.comparator.CtLineElementComparator());
        elements.addAll(type.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldAccess.class)));
        org.junit.Assert.assertEquals(3, elements.size());
        final spoon.reflect.reference.CtTypeReference<java.lang.Object> firstExpected = type.getFactory().Type().createReference("spoon.test.targeted.testclasses.Tapas$1$InnerSubscriber");
        spoon.reflect.code.CtThisAccess<java.lang.Object> expectedThisAccess = type.getFactory().Code().createThisAccess(firstExpected);
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(firstExpected).target(expectedThisAccess).type(spoon.reflect.code.CtFieldWrite.class).result("this.index"), elements.get(0));
        final spoon.reflect.reference.CtTypeReference<java.lang.Object> secondExpectedInner = type.getFactory().Type().createReference("spoon.test.targeted.testclasses.Tapas$3InnerSubscriber");
        expectedThisAccess = type.getFactory().Code().createThisAccess(secondExpectedInner);
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(secondExpectedInner).target(expectedThisAccess).type(spoon.reflect.code.CtFieldWrite.class).result("this.index").isLocal(), elements.get(1));
        final spoon.reflect.reference.CtTypeReference<java.lang.Object> thirdExpectedInner = type.getFactory().Type().createReference("spoon.test.targeted.testclasses.Tapas$4InnerSubscriber");
        expectedThisAccess = type.getFactory().Code().createThisAccess(thirdExpectedInner);
        assertEqualsFieldAccess(new spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression().declaringType(thirdExpectedInner).target(expectedThisAccess).type(spoon.reflect.code.CtFieldWrite.class).result("this.index").isLocal(), elements.get(2));
    }

    private void assertEqualsFieldAccess(spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression expected, spoon.reflect.code.CtFieldAccess<?> fieldAccess) {
        if ((expected.declaringType) == null) {
            org.junit.Assert.assertNull(fieldAccess.getVariable().getDeclaringType());
        }else {
            org.junit.Assert.assertEquals(expected.isLocal, fieldAccess.getVariable().getDeclaringType().isLocalType());
            org.junit.Assert.assertEquals(expected.declaringType.getQualifiedName(), fieldAccess.getVariable().getDeclaringType().getQualifiedName());
        }
        if ((expected.targetClass) != null) {
            org.junit.Assert.assertEquals(expected.targetClass, fieldAccess.getTarget().getClass());
        }else
            if ((expected.targetString) != null) {
                org.junit.Assert.assertEquals(expected.targetString, fieldAccess.getTarget().toString());
            }

        org.junit.Assert.assertEquals(expected.result, fieldAccess.toString());
        if ((expected.type) != null) {
            org.junit.Assert.assertTrue(expected.type.isInstance(fieldAccess));
        }
    }

    private void assertEqualsInvocation(spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression expected, spoon.reflect.code.CtInvocation<?> invocation) {
        org.junit.Assert.assertEquals(expected.result, invocation.toString());
        org.junit.Assert.assertEquals(expected.declaringType, invocation.getExecutable().getDeclaringType());
        if ((expected.targetClass) != null) {
            org.junit.Assert.assertEquals(expected.targetClass, invocation.getTarget().getClass());
        }else
            if ((expected.targetString) != null) {
                org.junit.Assert.assertEquals(expected.targetString, invocation.getTarget().toString());
            }

    }

    private class ExpectedTargetedExpression {
        java.lang.Class<? extends spoon.reflect.code.CtExpression> type;

        java.lang.Class<? extends spoon.reflect.code.CtExpression> targetClass;

        java.lang.String targetString;

        spoon.reflect.code.CtExpression<?> target;

        spoon.reflect.reference.CtTypeReference<?> declaringType;

        java.lang.String result;

        boolean isLocal = false;

        public spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression type(java.lang.Class<? extends spoon.reflect.code.CtExpression> type) {
            this.type = type;
            return this;
        }

        public spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression target(java.lang.Class<? extends spoon.reflect.code.CtExpression> target) {
            this.targetClass = target;
            return this;
        }

        public spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression target(java.lang.String target) {
            this.targetString = target;
            return this;
        }

        public spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression target(spoon.reflect.code.CtExpression<?> target) {
            this.target = target;
            return this;
        }

        public spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression declaringType(spoon.reflect.reference.CtTypeReference<?> declaringType) {
            this.declaringType = declaringType;
            return this;
        }

        public spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression result(java.lang.String result) {
            this.result = result;
            return this;
        }

        public spoon.test.targeted.TargetedExpressionTest.ExpectedTargetedExpression isLocal() {
            this.isLocal = true;
            return this;
        }
    }
}

