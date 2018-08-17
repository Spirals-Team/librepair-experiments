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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.questionnairepool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.CsvRecords;

/**
 *
 * @author olhshk
 */
public class QuestionnaireStimuliFromCsvToXml {

//    <stimulus label="Pushkin" identifier="Pushkin_abc" ratingLabels="yes,no" correctAnswer="yes" pauseMs="0" tags="quest aut 1"/>
    public String parseWordsInputCSVStringToXml(String csvString, String experimentName, String ratingLabels, String challengeColumn, String answerColumn) throws Exception {

        StringBuilder retVal = new StringBuilder();

        CsvRecords csvWrapper = new CsvRecords(null, ";", "\n");
        csvWrapper.readRecords(csvString);
        ArrayList<LinkedHashMap<String, String>> records = csvWrapper.getRecords();

        int i = 0;
        for (LinkedHashMap<String, String> record : records) {

            String label1 = record.get(challengeColumn + "1");
            if (label1 == null) {
                throw new IOException(challengeColumn + "1" + " is undefined");
            }
            label1 = label1.trim();
            String correctAnswer1 = record.get(answerColumn + "1").trim();
            i++;
            String uniqueId1 = experimentName+i;
            String currentSt1 = this.makeStimulusString(uniqueId1, label1, "questionnaire col1 " + experimentName, ratingLabels, correctAnswer1);
            retVal.append(currentSt1);

            String label2 = record.get(challengeColumn + "2");
            if (label2 == null) {
                throw new IOException(challengeColumn + "2" + " is undefined");
            }
            label2 = label2.trim();
            String correctAnswer2 = record.get(answerColumn + "2").trim();
            i++;
            String uniqueId2 = experimentName+i;
            String currentSt2 = this.makeStimulusString(uniqueId2, label2, "questionnaire col2 " + experimentName, ratingLabels, correctAnswer2);
            retVal.append(currentSt2);

            String label3 = record.get(challengeColumn + "3");
            if (label3 == null) {
                throw new IOException(challengeColumn + "3" + " is undefined");
            }
            label3 = label3.trim();
            String correctAnswer3 = record.get(answerColumn + "3").trim();
            i++;
            String uniqueId3 = experimentName+i;
            String currentSt3 = this.makeStimulusString(uniqueId3, label3, "questionnaire col3 " + experimentName, ratingLabels, correctAnswer3);
            retVal.append(currentSt3);

        }

        return retVal.toString();
    }

    private String makeStimulusString(String uniqueId, String label, String tags, String ratingLabels, String correctResp) {
        StringBuilder retVal = new StringBuilder();
        retVal.append("<stimulus ");
        retVal.append(" identifier=\"").append(uniqueId).append("\" ");
        retVal.append(" label=\"").append(label).append("\" ");
        retVal.append(" tags=\"").append(tags).append("\" ");
        retVal.append(" ratingLabels=\"").append(ratingLabels).append("\" ");
        if (correctResp != null) {
            if (!correctResp.isEmpty()) {
                if (!correctResp.equals("NEE")) {
                    retVal.append(" correctResponses=\"").append(correctResp).append("\" ");
                }
            }
        }
        retVal.append(" pauseMs=\"0\" ");

        retVal.append(" />\n");
        return retVal.toString();
    }
}
