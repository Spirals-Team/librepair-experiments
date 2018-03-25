package uk.co.ohmgeek.jdcraw;

import junit.framework.TestCase;
import uk.co.ohmgeek.jdcraw.operations.NegativeWhiteBalanceException;
import uk.co.ohmgeek.jdcraw.operations.SetWBCustomOperation;
import uk.co.ohmgeek.jdcraw.operations.SetWBPresetOperation;
import uk.co.ohmgeek.jdcraw.operations.WBPreset;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 30/06/17.
 */
public class SetWBPresetOperationTest extends TestCase {
    public void testCameraPreset() {
        SetWBPresetOperation op = new SetWBPresetOperation(WBPreset.CAMERA);

        List<String> expected = new ArrayList<String>();
        expected.add("-w");

        assertEquals(op.getArgumentList(), expected);
    }

    public void testDefaultPreset() {
        SetWBPresetOperation op = new SetWBPresetOperation(WBPreset.DEFAULT);

        List<String> expected = new ArrayList<String>();

        assertEquals(op.getArgumentList(), expected);
    }
}
