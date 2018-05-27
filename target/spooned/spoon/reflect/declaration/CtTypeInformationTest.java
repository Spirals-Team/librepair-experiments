package spoon.reflect.declaration;


public class CtTypeInformationTest {
    private spoon.reflect.factory.Factory factory;

    @org.junit.Before
    public void setUp() throws java.lang.Exception {
        factory = spoon.testing.utils.ModelUtils.build(spoon.reflect.declaration.testclasses.ExtendsArrayList.class, spoon.reflect.declaration.testclasses.Subclass.class, spoon.reflect.declaration.testclasses.Subinterface.class, spoon.reflect.declaration.testclasses.TestInterface.class);
    }

    @org.junit.Test
    public void testClassTypingContextContinueScanning() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<?> subClass = this.factory.Type().get(spoon.reflect.declaration.testclasses.Subclass.class);
        final spoon.reflect.reference.CtTypeReference<?> subinterface = this.factory.Type().createReference(spoon.reflect.declaration.testclasses.Subinterface.class);
        final spoon.reflect.reference.CtTypeReference<?> testInterface = this.factory.Type().createReference(spoon.reflect.declaration.testclasses.TestInterface.class);
        final spoon.reflect.reference.CtTypeReference<?> extendObject = this.factory.Type().createReference(spoon.reflect.declaration.testclasses.ExtendsArrayList.class);
        final spoon.reflect.reference.CtTypeReference<?> arrayList = this.factory.Type().createReference(java.util.ArrayList.class);
        final spoon.reflect.reference.CtTypeReference<?> abstractList = this.factory.Type().createReference(java.util.AbstractList.class);
        final spoon.reflect.reference.CtTypeReference<?> abstractCollection = this.factory.Type().createReference(java.util.AbstractCollection.class);
        final spoon.reflect.reference.CtTypeReference<?> object = this.factory.Type().createReference(java.lang.Object.class);
        {
            final spoon.support.visitor.ClassTypingContext ctc = ((spoon.support.visitor.ClassTypingContext) (this.factory.createTypeAdapter(subClass)));
            org.junit.Assert.assertEquals(subClass.getQualifiedName(), getLastResolvedSuperclass(ctc).getQualifiedName());
            org.junit.Assert.assertTrue(ctc.isSubtypeOf(subClass.getReference()));
            org.junit.Assert.assertEquals(subClass.getQualifiedName(), getLastResolvedSuperclass(ctc).getQualifiedName());
            org.junit.Assert.assertTrue(ctc.isSubtypeOf(subinterface));
            org.junit.Assert.assertEquals(subClass.getQualifiedName(), getLastResolvedSuperclass(ctc).getQualifiedName());
            org.junit.Assert.assertTrue(ctc.isSubtypeOf(testInterface));
            org.junit.Assert.assertEquals(subClass.getQualifiedName(), getLastResolvedSuperclass(ctc).getQualifiedName());
            org.junit.Assert.assertTrue(ctc.isSubtypeOf(factory.createCtTypeReference(java.lang.Comparable.class)));
            org.junit.Assert.assertEquals(subClass.getQualifiedName(), getLastResolvedSuperclass(ctc).getQualifiedName());
            org.junit.Assert.assertTrue(ctc.isSubtypeOf(extendObject));
            org.junit.Assert.assertEquals(extendObject.getQualifiedName(), getLastResolvedSuperclass(ctc).getQualifiedName());
            org.junit.Assert.assertTrue(ctc.isSubtypeOf(arrayList));
            org.junit.Assert.assertTrue(ctc.isSubtypeOf(extendObject));
            org.junit.Assert.assertTrue(ctc.isSubtypeOf(subClass.getReference()));
            org.junit.Assert.assertEquals(arrayList.getQualifiedName(), getLastResolvedSuperclass(ctc).getQualifiedName());
            org.junit.Assert.assertTrue(ctc.isSubtypeOf(factory.createCtTypeReference(java.util.RandomAccess.class)));
            org.junit.Assert.assertEquals(arrayList.getQualifiedName(), getLastResolvedSuperclass(ctc).getQualifiedName());
            org.junit.Assert.assertTrue(ctc.isSubtypeOf(abstractList));
            org.junit.Assert.assertEquals(abstractList.getQualifiedName(), getLastResolvedSuperclass(ctc).getQualifiedName());
            org.junit.Assert.assertTrue(ctc.isSubtypeOf(abstractCollection));
            org.junit.Assert.assertEquals(abstractCollection.getQualifiedName(), getLastResolvedSuperclass(ctc).getQualifiedName());
            org.junit.Assert.assertTrue(ctc.isSubtypeOf(object));
            org.junit.Assert.assertEquals(object.getQualifiedName(), getLastResolvedSuperclass(ctc).getQualifiedName());
            org.junit.Assert.assertFalse(ctc.isSubtypeOf(factory.Type().createReference("java.io.InputStream")));
            org.junit.Assert.assertNull(getLastResolvedSuperclass(ctc));
            org.junit.Assert.assertTrue(ctc.isSubtypeOf(arrayList));
            org.junit.Assert.assertTrue(ctc.isSubtypeOf(extendObject));
            org.junit.Assert.assertTrue(ctc.isSubtypeOf(subClass.getReference()));
        }
        {
            final spoon.support.visitor.ClassTypingContext ctc2 = ((spoon.support.visitor.ClassTypingContext) (this.factory.createTypeAdapter(subClass)));
            org.junit.Assert.assertEquals(subClass.getQualifiedName(), getLastResolvedSuperclass(ctc2).getQualifiedName());
            org.junit.Assert.assertFalse(ctc2.isSubtypeOf(factory.Type().createReference("java.io.InputStream")));
            org.junit.Assert.assertNull(getLastResolvedSuperclass(ctc2));
            org.junit.Assert.assertTrue(ctc2.isSubtypeOf(arrayList));
            org.junit.Assert.assertTrue(ctc2.isSubtypeOf(extendObject));
            org.junit.Assert.assertTrue(ctc2.isSubtypeOf(subClass.getReference()));
        }
    }

    private spoon.reflect.declaration.CtTypeInformation getLastResolvedSuperclass(spoon.support.visitor.ClassTypingContext ctc) throws java.lang.Exception {
        java.lang.reflect.Field f = spoon.support.visitor.ClassTypingContext.class.getDeclaredField("lastResolvedSuperclass");
        f.setAccessible(true);
        return ((spoon.reflect.declaration.CtTypeInformation) (f.get(ctc)));
    }

    @org.junit.Test
    public void testGetAllMethodsReturnsTheRightNumber() {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/ExtendsObject.java");
        launcher.buildModel();
        int nbMethodsObject = launcher.getFactory().Type().get(java.lang.Object.class).getAllMethods().size();
        final spoon.reflect.declaration.CtType<?> extendsObject = launcher.getFactory().Type().get("test.ExtendsObject");
        org.junit.Assert.assertEquals(("It should contain only 'oneMethod' and 'toString' but also contains: " + (org.apache.commons.lang3.StringUtils.join(extendsObject.getMethods(), "\n"))), 2, extendsObject.getMethods().size());
        org.junit.Assert.assertEquals((nbMethodsObject + 1), extendsObject.getAllMethods().size());
    }

    @org.junit.Test
    public void testGetSuperclass() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<?> extendsArrayList = this.factory.Type().get(spoon.reflect.declaration.testclasses.ExtendsArrayList.class);
        org.junit.Assert.assertEquals(1, extendsArrayList.getMethods().size());
        int nbMethodExtendedArrayList = extendsArrayList.getAllMethods().size();
        final spoon.reflect.declaration.CtType<?> subClass = this.factory.Type().get(spoon.reflect.declaration.testclasses.Subclass.class);
        org.junit.Assert.assertEquals(2, subClass.getMethods().size());
        org.junit.Assert.assertEquals((nbMethodExtendedArrayList + 2), subClass.getAllMethods().size());
        spoon.reflect.reference.CtTypeReference<?> superclass = subClass.getSuperclass();
        org.junit.Assert.assertEquals(spoon.reflect.declaration.testclasses.ExtendsArrayList.class.getName(), superclass.getQualifiedName());
        org.junit.Assert.assertEquals(spoon.reflect.declaration.testclasses.ExtendsArrayList.class.getName(), superclass.getQualifiedName());
        org.junit.Assert.assertNotNull(superclass.getSuperclass());
        java.util.Set<spoon.reflect.reference.CtTypeReference<?>> superInterfaces = subClass.getSuperInterfaces();
        org.junit.Assert.assertEquals(1, superInterfaces.size());
        spoon.reflect.reference.CtTypeReference<?> superinterface = superInterfaces.iterator().next();
        org.junit.Assert.assertEquals(spoon.reflect.declaration.testclasses.Subinterface.class.getName(), superinterface.getQualifiedName());
        org.junit.Assert.assertNull(superinterface.getSuperclass());
        final spoon.reflect.declaration.CtType<?> type2 = this.factory.Type().get(spoon.reflect.declaration.testclasses.Subinterface.class);
        org.junit.Assert.assertNull(type2.getSuperclass());
        spoon.reflect.declaration.CtMethod<?> fooConcrete = subClass.getMethodsByName("foo").get(0);
        spoon.reflect.declaration.CtMethod<?> fooAbstract = type2.getMethodsByName("foo").get(0);
        org.junit.Assert.assertEquals(fooConcrete.getSignature(), fooAbstract.getSignature());
        org.junit.Assert.assertNotEquals(fooConcrete, fooAbstract);
        org.junit.Assert.assertEquals(subClass.getMethodsByName("foo").get(0).getSignature(), type2.getMethodsByName("foo").get(0).getSignature());
    }

    @org.junit.Test
    public void testGetAllMethodsWontReturnOverriddenMethod() {
        final spoon.reflect.declaration.CtType<?> subClass = this.factory.Type().get(spoon.reflect.declaration.testclasses.Subclass.class);
        java.util.Set<spoon.reflect.declaration.CtMethod<?>> listCtMethods = subClass.getAllMethods();
        boolean detectedCompareTo = false;
        for (spoon.reflect.declaration.CtMethod<?> ctMethod : listCtMethods) {
            if (ctMethod.getSimpleName().equals("compareTo")) {
                org.junit.Assert.assertFalse(ctMethod.hasModifier(spoon.reflect.declaration.ModifierKind.ABSTRACT));
                org.junit.Assert.assertFalse(((ctMethod.getParameters().get(0).getType()) instanceof spoon.reflect.declaration.CtTypeParameter));
                org.junit.Assert.assertEquals("Object", ctMethod.getParameters().get(0).getType().getSimpleName());
                detectedCompareTo = true;
            }
        }
        org.junit.Assert.assertTrue(detectedCompareTo);
    }
}

