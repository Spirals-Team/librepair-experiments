package spoon.test.factory;


public class FieldFactoryTest {
    @org.junit.Test
    public void testCreate() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.testclasses", "SampleClass");
        spoon.reflect.factory.FieldFactory ff = type.getFactory().Field();
        spoon.reflect.factory.TypeFactory tf = type.getFactory().Type();
        java.util.Set<spoon.reflect.declaration.ModifierKind> mods = new java.util.HashSet<spoon.reflect.declaration.ModifierKind>();
        mods.add(spoon.reflect.declaration.ModifierKind.PRIVATE);
        spoon.reflect.reference.CtTypeReference<?> tref = tf.createReference(java.lang.String.class);
        ff.create(type, mods, tref, "name");
        spoon.reflect.declaration.CtField<?> field = type.getField("name");
        org.junit.Assert.assertEquals("name", field.getSimpleName());
        org.junit.Assert.assertEquals(tref, field.getType());
        spoon.reflect.declaration.CtElement parent = field.getParent();
        org.junit.Assert.assertTrue((parent instanceof spoon.reflect.declaration.CtClass<?>));
        org.junit.Assert.assertEquals("SampleClass", ((spoon.reflect.declaration.CtClass<?>) (parent)).getSimpleName());
    }

    @org.junit.Test
    public void testCreateFromSource() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> target = spoon.testing.utils.ModelUtils.build("spoon.test.testclasses", "SampleClass");
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.targeted.testclasses.Foo.class, spoon.test.targeted.testclasses.Bar.class, spoon.test.targeted.testclasses.SuperClass.class);
        final spoon.reflect.declaration.CtClass<java.lang.Object> type = factory.Class().get(spoon.test.targeted.testclasses.Foo.class);
        spoon.reflect.declaration.CtField<?> source = type.getField("i");
        spoon.reflect.factory.FieldFactory ff = type.getFactory().Field();
        spoon.reflect.factory.TypeFactory tf = type.getFactory().Type();
        ff.create(target, source);
        spoon.reflect.declaration.CtField<?> field = target.getField("i");
        org.junit.Assert.assertEquals("i", field.getSimpleName());
        spoon.reflect.reference.CtTypeReference<?> tref = tf.createReference("int");
        org.junit.Assert.assertEquals(tref, field.getType());
        spoon.reflect.declaration.CtElement parent = field.getParent();
        org.junit.Assert.assertTrue((parent instanceof spoon.reflect.declaration.CtClass<?>));
        org.junit.Assert.assertEquals("SampleClass", ((spoon.reflect.declaration.CtClass<?>) (parent)).getSimpleName());
    }
}

