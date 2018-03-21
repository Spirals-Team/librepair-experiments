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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BookkeepingStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.vocabulary.AdVocAsStimulus;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;
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
public class AdVocAsStimuliProviderTest {
    
    public AdVocAsStimuliProviderTest() {
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
     * Test of initialiseStimuliState method, of class AdVocAsStimuliProvider.
     */
    @Ignore
    @Test
    public void testInitialiseStimuliState() {
        System.out.println("initialiseStimuliState");
        String stimuliStateSnapshot = "";
        AdVocAsStimuliProvider instance = null;
        instance.initialiseStimuliState(stimuliStateSnapshot);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setnonwordsPerBlock method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testSetnonwordsPerBlock() {
        System.out.println("setnonwordsPerBlock");
        String nonWrodsPerBlock = "";
        AdVocAsStimuliProvider instance = null;
        instance.setnonwordsPerBlock(nonWrodsPerBlock);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setwordsPerBand method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testSetwordsPerBand() {
        System.out.println("setwordsPerBand");
        String wordsPerBand = "";
        AdVocAsStimuliProvider instance = null;
        instance.setwordsPerBand(wordsPerBand);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setaverageNonWordPosition method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testSetaverageNonWordPosition() {
        System.out.println("setaverageNonWordPosition");
        String averageNonWordPosition = "";
        AdVocAsStimuliProvider instance = null;
        instance.setaverageNonWordPosition(averageNonWordPosition);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWords method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetWords() {
        System.out.println("getWords");
        AdVocAsStimuliProvider instance = null;
        ArrayList<ArrayList<AdVocAsStimulus>> expResult = null;
        ArrayList<ArrayList<AdVocAsStimulus>> result = instance.getWords();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNonwords method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetNonwords() {
        System.out.println("getNonwords");
        AdVocAsStimuliProvider instance = null;
        ArrayList<AdVocAsStimulus> expResult = null;
        ArrayList<AdVocAsStimulus> result = instance.getNonwords();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNonWordsIndices method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetNonWordsIndices() {
        System.out.println("getNonWordsIndices");
        AdVocAsStimuliProvider instance = null;
        ArrayList<Integer> expResult = null;
        ArrayList<Integer> result = instance.getNonWordsIndices();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentBandNumber method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetCurrentBandNumber() {
        System.out.println("getCurrentBandNumber");
        AdVocAsStimuliProvider instance = null;
        int expResult = 0;
        int result = instance.getCurrentBandNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deriveNextFastTrackStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testDeriveNextFastTrackStimulus() {
        System.out.println("deriveNextFastTrackStimulus");
        AdVocAsStimuliProvider instance = null;
        BookkeepingStimulus expResult = null;
        BookkeepingStimulus result = instance.deriveNextFastTrackStimulus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of analyseCorrectness method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testAnalyseCorrectness() {
        System.out.println("analyseCorrectness");
        Stimulus stimulus = null;
        String stimulusResponse = "";
        AdVocAsStimuliProvider instance = null;
        boolean expResult = false;
        boolean result = instance.analyseCorrectness(stimulus, stimulusResponse);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fastTrackToBeContinuedWithSecondChance method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testFastTrackToBeContinuedWithSecondChance() {
        System.out.println("fastTrackToBeContinuedWithSecondChance");
        AdVocAsStimuliProvider instance = null;
        boolean expResult = false;
        boolean result = instance.fastTrackToBeContinuedWithSecondChance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enoughStimuliForFastTrack method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testEnoughStimuliForFastTrack() {
        System.out.println("enoughStimuliForFastTrack");
        AdVocAsStimuliProvider instance = null;
        boolean expResult = false;
        boolean result = instance.enoughStimuliForFastTrack();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initialiseNextFineTuningTuple method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testInitialiseNextFineTuningTuple() {
        System.out.println("initialiseNextFineTuningTuple");
        AdVocAsStimuliProvider instance = null;
        boolean expResult = false;
        boolean result = instance.initialiseNextFineTuningTuple();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of recycleUnusedStimuli method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testRecycleUnusedStimuli() {
        System.out.println("recycleUnusedStimuli");
        AdVocAsStimuliProvider instance = null;
        instance.recycleUnusedStimuli();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStringFastTrack method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetStringFastTrack() {
        System.out.println("getStringFastTrack");
        String startRow = "";
        String endRow = "";
        String startColumn = "";
        String endColumn = "";
        AdVocAsStimuliProvider instance = null;
        String expResult = "";
        String result = instance.getStringFastTrack(startRow, endRow, startColumn, endColumn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStringFineTuningHistory method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetStringFineTuningHistory() {
        System.out.println("getStringFineTuningHistory");
        String startRow = "";
        String endRow = "";
        String startColumn = "";
        String endColumn = "";
        String format = "";
        AdVocAsStimuliProvider instance = null;
        String expResult = "";
        String result = instance.getStringFineTuningHistory(startRow, endRow, startColumn, endColumn, format);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHtmlStimuliReport method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetHtmlStimuliReport() {
        System.out.println("getHtmlStimuliReport");
        AdVocAsStimuliProvider instance = null;
        String expResult = "";
        String result = instance.getHtmlStimuliReport();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateDiagramSequence method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGenerateDiagramSequence() {
        System.out.println("generateDiagramSequence");
        ArrayList<BookkeepingStimulus<AdVocAsStimulus>> records = null;
        LinkedHashMap<Long, Integer> percentageBandTable = null;
        AdVocAsStimuliProvider instance = null;
        LinkedHashMap<Long, String> expResult = null;
        LinkedHashMap<Long, String> result = instance.generateDiagramSequence(records, percentageBandTable);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of retrieveSampleWords method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testRetrieveSampleWords() {
        System.out.println("retrieveSampleWords");
        ArrayList<BookkeepingStimulus<AdVocAsStimulus>> records = null;
        ArrayList<ArrayList<AdVocAsStimulus>> nonusedWords = null;
        AdVocAsStimuliProvider instance = null;
        LinkedHashMap<Integer, String> expResult = null;
        LinkedHashMap<Integer, String> result = instance.retrieveSampleWords(records, nonusedWords);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testToString() {
        System.out.println("toString");
        AdVocAsStimuliProvider instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
