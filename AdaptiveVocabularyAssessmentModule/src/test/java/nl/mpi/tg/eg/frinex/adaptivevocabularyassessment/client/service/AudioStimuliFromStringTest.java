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
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.audiopool.AudioStimuliFromString;
import java.util.LinkedHashMap;
import java.util.Set;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BookkeepingStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.AudioAsStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.Trial;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.TrialCondition;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.WordType;
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
public class AudioStimuliFromStringTest {

    public AudioStimuliFromStringTest() {
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
     * Test of readTrialsAsCsv method, of class AudioStimuliFromString.
     */
    @Test
    public void testReadTrialsAsCsv() {
        System.out.println("readTrialsAsCsv");
        AudioStimuliFromString instance = new AudioStimuliFromString();
        instance.readTrialsAsCsv(AudioAsStimuliProvider.LABELLING);
        LinkedHashMap<Integer, Trial> trials = instance.getHashedTrials();
        Set<Integer> keys = trials.keySet();
        assertEquals(2156, trials.size());
        for (Integer i : keys) {

            Trial trial = trials.get(i);
            assertEquals(i, new Integer(trial.getId()));
            assertEquals(trial.getTrialLength() + 1, trial.getStimuli().size());

            AudioAsStimulus cue = trial.getStimuli().get(0).getStimulus();
            assertEquals(WordType.EXAMPLE_TARGET_NON_WORD, cue.getwordType());
            assertFalse(cue.hasRatingLabels());

            // checking non-cue stimuli
            for (int j = 1; j < trial.getStimuli().size(); j++) {
                assertTrue(trial.getStimuli().get(j).getStimulus().hasRatingLabels());
            }

            if (trial.getPositionTarget() > 0) {
                AudioAsStimulus target = trial.getStimuli().get(trial.getPositionTarget()).getStimulus();
                String[] bufExp = cue.getLabel().split("_");
                String expectedLabel = bufExp[0];
                String[] bufLabel = target.getLabel().split("_");
                String label = bufLabel[0];
                assertEquals("Trial number " + (new Integer(i + 1)).toString(), expectedLabel, label);
            }

            if (trial.getCondition() == TrialCondition.TARGET_AND_FOIL) {
                assertTrue(trial.getPositionFoil() > 0);
                assertTrue(trial.getPositionTarget() > 0);
            }
            if (trial.getPositionFoil() > 0 && trial.getPositionTarget() > 0) {
                assertEquals(TrialCondition.TARGET_AND_FOIL, trial.getCondition());
            }

            if (trial.getCondition() == TrialCondition.NO_TARGET) {
                assertTrue(trial.getPositionTarget() == 0);
                assertTrue(trial.getPositionFoil() == 0);
            }
            if (trial.getPositionTarget() == 0) {
                assertEquals(trial.getCondition(), TrialCondition.NO_TARGET);
            }

            if (trial.getCondition() == TrialCondition.TARGET_ONLY) {
                assertTrue(trial.getPositionTarget() > 0);
                assertTrue(trial.getPositionFoil() == 0);
            }
            if (trial.getPositionTarget() > 0 && trial.getPositionFoil() == 0) {
                assertEquals(trial.getCondition(), TrialCondition.TARGET_ONLY);
            }

            for (int j = 0; j < trial.getStimuli().size(); j++) {
                AudioAsStimulus stimulus = trial.getStimuli().get(j).getStimulus();
                assertEquals(trial.getBandIndex(), stimulus.getbandIndex());
                assertEquals(trial.getBandLabel(), stimulus.getbandLabel());
            }

        }

        // "1;vloer;smoer_1.wav;1;Target-only;3 words;deebral.wav;smoer_2.wav;wijp.wav;;;;2;plus10db;0;";
        Trial trial1 = trials.get(1);
        assertEquals("vloer", trial1.getWord());
        assertEquals("smoer_1", trial1.getTargetNonWord());
        assertEquals(1, trial1.getNumberOfSyllables());
        assertEquals(TrialCondition.TARGET_ONLY, trial1.getCondition());
        assertEquals(3, trial1.getTrialLength());
        assertEquals("smoer_1", trial1.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("deebral", trial1.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("smoer_2", trial1.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("wijp", trial1.getStimuli().get(3).getStimulus().getLabel());
        assertEquals(2, trial1.getPositionTarget());
        assertEquals("plus10db", trial1.getBandLabel());
        assertEquals(0, trial1.getBandIndex());
        assertEquals(0, trial1.getPositionFoil());

        // "1683;hand;kem_1.wav;1;Target+Foil;5 words;guil.wav;kedlim.wav;sorbuin.wav;kem_2.wav;vep.wav;;4;min6db;2;";
        Trial trial2 = trials.get(1683);
        assertEquals("hand", trial2.getWord());
        assertEquals("kem_1", trial2.getTargetNonWord());
        assertEquals(1, trial2.getNumberOfSyllables());
        assertEquals(TrialCondition.TARGET_AND_FOIL, trial2.getCondition());
        assertEquals(5, trial2.getTrialLength());
        assertEquals("kem_1", trial2.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("guil", trial2.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("kedlim", trial2.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("sorbuin", trial2.getStimuli().get(3).getStimulus().getLabel());
        assertEquals("kem_2", trial2.getStimuli().get(4).getStimulus().getLabel());
        assertEquals("vep", trial2.getStimuli().get(5).getStimulus().getLabel());
        assertEquals(4, trial2.getPositionTarget());
        assertEquals("min6db", trial2.getBandLabel());
        assertEquals(8, trial2.getBandIndex());
        assertEquals(2, trial2.getPositionFoil());

        // "2156;wol;pra.wav;1;NoTarget;6 words;reuwel.wav;wog.wav;consmilp.wav;leskert.wav;mels.wav;dwaat.wav;0;min10db;0;";
        Trial trial3 = trials.get(2156);
        assertEquals("wol", trial3.getWord());
        assertEquals("pra", trial3.getTargetNonWord());
        assertEquals(1, trial3.getNumberOfSyllables());
        assertEquals(TrialCondition.NO_TARGET, trial3.getCondition());
        assertEquals(6, trial3.getTrialLength());
        assertEquals("pra", trial3.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("reuwel", trial3.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("wog", trial3.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("consmilp", trial3.getStimuli().get(3).getStimulus().getLabel());
        assertEquals("leskert", trial3.getStimuli().get(4).getStimulus().getLabel());
        assertEquals("mels", trial3.getStimuli().get(5).getStimulus().getLabel());
        assertEquals("dwaat", trial3.getStimuli().get(6).getStimulus().getLabel());
        assertEquals(0, trial3.getPositionTarget());
        assertEquals("min10db", trial3.getBandLabel());
        assertEquals(10, trial3.getBandIndex());
        assertEquals(0, trial3.getPositionFoil());;
    }

    @Test
    public void testGetStimuliTrialIndex() {

        AudioStimuliFromString instance = new AudioStimuliFromString();
        instance.readTrialsAsCsv(AudioAsStimuliProvider.LABELLING);
        LinkedHashMap<Integer, Trial> trials = instance.getHashedTrials();
        LinkedHashMap<String, Integer> stimuliTrialReference = instance.getStimuliTrialIndex();

        // soundness: a given stimulus is indeed in the declared by the reference trial
        Set<String> stimuliIDs = stimuliTrialReference.keySet();
        for (String stimulusID : stimuliIDs) {
            Integer trialID = stimuliTrialReference.get(stimulusID);
            Trial trial = trials.get(trialID);
            assertTrue(this.trialContainsStimulus(trial, stimulusID));
        }

        // completeness: for every stimulus there is a position in the reference
        Set<Integer> trialIDs = trials.keySet();
        for (Integer trialID : trialIDs) {
            Trial trial = trials.get(trialID);
            ArrayList<BookkeepingStimulus<AudioAsStimulus>> bStimuli = trial.getStimuli();
            for (BookkeepingStimulus<AudioAsStimulus> bStimulus : bStimuli) {
                String stimulusID = bStimulus.getStimulus().getUniqueId();
                assertTrue(stimuliIDs.contains(stimulusID));
            }
        }

    }

    private boolean trialContainsStimulus(Trial trial, String stimulusID) {
        ArrayList<BookkeepingStimulus<AudioAsStimulus>> bStimuli = trial.getStimuli();
        for (BookkeepingStimulus<AudioAsStimulus> bStimulus : bStimuli) {
            String currentStimulusID = bStimulus.getStimulus().getUniqueId();
            if (currentStimulusID.equals(stimulusID)) {
                return true;
            }
        }
        return false;
    }

}
