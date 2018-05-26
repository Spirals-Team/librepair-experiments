package spoon;


public class MavenLauncherTest {
    @org.junit.Test
    public void spoonMavenLauncherTest() {
        spoon.MavenLauncher launcher = new spoon.MavenLauncher("./", spoon.MavenLauncher.SOURCE_TYPE.APP_SOURCE);
        org.junit.Assert.assertEquals(7, launcher.getEnvironment().getSourceClasspath().length);
        org.junit.Assert.assertEquals(52, launcher.getModelBuilder().getInputSources().size());
        launcher = new spoon.MavenLauncher("./", spoon.MavenLauncher.SOURCE_TYPE.ALL_SOURCE);
        org.junit.Assert.assertTrue(("size: " + (launcher.getModelBuilder().getInputSources().size())), ((launcher.getModelBuilder().getInputSources().size()) >= 220));
        launcher = new spoon.MavenLauncher("./pom.xml", spoon.MavenLauncher.SOURCE_TYPE.APP_SOURCE);
        org.junit.Assert.assertEquals(8, launcher.getEnvironment().getComplianceLevel());
    }

    @org.junit.Test
    public void multiModulesProjectTest() {
        spoon.MavenLauncher launcher = new spoon.MavenLauncher("./src/test/resources/maven-launcher/pac4j", spoon.MavenLauncher.SOURCE_TYPE.ALL_SOURCE);
        org.junit.Assert.assertEquals(8, launcher.getEnvironment().getComplianceLevel());
        org.junit.Assert.assertEquals(0, launcher.getModelBuilder().getInputSources().size());
    }

    @org.junit.Test(expected = spoon.SpoonException.class)
    public void mavenLauncherOnANotExistingFileTest() {
        new spoon.MavenLauncher("./pomm.xml", spoon.MavenLauncher.SOURCE_TYPE.APP_SOURCE);
    }

    @org.junit.Test(expected = spoon.SpoonException.class)
    public void mavenLauncherOnDirectoryWithoutPomTest() {
        new spoon.MavenLauncher("./src", spoon.MavenLauncher.SOURCE_TYPE.APP_SOURCE);
    }

    @org.junit.Test
    public void mavenLauncherTestWithVerySimpleProject() {
        spoon.MavenLauncher launcher = new spoon.MavenLauncher("./src/test/resources/maven-launcher/very-simple", spoon.MavenLauncher.SOURCE_TYPE.ALL_SOURCE);
        org.junit.Assert.assertEquals(1, launcher.getModelBuilder().getInputSources().size());
    }

    @org.junit.Test
    public void mavenLauncherTestMultiModulesAndVariables() {
        spoon.MavenLauncher launcher = new spoon.MavenLauncher("./src/test/resources/maven-launcher/pac4j/pac4j-config", spoon.MavenLauncher.SOURCE_TYPE.ALL_SOURCE);
        java.util.List<java.lang.String> classpath = java.util.Arrays.asList(launcher.getEnvironment().getSourceClasspath());
        java.lang.String lookingFor = "junit/junit/4.12/junit-4.12.jar";
        boolean findIt = false;
        for (java.lang.String s : classpath) {
            findIt = findIt || (s.contains(lookingFor));
        }
        org.junit.Assert.assertTrue(("Content of classpath: " + (org.apache.commons.lang3.StringUtils.join(classpath, ":"))), findIt);
    }
}

