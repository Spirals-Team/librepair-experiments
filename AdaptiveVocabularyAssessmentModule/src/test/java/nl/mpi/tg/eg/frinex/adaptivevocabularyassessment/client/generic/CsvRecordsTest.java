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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
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
public class CsvRecordsTest {

    public CsvRecordsTest() {
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
     * Test of readRecords method, of class CsvRecords.
     */
    @Test
    public void testReadRecords() throws Exception {
        System.out.println("readRecords");
        String rawString = "Nr;Word;Target_nonword;Syllables;Condition;Length_list;Word1;Word2;Word3;Word4;Word5;Word6;Position_target;Noise_level;Position_foil;\n"
                + "1;vloer;smoer_1.wav;1;Target-only;3 words;deebral.wav;smoer_2.wav;wijp.wav;;;;2;plus10db;0;\n"
                + "2;pauw;paud_1.wav;1;Target-only;3 words;rolscheegt.wav;paud_2.wav;staap.wav;;;;2;plus10db;0;\n"
                + "3;krik;grik_1.wav;1;Target-only;3 words;spank.wav;grik_2.wav;pintein.wav;;;;2;plus10db;0;\n"
                + "4;schelp;schess_1.wav;1;Target-only;3 words;rim.wav;schess_2.wav;werelglal.wav;;;;2;plus10db;0;\n";
        CsvRecords instance = new CsvRecords(null, ";", "\n");
        instance.readRecords(rawString);
        ArrayList<LinkedHashMap<String, String>> result = instance.getRecords();
        
        assertEquals(4,result.size());
        
        for (LinkedHashMap<String, String> record:result) {
            Set<String> header = record.keySet();
            assertEquals(15, header.size());
        }
        
        assertEquals("1", result.get(0).get("Nr"));
        assertEquals("vloer", result.get(0).get("Word"));
        assertEquals("smoer_1.wav", result.get(0).get("Target_nonword"));
        assertEquals("1", result.get(0).get("Syllables"));
        assertEquals("Target-only", result.get(0).get("Condition"));
        assertEquals("3 words", result.get(0).get("Length_list"));
        assertEquals("deebral.wav", result.get(0).get("Word1"));
        assertEquals("smoer_2.wav", result.get(0).get("Word2"));
        assertEquals("wijp.wav", result.get(0).get("Word3"));
        assertEquals("", result.get(0).get("Word4"));
        assertEquals("", result.get(0).get("Word5"));
        assertEquals("", result.get(0).get("Word6"));
        assertEquals("2", result.get(0).get("Position_target"));
        assertEquals("plus10db", result.get(0).get("Noise_level"));
        assertEquals("0", result.get(0).get("Position_foil"));
        
        assertEquals("2", result.get(1).get("Nr"));
        assertEquals("pauw", result.get(1).get("Word"));
        assertEquals("paud_1.wav", result.get(1).get("Target_nonword"));
        assertEquals("1", result.get(1).get("Syllables"));
        assertEquals("Target-only", result.get(1).get("Condition"));
        assertEquals("3 words", result.get(1).get("Length_list"));
        assertEquals("rolscheegt.wav", result.get(1).get("Word1"));
        assertEquals("paud_2.wav", result.get(1).get("Word2"));
        assertEquals("staap.wav", result.get(1).get("Word3"));
        assertEquals("", result.get(1).get("Word4"));
        assertEquals("", result.get(1).get("Word5"));
        assertEquals("", result.get(1).get("Word6"));
        assertEquals("2", result.get(1).get("Position_target"));
        assertEquals("plus10db", result.get(1).get("Noise_level"));
        assertEquals("0", result.get(1).get("Position_foil"));
        
        assertEquals("4", result.get(3).get("Nr"));
        assertEquals("schelp", result.get(3).get("Word"));
        assertEquals("schess_1.wav", result.get(3).get("Target_nonword"));
        assertEquals("1", result.get(3).get("Syllables"));
        assertEquals("Target-only", result.get(3).get("Condition"));
        assertEquals("3 words", result.get(3).get("Length_list"));
        assertEquals("rim.wav", result.get(3).get("Word1"));
        assertEquals("schess_2.wav", result.get(3).get("Word2"));
        assertEquals("werelglal.wav", result.get(3).get("Word3"));
        assertEquals("", result.get(3).get("Word4"));
        assertEquals("", result.get(3).get("Word5"));
        assertEquals("", result.get(3).get("Word6"));
        assertEquals("2", result.get(3).get("Position_target"));
        assertEquals("plus10db", result.get(3).get("Noise_level"));
        assertEquals("0", result.get(3).get("Position_foil"));
    }

     @Test
    public void testReadRecordsHeaderSeparate() throws Exception {
        System.out.println("readRecords");
        String headerString = "Nr;Word;Target_nonword;Syllables;Condition;Length_list;Word1;Word2;Word3;Word4;Word5;Word6;Position_target;Noise_level;Position_foil";
        String rawString =  "1;vloer;smoer_1.wav;1;Target-only;3 words;deebral.wav;smoer_2.wav;wijp.wav;;;;2;plus10db;0;\n"
                + "2;pauw;paud_1.wav;1;Target-only;3 words;rolscheegt.wav;paud_2.wav;staap.wav;;;;2;plus10db;0;\n"
                + "3;krik;grik_1.wav;1;Target-only;3 words;spank.wav;grik_2.wav;pintein.wav;;;;2;plus10db;0;\n"
                + "4;schelp;schess_1.wav;1;Target-only;3 words;rim.wav;schess_2.wav;werelglal.wav;;;;2;plus10db;0;\n";
        CsvRecords instance = new CsvRecords(headerString.split(";"), ";", "\n");
        instance.readRecords(rawString);
        ArrayList<LinkedHashMap<String, String>> result = instance.getRecords();
        
        assertEquals(4,result.size());
        
        for (LinkedHashMap<String, String> record:result) {
            Set<String> header = record.keySet();
            assertEquals(15, header.size());
        }
        
        assertEquals("1", result.get(0).get("Nr"));
        assertEquals("vloer", result.get(0).get("Word"));
        assertEquals("smoer_1.wav", result.get(0).get("Target_nonword"));
        assertEquals("1", result.get(0).get("Syllables"));
        assertEquals("Target-only", result.get(0).get("Condition"));
        assertEquals("3 words", result.get(0).get("Length_list"));
        assertEquals("deebral.wav", result.get(0).get("Word1"));
        assertEquals("smoer_2.wav", result.get(0).get("Word2"));
        assertEquals("wijp.wav", result.get(0).get("Word3"));
        assertEquals("", result.get(0).get("Word4"));
        assertEquals("", result.get(0).get("Word5"));
        assertEquals("", result.get(0).get("Word6"));
        assertEquals("2", result.get(0).get("Position_target"));
        assertEquals("plus10db", result.get(0).get("Noise_level"));
        assertEquals("0", result.get(0).get("Position_foil"));
        
        assertEquals("2", result.get(1).get("Nr"));
        assertEquals("pauw", result.get(1).get("Word"));
        assertEquals("paud_1.wav", result.get(1).get("Target_nonword"));
        assertEquals("1", result.get(1).get("Syllables"));
        assertEquals("Target-only", result.get(1).get("Condition"));
        assertEquals("3 words", result.get(1).get("Length_list"));
        assertEquals("rolscheegt.wav", result.get(1).get("Word1"));
        assertEquals("paud_2.wav", result.get(1).get("Word2"));
        assertEquals("staap.wav", result.get(1).get("Word3"));
        assertEquals("", result.get(1).get("Word4"));
        assertEquals("", result.get(1).get("Word5"));
        assertEquals("", result.get(1).get("Word6"));
        assertEquals("2", result.get(1).get("Position_target"));
        assertEquals("plus10db", result.get(1).get("Noise_level"));
        assertEquals("0", result.get(1).get("Position_foil"));
        
        assertEquals("4", result.get(3).get("Nr"));
        assertEquals("schelp", result.get(3).get("Word"));
        assertEquals("schess_1.wav", result.get(3).get("Target_nonword"));
        assertEquals("1", result.get(3).get("Syllables"));
        assertEquals("Target-only", result.get(3).get("Condition"));
        assertEquals("3 words", result.get(3).get("Length_list"));
        assertEquals("rim.wav", result.get(3).get("Word1"));
        assertEquals("schess_2.wav", result.get(3).get("Word2"));
        assertEquals("werelglal.wav", result.get(3).get("Word3"));
        assertEquals("", result.get(3).get("Word4"));
        assertEquals("", result.get(3).get("Word5"));
        assertEquals("", result.get(3).get("Word6"));
        assertEquals("2", result.get(3).get("Position_target"));
        assertEquals("plus10db", result.get(3).get("Noise_level"));
        assertEquals("0", result.get(3).get("Position_foil"));
    }

  
}
