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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service;

import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.audiopool.AudioStimuliFromString;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BandStimuliProvider;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BookkeepingStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.Trial;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.AudioAsStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.TrialTuple;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.WordType;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.audiopool.Indices;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;

/**
 *
 * @author olhshk
 */
public class AudioAsLearningStimuliProvider extends BandStimuliProvider<AudioAsStimulus> {

    private String stimuliDir;
    private String firstStimulusDurationMs;
    AudioStimuliFromString reader = new AudioStimuliFromString();

    private LinkedHashMap<Integer, Trial> learningTrials; // shared between various permutation-pairs, reduced while it is used
    private ArrayList<Integer> learningTrialsIds;
    private TrialTuple currentTrialTuple;
    
    public TrialTuple getCurrentTrialTuple(){
        return this.currentTrialTuple;
    }

    public AudioAsLearningStimuliProvider(Stimulus[] stimulusArray) {
        super(stimulusArray);
    }

    public void setlearningTrials(String learningTrials) {
        String[] learningTrialsIDsString = learningTrials.split(",");
        this.learningTrialsIds = new ArrayList<Integer>(learningTrialsIDsString.length);
        for (String idStr : learningTrialsIDsString) {
            this.learningTrialsIds.add(Integer.parseInt(idStr.trim()));
        }
    }

    public void setstimuliDir(String dir) {
        this.stimuliDir = dir;
    }

    public void setfirstStimulusDurationMs(String firstStimulusDurationMs) {
        this.firstStimulusDurationMs = firstStimulusDurationMs;
    }

    @Override
    public void initialiseStimuliState(String stimuliStateSnapshot) {

        super.initialiseStimuliState(stimuliStateSnapshot);

        long maxDurationMinLong = Long.parseLong(this.maxDurationMinutes);
        this.maxDurationMs = maxDurationMinLong * 60 * 1000;

        if (stimuliStateSnapshot.isEmpty()) {

            this.currentBandIndex = this.startBand;
            this.reader = new AudioStimuliFromString();
            this.reader.readTrialsAsCsv(this, this.stimuliDir);
            this.reader.prepareLearningTrialsAsCsv(this.learningTrialsIds);
            this.learningTrials = this.reader.getHashedLearningTrials();
            this.isCorrectCurrentResponse = true;
            this.initialiseNextFineTuningTuple();
        } else {

            try {
                this.deserialiseSpecific(stimuliStateSnapshot);
            } catch (Exception ex) {
                this.exceptionLogging(ex);
            }
        }
    }

    @Override
    public AudioAsStimulus getCurrentStimulus() {
        AudioAsStimulus retVal = super.getCurrentStimulus();

        if (this.startTimeMs > 0) {
            return retVal;
        }
        if (!retVal.getwordType().equals(WordType.EXAMPLE_TARGET_NON_WORD)) { // set timer after the first cue is played, before the first non-cue is played
            long firstStimulusDurationMsLong = Long.parseLong(this.firstStimulusDurationMs);
            this.startTimeMs = System.currentTimeMillis() - firstStimulusDurationMsLong;
        }

        return retVal;
    }

    @Override
    public boolean initialiseNextFineTuningTuple() {
        ArrayList<Trial> trs = new ArrayList<Trial>(this.learningTrialsIds.size());
        for (Integer id : this.learningTrialsIds) {
            Trial trial = this.learningTrials.get(id);
            trs.add(trial);
        }
        this.currentTrialTuple = new TrialTuple(trs);
        return true;
    }

    @Override
    public boolean hasNextStimulus(int increment) {

        // first evaluate correctness of the previous answer
        if (!this.responseRecord.isEmpty()) {
            int index = this.getCurrentStimulusIndex();
            BookkeepingStimulus<AudioAsStimulus> bStimulus = this.responseRecord.get(index);
            if (bStimulus.getCorrectness() == null) { // has not been analysed yet, i.e. the button was not pressed, isCorrectResponse has not been called
                WordType wt = bStimulus.getStimulus().getwordType();
                if (wt.equals(WordType.TARGET_NON_WORD)) { // missed target
                    bStimulus.setCorrectness(false);
                    this.isCorrectCurrentResponse = false;
                } else {
                    bStimulus.setCorrectness(true);
                    this.isCorrectCurrentResponse = true;
                }
            }
        }

        return this.tupleIsNotEmpty();
    }

    @Override
    public void nextStimulus(int increment) {
        BookkeepingStimulus<AudioAsStimulus> bStimulus = this.currentTrialTuple.getFirstNonusedTrial().getStimuli().remove(0);
        this.responseRecord.add(bStimulus);
    }

    @Override
    protected boolean tupleIsNotEmpty() {
        return this.currentTrialTuple.isNotEmpty();
    }

    @Override
    protected Boolean isEnoughCorrectResponses() {
        return null;
//        boolean allTupleCorrect = true;
//        int i = this.responseRecord.size() - 1;
//        while (i > -1 && this.responseRecord.get(i) != null) {
//            BookkeepingStimulus<AudioAsStimulus> bStimulus = this.responseRecord.get(i);
//            AudioAsStimulus audioStimulus = bStimulus.getStimulus();
//            WordType stimulusType = audioStimulus.getwordType();
//            if (!stimulusType.equals(WordType.EXAMPLE_TARGET_NON_WORD)) {
//                boolean correctness = (stimulusType.equals(WordType.TARGET_NON_WORD) && bStimulus.getReaction() != null) || ((!stimulusType.equals(WordType.TARGET_NON_WORD)) && bStimulus.getReaction() == null);
//                bStimulus.setCorrectness(correctness);
//                if (!correctness && allTupleCorrect) {
//                    allTupleCorrect = false;
//                }
//            }
//            i--;
//        }
//        this.currentTrialTuple.setCorrectness(allTupleCorrect);
//        this.responseRecord.add(null); // marking the end of the trial tuple
//        return allTupleCorrect;
    }

    @Override
    protected String bandIndexToLabel(int index) {
        Set<String> labels = Indices.BAND_LABEL_TO_INDEX.keySet();
        for (String labelDb : labels) {
            if (Indices.BAND_LABEL_TO_INDEX.get(labelDb).equals(index)) {
                return labelDb;
            }
        }
        return null;
    }

    @Override
    public String getStringFineTuningHistory(String startRow, String endRow, String startColumn, String endColumn, String format) {

        LinkedHashMap<String, Integer> stimuliTrialIndex = this.reader.getStimuliTrialIndex();
        LinkedHashMap<Integer, Trial> trialIndex = this.reader.getHashedTrials();

        StringBuilder empty = new StringBuilder();
        empty.append(startColumn).append(" ").append(endColumn);
        empty.append(startColumn).append(" ").append(endColumn);
        empty.append(startColumn).append(" ").append(endColumn);
        empty.append(startColumn).append(" ").append(endColumn);
        empty.append(startColumn).append(" ").append(endColumn);
        empty.append(startColumn).append(" ").append(endColumn);

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(startRow);
        stringBuilder.append(startColumn).append("BandLabel").append(endColumn);
        stringBuilder.append(startColumn).append("# trial").append(endColumn);
        stringBuilder.append(startColumn).append("Trial ID").append(endColumn);
        stringBuilder.append(startColumn).append("Trial type").append(endColumn);
        stringBuilder.append(startColumn).append("Trial length").append(endColumn);
        stringBuilder.append(startColumn).append("Stimulus Label").append(endColumn);
        stringBuilder.append(startColumn).append("StimulusType").append(endColumn);
        stringBuilder.append(startColumn).append("UserAnswer").append(endColumn);
        stringBuilder.append(startColumn).append("IsAnswerCorrect").append(endColumn);
        stringBuilder.append(startColumn).append("Timestamp").append(endColumn);
        stringBuilder.append(startColumn).append("Visiting Number").append(endColumn);
        stringBuilder.append(endRow);

        ArrayList<String> uniqueIDs = new ArrayList<>();
        Integer previousTrialID = null;
        int trialSequenceNumber = 0;
        for (int i = 0; i < this.responseRecord.size(); i++) {
            BookkeepingStimulus<AudioAsStimulus> bStimulus = this.responseRecord.get(i);
            if (bStimulus == null) { // trial tuple border
                stringBuilder.append(startRow).append(endRow);
                stringBuilder.append(startRow).append(endRow);
                stringBuilder.append(startRow).append(endRow);
                continue;
            }
            AudioAsStimulus stimulus = bStimulus.getStimulus();
            StringBuilder row = new StringBuilder();
            String time = (new Date(bStimulus.getTimeStamp())).toString();

            row.append(startColumn).append(stimulus.getbandLabel()).append(endColumn);

            Integer trialID = stimuliTrialIndex.get(stimulus.getUniqueId());
            if (!trialID.equals(previousTrialID)) {
                trialSequenceNumber++;
            }
            previousTrialID = trialID;

            Trial trial = trialIndex.get(trialID);

            row.append(startColumn).append(trialSequenceNumber).append(endColumn);
            row.append(startColumn).append(trialID).append(endColumn);
            row.append(startColumn).append(trial.getCondition()).append(endColumn);
            row.append(startColumn).append(trial.getTrialLength()).append(endColumn);

            row.append(startColumn).append(stimulus.getLabel()).append(endColumn);
            row.append(startColumn).append(stimulus.getwordType()).append(endColumn);
            row.append(startColumn).append(bStimulus.getReaction()).append(endColumn);
            row.append(startColumn).append(bStimulus.getCorrectness()).append(endColumn);
            row.append(startColumn).append(time).append(endColumn);
            row.append(startColumn).append(i).append(endColumn);
            stringBuilder.append(startRow).append(row).append(endRow);
            uniqueIDs.add(stimulus.getUniqueId());
        }

        // check if there are repititions
        HashSet<String> set = new HashSet(uniqueIDs);
        if (set.size() < uniqueIDs.size()) {
            stringBuilder.append(startRow).append(startColumn)
                    .append("Repetitions of stimuli detected")
                    .append(endColumn).append(endRow);
        }
        return stringBuilder.toString();
    }
    
  
    
     @Override
    public String getHtmlStimuliReport() {
        String inhoudFineTuning = this.getStringFineTuningHistory("<tr>", "</tr>", "<td>", "</td>", "html");
        StringBuilder htmlStringBuilder = new StringBuilder();
        htmlStringBuilder.append("<p>Resultaat van je oefening</p><table border=1>").append(inhoudFineTuning).append("</table>");
        return htmlStringBuilder.toString();

    }
    



    @Override
    protected void deserialiseSpecific(String str) throws Exception {
       
    }

    @Override
    public boolean analyseCorrectness(Stimulus stimulus, String stimulusResponse) {
        int index = this.getCurrentStimulusIndex();
        BookkeepingStimulus<AudioAsStimulus> bStimulus = this.responseRecord.get(index);
        bStimulus.setTimeStamp(System.currentTimeMillis());

        AudioAsStimulus audioStimulus = bStimulus.getStimulus();
        WordType stimulusType = audioStimulus.getwordType();

        bStimulus.setReaction(AudioAsStimulus.USER_REACTION);
        if (audioStimulus.wordType.equals(WordType.EXAMPLE_TARGET_NON_WORD)) {
            return true;
        } else {
            this.currentTrialTuple.getFirstNonusedTrial().getStimuli().clear(); // also on button pressed: flush rest of the trial to trigger a new trial
            boolean correctness = stimulusType.equals(WordType.TARGET_NON_WORD); // button should be pressed only on the target nonword
            return correctness;
        }
    }

    @Override
    protected boolean fineTuningToBeContinuedFirstWrongOut() {
        return true;
    }

    @Override
    protected void checkTimeOut() {
        // check if the time out has happened
        // first check if the trial was finished
        Trial trial = this.currentTrialTuple.getFirstNonusedTrial();
        if (trial != null) {
            if (trial.getStimuli().size() == trial.getTrialLength() + 1) { // we are about to start a new trial, so time-out must be checked
                // amount of stimuli == trialLength + 1 cue stimulus
                this.isTimeOut();
            }
        } else { // trial is empty, check time out
            this.isTimeOut();
        }
    }

    @Override
    public boolean fastTrackToBeContinuedWithSecondChance() {
        return true;
    }

    @Override
    public boolean enoughStimuliForFastTrack() {
        return true;
    }

    @Override
    protected void recycleUnusedStimuli() {

    }

    @Override
    public String getStringFastTrack(String startRow, String endRow, String startColumn, String endColumn) {
        return "";
    }

    @Override
    public BookkeepingStimulus<AudioAsStimulus> deriveNextFastTrackStimulus() {
        return null;
    }

    @Override
    public long getPercentageScore() {
        return -1;
    }
}
