package spoon.test.intercession.insertBefore;


import spoon.Launcher;
import spoon.compiler.SpoonResourceHelper;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.Factory;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;


public class InsertMethodsTest {
    Factory factory;

    private spoon.reflect.declaration.CtClass<?> assignmentClass;

    private spoon.reflect.declaration.CtClass<?> insertExampleClass;

    @org.junit.Before
    public void setup() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        factory = spoon.createFactory();
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/intercession/insertBefore/InsertBeforeExample.java")).build();
        assignmentClass = factory.Code().createCodeSnippetStatement(("class X {" + ((((("public void foo() {" + "  int x=0;") + "  int y=0;") + "  int z=x+y;") + " }") + "};"))).compile();
        insertExampleClass = factory.Package().get("spoon.test.intercession.insertBefore").getType("InsertBeforeExample");
    }

    @org.junit.Test
    public void testInsertBefore() {
        spoon.reflect.declaration.CtMethod<java.lang.Void> foo = ((spoon.reflect.declaration.CtMethod<java.lang.Void>) (assignmentClass.getMethods().toArray()[0]));
        spoon.reflect.code.CtBlock<?> body = foo.getBody();
        org.junit.Assert.assertEquals(3, body.getStatements().size());
        CtStatement s = body.getStatements().get(2);
        org.junit.Assert.assertEquals("int z = x + y", s.toString());
        spoon.reflect.code.CtCodeSnippetStatement stmt = factory.Core().createCodeSnippetStatement();
        stmt.setValue("System.out.println(x);");
        s.insertBefore(stmt);
        org.junit.Assert.assertEquals(4, body.getStatements().size());
        org.junit.Assert.assertSame(stmt, body.getStatements().get(2));
        org.junit.Assert.assertEquals(s.getParent(), stmt.getParent());
    }

    @org.junit.Test
    public void testInsertAfter() throws java.lang.Exception {
        spoon.reflect.declaration.CtMethod<java.lang.Void> foo = ((spoon.reflect.declaration.CtMethod<java.lang.Void>) (assignmentClass.getMethods().toArray()[0]));
        spoon.reflect.code.CtBlock<?> body = foo.getBody();
        org.junit.Assert.assertEquals(3, body.getStatements().size());
        CtStatement s = body.getStatements().get(2);
        org.junit.Assert.assertEquals("int z = x + y", s.toString());
        spoon.reflect.code.CtCodeSnippetStatement stmt = factory.Core().createCodeSnippetStatement();
        stmt.setValue("System.out.println(x);");
        s.insertAfter(stmt);
        org.junit.Assert.assertEquals(4, body.getStatements().size());
        org.junit.Assert.assertSame(stmt, body.getStatements().get(3));
        org.junit.Assert.assertEquals(body, stmt.getParent());
    }

    @org.junit.Test
    public void testInsertBeforeWithoutBrace() throws java.lang.Exception {
        spoon.reflect.declaration.CtMethod<?> ifWithoutBraces_m = insertExampleClass.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "ifWithoutBraces")).get(0);
        spoon.reflect.code.CtCodeSnippetStatement s = factory.Code().createCodeSnippetStatement("return 2");
        CtIf ifWithoutBraces = ifWithoutBraces_m.getElements(new TypeFilter<>(CtIf.class)).get(0);
        ifWithoutBraces.getThenStatement().insertBefore(s);
        org.junit.Assert.assertTrue(((ifWithoutBraces.getThenStatement()) instanceof spoon.reflect.code.CtBlock));
        org.junit.Assert.assertEquals(s, ((spoon.reflect.code.CtBlock<?>) (ifWithoutBraces.getThenStatement())).getStatement(0));
        org.junit.Assert.assertEquals(ifWithoutBraces.getThenStatement(), s.getParent());
    }

    @org.junit.Test
    public void testInsertBeforeWithBrace() throws java.lang.Exception {
        spoon.reflect.declaration.CtMethod<?> ifWithBraces_m = insertExampleClass.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "ifWithBraces")).get(0);
        spoon.reflect.code.CtCodeSnippetStatement s = factory.Code().createCodeSnippetStatement("return 2");
        CtIf ifWithBraces = ifWithBraces_m.getElements(new TypeFilter<CtIf>(CtIf.class)).get(0);
        ifWithBraces.getThenStatement().insertBefore(s);
        org.junit.Assert.assertTrue(((ifWithBraces.getThenStatement()) instanceof spoon.reflect.code.CtBlock));
        org.junit.Assert.assertEquals(s, ((spoon.reflect.code.CtBlock<?>) (ifWithBraces.getThenStatement())).getStatement(0));
        org.junit.Assert.assertEquals(ifWithBraces.getThenStatement(), s.getParent());
    }

    @org.junit.Test
    public void testInsertAfterWithoutBrace() throws java.lang.Exception {
        spoon.reflect.declaration.CtMethod<?> ifWithoutBraces_m = insertExampleClass.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "ifWithoutBraces")).get(0);
        spoon.reflect.code.CtCodeSnippetStatement s = factory.Code().createCodeSnippetStatement("return 2");
        CtIf ifWithoutBraces = ifWithoutBraces_m.getElements(new TypeFilter<>(CtIf.class)).get(0);
        ifWithoutBraces.getThenStatement().insertAfter(s);
        org.junit.Assert.assertTrue(((ifWithoutBraces.getThenStatement()) instanceof spoon.reflect.code.CtBlock));
        org.junit.Assert.assertEquals(s, ((spoon.reflect.code.CtBlock<?>) (ifWithoutBraces.getThenStatement())).getStatement(1));
        org.junit.Assert.assertEquals(ifWithoutBraces.getThenStatement(), s.getParent());
    }

    @org.junit.Test
    public void testInsertAfterWithBrace() throws java.lang.Exception {
        spoon.reflect.declaration.CtMethod<?> ifWithBraces_m = insertExampleClass.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "ifWithBraces")).get(0);
        spoon.reflect.code.CtCodeSnippetStatement s = factory.Code().createCodeSnippetStatement("return 2");
        CtIf ifWithBraces = ifWithBraces_m.getElements(new TypeFilter<CtIf>(CtIf.class)).get(0);
        ifWithBraces.getThenStatement().insertAfter(s);
        org.junit.Assert.assertTrue(((ifWithBraces.getThenStatement()) instanceof spoon.reflect.code.CtBlock));
        org.junit.Assert.assertEquals(s, ((spoon.reflect.code.CtBlock<?>) (ifWithBraces.getThenStatement())).getStatement(1));
        org.junit.Assert.assertEquals(ifWithBraces.getThenStatement(), s.getParent());
    }

    @org.junit.Test
    public void testInsertBeforeSwitchCase() throws java.lang.Exception {
        spoon.reflect.declaration.CtMethod<?> sm = insertExampleClass.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "switchMethod")).get(0);
        spoon.reflect.code.CtSwitch<?> sw = sm.getElements(new TypeFilter<spoon.reflect.code.CtSwitch<?>>(spoon.reflect.code.CtSwitch.class)).get(0);
        spoon.reflect.code.CtCase<?> ctCase1 = sw.getCases().get(2);
        spoon.reflect.code.CtCase<?> ctCase2 = sw.getCases().get(3);
        spoon.reflect.code.CtCodeSnippetStatement snippet = factory.Code().createCodeSnippetStatement("System.out.println(\"foo\")");
        ctCase1.getStatements().get(0).insertBefore(snippet);
        org.junit.Assert.assertEquals(snippet, ctCase1.getStatements().get(0));
        org.junit.Assert.assertEquals(ctCase1, snippet.getParent());
        spoon.reflect.code.CtCodeSnippetStatement snippet2 = snippet.clone();
        ctCase2.getStatements().get(1).insertBefore(snippet2);
        org.junit.Assert.assertEquals(snippet2, ctCase2.getStatements().get(1));
        org.junit.Assert.assertEquals(ctCase2, snippet2.getParent());
        spoon.reflect.code.CtCase<java.lang.Object> caseElem = factory.Core().createCase();
        spoon.reflect.code.CtLiteral<java.lang.Object> literal = factory.Core().createLiteral();
        literal.setValue(1);
        caseElem.setCaseExpression(literal);
        spoon.reflect.code.CtCase<?> ctCase = sw.getCases().get(1);
        ctCase.insertBefore(((CtStatement) (caseElem)));
        org.junit.Assert.assertEquals(5, sw.getCases().size());
        org.junit.Assert.assertEquals(caseElem, sw.getCases().get(1));
        org.junit.Assert.assertEquals(ctCase, sw.getCases().get(2));
        org.junit.Assert.assertEquals(sw, caseElem.getParent());
    }

    @org.junit.Test
    public void testInsertAfterSwitchCase() throws java.lang.Exception {
        spoon.reflect.declaration.CtMethod<?> sm = insertExampleClass.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "switchMethod")).get(0);
        spoon.reflect.code.CtSwitch<?> sw = sm.getElements(new TypeFilter<spoon.reflect.code.CtSwitch<?>>(spoon.reflect.code.CtSwitch.class)).get(0);
        spoon.reflect.code.CtCase<?> ctCase1 = sw.getCases().get(2);
        spoon.reflect.code.CtCase<?> ctCase2 = sw.getCases().get(3);
        spoon.reflect.code.CtCodeSnippetStatement snippet = factory.Code().createCodeSnippetStatement("System.out.println(\"foo\")");
        ctCase1.getStatements().get(0).insertAfter(snippet);
        org.junit.Assert.assertEquals(snippet, ctCase1.getStatements().get(1));
        org.junit.Assert.assertEquals(ctCase1, snippet.getParent());
        spoon.reflect.code.CtCodeSnippetStatement snippet2 = snippet.clone();
        ctCase2.getStatements().get(1).insertAfter(snippet2);
        org.junit.Assert.assertEquals(snippet2, ctCase2.getStatements().get(2));
        org.junit.Assert.assertEquals(ctCase2, snippet2.getParent());
        spoon.reflect.code.CtCase<java.lang.Object> caseElem = factory.Core().createCase();
        spoon.reflect.code.CtLiteral<java.lang.Object> literal = factory.Core().createLiteral();
        literal.setValue(1);
        caseElem.setCaseExpression(literal);
        spoon.reflect.code.CtCase<?> ctCase = sw.getCases().get(1);
        ctCase.insertAfter(((CtStatement) (caseElem)));
        org.junit.Assert.assertEquals(5, sw.getCases().size());
        org.junit.Assert.assertEquals(caseElem, sw.getCases().get(2));
        org.junit.Assert.assertEquals(ctCase, sw.getCases().get(1));
        org.junit.Assert.assertEquals(sw, caseElem.getParent());
    }

    @org.junit.Test
    public void insertBeforeAndUpdateParent() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        Factory factory = spoon.createFactory();
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/resources/spoon/test/intercession/insertBefore/InsertBeforeExample2.java")).build();
        java.util.List<CtWhile> elements = Query.getElements(factory, new TypeFilter<CtWhile>(CtWhile.class));
        org.junit.Assert.assertTrue((1 == (elements.size())));
        CtWhile theWhile = elements.get(0);
        CtElement parent = theWhile.getParent();
        org.junit.Assert.assertTrue((parent instanceof spoon.reflect.code.CtBlock));
        org.junit.Assert.assertTrue(parent.isImplicit());
        CtIf ifParent = ((CtIf) (parent.getParent()));
        CtStatement insert = factory.Code().createCodeSnippetStatement("System.out.println()");
        theWhile.insertBefore(insert);
        CtElement newParent = theWhile.getParent();
        org.junit.Assert.assertTrue((newParent != ifParent));
        org.junit.Assert.assertTrue((newParent instanceof spoon.reflect.code.CtBlock));
        org.junit.Assert.assertFalse(newParent.isImplicit());
    }
}

