package spoon.test.interfaces;


public class InterfaceTest {
    private spoon.reflect.factory.Factory factory;

    @org.junit.Before
    public void setUp() throws java.lang.Exception {
        final java.io.File testDirectory = new java.io.File("./src/test/java/spoon/test/interfaces/testclasses/");
        final spoon.Launcher launcher = new spoon.Launcher();
        this.factory = launcher.createFactory();
        factory.getEnvironment().setComplianceLevel(8);
        spoon.SpoonModelBuilder compiler = launcher.createCompiler(this.factory);
        compiler.addInputSource(testDirectory);
        compiler.build();
    }

    @org.junit.Test
    public void testDefaultMethodInInterface() throws java.lang.Exception {
        final spoon.reflect.declaration.CtInterface<?> ctInterface = ((spoon.reflect.declaration.CtInterface<?>) (factory.Type().get(spoon.test.interfaces.testclasses.InterfaceWithDefaultMethods.class)));
        final spoon.reflect.declaration.CtMethod<?> ctMethod = ctInterface.getMethodsByName("getZonedDateTime").get(0);
        org.junit.Assert.assertTrue("The method in the interface must to be default", ctMethod.isDefaultMethod());
        final java.lang.String expected = ((("default java.time.ZonedDateTime getZonedDateTime(java.lang.String zoneString) {" + (java.lang.System.lineSeparator())) + "    return java.time.ZonedDateTime.of(getLocalDateTime(), spoon.test.interfaces.testclasses.InterfaceWithDefaultMethods.getZoneId(zoneString));") + (java.lang.System.lineSeparator())) + "}";
        org.junit.Assert.assertEquals("The default method must to be well printed", expected, ctMethod.toString());
    }

    @org.junit.Test
    public void testDefaultMethodInConsumer() throws java.lang.Exception {
        final spoon.reflect.declaration.CtInterface<?> ctInterface = ((spoon.reflect.declaration.CtInterface<?>) (factory.Type().get(java.util.function.Consumer.class)));
        final spoon.reflect.declaration.CtMethod<?> ctMethod = ctInterface.getMethodsByName("andThen").get(0);
        org.junit.Assert.assertTrue("The method in the interface must to be default", ctMethod.isDefaultMethod());
    }

    @org.junit.Test
    public void testExtendsDefaultMethodInSubInterface() throws java.lang.Exception {
        final spoon.reflect.declaration.CtInterface<?> ctInterface = ((spoon.reflect.declaration.CtInterface<?>) (factory.Type().get(spoon.test.interfaces.testclasses.ExtendsDefaultMethodInterface.class)));
        org.junit.Assert.assertEquals("Sub interface must have only one method in its interface", 1, ctInterface.getMethods().size());
        org.junit.Assert.assertEquals("Sub interface must have 6 methods in its interface and its super interfaces", 6, ctInterface.getAllMethods().size());
        final spoon.reflect.declaration.CtMethod<?> getZonedDateTimeMethod = ctInterface.getMethodsByName("getZonedDateTime").get(0);
        org.junit.Assert.assertTrue("Method in the sub interface must be a default method", getZonedDateTimeMethod.isDefaultMethod());
        org.junit.Assert.assertEquals("Interface of the default method must be the sub interface", spoon.test.interfaces.testclasses.ExtendsDefaultMethodInterface.class, getZonedDateTimeMethod.getDeclaringType().getActualClass());
    }

    @org.junit.Test
    public void testRedefinesDefaultMethodInSubInterface() throws java.lang.Exception {
        final spoon.reflect.declaration.CtInterface<?> ctInterface = ((spoon.reflect.declaration.CtInterface<?>) (factory.Type().get(spoon.test.interfaces.testclasses.RedefinesDefaultMethodInterface.class)));
        org.junit.Assert.assertEquals("Sub interface must have only one method in its interface", 1, ctInterface.getMethods().size());
        org.junit.Assert.assertEquals("Sub interface must have 6 methods in its interface and its super interfaces", 6, ctInterface.getAllMethods().size());
        final spoon.reflect.declaration.CtMethod<?> getZonedDateTimeMethod = ctInterface.getMethodsByName("getZonedDateTime").get(0);
        org.junit.Assert.assertFalse("Method in the sub interface mustn't be a default method", getZonedDateTimeMethod.isDefaultMethod());
        org.junit.Assert.assertEquals("Interface of the default method must be the sub interface", spoon.test.interfaces.testclasses.RedefinesDefaultMethodInterface.class, getZonedDateTimeMethod.getDeclaringType().getActualClass());
    }

    @org.junit.Test
    public void testExtendsStaticMethodInSubInterface() throws java.lang.Exception {
        final spoon.reflect.declaration.CtInterface<?> ctInterface = ((spoon.reflect.declaration.CtInterface<?>) (factory.Type().get(spoon.test.interfaces.testclasses.ExtendsStaticMethodInterface.class)));
        org.junit.Assert.assertEquals("Sub interface must have only one method in its interface", 1, ctInterface.getMethods().size());
        org.junit.Assert.assertEquals("Sub interface must have 6 methods in its interface and its super interfaces", 6, ctInterface.getAllMethods().size());
        final spoon.reflect.declaration.CtMethod<?> getZoneIdMethod = ctInterface.getMethodsByName("getZoneId").get(0);
        org.junit.Assert.assertTrue("Method in the sub interface must be a static method", getZoneIdMethod.getModifiers().contains(spoon.reflect.declaration.ModifierKind.STATIC));
        org.junit.Assert.assertEquals("Interface of the static method must be the sub interface", spoon.test.interfaces.testclasses.ExtendsStaticMethodInterface.class, getZoneIdMethod.getDeclaringType().getActualClass());
    }

    @org.junit.Test
    public void testRedefinesStaticMethodInSubInterface() throws java.lang.Exception {
        final spoon.reflect.declaration.CtInterface<?> ctInterface = ((spoon.reflect.declaration.CtInterface<?>) (factory.Type().get(spoon.test.interfaces.testclasses.RedefinesStaticMethodInterface.class)));
        org.junit.Assert.assertEquals("Sub interface must have only one method in its interface", 1, ctInterface.getMethods().size());
        org.junit.Assert.assertEquals("Sub interface must have 6+12(from java.lang.Object) methods in its interface and its super interfaces", 6, ctInterface.getAllMethods().size());
        final spoon.reflect.declaration.CtMethod<?> getZoneIdMethod = ctInterface.getMethodsByName("getZoneId").get(0);
        org.junit.Assert.assertFalse("Method in the sub interface mustn't be a static method", getZoneIdMethod.getModifiers().contains(spoon.reflect.declaration.ModifierKind.STATIC));
        org.junit.Assert.assertEquals("Interface of the static method must be the sub interface", spoon.test.interfaces.testclasses.RedefinesStaticMethodInterface.class, getZoneIdMethod.getDeclaringType().getActualClass());
    }
}

