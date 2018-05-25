package spoon.test.javadoc;


public class JavaDocTest {
    @org.junit.Test
    public void testJavaDocReprint() throws java.lang.Exception {
        spoon.SpoonAPI launcher = new spoon.Launcher();
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setCommentEnabled(true);
        launcher.getEnvironment().setCopyResources(false);
        launcher.addInputResource("./src/test/java/spoon/test/javadoc/testclasses/");
        launcher.setSourceOutputDirectory("./target/spooned/");
        launcher.run();
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        spoon.reflect.declaration.CtClass<?> aClass = factory.Class().get(spoon.test.javadoc.testclasses.Bar.class);
        org.junit.Assert.assertEquals((((((((((((((((((((((((("public class Bar {" + (java.lang.System.lineSeparator())) + "    /**") + (java.lang.System.lineSeparator())) + "     * Creates an annotation type.") + (java.lang.System.lineSeparator())) + "     *") + (java.lang.System.lineSeparator())) + "     * @param owner") + (java.lang.System.lineSeparator())) + "     * \t\tthe package of the annotation type") + (java.lang.System.lineSeparator())) + "     * @param simpleName") + (java.lang.System.lineSeparator())) + "     * \t\tthe name of annotation") + (java.lang.System.lineSeparator())) + "     */") + (java.lang.System.lineSeparator())) + "    public <T> CtAnnotationType<?> create(CtPackage owner, String simpleName) {") + (java.lang.System.lineSeparator())) + "        return null;") + (java.lang.System.lineSeparator())) + "    }") + (java.lang.System.lineSeparator())) + "}"), aClass.toString());
        org.junit.Assert.assertEquals("", aClass.getDocComment());
        org.junit.Assert.assertEquals(((((((((("Creates an annotation type." + (java.lang.System.lineSeparator())) + "@param owner") + (java.lang.System.lineSeparator())) + "\t\tthe package of the annotation type") + (java.lang.System.lineSeparator())) + "@param simpleName") + (java.lang.System.lineSeparator())) + "\t\tthe name of annotation") + (java.lang.System.lineSeparator())), aClass.getMethodsByName("create").get(0).getDocComment());
        org.junit.Assert.assertEquals(2, aClass.getMethodsByName("create").get(0).getComments().get(0).asJavaDoc().getTags().size());
    }

    @org.junit.Test
    public void testJavadocNotPresentInAST() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setCommentEnabled(false);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/javadoc/testclasses/");
        launcher.run();
        new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public void scan(spoon.reflect.declaration.CtElement element) {
                if (element != null) {
                    org.junit.Assert.assertEquals(0, element.getComments().size());
                }
                super.scan(element);
            }

            @java.lang.Override
            public void visitCtComment(spoon.reflect.code.CtComment comment) {
                org.junit.Assert.fail("Shouldn't have comment in the model.");
                super.visitCtComment(comment);
            }
        }.scan(launcher.getModel().getRootPackage());
    }
}

