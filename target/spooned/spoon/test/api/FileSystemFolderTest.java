package spoon.test.api;


import spoon.Launcher;


public class FileSystemFolderTest {
    @org.junit.Test
    public void jarFileIsNotSubfolder() {
        java.lang.String folderPath = "./src/test/resources/folderWithJar";
        spoon.support.compiler.FileSystemFolder folder = new spoon.support.compiler.FileSystemFolder(new java.io.File(folderPath));
        java.util.List<spoon.compiler.SpoonFolder> subFolders = folder.getSubFolders();
        org.junit.Assert.assertTrue(subFolders.isEmpty());
    }

    @org.junit.Test
    public void testLauncherWithWrongPathAsInput() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/wrong/direction/File.java");
        try {
            spoon.buildModel();
        } catch (spoon.SpoonException spe) {
            java.lang.Throwable containedException = spe.getCause().getCause();
            org.junit.Assert.assertTrue((containedException instanceof java.io.FileNotFoundException));
        }
    }
}

