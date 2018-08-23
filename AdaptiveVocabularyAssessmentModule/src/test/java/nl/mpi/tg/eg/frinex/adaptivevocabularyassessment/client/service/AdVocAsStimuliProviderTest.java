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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BookkeepingStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.vocabulary.AdVocAsStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.advocaspool.AdVocAsStimuliFromString;

import nl.mpi.tg.eg.frinex.common.model.Stimulus;
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
public class AdVocAsStimuliProviderTest {

    private final String numberOfBands = "54";
    private final String nonwordsPerBlock = "4";
    private final String startBand = "20";
    private final String averageNonWordPoistion = "3";
    private final String fineTuningTupleLength = "4";
    private final String fineTuningUpperBoundForCycles = "2";
    private final static String NONWORD_NL = "NEE&#44; ik ken dit woord niet";
    private final static String WORD_NL = "JA&#44; ik ken dit woord";

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
     * Test of estinItialiseStimuliState method, of class
     * AdVocAsStimuliProvider.
     */
    @Test
    public void testItialiseStimuliState1() {
        System.out.println("initialiseStimuliState-1");

        int nOfBands = Integer.parseInt(this.numberOfBands);
        int sBand = Integer.parseInt(this.startBand);
        int aNonWordPosition = Integer.parseInt(this.averageNonWordPoistion);

        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        provider.setwordsSource("Words_NL_1round");
        provider.setnonwordsSource("NonWords_NL_1round");
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand("40");
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.initialiseStimuliState("");

        assertEquals(Integer.parseInt(this.startBand) - 1, provider.getCurrentBandIndex());

        ArrayList<ArrayList<AdVocAsStimulus>> words = provider.getWords();
        assertEquals(nOfBands, words.size());
        for (int i = 0; i < nOfBands; i++) {
            assertEquals(40, words.get(i).size());
        }

        ArrayList<AdVocAsStimulus> nonwords = provider.getNonwords();

        int expectedNonwordsLength = 1352;
        assertEquals(expectedNonwordsLength, nonwords.size());

        int expectedTotalsStimuli = nOfBands * 40 + expectedNonwordsLength;
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
    @Test
    public void testItialiseStimuliState20() {
        System.out.println("initialiseStimuliState-21");

        int nOfBands = Integer.parseInt(this.numberOfBands);

        String wordsPerBand = "20";
        int sBand = Integer.parseInt(this.startBand);
        int aNonWordPosition = Integer.parseInt(this.averageNonWordPoistion);

        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        provider.setwordsSource("Words_NL_2rounds_1");
        provider.setnonwordsSource("NonWords_NL_2rounds_1");
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.initialiseStimuliState("");

        assertEquals(Integer.parseInt(this.startBand) - 1, provider.getCurrentBandIndex());

        ArrayList<ArrayList<AdVocAsStimulus>> words = provider.getWords();
        assertEquals(nOfBands, words.size());
        for (int i = 0; i < nOfBands; i++) {
            assertEquals(Integer.parseInt(wordsPerBand), words.get(i).size());
        }

        ArrayList<AdVocAsStimulus> nonwords = provider.getNonwords();

        int expectedNonwordsLength = 676;
        assertEquals(expectedNonwordsLength, nonwords.size());

        int expectedTotalsStimuli = nOfBands * Integer.parseInt(wordsPerBand) + expectedNonwordsLength;
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
    @Test
    public void testItialiseStimuliState21() {
        System.out.println("initialiseStimuliState-22");

        int nOfBands = Integer.parseInt(this.numberOfBands);

        String wordsPerBand = "20";
        int sBand = Integer.parseInt(this.startBand);
        int aNonWordPosition = Integer.parseInt(this.averageNonWordPoistion);

        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        provider.setwordsSource("Words_NL_2rounds_2");
        provider.setnonwordsSource("NonWords_NL_2rounds_2");
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.initialiseStimuliState("");

        assertEquals(Integer.parseInt(this.startBand) - 1, provider.getCurrentBandIndex());

        ArrayList<ArrayList<AdVocAsStimulus>> words = provider.getWords();
        assertEquals(nOfBands, words.size());
        for (int i = 0; i < nOfBands; i++) {
            assertEquals(Integer.parseInt(wordsPerBand), words.get(i).size());
        }

        ArrayList<AdVocAsStimulus> nonwords = provider.getNonwords();

        int expectedNonwordsLength = 676;
        assertEquals(expectedNonwordsLength, nonwords.size());

        int expectedTotalsStimuli = nOfBands * Integer.parseInt(wordsPerBand) + expectedNonwordsLength;
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
    @Test
    public void testGetCurrentStimulusIndex() {
        System.out.println("getCurrentStimulusIndex");
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        int nOfBands = Integer.parseInt(this.numberOfBands);
        provider.setwordsSource("Words_NL_2rounds_2");
        provider.setnonwordsSource("NonWords_NL_2rounds_2");
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand("20");
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.initialiseStimuliState("");
        provider.hasNextStimulus(0);
        provider.nextStimulus(0);
        int result = provider.getCurrentStimulusIndex();
        assertEquals(0, result);
        assertEquals(Integer.parseInt(this.startBand) - 1, provider.getCurrentBandIndex());
    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    public void testGetCurrentStimulus1_1() {
        this.testGetCurrentStimulus("Words_NL_1round", "NonWords_NL_1round", "getCurrentStimulus10_1", "40");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testGetCurrentStimulus1_2() {
        this.testGetCurrentStimulus("Words_NL_1round", "NonWords_NL_1round", "getCurrentStimulus10_2", "40");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testGetCurrentStimulus1_3() {
        this.testGetCurrentStimulus("Words_NL_1round", "NonWords_NL_1round", "getCurrentStimulus10_3", "40");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testGetCurrentStimulus20_1() {
        this.testGetCurrentStimulus("Words_NL_2rounds_1", "NonWords_NL_2rounds_1", "getCurrentStimulus20_1", "20");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testGetCurrentStimulus20_2() {
        this.testGetCurrentStimulus("Words_NL_2rounds_1", "NonWords_NL_2rounds_1", "getCurrentStimulus20_2", "20");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testGetCurrentStimulus20_3() {
        this.testGetCurrentStimulus("Words_NL_2rounds_1", "NonWords_NL_2rounds_1", "getCurrentStimulus20_3", "20");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testGetCurrentStimulus21_1() {
        this.testGetCurrentStimulus("Words_NL_2rounds_2", "NonWords_NL_2rounds_2", "getCurrentStimulus20_1", "20");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testGetCurrentStimulus21_2() {
        this.testGetCurrentStimulus("Words_NL_2rounds_2", "NonWords_NL_2rounds_2", "getCurrentStimulus20_2", "20");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testGetCurrentStimulus21_3() {
        this.testGetCurrentStimulus("Words_NL_2rounds_2", "NonWords_NL_2rounds_2", "getCurrentStimulus20_3", "20");

    }

    private void testGetCurrentStimulus(String wordsSource, String nonwordsSource, String info, String wordsPerBand) {

        System.out.println(info);

        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);

        provider.setwordsSource(wordsSource);
        provider.setnonwordsSource(nonwordsSource);
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.initialiseStimuliState("");
        assertEquals(Integer.parseInt(this.startBand) - 1, provider.getCurrentBandIndex());
        provider.hasNextStimulus(0);
        provider.nextStimulus(0);
        assertEquals(1, provider.getResponseRecord().size());
        Stimulus stimulus = provider.getCurrentStimulus();
        assertTrue(stimulus != null);
        String label = stimulus.getLabel();
        assertTrue(label != null);
        //System.out.println("Label: " + label);
        BookkeepingStimulus<AdVocAsStimulus> bStimulus = provider.getResponseRecord().get(provider.getCurrentStimulusIndex());
        int expectedBand = stimulus.getCorrectResponses().equals(WORD_NL) ? Integer.parseInt(this.startBand) : 0;
        assertEquals(expectedBand, bStimulus.getStimulus().getBandNumber());
    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testIsCorrectResponse_1() throws Exception {
        this.testIsCorrectResponse("Words_NL_1round", "NonWords_NL_1round", "testIsCorrectResponse_1", "40");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testIsCorrectResponse_20() throws Exception {
        this.testIsCorrectResponse("Words_NL_2rounds_1", "NonWords_NL_2rounds_1", "testIsCorrectResponse_20", "20");

    }

    /**
     * Test of getCurrentStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testIsCorrectResponse_21() throws Exception {
        this.testIsCorrectResponse("Words_NL_2rounds_2", "NonWords_NL_2rounds_2", "testIsCorrectResponse_21", "20");

    }

    private void testIsCorrectResponse(String wordsSource, String nonwordsSource, String info, String wordsPerBand) throws Exception {
        System.out.println(info);
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);

        provider.setwordsSource(wordsSource);
        provider.setnonwordsSource(nonwordsSource);
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.initialiseStimuliState("");
        assertEquals(Integer.parseInt(this.startBand) - 1, provider.getCurrentBandIndex());

        //stimulus 1
        provider.hasNextStimulus(0);
        provider.nextStimulus(0);
        Stimulus stimulus = provider.getCurrentStimulus();
        boolean result = provider.isCorrectResponse(stimulus, stimulus.getCorrectResponses());
        assertTrue(result);

        BookkeepingStimulus<AdVocAsStimulus> bStimulus = provider.getResponseRecord().get(0);
        assertTrue(bStimulus.getCorrectness());

        String expectedReaction = stimulus.getCorrectResponses();
        assertEquals(expectedReaction, bStimulus.getReaction());

        // stimulus 2
        provider.hasNextStimulus(0);
        provider.nextStimulus(0);
        Stimulus stimulus2 = provider.getCurrentStimulus();
        // making worng response
        String response2 = NONWORD_NL;
        if (stimulus2.getCorrectResponses().equals(NONWORD_NL)) {
            response2 = WORD_NL;
        } else {
            if (!stimulus2.getCorrectResponses().equals(WORD_NL)) {
                throw new Exception("The reaction is neither nonword nor word, something went terribly worng.");
            }
        }
        boolean result2 = provider.isCorrectResponse(stimulus2, response2);
        assertFalse(result2);

        BookkeepingStimulus<AdVocAsStimulus> bStimulus2 = provider.getResponseRecord().get(1);
        assertFalse(bStimulus2.getCorrectness());

        String expectedCorrectReaction2 = stimulus2.getCorrectResponses();
        assertNotEquals(expectedCorrectReaction2, bStimulus2.getReaction());

    }

    private void testGetTotalStimuli(String wordsSource, String nonwordsSource, String info, int nNonwords, String wordsPerBand) {
        System.out.println(info);
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        int nOfBands = Integer.parseInt(this.numberOfBands);

        provider.setwordsSource(wordsSource);
        provider.setnonwordsSource(nonwordsSource);
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.initialiseStimuliState("");
        assertEquals(Integer.parseInt(this.startBand) - 1, provider.getCurrentBandIndex());

        int totalStimuli = provider.getTotalStimuli();
        assertEquals(nNonwords + nOfBands * Integer.parseInt(wordsPerBand), totalStimuli);
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testGetTotalStimuli10_1() throws Exception {
        AdVocAsStimuliFromString reader = new AdVocAsStimuliFromString();
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        reader.parseNonWordsInputCSVString(provider, "NonWords_NL_1round", "Words_NL_1round");
        ArrayList<AdVocAsStimulus> rawNonwords = reader.getNonwords();
        int nonWordsLength = rawNonwords.size();
        this.testGetTotalStimuli("Words_NL_1round", "NonWords_NL_1round", "testGetTotalStimuli10_1", nonWordsLength, "40");
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testGetTotalStimuli10_2() throws Exception {
        AdVocAsStimuliFromString reader = new AdVocAsStimuliFromString();
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        reader.parseNonWordsInputCSVString(provider, "NonWords_NL_1round", "Words_NL_1round");
        ArrayList<AdVocAsStimulus> rawNonwords = reader.getNonwords();
        int nonWordsLength = rawNonwords.size();
        this.testGetTotalStimuli("Words_NL_1round", "NonWords_NL_1round", "testGetTotalStimuli10_1", nonWordsLength, "40");
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testGetTotalStimuli10_3() throws Exception {
        AdVocAsStimuliFromString reader = new AdVocAsStimuliFromString();
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        reader.parseNonWordsInputCSVString(provider, "NonWords_NL_1round", "Words_NL_1round");
        ArrayList<AdVocAsStimulus> rawNonwords = reader.getNonwords();
        int nonWordsLength = rawNonwords.size();
        this.testGetTotalStimuli("Words_NL_1round", "NonWords_NL_1round", "testGetTotalStimuli10_1", nonWordsLength, "40");
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testGetTotalStimuli20_1() throws Exception {
        AdVocAsStimuliFromString reader = new AdVocAsStimuliFromString();
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        reader.parseNonWordsInputCSVString(provider, "NonWords_NL_2rounds_1", "Words_NL_1round");
        ArrayList<AdVocAsStimulus> rawNonwords = reader.getNonwords();
        int nonWordsLength = rawNonwords.size();
        this.testGetTotalStimuli("Words_NL_2rounds_1", "NonWords_NL_2rounds_1", "testGetTotalStimuli20_1", nonWordsLength, "20");
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testGetTotalStimuli20_2() throws Exception {
        AdVocAsStimuliFromString reader = new AdVocAsStimuliFromString();
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        reader.parseNonWordsInputCSVString(provider, "NonWords_NL_2rounds_1", "Words_NL_2rounds_1");
        ArrayList<AdVocAsStimulus> rawNonwords = reader.getNonwords();
        int nonWordsLength = rawNonwords.size();
        this.testGetTotalStimuli("Words_NL_2rounds_1", "NonWords_NL_2rounds_1", "testGetTotalStimuli20_1", nonWordsLength, "20");
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testGetTotalStimuli20_3() throws Exception {
        AdVocAsStimuliFromString reader = new AdVocAsStimuliFromString();
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        reader.parseNonWordsInputCSVString(provider, "NonWords_NL_2rounds_1", "Words_NL_2rounds_1");
        ArrayList<AdVocAsStimulus> rawNonwords = reader.getNonwords();
        int nonWordsLength = rawNonwords.size();
        this.testGetTotalStimuli("Words_NL_2rounds_1", "NonWords_NL_2rounds_1", "testGetTotalStimuli20_1", nonWordsLength, "20");
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testGetTotalStimuli21_1() throws Exception {
        AdVocAsStimuliFromString reader = new AdVocAsStimuliFromString();
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        reader.parseNonWordsInputCSVString(provider, "NonWords_NL_2rounds_2", "Words_NL_2rounds_2");
        ArrayList<AdVocAsStimulus> rawNonwords = reader.getNonwords();
        int nonWordsLength = rawNonwords.size();
        this.testGetTotalStimuli("Words_NL_2rounds_2", "NonWords_NL_2rounds_2", "testGetTotalStimuli20_2", nonWordsLength, "20");
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testGetTotalStimuli21_2() throws Exception {
        AdVocAsStimuliFromString reader = new AdVocAsStimuliFromString();
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        reader.parseNonWordsInputCSVString(provider, "NonWords_NL_2rounds_2", "Words_NL_2rounds_2");
        ArrayList<AdVocAsStimulus> rawNonwords = reader.getNonwords();
        int nonWordsLength = rawNonwords.size();
        this.testGetTotalStimuli("Words_NL_2rounds_2", "NonWords_NL_2rounds_2", "testGetTotalStimuli20_2", nonWordsLength, "20");
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testGetTotalStimuli21_3() throws Exception {
        AdVocAsStimuliFromString reader = new AdVocAsStimuliFromString();
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        reader.parseNonWordsInputCSVString(provider, "NonWords_NL_2rounds_2", "Words_NL_2rounds_2");
        ArrayList<AdVocAsStimulus> rawNonwords = reader.getNonwords();
        int nonWordsLength = rawNonwords.size();
        this.testGetTotalStimuli("Words_NL_2rounds_2", "NonWords_NL_2rounds_2", "testGetTotalStimuli20_2", nonWordsLength, "20");
    }

    private void getStimuliReport(String wordsSource, String nonwordsSource, String info, String wordsPerBand) throws Exception {
        System.out.println("getStimuliReport");
        AdVocAsStimuliProvider provider = this.testHasNextStimulus(wordsSource, nonwordsSource, info, wordsPerBand);

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
    @Test
    public void getStimuliReport_1() throws Exception {
        this.getStimuliReport("Words_NL_1round", "NonWords_NL_1round", "getStimuliReport_1", "40");
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void getStimuliReport_20() throws Exception {
        this.getStimuliReport("Words_NL_2rounds_1", "NonWords_NL_2rounds_1", "getStimuliReport_20", "20");
    }

    /**
     * Test of getTotalStimuli method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void getStimuliReport_21() throws Exception {
        this.getStimuliReport("Words_NL_2rounds_2", "NonWords_NL_2rounds_2", "getStimuliReport_21", "20");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testHasNextStimulus10_1() throws Exception {
        this.testHasNextStimulus("Words_NL_1round", "NonWords_NL_1round", "testHasNextStimulus10_1", "40");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testHasNextStimulus10_2() throws Exception {
        this.testHasNextStimulus("Words_NL_1round", "NonWords_NL_1round", "testHasNextStimulus10_2", "40");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testHasNextStimulus10_3() throws Exception {
        this.testHasNextStimulus("Words_NL_1round", "NonWords_NL_1round", "testHasNextStimulus10_3", "40");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testHasNextStimulus20_1() throws Exception {
        this.testHasNextStimulus("Words_NL_2rounds_1", "NonWords_NL_2rounds_1", "testHasNextStimulus20_1", "20");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testHasNextStimulus20_2() throws Exception {
        this.testHasNextStimulus("Words_NL_2rounds_1", "NonWords_NL_2rounds_1", "testHasNextStimulus20_2", "20");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testHasNextStimulus20_3() throws Exception {
        this.testHasNextStimulus("Words_NL_2rounds_1", "NonWords_NL_2rounds_1", "testHasNextStimulus20_3", "20");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testHasNextStimulus21_1() throws Exception {
        this.testHasNextStimulus("Words_NL_2rounds_2", "NonWords_NL_2rounds_2", "testHasNextStimulus21_1", "20");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testHasNextStimulus21_2() throws Exception {
        this.testHasNextStimulus("Words_NL_2rounds_2", "NonWords_NL_2rounds_2", "testHasNextStimulus21_2", "20");
    }

    /**
     * Test of hasNextStimulus method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testHasNextStimulus21_3() throws Exception {
        this.testHasNextStimulus("Words_NL_2rounds_2", "NonWords_NL_2rounds_2", "testHasNextStimulus21_3", "20");
    }

    // also tests nextStimulus
    private AdVocAsStimuliProvider testHasNextStimulus(String wordsSource, String nonwordsSource, String info, String wordsPerBand) throws Exception {
        System.out.println(info);
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);

        provider.setwordsSource(wordsSource);
        provider.setnonwordsSource(nonwordsSource);
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.initialiseStimuliState("");
        assertEquals(Integer.parseInt(this.startBand) - 1, provider.getCurrentBandIndex());
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

        //provider.isCorrectResponse(stimulus, stimulus.getCorrectResponses());
        stimulus.isCorrect(stimulus.getCorrectResponses());

        boolean result1 = provider.hasNextStimulus(0);
        assertTrue(result1);
        int sBand = Integer.parseInt(this.startBand);
        int expectedBand = stimulus.getCorrectResponses().equals(WORD_NL) ? (sBand + 1) : sBand;
        assertEquals(expectedBand, provider.getCurrentBandIndex() + 1);

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
        if (correctResponse.equals(WORD_NL)) {
            response = NONWORD_NL;
        }
        if (correctResponse.equals(NONWORD_NL)) {
            response = WORD_NL;
        }
        if (response == null) {
            throw new Exception("Wrong reaction");
        }

        stimulus2.isCorrect(response);
        boolean result12 = provider.hasNextStimulus(0);
        assertTrue(result12);
        assertEquals(expectedBand, provider.getCurrentBandIndex() + 1);
        assertEquals(0, provider.getBestFastTrackIndexBand()); // stil on fast track, expecting the second chance

        provider.nextStimulus(0); // give the second chance
        assertEquals(3, provider.getResponseRecord().size());
        int invariant3 = provider.getResponseRecord().size() + provider.getNonwords().size() + this.getListOfListLength(provider.getWords());
        assertEquals(invariant, invariant3);

        int ind3 = provider.getCurrentStimulusIndex();
        assertEquals(2, ind3);
        AdVocAsStimulus stimulus3 = provider.getCurrentStimulus();
        String correctResponse3 = stimulus3.getCorrectResponses();
        String response3 = null;
        if (correctResponse3.equals(WORD_NL)) {
            response3 = NONWORD_NL;
        }
        if (correctResponse3.equals(NONWORD_NL)) {
            response3 = WORD_NL;
        }
        if (response3 == null) {
            throw new Exception("Wrong reaction");
        }

        //provider.isCorrectResponse(stimulus3, response3);
        stimulus3.isCorrect(response3);

        boolean result3 = provider.hasNextStimulus(0);
        assertTrue(result3);
        // now current band represents the last correct band on the fast track
        // expected band drops one because of two errors in a row
        expectedBand--;
        assertEquals(expectedBand, provider.getCurrentBandIndex() + 1);
        assertEquals(provider.getCurrentBandIndex(), provider.getBestFastTrackIndexBand()); // stil on fast track, expecting the sec0nd chance

        return provider;

    }

    /**
     * Test of getCurrentStimulusUniqueId method, of class
     * AdVocAsStimuliProvider.
     */
    @Test
    public void testGetCurrentStimulusUniqueId_1() {
        this.testGetCurrentStimulusUniqueId("Words_NL_1round", "NonWords_NL_1round", "testGetCurrentStimulusUniqueId_1", "40");
    }

    /**
     * Test of getCurrentStimulusUniqueId method, of class
     * AdVocAsStimuliProvider.
     */
    @Test
    public void testGetCurrentStimulusUniqueId_20() {
        this.testGetCurrentStimulusUniqueId("Words_NL_2rounds_1", "NonWords_NL_2rounds_1", "testGetCurrentStimulusUniqueId_21", "20");
    }

    /**
     * Test of getCurrentStimulusUniqueId method, of class
     * AdVocAsStimuliProvider.
     */
    @Test
    public void testGetCurrentStimulusUniqueId_21() {
        this.testGetCurrentStimulusUniqueId("Words_NL_2rounds_2", "NonWords_NL_2rounds_2", "testGetCurrentStimulusUniqueId_22", "20");
    }

    private void testGetCurrentStimulusUniqueId(String wordsSource, String nonwordsSource, String info, String wordsPerBand) {
        System.out.println(info);
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);

        provider.setwordsSource(wordsSource);
        provider.setnonwordsSource(nonwordsSource);
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.initialiseStimuliState("");
        assertEquals(Integer.parseInt(this.startBand) - 1, provider.getCurrentBandIndex());
        provider.hasNextStimulus(0);
        provider.nextStimulus(0);
        String result = provider.getCurrentStimulusUniqueId();
        assertTrue(result != null);
    }

    /**
     * Test of detectLoop method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testDetectLoop() {
        System.out.println("detectLoop");
        Integer[] arr1 = {42, 43, 42, 43, 42, 43, 42};
        boolean result1 = AdVocAsStimuliProvider.detectLoop(arr1);
        assertEquals(true, result1);
        Integer[] arr2 = {42, 43, 42, 43, 42, 43, 45};
        boolean result2 = AdVocAsStimuliProvider.detectLoop(arr2);
        assertEquals(false, result2);
        Integer[] arr3 = {43, 42, 43, 42, 43, 42, 45, 42};
        boolean result3 = AdVocAsStimuliProvider.detectLoop(arr3);
        assertEquals(false, result3);
    }

    /**
     * Test of shiftFIFO method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testShiftFIFO() {
        System.out.println("shiftFIFO");
        Integer[] fifo = {0, 1, 2, 3, 4, 5, 6};
        int newelement = 7;
        AdVocAsStimuliProvider.shiftFIFO(fifo, newelement);
        for (int i = 0; i < 7; i++) {
            assertEquals(new Integer(i + 1), fifo[i]);
        }
    }

    private int getListOfListLength(ArrayList<ArrayList<AdVocAsStimulus>> ll) {
        int retVal = 0;
        for (ArrayList<AdVocAsStimulus> l : ll) {
            retVal += l.size();
        }
        return retVal;
    }

    private void testPercentageBandTable(String wordsSource, String nonwordsSource, String info, String wordsPerBand) {
        System.out.println(info);

        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        int nOfBands = Integer.parseInt(this.numberOfBands);

        provider.setwordsSource(wordsSource);
        provider.setnonwordsSource(nonwordsSource);
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.initialiseStimuliState("");
        assertEquals(Integer.parseInt(this.startBand) - 1, provider.getCurrentBandIndex());

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
            float bnd = ((float) (54 * percentage)) / ((float) 100);
            int roundBnd = Math.round(bnd);
            assertEquals(new Integer(roundBnd), percentageTable.get(percentage));
        }

    }

    @Test
    public void testPercentageBandTable_1() {
        this.testPercentageBandTable("Words_NL_1round", "NonWords_NL_1round", "testPercentageBandTable_1", "40");

    }

    @Test
    public void testPercentageBandTable_20() {
        this.testPercentageBandTable("Words_NL_2rounds_1", "NonWords_NL_2rounds_1", "testPercentageBandTable_20", "20");

    }

    @Test
    public void testPercentageBandTable_21() {
        this.testPercentageBandTable("Words_NL_2rounds_2", "NonWords_NL_2rounds_2", "testPercentageBandTable_21", "20");

    }

    @Test
    public void generalRandomTest1() throws Exception {
        for (int i = 1; i < 11; i++) {
            double prob = 0.5 + i * 0.05;
            System.out.println("Probabilistic test for 1 round, prob of corret answer is " + prob);
            this.testRound(prob, "Words_NL_1round", "NonWords_NL_1round", "40");
        }
    }

    @Test
    public void generalRandomTest2() throws Exception {
        for (int i = 1; i < 11; i++) {
            double prob = 0.5 + i * 0.05;
            System.out.println("Probabilistic test for 2 rounds, prob of corret answer is " + prob);
            System.out.println("Round 1");
            AdVocAsStimuliProvider provider1 = this.testRound(prob, "Words_NL_2rounds_1", "NonWords_NL_2rounds_1", "20");
            int score1 = provider1.getBandIndexScore();
            System.out.println("Band Score: " + score1);
            System.out.println("Round 2");
            AdVocAsStimuliProvider provider2 = this.testRound(prob, "Words_NL_2rounds_2", "NonWords_NL_2rounds_2", "20");
            int score2 = provider2.getBandIndexScore();
            System.out.println("Band Score: " + score2);
            if (score1 != score2) {
                System.out.println("Attention. Difference between score in consecutive rounds is detected, score 1 and 2 are " + score1 + " and " + score2 + " respectively.");
            }
            System.out.println("***");
        }
    }

    @Test
    public void notEnoughStimuliTest_1() throws Exception {
        this.longFineTuningTest("40", "Words_NL_1round", "NonWords_NL_1round");
    }

    @Test
    public void notEnoughStimuliTest_21() throws Exception {
        this.longFineTuningTest("20", "Words_NL_2rounds_1", "NonWords_NL_2rounds_1");
    }

    @Test
    public void notEnoughStimuliTest_22() throws Exception {
        this.longFineTuningTest("20", "Words_NL_2rounds_2", "NonWords_NL_2rounds_2");
    }

    public AdVocAsStimuliProvider testRound(double prob, String wordsSource, String nonwordsSource, String wordsPerBand) throws Exception {
        Random rnd = new Random();

        int nOfBands = Integer.parseInt(this.numberOfBands);
        int wPerBand = Integer.parseInt(wordsPerBand);
        int sBand = Integer.parseInt(this.startBand);
        int aNonWordPosition = Integer.parseInt(this.averageNonWordPoistion);

        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        provider.setwordsSource(wordsSource);
        provider.setnonwordsSource(nonwordsSource);
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.initialiseStimuliState("");
        assertEquals(Integer.parseInt(this.startBand) - 1, provider.getCurrentBandIndex());

        boolean hasNextStimulus = provider.hasNextStimulus(0);
        int currentExperimentCount = 0;
        ArrayList<Integer> bandNumberRecord = new ArrayList<Integer>();
        while (hasNextStimulus) {
            ArrayList<BookkeepingStimulus<AdVocAsStimulus>> ft = provider.getFTtuple();
            if (ft.size() == 4) {
                int bandNumber = this.checkFreshFineTuningTuple(ft);
                bandNumberRecord.add(bandNumber);
            }
            provider.nextStimulus(0);
            currentExperimentCount = provider.getCurrentStimulusIndex();
            //System.out.println(currentExperimentCount);
            AdVocAsStimulus stimulus = provider.getCurrentStimulus();
            String answer = this.probabilisticAnswerer(stimulus, prob, rnd);
            //boolean isCorrect = provider.isCorrectResponse(stimulus, answer);
            boolean isCorrect = stimulus.isCorrect(answer);
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
                assertEquals(0, provider.getBandIndexScore());
                assertEquals(2, provider.getPercentageScore()); // 100/54 = 1.85... rounded to two
            } else {
                assertTrue(provider.getPercentageScore() > 1);
            }

        }

        if (champion) {
            assertEquals(nOfBands - 1, provider.getBandIndexScore());
            assertEquals(100, provider.getPercentageScore());
        }

        this.checkFastTrack(provider.getResponseRecord(), provider.getEndFastTrackTimeTick(), provider.getBestFastTrackIndexBand());
        this.checkFineTuning(provider.getResponseRecord(), bandNumberRecord, provider.getEndFastTrackTimeTick(), provider.getBestFastTrackIndexBand(), cycle2, provider.getBandIndexScore());

        // checking generating graph
        // first check if the sample set is generated ok
        LinkedHashMap<Integer, String> samples = provider.retrieveSampleWords(provider.getResponseRecord(), provider.getWords());

        AdVocAsStimuliFromString reader = new AdVocAsStimuliFromString();
        reader.parseWordsInputCSVString(provider, wordsSource, nonwordsSource, Integer.parseInt(this.numberOfBands));
        ArrayList<ArrayList<AdVocAsStimulus>> rawWords = reader.getWords();

        assertEquals(nOfBands, samples.keySet().size());
        for (int bandNumber = 1; bandNumber <= nOfBands; bandNumber++) {
            assertTrue(samples.containsKey(bandNumber));
            String sample = samples.get(bandNumber);
            assertNotNull(sample);
            ArrayList<String> words = this.getSpellings(rawWords.get(bandNumber - 1));
            assertTrue(words.contains(sample));
        }

        // now check if the graph sequence for percentage is ok 
        LinkedHashMap<Long, String> graph = provider.generateDiagramSequence(provider.getResponseRecord(), provider.getPercentageBandTable());

        Integer bandIndexScore = provider.getBandIndexScore();

        Long percentScore = provider.getPercentageScore();
        assertTrue(graph.containsKey(percentScore));
        if (graph.get(percentScore) == null) {
            fail("In the percentage-example table there is no value for percent " + percentScore);
        }
        ArrayList<String> wordsBS = this.getSpellings(rawWords.get(bandIndexScore));
        assertTrue(wordsBS.contains(graph.get(percentScore)));

        if (percentScore >= 5) {
            Long one = new Long(1);
            assertTrue(graph.containsKey(one));
            assertNotNull(graph.get(one));
            // which band number correspond to 1 percent of # 54 is 100%?
            // 54/100 = 0.54 ~ 1
            ArrayList<String> wordsOnePercent = this.getSpellings(rawWords.get(0));
            assertTrue(wordsOnePercent.contains(graph.get(one)));
        }

        if (percentScore < 95) {
            Long nn = new Long(99);
            if (!graph.containsKey(nn)) {
                System.out.println("alarm: no key for percentageScore " + nn.toString());
            }
            assertTrue(graph.containsKey(nn));
            assertNotNull(graph.get(nn));
            // which band number correspond to 99 percent of # 54 is 100%?
            // 54*0.99 = 53.46 ~ 53
            ArrayList<String> words99Percent = this.getSpellings(rawWords.get(52));
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

        String stateSnapshotExpected = provider.toString();
        AdVocAsStimuliProvider provider2 = new AdVocAsStimuliProvider(null);
        provider2.initialiseStimuliState(stateSnapshotExpected);
        String stateSnapshot = provider2.toString();
        //System.out.println(stateSnapshotExpected);
        assertEquals(stateSnapshotExpected, stateSnapshot);

        return provider;
    }

    private ArrayList<String> getSpellings(ArrayList<AdVocAsStimulus> stimuli) {
        ArrayList<String> retVal = new ArrayList<>(stimuli.size());
        for (AdVocAsStimulus stimulus : stimuli) {
            retVal.add(stimulus.getLabel());
        }
        return retVal;
    }

    public AdVocAsStimuliProvider longFineTuningTest(String wordsPerBand, String wordsSource, String nonWordsSource) throws Exception {

        int nOfBands = Integer.parseInt(this.numberOfBands);
        int wPerBand = Integer.parseInt(wordsPerBand);
        int sBand = Integer.parseInt(this.startBand);
        int aNonWordPosition = Integer.parseInt(this.averageNonWordPoistion);

        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        provider.setwordsSource(wordsSource);
        provider.setnonwordsSource(nonWordsSource);
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningUpperBoundForCycles(this.fineTuningUpperBoundForCycles);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setwordsPerBand(wordsPerBand);
        provider.setnonwordsPerBlock(this.nonwordsPerBlock);
        provider.setstartBand(this.startBand);
        provider.setaverageNonWordPosition(this.averageNonWordPoistion);
        provider.initialiseStimuliState("");
        assertEquals(Integer.parseInt(this.startBand) - 1, provider.getCurrentBandIndex());

        // make to wrong answers to start fine tuning immediately
        provider.hasNextStimulus(0);
        provider.nextStimulus(0);
        AdVocAsStimulus stimulus = provider.getCurrentStimulus();
        String answer = this.makeResponseWrong(stimulus);
        //boolean isCorrect = provider.isCorrectResponse(stimulus, answer);
        boolean isCorrect = stimulus.isCorrect(answer);

        provider.hasNextStimulus(0);
        provider.nextStimulus(0);
        stimulus = provider.getCurrentStimulus();
        answer = this.makeResponseWrong(stimulus);
        //isCorrect = provider.isCorrectResponse(stimulus, answer);
        isCorrect = stimulus.isCorrect(answer);

        boolean hasNextStimulus = provider.hasNextStimulus(0);
        // fine tuning correct till the band is 54 then back till the band is 20
        boolean runHigher = true;
        int tupleCounter = 0;
        ArrayList<Integer> bandNumberSequence = new ArrayList<Integer>();
        while (hasNextStimulus) {

            ArrayList<BookkeepingStimulus<AdVocAsStimulus>> ft = provider.getFTtuple();
            if (ft.size() == 4) {
                Integer bandNumber = this.checkFreshFineTuningTuple(ft);
                bandNumberSequence.add(bandNumber);
                tupleCounter++;
            }

            if (runHigher && provider.getCurrentBandIndex() == 53) {
                runHigher = false; // start climbing backwards
            }
            if (!runHigher && provider.getCurrentBandIndex() == 19) {
                runHigher = true; // start climbing forward
            }
            provider.nextStimulus(0);
            stimulus = provider.getCurrentStimulus();
            if (runHigher) {
                answer = stimulus.getCorrectResponses();
            } else {
                answer = this.makeResponseWrong(stimulus);
            }
            //isCorrect = provider.isCorrectResponse(stimulus, answer);
            isCorrect = stimulus.isCorrect(answer);
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
        assertEquals(19, provider.getBestFastTrackIndexBand() + 1);

        this.checkFastTrack(provider.getResponseRecord(), provider.getEndFastTrackTimeTick(), provider.getBestFastTrackIndexBand());
        this.checkFineTuning(provider.getResponseRecord(), bandNumberSequence, provider.getEndFastTrackTimeTick(), provider.getBestFastTrackIndexBand(), cycle2, provider.getBandIndexScore());

        String stateSnapshotExpected = provider.toString();
        AdVocAsStimuliProvider provider2 = new AdVocAsStimuliProvider(null);
        provider2.initialiseStimuliState(stateSnapshotExpected);
        String stateSnapshot = provider2.toString();
        //System.out.println(stateSnapshotExpected);
        assertEquals(stateSnapshotExpected, stateSnapshot);

        return provider;

    }

    private String probabilisticAnswerer(AdVocAsStimulus stimulus, double correctnessUpperBound, Random rnd) throws Exception {
        String retVal = stimulus.getCorrectResponses();
        double rndDouble = rnd.nextDouble();
        //System.out.println("*****");
        //System.out.println(retVal);
        //System.out.println(rndDouble);
        if (rndDouble > correctnessUpperBound) { // spoil the answer
            if (retVal.equals(WORD_NL)) {
                retVal = NONWORD_NL;
            } else {
                if (retVal.equals(NONWORD_NL)) {
                    retVal = WORD_NL;
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
        String answer = NONWORD_NL;
        if (stimulus.getCorrectResponses().equals(NONWORD_NL)) {
            answer = WORD_NL;
        };
        return answer;
    }

    private int checkFreshFineTuningTuple(ArrayList<BookkeepingStimulus<AdVocAsStimulus>> tuple) {
        int nNonwords = 0;
        int bandNumber = 0;
        for (BookkeepingStimulus<AdVocAsStimulus> stimulus : tuple) {
            assertEquals(null, stimulus.getReaction());
            assertEquals(null, stimulus.getCorrectness());
            if (stimulus.getStimulus().getCorrectResponses().equals(WORD_NL)) {
                if (bandNumber > 0) {
                    assertEquals(bandNumber, stimulus.getStimulus().getBandNumber()); // they allmust be from the same band
                } else {
                    bandNumber = stimulus.getStimulus().getBandNumber();
                }
            }
            if (stimulus.getStimulus().getCorrectResponses().equals(NONWORD_NL)) {
                nNonwords++;
            }
        }
        assertEquals(1, nNonwords);
        assertTrue(bandNumber > 0);
        return bandNumber;
    }

    private void checkNonWordFrequenceFastTrack(ArrayList<BookkeepingStimulus<AdVocAsStimulus>> records, int timeTick) {
        int counterNonwords = 0;
        double frequency = 0;

        assertNotEquals(0, records.size());

        for (int i = 0; i <= timeTick; i++) {
            BookkeepingStimulus<AdVocAsStimulus> stimulus = records.get(i);
            if (stimulus.getStimulus().getCorrectResponses().equals(NONWORD_NL)) {
                counterNonwords++;
            }
            frequency = ((double) counterNonwords) / ((double) (i + 1));
        }
//        if (timeTick >= 3) {
//            StringBuilder builder= new StringBuilder();
//            for (BookkeepingStimulus<AdVocAsStimulus> record:records) {
//                builder.append(record.getStimulus().getLabel()).append("  ");
//                if (record.getStimulus().getBandNumber()>0) {
//                   builder.append("woord");
//                } else {
//                   builder.append("nietwoord"); 
//                }
//                builder.append(",\n");
//            }
//            assertTrue(builder.toString(), frequency>0);
//        }
        double idealFrequency = 1.0 / Double.valueOf(this.averageNonWordPoistion);
        double diff = Math.abs(frequency - idealFrequency);
        //System.out.println(frequency);
        //System.out.println(idealFrequency);
        int blockSize = Integer.parseInt(this.averageNonWordPoistion) * Integer.parseInt(this.nonwordsPerBlock);
        // timeTick + 1: ticks are counted from 0,1,...,timeTick, altgether timeTick+1 clocks
        if ((timeTick + 1) % blockSize == 0) {
            System.out.println(frequency);
            assertTrue(diff <= 0.01);
        }
    }

    private void checkAllWordsAreDifferent(ArrayList<BookkeepingStimulus<AdVocAsStimulus>> records) {
        int sz = records.size();
        HashSet<BookkeepingStimulus<AdVocAsStimulus>> testEqualitySet = new HashSet(records);
        assertEquals(testEqualitySet.size(), sz);
        assertEquals(sz, records.size());
    }

    private void checkFastTrack(ArrayList<BookkeepingStimulus<AdVocAsStimulus>> records, int lastTimeTickFastTrack, int bestFastTrackIndexBand) {
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

            assertFalse(previousbStimulus.getCorrectness());
            if (lastTimeTickFastTrack >= 2) {
                assertTrue(records.get(lastTimeTickFastTrack - 2).getCorrectness());
            }
        }
        if (stimulus.getBandNumber() > 0) {
            // two erros in a row on fast track: go back 1 band
            if (bestFastTrackIndexBand + 1 == 54) {
                // we have finished fast track because there was no more bands
                assertEquals(bestFastTrackIndexBand + 1, stimulus.getBandNumber());
            } else {
                // we have finished fast track because we have made 2 error in a row
                assertEquals(bestFastTrackIndexBand + 1, stimulus.getBandNumber() - 1);
            }
        }

    }

    private void checkFineTuning(ArrayList<BookkeepingStimulus<AdVocAsStimulus>> records, ArrayList<Integer> bandNumberSequence, int lastTimeTickFastTrack, int bestFastTrackBandIndex, boolean cycle2, int indexScore) {

        // fine tuning starting check
        assertEquals(bestFastTrackBandIndex + 1, bandNumberSequence.get(0).intValue());

        // cycle check 
        if (cycle2) {
            int lastIndex = bandNumberSequence.size() - 1;
            // ignore the last erronenous reaction
            assertEquals(bandNumberSequence.get(lastIndex).intValue(), bandNumberSequence.get(lastIndex - 2).intValue());
            assertEquals(bandNumberSequence.get(lastIndex - 1).intValue(), bandNumberSequence.get(lastIndex - 3).intValue());
            assertEquals(bandNumberSequence.get(lastIndex - 2).intValue(), bandNumberSequence.get(lastIndex - 4).intValue());

            //Here implemented loop-based approach , with the last element excluded from loop detection
            // x, x+1, x, x+1, x, (x+1)  (error, could have passed to x, if was not stopped) -> x
            // x+1, x, x+1, x, x+1, (x+2)  (error, could have passed to x+1, if was not stopped) -> x+1
            //Alternative-2 loop-based with the last element taken into account during the loop detection
            // x, x+1, x, x+1, x  (error) -> x
            // x+1, x, x+1, x, x+1 (error) -> x+1
            //Alternative-1 oscillation-based
            // x, x+1, x, x+1, x, x+1 (error) -> x+1
            // x+1, x, x+1, x, x+1, x (error) -> x
            int expectedBandNumber = Math.min(bandNumberSequence.get(lastIndex), bandNumberSequence.get(lastIndex - 1));
            assertEquals(expectedBandNumber, indexScore + 1);
        }

        int counterInTuple = 0;
        AdVocAsStimulus stimulus;

        Integer currentBandNumber;
        int tupleCounter = 0;
        for (int i = lastTimeTickFastTrack + 1; i < records.size(); i++) {

            assertTrue(tupleCounter < bandNumberSequence.size());
            currentBandNumber = bandNumberSequence.get(tupleCounter);

            BookkeepingStimulus<AdVocAsStimulus> bStimulus = records.get(i);
            stimulus = bStimulus.getStimulus();

            if (stimulus.getBandNumber() > 0) {
                assertTrue(currentBandNumber.equals(stimulus.getBandNumber()));
            }

            // all previous in the tuple must be answered correctly and be of the same band
            for (int j = 0; j < counterInTuple - 1; j++) {
                assertTrue(records.get(i - 1 - j).getCorrectness());
                Integer bNumber = records.get(i - j).getStimulus().getBandNumber();
                if (bNumber > 0) {
                    // all words in the tuple must be in one band
                    assertEquals(currentBandNumber, bNumber);

                }
            }

            if (bStimulus.getCorrectness()) {
                counterInTuple++;
                if (counterInTuple == Integer.parseInt(this.fineTuningTupleLength)) {
                    if (i <= records.size() - 2) { // there will be next
                        tupleCounter++; // to next tuple because it was all correct
                        if (currentBandNumber < Integer.valueOf(this.numberOfBands)) {
                            assertEquals(currentBandNumber + 1, bandNumberSequence.get(tupleCounter).intValue()); // the band will be increased because all four correct
                        } else {
                            assertEquals(currentBandNumber.intValue(), bandNumberSequence.get(tupleCounter).intValue()); // second trial
                        }
                    }

                    counterInTuple = 0;

                    // all  4 ccorrect answers in a row
                    // they all must be in 1 band (except the nonword)
                    int nonWordCounter = 0;

                    // check the tuple
                    for (int j = 0; j < Integer.parseInt(this.fineTuningTupleLength); j++) {
                        Integer bNumber = records.get(i - j).getStimulus().getBandNumber();
                        if (bNumber == 0) {
                            nonWordCounter++;
                        } else {
                            // all words in the tuple must be in one band
                            assertEquals(currentBandNumber, bNumber);
                        }
                    }
                    // there must be exactly one nonword per tuple
                    assertEquals(1, nonWordCounter);
                }
            } else {
                if (i <= records.size() - 2) { // there will be next
                    // initialise next step
                    tupleCounter++; // to next tuple because one was wrong
                    if (currentBandNumber > 1) {
                        assertEquals(currentBandNumber - 1, bandNumberSequence.get(tupleCounter).intValue()); // the band will be decreased because of a mistake
                    } else {
                        assertEquals(currentBandNumber.intValue(), bandNumberSequence.get(tupleCounter).intValue()); //second chance
                    }
                    counterInTuple = 0;
                }
            }
        }

    }

    /**
     * Test of getToString method, of class AdVocAsStimuliProvider.
     */
    @Test
    public void testToStringPlusInitialise() {
        System.out.println("toString");
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);

        provider.setwordsSource("Words_NL_1round");
        provider.setnonwordsSource("NonWords_NL_1round");

        provider.setnumberOfBands("54");
        provider.setfastTrackPresent("true");
        provider.setfineTuningFirstWrongOut("false");
        provider.setfineTuningTupleLength("4");
        provider.setfineTuningUpperBoundForCycles("2");

        provider.setstartBand("20");
        provider.setnonwordsPerBlock("4");
        provider.setwordsPerBand("40");
        provider.setaverageNonWordPosition("3");
        provider.initialiseStimuliState("");
        assertEquals(Integer.parseInt(this.startBand) - 1, provider.getCurrentBandIndex());
        String toStringOut = provider.toString(); // the line is too long (due to word lists) to make a classical unit test on it, so I combine serialisiation and deserialisation

        // testing only specific for AdVocAsProvider implementation part, the parent calss has been tested separately
        AdVocAsStimuliProvider freshProvider = new AdVocAsStimuliProvider(null);
        freshProvider.initialiseStimuliState(toStringOut);

        assertEquals(toStringOut, freshProvider.toString());

        ArrayList<ArrayList<AdVocAsStimulus>> resultWords = freshProvider.getWords();
        ArrayList<ArrayList<AdVocAsStimulus>> expectedWords = provider.getWords();
        assertEquals(resultWords.size(), expectedWords.size());
        for (int i = 0; i < resultWords.size(); i++) {
            assertEquals(expectedWords.get(i).size(), resultWords.get(i).size());
            for (int j = 0; j < resultWords.get(i).size(); j++) {
                assertEquals(expectedWords.get(i).get(j), resultWords.get(i).get(j)); // even pointer must be the same -- to the same static object of AdVocAsStimulus
            }
        }

        assertEquals(provider.getCurrentBandIndex(), freshProvider.getCurrentBandIndex());
        assertEquals(provider.getBandIndexScore(), freshProvider.getBandIndexScore());
        assertEquals(provider.getHtmlStimuliReport(), freshProvider.getHtmlStimuliReport());

        ArrayList<Integer> resultNonWordIndices = freshProvider.getNonWordsIndices();
        ArrayList<Integer> expectedNonWordIndices = provider.getNonWordsIndices();
        assertEquals(expectedNonWordIndices.size(), resultNonWordIndices.size());
        for (int i = 0; i < resultNonWordIndices.size(); i++) {
            assertEquals(expectedNonWordIndices.get(i), resultNonWordIndices.get(i));
        }

        ArrayList<AdVocAsStimulus> resultNonwords = freshProvider.getNonwords();
        ArrayList<AdVocAsStimulus> expectedNonWrods = provider.getNonwords();
        assertEquals(expectedNonWordIndices.size(), resultNonWordIndices.size());
        for (int i = 0; i < resultNonwords.size(); i++) {
            assertEquals(expectedNonWrods.get(i), resultNonwords.get(i));
        }

        assertEquals(provider.getStringFastTrack("", "\n", "", ";"), freshProvider.getStringFastTrack("", "\n", "", ";"));

        assertEquals(provider.getStringFineTuningHistory("", "\n", "", ";", "csv"), freshProvider.getStringFineTuningHistory("", "\n", "", ";", "csv"));

    }

    /**
     * Test of getPercentageBandTable method, of class BandStimuliProvider.
     */
    @Test
    public void testGetPercentageBandTable() {
        System.out.println("getPercentageBandTable");

        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);

        provider.setwordsSource("Words_NL_1round");
        provider.setnonwordsSource("NonWords_NL_1round");

        provider.setnumberOfBands("54");
        provider.setfastTrackPresent("true");
        provider.setfineTuningFirstWrongOut("false");
        provider.setfineTuningTupleLength("4");
        provider.setfineTuningUpperBoundForCycles("2");

        provider.setstartBand("20");
        provider.setnonwordsPerBlock("4");
        provider.setwordsPerBand("40");
        provider.setaverageNonWordPosition("3");
        provider.initialiseStimuliState("");
        assertEquals(Integer.parseInt(this.startBand) - 1, provider.getCurrentBandIndex());

        LinkedHashMap<Long, Integer> table = provider.getPercentageBandTable();
        assertEquals(11, table.size());

        Long[] keys = table.keySet().toArray(new Long[0]);
        assertTrue(1 == keys[0]);
        assertTrue(10 == keys[1]);
        assertTrue(20 == keys[2]);
        assertTrue(30 == keys[3]);
        assertTrue(40 == keys[4]);
        assertTrue(50 == keys[5]);
        assertTrue(60 == keys[6]);
        assertTrue(70 == keys[7]);
        assertTrue(80 == keys[8]);
        assertTrue(90 == keys[9]);
        assertTrue(99 == keys[10]);

        assertEquals(1, table.get(keys[0]).intValue());
        assertEquals(5, table.get(keys[1]).intValue());
        assertEquals(11, table.get(keys[2]).intValue());
        assertEquals(16, table.get(keys[3]).intValue());
        assertEquals(22, table.get(keys[4]).intValue());
        assertEquals(27, table.get(keys[5]).intValue());
        assertEquals(32, table.get(keys[6]).intValue());
        assertEquals(38, table.get(keys[7]).intValue());
        assertEquals(43, table.get(keys[8]).intValue());
        assertEquals(49, table.get(keys[9]).intValue());
        assertEquals(53, table.get(keys[10]).intValue());
    }

    /**
     * Test of bandNumberIntoPercentage method, of class BandStimuliProvider.
     */
    @Test
    public void testBandNumberIntoPercentage() {
        System.out.println("bandNumberIntoPercentage");
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        assertEquals(0L, provider.bandNumberIntoPercentage(0));

        provider.setnumberOfBands("54");
        assertEquals(100L, provider.bandNumberIntoPercentage(54));
        assertEquals(50L, provider.bandNumberIntoPercentage(27));
        assertEquals(33L, provider.bandNumberIntoPercentage(18));
    }

    /**
     * Test of percentageIntoBandNumber method, of class BandStimuliProvider.
     */
    @Test
    public void testPercentageIntoBandNumber() {
        System.out.println("percentageIntoBandNumber");

        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        assertEquals(0, provider.percentageIntoBandNumber(0));
        provider.setnumberOfBands("54");
        assertEquals(54, provider.percentageIntoBandNumber(100));
        assertEquals(53, provider.percentageIntoBandNumber(99));
        assertEquals(27, provider.percentageIntoBandNumber(50));
        assertEquals(26, provider.percentageIntoBandNumber(49));
        assertEquals(5, provider.percentageIntoBandNumber(10));
        assertEquals(1, provider.percentageIntoBandNumber(1));
        assertEquals(1, provider.percentageIntoBandNumber(2));

    }

}
