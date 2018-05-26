package spoon.test.delete;


public class DeleteTest {
    @org.junit.Test
    public void testDeleteAStatementInAnonymousExecutable() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtClass<spoon.test.delete.testclasses.Adobada> adobada = factory.Class().get(spoon.test.delete.testclasses.Adobada.class);
        final java.util.List<spoon.reflect.declaration.CtAnonymousExecutable> anonymousExecutables = adobada.getAnonymousExecutables();
        final spoon.reflect.declaration.CtAnonymousExecutable instanceExec = anonymousExecutables.get(0);
        org.junit.Assert.assertEquals(2, instanceExec.getBody().getStatements().size());
        final spoon.reflect.code.CtStatement statement = instanceExec.getBody().getStatement(1);
        statement.delete();
        org.junit.Assert.assertEquals(1, instanceExec.getBody().getStatements().size());
        org.junit.Assert.assertFalse(instanceExec.getBody().getStatements().contains(statement));
    }

    @org.junit.Test
    public void testDeleteAStatementInStaticAnonymousExecutable() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtClass<spoon.test.delete.testclasses.Adobada> adobada = factory.Class().get(spoon.test.delete.testclasses.Adobada.class);
        final java.util.List<spoon.reflect.declaration.CtAnonymousExecutable> anonymousExecutables = adobada.getAnonymousExecutables();
        final spoon.reflect.declaration.CtAnonymousExecutable staticExec = anonymousExecutables.get(1);
        org.junit.Assert.assertEquals(2, staticExec.getBody().getStatements().size());
        final spoon.reflect.code.CtStatement statement = staticExec.getBody().getStatement(1);
        statement.delete();
        org.junit.Assert.assertEquals(1, staticExec.getBody().getStatements().size());
        org.junit.Assert.assertFalse(staticExec.getBody().getStatements().contains(statement));
    }

    @org.junit.Test
    public void testDeleteAStatementInConstructor() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtClass<spoon.test.delete.testclasses.Adobada> adobada = factory.Class().get(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtConstructor<spoon.test.delete.testclasses.Adobada> constructor = adobada.getConstructor();
        org.junit.Assert.assertEquals(3, constructor.getBody().getStatements().size());
        final spoon.reflect.code.CtStatement statement = constructor.getBody().getStatement(1);
        statement.delete();
        org.junit.Assert.assertEquals(2, constructor.getBody().getStatements().size());
        org.junit.Assert.assertFalse(constructor.getBody().getStatements().contains(statement));
    }

    @org.junit.Test
    public void testDeleteAStatementInMethod() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtClass<spoon.test.delete.testclasses.Adobada> adobada = factory.Class().get(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtMethod method = adobada.getMethod("m");
        org.junit.Assert.assertEquals(2, method.getBody().getStatements().size());
        final spoon.reflect.code.CtStatement statement = method.getBody().getStatement(1);
        statement.delete();
        org.junit.Assert.assertEquals(1, method.getBody().getStatements().size());
        org.junit.Assert.assertFalse(method.getBody().getStatements().contains(statement));
    }

    @org.junit.Test
    public void testDeleteReturn() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtClass<spoon.test.delete.testclasses.Adobada> adobada = factory.Class().get(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtMethod method = adobada.getMethod("m2");
        org.junit.Assert.assertEquals(1, method.getBody().getStatements().size());
        final spoon.reflect.code.CtStatement statement = method.getBody().getStatement(0);
        statement.delete();
        org.junit.Assert.assertEquals(0, method.getBody().getStatements().size());
        org.junit.Assert.assertFalse(method.getBody().getStatements().contains(statement));
    }

    @org.junit.Test
    public void testDeleteStatementInCase() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtClass<spoon.test.delete.testclasses.Adobada> adobada = factory.Class().get(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtMethod method = adobada.getMethod("m3");
        final spoon.reflect.code.CtCase aCase = method.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtCase.class)).get(0);
        org.junit.Assert.assertEquals(2, aCase.getStatements().size());
        final spoon.reflect.code.CtStatement statement = aCase.getStatements().get(1);
        statement.delete();
        org.junit.Assert.assertEquals(1, aCase.getStatements().size());
        org.junit.Assert.assertFalse(aCase.getStatements().contains(statement));
    }

    @org.junit.Test
    public void testDeleteACaseOfASwitch() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtClass<spoon.test.delete.testclasses.Adobada> adobada = factory.Class().get(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtMethod method = adobada.getMethod("m3");
        final spoon.reflect.code.CtSwitch aSwitch = method.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtSwitch.class)).get(0);
        final spoon.reflect.code.CtCase aCase = ((spoon.reflect.code.CtCase) (aSwitch.getCases().get(1)));
        org.junit.Assert.assertEquals(2, aSwitch.getCases().size());
        aCase.delete();
        org.junit.Assert.assertEquals(1, aSwitch.getCases().size());
        org.junit.Assert.assertFalse(aSwitch.getCases().contains(aCase));
    }

    @org.junit.Test
    public void testDeleteMethod() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtClass<spoon.test.delete.testclasses.Adobada> adobada = factory.Class().get(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtMethod method = adobada.getMethod("m4", factory.Type().INTEGER_PRIMITIVE, factory.Type().FLOAT_PRIMITIVE, factory.Type().STRING);
        int n = adobada.getMethods().size();
        method.delete();
        org.junit.Assert.assertEquals((n - 1), adobada.getMethods().size());
        org.junit.Assert.assertFalse(adobada.getMethods().contains(method));
    }

    @org.junit.Test
    public void testDeleteParameterOfMethod() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtClass<spoon.test.delete.testclasses.Adobada> adobada = factory.Class().get(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtMethod method = adobada.getMethod("m4", factory.Type().INTEGER_PRIMITIVE, factory.Type().FLOAT_PRIMITIVE, factory.Type().STRING);
        final spoon.reflect.declaration.CtParameter param = ((spoon.reflect.declaration.CtParameter) (method.getParameters().get(1)));
        org.junit.Assert.assertEquals(3, method.getParameters().size());
        param.delete();
        org.junit.Assert.assertEquals(2, method.getParameters().size());
        org.junit.Assert.assertFalse(method.getParameters().contains(param));
    }

    @org.junit.Test
    public void testDeleteBodyOfAMethod() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtClass<spoon.test.delete.testclasses.Adobada> adobada = factory.Class().get(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtMethod method = adobada.getMethod("m");
        org.junit.Assert.assertNotNull(method.getBody());
        method.getBody().delete();
        org.junit.Assert.assertNull(method.getBody());
    }

    @org.junit.Test
    public void testDeleteAnnotationOnAClass() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtClass<spoon.test.delete.testclasses.Adobada> adobada = factory.Class().get(spoon.test.delete.testclasses.Adobada.class);
        org.junit.Assert.assertEquals(1, adobada.getAnnotations().size());
        final spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> annotation = adobada.getAnnotations().get(0);
        annotation.delete();
        org.junit.Assert.assertEquals(0, adobada.getAnnotations().size());
        org.junit.Assert.assertFalse(adobada.getAnnotations().contains(annotation));
    }

    @org.junit.Test
    public void testDeleteAClassTopLevel() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtClass<spoon.test.delete.testclasses.Adobada> adobada = factory.Class().get(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtPackage aPackage = adobada.getParent(spoon.reflect.declaration.CtPackage.class);
        org.junit.Assert.assertEquals(1, aPackage.getTypes().size());
        adobada.delete();
        org.junit.Assert.assertEquals(0, aPackage.getTypes().size());
        org.junit.Assert.assertFalse(aPackage.getTypes().contains(adobada));
    }

    @org.junit.Test
    public void testDeleteConditionInACondition() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtClass<spoon.test.delete.testclasses.Adobada> adobada = factory.Class().get(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtMethod method = adobada.getMethod("m4", factory.Type().INTEGER_PRIMITIVE, factory.Type().FLOAT_PRIMITIVE, factory.Type().STRING);
        final spoon.reflect.code.CtIf anIf = method.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtIf.class)).get(0);
        org.junit.Assert.assertNotNull(anIf.getCondition());
        anIf.getCondition().delete();
        org.junit.Assert.assertNull(anIf.getCondition());
    }

    @org.junit.Test
    public void testDeleteChainOfAssignment() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtClass<spoon.test.delete.testclasses.Adobada> adobada = factory.Class().get(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtMethod method = adobada.getMethod("m4", factory.Type().INTEGER_PRIMITIVE, factory.Type().FLOAT_PRIMITIVE, factory.Type().STRING);
        final spoon.reflect.code.CtAssignment chainOfAssignment = method.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtAssignment.class)).get(0);
        org.junit.Assert.assertNotNull(chainOfAssignment.getAssignment());
        chainOfAssignment.getAssignment().delete();
        org.junit.Assert.assertNull(chainOfAssignment.getAssignment());
    }
}

