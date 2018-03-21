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

import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.AdVocAsStimuliProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author olhshk
 */
public class UtilsIOTest {
    
    public UtilsIOTest() {
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
     * Test of writeCsvFileFastTrack method, of class UtilsIO.
     */
    @Ignore @Test
    public void testWriteCsvFileFastTrack() throws Exception {
        System.out.println("writeCsvFileFastTrack");
        AdVocAsStimuliProvider provider = null;
        int stopBand = 0;
        String outputDir = "";
        int averageNonWordPosition = 0;
        int nonWordsPerBlock = 0;
        UtilsIO.writeCsvFileFastTrack(provider, stopBand, outputDir, averageNonWordPosition, nonWordsPerBlock);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeHtmlFileFastTrack method, of class UtilsIO.
     */
    @Ignore @Test
    public void testWriteHtmlFileFastTrack() throws Exception {
        System.out.println("writeHtmlFileFastTrack");
        AdVocAsStimuliProvider provider = null;
        int stopBand = 0;
        String outputDir = "";
        int averageNonWordPosition = 0;
        int nonWordsPerBlock = 0;
        UtilsIO.writeHtmlFileFastTrack(provider, stopBand, outputDir, averageNonWordPosition, nonWordsPerBlock);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeCsvFileFineTuningHistory method, of class UtilsIO.
     */
    @Ignore @Test
    public void testWriteCsvFileFineTuningHistory() throws Exception {
        System.out.println("writeCsvFileFineTuningHistory");
        AdVocAsStimuliProvider provider = null;
        String outputDir = "";
        String fileName = "";
        UtilsIO.writeCsvFileFineTuningHistory(provider, outputDir, fileName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeHtmlFileFineTuningHistory method, of class UtilsIO.
     */
    @Ignore @Test
    public void testWriteHtmlFileFineTuningHistory() throws Exception {
        System.out.println("writeHtmlFileFineTuningHistory");
        AdVocAsStimuliProvider provider = null;
        String outputDir = "";
        String fileName = "";
        UtilsIO.writeHtmlFileFineTuningHistory(provider, outputDir, fileName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeHtmlFullUserResults method, of class UtilsIO.
     */
    @Ignore @Test
    public void testWriteHtmlFullUserResults() throws Exception {
        System.out.println("writeHtmlFullUserResults");
        AdVocAsStimuliProvider provider = null;
        String outputDir = "";
        String fileName = "";
        UtilsIO.writeHtmlFullUserResults(provider, outputDir, fileName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeCsvMapAsOneCsv method, of class UtilsIO.
     */
    @Ignore @Test
    public void testWriteCsvMapAsOneCsv() throws Exception {
        System.out.println("writeCsvMapAsOneCsv");
        AdVocAsStimuliProvider provider = null;
        String outputDir = "";
        String fileName = "";
        UtilsIO.writeCsvMapAsOneCsv(provider, outputDir, fileName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
