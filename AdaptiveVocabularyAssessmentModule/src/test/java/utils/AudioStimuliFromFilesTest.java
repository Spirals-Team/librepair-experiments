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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author olhshk
 */
public class AudioStimuliFromFilesTest {
    
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
     * Test of parseInputCSVIntoStringArray method, of class AudioStimuliFromFiles.
     */
    @Ignore
    @Test
    public void testParseInputCSVIntoStringArray() throws Exception {
        System.out.println("parseInputCSVIntoStringArray");
        AudioStimuliFromFiles instance = new AudioStimuliFromFiles();
        ArrayList<String> result = instance.parseInputCSVIntoStringArray(this.filePath);
        for (String row:result) {
            System.out.println(row);
            System.out.println("****************");
            System.out.println("****************");
            System.out.println("****************");
            System.out.println("****************");
            System.out.println("****************");
        }
    }
    
   
 
}
