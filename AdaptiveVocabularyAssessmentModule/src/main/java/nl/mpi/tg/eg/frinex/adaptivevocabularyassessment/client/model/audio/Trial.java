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
import java.util.LinkedHashMap;
import java.util.Set;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BookkeepingStimulus;

/**
 *
 * @author olhshk
 */
public class Trial {

    private final int trialId;
    private final ArrayList<BookkeepingStimulus<AudioAsStimulus>> stimuli;  // the first one (zero index) is the cue, it is in the order it should appear for the participant
    private final String word;
    private final String targetNonWord;
    private final int numberOfSyllables;
    private final TrialCondition condition;
    private final int lgth;
    private final int bandIndex;
    private final String bandLabel;
    private final int positionTarget;
    private final int positionFoil;
    
    public Trial(int id, String word, String targetNonWord, int nOfSyllables, TrialCondition condition, int length, String bandLabel, int bandIndex, int positionTarget, int positionFoil, ArrayList<BookkeepingStimulus<AudioAsStimulus>> stimuli) throws Exception {

        if (stimuli.size() != length + 1) {
            throw new Exception("Amount of stimuli " + stimuli.size() + " does not correspond to the specification " + "1 (cue) + " + length + " stimuli.");
        }

        this.trialId = id;
        this.word = word;
        this.targetNonWord = targetNonWord;
        this.numberOfSyllables = nOfSyllables;
        this.lgth = length;
        this.bandLabel = bandLabel;
        this.bandIndex = bandIndex;
        this.condition = condition;
        this.positionTarget = positionTarget;
        this.positionFoil = positionFoil;
        this.stimuli = stimuli;

    }

    public String getWord() {
        return this.word;
    }

    public int getId() {
        return this.trialId;
    }

    public int getBandIndex() {
        return this.bandIndex;
    }

    public String getBandLabel() {
        return this.bandLabel;
    }

    public String getTargetNonWord() {
        return this.targetNonWord;
    }

    public int getNumberOfSyllables() {
        return this.numberOfSyllables;
    }

    public TrialCondition getCondition() {
        return this.condition;
    }

    public int getTrialLength() {
        return this.lgth;
    }

    public int getPositionTarget() {
        return this.positionTarget;
    }

    public int getPositionFoil() {
        return this.positionFoil;
    }

    public ArrayList<BookkeepingStimulus<AudioAsStimulus>> getStimuli() {
        return this.stimuli;
    }

    public static ArrayList<ArrayList<LinkedHashMap<TrialCondition, ArrayList<Trial>>>> prepareTrialMatrix(LinkedHashMap<Integer, Trial> csvTrials, int numberOfBands, int maxTrialLength) {
        ArrayList<ArrayList<LinkedHashMap<TrialCondition, ArrayList<Trial>>>> retVal = initMatrix(numberOfBands, maxTrialLength);
        Set<Integer> keys = csvTrials.keySet();
        for (Integer key:keys) {
            Trial trial = csvTrials.get(key);
            TrialCondition condition = trial.getCondition();
            int bandIndex = trial.getBandIndex();
            int trialLength = trial.getTrialLength();
            ArrayList<Trial> listToInsert = retVal.get(bandIndex).get(trialLength).get(condition);
            listToInsert.add(trial);            
        }
        return retVal;
    }

    private static ArrayList<ArrayList<LinkedHashMap<TrialCondition, ArrayList<Trial>>>> initMatrix(int numbOfBands, int maxLength) {
        ArrayList<ArrayList<LinkedHashMap<TrialCondition, ArrayList<Trial>>>> retVal = new ArrayList<ArrayList<LinkedHashMap<TrialCondition, ArrayList<Trial>>>>(numbOfBands);

        for (int i = 0; i < numbOfBands; i++) {
            ArrayList<LinkedHashMap<TrialCondition, ArrayList<Trial>>> trialslForBand = new ArrayList<LinkedHashMap<TrialCondition, ArrayList<Trial>>>(maxLength + 1);
            retVal.add(i, trialslForBand);

            for (int j = 0; j <= maxLength; j++) {

                LinkedHashMap<TrialCondition, ArrayList<Trial>> trialslForLength = new LinkedHashMap<TrialCondition, ArrayList<Trial>>();
                trialslForBand.add(j, trialslForLength);

                for (TrialCondition trialType : TrialCondition.values()) {
                    trialslForLength.put(trialType, new ArrayList<Trial>());
                }
            }

        }
        return retVal;
    }

    @Override
    public String toString() {
         return String.valueOf(this.getId());
    }

}
