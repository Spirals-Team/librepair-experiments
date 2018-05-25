package spoon.test.loop;


public class LoopTest {
    private static final java.lang.String nl = java.lang.System.lineSeparator();

    @org.junit.Test
    public void testAnnotationInForLoop() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> aFoo = spoon.testing.utils.ModelUtils.build(new java.io.File("./src/test/resources/spoon/test/loop/testclasses/")).Type().get("spoon.test.loop.testclasses.Foo");
        spoon.reflect.code.CtFor aFor = aFoo.getMethod("m").getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFor.class)).get(0);
        org.junit.Assert.assertEquals(1, ((spoon.reflect.code.CtLocalVariable) (aFor.getForInit().get(0))).getType().getAnnotations().size());
        org.junit.Assert.assertEquals(1, ((spoon.reflect.code.CtLocalVariable) (aFor.getForInit().get(1))).getType().getAnnotations().size());
        spoon.reflect.code.CtForEach aForEach = aFoo.getMethod("m").getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtForEach.class)).get(0);
        org.junit.Assert.assertEquals(1, aForEach.getVariable().getType().getAnnotations().size());
    }

    @org.junit.Test
    public void testForeachShouldHaveAlwaysABlockInItsBody() throws java.lang.Exception {
        final spoon.reflect.declaration.CtClass<spoon.test.loop.testclasses.Join> aType = spoon.testing.utils.ModelUtils.build(spoon.test.loop.testclasses.Join.class, spoon.test.loop.testclasses.Condition.class).Class().get(spoon.test.loop.testclasses.Join.class);
        final spoon.reflect.declaration.CtConstructor<spoon.test.loop.testclasses.Join> joinCtConstructor = aType.getConstructors().stream().findFirst().get();
        final spoon.reflect.code.CtLoop ctLoop = joinCtConstructor.getBody().getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtLoop.class)).get(0);
        org.junit.Assert.assertTrue(((ctLoop.getBody()) instanceof spoon.reflect.code.CtBlock));
        java.lang.String expected = (("for (spoon.test.loop.testclasses.Condition<? super T> condition : conditions)" + (spoon.test.loop.LoopTest.nl)) + "    this.conditions.add(spoon.test.loop.testclasses.Join.notNull(condition));") + (spoon.test.loop.LoopTest.nl);
        org.junit.Assert.assertEquals(expected, ctLoop.toString());
    }
}

