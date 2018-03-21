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

import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BandStimulus;

/**
 *
 * @author olhshk
 */
abstract public class AudioAsStimulus extends BandStimulus {

    
    public static final String AUDIO_RATING_LABEL = "&#160;";
    public static final String EXAMPLE_TARGET_LABEL = null;
    public static final int PAUSE_EXAMPLE = 60000;
    public static final int PAUSE = 900;

    public AudioAsStimulus(String uniqueId, Tag[] tags, String label, String code, int pauseMs, String audioPath, String videoPath, String imagePath, String ratingLabels, String correctResponses) {
        super(uniqueId, tags, label, code, pauseMs, audioPath, videoPath, imagePath, ratingLabels, correctResponses);
    }
    
 
    public abstract  String getwordType();
    public abstract  String getpositionInTrial();
    
    public WordType getWordTypeWT(){
       return WordType.valueOf(this.getwordType()); 
    }
    
    
    public abstract  String gettrialNumber();
    public abstract  String gettrialWord();
    public abstract  String gettrialSyllables();
    public abstract  String gettrialCondition();
    public abstract  String gettrialLength();
    public abstract  String gettrialPositionTarget();
    public abstract  String gettrialPositionFoil();
    public abstract  String gettrialTargetNonword();
    
    
    public int getPositionInTrialInt(){
       return Integer.parseInt(this.getpositionInTrial()); 
    }
    
    public int getTrialNumberInt(){
       return Integer.parseInt(this.gettrialNumber()); 
    }
    
    public int getTrialSyllablesInt(){
       return Integer.parseInt(this.gettrialSyllables()); 
    }
    
    public int getTrialLengthInt(){
       return Integer.parseInt(this.gettrialLength()); 
    }
    
    public int getTrialPositionTargetInt(){
       return Integer.parseInt(this.gettrialPositionTarget()); 
    }
    
    public int getTrialPositionFoilInt(){
       return Integer.parseInt(this.gettrialPositionFoil()); 
    }
    
   
    public TrialCondition getTrialConditionTC(){
       return TrialCondition.valueOf(this.gettrialCondition()); 
    }

    @Override
    public boolean hasCorrectResponses() {
        return true;
    }

}
