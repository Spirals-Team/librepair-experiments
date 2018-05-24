package spoon.test.template;


public class TemplateEnumAccessTest {
    @org.junit.Test
    public void testEnumAccessTest() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addTemplateResource(new spoon.support.compiler.FileSystemFile("./src/test/java/spoon/test/template/testclasses/EnumAccessTemplate.java"));
        launcher.buildModel();
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        spoon.reflect.declaration.CtClass<?> resultKlass = factory.Class().create(factory.Package().getOrCreate("spoon.test.template"), "EnumAccessResult");
        new spoon.test.template.testclasses.EnumAccessTemplate(java.lang.annotation.ElementType.FIELD, launcher.getFactory()).apply(resultKlass);
        org.junit.Assert.assertEquals("java.lang.annotation.ElementType.FIELD.name()", resultKlass.getMethod("method").getBody().getStatement(0).toString());
        launcher.setSourceOutputDirectory(new java.io.File("./target/spooned/"));
        launcher.getModelBuilder().generateProcessedSourceFiles(spoon.OutputType.CLASSES);
        spoon.testing.utils.ModelUtils.canBeBuilt(new java.io.File("./target/spooned/spoon/test/template/EnumAccessResult.java"), 8);
    }
}

