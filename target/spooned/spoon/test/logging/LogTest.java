package spoon.test.logging;


@org.junit.runner.RunWith(org.junit.runners.Parameterized.class)
public class LogTest {
    @org.junit.runners.Parameterized.Parameters
    public static java.util.Collection<java.lang.Object[]> data() {
        return java.util.Arrays.asList(new java.lang.Object[][]{ new java.lang.Object[]{ org.apache.log4j.Level.ALL, true, true, true, true }, new java.lang.Object[]{ org.apache.log4j.Level.DEBUG, true, true, true, true }, new java.lang.Object[]{ org.apache.log4j.Level.INFO, true, true, true, false }, new java.lang.Object[]{ org.apache.log4j.Level.WARN, false, true, true, false }, new java.lang.Object[]{ org.apache.log4j.Level.ERROR, false, false, true, false }, new java.lang.Object[]{ org.apache.log4j.Level.OFF, false, false, false, false } });
    }

    @org.junit.runners.Parameterized.Parameter(0)
    public org.apache.log4j.Level level;

    @org.junit.runners.Parameterized.Parameter(1)
    public boolean isInfo;

    @org.junit.runners.Parameterized.Parameter(2)
    public boolean isWarn;

    @org.junit.runners.Parameterized.Parameter(3)
    public boolean isError;

    @org.junit.runners.Parameterized.Parameter(4)
    public boolean isDebug;

    @org.junit.Test
    public void testAllLevelsForLogs() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "-i", "./src/test/java/spoon/test/logging", "--level", level.toString() });
        org.junit.Assert.assertEquals(level, launcher.getFactory().getEnvironment().getLevel());
        spoon.Launcher.LOGGER.info("Log info");
        spoon.Launcher.LOGGER.warn("Log warn");
        spoon.Launcher.LOGGER.error("Log error");
        spoon.Launcher.LOGGER.debug("Log debug");
        org.junit.Assert.assertEquals(isInfo, spoon.Launcher.LOGGER.isEnabledFor(org.apache.log4j.Priority.INFO));
        org.junit.Assert.assertEquals(isWarn, spoon.Launcher.LOGGER.isEnabledFor(org.apache.log4j.Priority.WARN));
        org.junit.Assert.assertEquals(isError, spoon.Launcher.LOGGER.isEnabledFor(org.apache.log4j.Priority.ERROR));
        org.junit.Assert.assertEquals(isDebug, spoon.Launcher.LOGGER.isEnabledFor(org.apache.log4j.Priority.DEBUG));
    }
}

