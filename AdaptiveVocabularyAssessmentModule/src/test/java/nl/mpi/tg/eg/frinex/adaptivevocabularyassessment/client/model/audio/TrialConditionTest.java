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
public class TrialConditionTest {
    
    private final String[] stringVals = {"TARGET_ONLY", "TARGET_AND_FOIL", "NO_TARGET"}; 
    
    public TrialConditionTest() {
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
     * Test of values method, of class TrialCondition.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        TrialCondition[] result = TrialCondition.values();
        for (int i=0; i<3; i++){
            assertEquals(this.stringVals[i], result[i].toString());
        }
    }

    /**
     * Test of valueOf method, of class TrialCondition.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        TrialCondition result1 = TrialCondition.valueOf("TARGET_ONLY");
        assertEquals(TrialCondition.TARGET_ONLY, result1);
        TrialCondition result2 = TrialCondition.valueOf("TARGET_AND_FOIL");
        assertEquals(TrialCondition.TARGET_AND_FOIL, result2);
        TrialCondition result3 = TrialCondition.valueOf("NO_TARGET");
        assertEquals(TrialCondition.NO_TARGET, result3);
        try {
           TrialCondition result4 = TrialCondition.valueOf("Rubbish");
           fail("No exception thrown!");
        } catch (IllegalArgumentException e) {
           System.out.println("fine, illegal argument is catched");
        } 
    }


}
