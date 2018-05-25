package spoon.test.reference;


import spoon.Launcher;
import spoon.compiler.SpoonResourceHelper;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;


public class TypeReferenceTest {
    @org.junit.Test
    public void testGetAllExecutablesForInterfaces() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{ "--output-type", "nooutput" });
        Factory factory = spoon.createFactory();
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/reference/Foo.java")).build();
        CtInterface<spoon.test.reference.Foo> foo = factory.Package().get("spoon.test.reference").getType("Foo");
        java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> execs = foo.getReference().getAllExecutables();
        org.junit.Assert.assertEquals(2, execs.size());
    }

    @java.lang.SuppressWarnings("rawtypes")
    @org.junit.Test
    public void loadReferencedClassFromClasspath() throws java.lang.Exception {
        spoon.SpoonModelBuilder comp = new Launcher().createCompiler();
        Factory factory = comp.getFactory();
        String packageName = "spoon.test.reference";
        String className = "ReferencingClass";
        String qualifiedName = (packageName + ".") + className;
        String referencedQualifiedName = (packageName + ".") + "ReferencedClass";
        java.util.List<spoon.compiler.SpoonResource> fileToBeSpooned = SpoonResourceHelper.resources((("./src/test/resources/reference-test/input/" + (qualifiedName.replace('.', '/'))) + ".java"));
        comp.addInputSources(fileToBeSpooned);
        org.junit.Assert.assertEquals(1, fileToBeSpooned.size());
        java.util.List<spoon.compiler.SpoonResource> classpath = SpoonResourceHelper.resources("./src/test/resources/reference-test/ReferenceTest.jar");
        String[] dependencyClasspath = new String[]{ classpath.get(0).getPath() };
        factory.getEnvironment().setSourceClasspath(dependencyClasspath);
        org.junit.Assert.assertEquals(1, classpath.size());
        comp.build();
        spoon.reflect.declaration.CtType<?> theClass = factory.Type().get(qualifiedName);
        CtTypeReference referencedType = null;
        spoon.reflect.visitor.filter.ReferenceTypeFilter<CtTypeReference> referenceTypeFilter = new spoon.reflect.visitor.filter.ReferenceTypeFilter<CtTypeReference>(CtTypeReference.class);
        java.util.List<CtTypeReference> elements = spoon.reflect.visitor.Query.getElements(theClass, referenceTypeFilter);
        for (CtTypeReference reference : elements) {
            if (reference.getQualifiedName().equals(referencedQualifiedName)) {
                referencedType = reference;
                break;
            }
        }
        org.junit.Assert.assertFalse((referencedType == null));
        java.lang.Class referencedClass = referencedType.getActualClass();
        org.junit.Assert.assertEquals(referencedQualifiedName, referencedClass.getName());
    }

    @java.lang.SuppressWarnings({ "rawtypes", "unchecked" })
    @org.junit.Test
    public void doNotCloseLoader() throws java.lang.Exception {
        spoon.SpoonModelBuilder comp = new Launcher().createCompiler();
        Factory factory = comp.getFactory();
        String qualifiedName = "spoontest.a.ClassA";
        String referenceQualifiedName = "spoontest.b.ClassB";
        java.util.List<spoon.compiler.SpoonResource> fileToBeSpooned = SpoonResourceHelper.resources((("./src/test/resources/reference-test-2/" + (qualifiedName.replace('.', '/'))) + ".java"));
        comp.addInputSources(fileToBeSpooned);
        org.junit.Assert.assertEquals(1, fileToBeSpooned.size());
        java.util.List<spoon.compiler.SpoonResource> classpath = SpoonResourceHelper.resources("./src/test/resources/reference-test-2/ReferenceTest2.jar");
        String[] dependencyClasspath = new String[]{ classpath.get(0).getPath() };
        factory.getEnvironment().setSourceClasspath(dependencyClasspath);
        org.junit.Assert.assertEquals(1, classpath.size());
        comp.build();
        spoon.reflect.declaration.CtType<?> theClass = factory.Type().get(qualifiedName);
        java.util.List<spoon.reflect.declaration.CtField<?>> fields = theClass.getFields();
        org.junit.Assert.assertEquals(1, fields.size());
        spoon.reflect.declaration.CtField<?> bField = fields.get(0);
        CtTypeReference referencedType = bField.getType();
        org.junit.Assert.assertEquals(referenceQualifiedName, referencedType.getQualifiedName());
        java.util.Collection<spoon.reflect.reference.CtFieldReference<?>> fieldsOfB = referencedType.getAllFields();
        if ((fieldsOfB.size()) == 2) {
            final spoon.reflect.reference.CtFieldReference<?> potentialJacoco = ((spoon.reflect.reference.CtFieldReference<?>) (fieldsOfB.toArray()[1]));
            if ("$jacocoData".equals(potentialJacoco.getSimpleName())) {
                fieldsOfB.remove(potentialJacoco);
            }
        }
        org.junit.Assert.assertEquals(1, fieldsOfB.size());
        spoon.reflect.reference.CtFieldReference<?> cField = fieldsOfB.iterator().next();
        org.junit.Assert.assertEquals("spoontest.c.ClassC", cField.getType().getQualifiedName());
    }

    @org.junit.Test
    public void testNullReferenceSubtype() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        Factory factory = spoon.createFactory();
        CtTypeReference<?> ref = factory.Type().createReference(String.class);
        CtTypeReference<?> nullRef = factory.Type().createReference(CtTypeReference.NULL_TYPE_NAME);
        org.junit.Assert.assertFalse(ref.isSubtypeOf(nullRef));
        org.junit.Assert.assertFalse(nullRef.isSubtypeOf(ref));
    }

    @org.junit.Test
    public void unboxTest() {
        Factory factory = new Launcher().createFactory();
        CtTypeReference<java.lang.Boolean> boxedBoolean = factory.Class().createReference(java.lang.Boolean.class);
        org.junit.Assert.assertEquals(boxedBoolean.unbox().getActualClass(), boolean.class);
    }

    @org.junit.Test
    public void testToStringEqualityBetweenTwoGenericTypeDifferent() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "--output-type", "nooutput", "--noclasspath" });
        launcher.addInputResource("src/test/java/spoon/test/reference/TypeReferenceTest.java");
        launcher.run();
        Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtTypeParameter firstTypeParam = factory.Type().get(spoon.test.reference.TypeReferenceTest.A.Tacos.class).getFormalCtTypeParameters().get(0);
        final spoon.reflect.declaration.CtTypeParameter secondTypeParam = factory.Type().get(spoon.test.reference.TypeReferenceTest.B.Tacos.class).getFormalCtTypeParameters().get(0);
        org.junit.Assert.assertNotEquals(firstTypeParam.toString(), secondTypeParam.toString());
        org.junit.Assert.assertNotEquals(firstTypeParam, secondTypeParam);
    }

    @org.junit.Test
    public void testRecursiveTypeReference() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/reference/testclasses/Tacos.java");
        launcher.setSourceOutputDirectory("./target/spoon-test");
        launcher.run();
        final spoon.reflect.code.CtInvocation<?> inv = spoon.reflect.visitor.Query.getElements(launcher.getFactory(), new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtInvocation<?>>(spoon.reflect.code.CtInvocation.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtInvocation<?> element) {
                return (!(element.getExecutable().isConstructor())) && (super.matches(element));
            }
        }).get(0);
        org.junit.Assert.assertNotNull(inv.getExecutable());
        final CtTypeReference<?> returnType = inv.getExecutable().getType();
        org.junit.Assert.assertNotNull(returnType);
        org.junit.Assert.assertEquals(1, returnType.getActualTypeArguments().size());
        final spoon.reflect.reference.CtTypeParameterReference genericType = ((spoon.reflect.reference.CtTypeParameterReference) (returnType.getActualTypeArguments().get(0)));
        org.junit.Assert.assertNotNull(genericType);
        org.junit.Assert.assertNotNull(genericType.getBoundingType());
        CtTypeReference<?> extendsGeneric = genericType.getBoundingType();
        org.junit.Assert.assertNotNull(extendsGeneric);
        org.junit.Assert.assertEquals(1, extendsGeneric.getActualTypeArguments().size());
        spoon.reflect.reference.CtTypeParameterReference genericExtends = ((spoon.reflect.reference.CtTypeParameterReference) (extendsGeneric.getActualTypeArguments().get(0)));
        org.junit.Assert.assertNotNull(genericExtends);
        org.junit.Assert.assertNotNull(genericExtends.getBoundingType());
        org.junit.Assert.assertTrue(((genericExtends.getBoundingType()) instanceof CtTypeReference));
    }

    @org.junit.Test
    public void testRecursiveTypeReferenceInGenericType() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/reference/testclasses/EnumValue.java");
        launcher.setSourceOutputDirectory("./target/spoon-test");
        launcher.run();
        final spoon.reflect.declaration.CtClass<spoon.test.reference.testclasses.EnumValue> aClass = launcher.getFactory().Class().get(spoon.test.reference.testclasses.EnumValue.class);
        final spoon.reflect.declaration.CtMethod<?> asEnum = aClass.getMethodsByName("asEnum").get(0);
        final spoon.reflect.declaration.CtTypeParameter typeParameter = asEnum.getFormalCtTypeParameters().get(0);
        org.junit.Assert.assertNotNull(typeParameter);
        org.junit.Assert.assertNotNull(typeParameter.getSuperclass());
        final CtTypeReference<?> extendsGeneric = typeParameter.getSuperclass();
        org.junit.Assert.assertNotNull(extendsGeneric);
        org.junit.Assert.assertEquals(1, extendsGeneric.getActualTypeArguments().size());
        final CtTypeReference circularRef = extendsGeneric.getActualTypeArguments().get(0);
        org.junit.Assert.assertNotNull(circularRef);
    }

    @org.junit.Test
    public void testUnknownSuperClassWithSameNameInNoClasspath() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/Attachment.java");
        launcher.setSourceOutputDirectory("./target/class-declaration");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        spoon.reflect.declaration.CtClass<?> ctType = ((spoon.reflect.declaration.CtClass<?>) (launcher.getFactory().Class().getAll().get(0)));
        org.junit.Assert.assertNotEquals(ctType.getSuperclass(), ctType.getReference());
        org.junit.Assert.assertEquals("it.feio.android.omninotes.commons.models.Attachment", ctType.getSuperclass().toString());
        org.junit.Assert.assertEquals("it.feio.android.omninotes.models.Attachment", ctType.getReference().toString());
    }

    @org.junit.Test
    public void testPackageInNoClasspath() {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/Demo.java");
        launcher.setSourceOutputDirectory("./target/class-declaration");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<java.lang.Object> aClass = launcher.getFactory().Class().get("Demo");
        final java.util.Set<CtTypeReference<?>> referencedTypes = aClass.getReferencedTypes();
        boolean containsDemoReference = false;
        boolean containsVoidReference = false;
        boolean containsStringReference = false;
        boolean containsJoinerReference = false;
        for (java.util.Iterator<CtTypeReference<?>> iterator = referencedTypes.iterator(); iterator.hasNext();) {
            CtTypeReference<?> reference = iterator.next();
            if (reference.toString().equals("Demo")) {
                containsDemoReference = true;
            }else
                if (reference.toString().equals("void")) {
                    containsVoidReference = true;
                }else
                    if (reference.toString().equals("java.lang.String")) {
                        containsStringReference = true;
                    }else
                        if (reference.toString().equals("com.google.common.base.Joiner")) {
                            containsJoinerReference = true;
                        }



        }
        org.junit.Assert.assertTrue("Reference to Demo is missing", containsDemoReference);
        org.junit.Assert.assertTrue("Reference to void is missing", containsVoidReference);
        org.junit.Assert.assertTrue("Reference to String is missing", containsStringReference);
        org.junit.Assert.assertTrue("Reference to Joiner is missing", containsJoinerReference);
    }

    @org.junit.Test
    public void testTypeReferenceSpecifiedInClassDeclarationInNoClasspath() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/Demo.java");
        launcher.setSourceOutputDirectory("./target/class-declaration");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<java.lang.Object> aClass = launcher.getFactory().Class().get("Demo");
        org.junit.Assert.assertNotNull(aClass.getSuperclass());
        org.junit.Assert.assertEquals("com.google.common.base.Function", aClass.getSuperclass().getQualifiedName());
        org.junit.Assert.assertEquals(2, aClass.getSuperclass().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("java.lang.String", aClass.getSuperclass().getActualTypeArguments().get(0).toString());
        org.junit.Assert.assertEquals("java.lang.String", aClass.getSuperclass().getActualTypeArguments().get(1).toString());
        org.junit.Assert.assertEquals(1, aClass.getSuperInterfaces().size());
        for (CtTypeReference<?> superInterface : aClass.getSuperInterfaces()) {
            org.junit.Assert.assertEquals("com.google.common.base.Function", superInterface.getQualifiedName());
            org.junit.Assert.assertEquals(2, superInterface.getActualTypeArguments().size());
            org.junit.Assert.assertEquals("java.lang.String", superInterface.getActualTypeArguments().get(0).toString());
            org.junit.Assert.assertEquals("java.lang.String", superInterface.getActualTypeArguments().get(1).toString());
        }
    }

    @org.junit.Test
    public void testTypeReferenceSpecifiedInClassDeclarationInNoClasspathWithGenerics() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/Demo2.java");
        launcher.setSourceOutputDirectory("./target/class-declaration");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<java.lang.Object> aClass = launcher.getFactory().Class().get("Demo2");
        java.util.Set<CtTypeReference<?>> superInterfaces = aClass.getSuperInterfaces();
        final CtTypeReference superInterface = superInterfaces.toArray(new CtTypeReference[superInterfaces.size()])[0];
        org.junit.Assert.assertEquals("Bar", superInterface.getSimpleName());
        org.junit.Assert.assertEquals(2, superInterface.getActualTypeArguments().size());
        final CtTypeReference<?> first = superInterface.getActualTypeArguments().get(0);
        org.junit.Assert.assertTrue((first instanceof spoon.reflect.reference.CtTypeParameterReference));
        org.junit.Assert.assertEquals("?", first.getSimpleName());
        final CtTypeReference<?> second = superInterface.getActualTypeArguments().get(1);
        org.junit.Assert.assertTrue((second instanceof spoon.reflect.reference.CtTypeParameterReference));
        org.junit.Assert.assertEquals("?", second.getSimpleName());
        final CtTypeReference<?> bound = ((spoon.reflect.reference.CtTypeParameterReference) (second)).getBoundingType();
        org.junit.Assert.assertEquals("Tacos", bound.getSimpleName());
        org.junit.Assert.assertEquals(1, bound.getActualTypeArguments().size());
        org.junit.Assert.assertEquals("?", bound.getActualTypeArguments().get(0).getSimpleName());
        org.junit.Assert.assertEquals("example.FooBar", superInterface.getDeclaringType().getQualifiedName());
        org.junit.Assert.assertEquals("example.FooBar<?, ? extends Tacos<?>>.Bar<?, ? extends Tacos<?>>", superInterface.toString());
    }

    @org.junit.Test
    public void testArgumentOfAInvocationIsNotATypeAccess() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/Demo3.java");
        launcher.setSourceOutputDirectory("./target/class-declaration");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<java.lang.Object> demo3 = launcher.getFactory().Class().get("Demo3");
        final java.util.List<spoon.reflect.code.CtFieldRead> fields = demo3.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtFieldRead>(spoon.reflect.code.CtFieldRead.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtFieldRead element) {
                return ("bar".equals(element.getVariable().getSimpleName())) && (super.matches(element));
            }
        });
        org.junit.Assert.assertEquals(1, fields.size());
    }

    @org.junit.Test
    public void testInvocationWithFieldAccessInNoClasspath() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/Demo4.java");
        launcher.setSourceOutputDirectory("./target/class-declaration");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<java.lang.Object> demo4 = launcher.getFactory().Class().get("Demo4");
        final spoon.reflect.declaration.CtMethod<?> doSomething = demo4.getMethodsByName("doSomething").get(0);
        final spoon.reflect.code.CtInvocation topInvocation = doSomething.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtInvocation.class)).get(0);
        org.junit.Assert.assertNotNull(topInvocation.getTarget());
        org.junit.Assert.assertTrue(((topInvocation.getTarget()) instanceof spoon.reflect.code.CtInvocation));
        org.junit.Assert.assertNotNull(((spoon.reflect.code.CtInvocation) (topInvocation.getTarget())).getTarget());
        org.junit.Assert.assertTrue(((((spoon.reflect.code.CtInvocation) (topInvocation.getTarget())).getTarget()) instanceof spoon.reflect.code.CtFieldRead));
        org.junit.Assert.assertEquals(1, topInvocation.getArguments().size());
        org.junit.Assert.assertTrue(((topInvocation.getArguments().get(0)) instanceof spoon.reflect.code.CtFieldRead));
        org.junit.Assert.assertEquals("a.foo().bar(b)", topInvocation.toString());
        spoon.testing.utils.ModelUtils.canBeBuilt("./src/test/resources/noclasspath/TestBot.java", 8, true);
    }

    @org.junit.Test
    public void testAnnotationOnMethodWithPrimitiveReturnTypeInNoClasspath() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/A.java");
        launcher.setSourceOutputDirectory("./target/class-declaration");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<java.lang.Object> aClass = launcher.getFactory().Class().get("A");
        final spoon.reflect.declaration.CtClass anonymousClass = aClass.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtNewClass.class)).get(0).getAnonymousClass();
        final spoon.reflect.declaration.CtMethod run = anonymousClass.getMethod("run");
        org.junit.Assert.assertNotNull(run);
        org.junit.Assert.assertEquals(1, run.getAnnotations().size());
        org.junit.Assert.assertEquals("@java.lang.Override", run.getAnnotations().get(0).toString());
    }

    @org.junit.Test
    public void testAnonymousClassesHaveAnEmptyStringForItsNameInNoClasspath() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/A.java");
        launcher.setSourceOutputDirectory("./target/class-declaration");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<java.lang.Object> aClass = launcher.getFactory().Class().get("A");
        final spoon.reflect.declaration.CtClass anonymousClass = aClass.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtNewClass.class)).get(0).getAnonymousClass();
        org.junit.Assert.assertEquals("1", anonymousClass.getReference().getSimpleName());
        java.util.Set<CtTypeReference<?>> referencedTypes = aClass.getReferencedTypes();
        java.util.List<String> referencedTypeNames = referencedTypes.stream().map(java.lang.Object::toString).collect(java.util.stream.Collectors.toList());
        org.junit.Assert.assertEquals(7, referencedTypeNames.size());
        org.junit.Assert.assertTrue(referencedTypeNames.contains("A"));
        org.junit.Assert.assertTrue(referencedTypeNames.contains("example.B"));
        org.junit.Assert.assertTrue(referencedTypeNames.contains("java.lang.Runnable"));
        org.junit.Assert.assertTrue(referencedTypeNames.contains("java.lang.Override"));
        org.junit.Assert.assertTrue(referencedTypeNames.contains("java.lang.Object"));
        org.junit.Assert.assertTrue(referencedTypeNames.contains("A.1"));
        org.junit.Assert.assertTrue(referencedTypeNames.contains("void"));
    }

    @org.junit.Test
    public void testConstructorCallInNoClasspath() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/resources/noclasspath/Demo5.java");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<java.lang.Object> demo5 = launcher.getFactory().Class().get("Demo5");
        final spoon.reflect.declaration.CtMethod<java.lang.Object> foo = demo5.getMethod("foo");
        final java.util.List<spoon.reflect.code.CtConstructorCall> elements = foo.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtConstructorCall.class));
        org.junit.Assert.assertEquals("A.B<C>", elements.get(0).getType().toString());
        org.junit.Assert.assertEquals("D", elements.get(1).getType().toString());
    }

    @org.junit.Test
    public void testShortTypeReference() throws java.lang.Exception {
        CtTypeReference<java.lang.Short> aShort = spoon.testing.utils.ModelUtils.createFactory().Type().SHORT;
        CtTypeReference<java.lang.Short> shortPrimitive = spoon.testing.utils.ModelUtils.createFactory().Type().SHORT_PRIMITIVE;
        org.junit.Assert.assertEquals(java.lang.Short.class, aShort.getActualClass());
        org.junit.Assert.assertEquals(short.class, shortPrimitive.getActualClass());
    }

    @org.junit.Test
    public void testClearBoundsForTypeParameterReference() throws java.lang.Exception {
        final Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        final spoon.reflect.reference.CtTypeParameterReference reference = factory.Type().createTypeParameterReference("T");
        reference.addBound(factory.Type().createReference(String.class));
        org.junit.Assert.assertNotNull(reference.getBoundingType());
        reference.setBounds(null);
        org.junit.Assert.assertEquals(factory.Type().OBJECT, reference.getBoundingType());
        org.junit.Assert.assertTrue(reference.isDefaultBoundingType());
        reference.addBound(factory.Type().createReference(String.class));
        org.junit.Assert.assertNotNull(reference.getBoundingType());
        reference.setBounds(new java.util.ArrayList<>());
        org.junit.Assert.assertEquals(factory.Type().OBJECT, reference.getBoundingType());
        org.junit.Assert.assertTrue(reference.isDefaultBoundingType());
    }

    @org.junit.Test
    public void testIgnoreEnclosingClassInActualTypes() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.reference.testclasses.Panini> aPanini = spoon.testing.utils.ModelUtils.buildClass(spoon.test.reference.testclasses.Panini.class);
        final spoon.reflect.code.CtStatement ctReturn = aPanini.getMethod("entryIterator").getBody().getStatement(0);
        org.junit.Assert.assertTrue((ctReturn instanceof spoon.reflect.code.CtReturn));
        final spoon.reflect.code.CtExpression ctConstructorCall = ((spoon.reflect.code.CtReturn) (ctReturn)).getReturnedExpression();
        org.junit.Assert.assertTrue((ctConstructorCall instanceof spoon.reflect.code.CtConstructorCall));
        org.junit.Assert.assertEquals("spoon.test.reference.testclasses.Panini<K, V>.Itr<java.util.Map.Entry<K, V>>", ctConstructorCall.getType().toString());
    }

    class A {
        class Tacos<K> {}
    }

    class B {
        class Tacos<K extends spoon.test.reference.TypeReferenceTest.A> {}
    }

    @org.junit.Test
    public void testCorrectEnumParent() {
        final Launcher launcher = new Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.buildModel();
        spoon.reflect.declaration.CtEnum e = launcher.getFactory().Enum().create("spoon.test.reference.EnumE");
        CtTypeReference correctParent = launcher.getFactory().Type().createReference(java.lang.Enum.class);
        org.junit.Assert.assertEquals(correctParent, e.getReference().getSuperclass());
    }

    @org.junit.Test
    public void testImproveAPIActualTypeReference() throws java.lang.Exception {
        final Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        java.util.List<spoon.reflect.reference.CtTypeParameterReference> typeParameterReferences = new java.util.ArrayList<>();
        typeParameterReferences.add(factory.Type().createTypeParameterReference("Foo"));
        final CtTypeReference<java.lang.Object> typeReference = factory.Core().createTypeReference();
        typeReference.setActualTypeArguments(typeParameterReferences);
        org.junit.Assert.assertEquals(1, typeReference.getActualTypeArguments().size());
    }

    @org.junit.Test
    public void testIsSubTypeSuperClassNull() throws java.lang.Exception {
        Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        factory.Class().create("Tacos");
        CtTypeReference<?> subRef = factory.Type().createReference(java.lang.AutoCloseable.class);
        CtTypeReference<?> superRef = factory.Type().createReference("Tacos");
        org.junit.Assert.assertFalse(subRef.isSubtypeOf(superRef));
    }

    @org.junit.Test
    public void testSubTypeAnonymous() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<spoon.test.reference.testclasses.Panini> paniniCtType = spoon.testing.utils.ModelUtils.buildClass(spoon.test.reference.testclasses.Panini.class);
        spoon.reflect.declaration.CtClass anonymousClass = ((spoon.reflect.code.CtNewClass) (((spoon.reflect.code.CtReturn) (paniniCtType.getMethod("entryIterator").getBody().getStatement(0))).getReturnedExpression())).getAnonymousClass();
        org.junit.Assert.assertTrue(anonymousClass.getReference().isSubtypeOf(paniniCtType.getFactory().Type().createReference("spoon.test.reference.testclasses.Panini$Itr")));
    }

    @org.junit.Test
    public void testGetTypeDeclaration() throws java.lang.Exception {
        Launcher l = new Launcher();
        l.addInputResource("src/test/resources/compilation/compilation-tests/");
        l.buildModel();
        spoon.reflect.declaration.CtType<?> bar = l.getFactory().Type().get("compilation.Bar");
        spoon.reflect.declaration.CtType iBar = bar.getSuperInterfaces().toArray(new CtTypeReference[0])[0].getTypeDeclaration();
        org.junit.Assert.assertNotNull(iBar);
        org.junit.Assert.assertEquals("compilation.IBar", iBar.getQualifiedName());
    }

    @org.junit.Test
    public void testTypeDeclarationWildcard() throws java.lang.Exception {
        spoon.reflect.code.CtLocalVariable<?> s = new Launcher().getFactory().Code().createCodeSnippetStatement("java.util.List<?> l = null").compile();
        org.junit.Assert.assertEquals("?", s.getType().getActualTypeArguments().get(0).getSimpleName());
        org.junit.Assert.assertTrue(spoon.reflect.reference.CtWildcardReference.class.isInstance(s.getType().getActualTypeArguments().get(0)));
        org.junit.Assert.assertEquals("Object", s.getType().getActualTypeArguments().get(0).getTypeDeclaration().getSimpleName());
        org.junit.Assert.assertEquals(java.lang.Object.class, s.getType().getActualTypeArguments().get(0).getTypeDeclaration().getActualClass());
        spoon.reflect.code.CtLocalVariable<?> s2 = new Launcher().getFactory().Code().createCodeSnippetStatement("java.util.List<String> l = null").compile();
        org.junit.Assert.assertEquals("String", s2.getType().getActualTypeArguments().get(0).getSimpleName());
        org.junit.Assert.assertEquals(String.class, s2.getType().getActualTypeArguments().get(0).getTypeDeclaration().getActualClass());
    }

    @org.junit.Test
    public void testEqualityTypeReference() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<spoon.test.reference.testclasses.ParamRefs> aClass = ((spoon.reflect.declaration.CtClass) (spoon.testing.utils.ModelUtils.buildClass(spoon.test.reference.testclasses.ParamRefs.class)));
        spoon.reflect.declaration.CtParameter<?> parameter = aClass.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtParameter.class, "param")).get(0);
        spoon.reflect.reference.CtParameterReference<?> parameterRef1 = parameter.getReference();
        spoon.reflect.reference.CtParameterReference<?> parameterRef2 = aClass.getElements((spoon.reflect.reference.CtParameterReference<?> ref) -> ref.getSimpleName().equals("param")).get(0);
        org.junit.Assert.assertEquals(null, parameterRef1.getDeclaringExecutable());
        org.junit.Assert.assertEquals(aClass.getReference(), parameterRef2.getDeclaringExecutable().getType());
        org.junit.Assert.assertEquals(parameterRef1, parameterRef2);
    }

    @org.junit.Test
    public void testTypeReferenceWithGenerics() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/resources/import-with-generics/TestWithGenerics.java");
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.buildModel();
        spoon.reflect.declaration.CtField field = launcher.getModel().getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtField>(spoon.reflect.declaration.CtField.class)).get(0);
        CtTypeReference fieldTypeRef = field.getType();
        org.junit.Assert.assertEquals("spoon.test.imports.testclasses.withgenerics.Target", fieldTypeRef.getQualifiedName());
        org.junit.Assert.assertEquals(2, fieldTypeRef.getActualTypeArguments().size());
    }
}

