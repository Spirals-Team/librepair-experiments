package spoon.support.compiler.classpath;


public class ComputeClasspathTest {
    private static final java.lang.String TEST_CLASSPATH = (((((("./src/test/java/spoon/test/annotation/" + (java.io.File.pathSeparator)) + "./src/test/java/spoon/test/api/") + (java.io.File.pathSeparator)) + "./src/test/java/spoon/test/arrays/") + (java.io.File.pathSeparator)) + "./src/test/java/spoon/test/casts/") + (java.io.File.pathSeparator);

    private spoon.support.compiler.jdt.JDTBasedSpoonCompiler compiler;

    private java.lang.Class<? extends spoon.support.compiler.jdt.JDTBasedSpoonCompiler> compilerClass;

    private java.lang.String[] systemClasspath;

    @org.junit.Before
    public void setUp() {
        spoon.Launcher launcher = new spoon.Launcher() {
            public spoon.SpoonModelBuilder createCompiler(spoon.reflect.factory.Factory factory) {
                return new spoon.support.compiler.jdt.JDTBasedSpoonCompiler(factory);
            }
        };
        launcher.getEnvironment().setLevel("OFF");
        this.compiler = ((spoon.support.compiler.jdt.JDTBasedSpoonCompiler) (launcher.createCompiler()));
        this.compilerClass = compiler.getClass();
        this.systemClasspath = spoon.support.compiler.classpath.ComputeClasspathTest.TEST_CLASSPATH.split(java.io.File.pathSeparator);
    }

    @org.junit.Test
    public void testSourceClasspath() throws java.lang.IllegalAccessException, java.lang.NoSuchMethodException, java.lang.reflect.InvocationTargetException {
        final spoon.compiler.builder.ClasspathOptions options = new spoon.compiler.builder.ClasspathOptions().classpath(systemClasspath);
        org.junit.Assert.assertEquals(("-cp " + (spoon.support.compiler.classpath.ComputeClasspathTest.TEST_CLASSPATH)), java.lang.String.join(" ", options.build()));
    }
}

