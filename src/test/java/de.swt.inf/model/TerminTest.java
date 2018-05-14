package de.swt.inf.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class TerminTest {

    @Test
    @Parameters({"22-10-17, 23-10-17, 10:00, 11:00", "22-10-17, 22-10-17, 10:00, 11:00"})
    public void TestIsValid(final String sD, final String eD, final String sT, final String eT) {
        //Test Day
        assertTrue(Termin.isValid(sD, eD, sT, eT));
        if (!sD.equals(eD)) {
            assertFalse(Termin.isValid(eD, sD, sT, eT));
        }
        //assertFalse(true);

        //Test Time
        assertTrue(Termin.isValid(sD, eD, sT, eT));
        if (!sT.equals(eT)  && sD.equals(eD))  {
            assertFalse(Termin.isValid(sD, eD, eT, sT));
        }
    }

}
