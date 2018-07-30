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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BandStimuliProvider;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BookkeepingStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.UtilsJSONdialect;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.peabody.PeabodyStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.peabodypool.Indices;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.peabodypool.PeabodyStimuliFromString;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;

/**
 *
 * @author olhshk
 */
public class PeabodyStimuliProvider extends BandStimuliProvider<PeabodyStimulus> {

    private final static String[] SPECIFIC_FLDS = {"finalScore", "responseRecord", "tupleFT"};

    private int baseSetIndex;
    private String stimuliDir;

    private final int maxMistakesForOkBase = 4;
    private final int maxMistakesForOk = 8;
    private int finalScore = 0;
   

    ArrayList<ArrayList<PeabodyStimulus>> stimuliPool;

    public PeabodyStimuliProvider(Stimulus[] stimulusArray) {
        super(stimulusArray);
    }

    @Override
    public void initialiseStimuliState(String stimuliStateSnapshot) {

        this.baseSetIndex = this.startBand - 1;

        if (stimuliStateSnapshot.trim().isEmpty()) {
            this.bandIndexScore = 0;
            this.isCorrectCurrentResponse = null;

            this.enoughFineTuningStimulae = true;
            this.champion = false;
            this.looser = false;

            this.currentBandIndex = this.baseSetIndex;

            PeabodyStimuliFromString reader = new PeabodyStimuliFromString();

            try {
                reader.parseWordsInputCSVString(this, this.numberOfBands, this.stimuliDir);
                this.stimuliPool = reader.getStimuliByBands();
                this.initialiseNextFineTuningTuple();
            } catch (Exception exReading) {
                this.exceptionLogging(exReading);
            }

        } else {
            try {
                this.deserialiseToThis(stimuliStateSnapshot);
                this.deserialiseSpecific(stimuliStateSnapshot);
            } catch (Exception ex) {
                this.exceptionLogging(ex);
            }
        }

    }

    public ArrayList<ArrayList<PeabodyStimulus>> getStimuliPool() {
        return this.stimuliPool;
    }

    public void setstimuliDir(String dir) {
        this.stimuliDir = dir;
    }

    // in peabody experiment tupel contains 12 units (a unit/stimulus is picure + audio)
    @Override
    protected boolean fineTuningToBeContinuedWholeTuple() {

        boolean retVal;

        if (this.tupleIsNotEmpty()) {
            // we have not hit the last stimuli in the tuple yet
            // continue
            return true;
        } else {
            // tuple is empty: register that the band is finished 

            // analyse correctness of the current tuple as a whole
            boolean enoughCorrect = this.isEnoughCorrectResponses();

            if (enoughCorrect) {
                if (this.currentBandIndex == this.numberOfBands - 1) { // the last band is hit
                    this.champion = true;
                    this.bandIndexScore = this.numberOfBands - 1;
                    retVal = false;
                } else {
                    retVal = true;
                    if (this.currentBandIndex == this.baseSetIndex) {  // if we were climbing with bad results
                        // start moving down
                        this.currentBandIndex = (this.startBand - 1) + 1; // 1 band below the start band 
                    } else {
                        this.currentBandIndex++;
                    }
                }
            } else {
                if (this.currentBandIndex == 0) {
                    this.looser = true;
                    this.bandIndexScore = 0;
                    retVal = false;
                } else {
                    if (this.currentBandIndex == this.baseSetIndex) { // if we were climbing with bad results
                        this.currentBandIndex--;
                        this.baseSetIndex--;
                        retVal = true;
                    } else {
                        retVal = false;
                        this.bandIndexScore = this.currentBandIndex;
                    }
                }

            }
        }

        if (retVal) {
            // check if there are enough stimuli left
            this.enoughFineTuningStimulae = this.initialiseNextFineTuningTuple();
            if (!this.enoughFineTuningStimulae) {
                System.out.println(this.errorMessage);
                retVal = false;
            }
        } else {
            this.finalScore = this.computeFinalScore(this.responseRecord);
        }

        return retVal;
    }

    public int getFinalScore() {
        return this.finalScore;
    }

    @Override
    protected Boolean isEnoughCorrectResponses() {
        int wrongAnswers = 0;
        int lastIndex = this.responseRecord.size() - 1;
        for (int i = 0; i < this.fineTuningTupleLength; i++) {
            if (!this.responseRecord.get(lastIndex - i).getCorrectness()) {
                wrongAnswers++;
            }
        }
        if (this.currentBandIndex == this.baseSetIndex) {
            return wrongAnswers <= this.maxMistakesForOkBase;
        } else {
            return wrongAnswers <= this.maxMistakesForOk;
        }

    }

    private int computeFinalScore(ArrayList<BookkeepingStimulus<PeabodyStimulus>> recordi) {
        int lastIndex = recordi.size() - 1;
        BookkeepingStimulus<PeabodyStimulus> lastBStimulus = recordi.get(lastIndex);
        String audioPath = lastBStimulus.getStimulus().getAudio();
        String audioFile = audioPath.substring(this.stimuliDir.length());
        String[] bits = audioFile.split("_");
        int lastAudioIndex = Integer.parseInt(bits[0]);
        int errors = 0;
        for (BookkeepingStimulus<PeabodyStimulus> bStimulus : recordi) {
            if (!bStimulus.getCorrectness()) {
                errors++;
            }
        }
        return lastAudioIndex - errors;
    }

    @Override
    public boolean initialiseNextFineTuningTuple() {
        ArrayList<PeabodyStimulus> stimuli = this.stimuliPool.get(this.currentBandIndex);
        this.tupleFT = new ArrayList<BookkeepingStimulus<PeabodyStimulus>>();
        for (PeabodyStimulus stimulus : stimuli) {
            BookkeepingStimulus<PeabodyStimulus> bStimulus = new BookkeepingStimulus<PeabodyStimulus>(stimulus);
            this.tupleFT.add(bStimulus);
        }
        return true;
    }

    @Override
    public boolean analyseCorrectness(Stimulus stimulus, String stimulusResponse) {
        return stimulusResponse.equals(stimulus.getCorrectResponses());
    }

    @Override
    public String getStringSummary(String startRow, String endRow, String startColumn, String endColumn) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(startRow);
        stringBuilder.append(startColumn).append("Score").append(endColumn);

        stringBuilder.append(startColumn).append("Champion").append(endColumn);
        stringBuilder.append(startColumn).append("Looser").append(endColumn);

        stringBuilder.append(endRow);
        stringBuilder.append(startRow);
        stringBuilder.append(startColumn).append(this.finalScore).append(endColumn);

        stringBuilder.append(startColumn).append(this.champion).append(endColumn);
        stringBuilder.append(startColumn).append(this.looser).append(endColumn);

        stringBuilder.append(endRow);
        return stringBuilder.toString();
    }

    @Override
    public String getStringFineTuningHistory(String startRow, String endRow, String startColumn, String endColumn, String format) {
        StringBuilder empty = new StringBuilder();
        empty.append(startColumn).append(" ").append(endColumn);
        empty.append(startColumn).append(" ").append(endColumn);
        empty.append(startColumn).append(" ").append(endColumn);
        empty.append(startColumn).append(" ").append(endColumn);
        empty.append(startColumn).append(" ").append(endColumn);
        empty.append(startColumn).append(" ").append(endColumn);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(startRow);
        stringBuilder.append(startColumn).append("ImageFile").append(endColumn);
        stringBuilder.append(startColumn).append("AudioFile").append(endColumn);
        stringBuilder.append(startColumn).append("SetNumber").append(endColumn);
        stringBuilder.append(startColumn).append("UserAnswer").append(endColumn);
        stringBuilder.append(startColumn).append("IsAnswerCorrect").append(endColumn);
        stringBuilder.append(startColumn).append("Timestamp").append(endColumn);
        stringBuilder.append(startColumn).append("Visiting Number").append(endColumn);
        stringBuilder.append(endRow);
        int modCounter = 0;
        for (int i = 0; i < this.responseRecord.size(); i++) {
            BookkeepingStimulus<PeabodyStimulus> stimulus = this.responseRecord.get(i);
            StringBuilder row = new StringBuilder();
            String time = (new Date(stimulus.getTimeStamp())).toString();

            String imagePath = stimulus.getStimulus().getImage();
            String imageFile = imagePath.substring(this.stimuliDir.length());
            row.append(startColumn).append(imageFile).append(endColumn);

            String audioPath = stimulus.getStimulus().getAudio();
            String audioFile = audioPath.substring(this.stimuliDir.length());
            row.append(startColumn).append(audioFile).append(endColumn);

            row.append(startColumn).append(stimulus.getStimulus().getSetNumber()).append(endColumn);
            String reaction = stimulus.getReaction();
            row.append(startColumn).append(reaction).append(endColumn);
            row.append(startColumn).append(stimulus.getCorrectness()).append(endColumn);
            row.append(startColumn).append(time).append(endColumn);
            row.append(startColumn).append(i).append(endColumn);
            stringBuilder.append(startRow).append(row).append(endRow);
            modCounter++;
            if (modCounter == this.fineTuningTupleLength) {
                stringBuilder.append(startRow).append(empty).append(endRow); // skip between tuples
                stringBuilder.append(startRow).append(empty).append(endRow);
                modCounter = 0;
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        Map<String, Object> map = super.toMap();
        map.put("finalScore", this.finalScore);
        return map.toString();
    }

    @Override
    protected String bandIndexToLabel(int index) {
        return Indices.SET_INDEX[index];
    }

    @Override
    public String getStringFastTrack(String p1, String p2, String p3, String p4) {
        return "";
    }

    @Override
    protected void recycleUnusedStimuli() {
    }

    @Override
    protected void checkTimeOut() {
    }

    @Override
    protected void deserialiseSpecific(String str) throws Exception {
        Map<String, Object> map = UtilsJSONdialect.stringToObjectMap(str, SPECIFIC_FLDS);
        this.finalScore = Integer.parseInt(map.get("finalScore").toString());

        this.responseRecord = new ArrayList<BookkeepingStimulus<PeabodyStimulus>>();
        BookkeepingStimulus<PeabodyStimulus> factory = new BookkeepingStimulus<PeabodyStimulus>(null);
        Object recordObj = map.get("responseRecord");

        PeabodyStimuliFromString reader = new PeabodyStimuliFromString();
        reader.parseWordsInputCSVString(this, this.numberOfBands, this.stimuliDir);
        this.stimuliPool = reader.getStimuliByBands();
        
        if (recordObj != null) {
            List<Object> objs = (List<Object>) recordObj;
            for (int i = 0; i < objs.size(); i++) {
                Map<String, Object> mapBStimulus = (Map<String, Object>) objs.get(i);
                BookkeepingStimulus<PeabodyStimulus> bStimulus = factory.toBookkeepingStimulusObject(mapBStimulus, reader.getHashedStimuli());
                this.responseRecord.add(i, bStimulus);
            }
        }
    
        Object tupleFTObj = map.get("tupleFT");
        this.tupleFT = new ArrayList<BookkeepingStimulus<PeabodyStimulus>>();
        if (tupleFTObj != null) {
            List<Object> objs = (List<Object>) tupleFTObj;
            for (int i = 0; i < objs.size(); i++) {
                Map<String, Object> currentObj = (Map<String, Object>) objs.get(i);
                BookkeepingStimulus<PeabodyStimulus> bStimulus = factory.toBookkeepingStimulusObject(currentObj, reader.getHashedStimuli());
                this.tupleFT.add(i, bStimulus);
            }
        }
        
    }

    @Override
    public boolean enoughStimuliForFastTrack() {
        return true;
    }

    @Override
    public BookkeepingStimulus<PeabodyStimulus> deriveNextFastTrackStimulus() {
        return null;
    }

    @Override
    public long getPercentageScore() {
        return -1;
    }
}
