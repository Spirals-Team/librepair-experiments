package spoon.test.factory;


public class AnnotationFactoryTest {
    @org.junit.Test
    public void testAnnotate() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.testclasses", "SampleClass");
        spoon.reflect.factory.AnnotationFactory af = type.getFactory().Annotation();
        af.annotate(type, spoon.test.factory.SampleAnnotation.class, "names", new java.lang.String[]{ "foo", "bar" });
        final spoon.reflect.declaration.CtAnnotation<spoon.test.factory.SampleAnnotation> annotation = type.getAnnotation(type.getFactory().Annotation().createReference(spoon.test.factory.SampleAnnotation.class));
        org.junit.Assert.assertTrue(((annotation.getValue("names")) instanceof spoon.reflect.code.CtNewArray));
        final spoon.reflect.code.CtNewArray names = annotation.getValue("names");
        org.junit.Assert.assertEquals(2, names.getElements().size());
        org.junit.Assert.assertEquals("foo", ((spoon.reflect.code.CtLiteral) (names.getElements().get(0))).getValue());
        org.junit.Assert.assertEquals("bar", ((spoon.reflect.code.CtLiteral) (names.getElements().get(1))).getValue());
    }
}

