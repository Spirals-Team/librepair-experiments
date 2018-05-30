package spoon.test.secondaryclasses;


public class ClassesTest {
    @org.junit.Test
    public void testClassWithInternalPublicClassOrInterf() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.secondaryclasses", "ClassWithInternalPublicClassOrInterf");
        org.junit.Assert.assertEquals("ClassWithInternalPublicClassOrInterf", type.getSimpleName());
        org.junit.Assert.assertEquals(3, type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtType<?>>(spoon.reflect.declaration.CtType.class)).size());
        org.junit.Assert.assertEquals(2, type.getNestedTypes().size());
        org.junit.Assert.assertTrue(type.getNestedTypes().contains(type.getFactory().Class().get(spoon.test.secondaryclasses.ClassWithInternalPublicClassOrInterf.InternalClass.class)));
        org.junit.Assert.assertEquals(1, type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtNamedElement.class, "InternalInterf")).size());
    }

    @org.junit.Test
    public void testAnonymousClass() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.secondaryclasses.testclasses", "AnonymousClass");
        org.junit.Assert.assertEquals("AnonymousClass", type.getSimpleName());
        spoon.reflect.code.CtNewClass<?> x = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtNewClass<?>>(spoon.reflect.code.CtNewClass.class)).get(0);
        spoon.reflect.code.CtNewClass<?> y = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtNewClass<?>>(spoon.reflect.code.CtNewClass.class)).get(1);
        if ((x.getParent()) instanceof spoon.reflect.code.CtBlock) {
            spoon.reflect.code.CtNewClass<?> z = x;
            x = y;
            y = z;
        }
        spoon.reflect.declaration.CtClass<?> anonymousClass0 = x.getAnonymousClass();
        spoon.reflect.declaration.CtClass<?> anonymousClass1 = y.getAnonymousClass();
        org.junit.Assert.assertEquals("1", anonymousClass0.getSimpleName());
        org.junit.Assert.assertEquals("2", anonymousClass1.getSimpleName());
        org.junit.Assert.assertEquals("spoon.test.secondaryclasses.testclasses.AnonymousClass$1", anonymousClass0.getQualifiedName());
        org.junit.Assert.assertEquals("spoon.test.secondaryclasses.testclasses.AnonymousClass$2", anonymousClass1.getQualifiedName());
        org.junit.Assert.assertNull(x.getType().getDeclaration());
        org.junit.Assert.assertNotNull(x.getType().getTypeDeclaration());
        org.junit.Assert.assertEquals(java.awt.event.ActionListener.class, x.getType().getActualClass());
        org.junit.Assert.assertNotNull(y.getType().getDeclaration());
        org.junit.Assert.assertEquals("spoon.test.secondaryclasses.testclasses.AnonymousClass$2()", y.getExecutable().toString());
        org.junit.Assert.assertEquals(type.getFactory().Type().createReference(spoon.test.secondaryclasses.testclasses.AnonymousClass.I.class), y.getAnonymousClass().getSuperInterfaces().toArray(new spoon.reflect.reference.CtTypeReference[0])[0]);
    }

    @org.junit.Test
    public void testIsAnonymousMethodInCtClass() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.secondaryclasses.testclasses", "AnonymousClass");
        java.util.TreeSet<spoon.reflect.declaration.CtClass<?>> ts = new java.util.TreeSet<spoon.reflect.declaration.CtClass<?>>(new spoon.support.comparator.CtLineElementComparator());
        ts.addAll(type.getElements(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.declaration.CtClass<?>>(spoon.reflect.declaration.CtClass.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtClass<?> element) {
                return element.isAnonymous();
            }
        }));
        java.util.List<spoon.reflect.declaration.CtClass<?>> anonymousClass = new java.util.ArrayList<spoon.reflect.declaration.CtClass<?>>();
        anonymousClass.addAll(ts);
        org.junit.Assert.assertFalse(type.isAnonymous());
        org.junit.Assert.assertTrue(ts.first().isAnonymous());
        org.junit.Assert.assertTrue(anonymousClass.get(1).isAnonymous());
        org.junit.Assert.assertEquals(2, anonymousClass.size());
        org.junit.Assert.assertEquals(2, ts.size());
        org.junit.Assert.assertEquals("spoon.test.secondaryclasses.testclasses.AnonymousClass$1", anonymousClass.get(0).getQualifiedName());
        org.junit.Assert.assertEquals("spoon.test.secondaryclasses.testclasses.AnonymousClass$2", anonymousClass.get(1).getQualifiedName());
    }

    @org.junit.Test
    public void testTopLevel() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.secondaryclasses", "TopLevel");
        org.junit.Assert.assertEquals("TopLevel", type.getSimpleName());
        spoon.reflect.declaration.CtClass<?> x = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtClass.class, "InnerClass")).get(0);
        java.util.List<spoon.reflect.declaration.CtField<?>> fields = x.getFields();
        org.junit.Assert.assertEquals(1, fields.size());
        org.junit.Assert.assertEquals(1, fields.get(0).getType().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("?", fields.get(0).getType().getActualTypeArguments().get(0).getSimpleName());
    }

    @org.junit.Test
    public void testInnerClassContruction() throws java.lang.Exception {
        spoon.reflect.factory.Factory f = spoon.testing.utils.ModelUtils.build(spoon.test.secondaryclasses.PrivateInnerClasses.class);
        spoon.reflect.declaration.CtClass<?> c = f.Class().get(spoon.test.secondaryclasses.PrivateInnerClasses.class);
        org.junit.Assert.assertNotNull(c);
        org.junit.Assert.assertEquals(0, f.getEnvironment().getErrorCount());
    }

    @org.junit.Test
    public void testAnonymousClassInStaticField() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.secondaryclasses.testclasses.Pozole> type = spoon.testing.utils.ModelUtils.buildClass(spoon.test.secondaryclasses.testclasses.Pozole.class);
        final spoon.reflect.code.CtNewClass<?> anonymousClass = type.getField("CONFLICT_HOOK").getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtNewClass.class)).get(1);
        final spoon.reflect.code.CtVariableRead<?> ctVariableRead = anonymousClass.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtVariableRead.class)).get(2);
        final spoon.reflect.declaration.CtVariable<?> declaration = ctVariableRead.getVariable().getDeclaration();
        org.junit.Assert.assertNotNull(declaration);
        org.junit.Assert.assertEquals("int i", declaration.toString());
    }
}

