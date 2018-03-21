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
package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author olhshk
 */
public class AudioStimuliFromFiles {

    public ArrayList<String> bandIndex = new ArrayList<String>();

    //Nr;Word;Target_nonword;Syllables;Condition;Length_list;Word1;Word2;Word3;Word4;Word5;Word6;Position_target;Noise_level;Position_foil;
    public String parseTrialsInputCSV(String fileLocation, ArrayList<String> fileNameExtensions, HashMap<String, String> bandIndexing) throws IOException {

        File inputFileWords = new File(fileLocation);
        final Reader reader = new InputStreamReader(inputFileWords.toURL().openStream(), "UTF-8"); // todo: this might need to change to "ISO-8859-1" depending on the usage
        Iterable<CSVRecord> records = CSVFormat.newFormat(';').withHeader().parse(reader);

        StringBuilder retVal = new StringBuilder();

        for (CSVRecord record : records) {

            String trialNumber = record.get("Nr").trim();
            if (trialNumber == null) {
                throw new IOException(trialNumber + "is undefined");
            }

            String trialWord = record.get("Word").trim();
            if (trialWord == null) {
                throw new IOException(trialWord + "is undefined");
            }

            String trialTargetNonword = record.get("Target_nonword").trim();
            if (trialTargetNonword == null) {
                throw new IOException(trialTargetNonword + "is undefined");
            }
            

            String trialSyllables = record.get("Syllables").trim();
            if (trialSyllables == null) {
                throw new IOException(trialSyllables + "is undefined");
            }

            String trialCondition = record.get("Condition").trim();
            if (trialCondition == null) {
                throw new IOException(trialCondition + "is undefined");
            }
            String trialLength = record.get("Length_list").trim().substring(0, 1);
            if (trialLength == null) {
                throw new IOException(trialLength + "is undefined");
            }
            int trialLengthInt = Integer.parseInt(trialLength);

            ArrayList<String> words = new ArrayList<String>(trialLengthInt + 1);
            for (int i = 0; i < trialLengthInt + 1; i++) {
                words.add("");
            }

            words.set(0, trialTargetNonword);

            for (int i = 1; i <= trialLengthInt; i++) {
                String filedName = "Word" + i;
                String currentWord = record.get(filedName).trim();
                if (currentWord == null) {
                    throw new IOException(currentWord + "is undefined");
                }
                words.set(i, currentWord);
            }

            String trialPositionTarget = record.get("Position_target").trim();
            if (trialTargetNonword == null) {
                throw new IOException(trialTargetNonword + "is undefined");
            }
            int trialPositionTargetInt = Integer.parseInt(trialPositionTarget);
            if (trialPositionTarget == null) {
                throw new IOException(trialPositionTarget + "is undefined");
            }

            String trialPositionFoil = record.get("Position_foil").trim();
            if (trialPositionFoil == null) {
                throw new IOException(trialPositionFoil + "is undefined");
            }
            int trialPositionFoilInt = Integer.parseInt(trialPositionFoil);

            String trialBandLabel = record.get("Noise_level").trim();
            if (trialBandLabel == null) {
                throw new IOException(trialBandLabel + "is undefined");
            }

// <stimulus audioPath="deebral" code="deebral" identifier="deebral" pauseMs="900" ratingLabels="" correctResponses="" tags="round1" positionInTrial="1" wordType="NON_WORD" 
//trialNumber="1" trialWord="vloer" trialTargetNonword="smoer_1" trialSyllables="1" trialCondition="Target-only" trialLength="3" trialPositionTarget="2" 
// bandLabel="plus10db" bandIndex="3" trialPositionFoil="0"/>
            for (int i = 0; i < trialLengthInt; i++) {

                StringBuilder strBuilder = new StringBuilder();
                strBuilder.append("<stimulus ");

                String wrd = this.removeFileNameExtensions(words.get(i), fileNameExtensions);
                strBuilder.append("audioPath=\"stimuli/").append(trialBandLabel).append("/").append(wrd).append("\" ");

                strBuilder.append("code=\"").append(wrd).append("\" ");

                strBuilder.append("identifier=\"").append(wrd).append("\" ");

                strBuilder.append("ratingLabels=\"").append("").append("\" ");
                strBuilder.append("correctResponses=\"").append("").append("\" ");
                strBuilder.append("tags=\"").append("round1").append("\" ");

                strBuilder.append("positionInTrial=\"").append(i).append("\" ");

                String wordType;
                if (i == 0) {
                    strBuilder.append("pauseMs=\"").append(500).append("\" ");
                    wordType = "EXAMPLE_TARGET_NON_WORD";
                } else {
                    strBuilder.append("pauseMs=\"").append(900).append("\" ");
                    if (trialPositionTargetInt == i) {
                        wordType = "TARGET_NON_WORD";
                    } else {
                        if (trialPositionFoilInt == i) {
                            wordType = "FOIL";
                        } else {
                            wordType = "NON_WORD";
                        }
                    }
                }
                strBuilder.append("wordType=\"").append(wordType).append("\" ");

                strBuilder.append("trialNumber=\"").append(trialNumber).append("\" ");
                strBuilder.append("trialWord=\"").append(trialWord).append("\" ");
                strBuilder.append("trialTargetNonword=\"").append(trialTargetNonword).append("\" ");
                strBuilder.append("trialSyllables=\"").append(trialSyllables).append("\" ");
                strBuilder.append("trialCondition=\"").append(trialCondition).append("\" ");
                strBuilder.append("trialLength=\"").append(trialLength).append("\" ");
                strBuilder.append("trialPositionTarget=\"").append(trialPositionTarget).append("\" ");
                strBuilder.append("bandLabel=\"").append(trialBandLabel).append("\" ");
                if (bandIndexing.get(trialBandLabel) == null) {
                    throw new IOException("There is no index for " + trialBandLabel + "in the indexing map");
                }

                strBuilder.append("bandIndex=\"").append(bandIndexing.get(trialBandLabel)).append("\" ");
                strBuilder.append("trialPositionFoil=\"").append(trialPositionFoil).append("\" ");

                strBuilder.append("/> \n");
                String row = strBuilder.toString();
                retVal.append(row);
            }

        }
        return retVal.toString();
    }

    public String removeFileNameExtensions(String fileName, ArrayList<String> nameExtensions) {

        for (String nameExtension : nameExtensions) {
            String suffix = "." + nameExtension;
            if (fileName.endsWith(suffix)) {
                int nameLength = fileName.length();
                return fileName.substring(0, nameLength - suffix.length());
            }
        }

        return fileName;
    }

}
