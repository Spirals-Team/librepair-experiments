package spoon.test.parameters;


public class ParameterTest {
    @org.junit.Test
    public void testParameterInNoClasspath() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/parameter");
        launcher.setSourceOutputDirectory("./target/parameter");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<java.lang.Object> aClass = launcher.getFactory().Class().get("org.eclipse.draw2d.text.FlowUtilities");
        final spoon.reflect.declaration.CtParameter<?> parameter = aClass.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtParameter.class, "font")).get(0);
        org.junit.Assert.assertEquals("font", parameter.getSimpleName());
        org.junit.Assert.assertNotNull(parameter.getType());
        org.junit.Assert.assertEquals("org.eclipse.swt.graphics.Font", parameter.getType().toString());
        org.junit.Assert.assertEquals("org.eclipse.swt.graphics.Font font", parameter.toString());
    }

    @org.junit.Test
    public void testGetParameterReferenceInLambdaNoClasspath() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/Tacos.java");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.buildModel();
        spoon.reflect.declaration.CtMethod<?> ctMethod = launcher.getFactory().Type().get("Tacos").getMethodsByName("setStarRatings").get(0);
        spoon.reflect.declaration.CtParameter ctParameter = ctMethod.getBody().getStatement(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtParameter>(spoon.reflect.declaration.CtParameter.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtParameter element) {
                return ("entryPair".equals(element.getSimpleName())) && (super.matches(element));
            }
        }).get(0);
        org.junit.Assert.assertNotNull(ctParameter.getReference());
        java.util.List<spoon.reflect.reference.CtParameterReference> elements = ctMethod.getBody().getStatement(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.reference.CtParameterReference>(spoon.reflect.reference.CtParameterReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtParameterReference element) {
                return ("entryPair".equals(element.getSimpleName())) && (super.matches(element));
            }
        });
        org.junit.Assert.assertEquals(2, elements.size());
        for (spoon.reflect.reference.CtParameterReference element : elements) {
            org.junit.Assert.assertEquals(ctParameter, element.getDeclaration());
            org.junit.Assert.assertEquals(ctParameter.getReference(), element);
        }
    }

    @org.junit.Test
    @java.lang.SuppressWarnings("unchecked")
    public void testMultiParameterLambdaTypeReference() {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/lambdas/MultiParameterLambda.java");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.buildModel();
        java.util.List<spoon.reflect.declaration.CtParameter> parameters;
        parameters = launcher.getModel().getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "stringLambda")).get(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtParameter.class));
        org.junit.Assert.assertEquals(2, parameters.size());
        for (final spoon.reflect.declaration.CtParameter param : parameters) {
            spoon.reflect.reference.CtTypeReference refType = ((spoon.reflect.reference.CtTypeReference) (param.getReference().getType()));
            org.junit.Assert.assertEquals(launcher.getFactory().Type().STRING, refType);
        }
        parameters = launcher.getModel().getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "integerLambda")).get(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtParameter.class));
        org.junit.Assert.assertEquals(2, parameters.size());
        for (final spoon.reflect.declaration.CtParameter param : parameters) {
            spoon.reflect.reference.CtTypeReference refType = ((spoon.reflect.reference.CtTypeReference) (param.getReference().getType()));
            org.junit.Assert.assertEquals(launcher.getFactory().Type().INTEGER, refType);
        }
        parameters = launcher.getModel().getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "unknownLambda")).get(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtParameter.class));
        org.junit.Assert.assertEquals(2, parameters.size());
        for (final spoon.reflect.declaration.CtParameter param : parameters) {
            spoon.reflect.reference.CtTypeReference refType = ((spoon.reflect.reference.CtTypeReference) (param.getReference().getType()));
            org.junit.Assert.assertEquals(null, refType);
        }
    }
}

