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
public class WordTypeTest {

    private final String[] vals = {"WORD", "EXAMPLE_TARGET_NON_WORD", "TARGET_NON_WORD", "FOIL", "NON_WORD"};

    public WordTypeTest() {
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
     * Test of values method, of class WordType.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        WordType[] result = WordType.values();
        assertEquals(5, result.length);
        for (int i = 0; i < 5; i++) {
            assertEquals(this.vals[i], result[i].toString());
        }
    }

    /**
     * Test of valueOf method, of class WordType.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        WordType result1 = WordType.valueOf("WORD");
        assertEquals(WordType.WORD, result1);
        WordType result2 = WordType.valueOf("TARGET_NON_WORD");
        assertEquals(WordType.TARGET_NON_WORD, result2);
        WordType result3 = WordType.valueOf("EXAMPLE_TARGET_NON_WORD");
        assertEquals(WordType.EXAMPLE_TARGET_NON_WORD, result3);
         WordType result4 = WordType.valueOf("FOIL");
        assertEquals(WordType.FOIL, result4);
        WordType result5 = WordType.valueOf("NON_WORD");
        assertEquals(WordType.NON_WORD, result5);
    }

 
}
