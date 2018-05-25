package spoon.test.factory;


import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.TypeFactory;
import spoon.reflect.visitor.filter.NamedElementFilter;


public class FactoryTest {
    @org.junit.Test
    public void testClone() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.testclasses", "SampleClass");
        spoon.reflect.declaration.CtMethod<?> m = type.getMethodsByName("method3").get(0);
        int i = m.getBody().getStatements().size();
        m = m.clone();
        org.junit.Assert.assertEquals(i, m.getBody().getStatements().size());
        org.junit.Assert.assertFalse(m.isParentInitialized());
    }

    @org.junit.Test
    public void testFactoryOverriding() throws java.lang.Exception {
        @java.lang.SuppressWarnings("serial")
        class MyCtMethod<T> extends spoon.support.reflect.declaration.CtMethodImpl<T> {}
        @java.lang.SuppressWarnings("serial")
        final spoon.reflect.factory.CoreFactory specialCoreFactory = new spoon.support.DefaultCoreFactory() {
            @java.lang.Override
            public <T> spoon.reflect.declaration.CtMethod<T> createMethod() {
                MyCtMethod<T> m = new MyCtMethod<T>();
                m.setFactory(getMainFactory());
                return m;
            }
        };
        Launcher launcher = new Launcher() {
            @java.lang.Override
            public spoon.reflect.factory.Factory createFactory() {
                return new spoon.reflect.factory.FactoryImpl(specialCoreFactory, new spoon.support.StandardEnvironment());
            }
        };
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.testclasses", "SampleClass", launcher.getFactory());
        spoon.reflect.declaration.CtMethod<?> m = type.getMethodsByName("method3").get(0);
        org.junit.Assert.assertTrue((m instanceof MyCtMethod));
    }

    @org.junit.Test
    public void testClassAccessCreatedFromFactories() throws java.lang.Exception {
        final CtType<spoon.test.factory.testclasses.Foo> foo = spoon.testing.utils.ModelUtils.buildClass(spoon.test.factory.testclasses.Foo.class);
        org.junit.Assert.assertEquals(1, foo.getAnnotations().size());
        org.junit.Assert.assertEquals(0, foo.getAnnotations().get(0).getValues().size());
        foo.getFactory().Annotation().annotate(foo, spoon.test.factory.testclasses.Foo.Bar.class, "clazz", spoon.test.factory.testclasses.Foo.class);
        org.junit.Assert.assertEquals(1, foo.getAnnotations().size());
        org.junit.Assert.assertEquals(1, foo.getAnnotations().get(0).getValues().size());
        org.junit.Assert.assertTrue(((foo.getAnnotations().get(0).getValues().get("clazz")) instanceof spoon.reflect.code.CtFieldRead));
        org.junit.Assert.assertEquals("spoon.test.factory.testclasses.Foo.class", foo.getAnnotations().get(0).getValues().get("clazz").toString());
        foo.getFactory().Annotation().annotate(foo, spoon.test.factory.testclasses.Foo.Bar.class, "classes", new java.lang.Class[]{ spoon.test.factory.testclasses.Foo.class });
        org.junit.Assert.assertEquals(1, foo.getAnnotations().size());
        org.junit.Assert.assertEquals(2, foo.getAnnotations().get(0).getValues().size());
        org.junit.Assert.assertTrue(((foo.getAnnotations().get(0).getValues().get("classes")) instanceof spoon.reflect.code.CtNewArray));
        org.junit.Assert.assertEquals(1, ((spoon.reflect.code.CtNewArray) (foo.getAnnotations().get(0).getValues().get("classes"))).getElements().size());
        org.junit.Assert.assertEquals("spoon.test.factory.testclasses.Foo.class", ((spoon.reflect.code.CtNewArray) (foo.getAnnotations().get(0).getValues().get("classes"))).getElements().get(0).toString());
    }

    @org.junit.Test
    public void testCtModel() throws java.lang.Exception {
        SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/test/java/spoon/test/factory/testclasses");
        spoon.buildModel();
        CtModel model = spoon.getModel();
        org.junit.Assert.assertEquals(1, model.getAllTypes().size());
        org.junit.Assert.assertEquals(5, model.getAllPackages().size());
        model.getRootPackage().addPackage(model.getRootPackage());
        org.junit.Assert.assertEquals(1, model.getAllTypes().size());
        org.junit.Assert.assertEquals(5, model.getAllPackages().size());
        model.getRootPackage().getPackage("spoon").addPackage(model.getRootPackage().getPackage("spoon"));
        org.junit.Assert.assertEquals(1, model.getAllTypes().size());
        org.junit.Assert.assertEquals(5, model.getAllPackages().size());
        model.getRootPackage().addPackage(model.getRootPackage().getPackage("spoon"));
        org.junit.Assert.assertEquals(1, model.getAllTypes().size());
        org.junit.Assert.assertEquals(5, model.getAllPackages().size());
        CtPackage p = model.getElements(new NamedElementFilter<>(CtPackage.class, "spoon")).get(0).clone();
        CtField f = spoon.getFactory().Core().createField();
        f.setSimpleName("foo");
        f.setType(spoon.getFactory().Type().BYTE);
        p.getElements(new NamedElementFilter<>(CtPackage.class, "testclasses")).get(0).getType("Foo").addField(f);
        try {
            model.getRootPackage().addPackage(p);
            org.junit.Assert.fail("no exception thrown");
        } catch (java.lang.IllegalStateException success) {
        }
        model.processWith(new spoon.processing.AbstractProcessor<CtType>() {
            @java.lang.Override
            public void process(CtType element) {
                element.delete();
            }
        });
        org.junit.Assert.assertEquals(0, model.getAllTypes().size());
    }

    public void testIncrementalModel() throws java.lang.Exception {
    }

    @org.junit.Test
    public void specificationCoreFactoryCreate() throws java.lang.Exception {
        for (CtType<? extends spoon.reflect.declaration.CtElement> itf : spoon.test.SpoonTestHelpers.getAllInstantiableMetamodelInterfaces()) {
            spoon.reflect.declaration.CtElement o = itf.getFactory().Core().create(itf.getActualClass());
            org.junit.Assert.assertNotNull(o);
            org.junit.Assert.assertTrue(itf.getActualClass().isInstance(o));
        }
    }
}

