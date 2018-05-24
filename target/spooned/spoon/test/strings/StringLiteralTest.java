package spoon.test.strings;


public class StringLiteralTest {
    @java.lang.SuppressWarnings("unused")
    @org.junit.Test
    public void testSnippetFullClass() {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("class StringValueUTF {\n" + (((((((((((("\tString f0 = \"toto\";\n" + "\tString f1 = \"\\n\";\n") + "\tchar c1 = \'\\n\';\n") + "\tString f2 = \"\\u20ac\";\n") + "\tchar c2 = \'\\u20ac\';\n") + "\tString f3 = \"\u20ac\";\n") + "\tchar c3 = \'\u20ac\';\n") + "\tString f4 = \"\\t\";\n") + "\tchar c4 = \'\\t\';\n") + "\tString f5 = \"\t\";\n") + "\tchar c5 = \'\t\';\n") + "\tString f6 = \"\u20ac\\u20ac\";\n") + "}"))).compile();
        spoon.reflect.declaration.CtField<?> f0 = ((spoon.reflect.declaration.CtField<?>) (clazz.getFields().toArray()[0]));
        spoon.reflect.declaration.CtField<?> f1 = ((spoon.reflect.declaration.CtField<?>) (clazz.getFields().toArray()[1]));
        spoon.reflect.declaration.CtField<?> c1 = ((spoon.reflect.declaration.CtField<?>) (clazz.getFields().toArray()[2]));
        spoon.reflect.declaration.CtField<?> f2 = ((spoon.reflect.declaration.CtField<?>) (clazz.getFields().toArray()[3]));
        spoon.reflect.declaration.CtField<?> c2 = ((spoon.reflect.declaration.CtField<?>) (clazz.getFields().toArray()[4]));
        spoon.reflect.declaration.CtField<?> f3 = ((spoon.reflect.declaration.CtField<?>) (clazz.getFields().toArray()[5]));
        spoon.reflect.declaration.CtField<?> c3 = ((spoon.reflect.declaration.CtField<?>) (clazz.getFields().toArray()[6]));
        spoon.reflect.declaration.CtField<?> f4 = ((spoon.reflect.declaration.CtField<?>) (clazz.getFields().toArray()[7]));
        spoon.reflect.declaration.CtField<?> c4 = ((spoon.reflect.declaration.CtField<?>) (clazz.getFields().toArray()[8]));
        spoon.reflect.declaration.CtField<?> f5 = ((spoon.reflect.declaration.CtField<?>) (clazz.getFields().toArray()[9]));
        spoon.reflect.declaration.CtField<?> c5 = ((spoon.reflect.declaration.CtField<?>) (clazz.getFields().toArray()[10]));
        spoon.reflect.declaration.CtField<?> f6 = ((spoon.reflect.declaration.CtField<?>) (clazz.getFields().toArray()[11]));
        org.junit.Assert.assertEquals("java.lang.String f0 = \"toto\";", f0.toString());
        org.junit.Assert.assertEquals("java.lang.String f1 = \"\\n\";", f1.toString());
        org.junit.Assert.assertEquals("char c1 = \'\\n\';", c1.toString());
        org.junit.Assert.assertEquals("java.lang.String f2 = \"\\u20ac\";", f2.toString());
        org.junit.Assert.assertEquals("char c2 = \'\\u20ac\';", c2.toString());
        org.junit.Assert.assertEquals("java.lang.String f3 = \"\u20ac\";", f3.toString());
        org.junit.Assert.assertEquals("char c3 = '€';", c3.toString());
        org.junit.Assert.assertEquals("java.lang.String f4 = \"\\t\";", f4.toString());
        org.junit.Assert.assertEquals("char c4 = \'\\t\';", c4.toString());
        org.junit.Assert.assertEquals("java.lang.String f5 = \"\t\";", f5.toString());
        org.junit.Assert.assertEquals("char c5 = '	';", c5.toString());
    }
}

