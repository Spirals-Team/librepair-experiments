package spoon.test.reference;


import spoon.Launcher;
import spoon.SpoonModelBuilder;
import spoon.compiler.SpoonResourceHelper;


public class ExecutableReferenceGenericTest {
    private spoon.reflect.factory.Factory factory;

    public static final java.lang.String NAME_MY_CLASS_1 = "MyClass";

    @org.junit.Before
    public void setUp() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        factory = spoon.createFactory();
        SpoonModelBuilder compiler = spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/reference/MyClass.java", "./src/test/java/spoon/test/reference/MyClass2.java", "./src/test/java/spoon/test/reference/MyClass3.java"));
        compiler.build();
    }

    @org.junit.Test
    public void testReferencesBetweenConstructors() throws java.lang.Exception {
        final java.util.List<spoon.reflect.declaration.CtConstructor<?>> constructors = getConstructorsByClass("MyClass");
        spoon.reflect.declaration.CtConstructor<?> emptyConstructor = constructors.get(0);
        spoon.reflect.declaration.CtConstructor<?> oneParamConstructor = constructors.get(1);
        spoon.reflect.declaration.CtConstructor<?> twoParamsConstructor = constructors.get(2);
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> refConstructors = getCtConstructorsByCtConstructor(emptyConstructor);
        org.junit.Assert.assertEquals(1, refConstructors.size());
        org.junit.Assert.assertEquals(1, refConstructors.get(0).getDeclaration().getParameters().size());
        org.junit.Assert.assertEquals(oneParamConstructor, refConstructors.get(0).getDeclaration());
        refConstructors = getCtConstructorsByCtConstructor(oneParamConstructor);
        org.junit.Assert.assertEquals(1, refConstructors.size());
        org.junit.Assert.assertEquals(2, refConstructors.get(0).getDeclaration().getParameters().size());
        org.junit.Assert.assertEquals(twoParamsConstructor, refConstructors.get(0).getDeclaration());
    }

    @org.junit.Test
    public void testReferencesBetweenConstructorsInOtherClass() throws java.lang.Exception {
        final java.util.List<spoon.reflect.declaration.CtConstructor<?>> constructors = getConstructorsByClass("MyClass2");
        final spoon.reflect.declaration.CtConstructor<?> ctConstructor = constructors.get(0);
        final java.util.List<spoon.reflect.reference.CtExecutableReference<?>> refConstructors = getCtConstructorsReferencedInCtConstructor(ctConstructor);
        final spoon.reflect.declaration.CtClass<?> clazz1 = getCtClassByName("MyClass");
        final spoon.reflect.declaration.CtConstructor<?> emptyConstructorClass1 = getConstructorsByClass(clazz1.getSimpleName()).get(0);
        final spoon.reflect.declaration.CtClass<?> clazz3 = getCtClassByName("MyClass3");
        final spoon.reflect.declaration.CtConstructor<?> emptyConstructorClass3 = getConstructorsByClass(clazz3.getSimpleName()).get(0);
        org.junit.Assert.assertEquals(3, refConstructors.size());
        org.junit.Assert.assertEquals(0, emptyConstructorClass1.getParameters().size());
        org.junit.Assert.assertEquals(0, emptyConstructorClass3.getParameters().size());
        org.junit.Assert.assertNull(refConstructors.get(0).getDeclaration());
        spoon.reflect.declaration.CtExecutable<?> decl = refConstructors.get(0).getExecutableDeclaration();
        org.junit.Assert.assertEquals("Object", decl.getType().getSimpleName());
        org.junit.Assert.assertEquals(0, decl.getParameters().size());
        org.junit.Assert.assertNotNull(refConstructors.get(0).getExecutableDeclaration());
        org.junit.Assert.assertEquals(emptyConstructorClass1, refConstructors.get(1).getDeclaration());
        org.junit.Assert.assertEquals(emptyConstructorClass3, refConstructors.get(2).getDeclaration());
    }

    @org.junit.Test
    public void testOneReferenceBetweenMethodsInSameClass() throws java.lang.Exception {
        final spoon.reflect.declaration.CtClass<?> clazz = getCtClassByName("MyClass");
        spoon.reflect.declaration.CtMethod<?> method1 = getCtMethodByNameFromCtClass(clazz, "method1");
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> refsMethod1 = getReferencesOfAMethod(method1);
        spoon.reflect.declaration.CtMethod<?> expected = getCtMethodByNameFromCtClass(clazz, "method2");
        org.junit.Assert.assertEquals(1, refsMethod1.size());
        org.junit.Assert.assertEquals(expected, refsMethod1.get(0).getDeclaration());
    }

    @org.junit.Test
    public void testMultiReferenceBetweenMethodsWithGenericInSameClass() throws java.lang.Exception {
        final spoon.reflect.declaration.CtClass<?> clazz = getCtClassByName("MyClass");
        spoon.reflect.declaration.CtMethod<?> method2 = getCtMethodByNameFromCtClass(clazz, "method2");
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> refsMethod2 = getReferencesOfAMethod(method2);
        spoon.reflect.declaration.CtMethod<?> expectedMethod1 = getCtMethodByNameFromCtClass(clazz, "method1");
        spoon.reflect.declaration.CtMethod<?> expectedMethod5 = getCtMethodByNameFromCtClass(clazz, "method5");
        org.junit.Assert.assertEquals(3, refsMethod2.size());
        spoon.reflect.declaration.CtExecutable execRefsMethods2 = refsMethod2.get(0).getDeclaration();
        org.junit.Assert.assertEquals("method1(T extends java.lang.String)", execRefsMethods2.getSignature());
        org.junit.Assert.assertEquals(expectedMethod1, refsMethod2.get(1).getDeclaration());
        org.junit.Assert.assertEquals(expectedMethod5, refsMethod2.get(2).getDeclaration());
    }

    @org.junit.Test
    public void testMultiReferencesBetweenMethodsWithoutGenericInSameClass() throws java.lang.Exception {
        final spoon.reflect.declaration.CtClass<?> clazz = getCtClassByName("MyClass");
        spoon.reflect.declaration.CtMethod<?> method3 = getCtMethodByNameFromCtClass(clazz, "method3");
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> refsMethod3 = getReferencesOfAMethod(method3);
        spoon.reflect.declaration.CtMethod<?> expectedMethod2 = getCtMethodByNameFromCtClass(clazz, "method2");
        spoon.reflect.declaration.CtMethod<?> expectedMethod4 = getCtMethodByNameFromCtClass(clazz, "method4");
        org.junit.Assert.assertEquals(2, refsMethod3.size());
        org.junit.Assert.assertEquals(expectedMethod2, refsMethod3.get(0).getDeclaration());
        org.junit.Assert.assertEquals(expectedMethod4, refsMethod3.get(1).getDeclaration());
    }

    @org.junit.Test
    public void testMethodWithoutReferences() throws java.lang.Exception {
        final spoon.reflect.declaration.CtClass<?> clazz = getCtClassByName("MyClass");
        spoon.reflect.declaration.CtMethod<?> method4 = getCtMethodByNameFromCtClass(clazz, "method4");
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> refsMethod4 = getReferencesOfAMethod(method4);
        org.junit.Assert.assertEquals(0, refsMethod4.size());
    }

    @org.junit.Test
    public void testMethodGenericWithoutReferences() throws java.lang.Exception {
        final spoon.reflect.declaration.CtClass<?> clazz = getCtClassByName("MyClass");
        spoon.reflect.declaration.CtMethod<?> method5 = getCtMethodByNameFromCtClass(clazz, "method5");
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> refsMethod5 = getReferencesOfAMethod(method5);
        org.junit.Assert.assertEquals(0, refsMethod5.size());
    }

    @org.junit.Test
    public void testOneReferenceWithGenericMethodOutOfTheClass() throws java.lang.Exception {
        final spoon.reflect.declaration.CtClass<?> clazz = getCtClassByName("MyClass");
        final spoon.reflect.declaration.CtClass<?> clazz2 = getCtClassByName("MyClass2");
        spoon.reflect.declaration.CtMethod<?> methodA = getCtMethodByNameFromCtClass(clazz2, "methodA");
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> refsMethodA = getReferencesOfAMethod(methodA);
        spoon.reflect.declaration.CtMethod<?> expectedMethod1 = getCtMethodByNameFromCtClass(clazz, "method1");
        org.junit.Assert.assertEquals(1, refsMethodA.size());
        spoon.reflect.declaration.CtExecutable execRefsMethods2 = refsMethodA.get(0).getDeclaration();
        org.junit.Assert.assertEquals(execRefsMethods2.getSignature(), "method1(T extends java.lang.String)");
    }

    @org.junit.Test
    public void testOneReferenceWithMethodNotGenericOutOfTheClass() throws java.lang.Exception {
        final spoon.reflect.declaration.CtClass<?> clazz = getCtClassByName("MyClass");
        final spoon.reflect.declaration.CtClass<?> clazz2 = getCtClassByName("MyClass2");
        spoon.reflect.declaration.CtMethod<?> methodB = getCtMethodByNameFromCtClass(clazz2, "methodB");
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> refsMethodB = getReferencesOfAMethod(methodB);
        spoon.reflect.declaration.CtMethod<?> expectedMethod2 = getCtMethodByNameFromCtClass(clazz, "method2");
        org.junit.Assert.assertEquals(1, refsMethodB.size());
        org.junit.Assert.assertEquals(expectedMethod2, refsMethodB.get(0).getDeclaration());
    }

    @org.junit.Test
    public void testMultiReferenceWithGenericMethodOutOfTheClass() throws java.lang.Exception {
        final spoon.reflect.declaration.CtClass<?> clazz2 = getCtClassByName("MyClass2");
        final spoon.reflect.declaration.CtClass<?> clazz3 = getCtClassByName("MyClass3");
        spoon.reflect.declaration.CtMethod<?> methodC = getCtMethodByNameFromCtClass(clazz2, "methodC");
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> refsMethodC = getReferencesOfAMethod(methodC);
        spoon.reflect.declaration.CtMethod<?> expectedMethodI = getCtMethodByNameFromCtClass(clazz3, "methodI");
        spoon.reflect.declaration.CtMethod<?> expectedMethodII = getCtMethodByNameFromCtClass(clazz3, "methodII");
        org.junit.Assert.assertEquals(2, refsMethodC.size());
        org.junit.Assert.assertEquals(expectedMethodI, refsMethodC.get(0).getDeclaration());
        org.junit.Assert.assertEquals(expectedMethodII, refsMethodC.get(1).getDeclaration());
    }

    @org.junit.Test
    public void testReferencesBetweenMethods() throws java.lang.Exception {
        final spoon.reflect.declaration.CtClass<?> clazz2 = getCtClassByName("MyClass2");
        spoon.reflect.declaration.CtMethod<?> methodD = getCtMethodByNameFromCtClass(clazz2, "methodD");
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> refsMethodD = getReferencesOfAMethod(methodD);
        spoon.reflect.declaration.CtMethod<?> expectedMethodE = getCtMethodByNameFromCtClass(clazz2, "methodE");
        org.junit.Assert.assertEquals(1, refsMethodD.size());
        org.junit.Assert.assertEquals(expectedMethodE, refsMethodD.get(0).getDeclaration());
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> refsMethodE = getReferencesOfAMethod(expectedMethodE);
        spoon.reflect.declaration.CtMethod<?> expectedMethodF = getCtMethodByNameFromCtClass(clazz2, "methodF");
        org.junit.Assert.assertEquals(1, refsMethodE.size());
        org.junit.Assert.assertEquals(expectedMethodF, refsMethodE.get(0).getDeclaration());
    }

    @org.junit.Test
    public void testExecutableReferences() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> classMyClass = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtClass.class, "MyClass")).get(0);
        org.junit.Assert.assertEquals("MyClass", classMyClass.getSimpleName());
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> refsExecutableClass1 = spoon.reflect.visitor.Query.getElements(classMyClass, new spoon.reflect.visitor.filter.AbstractReferenceFilter<spoon.reflect.reference.CtExecutableReference<?>>(spoon.reflect.reference.CtExecutableReference.class) {
            public boolean matches(spoon.reflect.reference.CtExecutableReference<?> reference) {
                return true;
            }
        });
        spoon.reflect.declaration.CtClass<?> classMyClass2 = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtClass.class, "MyClass2")).get(0);
        org.junit.Assert.assertEquals("MyClass2", classMyClass2.getSimpleName());
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> refsExecutableClass2 = spoon.reflect.visitor.Query.getElements(classMyClass2, new spoon.reflect.visitor.filter.AbstractReferenceFilter<spoon.reflect.reference.CtExecutableReference<?>>(spoon.reflect.reference.CtExecutableReference.class) {
            public boolean matches(spoon.reflect.reference.CtExecutableReference<?> reference) {
                return true;
            }
        });
        org.junit.Assert.assertEquals(10, refsExecutableClass1.size());
        for (spoon.reflect.reference.CtExecutableReference<?> ref : refsExecutableClass1) {
            org.junit.Assert.assertNotNull(ref);
            if (!(ref.toString().equals("java.lang.Object()"))) {
                org.junit.Assert.assertNotNull(ref.getDeclaration());
            }
        }
        org.junit.Assert.assertEquals(9, refsExecutableClass2.size());
        for (spoon.reflect.reference.CtExecutableReference<?> ref : refsExecutableClass2) {
            org.junit.Assert.assertNotNull(ref);
            if (!(ref.toString().equals("java.lang.Object()"))) {
                org.junit.Assert.assertNotNull(ref.getDeclaration());
            }
        }
    }

    private java.util.List<spoon.reflect.declaration.CtConstructor<?>> getConstructorsByClass(final java.lang.String myClass) {
        return spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtConstructor<?>>() {
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtConstructor<?> element) {
                return myClass.equals(((spoon.reflect.declaration.CtClass<?>) (element.getParent())).getSimpleName());
            }
        });
    }

    private java.util.List<spoon.reflect.reference.CtExecutableReference<?>> getCtConstructorsByCtConstructor(spoon.reflect.declaration.CtConstructor<?> aConstructor) {
        if ((aConstructor.getBody().getStatements().size()) == 0) {
            return new java.util.ArrayList<>();
        }
        if (!((aConstructor.getBody().getStatement(0)) instanceof spoon.reflect.code.CtInvocation)) {
            return new java.util.ArrayList<>();
        }
        final spoon.reflect.code.CtInvocation inv = aConstructor.getBody().getStatement(0);
        if (!(inv.getExecutable().getSimpleName().equals(spoon.reflect.reference.CtExecutableReference.CONSTRUCTOR_NAME))) {
            return new java.util.ArrayList<>();
        }
        return inv.getExecutable().getElements(new spoon.reflect.visitor.filter.AbstractReferenceFilter<spoon.reflect.reference.CtExecutableReference<?>>(spoon.reflect.reference.CtExecutableReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtExecutableReference<?> reference) {
                return reference.isConstructor();
            }
        });
    }

    private java.util.List<spoon.reflect.reference.CtExecutableReference<?>> getCtConstructorsReferencedInCtConstructor(spoon.reflect.declaration.CtConstructor<?> aConstructor) {
        return aConstructor.getElements(new spoon.reflect.visitor.filter.AbstractReferenceFilter<spoon.reflect.reference.CtExecutableReference<?>>(spoon.reflect.reference.CtExecutableReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtExecutableReference<?> reference) {
                return reference.isConstructor();
            }
        });
    }

    private spoon.reflect.declaration.CtClass<?> getCtClassByName(final java.lang.String name) {
        return spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtClass<?>>() {
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtClass<?> element) {
                return name.equals(element.getSimpleName());
            }
        }).get(0);
    }

    private java.util.List<spoon.reflect.reference.CtExecutableReference<?>> getReferencesOfAMethod(spoon.reflect.declaration.CtMethod<?> method1) {
        return method1.getElements(new spoon.reflect.visitor.filter.ReferenceTypeFilter<spoon.reflect.reference.CtExecutableReference<?>>(spoon.reflect.reference.CtExecutableReference.class));
    }

    private spoon.reflect.declaration.CtMethod<?> getCtMethodByNameFromCtClass(spoon.reflect.declaration.CtClass<?> clazz, java.lang.String nameMethod5) {
        return clazz.getMethodsByName(nameMethod5).get(0);
    }
}

