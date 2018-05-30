package spoon;


public class LauncherTest {
    @org.junit.Test
    public void testInitEnvironmentDefault() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[0]);
        launcher.processArguments();
        final spoon.compiler.Environment environment = launcher.getEnvironment();
        org.junit.Assert.assertFalse(environment.isAutoImports());
        org.junit.Assert.assertFalse(environment.isUsingTabulations());
        org.junit.Assert.assertFalse(environment.isPreserveLineNumbers());
        org.junit.Assert.assertEquals(4, environment.getTabulationSize());
        org.junit.Assert.assertTrue(environment.isCopyResources());
        spoon.support.JavaOutputProcessor processor = ((spoon.support.JavaOutputProcessor) (environment.getDefaultFileGenerator()));
        org.junit.Assert.assertTrue(((processor.getPrinter()) instanceof spoon.reflect.visitor.DefaultJavaPrettyPrinter));
        final spoon.SpoonModelBuilder builder = launcher.getModelBuilder();
        org.junit.Assert.assertEquals(new java.io.File("spooned").getCanonicalFile(), builder.getSourceOutputDirectory());
        org.junit.Assert.assertEquals(0, builder.getInputSources().size());
        org.junit.Assert.assertEquals("UTF-8", environment.getEncoding().displayName());
    }

    @org.junit.Test
    public void testInitEnvironment() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs("--tabs --tabsize 42 --compliance 5 --with-imports -r --lines -o spooned2 -i src/main/java --encoding UTF-16".split(" "));
        launcher.processArguments();
        final spoon.compiler.Environment environment = launcher.getEnvironment();
        org.junit.Assert.assertTrue(environment.isAutoImports());
        org.junit.Assert.assertTrue(environment.isUsingTabulations());
        org.junit.Assert.assertTrue(environment.isPreserveLineNumbers());
        org.junit.Assert.assertEquals(42, environment.getTabulationSize());
        org.junit.Assert.assertEquals(5, environment.getComplianceLevel());
        org.junit.Assert.assertFalse(environment.isCopyResources());
        final spoon.SpoonModelBuilder builder = launcher.getModelBuilder();
        org.junit.Assert.assertEquals(new java.io.File("spooned2").getCanonicalFile(), builder.getSourceOutputDirectory());
        java.util.List<java.io.File> inputSources = new java.util.ArrayList<>(builder.getInputSources());
        org.junit.Assert.assertTrue(inputSources.get(0).getPath().replace('\\', '/').contains("src/main/java"));
        org.junit.Assert.assertEquals("UTF-16", environment.getEncoding().displayName());
    }

    @org.junit.Test
    public void testLauncherInEmptyWorkingDir() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        java.nio.file.Path path = java.nio.file.Files.createTempDirectory("emptydir");
        java.lang.String oldUserDir = java.lang.System.getProperty("user.dir");
        java.lang.System.setProperty("user.dir", path.toFile().getAbsolutePath());
        launcher.addInputResource((oldUserDir + "/src/test/java/spoon/LauncherTest.java"));
        try {
            launcher.buildModel();
        } finally {
            java.lang.System.setProperty("user.dir", oldUserDir);
        }
    }

    @org.junit.Test
    public void testLLauncherBuildModelReturnAModel() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/spoon/test/api/Foo.java");
        launcher.getEnvironment().setNoClasspath(true);
        spoon.reflect.CtModel model = launcher.buildModel();
        org.junit.Assert.assertNotNull(model);
        org.junit.Assert.assertEquals(2, model.getAllTypes().size());
    }
}

