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
import org.junit.Ignore;

/**
 *
 * @author olhshk
 */
public class BookkeepingStimulusTest {
    
    public BookkeepingStimulusTest() {
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
     * Test of getStimulus method, of class BookkeepingStimulus.
     */
    @Ignore @Test
    public void testGetStimulus() {
        System.out.println("getStimulus");
        BookkeepingStimulus instance = null;
        BandStimulus expResult = null;
        BandStimulus result = instance.getStimulus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReaction method, of class BookkeepingStimulus.
     */
    @Ignore @Test
    public void testGetReaction() {
        System.out.println("getReaction");
        BookkeepingStimulus instance = null;
        String expResult = "";
        String result = instance.getReaction();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTimeStamp method, of class BookkeepingStimulus.
     */
    @Ignore @Test
    public void testGetTimeStamp() {
        System.out.println("getTimeStamp");
        BookkeepingStimulus instance = null;
        long expResult = 0L;
        long result = instance.getTimeStamp();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCorrectness method, of class BookkeepingStimulus.
     */
    @Ignore @Test
    public void testGetCorrectness() {
        System.out.println("getCorrectness");
        BookkeepingStimulus instance = null;
        Boolean expResult = null;
        Boolean result = instance.getCorrectness();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setReaction method, of class BookkeepingStimulus.
     */
    @Ignore @Test
    public void testSetReaction() {
        System.out.println("setReaction");
        String reaction = "";
        BookkeepingStimulus instance = null;
        instance.setReaction(reaction);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCorrectness method, of class BookkeepingStimulus.
     */
    @Ignore @Test
    public void testSetCorrectness() {
        System.out.println("setCorrectness");
        boolean eval = false;
        BookkeepingStimulus instance = null;
        instance.setCorrectness(eval);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTimeStamp method, of class BookkeepingStimulus.
     */
    @Ignore @Test
    public void testSetTimeStamp() {
        System.out.println("setTimeStamp");
        long timeStr = 0L;
        BookkeepingStimulus instance = null;
        instance.setTimeStamp(timeStr);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
