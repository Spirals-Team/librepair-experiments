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
import java.util.Random;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BookkeepingStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.peabody.PeabodyStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.peabodypool.PeabodyStimuliFromStringTest;
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
public class PeabodyStimuliProviderTest {

    int amountOfStimuli = 204;
    String fineTuningTupleLength = "12";
    String numberOfBands = "17";
    String startBand = "13";

    public PeabodyStimuliProviderTest() {
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
     * Test of initialiseStimuliState method, of class PeabodyStimuliProvider.
     */
    @Test
    public void testInitialiseStimuliState() {
        System.out.println("initialiseStimuliState");
        String stimuliStateSnapshot = "";
        int nOfBands = Integer.parseInt(this.numberOfBands);
        int sBand = Integer.parseInt(this.startBand);
        int stimuliPerBand = Integer.parseInt(this.fineTuningTupleLength);

        PeabodyStimuliProvider provider = new PeabodyStimuliProvider(null);
        provider.setfastTrackPresent("False");
        provider.setfineTuningFirstWrongOut("False");
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setstartBand(this.startBand);
        provider.setstimuliDir(PeabodyStimuliFromStringTest.PEABODY_STIMULI_DIR);
        provider.initialiseStimuliState(stimuliStateSnapshot);

        assertEquals(sBand - 1, provider.getCurrentBandIndex());

        ArrayList<ArrayList<PeabodyStimulus>> result = provider.getStimuliPool();
        assertEquals(nOfBands, result.size());
        int counter = 0;
        for (int setIndex = 0; setIndex < nOfBands; setIndex++) {
            for (int pageIndex = 0; pageIndex < stimuliPerBand; pageIndex++) {

                PeabodyStimulus stimulus = result.get(setIndex).get(pageIndex);
                counter++;

                String audioFile = stimulus.getAudio().substring(PeabodyStimuliFromStringTest.PEABODY_STIMULI_DIR.length());
                String[] bits = audioFile.split("_");
                int number = Integer.parseInt(bits[0]);
                assertEquals(counter, number);

                String[] bitsImage = stimulus.getImage().substring(PeabodyStimuliFromStringTest.PEABODY_STIMULI_DIR.length()).split("_");
                String set = bitsImage[0];
                int expectedSetNumber = setIndex + 1;
                assertEquals("set" + expectedSetNumber, set);

                int pageNumber = Integer.parseInt(bitsImage[2]);
                assertEquals(pageIndex + 1, pageNumber);

            }
        }
        assertEquals(this.amountOfStimuli, counter);

    }

    /**
     * Test of fineTuningToBeContinuedWholeTuple method, of class
     * PeabodyStimuliProvider.
     */
    @Test
    public void testRoundChampion_Full() {
        System.out.println("hasNextStimulus, nextStimulus, getCurrentStimulus, isCorrectResponse");
        String stimuliStateSnapshot = "";
        int nOfBands = Integer.parseInt(this.numberOfBands);
        int sBand = Integer.parseInt(this.startBand);
        int stimuliPerBand = Integer.parseInt(this.fineTuningTupleLength);

        PeabodyStimuliProvider provider = new PeabodyStimuliProvider(null);
        provider.setfastTrackPresent("False");
        provider.setfineTuningFirstWrongOut("False");
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setstartBand(this.startBand);
        provider.setstimuliDir(PeabodyStimuliFromStringTest.PEABODY_STIMULI_DIR);
        
        provider.initialiseStimuliState(stimuliStateSnapshot);

        // 
        int amountOfBandsToPass = nOfBands - sBand + 1;
        provider = this.roundWithErrors(provider, 0, 0, null, amountOfBandsToPass, stimuliPerBand, 0);

        assertFalse(provider.hasNextStimulus(0));
        assertTrue(provider.getChampion());
        assertFalse(provider.getLooser());
        assertEquals(this.amountOfStimuli, provider.getFinalScore());
    }

    /**
     * Test of fineTuningToBeContinuedWholeTuple method, of class
     * PeabodyStimuliProvider.
     */
    @Test
    public void testRoundChampion_FixedBorderline() {
        System.out.println("hasNextStimulus, nextStimulus, getCurrentStimulus, isCorrectResponse");
        String stimuliStateSnapshot = "";
        int nOfBands = Integer.parseInt(this.numberOfBands);
        int sBand = Integer.parseInt(this.startBand);
        int stimuliPerBand = Integer.parseInt(this.fineTuningTupleLength);

        PeabodyStimuliProvider provider = new PeabodyStimuliProvider(null);
        provider.setfastTrackPresent("False");
        provider.setfineTuningFirstWrongOut("False");
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setstartBand(this.startBand);
        provider.setstimuliDir(PeabodyStimuliFromStringTest.PEABODY_STIMULI_DIR);
        
        provider.initialiseStimuliState(stimuliStateSnapshot);

        provider = this.roundWithErrors(provider, 4, 4, null, 1, stimuliPerBand, 0);

        // 
        int amountOfBandsToPass = nOfBands - sBand + 1;

        provider = this.roundWithErrors(provider, 8, 8, null, amountOfBandsToPass - 1, stimuliPerBand, stimuliPerBand);

        assertFalse(provider.hasNextStimulus(0));
        assertTrue(provider.getChampion());
        assertFalse(provider.getLooser());
        assertEquals(this.amountOfStimuli - (4 + (amountOfBandsToPass - 1) * 8), provider.getFinalScore());
    }

    @Test
    public void testRoundChampion_RandomBorderline() {
        System.out.println("hasNextStimulus, nextStimulus, getCurrentStimulus, isCorrectResponse");
        String stimuliStateSnapshot = "";
        int nOfBands = Integer.parseInt(this.numberOfBands);
        int sBand = Integer.parseInt(this.startBand);
        int stimuliPerBand = Integer.parseInt(this.fineTuningTupleLength);

        PeabodyStimuliProvider provider = new PeabodyStimuliProvider(null);
        provider.setfastTrackPresent("False");
        provider.setfineTuningFirstWrongOut("False");
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setstartBand(this.startBand);
        provider.setstimuliDir(PeabodyStimuliFromStringTest.PEABODY_STIMULI_DIR);
        
        provider.initialiseStimuliState(stimuliStateSnapshot);

        Random rnd = new Random();

        provider = this.roundWithErrors(provider, 0, 4, rnd, 1, stimuliPerBand, 0);

        // 
        int amountOfBandsToPass = nOfBands - sBand + 1;

        provider = this.roundWithErrors(provider, 0, 8, rnd, amountOfBandsToPass - 1, stimuliPerBand, stimuliPerBand);

        assertFalse(provider.hasNextStimulus(0));
        assertTrue(provider.getChampion());
        assertFalse(provider.getLooser());
        assertTrue(this.amountOfStimuli - (4 + (amountOfBandsToPass - 1) * 8) <= provider.getFinalScore());
    }

    private PeabodyStimuliProvider roundWithErrors(PeabodyStimuliProvider provider, int lowerErrorBound, int upperErrorBound, Random rnd, int amountOfBandsToPass, int stimuliPerBand, int initIndex) {
        ArrayList<Integer> erroneousStimuli = new ArrayList<Integer>();
        int errorCounter = 0;
        for (int i = 0; i < amountOfBandsToPass * stimuliPerBand; i++) {

            assertTrue(String.valueOf(i) + " " + String.valueOf(amountOfBandsToPass) + " " + String.valueOf(stimuliPerBand) + " " + String.valueOf(errorCounter), provider.hasNextStimulus(0));

            int mod = (initIndex + i) % stimuliPerBand;

            if (mod == 0) {
                // there will be the new page next
                if (rnd != null) {
                    int limit = this.randomBetweenIncl(rnd, lowerErrorBound, upperErrorBound);
                    erroneousStimuli = this.initialiseRandomIndexArray(limit, stimuliPerBand);
                } else {
                    erroneousStimuli = this.initialiseRandomIndexArray(upperErrorBound, stimuliPerBand);
                }
            }
            
            boolean isCorrect = this.peabodyStepIsCorrect(provider, erroneousStimuli, initIndex + i, stimuliPerBand);
            if (!isCorrect) {
                errorCounter++;
            }
        }
        return provider;
    }

    private boolean peabodyStepIsCorrect(PeabodyStimuliProvider provider, ArrayList<Integer> erroneousStimuli, int stimulusIndex, int stimuliPerBand) {

        boolean retVal = true;

        int mod = stimulusIndex % stimuliPerBand;
        
        provider.nextStimulus(0);  // put the first stimuli in the leftwover tuple on top of the record array
        assertEquals(stimulusIndex, provider.getCurrentStimulusIndex());
        PeabodyStimulus stimulus = provider.getCurrentStimulus(); // picks the  top stimulus from the record array

        if (erroneousStimuli.contains(mod)) {
            String correctResponse = stimulus.getCorrectResponses();
            int corrIndex = Integer.parseInt(correctResponse);
            int distortedIndex;
            if (corrIndex < 4) {
                distortedIndex = corrIndex + 1;
            } else {
                distortedIndex = 1;
            }
            String distortedResponse = (new Integer(distortedIndex)).toString();
            boolean worngAnswer = provider.isCorrectResponse(stimulus, distortedResponse);
            assertFalse(worngAnswer);
            retVal = false;
        } else {
            boolean correctAnswer = provider.isCorrectResponse(stimulus, stimulus.getCorrectResponses());
            assertTrue(correctAnswer);
        }
        return retVal;
    }

    /**
     * Test of fineTuningToBeContinuedWholeTuple method, of class
     * PeabodyStimuliProvider.
     */
    @Test
    public void testRoundLooser_Full() {
        System.out.println("testRoundLooser_Full: hasNextStimulus, nextStimulus, getCurrentStimulus, isCorrectResponse");
        String stimuliStateSnapshot = "";
        int nOfBands = Integer.parseInt(this.numberOfBands);
        int sBand = Integer.parseInt(this.startBand);
        int stimuliPerBand = Integer.parseInt(this.fineTuningTupleLength);

        PeabodyStimuliProvider provider = new PeabodyStimuliProvider(null);
        provider.setfastTrackPresent("False");
        provider.setfineTuningFirstWrongOut("False");
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setstartBand(this.startBand);
        provider.setstimuliDir(PeabodyStimuliFromStringTest.PEABODY_STIMULI_DIR);
        
        provider.initialiseStimuliState(stimuliStateSnapshot);

        // 
        int amountOfBandsToPass = sBand;
        provider = this.roundWithErrors(provider, 9, 9, null, amountOfBandsToPass, stimuliPerBand, 0);

        assertFalse(provider.hasNextStimulus(0));
        assertFalse(provider.getChampion());
        assertTrue(provider.getLooser());
        assertEquals(12 - 9 * amountOfBandsToPass, provider.getFinalScore());
    }

    @Test
    public void testRoundLooser_Fixed() {
        System.out.println("testRoundLooser_Fixed: hasNextStimulus, nextStimulus, getCurrentStimulus, isCorrectResponse");
        String stimuliStateSnapshot = "";
        int nOfBands = Integer.parseInt(this.numberOfBands);
        int sBand = Integer.parseInt(this.startBand);
        int stimuliPerBand = Integer.parseInt(this.fineTuningTupleLength);

        PeabodyStimuliProvider provider = new PeabodyStimuliProvider(null);
        provider.setfastTrackPresent("False");
        provider.setfineTuningFirstWrongOut("False");
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setstartBand(this.startBand);
        provider.setstimuliDir(PeabodyStimuliFromStringTest.PEABODY_STIMULI_DIR);
        
        provider.initialiseStimuliState(stimuliStateSnapshot);

        // 
        int amountOfBandsToPass = sBand;

        provider = this.roundWithErrors(provider, 5, 5, null, 1, stimuliPerBand, 0);

        provider = this.roundWithErrors(provider, 9, 9, null, amountOfBandsToPass - 1, stimuliPerBand, 12);

        assertFalse(provider.hasNextStimulus(0));
        assertFalse(provider.getChampion());
        assertTrue(provider.getLooser());
        assertEquals(12 - 5 - 9 * (amountOfBandsToPass - 1), provider.getFinalScore());
    }

    @Test
    public void testRoundLooser_Random() {
        System.out.println("hasNextStimulus, nextStimulus, getCurrentStimulus, isCorrectResponse");
        String stimuliStateSnapshot = "";
        int nOfBands = Integer.parseInt(this.numberOfBands);
        int sBand = Integer.parseInt(this.startBand);
        int stimuliPerBand = Integer.parseInt(this.fineTuningTupleLength);

        PeabodyStimuliProvider provider = new PeabodyStimuliProvider(null);
        provider.setfastTrackPresent("False");
        provider.setfineTuningFirstWrongOut("False");
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setstartBand(this.startBand);
        provider.setstimuliDir(PeabodyStimuliFromStringTest.PEABODY_STIMULI_DIR);
        
        provider.initialiseStimuliState(stimuliStateSnapshot);

        // 
        int amountOfBandsToPass = sBand;

        Random rnd = new Random();

        provider = this.roundWithErrors(provider, 5, 12, rnd, 1, stimuliPerBand, 0);

        provider = this.roundWithErrors(provider, 9, 12, rnd, amountOfBandsToPass - 1, stimuliPerBand, 12);

        assertFalse(provider.hasNextStimulus(0));
        assertFalse(provider.getChampion());
        assertTrue(provider.getLooser());
        assertTrue(12 - 5 - 9 * (amountOfBandsToPass - 1) >= provider.getFinalScore());
    }

    @Test
    public void testRound_Random() {
        System.out.println("hasNextStimulus, nextStimulus, getCurrentStimulus, isCorrectResponse");
        String stimuliStateSnapshot = "";
        int nOfBands = Integer.parseInt(this.numberOfBands);
        int sBand = Integer.parseInt(this.startBand);
        int stimuliPerBand = Integer.parseInt(this.fineTuningTupleLength);

        PeabodyStimuliProvider provider = new PeabodyStimuliProvider(null);
        provider.setfastTrackPresent("False");
        provider.setfineTuningFirstWrongOut("False");
        provider.setnumberOfBands(this.numberOfBands);
        provider.setfineTuningTupleLength(this.fineTuningTupleLength);
        provider.setstartBand(this.startBand);
        provider.setstimuliDir(PeabodyStimuliFromStringTest.PEABODY_STIMULI_DIR);
        
        provider.initialiseStimuliState(stimuliStateSnapshot);

        Random rnd = new Random();

        ArrayList<Integer> erronenousStimuli = new ArrayList<Integer>();
        int stepCounter = 0;
        int errorCounter = 0;
        while (provider.hasNextStimulus(0)) {
            //PeabodyStep(PeabodyStimuliProvider provider, ArrayList<Integer> erronenousStimuli, int stepCounter, int lowerErrorBound, int upperErrorBound, Random rnd, int stimuliPerBand, int initIndex)
            boolean isCorrect = this.peabodyStepIsCorrect(provider, erronenousStimuli, stepCounter, stimuliPerBand);
            if (!isCorrect) {
                errorCounter++;
            }
            stepCounter++;

        }
        ArrayList<BookkeepingStimulus<PeabodyStimulus>> recordi = provider.getResponseRecord();
        String audioString = recordi.get(recordi.size()-1).getStimulus().getAudio();
        String audioFile = audioString.substring(PeabodyStimuliFromStringTest.PEABODY_STIMULI_DIR.length());
        String[] parts = audioFile.split("_");
        int index =  Integer.parseInt(parts[0]);
        assertEquals(index-errorCounter, provider.getFinalScore());
    }

    int randomBetweenIncl(Random rnd, int min, int max) {
        if (min >= max) {
            return max;
        }
        int diff = max - min;
        int rndInt = rnd.nextInt(diff + 1);
        return min + rndInt;
    }

    private ArrayList<Integer> initialiseRandomInices(int amount, ArrayList<Integer> initial) {
        ArrayList<Integer> retVal = new ArrayList<Integer>(initial.size());
        for (Integer i : initial) {
            retVal.add(i);
        }
        Random rnd = new Random();
        for (int i = 0; i < initial.size() - amount; i++) {
            int ind = rnd.nextInt(retVal.size());
            retVal.remove(ind);
        }
        return retVal;
    }

    private ArrayList<Integer> initialiseRandomIndexArray(int amount, int upperBound) {
        ArrayList<Integer> help = new ArrayList<Integer>(upperBound);
        for (int i = 0; i < upperBound; i++) {
            help.add(i);
        }
        ArrayList<Integer> retVal = this.initialiseRandomInices(amount, help);
        return retVal;
    }

}
