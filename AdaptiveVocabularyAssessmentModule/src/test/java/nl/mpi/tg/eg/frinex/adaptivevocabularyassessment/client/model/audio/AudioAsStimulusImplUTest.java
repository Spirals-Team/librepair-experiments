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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.model.audio;

/**
 *
 * @author olhshk
 */
public class AudioAsStimulusImplUTest extends AudioAsStimulus {

    private final String wordType;
    private final String positionInTrial;
    private final String bandIndex;
    private final String bandLabel;
    private final String trialNumber;
    private final String trialWord;
    private final String trialTargetNonword;
    private final String trialSyllables;
    private final String trialCondition;
    private final String trialLength;
    private final String trialPositionTarget;
    private final String trialPositionFoil;

    public AudioAsStimulusImplUTest(String uniqueId, Tag[] tags, String label, String code, int pauseMs, String audioPath, String videoPath, String imagePath, String ratingLabels, String correctResponses,
            String wordType, String positionInTrial, String bandIndex, String bandLabel, String trialNumber, String trialWord, String trialTargetNonword, String trialSyllables,
            String trialCondition, String trialLength, String trialPositionTarget, String trialPositionFoil) {
        super(uniqueId, tags, label, code, pauseMs, audioPath, videoPath, imagePath, ratingLabels, correctResponses);
        this.wordType = wordType;
        this.positionInTrial = positionInTrial;
        this.bandIndex = bandIndex;
        this.bandLabel = bandLabel;
        this.trialNumber = trialNumber;
        this.trialWord = trialWord;
        this.trialTargetNonword = trialTargetNonword;
        this.trialSyllables = trialSyllables;
        this.trialCondition = trialCondition;
        this.trialLength = trialLength;
        this.trialPositionTarget = trialPositionTarget;
        this.trialPositionFoil = trialPositionFoil;
    }

    @Override
    public String getwordType() {
        return this.wordType;
    }

    @Override
    public String getpositionInTrial() {
        return this.positionInTrial;
    }

    @Override
    public String getbandIndex() {
        return this.bandIndex;
    }

    @Override
    public String getbandLabel() {
        return this.bandLabel;
    }

    @Override
    public String gettrialNumber() {
        return this.trialNumber;
    }

    @Override
    public String gettrialWord() {
        return this.trialWord;
    }


    @Override
    public String gettrialSyllables() {
        return this.trialSyllables;
    }

    @Override
    public String gettrialCondition() {
        return this.trialCondition;
    }

    @Override
    public String gettrialLength() {
        return this.trialLength;
    }

    @Override
    public String gettrialPositionTarget() {
        return this.trialPositionTarget;
    }

    @Override
    public String gettrialPositionFoil() {
        return this.trialPositionFoil;
    }
    
    @Override
    public String gettrialTargetNonword() {
        return this.trialTargetNonword;
    }

}
