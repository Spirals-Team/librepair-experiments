package spoon.test.change;


public class ChangeCollectorTest {
    @org.junit.Test
    public void testChangeCollector() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.change.testclasses.SubjectOfChange.class);
        spoon.reflect.factory.Factory f = ctClass.getFactory();
        org.junit.Assert.assertNull(spoon.experimental.modelobs.ChangeCollector.getChangeCollector(f.getEnvironment()));
        spoon.experimental.modelobs.ChangeCollector changeCollector = new spoon.experimental.modelobs.ChangeCollector().attachTo(f.getEnvironment());
        org.junit.Assert.assertSame(changeCollector, spoon.experimental.modelobs.ChangeCollector.getChangeCollector(f.getEnvironment()));
        org.junit.Assert.assertEquals(0, changeCollector.getChanges(f.getModel().getRootPackage()).size());
        f.getModel().getRootPackage().filterChildren(null).forEach((spoon.reflect.declaration.CtElement e) -> {
            org.junit.Assert.assertEquals(0, changeCollector.getDirectChanges(e).size());
        });
        ctClass.setSimpleName("aaa");
        org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.path.CtRole.SUB_PACKAGE)), changeCollector.getChanges(f.getModel().getRootPackage()));
        org.junit.Assert.assertEquals(new java.util.HashSet<>(), changeCollector.getDirectChanges(f.getModel().getRootPackage()));
        org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.path.CtRole.CONTAINED_TYPE)), changeCollector.getChanges(ctClass.getPackage()));
        org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList()), changeCollector.getDirectChanges(ctClass.getPackage()));
        org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.path.CtRole.NAME)), changeCollector.getChanges(ctClass));
        org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.path.CtRole.NAME)), changeCollector.getDirectChanges(ctClass));
        spoon.reflect.declaration.CtField<?> field = ctClass.getField("someField");
        field.getDefaultExpression().delete();
        org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.path.CtRole.NAME, spoon.reflect.path.CtRole.TYPE_MEMBER)), changeCollector.getChanges(ctClass));
        org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.path.CtRole.NAME)), changeCollector.getDirectChanges(ctClass));
        org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION)), changeCollector.getChanges(field));
        org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION)), changeCollector.getDirectChanges(field));
        ctClass.removeTypeMember(field);
        org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.path.CtRole.NAME, spoon.reflect.path.CtRole.TYPE_MEMBER)), changeCollector.getChanges(ctClass));
        org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.path.CtRole.NAME, spoon.reflect.path.CtRole.TYPE_MEMBER)), changeCollector.getDirectChanges(ctClass));
        org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION)), changeCollector.getChanges(field));
        org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION)), changeCollector.getDirectChanges(field));
    }
}

