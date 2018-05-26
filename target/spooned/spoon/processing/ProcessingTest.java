package spoon.processing;


public class ProcessingTest {
    @org.junit.Test
    public void testInterruptAProcessor() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/processing/");
        final spoon.test.processing.processors.MyProcessor processor = new spoon.test.processing.processors.MyProcessor();
        launcher.addProcessor(processor);
        try {
            launcher.run();
        } catch (spoon.processing.ProcessInterruption e) {
            org.junit.Assert.fail("ProcessInterrupt exception must be catch in the ProcessingManager.");
        }
        org.junit.Assert.assertFalse(processor.isShouldStayAtFalse());
    }

    @org.junit.Test
    public void testSpoonTagger() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addProcessor("spoon.processing.SpoonTagger");
        launcher.run();
        org.junit.Assert.assertTrue(new java.io.File(((launcher.getModelBuilder().getSourceOutputDirectory()) + "/spoon/Spoon.java")).exists());
    }
}

