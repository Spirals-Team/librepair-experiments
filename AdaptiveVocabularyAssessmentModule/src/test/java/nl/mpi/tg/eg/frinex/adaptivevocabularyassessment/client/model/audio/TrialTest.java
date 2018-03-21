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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BookkeepingStimulus;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author olhshk
 */
public class TrialTest {

   private Trial[] instance = (new TrialTestPool()).trials;
    
  
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
     * Test of getStimuliList method, of class Trial.
     */
     @Ignore
    @Test
    public void testGetStimuliList() {
        System.out.println("getStimuliList");
        ArrayList<BookkeepingStimulus<AudioAsStimulus>>[] stimuli = new ArrayList[this.instance.length];
        for (int i = 0; i < this.instance.length; i++) {
            stimuli[i] = this.instance[i].getStimuli();
        }
        assertEquals(4, stimuli[0].size());
        assertEquals("smoer", stimuli[0].get(0).getStimulus().getLabel());
        assertEquals("deerbal", stimuli[0].get(1).getStimulus().getLabel());
        assertEquals("smoer", stimuli[0].get(2).getStimulus().getLabel());
        assertEquals("wijp", stimuli[0].get(3).getStimulus().getLabel());

        assertEquals(5, stimuli[1].size());
        assertEquals("hers", stimuli[1].get(0).getStimulus().getLabel());
        assertEquals("geider", stimuli[1].get(1).getStimulus().getLabel());
        assertEquals("hers", stimuli[1].get(2).getStimulus().getLabel());
        assertEquals("atgraus", stimuli[1].get(3).getStimulus().getLabel());
        assertEquals("hamp", stimuli[1].get(4).getStimulus().getLabel());

        assertEquals(6, stimuli[2].size());
        assertEquals("fjon", stimuli[2].get(0).getStimulus().getLabel());
        assertEquals("fjodschelg", stimuli[2].get(1).getStimulus().getLabel());
        assertEquals("fjon", stimuli[2].get(2).getStimulus().getLabel());
        assertEquals("wisdaag", stimuli[2].get(3).getStimulus().getLabel());
        assertEquals("tuik", stimuli[2].get(4).getStimulus().getLabel());
        assertEquals("poks", stimuli[2].get(5).getStimulus().getLabel());

        assertEquals(5, stimuli[3].size());
        assertEquals("lop", stimuli[3].get(0).getStimulus().getLabel());
        assertEquals("voorserm", stimuli[3].get(1).getStimulus().getLabel());
        assertEquals("muiland", stimuli[3].get(2).getStimulus().getLabel());
        assertEquals("fraal", stimuli[3].get(3).getStimulus().getLabel());
        assertEquals("kijn", stimuli[3].get(4).getStimulus().getLabel());
    }

    /**
     * Test of getWord method, of class Trial.
     */
     @Ignore
    @Test
    public void testGetWord() {
        System.out.println("getWord");
        String[] words = new String[this.instance.length];
        for (int i = 0; i < this.instance.length; i++) {
            words[i] = this.instance[i].getWord();
        }
        assertEquals("vloer", words[0]);
        assertEquals("kers", words[1]);
        assertEquals("vuur", words[2]);
        assertEquals("pop", words[3]);
    }


    /**
     * Test of getBandNumber method, of class Trial.
     */
     @Ignore
    @Test
    public void testGetBandIndex() {
        System.out.println("getBandNumber");
        int[] bands = new int[this.instance.length];
        for (int i = 0; i < this.instance.length; i++) {
            bands[i] = this.instance[i].getBandIndex();
        }
        assertEquals(1, bands[0]);
        assertEquals(2, bands[1]);
        assertEquals(2, bands[2]);
        assertEquals(0, bands[3]);
    }
     @Ignore
     @Test
    public void testGetBandLabel() {
        System.out.println("getBandLabel");
        String[] bands = new String[this.instance.length];
        for (int i = 0; i < this.instance.length; i++) {
            bands[i] = this.instance[i].getBandLabel();
        }
        assertEquals("6dB", bands[0]);
        assertEquals("10dB", bands[1]);
        assertEquals("10dB", bands[2]);
        assertEquals("2dB", bands[3]);
        
    }

    /**
     * Test of getTargetNonWord method, of class Trial.
     */
    @Ignore
    @Test
    public void testGetTargetNonWord() {
        System.out.println("getTargetNonWord");
        String[] targetNonWords = new String[this.instance.length];
        for (int i = 0; i < this.instance.length; i++) {
            targetNonWords[i] = this.instance[i].getTargetNonWord();
        }
        assertEquals("smoer", targetNonWords[0]);
        assertEquals("hers", targetNonWords[1]);
        assertEquals("fjon", targetNonWords[2]);
        assertEquals("lop", targetNonWords[3]);
    }

    /**
     * Test of getNumberOfSyllables method, of class Trial.
     */
     @Ignore
    @Test
    public void testGetNumberOfSyllables() {
        System.out.println("getNumberOfSyllables");
        int[] syllabN = new int[this.instance.length];
        for (int i = 0; i < this.instance.length; i++) {
            syllabN[i] = this.instance[i].getNumberOfSyllables();
        }
        assertEquals(1, syllabN[0]);
        assertEquals(1, syllabN[1]);
        assertEquals(1, syllabN[2]);
        assertEquals(1, syllabN[3]);
    }

    /**
     * Test of getCondition method, of class Trial.
     */
     @Ignore
    @Test
    public void testGetCondition() {
        System.out.println("getCondition");
        TrialCondition[] consitions = new TrialCondition[this.instance.length];
        for (int i = 0; i < this.instance.length; i++) {
            consitions[i] = this.instance[i].getCondition();
        }
        assertEquals(TrialCondition.TARGET_ONLY, consitions[0]);
        assertEquals(TrialCondition.TARGET_ONLY, consitions[1]);
        assertEquals(TrialCondition.TARGET_AND_FOIL, consitions[2]);
        assertEquals(TrialCondition.NO_TARGET, consitions[3]);
    }

    /**
     * Test of getTrialLength method, of class Trial.
     */
     @Ignore
    @Test
    public void testGetTrialLength() {
        System.out.println("getTrialLength");
        int[] trailL = new int[this.instance.length];
        ArrayList<BookkeepingStimulus<AudioAsStimulus>>[] stimuli = new ArrayList[this.instance.length];

        for (int i = 0; i < this.instance.length; i++) {
            trailL[i] = this.instance[i].getTrialLength();
            stimuli[i] = this.instance[i].getStimuli();
        }
        assertEquals(3, trailL[0]);
        assertEquals(4, trailL[1]);
        assertEquals(5, trailL[2]);
        assertEquals(4, trailL[3]);

        assertEquals(trailL[0] + 1, stimuli[0].size());
        assertEquals(trailL[1] + 1, stimuli[1].size());
        assertEquals(trailL[2] + 1, stimuli[2].size());
        assertEquals(trailL[3] + 1, stimuli[3].size());
    }

    /**
     * Test of addStimulus method, of class Trial.
     */
     @Ignore
    @Test
    public void testAddStimulus() {
        System.out.println("addStimulus");
        StimuliTestPool pool = new  StimuliTestPool();
        BookkeepingStimulus<AudioAsStimulus> stimulus = pool.stimuli.get(0);
        int stimulusPosition = 0;
        Trial instance = null;
        instance.addStimulus(stimulus, stimulusPosition);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStimuli method, of class Trial.
     */
     @Ignore
    @Test
    public void testGetStimuli() {
        System.out.println("getStimuli");
        TrialTestPool pool = new TrialTestPool();
        Trial instance = pool.trials[0];
        ArrayList<BookkeepingStimulus<AudioAsStimulus>> expResult = null;
        ArrayList<BookkeepingStimulus<AudioAsStimulus>> result = instance.getStimuli();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class Trial.
     */
     @Ignore
    @Test
    public void testGetId() {
        System.out.println("getId");
        Trial instance = null;
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Trial.
     */
     @Ignore
    @Test
    public void testToString() {
        System.out.println("toString");
        Trial instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toObject method, of class Trial.
     */
     @Ignore
    @Test
    public void testToObject() {
        System.out.println("toObject");
        String str = "";
        LinkedHashMap<String, AudioAsStimulus> hashedStimuli = null;
        Trial expResult = null;
        Trial result = Trial.toObject(str, hashedStimuli);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of map3ToString method, of class Trial.
     */
     @Ignore
    @Test
    public void testMap3ToString() {
        System.out.println("map3ToString");
        Map<TrialCondition, ArrayList<ArrayList<ArrayList<Trial>>>> map = null;
        String expResult = "";
        String result = Trial.map3ToString(map);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
