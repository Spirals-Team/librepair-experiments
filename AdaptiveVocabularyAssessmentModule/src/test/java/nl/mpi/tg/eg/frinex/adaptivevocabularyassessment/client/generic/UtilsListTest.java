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

import java.util.ArrayList;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.TrialCondition;
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
public class UtilsListTest {

    public UtilsListTest() {
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
     * Test of listelementExists method, of class UtilsList.
     */
    @Test
    public void testListelementExists() {
        System.out.println("listelementExists");
        UtilsList<Integer> instance = new UtilsList<Integer>();
        ArrayList<ArrayList<Integer>> source = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> perm1 = new ArrayList<Integer>(4);
        perm1.add(0, 3);
        perm1.add(1, 4);
        perm1.add(2, 5);
        perm1.add(3, 6);
        ArrayList<Integer> perm2 = new ArrayList<Integer>(4);
        perm2.add(0, 4);
        perm2.add(1, 5);
        perm2.add(2, 6);
        perm2.add(3, 3);
        ArrayList<Integer> perm3 = new ArrayList<Integer>(4);
        perm3.add(0, 5);
        perm3.add(1, 6);
        perm3.add(2, 3);
        perm3.add(3, 4);

        source.add(0, perm1);
        source.add(1, perm2);
        source.add(2, perm3);

        ArrayList<Integer> candidate1 = new ArrayList<Integer>(4);
        candidate1.add(0, 4);
        candidate1.add(1, 5);
        candidate1.add(2, 6);
        candidate1.add(3, 3);

        ArrayList<Integer> candidate2 = new ArrayList<Integer>(4);
        candidate2.add(0, 6);
        candidate2.add(1, 3);
        candidate2.add(2, 4);
        candidate2.add(3, 5);

        assertTrue(instance.listElementExists(source, candidate1));
        assertFalse(instance.listElementExists(source, candidate2));
    }

    /**
     * Test of listelementExists method, of class UtilsList.
     */
    @Test
    public void testListelementExists2() {
        System.out.println("listelementExists");
        UtilsList<TrialCondition> instance = new UtilsList<TrialCondition>();
        ArrayList<ArrayList<TrialCondition>> source = new ArrayList<ArrayList<TrialCondition>>();
        ArrayList<TrialCondition> perm1 = new ArrayList<TrialCondition>(4);
        perm1.add(0, TrialCondition.TARGET_ONLY);
        perm1.add(1, TrialCondition.TARGET_ONLY);
        perm1.add(2, TrialCondition.NO_TARGET);
        perm1.add(3, TrialCondition.TARGET_AND_FOIL);
        ArrayList<TrialCondition> perm2 = new ArrayList<TrialCondition>(4);
        perm2.add(0, TrialCondition.NO_TARGET);
        perm2.add(1, TrialCondition.TARGET_ONLY);
        perm2.add(2, TrialCondition.TARGET_ONLY);
        perm2.add(3, TrialCondition.TARGET_AND_FOIL);
        ArrayList<TrialCondition> perm3 = new ArrayList<TrialCondition>(4);
        perm3.add(0, TrialCondition.TARGET_AND_FOIL);
        perm3.add(1, TrialCondition.TARGET_ONLY);
        perm3.add(2, TrialCondition.NO_TARGET);
        perm3.add(3, TrialCondition.TARGET_ONLY);

        source.add(0, perm1);
        source.add(1, perm2);
        source.add(2, perm3);

        ArrayList<TrialCondition> candidate1 = new ArrayList<TrialCondition>(4);
        candidate1.add(0, TrialCondition.TARGET_AND_FOIL);
        candidate1.add(1, TrialCondition.TARGET_ONLY);
        candidate1.add(2, TrialCondition.NO_TARGET);
        candidate1.add(3, TrialCondition.TARGET_ONLY);

        ArrayList<TrialCondition> candidate2 = new ArrayList<TrialCondition>(4);
        candidate2.add(0, TrialCondition.TARGET_ONLY);
        candidate2.add(1, TrialCondition.NO_TARGET);
        candidate2.add(2, TrialCondition.TARGET_ONLY);
        candidate2.add(3, TrialCondition.TARGET_AND_FOIL);

        assertEquals(true, instance.listElementExists(source, candidate1));
        assertEquals(false, instance.listElementExists(source, candidate2));
    }

    /**
     * Test of generatePermutations method, of class UtilsList.
     */
    @Test
    public void testGeneratePermutations_4() {
        System.out.println("generatePermutations. numeric length 4");
        UtilsList<Integer> instance = new UtilsList<Integer>();
        ArrayList<Integer> generatorSet = new ArrayList<Integer>(4);
        generatorSet.add(0, 3);
        generatorSet.add(1, 4);
        generatorSet.add(2, 5);
        generatorSet.add(3, 6);
        ArrayList<ArrayList<Integer>> result = instance.generatePermutations(generatorSet, 4);
        assertEquals(4*3*2*1, result.size());
        for (ArrayList<Integer> perm:result ) {
            assertEquals(4, perm.size());
        }

        assertTrue(instance.listElementExists(result, generatorSet));
        
        ArrayList<Integer> perm = new ArrayList<Integer>(4);
        perm.add(0, 6);
        perm.add(1, 5);
        perm.add(2, 4);
        perm.add(3, 3);
        assertTrue(instance.listElementExists(result, perm));
        
        ArrayList<Integer> wrong = new ArrayList<Integer>(4);
        wrong.add(0, 6);
        wrong.add(1, 5);
        wrong.add(2, 5);
        wrong.add(3, 3);
        assertFalse(instance.listElementExists(result, wrong));
        
        // check that there are no dublicates
        for (int j = 0; j < result.size(); j++) {
            ArrayList<ArrayList<Integer>> copy = new ArrayList<ArrayList<Integer>>(result.size());
            for (int i = 0; i < result.size(); i++) {
                copy.add(i, result.get(i));
            }
            ArrayList<Integer> element = copy.remove(j);
            assertFalse(instance.listElementExists(copy, element));
        }

    }
    
    /**
     * Test of generatePermutations method, of class UtilsList.
     */
    @Test
    public void testGeneratePermutations_3() {
        System.out.println("generatePermutations, numeric length 3");
        UtilsList<Integer> instance = new UtilsList<Integer>();
        ArrayList<Integer> generatorSet = new ArrayList<Integer>(4);
        generatorSet.add(0, 3);
        generatorSet.add(1, 4);
        generatorSet.add(2, 5);
        generatorSet.add(3, 6);
        ArrayList<ArrayList<Integer>> result = instance.generatePermutations(generatorSet, 3);
        assertEquals(4*3*2, result.size());
        for (ArrayList<Integer> perm:result ) {
            assertEquals(3, perm.size());
        }

        assertTrue(instance.listElementExists(result, generatorSet));
        
        ArrayList<Integer> perm = new ArrayList<Integer>(3);
        perm.add(0, 6);
        perm.add(1, 5);
        perm.add(2, 4);
        assertTrue(instance.listElementExists(result, perm));
        
        ArrayList<Integer> perm2 = new ArrayList<Integer>(3);
        perm2.add(0, 6);
        perm2.add(1, 5);
        perm2.add(2, 3);
        assertTrue(instance.listElementExists(result, perm2));
        
        ArrayList<Integer> perm3 = new ArrayList<Integer>(3);
        perm3.add(0, 4);
        perm3.add(1, 5);
        perm3.add(2, 3);
        assertTrue(instance.listElementExists(result, perm3));
        
        ArrayList<Integer> wrong = new ArrayList<Integer>(3);
        wrong.add(0, 6);
        wrong.add(1, 5);
        wrong.add(2, 5);
        assertFalse(instance.listElementExists(result, wrong));
        
        // check that there are no dublicates
        for (int j = 0; j < result.size(); j++) {
            ArrayList<ArrayList<Integer>> copy = new ArrayList<ArrayList<Integer>>(result.size());
            for (int i = 0; i < result.size(); i++) {
                copy.add(i, result.get(i));
            }
            ArrayList<Integer> element = copy.remove(j);
            assertFalse(instance.listElementExists(copy, element));
        }

    }
    
     /**
     * Test of generatePermutations method, of class UtilsList.
     */
    @Test
    public void testGeneratePermutations_tc_3() {
        System.out.println("generatePermutations");
        UtilsList<TrialCondition> instance = new UtilsList<TrialCondition>();
        ArrayList<TrialCondition> generatorSet = new ArrayList<TrialCondition>(3);
        generatorSet.add(0, TrialCondition.TARGET_ONLY);
        generatorSet.add(1, TrialCondition.NO_TARGET);
        generatorSet.add(2, TrialCondition.TARGET_AND_FOIL);
        ArrayList<ArrayList<TrialCondition>> result = instance.generatePermutations(generatorSet, 3);
        assertEquals(3*2*1 , result.size());

        assertTrue(instance.listElementExists(result, generatorSet));
        
        ArrayList<TrialCondition> perm = new ArrayList<TrialCondition>(3);
        perm.add(0, TrialCondition.NO_TARGET);
        perm.add(1, TrialCondition.TARGET_ONLY);
        perm.add(2, TrialCondition.TARGET_AND_FOIL);
        assertTrue(instance.listElementExists(result, perm));
        
        ArrayList<TrialCondition> wrong = new ArrayList<TrialCondition>(3);
        wrong.add(0, TrialCondition.NO_TARGET);
        wrong.add(1, TrialCondition.NO_TARGET);
        wrong.add(2, TrialCondition.TARGET_ONLY);
        assertFalse(instance.listElementExists(result, wrong));
        
        // check that there are no duplicates
        for (int j = 0; j < result.size(); j++) {
            ArrayList<ArrayList<TrialCondition>> copy = new ArrayList<ArrayList<TrialCondition>>(result.size());
            for (int i = 0; i < result.size(); i++) {
                copy.add(i, result.get(i));
            }
            ArrayList<TrialCondition> element = copy.remove(j);
            assertFalse(instance.listElementExists(copy, element));
        }

    }

}
