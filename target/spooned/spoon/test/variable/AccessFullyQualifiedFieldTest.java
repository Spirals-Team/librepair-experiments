package spoon.test.variable;


import java.util.List;
import spoon.Launcher;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.PrettyPrinter;
import spoon.testing.utils.ModelUtils;


public class AccessFullyQualifiedFieldTest {
    @org.junit.Test
    public void testCheckAssignmentContracts() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = ModelUtils.build(spoon.test.variable.testclasses.Tacos.class);
        spoon.test.main.MainTest.checkAssignmentContracts(factory.Package().getRootPackage());
    }

    private String buildResourceAndReturnResult(String pathResource, String output) {
        Launcher spoon = new Launcher();
        spoon.addInputResource(pathResource);
        spoon.setSourceOutputDirectory(output);
        spoon.run();
        PrettyPrinter prettyPrinter = spoon.createPrettyPrinter();
        CtType element = spoon.getFactory().Class().getAll().get(0);
        List<CtType<?>> toPrint = new java.util.ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        return prettyPrinter.getResult();
    }

    @org.junit.Test
    public void testNoFQNWhenShadowedByField() throws java.lang.Exception {
        String pathResource = "src/test/java/spoon/test/variable/testclasses/BurritosFielded.java";
        String output = ("target/spooned-" + (this.getClass().getSimpleName())) + "-Field/";
        String result = this.buildResourceAndReturnResult(pathResource, output);
        org.junit.Assert.assertTrue("The java file should contain import for Launcher", result.contains("import spoon.Launcher;"));
        org.junit.Assert.assertTrue("The xx variable is attributed with Launcher.SPOONED_CLASSES", result.contains("xx = Launcher.SPOONED_CLASSES"));
        ModelUtils.canBeBuilt(output, 7);
    }

    @org.junit.Test
    public void testNoFQNWhenShadowedByLocalVariable() throws java.lang.Exception {
        String output = ("target/spooned-" + (this.getClass().getSimpleName())) + "-Local/";
        String pathResource = "src/test/java/spoon/test/variable/testclasses/Burritos.java";
        String result = this.buildResourceAndReturnResult(pathResource, output);
        org.junit.Assert.assertTrue("The java file should contain import for Launcher", result.contains("import spoon.Launcher;"));
        org.junit.Assert.assertTrue("The x variable should be attributed with SPOONED_CLASSES", result.contains("x = Launcher.SPOONED_CLASSES"));
        org.junit.Assert.assertTrue("The java.util.Map is not imported", (!(result.contains("import java.util.Map"))));
        org.junit.Assert.assertTrue("The Map type use FQN", result.contains("java.util.Map uneMap"));
        org.junit.Assert.assertTrue("The other variable use FQN too", result.contains("ForStaticVariables.Map"));
        ModelUtils.canBeBuilt(output, 7);
    }

    @org.junit.Test
    public void testNoFQNWhenUsedInInnerClassAndShadowedByLocalVariable() throws java.lang.Exception {
        String output = ("target/spooned-" + (this.getClass().getSimpleName())) + "-StaticMethod/";
        String pathResource = "src/test/java/spoon/test/variable/testclasses/BurritosStaticMethod.java";
        String result = this.buildResourceAndReturnResult(pathResource, output);
        org.junit.Assert.assertTrue("The inner class should contain call using import", result.contains(" toto();"));
        ModelUtils.canBeBuilt(output, 7);
    }

    @org.junit.Test
    public void testNoFQNWhenUsedInTryCatch() throws java.lang.Exception {
        String output = ("target/spooned-" + (this.getClass().getSimpleName())) + "-TryCatch/";
        String pathResource = "src/test/java/spoon/test/variable/testclasses/BurritosWithTryCatch.java";
        String result = this.buildResourceAndReturnResult(pathResource, output);
        org.junit.Assert.assertTrue("The java file should contain import for Launcher", result.contains("import spoon.Launcher;"));
        org.junit.Assert.assertTrue("The xx variable should be attributed with SPOONED_CLASSES", result.contains("xx = Launcher.SPOONED_CLASSES"));
        ModelUtils.canBeBuilt(output, 7);
    }

    @org.junit.Test
    public void testNoFQNWhenUsedInLoop() throws java.lang.Exception {
        String output = ("target/spooned-" + (this.getClass().getSimpleName())) + "-Loop/";
        String pathResource = "src/test/java/spoon/test/variable/testclasses/BurritosWithLoop.java";
        String result = this.buildResourceAndReturnResult(pathResource, output);
        org.junit.Assert.assertTrue("The java file should contain import for Launcher", result.contains("import spoon.Launcher;"));
        org.junit.Assert.assertTrue("The xx variable should be attributed with SPOONED_CLASSES", result.contains("xx = Launcher.SPOONED_CLASSES"));
        ModelUtils.canBeBuilt(output, 7);
    }

    @org.junit.Test
    public void testStaticImportWithAutoImport() throws java.lang.Exception {
        String output = ("target/spooned-" + (this.getClass().getSimpleName())) + "-MultiAutoImport/";
        String pathResource = "src/test/java/spoon/test/variable/testclasses/MultiBurritos.java";
        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{ "--with-imports" });
        spoon.addInputResource(pathResource);
        spoon.setSourceOutputDirectory(output);
        spoon.run();
        PrettyPrinter prettyPrinter = spoon.createPrettyPrinter();
        CtType element = spoon.getFactory().Class().getAll().get(0);
        List<CtType<?>> toPrint = new java.util.ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String result = prettyPrinter.getResult();
        org.junit.Assert.assertTrue("The result should contain a static import for spoon.Launcher.SPOONED_CLASSES", result.contains("import static spoon.Launcher.SPOONED_CLASSES;"));
        org.junit.Assert.assertTrue("The variable x should be assigned with only SPOONED_CLASSES", result.contains("Object x = SPOONED_CLASSES;"));
        org.junit.Assert.assertTrue("The result should not contain a static import for spoon.test.variable.testclasses.ForStaticVariables.foo as it is in the same package", (!(result.contains("import static spoon.test.variable.testclasses.ForStaticVariables.foo;"))));
        org.junit.Assert.assertTrue("The result should not contain a import static for spoon.test.variable.testclasses.MultiBurritos.toto as it is in the same class", (!(result.contains("import static spoon.test.variable.testclasses.MultiBurritos.toto;"))));
        org.junit.Assert.assertTrue("The result should not contain a FQN for toto", (!(result.contains("spoon.test.variable.testclasses.MultiBurritos.toto();"))));
        org.junit.Assert.assertTrue("The result should not contain a FQN for spoon access", (!(result.contains("spoon.test.variable.testclasses.MultiBurritos.spoon = \"truc\";"))));
        org.junit.Assert.assertTrue("The result should not contain a FQN for foo", (!(result.contains("spoon.test.variable.testclasses.ForStaticVariables.foo();"))));
        ModelUtils.canBeBuilt(output, 7);
    }

    @org.junit.Test
    public void testNoFQNAndStaticImport() throws java.lang.Exception {
        String output = ("target/spooned-" + (this.getClass().getSimpleName())) + "-MultiNoAutoImport/";
        String pathResource = "src/test/java/spoon/test/variable/testclasses/MultiBurritos.java";
        String result = this.buildResourceAndReturnResult(pathResource, output);
        org.junit.Assert.assertTrue("The result should contain a static import for spoon.Launcher.SPOONED_CLASSES", result.contains("import static spoon.Launcher.SPOONED_CLASSES;"));
        org.junit.Assert.assertTrue("The result should not contain a FQN call for foo (i.e. spoon.test.variable.testclasses.ForStaticVariables.foo())", (!(result.contains("spoon.test.variable.testclasses.ForStaticVariables.foo()"))));
        ModelUtils.canBeBuilt(output, 7);
    }

    @org.junit.Test
    public void testPrivateStaticImportShouldNotBeImportedInSameClass() throws java.lang.Exception {
        String output = ("target/spooned-" + (this.getClass().getSimpleName())) + "-privateStatic/";
        String pathResource = "src/test/java/spoon/test/variable/testclasses/digest/DigestUtil.java";
        String result = this.buildResourceAndReturnResult(pathResource, output);
        org.junit.Assert.assertTrue("The result should not contain a static import for STREAM_BUFFER_LENGTH", (!(result.contains("import static spoon.test.variable.testclasses.digest.DigestUtil.STREAM_BUFFER_LENGTH;"))));
        ModelUtils.canBeBuilt(output, 7);
    }
}

