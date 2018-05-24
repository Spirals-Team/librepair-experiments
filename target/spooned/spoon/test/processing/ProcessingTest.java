package spoon.test.processing;


import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtType;
import spoon.test.processing.testclasses.CtClassProcessor;
import spoon.test.processing.testclasses.CtInterfaceProcessor;
import spoon.test.processing.testclasses.CtTypeProcessor;
import spoon.test.processing.testclasses.GenericCtTypeProcessor;


public class ProcessingTest {
    @org.junit.Test
    public void testInsertBegin() throws java.lang.Exception {
        CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.processing.testclasses", "SampleForInsertBefore");
        for (spoon.reflect.declaration.CtMethod<?> meth : type.getMethods()) {
            int i = meth.getBody().getStatements().size();
            meth.getBody().insertBegin(type.getFactory().Code().createCodeSnippetStatement("int i = 0;"));
            org.junit.Assert.assertEquals(("insert failed for method " + (meth.getSimpleName())), (i + 1), meth.getBody().getStatements().size());
            org.junit.Assert.assertEquals(("insert failed for method " + (meth.getSimpleName())), "int i = 0;", meth.getBody().getStatement(0).toString());
        }
        for (spoon.reflect.declaration.CtConstructor<?> constructor : type.getConstructors()) {
            int i = constructor.getBody().getStatements().size();
            constructor.getBody().insertBegin(type.getFactory().Code().createCodeSnippetStatement("int i = 0;"));
            org.junit.Assert.assertEquals(("insert failed for constructor " + (constructor.getSimpleName())), (i + 1), constructor.getBody().getStatements().size());
            org.junit.Assert.assertEquals(("insert failed for constructor " + (constructor.getSimpleName())), "int i = 0;", constructor.getBody().getStatement(1).toString());
        }
        spoon.reflect.declaration.CtConstructor<?> constructor = type.getConstructor(type.getFactory().Type().INTEGER_PRIMITIVE);
        java.lang.String myBeforeStatementAsString = "int before";
        for (spoon.reflect.code.CtSwitch<?> ctSwitch : constructor.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtSwitch<?>>(spoon.reflect.code.CtSwitch.class))) {
            ctSwitch.insertBefore(type.getFactory().Code().createCodeSnippetStatement(myBeforeStatementAsString));
        }
        org.junit.Assert.assertEquals("insert has not been done at the right position", myBeforeStatementAsString, constructor.getBody().getStatement(3).toString());
        org.junit.Assert.assertEquals("insert has not been done at the right position", myBeforeStatementAsString, constructor.getBody().getStatement(5).toString());
        org.junit.Assert.assertEquals("insert has not been done at the right position", myBeforeStatementAsString, constructor.getBody().getStatement(7).toString());
        org.junit.Assert.assertFalse("switch should not be the same", constructor.getBody().getStatement(6).equals(constructor.getBody().getStatement(8)));
        org.junit.Assert.assertFalse("switch should not be the same", constructor.getBody().getStatement(6).toString().equals(constructor.getBody().getStatement(8).toString()));
    }

    @org.junit.Test
    public void testInsertEnd() throws java.lang.Exception {
        CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.processing.testclasses", "SampleForInsertBefore");
        for (spoon.reflect.declaration.CtMethod<?> meth : type.getMethods()) {
            int i = meth.getBody().getStatements().size();
            meth.getBody().insertEnd(type.getFactory().Code().createCodeSnippetStatement("int i = 0"));
            org.junit.Assert.assertEquals(("insert failed for method " + (meth.getSimpleName())), (i + 1), meth.getBody().getStatements().size());
            org.junit.Assert.assertEquals(("insert failed for method " + (meth.getSimpleName())), "int i = 0", meth.getBody().getStatement(((meth.getBody().getStatements().size()) - 1)).toString());
        }
        for (spoon.reflect.declaration.CtConstructor<?> constructor : type.getConstructors()) {
            int i = constructor.getBody().getStatements().size();
            constructor.getBody().insertEnd(type.getFactory().Code().createCodeSnippetStatement("int i = 0"));
            org.junit.Assert.assertEquals(("insert failed for constructor " + (constructor.getSimpleName())), (i + 1), constructor.getBody().getStatements().size());
            org.junit.Assert.assertEquals("insert failed for constructor", "int i = 0", constructor.getBody().getStatement(((constructor.getBody().getStatements().size()) - 1)).toString());
        }
        spoon.reflect.declaration.CtConstructor<?> constructor = type.getConstructor(type.getFactory().Type().INTEGER_PRIMITIVE);
        java.lang.String myBeforeStatementAsString = "int after";
        for (spoon.reflect.code.CtSwitch<?> ctSwitch : constructor.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtSwitch<?>>(spoon.reflect.code.CtSwitch.class))) {
            ctSwitch.insertAfter(type.getFactory().Code().createCodeSnippetStatement(myBeforeStatementAsString));
        }
        org.junit.Assert.assertEquals("insert has not been done at the right position", myBeforeStatementAsString, constructor.getBody().getStatement(3).toString());
        org.junit.Assert.assertEquals("insert has not been done at the right position", myBeforeStatementAsString, constructor.getBody().getStatement(5).toString());
        org.junit.Assert.assertEquals("insert has not been done at the right position", myBeforeStatementAsString, constructor.getBody().getStatement(7).toString());
        org.junit.Assert.assertFalse("switch should not be the same", constructor.getBody().getStatement(6).equals(constructor.getBody().getStatement(8)));
        org.junit.Assert.assertFalse("switch should not be the same", constructor.getBody().getStatement(6).toString().equals(constructor.getBody().getStatement(8).toString()));
    }

    @org.junit.Test
    public void testProcessorNotFoundThrowAnException() throws java.lang.Exception {
        try {
            new Launcher().run(new java.lang.String[]{ "-p", "fr.inria.gforge.spoon.MakeAnAwesomeTacosProcessor" });
            org.junit.Assert.fail("The processor doesn't exist. We must throw an exception.");
        } catch (spoon.SpoonException ignore) {
        }
    }

    class WrongProcessor extends spoon.processing.AbstractProcessor<spoon.reflect.declaration.CtElement> {
        public WrongProcessor(int myParameter) {
        }

        @java.lang.Override
        public void process(spoon.reflect.declaration.CtElement element) {
            java.lang.System.out.println(element);
        }
    }

    @org.junit.Test
    public void testProcessorWithNoArgumentsInConstructor() throws java.lang.Exception {
        Launcher l = new Launcher();
        l.getEnvironment().setLevel(org.apache.log4j.Level.ERROR.toString());
        l.buildModel();
        try {
            new spoon.support.compiler.jdt.JDTBasedSpoonCompiler(l.getFactory()).instantiateAndProcess(java.util.Collections.singletonList("spoon.test.processing.ProcessingTest$WrongProcessor"));
            org.junit.Assert.fail();
        } catch (spoon.SpoonException e) {
            org.junit.Assert.assertTrue(e.getMessage().startsWith("Unable to instantiate processor"));
            org.junit.Assert.assertTrue(e.getMessage().endsWith("Your processor should have a constructor with no arguments"));
            org.junit.Assert.assertTrue(((e.getCause()) instanceof java.lang.InstantiationException));
        }
    }

    @org.junit.Test
    public void testInitProperties() throws java.lang.Exception {
        class AProcessor extends spoon.processing.AbstractManualProcessor {
            @spoon.processing.Property
            java.lang.String aString;

            @spoon.processing.Property
            int anInt;

            @spoon.processing.Property
            java.lang.Object anObject;

            @spoon.processing.Property
            int[] arrayInt;

            @spoon.processing.Property
            java.util.List<java.lang.String> listString;

            @spoon.processing.Property
            boolean[] arrayBoolean;

            @spoon.processing.Property
            java.util.Map<java.lang.String, java.lang.Double> mapStringDouble;

            @java.lang.Override
            public void process() {
            }
        }
        AProcessor p = new AProcessor();
        Launcher launcher = new Launcher();
        p.setFactory(launcher.getFactory());
        spoon.processing.ProcessorProperties props = new spoon.processing.ProcessorPropertiesImpl();
        props.set("aString", "foo");
        props.set("anInt", 5);
        java.lang.Object o = new java.lang.Object();
        props.set("anObject", o);
        int[] arrayInt = new int[]{ 1, 2, 3 };
        props.set("arrayInt", arrayInt);
        props.set("listString", java.util.Arrays.asList(new java.lang.String[]{ "42" }));
        boolean[] arrayBoolean = new boolean[]{ true };
        props.set("arrayBoolean", arrayBoolean);
        java.util.HashMap<java.lang.String, java.lang.Double> mapTest = new java.util.HashMap<>();
        mapTest.put("foobar", 42.42);
        props.set("mapStringDouble", mapTest);
        spoon.testing.utils.ProcessorUtils.initProperties(p, props);
        org.junit.Assert.assertEquals("foo", p.aString);
        org.junit.Assert.assertEquals(5, p.anInt);
        org.junit.Assert.assertSame(o, p.anObject);
        org.junit.Assert.assertSame(arrayInt, p.arrayInt);
        org.junit.Assert.assertEquals(java.util.Arrays.asList(new java.lang.String[]{ "42" }), p.listString);
        org.junit.Assert.assertSame(arrayBoolean, p.arrayBoolean);
        org.junit.Assert.assertSame(mapTest, p.mapStringDouble);
    }

    @org.junit.Test
    public void testInitPropertiesWithWrongType() throws java.lang.Exception {
        class AProcessor extends spoon.processing.AbstractManualProcessor {
            @spoon.processing.Property
            java.lang.String aString;

            @spoon.processing.Property
            int anInt;

            @spoon.processing.Property
            java.lang.Object anObject;

            @java.lang.Override
            public void process() {
            }
        }
        AProcessor p = new AProcessor();
        Launcher launcher = new Launcher();
        p.setFactory(launcher.getFactory());
        spoon.processing.ProcessorProperties props = new spoon.processing.ProcessorPropertiesImpl();
        props.set("aString", "foo");
        props.set("anObject", "foo");
        props.set("anInt", "foo");
        try {
            spoon.testing.utils.ProcessorUtils.initProperties(p, props);
            org.junit.Assert.fail();
        } catch (spoon.SpoonException e) {
            org.junit.Assert.assertTrue(e.getMessage().contains("anInt"));
        }
        org.junit.Assert.assertEquals("foo", p.aString);
        org.junit.Assert.assertEquals(0, p.anInt);
        org.junit.Assert.assertNull(p.anObject);
    }

    @org.junit.Test
    public void testInitPropertiesWithStringType() throws java.lang.Exception {
        class AProcessor extends spoon.processing.AbstractManualProcessor {
            @spoon.processing.Property
            java.lang.String aString;

            @spoon.processing.Property
            int anInt;

            @spoon.processing.Property
            java.lang.Object anObject;

            @spoon.processing.Property
            int[] arrayInt;

            @spoon.processing.Property
            java.util.List<java.lang.String> listString;

            @spoon.processing.Property
            boolean[] arrayBoolean;

            @spoon.processing.Property
            java.util.Map<java.lang.String, java.lang.Double> mapStringDouble;

            @java.lang.Override
            public void process() {
            }
        }
        AProcessor p = new AProcessor();
        Launcher launcher = new Launcher();
        p.setFactory(launcher.getFactory());
        spoon.processing.ProcessorProperties props = new spoon.processing.ProcessorPropertiesImpl();
        props.set("aString", "foo");
        props.set("anInt", "42");
        props.set("anObject", "{}");
        props.set("arrayInt", "[42,43]");
        props.set("listString", "[\"foo\", \"bar\"]");
        props.set("arrayBoolean", "[true]");
        props.set("mapStringDouble", "{\"foo\": 10.21, \"bar\": 14.42}");
        spoon.testing.utils.ProcessorUtils.initProperties(p, props);
        org.junit.Assert.assertEquals("foo", p.aString);
        org.junit.Assert.assertEquals(42, p.anInt);
        org.junit.Assert.assertNotNull(p.anObject);
        org.junit.Assert.assertArrayEquals(new int[]{ 42, 43 }, p.arrayInt);
        org.junit.Assert.assertEquals(java.util.Arrays.asList(new java.lang.String[]{ "foo", "bar" }), p.listString);
        org.junit.Assert.assertArrayEquals(new boolean[]{ true }, p.arrayBoolean);
        java.util.Map<java.lang.String, java.lang.Double> mamap = new java.util.HashMap<>();
        mamap.put("foo", 10.21);
        mamap.put("bar", 14.42);
        org.junit.Assert.assertEquals(mamap, p.mapStringDouble);
    }

    @org.junit.Test
    public void testProcessorWithGenericType() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/imports/testclasses");
        CtClassProcessor classProcessor = new CtClassProcessor();
        spoon.addProcessor(classProcessor);
        spoon.run();
        org.junit.Assert.assertFalse(classProcessor.elements.isEmpty());
        for (CtType type : classProcessor.elements) {
            org.junit.Assert.assertTrue((("Type " + (type.getSimpleName())) + " is not a class"), (type instanceof CtClass));
        }
    }

    @org.junit.Test
    public void testCallProcessorWithMultipleTypes() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/imports/testclasses");
        CtClassProcessor classProcessor = new CtClassProcessor();
        spoon.addProcessor(classProcessor);
        CtTypeProcessor typeProcessor = new CtTypeProcessor();
        spoon.addProcessor(typeProcessor);
        CtInterfaceProcessor interfaceProcessor = new CtInterfaceProcessor();
        spoon.addProcessor(interfaceProcessor);
        spoon.run();
        org.junit.Assert.assertFalse(classProcessor.elements.isEmpty());
        for (CtType type : classProcessor.elements) {
            org.junit.Assert.assertTrue((("Type " + (type.getSimpleName())) + " is not a class"), (type instanceof CtClass));
        }
        org.junit.Assert.assertFalse(classProcessor.elements.isEmpty());
        for (CtType type : interfaceProcessor.elements) {
            org.junit.Assert.assertTrue((("Type " + (type.getSimpleName())) + " is not an interface"), (type instanceof CtInterface));
        }
        org.junit.Assert.assertFalse(typeProcessor.elements.isEmpty());
        for (CtType type : typeProcessor.elements) {
            if (type instanceof CtClass) {
                org.junit.Assert.assertTrue(classProcessor.elements.contains(type));
                org.junit.Assert.assertFalse(interfaceProcessor.elements.contains(type));
            }else
                if (type instanceof CtInterface) {
                    org.junit.Assert.assertFalse(classProcessor.elements.contains(type));
                    org.junit.Assert.assertTrue(interfaceProcessor.elements.contains(type));
                }else {
                    org.junit.Assert.assertFalse(classProcessor.elements.contains(type));
                    org.junit.Assert.assertFalse(interfaceProcessor.elements.contains(type));
                }

        }
    }
}

