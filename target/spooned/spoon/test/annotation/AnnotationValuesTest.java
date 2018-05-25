package spoon.test.annotation;


public class AnnotationValuesTest {
    static int i;

    @org.junit.Test
    public void testValuesOnJava7Annotation() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<spoon.test.annotation.testclasses.AnnotationValues> aClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.annotation.testclasses.AnnotationValues.class);
        spoon.reflect.declaration.CtAnnotation<?> ctAnnotation = spoon.test.annotation.AnnotationValuesTest.Request.on(aClass).giveMeAnnotation(spoon.test.annotation.testclasses.AnnotationValues.Annotation.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("integer").isTypedBy(spoon.reflect.code.CtLiteral.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("integers").isTypedBy(spoon.reflect.code.CtNewArray.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("string").isTypedBy(spoon.reflect.code.CtLiteral.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("strings").isTypedBy(spoon.reflect.code.CtLiteral.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("clazz").isTypedBy(spoon.reflect.code.CtFieldAccess.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("classes").isTypedBy(spoon.reflect.code.CtNewArray.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("b").isTypedBy(spoon.reflect.code.CtLiteral.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("e").isTypedBy(spoon.reflect.code.CtFieldAccess.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("ia").isTypedBy(spoon.reflect.declaration.CtAnnotation.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("ias").isTypedBy(spoon.reflect.code.CtNewArray.class);
    }

    @org.junit.Test
    public void testValuesOnJava8Annotation() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<spoon.test.annotation.testclasses.AnnotationValues> aClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.annotation.testclasses.AnnotationValues.class);
        spoon.reflect.code.CtConstructorCall aConstructorCall = aClass.getMethod("method").getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtConstructorCall.class)).get(0);
        spoon.reflect.declaration.CtAnnotation<?> ctAnnotation = spoon.test.annotation.AnnotationValuesTest.Request.on(aConstructorCall.getType()).giveMeAnnotation(spoon.test.annotation.testclasses.AnnotationValues.Annotation.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("integer").isTypedBy(spoon.reflect.code.CtLiteral.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("integers").isTypedBy(spoon.reflect.code.CtNewArray.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("string").isTypedBy(spoon.reflect.code.CtLiteral.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("strings").isTypedBy(spoon.reflect.code.CtLiteral.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("clazz").isTypedBy(spoon.reflect.code.CtFieldAccess.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("classes").isTypedBy(spoon.reflect.code.CtNewArray.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("b").isTypedBy(spoon.reflect.code.CtLiteral.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("e").isTypedBy(spoon.reflect.code.CtFieldAccess.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("ia").isTypedBy(spoon.reflect.declaration.CtAnnotation.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(ctAnnotation).giveMeAnnotationValue("ias").isTypedBy(spoon.reflect.code.CtNewArray.class);
    }

    @org.junit.Test
    public void testCtAnnotationAPI() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> annotation = factory.Core().createAnnotation();
        annotation.addValue("integers", 7);
        spoon.test.annotation.AnnotationValuesTest.Request.on(annotation).giveMeAnnotationValue("integers").isTypedBy(spoon.reflect.code.CtLiteral.class);
        annotation.addValue("integers", 42);
        spoon.test.annotation.AnnotationValuesTest.Request.on(annotation).giveMeAnnotationValue("integers").isTypedBy(spoon.reflect.code.CtNewArray.class);
        annotation.addValue("classes", java.lang.String.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(annotation).giveMeAnnotationValue("classes").isTypedBy(spoon.reflect.code.CtFieldAccess.class);
        annotation.addValue("classes", java.lang.Integer.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(annotation).giveMeAnnotationValue("classes").isTypedBy(spoon.reflect.code.CtNewArray.class);
        annotation.addValue("field", spoon.test.annotation.AnnotationValuesTest.class.getDeclaredField("i"));
        spoon.test.annotation.AnnotationValuesTest.Request.on(annotation).giveMeAnnotationValue("field").isTypedBy(spoon.reflect.code.CtFieldAccess.class);
    }

    @org.junit.Test
    public void testAnnotationFactory() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        final spoon.reflect.declaration.CtClass<java.lang.Object> target = factory.Class().create("org.example.Tacos");
        spoon.test.annotation.AnnotationValuesTest.Request.on(target).isNotAnnotated();
        spoon.reflect.declaration.CtAnnotation<java.lang.SuppressWarnings> annotation = factory.Annotation().annotate(target, java.lang.SuppressWarnings.class, "value", "test");
        spoon.test.annotation.AnnotationValuesTest.Request.on(target).giveMeAnnotation(java.lang.SuppressWarnings.class);
        spoon.test.annotation.AnnotationValuesTest.Request.on(annotation).giveMeAnnotationValue("value").isTypedBy(spoon.reflect.code.CtLiteral.class);
        annotation = factory.Annotation().annotate(target, java.lang.SuppressWarnings.class, "value", "test2");
        spoon.test.annotation.AnnotationValuesTest.Request.on(annotation).giveMeAnnotationValue("value").isTypedBy(spoon.reflect.code.CtNewArray.class);
    }

    @org.junit.Test
    public void testAnnotateWithEnum() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        final spoon.reflect.declaration.CtClass<java.lang.Object> target = factory.Class().create("org.example.Tacos");
        final spoon.reflect.declaration.CtField<java.lang.String> field = factory.Field().create(target, new java.util.HashSet<>(), factory.Type().STRING, "field");
        target.addField(field);
        final spoon.reflect.declaration.CtAnnotation<spoon.test.annotation.testclasses.BoundNumber> byteOrder = factory.Annotation().annotate(field, spoon.test.annotation.testclasses.BoundNumber.class, "byteOrder", spoon.test.annotation.testclasses.BoundNumber.ByteOrder.LittleEndian);
        org.junit.Assert.assertEquals(byteOrder, spoon.test.annotation.AnnotationValuesTest.Request.on(field).giveMeAnnotation(spoon.test.annotation.testclasses.BoundNumber.class));
        org.junit.Assert.assertTrue(((spoon.test.annotation.AnnotationValuesTest.Request.on(byteOrder).giveMeAnnotationValue("byteOrder").element) instanceof spoon.reflect.code.CtFieldRead));
    }

    @org.junit.Test
    public void testAnnotationPrintAnnotation() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("src/test/resources/printer-test/spoon/test/AnnotationSpecTest.java");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.buildModel();
        org.junit.Assert.assertEquals(spoon.test.annotation.AnnotationValuesTest.strCtClassOracle, launcher.getFactory().Class().getAll().get(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtClass.class)).get(2).toString());
    }

    private static final java.lang.String nl = java.lang.System.lineSeparator();

    private static final java.lang.String strCtClassOracle = (("@com.squareup.javapoet.AnnotationSpecTest.HasDefaultsAnnotation(o = com.squareup.javapoet.AnnotationSpecTest.Breakfast.PANCAKES, p = 1701, f = 11.1, m = { 9, 8, 1 }, l = java.lang.Override.class, j = @com.squareup.javapoet.AnnotationSpecTest.AnnotationA" + (", q = @com.squareup.javapoet.AnnotationSpecTest.AnnotationC(\"bar\")" + ", r = { java.lang.Float.class, java.lang.Double.class })")) + (spoon.test.annotation.AnnotationValuesTest.nl)) + "public class IsAnnotated {}";

    static class Request {
        private static spoon.test.annotation.AnnotationValuesTest.Request myself = new spoon.test.annotation.AnnotationValuesTest.Request();

        private static spoon.reflect.declaration.CtElement element;

        public static spoon.test.annotation.AnnotationValuesTest.Request on(spoon.reflect.declaration.CtElement ctElement) {
            org.junit.Assert.assertNotNull(ctElement);
            spoon.test.annotation.AnnotationValuesTest.Request.element = ctElement;
            return spoon.test.annotation.AnnotationValuesTest.Request.myself;
        }

        public <A extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> giveMeAnnotation(java.lang.Class<A> annotation) {
            for (spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> ctAnnotation : spoon.test.annotation.AnnotationValuesTest.Request.element.getAnnotations()) {
                if (ctAnnotation.getActualAnnotation().annotationType().equals(annotation)) {
                    return ctAnnotation;
                }
            }
            org.junit.Assert.fail("Annotation isn't present on the current element.");
            return null;
        }

        public spoon.test.annotation.AnnotationValuesTest.Request giveMeAnnotationValue(java.lang.String key) {
            org.junit.Assert.assertTrue("Element given in the method on should be an CtAnnotation.", ((spoon.test.annotation.AnnotationValuesTest.Request.element) instanceof spoon.reflect.declaration.CtAnnotation));
            spoon.reflect.declaration.CtAnnotation<?> ctAnnotation = ((spoon.reflect.declaration.CtAnnotation<?>) (spoon.test.annotation.AnnotationValuesTest.Request.element));
            spoon.reflect.code.CtExpression value = null;
            try {
                value = ctAnnotation.getValue(key);
            } catch (java.lang.ClassCastException e) {
                org.junit.Assert.fail("Value of the given key can't be cast to an expression.");
            }
            org.junit.Assert.assertNotNull(value);
            spoon.test.annotation.AnnotationValuesTest.Request.element = value;
            return spoon.test.annotation.AnnotationValuesTest.Request.myself;
        }

        public <T extends spoon.reflect.declaration.CtElement> spoon.test.annotation.AnnotationValuesTest.Request isTypedBy(java.lang.Class<T> expectedType) {
            try {
                expectedType.cast(spoon.test.annotation.AnnotationValuesTest.Request.element);
            } catch (java.lang.ClassCastException e) {
                org.junit.Assert.fail("The given element can't be cast by the given type.");
            }
            return spoon.test.annotation.AnnotationValuesTest.Request.myself;
        }

        public spoon.test.annotation.AnnotationValuesTest.Request isNotAnnotated() {
            org.junit.Assert.assertEquals(0, spoon.test.annotation.AnnotationValuesTest.Request.element.getAnnotations().size());
            return spoon.test.annotation.AnnotationValuesTest.Request.myself;
        }
    }
}

