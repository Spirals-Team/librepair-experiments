package spoon.reflect.visitor;


public class CtVisitorTest {
    @org.junit.Test
    public void testMethodsInVisitor() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addProcessor(new spoon.reflect.visitor.processors.CheckVisitorTestProcessor(spoon.reflect.visitor.CtVisitor.class).withVisitors());
        launcher.addInputResource("./src/main/java/spoon/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/reflect/reference");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/reference");
        launcher.addInputResource("./src/main/java/spoon/reflect/visitor/CtVisitor.java");
        launcher.run();
    }
}

