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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.vocabulary;

import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.advocaspool.Vocabulary;
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
public class AdVocAsStimulusTest {
    
    public AdVocAsStimulusTest() {
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

    /*
     * Test of getBandNumber method, of class AdVocAsStimulus.
     */
    @Test
    public void testGetBandNumber() {
        System.out.println("getBandNumber");
        AdVocAsStimulus instance = new AdVocAsStimulusImpl1();
        int result = instance.getBandNumber();
        assertEquals(20, result);
    }

    public class AdVocAsStimulusImpl1 extends AdVocAsStimulus {

        //AdVocAsStimulus(String uniqueId, Tag[] tags, String label, String code, int pauseMs, String audioPath, String videoPath, String imagePath, String ratingLabels, String correctResponses)
        public AdVocAsStimulusImpl1() {
            super("abc_000", new Tag[1], "abc", "", 0, null, null, null, Vocabulary.NONWORD+","+Vocabulary.WORD, Vocabulary.NONWORD);
        }
        
        @Override
        public String getbandIndex(){
            return "19";
        }
        
         @Override
        public String getbandLabel(){
            return "20";
        }
    }
    
}
