package spoon.test.annotation;


import java.util.List;
import spoon.Launcher;
import spoon.processing.ProcessingManager;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.NamedElementFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.QueueProcessingManager;
import spoon.test.annotation.testclasses.TypeAnnotation;
import spoon.test.annotation.testclasses.notrepeatable.StringAnnot;
import spoon.test.annotation.testclasses.repeatable.Repeated;
import spoon.test.annotation.testclasses.repeatable.Tag;
import spoon.test.annotation.testclasses.repeatandarrays.RepeatedArrays;
import spoon.test.annotation.testclasses.repeatandarrays.TagArrays;
import spoon.test.annotation.testclasses.spring.AliasFor;


public class AnnotationTest {
    @org.junit.Test
    public void testAnnotationValueReflection() throws java.lang.Exception {
        Factory factory = new Launcher().getFactory();
        CtTypeReference reference = factory.createCtTypeReference(spoon.reflect.annotations.PropertyGetter.class);
        CtAnnotation annotation = factory.Interface().get(spoon.reflect.declaration.CtNamedElement.class).getMethod("getSimpleName").getAnnotation(reference);
        org.junit.Assert.assertEquals("The annotation must have a value", 1, annotation.getValues().size());
        org.junit.Assert.assertEquals("NAME", ((spoon.reflect.code.CtFieldRead) (annotation.getValue("role"))).getVariable().getSimpleName());
    }

    @org.junit.Test
    public void testModelBuildingAnnotationBound() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/Bound.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.Bound");
        org.junit.Assert.assertEquals("Bound", type.getSimpleName());
        org.junit.Assert.assertEquals(1, type.getAnnotations().size());
    }

    @org.junit.Test
    public void testWritingAnnotParamArray() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotParam.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.AnnotParam");
        org.junit.Assert.assertEquals("@java.lang.SuppressWarnings({ \"unused\", \"rawtypes\" })", type.getElements(new TypeFilter<>(CtAnnotation.class)).get(0).toString());
    }

    @org.junit.Test
    public void testModelBuildingAnnotationBoundUsage() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/Main.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.Main");
        org.junit.Assert.assertEquals("Main", type.getSimpleName());
        CtParameter<?> param = type.getElements(new TypeFilter<CtParameter<?>>(CtParameter.class)).get(0);
        org.junit.Assert.assertEquals("a", param.getSimpleName());
        List<CtAnnotation<? extends java.lang.annotation.Annotation>> annotations = param.getAnnotations();
        org.junit.Assert.assertEquals(1, annotations.size());
        CtAnnotation<?> a = annotations.get(0);
        spoon.test.annotation.testclasses.Bound actualAnnotation = ((spoon.test.annotation.testclasses.Bound) (a.getActualAnnotation()));
        org.junit.Assert.assertEquals(8, actualAnnotation.max());
    }

    @org.junit.Test
    public void testPersistenceProperty() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/PersistenceProperty.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.PersistenceProperty");
        org.junit.Assert.assertEquals("PersistenceProperty", type.getSimpleName());
        org.junit.Assert.assertEquals(2, type.getAnnotations().size());
        CtAnnotation<java.lang.annotation.Target> a1 = type.getAnnotation(type.getFactory().Type().createReference(java.lang.annotation.Target.class));
        org.junit.Assert.assertNotNull(a1);
        CtAnnotation<java.lang.annotation.Retention> a2 = type.getAnnotation(type.getFactory().Type().createReference(java.lang.annotation.Retention.class));
        org.junit.Assert.assertNotNull(a2);
        org.junit.Assert.assertTrue(a1.getValues().containsKey("value"));
        org.junit.Assert.assertTrue(a2.getValues().containsKey("value"));
    }

    @org.junit.Test
    public void testAnnotationParameterTypes() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/Main.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.Main");
        CtMethod<?> m1 = type.getElements(new NamedElementFilter<>(CtMethod.class, "m1")).get(0);
        List<CtAnnotation<? extends java.lang.annotation.Annotation>> annotations = m1.getAnnotations();
        org.junit.Assert.assertEquals(1, annotations.size());
        CtAnnotation<?> a = annotations.get(0);
        spoon.test.annotation.testclasses.AnnotParamTypes annot = ((spoon.test.annotation.testclasses.AnnotParamTypes) (a.getActualAnnotation()));
        org.junit.Assert.assertEquals(42, annot.integer());
        org.junit.Assert.assertEquals(1, annot.integers().length);
        org.junit.Assert.assertEquals(42, annot.integers()[0]);
        org.junit.Assert.assertEquals("Hello World!", annot.string());
        org.junit.Assert.assertEquals(2, annot.strings().length);
        org.junit.Assert.assertEquals("Hello", annot.strings()[0]);
        org.junit.Assert.assertEquals("World", annot.strings()[1]);
        org.junit.Assert.assertEquals(java.lang.Integer.class, annot.clazz());
        org.junit.Assert.assertEquals(2, annot.classes().length);
        org.junit.Assert.assertEquals(java.lang.Integer.class, annot.classes()[0]);
        org.junit.Assert.assertEquals(String.class, annot.classes()[1]);
        org.junit.Assert.assertEquals(true, annot.b());
        org.junit.Assert.assertEquals('c', annot.c());
        org.junit.Assert.assertEquals(42, annot.byt());
        org.junit.Assert.assertEquals(((short) (42)), annot.s());
        org.junit.Assert.assertEquals(42, annot.l());
        org.junit.Assert.assertEquals(3.14F, annot.f(), 0.0F);
        org.junit.Assert.assertEquals(3.14159, annot.d(), 0);
        org.junit.Assert.assertEquals(spoon.test.annotation.testclasses.AnnotParamTypeEnum.G, annot.e());
        org.junit.Assert.assertEquals("dd", annot.ia().value());
        CtMethod<?> m2 = type.getElements(new NamedElementFilter<>(CtMethod.class, "m2")).get(0);
        annotations = m2.getAnnotations();
        org.junit.Assert.assertEquals(1, annotations.size());
        a = annotations.get(0);
        annot = ((spoon.test.annotation.testclasses.AnnotParamTypes) (a.getActualAnnotation()));
        org.junit.Assert.assertEquals(42, annot.integer());
        org.junit.Assert.assertEquals(1, annot.integers().length);
        org.junit.Assert.assertEquals(42, annot.integers()[0]);
        org.junit.Assert.assertEquals("Hello World!", annot.string());
        org.junit.Assert.assertEquals(2, annot.strings().length);
        org.junit.Assert.assertEquals("Hello", annot.strings()[0]);
        org.junit.Assert.assertEquals("world", annot.strings()[1]);
        org.junit.Assert.assertEquals(false, annot.b());
        org.junit.Assert.assertEquals(42, annot.byt());
        org.junit.Assert.assertEquals(((short) (42)), annot.s());
        org.junit.Assert.assertEquals(42, annot.l());
        org.junit.Assert.assertEquals(3.14F, annot.f(), 0.0F);
        org.junit.Assert.assertEquals(3.14159, annot.d(), 0);
        org.junit.Assert.assertEquals(spoon.test.annotation.testclasses.AnnotParamTypeEnum.G, annot.e());
        org.junit.Assert.assertEquals("dd", annot.ia().value());
        CtMethod<?> m3 = type.getElements(new NamedElementFilter<>(CtMethod.class, "m3")).get(0);
        annotations = m3.getAnnotations();
        org.junit.Assert.assertEquals(1, annotations.size());
        a = annotations.get(0);
        annot = ((spoon.test.annotation.testclasses.AnnotParamTypes) (a.getActualAnnotation()));
        org.junit.Assert.assertEquals(45, annot.integer());
        org.junit.Assert.assertEquals(2, annot.integers().length);
        org.junit.Assert.assertEquals(40, annot.integers()[0]);
        org.junit.Assert.assertEquals((42 * 3), annot.integers()[1]);
        org.junit.Assert.assertEquals("Hello World!concatenated", annot.string());
        org.junit.Assert.assertEquals(2, annot.strings().length);
        org.junit.Assert.assertEquals("Helloconcatenated", annot.strings()[0]);
        org.junit.Assert.assertEquals("worldconcatenated", annot.strings()[1]);
        org.junit.Assert.assertEquals(true, annot.b());
        org.junit.Assert.assertEquals((42 ^ 1), annot.byt());
        org.junit.Assert.assertEquals((((short) (42)) / 2), annot.s());
        org.junit.Assert.assertEquals(43, annot.l());
        org.junit.Assert.assertEquals((3.14F * 2.0F), annot.f(), 0.0F);
        org.junit.Assert.assertEquals((3.14159 / 3.0), annot.d(), 0);
        org.junit.Assert.assertEquals(spoon.test.annotation.testclasses.AnnotParamTypeEnum.G, annot.e());
        org.junit.Assert.assertEquals("dddd", annot.ia().value());
    }

    @org.junit.Test
    public void testAnnotatedElementTypes() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        spoon.reflect.declaration.CtPackage pkg = factory.Package().get("spoon.test.annotation.testclasses");
        List<CtAnnotation<?>> annotations = pkg.getAnnotations();
        org.junit.Assert.assertEquals(2, annotations.size());
        org.junit.Assert.assertTrue(annotations.get(0).getAnnotatedElement().equals(pkg));
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.PACKAGE, annotations.get(0).getAnnotatedElementType());
        spoon.reflect.declaration.CtClass<?> clazz = pkg.getType("Main");
        org.junit.Assert.assertEquals(spoon.test.annotation.testclasses.Main.class, clazz.getActualClass());
        annotations = clazz.getAnnotations();
        org.junit.Assert.assertEquals(1, annotations.size());
        org.junit.Assert.assertTrue(annotations.get(0).getAnnotatedElement().equals(clazz));
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.TYPE, clazz.getAnnotations().get(0).getAnnotatedElementType());
        List<CtMethod<?>> methods = clazz.getMethodsByName("toString");
        org.junit.Assert.assertEquals(1, methods.size());
        CtMethod<?> method = methods.get(0);
        org.junit.Assert.assertEquals("toString", method.getSimpleName());
        annotations = method.getAnnotations();
        org.junit.Assert.assertEquals(1, annotations.size());
        org.junit.Assert.assertTrue(annotations.get(0).getAnnotatedElement().equals(method));
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.METHOD, annotations.get(0).getAnnotatedElementType());
        methods = clazz.getMethodsByName("m");
        org.junit.Assert.assertEquals(1, methods.size());
        method = methods.get(0);
        org.junit.Assert.assertEquals("m", method.getSimpleName());
        List<CtParameter<?>> parameters = method.getParameters();
        org.junit.Assert.assertEquals(1, parameters.size());
        CtParameter<?> parameter = parameters.get(0);
        annotations = parameter.getAnnotations();
        org.junit.Assert.assertEquals(1, annotations.size());
        org.junit.Assert.assertTrue(annotations.get(0).getAnnotatedElement().equals(parameter));
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.PARAMETER, annotations.get(0).getAnnotatedElementType());
        java.util.Set<? extends spoon.reflect.declaration.CtConstructor<?>> constructors = clazz.getConstructors();
        org.junit.Assert.assertEquals(1, constructors.size());
        spoon.reflect.declaration.CtConstructor<?> constructor = constructors.iterator().next();
        annotations = constructor.getAnnotations();
        org.junit.Assert.assertEquals(1, annotations.size());
        org.junit.Assert.assertTrue(annotations.get(0).getAnnotatedElement().equals(constructor));
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.CONSTRUCTOR, annotations.get(0).getAnnotatedElementType());
        methods = clazz.getMethodsByName("m1");
        org.junit.Assert.assertEquals(1, methods.size());
        method = methods.get(0);
        annotations = method.getAnnotations();
        org.junit.Assert.assertEquals(1, annotations.size());
        CtAnnotation<?> annotation = annotations.get(0);
        org.junit.Assert.assertTrue(annotations.get(0).getAnnotatedElement().equals(method));
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.METHOD, annotations.get(0).getAnnotatedElementType());
        java.lang.Object element = annotation.getValues().get("ia");
        org.junit.Assert.assertNotNull(element);
        org.junit.Assert.assertTrue((element instanceof CtAnnotation));
        org.junit.Assert.assertTrue(((CtAnnotation<?>) (element)).getAnnotatedElement().equals(annotation));
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.ANNOTATION_TYPE, ((CtAnnotation<?>) (element)).getAnnotatedElementType());
        spoon.reflect.declaration.CtEnum<?> enumeration = pkg.getType("AnnotParamTypeEnum");
        org.junit.Assert.assertEquals(spoon.test.annotation.testclasses.AnnotParamTypeEnum.class, enumeration.getActualClass());
        annotations = enumeration.getAnnotations();
        org.junit.Assert.assertEquals(1, annotations.size());
        org.junit.Assert.assertTrue(annotations.get(0).getAnnotatedElement().equals(enumeration));
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.TYPE, annotations.get(0).getAnnotatedElementType());
        List<spoon.reflect.declaration.CtEnumValue<?>> fields = enumeration.getEnumValues();
        org.junit.Assert.assertEquals(3, fields.size());
        annotations = fields.get(0).getAnnotations();
        org.junit.Assert.assertEquals(1, annotations.size());
        org.junit.Assert.assertTrue(annotations.get(0).getAnnotatedElement().equals(fields.get(0)));
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.FIELD, annotations.get(0).getAnnotatedElementType());
        spoon.reflect.declaration.CtInterface<?> ctInterface = pkg.getType("TestInterface");
        org.junit.Assert.assertEquals(spoon.test.annotation.testclasses.TestInterface.class, ctInterface.getActualClass());
        annotations = ctInterface.getAnnotations();
        org.junit.Assert.assertEquals(1, annotations.size());
        org.junit.Assert.assertTrue(annotations.get(0).getAnnotatedElement().equals(ctInterface));
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.TYPE, annotations.get(0).getAnnotatedElementType());
        spoon.reflect.declaration.CtAnnotationType<?> annotationType = pkg.getType("Bound");
        org.junit.Assert.assertEquals(spoon.test.annotation.testclasses.Bound.class, annotationType.getActualClass());
        org.junit.Assert.assertNull(annotationType.getSuperclass());
        org.junit.Assert.assertEquals(1, annotationType.getMethods().size());
        org.junit.Assert.assertEquals(0, annotationType.getSuperInterfaces().size());
        annotations = annotationType.getAnnotations();
        org.junit.Assert.assertEquals(1, annotations.size());
        org.junit.Assert.assertTrue(annotations.get(0).getAnnotatedElement().equals(annotationType));
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.ANNOTATION_TYPE, annotations.get(0).getAnnotatedElementType());
    }

    @org.junit.Test
    public void testAnnotationWithDefaultArrayValue() throws java.lang.Throwable {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotArrayInnerClass.java");
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotArray.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final String res = "java.lang.Class<?>[] value() default {  };";
        CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.AnnotArrayInnerClass");
        CtType<?> annotationInnerClass = type.getNestedType("Annotation");
        org.junit.Assert.assertEquals("Annotation", annotationInnerClass.getSimpleName());
        org.junit.Assert.assertEquals(1, annotationInnerClass.getAnnotations().size());
        org.junit.Assert.assertEquals(res, annotationInnerClass.getMethod("value").toString());
        CtType<?> annotation = factory.Type().get("spoon.test.annotation.testclasses.AnnotArray");
        org.junit.Assert.assertEquals("AnnotArray", annotation.getSimpleName());
        org.junit.Assert.assertEquals(1, annotation.getAnnotations().size());
        org.junit.Assert.assertEquals(res, annotation.getMethod("value").toString());
    }

    @org.junit.Test
    public void testInnerAnnotationsWithArray() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/Foo.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get("spoon.test.annotation.testclasses.Foo")));
        final CtMethod<?> testMethod = ctClass.getMethodsByName("test").get(0);
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> testMethodAnnotations = testMethod.getAnnotations();
        org.junit.Assert.assertEquals(1, testMethodAnnotations.size());
        final CtAnnotation<? extends java.lang.annotation.Annotation> firstAnnotation = testMethodAnnotations.get(0);
        org.junit.Assert.assertEquals(spoon.test.annotation.testclasses.Foo.OuterAnnotation.class, spoon.test.annotation.AnnotationTest.getActualClassFromAnnotation(firstAnnotation));
        final spoon.reflect.code.CtNewArray<?> arrayAnnotations = ((spoon.reflect.code.CtNewArray<?>) (firstAnnotation.getValues().get("value")));
        org.junit.Assert.assertEquals(2, arrayAnnotations.getElements().size());
        final CtAnnotation<?> firstAnnotationInArray = getMiddleAnnotation(arrayAnnotations, 0);
        org.junit.Assert.assertEquals(spoon.test.annotation.testclasses.Foo.MiddleAnnotation.class, spoon.test.annotation.AnnotationTest.getActualClassFromAnnotation(firstAnnotationInArray));
        final CtAnnotation<?> secondAnnotationInArray = getMiddleAnnotation(arrayAnnotations, 1);
        org.junit.Assert.assertEquals(spoon.test.annotation.testclasses.Foo.MiddleAnnotation.class, spoon.test.annotation.AnnotationTest.getActualClassFromAnnotation(secondAnnotationInArray));
        final CtAnnotation<?> innerAnnotationInFirstMiddleAnnotation = getInnerAnnotation(firstAnnotationInArray);
        org.junit.Assert.assertEquals(spoon.test.annotation.testclasses.Foo.InnerAnnotation.class, spoon.test.annotation.AnnotationTest.getActualClassFromAnnotation(innerAnnotationInFirstMiddleAnnotation));
        org.junit.Assert.assertEquals("hello", getLiteralValueInAnnotation(innerAnnotationInFirstMiddleAnnotation).getValue());
        final CtAnnotation<?> innerAnnotationInSecondMiddleAnnotation = getInnerAnnotation(secondAnnotationInArray);
        org.junit.Assert.assertEquals(spoon.test.annotation.testclasses.Foo.InnerAnnotation.class, spoon.test.annotation.AnnotationTest.getActualClassFromAnnotation(innerAnnotationInSecondMiddleAnnotation));
        org.junit.Assert.assertEquals("hello again", getLiteralValueInAnnotation(innerAnnotationInSecondMiddleAnnotation).getValue());
    }

    @org.junit.Test
    public void testAccessAnnotationValue() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/Main.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get("spoon.test.annotation.testclasses.Main")));
        CtMethod<?> testMethod = ctClass.getMethodsByName("testValueWithArray").get(0);
        java.lang.Class<?>[] value = testMethod.getAnnotation(spoon.test.annotation.testclasses.AnnotArray.class).value();
        org.junit.Assert.assertArrayEquals(new java.lang.Class[]{ java.lang.RuntimeException.class }, value);
        testMethod = ctClass.getMethodsByName("testValueWithoutArray").get(0);
        value = testMethod.getAnnotation(spoon.test.annotation.testclasses.AnnotArray.class).value();
        org.junit.Assert.assertArrayEquals(new java.lang.Class[]{ java.lang.RuntimeException.class }, value);
    }

    @org.junit.Test
    public void testUsageOfTypeAnnotationInNewInstance() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsAppliedOnAnyTypeInAClass.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get("spoon.test.annotation.testclasses.AnnotationsAppliedOnAnyTypeInAClass")));
        final spoon.reflect.code.CtConstructorCall<?> ctConstructorCall = ctClass.getElements(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtConstructorCall<?>>(spoon.reflect.code.CtConstructorCall.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtConstructorCall<?> element) {
                return "String".equals(element.getType().getSimpleName());
            }
        }).get(0);
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> typeAnnotations = ctConstructorCall.getType().getAnnotations();
        org.junit.Assert.assertEquals("Type of the new class must use an annotation", 1, typeAnnotations.size());
        org.junit.Assert.assertEquals("Type of the new class is typed by TypeAnnotation", TypeAnnotation.class, typeAnnotations.get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.TYPE_USE, typeAnnotations.get(0).getAnnotatedElementType());
        org.junit.Assert.assertEquals("New class with an type annotation must be well printed", (("new java.lang.@spoon.test.annotation.testclasses.TypeAnnotation" + (java.lang.System.lineSeparator())) + "String()"), ctConstructorCall.toString());
    }

    @org.junit.Test
    public void testUsageOfTypeAnnotationInCast() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsAppliedOnAnyTypeInAClass.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get("spoon.test.annotation.testclasses.AnnotationsAppliedOnAnyTypeInAClass")));
        final spoon.reflect.code.CtReturn<?> returns = ctClass.getElements(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtReturn<?>>(spoon.reflect.code.CtReturn.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtReturn<?> element) {
                return !(element.getReturnedExpression().getTypeCasts().isEmpty());
            }
        }).get(0);
        final spoon.reflect.code.CtExpression<?> returnedExpression = returns.getReturnedExpression();
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> typeAnnotations = returnedExpression.getTypeCasts().get(0).getAnnotations();
        org.junit.Assert.assertEquals("Cast with a type annotation must have it in its model", 1, typeAnnotations.size());
        org.junit.Assert.assertEquals("Type annotation in the cast must be typed by TypeAnnotation", TypeAnnotation.class, typeAnnotations.get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.TYPE_USE, typeAnnotations.get(0).getAnnotatedElementType());
        org.junit.Assert.assertEquals("Cast with an type annotation must be well printed", (("((java.lang.@spoon.test.annotation.testclasses.TypeAnnotation" + (java.lang.System.lineSeparator())) + "String) (s))"), returnedExpression.toString());
    }

    @org.junit.Test
    public void testUsageOfTypeAnnotationBeforeExceptionInSignatureOfMethod() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsAppliedOnAnyTypeInAClass.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get("spoon.test.annotation.testclasses.AnnotationsAppliedOnAnyTypeInAClass")));
        final CtMethod<?> method = ctClass.getMethodsByName("m").get(0);
        final CtTypeReference<?> thrownReference = method.getThrownTypes().toArray(new CtTypeReference<?>[0])[0];
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> typeAnnotations = thrownReference.getAnnotations();
        org.junit.Assert.assertEquals("Thrown type with a type annotation must have it in its model", 1, typeAnnotations.size());
        org.junit.Assert.assertEquals("Type annotation with the thrown type must be typed by TypeAnnotation", TypeAnnotation.class, typeAnnotations.get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.TYPE_USE, typeAnnotations.get(0).getAnnotatedElementType());
        org.junit.Assert.assertEquals("Thrown type with an type annotation must be well printed", (((("public void m() throws java.lang.@spoon.test.annotation.testclasses.TypeAnnotation" + (java.lang.System.lineSeparator())) + "Exception {") + (java.lang.System.lineSeparator())) + "}"), method.toString());
    }

    @org.junit.Test
    public void testUsageOfTypeAnnotationInReturnTypeInMethod() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsAppliedOnAnyTypeInAClass.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get("spoon.test.annotation.testclasses.AnnotationsAppliedOnAnyTypeInAClass")));
        final CtMethod<?> method = ctClass.getMethodsByName("m3").get(0);
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> typeAnnotations = method.getType().getAnnotations();
        org.junit.Assert.assertEquals("Return type with a type annotation must have it in its model", 1, typeAnnotations.size());
        org.junit.Assert.assertEquals("Type annotation with the return type must be typed by TypeAnnotation", TypeAnnotation.class, typeAnnotations.get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.TYPE_USE, typeAnnotations.get(0).getAnnotatedElementType());
        org.junit.Assert.assertEquals("Return type with an type annotation must be well printed", (((((("public java.lang.@spoon.test.annotation.testclasses.TypeAnnotation" + (java.lang.System.lineSeparator())) + "String m3() {") + (java.lang.System.lineSeparator())) + "    return \"\";") + (java.lang.System.lineSeparator())) + "}"), method.toString());
    }

    @org.junit.Test
    public void testUsageOfTypeAnnotationOnParameterInMethod() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsAppliedOnAnyTypeInAClass.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.annotation.testclasses.AnnotationsAppliedOnAnyTypeInAClass.class)));
        final CtMethod<?> method = ctClass.getMethodsByName("m6").get(0);
        final CtParameter<?> ctParameter = method.getParameters().get(0);
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> typeAnnotations = ctParameter.getType().getAnnotations();
        org.junit.Assert.assertEquals("Parameter type with a type annotation must have it in its model", 1, typeAnnotations.size());
        org.junit.Assert.assertEquals("Type annotation with the parameter type must be typed by TypeAnnotation", TypeAnnotation.class, typeAnnotations.get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.TYPE_USE, typeAnnotations.get(0).getAnnotatedElementType());
        org.junit.Assert.assertEquals("Parameter type with an type annotation must be well printed", (("java.lang.@spoon.test.annotation.testclasses.TypeAnnotation" + (java.lang.System.lineSeparator())) + "String param"), ctParameter.toString());
    }

    @org.junit.Test
    public void testUsageOfTypeAnnotationOnLocalVariableInMethod() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsAppliedOnAnyTypeInAClass.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.annotation.testclasses.AnnotationsAppliedOnAnyTypeInAClass.class)));
        final CtMethod<?> method = ctClass.getMethodsByName("m6").get(0);
        final spoon.reflect.code.CtLocalVariable<?> ctLocalVariable = method.getBody().getElements(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtLocalVariable<?>>(spoon.reflect.code.CtLocalVariable.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtLocalVariable<?> element) {
                return true;
            }
        }).get(0);
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> typeAnnotations = ctLocalVariable.getType().getAnnotations();
        org.junit.Assert.assertEquals("Local variable type with a type annotation must have it in its model", 1, typeAnnotations.size());
        org.junit.Assert.assertEquals("Type annotation with the local variable type must be typed by TypeAnnotation", TypeAnnotation.class, typeAnnotations.get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.TYPE_USE, typeAnnotations.get(0).getAnnotatedElementType());
        org.junit.Assert.assertEquals("Local variable type with an type annotation must be well printed", (("java.lang.@spoon.test.annotation.testclasses.TypeAnnotation" + (java.lang.System.lineSeparator())) + "String s = \"\""), ctLocalVariable.toString());
    }

    @org.junit.Test
    public void testUsageOfTypeAnnotationInExtendsImplementsOfAClass() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsAppliedOnAnyTypeInAClass.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get("spoon.test.annotation.testclasses.AnnotationsAppliedOnAnyTypeInAClass")));
        final spoon.reflect.declaration.CtClass<?> innerClass = ctClass.getElements(new NamedElementFilter<>(spoon.reflect.declaration.CtClass.class, "DummyClass")).get(0);
        final CtTypeReference<?> extendsActual = innerClass.getSuperclass();
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> extendsTypeAnnotations = extendsActual.getAnnotations();
        final String superClassExpected = ("spoon.test.annotation.testclasses.@spoon.test.annotation.testclasses.TypeAnnotation" + (java.lang.System.lineSeparator())) + "AnnotArrayInnerClass";
        org.junit.Assert.assertEquals("Extends with a type annotation must have it in its model", 1, extendsTypeAnnotations.size());
        org.junit.Assert.assertEquals("Type annotation on a extends must be typed by TypeAnnotation", TypeAnnotation.class, extendsTypeAnnotations.get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.TYPE_USE, extendsTypeAnnotations.get(0).getAnnotatedElementType());
        org.junit.Assert.assertEquals("Extends with an type annotation must be well printed", superClassExpected, extendsActual.toString());
        final java.util.Set<CtTypeReference<?>> superInterfaces = innerClass.getSuperInterfaces();
        final CtTypeReference<?> firstSuperInterface = superInterfaces.toArray(new CtTypeReference<?>[0])[0];
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> implementsTypeAnnotations = firstSuperInterface.getAnnotations();
        final String superInterfaceExpected = ("spoon.test.annotation.testclasses.@spoon.test.annotation.testclasses.TypeAnnotation" + (java.lang.System.lineSeparator())) + "BasicAnnotation";
        org.junit.Assert.assertEquals("Implements with a type annotation must have it in its model", 1, implementsTypeAnnotations.size());
        org.junit.Assert.assertEquals("Type annotation on a extends must be typed by TypeAnnotation", TypeAnnotation.class, implementsTypeAnnotations.get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.TYPE_USE, implementsTypeAnnotations.get(0).getAnnotatedElementType());
        org.junit.Assert.assertEquals("Extends with an type annotation must be well printed", superInterfaceExpected, firstSuperInterface.toString());
        final spoon.reflect.declaration.CtEnum<?> enumActual = ctClass.getElements(new NamedElementFilter<>(spoon.reflect.declaration.CtEnum.class, "DummyEnum")).get(0);
        final java.util.Set<CtTypeReference<?>> superInterfacesOfEnum = enumActual.getSuperInterfaces();
        final CtTypeReference<?> firstSuperInterfaceOfEnum = superInterfacesOfEnum.toArray(new CtTypeReference<?>[0])[0];
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> enumTypeAnnotations = firstSuperInterfaceOfEnum.getAnnotations();
        final String enumExpected = ((((("public enum DummyEnum implements spoon.test.annotation.testclasses.@spoon.test.annotation.testclasses.TypeAnnotation" + (java.lang.System.lineSeparator())) + "BasicAnnotation {") + (java.lang.System.lineSeparator())) + "    ;") + (java.lang.System.lineSeparator())) + "}";
        org.junit.Assert.assertEquals("Implements in a enum with a type annotation must have it in its model", 1, enumTypeAnnotations.size());
        org.junit.Assert.assertEquals("Type annotation on a implements in a enum must be typed by TypeAnnotation", TypeAnnotation.class, enumTypeAnnotations.get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.TYPE_USE, enumTypeAnnotations.get(0).getAnnotatedElementType());
        org.junit.Assert.assertEquals("Implements in a enum with an type annotation must be well printed", enumExpected, enumActual.toString());
        final spoon.reflect.declaration.CtInterface<?> interfaceActual = ctClass.getElements(new NamedElementFilter<>(spoon.reflect.declaration.CtInterface.class, "DummyInterface")).get(0);
        final java.util.Set<CtTypeReference<?>> superInterfacesOfInterface = interfaceActual.getSuperInterfaces();
        final CtTypeReference<?> firstSuperInterfaceOfInterface = superInterfacesOfInterface.toArray(new CtTypeReference<?>[0])[0];
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> interfaceTypeAnnotations = firstSuperInterfaceOfInterface.getAnnotations();
        final String interfaceExpected = ("public interface DummyInterface extends spoon.test.annotation.testclasses.@spoon.test.annotation.testclasses.TypeAnnotation" + (java.lang.System.lineSeparator())) + "BasicAnnotation {}";
        org.junit.Assert.assertEquals("Implements in a interface with a type annotation must have it in its model", 1, interfaceTypeAnnotations.size());
        org.junit.Assert.assertEquals("Type annotation on a implements in a enum must be typed by TypeAnnotation", TypeAnnotation.class, interfaceTypeAnnotations.get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotatedElementType.TYPE_USE, interfaceTypeAnnotations.get(0).getAnnotatedElementType());
        org.junit.Assert.assertEquals("Implements in a interface with an type annotation must be well printed", interfaceExpected, interfaceActual.toString());
    }

    @org.junit.Test
    public void testUsageOfTypeAnnotationWithGenericTypesInClassDeclaration() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsAppliedOnAnyTypeInAClass.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get("spoon.test.annotation.testclasses.AnnotationsAppliedOnAnyTypeInAClass")));
        final spoon.reflect.declaration.CtClass<?> genericClass = ctClass.getElements(new NamedElementFilter<>(spoon.reflect.declaration.CtClass.class, "DummyGenericClass")).get(0);
        final List<spoon.reflect.declaration.CtTypeParameter> typeParameters = genericClass.getFormalCtTypeParameters();
        org.junit.Assert.assertEquals("Generic class has 2 generics parameters.", 2, typeParameters.size());
        org.junit.Assert.assertEquals("First generic type must have type annotation", (("@spoon.test.annotation.testclasses.TypeAnnotation" + (java.lang.System.lineSeparator())) + "T"), typeParameters.get(0).toString());
        org.junit.Assert.assertEquals("Second generic type must have type annotation", (("@spoon.test.annotation.testclasses.TypeAnnotation" + (java.lang.System.lineSeparator())) + "K"), typeParameters.get(1).toString());
        final CtTypeReference<?> superInterface = genericClass.getSuperInterfaces().toArray(new CtTypeReference<?>[0])[0];
        final String expected = ("spoon.test.annotation.testclasses.BasicAnnotation<@spoon.test.annotation.testclasses.TypeAnnotation" + (java.lang.System.lineSeparator())) + "T>";
        org.junit.Assert.assertEquals("Super interface has a generic type with type annotation", expected, superInterface.toString());
    }

    @org.junit.Test
    public void testUsageOfTypeAnnotationWithGenericTypesInStatements() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsAppliedOnAnyTypeInAClass.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get("spoon.test.annotation.testclasses.AnnotationsAppliedOnAnyTypeInAClass")));
        final CtMethod<?> method = ctClass.getMethodsByName("m4").get(0);
        final List<spoon.reflect.declaration.CtTypeParameter> typeParameters = method.getFormalCtTypeParameters();
        org.junit.Assert.assertEquals("Method has 1 generic parameter", 1, typeParameters.size());
        org.junit.Assert.assertEquals("Method with an type annotation must be well printed", (("@spoon.test.annotation.testclasses.TypeAnnotation" + (java.lang.System.lineSeparator())) + "T"), typeParameters.get(0).toString());
        final spoon.reflect.code.CtBlock<?> body = method.getBody();
        final String expectedFirstStatement = ("java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation" + (java.lang.System.lineSeparator())) + "T> list = new java.util.ArrayList<>()";
        final spoon.reflect.code.CtStatement firstStatement = body.getStatement(0);
        org.junit.Assert.assertEquals("Type annotation on generic parameter declared in the method", expectedFirstStatement, firstStatement.toString());
        final spoon.reflect.code.CtConstructorCall firstConstructorCall = firstStatement.getElements(new TypeFilter<spoon.reflect.code.CtConstructorCall>(spoon.reflect.code.CtConstructorCall.class)).get(0);
        final CtTypeReference<?> firstTypeReference = firstConstructorCall.getType().getActualTypeArguments().get(0);
        org.junit.Assert.assertTrue(firstTypeReference.isImplicit());
        org.junit.Assert.assertEquals("T", firstTypeReference.getSimpleName());
        final String expectedSecondStatement = ("java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation" + (java.lang.System.lineSeparator())) + "?> list2 = new java.util.ArrayList<>()";
        final spoon.reflect.code.CtStatement secondStatement = body.getStatement(1);
        org.junit.Assert.assertEquals("Wildcard with an type annotation must be well printed", expectedSecondStatement, secondStatement.toString());
        final spoon.reflect.code.CtConstructorCall secondConstructorCall = secondStatement.getElements(new TypeFilter<spoon.reflect.code.CtConstructorCall>(spoon.reflect.code.CtConstructorCall.class)).get(0);
        final CtTypeReference<?> secondTypeReference = secondConstructorCall.getType().getActualTypeArguments().get(0);
        org.junit.Assert.assertTrue(secondTypeReference.isImplicit());
        org.junit.Assert.assertEquals("Object", secondTypeReference.getSimpleName());
        final String expectedThirdStatement = ((("java.util.List<spoon.test.annotation.testclasses.@spoon.test.annotation.testclasses.TypeAnnotation" + (java.lang.System.lineSeparator())) + "BasicAnnotation> list3 = new java.util.ArrayList<spoon.test.annotation.testclasses.@spoon.test.annotation.testclasses.TypeAnnotation") + (java.lang.System.lineSeparator())) + "BasicAnnotation>()";
        org.junit.Assert.assertEquals("Type in generic parameter with an type annotation must be well printed", expectedThirdStatement, body.getStatement(2).toString());
    }

    @org.junit.Test
    public void testUsageOfParametersInTypeAnnotation() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsAppliedOnAnyTypeInAClass.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get("spoon.test.annotation.testclasses.AnnotationsAppliedOnAnyTypeInAClass")));
        final CtMethod<?> method = ctClass.getMethodsByName("m5").get(0);
        final String integerParam = ("java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(integer = 1)" + (java.lang.System.lineSeparator())) + "T> list";
        org.junit.Assert.assertEquals("integer parameter in type annotation", integerParam, method.getBody().getStatement(0).toString());
        final String arrayIntegerParam = ("java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(integers = { 1 })" + (java.lang.System.lineSeparator())) + "T> list2";
        org.junit.Assert.assertEquals("array of integers parameter in type annotation", arrayIntegerParam, method.getBody().getStatement(1).toString());
        final String stringParam = ("java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(string = \"\")" + (java.lang.System.lineSeparator())) + "T> list3";
        org.junit.Assert.assertEquals("string parameter in type annotation", stringParam, method.getBody().getStatement(2).toString());
        final String arrayStringParam = ("java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(strings = { \"\" })" + (java.lang.System.lineSeparator())) + "T> list4";
        org.junit.Assert.assertEquals("array of strings parameter in type annotation", arrayStringParam, method.getBody().getStatement(3).toString());
        final String classParam = ("java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(clazz = java.lang.String.class)" + (java.lang.System.lineSeparator())) + "T> list5";
        org.junit.Assert.assertEquals("class parameter in type annotation", classParam, method.getBody().getStatement(4).toString());
        final String arrayClassParam = ("java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(classes = { java.lang.String.class })" + (java.lang.System.lineSeparator())) + "T> list6";
        org.junit.Assert.assertEquals("array of classes parameter in type annotation", arrayClassParam, method.getBody().getStatement(5).toString());
        final String primitiveParam = ("java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(b = true)" + (java.lang.System.lineSeparator())) + "T> list7";
        org.junit.Assert.assertEquals("primitive parameter in type annotation", primitiveParam, method.getBody().getStatement(6).toString());
        final String enumParam = ("java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(e = spoon.test.annotation.testclasses.AnnotParamTypeEnum.R)" + (java.lang.System.lineSeparator())) + "T> list8";
        org.junit.Assert.assertEquals("enum parameter in type annotation", enumParam, method.getBody().getStatement(7).toString());
        final String annotationParam = ("java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(ia = @spoon.test.annotation.testclasses.InnerAnnot(\"\"))" + (java.lang.System.lineSeparator())) + "T> list9";
        org.junit.Assert.assertEquals("annotation parameter in type annotation", annotationParam, method.getBody().getStatement(8).toString());
        final String arrayAnnotationParam = ("java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(ias = { @spoon.test.annotation.testclasses.InnerAnnot(\"\") })" + (java.lang.System.lineSeparator())) + "T> list10";
        org.junit.Assert.assertEquals("array of annotations parameter in type annotation", arrayAnnotationParam, method.getBody().getStatement(9).toString());
        final String complexArrayParam = ("java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(inceptions = { @spoon.test.annotation.testclasses.Inception(value = @spoon.test.annotation.testclasses.InnerAnnot(\"\"), values = { @spoon.test.annotation.testclasses.InnerAnnot(\"\") }) })" + (java.lang.System.lineSeparator())) + "T> list11";
        org.junit.Assert.assertEquals("array of complexes parameters in type annotation", complexArrayParam, method.getBody().getStatement(10).toString());
    }

    @org.junit.Test
    public void testOutputGeneratedByTypeAnnotation() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsAppliedOnAnyTypeInAClass.java");
        launcher.buildModel();
        launcher.setSourceOutputDirectory(new java.io.File("./target/spooned-annotation-output/"));
        launcher.getModelBuilder().generateProcessedSourceFiles(spoon.OutputType.CLASSES);
        spoon.testing.utils.ModelUtils.canBeBuilt(new java.io.File("./target/spooned-annotation-output/spoon/test/annotation/testclasses/"), 8);
    }

    @org.junit.Test
    public void testRepeatSameAnnotationOnClass() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsRepeated.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.annotation.testclasses.AnnotationsRepeated.class)));
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> annotations = ctClass.getAnnotations();
        org.junit.Assert.assertEquals("Class must to have multi annotation of the same type", 2, annotations.size());
        org.junit.Assert.assertEquals("Type of the first annotation is AnnotationRepeated", spoon.test.annotation.testclasses.AnnotationRepeated.class, annotations.get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals("Type of the second annotation is AnnotationRepeated", spoon.test.annotation.testclasses.AnnotationRepeated.class, annotations.get(1).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals("Argument of the first annotation is \"First\"", "First", ((spoon.reflect.code.CtLiteral) (annotations.get(0).getValue("value"))).getValue());
        org.junit.Assert.assertEquals("Argument of the second annotation is \"Second\"", "Second", ((spoon.reflect.code.CtLiteral) (annotations.get(1).getValue("value"))).getValue());
    }

    @org.junit.Test
    public void testRepeatSameAnnotationOnField() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsRepeated.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.annotation.testclasses.AnnotationsRepeated.class)));
        final CtField<?> field = ctClass.getField("field");
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> annotations = field.getAnnotations();
        org.junit.Assert.assertEquals("Field must to have multi annotation of the same type", 2, annotations.size());
        org.junit.Assert.assertEquals("Type of the first annotation is AnnotationRepeated", spoon.test.annotation.testclasses.AnnotationRepeated.class, annotations.get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals("Type of the second annotation is AnnotationRepeated", spoon.test.annotation.testclasses.AnnotationRepeated.class, annotations.get(1).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals("Argument of the first annotation is \"Field 1\"", "Field 1", ((spoon.reflect.code.CtLiteral) (annotations.get(0).getValue("value"))).getValue());
        org.junit.Assert.assertEquals("Argument of the second annotation is \"Field 2\"", "Field 2", ((spoon.reflect.code.CtLiteral) (annotations.get(1).getValue("value"))).getValue());
    }

    @org.junit.Test
    public void testRepeatSameAnnotationOnMethod() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsRepeated.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.annotation.testclasses.AnnotationsRepeated.class)));
        final CtMethod<?> method = ctClass.getMethodsByName("method").get(0);
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> annotations = method.getAnnotations();
        org.junit.Assert.assertEquals("Method must to have multi annotation of the same type", 2, annotations.size());
        org.junit.Assert.assertEquals("Type of the first annotation is AnnotationRepeated", spoon.test.annotation.testclasses.AnnotationRepeated.class, annotations.get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals("Type of the second annotation is AnnotationRepeated", spoon.test.annotation.testclasses.AnnotationRepeated.class, annotations.get(1).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals("Argument of the first annotation is \"Method 1\"", "Method 1", ((spoon.reflect.code.CtLiteral) (annotations.get(0).getValue("value"))).getValue());
        org.junit.Assert.assertEquals("Argument of the second annotation is \"Method 2\"", "Method 2", ((spoon.reflect.code.CtLiteral) (annotations.get(1).getValue("value"))).getValue());
    }

    @org.junit.Test
    public void testRepeatSameAnnotationOnConstructor() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsRepeated.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.annotation.testclasses.AnnotationsRepeated.class)));
        final spoon.reflect.declaration.CtConstructor<?> ctConstructor = ctClass.getConstructors().toArray(new spoon.reflect.declaration.CtConstructor<?>[0])[0];
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> annotations = ctConstructor.getAnnotations();
        org.junit.Assert.assertEquals("Constructor must to have multi annotation of the same type", 2, annotations.size());
        org.junit.Assert.assertEquals("Type of the first annotation is AnnotationRepeated", spoon.test.annotation.testclasses.AnnotationRepeated.class, annotations.get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals("Type of the second annotation is AnnotationRepeated", spoon.test.annotation.testclasses.AnnotationRepeated.class, annotations.get(1).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals("Argument of the first annotation is \"Constructor 1\"", "Constructor 1", ((spoon.reflect.code.CtLiteral) (annotations.get(0).getValue("value"))).getValue());
        org.junit.Assert.assertEquals("Argument of the second annotation is \"Constructor 2\"", "Constructor 2", ((spoon.reflect.code.CtLiteral) (annotations.get(1).getValue("value"))).getValue());
    }

    @org.junit.Test
    public void testRepeatSameAnnotationOnParameter() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsRepeated.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.annotation.testclasses.AnnotationsRepeated.class)));
        final CtMethod<?> method = ctClass.getMethodsByName("methodWithParameter").get(0);
        final CtParameter<?> ctParameter = method.getParameters().get(0);
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> annotations = ctParameter.getAnnotations();
        org.junit.Assert.assertEquals("Parameter must to have multi annotation of the same type", 2, annotations.size());
        org.junit.Assert.assertEquals("Type of the first annotation is AnnotationRepeated", spoon.test.annotation.testclasses.AnnotationRepeated.class, annotations.get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals("Type of the second annotation is AnnotationRepeated", spoon.test.annotation.testclasses.AnnotationRepeated.class, annotations.get(1).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals("Argument of the first annotation is \"Param 1\"", "Param 1", ((spoon.reflect.code.CtLiteral) (annotations.get(0).getValue("value"))).getValue());
        org.junit.Assert.assertEquals("Argument of the second annotation is \"Param 2\"", "Param 2", ((spoon.reflect.code.CtLiteral) (annotations.get(1).getValue("value"))).getValue());
    }

    @org.junit.Test
    public void testRepeatSameAnnotationOnLocalVariable() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsRepeated.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.annotation.testclasses.AnnotationsRepeated.class)));
        final CtMethod<?> method = ctClass.getMethodsByName("methodWithLocalVariable").get(0);
        final spoon.reflect.code.CtLocalVariable<?> ctLocalVariable = method.getBody().getElements(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtLocalVariable<?>>(spoon.reflect.code.CtLocalVariable.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtLocalVariable<?> element) {
                return true;
            }
        }).get(0);
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> annotations = ctLocalVariable.getAnnotations();
        org.junit.Assert.assertEquals("Local variable must to have multi annotation of the same type", 2, annotations.size());
        org.junit.Assert.assertEquals("Type of the first annotation is AnnotationRepeated", spoon.test.annotation.testclasses.AnnotationRepeated.class, annotations.get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals("Type of the second annotation is AnnotationRepeated", spoon.test.annotation.testclasses.AnnotationRepeated.class, annotations.get(1).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals("Argument of the first annotation is \"Local 1\"", "Local 1", ((spoon.reflect.code.CtLiteral) (annotations.get(0).getValue("value"))).getValue());
        org.junit.Assert.assertEquals("Argument of the second annotation is \"Local 2\"", "Local 2", ((spoon.reflect.code.CtLiteral) (annotations.get(1).getValue("value"))).getValue());
    }

    @org.junit.Test
    public void testRepeatSameAnnotationOnPackage() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsRepeated.java");
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/package-info.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtPackage pkg = factory.Package().get("spoon.test.annotation.testclasses");
        final List<CtAnnotation<? extends java.lang.annotation.Annotation>> annotations = pkg.getAnnotations();
        org.junit.Assert.assertEquals("Local variable must to have multi annotation of the same type", 2, annotations.size());
        org.junit.Assert.assertEquals("Type of the first annotation is AnnotationRepeated", spoon.test.annotation.testclasses.AnnotationRepeated.class, annotations.get(0).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals("Type of the second annotation is AnnotationRepeated", spoon.test.annotation.testclasses.AnnotationRepeated.class, annotations.get(1).getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals("Argument of the first annotation is \"Package 1\"", "Package 1", ((spoon.reflect.code.CtLiteral) (annotations.get(0).getValue("value"))).getValue());
        org.junit.Assert.assertEquals("Argument of the second annotation is \"Package 2\"", "Package 2", ((spoon.reflect.code.CtLiteral) (annotations.get(1).getValue("value"))).getValue());
    }

    @org.junit.Test
    public void testDefaultValueInAnnotationsForAnnotationFields() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationDefaultAnnotation.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final CtType<?> annotation = factory.Type().get(spoon.test.annotation.testclasses.AnnotationDefaultAnnotation.class);
        final spoon.reflect.declaration.CtAnnotationMethod<?> ctAnnotations = annotation.getMethods().toArray(new spoon.reflect.declaration.CtAnnotationMethod<?>[0])[0];
        org.junit.Assert.assertEquals("Field is typed by an annotation.", spoon.test.annotation.testclasses.InnerAnnot.class, ctAnnotations.getType().getActualClass());
        org.junit.Assert.assertEquals("Default value of a field typed by an annotation must be an annotation", spoon.test.annotation.testclasses.InnerAnnot.class, ctAnnotations.getDefaultExpression().getType().getActualClass());
    }

    @org.junit.Test
    public void testGetAnnotationOuter() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/Foo.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> ctClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get("spoon.test.annotation.testclasses.Foo")));
        final CtMethod<?> testMethod = ctClass.getMethodsByName("test").get(0);
        spoon.test.annotation.testclasses.Foo.OuterAnnotation annot = testMethod.getAnnotation(spoon.test.annotation.testclasses.Foo.OuterAnnotation.class);
        org.junit.Assert.assertNotNull(annot);
        org.junit.Assert.assertEquals(2, annot.value().length);
    }

    @org.junit.Test
    public void testAbstractAllAnnotationProcessor() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationsAppliedOnAnyTypeInAClass.java");
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/BasicAnnotation.java");
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/TypeAnnotation.java");
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotParamTypeEnum.java");
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/InnerAnnot.java");
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/Inception.java");
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/TestAnnotation.java");
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotArrayInnerClass.java");
        Factory factory = spoon.getFactory();
        spoon.buildModel();
        final ProcessingManager p = new QueueProcessingManager(factory);
        final spoon.test.annotation.AnnotationTest.TypeAnnotationProcessor processor = new spoon.test.annotation.AnnotationTest.TypeAnnotationProcessor();
        p.addProcessor(processor);
        p.process(factory.Class().getAll());
        org.junit.Assert.assertEquals(29, processor.elements.size());
    }

    @org.junit.Test
    public void testAbstractAllAnnotationProcessorWithGlobalAnnotation() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/ClassProcessed.java");
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/TypeAnnotation.java");
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotParamTypeEnum.java");
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/InnerAnnot.java");
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/Inception.java");
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/GlobalAnnotation.java");
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/TestAnnotation.java");
        Factory factory = spoon.getFactory();
        spoon.buildModel();
        final ProcessingManager p = new QueueProcessingManager(factory);
        final spoon.test.annotation.AnnotationTest.GlobalProcessor processor = new spoon.test.annotation.AnnotationTest.GlobalProcessor();
        p.addProcessor(processor);
        final spoon.test.annotation.AnnotationTest.TypeAnnotationMethodProcessor methodProcessor = new spoon.test.annotation.AnnotationTest.TypeAnnotationMethodProcessor();
        p.addProcessor(methodProcessor);
        p.process(factory.Class().getAll());
        org.junit.Assert.assertEquals(7, processor.elements.size());
        org.junit.Assert.assertEquals(2, methodProcessor.elements.size());
    }

    @org.junit.Test
    public void testAnnotationIntrospection() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/AnnotationIntrospection.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        spoon.reflect.declaration.CtClass<java.lang.Object> aClass = factory.Class().get(spoon.test.annotation.testclasses.AnnotationIntrospection.class);
        CtMethod<?> mMethod = aClass.getMethod("m");
        spoon.reflect.code.CtStatement statement = mMethod.getBody().getStatement(1);
        org.junit.Assert.assertEquals("annotation.equals(null)", statement.toString());
    }

    @org.junit.Test
    public void testFieldAndMethodInAnnotation() throws java.lang.Exception {
        final CtType<spoon.test.annotation.testclasses.SuperAnnotation> aTypeAnnotation = spoon.testing.utils.ModelUtils.buildClass(spoon.test.annotation.testclasses.SuperAnnotation.class);
        final CtField<?> fieldValue = aTypeAnnotation.getField("value");
        org.junit.Assert.assertNotNull(fieldValue);
        org.junit.Assert.assertEquals("java.lang.String value = \"\";", fieldValue.toString());
        final CtMethod<java.lang.Object> methodValue = aTypeAnnotation.getMethod("value");
        org.junit.Assert.assertTrue((methodValue instanceof spoon.reflect.declaration.CtAnnotationMethod));
        org.junit.Assert.assertEquals("java.lang.String value() default spoon.test.annotation.testclasses.SuperAnnotation.value;", methodValue.toString());
        final CtMethod<java.lang.Object> methodNoDefault = aTypeAnnotation.getMethod("value1");
        org.junit.Assert.assertTrue((methodNoDefault instanceof spoon.reflect.declaration.CtAnnotationMethod));
        org.junit.Assert.assertEquals("java.lang.String value1();", methodNoDefault.toString());
        org.junit.Assert.assertEquals(2, aTypeAnnotation.getMethods().size());
        aTypeAnnotation.addMethod(methodValue.clone());
        org.junit.Assert.assertEquals(2, aTypeAnnotation.getMethods().size());
    }

    @org.junit.Test
    public void testAnnotationInterfacePreserveMethods() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/PortRange.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtAnnotationType<?> ctAnnotationType = ((spoon.reflect.declaration.CtAnnotationType) (factory.Type().get(spoon.test.annotation.testclasses.PortRange.class)));
        List<CtMethod<?>> ctMethodMin = ctAnnotationType.getMethodsByName("min");
        org.junit.Assert.assertEquals("Method min is preserved after transformation", 1, ctMethodMin.size());
        List<CtMethod<?>> ctMethodMax = ctAnnotationType.getMethodsByName("max");
        org.junit.Assert.assertEquals("Method max is preserved after transformation", 1, ctMethodMax.size());
        List<CtMethod<?>> ctMethodMessage = ctAnnotationType.getMethodsByName("message");
        org.junit.Assert.assertEquals("Method message is preserved after transformation", 1, ctMethodMessage.size());
        List<CtMethod<?>> ctMethodGroups = ctAnnotationType.getMethodsByName("groups");
        org.junit.Assert.assertEquals("Method groups is preserved after transformation", 1, ctMethodGroups.size());
        List<CtMethod<?>> ctMethodPayload = ctAnnotationType.getMethodsByName("payload");
        org.junit.Assert.assertEquals("Method payload is preserved after transformation", 1, ctMethodPayload.size());
    }

    abstract class AbstractElementsProcessor<A extends java.lang.annotation.Annotation, E extends spoon.reflect.declaration.CtElement> extends spoon.processing.AbstractAnnotationProcessor<A, E> {
        final List<spoon.reflect.declaration.CtElement> elements = new java.util.ArrayList<>();

        @java.lang.Override
        public void process(A annotation, E element) {
            elements.add(element);
        }
    }

    class GlobalProcessor extends spoon.test.annotation.AnnotationTest.AbstractElementsProcessor<spoon.test.annotation.testclasses.GlobalAnnotation, spoon.reflect.declaration.CtElement> {
        @java.lang.Override
        public void process(spoon.test.annotation.testclasses.GlobalAnnotation annotation, spoon.reflect.declaration.CtElement element) {
            super.process(annotation, element);
        }
    }

    class TypeAnnotationProcessor extends spoon.test.annotation.AnnotationTest.AbstractElementsProcessor<TypeAnnotation, spoon.reflect.declaration.CtElement> {
        @java.lang.Override
        public void process(TypeAnnotation annotation, spoon.reflect.declaration.CtElement element) {
            super.process(annotation, element);
        }
    }

    class TypeAnnotationMethodProcessor extends spoon.test.annotation.AnnotationTest.AbstractElementsProcessor<TypeAnnotation, CtTypeReference<?>> {
        @java.lang.Override
        public void process(TypeAnnotation annotation, CtTypeReference<?> element) {
            if ((element.getParent()) instanceof CtMethod) {
                super.process(annotation, element);
            }
        }
    }

    public static java.lang.Class<? extends java.lang.annotation.Annotation> getActualClassFromAnnotation(CtAnnotation<? extends java.lang.annotation.Annotation> annotation) {
        return annotation.getAnnotationType().getActualClass();
    }

    private spoon.reflect.code.CtLiteral<?> getLiteralValueInAnnotation(CtAnnotation<?> annotation) {
        return ((spoon.reflect.code.CtLiteral<?>) (annotation.getValues().get("value")));
    }

    private CtAnnotation<?> getInnerAnnotation(CtAnnotation<?> firstAnnotationInArray) {
        return ((CtAnnotation<?>) (firstAnnotationInArray.getValues().get("value")));
    }

    private CtAnnotation<?> getMiddleAnnotation(spoon.reflect.code.CtNewArray<?> arrayAnnotations, int index) {
        return ((CtAnnotation<?>) (arrayAnnotations.getElements().get(index)));
    }

    @org.junit.Test
    public void testSpoonSpoonResult() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/dropwizard/GraphiteReporterFactory.java");
        String output = ("target/spooned-" + (this.getClass().getSimpleName())) + "-firstspoon/";
        spoon.setSourceOutputDirectory(output);
        Factory factory = spoon.getFactory();
        spoon.run();
        Launcher spoon2 = new Launcher();
        spoon2.addInputResource((output + "/spoon/test/annotation/testclasses/dropwizard/GraphiteReporterFactory.java"));
        spoon2.buildModel();
        List<CtField<?>> fields = spoon2.getModel().getElements(new NamedElementFilter(CtField.class, "port"));
        org.junit.Assert.assertEquals("Number of fields port should be 1", 1, fields.size());
        CtField<?> getport = fields.get(0);
        CtTypeReference returnType = getport.getType();
        List<CtAnnotation<?>> annotations = returnType.getAnnotations();
        org.junit.Assert.assertEquals("Number of annotation for return type of method getPort should be 1", 1, annotations.size());
        CtAnnotation annotation = annotations.get(0);
        org.junit.Assert.assertEquals("Annotation should be @spoon.test.annotation.testclasses.PortRange", "spoon.test.annotation.testclasses.PortRange", annotation.getAnnotationType().getQualifiedName());
    }

    @org.junit.Test
    public void testGetAnnotationFromParameter() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("src/test/resources/noclasspath/Initializer.java");
        String output = ("target/spooned-" + (this.getClass().getSimpleName())) + "-firstspoon/";
        spoon.setSourceOutputDirectory(output);
        spoon.getEnvironment().setNoClasspath(true);
        Factory factory = spoon.getFactory();
        spoon.buildModel();
        List<CtMethod> methods = factory.getModel().getElements(new NamedElementFilter<>(CtMethod.class, "setField"));
        org.junit.Assert.assertThat(methods.size(), org.hamcrest.core.Is.is(1));
        CtMethod methodSet = methods.get(0);
        org.junit.Assert.assertThat(methodSet.getSimpleName(), org.hamcrest.core.Is.is("setField"));
        List<CtParameter> parameters = methodSet.getParameters();
        org.junit.Assert.assertThat(parameters.size(), org.hamcrest.core.Is.is(1));
        CtParameter thisParameter = parameters.get(0);
        org.junit.Assert.assertThat(thisParameter.getSimpleName(), org.hamcrest.core.Is.is("this"));
        CtTypeReference thisParamType = thisParameter.getType();
        org.junit.Assert.assertThat(thisParamType.getSimpleName(), org.hamcrest.core.Is.is("Initializer"));
        List<CtAnnotation<?>> annotations = thisParameter.getType().getAnnotations();
        org.junit.Assert.assertThat(annotations.size(), org.hamcrest.core.Is.is(2));
        CtAnnotation unknownInit = annotations.get(0);
        CtAnnotation raw = annotations.get(1);
        org.junit.Assert.assertThat(unknownInit.getAnnotationType().getSimpleName(), org.hamcrest.core.Is.is("UnknownInitialization"));
        org.junit.Assert.assertThat(raw.getAnnotationType().getSimpleName(), org.hamcrest.core.Is.is("Raw"));
    }

    @org.junit.Test
    public void annotationAddValue() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/Bar.java");
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        List<CtMethod> methods = factory.getModel().getElements(new NamedElementFilter<CtMethod>(CtMethod.class, "bidule"));
        org.junit.Assert.assertThat(methods.size(), org.hamcrest.core.Is.is(1));
        CtAnnotation anno1 = factory.Annotation().annotate(methods.get(0), TypeAnnotation.class).addValue("params", new String[]{ "test" });
        org.junit.Assert.assertThat(anno1.getValue("params").getType(), org.hamcrest.core.Is.is(factory.Type().createReference(String[].class)));
        CtAnnotation anno = factory.Annotation().annotate(methods.get(0), TypeAnnotation.class).addValue("params", new String[0]);
        org.junit.Assert.assertThat(anno.getValue("params").getType(), org.hamcrest.core.Is.is(factory.Type().createReference(String[].class)));
    }

    @org.junit.Test
    public void annotationOverrideFQNIsOK() {
        Launcher spoon = new Launcher();
        Factory factory = spoon.getFactory();
        factory.getEnvironment().setNoClasspath(true);
        spoon.addInputResource("./src/test/resources/noclasspath/annotation/issue1307/SpecIterator.java");
        spoon.buildModel();
        List<CtAnnotation> overrideAnnotations = factory.getModel().getElements(new TypeFilter<CtAnnotation>(CtAnnotation.class));
        for (CtAnnotation annotation : overrideAnnotations) {
            CtTypeReference typeRef = annotation.getAnnotationType();
            if (typeRef.getSimpleName().equals("Override")) {
                org.junit.Assert.assertThat(typeRef.getQualifiedName(), org.hamcrest.core.Is.is("java.lang.Override"));
            }
        }
    }

    @org.junit.Test
    public void testCreateAnnotation() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        Factory factory = launcher.getFactory();
        CtType<?> type = factory.Annotation().create("spoon.test.annotation.testclasses.NewAnnot");
        org.junit.Assert.assertTrue(type.isAnnotationType());
        org.junit.Assert.assertSame(type, type.getReference().getDeclaration());
    }

    @org.junit.Test
    public void testReplaceAnnotationValue() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/Main.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        CtType<?> type = factory.Type().get("spoon.test.annotation.testclasses.Main");
        CtMethod<?> m1 = type.getElements(new NamedElementFilter<>(CtMethod.class, "m1")).get(0);
        List<CtAnnotation<? extends java.lang.annotation.Annotation>> annotations = m1.getAnnotations();
        org.junit.Assert.assertEquals(1, annotations.size());
        CtAnnotation<?> a = annotations.get(0);
        spoon.test.annotation.testclasses.AnnotParamTypes annot = ((spoon.test.annotation.testclasses.AnnotParamTypes) (a.getActualAnnotation()));
        spoon.reflect.code.CtExpression integerValue = a.getValue("integer");
        org.junit.Assert.assertEquals(42, ((spoon.reflect.code.CtLiteral<java.lang.Integer>) (integerValue)).getValue().intValue());
        org.junit.Assert.assertEquals(42, annot.integer());
        integerValue.replace(factory.createLiteral(17));
        spoon.reflect.code.CtExpression newIntegerValue = a.getValue("integer");
        org.junit.Assert.assertEquals(17, ((spoon.reflect.code.CtLiteral<java.lang.Integer>) (newIntegerValue)).getValue().intValue());
        org.junit.Assert.assertEquals(17, annot.integer());
        try {
            a.getValue("integer").replace(java.util.Arrays.asList(factory.createLiteral(18), null));
            org.junit.Assert.fail();
        } catch (spoon.SpoonException e) {
        }
        a.getValue("integer").delete();
        org.junit.Assert.assertNull(a.getValue("integer"));
        try {
            annot.integer();
            org.junit.Assert.fail();
        } catch (java.lang.NullPointerException e) {
        }
        a.getValue("string").replace(((spoon.reflect.declaration.CtElement) (null)));
        org.junit.Assert.assertNull(a.getValue("string"));
        org.junit.Assert.assertNull(annot.string());
        a.getValue("clazz").replace(java.util.Collections.singletonList(null));
        org.junit.Assert.assertNull(a.getValue("clazz"));
        org.junit.Assert.assertNull(annot.clazz());
        org.junit.Assert.assertEquals(1, annot.integers().length);
        org.junit.Assert.assertEquals(42, annot.integers()[0]);
        spoon.reflect.code.CtNewArray<?> integersNewArray = ((spoon.reflect.code.CtNewArray) (a.getValue("integers")));
        integersNewArray.getElements().get(0).replace(java.util.Arrays.asList(null, factory.createLiteral(101), null, factory.createLiteral(102)));
        org.junit.Assert.assertEquals(2, annot.integers().length);
        org.junit.Assert.assertEquals(101, annot.integers()[0]);
        org.junit.Assert.assertEquals(102, annot.integers()[1]);
    }

    @org.junit.Test
    public void testSpoonManageRecursivelyDefinedAnnotation() {
        Launcher spoon = new Launcher();
        CtType type = spoon.getFactory().Type().get(AliasFor.class);
        org.junit.Assert.assertEquals(3, type.getFields().size());
    }

    @org.junit.Test
    public void testRepeatableAnnotationAreManaged() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/repeatable");
        spoon.buildModel();
        CtType type = spoon.getFactory().Type().get(Repeated.class);
        CtMethod firstMethod = ((CtMethod) (type.getMethodsByName("method").get(0)));
        List<CtAnnotation<?>> annotations = firstMethod.getAnnotations();
        org.junit.Assert.assertEquals(2, annotations.size());
        for (CtAnnotation a : annotations) {
            org.junit.Assert.assertEquals("Tag", a.getAnnotationType().getSimpleName());
        }
        String classContent = type.toString();
        org.junit.Assert.assertTrue(("Content of the file: " + classContent), classContent.contains("@spoon.test.annotation.testclasses.repeatable.Tag(\"machin\")"));
        org.junit.Assert.assertTrue(("Content of the file: " + classContent), classContent.contains("@spoon.test.annotation.testclasses.repeatable.Tag(\"truc\")"));
    }

    @org.junit.Test
    public void testCreateRepeatableAnnotation() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/repeatable");
        spoon.buildModel();
        CtType type = spoon.getFactory().Type().get(Repeated.class);
        CtMethod firstMethod = ((CtMethod) (type.getMethodsByName("withoutAnnotation").get(0)));
        List<CtAnnotation<?>> annotations = firstMethod.getAnnotations();
        org.junit.Assert.assertTrue(annotations.isEmpty());
        spoon.getFactory().Annotation().annotate(firstMethod, Tag.class, "value", "foo");
        org.junit.Assert.assertEquals(1, firstMethod.getAnnotations().size());
        spoon.getFactory().Annotation().annotate(firstMethod, Tag.class, "value", "bar");
        annotations = firstMethod.getAnnotations();
        org.junit.Assert.assertEquals(2, annotations.size());
        for (CtAnnotation a : annotations) {
            org.junit.Assert.assertEquals("Tag", a.getAnnotationType().getSimpleName());
        }
        String classContent = type.toString();
        org.junit.Assert.assertTrue(("Content of the file: " + classContent), classContent.contains("@spoon.test.annotation.testclasses.repeatable.Tag(\"foo\")"));
        org.junit.Assert.assertTrue(("Content of the file: " + classContent), classContent.contains("@spoon.test.annotation.testclasses.repeatable.Tag(\"bar\")"));
    }

    @org.junit.Test
    public void testRepeatableAnnotationAreManagedWithArrays() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/repeatandarrays");
        spoon.buildModel();
        CtType type = spoon.getFactory().Type().get(RepeatedArrays.class);
        CtMethod firstMethod = ((CtMethod) (type.getMethodsByName("method").get(0)));
        List<CtAnnotation<?>> annotations = firstMethod.getAnnotations();
        org.junit.Assert.assertEquals(2, annotations.size());
        for (CtAnnotation a : annotations) {
            org.junit.Assert.assertEquals("TagArrays", a.getAnnotationType().getSimpleName());
        }
        String classContent = type.toString();
        org.junit.Assert.assertTrue(("Content of the file: " + classContent), classContent.contains("@spoon.test.annotation.testclasses.repeatandarrays.TagArrays({ \"machin\", \"truc\" })"));
        org.junit.Assert.assertTrue(("Content of the file: " + classContent), classContent.contains("@spoon.test.annotation.testclasses.repeatandarrays.TagArrays({ \"truc\", \"bidule\" })"));
    }

    @org.junit.Test
    public void testCreateRepeatableAnnotationWithArrays() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/repeatandarrays");
        spoon.buildModel();
        CtType type = spoon.getFactory().Type().get(Repeated.class);
        CtMethod firstMethod = ((CtMethod) (type.getMethodsByName("withoutAnnotation").get(0)));
        List<CtAnnotation<?>> annotations = firstMethod.getAnnotations();
        org.junit.Assert.assertTrue(annotations.isEmpty());
        spoon.getFactory().Annotation().annotate(firstMethod, TagArrays.class, "value", "foo");
        org.junit.Assert.assertEquals(1, firstMethod.getAnnotations().size());
        spoon.getFactory().Annotation().annotate(firstMethod, TagArrays.class, "value", "bar");
        annotations = firstMethod.getAnnotations();
        org.junit.Assert.assertEquals(2, annotations.size());
        for (CtAnnotation a : annotations) {
            org.junit.Assert.assertEquals("TagArrays", a.getAnnotationType().getSimpleName());
        }
        String classContent = type.toString();
        org.junit.Assert.assertTrue(("Content of the file: " + classContent), classContent.contains("@spoon.test.annotation.testclasses.repeatandarrays.TagArrays(\"foo\")"));
        org.junit.Assert.assertTrue(("Content of the file: " + classContent), classContent.contains("@spoon.test.annotation.testclasses.repeatandarrays.TagArrays(\"bar\")"));
    }

    @org.junit.Test
    public void testAnnotationNotRepeatableNotArrayAnnotation() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/annotation/testclasses/notrepeatable/StringAnnot.java");
        spoon.buildModel();
        CtMethod aMethod = spoon.getFactory().createMethod().setSimpleName("mamethod");
        spoon.getFactory().Annotation().annotate(aMethod, StringAnnot.class, "value", "foo");
        org.junit.Assert.assertEquals(1, aMethod.getAnnotations().size());
        String methodContent = aMethod.toString();
        org.junit.Assert.assertTrue(("Content: " + methodContent), methodContent.contains("@spoon.test.annotation.testclasses.notrepeatable.StringAnnot(\"foo\")"));
        try {
            spoon.getFactory().Annotation().annotate(aMethod, StringAnnot.class, "value", "bar");
            methodContent = aMethod.toString();
            org.junit.Assert.fail(("You should not be able to add two values to StringAnnot annotation: " + methodContent));
        } catch (spoon.SpoonException e) {
            org.junit.Assert.assertEquals("cannot assign an array to a non-array annotation element", e.getMessage());
        }
    }

    @org.junit.Test
    public void testAnnotationTypeAndFieldOnField() throws java.io.IOException {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/typeandfield");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.setSourceOutputDirectory("./target/spooned-typeandfield");
        launcher.run();
        CtType type = launcher.getFactory().Type().get(spoon.test.annotation.testclasses.typeandfield.SimpleClass.class);
        CtField field = type.getField("mandatoryField");
        org.junit.Assert.assertEquals(1, field.getAnnotations().size());
        CtAnnotation annotation = field.getAnnotations().get(0);
        org.junit.Assert.assertEquals("spoon.test.annotation.testclasses.typeandfield.AnnotTypeAndField", annotation.getAnnotationType().getQualifiedName());
        CtTypeReference fieldType = field.getType();
        org.junit.Assert.assertEquals(1, fieldType.getAnnotations().size());
        CtAnnotation anotherAnnotation = fieldType.getAnnotations().get(0);
        org.junit.Assert.assertEquals(annotation, anotherAnnotation);
        org.junit.Assert.assertEquals("java.lang.String", field.getType().getQualifiedName());
        org.junit.Assert.assertEquals(1, field.getType().getAnnotations().size());
        List<String> lines = java.nio.file.Files.readAllLines(new java.io.File("./target/spooned-typeandfield/spoon/test/annotation/testclasses/typeandfield/SimpleClass.java").toPath());
        String fileContent = org.apache.commons.lang3.StringUtils.join(lines, "\n");
        org.junit.Assert.assertTrue(("Content :" + fileContent), fileContent.contains("@spoon.test.annotation.testclasses.typeandfield.AnnotTypeAndField"));
        org.junit.Assert.assertTrue(("Content :" + fileContent), fileContent.contains("public java.lang.String mandatoryField;"));
    }

    @org.junit.Test
    public void testAnnotationAndShadowDefaultRetentionPolicy() {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/shadow");
        spoon.reflect.CtModel model = launcher.buildModel();
        spoon.reflect.declaration.CtClass<?> dumbKlass = model.getElements(new NamedElementFilter<spoon.reflect.declaration.CtClass>(spoon.reflect.declaration.CtClass.class, "DumbKlass")).get(0);
        CtMethod<?> fooMethod = dumbKlass.getMethodsByName("foo").get(0);
        final Factory shadowFactory = spoon.testing.utils.ModelUtils.createFactory();
        CtType<?> shadowDumbKlass = shadowFactory.Type().get(spoon.test.annotation.testclasses.shadow.DumbKlass.class);
        CtMethod<?> shadowFooMethod = shadowDumbKlass.getMethodsByName("foo").get(0);
        org.junit.Assert.assertEquals(0, shadowFooMethod.getAnnotations().size());
    }

    @org.junit.Test
    public void testAnnotationAndShadowClassRetentionPolicy() {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/shadow");
        spoon.reflect.CtModel model = launcher.buildModel();
        spoon.reflect.declaration.CtClass<?> dumbKlass = model.getElements(new NamedElementFilter<>(spoon.reflect.declaration.CtClass.class, "DumbKlass")).get(0);
        CtMethod<?> fooMethod = dumbKlass.getMethodsByName("fooClass").get(0);
        final Factory shadowFactory = spoon.testing.utils.ModelUtils.createFactory();
        CtType<?> shadowDumbKlass = shadowFactory.Type().get(spoon.test.annotation.testclasses.shadow.DumbKlass.class);
        CtMethod<?> shadowFooMethod = shadowDumbKlass.getMethodsByName("fooClass").get(0);
        org.junit.Assert.assertEquals(0, shadowFooMethod.getAnnotations().size());
    }

    @org.junit.Test
    public void testAnnotationAndShadowRuntimeRetentionPolicy() {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/shadow");
        spoon.reflect.CtModel model = launcher.buildModel();
        spoon.reflect.declaration.CtClass<?> dumbKlass = model.getElements(new NamedElementFilter<>(spoon.reflect.declaration.CtClass.class, "DumbKlass")).get(0);
        CtMethod<?> fooMethod = dumbKlass.getMethodsByName("barOneValue").get(0);
        final Factory shadowFactory = spoon.testing.utils.ModelUtils.createFactory();
        CtType<?> shadowDumbKlass = shadowFactory.Type().get(spoon.test.annotation.testclasses.shadow.DumbKlass.class);
        CtMethod<?> shadowFooMethod = shadowDumbKlass.getMethodsByName("barOneValue").get(0);
        org.junit.Assert.assertEquals(fooMethod.getAnnotations().size(), shadowFooMethod.getAnnotations().size());
    }

    @org.junit.Test
    public void testAnnotationArray() throws java.lang.Exception {
        java.lang.reflect.Method barOneValueMethod = spoon.test.annotation.testclasses.shadow.DumbKlass.class.getMethod("barOneValue");
        java.lang.reflect.Method barMultipleValueMethod = spoon.test.annotation.testclasses.shadow.DumbKlass.class.getMethod("barMultipleValues");
        java.lang.annotation.Annotation annotationOneValue = barOneValueMethod.getAnnotations()[0];
        java.lang.annotation.Annotation annotationMultiple = barMultipleValueMethod.getAnnotations()[0];
        java.lang.Object oneValue = annotationOneValue.getClass().getMethod("role").invoke(annotationOneValue);
        java.lang.Object multipleValue = annotationMultiple.getClass().getMethod("role").invoke(annotationMultiple);
        org.junit.Assert.assertTrue("[Java] annotation are not arrays type", ((oneValue instanceof String[]) && (multipleValue instanceof String[])));
        org.junit.Assert.assertEquals("[Java] annotation string values are not the same", ((String[]) (oneValue))[0], ((String[]) (multipleValue))[0]);
        final Factory shadowFactory = spoon.testing.utils.ModelUtils.createFactory();
        CtType<?> shadowDumbKlass = shadowFactory.Type().get(spoon.test.annotation.testclasses.shadow.DumbKlass.class);
        CtMethod<?> shadowBarOne = shadowDumbKlass.getMethodsByName("barOneValue").get(0);
        CtAnnotation shadowAnnotationOne = shadowBarOne.getAnnotations().get(0);
        CtMethod<?> shadowMultiple = shadowDumbKlass.getMethodsByName("barMultipleValues").get(0);
        CtAnnotation shadowAnnotationMultiple = shadowMultiple.getAnnotations().get(0);
        org.junit.Assert.assertEquals("[Shadow] Annotation one and multiple are not of the same type", shadowAnnotationOne.getAnnotationType(), shadowAnnotationMultiple.getAnnotationType());
        org.junit.Assert.assertEquals("[Shadow] Annotation one and multiples values are not the same", shadowAnnotationOne.getValue("role"), shadowAnnotationMultiple.getValue("role"));
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/annotation/testclasses/shadow");
        spoon.reflect.CtModel model = launcher.buildModel();
        spoon.reflect.declaration.CtClass<?> dumbKlass = model.getElements(new NamedElementFilter<>(spoon.reflect.declaration.CtClass.class, "DumbKlass")).get(0);
        CtMethod<?> barOneValue = dumbKlass.getMethodsByName("barOneValue").get(0);
        CtAnnotation annotationOne = barOneValue.getAnnotations().get(0);
        CtMethod<?> barMultipleValue = dumbKlass.getMethodsByName("barMultipleValues").get(0);
        CtAnnotation annotationMultipleVal = barMultipleValue.getAnnotations().get(0);
        org.junit.Assert.assertEquals("[Spoon] Annotation one and multiple are not of the same type", annotationOne.getAnnotationType(), annotationMultipleVal.getAnnotationType());
        org.junit.Assert.assertTrue(((annotationOne.getValue("role")) instanceof spoon.reflect.code.CtLiteral));
        org.junit.Assert.assertTrue(((annotationMultipleVal.getValue("role")) instanceof spoon.reflect.code.CtNewArray));
        org.junit.Assert.assertTrue(((annotationOne.getWrappedValue("role")) instanceof spoon.reflect.code.CtNewArray));
        org.junit.Assert.assertTrue(((annotationMultipleVal.getWrappedValue("role")) instanceof spoon.reflect.code.CtNewArray));
        org.junit.Assert.assertEquals(annotationMultipleVal.getWrappedValue("role"), annotationOne.getWrappedValue("role"));
        org.junit.Assert.assertEquals(annotationOne.getAnnotationType(), shadowAnnotationOne.getAnnotationType());
        org.junit.Assert.assertTrue(((shadowAnnotationOne.getValue("role")) instanceof spoon.reflect.code.CtLiteral));
        org.junit.Assert.assertEquals(annotationOne.getValue("role"), shadowAnnotationOne.getValue("role"));
    }
}

