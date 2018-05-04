/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.UtilsJSONdialect;

/**
 * produces the collection of [p * N] indices(for non-word) from 0 to N-1, where
 * p = 1/n is the probability for a non word to appear.
 *
 * @author olhshk
 */
public class RandomIndexing {

    private static final String[] FLDS = {"fastTrackSequenceLength", "averageNonwordPosition", "nonwordsPerBlock", "numberOfNonwords", "numberOfWords", "randomIndices", "frequences"};

    private int fastTrackSequenceLength; //  N
    private final int averageNonwordPosition; // n
    private final int nonwordsPerBlock;
    private final int numberOfNonwords; // [1/n * N]
    private final int numberOfWords; // [(n-1)/n * N]
    private ArrayList<Integer> randomIndices;
    private ArrayList<Double> frequences;

    public RandomIndexing(int startBand, int numberOfBands, int nonwordsPerBlock, int averageNonwordPosition, int nonwordsAvailable) {
        this.averageNonwordPosition = averageNonwordPosition;
        this.nonwordsPerBlock = nonwordsPerBlock;
        int help = numberOfBands - startBand + 1;
        int wordsAvailable = help * 2;// one from each band, possibly stying 2 times on one band because of the second chance
        // number of nonwords = 1/n * fastTrackSequenceLength
        // number of words = (n-1)/n * fastTrackSequenceLength
        this.fastTrackSequenceLength = (averageNonwordPosition * wordsAvailable) / (averageNonwordPosition - 1);
        if (this.fastTrackSequenceLength > nonwordsAvailable) {
            this.fastTrackSequenceLength = nonwordsAvailable;
            this.numberOfWords = (this.fastTrackSequenceLength * (averageNonwordPosition - 1)) / averageNonwordPosition;
        } else {
            this.numberOfWords = wordsAvailable;
        }

        this.numberOfNonwords = this.fastTrackSequenceLength - this.numberOfWords;
    }

    public RandomIndexing(int fastTrackSequenceLength, int averageNonwordPosition, int nonwordsPerBlock, int numberOfNonwords, int numberOfWords, ArrayList<Integer> randomIndices, ArrayList<Double> frequences) {
        this.fastTrackSequenceLength = fastTrackSequenceLength;
        this.averageNonwordPosition = averageNonwordPosition;
        this.nonwordsPerBlock = nonwordsPerBlock;
        this.numberOfNonwords = numberOfNonwords;
        this.numberOfWords = numberOfWords;
        this.randomIndices = randomIndices;
        this.frequences = frequences;
    }

    public int getNumberOfNonWords() {
        return this.numberOfNonwords;
    }

    public int getNumberOfWords() {
        return this.numberOfWords;
    }

    // we divide all the indices from 0 to fastTrackSequenceLength-1 on blocks,
    // each block has size nonwordsPerBlock*averageNonwordPosition positions
    // and we pick nonwordsPerBlock positions for non-words in each block
    private ArrayList<Integer> calculateRandomIndices() {

        ArrayList<Integer> retVal = new ArrayList<>();
        int randOffset;
        int blockSize = this.nonwordsPerBlock * this.averageNonwordPosition;
        int numberOfBlocks = this.fastTrackSequenceLength / blockSize;
        Random rnd = new Random();
        for (int i = 0; i < numberOfBlocks; i++) {
            ArrayList<Integer> offsetBuffer = new ArrayList<>(blockSize - 1); // the last element in the block should be always a word to avoid 2 nonwords in a row

            for (int j = 0; j < blockSize - 1; j++) {
                offsetBuffer.add(j);
            }
            for (int k = 0; k < this.nonwordsPerBlock; k++) {
                int n = offsetBuffer.size();
                int indOffset = rnd.nextInt(n); // excl.n
                int offset = offsetBuffer.get(indOffset);
                retVal.add(i * blockSize + offset);
                offsetBuffer.remove(new Integer(offset));
                offsetBuffer.remove(new Integer(offset - 1)); // avoiding neigbouring nonwords
                offsetBuffer.remove(new Integer(offset + 1));
            }

        }

        // if there are positions to pick up left
        int remainderBlock = this.fastTrackSequenceLength - blockSize * numberOfBlocks;
        ArrayList<Integer> offsetBuffer = new ArrayList<>(remainderBlock);
        for (int j = 0; j < remainderBlock; j++) {
            offsetBuffer.add(j);
        }
        int n = remainderBlock;
        int nonwordsRemainder = this.numberOfNonwords - retVal.size();
        for (int k = 0; k < nonwordsRemainder; k++) {
            randOffset = rnd.nextInt(n);
            retVal.add(blockSize * numberOfBlocks + offsetBuffer.get(randOffset));
            offsetBuffer.remove(randOffset);
            n--;
        }
        ////

        Collections.sort(retVal);
        this.correctLastPosition(retVal);

        return retVal;
    }

    // the last positin in any sequence must be always a word (from a band 54)
    private void correctLastPosition(ArrayList<Integer> sortedIndices) {
        int lastNonwordPosition = sortedIndices.get(sortedIndices.size() - 1);
        if (lastNonwordPosition == this.fastTrackSequenceLength - 1) {
            int wordAt = this.findLastWordPositionWithGap(sortedIndices, sortedIndices.size() - 1);
            sortedIndices.set(sortedIndices.size() - 1, wordAt);
        }
    }

    private int findLastWordPositionWithGap(ArrayList<Integer> sortedIndices, int n) {
        // look for the first gap
        while (sortedIndices.get(n) - sortedIndices.get(n - 1) <= 2) {
            n--;
        }
        return sortedIndices.get(n) - 1;
    }

    private int amountOfSelectedIndecesBetween(int a, int b) {
        int retVal = 0;
        for (int i = a; i <= b; i++) {
            if (this.randomIndices.contains(i)) {
                retVal++;
            }
        }
        return retVal;
    }

    private ArrayList<Double> calculateFrequencesOfNonWordIndices() {
        ArrayList<Double> retVal = new ArrayList<Double>(this.fastTrackSequenceLength);
        int nonwordsCounter;
        for (int i = 0; i < this.fastTrackSequenceLength; i++) {
            nonwordsCounter = amountOfSelectedIndecesBetween(0, i);
            retVal.add(i, ((double) nonwordsCounter) / ((double) (i + 1)));
        }
        return retVal;
    }

    public ArrayList<Integer> updateAndGetIndices() {
        this.randomIndices = this.calculateRandomIndices();
        return this.randomIndices;
    }

    public ArrayList<Integer> getIndices() {
        return this.randomIndices;
    }

    public void updateFrequencesOfNonWordIndices() {
        this.frequences = this.calculateFrequencesOfNonWordIndices();
    }

    public ArrayList<Double> getFrequencesOfNonWordindices() {
        return this.frequences;
    }

    public int getFastTrackSequenceLength() {
        return this.fastTrackSequenceLength;
    }

    public static ArrayList<Integer> generateRandomArray(int n) {
        ArrayList<Integer> tmp = new ArrayList<Integer>(n);
        for (int i = 0; i < n; i++) {
            tmp.add(i);
        }
        ArrayList<Integer> retVal = new ArrayList<Integer>(n);
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            int j = r.nextInt(n - i);
            Integer x = tmp.remove(j);
            retVal.add(x);
        }
        return retVal;
    }

    @Override
    public String toString() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("fields", Arrays.asList(RandomIndexing.FLDS).toString());
        map.put("fastTrackSequenceLength", this.fastTrackSequenceLength);
        map.put("averageNonwordPosition", this.averageNonwordPosition);
        map.put("nonwordsPerBlock", this.nonwordsPerBlock);
        map.put("numberOfNonwords", this.numberOfNonwords);
        map.put("numberOfWords", this.numberOfWords);
        if (this.randomIndices == null) {
            map.put("randomIndices", null);
        } else {
            map.put("randomIndices", this.randomIndices);
        
        }
        if (this.frequences == null) {
            map.put("frequences", null);
        } else {
            map.put("frequences", this.frequences);
        }
        return map.toString();

    }

    public static RandomIndexing mapToObject(Map<String, Object> map) {
        try {
            int fastTrackSequenceLength1 = Integer.parseInt(map.get("fastTrackSequenceLength").toString());
            int averageNonwordPosition1 = Integer.parseInt(map.get("averageNonwordPosition").toString());
            int nonwordsPerBlock1 = Integer.parseInt(map.get("nonwordsPerBlock").toString());
            int numberOfNonwords1 = Integer.parseInt(map.get("numberOfNonwords").toString());
            int numberOfWords1 = Integer.parseInt(map.get("numberOfWords").toString());
            Object randomIndices1obj = map.get("randomIndices");
            ArrayList<Integer> randomIndices1 = UtilsJSONdialect.objectToListInteger(randomIndices1obj);
            Object frequences1obj = map.get("frequences");
            ArrayList<Double> frequences1 = UtilsJSONdialect.objectToListDouble(frequences1obj);
            // RandomIndexing(int fastTrackSequenceLength, int  averageNonwordPosition, int nonwordsPerBlock, int numberOfNonwords, int numberOfWords, ArrayList<Integer> randomIndices, double[] frequences)
            RandomIndexing retVal = new RandomIndexing(fastTrackSequenceLength1, averageNonwordPosition1, nonwordsPerBlock1, numberOfNonwords1, numberOfWords1, randomIndices1, frequences1);
            return retVal;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }

    }

    public static RandomIndexing toObject(String str) {
        try {
            Map<String, Object> map = UtilsJSONdialect.stringToObjectMap(str, RandomIndexing.FLDS);
            return mapToObject(map);
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }
}
