package spoon.test.compilationunit;


public class TestCompilationUnit {
    @org.junit.Test
    public void testIsoEncodingIsSupported() throws java.lang.Exception {
        java.io.File resource = new java.io.File("./src/test/resources/noclasspath/IsoEncoding.java");
        java.lang.String content = new java.lang.String(java.nio.file.Files.readAllBytes(resource.toPath()), "ISO-8859-1");
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource(resource.getPath());
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setEncoding(java.nio.charset.Charset.forName("ISO-8859-1"));
        launcher.buildModel();
        spoon.reflect.cu.CompilationUnit cu = launcher.getFactory().CompilationUnit().getOrCreate(resource.getPath());
        org.junit.Assert.assertEquals(content, cu.getOriginalSourceCode());
    }

    @org.junit.Test
    public void testGetUnitTypeWorksWithDeclaredType() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/api/testclasses/Bar.java");
        launcher.buildModel();
        spoon.reflect.declaration.CtType type = launcher.getFactory().Type().get(spoon.test.api.testclasses.Bar.class);
        spoon.reflect.cu.CompilationUnit compilationUnit = type.getPosition().getCompilationUnit();
        org.junit.Assert.assertEquals(spoon.reflect.cu.CompilationUnit.UNIT_TYPE.TYPE_DECLARATION, compilationUnit.getUnitType());
    }

    @org.junit.Test
    public void testGetUnitTypeWorksWithDeclaredPackage() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/pkg/package-info.java");
        launcher.buildModel();
        spoon.reflect.declaration.CtPackage ctPackage = launcher.getFactory().Package().get("spoon.test.pkg");
        spoon.reflect.cu.CompilationUnit compilationUnit = ctPackage.getPosition().getCompilationUnit();
        org.junit.Assert.assertEquals(spoon.reflect.cu.CompilationUnit.UNIT_TYPE.PACKAGE_DECLARATION, compilationUnit.getUnitType());
    }

    @org.junit.Test
    public void testGetUnitTypeWorksWithCreatedObjects() {
        final spoon.Launcher launcher = new spoon.Launcher();
        spoon.reflect.declaration.CtPackage myPackage = launcher.getFactory().Package().getOrCreate("my.package");
        spoon.reflect.cu.CompilationUnit cu = launcher.getFactory().createCompilationUnit();
        org.junit.Assert.assertEquals(spoon.reflect.cu.CompilationUnit.UNIT_TYPE.UNKNOWN, cu.getUnitType());
        cu.setDeclaredPackage(myPackage);
        org.junit.Assert.assertEquals(spoon.reflect.cu.CompilationUnit.UNIT_TYPE.PACKAGE_DECLARATION, cu.getUnitType());
        cu.setDeclaredTypes(java.util.Collections.singletonList(launcher.getFactory().createClass()));
        org.junit.Assert.assertEquals(spoon.reflect.cu.CompilationUnit.UNIT_TYPE.TYPE_DECLARATION, cu.getUnitType());
    }

    @org.junit.Test
    public void testCompilationUnitDeclaredTypes() throws java.io.IOException {
        java.io.File resource = new java.io.File("./src/test/java/spoon/test/model/Foo.java");
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource(resource.getPath());
        launcher.buildModel();
        spoon.reflect.cu.CompilationUnit cu = launcher.getFactory().CompilationUnit().getOrCreate(resource.getCanonicalPath());
        org.junit.Assert.assertEquals(3, cu.getDeclaredTypes().size());
        java.util.List<spoon.reflect.declaration.CtType<?>> typeList = cu.getDeclaredTypes();
        try {
            typeList.remove(0);
            org.junit.Assert.fail();
        } catch (java.lang.UnsupportedOperationException e) {
        }
    }

    @org.junit.Test
    public void testAddDeclaredTypeInCU() throws java.io.IOException {
        java.io.File resource = new java.io.File("./src/test/java/spoon/test/model/Foo.java");
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "compilationunits" });
        launcher.addInputResource(resource.getPath());
        launcher.setSourceOutputDirectory("./target/cu-onemoretype");
        launcher.buildModel();
        spoon.reflect.cu.CompilationUnit cu = launcher.getFactory().CompilationUnit().getOrCreate(resource.getCanonicalPath());
        org.junit.Assert.assertEquals(3, cu.getDeclaredTypes().size());
        spoon.reflect.declaration.CtType typeBla = launcher.getFactory().Class().create("spoon.test.model.Bla");
        cu.addDeclaredType(typeBla);
        org.junit.Assert.assertEquals(4, cu.getDeclaredTypes().size());
        launcher.prettyprint();
        java.io.File output = new java.io.File("./target/cu-onemoretype/spoon/test/model/Foo.java");
        java.util.List<java.lang.String> lines = java.nio.file.Files.readAllLines(output.toPath());
        java.lang.String fullContent = org.apache.commons.lang3.StringUtils.join(lines, "\n");
        org.junit.Assert.assertTrue(fullContent.contains("public class Foo"));
        org.junit.Assert.assertTrue(fullContent.contains("class Bar"));
        org.junit.Assert.assertTrue(fullContent.contains("class Baz"));
        org.junit.Assert.assertTrue(fullContent.contains("class Bla"));
    }

    @org.junit.Test
    public void testNewlyCreatedCUWouldGetAPartialPosition() throws java.io.IOException {
        final spoon.Launcher launcher = new spoon.Launcher();
        org.junit.Assert.assertTrue(launcher.getFactory().CompilationUnit().getMap().isEmpty());
        spoon.reflect.declaration.CtClass myNewClass = launcher.getFactory().createClass("my.new.MyClass");
        org.junit.Assert.assertEquals(spoon.reflect.cu.SourcePosition.NOPOSITION, myNewClass.getPosition());
        spoon.reflect.cu.CompilationUnit cu = launcher.getFactory().CompilationUnit().getOrCreate(myNewClass);
        org.junit.Assert.assertNotNull(cu);
        org.junit.Assert.assertSame(cu, launcher.getFactory().CompilationUnit().getOrCreate(myNewClass));
        spoon.reflect.cu.SourcePosition sourcePosition = myNewClass.getPosition();
        org.junit.Assert.assertTrue((sourcePosition instanceof spoon.support.reflect.cu.position.PartialSourcePositionImpl));
        org.junit.Assert.assertSame(cu, sourcePosition.getCompilationUnit());
        java.io.File f = new java.io.File(spoon.Launcher.OUTPUTDIR, "my/new/MyClass.java");
        org.junit.Assert.assertEquals(f.getCanonicalFile(), cu.getFile());
    }
}

