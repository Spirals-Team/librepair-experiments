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
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.CsvRecords;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.peabody.PeabodyStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.PeabodyStimuliProvider;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;

/**
 *
 * @author olhshk
 */
public class PeabodyStimuliFromString {

    private final LinkedHashMap<String, PeabodyStimulus> hashedStimuli = new LinkedHashMap<String, PeabodyStimulus>();
    private final  ArrayList<ArrayList<PeabodyStimulus>> stimuliByBands = new ArrayList<ArrayList<PeabodyStimulus>>();
  
    public void parseWordsInputCSVString(final PeabodyStimuliProvider provider, int numberOfBands, String stimuliDir) throws Exception {
        
        
        String csvString = CsvTable.CSV_STRING_NL;
        
        for (int i=0; i<numberOfBands; i++) {
            this.stimuliByBands.add(new ArrayList<PeabodyStimulus>());
        }

        CsvRecords csvWrapper = new CsvRecords(null, "\t", "\n");
        csvWrapper.readRecords(csvString);
        ArrayList<LinkedHashMap<String, String>> records = csvWrapper.getRecords();

        
        for (LinkedHashMap<String, String> record : records) {

            String picture = record.get("Picture").trim();
            if (picture == null) {
                throw new IOException(picture + "is undefined");
            }
            
            
            
            String sound = record.get("Sound").trim();
            if (sound == null) {
                throw new IOException(sound + "is undefined");
            }
            
            String correctAnswer = record.get("CorrectAnswer").trim();
            if (correctAnswer == null) {
                throw new IOException(correctAnswer + "is undefined");
            }

            String imagePath = picture.trim();
            String audioPath = this.removeFileExtension(sound.trim(), ".wav");
            
            String uniqueId = this.removeFileExtension(imagePath, ".png") +"_"+audioPath;
            String label = uniqueId;
            
            String[] helpBandLabel = imagePath.split("_");
            String set = helpBandLabel[0];
            int bandIndex = Indices.SET_TO_BAND_INDEX.get(set);
            
            imagePath = stimuliDir + imagePath;
            audioPath = stimuliDir + audioPath;
                    
            // PeabodyStimulus(String uniqueId, Tag[] tags, String label, String code, int pauseMs, String audioPath, String videoPath, String imagePath, String ratingLabels, String correctResponses, String set, int bandIndex)
            PeabodyStimulus stimulus = new PeabodyStimulus(uniqueId,new Stimulus.Tag[0], label, "",  0, audioPath, "", imagePath, "1,2,3,4", correctAnswer.trim(), set, bandIndex){
                    @Override
                    public boolean isCorrect(String value) {
                        return provider.isCorrectResponse(this, value);
                    }
                };
            this.stimuliByBands.get(bandIndex).add(stimulus);
            this.hashedStimuli.put(uniqueId, stimulus);
        }
    }

    
  

    public LinkedHashMap<String, PeabodyStimulus> getHashedStimuli() {
        return this.hashedStimuli;
    }
    
     public  ArrayList<ArrayList<PeabodyStimulus>> getStimuliByBands(){
         return this.stimuliByBands;
     }

  
    public String removeFileExtension(String name, String extension) {
        if (name.endsWith(extension)) {
            String retVal = name.substring(0, name.length()-extension.length());
            return retVal;
        } else {
            return name;
        }
    }
  
    
}
