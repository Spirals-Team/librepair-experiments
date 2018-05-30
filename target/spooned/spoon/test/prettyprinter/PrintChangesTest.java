package spoon.test.prettyprinter;


public class PrintChangesTest {
    @org.junit.Test
    public void testPrintUnchaged() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.prettyprinter.testclasses.ToBeChanged.class);
        spoon.reflect.factory.Factory f = ctClass.getFactory();
        new spoon.experimental.modelobs.SourceFragmentsTreeCreatingChangeCollector().attachTo(f.getEnvironment());
        spoon.reflect.visitor.printer.change.ChangesAwareDefaultJavaPrettyPrinter printer = new spoon.reflect.visitor.printer.change.ChangesAwareDefaultJavaPrettyPrinter(f.getEnvironment());
        spoon.reflect.cu.CompilationUnit cu = f.CompilationUnit().getOrCreate(ctClass);
        java.util.List<spoon.reflect.declaration.CtType<?>> toBePrinted = new java.util.ArrayList<>();
        toBePrinted.add(ctClass);
        printer.calculate(cu, toBePrinted);
        org.junit.Assert.assertEquals(ctClass.getPosition().getCompilationUnit().getOriginalSourceCode(), printer.getResult());
    }

    @org.junit.Test
    public void testPrintChanged() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/prettyprinter/testclasses/ToBeChanged.java");
        launcher.getEnvironment().setCommentEnabled(true);
        launcher.getEnvironment().setAutoImports(true);
        launcher.buildModel();
        spoon.reflect.factory.Factory f = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = launcher.getFactory().Class().get(spoon.test.prettyprinter.testclasses.ToBeChanged.class);
        new spoon.experimental.modelobs.SourceFragmentsTreeCreatingChangeCollector().attachTo(f.getEnvironment());
        ctClass.getField("string").setSimpleName("modified");
        spoon.reflect.visitor.printer.change.ChangesAwareDefaultJavaPrettyPrinter printer = new spoon.reflect.visitor.printer.change.ChangesAwareDefaultJavaPrettyPrinter(f.getEnvironment());
        spoon.reflect.cu.CompilationUnit cu = f.CompilationUnit().getOrCreate(ctClass);
        java.util.List<spoon.reflect.declaration.CtType<?>> toBePrinted = new java.util.ArrayList<>();
        toBePrinted.add(ctClass);
        printer.calculate(cu, toBePrinted);
        org.junit.Assert.assertEquals(ctClass.getPosition().getCompilationUnit().getOriginalSourceCode(), printer.getResult());
    }
}

