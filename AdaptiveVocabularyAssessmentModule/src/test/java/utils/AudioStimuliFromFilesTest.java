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
package utils;

import java.util.ArrayList;
import java.util.HashMap;
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
public class AudioStimuliFromFilesTest {
    
    String[] labelling ={"min10db", "min8db", "min6db", "min4db", "min2db", "zerodb", "plus2db", "plus4db", "plus6db", "plus8db", "plus10db"};
    String filePath = "/Users/olhshk/Documents/ExperimentTemplate/AdaptiveVocabularyAssessmentModule/src/test/java/utils/Stimuli_NonwordMonitoring_working.csv";
    
    public AudioStimuliFromFilesTest() {
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
     * Test of parseTrialsInputCSV method, of class AudioStimuliFromFiles.
     */
    @Test
    public void testParseTrialsInputCSV() throws Exception {
        System.out.println("parseTrialsInputCSV");
        ArrayList<String> fileNameExtensions = new ArrayList<String>(1);
        fileNameExtensions.add("wav");
        HashMap<String, String> bandIndexing = new HashMap<String, String>();
        for (int i=0; i<labelling.length; i++){
           bandIndexing.put(labelling[i], (new Integer(i)).toString());
        }
        
        
        
        AudioStimuliFromFiles instance = new AudioStimuliFromFiles();
        String result = instance.parseTrialsInputCSV(this.filePath, fileNameExtensions, bandIndexing);
        System.out.println(result);
        assertTrue(result.startsWith("<stimulus "));
        assertTrue(result.endsWith("/> \n"));
    }

    /**
     * Test of removeFileNameExtensions method, of class AudioStimuliFromFiles.
     */
    @Test
    public void testRenoveFileNameExtensions() {
        System.out.println("renoveFileNameExtensions");
        String fileName = "rhabarber.wav";
        ArrayList<String> fileNameExtensions = new ArrayList<String>(1);
        fileNameExtensions.add("wav");
        AudioStimuliFromFiles instance = new AudioStimuliFromFiles();
        String result = instance.removeFileNameExtensions(fileName, fileNameExtensions);
        assertEquals("rhabarber", result);
    }

 
}
