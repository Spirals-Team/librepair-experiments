package spoon.test.compilationunit;


public class GetBinaryFilesTest {
    @org.junit.Rule
    public org.junit.rules.TemporaryFolder tmpFolder = new org.junit.rules.TemporaryFolder();

    @org.junit.Test
    public void testSingleBinary() {
        final java.lang.String input = "./src/test/resources/compilation/compilation-tests/IBar.java";
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource(input);
        launcher.setBinaryOutputDirectory(tmpFolder.getRoot());
        launcher.buildModel();
        launcher.getModelBuilder().compile(spoon.SpoonModelBuilder.InputType.FILES);
        final java.util.Map<java.lang.String, spoon.reflect.cu.CompilationUnit> cus = launcher.getFactory().CompilationUnit().getMap();
        org.junit.Assert.assertEquals(1, cus.size());
        final java.util.List<java.io.File> binaries = cus.values().iterator().next().getBinaryFiles();
        org.junit.Assert.assertEquals(1, binaries.size());
        org.junit.Assert.assertEquals("IBar.class", binaries.get(0).getName());
        org.junit.Assert.assertTrue(binaries.get(0).isFile());
    }

    @org.junit.Test
    public void testExistingButNotBuiltBinary() throws java.io.IOException {
        tmpFolder.newFolder("compilation");
        tmpFolder.newFile("compilation/IBar$Test.class");
        final java.lang.String input = "./src/test/resources/compilation/compilation-tests/IBar.java";
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource(input);
        launcher.setBinaryOutputDirectory(tmpFolder.getRoot());
        launcher.buildModel();
        launcher.getModelBuilder().compile(spoon.SpoonModelBuilder.InputType.FILES);
        final java.util.Map<java.lang.String, spoon.reflect.cu.CompilationUnit> cus = launcher.getFactory().CompilationUnit().getMap();
        org.junit.Assert.assertEquals(1, cus.size());
        final java.util.List<java.io.File> binaries = cus.values().iterator().next().getBinaryFiles();
        org.junit.Assert.assertEquals(1, binaries.size());
        org.junit.Assert.assertEquals("IBar.class", binaries.get(0).getName());
        org.junit.Assert.assertTrue(binaries.get(0).isFile());
        final java.io.File[] files = binaries.get(0).getParentFile().listFiles();
        org.junit.Assert.assertNotNull(files);
        org.junit.Assert.assertEquals(2, files.length);
        org.junit.Assert.assertTrue(((files[0].getName().equals("IBar$Test.class")) || (files[1].getName().equals("IBar$Test.class"))));
    }

    @org.junit.Test
    public void testMultiClassInSingleFile() throws java.io.IOException {
        final java.lang.String input = "./src/test/resources/compilation/compilation-tests/";
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource(input);
        launcher.setBinaryOutputDirectory(tmpFolder.getRoot());
        launcher.buildModel();
        launcher.getModelBuilder().compile(spoon.SpoonModelBuilder.InputType.FILES);
        final java.util.Map<java.lang.String, spoon.reflect.cu.CompilationUnit> cus = launcher.getFactory().CompilationUnit().getMap();
        org.junit.Assert.assertEquals(2, cus.size());
        final java.util.List<java.io.File> ibarBinaries = cus.get(new java.io.File(input, "IBar.java").getCanonicalFile().getAbsolutePath()).getBinaryFiles();
        org.junit.Assert.assertEquals(1, ibarBinaries.size());
        org.junit.Assert.assertEquals("IBar.class", ibarBinaries.get(0).getName());
        org.junit.Assert.assertTrue(ibarBinaries.get(0).isFile());
        final java.util.List<java.io.File> barBinaries = cus.get(new java.io.File(input, "Bar.java").getCanonicalFile().getAbsolutePath()).getBinaryFiles();
        org.junit.Assert.assertEquals(2, barBinaries.size());
        org.junit.Assert.assertEquals("Bar.class", barBinaries.get(0).getName());
        org.junit.Assert.assertEquals("FooEx.class", barBinaries.get(1).getName());
        org.junit.Assert.assertTrue(barBinaries.get(0).isFile());
        org.junit.Assert.assertTrue(barBinaries.get(1).isFile());
    }

    @org.junit.Test
    public void testNestedTypes() throws java.io.IOException {
        final java.lang.String input = "./src/test/java/spoon/test/imports/testclasses/internal/PublicInterface2.java";
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource(input);
        launcher.setBinaryOutputDirectory(tmpFolder.getRoot());
        launcher.buildModel();
        launcher.getModelBuilder().compile(spoon.SpoonModelBuilder.InputType.FILES);
        final java.util.Map<java.lang.String, spoon.reflect.cu.CompilationUnit> cus = launcher.getFactory().CompilationUnit().getMap();
        org.junit.Assert.assertEquals(1, cus.size());
        final java.util.List<java.io.File> binaries = cus.get(new java.io.File(input).getCanonicalFile().getAbsolutePath()).getBinaryFiles();
        org.junit.Assert.assertEquals(3, binaries.size());
        org.junit.Assert.assertEquals("PublicInterface2.class", binaries.get(0).getName());
        org.junit.Assert.assertEquals("PublicInterface2$NestedInterface.class", binaries.get(1).getName());
        org.junit.Assert.assertEquals("PublicInterface2$NestedClass.class", binaries.get(2).getName());
        org.junit.Assert.assertTrue(binaries.get(0).isFile());
        org.junit.Assert.assertTrue(binaries.get(1).isFile());
        org.junit.Assert.assertTrue(binaries.get(2).isFile());
    }

    @org.junit.Test
    public void testAnonymousClasses() throws java.io.IOException {
        final java.lang.String input = "./src/test/java/spoon/test/secondaryclasses/testclasses/AnonymousClass.java";
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource(input);
        launcher.setBinaryOutputDirectory(tmpFolder.getRoot());
        launcher.buildModel();
        launcher.getModelBuilder().compile(spoon.SpoonModelBuilder.InputType.FILES);
        final java.util.Map<java.lang.String, spoon.reflect.cu.CompilationUnit> cus = launcher.getFactory().CompilationUnit().getMap();
        org.junit.Assert.assertEquals(1, cus.size());
        final java.util.List<java.io.File> binaries = cus.get(new java.io.File(input).getCanonicalFile().getAbsolutePath()).getBinaryFiles();
        org.junit.Assert.assertEquals(4, binaries.size());
        org.junit.Assert.assertEquals("AnonymousClass.class", binaries.get(0).getName());
        org.junit.Assert.assertEquals("AnonymousClass$I.class", binaries.get(1).getName());
        org.junit.Assert.assertEquals("AnonymousClass$1.class", binaries.get(2).getName());
        org.junit.Assert.assertEquals("AnonymousClass$2.class", binaries.get(3).getName());
        org.junit.Assert.assertTrue(binaries.get(0).isFile());
        org.junit.Assert.assertTrue(binaries.get(1).isFile());
        org.junit.Assert.assertTrue(binaries.get(2).isFile());
        org.junit.Assert.assertTrue(binaries.get(3).isFile());
    }
}

