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
public class TrialTestPool {
    
    public final Trial[] trials = new Trial[5];
    private final String[] words ={"baf", "git", "nom", "nam", "daf"};
    private final String[] cueFiles ={"baaf.ogg", "gid.ogg", "nomm.ogg", "naam.ogg", "daaf.ogg"};
    private final TrialCondition[] conditions = {TrialCondition.TARGET_ONLY, TrialCondition.NO_TARGET,TrialCondition.TARGET_AND_FOIL, TrialCondition.NO_TARGET, TrialCondition.TARGET_AND_FOIL};
    private final int[] lengths = {3, 6,4, 5,3};
    private final int[] bandInds = {4,3 ,3, 3,3};
    private final String[] bandLabels = {"0dB", "4dB", "4dB", "4dB", "4dB"};
    
    //Trial(int id, String word, String cueFile, int nOfSyllables, TrialCondition condition, int length, String bandLabel, int bandIndex)
    public TrialTestPool() {
        for (int i=0; i<5; i++) {
           String bandLabel = (new Integer(bandInds[i])).toString()+"dB";
           this.trials[i] = new Trial(i+1, this.cueFiles[i], this.words[i], 1, conditions[i], lengths[i], bandLabel, bandInds[i]); 
        }
    }
}
