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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.UtilsList;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.AudioAsStimuliProvider;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.audiopool.AudioStimuliFromString;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author olhshk
 */
public class PermutationPairTest {

    private final AudioStimuliFromString reader = new AudioStimuliFromString();
    private final LinkedHashMap<Integer, Trial> hashedTrials;
    private final ArrayList<ArrayList<LinkedHashMap<TrialCondition, ArrayList<Trial>>>> trialMatrix;
    private final ArrayList<Integer> requiredLengths = new ArrayList<Integer>(Arrays.asList(3, 4, 5, 6));
    private final ArrayList<TrialCondition> requiredTrialTypes = new ArrayList<TrialCondition>(Arrays.asList(TrialCondition.TARGET_ONLY, TrialCondition.TARGET_AND_FOIL, TrialCondition.NO_TARGET));
    private final ArrayList<ArrayList<Integer>> allLengthPermuations;
    private final ArrayList<ArrayList<TrialCondition>> allConditionPermuations;
    private final int tupleSize = 3;
    private final AudioAsStimuliProvider provider = new AudioAsStimuliProvider(null);

    public PermutationPairTest() {
        this.reader.readTrialsAsCsv(this.provider, TestConfigurationConstants.AUDIO_STIMULI_DIR);
        this.hashedTrials = this.reader.getHashedTrials();
        this.trialMatrix = Trial.prepareTrialMatrix(this.hashedTrials, TestConfigurationConstants.AUDIO_NUMBER_OF_BANDS, TestConfigurationConstants.AUDIO_MAX_LENGTH);
        UtilsList<Integer> utilInt = new UtilsList<Integer>();
        this.allLengthPermuations = utilInt.generatePermutations(this.requiredLengths, this.tupleSize);
        UtilsList<TrialCondition> utilCond = new UtilsList<TrialCondition>();
        this.allConditionPermuations = utilCond.generatePermutations(this.requiredTrialTypes, this.tupleSize);
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
     * Test of initialiseAvailabilityList method, of class PermutationPair.
     */
    @Test
    public void testInitialiseAvailabilityList() {
        System.out.println("initialiseAvailabilityList");
        ArrayList<ArrayList<PermutationPair>> result = PermutationPair.initialiseAvailabilityList(this.trialMatrix,
                this.allLengthPermuations,
                this.allConditionPermuations,
                TestConfigurationConstants.AUDIO_NUMBER_OF_BANDS);
        assertEquals(TestConfigurationConstants.AUDIO_NUMBER_OF_BANDS, result.size());
        int numberOfPairs = this.allLengthPermuations.size() * this.allConditionPermuations.size();
        for (int i = 0; i < TestConfigurationConstants.AUDIO_NUMBER_OF_BANDS; i++) {
            assertNotNull(result.get(i));
            ArrayList<PermutationPair> bandList = result.get(i);
            assertEquals(numberOfPairs, bandList.size());
            for (PermutationPair permPair : bandList) {

                assertTrue(permPair.isAvailable());

                ArrayList<Integer> trialLengths = permPair.getTrialLengths();
                assertEquals(this.tupleSize, trialLengths.size());

                ArrayList<TrialCondition> trialConds = permPair.getTrialConditions();
                assertEquals(this.tupleSize, trialConds.size());

                ArrayList<ArrayList<Trial>> trials = permPair.getTrials();
                assertEquals(this.tupleSize, trials.size());

                int countTargetAndFoil = 0;
                int countNoTarget = 0;
                int countTargetOnly = 0;
                int count3 = 0;
                int count4 = 0;
                int count5 = 0;
                int count6 = 0;
                for (int j = 0; j < this.tupleSize; j++) {
                    TrialCondition tc = trialConds.get(j);
                    int tLength = trialLengths.get(j);
                    ArrayList<Trial> examples = trials.get(j);

                    if (tc.equals(TrialCondition.NO_TARGET)) {
                        countNoTarget++;
                    }

                    if (tc.equals(TrialCondition.TARGET_AND_FOIL)) {
                        countTargetAndFoil++;
                    }

                    if (tc.equals(TrialCondition.TARGET_ONLY)) {
                        countTargetOnly++;
                    }

                    if (tLength == 3) {
                        count3++;
                    }
                    if (tLength == 4) {
                        count4++;
                    }
                    if (tLength == 5) {
                        count5++;
                    }
                    if (tLength == 6) {
                        count6++;
                    }

                    for (Trial trial : examples) {
                        assertEquals(tc, trial.getCondition());
                        assertEquals(tLength, trial.getTrialLength());
                        assertEquals(i, trial.getBandIndex());

                        // references to the trial must be the same
                        Integer id = trial.getId();
                        assertEquals(this.hashedTrials.get(id), trial);

                    }
                }
                assertEquals(1, countNoTarget);
                assertEquals(1, countTargetOnly);
                assertEquals(1, countTargetAndFoil);

                assertEquals(this.tupleSize, count3 + count4 + count5 + count6);
                assertTrue(count3 <= 1);
                assertTrue(count4 <= 1);
                assertTrue(count5 <= 1);
                assertTrue(count6 <= 1);
            }
        }

    }

    /**
     * Test of toString method, of class PermutationPair.
     */
    @Test
    public void testToString() {
        System.out.println("toString with null trial List");
        ArrayList<Integer> lengths = new ArrayList<Integer>(3);
        lengths.add(3);
        lengths.add(5);
        lengths.add(6);

        ArrayList<TrialCondition> conditions = new ArrayList<TrialCondition>(3);
        conditions.add(TrialCondition.TARGET_AND_FOIL);
        conditions.add(TrialCondition.NO_TARGET);
        conditions.add(TrialCondition.TARGET_ONLY);

        PermutationPair instance = new PermutationPair(conditions, lengths, null);
        String expResult = "{fields=[trialConditions, trialLengths, trials], trialConditions=[TARGET_AND_FOIL, NO_TARGET, TARGET_ONLY], trialLengths=[3, 5, 6], trials=null}";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of toObject method, of class PermutationPair.
     */
    @Test
    public void testToObject() {
        System.out.println("toObject");

        ArrayList<TrialCondition> conditions = new ArrayList<TrialCondition>(3);
        conditions.add(TrialCondition.TARGET_AND_FOIL);
        conditions.add(TrialCondition.NO_TARGET);
        conditions.add(TrialCondition.TARGET_ONLY);

        ArrayList<Integer> lengths = new ArrayList<Integer>(3);
        lengths.add(3);
        lengths.add(5);
        lengths.add(6);

        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("fields", PermutationPair.FLDS);
        map.put("trialConditions", conditions);
        map.put("trialLengths", lengths);
        map.put("trials", null);

        PermutationPair result = PermutationPair.toObject(map, this.reader.getHashedTrials());
        PermutationPair expResult = new PermutationPair(conditions, lengths, null);
        assertEquals(expResult.getTrialConditions(), result.getTrialConditions());
        assertEquals(expResult.getTrialLengths(), result.getTrialLengths());
        assertNull(result.getTrials());
    }

    /**
     * Test of getTrialConditions method, of class PermutationPair.
     */
    @Test
    public void testGetTrialConditions() {
        System.out.println("getTrialConditions");
        ArrayList<Integer> lengths = new ArrayList<Integer>(3);
        lengths.add(3);
        lengths.add(5);
        lengths.add(6);

        ArrayList<TrialCondition> conditions = new ArrayList<TrialCondition>(3);
        conditions.add(TrialCondition.TARGET_AND_FOIL);
        conditions.add(TrialCondition.NO_TARGET);
        conditions.add(TrialCondition.TARGET_ONLY);

        PermutationPair instance = new PermutationPair(conditions, lengths, null);
        ArrayList<TrialCondition> result = instance.getTrialConditions();
        for (int i = 0; i < conditions.size(); i++) {
            assertEquals(conditions.get(i), result.get(i));
        }
    }

    /**
     * Test of getTrialLengths method, of class PermutationPair.
     */
    @Test
    public void testGetTrialLengths() {
        System.out.println("getTrialLengths");
        ArrayList<Integer> lengths = new ArrayList<Integer>(3);
        lengths.add(3);
        lengths.add(5);
        lengths.add(6);

        ArrayList<TrialCondition> conditions = new ArrayList<TrialCondition>(3);
        conditions.add(TrialCondition.TARGET_AND_FOIL);
        conditions.add(TrialCondition.NO_TARGET);
        conditions.add(TrialCondition.TARGET_ONLY);

        PermutationPair instance = new PermutationPair(conditions, lengths, null);
        ArrayList<Integer> result = instance.getTrialLengths();
        for (int i = 0; i < lengths.size(); i++) {
            assertEquals(lengths.get(i), result.get(i));
        }
    }

    /**
     * Test of getHashedTrials method, of class PermutationPair.
     */
    @Test
    public void testGetTrials() {
        System.out.println("getTrials");
        ArrayList<Integer> lengths = new ArrayList<Integer>(3);
        lengths.add(3);
        lengths.add(5);
        lengths.add(6);

        ArrayList<TrialCondition> conditions = new ArrayList<TrialCondition>(3);
        conditions.add(TrialCondition.TARGET_AND_FOIL);
        conditions.add(TrialCondition.NO_TARGET);
        conditions.add(TrialCondition.TARGET_ONLY);

        PermutationPair instance = new PermutationPair(conditions, lengths, null);
        ArrayList<ArrayList<Trial>> result = instance.getTrials();
        assertEquals(null, result);

        // alos see initialisation
    }

    /**
     * Test of isAvailable method, of class PermutationPair.
     */
    @Test
    public void testIsAvailable() {
        System.out.println("isAvailable");
        ArrayList<Integer> lengths = new ArrayList<Integer>(3);
        lengths.add(3);
        lengths.add(5);
        lengths.add(6);

        ArrayList<TrialCondition> conditions = new ArrayList<TrialCondition>(3);
        conditions.add(TrialCondition.TARGET_AND_FOIL);
        conditions.add(TrialCondition.NO_TARGET);
        conditions.add(TrialCondition.TARGET_ONLY);

        PermutationPair instance = new PermutationPair(conditions, lengths, null);
        assertFalse(instance.isAvailable());

        ArrayList<ArrayList<PermutationPair>> list = PermutationPair.initialiseAvailabilityList(this.trialMatrix,
                this.allLengthPermuations,
                this.allConditionPermuations,
                TestConfigurationConstants.AUDIO_NUMBER_OF_BANDS);

        ArrayList<PermutationPair> middleBand = list.get(5);

        int count = 0;
        for (PermutationPair permPair : middleBand) {

            int i = count % this.tupleSize;

            if (permPair.isAvailable()) {
                if (permPair.getTrials().get(i).size() > 1) {
                    permPair.getTrials().get(i).remove(0);// remove just one candidate for the i-th element of the pair
                    assertTrue(permPair.isAvailable());
                }
            }

            permPair.getTrials().get(i).clear(); // remove all candidates for the i-th element of the pair
            assertFalse(permPair.isAvailable());
            count++;
        }

    }

}
