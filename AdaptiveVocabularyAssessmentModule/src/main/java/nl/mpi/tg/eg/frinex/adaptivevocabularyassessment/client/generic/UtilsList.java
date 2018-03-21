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
package nl.mpi.tg.eg.frinex.adaptivevocabularyassessment.client.generic;

import java.util.ArrayList;

/**
 *
 * @author olhshk
 */
public class UtilsList<T> {
    
     public boolean listElementExists(ArrayList<ArrayList<T>> source, ArrayList<T> candidate) {
        for (ArrayList<T> list : source) {
            boolean coinside = true;
            int i = 0;
            while (coinside && i < list.size()) {
                if (list.get(i).equals(candidate.get(i))) {
                    i++;
                } else {
                    coinside = false;
                }
            }
            if (coinside) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<ArrayList<T>> generatePermutations(ArrayList<T> elements) {
        
        if (elements == null) {
            return null;
        }
        
        if (elements.isEmpty()) {
            return new ArrayList<ArrayList<T>>();
        }
        
         if (elements.size()==1) {
            ArrayList<ArrayList<T>> retVal = new ArrayList<ArrayList<T>>(1); 
            retVal.add(0,elements);
            return retVal;
        }
        
        ArrayList<ArrayList<T>> retVal = new ArrayList<ArrayList<T>>();
        for (int i = 0; i < elements.size(); i++) {
            ArrayList<T> copyReferences = new ArrayList<T>(elements.size());
            for (int j = 0; j < elements.size(); j++) {
                copyReferences.add(j, elements.get(j));
            }
            T element = copyReferences.remove(i);
            ArrayList<ArrayList<T>> tailPermutations = this.generatePermutations(copyReferences);
            for (ArrayList<T> tailPermutation : tailPermutations) {
                ArrayList<T> permutation = new ArrayList<T>(tailPermutation.size() + 1);
                permutation.add(element);
                permutation.addAll(tailPermutation);
                boolean duplication = this.listElementExists(retVal, permutation);
                if (!duplication) {
                    retVal.add(permutation);
                }
            }
        }
        return retVal;
    }
    
   

}
