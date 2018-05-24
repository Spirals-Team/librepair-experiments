package spoon.test.modifiers;


import spoon.Launcher;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.test.modifiers.testclasses.AbstractClass;
import spoon.test.modifiers.testclasses.MethodVarArgs;
import spoon.test.modifiers.testclasses.StaticMethod;


public class TestModifiers {
    @org.junit.Test
    public void testMethodWithVarargsDoesNotBecomeTransient() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/modifiers/testclasses/MethodVarArgs.java");
        spoon.buildModel();
        CtType<?> myClass = spoon.getFactory().Type().get(MethodVarArgs.class);
        CtMethod methodVarargs = myClass.getMethodsByName("getInitValues").get(0);
        java.util.Set<ModifierKind> expectedModifiers = java.util.Collections.singleton(ModifierKind.PROTECTED);
        org.junit.Assert.assertEquals(expectedModifiers, methodVarargs.getModifiers());
        spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/modifiers/testclasses/MethodVarArgs.java");
        spoon.getEnvironment().setShouldCompile(true);
        spoon.run();
    }

    @org.junit.Test
    public void testCtModifiableAddRemoveReturnCtModifiable() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/modifiers/testclasses/MethodVarArgs.java");
        spoon.buildModel();
        CtType<?> myClass = spoon.getFactory().Type().get(MethodVarArgs.class);
        CtMethod methodVarargs = myClass.getMethodsByName("getInitValues").get(0);
        java.lang.Object o = methodVarargs.addModifier(ModifierKind.FINAL);
        org.junit.Assert.assertEquals(methodVarargs, o);
        o = methodVarargs.removeModifier(ModifierKind.FINAL);
        org.junit.Assert.assertEquals(methodVarargs, o);
    }

    @org.junit.Test
    public void testSetVisibility() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/modifiers/testclasses/StaticMethod.java");
        spoon.buildModel();
        CtType<?> myClass = spoon.getFactory().Type().get(StaticMethod.class);
        CtMethod methodPublicStatic = myClass.getMethodsByName("maMethod").get(0);
        org.junit.Assert.assertEquals(ModifierKind.PUBLIC, methodPublicStatic.getVisibility());
        methodPublicStatic.setVisibility(ModifierKind.PROTECTED);
        org.junit.Assert.assertEquals(ModifierKind.PROTECTED, methodPublicStatic.getVisibility());
        try {
            methodPublicStatic.setVisibility(ModifierKind.FINAL);
            org.junit.Assert.fail();
        } catch (spoon.SpoonException e) {
        }
        org.junit.Assert.assertEquals(ModifierKind.PROTECTED, methodPublicStatic.getVisibility());
    }

    @org.junit.Test
    public void testGetModifiersHelpers() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/modifiers/testclasses/AbstractClass.java");
        spoon.addInputResource("./src/test/java/spoon/test/modifiers/testclasses/ConcreteClass.java");
        spoon.getEnvironment().setShouldCompile(true);
        spoon.run();
        CtType<?> abstractClass = spoon.getFactory().Type().get(AbstractClass.class);
        checkCtModifiableHelpersAssertion(abstractClass, true, false, false, true, false, false);
        org.junit.Assert.assertEquals(4, abstractClass.getFields().size());
        for (spoon.reflect.declaration.CtField field : abstractClass.getFields()) {
            switch (field.getSimpleName()) {
                case "privateField" :
                    checkCtModifiableHelpersAssertion(field, false, false, true, false, false, false);
                    break;
                case "protectedField" :
                    checkCtModifiableHelpersAssertion(field, false, true, false, false, false, false);
                    break;
                case "privateStaticField" :
                    checkCtModifiableHelpersAssertion(field, false, false, true, false, false, true);
                    break;
                case "publicFinalField" :
                    checkCtModifiableHelpersAssertion(field, true, false, false, false, true, false);
                    break;
                default :
                    org.junit.Assert.fail((("The field " + (field.getSimpleName())) + " should be take into account."));
            }
        }
        org.junit.Assert.assertEquals(4, abstractClass.getMethods().size());
        for (CtMethod method : abstractClass.getMethods()) {
            switch (method.getSimpleName()) {
                case "method" :
                    checkCtModifiableHelpersAssertion(method, true, false, false, false, true, true);
                    break;
                case "onlyStatic" :
                    checkCtModifiableHelpersAssertion(method, true, false, false, false, false, true);
                    break;
                case "otherMethod" :
                    checkCtModifiableHelpersAssertion(method, false, true, false, true, false, false);
                    break;
                case "anotherOne" :
                    checkCtModifiableHelpersAssertion(method, false, false, false, true, false, false);
                    break;
                default :
                    org.junit.Assert.fail((("The method " + (method.getSimpleName())) + " should be taken into account."));
            }
        }
        CtType<?> concreteClass = spoon.getFactory().Type().get("spoon.test.modifiers.testclasses.ConcreteClass");
        checkCtModifiableHelpersAssertion(concreteClass, false, false, false, false, true, false);
        org.junit.Assert.assertEquals(2, concreteClass.getFields().size());
        for (spoon.reflect.declaration.CtField field : concreteClass.getFields()) {
            switch (field.getSimpleName()) {
                case "className" :
                    checkCtModifiableHelpersAssertion(field, true, false, false, false, true, true);
                    break;
                case "test" :
                    checkCtModifiableHelpersAssertion(field, false, false, true, false, false, true);
                    break;
                default :
                    org.junit.Assert.fail((("The field " + (field.getSimpleName())) + " should be take into account."));
            }
        }
        org.junit.Assert.assertEquals(2, concreteClass.getMethods().size());
        for (CtMethod method : concreteClass.getMethods()) {
            switch (method.getSimpleName()) {
                case "otherMethod" :
                    checkCtModifiableHelpersAssertion(method, false, true, false, false, false, false);
                    break;
                case "anotherOne" :
                    checkCtModifiableHelpersAssertion(method, false, false, false, false, true, false);
                    break;
                default :
                    org.junit.Assert.fail((("The method " + (method.getSimpleName())) + " should be taken into account."));
            }
        }
    }

    private void checkCtModifiableHelpersAssertion(spoon.reflect.declaration.CtModifiable element, boolean isPublic, boolean isProtected, boolean isPrivate, boolean isAbstract, boolean isFinal, boolean isStatic) {
        org.junit.Assert.assertEquals((("isPublic for " + element) + " is wrong"), isPublic, element.isPublic());
        org.junit.Assert.assertEquals((("isProtected for " + element) + " is wrong"), isProtected, element.isProtected());
        org.junit.Assert.assertEquals((("isPrivate for " + element) + " is wrong"), isPrivate, element.isPrivate());
        org.junit.Assert.assertEquals((("isAbstract for " + element) + " is wrong"), isAbstract, element.isAbstract());
        org.junit.Assert.assertEquals((("isFinal for " + element) + " is wrong"), isFinal, element.isFinal());
        org.junit.Assert.assertEquals((("isStatic for " + element) + " is wrong"), isStatic, element.isStatic());
    }

    @org.junit.Test
    public void testClearModifiersByEmptySet() throws java.lang.Exception {
        CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(StaticMethod.class);
        org.junit.Assert.assertTrue(ctClass.hasModifier(ModifierKind.PUBLIC));
        org.junit.Assert.assertEquals(1, ctClass.getModifiers().size());
        ctClass.setModifiers(java.util.Collections.emptySet());
        org.junit.Assert.assertFalse(ctClass.hasModifier(ModifierKind.PUBLIC));
        org.junit.Assert.assertEquals(0, ctClass.getModifiers().size());
    }

    @org.junit.Test
    public void testClearModifiersByNull() throws java.lang.Exception {
        CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(StaticMethod.class);
        org.junit.Assert.assertTrue(ctClass.hasModifier(ModifierKind.PUBLIC));
        org.junit.Assert.assertEquals(1, ctClass.getModifiers().size());
        ctClass.setModifiers(null);
        org.junit.Assert.assertFalse(ctClass.hasModifier(ModifierKind.PUBLIC));
        org.junit.Assert.assertEquals(0, ctClass.getModifiers().size());
    }
}

