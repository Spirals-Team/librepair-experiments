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
public class TrialTupleTest {

  

    private TrialTuple instance;
  
    public TrialTupleTest() {
        
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() { 
        ArrayList<Trial> trs = new ArrayList<Trial>(4);
        Trial[] pool = (new TrialTestPool()).trials;
        for (int i=0; i<4; i++) {
           trs.add(pool[i+1]);
        }
        this.instance = new TrialTuple(trs);
    }

    @After
    public void tearDown() {
    }

   
    /**
     * Test of getCorrectness and setCorrectness method, of class TrialTuple.
     */
    @Test
    public void testGetSetCorrectness() {
        System.out.println("setCorrectness");
        assertNull(this.instance.getCorrectness());  // correctness is not set yet
        this.instance.setCorrectness(true);
        assertTrue(this.instance.getCorrectness());
        this.instance.setCorrectness(false);
        assertFalse(this.instance.getCorrectness());
    }

    /**
     * Test of isNotEmpty method, of class TrialTuple.
     */
     @Ignore
    @Test
    public void testIsNotEmpty() {
        System.out.println("isNotEmpty");
        int allTogetherStimuli = 4 + 5 + 6 + 5;
        for (int count = 1; count <= allTogetherStimuli; count++) {
            assertTrue(this.instance.isNotEmpty());
            this.instance.removeFirstAvailableStimulus();
        }
        assertFalse(this.instance.isNotEmpty());
    }

    
    /**
     * Test of getTrials method, of class TrialTuple.
     */
     @Ignore
    @Test
    public void testGetTrials() {
        System.out.println("getTrials");
        ArrayList<Trial> result = this.instance.getTrials();
        assertEquals(4, result.size());
        assertEquals("geider", result.get(1).getStimuli().get(1).getStimulus().getLabel());
    }

    /**
     * Test of getNumberOfStimuli method, of class TrialTuple.
     */
     @Ignore
    @Test
    public void testGetNumberOfStimuli() {
        System.out.println("getNumberOfStimuli");
        int result = this.instance.getNumberOfStimuli();
        int expectedResult = 0;
        assertEquals(expectedResult, result);
    }

    /**
     * Test of getCorrectness method, of class TrialTuple.
     */
    
    @Test
    public void testGetCorrectness() {
        System.out.println("getCorrectness");
        Boolean result = this.instance.getCorrectness();
        assertEquals(null, result);
    }

    /**
     * Test of setCorrectness method, of class TrialTuple.
     */
    @Test
    public void testSetCorrectness() {
        System.out.println("setCorrectness");
        this.instance.setCorrectness(true);
        assertTrue(this.instance.getCorrectness());
        this.instance.setCorrectness(false);
        assertFalse(this.instance.getCorrectness());
    }

   
    /**
     * Test of toString method, of class TrialTuple.
     */
     @Ignore
    @Test
    public void testToString() {
        System.out.println("toString");
        TrialTuple instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toObject method, of class TrialTuple.
     */
     @Ignore
    @Test
    public void testToObject() {
        System.out.println("toObject");
        String str = "";
        LinkedHashMap<String, AudioAsStimulus> hashedStimuli = null;
        TrialTuple expResult = null;
        TrialTuple result = TrialTuple.toObject(str, hashedStimuli);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

 
}
