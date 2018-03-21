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

import java.util.ArrayList;
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.BookkeepingStimulus;
import nl.mpi.tg.eg.frinex.common.model.Stimulus.Tag;

/**
 *
 * @author olhshk
 */
public class StimuliTestPool {
    
    public ArrayList<BookkeepingStimulus<AudioAsStimulus>> stimuli = new ArrayList<BookkeepingStimulus<AudioAsStimulus>>(4);
    
    //AudioAsStimulusImplUTest(String uniqueId, Stimulus.Tag[] tags, String label, String code, int pauseMs, String audioPath, String videoPath, String imagePath, String ratingLabels, String correctResponses,
    //        String wordType, String positionInTrial, String bandIndex, String bandLabel, String trialNumber, String trialWord, String trialCueFile, String trialSyllables,
    //        String trialCondition, String trialLength, String trialPositionTarget, String trialPositionFoil)
    
    public StimuliTestPool(){
        
        AudioAsStimulusImplUTest st1 = new AudioAsStimulusImplUTest("baf_0000", new Tag[1], "baf", "", 0, "", "", "", "", "",  
                WordType.TARGET_NON_WORD.toString(), 
                "2", 
                "5", "5dB", "4", "baaf", "baf.ogg", "1",TrialCondition.TARGET_ONLY.toString(), "4", "2", "0");
        this.stimuli.add(new BookkeepingStimulus<AudioAsStimulus>(st1));
        
    }
}
