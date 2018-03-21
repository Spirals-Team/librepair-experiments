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
import java.util.HashMap;
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
    
   
    protected final A[] stimuli;
    
    protected HashMap<String, A> hashedStimuli;


    protected int type = 0;
    protected int numberOfBands = 0;
    protected int numberOfSeries = 0;
    protected int startBand = 0;
    protected int fineTuningTupleLength = 0;
    protected int fineTuningUpperBoundForCycles;
    protected boolean fastTrackPresent = true;
    protected boolean fineTuningFirstWrongOut = true;

    protected int bandScore = -1;
    protected long percentageScore = 0;
    protected Boolean isCorrectCurrentResponse;
    protected int currentBandIndex = 0;
    protected int totalStimuli;

    final protected ArrayList<BookkeepingStimulus<A>> responseRecord = new ArrayList<>();

    protected LinkedHashMap<Long, Integer> percentageBandTable;

    // fast track stuff
    private int bestBandFastTrack = -1;
    protected boolean isFastTrackIsStillOn = true;
    protected boolean secondChanceFastTrackIsFired = false;
    protected int timeTickEndFastTrack = -1;

    // fine tuning stuff
    protected final ArrayList<BookkeepingStimulus<A>> tupleFT = new ArrayList<>(this.fineTuningTupleLength);
    
 
    // fine tuning stopping
    protected boolean enoughFineTuningStimulae = true;
    protected int[] bandVisitCounter;
    protected int[] cycle2helper;
    protected boolean cycle2 = false;
    protected boolean champion = false;
    protected boolean looser = false;
    protected boolean justVisitedLastBand = false;
    protected boolean justVisitedFirstBand = false;

    protected String htmlReport = "";

    protected String errorMessage;

    // add experiment specific stuff here
    // ...
    
    public BandStimuliProvider(final A[] stimulusArray) {
        super(stimulusArray);
        this.stimuli = stimulusArray;
    }
    
    public void settype(String type) {
        this.type = Integer.parseInt(type);
    }

    public void setfastTrackPresent(String fastTrackPresent) {
        this.fastTrackPresent = Boolean.parseBoolean(fastTrackPresent);
    }

    public void setfineTuningFirstWrongOut(String fineTuningFirstWrongOut) {
        this.fineTuningFirstWrongOut = Boolean.parseBoolean(fineTuningFirstWrongOut);
    }

    public void setnumberOfBands(String numberOfBands) {
        this.numberOfBands = Integer.parseInt(numberOfBands);
    }

    public void setnumberOfSeries(String numberOfSeries) {
        this.numberOfSeries = Integer.parseInt(numberOfSeries);
    }

    public void setstartBand(String startBand) {
        this.startBand = Integer.parseInt(startBand);
    }

    public void setfineTuningTupleLength(String fineTuningTupleLength) {
        this.fineTuningTupleLength = Integer.parseInt(fineTuningTupleLength);
    }

    public void setfineTuningUpperBoundForCycles(String fineTuningUpperBoundForCycles) {
        this.fineTuningUpperBoundForCycles = Integer.parseInt(fineTuningUpperBoundForCycles);
    }

    @Override
    public void initialiseStimuliState(String stimuliStateSnapshot) {
        //this.stimuli = this.GETPETER!!
        this.bandScore = -1;
        this.percentageScore = 0;
        this.isCorrectCurrentResponse = null;
        this.currentBandIndex = this.startBand - 1;
        this.bandVisitCounter = new int[this.numberOfBands];

        //this.totalStimuli: see the child class
        this.enoughFineTuningStimulae = true;
        for (int i = 0; i < this.numberOfBands; i++) {
            this.bandVisitCounter[i] = 0;
        }

        this.cycle2helper = new int[this.fineTuningUpperBoundForCycles * 2 + 1];
        for (int i = 0; i < this.fineTuningUpperBoundForCycles * 2 + 1; i++) {
            this.cycle2helper[i] = 0;
        }
        this.cycle2 = false;
        this.champion = false;
        this.looser = false;
        this.justVisitedLastBand = false;
        this.percentageBandTable = this.generatePercentageBandTable();

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
        return this.htmlReport;
    }

    public int getCurrentBandIndex() {
        return this.currentBandIndex;
    }

    public int getNumberOfBands() {
        return this.numberOfBands;
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
        if (this.percentageScore <= 0) { // not computed yet
            this.percentageScore = this.bandNumberIntoPercentage(this.bandScore);
        }
        return this.percentageScore;
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
        A  retVal = this.responseRecord.get(this.getCurrentStimulusIndex()).getStimulus();
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
        if (!this.htmlReport.equals("")) { // the report is generated, no stimuli more
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

    // experiment-specific, must be overridden
    public BookkeepingStimulus deriveNextFastTrackStimulus() {
        return null;
    }

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
        if (this.htmlReport.equals("")) {
            String summary = this.getStringSummary("<tr>", "</tr>", "<td>", "</td>");
            String inhoudFineTuning = this.getStringFineTuningHistory("<tr>", "</tr>", "<td>", "</td>", "html");
            StringBuilder htmlStringBuilder = new StringBuilder();
            htmlStringBuilder.append("<p>User summary</p><table border=1>").append(summary).append("</table><br><br>");
            if (this.fastTrackPresent) {
                String inhoudFastTrack = this.getStringFastTrack("<tr>", "</tr>", "<td>", "</td>");
                htmlStringBuilder.append("<p>Fast Track History</p><table border=1>").append(inhoudFastTrack).append("</table><br><b>");
            }
            htmlStringBuilder.append("<p>Fine tuning History</p><table border=1>").append(inhoudFineTuning).append("</table>");
            this.htmlReport = htmlStringBuilder.toString();
        }
        return this.htmlReport;
    }

    @Override
    public Map<String, String> getStimuliReport(String reportType) {

        final LinkedHashMap<String, String> returnMap = new LinkedHashMap<>();

        if (!this.htmlReport.equals("")) { // reports have been already generated 
            return returnMap;
        }

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

    // experiment specific, must be overridden
    protected boolean analyseCorrectness(Stimulus stimulus, String stimulusResponse) {
        return true;
    }

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

    // experiment specific, should be overridden
    protected boolean enoughStimuliForFastTrack() {
        return true;
    }

    private boolean switchToFineTuning() {
        this.timeTickEndFastTrack = this.responseRecord.size() - 1; // the last time on fast track (if we start counting form zero)
        this.bestBandFastTrack = this.currentBandIndex + 1; // band number is 1 more w.r.t bandIndex, with indexing offsets start woth zero
        return this.initialiseNextFineTuningTuple();
    }

    // experiment specific, must be overridden
    public boolean initialiseNextFineTuningTuple() {

        for (int i = 0; i < this.fineTuningTupleLength; i++) {
            this.tupleFT.add(null);
        }
        this.errorMessage = "This method must have been overriden in the implementing class";
        return false;
    }

    private boolean fineTuningToBeContinued() {
        if (this.fineTuningFirstWrongOut) {
            return this.fineTuningToBeContinuedFirstWrongOut();
        } else {
            return this.fineTuningToBeContinuedWholeTuple();
        }
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
            boolean allTupleCorrect = this.allTupleIsCorrect();

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
    protected Boolean allTupleIsCorrect() {
        boolean allTupleCorrect = true;
        int lastIndex = this.responseRecord.size() - 1;
        for (int i = 0; i < this.fineTuningTupleLength; i++) {
            if (!this.responseRecord.get(lastIndex - i).getCorrectness()) {
                allTupleCorrect = false;
                break;
            }
        }
        return allTupleCorrect;
    }

    protected long bandNumberIntoPercentage(int bandNumber) {
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
    protected void recycleUnusedStimuli() {

    }

    protected boolean toBeContinuedLoopChecker() {
        boolean retVal;
        // detecting is should be stopped
        this.cycle2 = detectLoop(cycle2helper);
        if (this.cycle2) {
            System.out.println("Detected: " + this.fineTuningUpperBoundForCycles + " times oscillation between two neighbouring bands");
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

    public static boolean detectLoop(int[] arr) {
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

    public static void shiftFIFO(int[] fifo, int newelement) {
        for (int i = 0; i < fifo.length - 1; i++) {
            fifo[i] = fifo[i + 1];
        }
        fifo[fifo.length - 1] = newelement;
    }

    public int mostOftenVisitedBandNumber(int[] bandVisitCounter, int controlIndex) {

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
    public String getStringFastTrack(String startRow, String endRow, String startColumn, String endColumn) {
        return "";
    }

    // Override,  very dependant  on the type of experiment
    public String getStringFineTuningHistory(String startRow, String endRow, String startColumn, String endColumn, String format) {
        return "";
    }

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

    @Override
    public String toString() {
//    protected int type = 0;
//    protected int numberOfBands = 0;
//    protected int numberOfSeries = 0;
//    protected int startBand = 0;
//    protected int fineTuningTupleLength = 0;
//    protected int fineTuningUpperBoundForCycles;
//    protected boolean fastTrackPresent = true;
//    protected boolean fineTuningFirstWrongOut = true;
//
//    protected int bandScore = -1;
//    protected long percentageScore = 0;
//    protected Boolean isCorrectCurrentResponse;
//    protected int currentBandIndex = 0;
//    protected int totalStimuli;
//
        StringBuilder builder = new StringBuilder();

        builder.append("type:{").append(this.type).append("},");
        builder.append("numberOfBands:{").append(this.numberOfBands).append("},");
        builder.append("numberOfSeries:{").append(this.numberOfSeries).append("},");
        builder.append("startBand:{").append(this.startBand).append("},");
        builder.append("fineTuningTupleLength:{").append(this.fineTuningTupleLength).append("},");
        builder.append("fineTuningUpperBoundForCycles:{").append(this.fineTuningUpperBoundForCycles).append("},");
        builder.append("fastTrackPresent:{").append(this.fastTrackPresent).append("},");
        builder.append("fineTuningFirstWrongOut:{").append(this.fineTuningFirstWrongOut).append("},");

        builder.append("bandScore:{").append(this.bandScore).append("},");
        builder.append("percentageScore:{").append(this.percentageScore).append("},");
        builder.append("isCorrectCurrentResponse:{").append(this.isCorrectCurrentResponse).append("},");
        builder.append("currentBandIndex:{").append(this.currentBandIndex).append("},");
        builder.append("totalStimuli:{").append(this.totalStimuli).append("},");

        UtilsJSONdialect<BookkeepingStimulus<A>> util = new UtilsJSONdialect<BookkeepingStimulus<A>>();
        try {
            String responseRecordString = util.arrayListToString(this.responseRecord);
            if (responseRecordString != null) {
                builder.append("responseRecord:").append(responseRecordString).append(",");
            }

        } catch (Exception ex) {
            return null;
        }

// fast track stuff
//    private int bestBandFastTrack = -1;
//    protected boolean isFastTrackIsStillOn = true;
//    protected boolean secondChanceFastTrackIsFired = false;
//    protected int timeTickEndFastTrack = -1;
        builder.append("bestBandFastTrack:{").append(this.bestBandFastTrack).append("},");
        builder.append("isFastTrackIsStillOn:{").append(this.isFastTrackIsStillOn).append("},");
        builder.append("secondChanceFastTrackIsFired:{").append(this.secondChanceFastTrackIsFired).append("},");
        builder.append("timeTickEndFastTrack:{").append(this.timeTickEndFastTrack).append("},");

        // fine tuning stuff
//    protected final ArrayList<BookkeepingStimulus> tupleFT = new ArrayList<>(this.fineTuningTupleLength);
        try {
            String tupleFTString = util.arrayListToString(this.tupleFT);
            if (tupleFTString != null) {
                builder.append("tupleFT:").append(tupleFTString).append(",");
            }
        } catch (Exception ex) {
            return null;
        }

//    // fine tuning stopping
//    protected boolean enoughFineTuningStimulae = true;
//    protected int[] bandVisitCounter;
//    protected int[] cycle2helper;
//    protected boolean cycle2 = false;
//    protected boolean champion = false;
//    protected boolean looser = false;
//    protected boolean justVisitedLastBand = false;
//    protected boolean justVisitedFirstBand = false;
        builder.append("enoughFineTuningStimulae:{").append(this.enoughFineTuningStimulae).append("},");

        try {
            String bandVisitCounterStr = util.intArrayListToString(this.bandVisitCounter);
            if (bandVisitCounterStr != null) {
                builder.append("bandVisitCounter:").append(bandVisitCounterStr).append(",");
            }
        } catch (Exception ex) {
            return null;
        }

        try {
            String cycle2helperStr = util.intArrayListToString(this.cycle2helper);
            if (cycle2helperStr != null) {
                builder.append("cycle2helper:").append(cycle2helperStr).append(",");
            }
        } catch (Exception ex) {
            return null;
        }

        builder.append("cycle2:{").append(this.cycle2).append("},");

        builder.append("champion:{").append(this.champion).append("},");
        builder.append("looser:{").append(this.looser).append("},");
        builder.append("isFastTrackIsStillOn:{").append(this.isFastTrackIsStillOn).append("},");
        builder.append("justVisitedLastBand:{").append(this.justVisitedLastBand).append("},");
        builder.append("justVisitedFirstBand:{").append(this.justVisitedFirstBand).append("},");
//
//    protected String htmlReport = "";
//
//    protected String errorMessage; 

        builder.append("htmlReport:{").append(this.htmlReport).append("}");
        if (this.errorMessage != null) {
            builder.append(",errorMessage:{").append(this.errorMessage).append("}");
        }

        return builder.toString();
    }
    
  
}
