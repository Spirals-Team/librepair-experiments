package spoon.test.staticFieldAccess;


import java.io.File;
import java.util.Arrays;
import spoon.Launcher;
import spoon.OutputType;
import spoon.compiler.SpoonResourceHelper;
import spoon.reflect.code.CtBlock;
import spoon.reflect.declaration.CtType;


public class StaticAccessTest {
    Launcher spoon;

    spoon.reflect.factory.Factory factory;

    spoon.SpoonModelBuilder compiler;

    @org.junit.Before
    public void setUp() throws java.lang.Exception {
        spoon = new Launcher();
        factory = spoon.createFactory();
        compiler = spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/staticFieldAccess/internal/", "./src/test/java/spoon/test/staticFieldAccess/StaticAccessBug.java"));
        compiler.build();
    }

    @org.junit.Test
    public void testReferences() throws java.lang.Exception {
        CtType<?> type = ((CtType<?>) (factory.Type().get("spoon.test.staticFieldAccess.StaticAccessBug")));
        CtBlock<?> block = type.getMethod("references").getBody();
        org.junit.Assert.assertTrue(block.getStatement(0).toString().contains("Extends.MY_STATIC_VALUE"));
        org.junit.Assert.assertTrue(block.getStatement(1).toString().contains("Extends.MY_OTHER_STATIC_VALUE"));
    }

    @org.junit.Test
    public void testProcessAndCompile() throws java.lang.Exception {
        compiler.instantiateAndProcess(Arrays.asList(spoon.test.staticFieldAccess.InsertBlockProcessor.class.getName()));
        File tmpdir = new File("target/spooned/staticFieldAccess");
        tmpdir.mkdirs();
        compiler.setSourceOutputDirectory(tmpdir);
        compiler.generateProcessedSourceFiles(OutputType.COMPILATION_UNITS);
        spoon = new Launcher();
        compiler = spoon.createCompiler(SpoonResourceHelper.resources(tmpdir.getAbsolutePath()));
        org.junit.Assert.assertTrue(compiler.build());
    }
}

