package spoon.test.initializers;


public class InitializerTest {
    @org.junit.Test
    public void testModelBuildingStaticInitializer() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.initializers", "InternalClassStaticFieldInit");
        org.junit.Assert.assertEquals("InternalClassStaticFieldInit", type.getSimpleName());
        spoon.reflect.declaration.CtClass<?> InternalClass = type.getNestedType("InternalClass");
        org.junit.Assert.assertTrue(InternalClass.getModifiers().contains(spoon.reflect.declaration.ModifierKind.STATIC));
        spoon.reflect.declaration.CtAnonymousExecutable staticBlock = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtAnonymousExecutable>(spoon.reflect.declaration.CtAnonymousExecutable.class)).get(0);
        org.junit.Assert.assertTrue(staticBlock.getModifiers().contains(spoon.reflect.declaration.ModifierKind.STATIC));
        org.junit.Assert.assertEquals(1, staticBlock.getBody().getStatements().size());
    }

    @org.junit.Test
    public void testModelBuildingInitializer() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.initializers", "InstanceInitializers");
        org.junit.Assert.assertEquals("InstanceInitializers", type.getSimpleName());
        spoon.reflect.declaration.CtField<?> k = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtField.class, "k")).get(0);
        org.junit.Assert.assertTrue(((k.getDefaultExpression()) instanceof spoon.reflect.code.CtConstructorCall));
        spoon.reflect.declaration.CtField<?> l = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtField.class, "l")).get(0);
        org.junit.Assert.assertTrue(((l.getDefaultExpression()) instanceof spoon.reflect.code.CtConstructorCall));
        spoon.reflect.declaration.CtField<?> x = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtField.class, "x")).get(0);
        org.junit.Assert.assertTrue(((x.getDefaultExpression()) == null));
        spoon.reflect.declaration.CtField<?> y = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtField.class, "y")).get(0);
        org.junit.Assert.assertTrue(((y.getDefaultExpression()) instanceof spoon.reflect.code.CtLiteral));
        spoon.reflect.declaration.CtField<?> z = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtField.class, "z")).get(0);
        org.junit.Assert.assertTrue(z.getDefaultExpression().toString().equals("5"));
        spoon.reflect.declaration.CtAnonymousExecutable ex = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtAnonymousExecutable>(spoon.reflect.declaration.CtAnonymousExecutable.class)).get(0);
        org.junit.Assert.assertEquals("x = 3", ex.getBody().getStatements().get(0).toString());
    }
}

