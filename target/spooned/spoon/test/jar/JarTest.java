package spoon.test.jar;


import spoon.Launcher;
import spoon.SpoonModelBuilder;
import spoon.compiler.SpoonResourceHelper;
import spoon.reflect.factory.Factory;


public class JarTest {
    @org.junit.Test
    public void testJar() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        Factory factory = spoon.createFactory();
        factory.getEnvironment().setNoClasspath(true);
        SpoonModelBuilder compiler = spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/resources/sourceJar/test.jar"));
        org.junit.Assert.assertTrue(compiler.build());
        org.junit.Assert.assertEquals(1, factory.getModel().getAllTypes().size());
        org.junit.Assert.assertEquals("spoon.test.strings.Main", factory.getModel().getAllTypes().iterator().next().getQualifiedName());
    }

    @org.junit.Test
    public void testFile() throws java.lang.Exception {
        Launcher launcher = new Launcher();
        SpoonModelBuilder compiler = launcher.createCompiler(launcher.getFactory(), java.util.Arrays.asList(SpoonResourceHelper.createFile(new java.io.File("./src/test/resources/spoon/test/api/Foo.java"))));
        org.junit.Assert.assertTrue(compiler.build());
        org.junit.Assert.assertNotNull(launcher.getFactory().Type().get("Foo"));
    }

    @org.junit.Test
    public void testResource() throws java.lang.Exception {
        Launcher launcher = new Launcher();
        SpoonModelBuilder compiler = launcher.createCompiler(launcher.getFactory(), java.util.Arrays.asList(new spoon.support.compiler.VirtualFile("class Foo {}", "Foo.java")));
        org.junit.Assert.assertTrue(compiler.build());
        org.junit.Assert.assertNotNull(launcher.getFactory().Type().get("Foo"));
    }
}

