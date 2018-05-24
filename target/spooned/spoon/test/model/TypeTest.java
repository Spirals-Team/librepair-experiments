package spoon.test.model;


public class TypeTest {
    @org.junit.Test
    public void testGetAllExecutables() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.model", "Foo");
        org.junit.Assert.assertEquals(1, type.getDeclaredFields().size());
        org.junit.Assert.assertEquals(3, type.getMethods().size());
        org.junit.Assert.assertEquals(4, type.getDeclaredExecutables().size());
        org.junit.Assert.assertEquals(2, type.getAllFields().size());
        org.junit.Assert.assertEquals(1, type.getConstructors().size());
        org.junit.Assert.assertEquals(16, type.getAllMethods().size());
        org.junit.Assert.assertEquals(12, type.getFactory().Type().get(java.lang.Object.class).getAllMethods().size());
        java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> allExecutables = type.getAllExecutables();
        org.junit.Assert.assertEquals(17, allExecutables.size());
    }

    @org.junit.Test
    public void testGetUsedTypes() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.model", "Foo");
        spoon.reflect.factory.TypeFactory tf = type.getFactory().Type();
        java.util.Set<spoon.reflect.reference.CtTypeReference<?>> usedTypes = type.getUsedTypes(true);
        org.junit.Assert.assertEquals(3, usedTypes.size());
        org.junit.Assert.assertTrue(usedTypes.contains(tf.createReference(spoon.test.model.Bar.class)));
        org.junit.Assert.assertTrue(usedTypes.contains(tf.createReference(spoon.test.model.Baz.class)));
        org.junit.Assert.assertTrue(usedTypes.contains(tf.createReference(spoon.test.model.Baz.Inner.class)));
        org.junit.Assert.assertEquals(0, type.getUsedTypes(false).size());
    }

    @org.junit.Test
    public void superclassTest() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.model.testclasses", "InterfaceSuperclass");
        java.util.Set<spoon.reflect.reference.CtTypeReference<?>> interfaces = type.getSuperInterfaces();
        org.junit.Assert.assertEquals(1, interfaces.size());
        spoon.reflect.reference.CtTypeReference<?> inface = interfaces.iterator().next();
        org.junit.Assert.assertNull(inface.getSuperclass());
    }

    @org.junit.Test
    public void testGetUsedTypesForTypeInRootPackage() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> cl = spoon.testing.utils.ModelUtils.createFactory().Code().createCodeSnippetStatement("class X { X x; }").compile();
        org.junit.Assert.assertEquals(0, cl.getUsedTypes(false).size());
    }

    @org.junit.Test
    public void testGetDeclaredOrIheritedFieldOnType() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.model", "ClassWithSuperAndIFace");
        org.junit.Assert.assertEquals("classField", type.getDeclaredOrInheritedField("classField").getSimpleName());
        org.junit.Assert.assertEquals("i", type.getDeclaredOrInheritedField("i").getSimpleName());
        org.junit.Assert.assertNull(type.getDeclaredOrInheritedField("fooMethod"));
        org.junit.Assert.assertEquals("j", type.getDeclaredOrInheritedField("j").getSimpleName());
        org.junit.Assert.assertEquals("IFACE_FIELD_1", type.getDeclaredOrInheritedField("IFACE_FIELD_1").getSimpleName());
        org.junit.Assert.assertEquals("IFACE_FIELD_2", type.getDeclaredOrInheritedField("IFACE_FIELD_2").getSimpleName());
        org.junit.Assert.assertNull(type.getDeclaredOrInheritedField("notExists"));
    }

    @org.junit.Test
    public void testGetDeclaredOrIheritedFieldOnTypeRef() throws java.lang.Exception {
        spoon.reflect.reference.CtTypeReference<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.model", "ClassWithSuperAndIFace").getReference();
        org.junit.Assert.assertEquals("classField", type.getDeclaredOrInheritedField("classField").getSimpleName());
        org.junit.Assert.assertEquals("i", type.getDeclaredOrInheritedField("i").getSimpleName());
        org.junit.Assert.assertNull(type.getDeclaredOrInheritedField("fooMethod"));
        org.junit.Assert.assertEquals("j", type.getDeclaredOrInheritedField("j").getSimpleName());
        org.junit.Assert.assertEquals("IFACE_FIELD_1", type.getDeclaredOrInheritedField("IFACE_FIELD_1").getSimpleName());
        org.junit.Assert.assertEquals("IFACE_FIELD_2", type.getDeclaredOrInheritedField("IFACE_FIELD_2").getSimpleName());
        org.junit.Assert.assertNull(type.getDeclaredOrInheritedField("notExists"));
    }

    @org.junit.Test
    public void testGetDeclaredOrIheritedFieldByReflection() throws java.lang.Exception {
        spoon.reflect.reference.CtTypeReference<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.model", "ClassWithSuperOutOfModel").getReference();
        org.junit.Assert.assertEquals("buf", type.getDeclaredOrInheritedField("buf").getSimpleName());
        org.junit.Assert.assertEquals("count", type.getDeclaredOrInheritedField("count").getSimpleName());
    }

    @org.junit.Test
    public void testTypeInfoIsInterface() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> clazz = spoon.testing.utils.ModelUtils.build("spoon.test.model", "ClassWithSuperOutOfModel");
        checkIsSomething("class", clazz);
        spoon.reflect.declaration.CtType<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.model", "InterfaceWrithFields");
        checkIsSomething("interface", type);
        checkIsSomething("enum", type.getFactory().Enum().create(type.getPackage(), "someEnum"));
        spoon.reflect.declaration.CtType<?> ctAnnotation = type.getFactory().Annotation().create(type.getPackage(), "someAnnotation");
        checkIsSomething("annotation", ctAnnotation);
        spoon.reflect.declaration.CtTypeParameter ctTypeParam = type.getFactory().Core().createTypeParameter();
        ctTypeParam.setSimpleName("T");
        clazz.addFormalCtTypeParameter(ctTypeParam);
        checkIsSomething("generics", ctTypeParam);
    }

    private void checkIsSomething(java.lang.String expectedType, spoon.reflect.declaration.CtType type) {
        _checkIsSomething(expectedType, type);
        _checkIsSomething(expectedType, type.getReference());
    }

    private void _checkIsSomething(java.lang.String expectedType, spoon.reflect.declaration.CtTypeInformation type) {
        org.junit.Assert.assertEquals("interface".equals(expectedType), type.isInterface());
        org.junit.Assert.assertEquals("class".equals(expectedType), type.isClass());
        org.junit.Assert.assertEquals("annotation".equals(expectedType), type.isAnnotationType());
        org.junit.Assert.assertEquals("anonymous".equals(expectedType), type.isAnonymous());
        org.junit.Assert.assertEquals("enum".equals(expectedType), type.isEnum());
        org.junit.Assert.assertEquals("generics".equals(expectedType), type.isGenerics());
    }
}

