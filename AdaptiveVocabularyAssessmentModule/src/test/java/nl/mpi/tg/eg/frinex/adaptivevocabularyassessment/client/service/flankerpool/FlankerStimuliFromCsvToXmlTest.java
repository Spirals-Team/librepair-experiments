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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.flankerpool;

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
public class FlankerStimuliFromCsvToXmlTest {
    
    public FlankerStimuliFromCsvToXmlTest() {
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
     * Test of parseWordsInputCSVStringToXml method, of class FlankerStimuliFromCsvToXml.
     */
    @Test
    public void testParseWordsInputCSVStringToXmlPractice() throws Exception {
        System.out.println("parseWordsInputCSVStringToXml_Practice");
        FlankerStimuliFromCsvToXml instance = new FlankerStimuliFromCsvToXml();
        String resultPractice = instance.parseWordsInputCSVStringToXml(CsvTablesFlanker.CSV_STRING_PRACTICE, "flanker" , "Practice");
        assertTrue(resultPractice.startsWith("<stimulus "));
        assertTrue(resultPractice.endsWith(" />\n"));
        System.out.println(resultPractice);
    }
    
     /**
     * Test of parseWordsInputCSVStringToXml method, of class FlankerStimuliFromCsvToXml.
     */
    @Test
    public void testParseWordsInputCSVStringToXmlRealDeal_RealDelal() throws Exception {
        System.out.println("parseWordsInputCSVStringToXml");
        FlankerStimuliFromCsvToXml instance = new FlankerStimuliFromCsvToXml();
        String resultPractice = instance.parseWordsInputCSVStringToXml(CsvTablesFlanker.CSV_STRING_REAL_DEAL, "flanker" , "RealDeal");
        assertTrue(resultPractice.startsWith("<stimulus "));
        assertTrue(resultPractice.endsWith(" />\n"));
        System.out.println(resultPractice);
    }
    
}
