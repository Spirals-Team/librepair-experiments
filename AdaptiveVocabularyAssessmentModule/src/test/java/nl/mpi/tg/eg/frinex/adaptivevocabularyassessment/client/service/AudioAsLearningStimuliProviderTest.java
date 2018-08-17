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
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.TestConfigurationConstants;
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
 * @author admin_olha
 */
public class AudioAsLearningStimuliProviderTest {

    private AudioAsLearningStimuliProvider instance;

    public AudioAsLearningStimuliProviderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.instance = new AudioAsLearningStimuliProvider(null);
        // stimuliDir="static/stimuli/" maxDurationMin="10" firstStimulusDurationMs="1500" learningTrials="1000,1737,2045"
        this.instance.setstimuliDir(TestConfigurationConstants.AUDIO_STIMULI_DIR);
        this.instance.setmaxDurationMin(TestConfigurationConstants.AUDIO_MAX_DURATION_MINUTES);
        this.instance.setfirstStimulusDurationMs(TestConfigurationConstants.AUDIO_FIRST_STIMULUS_DURATION);
        this.instance.setlearningTrials(TestConfigurationConstants.AUDIO_LEARNING_TRIALS);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of initialiseStimuliState method, of class
     * AudioAsLearningStimuliProvider.
     */
    @Test
    public void testInitialiseStimuliState() {
        System.out.println("initialiseStimuliState");
        String stimuliStateSnapshot = "";
        this.instance.initialiseStimuliState(stimuliStateSnapshot);
        TrialTuple tt = this.instance.getCurrentTrialTuple();

        ArrayList<Trial> trials = tt.getTrials();
        assertEquals(TestConfigurationConstants.AUDIO_N_LEARNING_TRIALS, tt.getTrials().size());

        // "1000;kurk;skee_1.wav;1;Target-only;4 words;pif.wav;skee_2.wav;wadees.wav;landhoeg.wav;;;2;zerodb;0;";
        Trial trial1 = trials.get(0);
        assertEquals(1000, trial1.getId());
        assertEquals("kurk", trial1.getWord());
        assertEquals("skee_1", trial1.getTargetNonWord());
        assertEquals(1, trial1.getNumberOfSyllables());
        assertEquals(TrialCondition.TARGET_ONLY, trial1.getCondition());
        assertEquals(4, trial1.getTrialLength());
        assertEquals("skee_1", trial1.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("pif", trial1.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("skee_2", trial1.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("wadees", trial1.getStimuli().get(3).getStimulus().getLabel());
        assertEquals("landhoeg", trial1.getStimuli().get(4).getStimulus().getLabel());
        assertEquals(2, trial1.getPositionTarget());
        assertEquals("zerodb", trial1.getBandLabel());
        assertEquals(5, trial1.getBandIndex());
        assertEquals(0, trial1.getPositionFoil());

        //"1737;vocht;gocht.wav;1;NoTarget;5 words;inbong.wav;gop.wav;dorm.wav;wiffer.wav;blem.wav;;0;min6db;0;\n"
        Trial trial2 = trials.get(1);
        assertEquals(1737, trial2.getId());
        assertEquals("vocht", trial2.getWord());
        assertEquals("gocht", trial2.getTargetNonWord());
        assertEquals(1, trial2.getNumberOfSyllables());
        assertEquals(TrialCondition.NO_TARGET, trial2.getCondition());
        assertEquals(5, trial2.getTrialLength());
        assertEquals("gocht", trial2.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("inbong", trial2.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("gop", trial2.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("dorm", trial2.getStimuli().get(3).getStimulus().getLabel());
        assertEquals("wiffer", trial2.getStimuli().get(4).getStimulus().getLabel());
        assertEquals("blem", trial2.getStimuli().get(5).getStimulus().getLabel());
        assertEquals(0, trial2.getPositionTarget());
        assertEquals("min6db", trial2.getBandLabel());
        assertEquals(8, trial2.getBandIndex());
        assertEquals(0, trial2.getPositionFoil());

        //"2045;ring;ling_1.wav;1;Target+Foil;3 words;lixkroei.wav;ling_2.wav;daul.wav;;;;2;min10db;1;\n"
        Trial trial3 = trials.get(2);
        assertEquals(2045, trial3.getId());
        assertEquals("ring", trial3.getWord());
        assertEquals("ling_1", trial3.getTargetNonWord());
        assertEquals(1, trial3.getNumberOfSyllables());
        assertEquals(TrialCondition.TARGET_AND_FOIL, trial3.getCondition());
        assertEquals(3, trial3.getTrialLength());
        assertEquals("ling_1", trial3.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("lixkroei", trial3.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("ling_2", trial3.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("daul", trial3.getStimuli().get(3).getStimulus().getLabel());
        assertEquals(2, trial3.getPositionTarget());
        assertEquals("min10db", trial3.getBandLabel());
        assertEquals(10, trial3.getBandIndex());
        assertEquals(1, trial3.getPositionFoil());
    }

    @Test
    public void testRoundAllCorrect() {

        System.out.println("test round all correct");
        String stimuliStateSnapshot = "";
        this.instance.initialiseStimuliState(stimuliStateSnapshot);
        int stimuliCounter = 0;
        while (this.instance.hasNextStimulus(0)) {
            this.instance.nextStimulus(0);
            AudioAsStimulus audioStimulus = this.instance.getCurrentStimulus();
            stimuliCounter++;
            Stimulus stimulus = audioStimulus;

            if (audioStimulus.getwordType().equals(WordType.EXAMPLE_TARGET_NON_WORD)) {
                String correctResponce = audioStimulus.getCorrectResponses();
                this.instance.isCorrectResponse(stimulus, correctResponce);
                continue;
            }

            if (audioStimulus.getwordType().equals(WordType.TARGET_NON_WORD)) { // hit the target
                String correctResponce = audioStimulus.getCorrectResponses();
                this.instance.isCorrectResponse(stimulus, correctResponce);
            }

        }
        assertFalse(this.instance.getCurrentTrialTuple().isNotEmpty());
        ArrayList<BookkeepingStimulus<AudioAsStimulus>> recordi = this.instance.getResponseRecord();
        assertEquals(stimuliCounter, recordi.size());
        for (BookkeepingStimulus<AudioAsStimulus> record : recordi) {
            assertTrue(record.getCorrectness());
        }

    }

    @Test
    public void testRoundAllWrongReactions() {

        System.out.println("test round all correct");
        String stimuliStateSnapshot = "";
        this.instance.initialiseStimuliState(stimuliStateSnapshot);
        int stimuliCounter = 0;
        while (this.instance.hasNextStimulus(0)) {
            this.instance.nextStimulus(0);
            AudioAsStimulus audioStimulus = this.instance.getCurrentStimulus();
            stimuliCounter++;
            Stimulus stimulus = audioStimulus;

            if (audioStimulus.getwordType().equals(WordType.EXAMPLE_TARGET_NON_WORD)) {
                String correctResponce = audioStimulus.getCorrectResponses();
                this.instance.isCorrectResponse(stimulus, correctResponce);
                continue;
            }

            if (!audioStimulus.getwordType().equals(WordType.TARGET_NON_WORD)) { // hit the non-target
                String correctResponce = audioStimulus.getCorrectResponses();
                this.instance.isCorrectResponse(stimulus, correctResponce);
            }

        }
        assertFalse(this.instance.getCurrentTrialTuple().isNotEmpty());
        ArrayList<BookkeepingStimulus<AudioAsStimulus>> recordi = this.instance.getResponseRecord();
        assertEquals(stimuliCounter, recordi.size());
        for (BookkeepingStimulus<AudioAsStimulus> record : recordi) {
            if (!record.getStimulus().getwordType().equals(WordType.EXAMPLE_TARGET_NON_WORD)) {
                assertFalse(record.getCorrectness());
            }
        }

    }

}
