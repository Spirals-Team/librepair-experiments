package spoon.test.support;


public class ResourceTest {
    @org.junit.Test
    public void testEqualsFileSystemFile() throws java.lang.Exception {
        java.lang.String entry = "src/test/resources/spoon/test/api/Foo.java";
        org.junit.Assert.assertTrue(new spoon.support.compiler.FileSystemFile(new java.io.File(entry)).equals(new spoon.support.compiler.FileSystemFile(new java.io.File(("./" + entry)))));
    }

    @org.junit.Test
    public void testFileSystemFolder() throws java.lang.Exception {
        java.lang.String dir = "src/test/resources/spoon/test/api/";
        spoon.support.compiler.FileSystemFolder fileSystemFolder = new spoon.support.compiler.FileSystemFolder(new java.io.File(dir));
        org.junit.Assert.assertEquals(2, fileSystemFolder.getAllFiles().size());
        org.junit.Assert.assertEquals(2, fileSystemFolder.getAllJavaFiles().size());
        java.lang.String entry = "src/test/resources/spoon/test/api/Foo.java";
        spoon.support.compiler.FileSystemFile file = new spoon.support.compiler.FileSystemFile(new java.io.File(entry));
        java.lang.String entry1 = "src/test/resources/spoon/test/api/CommentedClass.java";
        spoon.support.compiler.FileSystemFile file1 = new spoon.support.compiler.FileSystemFile(new java.io.File(entry1));
        org.junit.Assert.assertThat(fileSystemFolder.getAllFiles().contains(file), org.hamcrest.CoreMatchers.is(true));
        org.junit.Assert.assertThat(fileSystemFolder.getAllFiles().contains(file1), org.hamcrest.CoreMatchers.is(true));
    }

    @org.junit.Test
    public void testVirtualFolder() throws java.lang.Exception {
        java.lang.String dir = "src/test/resources/spoon/test/api/";
        spoon.support.compiler.FileSystemFolder fileSystemFolder = new spoon.support.compiler.FileSystemFolder(new java.io.File(dir));
        java.lang.String dir2 = "src/test/resources/spoon/test/exceptions/";
        spoon.support.compiler.FileSystemFolder fileSystemFolder2 = new spoon.support.compiler.FileSystemFolder(new java.io.File(dir2));
        spoon.compiler.SpoonFolder folder = new spoon.support.compiler.VirtualFolder();
        folder.addFolder(fileSystemFolder);
        folder.addFolder(fileSystemFolder2);
        org.junit.Assert.assertEquals(4, folder.getAllFiles().size());
        org.junit.Assert.assertEquals(3, folder.getAllJavaFiles().size());
    }

    @org.junit.Test
    public void testFilteringFolder() throws java.lang.Exception {
        spoon.SpoonModelBuilder mb = new spoon.Launcher().getModelBuilder();
        spoon.support.compiler.FilteringFolder resources = new spoon.support.compiler.FilteringFolder();
        resources.addFolder(new spoon.support.compiler.FileSystemFolder("src/test/java/spoon/test/visibility/"));
        mb.addInputSource(resources);
        mb.build();
        int nbAll = mb.getFactory().getModel().getAllTypes().size();
        org.junit.Assert.assertEquals(12, nbAll);
        spoon.SpoonModelBuilder mb2 = new spoon.Launcher().getModelBuilder();
        spoon.support.compiler.FilteringFolder resources2 = new spoon.support.compiler.FilteringFolder();
        resources2.addFolder(new spoon.support.compiler.FileSystemFolder("src/test/java/spoon/test/visibility/packageprotected/"));
        mb2.addInputSource(resources2);
        mb2.build();
        int nbPackageProtected = mb2.getFactory().getModel().getAllTypes().size();
        org.junit.Assert.assertEquals(2, nbPackageProtected);
        spoon.SpoonModelBuilder mb3 = new spoon.Launcher().getModelBuilder();
        spoon.support.compiler.FilteringFolder resources3 = new spoon.support.compiler.FilteringFolder();
        resources3.addFolder(new spoon.support.compiler.FileSystemFolder("src/test/java/spoon/test/visibility/"));
        resources3.removeAllThatMatch(".*packageprotected.*");
        mb3.addInputSource(resources3);
        mb3.build();
        org.junit.Assert.assertEquals((nbAll - nbPackageProtected), mb3.getFactory().getModel().getAllTypes().size());
    }
}

