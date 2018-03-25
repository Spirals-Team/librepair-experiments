package uk.co.ohmgeek.jdcraw;

import junit.framework.TestCase;
import uk.co.ohmgeek.jdcraw.operations.NegativeWhiteBalanceException;
import uk.co.ohmgeek.jdcraw.operations.SetWBCustomOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 30/06/17.
 */
public class SetWBCustomOperationTest extends TestCase {
    public void testSetCustomWhiteBalanceCorrect() throws NegativeWhiteBalanceException {
        SetWBCustomOperation op = new SetWBCustomOperation(1,2,3,4);

        // build expected results
        List<String> expectedResults = new ArrayList<String>();
        expectedResults.add("-r");
        expectedResults.add("1.0");
        expectedResults.add("2.0");
        expectedResults.add("3.0");
        expectedResults.add("4.0");

        assertEquals(op.getArgumentList(), expectedResults);
    }

    public void testNegativeMult0() {
        try {
            SetWBCustomOperation op = new SetWBCustomOperation(-1,2,3,4);
            fail("Mult0 should cause exception to be thrown here.");
        } catch (NegativeWhiteBalanceException e) {
            e.printStackTrace();
        }
    }

    public void testNegativeMult1() {
        try {
            SetWBCustomOperation op = new SetWBCustomOperation(1,-2,3,4);
            fail("Mult1 should cause exception to be thrown here.");
        } catch (NegativeWhiteBalanceException e) {
            e.printStackTrace();
        }
    }

    public void testNegativeMult2() {
        try {
            SetWBCustomOperation op = new SetWBCustomOperation(1,2,-3,4);
            fail("Mult2 should cause exception to be thrown here.");
        } catch (NegativeWhiteBalanceException e) {
            e.printStackTrace();
        }
    }

    public void testNegativeMult3() {
        try {
            SetWBCustomOperation op = new SetWBCustomOperation(1,-2,3,-4);
            fail("Mult3 should cause exception to be thrown here.");
        } catch (NegativeWhiteBalanceException e) {
            e.printStackTrace();
        }
    }
}
