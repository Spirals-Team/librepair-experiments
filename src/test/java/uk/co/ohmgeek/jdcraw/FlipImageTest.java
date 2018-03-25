package uk.co.ohmgeek.jdcraw;

import junit.framework.TestCase;
import uk.co.ohmgeek.jdcraw.operations.FlipAngleEnum;
import uk.co.ohmgeek.jdcraw.operations.FlipImageOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 29/06/17.
 */
public class FlipImageTest extends TestCase {
    //todo iterate through all values of enum rather than this hacky method.
    public void testAngleZero() {
        List<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("-t");
        expectedOutput.add("0");

        FlipImageOperation op = new FlipImageOperation(FlipAngleEnum.NONE);
        assertEquals(op.getArgumentList(), expectedOutput);
    }
    public void testAngleNinety() {
        List<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("-t");
        expectedOutput.add("90");

        FlipImageOperation op = new FlipImageOperation(FlipAngleEnum.DEGREES90);
        assertEquals(op.getArgumentList(), expectedOutput);
    }
    public void testAngleOneEighty() {
        List<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("-t");
        expectedOutput.add("180");

        FlipImageOperation op = new FlipImageOperation(FlipAngleEnum.DEGREES180);
        assertEquals(op.getArgumentList(), expectedOutput);
    }
    public void testAngleTwoSeventy() {
        List<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("-t");
        expectedOutput.add("270");

        FlipImageOperation op = new FlipImageOperation(FlipAngleEnum.DEGREES270);
        assertEquals(op.getArgumentList(), expectedOutput);
    }
}
