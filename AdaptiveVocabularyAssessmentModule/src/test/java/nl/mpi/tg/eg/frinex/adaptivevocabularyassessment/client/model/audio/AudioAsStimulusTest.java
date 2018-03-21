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
    
    public AudioAsStimulusTest() {
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
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        String result = instance.getwordType();
        WordType result1 = WordType.valueOf(result);
        assertEquals(WordType.TARGET_NON_WORD.toString(), result);
        assertEquals(WordType.TARGET_NON_WORD, result1);
    }

    /**
     * Test of getpositionInTrial method, of class AudioAsStimulus.
     */
    @Test
    public void testGetpositionInTrial() {
        System.out.println("getpositionInTrial");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        String result = instance.getpositionInTrial();
        assertEquals("2", result);
    }

    /**
     * Test of getbandIndex method, of class AudioAsStimulus.
     */
    @Test
    public void testGetbandIndex() {
        System.out.println("getbandIndex");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        String expResult = "5";
        String result = instance.getbandIndex();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWordTypeWT method, of class AudioAsStimulus.
     */
    @Test
    public void testGetWordTypeWT() {
        System.out.println("getWordTypeWT");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        WordType result = instance.getWordTypeWT();
        assertEquals(WordType.TARGET_NON_WORD, result);
    }

    /**
     * Test of gettrialNumber method, of class AudioAsStimulus.
     */
    @Test
    public void testGettrialNumber() {
        System.out.println("gettrialNumber");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        String expResult = "3";
        String result = instance.gettrialNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of gettrialWord method, of class AudioAsStimulus.
     */
    @Test
    public void testGettrialWord() {
        System.out.println("gettrialWord");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        String expResult = "baaf";
        String result = instance.gettrialWord();
        assertEquals(expResult, result);
    }

    /**
     * Test of gettrialCueFile method, of class AudioAsStimulus.
     */
    @Test
    public void testGettrialCueFile() {
        System.out.println("gettrialCueFile");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        String expResult = "baf.ogg";
        String result = instance.gettrialCueFile();
        assertEquals(expResult, result);
    }

    /**
     * Test of gettrialSyllables method, of class AudioAsStimulus.
     */
    @Test
    public void testGettrialSyllables() {
        System.out.println("gettrialSyllables");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        String expResult = "1";
        String result = instance.gettrialSyllables();
        assertEquals(expResult, result);
    }

    /**
     * Test of gettrialCondition method, of class AudioAsStimulus.
     */
    @Test
    public void testGettrialCondition() {
        System.out.println("gettrialCondition");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        String expResult = TrialCondition.TARGET_ONLY.toString();
        String result = instance.gettrialCondition();
        assertEquals(expResult, result);
    }

    /**
     * Test of gettrialLength method, of class AudioAsStimulus.
     */
    @Test
    public void testGettrialLength() {
        System.out.println("gettrialLength");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        String expResult = "4";
        String result = instance.gettrialLength();
        assertEquals(expResult, result);
    }

    /**
     * Test of gettrialPositionTarget method, of class AudioAsStimulus.
     */
    @Test
    public void testGettrialPositionTarget() {
        System.out.println("gettrialPositionTarget");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        String expResult = "2";
        String result = instance.gettrialPositionTarget();
        assertEquals(expResult, result);
    }

    /**
     * Test of gettrialPositionFoil method, of class AudioAsStimulus.
     */
    @Test
    public void testGettrialPositionFoil() {
        System.out.println("gettrialPositionFoil");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        String expResult = "0";
        String result = instance.gettrialPositionFoil();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPositionInTrialInt method, of class AudioAsStimulus.
     */
    @Test
    public void testGetPositionInTrialInt() {
        System.out.println("getPositionInTrialInt");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        int expResult = 2;
        int result = instance.getPositionInTrialInt();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTrialNumberInt method, of class AudioAsStimulus.
     */
    @Test
    public void testGetTrialNumberInt() {
        System.out.println("getTrialNumberInt");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        int expResult = 3;
        int result = instance.getTrialNumberInt();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTrialSyllablesInt method, of class AudioAsStimulus.
     */
    @Test
    public void testGetTrialSyllablesInt() {
        System.out.println("getTrialSyllablesInt");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        int expResult = 1;
        int result = instance.getTrialSyllablesInt();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTrialLengthInt method, of class AudioAsStimulus.
     */
    @Test
    public void testGetTrialLengthInt() {
        System.out.println("getTrialLengthInt");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        int expResult = 4;
        int result = instance.getTrialLengthInt();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTrialPositionTargetInt method, of class AudioAsStimulus.
     */
    @Test
    public void testGetTrialPositionTargetInt() {
        System.out.println("getTrialPositionTargetInt");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        int expResult = 2;
        int result = instance.getTrialPositionTargetInt();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTrialPositionFoilInt method, of class AudioAsStimulus.
     */
    @Test
    public void testGetTrialPositionFoilInt() {
        System.out.println("getTrialPositionFoilInt");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        int expResult = 0;
        int result = instance.getTrialPositionFoilInt();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTrialConditionTC method, of class AudioAsStimulus.
     */
    @Test
    public void testGetTrialConditionTC() {
        System.out.println("getTrialConditionTC");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        TrialCondition expResult = TrialCondition.TARGET_ONLY;
        TrialCondition result = instance.getTrialConditionTC();
        assertEquals(expResult, result);
    }

    /**
     * Test of hasCorrectResponses method, of class AudioAsStimulus.
     */
    @Test
    public void testHasCorrectResponses() {
        System.out.println("hasCorrectResponses");
        AudioAsStimulusImpl instance = new AudioAsStimulusImpl();
        boolean expResult = true;
        boolean result = instance.hasCorrectResponses();
        assertEquals(expResult, result);
    }

    public class AudioAsStimulusImpl extends AudioAsStimulus {

        //AudioAsStimulusImpl(String uniqueId, Tag[] tags, String label, String code, int pauseMs, String audioPath, String videoPath, String imagePath, String ratingLabels, String correctResponses) {
        
       

        public AudioAsStimulusImpl() {
            super("baf_0001", new Tag[1], "baf_1", "", 900, "", "", "", "", "");
        }

        public String getwordType() {
            WordType retVal1 = WordType.TARGET_NON_WORD;
            String check = retVal1.TARGET_NON_WORD.toString();
            return check;
            //return WordType.TARGET_NON_WORD.toString();
        }

        public String getpositionInTrial() {
            return "2";
        }

        public String getbandIndex() {
            return "5";
        }
        
        public String getbandLabel() {
            return "6dB";
        }

        public String gettrialNumber() {
            return "3";
        }

        public String gettrialWord() {
            return "baaf";
        }

        public String gettrialCueFile() {
            return "baf.ogg";
        }

        public String gettrialSyllables() {
            return "1";
        }

        public String gettrialCondition() {
            return TrialCondition.TARGET_ONLY.toString();
        }

        public String gettrialLength() {
            return "4";
        }

        public String gettrialPositionTarget() {
            return "2";
        }

        public String gettrialPositionFoil() {
            return "0";
        }
        
        public String gettrialTargetNonword() {
            return null;
        }
    }

   
    
}
