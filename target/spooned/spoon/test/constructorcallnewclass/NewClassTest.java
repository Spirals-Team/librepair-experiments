package spoon.test.constructorcallnewclass;


public class NewClassTest {
    private java.util.List<spoon.reflect.code.CtNewClass<?>> newClasses;

    @org.junit.Before
    public void setUp() throws java.lang.Exception {
        final spoon.reflect.factory.Factory build = spoon.testing.utils.ModelUtils.build(spoon.test.constructorcallnewclass.testclasses.Foo.class);
        final spoon.reflect.declaration.CtClass<?> foo = ((spoon.reflect.declaration.CtClass<?>) (build.Type().get(spoon.test.constructorcallnewclass.testclasses.Foo.class)));
        newClasses = foo.getElements(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtNewClass<?>>(spoon.reflect.code.CtNewClass.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtNewClass<?> element) {
                return true;
            }
        });
    }

    @org.junit.Test
    public void testNewClassWithObjectClass() throws java.lang.Exception {
        final spoon.reflect.code.CtNewClass<?> newClass = newClasses.get(0);
        assertType(java.lang.Object.class, newClass);
        assertIsConstructor(newClass.getExecutable());
        assertHasParameters(0, newClass.getArguments());
        assertIsAnonymous(newClass.getAnonymousClass());
        assertSuperClass(java.lang.Object.class, newClass.getAnonymousClass());
    }

    @org.junit.Test
    public void testNewClassWithInterface() throws java.lang.Exception {
        final spoon.reflect.code.CtNewClass<?> newClass = newClasses.get(1);
        assertType(spoon.test.constructorcallnewclass.testclasses.Foo.Bar.class, newClass);
        assertIsConstructor(newClass.getExecutable());
        assertHasParameters(0, newClass.getArguments());
        assertIsAnonymous(newClass.getAnonymousClass());
        assertSuperInterface(spoon.test.constructorcallnewclass.testclasses.Foo.Bar.class, newClass.getAnonymousClass());
    }

    @org.junit.Test
    public void testNewClassWithInterfaceGeneric() throws java.lang.Exception {
        final spoon.reflect.code.CtNewClass<?> newClass = newClasses.get(2);
        assertType(spoon.test.constructorcallnewclass.testclasses.Foo.Tacos.class, newClass);
        assertIsConstructor(newClass.getExecutable());
        assertHasParameters(0, newClass.getArguments());
        assertIsAnonymous(newClass.getAnonymousClass());
        assertSuperInterface(spoon.test.constructorcallnewclass.testclasses.Foo.Tacos.class, newClass.getAnonymousClass());
        spoon.reflect.reference.CtTypeReference[] ctTypeReferences = newClass.getAnonymousClass().getSuperInterfaces().toArray(new spoon.reflect.reference.CtTypeReference[0]);
        org.junit.Assert.assertEquals("Super interface is typed by the class of the constructor", java.lang.String.class, ctTypeReferences[0].getActualTypeArguments().get(0).getActualClass());
    }

    @org.junit.Test
    public void testNewClassInterfaceWithParameters() throws java.lang.Exception {
        final spoon.reflect.code.CtNewClass<?> newClass = newClasses.get(3);
        assertType(spoon.test.constructorcallnewclass.testclasses.Foo.BarImpl.class, newClass);
        assertIsConstructor(newClass.getExecutable());
        assertHasParameters(1, newClass.getArguments());
        assertIsAnonymous(newClass.getAnonymousClass());
        assertSuperClass(spoon.test.constructorcallnewclass.testclasses.Foo.BarImpl.class, newClass.getAnonymousClass());
    }

    @org.junit.Test
    public void testNewClassInEnumeration() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = null;
        try {
            factory = spoon.testing.utils.ModelUtils.build(spoon.test.constructorcallnewclass.testclasses.Bar.class);
        } catch (java.lang.NullPointerException e) {
            org.junit.Assert.fail();
        }
        final spoon.reflect.declaration.CtClass<?> foo = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.constructorcallnewclass.testclasses.Bar.class)));
        final spoon.reflect.code.CtNewClass<?> newClass = foo.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtNewClass<?>>(spoon.reflect.code.CtNewClass.class)).get(0);
        assertIsConstructor(newClass.getExecutable());
        assertHasParameters(1, newClass.getArguments());
        org.junit.Assert.assertEquals("\">\"", newClass.getArguments().get(0).toString());
        assertIsAnonymous(newClass.getAnonymousClass());
        assertSuperClass(spoon.test.constructorcallnewclass.testclasses.Bar.class, newClass.getAnonymousClass());
    }

    private void assertSuperClass(java.lang.Class<?> expected, spoon.reflect.declaration.CtClass<?> anonymousClass) {
        org.junit.Assert.assertEquals("There isn't a super interface if there is a super class", 0, anonymousClass.getSuperInterfaces().size());
        org.junit.Assert.assertEquals("There is a super class if there isn't a super interface", expected, anonymousClass.getSuperclass().getActualClass());
    }

    private void assertSuperInterface(java.lang.Class<?> expected, spoon.reflect.declaration.CtClass<?> anonymousClass) {
        org.junit.Assert.assertNull("There isn't super class if there is a super interface", anonymousClass.getSuperclass());
        org.junit.Assert.assertEquals("There is a super interface if there isn't super class", expected, anonymousClass.getSuperInterfaces().toArray(new spoon.reflect.reference.CtTypeReference[0])[0].getActualClass());
    }

    private void assertIsAnonymous(spoon.reflect.declaration.CtClass<?> anonymousClass) {
        org.junit.Assert.assertTrue("Class in CtNewClass is anonymous", anonymousClass.isAnonymous());
    }

    private void assertHasParameters(int sizeExpected, java.util.List<spoon.reflect.code.CtExpression<?>> arguments) {
        if (sizeExpected == 0) {
            org.junit.Assert.assertEquals("New class without parameter", sizeExpected, arguments.size());
        }else {
            org.junit.Assert.assertEquals("New class with parameters", sizeExpected, arguments.size());
        }
    }

    private void assertIsConstructor(spoon.reflect.reference.CtExecutableReference<?> executable) {
        org.junit.Assert.assertTrue("Method must be a constructor", executable.isConstructor());
    }

    private void assertType(java.lang.Class<?> typeExpected, spoon.reflect.code.CtNewClass<?> newClass) {
        org.junit.Assert.assertEquals("New class is typed by the class of the constructor", typeExpected, newClass.getType().getActualClass());
    }

    @org.junit.Test
    public void testMoreThan9NewClass() throws java.lang.Exception {
        final spoon.reflect.factory.Factory build = spoon.testing.utils.ModelUtils.build(spoon.test.constructorcallnewclass.testclasses.Foo2.class);
        final spoon.reflect.declaration.CtClass<?> foo = ((spoon.reflect.declaration.CtClass<?>) (build.Type().get(spoon.test.constructorcallnewclass.testclasses.Foo2.class)));
        java.util.List<spoon.reflect.code.CtNewClass<?>> elements = foo.getElements(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtNewClass<?>>(spoon.reflect.code.CtNewClass.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtNewClass<?> element) {
                return true;
            }
        });
        org.junit.Assert.assertEquals(13, elements.size());
        org.junit.Assert.assertEquals(((spoon.test.constructorcallnewclass.testclasses.Foo2.class.getCanonicalName()) + "$12"), elements.get(11).getAnonymousClass().getQualifiedName());
        org.junit.Assert.assertEquals(((spoon.test.constructorcallnewclass.testclasses.Foo2.class.getCanonicalName()) + "$12$1"), elements.get(12).getAnonymousClass().getQualifiedName());
    }

    @org.junit.Test
    public void testCtNewClassInNoClasspath() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/new-class");
        launcher.setSourceOutputDirectory("./target/new-class");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<java.lang.Object> aClass = launcher.getFactory().Class().get("IndexWriter");
        final java.util.List<spoon.reflect.code.CtNewClass> ctNewClasses = aClass.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtNewClass.class));
        final spoon.reflect.code.CtNewClass ctNewClass = ctNewClasses.get(0);
        final spoon.reflect.code.CtNewClass secondNewClass = ctNewClasses.get(1);
        final spoon.reflect.declaration.CtClass anonymousClass = ctNewClass.getAnonymousClass();
        org.junit.Assert.assertNotNull(anonymousClass);
        org.junit.Assert.assertNotNull(anonymousClass.getSuperclass());
        org.junit.Assert.assertEquals("With", anonymousClass.getSuperclass().getSimpleName());
        org.junit.Assert.assertEquals("org.apache.lucene.store.Lock$With", anonymousClass.getSuperclass().getQualifiedName());
        org.junit.Assert.assertEquals("Lock", anonymousClass.getSuperclass().getDeclaringType().getSimpleName());
        org.junit.Assert.assertEquals("org.apache.lucene.store.Lock.With", anonymousClass.getSuperclass().toString());
        org.junit.Assert.assertEquals("1", anonymousClass.getSimpleName());
        org.junit.Assert.assertEquals("2", secondNewClass.getAnonymousClass().getSimpleName());
        org.junit.Assert.assertEquals(1, anonymousClass.getMethods().size());
        spoon.testing.utils.ModelUtils.canBeBuilt("./target/new-class", 8, true);
    }
}

