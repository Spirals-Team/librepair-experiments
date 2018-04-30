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
            "bandScore", "isCorrectCurrentResponse", "currentBandIndex", "totalStimuli", 
             "responseRecord", "tupleFT", 
            "bestBandFastTrack", "isFastTrackIsStillOn", "secondChanceFastTrackIsFired", "timeTickEndFastTrack", 
            "enoughFineTuningStimulae", "bandVisitCounter", "cycle2helper", 
            "cycle2", "champion", "looser", "justVisitedLastBand", "justVisitedFirstBand", "endOfRound", "errorMessage"};
    
    
    protected int numberOfBands = 0;
    
    protected int startBand = 0;
    protected int fineTuningTupleLength = 0;
    protected int fineTuningUpperBoundForCycles = 0;
    protected boolean fastTrackPresent = true;
    protected boolean fineTuningFirstWrongOut = true;

    protected int bandScore = 0;
    protected Boolean isCorrectCurrentResponse;
    protected int currentBandIndex = 0;
    protected int totalStimuli=0;

    protected ArrayList<BookkeepingStimulus<A>> responseRecord = new ArrayList<>();

    protected LinkedHashMap<Long, Integer> percentageBandTable;

    // fast track stuff
    private int bestBandFastTrack = 0;
    protected boolean isFastTrackIsStillOn = true;
    protected boolean secondChanceFastTrackIsFired = false;
    protected int timeTickEndFastTrack = 0;

    // fine tuning stuff
    protected ArrayList<BookkeepingStimulus<A>> tupleFT = new ArrayList<>(this.fineTuningTupleLength);

    // fine tuning stopping
    protected boolean enoughFineTuningStimulae = true;
    protected Integer[] bandVisitCounter;
    protected Integer[] cycle2helper;
    protected boolean cycle2 = false;
    protected boolean champion = false;
    protected boolean looser = false;
    protected boolean justVisitedLastBand = false;
    protected boolean justVisitedFirstBand = false;
    protected String errorMessage=null;
    
    protected boolean endOfRound = false;

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
        return this.justVisitedFirstBand;
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

    @Override
    public void initialiseStimuliState(String stimuliStateSnapshot) {

        if (stimuliStateSnapshot.trim().isEmpty()) {
            this.bandScore = 0;
            this.isCorrectCurrentResponse = null;
            this.currentBandIndex = this.startBand - 1;
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
            this.percentageBandTable = this.generatePercentageBandTable();
        } else {
            try {
                this.deserialiseToThis(stimuliStateSnapshot);
                this.percentageBandTable = this.generatePercentageBandTable();
            } catch (Exception ex) {
                System.out.println();
                System.out.println(Arrays.asList(ex.getStackTrace()));
                System.out.println();
            }
        }

    }

    private LinkedHashMap<Long, Integer> generatePercentageBandTable() {
        LinkedHashMap<Long, Integer> retVal = new LinkedHashMap<Long, Integer>();
        Integer value1 = this.percentageIntoBandNumber(1);
        retVal.put(new Long(1), value1);
        for (int p = 1; p <= 9; p++) {
            long percentage = p * 10;
            Integer value = this.percentageIntoBandNumber(percentage);
            retVal.put(percentage, value);

        }
        Integer value99 = this.percentageIntoBandNumber(99);
        retVal.put(new Long(99), value99);
        return retVal;
    }


    @Override
    public String generateStimuliStateSnapshot() {
        return this.toString();
    }

    public int getCurrentBandIndex() {
        return this.currentBandIndex;
    }

    public LinkedHashMap<Long, Integer> getPercentageBandTable() {
        return this.percentageBandTable;
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

    public int getBestFastTrackBand() {
        return this.bestBandFastTrack;
    }

    public int getBandScore() {
        return this.bandScore;
    }

    public long getPercentageScore() {
        long percentageScore = this.bandNumberIntoPercentage(this.bandScore);
        return percentageScore;
    }

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
        StringBuilder htmlStringBuilder = new StringBuilder();
        htmlStringBuilder.append("<p>User summary</p><table border=1>").append(summary).append("</table><br><br>");
        if (this.fastTrackPresent) {
            String inhoudFastTrack = this.getStringFastTrack("<tr>", "</tr>", "<td>", "</td>");
            htmlStringBuilder.append("<p>Fast Track History</p><table border=1>").append(inhoudFastTrack).append("</table><br><b>");
        }
        htmlStringBuilder.append("<p>Fine tuning History</p><table border=1>").append(inhoudFineTuning).append("</table>");
        return htmlStringBuilder.toString();
        
    }

    @Override
    public Map<String, String> getStimuliReport(String reportType) {

        final LinkedHashMap<String, String> returnMap = new LinkedHashMap<>();

        switch (reportType) {
            case "user_summary": {
                String summary = this.getStringSummary("", "\n", "", ";");
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

    @Override
    public boolean isCorrectResponse(Stimulus stimulus, String stimulusResponse) {
        int index = this.getCurrentStimulusIndex();
        this.responseRecord.get(index).setTimeStamp(System.currentTimeMillis());
        this.isCorrectCurrentResponse = this.analyseCorrectness(stimulus, stimulusResponse);
        this.responseRecord.get(index).setCorrectness(this.isCorrectCurrentResponse);
        this.responseRecord.get(index).setReaction(stimulusResponse);
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
        this.bestBandFastTrack = this.currentBandIndex + 1; // band number is 1 more w.r.t bandIndex, with indexing offsets start woth zero
        return this.initialiseNextFineTuningTuple();
    }

    public abstract boolean initialiseNextFineTuningTuple();

    private boolean fineTuningToBeContinued() {
        boolean contRound;
        if (this.fineTuningFirstWrongOut) {
            contRound = this.fineTuningToBeContinuedFirstWrongOut();
        } else {
            contRound=  this.fineTuningToBeContinuedWholeTuple();
        }
        this.endOfRound = !contRound;
        return contRound;
    }

    private boolean fineTuningToBeContinuedFirstWrongOut() {

        boolean retVal;
        int currentBandNumber = this.currentBandIndex + 1; // memoise currentBandNumber == index +1

        if (this.isCorrectCurrentResponse) {
            if (this.tupleFT.size() > 0) {
                // we have not hit the last atom in the tuple yet
                // continue
                return true;
            } else {
                // register finishing band 
                this.bandVisitCounter[this.currentBandIndex]++;

                // tranistion to the higher band ?
                if (this.currentBandIndex == this.numberOfBands - 1) { // the last band is hit
                    if (this.justVisitedLastBand) {
                        this.champion = true;
                        this.bandScore = this.numberOfBands;
                        retVal = false; // stop interation, the last band visied twice in a row
                    } else {
                        this.justVisitedLastBand = true; // the second trial to be sure
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
            }
        } else {
            // register finishing band 
            this.bandVisitCounter[this.currentBandIndex]++;

            // put back unused element of the tuple
            // recycling
            boolean ended = this.tupleFT.isEmpty();
            while (!ended) {
                this.recycleUnusedStimuli();
                ended = this.tupleFT.isEmpty();
            }

            retVal = this.toBeContinuedLoopChecker();
        }

        if (retVal) {
            shiftFIFO(cycle2helper, currentBandNumber); // update the loop detector
            // check if there are enough stimuli left
            this.enoughFineTuningStimulae = this.initialiseNextFineTuningTuple();
            if (!this.enoughFineTuningStimulae) {
                System.out.println(this.errorMessage);
                retVal = false;
                this.bandScore = this.mostOftenVisitedBandNumber(this.bandVisitCounter, this.currentBandIndex);
            }

        }
        return retVal;
    }

    protected boolean fineTuningToBeContinuedWholeTuple() {

        boolean retVal;

        if (this.tupleIsNotEmpty()) {
            // we have not hit the last stimuli in the tuple yet
            // continue
            return true;
        } else {
            // register finishing band 
            this.bandVisitCounter[this.currentBandIndex]++;

            // analyse correctness of the last tuple as a whole
            boolean allTupleCorrect = this.isWholeTupleCorrect();

            if (allTupleCorrect) {
                if (this.currentBandIndex == this.numberOfBands - 1) { // the last band is hit
                    if (this.justVisitedLastBand) {
                        this.champion = true;
                        this.bandScore = this.numberOfBands;
                        retVal = false; // stop interation, the last band visied twice in a row
                    } else {
                        this.justVisitedLastBand = true; // the second trial to be sure
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
                // register finishing band 
                this.bandVisitCounter[this.currentBandIndex]++;
                retVal = this.toBeContinuedLoopChecker();
            }
        }
        if (retVal) {
            int currentBandNumber = this.currentBandIndex + 1; // memoise currentBandNumber == index +1
            shiftFIFO(cycle2helper, currentBandNumber); // update the loop detector
            // check if there are enough stimuli left
            this.enoughFineTuningStimulae = this.initialiseNextFineTuningTuple();
            if (!this.enoughFineTuningStimulae) {
                System.out.println(this.errorMessage);
                retVal = false;
                this.bandScore = this.mostOftenVisitedBandNumber(this.bandVisitCounter, this.currentBandIndex);
            }

        }
        return retVal;
    }

    protected boolean tupleIsNotEmpty() {
        return this.tupleFT.size() > 0;
    }

    // must be overriden for trial tuples
    protected Boolean isWholeTupleCorrect() {
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

    public long bandNumberIntoPercentage(int bandNumber) {
        double tmp = ((double) bandNumber * 100.0) / this.numberOfBands;
        long retVal = Math.round(tmp);
        return retVal;
    }

    protected int percentageIntoBandNumber(long percentage) {
        float tmp = ((float) percentage * this.numberOfBands) / 100;
        int retVal = Math.round(tmp);
        return retVal;
    }

    // experiment-specifice, must be overridden
    protected abstract void recycleUnusedStimuli();

    protected boolean toBeContinuedLoopChecker() {
        boolean retVal;
        // detecting is should be stopped
        this.cycle2 = detectLoop(cycle2helper);
        if (this.cycle2) {
            System.out.println("Detected: " + this.fineTuningUpperBoundForCycles + 
                    " times oscillation between two neighbouring bands, "+
                    this.cycle2helper[cycle2helper.length - 2] + " and "+this.cycle2helper[cycle2helper.length - 1]);
            this.bandScore = this.cycle2helper[cycle2helper.length - 1];

            //Here implemented loop-based approach , with the last element excluded from loop detection
            // x, x+1, x, x+1, x, (x+1)  (error, could have passed to x, if was not stopped) -> x
            // x+1, x, x+1, x, x+1, (x+2)  (error, could have passed to x+1, if was not stopped) -> x+1
            //Alternative-2 loop-based with the last element taken into account during the loop detection
            // x, x+1, x, x+1, x  (error) -> x
            // x+1, x, x+1, x, x+1 (error) -> x+1
            //Alternative-1 oscillation-based
            // x, x+1, x, x+1, x, x+1 (error) -> x+1
            // x+1, x, x+1, x, x+1, x (error) -> x
            retVal = false;
        } else {
            if (this.currentBandIndex == 0) {
                if (this.justVisitedFirstBand) {
                    this.looser = true;// stop interation, the first band is visited twice in a row
                    this.bandScore = 1;
                    retVal = false;
                } else {
                    this.justVisitedFirstBand = true; // give the second chance
                    // nextBand is the same
                    retVal = true;
                }
            } else {
                this.justVisitedFirstBand = false;
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

    public int mostOftenVisitedBandNumber(Integer[] bandVisitCounter, int controlIndex) {

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
        int ind = 0;
        for (int i = 1; i < indices.size(); i++) {
            if (Math.abs(indices.get(i) - controlIndex) < diff) {
                retVal = indices.get(i);
                diff = Math.abs(retVal - controlIndex);
                ind = i;
            }
        }

        int retValSym = indices.get(indices.size() - 1);
        diff = Math.abs(retValSym - controlIndex);
        int indexSym = indices.size() - 1;
        for (int i = indices.size() - 2; i >= 0; i--) {
            if (Math.abs(indices.get(i) - controlIndex) < diff) {
                retValSym = indices.get(i);
                diff = Math.abs(retValSym - controlIndex);
                indexSym = i;
            }
        }

        if (indices.size() - indexSym >= ind + 1) {
            retVal = retValSym;
        }
        return retVal + 1;

    }

    // Override,  very dependant  on the type of experiment
    public abstract String getStringFastTrack(String startRow, String endRow, String startColumn, String endColumn);

    // Override,  very dependant  on the type of experiment
    public abstract String getStringFineTuningHistory(String startRow, String endRow, String startColumn, String endColumn, String format);

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
        stringBuilder.append(endRow);
        stringBuilder.append(startRow);
        stringBuilder.append(startColumn).append(this.bandScore).append(endColumn);
        if (this.fastTrackPresent) {
            stringBuilder.append(startColumn).append(this.bestBandFastTrack).append(endColumn);
        }
        stringBuilder.append(startColumn).append(this.cycle2).append(endColumn);
        stringBuilder.append(startColumn).append(this.enoughFineTuningStimulae).append(endColumn);
        stringBuilder.append(startColumn).append(this.champion).append(endColumn);
        stringBuilder.append(startColumn).append(this.looser).append(endColumn);
        
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
    
    protected Map<String, Object> toMap(){
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        //map.put("fields", BandStimuliProvider.FLDS);
        map.put("numberOfBands", this.numberOfBands);
        map.put("startBand", this.startBand);
        map.put("fineTuningTupleLength", this.fineTuningTupleLength);
        map.put("fineTuningUpperBoundForCycles", this.fineTuningUpperBoundForCycles);
        map.put("fastTrackPresent", this.fastTrackPresent);
        map.put("fineTuningFirstWrongOut", this.fineTuningFirstWrongOut);
        
        map.put("bandScore", this.bandScore);
        map.put("isCorrectCurrentResponse", this.isCorrectCurrentResponse);
        map.put("currentBandIndex", this.currentBandIndex);
        map.put("totalStimuli", this.totalStimuli);
        
        map.put("responseRecord", this.responseRecord);
        map.put("percentageBandTable", this.percentageBandTable);
        map.put("tupleFT", this.tupleFT);
        
        map.put("bestBandFastTrack", this.bestBandFastTrack);
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
        map.put("justVisitedFirstBand", this.justVisitedFirstBand);
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
    protected void deserialiseToThis(String str) throws Exception{
      
        Map<String,Object> map = UtilsJSONdialect.stringToObjectMap(str,BandStimuliProvider.FLDS);
        
        this.numberOfBands = Integer.parseInt(map.get("numberOfBands").toString());
        this.startBand = Integer.parseInt(map.get("startBand").toString());
        this.fineTuningTupleLength = Integer.parseInt(map.get("fineTuningTupleLength").toString());
        this.fineTuningUpperBoundForCycles = Integer.parseInt(map.get("fineTuningUpperBoundForCycles").toString());
        this.fastTrackPresent = Boolean.parseBoolean(map.get("fastTrackPresent").toString());

        String wrongOutStr = map.get("fineTuningFirstWrongOut").toString();
        this.fineTuningFirstWrongOut = Boolean.parseBoolean(wrongOutStr);

        this.bandScore = Integer.parseInt(map.get("bandScore").toString());

        Object correctResponse = map.get("isCorrectCurrentResponse");

        this.isCorrectCurrentResponse = (correctResponse != null) ? Boolean.parseBoolean(correctResponse.toString()) : null;

        this.currentBandIndex = Integer.parseInt(map.get("currentBandIndex").toString());
        this.totalStimuli = Integer.parseInt(map.get("totalStimuli").toString());

        this.bestBandFastTrack = Integer.parseInt(map.get("bestBandFastTrack").toString());
        this.isFastTrackIsStillOn = Boolean.parseBoolean(map.get("isFastTrackIsStillOn").toString());
        this.secondChanceFastTrackIsFired = Boolean.parseBoolean(map.get("secondChanceFastTrackIsFired").toString());
        this.timeTickEndFastTrack = Integer.parseInt(map.get("timeTickEndFastTrack").toString());

        this.enoughFineTuningStimulae = Boolean.parseBoolean(map.get("enoughFineTuningStimulae").toString());
        this.cycle2 = Boolean.parseBoolean(map.get("cycle2").toString());
        this.champion = Boolean.parseBoolean(map.get("champion").toString());
        this.looser = Boolean.parseBoolean(map.get("looser").toString());
        this.justVisitedFirstBand = Boolean.parseBoolean(map.get("justVisitedFirstBand").toString());
        this.justVisitedLastBand = Boolean.parseBoolean(map.get("justVisitedLastBand").toString());

       
        Object bandCounterObj = map.get("bandVisitCounter");
        this.bandVisitCounter = UtilsJSONdialect.objectToArrayInteger(bandCounterObj);
        
        Object cycle2Str = map.get("cycle2helper");
        this.cycle2helper =  UtilsJSONdialect.objectToArrayInteger(cycle2Str);

        Object reportStatus = map.get("endOfRound");
        this.endOfRound = Boolean.parseBoolean(reportStatus.toString());
        
        Object help = map.get("errorMessage");
        this.errorMessage = (help != null) ? help.toString() : null;
        
        
    }
    
    

}
