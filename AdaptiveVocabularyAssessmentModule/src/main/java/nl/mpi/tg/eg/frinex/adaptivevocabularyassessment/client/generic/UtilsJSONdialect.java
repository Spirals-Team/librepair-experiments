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
import java.util.HashMap;
/**
 *
 * @author olhshk
 */
public class UtilsJSONdialect<S> {

    public static String getKey(String jsonString, String key) throws Exception {
        if (jsonString == null) {
            return null;
        }
        if (key == null) {
            return jsonString;
        }
        String[] parts = jsonString.split(key + ":");
        if (parts.length < 2) {
            return null;
        }
        String buffer = parts[1].trim();
        StringBuilder retVal = new StringBuilder();
        char current = buffer.charAt(0);
        if (current != '{') {
            throw new Exception("Get key from string parsing error, no { at the beginning of the object");
        }
        int openBrackets = 1;
        retVal.append("{");
        for (int i = 1; i < buffer.length(); i++) {
            current = buffer.charAt(i);
            retVal.append(current);
            if (current == '}') {
                if (openBrackets == 1) {
                    return retVal.toString();
                }
                openBrackets--;
            } else {
                if (current == '{') {
                    openBrackets++;
                }
            }

        }
        throw new Exception("Get key from string parsing error, no matching } ");
    }

    public static String removeFirstAndLast(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() < 2) {
            return "";
        }
        String retVal = str.substring(1, str.length() - 1);
        return retVal;
    }

    public static String getKeyWithoutBrackets(String jsonString, String key) throws Exception {
        String buffer = getKey(jsonString, key);
        String retVal = removeFirstAndLast(buffer);
        return retVal;
    }

    public String arrayListToString(ArrayList<S> list) throws Exception {
        if (list == null) {
            return null;
        }
        if (list.isEmpty()) {
            return "{}";
        }
        StringBuilder retVal = new StringBuilder();
        retVal.append("{");
        for (int i = 0; i < list.size() - 1; i++) {
            S obj = list.get(i);
            retVal.append(i).append(":");
            String objString = obj.toString();
            if (objString.startsWith("{")) {
                retVal.append(objString);
            } else {
                retVal.append("{").append(objString).append("}");
            }
            retVal.append(",");
        }
        
        int lastIndex = list.size() - 1;
        S obj = list.get(lastIndex);
        retVal.append(lastIndex).append(":");
        String objString = obj.toString();
        if (objString.startsWith("{")) {
            retVal.append(objString);
        } else {
            retVal.append("{").append(objString).append("}");
        }
        retVal.append("}");
        return retVal.toString();
    }

    public String arrayList2String(ArrayList<ArrayList<S>> list) throws Exception {
        if (list == null) {
            return null;
        }
         if (list.isEmpty()) {
            return "{}";
        }
        StringBuilder retVal = new StringBuilder();
        UtilsJSONdialect<S> util = new UtilsJSONdialect<S>();
        retVal.append("{");
        for (int i = 0; i < list.size() - 1; i++) {
            ArrayList<S> subList = list.get(i);
            retVal.append(i).append(":");
            String subListString = util.arrayListToString(subList);
            retVal.append(subListString);
            retVal.append(",");
        }
        int lastIndex = list.size() - 1;
        ArrayList<S> subList = list.get(lastIndex);
        retVal.append(lastIndex).append(":");
        String subListString = util.arrayListToString(subList);
        retVal.append(subListString);
        retVal.append("}");
        return retVal.toString();
    }

    
    public String arrayList3String(ArrayList<ArrayList<ArrayList<S>>> list) throws Exception {
        if (list == null) {
            return null;
        }
         if (list.isEmpty()) {
            return "{}";
        }
        StringBuilder retVal = new StringBuilder();
        UtilsJSONdialect<S> util = new UtilsJSONdialect<S>();
        retVal.append("{");
        for (int i = 0; i < list.size() - 1; i++) {
            ArrayList<ArrayList<S>> subList = list.get(i);
            retVal.append(i).append(":");
            String subListString = util.arrayList2String(subList);
            retVal.append(subListString);
            retVal.append(",");
        }
        int lastIndex = list.size() - 1;
        ArrayList<ArrayList<S>> subList = list.get(lastIndex);
        retVal.append(lastIndex).append(":");
        String subListString = util.arrayList2String(subList);
        retVal.append(subListString);
        retVal.append("}");
        return retVal.toString();
    }

    public String intArrayListToString(int[] arr) throws Exception {
        if (arr == null) {
            return null;
        }
        StringBuilder retVal = new StringBuilder();
        retVal.append("{");
        for (int i = 0; i < arr.length - 1; i++) {
            retVal.append(i).append(":");
            retVal.append("{").append(arr[i]).append("}");
            retVal.append(",");
        }
        int lastIndex = arr.length - 1;
        retVal.append(lastIndex).append(":");
        retVal.append("{").append(arr[lastIndex]).append("}");
        retVal.append("}");
        return retVal.toString();
    }
    
     public String doubleArrayListToString(double[] arr) throws Exception {
        if (arr == null) {
            return null;
        }
        StringBuilder retVal = new StringBuilder();
        retVal.append("{");
        for (int i = 0; i < arr.length - 1; i++) {
            retVal.append(i).append(":");
            retVal.append("{").append(arr[i]).append("}");
            retVal.append(",");
        }
        int lastIndex = arr.length - 1;
        retVal.append(lastIndex).append(":");
        retVal.append("{").append(arr[lastIndex]).append("}");
        retVal.append("}");
        return retVal.toString();
    }

    
     public ArrayList<String> stringToArrayList(String listStr) throws Exception {
        if (listStr == null) {
            return null;
        }
        ArrayList<String> retVal = new ArrayList<String>();
        String current = this.getKey(listStr, "0");
        if (current == null) {
            return null;
        }
        int i = 0;
        while (current != null) {
            retVal.add(i, current);
            i++;
            String index = String.valueOf(i);
            current = this.getKey(listStr, index);
        }
        return retVal;
    }

    public ArrayList<Integer> stringToArrayListInteger(String listStr) throws Exception{
        ArrayList<String>  buffer = this.stringToArrayList(listStr);
        ArrayList<Integer> retVal = new  ArrayList<Integer>(buffer.size());
        for (int i=0; i<buffer.size(); i++) {
            String val = buffer.get(i);
            String tmp = removeFirstAndLast(val);
            Integer valInt = Integer.parseInt(tmp);
            retVal.add(i, valInt);
        }
        return retVal;
    }
    
    public double[] stringToArrayDouble(String listStr) throws Exception{
        ArrayList<String>  buffer = this.stringToArrayList(listStr);
        double[] retVal = new  double[buffer.size()];
        for (int i=0; i<buffer.size(); i++) {
            String val = buffer.get(i);
            String tmp = removeFirstAndLast(val);
            double valInt = Double.parseDouble(tmp);
            retVal[i]=valInt;
        }
        return retVal;
    }
    
   
}
