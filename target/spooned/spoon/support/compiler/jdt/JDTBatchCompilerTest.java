package spoon.support.compiler.jdt;


public class JDTBatchCompilerTest {
    @org.junit.Test
    public void testCompileGeneratedJavaFile() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/support/compiler/jdt/testclasses/Foo.java");
        launcher.setBinaryOutputDirectory("./target/binaries");
        launcher.getEnvironment().setShouldCompile(true);
        launcher.buildModel();
        launcher.getFactory().Class().create("spoon.Test");
        org.junit.Assert.assertTrue(launcher.getModelBuilder().compile());
        org.junit.Assert.assertTrue(new java.io.File("./target/binaries/spoon/Test.class").exists());
    }
}

