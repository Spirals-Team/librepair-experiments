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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.advocaspool;

import java.util.ArrayList;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.RandomIndexing;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.vocabulary.AdVocAsStimulus;

/**
 *
 * @author olhshk
 */
public class Vocabulary {

    private final int numberOfBands;
    private final int wordsPerBand;

    public Vocabulary(int numberOfBands, int wordsPerBand) {
        this.numberOfBands = numberOfBands;
        this.wordsPerBand = wordsPerBand;
    }

    // the sequence of words in each band should be randomly reshuffled any time we generate it
    // side effect: also adss stimuli to the hash map
    public ArrayList<ArrayList<AdVocAsStimulus>> initialiseWords(ArrayList<ArrayList<AdVocAsStimulus>> wrds) {
        if (wrds == null || wrds.isEmpty()) {
            System.out.println("Empty array of words in bands");
            return new ArrayList<>();
        }
        ArrayList<ArrayList<AdVocAsStimulus>> retVal = new ArrayList<>(this.numberOfBands);
        for (int bandIndex = 0; bandIndex < wrds.size(); bandIndex++) {
            if (wrds.get(bandIndex) == null || wrds.get(bandIndex).isEmpty()) {
                System.out.println("Empty array of words for band " + bandIndex);
                retVal.add(new ArrayList<AdVocAsStimulus>());
            } else {
                ArrayList<Integer> index = RandomIndexing.generateRandomArray(wrds.get(bandIndex).size()); // permutations without repetitions

                retVal.add(new ArrayList<AdVocAsStimulus>(this.wordsPerBand));
                for (int i = 0; i < wrds.get(bandIndex).size(); i++) {
                    int ind = index.get(i);
                    retVal.get(bandIndex).add(wrds.get(bandIndex).get(ind));
                }
            }
        }
        return retVal;
    }

    // the sequence of nonwords should be randomly reshuffled any time we generate it
    // side effect: also adss stimuli to the hash map
    public ArrayList<AdVocAsStimulus> initialiseNonwords(ArrayList<AdVocAsStimulus> nonwrds) {
        if (nonwrds == null || nonwrds.isEmpty()) {
            System.out.println("Empty array of nonwords");
            return new ArrayList<>();
        }

        ArrayList<AdVocAsStimulus> retVal = new ArrayList<>(nonwrds.size());
        ArrayList<Integer> index = RandomIndexing.generateRandomArray(nonwrds.size());

        for (int i = 0; i < index.size(); i++) {
            int ind = index.get(i);
            retVal.add(nonwrds.get(ind));
        }
        return retVal;
    }
    
    

}
