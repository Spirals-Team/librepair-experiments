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
import java.util.Map;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BookkeepingStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.UtilsJSONdialect;

/**
 *
 * @author olhshk
 */
public class Trial {

    private final int trialId;
    private final ArrayList<BookkeepingStimulus<AudioAsStimulus>> stimuli;  // the first one (zero index) is the cue, it is in the order it should appear for the participant
    private final String word;
    private final String cueFile;
    private final int numberOfSyllables;
    private final TrialCondition condition;
    private final int lgth;
    private final int bandIndex;
    private final String bandLabel;

    public Trial(int id, String word, String cueFile, int nOfSyllables, TrialCondition condition, int length, String bandLabel, int bandIndex) {
        this.trialId = id;
        this.word = word;
        this.cueFile = cueFile;
        this.numberOfSyllables = nOfSyllables;
        this.lgth = length;
        this.bandLabel = bandLabel;
        this.bandIndex = bandIndex;
        this.condition = condition;
        this.stimuli = new ArrayList<BookkeepingStimulus<AudioAsStimulus>>(length + 1);
        for (int i=0; i<length+1; i++){
           this.stimuli.add(null); 
        }
    }

    public void addStimulus(BookkeepingStimulus<AudioAsStimulus> stimulus, int stimulusPosition){
        this.stimuli.set(stimulusPosition, stimulus);
    }

    public ArrayList<BookkeepingStimulus<AudioAsStimulus>> getStimuli() {
        return this.stimuli;
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
        return this.cueFile;
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

  

    @Override
    public String toString() {
    
//    private final int trialId;
//    private final ArrayList<String> stimulusIDs;  // the first one (zero index) is the cue, it is in the order it should appear for the participant
//    private final String word;
//    private final String cueFile;
//    private final int numberOfSyllables;
//    private final TrialCondition condition;
//    private final int lgth;
//    private final int bandIndex;
//    private final String bandLabel;
        
StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("word:{").append(this.word).append("},");
        builder.append("cueFile:{").append(this.cueFile).append("},");
        builder.append("numberOfSyllables:{").append(this.numberOfSyllables).append("},");
        builder.append("lgth:{").append(this.lgth).append("},");
        builder.append("bandIndex:{").append(this.bandIndex).append("},");
        builder.append("bandLabel:{").append(this.bandLabel).append("},");
        builder.append("condition:{").append(this.condition).append("},");
        UtilsJSONdialect<BookkeepingStimulus<AudioAsStimulus>> util = new UtilsJSONdialect<BookkeepingStimulus<AudioAsStimulus>>();
        String stimulusIDsStr = "";
        try {
            stimulusIDsStr = util.arrayListToString(this.stimuli);
        } catch (Exception ex) {

        }
        builder.append("stimuli:").append(stimulusIDsStr);
        builder.append("}");
        return builder.toString();

    }

    public static Trial toObject(String str, LinkedHashMap<String, AudioAsStimulus> hashedStimuli) {
        try {
            String idStr = UtilsJSONdialect.getKeyWithoutBrackets(str, "trialId");
            
            String word = UtilsJSONdialect.getKeyWithoutBrackets(str, "word");
            String cueFile = UtilsJSONdialect.getKeyWithoutBrackets(str, "cueFile");
            
            String numberOfSyllablesStr = UtilsJSONdialect.getKeyWithoutBrackets(str, "numberOfSyllables");
            String lgthStr = UtilsJSONdialect.getKeyWithoutBrackets(str, "lgth");
            String bandIndexStr = UtilsJSONdialect.getKeyWithoutBrackets(str, "bandIndex");
            String bandLabel = UtilsJSONdialect.getKeyWithoutBrackets(str, "bandLabel");
            String conditionStr = UtilsJSONdialect.getKeyWithoutBrackets(str, "condition");
            
            int numberOfSyllables = Integer.parseInt(numberOfSyllablesStr);
            int lgth = Integer.parseInt(lgthStr);
            int bandIndex = Integer.parseInt(bandIndexStr);
            int id = Integer.parseInt(idStr);
            TrialCondition condition = TrialCondition.valueOf(conditionStr);

            String stimuliStr = UtilsJSONdialect.getKey(str, "stimuli");
            
            UtilsJSONdialect<BookkeepingStimulus<AudioAsStimulus>> util = new UtilsJSONdialect<BookkeepingStimulus<AudioAsStimulus>>();
            ArrayList<String> bStimuli = util.stringToArrayList(stimuliStr);
           
            //Trial(int id, String word, String cueFile, int nOfSyllables, TrialCondition condition, int length, String bandLabel, int bandIndex)
            Trial retVal = new Trial(id, word, cueFile, numberOfSyllables, condition, lgth, bandLabel, bandIndex);
            for (int i=0; i<bStimuli.size(); i++) {
                BookkeepingStimulus<AudioAsStimulus> ghost = new BookkeepingStimulus<AudioAsStimulus>(null);
                BookkeepingStimulus<AudioAsStimulus> bStimulus = ghost.toObject(bStimuli.get(i), hashedStimuli);
                int position =  bStimulus.getStimulus().getPositionInTrialInt();
                retVal.addStimulus(bStimulus, position);
            }
            
            return retVal;
        } catch (Exception ex) {
            return null;
        }

    }

    public static String map3ToString(Map<TrialCondition, ArrayList<ArrayList<ArrayList<Trial>>>> map) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        UtilsJSONdialect<Trial> utils = new UtilsJSONdialect<Trial>();
        TrialCondition[] conditions = TrialCondition.values();
        int i=0;
        for (TrialCondition condition : conditions) {
            builder.append(condition).append(":");
            ArrayList<ArrayList<ArrayList<Trial>>> list = map.get(condition);
            try {
                String listStr = utils.arrayList3String(list);
                builder.append(listStr);
                if (i < conditions.length-1) {
                  builder.append(",");  
                }
            } catch (Exception ex) {
                return null;
            }
            i++;
        }
        builder.append("}");
        return builder.toString();
    }
    
   

}
