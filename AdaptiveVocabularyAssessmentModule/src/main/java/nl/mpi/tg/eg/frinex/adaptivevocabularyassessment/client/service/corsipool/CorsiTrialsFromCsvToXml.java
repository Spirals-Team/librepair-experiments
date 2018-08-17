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
public class CorsiTrialsFromCsvToXml {
//   

    public String generateCSVStringToXml(String csvString) throws Exception {

        StringBuilder retVal = new StringBuilder();

        CsvRecords csvWrapper = new CsvRecords(null, ";", "\n");
        csvWrapper.readRecords(csvString);
        ArrayList<LinkedHashMap<String, String>> records = csvWrapper.getRecords();

        for (LinkedHashMap<String, String> record : records) {

            String uniqueId = record.get("Trial_id").trim();
            if (uniqueId == null) {
                throw new IOException("Trial_id is undefined");
            }

            String correctResponse = record.get("Correct_sequence");

            String label = record.get("Capture").trim();
            if (label == null) {
                throw new IOException("Capture is missing");
            }

            //String uniqueId,
            //String label,
            //String correctResponse,
            //String code
            //String tags
            String code = uniqueId;
            String tags = "corsi active";
            String currentStimulus = this.makeStimulusString(uniqueId, label, correctResponse, code, tags);
            retVal.append(currentStimulus);

        }
        return retVal.toString();
    }

    private String makeStimulusString(String uniqueId,
            String label,
            String correctResponse,
            String code,
            String tags) {

        StringBuilder retVal = new StringBuilder();
        retVal.append("<stimulus ");
        retVal.append(" identifier=\"").append(uniqueId).append("\" ");
        retVal.append(" label=\"").append(label).append("\" ");
        retVal.append(" correctResponses=\"").append(correctResponse).append("\" ");
        retVal.append(" code=\"").append(code).append("\" ");
        retVal.append(" tags=\"").append(tags).append("\" ");

        retVal.append(" />\n");
        return retVal.toString();

    }
}
