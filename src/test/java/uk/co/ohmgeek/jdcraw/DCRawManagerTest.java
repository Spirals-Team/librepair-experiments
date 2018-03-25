package uk.co.ohmgeek.jdcraw;

import junit.framework.TestCase;
import uk.co.ohmgeek.jdcraw.operations.FlipAngleEnum;
import uk.co.ohmgeek.jdcraw.operations.FlipImageOperation;
import uk.co.ohmgeek.jdcraw.operations.NegativeBrightnessException;
import uk.co.ohmgeek.jdcraw.operations.SetBrightnessOperation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 30/06/17.
 */
public class DCRawManagerTest extends TestCase {
    public void testSerialiser() throws NegativeBrightnessException {
        DCRawManager m = new DCRawManager(new File("img.dng")); //file currently doesn't exist, but
                                                                    // we'll ignore this for now
        m.addOperation(new FlipImageOperation(FlipAngleEnum.DEGREES90)); // -t 90
        m.addOperation(new SetBrightnessOperation(1)); // -b 1

        List<String> cmd = m.getCMDToExecute();


        // Now produce the expected output
        List<String> expectedResults = new ArrayList<String>();
        // first executable
        expectedResults.add("dcraw");
        // then command for flipping
        expectedResults.add("-t");
        expectedResults.add("90");
        //then command for brightness
        expectedResults.add("-b");
        expectedResults.add("1");
        expectedResults.add(new File("img.dng").getAbsolutePath());

        assertEquals(expectedResults, cmd);
    }
}
