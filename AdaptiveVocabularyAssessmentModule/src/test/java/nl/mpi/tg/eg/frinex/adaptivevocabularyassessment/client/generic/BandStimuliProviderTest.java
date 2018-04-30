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
import java.util.Map;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;
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
public class BandStimuliProviderTest {

    public final BandStimulus stimulus = new BandStimulus("smoer_xx", new Stimulus.Tag[0], "smoer", "", 900, "aud", "vid", "img",
            "a,b,c", "b,c", "plus10db", 10);

    private class BandStimuliProviderImp extends BandStimuliProvider<BandStimulus> {

        public BandStimuliProviderImp(final Stimulus[] stimulusArray) {
            super(stimulusArray);

        }

        ;
        @Override
        public BookkeepingStimulus<BandStimulus> deriveNextFastTrackStimulus() {
            return null;
        }

        @Override
        protected boolean analyseCorrectness(Stimulus stimulus, String stimulusResponse) {
            return true;
        }

        @Override
        public void deserialiseSpecific(String snapshot) {
        }

        @Override
        public boolean fastTrackToBeContinuedWithSecondChance() {
            return true;
        }

        @Override
        public boolean enoughStimuliForFastTrack() {
            return true;
        }

        @Override
        public boolean initialiseNextFineTuningTuple() {
            return true;
        }

        @Override
        public void recycleUnusedStimuli() {
        }

        @Override
        public String getStringFastTrack(String startRow, String endRow, String startColumn, String endColumn) {
            return "";
        }
        
        @Override
        public String getStringFineTuningHistory(String startRow, String endRow, String startColumn, String endColumn, String format){
            return "";
        }

    };

    private final BandStimuliProvider<BandStimulus> instance = new BandStimuliProviderImp(new Stimulus[0]);

    public BandStimuliProviderTest() {
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
     * Test of setfastTrackPresent method, of class BandStimuliProvider.
     */
    @Test
    public void testGetSetfastTrackPresent() {
        System.out.println("setfastTrackPresent");
        assertTrue(this.instance.getfastTrackPresent());
        String fastTrackPresent = "false";
        this.instance.setfastTrackPresent(fastTrackPresent);
        assertFalse(this.instance.getfastTrackPresent());
    }

    /**
     * Test of setfineTuningFirstWrongOut method, of class BandStimuliProvider.
     */
    @Test
    public void testGetSetfineTuningFirstWrongOut() {
        System.out.println("setfineTuningFirstWrongOut");
        String fineTuningFirstWrongOut = "false";
        assertTrue(this.instance.getfineTuningFirstWrongOut());
        instance.setfineTuningFirstWrongOut(fineTuningFirstWrongOut);
        assertFalse(this.instance.getfineTuningFirstWrongOut());
    }

    /**
     * Test of setnumberOfBands method, of class BandStimuliProvider.
     */
    @Test
    public void testGetSetnumberOfBands() {
        System.out.println("setnumberOfBands");
        String numberOfBands = "40";
        assertEquals(0, this.instance.getnumberOfBands());
        instance.setnumberOfBands(numberOfBands);
        assertEquals(40, this.instance.getnumberOfBands());
    }

 

    /**
     * Test of setstartBand method, of class BandStimuliProvider.
     */
    @Test
    public void testGetSetstartBand() {
        System.out.println("setstartBand");
        String startBand = "20";
        assertEquals(0, this.instance.getstartBand());
        this.instance.setstartBand(startBand);
        assertEquals(20, this.instance.getstartBand());
    }

    /**
     * Test of setfineTuningTupleLength method, of class BandStimuliProvider.
     */
    @Test
    public void testGetSetfineTuningTupleLength() {
        System.out.println("setfineTuningTupleLength");
        String fineTuningTupleLength = "4";
        assertEquals(0, this.instance.getfineTuningTupleLength());
        instance.setfineTuningTupleLength(fineTuningTupleLength);
        assertEquals(4, this.instance.getfineTuningTupleLength());
    }

    /**
     * Test of setfineTuningUpperBoundForCycles method, of class
     * BandStimuliProvider.
     */
    @Test
    public void testGetSetfineTuningUpperBoundForCycles() {
        System.out.println("setfineTuningUpperBoundForCycles");
        String fineTuningUpperBoundForCycles = "2";
        assertEquals(0, this.instance.getfineTuningUpperBoundForCycles());
        instance.setfineTuningUpperBoundForCycles(fineTuningUpperBoundForCycles);
        assertEquals(2, this.instance.getfineTuningUpperBoundForCycles());
    }

    /**
     * Test of initialiseStimuliState method, of class BandStimuliProvider.
     */
    @Test
    public void testInitialiseStimuliStateEmpty() {
        System.out.println("initialiseStimuliState");
        String stimuliStateSnapshot = "";

        this.instance.setnumberOfBands("40");
        
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState(stimuliStateSnapshot);
// this.bandScore = -1;
//            this.percentageScore = 0;
//            this.isCorrectCurrentResponse = null;
//            this.currentBandIndex = this.startBand - 1;
//            this.bandVisitCounter = new int[this.numberOfBands];
//
//            //this.totalStimuli: see the child class
//            this.enoughFineTuningStimulae = true;
//            for (int i = 0; i < this.numberOfBands; i++) {
//                this.bandVisitCounter[i] = 0;
//            }
//
//            this.cycle2helper = new int[this.fineTuningUpperBoundForCycles * 2 + 1];
//            for (int i = 0; i < this.fineTuningUpperBoundForCycles * 2 + 1; i++) {
//                this.cycle2helper[i] = 0;
//            }
//            this.cycle2 = false;
//            this.champion = false;
//            this.looser = false;
//            this.justVisitedLastBand = false;
//            this.percentageBandTable = this.generatePercentageBandTable();
        assertEquals(0, this.instance.getBandScore());
        assertEquals(19, this.instance.getCurrentBandIndex());
        assertTrue(this.instance.getEnoughFinetuningStimuli());
        assertTrue(this.instance.getnumberOfBands() == this.instance.getbandVisitCounter().length);
        for (int i = 0; i < this.instance.getnumberOfBands(); i++) {
            assertEquals(new Integer(0), this.instance.getbandVisitCounter()[i]);
        }
        assertTrue(this.instance.getfineTuningUpperBoundForCycles() * 2 + 1 == this.instance.getcycle2helper().length);
        for (int i = 0; i < this.instance.getfineTuningUpperBoundForCycles() * 2 + 1; i++) {
            assertEquals(new Integer(0), this.instance.getcycle2helper()[i]);
        }
        assertFalse(this.instance.getCycel2());
        assertFalse(this.instance.getChampion());
        assertFalse(this.instance.getLooser());
    }

    /**
     * Test of generateStimuliStateSnapshot method, of class
     * BandStimuliProvider.
     */
    @Test
    public void testGenerateStimuliStateSnapshot() {
        System.out.println("generateStimuliStateSnapshot");
        String stimuliStateSnapshot = "";

        this.instance.setnumberOfBands("40");
        
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState(stimuliStateSnapshot);

        assertEquals(this.instance.toString(), this.instance.generateStimuliStateSnapshot());
    }

    /**
     * Test of getPercentageBandTable method, of class BandStimuliProvider.
     */
    @Test
    public void testGetPercentageBandTable() {
        System.out.println("getPercentageBandTable");
        String stimuliStateSnapshot = "";

        this.instance.setnumberOfBands("40");
        
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState(stimuliStateSnapshot);

        LinkedHashMap<Long, Integer> table = this.instance.getPercentageBandTable();
        assertEquals(11, table.size());

        Long[] keys = table.keySet().toArray(new Long[0]);
        assertTrue(1 == keys[0]);
        assertTrue(10 == keys[1]);
        assertTrue(20 == keys[2]);
        assertTrue(30 == keys[3]);
        assertTrue(40 == keys[4]);
        assertTrue(50 == keys[5]);
        assertTrue(60 == keys[6]);
        assertTrue(70 == keys[7]);
        assertTrue(80 == keys[8]);
        assertTrue(90 == keys[9]);
        assertTrue(99 == keys[10]);

        assertTrue(0 == table.get(keys[0]));
        assertTrue(4 == table.get(keys[1]));
        assertTrue(8 == table.get(keys[2]));
        assertTrue(12 == table.get(keys[3]));
        assertTrue(16 == table.get(keys[4]));
        assertTrue(20 == table.get(keys[5]));
        assertTrue(24 == table.get(keys[6]));
        assertTrue(28 == table.get(keys[7]));
        assertTrue(32 == table.get(keys[8]));
        assertTrue(36 == table.get(keys[9]));
        assertTrue(40 == table.get(keys[10]));
    }

    /**
     * Test of getResponseRecord method, of class BandStimuliProvider.
     */
    @Test
    public void testGetResponseRecord() {
        System.out.println("getResponseRecord");
        String stimuliStateSnapshot = "";

        this.instance.setnumberOfBands("40");
        
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState(stimuliStateSnapshot);
        ArrayList<BookkeepingStimulus<BandStimulus>> result = instance.getResponseRecord();
        assertEquals(0, result.size());
    }

    /**
     * Test of getBestFastTrackBand method, of class BandStimuliProvider.
     */
    @Test
    public void testGetBestFastTrackBand() {
        System.out.println("getBestFastTrackBand");

        this.instance.setnumberOfBands("40");
        
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");
        int result = instance.getBestFastTrackBand();
        assertEquals(0, result);
    }

    /**
     * Test of getBandScore method, of class BandStimuliProvider.
     */
    @Test
    public void testGetBandScore() {
        System.out.println("getBandScore");

        this.instance.setnumberOfBands("40");
        
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");
        int result = instance.getBandScore();
        assertEquals(0, result);
    }

    /**
     * Test of getPercentageScore method, of class BandStimuliProvider.
     */
    @Test
    public void testbandNumberIntoPercentage() {
        System.out.println("getPercentageScore");

        this.instance.setnumberOfBands("40");
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");

        assertEquals(50, instance.bandNumberIntoPercentage(20));
    }

    /**
     * Test of getFTtuple method, of class BandStimuliProvider.
     */
    @Test
    public void testGetFTtuple() {
        System.out.println("getFTtuple");
        this.instance.setnumberOfBands("40");
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");

        ArrayList<BookkeepingStimulus<BandStimulus>> result = instance.getFTtuple();
        assertEquals(0, result.size());
    }

    /**
     * Test of getEndFastTrackTimeTick method, of class BandStimuliProvider.
     */
    @Test
    public void testGetEndFastTrackTimeTick() {
        System.out.println("getEndFastTrackTimeTick");
        this.instance.setnumberOfBands("40");
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");
        int result = instance.getEndFastTrackTimeTick();
        assertEquals(0, result);
    }

    /**
     * Test of getCurrentStimulus method, of class BandStimuliProvider.
     */
    @Test
    public void testGetCurrentStimulus() {
        System.out.println("getCurrentStimulus");
        this.instance.setnumberOfBands("40");
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");
        try {
            Stimulus result = instance.getCurrentStimulus();
        } catch (Exception e) {
            System.out.println("Yes, there must be the following exception here (the recorrds are not initialised) : " + e);
        }
    }

    /**
     * Test of getCurrentStimulusIndex method, of class BandStimuliProvider.
     */
    @Test
    public void testGetCurrentStimulusIndex() {
        System.out.println("getCurrentStimulusIndex");
        this.instance.setnumberOfBands("40");
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");
        int result = instance.getCurrentStimulusIndex();
        assertEquals(-1, result);
    }

    /**
     * Test of getTotalStimuli method, of class BandStimuliProvider.
     */
    @Test
    public void testGetTotalStimuli() {
        System.out.println("getTotalStimuli");
        int result = this.instance.getTotalStimuli();
        assertEquals(0, result);
    }

    /**
     * Test of nextStimulus method, of class BandStimuliProvider.
     */
    @Test
    public void testNextStimulus() {
        System.out.println("nextStimulus");
        int increment = 0;
        this.instance.setnumberOfBands("40");
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");

        this.instance.nextStimulus(increment);
    }

    /**
     * Test of deriveNextFastTrackStimulus method, of class BandStimuliProvider.
     */
    @Test
    public void testDeriveNextFastTrackStimulus() {
        System.out.println("deriveNextFastTrackStimulus");
        this.instance.setnumberOfBands("40");
        
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");
        BookkeepingStimulus result = instance.deriveNextFastTrackStimulus();
        assertEquals(null, result);
    }

    /**
     * Test of setCurrentStimuliIndex method, of class BandStimuliProvider.
     */
    @Test
    public void testSetCurrentStimuliIndex() {
        System.out.println("setCurrentStimuliIndex");
        this.instance.setnumberOfBands("40");
        
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");
        this.instance.setCurrentStimuliIndex(0); // the method actually does nothing and not relevant for band stimuli
        assertEquals(-1, this.instance.getCurrentStimulusIndex());

    }

    /**
     * Test of getCurrentStimulusUniqueId method, of class BandStimuliProvider.
     */
    @Test
    public void testGetCurrentStimulusUniqueId() {
        System.out.println("getCurrentStimulusUniqueId");
        System.out.println("setCurrentStimuliIndex");
        this.instance.setnumberOfBands("40");
        
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");
        try {
            String result = instance.getCurrentStimulusUniqueId();
        } catch (Exception e) {
            System.out.println("Yes, there must be an exception here, the stimuli list is not initialised yet: " + e);
        }

    }

    /**
     * Test of getHtmlStimuliReport method, of class BandStimuliProvider.
     */
    @Test
    public void testGetHtmlStimuliReport() {
        System.out.println("getHtmlStimuliReport");
        this.instance.setnumberOfBands("40");
        
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");
        String result = this.instance.getHtmlStimuliReport();
        assertNotNull(result);
    }

    /**
     * Test of getStimuliReport method, of class BandStimuliProvider.
     */
    @Test
    public void testGetStimuliReport() {
        System.out.println("getStimuliReport");
        this.instance.setnumberOfBands("40");
        
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");
        Map<String, String> result = instance.getStimuliReport("");
        assertEquals(0, result.size());
    }

    /**
     * Test of isCorrectResponse method, of class BandStimuliProvider.
     */
    @Test
    public void testIsCorrectResponse() {
        System.out.println("isCorrectResponse");
        this.instance.setnumberOfBands("40");
        
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");
        try {
            boolean result = instance.isCorrectResponse(stimulus, "");
        } catch (Exception e) {
            System.out.println("Yes, there must be an exception here, the record stimuli list is not initialised yet: " + e);
        }
    }

    /**
     * Test of tupleIsNotEmpty method, of class BandStimuliProvider.
     */
    @Test
    public void testTupleIsNotEmpty() {
        System.out.println("tupleIsNotEmpty");
        this.instance.setnumberOfBands("40");
        
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");
        assertFalse(this.instance.tupleIsNotEmpty());
    }

    /**
     * Test of allTupleIsCorrect method, of class BandStimuliProvider.
     */
    @Test
    public void testAllTupleIsCorrect() {
        System.out.println("allTupleIsCorrect");
        this.instance.setnumberOfBands("40");
        
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");
        // false positibe: the tuple is empty
        assertTrue(this.instance.isWholeTupleCorrect());
    }

    /**
     * Test of bandNumberIntoPercentage method, of class BandStimuliProvider.
     */
    @Test
    public void testBandNumberIntoPercentage() {
        System.out.println("bandNumberIntoPercentage");
        assertEquals(0L, this.instance.bandNumberIntoPercentage(0));

        this.instance.setnumberOfBands("40");
        assertEquals(100L, this.instance.bandNumberIntoPercentage(40));
        assertEquals(50L, this.instance.bandNumberIntoPercentage(20));
        assertEquals(75L, this.instance.bandNumberIntoPercentage(30));
        assertEquals(25L, this.instance.bandNumberIntoPercentage(10));
        assertEquals(13L, this.instance.bandNumberIntoPercentage(5));
    }

    /**
     * Test of percentageIntoBandNumber method, of class BandStimuliProvider.
     */
    @Test
    public void testPercentageIntoBandNumber() {
        System.out.println("percentageIntoBandNumber");
        assertEquals(0, this.instance.percentageIntoBandNumber(0));
        this.instance.setnumberOfBands("54");
        assertEquals(54, instance.percentageIntoBandNumber(100));
        assertEquals(53, instance.percentageIntoBandNumber(99));
        assertEquals(27, instance.percentageIntoBandNumber(50));
        assertEquals(26, instance.percentageIntoBandNumber(49));
        assertEquals(5, instance.percentageIntoBandNumber(10));
        assertEquals(1, instance.percentageIntoBandNumber(1));
        assertEquals(1, instance.percentageIntoBandNumber(2));

    }

    /**
     * Test of toBeContinuedLoopChecker method, of class BandStimuliProvider.
     */
    @Test
    public void testToBeContinuedLoopChecker() {
        System.out.println("toBeContinuedLoopChecker");
        this.instance.setnumberOfBands("40");
        
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");

        assertTrue(instance.toBeContinuedLoopChecker());

    }

    /**
     * Test of detectLoop method, of class BandStimuliProvider.
     */
    /**
     * Test of detectLoop method, of class this.instance.
     */
    @Test
    public void testDetectLoop() {
        System.out.println("detectLoop");
        Integer[] arr1 = {42, 43, 42, 43, 42, 43, 42};
        boolean result1 = BandStimuliProvider.detectLoop(arr1);
        assertEquals(true, result1);
        Integer[] arr2 = {42, 43, 42, 43, 42, 43, 45};
        boolean result2 = BandStimuliProvider.detectLoop(arr2);
        assertEquals(false, result2);
        Integer[] arr3 = {43, 42, 43, 42, 43, 42, 45, 42};
        boolean result3 = BandStimuliProvider.detectLoop(arr3);
        assertEquals(false, result3);
    }

    /**
     * Test of shiftFIFO method, of class this.instance.
     */
    @Test
    public void testShiftFIFO() {
        System.out.println("shiftFIFO");
        Integer[] fifo = {0, 1, 2, 3, 4, 5, 6};
        int newelement = 7;
        BandStimuliProvider.shiftFIFO(fifo, newelement);
        for (Integer i = 0; i < 7; i++) {
            assertEquals(new Integer(i + 1), fifo[i]);
        }
    }

    /**
     * Test of shiftFIFO method, of class this.instance.
     */
    @Test
    public void testMostOftenVisitedBandNumber() {
        System.out.println(" MostOftenVisitedBandMumber");
        Integer[] visitCounter = {1, 3, 2, 3, 3, 3, 1};
        // indices {1,3,4,5}
        // ind = 1, indSym = 2
        int currentIndex1 = 2; // at 2
        int bandNumber1 = this.instance.mostOftenVisitedBandNumber(visitCounter, currentIndex1);
        assertEquals(4, bandNumber1);

        int currentIndex2 = 3; // at 3
        int bandNumber2 = this.instance.mostOftenVisitedBandNumber(visitCounter, currentIndex2);
        assertEquals(4, bandNumber2);
    }

    /**
     * Test of getStringSummary method, of class BandStimuliProvider.
     */
    @Test
    public void testGetStringSummary() {
        System.out.println("getStringSummary");
        
        this.instance.setnumberOfBands("40");
        
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");
        
        String startRow = "";
        String endRow = "\n";
        String startColumn = "";
        String endColumn = ";";
        String result = this.instance.getStringSummary(startRow, endRow, startColumn, endColumn);
        String expResult = "Score;BestFastTrack;Cycel2oscillation;EnoughFineTuningStimuli;Champion;Looser;\n0;0;false;true;false;false;\n";
        assertEquals(expResult,result);
        
        this.instance.setfastTrackPresent("false");
        String result2 = this.instance.getStringSummary(startRow, endRow, startColumn, endColumn);
        String expResult2 = "Score;Cycel2oscillation;EnoughFineTuningStimuli;Champion;Looser;\n0;false;true;false;false;\n";
        assertEquals(expResult2,result2);
    }

    /**
     * Test of toString method, of class BandStimuliProvider.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        this.instance.setnumberOfBands("40");
        
        this.instance.setfastTrackPresent("true");
        this.instance.setfineTuningFirstWrongOut("false");
        this.instance.setfineTuningTupleLength("4");
        this.instance.setfineTuningUpperBoundForCycles("2");
        
        this.instance.setstartBand("20");
        this.instance.initialiseStimuliState("");
        String expResult="{numberOfBands=40, startBand=20, fineTuningTupleLength=4, fineTuningUpperBoundForCycles=2, fastTrackPresent=true, "
                + "fineTuningFirstWrongOut=false, bandScore=0, isCorrectCurrentResponse=null, currentBandIndex=19, totalStimuli=0, responseRecord=[], "
                + "percentageBandTable={1=0, 10=4, 20=8, 30=12, 40=16, 50=20, 60=24, 70=28, 80=32, 90=36, 99=40}, tupleFT=[], "
                + "bestBandFastTrack=0, isFastTrackIsStillOn=true, secondChanceFastTrackIsFired=false, timeTickEndFastTrack=0,"
                + " enoughFineTuningStimulae=true, "
                + "bandVisitCounter=[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], "
                + "cycle2helper=[0, 0, 0, 0, 0], "
                + "cycle2=false, champion=false, looser=false, justVisitedLastBand=false, justVisitedFirstBand=false, endOfRound=false, errorMessage=null}";
        assertEquals(expResult, this.instance.toString());
    }
    
     /**
     * Test of toString method, of class BandStimuliProvider.
     */
    @Test
    public void deserialisation() {
        System.out.println("toString");
        String input = "{"
                + "numberOfBands=54, "
                + "startBand=25, "
                + "fineTuningTupleLength=4, "
                + "fineTuningUpperBoundForCycles=3, "
                + "fastTrackPresent=false, "
                + "fineTuningFirstWrongOut=false, "
                + "bandScore=27, "
                + "percentageScore=50, "
                + "isCorrectCurrentResponse=true, "
                + "currentBandIndex=28, "
                + "totalStimuli=10, "
                + "responseRecord=null, "
                + "bestBandFastTrack=20, "
                + "isFastTrackIsStillOn=false, "
                + "secondChanceFastTrackIsFired=true, "
                + "timeTickEndFastTrack=10, "
                + "tupleFT=null, "
                + "enoughFineTuningStimulae=false, "
                + "bandVisitCounter=[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0], "
                + "cycle2helper=[10, 9, 8, 7, 6], "
                + "cycle2=true, "
                + "champion=true, "
                + "looser=true, "
                + "justVisitedLastBand=true, "
                + "justVisitedFirstBand=true, "
                + "endOfRound=true, "
                + "errorMessage=Error!}";
        
        this.instance.initialiseStimuliState(input);
        
        assertEquals(54,this.instance.getnumberOfBands());
        assertEquals(25,this.instance.getstartBand());
        
        assertEquals(4,this.instance.getfineTuningTupleLength());
        assertEquals(3,this.instance.getfineTuningUpperBoundForCycles());
        assertFalse(this.instance.getfastTrackPresent());
        assertFalse(this.instance.getfineTuningFirstWrongOut());
        
        assertEquals(27,this.instance.getBandScore());
        assertEquals(50,this.instance.getPercentageScore());
        assertTrue(this.instance.isCorrectCurrentResponse);
        assertEquals(28,this.instance.getCurrentBandIndex());
        assertEquals(10,this.instance.getTotalStimuli());
        assertEquals(0, this.instance.getResponseRecord().size()); // setting response record is ignored, it is a part of the concrete implementation
        assertEquals(20,this.instance.getBestFastTrackBand());
        assertFalse(this.instance.getIsFastTrackIsStillOn());
        assertTrue(this.instance.getSecondChanceFastTrackIsFired());
        assertEquals(10,this.instance.getEndFastTrackTimeTick());
        assertEquals(0, this.instance.getFTtuple().size());   // setting response record is ignored, it is a part of the concrete implementation
        assertFalse(this.instance.getEnoughFinetuningStimuli());
        
        Integer[] counter=this.instance.getbandVisitCounter();
        assertEquals(12, counter.length);
        for (int i=0; i<11; i++) {
            assertEquals(new Integer(i+1), counter[i]);
        }
        assertEquals(new Integer(0), counter[11]);
        
        Integer[] helper=this.instance.getcycle2helper();
        assertEquals(5, helper.length);
        for (int i=0; i<helper.length; i++) {
            assertEquals(new Integer(10-i), helper[i]);
        }
        
        assertTrue(this.instance.getCycel2());
        assertTrue(this.instance.getLooser());
        assertTrue(this.instance.getChampion());
        assertTrue(this.instance.getJustVisitedFirstBand());
        assertTrue(this.instance.getJustVisitedLastBand());
        assertEquals("Error!", this.instance.getErrorMessage());
        String expResult ="<p>User summary</p><table border=1><tr><td>Score</td><td>Cycel2oscillation</td>"
                + "<td>EnoughFineTuningStimuli</td><td>Champion</td><td>Looser</td></tr><tr><td>27</td>"
                + "<td>true</td><td>false</td>"
                + "<td>true</td><td>true</td></tr>"
                + "</table><br><br><p>Fine tuning History</p><table border=1></table>";
        assertEquals(expResult,this.instance.getHtmlStimuliReport());
    }
    
   

}
