/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics, Nijmegen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic;

import nl.mpi.tg.eg.frinex.common.model.Stimulus.Tag;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author olhshk
 */
public class BandStimulusTest {

    public final BandStimulus instance;

    public BandStimulusTest() {

        String uniqueId = "smoer";
        String label = "smoer";

        this.instance = new BandStimulus(uniqueId, new Tag[0], label, "", 900, "aud", "vid", "img",
                "a,b,c", "b,c", "plus10db", 10) {
            @Override
            public boolean isCorrect(String value) {
                if (value == null) {
                    return false;
                }
                return value.trim().equals("b") || value.trim().equals("c");
            }
        };

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getbandLabel method, of class BandStimulus.
     */
    @Test
    public void testGetbandLabel() {
        System.out.println("getbandLabel");
        String expResult = "plus10db";
        String result = instance.getbandLabel();
        assertEquals(expResult, result);
    }

    /**
     * Test of getbandIndex method, of class BandStimulus.
     */
    @Test
    public void testGetbandIndex() {
        System.out.println("getbandIndex");
        int expResult = 10;
        int result = instance.getbandIndex();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class BandStimulus.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "smoer";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

}
