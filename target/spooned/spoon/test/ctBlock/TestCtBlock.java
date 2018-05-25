package spoon.test.ctBlock;


import spoon.Launcher;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCase;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtSwitch;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.NamedElementFilter;
import spoon.test.ctBlock.testclasses.Toto;


public class TestCtBlock {
    @org.junit.Test
    public void testRemoveStatement() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/ctBlock/testclasses/Toto.java");
        spoon.buildModel();
        java.util.List<CtMethod> methods = spoon.getModel().getElements(new NamedElementFilter<>(CtMethod.class, "foo"));
        org.junit.Assert.assertEquals(1, methods.size());
        CtMethod foo = methods.get(0);
        CtBlock block = foo.getBody();
        CtStatement lastStatement = block.getLastStatement();
        org.junit.Assert.assertEquals("i++", lastStatement.toString());
        block.removeStatement(lastStatement);
        CtStatement newLastStatement = block.getLastStatement();
        org.junit.Assert.assertTrue((newLastStatement != lastStatement));
        org.junit.Assert.assertTrue((newLastStatement instanceof spoon.reflect.code.CtIf));
    }

    @org.junit.Test
    public void testAddStatementInBlock() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/ctBlock/testclasses/Toto.java");
        spoon.buildModel();
        CtClass<?> toto = spoon.getFactory().Class().get(Toto.class);
        CtMethod foo = toto.getMethodsByName("foo").get(0);
        CtBlock block = foo.getBody();
        CtBlock originalBlock = block.clone();
        org.junit.Assert.assertEquals(4, block.getStatements().size());
        block.addStatement(1, ((CtStatement) (spoon.getFactory().createInvocation().setExecutable(foo.getReference()))));
        org.junit.Assert.assertEquals(5, block.getStatements().size());
        org.junit.Assert.assertEquals(originalBlock.getStatement(0), block.getStatement(0));
        for (int i = 1; i < 4; i++) {
            org.junit.Assert.assertEquals(originalBlock.getStatement(i), block.getStatement((i + 1)));
        }
    }

    @org.junit.Test
    public void testAddStatementInCase() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/ctBlock/testclasses/Toto.java");
        spoon.buildModel();
        CtClass<?> toto = spoon.getFactory().Class().get(Toto.class);
        CtMethod bar = toto.getMethodsByName("bar").get(0);
        CtSwitch<?> ctSwitch = ((CtSwitch) (bar.getBody().getStatement(0)));
        CtCase firstCase = ctSwitch.getCases().get(0);
        CtCase originalFirstCase = firstCase.clone();
        CtCase secondCase = ctSwitch.getCases().get(1);
        CtCase originalSecondCase = secondCase.clone();
        org.junit.Assert.assertEquals(3, firstCase.getStatements().size());
        firstCase.addStatement(3, spoon.getFactory().createBreak());
        org.junit.Assert.assertEquals(4, firstCase.getStatements().size());
        for (int i = 0; i < 3; i++) {
            org.junit.Assert.assertEquals(originalFirstCase.getStatements().get(i), firstCase.getStatements().get(i));
        }
        org.junit.Assert.assertEquals(2, secondCase.getStatements().size());
        secondCase.addStatement(1, ((CtStatement) (spoon.getFactory().createInvocation().setExecutable(bar.getReference()))));
        org.junit.Assert.assertEquals(3, secondCase.getStatements().size());
        org.junit.Assert.assertEquals(originalSecondCase.getStatements().get(0), secondCase.getStatements().get(0));
        org.junit.Assert.assertEquals(originalSecondCase.getStatements().get(1), secondCase.getStatements().get(2));
    }
}

