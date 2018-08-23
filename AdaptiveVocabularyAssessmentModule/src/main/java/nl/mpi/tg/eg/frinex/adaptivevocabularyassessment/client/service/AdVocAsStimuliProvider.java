package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service;

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
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BandStimuliProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.RandomIndexing;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.advocaspool.Vocabulary;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BookkeepingStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.UtilsJSONdialect;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.vocabulary.AdVocAsStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.advocaspool.AdVocAsStimuliFromString;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.advocaspool.SourcenameIndices;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;

/**
 * @since Oct 27, 2017 2:01:33 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class AdVocAsStimuliProvider extends BandStimuliProvider<AdVocAsStimulus> {

    private final static String[] SPECIFIC_FLDS = {"rndIndexing", "nonWordsIndexes", "wordsPerBand", "nonWordsPerBlock",
        "averageNonWordPosition", "responseRecord", "tupleFT", "words", "nonwords", "wordsSource", "nonwordsSource"};
    private RandomIndexing rndIndexing;
    private ArrayList<Integer> nonWordsIndexes;
    private int wordsPerBand;
    private int nonWordsPerBlock;
    private int averageNonWordPosition = 0;
    private String wordsSource;
    private String nonwordsSource;
    private String wordsResponse;
    private String nonwordsResponse;

    private ArrayList<ArrayList<AdVocAsStimulus>> words;
    private ArrayList<AdVocAsStimulus> nonwords;

    private LinkedHashMap<Long, Integer> percentageBandTable;
    private Random rnd;

    public AdVocAsStimuliProvider(Stimulus[] stimulusArray) {
        super(stimulusArray);
    }

    @Override
    public void initialiseStimuliState(String stimuliStateSnapshot) {

        try {
            super.initialiseStimuliState(stimuliStateSnapshot);

            // specific part
            this.percentageBandTable = this.generatePercentageBandTable();

            if (stimuliStateSnapshot.trim().isEmpty()) {

                this.currentBandIndex = this.startBand - 1;

                this.wordsResponse = SourcenameIndices.RESPONSES_INDEX.get(this.wordsSource);
                this.nonwordsResponse = SourcenameIndices.RESPONSES_INDEX.get(this.nonwordsSource);

                AdVocAsStimuliFromString reader = new AdVocAsStimuliFromString();
                reader.parseWordsInputCSVString(this, this.wordsSource, this.nonwordsSource, this.numberOfBands);
                reader.parseNonWordsInputCSVString(this, this.nonwordsSource, this.wordsSource);
                ArrayList<ArrayList<AdVocAsStimulus>> rawWords = reader.getWords();
                ArrayList<AdVocAsStimulus> rawNonwords = reader.getNonwords();

                Vocabulary vocab = new Vocabulary(this.numberOfBands, this.wordsPerBand);
                this.words = vocab.initialiseWords(rawWords);
                this.nonwords = vocab.initialiseNonwords(rawNonwords);

                this.totalStimuli = this.nonwords.size();
                for (int i = 0; i < this.numberOfBands; i++) {
                    this.totalStimuli += this.words.get(i).size();
                }

                // int startBand, int nonwordsPerBlock, int averageNonwordPosition, int nonwordsAvailable
                this.rndIndexing = new RandomIndexing(this.startBand, this.numberOfBands, this.nonWordsPerBlock, this.averageNonWordPosition, nonwords.size());
                this.nonWordsIndexes = this.rndIndexing.updateAndGetIndices();

                this.rnd = new Random();
            } else {
                this.deserialiseSpecific(stimuliStateSnapshot);
                this.rnd = new Random();
            }
        } catch (Exception ex) {
            this.exceptionLogging(ex);
        }

    }

    public void setwordsSource(String wordsSource) {
        this.wordsSource = wordsSource;
    }

    public void setnonwordsSource(String nonwordsSource) {
        this.nonwordsSource = nonwordsSource;
    }

    public void setnonwordsPerBlock(String nonWrodsPerBlock) {
        this.nonWordsPerBlock = Integer.parseInt(nonWrodsPerBlock);
    }

    public int getNonWrodPerBlock() {
        return this.nonWordsPerBlock;
    }

    public void setwordsPerBand(String wordsPerBand) {
        this.wordsPerBand = Integer.parseInt(wordsPerBand);
    }

    public int getWordsPerBand() {
        return this.wordsPerBand;
    }

    public void setaverageNonWordPosition(String averageNonWordPosition) {
        this.averageNonWordPosition = Integer.parseInt(averageNonWordPosition);
    }

    public int getAverageNonWordPosition() {
        return this.averageNonWordPosition;
    }

    public ArrayList<ArrayList<AdVocAsStimulus>> getWords() {
        return this.words;
    }

    public ArrayList<AdVocAsStimulus> getNonwords() {
        return this.nonwords;
    }

    public ArrayList<Integer> getNonWordsIndices() {
        return this.nonWordsIndexes;
    }

    public LinkedHashMap<Long, Integer> getPercentageBandTable() {
        return this.percentageBandTable;
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

    public long bandNumberIntoPercentage(int bandNumber) {
        double tmp = ((double) bandNumber * 100.0) / this.numberOfBands;
        long retVal = Math.round(tmp);
        return retVal;
    }

    public int percentageIntoBandNumber(long percentage) {
        float tmp = ((float) percentage * this.numberOfBands) / 100;
        int retVal = Math.round(tmp);
        return retVal;
    }

    // experiment-specific, must be overridden
    // current band index is prepared by hasNextStimulus method
    @Override
    public BookkeepingStimulus<AdVocAsStimulus> deriveNextFastTrackStimulus() {
        int experimentNumber = this.responseRecord.size();
        AdVocAsStimulus stimulus;
        if (this.nonWordsIndexes.contains(experimentNumber)) {
            stimulus = this.nonwords.remove(0);
        } else {
            stimulus = this.words.get(this.currentBandIndex).remove(0);
        }

        BookkeepingStimulus<AdVocAsStimulus> retVal = new BookkeepingStimulus<AdVocAsStimulus>(stimulus);
        return retVal;
    }

    @Override
    protected boolean analyseCorrectness(Stimulus stimulus, String stimulusResponse) {
        boolean retVal;
        String stimulusResponseProcessed = (new StringBuilder()).append(stimulusResponse).toString();
        stimulusResponseProcessed = stimulusResponseProcessed.replaceAll(",", "&#44;");
        if (!(stimulusResponseProcessed.equals(this.wordsResponse)
                || (stimulusResponseProcessed.equals(this.nonwordsResponse)))) {
            System.out.println("Erroenous input: neither word nor nonword; something went terrible wrong.");
            return false;
        }
        String correctResponse = stimulus.getCorrectResponses();
        retVal = stimulusResponseProcessed.equals(correctResponse);
        return retVal;
    }

    @Override
    protected boolean fastTrackToBeContinuedWithSecondChance() {
        if (this.responseRecord.isEmpty()) {// just started the first experiment...
            return true;
        }
        boolean retVal;
        int index = this.responseRecord.size() - 1;
        boolean isWord = this.responseRecord.get(index).getStimulus().getCorrectResponses().equals(this.wordsResponse);
        if (this.isCorrectCurrentResponse) {
            this.secondChanceFastTrackIsFired = false;
            if (isWord) {
                if (this.currentBandIndex == (this.numberOfBands - 1)) {
                    retVal = false;
                } else {
                    this.currentBandIndex++;
                    retVal = true;
                }
            } else {
                retVal = true;
            }
        } else {
            // hit incorrect? 
            if (this.secondChanceFastTrackIsFired) {
                retVal = false;
                if (this.currentBandIndex >0 ) {
                    this.currentBandIndex--;
                }
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

    @Override
    protected boolean enoughStimuliForFastTrack() {
        boolean retVal = true;
        // check if we still have data for the next experiment
        int nextExperimentIndex = this.responseRecord.size();

        if (this.nonWordsIndexes.contains(nextExperimentIndex) && nonwords.size() < 1) {
            System.out.println("We do not have nonwords left for the Fast Track!");
            retVal = false;
        }

        if (!this.nonWordsIndexes.contains(nextExperimentIndex)
                && this.words.get(this.currentBandIndex).size() < 1) {
            System.out.println("We do not have words in band " + this.currentBandIndex + "  left for the Fast Track!");
            retVal = false;
        }
        return retVal;
    }

    // we already at the right band, the last band in the fast track with the correct answer
    // return false if there is not enough words and nonwords
    @Override
    public boolean initialiseNextFineTuningTuple() {
        if (this.nonwords.size() < 1) {
            this.errorMessage = "There is no non-words left.";
            return false;
        }
        if (this.words.get(this.currentBandIndex).size() < this.fineTuningTupleLength - 1) {
            this.errorMessage = "There is not enough stimuli for the band  with index " + this.currentBandIndex;
            return false;
        }
        int nonWordPos = rnd.nextInt(this.fineTuningTupleLength);
        BookkeepingStimulus<AdVocAsStimulus> bStimulus;
        for (int i = 0; i < nonWordPos; i++) {
            bStimulus = new BookkeepingStimulus<AdVocAsStimulus>(this.words.get(this.currentBandIndex).remove(0)); //injection constructor
            this.tupleFT.add(bStimulus);
        }
        bStimulus = new BookkeepingStimulus<AdVocAsStimulus>(this.nonwords.remove(0)); // injection constructor
        this.tupleFT.add(bStimulus);
        for (int i = nonWordPos + 1; i < this.fineTuningTupleLength; i++) {
            bStimulus = new BookkeepingStimulus<AdVocAsStimulus>(this.words.get(this.currentBandIndex).remove(0));//injection constructor
            this.tupleFT.add(bStimulus);
        }
        return true;
    }

    @Override
    protected void recycleUnusedStimuli() {
        boolean ended = this.tupleFT.isEmpty();
        while (!ended) {
            BookkeepingStimulus<AdVocAsStimulus> bStimulus = this.tupleFT.remove(0);
            //(String uniqueId, String label, String correctResponses, int bandNumber)
            AdVocAsStimulus stimulus = bStimulus.getStimulus();
            int bandNumber = stimulus.getBandNumber();
            if (bandNumber > 0) { // a word
                // bandIndex is 1 less then band number 
                this.words.get(bandNumber - 1).add(stimulus);
            } else {
                this.nonwords.add(stimulus);
            }
            ended = this.tupleFT.isEmpty();
        }

    }

    @Override
    protected void checkTimeOut() {}

    @Override
    public String getStringFastTrack(String startRow, String endRow, String startColumn, String endColumn) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(startRow);
        stringBuilder.append(startColumn).append("Spelling").append(endColumn);
        stringBuilder.append(startColumn).append("BandNumber").append(endColumn);
        stringBuilder.append(startColumn).append("UserAnswer").append(endColumn);
        stringBuilder.append(startColumn).append("IsAnswerCorrect").append(endColumn);
        stringBuilder.append(startColumn).append("Timestamp").append(endColumn);
        stringBuilder.append(startColumn).append("NonwordsFrequencyAtThisPoint").append(endColumn);
        stringBuilder.append(endRow);
        int nonwordCounter = 0;
        int limit = (this.responseRecord.isEmpty()) ? -1 : this.timeTickEndFastTrack;
        for (int i = 0; i <= limit; i++) {
            BookkeepingStimulus<AdVocAsStimulus> stimulus = this.responseRecord.get(i);
            Integer bandNumber = stimulus.getStimulus().getBandNumber();
            if (bandNumber == 0) {
                nonwordCounter++;
            }
            double frequency = ((double) nonwordCounter) / ((double) (i + 1));
            StringBuilder row = new StringBuilder();

            String time = (new Date(stimulus.getTimeStamp())).toString();
            row.append(startColumn).append(stimulus.getStimulus().getLabel()).append(endColumn);
            row.append(startColumn).append(stimulus.getStimulus().getBandNumber()).append(endColumn);
            String reaction = startRow.equals("<tr>") ? stimulus.getReaction() : stimulus.getReaction().replaceAll("&#44;", ",");

            row.append(startColumn).append(reaction).append(endColumn);
            row.append(startColumn).append(stimulus.getCorrectness()).append(endColumn);
            row.append(startColumn).append(time).append(endColumn);
            row.append(startColumn).append(frequency).append(endColumn);
            stringBuilder.append(startRow).append(row).append(endRow);
        }
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
        stringBuilder.append(startColumn).append("Spelling").append(endColumn);
        stringBuilder.append(startColumn).append("BandNumber").append(endColumn);
        stringBuilder.append(startColumn).append("UserAnswer").append(endColumn);
        stringBuilder.append(startColumn).append("IsAnswerCorrect").append(endColumn);
        stringBuilder.append(startColumn).append("Timestamp").append(endColumn);
        stringBuilder.append(startColumn).append("Visiting Number").append(endColumn);
        stringBuilder.append(endRow);
        int modCounter = 0;
        ArrayList<String> spellingsCheck = new ArrayList<>();
        for (int i = this.timeTickEndFastTrack + 1; i < this.responseRecord.size(); i++) {
            BookkeepingStimulus<AdVocAsStimulus> stimulus = this.responseRecord.get(i);
            StringBuilder row = new StringBuilder();
            String time = (new Date(stimulus.getTimeStamp())).toString();

            row.append(startColumn).append(stimulus.getStimulus().getLabel()).append(endColumn);
            row.append(startColumn).append(stimulus.getStimulus().getBandNumber()).append(endColumn);
            String reaction = startRow.equals("<tr>") ? stimulus.getReaction() : stimulus.getReaction().replaceAll("&#44;", ",");
            row.append(startColumn).append(reaction).append(endColumn);
            row.append(startColumn).append(stimulus.getCorrectness()).append(endColumn);
            row.append(startColumn).append(time).append(endColumn);
            row.append(startColumn).append(i).append(endColumn);
            stringBuilder.append(startRow).append(row).append(endRow);
            modCounter++;
            if (!stimulus.getCorrectness() || modCounter == this.fineTuningTupleLength) {
                stringBuilder.append(startRow).append(empty).append(endRow); // skip between tuples
                stringBuilder.append(startRow).append(empty).append(endRow);
                modCounter = 0;
            }
            spellingsCheck.add(stimulus.getStimulus().getLabel());
        }

        // check if there are repititions
        HashSet<String> set = new HashSet(spellingsCheck);
        if (set.size() < spellingsCheck.size()) {
            stringBuilder.append(startRow).append(startColumn)
                    .append("Repetitions of stimuli detected")
                    .append(endColumn).append(endRow);
        }
        return stringBuilder.toString();
    }

    @Override
    public String getHtmlStimuliReport() {
        StringBuilder htmlStringBuilder = new StringBuilder();
        String experimenteeReport = this.getHtmlExperimenteeReport();
        htmlStringBuilder.append(experimenteeReport);
        return htmlStringBuilder.toString();
    }

    @Override
    protected String bandIndexToLabel(int index) {
        int bandNumber = index + 1;
        return String.valueOf(bandNumber);
    }

    @Override
    public long getPercentageScore() {
        int bandNumber = this.bandIndexScore + 1;
        long percentageScore = this.bandNumberIntoPercentage(bandNumber);
        return percentageScore;
    }

    private String getHtmlExperimenteeReport() {
        StringBuilder htmlStringBuilder = new StringBuilder();
        LinkedHashMap<String, ArrayList<BookkeepingStimulus<AdVocAsStimulus>>> wordTables = this.generateWordNonWordSequences(this.responseRecord);

        ArrayList<BookkeepingStimulus<AdVocAsStimulus>> woorden = wordTables.get("words");
        ArrayList<BookkeepingStimulus<AdVocAsStimulus>> nietWoorden = wordTables.get("nonwords");
        String experimenteePositionDiagram = this.getHtmlExperimenteePositionDiagram();

        String lang = SourcenameIndices.LANGUAGE_INDEX.get(this.wordsSource);

        String overview = SourcenameIndices.getOverview(this.getPercentageScore(), lang);
        htmlStringBuilder.append(overview);

        HashMap<String, String> wordListHeaders = SourcenameIndices.getWordListHeaders(lang);

        String experimenteeWordTable = this.getHtmlExperimenteeRecords(woorden, null, wordListHeaders.get("headerWords"));
        String experimenteeNonWordTable = this.getHtmlExperimenteeRecords(nietWoorden, null, wordListHeaders.get("headerNonWords"));
        String twoColumnTable = this.getHtmlTwoColumnTable(experimenteeWordTable, experimenteeNonWordTable);
        String twoColumnTableWitCapture = this.getHtmlElementWithCapture(twoColumnTable, wordListHeaders.get("capture"));

        htmlStringBuilder.append("<table><tr style=\"vertical-align: top;\"><td>");
        htmlStringBuilder.append(twoColumnTableWitCapture).append("</td>");
        htmlStringBuilder.append("<td style=\"padding-left: 200px;\">").append(experimenteePositionDiagram).append("</td></tr></table>");

        return htmlStringBuilder.toString();
    }

    private String getHtmlExperimenteeRecords(ArrayList<BookkeepingStimulus<AdVocAsStimulus>> atoms, String colour, String header) {
        StringBuilder htmlStringBuilder = new StringBuilder();
        htmlStringBuilder.append("<table>");
        htmlStringBuilder.append("<tr><td><big>").append(header).append("</big></td></tr>");
        String colorStyle = "";
        if (colour != null) {
            colorStyle = "style=\"color:" + colour + "\"";
        }
        for (BookkeepingStimulus<AdVocAsStimulus> atom : atoms) {
            if (colour == null) {
                if (atom.getCorrectness()) {
                    colorStyle = "style=\"color:green\"";
                }
                if (!atom.getCorrectness()) {
                    colorStyle = "style=\"color:red\"";
                }
            }
            htmlStringBuilder.append("<tr ").append(colorStyle).append(">");
            htmlStringBuilder.append("<td><big>").append(atom.getStimulus().getLabel()).append("</big></td>");
            htmlStringBuilder.append("</tr>");
        }
        htmlStringBuilder.append("</table>");

        return htmlStringBuilder.toString();
    }

    private String getHtmlTwoColumnTable(String leftColumn, String rightColumn) {
        StringBuilder htmlStringBuilder = new StringBuilder();
        htmlStringBuilder.append("<table>");
        htmlStringBuilder.append("<tr style=\"vertical-align: top;\"><td>").append(leftColumn).append("</td>");
        htmlStringBuilder.append("<td style=\"padding-left: 5px;\">").append(rightColumn).append("</td></tr></table>");
        return htmlStringBuilder.toString();
    }

    private String getHtmlElementWithCapture(String element, String capture) {
        StringBuilder htmlStringBuilder = new StringBuilder();
        htmlStringBuilder.append("<table>");
        htmlStringBuilder.append("<tr><td>").append(capture).append("</td></tr>");
        htmlStringBuilder.append("<tr><td>").append(element).append("</td></tr></table>");
        return htmlStringBuilder.toString();
    }

    private String getHtmlExperimenteePositionDiagram() {
        StringBuilder htmlStringBuilder = new StringBuilder();
        long perScore = this.getPercentageScore();
        LinkedHashMap<Long, String> content = this.generateDiagramSequence(this.responseRecord, this.percentageBandTable);
        //htmlStringBuilder.append("<table frame=\"box\">");
        htmlStringBuilder.append("<table>");
        String lang = SourcenameIndices.LANGUAGE_INDEX.get(this.wordsSource);
        String header = SourcenameIndices.diagramHelper(lang);
        htmlStringBuilder.append(header);
        for (Long key : content.keySet()) {
            htmlStringBuilder.append("<tr>");
            String percent = key.toString();
            String contentString = content.get(key);
            if (key.equals(perScore)) {
                percent = "<b><big><big><big>" + percent + "</big></big></big></b>";
                contentString = "<b><big><big>" + contentString + "</big></big></b>";
            }
            String bar = this.makeDiagramBar(key);
            htmlStringBuilder.append("<td>").append(percent).append("</td>");
            htmlStringBuilder.append("<td>").append(bar).append("</td>");
            htmlStringBuilder.append("<td>").append(contentString).append("</td>");
            htmlStringBuilder.append("</tr>");
        }
        htmlStringBuilder.append("</table>");

        return htmlStringBuilder.toString();
    }

    private String makeDiagramBar(Long percentage) {
        long width = percentage;
        StringBuilder htmlStringBuilder = new StringBuilder();
        htmlStringBuilder.append("<table cellspacing=\"0\" cellpadding=\"0\" class=\"bargraphOuter\" style=\"width: 100px; height: 10px;\">")
                .append("<tbody><tr><td align=\"left\" style=\"vertical-align: top;\">")
                .append("<table cellspacing=\"0\" cellpadding=\"0\" class=\"bargraphInner\" style=\"width: ")
                .append(width)
                .append("px; height: 10px;\">")
                .append("<tbody><tr></tr></tbody></table></td></tr></tbody></table>");
        return htmlStringBuilder.toString();
    }

    public LinkedHashMap<Long, String> generateDiagramSequence(ArrayList<BookkeepingStimulus<AdVocAsStimulus>> records, LinkedHashMap<Long, Integer> percentageBandTable) {
        LinkedHashMap<Long, String> retVal = new LinkedHashMap<Long, String>();

        LinkedHashMap<Integer, String> sampleWords = this.retrieveSampleWords(records, this.words);

        Long perScore = this.getPercentageScore();
        Integer bNumberScore = this.getBandIndexScore() + 1;

        Set<Long> keysTmp = percentageBandTable.keySet();
        Set<Long> keys = new HashSet<Long>(keysTmp);

        Long one = new Long(1);
        Long nn = new Long(99);
        keys.remove(one);
        keys.remove(nn);

        if (perScore < 5) {
            retVal.put(perScore, sampleWords.get(bNumberScore));
        } else {
            Integer bandNumber = percentageBandTable.get(one);
            String value = sampleWords.get(bandNumber);
            retVal.put(one, value);
        }

        for (Long key : keys) {
            if (perScore >= key - 5 && perScore < key + 5) {
                // put the participant score here
                retVal.put(perScore, sampleWords.get(bNumberScore));
            } else {
                // put a predefined sample
                Integer bandNumber = percentageBandTable.get(key);
                String value = sampleWords.get(bandNumber);
                retVal.put(key, value);
            }
        }

        if (perScore >= 95) {
            retVal.put(perScore, sampleWords.get(bNumberScore));
        } else {
            Integer bandNumber = percentageBandTable.get(nn);
            String value = sampleWords.get(bandNumber);
            retVal.put(nn, value);
        }

        return retVal;
    }

    public LinkedHashMap<Integer, String> retrieveSampleWords(ArrayList<BookkeepingStimulus<AdVocAsStimulus>> records, ArrayList<ArrayList<AdVocAsStimulus>> nonusedWords) {
        LinkedHashMap<Integer, String> retVal = new LinkedHashMap<Integer, String>();
        for (BookkeepingStimulus<AdVocAsStimulus> bStimulus : records) {
            AdVocAsStimulus stimulus = bStimulus.getStimulus();
            if (stimulus.getCorrectResponses().equals(this.wordsResponse)) { // is a word
                Integer key = stimulus.getBandNumber();
                if (!retVal.containsKey(key)) {
                    retVal.put(key, stimulus.getLabel());
                }
            }
        }

        // fill in absent values
        for (int i = 1; i <= this.numberOfBands; i++) {
            Integer bandNumber = i;
            if (!retVal.containsKey(bandNumber)) {
                // unseen word in the band means that there must be for sure
                // non-used words for this band in the words-container
                // if there will be array out of boud exception
                String value = nonusedWords.get(i - 1).get(0).getLabel();
                retVal.put(bandNumber, value);
            }
        }

        return retVal;
    }

    private LinkedHashMap<String, ArrayList<BookkeepingStimulus<AdVocAsStimulus>>> generateWordNonWordSequences(ArrayList<BookkeepingStimulus<AdVocAsStimulus>> records) {
        LinkedHashMap<String, ArrayList<BookkeepingStimulus<AdVocAsStimulus>>> retVal = new LinkedHashMap<String, ArrayList<BookkeepingStimulus<AdVocAsStimulus>>>();
        retVal.put("words", new ArrayList<BookkeepingStimulus<AdVocAsStimulus>>());
        retVal.put("nonwords", new ArrayList<BookkeepingStimulus<AdVocAsStimulus>>());

        for (BookkeepingStimulus<AdVocAsStimulus> bStimulus : records) {
            if (this.wordsResponse.equals(bStimulus.getStimulus().getCorrectResponses())) {
                retVal.get("words").add(bStimulus);
            }
            if (this.nonwordsResponse.equals(bStimulus.getStimulus().getCorrectResponses())) {
                retVal.get("nonwords").add(bStimulus);
            }
        }
        return retVal;
    }

    @Override
    public String toString() {
        Map<String, Object> map = super.toMap();
        map.put("wordsPerBand", this.wordsPerBand);
        map.put("nonWordsPerBlock", this.nonWordsPerBlock);
        map.put("averageNonWordPosition", this.averageNonWordPosition);
        map.put("rndIndexing", this.rndIndexing);
        map.put("nonWordsIndexes", this.nonWordsIndexes);
        map.put("words", this.words);
        map.put("nonwords", this.nonwords);
        map.put("wordsSource", this.wordsSource);
        map.put("nonwordsSource", this.nonwordsSource);
        return map.toString();
    }

    @Override
    protected void deserialiseSpecific(String str) throws Exception {
        Map<String, Object> map = UtilsJSONdialect.stringToObjectMap(str, SPECIFIC_FLDS);

        Map<String, Object> rndMap = (Map<String, Object>) map.get("rndIndexing");
        this.rndIndexing = RandomIndexing.mapToObject(rndMap);

        List<Object> nonWordsIndexesObj = (List<Object>) map.get("nonWordsIndexes");
        this.nonWordsIndexes = UtilsJSONdialect.objectToListInteger(nonWordsIndexesObj);

        this.wordsPerBand = Integer.parseInt(map.get("wordsPerBand").toString());
        this.nonWordsPerBlock = Integer.parseInt(map.get("nonWordsPerBlock").toString());
        this.averageNonWordPosition = Integer.parseInt(map.get("averageNonWordPosition").toString());

        // reinitialise vocabulary
        this.wordsSource = map.get("wordsSource").toString();
        this.nonwordsSource = map.get("nonwordsSource").toString();
        this.wordsResponse = SourcenameIndices.RESPONSES_INDEX.get(this.wordsSource);
        this.nonwordsResponse = SourcenameIndices.RESPONSES_INDEX.get(this.nonwordsSource);

        AdVocAsStimuliFromString reader = new AdVocAsStimuliFromString();
        reader.parseWordsInputCSVString(this, this.wordsSource, this.nonwordsSource, this.numberOfBands);
        reader.parseNonWordsInputCSVString(this, this.nonwordsSource, this.wordsSource);

        LinkedHashMap<String, AdVocAsStimulus> hashedStimuli = reader.getHashedStimuli();

        BookkeepingStimulus<AdVocAsStimulus> ghost = new BookkeepingStimulus<AdVocAsStimulus>(null);
        Object recordObj = map.get("responseRecord");
        this.responseRecord = new ArrayList<BookkeepingStimulus<AdVocAsStimulus>>();
        if (recordObj != null) {
            List<Object> objs = (List<Object>) recordObj;
            for (int i = 0; i < objs.size(); i++) {
                if (objs.get(i) != null) {
                    Map<String, Object> currentObj = (Map<String, Object>) objs.get(i);
                    BookkeepingStimulus<AdVocAsStimulus> bStimulus = ghost.toBookkeepingStimulusObject(currentObj, hashedStimuli);
                    this.responseRecord.add(i, bStimulus);
                } else {
                    throw new Exception("Serialised bookeeping stimuli object happen to be null :( ");
                }
            }
        }

        Object tupleFTObj = map.get("tupleFT");
        this.tupleFT = new ArrayList<BookkeepingStimulus<AdVocAsStimulus>>();
        if (tupleFTObj != null) {
            List<Object> objs = (List<Object>) tupleFTObj;
            for (int i = 0; i < objs.size(); i++) {
                Map<String, Object> currentObj = (Map<String, Object>) objs.get(i);
                BookkeepingStimulus<AdVocAsStimulus> bStimulus = ghost.toBookkeepingStimulusObject(currentObj, hashedStimuli);
                this.tupleFT.add(i, bStimulus);
            }
        }

        List<Object> nonWordsObj = (List<Object>) map.get("nonwords");
        this.nonwords = new ArrayList<AdVocAsStimulus>(nonWordsObj.size());
        for (Object object : nonWordsObj) {
            AdVocAsStimulus current = hashedStimuli.get(object.toString());
            this.nonwords.add(current);
        }

        List<List<Object>> wordsObj = (List<List<Object>>) map.get("words");
        this.words = new ArrayList<ArrayList<AdVocAsStimulus>>(wordsObj.size());
        for (List<Object> listObj : wordsObj) {
            if (listObj == null) {
                this.words.add(null);
                continue;
            }
            ArrayList<AdVocAsStimulus> currentarray = new ArrayList<AdVocAsStimulus>(listObj.size());
            this.words.add(currentarray);
            for (Object object : listObj) {
                AdVocAsStimulus current = hashedStimuli.get(object.toString());
                currentarray.add(current);
            }
        }

    }

}
