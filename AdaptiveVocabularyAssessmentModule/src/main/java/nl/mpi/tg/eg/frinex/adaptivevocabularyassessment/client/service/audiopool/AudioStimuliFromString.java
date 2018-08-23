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

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BandStimuliProvider;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BookkeepingStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.CsvRecords;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.AudioAsStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.Trial;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.TrialCondition;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.WordType;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.AudioAsStimuliProvider;
import nl.mpi.tg.eg.frinex.common.model.Stimulus.Tag;

/**
 *
 * @author olhshk
 */
public class AudioStimuliFromString {

    private final LinkedHashMap<String, AudioAsStimulus> hashedStimuli = new LinkedHashMap<String, AudioAsStimulus>();
    private LinkedHashMap<Integer, Trial> trials = new LinkedHashMap<Integer, Trial>();
    private final LinkedHashMap<Integer, Trial> learningTrials = new LinkedHashMap<Integer, Trial>();
    private final LinkedHashMap<String, Integer> stimuliTrialIndex = new LinkedHashMap<String, Integer>();

    //private String audiPathDir = "/Users/olhshk/Documents/ExperimentTemplate/gwt-cordova/src/main/static/audioas2/stimuli/";
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

    //Nr;Word;Target_nonword;Syllables;Condition;Length_list;Word1;Word2;Word3;Word4;Word5;Word6;Position_target;Noise_level;Position_foil;
    private LinkedHashMap<Integer, Trial> parseTrialsInputCSVStringIntoTrialsArray(final BandStimuliProvider provider, String csvString, ArrayList<String> fileNameExtensions, String stimuliDir) throws Exception {

        LinkedHashMap<Integer, Trial> retVal = new LinkedHashMap<Integer, Trial>();

        CsvRecords csvWrapper = new CsvRecords(null, ";", "\n");
        csvWrapper.readRecords(csvString);
        ArrayList<LinkedHashMap<String, String>> records = csvWrapper.getRecords();

        int countNonFoundStimuli = 0;

        for (LinkedHashMap<String, String> record : records) {

            String trialNumber = record.get("Nr").trim();
            if (trialNumber == null) {
                throw new IOException(trialNumber + "is undefined");
            }

            Integer trialID = Integer.parseInt(trialNumber);

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

            String bandLabel = record.get("Noise_level").trim();
            if (bandLabel == null) {
                throw new IOException(bandLabel + "is undefined");
            }

            int bandIndex = Indices.BAND_LABEL_TO_INDEX.get(bandLabel);

            ArrayList<BookkeepingStimulus<AudioAsStimulus>> stimuli = new ArrayList<BookkeepingStimulus<AudioAsStimulus>>();

            for (int i = 0; i < words.size(); i++) {

                //AudioAsStimulus(String uniqueId, Stimulus.Tag[] tags, String label, String code, int pauseMs, String audioPath, String videoPath, String imagePath,
                //             String ratingLabels, String correctResponses, String bandLabel, int bandIndex, WordType wordType, int posInTrial)
                String wrd = removeFileNameExtensions(words.get(i), fileNameExtensions);
                String suffix = "_in_" + trialCondition;
                WordType wordType;
                String ratingLabels = "";
                String locationInDir;
                if (i == 0) {
                    suffix = suffix + "_clear_mono_for_" + bandLabel;
                    wordType = WordType.EXAMPLE_TARGET_NON_WORD;
                    ratingLabels = null;
                    locationInDir = "clear_mono/" + wrd;
                } else {
                    suffix = suffix + "_" + bandLabel;
                    locationInDir = Indices.BAND_LABEL_TO_DIRNAME.get(bandLabel) + "/" + wrd + "_" + Indices.BAND_LABEL_TO_INTEGER.get(bandLabel);
                    if (trialPositionTargetInt == i) {
                        wordType = WordType.TARGET_NON_WORD;
                    } else {
                        if (trialPositionFoilInt == i) {
                            wordType = WordType.FOIL;
                        } else {
                            wordType = WordType.NON_WORD;
                        }
                    }
                }

                String audioPath = stimuliDir + locationInDir;
                String uniqueId = wrd + "_" + wordType + suffix;
                AudioAsStimulus stimulus = new AudioAsStimulus(uniqueId, new Tag[0], wrd, "", 0, audioPath, null, null, ratingLabels, "", bandLabel, bandIndex, wordType, i) {
                    @Override
                    public boolean isCorrect(String value) {
                        return provider.isCorrectResponse(this, value);
                    }
                };
                this.hashedStimuli.put(uniqueId, stimulus);

                this.stimuliTrialIndex.put(uniqueId, trialID);

                BookkeepingStimulus<AudioAsStimulus> bStimulus = new BookkeepingStimulus<AudioAsStimulus>(stimulus);
                stimuli.add(bStimulus);

                //sanity check if the files exist
//                String wav = locationInDir+".wav";
//                String mp3 = locationInDir+".mp3";
//                String ogg = locationInDir+".ogg";
//                String audiPathDir = "/Users/olhshk/Documents/ExperimentTemplate/gwt-cordova/src/main/static/audioas2/stimuli/" ; // must be the same as in the configuration file
//                try {
//                    
//                    BufferedReader br = new BufferedReader(new FileReader(audiPathDir + wav));
//                    //System.out.println(audioPath);
//                    br.close();
//                    BufferedReader br1 = new BufferedReader(new FileReader(audiPathDir + mp3));
//                    br1.close();
//                    BufferedReader br2 = new BufferedReader(new FileReader(audiPathDir + ogg));
//                    br2.close();
//
//                } catch (FileNotFoundException ex) {
//                    countNonFoundStimuli++;
//                    System.out.println();
//                    System.out.println("Not found file number " + countNonFoundStimuli);
//                    System.out.println("Trial " + Integer.parseInt(trialNumber));
//                    System.out.println(ex);
//
//                }
            }

            TrialCondition tc = null;
            switch (trialCondition) {
                case "Target-only":
                    tc = TrialCondition.TARGET_ONLY;
                    break;
                case "Target+Foil":
                    tc = TrialCondition.TARGET_AND_FOIL;
                    break;
                case "NoTarget":
                    tc = TrialCondition.NO_TARGET;
                    break;
                default:
                    break;
            }

            //public Trial(int id, String word, String cueFile, int nOfSyllables, TrialCondition condition, int length, String bandLabel, int bandIndex, ArrayList<BookkeepingStimulus<AudioAsStimulus>> stimuli) throws Exception{
            Trial nextTrial = new Trial(trialID, trialWord, removeFileNameExtensions(trialTargetNonword, fileNameExtensions), Integer.parseInt(trialSyllables), tc,
                    Integer.parseInt(trialLength), bandLabel, bandIndex,
                    Integer.parseInt(trialPositionTarget), Integer.parseInt(trialPositionFoil), stimuli);
            retVal.put(nextTrial.getId(), nextTrial);

        }
        return retVal;
    }

    public void readTrialsAsCsv(final BandStimuliProvider provider, String stimuliDir) {
        this.trials = new LinkedHashMap<Integer, Trial>();
        try {
            ArrayList<String> fileNameExtensions = new ArrayList<String>(1);
            fileNameExtensions.add("wav");
            System.out.println("Portion 1");
            this.trials = this.parseTrialsInputCSVStringIntoTrialsArray(provider, TrialsCsv1.CSV_CONTENT, fileNameExtensions, stimuliDir);
            System.out.println("Portion 2");
            LinkedHashMap<Integer, Trial> trials2 = this.parseTrialsInputCSVStringIntoTrialsArray(provider, TrialsCsv2.CSV_CONTENT, fileNameExtensions, stimuliDir);
            System.out.println("Portion 3");
            LinkedHashMap<Integer, Trial> trials3 = this.parseTrialsInputCSVStringIntoTrialsArray(provider, TrialsCsv3.CSV_CONTENT, fileNameExtensions, stimuliDir);
            System.out.println("Portion 4");
            LinkedHashMap<Integer, Trial> trials4 = this.parseTrialsInputCSVStringIntoTrialsArray(provider, TrialsCsv4.CSV_CONTENT, fileNameExtensions, stimuliDir);
            System.out.println("Portion 5");
            LinkedHashMap<Integer, Trial> trials5 = this.parseTrialsInputCSVStringIntoTrialsArray(provider, TrialsCsv5.CSV_CONTENT, fileNameExtensions, stimuliDir);
            System.out.println("Portion 6");
            LinkedHashMap<Integer, Trial> trials6 = this.parseTrialsInputCSVStringIntoTrialsArray(provider, TrialsCsv6.CSV_CONTENT, fileNameExtensions, stimuliDir);
            System.out.println("Portion 7");
            LinkedHashMap<Integer, Trial> trials7 = this.parseTrialsInputCSVStringIntoTrialsArray(provider, TrialsCsv7.CSV_CONTENT, fileNameExtensions, stimuliDir);
            this.trials.putAll(trials2);
            this.trials.putAll(trials3);
            this.trials.putAll(trials4);
            this.trials.putAll(trials5);
            this.trials.putAll(trials6);
            this.trials.putAll(trials7);
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }

    public void prepareLearningTrialsAsCsv(ArrayList<Integer> learningTrialIDs) {
        for (Integer id : learningTrialIDs) {
            Trial trial = this.trials.get(id);
            this.learningTrials.put(id, trial);
        }
    }

    public LinkedHashMap<String, AudioAsStimulus> getHashedStimuli() {
        return this.hashedStimuli;
    }

    public LinkedHashMap<Integer, Trial> getHashedTrials() {
        return this.trials;
    }

    public LinkedHashMap<Integer, Trial> getHashedLearningTrials() {
        return this.learningTrials;
    }

    public LinkedHashMap<String, Integer> getStimuliTrialIndex() {
        return this.stimuliTrialIndex;
    }

}
