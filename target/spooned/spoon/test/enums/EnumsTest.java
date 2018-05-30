package spoon.test.enums;


public class EnumsTest {
    @org.junit.Test
    public void testModelBuildingEnum() throws java.lang.Exception {
        spoon.reflect.declaration.CtEnum<spoon.test.enums.Regular> enumeration = spoon.testing.utils.ModelUtils.build("spoon.test.enums", "Regular");
        org.junit.Assert.assertEquals("Regular", enumeration.getSimpleName());
        org.junit.Assert.assertEquals(3, spoon.test.enums.Regular.values().length);
        org.junit.Assert.assertEquals(3, enumeration.getEnumValues().size());
        org.junit.Assert.assertEquals("A", enumeration.getEnumValues().get(0).getSimpleName());
        org.junit.Assert.assertEquals(5, enumeration.getFields().size());
    }

    @org.junit.Test
    public void testAnnotationsOnEnum() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.run(new java.lang.String[]{ "-i", "./src/test/java/spoon/test/enums/testclasses", "-o", "./target/spooned" });
        final spoon.reflect.declaration.CtEnum<?> foo = ((spoon.reflect.declaration.CtEnum) (launcher.getFactory().Type().get(spoon.test.enums.testclasses.Foo.class)));
        org.junit.Assert.assertEquals(1, foo.getFields().size());
        org.junit.Assert.assertEquals(1, foo.getFields().get(0).getAnnotations().size());
        org.junit.Assert.assertEquals(java.lang.Deprecated.class, spoon.test.annotation.AnnotationTest.getActualClassFromAnnotation(foo.getFields().get(0).getAnnotations().get(0)));
        org.junit.Assert.assertEquals((((("public enum Foo {" + (spoon.reflect.visitor.DefaultJavaPrettyPrinter.LINE_SEPARATOR)) + "    @java.lang.Deprecated") + (spoon.reflect.visitor.DefaultJavaPrettyPrinter.LINE_SEPARATOR)) + "    Bar;}"), foo.toString());
    }

    @org.junit.Test
    public void testEnumWithoutField() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.enums.testclasses.Burritos.class);
        final spoon.reflect.declaration.CtType<spoon.test.enums.testclasses.Burritos> burritos = factory.Type().get(spoon.test.enums.testclasses.Burritos.class);
        org.junit.Assert.assertEquals(((((((((("public enum Burritos {" + (spoon.reflect.visitor.DefaultJavaPrettyPrinter.LINE_SEPARATOR)) + "    ;") + (spoon.reflect.visitor.DefaultJavaPrettyPrinter.LINE_SEPARATOR)) + (spoon.reflect.visitor.DefaultJavaPrettyPrinter.LINE_SEPARATOR)) + "    public static void m() {") + (spoon.reflect.visitor.DefaultJavaPrettyPrinter.LINE_SEPARATOR)) + "    }") + (spoon.reflect.visitor.DefaultJavaPrettyPrinter.LINE_SEPARATOR)) + "}"), burritos.toString());
    }

    @org.junit.Test
    public void testGetAllMethods() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.enums.testclasses.Burritos.class);
        final spoon.reflect.declaration.CtType<spoon.test.enums.testclasses.Burritos> burritos = factory.Type().get(spoon.test.enums.testclasses.Burritos.class);
        spoon.reflect.declaration.CtMethod name = factory.Core().createMethod();
        name.setSimpleName("name");
        name.setType(factory.Type().createReference(java.lang.String.class));
        org.junit.Assert.assertTrue(burritos.hasMethod(name));
        org.junit.Assert.assertTrue(burritos.getAllMethods().contains(name));
    }

    @org.junit.Test
    public void testNestedPrivateEnumValues() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.enums.testclasses.NestedEnums.class);
        {
            spoon.reflect.declaration.CtEnum<?> ctEnum = ctClass.getNestedType("PrivateENUM");
            org.junit.Assert.assertEquals(asSet(spoon.reflect.declaration.ModifierKind.PRIVATE), ctEnum.getModifiers());
            org.junit.Assert.assertEquals(asSet(spoon.reflect.declaration.ModifierKind.PRIVATE, spoon.reflect.declaration.ModifierKind.STATIC, spoon.reflect.declaration.ModifierKind.FINAL), ctEnum.getField("VALUE").getModifiers());
        }
        {
            spoon.reflect.declaration.CtEnum<?> ctEnum = ctClass.getNestedType("PublicENUM");
            org.junit.Assert.assertEquals(asSet(spoon.reflect.declaration.ModifierKind.PUBLIC), ctEnum.getModifiers());
            org.junit.Assert.assertEquals(asSet(spoon.reflect.declaration.ModifierKind.PUBLIC, spoon.reflect.declaration.ModifierKind.STATIC, spoon.reflect.declaration.ModifierKind.FINAL), ctEnum.getField("VALUE").getModifiers());
        }
        {
            spoon.reflect.declaration.CtEnum<?> ctEnum = ctClass.getNestedType("ProtectedENUM");
            org.junit.Assert.assertEquals(asSet(spoon.reflect.declaration.ModifierKind.PROTECTED), ctEnum.getModifiers());
            org.junit.Assert.assertEquals(asSet(spoon.reflect.declaration.ModifierKind.PROTECTED, spoon.reflect.declaration.ModifierKind.STATIC, spoon.reflect.declaration.ModifierKind.FINAL), ctEnum.getField("VALUE").getModifiers());
        }
        {
            spoon.reflect.declaration.CtEnum<?> ctEnum = ctClass.getNestedType("PackageProtectedENUM");
            org.junit.Assert.assertEquals(asSet(), ctEnum.getModifiers());
            org.junit.Assert.assertEquals(asSet(spoon.reflect.declaration.ModifierKind.STATIC, spoon.reflect.declaration.ModifierKind.FINAL), ctEnum.getField("VALUE").getModifiers());
        }
    }

    private <T> java.util.Set<T> asSet(T... values) {
        return new java.util.HashSet<>(java.util.Arrays.asList(values));
    }
}

