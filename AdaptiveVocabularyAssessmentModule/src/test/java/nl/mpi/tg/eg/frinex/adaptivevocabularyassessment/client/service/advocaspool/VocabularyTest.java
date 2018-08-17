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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.advocaspool;

import java.util.ArrayList;
import java.util.HashSet;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.vocabulary.AdVocAsStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.AdVocAsStimuliProvider;
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
public class VocabularyTest {

    private final int numberOfBands_NL = 54;
    private final int wordsPerBand_NL = 40;
    private final String wordsResponse_NL = "JA&#44; ik ken dit woord";
    private final String nonwordsResponse_NL = "NEE&#44; ik ken dit woord niet";

    private final int numberOfBands_EN = 62;
    private final int wordsPerBand_EN = 20;
    private final String wordsResponse_EN = "YES&#44; I know this word";
    private final String nonwordsResponse_EN = "NO&#44; I don’t know this word";

    public VocabularyTest() {
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

    private void testInitialiseWords(int numberOfBands, int wordsPerBand, String wordsSource, String nonwordsSource, String wordsResponse, String nonwordsResponse) throws Exception {
        System.out.println("initialiseWords: " + wordsSource);
        Vocabulary instance = new Vocabulary(numberOfBands, wordsPerBand);
        AdVocAsStimuliFromString reader = new AdVocAsStimuliFromString();
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        reader.parseWordsInputCSVString(provider, wordsSource, nonwordsSource, numberOfBands);
        ArrayList<ArrayList<AdVocAsStimulus>> rawWords = reader.getWords();

        ArrayList<ArrayList<AdVocAsStimulus>> words = instance.initialiseWords(rawWords);
        assertEquals(numberOfBands, words.size());
        for (int i = 0; i < numberOfBands; i++) {
            ArrayList<String> spellings = new ArrayList<>(words.get(i).size());
            assertEquals(wordsPerBand, words.get(i).size());
            for (AdVocAsStimulus stimulus : words.get(i)) {
                spellings.add(stimulus.getLabel());
                assertEquals(i + 1, stimulus.getBandNumber());
                assertEquals(wordsResponse, stimulus.getCorrectResponses());
                assertEquals(nonwordsResponse + "," + wordsResponse, stimulus.getRatingLabels());
                assertEquals(stimulus.getUniqueId(), stimulus.getLabel());
            }
            assertEquals(rawWords.get(i).size(), words.get(i).size());
            HashSet<String> set = new HashSet(spellings);
            assertEquals(set.size(), spellings.size()); // fails if there are repititions or permutation was incorrect
        }
    }

    /**
     * Test of initialiseWords method, of class Vocabulary.
     */
    @Test
    public void testInitialiseWords1() throws Exception {
        this.testInitialiseWords(this.numberOfBands_NL, this.wordsPerBand_NL, "Words_NL_1round", "NonWords_NL_1round", this.wordsResponse_NL, this.nonwordsResponse_NL);
    }

    /**
     * Test of initialiseWords method, of class Vocabulary.
     */
    @Test
    public void testInitialiseWords21() throws Exception {
        this.testInitialiseWords(this.numberOfBands_NL, this.wordsPerBand_NL / 2, "Words_NL_2rounds_1", "NonWords_NL_2rounds_1", this.wordsResponse_NL, this.nonwordsResponse_NL);
    }

    /**
     * Test of initialiseWords method, of class Vocabulary.
     */
    @Test
    public void testInitialiseWords22() throws Exception {
        this.testInitialiseWords(this.numberOfBands_NL, this.wordsPerBand_NL / 2, "Words_NL_2rounds_2", "NonWords_NL_2rounds_2", this.wordsResponse_NL, this.nonwordsResponse_NL);
    }

    /**
     * Test of initialiseWords method, of class Vocabulary.
     */
    @Test
    public void testInitialiseWords21_EN() throws Exception {
        this.testInitialiseWords(this.numberOfBands_EN, this.wordsPerBand_EN, "Words_EN_2rounds_1",  "NonWords_EN_2rounds_1", this.wordsResponse_EN, this.nonwordsResponse_EN);
    }

    /**
     * Test of initialiseWords method, of class Vocabulary.
     */
    @Test
    public void testInitialiseWords22_EN() throws Exception {
        this.testInitialiseWords(this.numberOfBands_EN, this.wordsPerBand_EN, "Words_EN_2rounds_2", "NonWords_EN_2rounds_2", this.wordsResponse_EN, this.nonwordsResponse_EN);
    }

    /**
     * Test of initialiseNonwords method, of class Vocabulary.
     */
    private void testInitialiseNonwords(String nonwordsSource, String wordsSource, String wordsResponse, String nonwordsResponse) throws Exception {
        System.out.println("initialiseNonwords: " + nonwordsSource);
        Vocabulary instance = new Vocabulary(0, 0);
        AdVocAsStimuliFromString reader = new AdVocAsStimuliFromString();
        AdVocAsStimuliProvider provider = new AdVocAsStimuliProvider(null);
        reader.parseNonWordsInputCSVString(provider, nonwordsSource, wordsSource);
        ArrayList<AdVocAsStimulus> rawNonWords = reader.getNonwords();

        ArrayList<AdVocAsStimulus> nonwords = instance.initialiseNonwords(rawNonWords);
        ArrayList<String> spellings = new ArrayList<>(nonwords.size());
        for (AdVocAsStimulus stimulus : nonwords) {
            spellings.add(stimulus.getLabel());
            assertEquals(0, stimulus.getBandNumber());
            assertEquals(nonwordsResponse, stimulus.getCorrectResponses());
            assertEquals(nonwordsResponse + "," + wordsResponse, stimulus.getRatingLabels());
            assertEquals(stimulus.getUniqueId(), stimulus.getLabel());

        }
        HashSet<String> set = new HashSet(spellings);
        assertEquals(nonwords.size(), set.size());

        // checking if the Equality is implemented OK on Strings
        ArrayList<String> testEqualityList = new ArrayList<>(2);
        testEqualityList.add("ok");
        testEqualityList.add("ok");
        assertEquals(2, testEqualityList.size());
        HashSet<String> testEqualitySet = new HashSet(testEqualityList);
        assertEquals(1, testEqualitySet.size());
    }

    /**
     * Test of initialiseWords method, of class Vocabulary.
     */
    @Test
    public void testInitialiseNonWords21_NL() throws Exception {
        this.testInitialiseNonwords("NonWords_NL_2rounds_1", "Words_NL_2rounds_1", this.wordsResponse_NL, this.nonwordsResponse_NL);
    }

    @Test
    public void testInitialiseNonWords22_NL() throws Exception {
        this.testInitialiseNonwords("NonWords_NL_2rounds_2", "Words_NL_2rounds_2",this.wordsResponse_NL, this.nonwordsResponse_NL);
    }

    @Test
    public void testInitialiseNonWords1_NL() throws Exception {
        this.testInitialiseNonwords("NonWords_NL_1round", "Words_NL_1round", this.wordsResponse_NL, this.nonwordsResponse_NL);
    }

    @Test
    public void testInitialiseNonWords21_EN() throws Exception {
        this.testInitialiseNonwords("NonWords_EN_2rounds_1", "Words_EN_2rounds_1", this.wordsResponse_EN, this.nonwordsResponse_EN);
    }

    @Test
    public void testInitialiseNonWords22_EN() throws Exception {
        this.testInitialiseNonwords("NonWords_EN_2rounds_2", "Words_EN_2rounds_2", this.wordsResponse_EN, this.nonwordsResponse_EN);
    }

}
