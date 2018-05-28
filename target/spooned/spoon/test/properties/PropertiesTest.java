package spoon.test.properties;


import java.util.Arrays;
import spoon.Launcher;
import spoon.SpoonModelBuilder;
import spoon.compiler.SpoonResourceHelper;
import spoon.reflect.factory.Factory;


public class PropertiesTest {
    @org.junit.Test
    public void testNonExistingDirectory() throws java.lang.Exception {
        java.io.File tempFile = java.io.File.createTempFile("SPOON", "SPOON");
        tempFile.delete();
        Launcher spoon = new Launcher();
        Factory factory = spoon.createFactory();
        SpoonModelBuilder compiler = spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/properties/testclasses/Sample.java"));
        compiler.build();
        compiler.instantiateAndProcess(Arrays.asList(spoon.test.properties.SimpleProcessor.class.getName()));
        org.junit.Assert.assertEquals(factory.getEnvironment().getErrorCount(), 0);
    }
}

