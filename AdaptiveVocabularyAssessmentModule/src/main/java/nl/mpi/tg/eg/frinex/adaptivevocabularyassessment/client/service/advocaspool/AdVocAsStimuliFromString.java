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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.advocaspool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.CsvRecords;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.vocabulary.AdVocAsStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.AdVocAsStimuliProvider;

/**
 *
 * @author olhshk
 */
public class AdVocAsStimuliFromString {

    private final LinkedHashMap<String, AdVocAsStimulus> hashedStimuli = new LinkedHashMap<String, AdVocAsStimulus>();
    private ArrayList<ArrayList<AdVocAsStimulus>> words;
    private ArrayList<AdVocAsStimulus> nonwords;
    
  
    public void parseWordsInputCSVString(final AdVocAsStimuliProvider provider, String classNameWord, String classNameNonWord, int numberOfBands) throws Exception {
        
        String answerNonWord = SourcenameIndices.RESPONSES_INDEX.get(classNameNonWord);
        String answerWord = SourcenameIndices.RESPONSES_INDEX.get(classNameWord);
        
        String csvString = SourcenameIndices.STIMULI_FILES_INDEX.get(classNameWord).CSV_STRING;
        
        this.words = new ArrayList<ArrayList<AdVocAsStimulus>>();
        for (int i=0; i<numberOfBands; i++) {
            this.words.add(new ArrayList<AdVocAsStimulus>());
        }

        CsvRecords csvWrapper = new CsvRecords(null, ";", "\n");
        csvWrapper.readRecords(csvString);
        ArrayList<LinkedHashMap<String, String>> records = csvWrapper.getRecords();

        
        for (LinkedHashMap<String, String> record : records) {

            String bandNumber = record.get("BandNumber").trim();
            if (bandNumber == null) {
                throw new IOException(bandNumber + "is undefined");
            }
            
            int bNumber = Integer.parseInt(bandNumber);
            
            String label = record.get("Spelling").trim();
            if (label == null) {
                throw new IOException(" Seplling is undefined");
            }

            //long time = System.currentTimeMillis();
            //String uniqueId = label+"_"+time;
            String uniqueId = label;
            AdVocAsStimulus stimulus = new AdVocAsStimulus(uniqueId,label, answerNonWord + ","+answerWord,  answerWord, bNumber){
                @Override 
                public boolean isCorrect(String value) {
                    return provider.isCorrectResponse(this, value);
                }
            };
            this.words.get(bNumber-1).add(stimulus);
            this.hashedStimuli.put(uniqueId, stimulus);
        }
    }

    
    public void parseNonWordsInputCSVString(final AdVocAsStimuliProvider provider, String classNameNonWord, String classNameWord) throws Exception {
        
        String answerNonWord = SourcenameIndices.RESPONSES_INDEX.get(classNameNonWord);
        String answerWord = SourcenameIndices.RESPONSES_INDEX.get(classNameWord);
        
        String csvString = SourcenameIndices.STIMULI_FILES_INDEX.get(classNameNonWord).CSV_STRING;

        this.nonwords = new ArrayList<AdVocAsStimulus>();
      
        CsvRecords csvWrapper = new CsvRecords(null, ";", "\n");
        csvWrapper.readRecords(csvString);
        ArrayList<LinkedHashMap<String, String>> records = csvWrapper.getRecords();

        
        for (LinkedHashMap<String, String> record : records) {

         
            String label = record.get("Spelling").trim();
            if (label == null) {
                throw new IOException(" Seplling is undefined");
            }

            //long time = System.currentTimeMillis();
            //String uniqueId = label+"_"+time;
            String uniqueId = label;
            AdVocAsStimulus stimulus = new AdVocAsStimulus(uniqueId,label, answerNonWord + ","+answerWord,  answerNonWord, 0){
                @Override 
                public boolean isCorrect(String value) {
                    return provider.isCorrectResponse(this, value);
                }
            };
            this.nonwords.add(stimulus);
            this.hashedStimuli.put(uniqueId, stimulus);
        }
    }
   

    public LinkedHashMap<String, AdVocAsStimulus> getHashedStimuli() {
        return this.hashedStimuli;
    }

    public ArrayList<ArrayList<AdVocAsStimulus>> getWords() {
        return this.words;
    }
    
    public ArrayList<AdVocAsStimulus> getNonwords() {
        return this.nonwords;
    }
    
    
}
