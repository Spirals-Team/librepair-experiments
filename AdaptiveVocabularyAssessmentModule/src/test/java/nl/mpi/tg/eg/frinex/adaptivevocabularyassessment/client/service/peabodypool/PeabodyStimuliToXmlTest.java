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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.peabodypool;

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
public class PeabodyStimuliToXmlTest {
    
    public static final String STIMULI_DIR_EN = "stimuli/peabody/";
    public static final String STIMULI_DIR_NL = "stimuli/";
    
    public PeabodyStimuliToXmlTest() {
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
     * Test of parseWordsInputCSVString method, of class PeabodyStimuliToXml.
     */
    
    @Test
    public void testParseWordsInputCSVStringEngl() throws Exception{
        System.out.println("parseWordsInputCSVString");
        PeabodyStimuliToXml instance = new PeabodyStimuliToXml();
        String result = instance.parseWordsInputCSVString(STIMULI_DIR_EN, CsvTable.CSV_STRING_ENG);
        
        System.out.println(result);
    }

    @Ignore
    @Test
    public void testParseWordsInputCSVStringNl() throws Exception {
        System.out.println("parseWordsInputCSVString");
        PeabodyStimuliToXml instance = new PeabodyStimuliToXml();
        String result = instance.parseWordsInputCSVString(STIMULI_DIR_NL, CsvTable.CSV_STRING_NL);
        System.out.println(result);
    }
    
}
