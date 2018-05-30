package spoon.test.reference;


public class AnnotationFieldReferenceTest {
    @org.junit.Test
    public void testAnnotationFieldReference() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.reference.testclasses.Parameter.class, spoon.test.reference.testclasses.Mole.class);
        final spoon.reflect.declaration.CtMethod<java.lang.Object> make = factory.Class().get(spoon.test.reference.testclasses.Mole.class).getMethod("make", factory.Type().createReference(spoon.test.reference.testclasses.Parameter.class));
        final spoon.reflect.code.CtInvocation<?> annotationInv = make.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtInvocation<?>>(spoon.reflect.code.CtInvocation.class)).get(0);
        final spoon.reflect.declaration.CtExecutable<?> executableDeclaration = annotationInv.getExecutable().getExecutableDeclaration();
        org.junit.Assert.assertNotNull(executableDeclaration);
        final spoon.reflect.declaration.CtMethod<?> value = factory.Annotation().get(spoon.test.reference.testclasses.Parameter.class).getMethod("value");
        org.junit.Assert.assertNotNull(value);
        org.junit.Assert.assertEquals(value.getSimpleName(), executableDeclaration.getSimpleName());
        org.junit.Assert.assertEquals(value.getType(), executableDeclaration.getType());
    }
}

