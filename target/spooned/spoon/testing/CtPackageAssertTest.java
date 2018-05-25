package spoon.testing;


public class CtPackageAssertTest {
    @org.junit.Test
    public void testEqualityBetweenTwoCtPackage() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        final spoon.reflect.declaration.CtPackage aRootPackage = factory.Package().getOrCreate("");
        aRootPackage.addType(factory.Class().create("spoon.testing.testclasses.Foo").addModifier(spoon.reflect.declaration.ModifierKind.PUBLIC));
        aRootPackage.addType(factory.Class().create("spoon.testing.testclasses.Bar").addModifier(spoon.reflect.declaration.ModifierKind.PUBLIC));
        spoon.testing.Assert.assertThat(spoon.testing.utils.ModelUtils.build(new java.io.File("./src/test/java/spoon/testing/testclasses/")).Package().getRootPackage()).isEqualTo(aRootPackage);
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void testEqualityBetweenTwoDifferentCtPackage() throws java.lang.Exception {
        spoon.testing.Assert.assertThat(spoon.testing.utils.ModelUtils.build(new java.io.File("./src/test/java/spoon/testing/testclasses/")).Package().getRootPackage()).isEqualTo(spoon.testing.utils.ModelUtils.createFactory().Package().getOrCreate("another.package"));
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void testEqualityBetweenTwoCtPackageWithDifferentTypes() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        final spoon.reflect.declaration.CtPackage aRootPackage = factory.Package().getOrCreate("");
        aRootPackage.addType(factory.Class().create("spoon.testing.testclasses.Foo").addModifier(spoon.reflect.declaration.ModifierKind.PUBLIC));
        spoon.testing.Assert.assertThat(spoon.testing.utils.ModelUtils.build(new java.io.File("./src/test/java/spoon/testing/testclasses/")).Package().getRootPackage()).isEqualTo(aRootPackage);
    }
}

