package spoon.test.casts;


public class CastTest {
    spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();

    @org.junit.Test
    public void testCast1() {
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("" + (((("class X {" + "public void foo() {") + " String x=(String) new Object();") + "}") + "};"))).compile();
        spoon.reflect.declaration.CtMethod<?> foo = ((spoon.reflect.declaration.CtMethod<?>) (clazz.getMethods().toArray()[0]));
        org.junit.Assert.assertEquals("java.lang.String x = ((java.lang.String) (new java.lang.Object()))", foo.getBody().getStatements().get(0).toString());
    }

    @org.junit.Test
    public void testCast2() {
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("" + (((("class X {" + "public void foo() {") + " Class<String> x=(Class<String>) new Object();") + "}") + "};"))).compile();
        spoon.reflect.declaration.CtMethod<?> foo = ((spoon.reflect.declaration.CtMethod<?>) (clazz.getMethods().toArray()[0]));
        org.junit.Assert.assertEquals("java.lang.Class<java.lang.String> x = ((java.lang.Class<java.lang.String>) (new java.lang.Object()))", foo.getBody().getStatements().get(0).toString());
    }

    @org.junit.Test
    public void testCast3() {
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("" + (((((("class X<A> {" + "void addConsumedAnnotationType(Class<? extends A> annotationType) {}\n") + "public void foo() {") + " Class<?> x = null;") + " addConsumedAnnotationType((Class<A>) x);") + "}") + "};"))).compile();
        spoon.reflect.declaration.CtMethod<?> foo = clazz.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "foo")).get(0);
        spoon.reflect.code.CtVariableRead<?> a = ((spoon.reflect.code.CtVariableRead<?>) (clazz.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtVariableRead.class)).get(0)));
        org.junit.Assert.assertEquals(1, a.getTypeCasts().size());
        org.junit.Assert.assertEquals("addConsumedAnnotationType(((java.lang.Class<A>) (x)))", foo.getBody().getStatements().get(1).toString());
    }

    @org.junit.Test
    public void testCase4() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.casts", "Castings");
        final spoon.reflect.declaration.CtMethod<?> getValueMethod = type.getMethodsByName("getValue").get(0);
        final spoon.reflect.code.CtInvocation<?> getValueInvocation = spoon.reflect.visitor.Query.getElements(type, new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtInvocation<?>>(spoon.reflect.code.CtInvocation.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtInvocation element) {
                return ("getValue".equals(element.getExecutable().getSimpleName())) && (super.matches(element));
            }
        }).get(0);
        org.junit.Assert.assertEquals("T", getValueMethod.getType().getSimpleName());
        org.junit.Assert.assertEquals("T", getValueMethod.getParameters().get(0).getType().getActualTypeArguments().get(0).toString());
        org.junit.Assert.assertEquals(type.getFactory().Class().INTEGER, getValueInvocation.getType());
    }

    @org.junit.Test
    public void testTypeAnnotationOnCast() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.casts.Castings> aCastings = spoon.testing.utils.ModelUtils.buildClass(spoon.test.casts.Castings.class);
        final spoon.reflect.code.CtLocalVariable local = aCastings.getMethod("bar").getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtLocalVariable.class)).get(0);
        org.junit.Assert.assertEquals(1, ((spoon.reflect.reference.CtTypeReference) (local.getDefaultExpression().getTypeCasts().get(0))).getAnnotations().size());
        org.junit.Assert.assertEquals((("((java.lang.@spoon.test.casts.Castings.TypeAnnotation(integer = 1)" + (java.lang.System.lineSeparator())) + "String) (\"\"))"), local.getDefaultExpression().toString());
    }
}

