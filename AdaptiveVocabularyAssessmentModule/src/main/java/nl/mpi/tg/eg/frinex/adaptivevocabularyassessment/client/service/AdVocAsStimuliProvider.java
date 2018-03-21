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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Set;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.RandomIndexing;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.service.advocaspool.Vocabulary;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BookkeepingStimulus;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.UtilsJSONdialect;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.vocabulary.AdVocAsStimulus;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;

/**
 * @since Oct 27, 2017 2:01:33 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class AdVocAsStimuliProvider extends BandStimuliProvider<AdVocAsStimulus> {

    // experiment specific stuff here
    private RandomIndexing rndIndexing;
    private ArrayList<Integer> nonWordsIndexes;
    private int wordsPerBand;
    private int wordsPerBandInSeries;
    private int nonWordsPerBlock;
    private int averageNonWordPosition = 0;

    private ArrayList<ArrayList<AdVocAsStimulus>> words;
    private ArrayList<AdVocAsStimulus> nonwords;
    
   
    // fine tuning stuff
    private Random rnd;

    public AdVocAsStimuliProvider(AdVocAsStimulus[] stimulusArray) {
        super(stimulusArray);
    }
    
    

    @Override
    public void initialiseStimuliState(String stimuliStateSnapshot) {

        this.htmlReport = stimuliStateSnapshot;
        if (stimuliStateSnapshot.equals("")) { // no report is generated, start from scratch for now
            super.initialiseStimuliState(stimuliStateSnapshot);

            this.wordsPerBandInSeries = this.wordsPerBand / this.numberOfSeries;
            Vocabulary vocab = new Vocabulary(this.numberOfBands, this.wordsPerBandInSeries);

            vocab.initialise(this.stimuli);
            this.words = vocab.getWordsInBands();
            this.nonwords = vocab.getNonwords();
            this.hashedStimuli = vocab.getHashedStimuli();
        
            this.totalStimuli = this.nonwords.size();
            for (int i = 0; i < this.numberOfBands; i++) {
                this.totalStimuli += this.words.get(i).size();
            }

            // int startBand, int nonwordsPerBlock, int averageNonwordPosition, int nonwordsAvailable
            this.rndIndexing = new RandomIndexing(this.startBand, this.numberOfBands, this.nonWordsPerBlock, this.averageNonWordPosition, nonwords.size());
            this.nonWordsIndexes = this.rndIndexing.updateAndGetIndices();

            this.rnd = new Random();
        } else {
            // TODO !!!
        }
    }

    public void setnonwordsPerBlock(String nonWrodsPerBlock) {
        this.nonWordsPerBlock = Integer.parseInt(nonWrodsPerBlock);
    }

    public void setwordsPerBand(String wordsPerBand) {
        this.wordsPerBand = Integer.parseInt(wordsPerBand);
    }

    public void setaverageNonWordPosition(String averageNonWordPosition) {
        this.averageNonWordPosition = Integer.parseInt(averageNonWordPosition);
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

    public int getCurrentBandNumber() {
        return (this.currentBandIndex + 1);
    }

    // experiment-specific, must be overridden
    // current band index is prepared by hasNextStimulus method
    @Override
    public BookkeepingStimulus deriveNextFastTrackStimulus() {
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
        String stimulusResponseProcessed = new String(stimulusResponse);
        stimulusResponseProcessed = stimulusResponseProcessed.replaceAll(",", "&#44;");
        if (!(stimulusResponseProcessed.equals(Vocabulary.WORD) || (stimulusResponseProcessed.equals(Vocabulary.NONWORD)))) {
            System.out.println("Erroenous input: neither word nor nonword; something went terrible wrong.");
            return false;
        }
        int index = this.getCurrentStimulusIndex();
        AdVocAsStimulus adVocAsStimulus = this.responseRecord.get(index).getStimulus();
        Integer bandNumber = adVocAsStimulus.getBandNumber();
        if (bandNumber > -1) {
            retVal = stimulusResponseProcessed.equals(Vocabulary.WORD);
        } else {
            retVal = stimulusResponseProcessed.equals(Vocabulary.NONWORD);
        }
        return retVal;
    }

    @Override
    protected boolean fastTrackToBeContinuedWithSecondChance() {
        if (this.responseRecord.isEmpty()) {// just started the first experiment...
            return true;
        }
        boolean retVal;
        int index = this.responseRecord.size() - 1;
        AdVocAsStimulus adVocAsStimulus = this.responseRecord.get(index).getStimulus();
         Integer bandNumber = adVocAsStimulus.getBandNumber();
        boolean isWord = bandNumber > -1;
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
        BookkeepingStimulus bStimulus;
        for (int i = 0; i < nonWordPos; i++) {
            
            bStimulus = new BookkeepingStimulus(this.words.get(this.currentBandIndex).remove(0)); //injection constructor
            this.tupleFT.add(bStimulus);
        }
        bStimulus = new BookkeepingStimulus(this.nonwords.remove(0)); // injection constructor
        this.tupleFT.add(bStimulus);
        for (int i = nonWordPos + 1; i < this.fineTuningTupleLength; i++) {
            bStimulus = new BookkeepingStimulus(this.words.get(this.currentBandIndex).remove(0));//injection constructor
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
            int bandNumber =stimulus.getBandNumber();
            if (bandNumber > -1) { // a word
                // bandIndex is 1 less then band number 
                this.words.get(bandNumber - 1).add(stimulus);
            } else {
                this.nonwords.add(stimulus);
            }
            ended = this.tupleFT.isEmpty();
        }

    }

    @Override
    public String getStringFastTrack(String startRow, String endRow, String startColumn, String endColumn) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(startRow);
        stringBuilder.append(startColumn).append("Label").append(endColumn);
        stringBuilder.append(startColumn).append("BandNumber").append(endColumn);
        stringBuilder.append(startColumn).append("UserAnswer").append(endColumn);
        stringBuilder.append(startColumn).append("IsAnswerCorrect").append(endColumn);
        stringBuilder.append(startColumn).append("Timestamp").append(endColumn);
        stringBuilder.append(startColumn).append("NonwordsFrequencyAtThisPoint").append(endColumn);
        stringBuilder.append(endRow);
        int nonwordCounter = 0;
        for (int i = 0; i <= this.timeTickEndFastTrack; i++) {
            BookkeepingStimulus<AdVocAsStimulus> bStimulus = this.responseRecord.get(i);
            AdVocAsStimulus stimulus = bStimulus.getStimulus();
            Integer bandNumber = stimulus.getBandNumber();
            if (bandNumber < 0) {
                nonwordCounter++;
            }
            double frequency = ((double) nonwordCounter) / ((double) (i + 1));
            StringBuilder row = new StringBuilder();

            String time = (new Date(bStimulus.getTimeStamp())).toString();

            row.append(startColumn).append(stimulus.getLabel()).append(endColumn);
            row.append(startColumn).append(stimulus.getBandNumber()).append(endColumn);
            row.append(startColumn).append(bStimulus.getReaction()).append(endColumn);
            row.append(startColumn).append(bStimulus.getCorrectness()).append(endColumn);
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
        stringBuilder.append(startColumn).append("BandNumber").append(endColumn);
        stringBuilder.append(startColumn).append("Label").append(endColumn);
        stringBuilder.append(startColumn).append("UserAnswer").append(endColumn);
        stringBuilder.append(startColumn).append("IsAnswerCorrect").append(endColumn);
        stringBuilder.append(startColumn).append("Timestamp").append(endColumn);
        stringBuilder.append(startColumn).append("Visiting Number").append(endColumn);
        stringBuilder.append(endRow);
        int modCounter = 0;
        ArrayList<String> spellingsCheck = new ArrayList<>();
        for (int i = this.timeTickEndFastTrack + 1; i < this.responseRecord.size(); i++) {
            BookkeepingStimulus<AdVocAsStimulus> bStimulus = this.responseRecord.get(i);
            AdVocAsStimulus stimulus = bStimulus.getStimulus();
            StringBuilder row = new StringBuilder();
            String time = (new Date(bStimulus.getTimeStamp())).toString();
            row.append(startColumn).append(stimulus.getBandNumber()).append(endColumn);
            row.append(startColumn).append(stimulus.getLabel()).append(endColumn);
            row.append(startColumn).append(bStimulus.getReaction()).append(endColumn);
            row.append(startColumn).append(bStimulus.getCorrectness()).append(endColumn);
            row.append(startColumn).append(time).append(endColumn);
            row.append(startColumn).append(i).append(endColumn);
            stringBuilder.append(startRow).append(row).append(endRow);
            modCounter++;
            if (!bStimulus.getCorrectness() || modCounter == this.fineTuningTupleLength) {
                stringBuilder.append(startRow).append(empty).append(endRow); // skip between tuples
                stringBuilder.append(startRow).append(empty).append(endRow);
                modCounter = 0;
            }
            spellingsCheck.add(stimulus.getLabel());
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
        if (this.htmlReport.equals("")) {
            StringBuilder htmlStringBuilder = new StringBuilder();
            String experimenteeReport = this.getHtmlExperimenteeReport();
            htmlStringBuilder.append(experimenteeReport);
            this.htmlReport = htmlStringBuilder.toString();
        }

        return this.htmlReport;
    }

    private String getHtmlExperimenteeReport() {
        StringBuilder htmlStringBuilder = new StringBuilder();
        LinkedHashMap<String, ArrayList<BookkeepingStimulus<AdVocAsStimulus>>> wordTables = this.generateWordNonWordSequences(this.responseRecord);

        ArrayList<BookkeepingStimulus<AdVocAsStimulus>> woorden = wordTables.get("words");
        ArrayList<BookkeepingStimulus<AdVocAsStimulus>> nietWoorden = wordTables.get("nonwords");
        String experimenteeWordTable = this.getHtmlExperimenteeRecords(woorden, null, "Woorden");
        String experimenteeNonWordTable = this.getHtmlExperimenteeRecords(nietWoorden, null, "Nep-woorden");
        String experimenteePositionDiagram = this.getHtmlExperimenteePositionDiagram();

        htmlStringBuilder.append("<p>Overzicht van uw resultaten:</p>");
        htmlStringBuilder.append("<p><small>(Scroll om volledig resultaten te bekijken als dat nodig is.)</small></p>");
        htmlStringBuilder.append("<p>U kent ongeveer <big><big><b>").append(this.getPercentageScore()).append("</b></big></big> &#37; van alle Nederlandse woorden.</p>");

        String twoColumnTable = this.getHtmlTwoColumnTable(experimenteeWordTable, experimenteeNonWordTable);
        String capture = "Groen=Correct herkend, Rood=Niet correct herkend";
        String twoColumnTableWitCapture = this.getHtmlElementWithCapture(twoColumnTable, capture);

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
        htmlStringBuilder.append("<tr><td>PERCENTAGE</td><td></td><td>VOORBEELD woord</td></tr>");
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
        Integer bScore = this.getBandScore();

        Set<Long> keysTmp = percentageBandTable.keySet();
        Set<Long> keys = new HashSet<Long>(keysTmp);

        Long one = new Long(1);
        Long nn = new Long(99);
        keys.remove(one);
        keys.remove(nn);

        if (perScore < 5) {
            retVal.put(perScore, sampleWords.get(bScore));
        } else {
            Integer bandNumber = percentageBandTable.get(one);
            String value = sampleWords.get(bandNumber);
            retVal.put(one, value);
        }

        for (Long key : keys) {
            if (perScore >= key - 5 && perScore < key + 5) {
                // put the participant score here
                retVal.put(perScore, sampleWords.get(bScore));
            } else {
                // put a predefined sample
                Integer bandNumber = percentageBandTable.get(key);
                String value = sampleWords.get(bandNumber);
                retVal.put(key, value);
            }
        }

        if (perScore >= 95) {
            retVal.put(perScore, sampleWords.get(bScore));
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
            if (stimulus.getCorrectResponses().equals(Vocabulary.WORD)) { // is a word
                Integer key =stimulus.getBandNumber();
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
            AdVocAsStimulus stimulus = bStimulus.getStimulus();
            if (Vocabulary.WORD.equals(stimulus.getCorrectResponses())) {
                retVal.get("words").add(bStimulus);
            }
            if (Vocabulary.NONWORD.equals(stimulus.getCorrectResponses())) {
                retVal.get("nonwords").add(bStimulus);
            }
        }
        return retVal;
    }

    private ArrayList<BookkeepingStimulus<AdVocAsStimulus>> getCorrectAnsweredStimuli(ArrayList<BookkeepingStimulus<AdVocAsStimulus>> records) {
        ArrayList<BookkeepingStimulus<AdVocAsStimulus>> retVal = new ArrayList<BookkeepingStimulus<AdVocAsStimulus>>();
        for (BookkeepingStimulus<AdVocAsStimulus> bStimulus : records) {
            if (bStimulus.getCorrectness()) {
                retVal.add(bStimulus);
            }

        }
        return retVal;
    }

    private ArrayList<BookkeepingStimulus<AdVocAsStimulus>> getWronglyAnsweredStimuli(ArrayList<BookkeepingStimulus<AdVocAsStimulus>> records) {
        ArrayList<BookkeepingStimulus<AdVocAsStimulus>> retVal = new ArrayList<BookkeepingStimulus<AdVocAsStimulus>>();
        for (BookkeepingStimulus<AdVocAsStimulus> bStimulus : records) {
            if (!(bStimulus.getCorrectness())) {
                retVal.add(bStimulus);
            }

        }
        return retVal;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
//    private RandomIndexing rndIndexing;
//    private ArrayList<Integer> nonWordsIndexes;
//    private int wordsPerBand;
//    private int wordsPerBandInSeries;
//    private int nonWordsPerBlock;
//    private int averageNonWordPosition = 0;
//
//    private ArrayList<ArrayList<AdVocAsStimulus>> words;
//    private ArrayList<AdVocAsStimulus> nonwords;
//
//    // fine tuning stuff
//    private Random rnd;

        builder.append("wordsPerBand:{").append(this.wordsPerBand).append("},");
        builder.append("wordsPerBandInSeries:{").append(this.wordsPerBandInSeries).append("},");
        builder.append("nonWordsPerBlock:{").append(this.nonWordsPerBlock).append("},");
        builder.append("averageNonWordPosition:{").append(this.averageNonWordPosition).append("}");
        
        String rndIndexingStr = this.rndIndexing.toString();
        if (rndIndexingStr != null) {
            builder.append(",rndIndexing:").append(rndIndexingStr);
        }
        
        UtilsJSONdialect<Integer> util = new UtilsJSONdialect<Integer>();
        try {
            String nonWordsIndexesStr = util.arrayListToString(this.nonWordsIndexes);
            if (nonWordsIndexesStr != null) {
                builder.append(",nonWordsIndexes:").append(nonWordsIndexesStr);
            }
        } catch (Exception ex) {
        }
        
        UtilsJSONdialect<AdVocAsStimulus> util1 = new UtilsJSONdialect<AdVocAsStimulus>();
        try {
            String wordsStr = util1.arrayList2String(this.words);
            if (wordsStr != null) {
                builder.append(",words:").append(wordsStr);
            }
        } catch (Exception ex) {
        }
        try {
            String nonwordsStr = util1.arrayListToString(this.nonwords);
            if (nonwordsStr != null) {
                builder.append(",nonwords:").append(nonwordsStr);
            }
        } catch (Exception ex) {
        }
        
        builder.append("}");
        return builder.toString();
    }

}
