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
import nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic.UtilsJSONdialect;

/**
 *
 * @author olhshk
 */
public class PermutationPair {

    public final ArrayList<TrialCondition> trialTypes;
    public final ArrayList<Integer> trialLengths;

    public PermutationPair(ArrayList<TrialCondition> trialTypes, ArrayList<Integer> trialLengths) {
        this.trialTypes = trialTypes;
        this.trialLengths = trialLengths;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        UtilsJSONdialect<TrialCondition> util1 = new UtilsJSONdialect<TrialCondition>();

        try {
            String trailTypesStr = util1.arrayListToString(this.trialTypes);
            if (trailTypesStr != null) {
                builder.append("trialTypes:").append(trailTypesStr).append(",");
            }
        } catch (Exception ex) {
        }

        UtilsJSONdialect<Integer> util2 = new UtilsJSONdialect<Integer>();
        try {
            String trialLengthsStr = util2.arrayListToString(this.trialLengths);
            if (trialLengthsStr != null) {
                builder.append("trialLengths:").append(trialLengthsStr);
            }
        } catch (Exception ex) {
        }

        builder.append("}");
        return builder.toString();
    }

    public PermutationPair toObject(String str) {

        UtilsJSONdialect<String>  util1 = new UtilsJSONdialect<String>();
        UtilsJSONdialect<Integer> util2 = new UtilsJSONdialect<Integer>();

        try {
            
            String triallTypesStr = UtilsJSONdialect.getKey(str, "trialTypes");
            ArrayList<String> trialTypesArrayStr = util1.stringToArrayList(triallTypesStr);
            ArrayList<TrialCondition> trialConds = new ArrayList<TrialCondition>(trialTypesArrayStr.size());
            for (int i=0; i<trialTypesArrayStr.size(); i++) {
                trialConds.add(null);
            }
            for (int i=0; i<trialTypesArrayStr.size(); i++) {
                TrialCondition tc = TrialCondition.valueOf(trialTypesArrayStr.get(i).trim());
                trialConds.set(i, tc);
            }
            
            String lengthStr = UtilsJSONdialect.getKey(str, "trialLength");
            ArrayList<Integer> lengths = util2.stringToArrayListInteger(lengthStr);
           
            PermutationPair retVal = new PermutationPair(trialConds, lengths);
            return retVal;
            
        } catch (Exception ex) {
            return null;
        }
  
    }

}
