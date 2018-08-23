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
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BandStimuliProvider;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BookkeepingStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.UtilsJSONdialect;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.UtilsList;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.Trial;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.AudioAsStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.PermutationPair;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.TrialCondition;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.TrialTuple;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.WordType;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.audiopool.Indices;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;

/**
 *
 * @author olhshk
 */
public class AudioAsStimuliProvider extends BandStimuliProvider<AudioAsStimulus> {

    private final static String[] SPECIFIC_FLDS = {"availableCombinations", "currentTrialTuple", "responseRecord", "startTimeMs", "timeOutExit", "durationMs"};

    private String stimuliDir;
    private String requiredLengthsStr;
    private String requiredTrialTypesStr;
    private String firstStimulusDurationMs;
    private ArrayList<Integer> requiredLengths;
    private int maxTrialLength;
    private ArrayList<TrialCondition> requiredTrialTypes;
    AudioStimuliFromString reader = new AudioStimuliFromString();
    private ArrayList<ArrayList<TrialCondition>> trialTypesPermutations;
    private ArrayList<ArrayList<Integer>> trialLengtPermutations;

    // x[i][j][contdition] is the list of all trials (id-s) of the band-index i  of the length j satisfying "condition"
    private ArrayList<ArrayList<LinkedHashMap<TrialCondition, ArrayList<Trial>>>> trials; // shared between various permutation-pairs, reduced while it is used
    Random rndSead = new Random();

    // to be serialised
    private ArrayList<ArrayList<PermutationPair>> availableCombinations; // x[i] is the list of permutations with non-empty possibilities to instantiate them using trials matrix of unused trials
    private TrialTuple currentTrialTuple;
    private ArrayList<String> usedCues = new ArrayList<String>();
    
    private ArrayList<Integer> learninTrials;

    public AudioAsStimuliProvider(Stimulus[] stimulusArray) {
        super(stimulusArray);
    }
    
    public void setlearningTrials(String learningTrials) {
        String[] learningTrialsIDsString = learningTrials.split(",");
        this.learninTrials = new ArrayList<Integer>(learningTrialsIDsString.length);
        for (String idStr : learningTrialsIDsString) {
            this.learninTrials.add(Integer.parseInt(idStr.trim()));
        }
    }

    public void setrequiredLengths(String lengths) {
        this.requiredLengthsStr = lengths;
    }

    public void setrequiredTrialTypes(String types) {
        this.requiredTrialTypesStr = types;
    }

    public void setstimuliDir(String dir) {
        this.stimuliDir = dir;
    }

    public void setfirstStimulusDurationMs(String firstStimulusDurationMs) {
        this.firstStimulusDurationMs = firstStimulusDurationMs;
    }

    public ArrayList<Integer> getRequiredLength() {
        return this.requiredLengths;
    }

    public ArrayList<TrialCondition> requiredTrialTypes() {
        return this.requiredTrialTypes;
    }

    public ArrayList<ArrayList<LinkedHashMap<TrialCondition, ArrayList<Trial>>>> getTrials() {
        return this.trials;
    }

    private void computeRequiredLength(String lengths) {
        String[] lengthArray = lengths.split(",");
        this.requiredLengths = new ArrayList<Integer>(lengthArray.length);
        this.maxTrialLength = 0;
        for (String length : lengthArray) {
            Integer lengthInt = Integer.parseInt(length.trim());
            this.requiredLengths.add(lengthInt);
            if (lengthInt > this.maxTrialLength) {
                maxTrialLength = lengthInt;
            }
        }
    }

    private void computeRequiredTrialTypes(String types) {
        String[] typesArray = types.split(",");
        this.requiredTrialTypes = new ArrayList<TrialCondition>(typesArray.length);
        for (String trialConditionStr : typesArray) {
            TrialCondition trialCondition = TrialCondition.valueOf(trialConditionStr.trim());
            this.requiredTrialTypes.add(trialCondition);
        }
    }

    @Override
    public void initialiseStimuliState(String stimuliStateSnapshot) {

        super.initialiseStimuliState(stimuliStateSnapshot);

        try {
            this.computeRequiredLength(this.requiredLengthsStr);
            this.computeRequiredTrialTypes(this.requiredTrialTypesStr);
            

        } catch (Exception ex) {
            this.exceptionLogging(ex);
        }

        UtilsList<TrialCondition> utilTrials = new UtilsList<TrialCondition>();
        this.trialTypesPermutations = utilTrials.generatePermutations(this.requiredTrialTypes, this.fineTuningTupleLength);

        UtilsList<Integer> utilSizes = new UtilsList<Integer>();
        this.trialLengtPermutations = utilSizes.generatePermutations(this.requiredLengths, this.fineTuningTupleLength);

        long maxDurationMinLong = Long.parseLong(this.maxDurationMinutes);
        this.maxDurationMs = maxDurationMinLong * 60 * 1000;

        if (stimuliStateSnapshot.isEmpty()) {

            this.currentBandIndex = this.startBand;

            this.reader = new AudioStimuliFromString();
            this.reader.readTrialsAsCsv(this, this.stimuliDir);
            
            LinkedHashMap<Integer, Trial> hashedTrials = this.reader.getHashedTrials();
            for (Integer learningTrialId : this.learninTrials) {
                hashedTrials.remove(learningTrialId);
            }
            
            this.trials = Trial.prepareTrialMatrix(hashedTrials, this.numberOfBands, this.maxTrialLength);
            
            this.availableCombinations = PermutationPair.initialiseAvailabilityList(this.trials, this.trialLengtPermutations, trialTypesPermutations, this.numberOfBands);

            this.isCorrectCurrentResponse = true;

            this.bandVisitsByTrials = new ArrayList<Integer>(this.numberOfBands);
            for (int i = 0; i < this.numberOfBands; i++) {
                this.bandVisitsByTrials.add(0);
            }

            boolean init = this.initialiseNextFineTuningTuple();
            if (!init) {
                System.out.println(this.errorMessage);
            }

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

        if (retVal.getwordType().equals(WordType.EXAMPLE_TARGET_NON_WORD)) {
            usedCues.add(retVal.getLabel());
        }

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

        ArrayList<PermutationPair> combinations = this.availableCombinations.get(this.currentBandIndex);
        this.currentTrialTuple = this.createTupleForBand(combinations);

        if (this.currentTrialTuple == null) {
            this.errorMessage = "There is no trial tuples left satisfying the specification, for the band with index (see indexing file for consult) " + this.currentBandIndex;
            return false;
        } else {

            // now remove permutation-pairs which have emptied list of trials 
            ArrayList<PermutationPair> toBeRemoved = new ArrayList<PermutationPair>();
            for (PermutationPair permut : combinations) {
                if (!permut.isAvailable()) {
                    toBeRemoved.add(permut);
                }
            }
            combinations.removeAll(toBeRemoved);
            return true;
        }
    }

    @Override
    public void nextStimulus(int increment) {

        BookkeepingStimulus<AudioAsStimulus> bStimulus = this.currentTrialTuple.getFirstNonusedTrial().getStimuli().remove(0);
        if (bStimulus.getStimulus().getwordType().equals(WordType.EXAMPLE_TARGET_NON_WORD)) {
            // we hit the trial for the first time
            int currentVisits = this.bandVisitsByTrials.get(this.currentBandIndex);
            this.bandVisitsByTrials.set(this.currentBandIndex, currentVisits + 1);
        }
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

    public TrialTuple getCurrentTrialTuple() {
        return this.currentTrialTuple;
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
    public String getBandFrequenceTable(String startRow, String endRow, String startColumn, String endColumn, String format) {
        StringBuilder stringBuilder = new StringBuilder();
        HashMap<Integer, String> indexMap = this.invertHashMap(Indices.BAND_LABEL_TO_INDEX);
        stringBuilder.append(startRow).append(endRow);
        stringBuilder.append(startRow).append(endRow);
        stringBuilder.append(startRow).append(endRow);
        stringBuilder.append(startRow).append(" Map Band to Frequence").append(endRow);

        stringBuilder.append(startRow);
        stringBuilder.append(startColumn).append("Band ").append(endColumn);
        stringBuilder.append(startColumn).append("# of appearances").append(endColumn);
        stringBuilder.append(endRow);
        for (int index = 0; index < this.bandVisitsByTrials.size(); index++) {
            String bandLabel = indexMap.get(index);
            stringBuilder.append(startRow);
            stringBuilder.append(startColumn).append(bandLabel).append(endColumn);
            stringBuilder.append(startColumn).append(this.bandVisitsByTrials.get(index)).append(endColumn);
            stringBuilder.append(endRow);
        }
        return stringBuilder.toString();
    }

    private HashMap<Integer, String> invertHashMap(HashMap<String, Integer> map) {
        Set<String> keys = map.keySet();
        HashMap<Integer, String> retVal = new HashMap<Integer, String>();
        for (String label : keys) {
            retVal.put(map.get(label), label);
        }
        return retVal;
    }

    public TrialTuple createTupleForBand(ArrayList<PermutationPair> availablePermutations) {

        if (availablePermutations.size() < 1) {
            return null;
        }

        int seed = this.rndSead.nextInt();
        Random rnd = new Random(seed);
        int combinationIndex = rnd.nextInt(availablePermutations.size());
        PermutationPair permPair = availablePermutations.get(combinationIndex);
        int tupleSize = permPair.getTrialConditions().size();
        ArrayList<Trial> trs = new ArrayList<Trial>(tupleSize);

        for (int i = 0; i < tupleSize; i++) {
            ArrayList<Trial> possibilities = permPair.getTrials().get(i);// shared part
            int trialIndex = rnd.nextInt(possibilities.size());
            String label = possibilities.get(trialIndex).getStimuli().get(0).getStimulus().getLabel();
            if (this.usedCues.contains(label)) { // correct index
                ArrayList<Integer> betterPossibilities = this.excludeRepeatedLabels(possibilities, this.usedCues);
                if (betterPossibilities.size() > 0) {
                    trialIndex = betterPossibilities.get(rnd.nextInt(betterPossibilities.size()));
                }
            }
            Trial currentTrial = possibilities.remove(trialIndex);
            trs.add(currentTrial);
        }

        TrialTuple retVal = new TrialTuple(trs);
        return retVal;
    }

    private ArrayList<Integer> excludeRepeatedLabels(ArrayList<Trial> possibilities, ArrayList<String> vorbiddenLabels) {
        ArrayList<Integer> retVal = new ArrayList<Integer>();
        for (int j = 0; j < possibilities.size(); j++) {
            String label = possibilities.get(j).getStimuli().get(0).getStimulus().getLabel();
            boolean isIn = false;
            for (String vorbiddenLabel : vorbiddenLabels) {
                if (label.equals(vorbiddenLabel)) {
                    isIn = true;
                    break;
                }
            }
            if (!isIn) {
                retVal.add(j);
            }
        }
        return retVal;
    }

    @Override
    public String toString() {
        Map<String, Object> map = super.toMap();
        map.put("availableCombinations", this.availableCombinations);
        map.put("currentTrialTuple", this.currentTrialTuple);
        map.put("startTimeMs", this.startTimeMs);
        map.put("timeOutExit", this.timeOutExit);
        map.put("durationMs", this.durationMs);
        return map.toString();
    }

    @Override
    protected void deserialiseSpecific(String str) throws Exception {

        this.reader.readTrialsAsCsv(this, this.stimuliDir);
        this.trials = Trial.prepareTrialMatrix(reader.getHashedTrials(), this.numberOfBands, this.maxTrialLength);

        Map<String, Object> map = UtilsJSONdialect.stringToObjectMap(str, SPECIFIC_FLDS);

        Object recordObj = map.get("responseRecord");

        this.startTimeMs = Long.parseLong((String) map.get("startTimeMs"));
        this.timeOutExit = Boolean.parseBoolean((String) map.get("timeOutExit"));
        this.durationMs = Long.parseLong((String) map.get("durationMs"));

        this.responseRecord = new ArrayList<BookkeepingStimulus<AudioAsStimulus>>();
        BookkeepingStimulus<AudioAsStimulus> factory = new BookkeepingStimulus<AudioAsStimulus>(null);
        this.usedCues = new ArrayList<String>();
        if (recordObj != null) {
            List<Object> objs = (List<Object>) recordObj;
            for (int i = 0; i < objs.size(); i++) {
                Map<String, Object> mapBStimulus = (Map<String, Object>) objs.get(i);
                BookkeepingStimulus<AudioAsStimulus> bStimulus = factory.toBookkeepingStimulusObject(mapBStimulus, this.reader.getHashedStimuli());
                this.responseRecord.add(i, bStimulus);
                if (bStimulus.getStimulus().getwordType().equals(WordType.EXAMPLE_TARGET_NON_WORD)) {
                    this.usedCues.add(bStimulus.getStimulus().getLabel());
                }
            }
        }

        Object obj1 = map.get("availableCombinations");
        List<Object> obj2 = (List<Object>) obj1;
        this.availableCombinations = new ArrayList<ArrayList<PermutationPair>>(obj2.size());
        for (int i = 0; i < obj2.size(); i++) {
            Object obj3 = obj2.get(i);
            if (obj3 == null) {
                this.availableCombinations.add(null);
                continue;
            }
            List<Object> obj4 = (List<Object>) obj3;
            ArrayList<PermutationPair> permForBand = new ArrayList<PermutationPair>(obj4.size());
            this.availableCombinations.add(permForBand);
            for (Object object : obj4) {
                LinkedHashMap<String, Object> objMap = (LinkedHashMap<String, Object>) object;
                PermutationPair perm = PermutationPair.toObject(objMap, this.reader.getHashedTrials());
                permForBand.add(perm);
            }
        }

        LinkedHashMap<String, Object> tupleMap = (LinkedHashMap<String, Object>) map.get("currentTrialTuple");
        this.currentTrialTuple = TrialTuple.mapToObject(tupleMap, this.reader.getHashedTrials());
        this.bandVisitsByTrials = this.reconstructBandVisistsByTrials(this.responseRecord);
    }

    private ArrayList<Integer> reconstructBandVisistsByTrials(ArrayList<BookkeepingStimulus<AudioAsStimulus>> records) {
        ArrayList<Integer> retVal = new ArrayList<Integer>(this.numberOfBands);
        for (int i = 0; i < this.numberOfBands; i++) {
            retVal.add(0);
        }
        for (BookkeepingStimulus<AudioAsStimulus> bStimulus : records) {
            if (bStimulus.getStimulus().getwordType().equals(WordType.EXAMPLE_TARGET_NON_WORD)) {
                int bandIndex = bStimulus.getStimulus().getbandIndex();
                int currentBandCount = retVal.get(bandIndex);
                retVal.set(bandIndex, currentBandCount + 1);
            }
        }
        return retVal;
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

        // preprocessing: set the correctness
        // check if correcntess eval was set on the last shown stimulus (i.e. if the space was pressed, and analysie correctness if it was not
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

        return super.fineTuningToBeContinuedFirstWrongOut();
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
        ArrayList<Trial> trs = this.currentTrialTuple.getTrials();
        for (Trial trial : trs) {

            if (trial.getStimuli().size() < trial.getTrialLength() + 1) { // trial is (partially) used
                continue;
            }

            TrialCondition tc = trial.getCondition();
            int tl = trial.getTrialLength();
            int band = trial.getBandIndex();
            this.trials.get(band).get(tl).get(tc).add(trial);
        }
        this.availableCombinations = PermutationPair.initialiseAvailabilityList(this.trials, this.trialLengtPermutations, this.trialTypesPermutations, this.numberOfBands);
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
