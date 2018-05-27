package spoon.test;


public class OutputTypeTest {
    @org.junit.Test
    public void testOutputTypeLoading() {
        spoon.OutputType outputType = spoon.OutputType.fromString("nulltest");
        org.junit.Assert.assertNull(outputType);
        outputType = spoon.OutputType.fromString("nooutput");
        org.junit.Assert.assertEquals(spoon.OutputType.NO_OUTPUT, outputType);
        outputType = spoon.OutputType.fromString("classes");
        org.junit.Assert.assertEquals(spoon.OutputType.CLASSES, outputType);
        outputType = spoon.OutputType.fromString("compilationunits");
        org.junit.Assert.assertEquals(spoon.OutputType.COMPILATION_UNITS, outputType);
    }
}

