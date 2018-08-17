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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.CsvRecords;

/**
 *
 * @author olhshk
 */
public class PeabodyStimuliToXml {

    
    public String parseWordsInputCSVString(String stimuliDir, String csvString) throws Exception {
        
        StringBuilder retVal= new StringBuilder();
        
        
        CsvRecords csvWrapper = new CsvRecords(null, "\t", "\n");
        csvWrapper.readRecords(csvString);
        ArrayList<LinkedHashMap<String, String>> records = csvWrapper.getRecords();

        
        for (LinkedHashMap<String, String> record : records) {

            String picture = record.get("Picture").trim();
            if (picture == null) {
                throw new IOException("Image is undefined");
            }
            
            
            
            String sound = record.get("Sound").trim();
            if (sound == null) {
                throw new IOException("Sound is undefined");
            }
            
            String correctAnswer = record.get("CorrectAnswer").trim();
            if (correctAnswer == null) {
                throw new IOException("Correct is undefined");
            }

            String imagePath = picture.trim();
            String audioPath = this.removeFileExtension(sound.trim(), ".wav");
            
            String uniqueId = this.removeFileExtension(imagePath, ".png") +"_"+audioPath;
            String label = uniqueId;
            
            String[] help = imagePath.split("_");
            String set = help[0];
            imagePath = stimuliDir + imagePath;
            audioPath = stimuliDir + audioPath;
                    
            String stimulusXml = this.makeStimulusString(uniqueId, label, correctAnswer, imagePath, audioPath, set);
            
            retVal.append(stimulusXml);
            
                            //sanity check if the files exist
                String wav = audioPath+".wav";
                String mp3 = audioPath+".mp3";
                String ogg = audioPath+".ogg";
                String pathDir = "/Users/olhshk/Documents/ExperimentTemplate/gwt-cordova/src/main/static/bq4english/" ; // must be the same as in the configuration file
                try {
                    
                    BufferedReader br = new BufferedReader(new FileReader(pathDir + wav));
                    //System.out.println(audioPath);
                    br.close();
                    BufferedReader br1 = new BufferedReader(new FileReader(pathDir + mp3));
                    br1.close();
                    BufferedReader br2 = new BufferedReader(new FileReader(pathDir + ogg));
                    br2.close();
                    BufferedReader brImage = new BufferedReader(new FileReader(pathDir+imagePath));
                    brImage.close();

                } catch (FileNotFoundException ex) {
                    System.out.println();
                    System.out.println(ex);

                }
        }
         return retVal.toString();
        
    }

    
  
  private String makeStimulusString(String uniqueId,
            String label,
            String correctResponse,
            String imagePath,
            String audioPath,
            String tags) {

        StringBuilder retVal = new StringBuilder();
        retVal.append("<stimulus ");
        retVal.append(" identifier=\"").append(uniqueId).append("\" ");
        retVal.append(" label=\"").append(label).append("\" ");
        retVal.append(" correctResponses=\"").append(correctResponse).append("\" ");
        retVal.append(" imagePath=\"").append(imagePath).append("\" ");
        retVal.append(" audioPath=\"").append(audioPath).append("\" ");
        retVal.append(" pauseMs=\"0\" ");
        retVal.append(" tags=\"").append(tags).append("\" ");

        retVal.append(" />\n");
        return retVal.toString();

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
