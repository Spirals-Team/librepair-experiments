package spoon.test.method;


public class MethodTest {
    @org.junit.Test
    public void testClone() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtClass<spoon.test.delete.testclasses.Adobada> adobada = factory.Class().get(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtMethod<?> m2 = adobada.getMethod("m2");
        spoon.reflect.declaration.CtMethod<?> clone = m2.clone();
        clone.setVisibility(spoon.reflect.declaration.ModifierKind.PRIVATE);
        org.junit.Assert.assertEquals(spoon.reflect.declaration.ModifierKind.PUBLIC, m2.getModifiers().iterator().next());
    }

    @org.junit.Test
    public void testSearchMethodWithGeneric() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<spoon.test.method.testclasses.Tacos> aTacos = spoon.testing.utils.ModelUtils.buildClass(spoon.test.method.testclasses.Tacos.class);
        spoon.reflect.declaration.CtMethod<java.lang.Object> method1 = aTacos.getMethod("method1", aTacos.getFactory().Type().integerType());
        org.junit.Assert.assertEquals((("public <T extends java.lang.Integer> void method1(T t) {" + (java.lang.System.lineSeparator())) + "}"), method1.toString());
        method1 = aTacos.getMethod("method1", aTacos.getFactory().Type().stringType());
        org.junit.Assert.assertEquals((("public <T extends java.lang.String> void method1(T t) {" + (java.lang.System.lineSeparator())) + "}"), method1.toString());
        method1 = aTacos.getMethod("method1", aTacos.getFactory().Type().objectType());
        org.junit.Assert.assertEquals((("public <T> void method1(T t) {" + (java.lang.System.lineSeparator())) + "}"), method1.toString());
    }

    @org.junit.Test
    public void testAddSameMethodsTwoTimes() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        final spoon.reflect.declaration.CtClass<java.lang.Object> tacos = factory.Class().create("Tacos");
        final spoon.reflect.declaration.CtMethod<java.lang.Void> method = factory.Method().create(tacos, new java.util.HashSet<>(), factory.Type().voidType(), "m", new java.util.ArrayList<>(), new java.util.HashSet<>());
        try {
            tacos.addMethod(method.clone());
        } catch (java.util.ConcurrentModificationException e) {
            org.junit.Assert.fail();
        }
    }

    @org.junit.Test
    public void testGetAllMethods() throws java.lang.Exception {
        spoon.Launcher l = new spoon.Launcher();
        l.getEnvironment().setNoClasspath(true);
        l.addInputResource("src/test/resources/noclasspath/A3.java");
        l.buildModel();
        java.util.Set<spoon.reflect.declaration.CtMethod<?>> methods = l.getFactory().Class().get("A3").getAllMethods();
        org.junit.Assert.assertEquals(1, methods.stream().filter(( method) -> "foo".equals(method.getSimpleName())).count());
    }

    @org.junit.Test
    public void testGetAllMethodsAdaptingType() throws java.lang.Exception {
        spoon.Launcher l = new spoon.Launcher();
        l.getEnvironment().setNoClasspath(true);
        l.addInputResource("src/test/resources/noclasspath/spring/PropertyComparator.java");
        l.buildModel();
        spoon.reflect.declaration.CtType<?> propertyComparator = l.getModel().getElements(new spoon.reflect.visitor.filter.NamedElementFilter<spoon.reflect.declaration.CtType>(spoon.reflect.declaration.CtType.class, "PropertyComparator")).get(0);
        java.util.Set<spoon.reflect.declaration.CtMethod<?>> allMethods = propertyComparator.getAllMethods();
        boolean compareFound = false;
        for (spoon.reflect.declaration.CtMethod<?> method : allMethods) {
            if (method.getSimpleName().equals("compare")) {
                org.junit.Assert.assertEquals("compare(T,T)", method.getSignature());
                compareFound = true;
            }
        }
        org.junit.Assert.assertTrue(compareFound);
    }
}

