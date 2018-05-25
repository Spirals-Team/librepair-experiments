package spoon.test.arrays;


public class ArraysTest {
    @org.junit.Test
    public void testArrayReferences() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.arrays", "ArrayClass");
        org.junit.Assert.assertEquals("ArrayClass", type.getSimpleName());
        org.junit.Assert.assertEquals("int[][][]", type.getField("i").getType().getSimpleName());
        org.junit.Assert.assertEquals(3, ((spoon.reflect.reference.CtArrayTypeReference<?>) (type.getField("i").getType())).getDimensionCount());
        final spoon.reflect.reference.CtArrayTypeReference<?> arrayTypeReference = ((spoon.reflect.reference.CtArrayTypeReference<?>) (type.getField("i").getDefaultExpression().getType()));
        org.junit.Assert.assertEquals(1, arrayTypeReference.getArrayType().getAnnotations().size());
        org.junit.Assert.assertEquals("@spoon.test.arrays.ArrayClass.TypeAnnotation(integer = 1)", arrayTypeReference.getArrayType().getAnnotations().get(0).toString());
        spoon.reflect.declaration.CtField<?> x = type.getField("x");
        org.junit.Assert.assertTrue(((x.getType()) instanceof spoon.reflect.reference.CtArrayTypeReference));
        org.junit.Assert.assertEquals("int[]", x.getType().getSimpleName());
        org.junit.Assert.assertEquals("int[]", x.getType().getQualifiedName());
        org.junit.Assert.assertEquals("int", ((spoon.reflect.reference.CtArrayTypeReference<?>) (x.getType())).getComponentType().getSimpleName());
        org.junit.Assert.assertTrue(((spoon.reflect.reference.CtArrayTypeReference<?>) (x.getType())).getComponentType().getActualClass().equals(int.class));
    }

    @org.junit.Test
    public void testInitializeWithNewArray() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/resources/noclasspath/Foo.java");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        spoon.reflect.declaration.CtType<java.lang.Object> aType = launcher.getFactory().Type().get("com.example.Foo");
        final java.util.List<spoon.reflect.code.CtNewArray> elements = aType.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtNewArray.class));
        org.junit.Assert.assertEquals(2, elements.size());
        final spoon.reflect.code.CtNewArray attribute = elements.get(0);
        org.junit.Assert.assertEquals(1, attribute.getDimensionExpressions().size());
        org.junit.Assert.assertEquals(0, ((spoon.reflect.code.CtLiteral) (attribute.getDimensionExpressions().get(0))).getValue());
        org.junit.Assert.assertTrue(((attribute.getType()) instanceof spoon.reflect.reference.CtArrayTypeReference));
        org.junit.Assert.assertEquals("new java.lang.String[0]", attribute.toString());
        final spoon.reflect.code.CtNewArray local = elements.get(1);
        org.junit.Assert.assertEquals(1, local.getDimensionExpressions().size());
        org.junit.Assert.assertTrue(((local.getDimensionExpressions().get(0)) instanceof spoon.reflect.code.CtInvocation));
        org.junit.Assert.assertTrue(((local.getType()) instanceof spoon.reflect.reference.CtArrayTypeReference));
        org.junit.Assert.assertEquals("new com.example.Type[list.size()]", local.toString());
    }

    @org.junit.Test
    public void testCtNewArrayInnerCtNewArray() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("src/test/java/spoon/test/arrays/testclasses/Foo.java");
        launcher.setSourceOutputDirectory("target/foo");
        launcher.buildModel();
        launcher.prettyprint();
        try {
            launcher.getModelBuilder().compile();
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @org.junit.Test
    public void testCtNewArrayWitComments() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("src/test/java/spoon/test/arrays/testclasses/NewArrayWithComment.java");
        launcher.getEnvironment().setCommentEnabled(true);
        launcher.setSourceOutputDirectory("target/foo2");
        launcher.buildModel();
        launcher.prettyprint();
        try {
            launcher.getModelBuilder().compile();
        } catch (java.lang.Exception e) {
            org.junit.Assert.fail(e.getMessage());
        }
    }

    @org.junit.Test
    public void testParameterizedVarargReference() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.arrays.testclasses.VaragParam.class);
        spoon.reflect.declaration.CtParameter<?> param1 = ctClass.getMethodsByName("m1").get(0).getParameters().get(0);
        spoon.reflect.reference.CtArrayTypeReference<?> varArg1TypeRef = ((spoon.reflect.reference.CtArrayTypeReference<?>) (param1.getType()));
        org.junit.Assert.assertEquals("java.util.List<?>[]", varArg1TypeRef.toString());
        org.junit.Assert.assertEquals("java.util.List<?>", varArg1TypeRef.getComponentType().toString());
        org.junit.Assert.assertEquals(1, varArg1TypeRef.getComponentType().getActualTypeArguments().size());
        org.junit.Assert.assertEquals(0, varArg1TypeRef.getActualTypeArguments().size());
    }

    @org.junit.Test
    public void testParameterizedArrayReference() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.arrays.testclasses.VaragParam.class);
        spoon.reflect.declaration.CtParameter<?> param1 = ctClass.getMethodsByName("m2").get(0).getParameters().get(0);
        spoon.reflect.reference.CtArrayTypeReference<?> varArg1TypeRef = ((spoon.reflect.reference.CtArrayTypeReference<?>) (param1.getType()));
        org.junit.Assert.assertEquals("java.util.List<?>[]", varArg1TypeRef.toString());
        org.junit.Assert.assertEquals("java.util.List<?>", varArg1TypeRef.getComponentType().toString());
        org.junit.Assert.assertEquals(1, varArg1TypeRef.getComponentType().getActualTypeArguments().size());
        org.junit.Assert.assertEquals(0, varArg1TypeRef.getActualTypeArguments().size());
    }

    @org.junit.Test
    public void testParameterizedArrayVarargReference() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.arrays.testclasses.VaragParam.class);
        spoon.reflect.declaration.CtParameter<?> param1 = ctClass.getMethodsByName("m3").get(0).getParameters().get(0);
        spoon.reflect.reference.CtArrayTypeReference<?> varArg1TypeRef = ((spoon.reflect.reference.CtArrayTypeReference<?>) (param1.getType()));
        org.junit.Assert.assertEquals("java.util.List<?>[][]", varArg1TypeRef.toString());
        org.junit.Assert.assertEquals("java.util.List<?>[]", varArg1TypeRef.getComponentType().toString());
        org.junit.Assert.assertEquals("java.util.List<?>", ((spoon.reflect.reference.CtArrayTypeReference<?>) (varArg1TypeRef.getComponentType())).getComponentType().toString());
        org.junit.Assert.assertEquals(1, ((spoon.reflect.reference.CtArrayTypeReference<?>) (varArg1TypeRef.getComponentType())).getComponentType().getActualTypeArguments().size());
        org.junit.Assert.assertEquals(0, varArg1TypeRef.getComponentType().getActualTypeArguments().size());
        org.junit.Assert.assertEquals(0, varArg1TypeRef.getActualTypeArguments().size());
    }

    @org.junit.Test
    public void testParameterizedTypeReference() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.arrays.testclasses.VaragParam.class);
        spoon.reflect.declaration.CtParameter<?> param1 = ctClass.getMethodsByName("m4").get(0).getParameters().get(0);
        spoon.reflect.reference.CtTypeReference<?> typeRef = ((spoon.reflect.reference.CtTypeReference<?>) (param1.getType()));
        org.junit.Assert.assertEquals("java.util.List<?>", typeRef.toString());
        org.junit.Assert.assertEquals(1, typeRef.getActualTypeArguments().size());
    }
}

