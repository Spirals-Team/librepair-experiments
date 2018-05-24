package spoon.test.exceptions;


import spoon.Launcher;
import spoon.compiler.SpoonResourceHelper;
import spoon.reflect.factory.Factory;


public class ExceptionTest {
    @org.junit.Test
    public void testExceptionIfNotCompilable() throws java.lang.Exception {
        try {
            Launcher spoon = new Launcher();
            Factory factory = spoon.createFactory();
            factory.getEnvironment().setNoClasspath(false);
            spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/resources/spoon/test/exceptions/ClassWithError.java")).build();
            org.junit.Assert.fail();
        } catch (spoon.compiler.ModelBuildingException e) {
        }
    }

    @org.junit.Test
    public void testExceptionNoFile() throws java.lang.Exception {
        try {
            Launcher spoon = new Launcher();
            Factory factory = spoon.createFactory();
            spoon.createCompiler(factory, SpoonResourceHelper.resources("this_file_does_not_exist.java")).build();
            org.junit.Assert.fail();
        } catch (java.io.FileNotFoundException e) {
        }
    }

    @org.junit.Test
    public void testExceptionInSnippet() {
        try {
            Factory factory = spoon.testing.utils.ModelUtils.createFactory();
            factory.Code().createCodeSnippetStatement(("" + ((("class X {" + "public void foo() {") + " int x=Foo;") + "}};"))).compile();
            org.junit.Assert.fail();
        } catch (spoon.compiler.ModelBuildingException e) {
        }
    }

    @org.junit.Test
    public void testExceptionInvalidAPI() throws java.lang.Exception {
        try {
            Launcher spoon = new Launcher();
            spoon.getFactory().getEnvironment().setLevel("OFF");
            spoon.SpoonModelBuilder comp = spoon.createCompiler();
            comp.setSourceClasspath("does_not_exist.jar");
            org.junit.Assert.fail();
        } catch (spoon.compiler.InvalidClassPathException e) {
        }
        try {
            Launcher spoon = new Launcher();
            spoon.getFactory().getEnvironment().setLevel("OFF");
            spoon.SpoonModelBuilder comp = spoon.createCompiler();
            comp.setSourceClasspath("src");
        } catch (spoon.compiler.InvalidClassPathException e) {
            org.junit.Assert.fail();
        }
    }

    @org.junit.Test(expected = spoon.compiler.ModelBuildingException.class)
    public void testExceptionDuplicateClass() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        Factory factory = spoon.createFactory();
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/resources/spoon/test/duplicateclasses/Foo.java", "./src/test/resources/spoon/test/duplicateclasses/Bar.java")).build();
    }

    @org.junit.Test
    public void testUnionCatchExceptionInsideLambdaInNoClasspath() {
        Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/UnionCatch.java");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.buildModel();
        java.util.List<spoon.reflect.code.CtCatch> catches = launcher.getFactory().getModel().getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtCatch.class));
        org.junit.Assert.assertEquals(2, catches.size());
        spoon.reflect.code.CtCatchVariable variable1 = catches.get(0).getParameter();
        spoon.reflect.code.CtCatchVariable variable2 = catches.get(1).getParameter();
        org.junit.Assert.assertEquals(variable1.getMultiTypes(), variable2.getMultiTypes());
    }
}

