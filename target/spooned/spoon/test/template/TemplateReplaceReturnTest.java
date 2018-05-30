package spoon.test.template;


public class TemplateReplaceReturnTest {
    @org.junit.Test
    public void testReturnReplaceTemplate() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addTemplateResource(new spoon.support.compiler.FileSystemFile("./src/test/java/spoon/test/template/testclasses/ReturnReplaceTemplate.java"));
        launcher.buildModel();
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        spoon.reflect.code.CtBlock<java.lang.String> model = ((spoon.reflect.code.CtBlock) (factory.Class().get(spoon.test.template.testclasses.ReturnReplaceTemplate.class).getMethod("sample").getBody()));
        spoon.reflect.declaration.CtClass<?> resultKlass = factory.Class().create(factory.Package().getOrCreate("spoon.test.template"), "ReturnReplaceResult");
        new spoon.test.template.testclasses.ReturnReplaceTemplate(model).apply(resultKlass);
        org.junit.Assert.assertEquals("{ if (((java.lang.System.currentTimeMillis()) % 2L) == 0) { return \"Panna\"; }else { return \"Orel\"; }}", spoon.testing.utils.ModelUtils.getOptimizedString(resultKlass.getMethod("method").getBody()));
        launcher.setSourceOutputDirectory(new java.io.File("./target/spooned/"));
        launcher.getModelBuilder().generateProcessedSourceFiles(spoon.OutputType.CLASSES);
        spoon.testing.utils.ModelUtils.canBeBuilt(new java.io.File("./target/spooned/spoon/test/template/ReturnReplaceResult.java"), 8);
    }

    @org.junit.Test
    public void testNoReturnReplaceTemplate() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addTemplateResource(new spoon.support.compiler.FileSystemFile("./src/test/java/spoon/test/template/testclasses/ReturnReplaceTemplate.java"));
        launcher.buildModel();
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        spoon.reflect.code.CtExpression<java.lang.String> model = factory.createLiteral("AStringLiteral");
        spoon.reflect.declaration.CtClass<?> resultKlass = factory.Class().create(factory.Package().getOrCreate("spoon.test.template"), "ReturnReplaceResult");
        new spoon.test.template.testclasses.ReturnReplaceTemplate(model).apply(resultKlass);
        org.junit.Assert.assertEquals("{ return \"AStringLiteral\";}", spoon.testing.utils.ModelUtils.getOptimizedString(resultKlass.getMethod("method").getBody()));
        launcher.setSourceOutputDirectory(new java.io.File("./target/spooned/"));
        launcher.getModelBuilder().generateProcessedSourceFiles(spoon.OutputType.CLASSES);
        spoon.testing.utils.ModelUtils.canBeBuilt(new java.io.File("./target/spooned/spoon/test/template/ReturnReplaceResult.java"), 8);
    }
}

