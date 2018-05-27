package spoon.test.labels;


public class TestLabels {
    @org.junit.Test
    public void testLabelsAreDetected() {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/labels/testclasses/ManyLabels.java");
        launcher.buildModel();
        spoon.reflect.declaration.CtMethod mainMethod = launcher.getFactory().getModel().getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "main")).get(0);
        spoon.reflect.code.CtBlock body = mainMethod.getBody();
        org.junit.Assert.assertEquals(2, body.getStatements().size());
        spoon.reflect.code.CtBlock block = ((spoon.reflect.code.CtBlock) (body.getStatement(0)));
        spoon.reflect.code.CtSwitch ctSwitch = ((spoon.reflect.code.CtSwitch) (body.getStatement(1)));
        org.junit.Assert.assertEquals("labelBlock", block.getLabel());
        org.junit.Assert.assertEquals("sw", ctSwitch.getLabel());
        org.junit.Assert.assertTrue(((block.getStatement(1)) instanceof spoon.reflect.code.CtIf));
        spoon.reflect.code.CtIf firstIf = ((spoon.reflect.code.CtIf) (block.getStatement(1)));
        spoon.reflect.code.CtBlock then = firstIf.getThenStatement();
        spoon.reflect.code.CtBreak firstBreak = ((spoon.reflect.code.CtBreak) (then.getStatement(1)));
        org.junit.Assert.assertEquals("labelBlock", firstBreak.getTargetLabel());
        org.junit.Assert.assertSame(block, firstBreak.getLabelledStatement());
        spoon.reflect.code.CtIf secondIf = ((spoon.reflect.code.CtIf) (block.getStatement(2)));
        org.junit.Assert.assertEquals("labelIf", secondIf.getLabel());
        spoon.reflect.code.CtBlock thenBlock = secondIf.getThenStatement();
        spoon.reflect.code.CtIf innerIf = ((spoon.reflect.code.CtIf) (thenBlock.getStatement(0)));
        spoon.reflect.code.CtBlock innerThenBlock = innerIf.getThenStatement();
        spoon.reflect.code.CtBreak breakInnerIf = ((spoon.reflect.code.CtBreak) (innerThenBlock.getStatement(0)));
        org.junit.Assert.assertSame(secondIf, breakInnerIf.getLabelledStatement());
        spoon.reflect.code.CtCase firstCase = ((spoon.reflect.code.CtCase) (ctSwitch.getCases().get(0)));
        java.util.List<spoon.reflect.code.CtStatement> statementList = firstCase.getStatements();
        org.junit.Assert.assertEquals(2, statementList.size());
        spoon.reflect.code.CtDo ctDo = ((spoon.reflect.code.CtDo) (statementList.get(0)));
        org.junit.Assert.assertEquals("label", ctDo.getLabel());
        spoon.reflect.code.CtBreak finalBreak = ((spoon.reflect.code.CtBreak) (statementList.get(1)));
        org.junit.Assert.assertNull(finalBreak.getTargetLabel());
        org.junit.Assert.assertNull(finalBreak.getLabelledStatement());
        spoon.reflect.code.CtBlock doBlock = ((spoon.reflect.code.CtBlock) (ctDo.getBody()));
        spoon.reflect.code.CtWhile ctWhile = ((spoon.reflect.code.CtWhile) (doBlock.getStatement(1)));
        org.junit.Assert.assertEquals("lWhile", ctWhile.getLabel());
        spoon.reflect.code.CtBlock whileBlock = ((spoon.reflect.code.CtBlock) (ctWhile.getBody()));
        spoon.reflect.code.CtFor forLoop = ((spoon.reflect.code.CtFor) (whileBlock.getStatement(0)));
        spoon.reflect.code.CtBreak breakSwitch = ((spoon.reflect.code.CtBreak) (whileBlock.getStatement(1)));
        org.junit.Assert.assertEquals("sw", breakSwitch.getTargetLabel());
        org.junit.Assert.assertSame(ctSwitch, breakSwitch.getLabelledStatement());
        org.junit.Assert.assertEquals("forloop", forLoop.getLabel());
        spoon.reflect.code.CtBlock forBlock = ((spoon.reflect.code.CtBlock) (forLoop.getBody()));
        org.junit.Assert.assertEquals(7, forBlock.getStatements().size());
        spoon.reflect.code.CtIf firstForIf = ((spoon.reflect.code.CtIf) (forBlock.getStatement(1)));
        spoon.reflect.code.CtIf secondForIf = ((spoon.reflect.code.CtIf) (forBlock.getStatement(2)));
        spoon.reflect.code.CtIf thirdForIf = ((spoon.reflect.code.CtIf) (forBlock.getStatement(3)));
        spoon.reflect.code.CtIf fourthForIf = ((spoon.reflect.code.CtIf) (forBlock.getStatement(4)));
        spoon.reflect.code.CtBreak breakItself = ((spoon.reflect.code.CtBreak) (forBlock.getStatement(6)));
        spoon.reflect.code.CtContinue continueFor = ((spoon.reflect.code.CtContinue) (((spoon.reflect.code.CtBlock) (firstForIf.getThenStatement())).getStatement(0)));
        org.junit.Assert.assertSame(forLoop, continueFor.getLabelledStatement());
        spoon.reflect.code.CtContinue continueWhile = ((spoon.reflect.code.CtContinue) (((spoon.reflect.code.CtBlock) (secondForIf.getThenStatement())).getStatement(0)));
        org.junit.Assert.assertSame(ctWhile, continueWhile.getLabelledStatement());
        spoon.reflect.code.CtContinue continueDo = ((spoon.reflect.code.CtContinue) (((spoon.reflect.code.CtBlock) (thirdForIf.getThenStatement())).getStatement(0)));
        org.junit.Assert.assertSame(ctDo, continueDo.getLabelledStatement());
        spoon.reflect.code.CtBreak breakDo = ((spoon.reflect.code.CtBreak) (((spoon.reflect.code.CtBlock) (fourthForIf.getThenStatement())).getStatement(0)));
        org.junit.Assert.assertSame(ctDo, breakDo.getLabelledStatement());
        org.junit.Assert.assertEquals("labelbreak", breakItself.getLabel());
        org.junit.Assert.assertEquals("labelbreak", breakItself.getTargetLabel());
        org.junit.Assert.assertSame(breakItself, breakItself.getLabelledStatement());
    }
}

