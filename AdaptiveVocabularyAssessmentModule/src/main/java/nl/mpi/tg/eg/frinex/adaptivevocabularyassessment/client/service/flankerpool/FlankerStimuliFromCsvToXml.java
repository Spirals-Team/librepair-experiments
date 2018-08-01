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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.flankerpool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.CsvRecords;

/**
 *
 * @author olhshk
 */
public class FlankerStimuliFromCsvToXml {

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
    public String parseWordsInputCSVStringToXml(String csvString, String appName, String roundName) throws Exception {

        StringBuilder retVal = new StringBuilder();
        String ratingLabels = "&lt;,&gt;";

        CsvRecords csvWrapper = new CsvRecords(null, "\t", "\n");
        csvWrapper.readRecords(csvString);
        ArrayList<LinkedHashMap<String, String>> records = csvWrapper.getRecords();

        // Block Stimulus Condition Correct_response Expected_button
        int i = 0;
        for (LinkedHashMap<String, String> record : records) {
            i++;
            StringBuilder currentSt = new StringBuilder();
            currentSt.append("<stimulus ");

            String block = record.get("Block").trim();
            if (block == null) {
                throw new IOException("Block is undefined");
            } else {
                block = "block_"+block;
            }

            String label = record.get("Stimulus").trim();
            if (label == null) {
                throw new IOException("Stimulus string is undefined");
            }

            String condition = record.get("Condition").trim();
            if (condition == null) {
                throw new IOException("Condition is undefined");
            }

            String expectedButton = record.get("Expected_button").trim();
            if (expectedButton == null) {
                throw new IOException("Expected_button");
            }

            String correctResponseString = record.get("Correct_response").trim();
            String correctResponse = null;
            if (correctResponseString == null) {
                throw new IOException(correctResponseString + "is undefined");
            }
            if (correctResponseString.equals("left")) {
                correctResponse = "&lt;";
            } else {
                if (correctResponseString.equals("right")) {
                    correctResponse = "&gt;";
                } else {
                    throw new IOException("Illegal correct response: " + correctResponseString);
                }
            }

            String uniqueId = appName + "_" + roundName + "_" + i;

            label = label.replaceAll("<", "&lt;");
            label = label.replaceAll(">", "&gt;");
            label = "&lt;span id='labelId_"+i+"'&gt; "+label+"&lt;/span&gt;";
            
            currentSt.append(" identifier=\"").append(uniqueId).append("\" ");
            currentSt.append(" label=\"").append(label).append("\" ");
            currentSt.append(" tags=\"").append(appName).append(" ").append(roundName).append(" ").append(block).append(" ").append(condition).append(" ").append(correctResponseString).append(" ").append(expectedButton).append(" ").append("\" ");
            currentSt.append(" ratingLabels=\"").append(ratingLabels).append("\" ");
            currentSt.append(" correctResponses=\"").append(correctResponse).append("\" pauseMs=\"0\"");

            currentSt.append(" />\n");
            retVal.append(currentSt);
        }

        return retVal.toString();
    }

}
