package spoon.test.imports;


public class ImportAndExtendWithPackageNameTest {
    private static final java.lang.String inputResource = "./src/test/resources/import-resources/ImportAndExtendWithPackageName.java";

    @org.junit.Test
    public void testBuildModel() {
        final spoon.Launcher runLaunch = new spoon.Launcher();
        runLaunch.getEnvironment().setNoClasspath(true);
        runLaunch.addInputResource(spoon.test.imports.ImportAndExtendWithPackageNameTest.inputResource);
        runLaunch.buildModel();
        final java.util.Collection<spoon.reflect.declaration.CtType<?>> types = runLaunch.getModel().getAllTypes();
        org.junit.Assert.assertSame(1, types.size());
        final spoon.reflect.declaration.CtType type = types.iterator().next();
        org.junit.Assert.assertEquals("ImportAndExtendWithPackageName", type.getSimpleName());
        final spoon.reflect.reference.CtTypeReference superClass = type.getSuperclass();
        org.junit.Assert.assertEquals("LLkParser", superClass.getSimpleName());
    }
}

