package spoon.test.template;


import spoon.Launcher;
import spoon.reflect.code.CtBlock;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.factory.Factory;
import spoon.support.compiler.FileSystemFile;
import spoon.test.template.testclasses.InvocationSubstitutionByExpressionTemplate;
import spoon.test.template.testclasses.InvocationSubstitutionByStatementTemplate;
import spoon.test.template.testclasses.SubstitutionByExpressionTemplate;


public class TemplateInvocationSubstitutionTest {
    @org.junit.Test
    public void testInvocationSubstitutionByStatement() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/InvocationSubstitutionByStatementTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        CtBlock<?> templateArg = factory.Class().get(InvocationSubstitutionByStatementTemplate.class).getMethod("sample").getBody();
        CtClass<?> resultKlass = factory.Class().create("Result");
        spoon.reflect.code.CtStatement result = new InvocationSubstitutionByStatementTemplate(templateArg).apply(resultKlass);
        org.junit.Assert.assertEquals("throw new java.lang.RuntimeException(\"Failed\")", result.toString());
    }

    @org.junit.Test
    public void testInvocationSubstitutionByExpression() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/InvocationSubstitutionByExpressionTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        CtClass<?> resultKlass = factory.Class().create("Result");
        CtBlock<?> result = new InvocationSubstitutionByExpressionTemplate(factory.createLiteral("abc")).apply(resultKlass);
        org.junit.Assert.assertEquals("java.lang.System.out.println(\"abc\".substring(1))", result.getStatement(0).toString());
        org.junit.Assert.assertEquals("java.lang.System.out.println(\"abc\".substring(1))", result.getStatement(1).toString());
    }

    @org.junit.Test
    public void testSubstitutionByExpression() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/SubstitutionByExpressionTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        CtClass<?> resultKlass = factory.Class().create("Result");
        CtBlock<?> result = new SubstitutionByExpressionTemplate(factory.createLiteral("abc")).apply(resultKlass);
        org.junit.Assert.assertEquals("java.lang.System.out.println(\"abc\".substring(1))", result.getStatement(0).toString());
    }
}

