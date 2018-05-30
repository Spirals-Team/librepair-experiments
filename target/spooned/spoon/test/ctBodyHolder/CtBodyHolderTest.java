package spoon.test.ctBodyHolder;


public class CtBodyHolderTest {
    @org.junit.Test
    public void testConstructor() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.ctBodyHolder.testclasses.ClassWithBodies.class, spoon.test.ctBodyHolder.testclasses.CWBStatementTemplate.class);
        spoon.reflect.declaration.CtClass<?> cwbClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.ctBodyHolder.testclasses.ClassWithBodies.class)));
        org.junit.Assert.assertEquals(1, cwbClass.getConstructors().size());
        spoon.reflect.declaration.CtConstructor<?> constructor = cwbClass.getConstructor();
        checkCtBody(constructor, "constructor_body", 1);
    }

    @org.junit.Test
    public void testMethod() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.ctBodyHolder.testclasses.ClassWithBodies.class, spoon.test.ctBodyHolder.testclasses.CWBStatementTemplate.class);
        spoon.reflect.declaration.CtClass<?> cwbClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.ctBodyHolder.testclasses.ClassWithBodies.class)));
        org.junit.Assert.assertEquals(2, cwbClass.getMethods().size());
        spoon.reflect.declaration.CtMethod<?> method = cwbClass.getMethod("method");
        checkCtBody(method, "method_body", 0);
    }

    @org.junit.Test
    public void testTryCatch() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.ctBodyHolder.testclasses.ClassWithBodies.class, spoon.test.ctBodyHolder.testclasses.CWBStatementTemplate.class);
        spoon.reflect.declaration.CtClass<?> cwbClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.ctBodyHolder.testclasses.ClassWithBodies.class)));
        org.junit.Assert.assertEquals(2, cwbClass.getMethods().size());
        spoon.reflect.declaration.CtMethod<?> method = cwbClass.getMethod("method2");
        spoon.reflect.code.CtBlock<?> methodBody = method.getBody();
        org.junit.Assert.assertTrue(((methodBody.getStatement(0)) instanceof spoon.reflect.code.CtTry));
        spoon.reflect.code.CtTry tryStmnt = ((spoon.reflect.code.CtTry) (methodBody.getStatement(0)));
        checkCtBody(tryStmnt, "try_body", 0);
        org.junit.Assert.assertEquals(1, tryStmnt.getCatchers().size());
        org.junit.Assert.assertTrue(((tryStmnt.getCatchers().get(0)) instanceof spoon.reflect.code.CtCatch));
        checkCtBody(tryStmnt.getCatchers().get(0), "catch_body", 0);
    }

    @org.junit.Test
    public void testForWithStatement() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.ctBodyHolder.testclasses.ClassWithBodies.class, spoon.test.ctBodyHolder.testclasses.CWBStatementTemplate.class);
        spoon.reflect.declaration.CtClass<?> cwbClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.ctBodyHolder.testclasses.ClassWithBodies.class)));
        org.junit.Assert.assertEquals(2, cwbClass.getMethods().size());
        spoon.reflect.declaration.CtMethod<?> method = cwbClass.getMethod("method2");
        spoon.reflect.code.CtBlock<?> methodBody = method.getBody();
        org.junit.Assert.assertTrue(((methodBody.getStatement(1)) instanceof spoon.reflect.code.CtFor));
        spoon.reflect.code.CtFor forStmnt = ((spoon.reflect.code.CtFor) (methodBody.getStatement(1)));
        checkCtBody(forStmnt, "for_statemnt", 0);
    }

    @org.junit.Test
    public void testForWithBlock() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.ctBodyHolder.testclasses.ClassWithBodies.class, spoon.test.ctBodyHolder.testclasses.CWBStatementTemplate.class);
        spoon.reflect.declaration.CtClass<?> cwbClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.ctBodyHolder.testclasses.ClassWithBodies.class)));
        org.junit.Assert.assertEquals(2, cwbClass.getMethods().size());
        spoon.reflect.declaration.CtMethod<?> method = cwbClass.getMethod("method2");
        spoon.reflect.code.CtBlock<?> methodBody = method.getBody();
        org.junit.Assert.assertTrue(((methodBody.getStatement(2)) instanceof spoon.reflect.code.CtFor));
        spoon.reflect.code.CtFor forStmnt = ((spoon.reflect.code.CtFor) (methodBody.getStatement(2)));
        checkCtBody(forStmnt, "for_block", 0);
    }

    @org.junit.Test
    public void testWhileWithBlock() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.ctBodyHolder.testclasses.ClassWithBodies.class, spoon.test.ctBodyHolder.testclasses.CWBStatementTemplate.class);
        spoon.reflect.declaration.CtClass<?> cwbClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.ctBodyHolder.testclasses.ClassWithBodies.class)));
        org.junit.Assert.assertEquals(2, cwbClass.getMethods().size());
        spoon.reflect.declaration.CtMethod<?> method = cwbClass.getMethod("method2");
        spoon.reflect.code.CtBlock<?> methodBody = method.getBody();
        org.junit.Assert.assertTrue(((methodBody.getStatement(3)) instanceof spoon.reflect.code.CtWhile));
        spoon.reflect.code.CtWhile whileStmnt = ((spoon.reflect.code.CtWhile) (methodBody.getStatement(3)));
        checkCtBody(whileStmnt, "while_block", 0);
    }

    private void checkCtBody(spoon.reflect.code.CtBodyHolder p_bodyHolder, java.lang.String p_constant, int off) {
        spoon.reflect.code.CtStatement body = p_bodyHolder.getBody();
        org.junit.Assert.assertTrue((body instanceof spoon.reflect.code.CtBlock<?>));
        spoon.reflect.code.CtBlock<?> block = ((spoon.reflect.code.CtBlock) (body));
        org.junit.Assert.assertEquals((1 + off), block.getStatements().size());
        org.junit.Assert.assertTrue(((block.getStatement(off)) instanceof spoon.reflect.code.CtAssignment));
        spoon.reflect.code.CtAssignment assignment = block.getStatement(off);
        org.junit.Assert.assertEquals(p_constant, ((spoon.reflect.code.CtLiteral<java.lang.String>) (assignment.getAssignment().partiallyEvaluate())).getValue());
        spoon.reflect.factory.Factory f = body.getFactory();
        spoon.reflect.code.CtStatement newStat = new spoon.test.ctBodyHolder.testclasses.CWBStatementTemplate("xx").apply(body.getParent(spoon.reflect.declaration.CtType.class));
        try {
            newStat.getParent();
            org.junit.Assert.fail();
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
        }
        p_bodyHolder.setBody(newStat);
        spoon.reflect.code.CtBlock newBlock = ((spoon.reflect.code.CtBlock) (p_bodyHolder.getBody()));
        org.junit.Assert.assertSame(p_bodyHolder, newBlock.getParent());
        org.junit.Assert.assertSame(newBlock, newStat.getParent());
        spoon.reflect.code.CtStatement newStat2 = newStat.clone();
        try {
            newStat2.getParent();
            org.junit.Assert.fail();
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
        }
        spoon.reflect.code.CtBlock newBlock2 = f.Code().createCtBlock(newStat2);
        org.junit.Assert.assertSame(newBlock2, newStat2.getParent());
        try {
            newBlock2.getParent();
            org.junit.Assert.fail();
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
        }
        p_bodyHolder.setBody(newBlock2);
        org.junit.Assert.assertSame(newBlock2, p_bodyHolder.getBody());
        org.junit.Assert.assertSame(p_bodyHolder, newBlock2.getParent());
        org.junit.Assert.assertSame(newBlock2, newStat2.getParent());
    }
}

