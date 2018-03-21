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
import java.util.HashMap;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.RandomIndexing;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.vocabulary.AdVocAsStimulus;

/**
 *
 * @author olhshk
 */
public class Vocabulary {

    public static final String NONWORD = "NEE&#44; ik ken dit woord niet";

    public static final String WORD = "JA&#44; ik ken dit woord";

    private final int numberOfBands;
    private final int wordsPerBandInSeries;
    
    private ArrayList<ArrayList<AdVocAsStimulus>> wordsInBands;
    private ArrayList<AdVocAsStimulus> nonWords;
    
   
    private HashMap<String, AdVocAsStimulus> hashedStimuli;


    public Vocabulary(int numberOfBands, int wordsPerBandInSeries) {
        this.numberOfBands = numberOfBands;
        this.wordsPerBandInSeries = wordsPerBandInSeries;
    }
    
  
    public ArrayList<ArrayList<AdVocAsStimulus>> getWordsInBands() {
        return this.wordsInBands;
    }

    public ArrayList<AdVocAsStimulus> getNonwords() {
        return this.nonWords;
    }

    public HashMap<String, AdVocAsStimulus> getHashedStimuli() {
        return this.hashedStimuli;
    }

    

    public void initialise(AdVocAsStimulus[] stimuli) {
        if (stimuli == null || stimuli.length==0) {
            System.out.println("Empty array of words in bands");
            return;
        }
        
        this.wordsInBands = new ArrayList<>(this.numberOfBands);
        this.nonWords  = new ArrayList<>();
        this.hashedStimuli = new HashMap<String,AdVocAsStimulus>();
        // initialisation
        for (int bandIndex = 0; bandIndex < this.numberOfBands; bandIndex++) {
            this.wordsInBands.add(new ArrayList<AdVocAsStimulus>(this.wordsPerBandInSeries));
        }

        ArrayList<Integer> permuteIndex = RandomIndexing.generateRandomArray(stimuli.length);
        
        for (int i=0; i<stimuli.length; i++) {
            AdVocAsStimulus stimulus = stimuli[permuteIndex.get(i)];
            String correctResponse = stimulus.getCorrectResponses();
            if (correctResponse.equals(NONWORD)) {
                this.nonWords.add(stimulus);
            } else {
                if (correctResponse.equals(WORD)) {
                    int index = stimulus.getBandIndexInt();
                    ArrayList<AdVocAsStimulus> bandStimuli = this.wordsInBands.get(index);
                    bandStimuli.add(stimulus);
                }
            }
         this.hashedStimuli.put(stimulus.getUniqueId(), stimulus);  
        }

    }

}
