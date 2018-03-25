package uk.co.ohmgeek.jdcraw;

import junit.framework.TestCase;
import uk.co.ohmgeek.jdcraw.operations.FileOutputTypeEnum;
import uk.co.ohmgeek.jdcraw.operations.SetFileOutputTypeOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Object to set the file output type, as either Tiff or PNM style format.
 * Created by ryan on 29/06/17.
 */
public class SetFileOutputTypeTest extends TestCase {
    public void testTiffFormat() {
        List<String> expected = new ArrayList<String>();
        expected.add("-T");

        SetFileOutputTypeOperation op = new SetFileOutputTypeOperation(FileOutputTypeEnum.TIFF);

        assertEquals(expected, op.getArgumentList());
    }

    public void testPNMFormat() {
        List<String> expected = new ArrayList<String>();

        SetFileOutputTypeOperation op = new SetFileOutputTypeOperation(FileOutputTypeEnum.PNM);

        assertEquals(expected, op.getArgumentList());
    }
}
