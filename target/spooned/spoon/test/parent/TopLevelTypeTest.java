package spoon.test.parent;


import spoon.Launcher;
import spoon.compiler.SpoonResourceHelper;


public class TopLevelTypeTest {
    spoon.reflect.factory.Factory factory;

    @org.junit.Before
    public void setup() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{ "--output-type", "nooutput" });
        factory = spoon.createFactory();
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/parent/Foo.java")).build();
    }

    @org.junit.Test
    public void testTopLevelType() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> foo = factory.Class().get(spoon.test.parent.Foo.class);
        org.junit.Assert.assertEquals(foo, foo.getTopLevelType());
        spoon.reflect.declaration.CtMethod<?> internalClassMethod = foo.getMethod("internalClass");
        org.junit.Assert.assertEquals(foo, internalClassMethod.getDeclaringType());
        org.junit.Assert.assertEquals(foo, internalClassMethod.getTopLevelType());
        spoon.reflect.declaration.CtClass<?> internalClass = ((spoon.reflect.declaration.CtClass<?>) (internalClassMethod.getBody().getStatement(0)));
        org.junit.Assert.assertEquals(foo, internalClassMethod.getDeclaringType());
        org.junit.Assert.assertEquals(foo, internalClassMethod.getTopLevelType());
        spoon.reflect.declaration.CtMethod<?> mm = internalClass.getMethod("m");
        org.junit.Assert.assertEquals(internalClass, mm.getDeclaringType());
        org.junit.Assert.assertEquals(foo, mm.getTopLevelType());
    }
}

