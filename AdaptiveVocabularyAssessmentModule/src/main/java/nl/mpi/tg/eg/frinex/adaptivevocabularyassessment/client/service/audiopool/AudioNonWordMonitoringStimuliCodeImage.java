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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.audiopool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.CsvRecords;

/**
 *
 * @author olhshk
 */
public class AudioNonWordMonitoringStimuliCodeImage {

    private String tmpDir = "/Users/olhshk/Documents/ExperimentTemplate/gwt-cordova/src/main/static/audiononwordmonitoring/stimuli/";

    //private String audiPathDir = "/Users/olhshk/Documents/ExperimentTemplate/gwt-cordova/src/main/static/audiononwordmonitoring/stimuli/";
    private String removeFileNameExtensions(String fileName, ArrayList<String> nameExtensions) {

        for (String nameExtension : nameExtensions) {
            String suffix = "." + nameExtension;
            if (fileName.endsWith(suffix)) {
                int nameLength = fileName.length();
                return fileName.substring(0, nameLength - suffix.length());
            }
        }

        return fileName;
    }

    //Order;Round;SNR;Condition;Length_list;Word;Target_nonword;Word1;Word2;Word3;Word4;Word5;Word6;Position_target;Position_foil;\n
    public String parseTrialsInputCSVStringIntoXml(String csvString, String stimuliDir) throws Exception {

        StringBuilder builder = new StringBuilder();

        CsvRecords csvWrapper = new CsvRecords(null, ";", "\n");
        csvWrapper.readRecords(csvString);
        ArrayList<LinkedHashMap<String, String>> records = csvWrapper.getRecords();

        int countNonFoundStimuli = 0;

        for (LinkedHashMap<String, String> record : records) {

            String trialNumber = record.get("Order").trim();
            if (trialNumber == null) {
                throw new IOException("Order is undefined");
            }

            String round = record.get("Round").trim();
            if (round == null) {
                throw new IOException("Round is undefined");
            }

            String snr = record.get("SNR").trim();
            if (snr == null) {
                throw new IOException("Snr is undefined");
            }

            String trialCondition = record.get("Condition").trim();
            if (trialCondition == null) {
                throw new IOException("Condition is undefined");
            }
            if (trialCondition.equals("Target-only") || trialCondition.equals("Target-Only")) {
                trialCondition = "TargetOnly";
            }
            if (trialCondition.equals("Target+Foil") || trialCondition.equals("Target+foil")) {
                trialCondition = "TargetAndFoil";
            }

            String trialLength = record.get("Length_list").trim().substring(0, 1);
            if (trialLength == null) {
                throw new IOException("Length is undefined");
            }
            int trialLengthInt = Integer.parseInt(trialLength);

            String trialWord = record.get("Word").trim();
            if (trialWord == null) {
                throw new IOException("Word is undefined");
            }

            String trialTargetNonword = record.get("Target_nonword").trim();
            if (trialTargetNonword == null) {
                throw new IOException("Target nonword is undefined");
            }

            if (!trialCondition.equals("NoTarget")) {
                trialTargetNonword += "_1";
            }

            // creating patternly-named copies 
            
            Path sourceOgg = Paths.get(tmpDir + "clear_mono/"+trialTargetNonword + ".ogg");
            Path sourceMp3 = Paths.get(tmpDir + "clear_mono/"+trialTargetNonword + ".mp3");
            Path destOgg = Paths.get(tmpDir + "Trial_" + trialNumber + "_cue.ogg");
            Path destMp3 = Paths.get(tmpDir + "Trial_" + trialNumber + "_cue.mp3");
            Files.copy(sourceOgg, destOgg, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(sourceMp3, destMp3, StandardCopyOption.REPLACE_EXISTING);
            // end creating copies
            // sanity check
            BufferedReader br1 = new BufferedReader(new FileReader(tmpDir + "Trial_" + trialNumber + "_cue.ogg"));
            br1.close();
            BufferedReader br2 = new BufferedReader(new FileReader(tmpDir + "Trial_" + trialNumber + "_cue.mp3"));
            br2.close();
            // end sanity check 

            String trialPositionTarget = record.get("Position_target").trim();
            if (trialPositionTarget == null) {
                throw new IOException("Position target is undefined");
            }
            int trialPositionTargetInt = Integer.parseInt(trialPositionTarget);

            String words = "";
            String subDir = Indices.SNR_TO_DIRNAME.get(snr);
            Integer snrInt = Integer.parseInt(snr);
            for (int i = 1; i <= trialLengthInt; i++) {
                String fieldName = "Word" + i;
                String currentWord = record.get(fieldName).trim();
                if (currentWord == null) {
                    throw new IOException(fieldName + " is undefined");
                }

                if (i == trialPositionTargetInt) {
                    currentWord = currentWord + "_2";
                }
                words += (" " + currentWord);

                
                sourceOgg =Paths.get(tmpDir + subDir +"/"+currentWord + "_"+snrInt +".ogg");
                sourceMp3 = Paths.get(tmpDir + subDir +"/"+currentWord + "_"+snrInt +".mp3");
                destOgg = Paths.get(tmpDir + "Trial_" + trialNumber + "word_" + i + ".ogg");
                destMp3 = Paths.get(tmpDir + "Trial_" + trialNumber + "word_" + i + ".mp3");
                Files.copy(sourceOgg, destOgg, StandardCopyOption.REPLACE_EXISTING);
                Files.copy(sourceMp3, destMp3, StandardCopyOption.REPLACE_EXISTING);
                // end creating copies
                // sanity check
                br1 = new BufferedReader(new FileReader(tmpDir + "Trial_" + trialNumber + "word_" + i + ".ogg"));
                br1.close();
                br2 = new BufferedReader(new FileReader(tmpDir + "Trial_" + trialNumber + "word_" + i + ".mp3"));
                br2.close();
                // end sanity check 
            }

            String trialPositionFoil = record.get("Position_foil").trim();
            if (trialPositionFoil == null) {
                throw new IOException("Position foil is undefined");
            }

            String tags = " length_" + trialLength + " word_" + trialWord + " sequence_" + words + " " + "foilPosition_" + trialPositionFoil;

            String uniqueId = "Trial_" + trialNumber;
            String label = "Trial_" + trialNumber + "_snr_" + snr + "_condition_" + trialCondition;
            String currentSt = this.makeStimulusString(uniqueId, label, uniqueId, tags);
            builder.append(currentSt);

        }
        return builder.toString();
    }

    private String makeStimulusString(String uniqueId,
            String label,
            String codeAudio,
            String tags) {

        StringBuilder retVal = new StringBuilder();
        retVal.append("<stimulus ");
        retVal.append(" identifier=\"").append(uniqueId).append("\" ");
        retVal.append(" label=\"").append(label).append("\" ");

        retVal.append(" pauseMs=\"0\" ");
        retVal.append(" codeAudio=\"").append(codeAudio).append("\" ");

        retVal.append(" tags=\"").append(tags).append("\" ");

        retVal.append(" />\n");
        return retVal.toString();

    }

    private void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

}
