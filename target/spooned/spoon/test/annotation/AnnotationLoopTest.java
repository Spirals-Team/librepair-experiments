package spoon.test.annotation;


public class AnnotationLoopTest {
    @org.junit.Test
    public void testAnnotationDeclaredInForInit() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.annotation.testclasses.Pozole> aPozole = spoon.testing.utils.ModelUtils.buildClass(spoon.test.annotation.testclasses.Pozole.class);
        final spoon.reflect.code.CtFor aLoop = aPozole.getMethod("cook").getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFor.class)).get(0);
        org.junit.Assert.assertEquals(3, aLoop.getForInit().size());
        org.junit.Assert.assertEquals(java.lang.SuppressWarnings.class, aLoop.getForInit().get(0).getAnnotations().get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals(java.lang.SuppressWarnings.class, aLoop.getForInit().get(1).getAnnotations().get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals(java.lang.SuppressWarnings.class, aLoop.getForInit().get(2).getAnnotations().get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals("u", ((spoon.reflect.code.CtLocalVariable) (aLoop.getForInit().get(0))).getSimpleName());
        org.junit.Assert.assertEquals("p", ((spoon.reflect.code.CtLocalVariable) (aLoop.getForInit().get(1))).getSimpleName());
        org.junit.Assert.assertEquals("e", ((spoon.reflect.code.CtLocalVariable) (aLoop.getForInit().get(2))).getSimpleName());
        org.junit.Assert.assertEquals(aPozole.getFactory().Type().STRING, ((spoon.reflect.code.CtLocalVariable) (aLoop.getForInit().get(0))).getType());
        org.junit.Assert.assertEquals(aPozole.getFactory().Type().STRING, ((spoon.reflect.code.CtLocalVariable) (aLoop.getForInit().get(1))).getType());
        org.junit.Assert.assertEquals(aPozole.getFactory().Type().STRING, ((spoon.reflect.code.CtLocalVariable) (aLoop.getForInit().get(2))).getType());
        final java.lang.String nl = java.lang.System.lineSeparator();
        final java.lang.String expected = ((("for (@java.lang.SuppressWarnings(\"rawtypes\")" + nl) + "java.lang.String u = \"\", p = \"\", e = \"\"; u != e; u = p , p = \"\") {") + nl) + "}";
        org.junit.Assert.assertEquals(expected, aLoop.toString());
    }
}

