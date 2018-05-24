package spoon.test.refactoring;


public class RefactoringTest {
    @org.junit.Test
    public void testRefactoringClassChangeAllCtTypeReferenceAssociatedWithClassConcerned() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "-i", "src/test/java/spoon/test/refactoring/testclasses", "-o", "target/spooned/refactoring" });
        launcher.run();
        final spoon.reflect.declaration.CtClass<?> aClass = launcher.getFactory().Class().get(spoon.test.refactoring.testclasses.AClass.class);
        org.junit.Assert.assertNotNull(aClass);
        launcher.setArgs(new java.lang.String[]{ "-i", "src/test/java/spoon/test/refactoring/testclasses", "-o", "target/spooned/refactoring", "-p", spoon.test.refactoring.ThisTransformationProcessor.class.getName() });
        launcher.run();
        final spoon.reflect.declaration.CtClass<?> classNotAccessible = launcher.getFactory().Class().get(spoon.test.refactoring.testclasses.AClass.class);
        org.junit.Assert.assertNull(launcher.getFactory().Class().get("spoon.test.refactoring.testclasses.AClass"));
        org.junit.Assert.assertNotNull(classNotAccessible);
        final spoon.reflect.declaration.CtClass<?> aClassX = launcher.getFactory().Class().get("spoon.test.refactoring.testclasses.AClassX");
        org.junit.Assert.assertNotNull(aClassX);
        final java.util.List<spoon.reflect.reference.CtTypeReference<?>> references = spoon.reflect.visitor.Query.getElements(aClassX.getFactory(), new spoon.reflect.visitor.filter.AbstractReferenceFilter<spoon.reflect.reference.CtTypeReference<?>>(spoon.reflect.reference.CtTypeReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtTypeReference<?> reference) {
                return aClassX.getQualifiedName().equals(reference.getQualifiedName());
            }
        });
        org.junit.Assert.assertNotEquals(0, references.size());
        for (spoon.reflect.reference.CtTypeReference<?> reference : references) {
            org.junit.Assert.assertEquals("AClassX", reference.getSimpleName());
        }
    }

    @org.junit.Test
    public void testThisInConstructor() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "-i", "src/test/java/spoon/test/refactoring/testclasses", "-o", "target/spooned/refactoring" });
        launcher.run();
        final spoon.reflect.declaration.CtClass<?> aClass = ((spoon.reflect.declaration.CtClass<?>) (launcher.getFactory().Type().get(spoon.test.refactoring.testclasses.AClass.class)));
        final spoon.reflect.code.CtInvocation<?> thisInvocation = aClass.getElements(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtInvocation<?>>(spoon.reflect.code.CtInvocation.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtInvocation<?> element) {
                return element.getExecutable().isConstructor();
            }
        }).get(0);
        org.junit.Assert.assertEquals("this(\"\")", thisInvocation.toString());
    }

    @org.junit.Test
    public void testThisInConstructorAfterATransformation() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "-i", "src/test/java/spoon/test/refactoring/testclasses", "-o", "target/spooned/refactoring", "-p", spoon.test.refactoring.ThisTransformationProcessor.class.getName() });
        launcher.run();
        final spoon.reflect.declaration.CtClass<?> aClassX = ((spoon.reflect.declaration.CtClass<?>) (launcher.getFactory().Type().get("spoon.test.refactoring.testclasses.AClassX")));
        final spoon.reflect.code.CtInvocation<?> thisInvocation = aClassX.getElements(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtInvocation<?>>(spoon.reflect.code.CtInvocation.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtInvocation<?> element) {
                return element.getExecutable().isConstructor();
            }
        }).get(0);
        org.junit.Assert.assertEquals("this(\"\")", thisInvocation.toString());
    }

    @org.junit.Test
    public void testTransformedInstanceofAfterATransformation() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "-i", "src/test/java/spoon/test/refactoring/testclasses", "-o", "target/spooned/refactoring", "-p", spoon.test.refactoring.ThisTransformationProcessor.class.getName() });
        launcher.run();
        final spoon.reflect.declaration.CtClass<?> aClassX = ((spoon.reflect.declaration.CtClass<?>) (launcher.getFactory().Type().get("spoon.test.refactoring.testclasses.AClassX")));
        final spoon.reflect.code.CtBinaryOperator<?> instanceofInvocation = aClassX.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtBinaryOperator<?>>(spoon.reflect.code.CtBinaryOperator.class)).get(0);
        org.junit.Assert.assertEquals(spoon.reflect.code.BinaryOperatorKind.INSTANCEOF, instanceofInvocation.getKind());
        org.junit.Assert.assertEquals("o", instanceofInvocation.getLeftHandOperand().toString());
        org.junit.Assert.assertEquals("spoon.test.refactoring.testclasses.AClassX", instanceofInvocation.getRightHandOperand().toString());
    }
}

