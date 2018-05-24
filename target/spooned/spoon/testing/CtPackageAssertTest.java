package spoon.testing;


public class CtPackageAssertTest {
    @org.junit.Test
    public void testEqualityBetweenTwoCtPackage() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        final spoon.reflect.declaration.CtPackage aRootPackage = factory.Package().getOrCreate("");
        java.util.List<spoon.reflect.declaration.CtType<?>> types1 = aRootPackage.filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtClass.class)).list();
        org.junit.Assert.assertEquals(0, types1.size());
        factory.Class().create("spoon.testing.testclasses.Foo").addModifier(spoon.reflect.declaration.ModifierKind.PUBLIC);
        factory.Class().create("spoon.testing.testclasses.Bar").addModifier(spoon.reflect.declaration.ModifierKind.PUBLIC);
        java.util.List<spoon.reflect.declaration.CtType<?>> types2 = aRootPackage.filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtClass.class)).list();
        org.junit.Assert.assertEquals(2, types2.size());
        final spoon.reflect.declaration.CtPackage aRootPackage2 = spoon.testing.utils.ModelUtils.build(new java.io.File("./src/test/java/spoon/testing/testclasses/")).Package().getRootPackage();
        org.junit.Assert.assertNotSame(aRootPackage, aRootPackage2);
        spoon.testing.Assert.assertThat(aRootPackage2).isEqualTo(aRootPackage);
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void testEqualityBetweenTwoDifferentCtPackage() throws java.lang.Exception {
        spoon.testing.Assert.assertThat(spoon.testing.utils.ModelUtils.build(new java.io.File("./src/test/java/spoon/testing/testclasses/")).Package().getRootPackage()).isEqualTo(spoon.testing.utils.ModelUtils.createFactory().Package().getOrCreate("another.package"));
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void testEqualityBetweenTwoCtPackageWithDifferentTypes() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        final spoon.reflect.declaration.CtPackage aRootPackage = factory.Package().getOrCreate("");
        factory.Class().create("spoon.testing.testclasses.Foo").addModifier(spoon.reflect.declaration.ModifierKind.PUBLIC);
        spoon.testing.Assert.assertThat(spoon.testing.utils.ModelUtils.build(new java.io.File("./src/test/java/spoon/testing/testclasses/")).Package().getRootPackage()).isEqualTo(aRootPackage);
    }

    @org.junit.Test
    public void testAddTypeToPackage() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        final spoon.reflect.declaration.CtType<?> type = factory.Core().createClass();
        type.setSimpleName("X");
        org.junit.Assert.assertSame(factory.getModel().getRootPackage(), type.getPackage());
        org.junit.Assert.assertEquals("X", type.getQualifiedName());
        final spoon.reflect.declaration.CtPackage aPackage1 = factory.Package().getOrCreate("some.package");
        aPackage1.addType(type);
        org.junit.Assert.assertEquals("some.package.X", type.getQualifiedName());
        aPackage1.addType(type);
        org.junit.Assert.assertEquals("some.package.X", type.getQualifiedName());
        final spoon.reflect.declaration.CtPackage aPackage2 = factory.Package().getOrCreate("another.package");
        try {
            aPackage2.addType(type);
            org.junit.Assert.fail();
        } catch (spoon.SpoonException e) {
        }
        org.junit.Assert.assertEquals("some.package.X", type.getQualifiedName());
        type.getPackage().removeType(type);
        aPackage2.addType(type);
        org.junit.Assert.assertEquals("another.package.X", type.getQualifiedName());
    }
}

