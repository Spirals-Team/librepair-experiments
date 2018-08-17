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
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
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
public class TrialTupleTest {

    private final AudioStimuliFromString reader = new AudioStimuliFromString();
    private final LinkedHashMap<Integer, Trial> hashedTrials;
    // "1;vloer;smoer_1.wav;1;Target-only;3 words;deebral.wav;smoer_2.wav;wijp.wav;;;;2;plus10db;0;";
    private final Trial trial1;
    //"107;vuur;fjon_1.wav;1;Target+Foil;5 words;fjodschelg.wav;fjon_2.wav;wisdaag.wav;tuik.wav;poks.wav;;2;plus10db;1;\n"
    private final Trial trial2;
    //"190;geel;geek.wav;1;NoTarget;6 words;styk.wav;deiver.wav;spok.wav;lieken.wav;drelg.wav;zesel.wav;0;plus10db;0;\n"
    private final Trial trial3;
    //"167;zoon;zoot.wav;1;NoTarget;4 words;loedes.wav;broep.wav;muip.wav;perlein.wav;;;0;plus10db;0;\n" 
    private final Trial trial4;
    
    private TrialTuple instance;
     private final AudioAsStimuliProvider provider = new AudioAsStimuliProvider(null);
    
    public TrialTupleTest() {
        this.reader.readTrialsAsCsv(this.provider, TestConfigurationConstants.AUDIO_STIMULI_DIR);
        this.hashedTrials = this.reader.getHashedTrials();
        this.trial1 = this.hashedTrials.get(1);
        this.trial2 = this.hashedTrials.get(107);
        this.trial3 = this.hashedTrials.get(190);
        this.trial4 = this.hashedTrials.get(167);
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ArrayList<Trial> trials = new  ArrayList<Trial>(4);
        trials.add(trial1);
        trials.add(trial2);
        trials.add(trial3);
        trials.add(trial4);
        this.instance = new TrialTuple(trials);
    }

    @After
    public void tearDown() {
        this.instance = null;
    }

    /**
     * Test of getCorrectness and setCorrectness method, of class TrialTuple.
     */
    @Test
    public void testGetSetCorrectness() {
        System.out.println("setCorrectness");
        assertNull(this.instance.getCorrectness());  // correctness is not set yet
        this.instance.setCorrectness(true);
        assertTrue(this.instance.getCorrectness());
        this.instance.setCorrectness(false);
        assertFalse(this.instance.getCorrectness());
    }
    
     /**
     * Test of isNotEmpty method, of class TrialTuple.
     */
    @Test
    public void testGetFirstNonusedTrial() {
        System.out.println("getFirstNonusedTrial");
        
        int limit1= this.trial1.getTrialLength()+1;  // Trial 1
        for (int count = 1; count <= limit1; count++) {
            assertEquals(this.trial1, this.instance.getFirstNonusedTrial());
            this.instance.getFirstNonusedTrial().getStimuli().remove(0);
        }
        int limit2= this.trial2.getTrialLength()+1; // Trial 2
        for (int count = 1; count <= limit2; count++) {
            assertEquals(this.trial2, this.instance.getFirstNonusedTrial());
            this.instance.getFirstNonusedTrial().getStimuli().remove(0);
        }
        int limit3= this.trial3.getTrialLength()+1; // Trial 3
        for (int count = 1; count <= limit3; count++) {
            assertEquals(this.trial3, this.instance.getFirstNonusedTrial());
            this.instance.getFirstNonusedTrial().getStimuli().remove(0);
        }
        int limit4= this.trial4.getTrialLength()+1; // Trial 4
        for (int count = 1; count <= limit4; count++) {
            assertEquals(this.trial4, this.instance.getFirstNonusedTrial());
            this.instance.getFirstNonusedTrial().getStimuli().remove(0);
        }
        assertNull(this.instance.getFirstNonusedTrial());
        assertFalse(this.instance.isNotEmpty());
    }

    /**
     * Test of isNotEmpty method, of class TrialTuple.
     */
    @Test
    public void testIsNotEmpty() {
        System.out.println("isNotEmpty");
        int allTogetherStimuli = 3 + 4 + 6 + 4 + 1+1+1+1;
        for (int count = 1; count <= allTogetherStimuli; count++) {
            assertTrue(this.instance.isNotEmpty());
            this.instance.getFirstNonusedTrial().getStimuli().remove(0);
        }
        assertFalse(this.instance.isNotEmpty());
    }

    /**
     * Test of getTrials method, of class TrialTuple.
     */
    @Test
    public void testGetTrials() {
        System.out.println("getTrials");
        ArrayList<Trial> result = this.instance.getTrials();
        assertEquals(4, result.size());
        
        // references should be the same since they have been passed to the constructor
        assertEquals(this.trial1, result.get(0));
        assertEquals(this.trial2, result.get(1));
        assertEquals(this.trial3, result.get(2));
        assertEquals(this.trial4, result.get(3));
        
    }

    /**
     * Test of getNumberOfStimuli method, of class TrialTuple.
     */
    @Test
    public void testGetNumberOfStimuli() {
        System.out.println("getNumberOfStimuli");
        int result = this.instance.getNumberOfStimuli();
        int allTogetherStimuli = 3 + 4 + 6 + 4 + 1+1+1+1;
        assertEquals(allTogetherStimuli, result);
    }

  
    /**
     * Test of removeFirstAvailableStimulus method, of class TrialTuple.
     */
    @Test
    public void testRemoveFirstAvailableStimulus() {
        System.out.println("removeFirstAvailableStimulus");
        int oldSize = this.instance.getNumberOfStimuli();
        
        BookkeepingStimulus<AudioAsStimulus> expResult = this.trial1.getStimuli().get(0);
        BookkeepingStimulus<AudioAsStimulus> result = this.instance.getFirstNonusedTrial().getStimuli().remove(0);
        assertEquals(oldSize-1,this.instance.getNumberOfStimuli());
        assertEquals(expResult, result);
        
        BookkeepingStimulus<AudioAsStimulus> expResult2 = this.trial1.getStimuli().get(0);
        BookkeepingStimulus<AudioAsStimulus> result2 = this.instance.getFirstNonusedTrial().getStimuli().remove(0);
        assertEquals(oldSize-2,this.instance.getNumberOfStimuli());
        assertEquals(expResult2, result2);
        
        BookkeepingStimulus<AudioAsStimulus> expResult3 = this.trial1.getStimuli().get(0);
        BookkeepingStimulus<AudioAsStimulus> result3 = this.instance.getFirstNonusedTrial().getStimuli().remove(0);
        assertEquals(oldSize-3,this.instance.getNumberOfStimuli());
        assertEquals(expResult3, result3);
        
        BookkeepingStimulus<AudioAsStimulus> expResult4 = this.trial1.getStimuli().get(0);
        BookkeepingStimulus<AudioAsStimulus> result4 = this.instance.getFirstNonusedTrial().getStimuli().remove(0);
        assertEquals(oldSize-4,this.instance.getNumberOfStimuli());
        assertEquals(expResult4, result4);
        assertEquals(0, this.trial1.getStimuli().size());
        
        BookkeepingStimulus<AudioAsStimulus> expResult5 = this.trial2.getStimuli().get(0);
        BookkeepingStimulus<AudioAsStimulus> result5 = this.instance.getFirstNonusedTrial().getStimuli().remove(0);
        assertEquals(oldSize-5,this.instance.getNumberOfStimuli());
        assertEquals(expResult5, result5);
        
    }
     
    /**
     * Test of toString method, of class TrialTuple.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        assertEquals("{fields=[trials, correctness], trials=[1, 107, 190, 167], correctness=null}", this.instance.toString());
        this.instance.setCorrectness(true);
        assertEquals("{fields=[trials, correctness], trials=[1, 107, 190, 167], correctness=true}", this.instance.toString());
    }

  

    /**
     * Test of mapToObject method, of class TrialTuple.
     */
    @Test
    public void testMapToObject() {
        System.out.println("mapToObject");
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        String[] flds = {"trials", "correctness"};
        map.put("fields", Arrays.asList(flds));
        map.put("trials", this.instance.getTrials());
        map.put("correctness", true);
        
        TrialTuple result = TrialTuple.mapToObject(map, this.hashedTrials);
        assertTrue(result.getCorrectness());
        for(int  i=0;i<4; i++) {
            assertEquals(this.instance.getTrials().get(i),result.getTrials().get(i));
        }
    }

}
