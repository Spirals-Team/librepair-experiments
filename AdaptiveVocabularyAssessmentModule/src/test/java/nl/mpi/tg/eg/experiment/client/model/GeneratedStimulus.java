package nl.mpi.tg.eg.experiment.client.model;

import java.util.Arrays;
import java.util.List;
import nl.mpi.tg.eg.experiment.client.util.GeneratedStimulusProvider;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;

public class GeneratedStimulus extends nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio.AudioAsStimulus {

    public enum Tag implements nl.mpi.tg.eg.frinex.common.model.Stimulus.Tag {

        tag_round1
    }

    private final String wordType;
    private final String positionInTrial;
    private final String trialNumber;
    private final String trialWord;
    private final String trialTargetNonword;
    private final String trialSyllables;
    private final String trialCondition;
    private final String trialLength;
    private final String trialPositionTarget;
    private final String bandLabel;
    private final String bandIndex;
    private final String trialPositionFoil;

    public static final void fillStimulusList(List<Stimulus> stimulusArray) {
        stimulusArray.addAll(Arrays.asList(GeneratedStimulusProvider.values));
    }

    public GeneratedStimulus(String uniqueId, Tag[] tags, String label, String code, int pauseMs, String audioPath, String videoPath, String imagePath, String ratingLabels, String correctResponses, String... parameters) {
        super(uniqueId, tags, label, code, pauseMs, audioPath, videoPath, imagePath, ratingLabels, correctResponses);
        wordType = parameters[1 - 1];
        positionInTrial = parameters[2 - 1];
        trialNumber = parameters[3 - 1];
        trialWord = parameters[4 - 1];
        trialTargetNonword = parameters[5 - 1];
        trialSyllables = parameters[6 - 1];
        trialCondition = parameters[7 - 1];
        trialLength = parameters[8 - 1];
        trialPositionTarget = parameters[9 - 1];
        bandLabel = parameters[10 - 1];
        bandIndex = parameters[11 - 1];
        trialPositionFoil = parameters[12 - 1];
    }

    /*public GeneratedStimulus(String uniqueId, Tag[] tags, String label, String code, int pauseMs, String ratingLabels, String correctResponses) {
            super(uniqueId, tags, label, code, pauseMs, ratingLabels, correctResponses);
            }*/
    @Override
    public String getAudio() {
        return "./static/" + super.getAudio();
    }

    @Override
    public String getImage() {
        return "./static/" + super.getImage();
    }

    @Override
    public String getVideo() {
        return "./static/" + super.getVideo();
    }

    @Override
    public String getwordType() {
        return wordType;
    }

    @Override
    public String getpositionInTrial() {
        return positionInTrial;
    }

    @Override
    public String gettrialNumber() {
        return trialNumber;
    }

    @Override
    public String gettrialWord() {
        return trialWord;
    }

    @Override
    public String gettrialTargetNonword() {
        return trialTargetNonword;
    }

    @Override
    public String gettrialSyllables() {
        return trialSyllables;
    }

    @Override
    public String gettrialCondition() {
        return trialCondition;
    }

    @Override
    public String gettrialLength() {
        return trialLength;
    }

    @Override
    public String gettrialPositionTarget() {
        return trialPositionTarget;
    }

    @Override
    public String getbandLabel() {
        return bandLabel;
    }

    @Override
    public String getbandIndex() {
        return bandIndex;
    }

    @Override
    public String gettrialPositionFoil() {
        return trialPositionFoil;
    }

}
