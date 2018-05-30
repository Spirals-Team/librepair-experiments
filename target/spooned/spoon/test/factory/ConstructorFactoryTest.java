package spoon.test.factory;


public class ConstructorFactoryTest {
    @org.junit.Test
    public void testCreate() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.testclasses", "SampleClass");
        spoon.reflect.factory.Factory factory = type.getFactory();
        spoon.reflect.factory.ConstructorFactory ctorf = factory.Constructor();
        spoon.reflect.factory.CoreFactory coref = factory.Core();
        java.util.Set<spoon.reflect.declaration.ModifierKind> mods = new java.util.HashSet<spoon.reflect.declaration.ModifierKind>();
        mods.add(spoon.reflect.declaration.ModifierKind.PUBLIC);
        java.util.List<spoon.reflect.declaration.CtParameter<?>> params = new java.util.ArrayList<spoon.reflect.declaration.CtParameter<?>>();
        spoon.reflect.declaration.CtParameter<?> param = coref.createParameter();
        spoon.reflect.reference.CtTypeReference<?> tref = factory.Type().createReference(java.lang.String.class);
        param.setType(((spoon.reflect.reference.CtTypeReference) (tref)));
        param.setSimpleName("str");
        params.add(param);
        java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes = new java.util.HashSet<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>>();
        ctorf.create(type, mods, params, thrownTypes);
        spoon.reflect.declaration.CtConstructor<?> c = type.getConstructor(tref);
        org.junit.Assert.assertEquals(1, c.getParameters().size());
        org.junit.Assert.assertEquals("str", c.getParameters().get(0).getSimpleName());
    }

    @org.junit.Test
    public void testCreateDefault() {
        spoon.reflect.factory.Factory factory = new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), new spoon.support.StandardEnvironment());
        spoon.reflect.factory.ClassFactory classf = factory.Class();
        spoon.reflect.factory.ConstructorFactory ctorf = factory.Constructor();
        spoon.reflect.declaration.CtClass<?> ctclass = classf.create("Sample");
        ctorf.createDefault(ctclass);
        spoon.reflect.declaration.CtConstructor<?> c = ctclass.getConstructor();
        org.junit.Assert.assertEquals(0, c.getParameters().size());
    }
}

