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
import java.util.LinkedHashMap;
import java.util.Map;
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
public class BandStimuliProviderTest {
    
    public BandStimuliProviderTest() {
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
     * Test of settype method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testSettype() {
        System.out.println("settype");
        String type = "";
        BandStimuliProvider instance = null;
        instance.settype(type);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setfastTrackPresent method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testSetfastTrackPresent() {
        System.out.println("setfastTrackPresent");
        String fastTrackPresent = "";
        BandStimuliProvider instance = null;
        instance.setfastTrackPresent(fastTrackPresent);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setfineTuningFirstWrongOut method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testSetfineTuningFirstWrongOut() {
        System.out.println("setfineTuningFirstWrongOut");
        String fineTuningFirstWrongOut = "";
        BandStimuliProvider instance = null;
        instance.setfineTuningFirstWrongOut(fineTuningFirstWrongOut);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setnumberOfBands method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testSetnumberOfBands() {
        System.out.println("setnumberOfBands");
        String numberOfBands = "";
        BandStimuliProvider instance = null;
        instance.setnumberOfBands(numberOfBands);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setnumberOfSeries method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testSetnumberOfSeries() {
        System.out.println("setnumberOfSeries");
        String numberOfSeries = "";
        BandStimuliProvider instance = null;
        instance.setnumberOfSeries(numberOfSeries);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setstartBand method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testSetstartBand() {
        System.out.println("setstartBand");
        String startBand = "";
        BandStimuliProvider instance = null;
        instance.setstartBand(startBand);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setfineTuningTupleLength method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testSetfineTuningTupleLength() {
        System.out.println("setfineTuningTupleLength");
        String fineTuningTupleLength = "";
        BandStimuliProvider instance = null;
        instance.setfineTuningTupleLength(fineTuningTupleLength);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setfineTuningUpperBoundForCycles method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testSetfineTuningUpperBoundForCycles() {
        System.out.println("setfineTuningUpperBoundForCycles");
        String fineTuningUpperBoundForCycles = "";
        BandStimuliProvider instance = null;
        instance.setfineTuningUpperBoundForCycles(fineTuningUpperBoundForCycles);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initialiseStimuliState method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testInitialiseStimuliState() {
        System.out.println("initialiseStimuliState");
        String stimuliStateSnapshot = "";
        BandStimuliProvider instance = null;
        instance.initialiseStimuliState(stimuliStateSnapshot);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateStimuliStateSnapshot method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGenerateStimuliStateSnapshot() {
        System.out.println("generateStimuliStateSnapshot");
        BandStimuliProvider instance = null;
        String expResult = "";
        String result = instance.generateStimuliStateSnapshot();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentBandIndex method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetCurrentBandIndex() {
        System.out.println("getCurrentBandIndex");
        BandStimuliProvider instance = null;
        int expResult = 0;
        int result = instance.getCurrentBandIndex();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumberOfBands method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetNumberOfBands() {
        System.out.println("getNumberOfBands");
        BandStimuliProvider instance = null;
        int expResult = 0;
        int result = instance.getNumberOfBands();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPercentageBandTable method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetPercentageBandTable() {
        System.out.println("getPercentageBandTable");
        BandStimuliProvider instance = null;
        LinkedHashMap<Long, Integer> expResult = null;
        LinkedHashMap<Long, Integer> result = instance.getPercentageBandTable();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getResponseRecord method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetResponseRecord() {
        System.out.println("getResponseRecord");
        BandStimuliProvider instance = null;
        ArrayList<BookkeepingStimulus<BandStimulusImplUTest>> expResult = null;
        ArrayList<BookkeepingStimulus<BandStimulusImplUTest>> result = instance.getResponseRecord();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnoughFinetuningStimuli method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetEnoughFinetuningStimuli() {
        System.out.println("getEnoughFinetuningStimuli");
        BandStimuliProvider instance = null;
        boolean expResult = false;
        boolean result = instance.getEnoughFinetuningStimuli();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCycel2 method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetCycel2() {
        System.out.println("getCycel2");
        BandStimuliProvider instance = null;
        boolean expResult = false;
        boolean result = instance.getCycel2();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChampion method, of class BandStimuliProvider.
     */
    @Test
    @Ignore
    public void testGetChampion() {
        System.out.println("getChampion");
        BandStimuliProvider instance = null;
        boolean expResult = false;
        boolean result = instance.getChampion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLooser method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetLooser() {
        System.out.println("getLooser");
        BandStimuliProvider instance = null;
        boolean expResult = false;
        boolean result = instance.getLooser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBestFastTrackBand method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetBestFastTrackBand() {
        System.out.println("getBestFastTrackBand");
        BandStimuliProvider instance = null;
        int expResult = 0;
        int result = instance.getBestFastTrackBand();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBandScore method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetBandScore() {
        System.out.println("getBandScore");
        BandStimuliProvider instance = null;
        int expResult = 0;
        int result = instance.getBandScore();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPercentageScore method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetPercentageScore() {
        System.out.println("getPercentageScore");
        BandStimuliProvider instance = null;
        long expResult = 0L;
        long result = instance.getPercentageScore();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFTtuple method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetFTtuple() {
        System.out.println("getFTtuple");
        BandStimuliProvider instance = null;
        ArrayList<BookkeepingStimulus<BandStimulusImplUTest>> expResult = null;
        ArrayList<BookkeepingStimulus<BandStimulusImplUTest>> result = instance.getFTtuple();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndFastTrackTimeTick method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetEndFastTrackTimeTick() {
        System.out.println("getEndFastTrackTimeTick");
        BandStimuliProvider instance = null;
        int expResult = 0;
        int result = instance.getEndFastTrackTimeTick();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentStimulus method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetCurrentStimulus() {
        System.out.println("getCurrentStimulus");
        BandStimuliProvider instance = null;
        Stimulus expResult = null;
        Stimulus result = instance.getCurrentStimulus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentStimulusIndex method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetCurrentStimulusIndex() {
        System.out.println("getCurrentStimulusIndex");
        BandStimuliProvider instance = null;
        int expResult = 0;
        int result = instance.getCurrentStimulusIndex();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalStimuli method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetTotalStimuli() {
        System.out.println("getTotalStimuli");
        BandStimuliProvider instance = null;
        int expResult = 0;
        int result = instance.getTotalStimuli();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasNextStimulus method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testHasNextStimulus() {
        System.out.println("hasNextStimulus");
        int increment = 0;
        BandStimuliProvider instance = null;
        boolean expResult = false;
        boolean result = instance.hasNextStimulus(increment);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nextStimulus method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testNextStimulus() {
        System.out.println("nextStimulus");
        int increment = 0;
        BandStimuliProvider instance = null;
        instance.nextStimulus(increment);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deriveNextFastTrackStimulus method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testDeriveNextFastTrackStimulus() {
        System.out.println("deriveNextFastTrackStimulus");
        BandStimuliProvider instance = null;
        BookkeepingStimulus expResult = null;
        BookkeepingStimulus result = instance.deriveNextFastTrackStimulus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCurrentStimuliIndex method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testSetCurrentStimuliIndex() {
        System.out.println("setCurrentStimuliIndex");
        int currentStimuliIndex = 0;
        BandStimuliProvider instance = null;
        instance.setCurrentStimuliIndex(currentStimuliIndex);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentStimulusUniqueId method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetCurrentStimulusUniqueId() {
        System.out.println("getCurrentStimulusUniqueId");
        BandStimuliProvider instance = null;
        String expResult = "";
        String result = instance.getCurrentStimulusUniqueId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHtmlStimuliReport method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetHtmlStimuliReport() {
        System.out.println("getHtmlStimuliReport");
        BandStimuliProvider instance = null;
        String expResult = "";
        String result = instance.getHtmlStimuliReport();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStimuliReport method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetStimuliReport() {
        System.out.println("getStimuliReport");
        String reportType = "";
        BandStimuliProvider instance = null;
        Map<String, String> expResult = null;
        Map<String, String> result = instance.getStimuliReport(reportType);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isCorrectResponse method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testIsCorrectResponse() {
        System.out.println("isCorrectResponse");
        Stimulus stimulus = null;
        String stimulusResponse = "";
        BandStimuliProvider instance = null;
        boolean expResult = false;
        boolean result = instance.isCorrectResponse(stimulus, stimulusResponse);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of analyseCorrectness method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testAnalyseCorrectness() {
        System.out.println("analyseCorrectness");
        Stimulus stimulus = null;
        String stimulusResponse = "";
        BandStimuliProvider instance = null;
        boolean expResult = false;
        boolean result = instance.analyseCorrectness(stimulus, stimulusResponse);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fastTrackToBeContinuedWithSecondChance method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testFastTrackToBeContinuedWithSecondChance() {
        System.out.println("fastTrackToBeContinuedWithSecondChance");
        BandStimuliProvider instance = null;
        boolean expResult = false;
        boolean result = instance.fastTrackToBeContinuedWithSecondChance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enoughStimuliForFastTrack method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testEnoughStimuliForFastTrack() {
        System.out.println("enoughStimuliForFastTrack");
        BandStimuliProvider instance = null;
        boolean expResult = false;
        boolean result = instance.enoughStimuliForFastTrack();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initialiseNextFineTuningTuple method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testInitialiseNextFineTuningTuple() {
        System.out.println("initialiseNextFineTuningTuple");
        BandStimuliProvider instance = null;
        boolean expResult = false;
        boolean result = instance.initialiseNextFineTuningTuple();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fineTuningToBeContinuedWholeTuple method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testFineTuningToBeContinuedWholeTuple() {
        System.out.println("fineTuningToBeContinuedWholeTuple");
        BandStimuliProvider instance = null;
        boolean expResult = false;
        boolean result = instance.fineTuningToBeContinuedWholeTuple();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of tupleIsNotEmpty method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testTupleIsNotEmpty() {
        System.out.println("tupleIsNotEmpty");
        BandStimuliProvider instance = null;
        boolean expResult = false;
        boolean result = instance.tupleIsNotEmpty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of allTupleIsCorrect method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testAllTupleIsCorrect() {
        System.out.println("allTupleIsCorrect");
        BandStimuliProvider instance = null;
        Boolean expResult = null;
        Boolean result = instance.allTupleIsCorrect();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of bandNumberIntoPercentage method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testBandNumberIntoPercentage() {
        System.out.println("bandNumberIntoPercentage");
        int bandNumber = 0;
        BandStimuliProvider instance = null;
        long expResult = 0L;
        long result = instance.bandNumberIntoPercentage(bandNumber);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of percentageIntoBandNumber method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testPercentageIntoBandNumber() {
        System.out.println("percentageIntoBandNumber");
        long percentage = 0L;
        BandStimuliProvider instance = null;
        int expResult = 0;
        int result = instance.percentageIntoBandNumber(percentage);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of recycleUnusedStimuli method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testRecycleUnusedStimuli() {
        System.out.println("recycleUnusedStimuli");
        BandStimuliProvider instance = null;
        instance.recycleUnusedStimuli();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toBeContinuedLoopChecker method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testToBeContinuedLoopChecker() {
        System.out.println("toBeContinuedLoopChecker");
        BandStimuliProvider instance = null;
        boolean expResult = false;
        boolean result = instance.toBeContinuedLoopChecker();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of detectLoop method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testDetectLoop() {
        System.out.println("detectLoop");
        int[] arr = null;
        boolean expResult = false;
        boolean result = BandStimuliProvider.detectLoop(arr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of shiftFIFO method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testShiftFIFO() {
        System.out.println("shiftFIFO");
        int[] fifo = null;
        int newelement = 0;
        BandStimuliProvider.shiftFIFO(fifo, newelement);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mostOftenVisitedBandNumber method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testMostOftenVisitedBandNumber() {
        System.out.println("mostOftenVisitedBandNumber");
        int[] bandVisitCounter = null;
        int controlIndex = 0;
        BandStimuliProvider instance = null;
        int expResult = 0;
        int result = instance.mostOftenVisitedBandNumber(bandVisitCounter, controlIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStringFastTrack method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetStringFastTrack() {
        System.out.println("getStringFastTrack");
        String startRow = "";
        String endRow = "";
        String startColumn = "";
        String endColumn = "";
        BandStimuliProvider instance = null;
        String expResult = "";
        String result = instance.getStringFastTrack(startRow, endRow, startColumn, endColumn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStringFineTuningHistory method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetStringFineTuningHistory() {
        System.out.println("getStringFineTuningHistory");
        String startRow = "";
        String endRow = "";
        String startColumn = "";
        String endColumn = "";
        String format = "";
        BandStimuliProvider instance = null;
        String expResult = "";
        String result = instance.getStringFineTuningHistory(startRow, endRow, startColumn, endColumn, format);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStringSummary method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testGetStringSummary() {
        System.out.println("getStringSummary");
        String startRow = "";
        String endRow = "";
        String startColumn = "";
        String endColumn = "";
        BandStimuliProvider instance = null;
        String expResult = "";
        String result = instance.getStringSummary(startRow, endRow, startColumn, endColumn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class BandStimuliProvider.
     */
    @Ignore
    @Test
    public void testToString() {
        System.out.println("toString");
        BandStimuliProvider instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

 
    
}
