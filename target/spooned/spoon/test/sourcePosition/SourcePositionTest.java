package spoon.test.sourcePosition;


public class SourcePositionTest {
    @org.junit.Test
    public void equalPositionsHaveSameHashcode() throws java.lang.Exception {
        java.lang.String packageName = "spoon.test.testclasses";
        java.lang.String sampleClassName = "SampleClass";
        java.lang.String qualifiedName = (packageName + ".") + sampleClassName;
        spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtMethod<?>> methodFilter = new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtMethod<?>>(spoon.reflect.declaration.CtMethod.class);
        spoon.reflect.factory.Factory aFactory = factoryFor(packageName, sampleClassName);
        java.util.List<spoon.reflect.declaration.CtMethod<?>> methods = aFactory.Class().get(qualifiedName).getElements(methodFilter);
        spoon.reflect.factory.Factory newInstanceOfSameFactory = factoryFor(packageName, sampleClassName);
        java.util.List<spoon.reflect.declaration.CtMethod<?>> newInstanceOfSameMethods = newInstanceOfSameFactory.Class().get(qualifiedName).getElements(methodFilter);
        org.junit.Assert.assertEquals(methods.size(), newInstanceOfSameMethods.size());
        for (int i = 0; i < (methods.size()); i += 1) {
            spoon.reflect.cu.SourcePosition aPosition = methods.get(i).getPosition();
            spoon.reflect.cu.SourcePosition newInstanceOfSamePosition = newInstanceOfSameMethods.get(i).getPosition();
            org.junit.Assert.assertTrue(aPosition.equals(newInstanceOfSamePosition));
            org.junit.Assert.assertEquals(aPosition.hashCode(), newInstanceOfSamePosition.hashCode());
        }
    }

    private spoon.reflect.factory.Factory factoryFor(java.lang.String packageName, java.lang.String className) throws java.lang.Exception {
        return spoon.testing.utils.ModelUtils.build(packageName, className).getFactory();
    }

    @org.junit.Test
    public void testSourcePositionOfSecondPrimitiveType() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> type = spoon.testing.utils.ModelUtils.buildClass(spoon.test.sourcePosition.testclasses.Brambora.class);
        spoon.reflect.code.CtInvocation<?> invocation = type.getMethodsByName("sourcePositionOfMyReturnTypeMustNotBeCopied").get(0).getBody().getStatement(0);
        spoon.reflect.reference.CtExecutableReference<?> execRef = invocation.getExecutable();
        spoon.reflect.reference.CtTypeReference<?> typeOfReturnValueOfPrintln = execRef.getType();
        org.junit.Assert.assertEquals("void", typeOfReturnValueOfPrintln.getQualifiedName());
        spoon.reflect.cu.SourcePosition sp = typeOfReturnValueOfPrintln.getPosition();
        if (sp.isValidPosition()) {
            org.junit.Assert.fail((((("The source position of invisible implicit reference to void is: [" + (sp.getSourceStart())) + "; ") + (sp.getSourceEnd())) + "]"));
        }
    }

    @org.junit.Test
    public void testSourcePositionStringFragment() throws java.lang.Exception {
        spoon.reflect.cu.CompilationUnit cu = new spoon.support.reflect.cu.CompilationUnitImpl() {
            @java.lang.Override
            public java.lang.String getOriginalSourceCode() {
                return "0123456789";
            }
        };
        spoon.support.reflect.cu.position.SourcePositionImpl sp = new spoon.support.reflect.cu.position.SourcePositionImpl(cu, 1, 9, null);
        org.junit.Assert.assertEquals("|1;9|123456789|", sp.getSourceDetails());
        spoon.support.reflect.cu.position.DeclarationSourcePositionImpl dsp = new spoon.support.reflect.cu.position.DeclarationSourcePositionImpl(cu, 4, 7, 2, 2, 1, 9, null);
        org.junit.Assert.assertEquals(("|1;9|123456789|\n" + ("modifier = |2;2|2|\n" + "name = |4;7|4567|")), dsp.getSourceDetails());
        spoon.support.reflect.cu.position.BodyHolderSourcePositionImpl bhsp = new spoon.support.reflect.cu.position.BodyHolderSourcePositionImpl(cu, 4, 7, 2, 2, 1, 9, 8, 9, null);
        org.junit.Assert.assertEquals(("|1;9|123456789|\n" + (("modifier = |2;2|2|\n" + "name = |4;7|4567|\n") + "body = |8;9|89|")), bhsp.getSourceDetails());
    }
}

