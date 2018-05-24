package spoon.test.factory;


public class TypeFactoryTest {
    @org.junit.Test
    public void testCreateTypeRef() {
        spoon.Launcher launcher = new spoon.Launcher();
        spoon.reflect.reference.CtTypeReference<java.lang.Object> ctTypeReference = launcher.getFactory().Code().createCtTypeReference(short.class);
        org.junit.Assert.assertEquals("short", ctTypeReference.getSimpleName());
        org.junit.Assert.assertEquals("short", ctTypeReference.getQualifiedName());
        ctTypeReference = launcher.getFactory().Code().createCtTypeReference(java.lang.Object.class);
        org.junit.Assert.assertEquals("Object", ctTypeReference.getSimpleName());
        org.junit.Assert.assertEquals("java.lang.Object", ctTypeReference.getQualifiedName());
        ctTypeReference = launcher.getFactory().Code().createCtTypeReference(null);
        org.junit.Assert.assertEquals(null, ctTypeReference);
        ctTypeReference = launcher.getFactory().Code().createCtTypeReference(spoon.reflect.code.CtComment.CommentType.class);
        org.junit.Assert.assertEquals("CommentType", ctTypeReference.getSimpleName());
        org.junit.Assert.assertEquals("spoon.reflect.code.CtComment$CommentType", ctTypeReference.getQualifiedName());
    }

    @org.junit.Test
    public void reflectionAPI() throws java.lang.Exception {
        spoon.reflect.declaration.CtType s = new spoon.reflect.factory.TypeFactory().get(java.lang.String.class);
        org.junit.Assert.assertEquals("String", s.getSimpleName());
        org.junit.Assert.assertEquals("java.lang.String", s.getQualifiedName());
        org.junit.Assert.assertEquals(3, s.getSuperInterfaces().size());
        org.junit.Assert.assertEquals(2, s.getMethodsByName("toLowerCase").size());
    }

    @org.junit.Test
    public void testGetClassInAnInterface() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.factory.testclasses3.Cooking> cook = spoon.testing.utils.ModelUtils.buildClass(spoon.test.factory.testclasses3.Cooking.class);
        org.junit.Assert.assertNotNull(cook.getFactory().Type().get("spoon.test.factory.testclasses3.Cooking$Tacos"));
        org.junit.Assert.assertNotNull(cook.getFactory().Class().get("spoon.test.factory.testclasses3.Cooking$Tacos"));
        org.junit.Assert.assertNotNull(cook.getFactory().Type().get(spoon.test.factory.testclasses3.Cooking.Tacos.class));
        org.junit.Assert.assertNotNull(cook.getFactory().Class().get(spoon.test.factory.testclasses3.Cooking.Tacos.class));
        final spoon.reflect.declaration.CtType<spoon.test.factory.testclasses3.Prepare> prepare = spoon.testing.utils.ModelUtils.buildClass(spoon.test.factory.testclasses3.Prepare.class);
        org.junit.Assert.assertNotNull(prepare.getFactory().Type().get("spoon.test.factory.testclasses3.Prepare$Tacos"));
        org.junit.Assert.assertNotNull(prepare.getFactory().Interface().get("spoon.test.factory.testclasses3.Prepare$Tacos"));
        org.junit.Assert.assertNotNull(prepare.getFactory().Type().get(spoon.test.factory.testclasses3.Prepare.Pozole.class));
        org.junit.Assert.assertNotNull(prepare.getFactory().Interface().get(spoon.test.factory.testclasses3.Prepare.Pozole.class));
    }

    @org.junit.Test
    public void testGetClassWithDollarAndNestedClass() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(new java.io.File("./src/test/resources/dollar-and-nested-classes"));
        spoon.reflect.declaration.CtType<?> poorName = factory.Type().get("$Poor$Name");
        spoon.reflect.declaration.CtType<?> poorNameChoice = factory.Type().get("$Poor$Name$Choice");
        org.junit.Assert.assertNotNull(poorName);
        org.junit.Assert.assertNotNull(poorNameChoice);
        org.junit.Assert.assertEquals(poorNameChoice, poorName.getMethodsByName("lookingForTroubles").get(0).getType().getTypeDeclaration());
    }
}

