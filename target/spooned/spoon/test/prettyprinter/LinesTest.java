package spoon.test.prettyprinter;


import spoon.Launcher;
import spoon.compiler.SpoonResourceHelper;


public class LinesTest {
    spoon.reflect.factory.Factory factory;

    @org.junit.Before
    public void setup() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        factory = spoon.createFactory();
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/prettyprinter/Validation.java")).build();
        factory.getEnvironment().setPreserveLineNumbers(true);
        factory.getEnvironment().setAutoImports(false);
    }

    @org.junit.Test
    public void testPrettyPrinterWithLines() throws java.lang.Exception {
        for (spoon.reflect.declaration.CtType<?> t : factory.Type().getAll()) {
            if (t.isTopLevel()) {
                spoon.reflect.visitor.DefaultJavaPrettyPrinter pp = new spoon.reflect.visitor.DefaultJavaPrettyPrinter(factory.getEnvironment());
                pp.calculate(t.getPosition().getCompilationUnit(), t.getPosition().getCompilationUnit().getDeclaredTypes());
            }
        }
        org.junit.Assert.assertEquals(0, factory.getEnvironment().getWarningCount());
        org.junit.Assert.assertEquals(0, factory.getEnvironment().getErrorCount());
        java.lang.String meth = factory.Type().get("spoon.test.prettyprinter.Validation").getMethodsByName("isIdentifier").get(0).toString();
        org.junit.Assert.assertFalse(java.util.regex.Pattern.compile("^\\s", java.util.regex.Pattern.DOTALL).asPredicate().test(meth.toString()));
    }

    @org.junit.Test
    public void testIdenticalPrettyPrinter() throws java.lang.Exception {
        java.lang.String[] options = new java.lang.String[]{ "--output-type", "compilationunits", "--output", "target/testIdenticalPrettyPrinter", "--enable-comments", "--lines", "--with-imports" };
        java.util.List<java.lang.String> paths = new java.util.ArrayList<>();
        paths.add("spoon/test/prettyprinter/testclasses/A.java");
        paths.add("spoon/test/prettyprinter/testclasses/AClass.java");
        final Launcher launcher = new Launcher();
        launcher.setArgs(options);
        for (java.lang.String path : paths) {
            launcher.addInputResource(("./src/test/java/" + path));
        }
        launcher.run();
        final Launcher launcher2 = new Launcher();
        launcher2.setArgs(options);
        for (java.lang.String path : paths) {
            launcher2.addInputResource(("./target/testIdenticalPrettyPrinter/" + path));
        }
        launcher2.run();
        int n = 0;
        java.util.List<spoon.reflect.declaration.CtElement> elements = launcher.getModel().getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtElement.class));
        for (int i = 0; i < (elements.size()); i++) {
            n++;
            spoon.reflect.declaration.CtElement e = elements.get(i);
            spoon.reflect.declaration.CtElement el2 = launcher2.getModel().getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtElement.class)).get(i);
            org.junit.Assert.assertNotSame(e, el2);
            if (e.getPosition().isValidPosition()) {
                org.junit.Assert.assertEquals(((e.toString()) + " not handled"), e.getPosition().getLine(), el2.getPosition().getLine());
                org.junit.Assert.assertEquals(((e.toString()) + " not handled"), e.getPosition().getEndLine(), el2.getPosition().getEndLine());
            }else {
                org.junit.Assert.assertFalse(el2.getPosition().isValidPosition());
            }
        }
        org.junit.Assert.assertTrue((n > 20));
    }

    @org.junit.Test
    public void testCompileWhenUsingLinesArgument() {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--compile", "--with-imports", "--lines" });
        launcher.addInputResource("./src/test/java/spoon/test/prettyprinter/testclasses/FooCasper.java");
        launcher.run();
        java.util.List<spoon.reflect.declaration.CtType> fooCasperClass = launcher.getModel().getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtType.class, "FooCasper"));
        org.junit.Assert.assertEquals(1, fooCasperClass.size());
    }
}

