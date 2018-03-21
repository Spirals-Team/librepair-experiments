/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.advocaspool.Vocabulary;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BookkeepingStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.vocabulary.AdVocAsStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.AdVocAsStimuliProvider;
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

    private final String numberOfBands = "54";
    private final String wordsPerBand = "40";
    private final String nonwordsPerBlock = "4";
    private final String startBand = "20";
    private final String averageNonWordPoistion = "3";
    private final String fineTuningTupleLength="4";
    private final String fineTuningUpperBoundForCycles="2";

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

    /*
    Attributes to set 
    type="1" 
    eventTag="stimuliN"
    numberOfSeries="2"
    numberOfBands="54"
    wordsPerBand="40"
    startBand="20"
    averageNonWordPosition="3" 
    startPercentageGraph="30"
    fineTuningTupleLength="4"
     */
    /**
     * Test of estinItialiseStimuliState method, of class
     * AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testItialiseStimuliState1() {
        System.out.println("initialiseStimuliState-1");

        int nOfBands = Integer.parseInt(this.numberOfBands);
        String numberOfSeries = "1";
        int nOfSeries = Integer.parseInt(numberOfSeries);
        int wPerBand = Integer.parseInt(this.wordsPerBand);
        int wordsPerBandInSeries = wPerBand / nOfSeries;
        int sBand = Integer.parseInt(this.startBand);
        int aNonWordPosition = Integer.parseInt(this.averageNonWordPoistion);

        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        provider.setnumberOfSeries(numberOfSeries);
        provider.settype("0");
        provider.setnumberOfBands(this.numberOfBands); 
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles); 
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(this.wordsPerBand); 
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.setfineTuningTupleLength("4");
        provider.initialiseStimuliState("");

        ArrayList<ArrayList<AdVocAsStimulus>> words = provider.getWords();
        assertEquals(nOfBands, words.size());
        for (int i = 0; i < nOfBands; i++) {
            assertEquals(wordsPerBandInSeries, words.get(i).size());
        }

        ArrayList<AdVocAsStimulus> nonwords = provider.getNonwords();

        int expectedNonwordsLength = 0;
        assertEquals(expectedNonwordsLength, nonwords.size());

        int expectedTotalsStimuli = nOfBands * wordsPerBandInSeries + expectedNonwordsLength;
        assertEquals(expectedTotalsStimuli, provider.getTotalStimuli());

        ArrayList<Integer> nonWordIndices = provider.getNonWordsIndices();
        int expectedWords = (nOfBands - sBand + 1) * 2;
        int expectedLength = (expectedWords * aNonWordPosition) / (aNonWordPosition - 1);
        int expectedNonwords = expectedLength / aNonWordPosition;
        assertEquals(expectedNonwords, nonWordIndices.size());

    }

    /**
     * Test of estinItialiseStimuliState method, of class
     * AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testItialiseStimuliState20() {
        System.out.println("initialiseStimuliState-20");

        int nOfBands = Integer.parseInt(this.numberOfBands);
        String numberOfSeries = "2";
        int nOfSeries = Integer.parseInt(numberOfSeries);
        int wPerBand = Integer.parseInt(this.wordsPerBand);
        int wordsPerBandInSeries = wPerBand / nOfSeries;
        int sBand = Integer.parseInt(this.startBand);
        int aNonWordPosition = Integer.parseInt(this.averageNonWordPoistion);

        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        provider.setnumberOfSeries(numberOfSeries);
        provider.settype("0");
        provider.setnumberOfBands(this.numberOfBands); 
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles); 
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(this.wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.setfineTuningTupleLength("4");
        provider.initialiseStimuliState("");

        ArrayList<ArrayList<AdVocAsStimulus>> words = provider.getWords();
        assertEquals(nOfBands, words.size());
        for (int i = 0; i < nOfBands; i++) {
            assertEquals(wordsPerBandInSeries, words.get(i).size());
        }

        ArrayList<AdVocAsStimulus> nonwords = provider.getNonwords();

        int expectedNonwordsLength = 0;
        assertEquals(expectedNonwordsLength, nonwords.size());

        int expectedTotalsStimuli = nOfBands * wordsPerBandInSeries + expectedNonwordsLength;
        assertEquals(expectedTotalsStimuli, provider.getTotalStimuli());

        ArrayList<Integer> nonWordIndices = provider.getNonWordsIndices();
        int expectedWords = (nOfBands - sBand + 1) * 2;
        int expectedLength = (expectedWords * aNonWordPosition) / (aNonWordPosition - 1);
        int expectedNonwords = expectedLength / aNonWordPosition;
        assertEquals(expectedNonwords, nonWordIndices.size());

    }

    /**
     * Test of estinItialiseStimuliState method, of class
     * AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testItialiseStimuliState21() {
        System.out.println("initialiseStimuliState-21");

        int nOfBands = Integer.parseInt(this.numberOfBands);
        String numberOfSeries = "2";
        int nOfSeries = Integer.parseInt(numberOfSeries);
        int wPerBand = Integer.parseInt(this.wordsPerBand);
        int wordsPerBandInSeries = wPerBand / nOfSeries;
        int sBand = Integer.parseInt(this.startBand);
        int aNonWordPosition = Integer.parseInt(this.averageNonWordPoistion);

        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        provider.setnumberOfSeries(numberOfSeries);
        provider.settype("1");
        provider.setnumberOfBands(this.numberOfBands); 
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles); 
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(this.wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.setfineTuningTupleLength("4");
        provider.initialiseStimuliState("");

        ArrayList<ArrayList<AdVocAsStimulus>> words = provider.getWords();
        assertEquals(nOfBands, words.size());
        for (int i = 0; i < nOfBands; i++) {
            assertEquals(wordsPerBandInSeries, words.get(i).size());
        }

        ArrayList<AdVocAsStimulus> nonwords = provider.getNonwords();

        int expectedNonwordsLength = 0;
        assertEquals(expectedNonwordsLength, nonwords.size());

        int expectedTotalsStimuli = nOfBands * wordsPerBandInSeries + expectedNonwordsLength;
        assertEquals(expectedTotalsStimuli, provider.getTotalStimuli());

        ArrayList<Integer> nonWordIndices = provider.getNonWordsIndices();
        int expectedWords = (nOfBands - sBand + 1) * 2;
        int expectedLength = (expectedWords * aNonWordPosition) / (aNonWordPosition - 1);
        int expectedNonwords = expectedLength / aNonWordPosition;
        assertEquals(expectedNonwords, nonWordIndices.size());

    }

    /**
     * Test of getCurrentStimulusIndex method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetCurrentStimulusIndex() {
        System.out.println("getCurrentStimulusIndex");
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        int nOfBands = Integer.parseInt(this.numberOfBands);
        String numberOfSeries = "2";
        int nOfSeries = Integer.parseInt(numberOfSeries);
        provider.setnumberOfSeries(numberOfSeries);
        provider.settype("1");
        provider.setnumberOfBands(this.numberOfBands); 
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(this.wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.setfineTuningTupleLength("4");
        provider.initialiseStimuliState("");
        provider.hasNextStimulus(0);
        provider.nextStimulus(0);
        int result = provider.getCurrentStimulusIndex();
        assertEquals(0, result);
    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetCurrentStimulus1_1() {
        this.testGetCurrentStimulus("1", "0", "getCurrentStimulus10_1");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetCurrentStimulus1_2() {
        this.testGetCurrentStimulus("1", "0", "getCurrentStimulus10_2");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetCurrentStimulus1_3() {
        this.testGetCurrentStimulus("1", "0", "getCurrentStimulus10_3");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetCurrentStimulus20_1() {
        this.testGetCurrentStimulus("2", "0", "getCurrentStimulus20_1");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetCurrentStimulus20_2() {
        this.testGetCurrentStimulus("2", "0", "getCurrentStimulus20_2");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetCurrentStimulus20_3() {
        this.testGetCurrentStimulus("2", "0", "getCurrentStimulus20_3");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetCurrentStimulus21_1() {
        this.testGetCurrentStimulus("2", "1", "getCurrentStimulus20_1");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetCurrentStimulus21_2() {
        this.testGetCurrentStimulus("2", "1", "getCurrentStimulus20_2");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetCurrentStimulus21_3() {
        this.testGetCurrentStimulus("2", "1", "getCurrentStimulus20_3");

    }

    private void testGetCurrentStimulus(String numberOfSeries, String type, String info) {

        System.out.println(info);

        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);

        int nOfBands = Integer.parseInt(this.numberOfBands);
        int nOfSeries = Integer.parseInt(numberOfSeries);
        int wPerBand = Integer.parseInt(this.wordsPerBand);
        int wordsPerBandInSeries = wPerBand / nOfSeries;
        int sBand = Integer.parseInt(this.startBand);
        int aNonWordPosition = Integer.parseInt(this.averageNonWordPoistion);

        provider.setnumberOfSeries(numberOfSeries);
        provider.settype(type);
        provider.setnumberOfBands(this.numberOfBands); 
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles); 
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(this.wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.setfineTuningTupleLength("4");
        provider.initialiseStimuliState("");
        provider.hasNextStimulus(0);
        provider.nextStimulus(0);
        assertEquals(1, provider.getResponseRecord().size());
        Stimulus stimulus = provider.getCurrentStimulus();
        assertTrue(stimulus != null);
        String label = stimulus.getLabel();
        assertTrue(label != null);
        //System.out.println("Label: " + label);
       BookkeepingStimulus<AdVocAsStimulus> bStimulus = provider.getResponseRecord().get(provider.getCurrentStimulusIndex());
        int expectedBand = stimulus.getCorrectResponses().equals(Vocabulary.WORD) ? Integer.parseInt(this.startBand) : -1;
        assertEquals(expectedBand, bStimulus.getStimulus().getbandLabel());
    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testIsCorrectResponse_1() throws Exception {
        this.testIsCorrectResponse("1", "0", "testIsCorrectResponse_1");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testIsCorrectResponse_20() throws Exception {
        this.testIsCorrectResponse("2", "0", "testIsCorrectResponse_20");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testIsCorrectResponse_21() throws Exception {
        this.testIsCorrectResponse("2", "1", "testIsCorrectResponse_21");

    }

    private void testIsCorrectResponse(String numberOfSeries, String type, String info) throws Exception {
        System.out.println(info);
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);

        int nOfBands = Integer.parseInt(this.numberOfBands);
        int nOfSeries = Integer.parseInt(numberOfSeries);
        int wPerBand = Integer.parseInt(this.wordsPerBand);
        int wordsPerBandInSeries = wPerBand / nOfSeries;
        int sBand = Integer.parseInt(this.startBand);
        int aNonWordPosition = Integer.parseInt(this.averageNonWordPoistion);

        provider.setnumberOfSeries(numberOfSeries);
        provider.settype(type);
        provider.setnumberOfBands(this.numberOfBands); provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles); 
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(this.wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.setfineTuningTupleLength("4");
        provider.initialiseStimuliState("");

        //stimulus 1
        provider.hasNextStimulus(0);
        provider.nextStimulus(0);
        Stimulus stimulus = provider.getCurrentStimulus();
        boolean result = provider.isCorrectResponse(stimulus, stimulus.getCorrectResponses());
        assertTrue(result);

        BookkeepingStimulus<AdVocAsStimulus> bStimulus = provider.getResponseRecord().get(0);
        assertTrue(bStimulus.getCorrectness());

        boolean expectedReaction = stimulus.getCorrectResponses().equals(Vocabulary.WORD);
        assertEquals(expectedReaction, bStimulus.getReaction());

        // stimulus 2
        provider.hasNextStimulus(0);
        provider.nextStimulus(0);
        Stimulus stimulus2 = provider.getCurrentStimulus();
        // making worng response
        String response2 = Vocabulary.NONWORD;
        if (stimulus2.getCorrectResponses().equals(Vocabulary.NONWORD)) {
            response2 = Vocabulary.WORD;
        } else {
            if (!stimulus2.getCorrectResponses().equals(Vocabulary.WORD)) {
                throw new Exception("The reaction is neither nonword nor word, something went terribly worng.");
            }
        }
        boolean result2 = provider.isCorrectResponse(stimulus2, response2);
        assertFalse(result2);

        BookkeepingStimulus<AdVocAsStimulus> bStimulus2 = provider.getResponseRecord().get(1);
        assertFalse(bStimulus2.getCorrectness());

        boolean expectedCorrectReaction2 = stimulus2.getCorrectResponses().equals(Vocabulary.WORD);
        assertEquals(!expectedCorrectReaction2, bStimulus2.getReaction());

    }

    private void testGetTotalStimuli(String numberOfSeries, String type, String info, int nNonwords) {
        System.out.println(info);
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        int nOfBands = Integer.parseInt(this.numberOfBands);
        int nOfSeries = Integer.parseInt(numberOfSeries);

        provider.setnumberOfSeries(numberOfSeries);

        provider.settype(type);
        provider.setnumberOfBands(this.numberOfBands); 
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles); 
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(this.wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.setfineTuningTupleLength("4");
        provider.initialiseStimuliState("");
        int wPerBand = Integer.parseInt(this.wordsPerBand);
        int wordsPerBandInSeries = wPerBand / nOfSeries;
        provider.initialiseStimuliState("");

        int totalStimuli = provider.getTotalStimuli();
        assertEquals(nNonwords + nOfBands * wordsPerBandInSeries, totalStimuli);
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetTotalStimuli10_1() {
        int nonWordsLength = 0;
        this.testGetTotalStimuli("1", "0", "testGetTotalStimuli10_1", nonWordsLength);
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetTotalStimuli10_2() {
        int nonWordsLength = 0;
        this.testGetTotalStimuli("1", "0", "testGetTotalStimuli10_1", nonWordsLength);
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetTotalStimuli10_3() {
        int nonWordsLength = 0;
        this.testGetTotalStimuli("1", "0", "testGetTotalStimuli10_1", nonWordsLength);
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetTotalStimuli20_1() {
        int nonWordsLength = 0;
        this.testGetTotalStimuli("2", "0", "testGetTotalStimuli20_1", nonWordsLength);
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetTotalStimuli20_2() {
        int nonWordsLength = 0;
        this.testGetTotalStimuli("2", "0", "testGetTotalStimuli20_2", nonWordsLength);
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetTotalStimuli20_3() {
        int nonWordsLength = 0;
        this.testGetTotalStimuli("2", "0", "testGetTotalStimuli20_3", nonWordsLength);
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetTotalStimuli21_1() {
        int nonWordsLength = 0;
        this.testGetTotalStimuli("2", "1", "testGetTotalStimuli21_1", nonWordsLength);
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetTotalStimuli21_2() {
        int nonWordsLength = 0;
        this.testGetTotalStimuli("2", "1", "testGetTotalStimuli21_2", nonWordsLength);
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetTotalStimuli21_3() {
        int nonWordsLength = 0;
        this.testGetTotalStimuli("2", "1", "testGetTotalStimuli21_3", nonWordsLength);
    }

    private void getStimuliReport(String numberOfSeries, String type, String info) throws Exception {
        System.out.println("getStimuliReport");
        AdVocAsStimuliProvider provider = this.testHasNextStimulus(numberOfSeries, type, "");

        Map<String, String> result = provider.getStimuliReport("user_summary");
        Set<String> keys = result.keySet();
        // header + data
        assertTrue(keys.size() == 2);
        for (String key : keys) {
            String row = result.get(key);
            int index = row.indexOf(";");
            assertTrue(index > -1);
        }

        result = provider.getStimuliReport("fast_track");
        keys = result.keySet();
        // header + data
        assertTrue(keys.size() > 2);
        for (String key : keys) {
            String row = result.get(key);
            int index = row.indexOf(";");
            assertTrue(index > -1);
        }

        result = provider.getStimuliReport("fine_tuning");
        keys = result.keySet();
        // header + data
        assertTrue(keys.size() >= 1);
        for (String key : keys) {
            String row = result.get(key);
            int index = row.indexOf(";");
            assertTrue(index > -1);
        }
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void getStimuliReport_1() throws Exception {
        this.getStimuliReport("1", "0", "getStimuliReport_1");
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void getStimuliReport_20() throws Exception {
        this.getStimuliReport("2", "0", "getStimuliReport_20");
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void getStimuliReport_21() throws Exception {
        this.getStimuliReport("2", "1", "getStimuliReport_21");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testHasNextStimulus10_1() throws Exception {
        this.testHasNextStimulus("1", "0", "testHasNextStimulus10_1");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testHasNextStimulus10_2() throws Exception {
        this.testHasNextStimulus("1", "0", "testHasNextStimulus10_2");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testHasNextStimulus10_3() throws Exception {
        this.testHasNextStimulus("1", "0", "testHasNextStimulus10_3");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testHasNextStimulus20_1() throws Exception {
        this.testHasNextStimulus("2", "0", "testHasNextStimulus20_1");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testHasNextStimulus20_2() throws Exception {
        this.testHasNextStimulus("2", "0", "testHasNextStimulus20_2");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testHasNextStimulus20_3() throws Exception {
        this.testHasNextStimulus("2", "0", "testHasNextStimulus20_3");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testHasNextStimulus21_1() throws Exception {
        this.testHasNextStimulus("2", "1", "testHasNextStimulus21_1");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testHasNextStimulus21_2() throws Exception {
        this.testHasNextStimulus("2", "1", "testHasNextStimulus21_2");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testHasNextStimulus21_3() throws Exception {
        this.testHasNextStimulus("2", "1", "testHasNextStimulus21_3");
    }

    // also tests nextStimulus
    private AdVocAsStimuliProvider testHasNextStimulus(String numberOfSeries, String type, String info) throws Exception {
        System.out.println(info);
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        int nOfBands = Integer.parseInt(this.numberOfBands);
        int nOfSeries = Integer.parseInt(numberOfSeries);
        provider.setnumberOfSeries(numberOfSeries);
        provider.settype(type);
        provider.setnumberOfBands(this.numberOfBands); 
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles); 
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(this.wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.setfineTuningTupleLength("4");
        provider.initialiseStimuliState("");
        boolean result = provider.hasNextStimulus(0);// does not depend on increment
        int invariant = provider.getResponseRecord().size() + provider.getNonwords().size() + this.getListOfListLength(provider.getWords());

        // but on internal state of the process
        assertTrue(result);

        provider.nextStimulus(0);
        assertEquals(1, provider.getResponseRecord().size());
        int invariant1 = provider.getResponseRecord().size() + provider.getNonwords().size() + this.getListOfListLength(provider.getWords());
        assertEquals(invariant, invariant1);

        //experiment 0, correct answer
        int ind1 = provider.getCurrentStimulusIndex();
        assertEquals(0, ind1);
        AdVocAsStimulus stimulus = provider.getCurrentStimulus();

        provider.isCorrectResponse(stimulus, stimulus.getCorrectResponses());

        boolean result1 = provider.hasNextStimulus(0);
        assertTrue(result1);
        int sBand = Integer.parseInt(this.startBand);
        int expectedBand = stimulus.getCorrectResponses().equals(Vocabulary.WORD) ? (sBand + 1) : sBand;
        assertEquals(expectedBand, provider.getCurrentBandNumber());

        provider.nextStimulus(0);
        assertEquals(2, provider.getResponseRecord().size());
        int invariant2 = provider.getResponseRecord().size() + provider.getNonwords().size() + this.getListOfListLength(provider.getWords());
        assertEquals(invariant, invariant2);

        //experiment 1, wrong answer, second chance must be given
        int ind2 = provider.getCurrentStimulusIndex();
        assertEquals(1, ind2);

        AdVocAsStimulus stimulus2 = provider.getCurrentStimulus();
        String correctResponse = stimulus2.getCorrectResponses();
        String response = null;
        if (correctResponse.equals(Vocabulary.WORD)) {
            response = Vocabulary.NONWORD;
        }
        if (correctResponse.equals(Vocabulary.NONWORD)) {
            response = Vocabulary.WORD;
        }
        if (response == null) {
            throw new Exception("Wrong reaction");
        }

        provider.isCorrectResponse(stimulus2, response);
        boolean result12 = provider.hasNextStimulus(0);
        assertTrue(result12);
        assertEquals(expectedBand, provider.getCurrentBandNumber());
        assertEquals(-1, provider.getBestFastTrackBand()); // stil on fast track, expecting the secind chance

        provider.nextStimulus(0); // give the second chance
        assertEquals(3, provider.getResponseRecord().size());
        int invariant3 = provider.getResponseRecord().size() + provider.getNonwords().size() + this.getListOfListLength(provider.getWords());
        assertEquals(invariant, invariant3);

        int ind3 = provider.getCurrentStimulusIndex();
        assertEquals(2, ind3);
        AdVocAsStimulus stimulus3 = provider.getCurrentStimulus();
        String correctResponse3 = stimulus3.getCorrectResponses();
        String response3 = null;
        if (correctResponse3.equals(Vocabulary.WORD)) {
            response3 = Vocabulary.NONWORD;
        }
        if (correctResponse3.equals(Vocabulary.NONWORD)) {
            response3 = Vocabulary.WORD;
        }
        if (response3 == null) {
            throw new Exception("Wrong reaction");
        }

        boolean result3 = provider.hasNextStimulus(0);
        assertTrue(result3);
        // now current band represents the last cirrect band on the fast track
        assertEquals(expectedBand, provider.getCurrentBandNumber());
        assertEquals(provider.getCurrentBandNumber(), provider.getBestFastTrackBand()); // stil on fast track, expecting the secind chance

        return provider;

    }

    /**
     * Test of getCurrentStimulusUniqueId method, of class
     * AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetCurrentStimulusUniqueId_1() {
        this.testGetCurrentStimulusUniqueId("1", "0", "testGetCurrentStimulusUniqueId_1");
    }

    /**
     * Test of getCurrentStimulusUniqueId method, of class
     * AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetCurrentStimulusUniqueId_20() {
        this.testGetCurrentStimulusUniqueId("2", "0", "testGetCurrentStimulusUniqueId_20");
    }

    /**
     * Test of getCurrentStimulusUniqueId method, of class
     * AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testGetCurrentStimulusUniqueId_21() {
        this.testGetCurrentStimulusUniqueId("2", "1", "testGetCurrentStimulusUniqueId_21");
    }

    private void testGetCurrentStimulusUniqueId(String numberOfSeries, String type, String info) {
        System.out.println(info);
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        int nOfBands = Integer.parseInt(this.numberOfBands);
        int nOfSeries = Integer.parseInt(numberOfSeries);
        provider.setnumberOfSeries(numberOfSeries);
        provider.settype(type);
        provider.setnumberOfBands(this.numberOfBands); 
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles); 
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(this.wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.setfineTuningTupleLength("4");
        provider.initialiseStimuliState("");
        provider.hasNextStimulus(0);
        provider.nextStimulus(0);
        String result = provider.getCurrentStimulusUniqueId();
        assertTrue(result != null);
    }

    /**
     * Test of detectLoop method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testDetectLoop() {
        System.out.println("detectLoop");
        int[] arr1 = {42, 43, 42, 43, 42, 43, 42};
        boolean result1 = AdVocAsStimuliProvider.detectLoop(arr1);
        assertEquals(true, result1);
        int[] arr2 = {42, 43, 42, 43, 42, 43, 45};
        boolean result2 = AdVocAsStimuliProvider.detectLoop(arr2);
        assertEquals(false, result2);
        int[] arr3 = {43, 42, 43, 42, 43, 42, 45, 42};
        boolean result3 = AdVocAsStimuliProvider.detectLoop(arr3);
        assertEquals(false, result3);
    }

    /**
     * Test of shiftFIFO method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testShiftFIFO() {
        System.out.println("shiftFIFO");
        int[] fifo = {0, 1, 2, 3, 4, 5, 6};
        int newelement = 7;
        AdVocAsStimuliProvider.shiftFIFO(fifo, newelement);
        for (int i = 0; i < 7; i++) {
            assertEquals(i + 1, fifo[i]);
        }
    }

    /**
     * Test of shiftFIFO method, of class AdVocAsStimuliProvider.
     */
    @Ignore @Test
    public void testMostOftenVisitedBandNumber() {
        System.out.println(" MostOftenVisitedBandMumber");
        int[] visitCounter = {1, 3, 2, 3, 3, 3, 1};
        // indices {1,3,4,5}
        // ind = 1, indSym = 2
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        int currentIndex1 = 2; // at 2
        int bandNumber1 = provider.mostOftenVisitedBandNumber(visitCounter, currentIndex1);
        assertEquals(4, bandNumber1);

        int currentIndex2 = 3; // at 3
        int bandNumber2 = provider.mostOftenVisitedBandNumber(visitCounter, currentIndex2);
        assertEquals(4, bandNumber2);
    }

    private int getListOfListLength(ArrayList<ArrayList<AdVocAsStimulus>> ll) {
        int retVal = 0;
        for (ArrayList<AdVocAsStimulus> l : ll) {
            retVal += l.size();
        }
        return retVal;
    }

    private void testPercentageBandTable(String numberOfSeries, String type, String info) {
        System.out.println(info);

        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        int nOfBands = Integer.parseInt(this.numberOfBands);
        int nOfSeries = Integer.parseInt(numberOfSeries);
        provider.setnumberOfSeries(numberOfSeries);
        provider.settype(type);
        provider.setnumberOfBands(this.numberOfBands); 
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles); 
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(this.wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.setfineTuningTupleLength("4");
        provider.initialiseStimuliState("");

        LinkedHashMap<Long, Integer> percentageTable = provider.getPercentageBandTable();

        Long one = new Long(1);
       // which band number correspond to 1 percent of # 54 is 100%?
        // 54/100 = 0.54 ~ 1
        assertEquals(new Integer(1), percentageTable.get(one));

        Long nn = new Long(99);
        // which band number correspond to 99 percent of # 54 is 100%?
        // 54*0.99 = 53.46 ~ 53
        assertEquals(new Integer(53), percentageTable.get(nn));

        for (long p = 1; p <= 9; p++) {
            Long percentage = p * 10;
            float bnd = ((float) (54*percentage))/ ((float) 100);
            int roundBnd = Math.round(bnd);
            assertEquals(new Integer(roundBnd), percentageTable.get(percentage));
        }

    }

    @Ignore @Test
    public void testPercentageBandTable_1() {
        this.testPercentageBandTable("1", "0", "testPercentageBandTable_1");

    }

    @Ignore @Test
    public void testPercentageBandTable_20() {
        this.testPercentageBandTable("2", "0", "testPercentageBandTable_20");

    }

    @Ignore @Test
    public void testPercentageBandTable_21() {
        this.testPercentageBandTable("2", "1", "testPercentageBandTable_21");

    }

    @Ignore @Test
    public void generalRandomTest1() throws Exception {
        for (int i = 1; i < 11; i++) {
            double prob = 0.5 + i * 0.05;
            System.out.println("Probabilistic test for 1 round, prob of corret answer is " + prob);
            this.testRound(prob, "1", "0");
        }
    }

    @Ignore @Test
    public void generalRandomTest2() throws Exception {
        for (int i = 1; i < 11; i++) {
            double prob = 0.5 + i * 0.05;
            System.out.println("Probabilistic test for 2 rounds, prob of corret answer is " + prob);
            System.out.println("Round 1");
            AdVocAsStimuliProvider provider1 = this.testRound(prob, "2", "0");
            int score1 = provider1.getBandScore();
            System.out.println("Band Score: " + score1);
            System.out.println("Round 2");
            AdVocAsStimuliProvider provider2 = this.testRound(prob, "2", "0");
            int score2 = provider2.getBandScore();
            System.out.println("Band Score: " + score2);
            if (score1 != score2) {
                System.out.println("Attention. Difference between score in consecutive rounds is detected, score 1 and 2 are " + score1 + " and " + score2 + " respectively.");
            }
            System.out.println("***");
        }
    }

    @Ignore @Test
    public void notEnoughStimuliTest() throws Exception {
        this.longFineTuningTest();
    }

    public AdVocAsStimuliProvider testRound(double prob, String numberOfSeries, String type) throws Exception {
        Random rnd = new Random();

        int nOfBands = Integer.parseInt(this.numberOfBands);
        int nOfSeries = Integer.parseInt(numberOfSeries);
        int wPerBand = Integer.parseInt(this.wordsPerBand);
        int wordsPerBandInSeries = wPerBand / nOfSeries;
        int sBand = Integer.parseInt(this.startBand);
        int aNonWordPosition = Integer.parseInt(this.averageNonWordPoistion);

        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        provider.setnumberOfSeries(numberOfSeries);
        provider.settype(type);
        provider.setnumberOfBands(this.numberOfBands); 
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles); 
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(this.wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.setfineTuningTupleLength("4");
        provider.initialiseStimuliState("");

        boolean hasNextStimulus = provider.hasNextStimulus(0);
        int currentExperimentCount = 0;
        while (hasNextStimulus) {
            ArrayList<BookkeepingStimulus<AdVocAsStimulus>> ft = provider.getFTtuple();
            if (ft.size() == 4) {
                this.checkFreshFineTuningTuple(ft);
            }
            provider.nextStimulus(0);
            currentExperimentCount = provider.getCurrentStimulusIndex();
            //System.out.println(currentExperimentCount);
            AdVocAsStimulus stimulus = provider.getCurrentStimulus();
            String answer = this.probabilisticAnswerer(stimulus, prob, rnd);
            boolean isCorrect = provider.isCorrectResponse(stimulus, answer);
            hasNextStimulus = provider.hasNextStimulus(0);
        }

        this.checkAllWordsAreDifferent(provider.getResponseRecord());
        this.checkNonWordFrequenceFastTrack(provider.getResponseRecord(), provider.getEndFastTrackTimeTick());

        boolean enoughFineTuningStimulae = provider.getEnoughFinetuningStimuli();
        boolean cycle2 = provider.getCycel2();
        boolean champion = provider.getChampion();
        boolean looser = provider.getLooser();

        if (enoughFineTuningStimulae && !champion) { // the fine tunig has been stoppped because of false reaction and further checks (cycle-2 or looser)
            int recordSize = provider.getResponseRecord().size();
            BookkeepingStimulus<AdVocAsStimulus> lastStimulus = provider.getResponseRecord().get(recordSize - 1);
            assertFalse(lastStimulus.getCorrectness());
            assertTrue(cycle2 || looser);
            if (looser) {
                assertEquals(1, provider.getBandScore());
                assertEquals(2, provider.getPercentageScore()); // 100/54 = 1.85... rounded to two
            } else {
                assertTrue(provider.getPercentageScore() > 1);
            }

        }

        if (champion) {
            assertEquals(nOfBands, provider.getBandScore());
            assertEquals(100, provider.getPercentageScore());
        }

        this.checkFastTrack(provider.getResponseRecord(), provider.getEndFastTrackTimeTick(), provider.getBestFastTrackBand());
        this.checkFineTuning(provider.getResponseRecord(), provider.getEndFastTrackTimeTick(), provider.getBestFastTrackBand(), cycle2, provider.getBandScore());

        // checking generating graph
        // first check if the sample set is generated ok
        LinkedHashMap<Integer, String> samples = provider.retrieveSampleWords(provider.getResponseRecord(), provider.getWords());

        Vocabulary vocab = new Vocabulary(nOfBands, wordsPerBandInSeries);
        AdVocAsStimulus[][] wordArray = null;
        ArrayList<ArrayList<AdVocAsStimulus>> wordsInBands = null;

        assertEquals(nOfBands, samples.keySet().size());
        for (int bandNumber = 1; bandNumber <= nOfBands; bandNumber++) {
            assertTrue(samples.containsKey(bandNumber));
            String sample = samples.get(bandNumber);
            assertNotNull(sample);
            ArrayList<String> words = this.getSpellings(wordsInBands.get(bandNumber - 1));
            assertTrue(words.contains(sample));
        }

        // now check if the graph sequence for percentage is ok 
        LinkedHashMap<Long, String> graph = provider.generateDiagramSequence(provider.getResponseRecord(), provider.getPercentageBandTable());

        Integer bandScore = provider.getBandScore();

        Long percentScore = provider.getPercentageScore();
        assertTrue(graph.containsKey(percentScore));
        assertNotNull(graph.get(percentScore));
        ArrayList<String> wordsBS = this.getSpellings(wordsInBands.get(bandScore - 1));
        assertTrue(wordsBS.contains(graph.get(percentScore)));

        if (percentScore >= 5) {
            Long one = new Long(1);
            assertTrue(graph.containsKey(one));
            assertNotNull(graph.get(one));
            // which band number correspond to 1 percent of # 54 is 100%?
            // 54/100 = 0.54 ~ 1
            ArrayList<String> wordsOnePercent = this.getSpellings(wordsInBands.get(0));
            assertTrue(wordsOnePercent.contains(graph.get(one)));
        }

        if (percentScore < 95) {
            Long nn = new Long(99);
            if (!graph.containsKey(nn)) {
                System.out.println("alarm: no key for percentageScore "+nn.toString());
            }
            assertTrue(graph.containsKey(nn));
            assertNotNull(graph.get(nn));
            // which band number correspond to 99 percent of # 54 is 100%?
            // 54*0.99 = 53.46 ~ 53
            ArrayList<String> words99Percent = this.getSpellings(wordsInBands.get(52));
            assertTrue(words99Percent.contains(graph.get(nn)));
        }

        for (long p = 1; p <= 9; p++) {
            Long percentage = p * 10;
            if (percentScore >= percentage - 5 && percentScore < percentage + 5 && !percentage.equals(percentScore)) {
                assertFalse(graph.containsKey(percentage)); // the participant score is instead
            } else {
                assertTrue(graph.containsKey(percentage));
            }

        }

        return provider;
    }

    private ArrayList<String> getSpellings(ArrayList<AdVocAsStimulus> stimuli) {
        ArrayList<String> retVal = new ArrayList<>(stimuli.size());
        for (AdVocAsStimulus stimulus : stimuli) {
            retVal.add(stimulus.getLabel());
        }
        return retVal;
    }

    ; 

    public AdVocAsStimuliProvider longFineTuningTest() throws Exception {

        int nOfBands = Integer.parseInt(this.numberOfBands);
        String numberOfSeries = "1";
        int nOfSeries = Integer.parseInt(numberOfSeries);
        int wPerBand = Integer.parseInt(this.wordsPerBand);
        int wordsPerBandInSeries = wPerBand / nOfSeries;
        int sBand = Integer.parseInt(this.startBand);
        int aNonWordPosition = Integer.parseInt(this.averageNonWordPoistion);

        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        provider.setnumberOfSeries(numberOfSeries);
        provider.settype("0");
        provider.setnumberOfBands(this.numberOfBands); 
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles); 
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(this.wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.setfineTuningTupleLength("4");
        provider.initialiseStimuliState("");

        // make to wrong answers to start fine tuning immediately
        provider.hasNextStimulus(0);
        provider.nextStimulus(0);
        AdVocAsStimulus stimulus = provider.getCurrentStimulus();
        String answer = this.makeResponseWrong(stimulus);
        boolean isCorrect = provider.isCorrectResponse(stimulus, answer);

        provider.hasNextStimulus(0);
        provider.nextStimulus(0);
        stimulus = provider.getCurrentStimulus();
        answer = this.makeResponseWrong(stimulus);
        isCorrect = provider.isCorrectResponse(stimulus, answer);

        boolean hasNextStimulus = provider.hasNextStimulus(0);
        // fine tuning correct till the band is 54 then back till the band is 20
        boolean runHigher = true;
        int tupleCounter = 0;
        while (hasNextStimulus) {

            ArrayList<BookkeepingStimulus<AdVocAsStimulus>> ft = provider.getFTtuple();
            if (ft.size() == 4) {
                this.checkFreshFineTuningTuple(ft);
                tupleCounter++;
            }

            if (runHigher && provider.getCurrentBandNumber() == 54) {
                runHigher = false; // start climbing backwards
            }
            if (!runHigher && provider.getCurrentBandNumber() == 20) {
                runHigher = true; // start climbing forward
            }
            provider.nextStimulus(0);
            stimulus = provider.getCurrentStimulus();
            if (runHigher) {
                answer = stimulus.getCorrectResponses();
            } else {
                answer = this.makeResponseWrong(stimulus);
            }
            isCorrect = provider.isCorrectResponse(stimulus, answer);
            hasNextStimulus = provider.hasNextStimulus(0);

        }

        this.checkAllWordsAreDifferent(provider.getResponseRecord());

        int minTuples = (9 / (Integer.parseInt(this.fineTuningTupleLength) - 1)) * (nOfBands - sBand);
        assertTrue(tupleCounter > minTuples);

        boolean enoughFineTuningStimulae = provider.getEnoughFinetuningStimuli();
        boolean cycle2 = provider.getCycel2();
        boolean champion = provider.getChampion();
        boolean looser = provider.getLooser();

        assertFalse(enoughFineTuningStimulae);
        assertFalse(cycle2);
        assertFalse(looser);
        assertFalse(champion);
        assertEquals(20, provider.getBestFastTrackBand());

        this.checkFastTrack(provider.getResponseRecord(), provider.getEndFastTrackTimeTick(), provider.getBestFastTrackBand());
        this.checkFineTuning(provider.getResponseRecord(), provider.getEndFastTrackTimeTick(), provider.getBestFastTrackBand(), cycle2, provider.getBandScore());

        return provider;

    }

    private String probabilisticAnswerer(AdVocAsStimulus stimulus, double correctnessUpperBound, Random rnd) throws Exception {
        String retVal = stimulus.getCorrectResponses();
        double rndDouble = rnd.nextDouble();
        //System.out.println("*****");
        //System.out.println(retVal);
        //System.out.println(rndDouble);
        if (rndDouble > correctnessUpperBound) { // spoil the answer
            if (retVal.equals(Vocabulary.WORD)) {
                retVal = Vocabulary.NONWORD;
            } else {
                if (retVal.equals(Vocabulary.NONWORD)) {
                    retVal = Vocabulary.WORD;
                } else {
                    throw new Exception("Wrong correct reaction in the stimulus, neither word, nor nonword: " + retVal);
                }
            }
        }
        //System.out.println(retVal);
        //System.out.println("*****");
        return retVal;
    }

    private String makeResponseWrong(AdVocAsStimulus stimulus) {
        String answer = Vocabulary.NONWORD;
        if (stimulus.getCorrectResponses().equals(Vocabulary.NONWORD)) {
            answer = Vocabulary.WORD;
        };
        return answer;
    }

    private void checkFreshFineTuningTuple(ArrayList<BookkeepingStimulus<AdVocAsStimulus>> tuple) {
        int nNonwords = 0;
        for (BookkeepingStimulus<AdVocAsStimulus> stimulus : tuple) {
            assertEquals(null, stimulus.getReaction());
            assertEquals(null, stimulus.getCorrectness());
            assertNotEquals(0, stimulus.getStimulus().getBandNumber());
            if (stimulus.getStimulus().getBandNumber() < 0) {
                nNonwords++;
            }
        }
        assertEquals(1, nNonwords);
    }

    private void checkNonWordFrequenceFastTrack(ArrayList<BookkeepingStimulus<AdVocAsStimulus>> records, int timeTick) {
        int counterNonwords = 0;
        double frequency = 0;

        for (int i = 0; i <= timeTick; i++) {
            BookkeepingStimulus<AdVocAsStimulus> stimulus = records.get(i);
            if (stimulus.getStimulus().getBandNumber() == -1) {
                counterNonwords++;
            }
            frequency = ((double) counterNonwords) / ((double) (i + 1));
        }
        if (timeTick >= 3) {
            assertTrue(frequency > 0);
        }
        double idealFrequency = 1.0 / Double.valueOf(this.averageNonWordPoistion);
        double diff = Math.abs(frequency - idealFrequency);
        //System.out.println(frequency);
        //System.out.println(idealFrequency);
        if (timeTick >= 12) {
            assertTrue(diff <= 0.2);
        }
    }

    private void checkAllWordsAreDifferent(ArrayList<BookkeepingStimulus<AdVocAsStimulus>> records) {
        int sz = records.size();
        HashSet<BookkeepingStimulus<AdVocAsStimulus>> testEqualitySet = new HashSet(records);
        assertEquals(testEqualitySet.size(), sz);
        assertEquals(sz, records.size());
    }

    private void checkFastTrack(ArrayList<BookkeepingStimulus<AdVocAsStimulus>> records, int lastTimeTickFastTrack, int bestFastTrackBand) {
        BookkeepingStimulus<AdVocAsStimulus> bStimulus = records.get(0);
        BookkeepingStimulus<AdVocAsStimulus> previousbStimulus;
        AdVocAsStimulus stimulus = bStimulus.getStimulus();
         AdVocAsStimulus previousStimulus;
        if (stimulus.getBandNumber() > 0) {
            assertEquals(Integer.parseInt(this.startBand), stimulus.getBandNumber());
        }
        for (int i = 1; i <= lastTimeTickFastTrack; i++) {
            previousbStimulus = bStimulus;
            previousStimulus = stimulus;
            
            bStimulus = records.get(i);
            stimulus = bStimulus.getStimulus();
            
            if (previousbStimulus.getCorrectness()) { // correcr reaction
                if (previousStimulus.getBandNumber() > 0 && stimulus.getBandNumber() > 0 && previousStimulus.getBandNumber() < Integer.parseInt(this.numberOfBands)) {
                    assertEquals(previousStimulus.getBandNumber() + 1, stimulus.getBandNumber());
                }
            } else {
                if (i >= 2) { // check pre-previous answer
                    boolean prepreCorrectness = records.get(i - 2).getCorrectness();
                    if (prepreCorrectness) {
                        // we had the first incorrect answer in a row coming to this band
                        // this is the second chance stimulus
                        if (previousStimulus.getBandNumber() > 0 && stimulus.getBandNumber() > 0) {
                            // second chance
                            assertEquals(previousStimulus.getBandNumber(), stimulus.getBandNumber());
                        }
                    } else {
                        // preprevious and previous reaction were wrong!!!
                        // we have proceeded after the second wrong answer in a row, it should not happen!!
                        assertTrue(false);
                    }
                }
            }
        }

        bStimulus = records.get(lastTimeTickFastTrack);
        stimulus = bStimulus.getStimulus();
        
        if (bStimulus.getCorrectness()) {
            // we stopped fast track because we have reached the end of the bands
            if (stimulus.getBandNumber() > 0) {
                assertEquals(Integer.parseInt(this.numberOfBands), stimulus.getBandNumber());
            }
        } else {
            // we stopped because there were two incorrect answers in a row
            previousbStimulus = records.get(lastTimeTickFastTrack - 1);
            previousStimulus = previousbStimulus.getStimulus();
            
            assertFalse(previousbStimulus.getCorrectness());
            if (lastTimeTickFastTrack >= 2) {
                assertTrue(records.get(lastTimeTickFastTrack - 2).getCorrectness());
            }
        }
        if (stimulus.getBandNumber() > 0) {
            assertEquals(bestFastTrackBand, stimulus.getBandNumber());
        }

    }

    private void checkFineTuning(ArrayList<BookkeepingStimulus<AdVocAsStimulus>> records, int lastTimeTickFastTrack, int bestFastTrackBand, boolean cycle2, int score) {
        int counterInTuple = 0;
        AdVocAsStimulus stimulus;
        ArrayList<Integer> bandSequence = new ArrayList<>();
        ArrayList<Boolean> bandSwitchReason = new ArrayList<>();
        int currentBandNumber = -1;

        for (int i = lastTimeTickFastTrack + 1; i < records.size(); i++) {
            
            BookkeepingStimulus<AdVocAsStimulus>  bStimulus = records.get(i);
            stimulus = bStimulus.getStimulus();
            
            if (stimulus.getBandNumber() > 0) {
                currentBandNumber = stimulus.getBandNumber();
            }
            if (bStimulus.getCorrectness()) {
                counterInTuple++;
                if (counterInTuple == Integer.parseInt(this.fineTuningTupleLength)) {
                    // all  4 ccorrect answers in a row
                    // they all must be in 1 band (except the nonword)
                    bandSequence.add(currentBandNumber);
                    bandSwitchReason.add(true);
                    int nonWordCounter = 0;

                    // check the tuple
                    for (int j = 0; j < Integer.parseInt(this.fineTuningTupleLength); j++) {
                        if (records.get(i - j).getStimulus().getBandNumber() > 0) {
                            // all words in the tuple must be in one band
                            assertEquals(currentBandNumber, records.get(i - j).getStimulus().getBandNumber());
                        } else {
                            nonWordCounter++;
                        }
                    }
                    // there must be exactly one nonword per tuple
                    assertEquals(1, nonWordCounter);

                    // initialise next step
                    currentBandNumber = -1;
                    counterInTuple = 0;
                }
            } else { // wrong answer -- switch band to the lower one
                bandSequence.add(currentBandNumber);
                bandSwitchReason.add(false);
                // initialise next step
                counterInTuple = 0;
                currentBandNumber = -1;
            }
        }

        // correction for the cases where the first answer in the tuple was wrong
        // and it was a non-word, so no band is detected
        int nOfBands = Integer.parseInt(this.numberOfBands);
        for (int i = 0; i < bandSequence.size(); i++) {
            if (bandSequence.get(i) < 0) { // have not been defined
                if (i > 0) {
                    if (bandSwitchReason.get(i - 1)) { // the reason for changing for the current band from the previous one 
                        //was all 4 atoms correct
                        if (bandSequence.get(i - 1) < nOfBands) {
                            bandSequence.set(i, bandSequence.get(i - 1) + 1);
                        } else {
                            bandSequence.set(i, nOfBands);
                        }
                    } else {
                        // the reason for changing for the current band from the previous one 
                        //was an error of the previous band
                        if (bandSequence.get(i - 1) > 1) {
                            bandSequence.set(i, bandSequence.get(i - 1) - 1);
                        } else {
                            bandSequence.set(i, 1);
                        }
                    }
                } else { // we are on the first band and it is undefined
                    bandSequence.set(0, bestFastTrackBand);
                }
            }
        }

        // fine tuning starting check
        assertEquals(bestFastTrackBand, bandSequence.get(0).intValue());

        // if the bands where changed correctly
        for (int i = 1; i < bandSequence.size(); i++) {

            if (bandSwitchReason.get(i - 1)) { // the reason for changing for the current band from the previous one 
                //was all 4 atoms correct
                if (bandSequence.get(i - 1) != nOfBands) {
                    assertEquals(bandSequence.get(i - 1) + 1, bandSequence.get(i).intValue());
                }
            } else {
                // the reason for changing for the current band from the previous one 
                //was an error of the previous band
                if (bandSequence.get(i - 1) != 1) {
                    assertEquals(bandSequence.get(i - 1) - 1, bandSequence.get(i).intValue());
                }
            }
        }

        if (cycle2) {
            int lastIndex = bandSequence.size() - 1;
            // ignore the last erronenous reaction
            assertEquals(bandSequence.get(lastIndex - 1).intValue(), bandSequence.get(lastIndex - 3).intValue());
            assertEquals(bandSequence.get(lastIndex - 1).intValue(), bandSequence.get(lastIndex - 5).intValue());
            assertEquals(bandSequence.get(lastIndex - 2).intValue(), bandSequence.get(lastIndex - 4).intValue());
            assertNotEquals(bandSequence.get(lastIndex - 1).intValue(), bandSequence.get(lastIndex - 2).intValue());

            //Here implemented loop-based approach , with the last element excluded from loop detection
            // x, x+1, x, x+1, x, (x+1)  (error, could have passed to x, if was not stopped) -> x
            // x+1, x, x+1, x, x+1, (x+2)  (error, could have passed to x+1, if was not stopped) -> x+1
            //Alternative-2 loop-based with the last element taken into account during the loop detection
            // x, x+1, x, x+1, x  (error) -> x
            // x+1, x, x+1, x, x+1 (error) -> x+1
            //Alternative-1 oscillation-based
            // x, x+1, x, x+1, x, x+1 (error) -> x+1
            // x+1, x, x+1, x, x+1, x (error) -> x
            int expectedScore = bandSequence.get(lastIndex - 1);
            assertEquals(expectedScore, score);
        }

    }
}
