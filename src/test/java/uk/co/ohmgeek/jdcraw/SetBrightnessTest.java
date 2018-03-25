package uk.co.ohmgeek.jdcraw;

import junit.framework.TestCase;
import uk.co.ohmgeek.jdcraw.operations.NegativeBrightnessException;
import uk.co.ohmgeek.jdcraw.operations.SetBrightnessOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 29/06/17.
 */
public class SetBrightnessTest extends TestCase {

    public void testGreaterEqualZero() throws NegativeBrightnessException {
        int brightnessLevel = 5;

        List<String> expected = new ArrayList<String>();
        expected.add("-b");
        expected.add(String.valueOf(brightnessLevel));
        SetBrightnessOperation op = new SetBrightnessOperation(brightnessLevel);

        assertEquals(op.getArgumentList(), expected);

    }
    public void testLessThanZero() {
        // this should throw an exception.
        int brightnessLevel = -1;
        try {
            SetBrightnessOperation op = new SetBrightnessOperation(brightnessLevel);
            fail("Negative brightness didn't fail. It should.");
        } catch(NegativeBrightnessException ex) {
            System.out.println("This should pass.");
            System.out.println(ex.getMessage());
        }
    }

}
