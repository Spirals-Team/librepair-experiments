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
public class AudioAsStimulusTest {
    
    private final AudioStimuliFromString reader = new AudioStimuliFromString();
    private final LinkedHashMap<Integer, Trial> trials;
    private final ArrayList<BookkeepingStimulus<AudioAsStimulus>> stimuli;
    private final  AudioAsStimulus instance1;
    private final  AudioAsStimulus instance2;
    private final  AudioAsStimulus instance3;
    private final AudioAsStimuliProvider provider = new AudioAsStimuliProvider(null);
    
    public AudioAsStimulusTest() {
        
        this.reader.readTrialsAsCsv(this. provider, TestConfigurationConstants.AUDIO_STIMULI_DIR);
        this.trials = this.reader.getHashedTrials();
        this.stimuli = trials.get(1).getStimuli();
        this.instance1 = this.stimuli.get(0).getStimulus();
        this.instance2 = this.stimuli.get(1).getStimulus();
        this.instance3 = this.stimuli.get(2).getStimulus();
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
     * Test of getwordType method, of class AudioAsStimulus.
     */
    @Test
    public void testGetwordType() {
        System.out.println("getwordType");
        //"1;vloer;smoer_1.wav;1;Target-only;3 words;deebral.wav;smoer_2.wav;wijp.wav;;;;2;plus10db;0;\n
        assertEquals(WordType.EXAMPLE_TARGET_NON_WORD, this.instance1.getwordType());
        
        assertEquals(WordType.NON_WORD, this.instance2.getwordType());
        
        assertEquals(WordType.TARGET_NON_WORD, this.instance3.getwordType());
    }

  
    /**
     * Test of hasCorrectResponses method, of class AudioAsStimulus.
     */
    @Test
    public void testHasCorrectResponses() {
        System.out.println("hasCorrectResponses");
        System.out.println("getpositionInTrial");
        assertTrue(this.instance1.hasCorrectResponses());
        assertTrue(this.instance2.hasCorrectResponses());
        assertTrue(this.instance3.hasCorrectResponses());
    }

    /**
     * Test of getpositionInTrial method, of class AudioAsStimulus.
     */
    @Test
    public void testGetpositionInTrial() {
        System.out.println("getpositionInTrial");
        assertEquals(0, this.instance1.getpositionInTrial());
        assertEquals(1, this.instance2.getpositionInTrial());
        assertEquals(2, this.instance3.getpositionInTrial());
    }

   
    
}
