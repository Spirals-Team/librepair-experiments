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
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.TestConfigurationConstants;
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
        AudioAsStimuliProvider provider = new AudioAsStimuliProvider(null);
        instance.readTrialsAsCsv(provider, TestConfigurationConstants.AUDIO_STIMULI_DIR);
        LinkedHashMap<Integer, Trial> trials = instance.getHashedTrials();
        Set<Integer> keys = trials.keySet();
        assertEquals(TestConfigurationConstants.N_AUDIO_TRIALS, trials.size());
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
                assertEquals("Trial number " + i, expectedLabel, label);
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
        
        //"499;vuur;fjon_1.wav;1;Target+Foil;5 words;fjodschelg.wav;fjon_2.wav;wisdaag.wav;tuik.wav;poks.wav;;2;plus6db;1;";
        Trial trial12 = trials.get(499);
        assertEquals("vuur", trial12.getWord());
        assertEquals("fjon_1", trial12.getTargetNonWord());
        assertEquals(1, trial12.getNumberOfSyllables());
        assertEquals(TrialCondition.TARGET_AND_FOIL, trial12.getCondition());
        assertEquals(5, trial12.getTrialLength());
        assertEquals("fjon_1", trial12.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("fjodschelg", trial12.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("fjon_2", trial12.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("wisdaag", trial12.getStimuli().get(3).getStimulus().getLabel());
        assertEquals("tuik", trial12.getStimuli().get(4).getStimulus().getLabel());
        assertEquals("poks", trial12.getStimuli().get(5).getStimulus().getLabel());
        assertEquals(2, trial12.getPositionTarget());
        assertEquals("plus6db", trial12.getBandLabel());
        assertEquals(2, trial12.getBandIndex());
        assertEquals(1, trial12.getPositionFoil());

        // 500	kip	kep_1.wav	1	Target+Foil	5 words	vui.wav	kekmieg.wav	kep_2.wav	peek.wav	tukliek.wav		3	plus6db	2
        Trial trial2 = trials.get(500);
        assertEquals("kip", trial2.getWord());
        assertEquals("kep_1", trial2.getTargetNonWord());
        assertEquals(1, trial2.getNumberOfSyllables());
        assertEquals(TrialCondition.TARGET_AND_FOIL, trial2.getCondition());
        assertEquals(5, trial2.getTrialLength());
        assertEquals("kep_1", trial2.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("vui", trial2.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("kekmieg", trial2.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("kep_2", trial2.getStimuli().get(3).getStimulus().getLabel());
        assertEquals("peek", trial2.getStimuli().get(4).getStimulus().getLabel());
        assertEquals("tukliek", trial2.getStimuli().get(5).getStimulus().getLabel());
        assertEquals(3, trial2.getPositionTarget());
        assertEquals("plus6db", trial2.getBandLabel());
        assertEquals(2, trial2.getBandIndex());
        assertEquals(2, trial2.getPositionFoil());
        
         // 999 kers	hers_1.wav	1	Target-only	4 words	geider.wav	hers_2.wav	atgraus.wav	hamp.wav			2	zerodb	0
        Trial trial22 = trials.get(999);
        assertEquals("kers", trial22.getWord());
        assertEquals("hers_1", trial22.getTargetNonWord());
        assertEquals(1, trial22.getNumberOfSyllables());
        assertEquals(TrialCondition.TARGET_ONLY, trial22.getCondition());
        assertEquals(4, trial22.getTrialLength());
        assertEquals("hers_1", trial22.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("geider", trial22.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("hers_2", trial22.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("atgraus", trial22.getStimuli().get(3).getStimulus().getLabel());
        assertEquals("hamp", trial22.getStimuli().get(4).getStimulus().getLabel());
        assertEquals(2, trial22.getPositionTarget());
        assertEquals("zerodb", trial22.getBandLabel());
        assertEquals(5, trial22.getBandIndex());
        assertEquals(0, trial22.getPositionFoil());
        
        /////
        
        // 1000;kurk;skee_1.wav;1;Target-only;4 words;pif.wav;skee_2.wav;wadees.wav;landhoeg.wav;;;2;zerodb;0;
        Trial trial31 = trials.get(1000);
        assertEquals("kurk", trial31.getWord());
        assertEquals("skee_1", trial31.getTargetNonWord());
        assertEquals(1, trial31.getNumberOfSyllables());
        assertEquals(TrialCondition.TARGET_ONLY, trial31.getCondition());
        assertEquals(4, trial31.getTrialLength());
        assertEquals("skee_1", trial31.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("pif", trial31.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("skee_2", trial31.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("wadees", trial31.getStimuli().get(3).getStimulus().getLabel());
        assertEquals("landhoeg", trial31.getStimuli().get(4).getStimulus().getLabel());
        assertEquals(2, trial31.getPositionTarget());
        assertEquals("zerodb", trial31.getBandLabel());
        assertEquals(5, trial31.getBandIndex());
        assertEquals(0, trial31.getPositionFoil());
        
         // 1499;paars;breeg_1.wav;1;Target+Foil;6 words;hoortbijn.wav;zel.wav;grubond.wav;breelmeeg.wav;breeg_2.wav;scherg.wav;5;min4db;4;
        Trial trial32 = trials.get(1499);
        assertEquals("paars", trial32.getWord());
        assertEquals("breeg_1", trial32.getTargetNonWord());
        assertEquals(1, trial32.getNumberOfSyllables());
        assertEquals(TrialCondition.TARGET_AND_FOIL, trial32.getCondition());
        assertEquals(6, trial32.getTrialLength());
        assertEquals("breeg_1", trial32.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("hoortbijn", trial32.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("zel", trial32.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("grubond", trial32.getStimuli().get(3).getStimulus().getLabel());
        assertEquals("breelmeeg", trial32.getStimuli().get(4).getStimulus().getLabel());
        assertEquals("breeg_2", trial32.getStimuli().get(5).getStimulus().getLabel());
        assertEquals("scherg", trial32.getStimuli().get(6).getStimulus().getLabel());
        assertEquals(5, trial32.getPositionTarget());
        assertEquals("min4db", trial32.getBandLabel());
        assertEquals(7, trial32.getBandIndex());
        assertEquals(4, trial32.getPositionFoil());
        
        // 1500;gans;gret_1.wav;1;Target+Foil;6 words;gremdoep.wav;snuim.wav;gret_2.wav;wuil.wav;warnis.wav;winkheek.wav;3;min4db;1;
        Trial trial41 = trials.get(1500);
        assertEquals("gans", trial41.getWord());
        assertEquals("gret_1", trial41.getTargetNonWord());
        assertEquals(1, trial41.getNumberOfSyllables());
        assertEquals(TrialCondition.TARGET_AND_FOIL, trial41.getCondition());
        assertEquals(6, trial41.getTrialLength());
        assertEquals("gret_1", trial41.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("gremdoep", trial41.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("snuim", trial41.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("gret_2", trial41.getStimuli().get(3).getStimulus().getLabel());
        assertEquals("wuil", trial41.getStimuli().get(4).getStimulus().getLabel());
        assertEquals("warnis", trial41.getStimuli().get(5).getStimulus().getLabel());
        assertEquals("winkheek", trial41.getStimuli().get(6).getStimulus().getLabel());
        assertEquals(3, trial41.getPositionTarget());
        assertEquals("min4db", trial41.getBandLabel());
        assertEquals(7, trial41.getBandIndex());
        assertEquals(1, trial41.getPositionFoil());
        
        
        // 1999;helm;hern_1.wav;1;Target-only;5 words;delk.wav;hern_2.wav;wazon.wav;tuip.wav;veser.wav;;2;min10db;0;
        Trial trial42 = trials.get(1999);
        assertEquals("helm", trial42.getWord());
        assertEquals("hern_1", trial42.getTargetNonWord());
        assertEquals(1, trial42.getNumberOfSyllables());
        assertEquals(TrialCondition.TARGET_ONLY, trial42.getCondition());
        assertEquals(5, trial42.getTrialLength());
        assertEquals("hern_1", trial42.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("delk", trial42.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("hern_2", trial42.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("wazon", trial42.getStimuli().get(3).getStimulus().getLabel());
        assertEquals("tuip", trial42.getStimuli().get(4).getStimulus().getLabel());
        assertEquals("veser", trial42.getStimuli().get(5).getStimulus().getLabel());
        assertEquals(2, trial42.getPositionTarget());
        assertEquals("min10db", trial42.getBandLabel());
        assertEquals(10, trial42.getBandIndex());
        assertEquals(0, trial42.getPositionFoil());
        
        //2000;muis;muin_1.wav;1;Target-only;5 words;blan.wav;muin_2.wav;wemp.wav;euzer.wav;knekgel.wav;;2;min10db;0;
        Trial trial51 = trials.get(2000);
        assertEquals("muis", trial51.getWord());
        assertEquals("muin_1", trial51.getTargetNonWord());
        assertEquals(1, trial51.getNumberOfSyllables());
        assertEquals(TrialCondition.TARGET_ONLY, trial51.getCondition());
        assertEquals(5, trial51.getTrialLength());
        assertEquals("muin_1", trial51.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("blan", trial51.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("muin_2", trial51.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("wemp", trial51.getStimuli().get(3).getStimulus().getLabel());
        assertEquals("euzer", trial51.getStimuli().get(4).getStimulus().getLabel());
        assertEquals("knekgel", trial51.getStimuli().get(5).getStimulus().getLabel());
        assertEquals(2, trial51.getPositionTarget());
        assertEquals("min10db", trial51.getBandLabel());
        assertEquals(10, trial51.getBandIndex());
        assertEquals(0, trial51.getPositionFoil());
        
        //2352;wol;pra.wav;1;NoTarget;6 words;reuwel.wav;wog.wav;consmilp.wav;leskert.wav;mels.wav;dwaat.wav;0;min12db;0;
        Trial trial52 = trials.get(2352);
        assertEquals("wol", trial52.getWord());
        assertEquals("pra", trial52.getTargetNonWord());
        assertEquals(1, trial52.getNumberOfSyllables());
        assertEquals(TrialCondition.NO_TARGET, trial52.getCondition());
        assertEquals(6, trial52.getTrialLength());
        assertEquals("pra", trial52.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("reuwel", trial52.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("wog", trial52.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("consmilp", trial52.getStimuli().get(3).getStimulus().getLabel());
        assertEquals("leskert", trial52.getStimuli().get(4).getStimulus().getLabel());
        assertEquals("mels", trial52.getStimuli().get(5).getStimulus().getLabel());
        assertEquals("dwaat", trial52.getStimuli().get(6).getStimulus().getLabel());
        assertEquals(0, trial52.getPositionTarget());
        assertEquals("min12db", trial52.getBandLabel());
        assertEquals(11, trial52.getBandIndex());
        assertEquals(0, trial52.getPositionFoil());
        
        
        //2500;week;woek.wav;1;NoTarget;3 words;vool.wav;jal.wav;tuzi.wav;;;;0;min14db;0;
        Trial trial53 = trials.get(2500);
        assertEquals("week", trial53.getWord());
        assertEquals("woek", trial53.getTargetNonWord());
        assertEquals(1, trial53.getNumberOfSyllables());
        assertEquals(TrialCondition.NO_TARGET, trial53.getCondition());
        assertEquals(3, trial53.getTrialLength());
        assertEquals("woek", trial53.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("vool", trial53.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("jal", trial53.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("tuzi", trial53.getStimuli().get(3).getStimulus().getLabel());
        assertEquals(0, trial53.getPositionTarget());
        assertEquals("min14db", trial53.getBandLabel());
        assertEquals(12, trial53.getBandIndex());
        assertEquals(0, trial53.getPositionFoil());
        
        
        //3000;tank;pank_1.wav;1;Target-only;6 words;alktoord.wav;bawo.wav;pank_2.wav;gam.wav;kel.wav;loofteed.wav;3;min20db;0;
        Trial trial61 = trials.get(3000);
        assertEquals("tank", trial61.getWord());
        assertEquals("pank_1", trial61.getTargetNonWord());
        assertEquals(1, trial61.getNumberOfSyllables());
        assertEquals(TrialCondition.TARGET_ONLY, trial61.getCondition());
        assertEquals(6, trial61.getTrialLength());
        assertEquals("pank_1", trial61.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("alktoord", trial61.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("bawo", trial61.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("pank_2", trial61.getStimuli().get(3).getStimulus().getLabel());
        assertEquals("gam", trial61.getStimuli().get(4).getStimulus().getLabel());
        assertEquals("kel", trial61.getStimuli().get(5).getStimulus().getLabel());
        assertEquals("loofteed", trial61.getStimuli().get(6).getStimulus().getLabel());
        assertEquals(3, trial61.getPositionTarget());
        assertEquals("min20db", trial61.getBandLabel());
        assertEquals(15, trial61.getBandIndex());
        assertEquals(0, trial61.getPositionFoil());
        
        //3136;wol;pra.wav;1;NoTarget;6 words;reuwel.wav;wog.wav;consmilp.wav;leskert.wav;mels.wav;dwaat.wav;0;min20db;0;
        Trial trial72 = trials.get(3136);
        assertEquals("wol", trial72.getWord());
        assertEquals("pra", trial72.getTargetNonWord());
        assertEquals(1, trial53.getNumberOfSyllables());
        assertEquals(TrialCondition.NO_TARGET, trial72.getCondition());
        assertEquals(6, trial72.getTrialLength());
        assertEquals("pra", trial72.getStimuli().get(0).getStimulus().getLabel());
        assertEquals("reuwel", trial72.getStimuli().get(1).getStimulus().getLabel());
        assertEquals("wog", trial72.getStimuli().get(2).getStimulus().getLabel());
        assertEquals("consmilp", trial72.getStimuli().get(3).getStimulus().getLabel());
        assertEquals("leskert", trial72.getStimuli().get(4).getStimulus().getLabel());
        assertEquals("mels", trial72.getStimuli().get(5).getStimulus().getLabel());
        assertEquals("dwaat", trial72.getStimuli().get(6).getStimulus().getLabel());
        assertEquals(0, trial72.getPositionTarget());
        assertEquals("min20db", trial72.getBandLabel());
        assertEquals(15, trial72.getBandIndex());
        assertEquals(0, trial72.getPositionFoil());
    }

    @Test
    public void testGetStimuliTrialIndex() {

        AudioStimuliFromString instance = new AudioStimuliFromString();
        AudioAsStimuliProvider provider = new AudioAsStimuliProvider(null);
        instance.readTrialsAsCsv(provider, TestConfigurationConstants.AUDIO_STIMULI_DIR);
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

    @Test
    public void testReadTrialsLearningAsCsv() {
        System.out.println("readTrialsLearningAsCsv");
        AudioStimuliFromString instance = new AudioStimuliFromString();
         AudioAsStimuliProvider provider = new AudioAsStimuliProvider(null);
        instance.readTrialsAsCsv(provider, TestConfigurationConstants.AUDIO_STIMULI_DIR);
        String[] learningTrialsIDsString = TestConfigurationConstants.AUDIO_LEARNING_TRIALS.split(",");
        ArrayList<Integer> learningTrialsIDs = new ArrayList<Integer>(TestConfigurationConstants.AUDIO_N_LEARNING_TRIALS);
        for (String idStr : learningTrialsIDsString) {
            learningTrialsIDs.add(Integer.parseInt(idStr.trim()));
        }
        instance.prepareLearningTrialsAsCsv(learningTrialsIDs);
        LinkedHashMap<Integer, Trial> trials = instance.getHashedLearningTrials();
        assertEquals(TestConfigurationConstants.AUDIO_N_LEARNING_TRIALS, trials.size());
        
        
       
        // "1000;kurk;skee_1.wav;1;Target-only;4 words;pif.wav;skee_2.wav;wadees.wav;landhoeg.wav;;;2;zerodb;0;";
        Trial trial1 = trials.get(learningTrialsIDs.get(0));
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
        Trial trial2 = trials.get(learningTrialsIDs.get(1));
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
        Trial trial3 = trials.get(learningTrialsIDs.get(2));
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
}
