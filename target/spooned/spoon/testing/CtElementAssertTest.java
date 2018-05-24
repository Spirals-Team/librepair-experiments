package spoon.testing;


public class CtElementAssertTest {
    public int i;

    @org.junit.Test
    public void testEqualityBetweenTwoCtElement() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.testing.CtElementAssertTest> type = spoon.testing.utils.ModelUtils.buildNoClasspath(spoon.testing.CtElementAssertTest.class).Type().get(spoon.testing.CtElementAssertTest.class);
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        final spoon.reflect.declaration.CtField<java.lang.Integer> expected = factory.Core().createField();
        expected.setSimpleName("i");
        expected.setType(factory.Type().INTEGER_PRIMITIVE);
        expected.addModifier(spoon.reflect.declaration.ModifierKind.PUBLIC);
        spoon.reflect.declaration.CtField<?> f = type.getField("i");
        spoon.testing.Assert.assertThat(f).isEqualTo(expected);
    }

    @org.junit.Test
    public void testEqualityBetweenACtElementAndAString() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.testing.CtElementAssertTest> type = spoon.testing.utils.ModelUtils.buildNoClasspath(spoon.testing.CtElementAssertTest.class).Type().get(spoon.testing.CtElementAssertTest.class);
        spoon.testing.Assert.assertThat(type.getField("i")).isEqualTo("public int i;");
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void testEqualityBetweenTwoCtElementWithTypeDifferent() throws java.lang.Exception {
        spoon.testing.Assert.assertThat(spoon.testing.utils.ModelUtils.createFactory().Core().createAnnotation()).isEqualTo(spoon.testing.utils.ModelUtils.createFactory().Core().createBlock());
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void testEqualityBetweenTwoCtElementWithTheSameSignatureButNotTheSameContent() throws java.lang.Exception {
        spoon.testing.Assert.assertThat(spoon.testing.utils.ModelUtils.buildNoClasspath(spoon.testing.CtElementAssertTest.class).Type().get(spoon.testing.CtElementAssertTest.class)).isEqualTo(spoon.testing.utils.ModelUtils.createFactory().Class().create(spoon.testing.CtElementAssertTest.class.getName()));
    }

    @org.junit.Test(expected = java.lang.AssertionError.class)
    public void testEqualityBetweenTwoDifferentCtElement() throws java.lang.Exception {
        class String {}
        final spoon.reflect.factory.Factory build = spoon.testing.utils.ModelUtils.buildNoClasspath(spoon.testing.CtElementAssertTest.class);
        final spoon.reflect.code.CtFieldAccess<java.lang.Class<String>> actual = build.Code().createClassAccess(build.Type().<String>get(String.class).getReference());
        final spoon.reflect.code.CtFieldAccess<java.lang.Class<java.lang.String>> expected = spoon.testing.utils.ModelUtils.createFactory().Code().createClassAccess(spoon.testing.utils.ModelUtils.createFactory().Type().STRING);
        spoon.testing.Assert.assertThat(actual).isEqualTo(expected);
    }
}

