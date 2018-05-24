package spoon.test.constructorcallnewclass;


import spoon.Launcher;
import spoon.reflect.code.CtConstructorCall;


public class ConstructorCallTest {
    private java.util.List<CtConstructorCall<?>> constructorCalls;

    private java.util.List<CtConstructorCall<?>> constructorCallsPanini;

    @org.junit.Before
    public void setUp() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource((("./src/test/java/" + (spoon.test.constructorcallnewclass.testclasses.Foo.class.getCanonicalName().replace(".", "/"))) + ".java"));
        launcher.addInputResource((("./src/test/java/" + (spoon.test.constructorcallnewclass.testclasses.Panini.class.getCanonicalName().replace(".", "/"))) + ".java"));
        launcher.setSourceOutputDirectory("./target/spooned");
        launcher.run();
        final spoon.reflect.factory.Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> foo = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.constructorcallnewclass.testclasses.Foo.class)));
        java.util.TreeSet ts = new java.util.TreeSet(new spoon.support.comparator.DeepRepresentationComparator());
        ts.addAll(foo.getElements(new spoon.reflect.visitor.filter.AbstractFilter<CtConstructorCall<?>>(CtConstructorCall.class) {
            @java.lang.Override
            public boolean matches(CtConstructorCall<?> element) {
                return true;
            }
        }));
        constructorCalls = new java.util.ArrayList(ts);
        final spoon.reflect.declaration.CtType<spoon.test.constructorcallnewclass.testclasses.Panini> panini = factory.Type().get(spoon.test.constructorcallnewclass.testclasses.Panini.class);
        constructorCallsPanini = panini.getElements(new spoon.reflect.visitor.filter.TypeFilter<CtConstructorCall<?>>(CtConstructorCall.class));
    }

    @org.junit.Test
    public void testConstructorCallStringWithoutParameters() throws java.lang.Exception {
        final CtConstructorCall<?> constructorCall = constructorCalls.get(2);
        assertConstructorCallWithType(java.lang.String.class, constructorCall);
        assertIsConstructor(constructorCall);
        assertHasParameters(0, constructorCall);
    }

    @org.junit.Test
    public void testConstructorCallStringWithParameters() throws java.lang.Exception {
        final CtConstructorCall<?> constructorCall = constructorCalls.get(1);
        assertConstructorCallWithType(java.lang.String.class, constructorCall);
        assertIsConstructor(constructorCall);
        assertHasParameters(1, constructorCall);
    }

    @org.junit.Test
    public void testConstructorCallObjectWithoutParameters() throws java.lang.Exception {
        final CtConstructorCall<?> constructorCall = constructorCalls.get(3);
        assertConstructorCallWithType(spoon.test.constructorcallnewclass.testclasses.Foo.class, constructorCall);
        assertIsConstructor(constructorCall);
        assertHasParameters(0, constructorCall);
    }

    @org.junit.Test
    public void testConstructorCallObjectWithParameters() throws java.lang.Exception {
        final CtConstructorCall<?> constructorCall = constructorCalls.get(4);
        assertConstructorCallWithType(spoon.test.constructorcallnewclass.testclasses.Foo.class, constructorCall);
        assertIsConstructor(constructorCall);
        assertHasParameters(1, constructorCall);
    }

    @org.junit.Test
    public void testConstructorCallWithGenericArray() throws java.lang.Exception {
        final CtConstructorCall<?> ctConstructorCall = constructorCallsPanini.get(0);
        org.junit.Assert.assertEquals(1, ctConstructorCall.getType().getActualTypeArguments().size());
        final spoon.reflect.reference.CtTypeReference<?> implicitArray = ctConstructorCall.getType().getActualTypeArguments().get(0);
        org.junit.Assert.assertTrue(implicitArray.isImplicit());
        final spoon.reflect.reference.CtArrayTypeReference implicitArrayTyped = ((spoon.reflect.reference.CtArrayTypeReference) (implicitArray));
        org.junit.Assert.assertEquals("", implicitArrayTyped.toString());
        org.junit.Assert.assertEquals("AtomicLong[]", implicitArrayTyped.getSimpleName());
        org.junit.Assert.assertTrue(implicitArrayTyped.getComponentType().isImplicit());
        org.junit.Assert.assertEquals("", implicitArrayTyped.getComponentType().toString());
        org.junit.Assert.assertEquals("AtomicLong", implicitArrayTyped.getComponentType().getSimpleName());
    }

    private void assertHasParameters(int sizeExpected, CtConstructorCall<?> constructorCall) {
        if (sizeExpected == 0) {
            org.junit.Assert.assertEquals("Constructor call without parameter", sizeExpected, constructorCall.getArguments().size());
        }else {
            org.junit.Assert.assertEquals("Constructor call with parameters", sizeExpected, constructorCall.getArguments().size());
        }
    }

    private void assertIsConstructor(CtConstructorCall<?> constructorCall) {
        org.junit.Assert.assertTrue("Method must be a constructor", constructorCall.getExecutable().isConstructor());
    }

    private void assertConstructorCallWithType(java.lang.Class<?> typeExpected, CtConstructorCall<?> constructorCall) {
        org.junit.Assert.assertEquals("Constructor call is typed by the class of the constructor", typeExpected, constructorCall.getType().getActualClass());
    }

    @org.junit.Test
    public void testCoreConstructorCall() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        CtConstructorCall call = spoon.getFactory().Core().createConstructorCall();
        call.setType(spoon.getFactory().Core().createTypeReference().setSimpleName("Foo"));
        org.junit.Assert.assertEquals("new Foo()", call.toString());
        CtConstructorCall call2 = spoon.getFactory().Code().createConstructorCall(spoon.getFactory().Core().createTypeReference().setSimpleName("Bar"));
        org.junit.Assert.assertEquals("new Bar()", call2.toString());
    }
}

