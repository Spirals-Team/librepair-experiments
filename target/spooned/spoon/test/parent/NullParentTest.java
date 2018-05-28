package spoon.test.parent;


import spoon.Launcher;
import spoon.compiler.SpoonResourceHelper;


public class NullParentTest {
    spoon.reflect.factory.Factory factory;

    @org.junit.Before
    public void setup() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        factory = spoon.createFactory();
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/parent/Foo.java")).build();
    }

    private <T extends spoon.reflect.declaration.CtElement> T get(java.lang.Class<T> elemType) {
        spoon.reflect.declaration.CtClass<java.lang.Object> fooClass = factory.Class().get(spoon.test.parent.Foo.class);
        spoon.reflect.declaration.CtMethod nullParent = fooClass.getMethodsByName("nullParent").get(0);
        return ((T) (nullParent.getBody().getElements(elemType::isInstance).get(0)));
    }

    @org.junit.Test
    public void testTargetedAccessNullTarget() {
        spoon.reflect.code.CtFieldAccess<?> access = get(spoon.reflect.code.CtFieldAccess.class);
        org.junit.Assert.assertEquals("foo.bar", access.toString());
        access.setTarget(null);
        org.junit.Assert.assertEquals("bar", access.toString());
    }

    @org.junit.Test
    public void testTargetedExpressionNullTarget() {
        spoon.reflect.code.CtInvocation<?> inv = get(spoon.reflect.code.CtInvocation.class);
        org.junit.Assert.assertEquals("foo.foo()", inv.toString());
        inv.setTarget(null);
        org.junit.Assert.assertEquals("foo()", inv.toString());
    }

    @org.junit.Test
    public void testAssertNullExpression() {
        spoon.reflect.code.CtAssert<?> asert = get(spoon.reflect.code.CtAssert.class);
        org.junit.Assert.assertEquals("assert true : \"message\"", asert.toString());
        asert.setExpression(null);
        org.junit.Assert.assertEquals("assert true", asert.toString());
    }

    static java.lang.String noSpaceToString(java.lang.Object obj) {
        return obj.toString().replaceAll("\\s+", "");
    }

    @org.junit.Test
    public void testForLoopNullChildren() {
        spoon.reflect.code.CtFor forLoop = get(spoon.reflect.code.CtFor.class);
        org.junit.Assert.assertEquals("for(inti=0;i<10;i++){}", spoon.test.parent.NullParentTest.noSpaceToString(forLoop));
        forLoop.setExpression(null);
        org.junit.Assert.assertEquals("for(inti=0;;i++){}", spoon.test.parent.NullParentTest.noSpaceToString(forLoop));
        forLoop.setBody(null);
        org.junit.Assert.assertEquals("for(inti=0;;i++);", spoon.test.parent.NullParentTest.noSpaceToString(forLoop));
    }

    @org.junit.Test
    public void testIfNullBranches() {
        spoon.reflect.code.CtIf ctIf = get(spoon.reflect.code.CtIf.class);
        org.junit.Assert.assertEquals("if(true){}else{}", spoon.test.parent.NullParentTest.noSpaceToString(ctIf));
        ctIf.setThenStatement(null);
        org.junit.Assert.assertEquals("if(true);else{}", spoon.test.parent.NullParentTest.noSpaceToString(ctIf));
        ctIf.setElseStatement(null);
        org.junit.Assert.assertEquals("if(true);", spoon.test.parent.NullParentTest.noSpaceToString(ctIf));
    }

    @org.junit.Test
    public void testLocalVariableNullDefaultExpression() {
        spoon.reflect.code.CtLocalVariable<?> local = get(spoon.reflect.code.CtLocalVariable.class);
        org.junit.Assert.assertEquals("int i = 0", local.toString());
        local.setDefaultExpression(null);
        org.junit.Assert.assertEquals("int i", local.toString());
    }

    @org.junit.Test
    public void testFieldNullDefaultExpression() {
        spoon.reflect.declaration.CtField<?> field = get(spoon.reflect.declaration.CtField.class);
        org.junit.Assert.assertEquals("int bar = 0;", field.toString());
        field.setDefaultExpression(null);
        org.junit.Assert.assertEquals("int bar;", field.toString());
    }

    @org.junit.Test
    public void testReturnNullExpression() {
        spoon.reflect.code.CtReturn<?> ret = get(spoon.reflect.code.CtReturn.class);
        org.junit.Assert.assertEquals("return 0", ret.toString());
        ret.setReturnedExpression(null);
        org.junit.Assert.assertEquals("return", ret.toString());
    }
}

