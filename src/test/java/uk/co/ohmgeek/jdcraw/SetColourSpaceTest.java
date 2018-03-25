package uk.co.ohmgeek.jdcraw;

import junit.framework.TestCase;
import uk.co.ohmgeek.jdcraw.operations.ColourSpaceEnum;
import uk.co.ohmgeek.jdcraw.operations.SetColorSpaceOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 29/06/17.
 */
public class SetColourSpaceTest extends TestCase {
    public void testArgsRAW() {
        List<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("-o");
        expectedOutput.add("0");

        SetColorSpaceOperation op = new SetColorSpaceOperation(ColourSpaceEnum.RAW);
        assertEquals(op.getArgumentList(), expectedOutput);
    }
    public void testArgsSRGB() {
        List<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("-o");
        expectedOutput.add("1");

        SetColorSpaceOperation op = new SetColorSpaceOperation(ColourSpaceEnum.SRGB);
        assertEquals(op.getArgumentList(), expectedOutput);
    }
    public void testArgsAdobeRGB() {
        List<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("-o");
        expectedOutput.add("2");

        SetColorSpaceOperation op = new SetColorSpaceOperation(ColourSpaceEnum.ADOBE_RGB);
        assertEquals(op.getArgumentList(), expectedOutput);
    }
    public void testArgsWide() {
        List<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("-o");
        expectedOutput.add("3");

        SetColorSpaceOperation op = new SetColorSpaceOperation(ColourSpaceEnum.WIDE);
        assertEquals(op.getArgumentList(), expectedOutput);
    }
    public void testArgsProPhoto() {
        List<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("-o");
        expectedOutput.add("4");

        SetColorSpaceOperation op = new SetColorSpaceOperation(ColourSpaceEnum.ProPhoto);
        assertEquals(op.getArgumentList(), expectedOutput);
    }
    public void testArgsXYZ() {
        List<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("-o");
        expectedOutput.add("5");

        SetColorSpaceOperation op = new SetColorSpaceOperation(ColourSpaceEnum.XYZ);
        assertEquals(op.getArgumentList(), expectedOutput);
    }
    public void testArgsACES() {
        List<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("-o");
        expectedOutput.add("6");

        SetColorSpaceOperation op = new SetColorSpaceOperation(ColourSpaceEnum.ACES);
        assertEquals(op.getArgumentList(), expectedOutput);
    }

}
