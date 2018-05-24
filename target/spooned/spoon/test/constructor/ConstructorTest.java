package spoon.test.constructor;


public class ConstructorTest {
    private spoon.reflect.factory.Factory factory;

    private spoon.reflect.declaration.CtClass<?> aClass;

    @org.junit.Before
    public void setUp() throws java.lang.Exception {
        spoon.SpoonAPI launcher = new spoon.Launcher();
        launcher.run(new java.lang.String[]{ "-i", "./src/test/java/spoon/test/constructor/testclasses/", "-o", "./target/spooned/" });
        factory = launcher.getFactory();
        aClass = factory.Class().get(spoon.test.constructor.testclasses.Tacos.class);
    }

    @org.junit.Test
    public void testImplicitConstructor() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> ctType = ((spoon.reflect.declaration.CtClass) (spoon.testing.utils.ModelUtils.buildClass(spoon.test.constructor.testclasses.ImplicitConstructor.class)));
        org.junit.Assert.assertTrue(ctType.getConstructor().isImplicit());
        org.junit.Assert.assertFalse(aClass.getConstructor().isImplicit());
    }

    @org.junit.Test
    public void testTransformationOnConstructorWithInsertBegin() throws java.lang.Exception {
        final spoon.reflect.declaration.CtConstructor<?> ctConstructor = aClass.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtConstructor<?>>(spoon.reflect.declaration.CtConstructor.class)).get(0);
        ctConstructor.getBody().insertBegin(factory.Code().createCodeSnippetStatement("int i = 0"));
        org.junit.Assert.assertEquals(2, ctConstructor.getBody().getStatements().size());
        org.junit.Assert.assertEquals("super()", ctConstructor.getBody().getStatement(0).toString());
        spoon.testing.utils.ModelUtils.canBeBuilt("./target/spooned/spoon/test/constructor/testclasses/", 8);
    }

    @org.junit.Test
    public void testTransformationOnConstructorWithInsertBefore() throws java.lang.Exception {
        final spoon.reflect.declaration.CtConstructor<?> ctConstructor = aClass.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtConstructor<?>>(spoon.reflect.declaration.CtConstructor.class)).get(0);
        try {
            ctConstructor.getBody().getStatement(0).insertBefore(factory.Code().createCodeSnippetStatement("int i = 0"));
            org.junit.Assert.fail();
        } catch (java.lang.RuntimeException ignore) {
        }
        org.junit.Assert.assertEquals(1, ctConstructor.getBody().getStatements().size());
        org.junit.Assert.assertEquals("super()", ctConstructor.getBody().getStatement(0).toString());
    }

    @org.junit.Test
    public void callParamConstructor() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<java.lang.Object> aClass = factory.Class().get(spoon.test.constructor.testclasses.AClass.class);
        spoon.reflect.declaration.CtConstructor<java.lang.Object> constructor = aClass.getConstructors().iterator().next();
        org.junit.Assert.assertEquals((((("{" + (java.lang.System.lineSeparator())) + "    enclosingInstance.super();") + (java.lang.System.lineSeparator())) + "}"), constructor.getBody().toString());
    }

    @org.junit.Test
    public void testConstructorCallFactory() throws java.lang.Exception {
        spoon.reflect.reference.CtTypeReference<java.util.ArrayList> ctTypeReference = factory.Code().createCtTypeReference(java.util.ArrayList.class);
        spoon.reflect.code.CtConstructorCall<java.util.ArrayList> constructorCall = factory.Code().createConstructorCall(ctTypeReference);
        org.junit.Assert.assertEquals("new java.util.ArrayList()", constructorCall.toString());
        spoon.reflect.code.CtConstructorCall<java.util.ArrayList> constructorCallWithParameter = factory.Code().createConstructorCall(ctTypeReference, constructorCall);
        org.junit.Assert.assertEquals("new java.util.ArrayList(new java.util.ArrayList())", constructorCallWithParameter.toString());
    }

    @org.junit.Test
    public void testTypeAnnotationOnExceptionDeclaredInConstructors() throws java.lang.Exception {
        final spoon.reflect.declaration.CtConstructor<?> aConstructor = aClass.getConstructor(factory.Type().OBJECT);
        org.junit.Assert.assertEquals(1, aConstructor.getThrownTypes().size());
        java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes = aConstructor.getThrownTypes();
        final spoon.reflect.reference.CtTypeReference[] thrownTypesReference = thrownTypes.toArray(new spoon.reflect.reference.CtTypeReference[thrownTypes.size()]);
        org.junit.Assert.assertEquals(1, thrownTypesReference.length);
        org.junit.Assert.assertEquals(1, thrownTypesReference[0].getAnnotations().size());
        org.junit.Assert.assertEquals((("java.lang.@spoon.test.constructor.testclasses.Tacos.TypeAnnotation(integer = 1)" + (java.lang.System.lineSeparator())) + "Exception"), thrownTypesReference[0].toString());
    }

    @org.junit.Test
    public void testTypeAnnotationWithConstructorsOnFormalType() throws java.lang.Exception {
        final spoon.reflect.declaration.CtConstructor<?> aConstructor = aClass.getConstructor(factory.Type().OBJECT);
        org.junit.Assert.assertEquals(1, aConstructor.getFormalCtTypeParameters().size());
        spoon.reflect.declaration.CtTypeParameter typeParameter = aConstructor.getFormalCtTypeParameters().get(0);
        org.junit.Assert.assertEquals("T", typeParameter.getSimpleName());
        org.junit.Assert.assertEquals(1, typeParameter.getAnnotations().size());
        assertIntersectionTypeInConstructor(typeParameter.getSuperclass());
    }

    private void assertIntersectionTypeInConstructor(spoon.reflect.reference.CtTypeReference<?> boundingType1) {
        org.junit.Assert.assertTrue((boundingType1 instanceof spoon.reflect.reference.CtIntersectionTypeReference));
        spoon.reflect.reference.CtIntersectionTypeReference<?> boundingType = boundingType1.asCtIntersectionTypeReference();
        final java.util.List<spoon.reflect.reference.CtTypeReference<?>> bounds = boundingType.getBounds().stream().collect(java.util.stream.Collectors.toList());
        spoon.reflect.reference.CtTypeReference<?> genericTacos = bounds.get(0);
        org.junit.Assert.assertEquals("Tacos", genericTacos.getSimpleName());
        org.junit.Assert.assertEquals(1, genericTacos.getAnnotations().size());
        org.junit.Assert.assertEquals(1, genericTacos.getActualTypeArguments().size());
        spoon.reflect.reference.CtTypeParameterReference wildcard = ((spoon.reflect.reference.CtTypeParameterReference) (genericTacos.getActualTypeArguments().get(0)));
        org.junit.Assert.assertEquals("?", wildcard.getSimpleName());
        org.junit.Assert.assertEquals(1, wildcard.getAnnotations().size());
        org.junit.Assert.assertEquals("C", wildcard.getBoundingType().getSimpleName());
        org.junit.Assert.assertEquals(1, wildcard.getBoundingType().getAnnotations().size());
        org.junit.Assert.assertEquals("Serializable", bounds.get(1).getSimpleName());
        org.junit.Assert.assertEquals(1, bounds.get(1).getAnnotations().size());
    }
}

