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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.corsipool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.CsvRecords;

/**
 *
 * @author olhshk
 */
public class CorsiStimuliFromCsvToXml {

//    <stimulus audioPath="COW" code="COW" identifier="COW" imagePath="COW" pauseMs="0" tags="COW"/>
//        <stimulus audioPath="COWmis" code="COWmis" identifier="COWmis" imagePath="COWmis" pauseMs="0" tags="COW"/>
//        <stimulus audioPath="COWmis-5" code="COWmis-5" identifier="COWmis-5" imagePath="COWmis-5" pauseMs="0" tags="COW"/>
//        <stimulus audioPath="FATHER" code="FATHER" identifier="FATHER" imagePath="FATHER" pauseMs="0" tags="FATHER"/>
//        <stimulus audioPath="FATHERmis" code="FATHERmis" identifier="FATHERmis" imagePath="FATHERmis" pauseMs="0" tags="FATHER"/>
//        <stimulus audioPath="GHOST-a" code="GHOST-a" identifier="GHOST-a" imagePath="GHOST-a" pauseMs="0" tags="GHOST_a"/>
//        <stimulus audioPath="GHOST-amis" code="GHOST-amis" identifier="GHOST-amis" imagePath="GHOST-amis" pauseMs="0" tags="GHOST_a"/>
//        <stimulus audioPath="GHOST-b" code="GHOST-b" identifier="GHOST-b" imagePath="GHOST-b" pauseMs="0" tags="GHOST_b"/>
//        <stimulus audioPath="GHOST-bmis" code="GHOST-bmis" identifier="GHOST-bmis" imagePath="GHOST-bmis" pauseMs="0" tags="GHOST_b"/>
//        <stimulus audioPath="PRAY" code="PRAY" identifier="PRAY" imagePath="PRAY" pauseMs="0" tags="PRAY"/>
//        <stimulus audioPath="PRAYmis" code="PRAYmis" identifier="PRAYmis" imagePath="PRAYmis" pauseMs="0" tags="PRAY"/>
//        <stimulus audioPath="SHY" code="SHY" identifier="SHY" imagePath="SHY" pauseMs="0" tags="SHY"/>
//        <stimulus audioPath="SHYmis" code="SHYmis" identifier="SHYmis" imagePath="SHYmis" pauseMs="0" tags="SHY"/>
//   
    public String parseWordsInputCSVStringToXml(String csvString, String appName, String dirName) throws Exception {

        StringBuilder retVal = new StringBuilder();

        CsvRecords csvWrapper = new CsvRecords(null, ";", "\n");
        csvWrapper.readRecords(csvString);
        ArrayList<LinkedHashMap<String, String>> records = csvWrapper.getRecords();

        int i = 0; // global stimuli counter
        int prefixSize = "Block_".length();

        for (LinkedHashMap<String, String> record : records) {

            String trialId = record.get("Trial_id").trim();
            if (trialId == null) {
                throw new IOException("Trial_id is undefined");
            }

            String blankStimulus = record.get("Blank_stimulis").trim();
            if (blankStimulus == null) {
                throw new IOException("Blank_stimulis string is undefined");
            } else {
                blankStimulus = blankStimulus + ".jpg";
            }

            String letterStimulus = record.get("Letter_stimulus").trim();
            if (letterStimulus == null) {
                throw new IOException("Letter_stimulus is undefined");
            } else {
                letterStimulus = letterStimulus + ".jpg";
            }

            String correctSequence = record.get("Correct_sequence");

            String voiceAnnouncement = record.get("Voice_announcement").trim();
            if (voiceAnnouncement == null) {
                throw new IOException("voiceAnnouncement is missing");
            }

            String capture = record.get("Capture").trim();
            if (capture == null) {
                throw new IOException("Capture is missing");
            } 

            int j = 1;
            ArrayList<String> nonBlankStimuli = new ArrayList<String>();
            while (j <= 8) {
                String currentStrimulus = record.get("Stimulus_" + j).trim();
                if (currentStrimulus == null) {
                    break;
                }
                if (currentStrimulus.isEmpty()) {
                    break;
                }
                j++;
                nonBlankStimuli.add(currentStrimulus + ".jpg");

            }

            if (trialId.startsWith("Block_")) {
                String checkStr = trialId.substring(prefixSize, prefixSize + 1);
                int check = Integer.parseInt(checkStr);
                if (check != nonBlankStimuli.size()) {
                    throw new IOException("The trial's " + trialId + " block size " + check + " is not equal to amount of non-blank stimuli " + nonBlankStimuli.size());
                }
            }

            // voice stimulus which starts the trial 
            i++;
            String uniqueId = appName + "_" + i;
            String tags = appName + " " + trialId + " greeting active";
            String label = "&lt;span id='labelId_"+i+"'&gt; "+capture+"&lt;/span&gt;";
            //String uniqueId,
            //String label,
            //String correctResponse,
            //String imagePath, 
            //String audioPath, 
            //String dirName,
            //String tags
            String currentStimulus = makeStimulusString(uniqueId, label, null, null, voiceAnnouncement, dirName, tags);
            retVal.append(currentStimulus);

            for (int k = 0; k < nonBlankStimuli.size() - 1; k++) {
                i++; // global stimuli counter
                uniqueId = appName + "_" + i;
                label = "&lt;span id='labelId_"+i+"'&gt; "+capture+"&lt;/span&gt;";
                tags = appName + " " + trialId + " active";
                currentStimulus = makeStimulusString(uniqueId, label, null, nonBlankStimuli.get(k), null, dirName, tags);
                retVal.append(currentStimulus);
                
                i++; // global stimuli counter
                uniqueId = appName + "_" + i;
                label = "&lt;span id='labelId_"+i+"'&gt; "+capture+"&lt;/span&gt;";
                tags = appName + " " + trialId + " blank active";
                currentStimulus = makeStimulusString(uniqueId, label, null, blankStimulus, null, dirName, tags);
                retVal.append(currentStimulus);
            }

            i++; // last non blnk stimulus
            uniqueId = appName + "_" + i;
            label = "&lt;span id='labelId_"+i+"'&gt; "+capture+"&lt;/span&gt;";
            tags = appName + " " + trialId + " active";
            currentStimulus = makeStimulusString(uniqueId, label, null, nonBlankStimuli.get(nonBlankStimuli.size() - 1), null, dirName, tags);
            retVal.append(currentStimulus);
            i++; // last non blnk stimulus
            uniqueId = appName + "_" + i;
            label = "&lt;span id='labelId_"+i+"'&gt; "+capture+"&lt;/span&gt;";
            tags = appName + " " + trialId + " letters active";
            currentStimulus = makeStimulusString(uniqueId, label, correctSequence, letterStimulus, null, dirName, tags);
            retVal.append(currentStimulus);

        }

        return retVal.toString();
    }

    private String makeStimulusString(String uniqueId,
            String label,
            String correctResponse,
            String imagePath, 
            String audioPath, 
            String dirName,
            String tags) {

        StringBuilder retVal = new StringBuilder();
        retVal.append("<stimulus ");
        retVal.append(" identifier=\"").append(uniqueId).append("\" ");
        retVal.append(" label=\"").append(label).append("\" ");
        if (correctResponse != null) {
            retVal.append(" correctResponses=\"").append(correctResponse.trim()).append("\" ");
        }
        retVal.append(" pauseMs=\"0\" ");
        if (imagePath != null) {
            retVal.append(" imagePath=\"").append(dirName).append(imagePath).append("\" ");
        }
        if (audioPath != null) {
            retVal.append(" audioPath=\"").append(dirName).append(audioPath).append("\" ");
        }

        retVal.append(" tags=\"").append(tags).append("\" ");

        retVal.append(" />\n");
        return retVal.toString();

    }
}
