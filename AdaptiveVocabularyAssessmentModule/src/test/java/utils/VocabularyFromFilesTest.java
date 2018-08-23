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
package utils;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author olhshk
 */
public class VocabularyFromFilesTest {

    //final String NONWORD_FILE_LOCATION_NL = "2.selection_words_nonwords.csv";
    final String NONWORD_FILE_LOCATION_NL = "nonwords_selection_1.csv";
    final String WORD_FILE_LOCATION_NL = "words_selection_1.csv";
    final String NONWORD_FILE_LOCATION_EN = "english_nonwords.csv";
    final String WORD_FILE_LOCATION_EN = "english_words.csv";
    final int wordsPerBand = 40;
    final int numberOfSeries = 2;
    private final static String NONWORD_NL = "NEE&#44; ik ken dit woord niet";
    private final static String WORD_NL = "JA&#44; ik ken dit woord";
    private final static String NONWORD_EN = "NO&#44; I do not know this word";
    private final static String WORD_EN = "YES&#44; I know the word";

    public VocabularyFromFilesTest() {
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
     * Test of parseWordInputCSV method, of class VocabularyFromFiles.
     */
    private void testParseWordInputCSV(String fileLocation, String bandColumn, String wordColumn, String ratingLabels, String correctResponseWords) throws Exception {
        VocabularyFromFiles instance = new VocabularyFromFiles();
        instance.parseWordInputCSV(fileLocation, bandColumn, wordColumn, ratingLabels, correctResponseWords);
        ArrayList<String> words = instance.getWords();
        this.printStimuli(words, 0, words.size());
    }

    private void printStimuli(ArrayList<String> stimuli, int from, int uptoExcl) {
        System.out.println("\n");
        System.out.println("BandNumber;Spelling\n");
        for (int i = from; i < uptoExcl; i++) {
            String stimulusString = stimuli.get(i);
            System.out.println(stimulusString);
        }

        System.out.println("\n");
    }

    @Ignore
    @Test
    public void testParseWordInputCSV_NL() throws Exception {
        System.out.println("parseWordInputCSV NL");
        this.testParseWordInputCSV(WORD_FILE_LOCATION_NL, "Band", "spelling", NONWORD_NL + "," + WORD_NL, WORD_NL);
    }

    @Ignore
    @Test
    public void testParseNonWordInputCSV_NL() throws Exception {
        System.out.println("parseNonWordInputCSV NL");
        VocabularyFromFiles instance = new VocabularyFromFiles();
        instance.parseNonwordInputCSV(NONWORD_FILE_LOCATION_NL, "spelling", NONWORD_NL + "," + WORD_NL, NONWORD_NL);
        ArrayList<String> nonwords = instance.getNonwords();
        this.printStimuli(nonwords, 0, nonwords.size());
    }

    @Ignore
    @Test
    public void testParseWordInputCSV_EN_A() throws Exception {
        System.out.println("parseWordInputCSV EN");
        this.testParseWordInputCSV(WORD_FILE_LOCATION_EN, "Band", "List A", NONWORD_EN + "," + WORD_EN, WORD_EN);
    }

    @Ignore
    @Test
    public void testParseWordInputCSV_EN_B() throws Exception {
        System.out.println("parseWordInputCSV EN");
        this.testParseWordInputCSV(WORD_FILE_LOCATION_EN, "Band", "List B", NONWORD_EN + "," + WORD_EN, WORD_EN);
    }

    @Ignore
    @Test
    public void testParseNonWordInputCSV_EN_1() throws Exception {
        System.out.println("parseNonWordInputCSV EN");
        VocabularyFromFiles instance = new VocabularyFromFiles();
        instance.parseNonwordInputCSV(NONWORD_FILE_LOCATION_EN, "spelling", NONWORD_EN + "," + WORD_EN, NONWORD_EN);
        ArrayList<String> nonwords = instance.getNonwords();
        int limit = nonwords.size() / 2;
        System.out.println("*******************");
        System.out.println(" Engl nonwords 1");
        System.out.println("*******************");
        this.printStimuli(nonwords, 0, limit);

    }

    @Ignore
    @Test
    public void testParseNonWordInputCSV_EN_2() throws Exception {
        System.out.println("parseNonWordInputCSV EN");
        VocabularyFromFiles instance = new VocabularyFromFiles();
        instance.parseNonwordInputCSV(NONWORD_FILE_LOCATION_EN, "spelling", NONWORD_EN + "," + WORD_EN, NONWORD_EN);
        ArrayList<String> nonwords = instance.getNonwords();
        int limit = nonwords.size() / 2;
        System.out.println("*******************");
        System.out.println(" Engl nonwords 2");
        System.out.println("*******************");
        this.printStimuli(nonwords, limit, nonwords.size());

    }

}
