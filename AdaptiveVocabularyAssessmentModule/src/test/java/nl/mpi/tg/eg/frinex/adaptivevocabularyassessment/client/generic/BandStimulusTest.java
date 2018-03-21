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
    
    public BandStimulusTest() {
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
        BandStimulusImpl instance = new BandStimulusImpl();
        String expResult = "bandLabel_20";
        String result = instance.getbandLabel();
        assertEquals(expResult, result);
    }

    /**
     * Test of getbandIndex method, of class BandStimulus.
     */
    @Test
    public void testGetbandIndex() {
        System.out.println("getbandIndex");
        BandStimulusImpl instance = new BandStimulusImpl();
        String expResult = "19";
        String result = instance.getbandIndex();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBandIndexInt method, of class BandStimulus.
     */
    @Test
    public void testGetBandIndexInt() {
        System.out.println("getBandIndexInt");
        BandStimulusImpl instance = new BandStimulusImpl();
        int expResult = 19;
        int result = instance.getBandIndexInt();
        assertEquals(expResult, result);
    }

   
    /**
     * Test of toString method, of class BandStimulus.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        BandStimulusImpl instance = new BandStimulusImpl();
        String expResult = "label_0000";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    public class BandStimulusImpl extends BandStimulus {

        //BandStimulus(String uniqueId, Tag[] tags, String label, String code, int pauseMs, String audioPath, String videoPath, String imagePath, String ratingLabels, String correctResponses
        
        public BandStimulusImpl() {
            super("label_0000", new Tag[1], "label", "", 0, "", "", "", "", "");
        }

        public String getbandLabel() {
            return "bandLabel_20";
        }

        public String getbandIndex() {
            return "19";
        }
    }

   
    
}
