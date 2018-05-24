package spoon.test.template;


import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.factory.Factory;
import spoon.support.compiler.FileSystemFile;
import spoon.test.template.testclasses.ClassAccessTemplate;


public class TemplateClassAccessTest {
    @org.junit.Test
    public void testClassAccessTest() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/ClassAccessTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        CtClass<?> resultKlass = factory.Class().create("Result");
        spoon.reflect.code.CtStatement result = new ClassAccessTemplate(String.class).apply(resultKlass);
        org.junit.Assert.assertEquals("java.lang.String.class.getName()", result.toString());
        spoon.reflect.code.CtStatement result2 = new ClassAccessTemplate(null).apply(resultKlass);
        org.junit.Assert.assertEquals("null.getName()", result2.toString());
    }
}

