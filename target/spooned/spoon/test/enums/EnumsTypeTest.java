package spoon.test.enums;


public class EnumsTypeTest {
    @org.junit.Test
    public void testEnumsType() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/reference-test/EnumsRef.java");
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        java.util.List<spoon.compiler.SpoonResource> classpath = spoon.compiler.SpoonResourceHelper.resources("./src/test/resources/reference-test/EnumJar.jar");
        java.lang.String[] dependencyClasspath = new java.lang.String[]{ classpath.get(0).getPath() };
        factory.getEnvironment().setSourceClasspath(dependencyClasspath);
        org.junit.Assert.assertEquals(1, classpath.size());
        launcher.buildModel();
        java.util.List<spoon.reflect.code.CtAssignment> assignments = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtAssignment.class));
        spoon.reflect.reference.CtTypeReference typeRefFromSource = assignments.get(0).getType();
        spoon.reflect.declaration.CtType typeFromSource = typeRefFromSource.getTypeDeclaration();
        org.junit.Assert.assertTrue(typeRefFromSource.isEnum());
        org.junit.Assert.assertTrue(typeFromSource.isEnum());
        org.junit.Assert.assertTrue((typeFromSource instanceof spoon.reflect.declaration.CtEnum));
        spoon.reflect.reference.CtTypeReference typeRefFromJar = assignments.get(1).getType();
        spoon.reflect.declaration.CtType typeFromJar = typeRefFromJar.getTypeDeclaration();
        org.junit.Assert.assertTrue(typeRefFromJar.isEnum());
        org.junit.Assert.assertTrue(typeFromJar.isEnum());
        org.junit.Assert.assertTrue((typeFromJar instanceof spoon.reflect.declaration.CtEnum));
    }

    @org.junit.Test
    public void testEnumsFromInterface() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/reference-test/InterfaceWithEnum.java");
        launcher.addInputResource("./src/test/resources/reference-test/InterfaceEnumRef.java");
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        java.util.List<spoon.compiler.SpoonResource> classpath = spoon.compiler.SpoonResourceHelper.resources("./src/test/resources/reference-test/InterfaceWithEnumJar.jar");
        java.lang.String[] dependencyClasspath = new java.lang.String[]{ classpath.get(0).getPath() };
        factory.getEnvironment().setSourceClasspath(dependencyClasspath);
        org.junit.Assert.assertEquals(1, classpath.size());
        launcher.buildModel();
        java.util.List<spoon.reflect.code.CtAssignment> assignments = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtAssignment.class));
        spoon.reflect.reference.CtTypeReference typeRefFromSource = assignments.get(0).getType();
        spoon.reflect.declaration.CtType typeFromSource = typeRefFromSource.getTypeDeclaration();
        org.junit.Assert.assertTrue(typeRefFromSource.isEnum());
        org.junit.Assert.assertTrue(typeFromSource.isEnum());
        org.junit.Assert.assertTrue((typeFromSource instanceof spoon.reflect.declaration.CtEnum));
        spoon.reflect.reference.CtTypeReference typeRefFromJar = assignments.get(1).getType();
        spoon.reflect.declaration.CtType typeFromJar = typeRefFromJar.getTypeDeclaration();
        org.junit.Assert.assertTrue(typeRefFromJar.isEnum());
        org.junit.Assert.assertTrue(typeFromJar.isEnum());
        org.junit.Assert.assertTrue((typeFromJar instanceof spoon.reflect.declaration.CtEnum));
    }
}

