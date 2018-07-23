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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.peabody.PeabodyStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.PeabodyStimuliProvider;
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
public class PeabodyStimuliFromStringTest {

    private final int amountOfStimuli = 204;
    private final int stimuliPerBand = 12;
    private final int numberOfBands = 17;
    public static final String PEABODY_STIMULI_DIR = "static/stimuli/";

    public PeabodyStimuliFromStringTest() {
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
     * Test of parseWordsInputCSVString method and getHashedStimuli(), of class
     * PeabodyStimuliFromString.
     */
    @Test
    public void testGetHashedStimuli() throws Exception {
        System.out.println("parseWordsInputCSVString");
        PeabodyStimuliFromString instance = new PeabodyStimuliFromString();
        PeabodyStimuliProvider provider = new PeabodyStimuliProvider(null);
        instance.parseWordsInputCSVString(provider, this.numberOfBands, PeabodyStimuliFromStringTest.PEABODY_STIMULI_DIR);

        LinkedHashMap<String, PeabodyStimulus> result = instance.getHashedStimuli();
        assertEquals(this.amountOfStimuli, result.size());
        Set<String> keys = result.keySet();
        int counter = 0;
        int setCounter = 0;
        int pageCounter = 0;
        for (String key : keys) {
            counter++;
            pageCounter++;
            PeabodyStimulus stimulus = result.get(key);

            String[] bits = stimulus.getAudio().split("_");
            String[] bits1 = bits[0].split("/");
            int number = Integer.parseInt(bits1[bits1.length - 1]);
            assertEquals(counter, number);

            String image = instance.removeFileExtension(stimulus.getImage(), ".png");
            String[] bitsImage = image.split("_");
            String[] bitsImage1 = bitsImage[0].split("/");
            String set = bitsImage1[bitsImage1.length - 1];
            int expectedSetNumber = setCounter + 1;
            assertEquals("set" + expectedSetNumber, set);

            int pageNumber = Integer.parseInt(bitsImage[2]);
            assertEquals(pageCounter, pageNumber);

            if (counter % 12 == 0) {
                setCounter++;
                pageCounter = 0;
            }
        }

    }

    /**
     * Test of parseWordsInputCSVString method and getStimuliByBands method, of
     * class PeabodyStimuliFromString.
     */
    @Test
    public void testGetStimuliByBands() throws Exception {
        System.out.println("getStimuliByBands");
        PeabodyStimuliFromString instance = new PeabodyStimuliFromString();
        PeabodyStimuliProvider provider = new PeabodyStimuliProvider(null);
        instance.parseWordsInputCSVString(provider, this.numberOfBands, PeabodyStimuliFromStringTest.PEABODY_STIMULI_DIR);

        ArrayList<ArrayList<PeabodyStimulus>> result = instance.getStimuliByBands();
        assertEquals(this.numberOfBands, result.size());
        int counter = 0;
        for (int setIndex = 0; setIndex < this.numberOfBands; setIndex++) {
            for (int pageIndex = 0; pageIndex < this.stimuliPerBand; pageIndex++) {

                PeabodyStimulus stimulus = result.get(setIndex).get(pageIndex);
                counter++;

                String[] bits = stimulus.getAudio().split("_");
                String[] bits1 = bits[0].split("/");
                int number = Integer.parseInt(bits1[bits1.length - 1]);
                assertEquals(counter, number);

                String image = instance.removeFileExtension(stimulus.getImage(), ".png");
                String[] bitsImage = image.split("_");
                String[] bitsImage1 = bitsImage[0].split("/");
                String set = bitsImage1[bitsImage1.length - 1];
                int expectedSetNumber = setIndex + 1;
                assertEquals("set" + expectedSetNumber, set);

                int pageNumber = Integer.parseInt(bitsImage[2]);
                assertEquals(pageIndex + 1, pageNumber);

            }
        }
        assertEquals(this.amountOfStimuli, counter);

    }

}
