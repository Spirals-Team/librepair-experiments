/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics, Nijmegen
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
// /Users/olhshk/Documents/ExperimentTemplate/FieldKitRecorder/src/android/nl/mpi/tg/eg/frinex/FieldKitRecorder.java
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import nl.mpi.tg.eg.frinex.common.AbstractStimuliProvider;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;

/**
 * @since Oct 27, 2017 2:01:33 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
/**
 * Generic BandStimuliProvider class.
 *
 * @param <A>
 */
public abstract class BandStimuliProvider<A extends BandStimulus> extends AbstractStimuliProvider {

    protected final static String[] FLDS = {"numberOfBands", "startBand", "fineTuningTupleLength", "fineTuningUpperBoundForCycles", "fastTrackPresent", "fineTuningFirstWrongOut",
        "bandIndexScore", "isCorrectCurrentResponse", "currentBandIndex", "totalStimuli",
        "responseRecord", "tupleFT",
        "bestBandFastTrack", "isFastTrackIsStillOn", "secondChanceFastTrackIsFired", "timeTickEndFastTrack",
        "enoughFineTuningStimulae", "bandVisitCounter", "cycle2helper",
        "cycle2", "champion", "looser", "justVisitedLastBand", "justVisitedLowestBand", "endOfRound", "errorMessage"};

    protected int numberOfBands = 0;

    protected int startBand = 0;
    protected int fineTuningTupleLength = 0;
    protected int fineTuningUpperBoundForCycles = 0;
    protected boolean fastTrackPresent = true;
    protected boolean fineTuningFirstWrongOut = true;

    protected int bandIndexScore = 0;
    protected Boolean isCorrectCurrentResponse;
    protected int currentBandIndex = 0;
    protected int totalStimuli = 0;

    protected ArrayList<BookkeepingStimulus<A>> responseRecord = new ArrayList<>();

    // fast track stuff
    private int bestIndexBandFastTrack = 0;
    protected boolean isFastTrackIsStillOn = true;
    protected boolean secondChanceFastTrackIsFired = false;
    protected int timeTickEndFastTrack = 0;

    // fine tuning stuff
    protected ArrayList<BookkeepingStimulus<A>> tupleFT = new ArrayList<>(this.fineTuningTupleLength);

    // fine tuning stopping
    protected boolean enoughFineTuningStimulae = true;
    protected Integer[] bandVisitCounter =  new Integer[0];
    protected Integer[] cycle2helper =   new Integer[0];
    protected boolean cycle2 = false;
    protected boolean champion = false;
    protected boolean looser = false;
    protected boolean justVisitedLastBand = false;
    protected boolean justVisitedLowestBand = false;
    protected String errorMessage = null;

    protected boolean endOfRound = false;

    //optional
    protected String maxDurationMinutes = null;
    protected long maxDurationMs = 0;
    protected long startTimeMs = 0;
    protected boolean timeOutExit = false;
    protected long durationMs = 0;
    protected ArrayList<Integer> bandVisitsByTrials; // bandVisitsByTrials[i] == # times the band with the index i was visited by trials

    // add experiment specific stuff here
    // ...
    public BandStimuliProvider(final Stimulus[] stimulusArray) {
        super(stimulusArray);
    }

    public boolean getfastTrackPresent() {
        return this.fastTrackPresent;
    }

    public void setfastTrackPresent(String fastTrackPresent) {
        this.fastTrackPresent = Boolean.parseBoolean(fastTrackPresent);
    }

    public boolean getfineTuningFirstWrongOut() {
        return this.fineTuningFirstWrongOut;
    }

    public void setfineTuningFirstWrongOut(String fineTuningFirstWrongOut) {
        this.fineTuningFirstWrongOut = Boolean.parseBoolean(fineTuningFirstWrongOut);
    }

    public int getnumberOfBands() {
        return this.numberOfBands;
    }

    public void setnumberOfBands(String numberOfBands) {
        this.numberOfBands = Integer.parseInt(numberOfBands);
    }

    public int getstartBand() {
        return this.startBand;
    }

    public void setstartBand(String startBand) {
        this.startBand = Integer.parseInt(startBand);
    }

    public void setmaxDurationMin(String maxDurationMin) {
        this.maxDurationMinutes = maxDurationMin;
    }

    public int getfineTuningTupleLength() {
        return this.fineTuningTupleLength;
    }

    public void setfineTuningTupleLength(String fineTuningTupleLength) {
        this.fineTuningTupleLength = Integer.parseInt(fineTuningTupleLength);
    }

    public int getfineTuningUpperBoundForCycles() {
        return this.fineTuningUpperBoundForCycles;
    }

    public void setfineTuningUpperBoundForCycles(String fineTuningUpperBoundForCycles) {
        this.fineTuningUpperBoundForCycles = Integer.parseInt(fineTuningUpperBoundForCycles);
    }

    public Integer[] getbandVisitCounter() {
        return this.bandVisitCounter;
    }

    public Integer[] getcycle2helper() {
        return this.cycle2helper;
    }

    public boolean getJustVisitedFirstBand() {
        return this.justVisitedLowestBand;
    }

    public boolean getJustVisitedLastBand() {
        return this.justVisitedLastBand;
    }

    public boolean getIsFastTrackIsStillOn() {
        return this.isFastTrackIsStillOn;
    }

    public boolean getSecondChanceFastTrackIsFired() {
        return this.secondChanceFastTrackIsFired;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public boolean getTimeOutExit() {
        return this.timeOutExit;
    }

    @Override
    public void initialiseStimuliState(String stimuliStateSnapshot) {

        if (stimuliStateSnapshot.trim().isEmpty()) {
            this.bandIndexScore = 0;
            this.isCorrectCurrentResponse = null;
            this.bandVisitCounter = new Integer[this.numberOfBands];

            //this.totalStimuli: see the child class
            this.enoughFineTuningStimulae = true;
            for (int i = 0; i < this.numberOfBands; i++) {
                this.bandVisitCounter[i] = 0;
            }

            this.cycle2helper = new Integer[this.fineTuningUpperBoundForCycles * 2 + 1];
            for (int i = 0; i < this.fineTuningUpperBoundForCycles * 2 + 1; i++) {
                this.cycle2helper[i] = 0;
            }
            this.cycle2 = false;
            this.champion = false;
            this.looser = false;
            this.justVisitedLastBand = false;
        } else {
            try {
                this.deserialiseToThis(stimuliStateSnapshot);
            } catch (Exception ex) {
                this.exceptionLogging(ex);
            }
        }

    }

    @Override
    public String generateStimuliStateSnapshot() {
        return this.toString();
    }

    public int getCurrentBandIndex() {
        return this.currentBandIndex;
    }

    public ArrayList<BookkeepingStimulus<A>> getResponseRecord() {
        return this.responseRecord;
    }

    public boolean getEnoughFinetuningStimuli() {
        return this.enoughFineTuningStimulae;
    }

    public boolean getCycel2() {
        return this.cycle2;
    }

    public boolean getChampion() {
        return this.champion;
    }

    public boolean getLooser() {
        return this.looser;
    }

    public int getBestFastTrackIndexBand() {
        return this.bestIndexBandFastTrack;
    }

    public int getBandIndexScore() {
        return this.bandIndexScore;
    }

    public abstract long getPercentageScore();

    public ArrayList<BookkeepingStimulus<A>> getFTtuple() {
        return this.tupleFT;
    }

    public int getEndFastTrackTimeTick() {
        return this.timeTickEndFastTrack;
    }

    // prepared by next stimulus
    @Override
    public A getCurrentStimulus() {
        A retVal = this.responseRecord.get(this.getCurrentStimulusIndex()).getStimulus();
        return retVal;
    }

    @Override
    public int getCurrentStimulusIndex() {
        return (this.responseRecord.size() - 1);
    }

    @Override
    public int getTotalStimuli() {
        return this.totalStimuli;
    }

    // actually defines if the series is to be continued or stopped
    // also set the band indices
    @Override
    public boolean hasNextStimulus(int increment) {
        if (this.endOfRound) {
            return false;
        }
        if (this.fastTrackPresent) {
            if (this.isFastTrackIsStillOn) { // fast track is still on, update it
                this.isFastTrackIsStillOn = this.fastTrackToBeContinuedWithSecondChance();
                if (this.isFastTrackIsStillOn) {
                    return true;
                } else {
                    // return false if not enough stimuli left
                    return this.switchToFineTuning();
                }
            } else {
                return this.fineTuningToBeContinued();
            }
        } else {
            return this.fineTuningToBeContinued();
        }

    }

    // all indices are updated in the "hasNextStimulus"
    @Override
    public void nextStimulus(int increment) {
        BookkeepingStimulus bStimulus;
        if (this.fastTrackPresent) {
            if (this.isFastTrackIsStillOn) {
                bStimulus = this.deriveNextFastTrackStimulus();
            } else {
                bStimulus = this.tupleFT.remove(0);
            }
        } else {
            bStimulus = this.tupleFT.remove(0);
        }
        this.responseRecord.add(bStimulus);

    }

    public abstract BookkeepingStimulus<A> deriveNextFastTrackStimulus();

    @Override
    public void setCurrentStimuliIndex(int currentStimuliIndex) {
        //  not relevant for now
        //this.fastTrackStimuliIndex = currentStimuliIndex;
    }

    @Override
    public String getCurrentStimulusUniqueId() {
        return getCurrentStimulus().getUniqueId();
    }

    @Override
    public String getHtmlStimuliReport() {
        String summary = this.getStringSummary("<tr>", "</tr>", "<td>", "</td>");
        String inhoudFineTuning = this.getStringFineTuningHistory("<tr>", "</tr>", "<td>", "</td>", "html");
        String inhoudFrequenceMap = this.getBandFrequenceTable("<tr>", "</tr>", "<td>", "</td>", "html");
        StringBuilder htmlStringBuilder = new StringBuilder();
        htmlStringBuilder.append("<p>User summary</p><table border=1>").append(summary).append("</table><br><br>");
        if (this.fastTrackPresent) {
            String inhoudFastTrack = this.getStringFastTrack("<tr>", "</tr>", "<td>", "</td>");
            htmlStringBuilder.append("<p>Fast Track History</p><table border=1>").append(inhoudFastTrack).append("</table><br><b>");
        }
        if (!inhoudFrequenceMap.isEmpty()) {
            htmlStringBuilder.append("<p>Band visit counter</p><table border=1>").append(inhoudFrequenceMap).append("</table>");
        }
        htmlStringBuilder.append("<p>Fine tuning History</p><table border=1>").append(inhoudFineTuning).append("</table>");

        return htmlStringBuilder.toString();

    }

    public String getBandFrequenceTable(String startRow, String endRow, String startColumn, String endColumn, String format) {
        return "";
    }

    @Override
    public Map<String, String> getStimuliReport(String reportType) {

        final LinkedHashMap<String, String> returnMap = new LinkedHashMap<>();

        switch (reportType) {
            case "user_summary": {
                String summary = this.getStringSummary("", "\n", "", ";");
                String inhoudFrequenceMap = this.getBandFrequenceTable("", "\n", "", ";", "csv");

                if (!inhoudFrequenceMap.isEmpty()) {
                    summary = summary + inhoudFrequenceMap;
                }
                LinkedHashMap<String, String> summaryMap = this.makeMapFromCsvString(summary);
                for (String key : summaryMap.keySet()) {
                    returnMap.put(key, summaryMap.get(key));
                }
                break;
            }
            case "fast_track": {
                String inhoudFastTrack = this.getStringFastTrack("", "\n", "", ";");
                LinkedHashMap<String, String> fastTrackMap = this.makeMapFromCsvString(inhoudFastTrack);
                for (String key : fastTrackMap.keySet()) {
                    returnMap.put(key, fastTrackMap.get(key));
                }
                break;
            }
            case "fine_tuning": {

                String inhoudFineTuning = this.getStringFineTuningHistory("", "\n", "", ";", "csv");
                LinkedHashMap<String, String> fineTuningBriefMap = this.makeMapFromCsvString(inhoudFineTuning);
                for (String key : fineTuningBriefMap.keySet()) {
                    returnMap.put(key, fineTuningBriefMap.get(key));
                }
                break;
            }
            default:
                break;

        }
        //returnMap.put("example_1", "1;2;3;4;5;6;7;8;9");
        //returnMap.put("example_2", "A;B;C;D;E;F;G;H;I");
        //returnMap.put("example_3", "X;X;X;X;X;X");
        //returnMap.put("number", "1");
        //returnMap.put("example", "1,2,3,4,5,6,7,8,9 \n 2,3,4,5,6,7,8,9,0");
        //returnMap.put("number", "1");
        return returnMap;
    }

    
    public boolean isCorrectResponse(Stimulus stimulus, String stimulusResponse) {
        int index = this.getCurrentStimulusIndex();
        this.responseRecord.get(index).setReaction(stimulusResponse);
        this.responseRecord.get(index).setTimeStamp(System.currentTimeMillis());
        this.isCorrectCurrentResponse = this.analyseCorrectness(stimulus, stimulusResponse);
        this.responseRecord.get(index).setCorrectness(this.isCorrectCurrentResponse);
        return this.isCorrectCurrentResponse;
    }

    protected abstract boolean analyseCorrectness(Stimulus stimulus, String stimulusResponse);

    // also updates indices
    // OVerride in the child class
    protected boolean fastTrackToBeContinuedWithSecondChance() {
        if (this.responseRecord.isEmpty()) {// just started the first experiment...
            return true;
        }
        boolean retVal;
        if (this.isCorrectCurrentResponse) {
            this.secondChanceFastTrackIsFired = false;
            if (this.currentBandIndex == (this.numberOfBands - 1)) {
                retVal = false;
            } else {
                this.currentBandIndex++;
                retVal = true;
            }

        } else {
            // hit incorrect? 
            if (this.secondChanceFastTrackIsFired) {
                retVal = false;
            } else {
                // giving the second chanse
                this.secondChanceFastTrackIsFired = true;
                retVal = true;
            }
        }

        if (retVal) {
            // check if we still have data for the next experiment
            retVal = this.enoughStimuliForFastTrack();
        }

        return retVal;
    }

    protected abstract boolean enoughStimuliForFastTrack();

    private boolean switchToFineTuning() {
        this.timeTickEndFastTrack = this.responseRecord.size() - 1; // the last time on fast track (if we start counting form zero)
        this.bestIndexBandFastTrack = this.currentBandIndex;
        return this.initialiseNextFineTuningTuple();
    }

    public abstract boolean initialiseNextFineTuningTuple();

    private boolean fineTuningToBeContinued() {

        boolean contRound;
        if (this.fineTuningFirstWrongOut) {
            contRound = this.fineTuningToBeContinuedFirstWrongOut();
        } else {
            contRound = this.fineTuningToBeContinuedWholeTuple();
        }
        this.endOfRound = !contRound;
        return contRound;
    }

    protected abstract void checkTimeOut();

    protected boolean fineTuningToBeContinuedFirstWrongOut() {

        boolean retVal;

        if (this.isCorrectCurrentResponse) {
            if (this.tupleIsNotEmpty()) {

                // we have not hit the last atom in the tuple yet
                // continue
                return true;
            } else {
                // the tuple is empty: register the fact that the band is finished 
                shiftFIFO(cycle2helper, this.currentBandIndex);
                this.bandVisitCounter[this.currentBandIndex]++;

                // tranistion to the higher band ?
                if (this.currentBandIndex == this.numberOfBands - 1) { // the last band is hit
                    if (this.justVisitedLastBand) {
                        this.champion = true;
                        this.bandIndexScore = this.numberOfBands - 1;
                        retVal = false; // stop interation, the last band visied twice in a row
                    } else {
                        this.justVisitedLastBand = true; // the second trial to be sure
                        // nextBand is the same
                        retVal = true;
                    }
                } else {
                    // ordinary next band step
                    this.justVisitedLastBand = false;
                    // go to the next band
                    this.currentBandIndex++;
                    retVal = true;
                }
            }
        } else {
            // register the fact that the band is finished 
            shiftFIFO(cycle2helper, this.currentBandIndex);
            this.bandVisitCounter[this.currentBandIndex]++;

            retVal = this.toBeContinuedLoopAndLooserChecker();

            this.recycleUnusedStimuli();
        }

        if (this.maxDurationMinutes != null) {
            if (retVal) {
                this.checkTimeOut();
            }
            retVal = retVal && (!this.timeOutExit);
        }

        if (retVal) {
            // check if there are enough stimuli left
            this.enoughFineTuningStimulae = this.initialiseNextFineTuningTuple();
            if (!this.enoughFineTuningStimulae) {
                System.out.println(this.errorMessage);
                retVal = false;
                this.bandIndexScore = this.mostOftenVisitedBandIndex(this.bandVisitCounter, this.currentBandIndex);
            }
        }
        
        if (!retVal) {
            long currentTimeMs = System.currentTimeMillis();
            this.durationMs  = currentTimeMs - this.startTimeMs;
        }
        
        

        return retVal;
    }

    protected boolean isTimeOut() {
        if (this.startTimeMs == 0) { // the coundown has not been set yet at all
            // it will be set when the very first stimulus will be asked to be shown
            return false;
        }
        
        long currentTimeMs = System.currentTimeMillis();
        long duration = currentTimeMs - this.startTimeMs;
        boolean retVal = (duration >= this.maxDurationMs);
        
        if (retVal) { // set exit values
            this.timeOutExit = true;
            this.durationMs = duration;
            this.bandIndexScore = this.getBandIndexScoreByTrialVisits(this.bandVisitsByTrials);
        }
        return retVal;
    }

    protected int getBandIndexScoreByTrialVisits(ArrayList<Integer> visitCounter) {
        int retVal;
        int max = 0;

        for (Integer bandVisits : visitCounter) {
            if (bandVisits > max) {
                max = bandVisits;
            }
        }
        int sum = 0;
        int amount = 0;
        for (int i = 0; i < visitCounter.size(); i++) {
            if (visitCounter.get(i) == max) {
                amount++;
                sum += i;
            }
        }
        float average = (float) sum / ((float) amount);
        retVal = Math.round(average);
        return retVal;
    }

    protected boolean fineTuningToBeContinuedWholeTuple() {

        boolean retVal;

        if (this.tupleIsNotEmpty()) {
            // we have not hit the last stimuli in the tuple yet
            // continue
            return true;
        } else {
            // tuple is empty: register that the band is finished 
            shiftFIFO(cycle2helper, this.currentBandIndex);
            this.bandVisitCounter[this.currentBandIndex]++;

            // analyse correctness of the last tuple as a whole
            boolean allTupleCorrect = this.isEnoughCorrectResponses();

            if (allTupleCorrect) {
                if (this.currentBandIndex == this.numberOfBands - 1) { // the last band is hit
                    if (this.justVisitedLastBand) {
                        this.champion = true;
                        this.bandIndexScore = this.numberOfBands - 1;
                        retVal = false; // stop interation, the last band visied twice in a row
                    } else {
                        this.justVisitedLastBand = true; // the second attempt to be sure
                        // nextBand is the same
                        retVal = true;
                    }
                } else {
                    // ordinary next band iteration
                    this.justVisitedLastBand = false;
                    // go to the next band
                    this.currentBandIndex++;
                    retVal = true;
                }
            } else {
                retVal = this.toBeContinuedLoopAndLooserChecker();
            }
        }
        if (this.maxDurationMinutes != null) {
            if (retVal) {
                this.checkTimeOut();
            }
            retVal = retVal && (!this.timeOutExit);
        }
        if (retVal) {
            // check if there are enough stimuli left
            this.enoughFineTuningStimulae = this.initialiseNextFineTuningTuple();
            if (!this.enoughFineTuningStimulae) {
                System.out.println(this.errorMessage);
                retVal = false;
                this.bandIndexScore = this.mostOftenVisitedBandIndex(this.bandVisitCounter, this.currentBandIndex);
            }

        }
        
        return retVal;
    }

    protected boolean tupleIsNotEmpty() {
        return this.tupleFT.size() > 0;
    }

    protected Boolean isEnoughCorrectResponses() {
        boolean allTupleCorrect = true;
        int lastIndex = this.responseRecord.size() - 1;
        int limit = (this.fineTuningTupleLength < this.responseRecord.size()) ? this.fineTuningTupleLength : this.responseRecord.size();
        for (int i = 0; i < limit; i++) {
            if (!this.responseRecord.get(lastIndex - i).getCorrectness()) {
                allTupleCorrect = false;
                break;
            }
        }
        return allTupleCorrect;
    }

    // experiment-specifice, must be overridden
    protected abstract void recycleUnusedStimuli();

    protected boolean toBeContinuedLoopAndLooserChecker() {
        boolean retVal;
        // detecting is should be stopped
        this.cycle2 = detectLoop(cycle2helper);
        if (this.cycle2) {
            System.out.println("Detected: " + this.fineTuningUpperBoundForCycles
                    + " times oscillation between two neighbouring bands, "
                    + this.cycle2helper[cycle2helper.length - 2] + " and " + this.cycle2helper[cycle2helper.length - 1]);
            this.bandIndexScore = (this.cycle2helper[cycle2helper.length - 1] < this.cycle2helper[cycle2helper.length - 2])
                    ? this.cycle2helper[cycle2helper.length - 1] : this.cycle2helper[cycle2helper.length - 2];

            retVal = false;
        } else {
            if (this.currentBandIndex == 0) {
                if (this.justVisitedLowestBand) {
                    this.looser = true;// stop interation, the first band is visited twice in a row
                    this.bandIndexScore = 0;
                    retVal = false;
                } else {
                    this.justVisitedLowestBand = true; // give the second chance
                    // nextBand is the same
                    retVal = true;
                }
            } else {
                this.justVisitedLowestBand = false;
                this.currentBandIndex--;
                retVal = true;
            }

        }
        return retVal;
    }

    public static boolean detectLoop(Integer[] arr) {
        for (int i = 0; i < arr.length - 2; i++) {
            if (arr[i] == 0 || arr[i + 2] == 0) {
                return false; // we are at the very beginning, to early to count loops
            }
            if (arr[i + 2] != arr[i]) {
                return false;
            }
        }
        return true;
    }

    public static void shiftFIFO(Integer[] fifo, int newelement) {
        for (int i = 0; i < fifo.length - 1; i++) {
            fifo[i] = fifo[i + 1];
        }
        fifo[fifo.length - 1] = newelement;
    }

    public int mostOftenVisitedBandIndex(Integer[] bandVisitCounter, int controlIndex) {

        int max = bandVisitCounter[0];
        ArrayList<Integer> indices = new ArrayList<>();
        indices.add(0);
        for (int i = 1; i < bandVisitCounter.length; i++) {
            if (max < bandVisitCounter[i]) {
                max = bandVisitCounter[i];
                indices.clear();
                indices.add(i);
            } else {
                if (max == bandVisitCounter[i]) {
                    indices.add(i);
                }
            }
        }

        // choose the band from "indices" which is closest to currentIndex;
        int retVal = indices.get(0);
        int diff = Math.abs(retVal - controlIndex);
        for (int i = 1; i < indices.size(); i++) {
            if (Math.abs(indices.get(i) - controlIndex) <= diff) {
                retVal = indices.get(i);
                diff = Math.abs(retVal - controlIndex);
            }
        }

        return retVal;

    }

    // Override,  very dependant  on the type of experiment
    public abstract String getStringFastTrack(String startRow, String endRow, String startColumn, String endColumn);

    // Override,  very dependant  on the type of experiment
    public abstract String getStringFineTuningHistory(String startRow, String endRow, String startColumn, String endColumn, String format);

    protected abstract String bandIndexToLabel(int bandIndex);

    public String getStringSummary(String startRow, String endRow, String startColumn, String endColumn) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(startRow);
        stringBuilder.append(startColumn).append("Score").append(endColumn);
        if (this.fastTrackPresent) {
            stringBuilder.append(startColumn).append("BestFastTrack").append(endColumn);
        }
        stringBuilder.append(startColumn).append("Cycel2oscillation").append(endColumn);
        stringBuilder.append(startColumn).append("EnoughFineTuningStimuli").append(endColumn);
        stringBuilder.append(startColumn).append("Champion").append(endColumn);
        stringBuilder.append(startColumn).append("Looser").append(endColumn);
        if (this.maxDurationMinutes != null) {
            stringBuilder.append(startColumn).append("Time-out exit").append(endColumn);
            stringBuilder.append(startColumn).append("Duration millisec").append(endColumn);
        }
        stringBuilder.append(endRow);
        stringBuilder.append(startRow);
        String bandLabelScore = this.bandIndexToLabel(this.bandIndexScore);
        stringBuilder.append(startColumn).append(bandLabelScore).append(endColumn);
        if (this.fastTrackPresent) {
            String bandLabelFastTrack = this.bandIndexToLabel(this.bestIndexBandFastTrack);
            stringBuilder.append(startColumn).append(bandLabelFastTrack).append(endColumn);
        }
        stringBuilder.append(startColumn).append(this.cycle2).append(endColumn);
        stringBuilder.append(startColumn).append(this.enoughFineTuningStimulae).append(endColumn);
        stringBuilder.append(startColumn).append(this.champion).append(endColumn);
        stringBuilder.append(startColumn).append(this.looser).append(endColumn);
        if (this.maxDurationMinutes != null) {
            stringBuilder.append(startColumn).append(this.timeOutExit).append(endColumn);
            stringBuilder.append(startColumn).append(this.durationMs).append(endColumn);
        }

        stringBuilder.append(endRow);
        return stringBuilder.toString();
    }

    private LinkedHashMap<String, String> makeMapFromCsvString(String csvTable) {
        String[] rows = csvTable.split("\n");
        LinkedHashMap<String, String> retVal = new LinkedHashMap();
        for (int i = 0; i < rows.length; i++) {
            String paddedInt = "" + i;
            while (paddedInt.length() < 6) {
                paddedInt = "0" + paddedInt;
            }
            retVal.put("row" + paddedInt, rows[i]);
        }
        return retVal;
    }

    protected Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        //map.put("fields", BandStimuliProvider.FLDS);
        map.put("numberOfBands", this.numberOfBands);
        map.put("startBand", this.startBand);
        map.put("fineTuningTupleLength", this.fineTuningTupleLength);
        map.put("fineTuningUpperBoundForCycles", this.fineTuningUpperBoundForCycles);
        map.put("fastTrackPresent", this.fastTrackPresent);
        map.put("fineTuningFirstWrongOut", this.fineTuningFirstWrongOut);

        map.put("bandIndexScore", this.bandIndexScore);
        map.put("isCorrectCurrentResponse", this.isCorrectCurrentResponse);
        map.put("currentBandIndex", this.currentBandIndex);
        map.put("totalStimuli", this.totalStimuli);

        map.put("responseRecord", this.responseRecord);
        map.put("tupleFT", this.tupleFT);

        map.put("bestBandFastTrack", this.bestIndexBandFastTrack);
        map.put("isFastTrackIsStillOn", this.isFastTrackIsStillOn);
        map.put("secondChanceFastTrackIsFired", this.secondChanceFastTrackIsFired);
        map.put("timeTickEndFastTrack", this.timeTickEndFastTrack);

        map.put("enoughFineTuningStimulae", this.enoughFineTuningStimulae);

        map.put("bandVisitCounter", Arrays.asList(this.bandVisitCounter));

        map.put("cycle2helper", Arrays.asList(this.cycle2helper));

        map.put("cycle2", this.cycle2);
        map.put("champion", this.champion);
        map.put("looser", this.looser);
        map.put("justVisitedLastBand", this.justVisitedLastBand);
        map.put("justVisitedLowestBand", this.justVisitedLowestBand);
        map.put("endOfRound", this.endOfRound);
        map.put("errorMessage", this.errorMessage);
        return map;
    }

    @Override
    public String toString() {
        Map<String, Object> map = this.toMap();
        return map.toString();
    }

    protected abstract void deserialiseSpecific(String str) throws Exception;

    //  percentageBandTable must be created from scratch. not serialised/deserialised
    protected void deserialiseToThis(String str) throws Exception {

        Map<String, Object> map = UtilsJSONdialect.stringToObjectMap(str, BandStimuliProvider.FLDS);

        this.numberOfBands = Integer.parseInt(map.get("numberOfBands").toString());
        this.startBand = Integer.parseInt(map.get("startBand").toString());
        this.fineTuningTupleLength = Integer.parseInt(map.get("fineTuningTupleLength").toString());
        this.fineTuningUpperBoundForCycles = Integer.parseInt(map.get("fineTuningUpperBoundForCycles").toString());
        this.fastTrackPresent = Boolean.parseBoolean(map.get("fastTrackPresent").toString());

        String wrongOutStr = map.get("fineTuningFirstWrongOut").toString();
        this.fineTuningFirstWrongOut = Boolean.parseBoolean(wrongOutStr);

        this.bandIndexScore = Integer.parseInt(map.get("bandIndexScore").toString());

        Object correctResponse = map.get("isCorrectCurrentResponse");

        this.isCorrectCurrentResponse = (correctResponse != null) ? Boolean.parseBoolean(correctResponse.toString()) : null;

        this.currentBandIndex = Integer.parseInt(map.get("currentBandIndex").toString());
        this.totalStimuli = Integer.parseInt(map.get("totalStimuli").toString());

        this.bestIndexBandFastTrack = Integer.parseInt(map.get("bestBandFastTrack").toString());
        this.isFastTrackIsStillOn = Boolean.parseBoolean(map.get("isFastTrackIsStillOn").toString());
        this.secondChanceFastTrackIsFired = Boolean.parseBoolean(map.get("secondChanceFastTrackIsFired").toString());
        this.timeTickEndFastTrack = Integer.parseInt(map.get("timeTickEndFastTrack").toString());

        this.enoughFineTuningStimulae = Boolean.parseBoolean(map.get("enoughFineTuningStimulae").toString());
        this.cycle2 = Boolean.parseBoolean(map.get("cycle2").toString());
        this.champion = Boolean.parseBoolean(map.get("champion").toString());
        this.looser = Boolean.parseBoolean(map.get("looser").toString());
        this.justVisitedLowestBand = Boolean.parseBoolean(map.get("justVisitedLowestBand").toString());
        this.justVisitedLastBand = Boolean.parseBoolean(map.get("justVisitedLastBand").toString());

        Object bandCounterObj = map.get("bandVisitCounter");
        this.bandVisitCounter = UtilsJSONdialect.objectToArrayInteger(bandCounterObj);

        Object cycle2Str = map.get("cycle2helper");
        this.cycle2helper = UtilsJSONdialect.objectToArrayInteger(cycle2Str);

        Object reportStatus = map.get("endOfRound");
        this.endOfRound = Boolean.parseBoolean(reportStatus.toString());

        Object help = map.get("errorMessage");
        this.errorMessage = (help != null) ? help.toString() : null;

    }

    protected void exceptionLogging(Exception ex) {
        System.out.println();
        System.out.println(ex);
        for (StackTraceElement message : ex.getStackTrace()) {
            System.out.println(message);

        }
        System.out.println();
    }

}
