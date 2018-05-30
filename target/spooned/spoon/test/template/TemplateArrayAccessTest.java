package spoon.test.template;


import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.factory.Factory;
import spoon.support.compiler.FileSystemFile;
import spoon.test.template.testclasses.SubstituteArrayAccessTemplate;
import spoon.test.template.testclasses.SubstituteArrayLengthTemplate;


public class TemplateArrayAccessTest {
    @org.junit.Test
    public void testArrayAccess() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/SubstituteArrayAccessTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        CtClass<?> resultKlass = factory.Class().create("Result");
        spoon.reflect.code.CtStatement result = new SubstituteArrayAccessTemplate(new String[]{ "a", null, "b" }).apply(resultKlass);
        org.junit.Assert.assertEquals("new java.lang.String[]{ \"a\", null, \"b\" }.toString()", result.toString());
    }

    @org.junit.Test
    public void testArrayLengthAccess() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/SubstituteArrayLengthTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        CtClass<?> resultKlass = factory.Class().create("Result");
        spoon.reflect.code.CtStatement result = new SubstituteArrayLengthTemplate(new String[]{ "a", null, "b" }).apply(resultKlass);
        org.junit.Assert.assertEquals("if (3 > 0);", result.toString());
    }
}

