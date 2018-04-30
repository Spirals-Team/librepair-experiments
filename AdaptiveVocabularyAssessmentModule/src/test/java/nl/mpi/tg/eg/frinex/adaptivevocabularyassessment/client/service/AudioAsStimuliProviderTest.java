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
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BookkeepingStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.AudioAsStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.Trial;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.TrialCondition;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.TrialTuple;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.WordType;
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
public class AudioAsStimuliProviderTest {

    private AudioAsStimuliProvider instance;
    private final int startBand = 5;
    private final int tupleSize = 4;
    private final int numberOfBands = 11;

    public AudioAsStimuliProviderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.instance = new AudioAsStimuliProvider(null);
        this.instance.setfastTrackPresent("False");
        this.instance.setfineTuningFirstWrongOut("False");
        this.instance.setfineTuningTupleLength(Integer.toString(this.tupleSize));
        this.instance.setfineTuningUpperBoundForCycles("2");
        this.instance.setnumberOfBands(Integer.toString(this.numberOfBands));
        this.instance.setstartBand(Integer.toString(this.startBand));
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of initialiseStimuliState method, of class AudioAsStimuliProvider.
     */
    @Test
    public void testInitialiseStimuliState() { // also check initialisation of trial tuple which is called inside initialiseStimuliState
        System.out.println("initialiseStimuliState");
        String stimuliStateSnapshot = "";
        this.instance.initialiseStimuliState(stimuliStateSnapshot);
        assertEquals(11, this.instance.getnumberOfBands());

        TrialTuple currentTrialTuple = this.instance.getCurrentTrialTuple();

        this.checkTuple(currentTrialTuple);

        assertEquals(this.startBand, this.instance.getCurrentBandIndex());
    }

    /**
     * Test of nextStimulus method, of class AudioAsStimuliProvider.
     */
    @Test
    public void testHasNextStAndNextStimulus() {
        System.out.println("nextStimulus");
        String stimuliStateSnapshot = "";
        this.instance.initialiseStimuliState(stimuliStateSnapshot);
        assertEquals(this.startBand, this.instance.getCurrentBandIndex());
        assertTrue(this.instance.hasNextStimulus(0));

        int memSize = this.instance.getCurrentTrialTuple().getNumberOfStimuli();
        this.instance.nextStimulus(0);
        assertEquals(memSize - 1, this.instance.getCurrentTrialTuple().getNumberOfStimuli());
        assertEquals(1, this.instance.getResponseRecord().size());
        assertEquals(WordType.EXAMPLE_TARGET_NON_WORD, this.instance.getCurrentStimulus().getwordType());

    }

    /**
     * Test of isWholeTupleCorrect method, of class AudioAsStimuliProvider.
     */
    @Test
    public void testAllTupleIsCorrect1() {
        System.out.println("allTupleIsCorrect");
        String stimuliStateSnapshot = "";
        this.instance.initialiseStimuliState(stimuliStateSnapshot);

        while (this.instance.getCurrentTrialTuple().isNotEmpty()) {
            this.instance.hasNextStimulus(0);
            this.instance.nextStimulus(0);
            AudioAsStimulus audioStimulus = this.instance.getCurrentStimulus();
            Stimulus stimulus = audioStimulus; // upcasting
            if (audioStimulus.getwordType().equals(WordType.TARGET_NON_WORD)) {
                instance.isCorrectResponse(stimulus, AudioAsStimulus.AUDIO_RATING_LABEL);
            }
        }
        assertTrue(this.instance.isWholeTupleCorrect());
    }

    /**
     * Test of isWholeTupleCorrect method, of class AudioAsStimuliProvider.
     */
    @Test
    public void testAllTupleIsCorrect2() {
        System.out.println("allTupleIsCorrect 2");
        String stimuliStateSnapshot = "";
        this.instance.initialiseStimuliState(stimuliStateSnapshot);

        boolean mistaken = false;
        while (this.instance.getCurrentTrialTuple().isNotEmpty()) {
            this.instance.hasNextStimulus(0);
            this.instance.nextStimulus(0);
            AudioAsStimulus audioStimulus = this.instance.getCurrentStimulus();
            Stimulus stimulus = audioStimulus; // upcasting
            if (!audioStimulus.getwordType().equals(WordType.EXAMPLE_TARGET_NON_WORD)) { //when evaluated
                if (audioStimulus.getwordType().equals(WordType.TARGET_NON_WORD)) {
                    instance.isCorrectResponse(stimulus, AudioAsStimulus.AUDIO_RATING_LABEL);
                }
                if (!audioStimulus.getwordType().equals(WordType.TARGET_NON_WORD) && !mistaken) { // hitting once worngly
                    instance.isCorrectResponse(stimulus, AudioAsStimulus.AUDIO_RATING_LABEL);
                    mistaken = true;
                }
            }
        }
        assertFalse(this.instance.isWholeTupleCorrect());
    }

    /**
     * Test of isWholeTupleCorrect method, of class AudioAsStimuliProvider.
     */
    @Test
    public void testAllTupleIsCorrect3() {
        System.out.println("allTupleIsCorrect 3");
        String stimuliStateSnapshot = "";
        this.instance.initialiseStimuliState(stimuliStateSnapshot);

        int n = this.instance.getCurrentTrialTuple().getNumberOfStimuli();
        boolean mistaken = false;
        while (this.instance.getCurrentTrialTuple().isNotEmpty()) {
            this.instance.hasNextStimulus(0);
            this.instance.nextStimulus(0);
            AudioAsStimulus audioStimulus = this.instance.getCurrentStimulus();
            Stimulus stimulus = audioStimulus; // upcasting
            if (audioStimulus.getwordType().equals(WordType.TARGET_NON_WORD)) { // missing a hit
                if (mistaken) {
                    instance.isCorrectResponse(stimulus, AudioAsStimulus.AUDIO_RATING_LABEL);
                } else {
                    mistaken = true;  // missing a hit
                }
            }
        }
        assertFalse(this.instance.isWholeTupleCorrect());
    }

    /**
     * Test of isCorrectResponse method, of class AudioAsStimuliProvider.
     */
    @Test
    public void testIsCorrectResponse1() {
        System.out.println("isCorrectResponse-1");
        String stimuliStateSnapshot = "";
        this.instance.initialiseStimuliState(stimuliStateSnapshot);
        while (this.instance.getCurrentTrialTuple().isNotEmpty()) {
            this.instance.hasNextStimulus(0);
            this.instance.nextStimulus(0);
            int lastIndex = this.instance.getResponseRecord().size() - 1;
            BookkeepingStimulus<AudioAsStimulus> bStimulus = this.instance.getResponseRecord().get(lastIndex);
            Stimulus stimulus = bStimulus.getStimulus(); // upcasting
            if (bStimulus.getStimulus().getwordType().equals(WordType.TARGET_NON_WORD)) {
                Trial trBefore = this.instance.getCurrentTrialTuple().getFirstNonusedTrial();
                assertTrue(this.instance.isCorrectResponse(stimulus, ""));
                assertEquals(AudioAsStimulus.USER_REACTION, bStimulus.getReaction());
                assertNotEquals(trBefore, this.instance.getCurrentTrialTuple().getFirstNonusedTrial()); // trial is wiped on pressing to go to the next trial
            } else {
                if (bStimulus.getStimulus().getwordType().equals(WordType.EXAMPLE_TARGET_NON_WORD)) {
                    Trial trBefore = this.instance.getCurrentTrialTuple().getFirstNonusedTrial();
                    this.instance.isCorrectResponse(stimulus, "");
                    assertEquals(trBefore, this.instance.getCurrentTrialTuple().getFirstNonusedTrial()); // trials are not cleaned after pressing button on the cue stimulus
                } else {
                    Trial trBefore = this.instance.getCurrentTrialTuple().getFirstNonusedTrial();
                    assertFalse(this.instance.isCorrectResponse(stimulus, ""));
                    assertEquals(AudioAsStimulus.USER_REACTION, bStimulus.getReaction());
                    assertNotEquals(trBefore, this.instance.getCurrentTrialTuple().getFirstNonusedTrial()); // trial is wiped on pressing to go to the next trial
                }
            }
        }
    }

    /**
     * Test of isCorrectResponse method, of class AudioAsStimuliProvider.
     */
    @Test
    public void testIsCorrectResponse2() {
        System.out.println("isCorrectResponse-2");
        String stimuliStateSnapshot = "";
        this.instance.initialiseStimuliState(stimuliStateSnapshot);
        while (this.instance.getCurrentTrialTuple().isNotEmpty()) {
            this.instance.hasNextStimulus(0);
            this.instance.nextStimulus(0);
            int lastIndex = this.instance.getResponseRecord().size() - 1;
            BookkeepingStimulus<AudioAsStimulus> bStimulus = this.instance.getResponseRecord().get(lastIndex);
            Stimulus stimulus = bStimulus.getStimulus(); // upcasting
            if (bStimulus.getStimulus().getwordType().equals(WordType.TARGET_NON_WORD)) {
                Trial trBefore = this.instance.getCurrentTrialTuple().getFirstNonusedTrial();
                assertTrue(this.instance.isCorrectResponse(stimulus, AudioAsStimulus.AUDIO_RATING_LABEL));
                assertEquals(AudioAsStimulus.USER_REACTION, bStimulus.getReaction());
                assertNotEquals(trBefore, this.instance.getCurrentTrialTuple().getFirstNonusedTrial()); // trial is wiped on pressing to go to the next trial
            } else {
                if (bStimulus.getStimulus().getwordType().equals(WordType.EXAMPLE_TARGET_NON_WORD)) {
                    Trial trBefore = this.instance.getCurrentTrialTuple().getFirstNonusedTrial();
                    this.instance.isCorrectResponse(stimulus, AudioAsStimulus.AUDIO_RATING_LABEL);
                    assertEquals(trBefore, this.instance.getCurrentTrialTuple().getFirstNonusedTrial()); // trials are not cleaned after pressing button on the cue stimulus
                } else {
                    Trial trBefore = this.instance.getCurrentTrialTuple().getFirstNonusedTrial();
                    assertFalse(this.instance.isCorrectResponse(stimulus, AudioAsStimulus.AUDIO_RATING_LABEL));
                    assertEquals(AudioAsStimulus.USER_REACTION, bStimulus.getReaction());
                    assertNotEquals(trBefore, this.instance.getCurrentTrialTuple().getFirstNonusedTrial()); // trial is wiped on pressing to go to the next trial
                }
            }
        }
    }

    /**
     * Test of initialiseNextFineTuningTuple method, of class
     * AudioAsStimuliProvider.
     */
    @Test
    public void testInitialiseNextFineTuningTuple() {
        System.out.println("initialiseNextFineTuningTuple");

        String stimuliStateSnapshot = "";
        this.instance.initialiseStimuliState(stimuliStateSnapshot);

        while (this.instance.getCurrentTrialTuple().isNotEmpty()) {
            this.instance.getCurrentTrialTuple().getFirstNonusedTrial().getStimuli().remove(0);
        }

        boolean result = this.instance.initialiseNextFineTuningTuple();
        TrialTuple tt = this.instance.getCurrentTrialTuple();
        this.checkTuple(tt);
        assertEquals(null, this.instance.getCurrentTrialTuple().getCorrectness());
    }

    /**
     * Test of tupleIsNotEmpty method, of class AudioAsStimuliProvider.
     */
    @Test
    public void testTupleIsNotEmptyAndNextStimulus() {
        System.out.println("tupleIsNotEmpty");

        String stimuliStateSnapshot = "";
        this.instance.initialiseStimuliState(stimuliStateSnapshot);

        for (int i = 0; i < this.tupleSize; i++) {
            assertTrue(this.instance.getCurrentTrialTuple().getTrials().get(i).getStimuli().size() > 1);
        }

        assertTrue(this.instance.getCurrentTrialTuple().isNotEmpty());

        while (this.instance.getCurrentTrialTuple().isNotEmpty()) {
            this.instance.nextStimulus(0);
        }

        assertFalse(this.instance.getCurrentTrialTuple().isNotEmpty());

        for (int i = 0; i < this.tupleSize; i++) {
            assertEquals(0, this.instance.getCurrentTrialTuple().getTrials().get(i).getStimuli().size());
        }

    }

    /**
     * Test of getTrialTuple method, of class AudioAsStimuliProvider.
     */
    @Test
    public void testGetTrialTuple() {
        System.out.println("getTrialTuple");
        System.out.println("tupleIsNotEmpty");

        String stimuliStateSnapshot = "";
        this.instance.initialiseStimuliState(stimuliStateSnapshot);

        for (int i = 0; i < this.tupleSize; i++) {
            Trial trial = this.instance.getCurrentTrialTuple().getTrials().get(i);
            TrialCondition cond = trial.getCondition();
            ArrayList<BookkeepingStimulus<AudioAsStimulus>> stimuli = trial.getStimuli();
            assertTrue(stimuli.size() > 1);
            int countFoil = 0;
            int countTarget = 0;
            int countNonWord = 0;
            for (BookkeepingStimulus<AudioAsStimulus> bStimulus : stimuli) {
                AudioAsStimulus stimulus = bStimulus.getStimulus();
                if (stimulus.getwordType().equals(WordType.FOIL)) {
                    countFoil++;
                }
                if (stimulus.getwordType().equals(WordType.TARGET_NON_WORD)) {
                    countTarget++;
                }
                if (stimulus.getwordType().equals(WordType.NON_WORD)) {
                    countNonWord++;
                }
            }
            if (cond.equals(TrialCondition.NO_TARGET)) {
                assertEquals(0, countFoil);
                assertEquals(0, countTarget);
                assertEquals(stimuli.size() - 1, countNonWord);
            }
            if (cond.equals(TrialCondition.TARGET_ONLY)) {
                assertEquals(0, countFoil);
                assertEquals(1, countTarget);
                assertEquals(stimuli.size() - 2, countNonWord);
            }
            if (cond.equals(TrialCondition.TARGET_AND_FOIL)) {
                assertEquals(1, countFoil);
                assertEquals(1, countTarget);
                assertEquals(stimuli.size() - 3, countNonWord);
            }
        }

    }

    /**
     *
     * /**
     * Test of hasNextStimulus method, of class AudioAsStimuliProvider.
     */
    @Test
    public void testHasNextStimulusChampion() {
        System.out.println("hasNextStimulus Champion");
        String stimuliStateSnapshot = "";
        this.instance.initialiseStimuliState(stimuliStateSnapshot);
        assertEquals(this.startBand, this.instance.getCurrentBandIndex());

        int i = 0;
        while (this.instance.hasNextStimulus(i)) {
            this.instance.nextStimulus(i);
            AudioAsStimulus audioStimulus = this.instance.getCurrentStimulus();
            Stimulus stimulus = audioStimulus;
            if (audioStimulus.getwordType().equals(WordType.TARGET_NON_WORD)) {
                String correctResponce = audioStimulus.getCorrectResponses();
                this.instance.isCorrectResponse(stimulus, correctResponce); // hit only when a target
            }
            i++;
        }

        assertTrue(this.instance.getChampion());
        assertFalse(this.instance.getCycel2());
        assertFalse(this.instance.getLooser());
        assertEquals(this.numberOfBands, this.instance.getBandScore());

        ArrayList<BookkeepingStimulus<AudioAsStimulus>> record = this.instance.getResponseRecord();
        //this.printRecord(record);

        System.out.println("getStringFineTuningHistory");
        String startRow = "";
        String endRow = "\n";
        String startColumn = "";
        String endColumn = ";";
        String format = "csv";
        String history = this.instance.getStringFineTuningHistory(startRow, endRow, startColumn, endColumn, format);
        assertNotNull(history);
        //System.out.println(history);

        System.out.println(" extra-test toString()");
        String snapShot = this.instance.toString();

        // clean 
        this.instance.initialiseStimuliState("");
        System.out.println(" extra-test reinitialise");
        this.instance.initialiseStimuliState(snapShot);
        System.out.println(" extra-test toString()-2");
        String snapShot2 = this.instance.toString();
        assertEquals(snapShot, snapShot2);
    }

    /**
     * Test of hasNextStimulus method, of class AudioAsStimuliProvider.
     */
    @Test
    public void testHasNextStimulusLooser() {
        System.out.println("hasNextStimulus Looser");
        String stimuliStateSnapshot = "";
        this.instance.initialiseStimuliState(stimuliStateSnapshot);
        assertEquals(this.startBand, this.instance.getCurrentBandIndex());

        int i = 0;
        boolean disturt = false;
        while (this.instance.hasNextStimulus(i)) {
            this.instance.nextStimulus(i);
            AudioAsStimulus audioStimulus = this.instance.getCurrentStimulus();
            Stimulus stimulus = audioStimulus;
            i++;
            if (disturt) {
                // make a mistake
                if (!audioStimulus.getwordType().equals(WordType.TARGET_NON_WORD) && !audioStimulus.getwordType().equals(WordType.EXAMPLE_TARGET_NON_WORD)) {
                    String correctResponce = audioStimulus.getCorrectResponses();
                    this.instance.isCorrectResponse(stimulus, correctResponce);
                }
            } else {
                // give a correct answer
                if (audioStimulus.getwordType().equals(WordType.TARGET_NON_WORD) && !audioStimulus.getwordType().equals(WordType.EXAMPLE_TARGET_NON_WORD)) {
                    String correctResponce = audioStimulus.getCorrectResponses();
                    this.instance.isCorrectResponse(stimulus, correctResponce);
                }
            }
            if (audioStimulus.getwordType().equals(WordType.EXAMPLE_TARGET_NON_WORD)) {
                disturt = true;
            } else {
                disturt = false;
            }
        }

        assertFalse(this.instance.getChampion());
        assertFalse(this.instance.getCycel2());
        assertTrue(this.instance.getLooser());
        assertEquals(1, this.instance.getBandScore());

        ArrayList<BookkeepingStimulus<AudioAsStimulus>> record = this.instance.getResponseRecord();
        //this.printRecord(record);

        System.out.println(" extra-test getStringFineTuningHistory");
        String startRow = "";
        String endRow = "\n";
        String startColumn = "";
        String endColumn = ";";
        String format = "csv";
        String history = this.instance.getStringFineTuningHistory(startRow, endRow, startColumn, endColumn, format);
        assertNotNull(history);
        //System.out.println(history);

        System.out.println(" extra-test toString()");
        String snapShot = this.instance.toString();

        // clean 
        this.instance.initialiseStimuliState("");
        System.out.println(" extra-test reinitialise");
        this.instance.initialiseStimuliState(snapShot);
        System.out.println(" extra-test toString()-2");
        String snapShot2 = this.instance.toString();
        assertEquals(snapShot, snapShot2);

    }

    /**
     * Test of hasNextStimulus method, of class AudioAsStimuliProvider.
     */
    @Test
    public void testHasNextStimulusLoop() {
        System.out.println("hasNextStimulus Loop");
        String stimuliStateSnapshot = "";
        this.instance.initialiseStimuliState(stimuliStateSnapshot);
        assertEquals(this.startBand, this.instance.getCurrentBandIndex());

        int i = 0;
        boolean distort = false;
        int previousBandIndex = 0;
        while (this.instance.hasNextStimulus(i)) {
            this.instance.nextStimulus(i);
            AudioAsStimulus audioStimulus = this.instance.getCurrentStimulus();
            Stimulus stimulus = audioStimulus;
            i++;

            if (this.instance.getCurrentBandIndex() > previousBandIndex) {
                distort = true; // we jumped to the higher band, need to make a mistake to force looping
            } else {
                distort = false;
            }

            if (!audioStimulus.getwordType().equals(WordType.EXAMPLE_TARGET_NON_WORD)) {
                previousBandIndex = this.instance.getCurrentBandIndex();
                if (distort) { // make a mistake
                    if (!audioStimulus.getwordType().equals(WordType.TARGET_NON_WORD)) {
                        this.instance.isCorrectResponse(stimulus, AudioAsStimulus.AUDIO_RATING_LABEL);
                    }
                    if (audioStimulus.getwordType().equals(WordType.TARGET_NON_WORD)) {
                        // hit is missed !
                    }
                } else { // non-distort, call isCorrect only on target
                    if (audioStimulus.getwordType().equals(WordType.TARGET_NON_WORD)) {
                        this.instance.isCorrectResponse(stimulus, AudioAsStimulus.AUDIO_RATING_LABEL);
                    }
                }
            }
        }

        assertFalse(this.instance.getChampion());
        assertTrue(this.instance.getCycel2());
        assertFalse(this.instance.getLooser());
        assertEquals(6, this.instance.getBandScore());

        ArrayList<BookkeepingStimulus<AudioAsStimulus>> record = this.instance.getResponseRecord();
        //this.printRecord(record);

        System.out.println("getStringFineTuningHistory");
        String startRow = "";
        String endRow = "\n";
        String startColumn = "";
        String endColumn = ";";
        String format = "csv";
        String history = this.instance.getStringFineTuningHistory(startRow, endRow, startColumn, endColumn, format);
        assertNotNull(history);
        //System.out.println(history);

        System.out.println(" extra-test toString()");
        String snapShot = this.instance.toString();

        // clean 
        this.instance.initialiseStimuliState("");
        System.out.println(" extra-test reinitialise");
        this.instance.initialiseStimuliState(snapShot);
        System.out.println(" extra-test toString()-2");
        String snapShot2 = this.instance.toString();
        assertEquals(snapShot, snapShot2);
    }

    /**
     * Test of toString method, of class AudioAsStimuliProvider.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        this.instance.initialiseStimuliState("");
        String result = this.instance.toString();
        this.instance.initialiseStimuliState(result);
        assertEquals(result, this.instance.toString());
    }

    private void checkTuple(TrialTuple tuple) {

        assertEquals(this.tupleSize, tuple.getTrials().size());
        int countTargetAndFoil = 0;
        int countNoTarget = 0;
        int countTargetOnly = 0;
        int count3 = 0;
        int count4 = 0;
        int count5 = 0;
        int count6 = 0;
        for (int i = 0; i < this.tupleSize; i++) {

            Trial trial = tuple.getTrials().get(i);
            assertEquals(this.startBand, trial.getBandIndex());
            ArrayList<BookkeepingStimulus<AudioAsStimulus>> stimuli = trial.getStimuli();
            assertEquals(WordType.EXAMPLE_TARGET_NON_WORD, stimuli.get(0).getStimulus().getwordType()); // the first stimulus should always example
            assertEquals(trial.getTrialLength() + 1, stimuli.size());

            int tLength = trial.getTrialLength();
            TrialCondition tc = trial.getCondition();

            if (tc.equals(TrialCondition.NO_TARGET)) {
                countNoTarget++;
            }

            if (tc.equals(TrialCondition.TARGET_AND_FOIL)) {
                countTargetAndFoil++;
            }

            if (tc.equals(TrialCondition.TARGET_ONLY)) {
                countTargetOnly++;
            }

            if (tLength == 3) {
                count3++;
            }
            if (tLength == 4) {
                count4++;
            }
            if (tLength == 5) {
                count5++;
            }
            if (tLength == 6) {
                count6++;
            }
        }

        assertEquals(2, countNoTarget);
        assertEquals(1, countTargetOnly);
        assertEquals(1, countTargetAndFoil);

        assertEquals(this.tupleSize, count3 + count4 + count5 + count6);
        assertTrue(count3 <= 1);
        assertTrue(count4 <= 1);
        assertTrue(count5 <= 1);
        assertTrue(count6 <= 1);
    }

    private void printRecord(ArrayList<BookkeepingStimulus<AudioAsStimulus>> record) {
        for (BookkeepingStimulus<AudioAsStimulus> bStimulus : record) {
            if (bStimulus == null) { // trial bound
                System.out.print("TUPLE BOUND \n");
                continue;
            }
            AudioAsStimulus stimulus = bStimulus.getStimulus();
            System.out.print(stimulus.getbandLabel());
            System.out.print("  ");
            System.out.print(stimulus.getLabel());
            System.out.print("  ");
            System.out.print(stimulus.getwordType());
            System.out.print("  ");
            System.out.print(stimulus.getCorrectResponses());
            System.out.print("  ");
            System.out.print(bStimulus.getReaction());
            System.out.println();
        }
    }

}
