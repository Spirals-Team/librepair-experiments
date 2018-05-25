package spoon.support.compiler.jdt;


public class JDTBuilderTest {
    private static final java.lang.String TEST_CLASSPATH = "./src/test/java/spoon/test/";

    @org.junit.Test
    public void testJdtBuilder() throws java.lang.Exception {
        final java.lang.String[] builder = new spoon.compiler.builder.JDTBuilderImpl().classpathOptions(new spoon.compiler.builder.ClasspathOptions().classpath(spoon.support.compiler.jdt.JDTBuilderTest.TEST_CLASSPATH).bootclasspath(spoon.support.compiler.jdt.JDTBuilderTest.TEST_CLASSPATH).binaries(".").encoding("UTF-8")).complianceOptions(new spoon.compiler.builder.ComplianceOptions().compliance(8)).annotationProcessingOptions(new spoon.compiler.builder.AnnotationProcessingOptions().compileProcessors()).advancedOptions(new spoon.compiler.builder.AdvancedOptions().continueExecution().enableJavadoc().preserveUnusedVars()).sources(new spoon.compiler.builder.SourceOptions().sources(".")).build();
        org.junit.Assert.assertEquals("-cp", builder[0]);
        org.junit.Assert.assertEquals(spoon.support.compiler.jdt.JDTBuilderTest.TEST_CLASSPATH, builder[1]);
        org.junit.Assert.assertEquals("-bootclasspath", builder[2]);
        org.junit.Assert.assertEquals(spoon.support.compiler.jdt.JDTBuilderTest.TEST_CLASSPATH, builder[3]);
        org.junit.Assert.assertEquals("-d", builder[4]);
        org.junit.Assert.assertEquals(new java.io.File(".").getAbsolutePath(), builder[5]);
        org.junit.Assert.assertEquals("-encoding", builder[6]);
        org.junit.Assert.assertEquals("UTF-8", builder[7]);
        org.junit.Assert.assertEquals("-1.8", builder[8]);
        org.junit.Assert.assertEquals("-proc:none", builder[9]);
        org.junit.Assert.assertEquals("-noExit", builder[10]);
        org.junit.Assert.assertEquals("-enableJavadoc", builder[11]);
        org.junit.Assert.assertEquals("-preserveAllLocals", builder[12]);
        org.junit.Assert.assertEquals(".", builder[13]);
    }
}

