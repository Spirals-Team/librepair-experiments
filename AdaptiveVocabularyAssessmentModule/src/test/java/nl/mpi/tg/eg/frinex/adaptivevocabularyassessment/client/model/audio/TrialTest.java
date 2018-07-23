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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BookkeepingStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.AudioAsStimuliProvider;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.audiopool.AudioStimuliFromString;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.audiopool.Indices;
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
public class TrialTest {

    private final AudioStimuliFromString reader = new AudioStimuliFromString();
    private final LinkedHashMap<Integer, Trial> hashedTrials;
    // "1;vloer;smoer_1.wav;1;Target-only;3 words;deebral.wav;smoer_2.wav;wijp.wav;;;;2;plus10db;0;";
    private final Trial trial1;
    // "1683;hand;kem_1.wav;1;Target+Foil;5 words;guil.wav;kedlim.wav;sorbuin.wav;kem_2.wav;vep.wav;;4;min6db;2;";
    private final Trial trial2;
    // "2156;wol;pra.wav;1;NoTarget;6 words;reuwel.wav;wog.wav;consmilp.wav;leskert.wav;mels.wav;dwaat.wav;0;min10db;0;";
    private final Trial trial3;
    private final AudioAsStimuliProvider provider = new AudioAsStimuliProvider(null);

    public TrialTest() {
        this.reader.readTrialsAsCsv(this.provider, TestConfigurationConstants.AUDIO_STIMULI_DIR);
        this.hashedTrials = this.reader.getHashedTrials();
        this.trial1 = this.hashedTrials.get(1);
        this.trial2 = this.hashedTrials.get(1683);
        this.trial3 = this.hashedTrials.get(2156);
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
     * Test of getStimuliList method, of class Trial.
     */
    @Test
    public void testGetStimuliList() {
        System.out.println("getStimuliList");

        ArrayList<BookkeepingStimulus<AudioAsStimulus>> stimuli = trial1.getStimuli();

        //"1;vloer;smoer_1.wav;1;Target-only;3 words;deebral.wav;smoer_2.wav;wijp.wav;;;;2;plus10db;0;\n" 
        assertEquals(4, stimuli.size());
        assertEquals("smoer_1", stimuli.get(0).getStimulus().getLabel());
        assertEquals("deebral", stimuli.get(1).getStimulus().getLabel());
        assertEquals("smoer_2", stimuli.get(2).getStimulus().getLabel());
        assertEquals("wijp", stimuli.get(3).getStimulus().getLabel());

        assertEquals(0, trial1.getBandIndex());
        assertEquals("plus10db", trial1.getBandLabel());
        assertEquals(TrialCondition.TARGET_ONLY, trial1.getCondition());
        assertEquals(1, trial1.getId());
        assertEquals(1, trial1.getNumberOfSyllables());
        assertEquals(0, trial1.getPositionFoil());
        assertEquals(2, trial1.getPositionTarget());
    }

    /**
     * Test of getWord method, of class Trial.
     */
    @Test
    public void testGetWord() {
        System.out.println("getWord");
        assertEquals("vloer", this.trial1.getWord());
        assertEquals("hand", this.trial2.getWord());
        assertEquals("wol", this.trial3.getWord());
    }

    /**
     * Test of getBandNumber method, of class Trial.
     */
    @Test
    public void testGetBandIndex() {
        System.out.println("getBandIndex");
        assertEquals(0, this.trial1.getBandIndex());
        assertEquals(8, this.trial2.getBandIndex());
        assertEquals(10, this.trial3.getBandIndex());
    }

    @Test
    public void testGetBandLabel() {
        System.out.println("getBandLabel");
        assertEquals("plus10db", this.trial1.getBandLabel());
        assertEquals("min6db", this.trial2.getBandLabel());
        assertEquals("min10db", this.trial3.getBandLabel());
    }

    /**
     * Test of getTargetNonWord method, of class Trial.
     */
    @Test
    public void testGetTargetNonWord() {
        System.out.println("getTargetNonWord");
        assertEquals("smoer_1", this.trial1.getTargetNonWord());
        assertEquals("kem_1", this.trial2.getTargetNonWord());
        assertEquals("pra", this.trial3.getTargetNonWord());
    }

    /**
     * Test of getNumberOfSyllables method, of class Trial.
     */
    @Test
    public void testGetNumberOfSyllables() {
        System.out.println("getNumberOfSyllables");
        assertEquals(1, this.trial1.getNumberOfSyllables());
        assertEquals(1, this.trial2.getNumberOfSyllables());
        assertEquals(1, this.trial3.getNumberOfSyllables());
    }

    /**
     * Test of getCondition method, of class Trial.
     */
    @Test
    public void testGetCondition() {
        System.out.println("getCondition");
        assertEquals(TrialCondition.TARGET_ONLY, this.trial1.getCondition());
        assertEquals(TrialCondition.TARGET_AND_FOIL, this.trial2.getCondition());
        assertEquals(TrialCondition.NO_TARGET, this.trial3.getCondition());
    }

    /**
     * Test of getTrialLength method, of class Trial.
     */
    @Test
    public void testGetTrialLength() {
        System.out.println("getTrialLength");
        assertEquals(3, this.trial1.getTrialLength());
        assertEquals(5, this.trial2.getTrialLength());
        assertEquals(6, this.trial3.getTrialLength());

        for (int i = 1; i <= this.hashedTrials.size(); i++) {
            assertTrue(this.hashedTrials.get(i).getTrialLength() >= 1);
        }
    }

    /**
     * Test of getStimuli method, of class Trial.
     */
    @Test
    public void testGetStimuli() {
        System.out.println("getStimuli");
        // "1683;hand;kem_1.wav;1;Target+Foil;5 words;guil.wav;kedlim.wav;sorbuin.wav;kem_2.wav;vep.wav;;4;min6db;2;";
        ArrayList<BookkeepingStimulus<AudioAsStimulus>> result = this.trial2.getStimuli();
        assertEquals(1 + this.trial2.getTrialLength(), result.size());
        assertEquals("kem_1", result.get(0).getStimulus().getLabel());
        assertEquals("guil", result.get(1).getStimulus().getLabel());
        assertEquals("kedlim", result.get(2).getStimulus().getLabel());
        assertEquals("sorbuin", result.get(3).getStimulus().getLabel());
        assertEquals("kem_2", result.get(4).getStimulus().getLabel());
        assertEquals("vep", result.get(5).getStimulus().getLabel());

        assertEquals(TestConfigurationConstants.AUDIO_STIMULI_DIR+"clear_mono/kem_1", result.get(0).getStimulus().getAudio());
        assertEquals(TestConfigurationConstants.AUDIO_STIMULI_DIR + Indices.BAND_LABEL_TO_DIRNAME.get(this.trial2.getBandLabel()) + "/guil_-6", result.get(1).getStimulus().getAudio());
        assertEquals(TestConfigurationConstants.AUDIO_STIMULI_DIR + Indices.BAND_LABEL_TO_DIRNAME.get(this.trial2.getBandLabel()) + "/kedlim_-6", result.get(2).getStimulus().getAudio());
        assertEquals(TestConfigurationConstants.AUDIO_STIMULI_DIR + Indices.BAND_LABEL_TO_DIRNAME.get(this.trial2.getBandLabel()) + "/sorbuin_-6", result.get(3).getStimulus().getAudio());
        assertEquals(TestConfigurationConstants.AUDIO_STIMULI_DIR + Indices.BAND_LABEL_TO_DIRNAME.get(this.trial2.getBandLabel()) + "/kem_2_-6", result.get(4).getStimulus().getAudio());
        assertEquals(TestConfigurationConstants.AUDIO_STIMULI_DIR + Indices.BAND_LABEL_TO_DIRNAME.get(this.trial2.getBandLabel()) + "/vep_-6", result.get(5).getStimulus().getAudio());

        assertEquals(1 + this.trial1.getTrialLength(), this.trial1.getStimuli().size());
        AudioAsStimulus cue = this.trial1.getStimuli().get(0).getStimulus();
        assertEquals(TestConfigurationConstants.AUDIO_STIMULI_DIR+"clear_mono/" + cue.getLabel(), cue.getAudio());
        for (int i = 1; i < this.trial1.getStimuli().size(); i++) {
            AudioAsStimulus stimulus = this.trial1.getStimuli().get(i).getStimulus();
            String bandLabel = this.trial1.getBandLabel();
            String filename = stimulus.getLabel() + "_" + Indices.BAND_LABEL_TO_INTEGER.get(bandLabel);
            assertEquals(TestConfigurationConstants.AUDIO_STIMULI_DIR + Indices.BAND_LABEL_TO_DIRNAME.get(bandLabel) + "/" + filename, stimulus.getAudio());
        }

        assertEquals(1 + this.trial3.getTrialLength(), this.trial3.getStimuli().size());
        AudioAsStimulus cue3 = this.trial3.getStimuli().get(0).getStimulus();
        assertEquals(TestConfigurationConstants.AUDIO_STIMULI_DIR+"clear_mono/" + cue3.getLabel(), cue3.getAudio());
        for (int i = 1; i < this.trial3.getStimuli().size(); i++) {
            AudioAsStimulus stimulus = this.trial3.getStimuli().get(i).getStimulus();
            String bandLabel = this.trial3.getBandLabel();
            String filename = stimulus.getLabel() + "_" + Indices.BAND_LABEL_TO_INTEGER.get(bandLabel);
            assertEquals(TestConfigurationConstants.AUDIO_STIMULI_DIR + Indices.BAND_LABEL_TO_DIRNAME.get(bandLabel) + "/" + filename, stimulus.getAudio());
        }

    }

    /**
     * Test of getId method, of class Trial.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        for (int i = 1; i <= this.hashedTrials.size(); i++) {
            assertEquals(i, this.hashedTrials.get(i).getId());
        }
    }

    /**
     * Test of getPositionTarget method, of class Trial.
     */
    @Test
    public void testGetPositionTarget() {
        System.out.println("getPositionTarget");
        assertEquals(2, this.trial1.getPositionTarget());
        assertEquals(4, this.trial2.getPositionTarget());
        assertEquals(0, this.trial3.getPositionTarget());
    }

    /**
     * Test of getPositionFoil method, of class Trial.
     */
    @Test
    public void testGetPositionFoil() {
        System.out.println("getPositionFoil");
        assertEquals(0, this.trial1.getPositionFoil());
        assertEquals(2, this.trial2.getPositionFoil());
        assertEquals(0, this.trial3.getPositionFoil());
    }

    /**
     * Test of prepareTrialMatrix method, of class Trial.
     */
    @Test
    public void testPrepareTrialMatrix() {
        System.out.println("prepareTrialMatrix");
        ArrayList<ArrayList<LinkedHashMap<TrialCondition, ArrayList<Trial>>>> result = Trial.prepareTrialMatrix(this.hashedTrials, 
                TestConfigurationConstants.AUDIO_NUMBER_OF_BANDS, 
                TestConfigurationConstants.AUDIO_MAX_LENGTH);
        int count = 0;
        for (int i = 0; i < TestConfigurationConstants.AUDIO_NUMBER_OF_BANDS; i++) {
            ArrayList<LinkedHashMap<TrialCondition, ArrayList<Trial>>> trialInBand = result.get(i);

            assertEquals(0, trialInBand.get(0).get(TrialCondition.NO_TARGET).size()); // there must be no trails of length 0
            assertEquals(0, trialInBand.get(0).get(TrialCondition.TARGET_AND_FOIL).size());
            assertEquals(0, trialInBand.get(0).get(TrialCondition.TARGET_ONLY).size());

            for (int j = 0; j < trialInBand.size(); j++) {
                LinkedHashMap<TrialCondition, ArrayList<Trial>> trailInBandOfLength = trialInBand.get(j);
                assertEquals(TrialCondition.values().length, trailInBandOfLength.size());
                for (TrialCondition tc : TrialCondition.values()) {
                    ArrayList<Trial> trialslOfCondition = trailInBandOfLength.get(tc);
                    for (Trial trial : trialslOfCondition) {
                        count++;
                        assertEquals(i, trial.getBandIndex());
                        assertEquals(new Integer(i), Indices.BAND_LABEL_TO_INDEX.get(trial.getBandLabel()));
                        assertEquals(j, trial.getTrialLength());
                        assertEquals(tc, trial.getCondition());
                    }
                }
            }
        }
        assertEquals(count, this.hashedTrials.size());
    }

    /**
     * Test of toString method, of class Trial.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        for (int i = 1; i <= this.hashedTrials.size(); i++) {
            Integer id = this.hashedTrials.get(i).getId();
            assertEquals(id.toString(), this.hashedTrials.get(i).toString());
        }
    }

}
