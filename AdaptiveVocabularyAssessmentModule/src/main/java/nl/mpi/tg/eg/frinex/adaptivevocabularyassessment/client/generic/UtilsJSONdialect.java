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
import java.util.LinkedHashMap;
import java.util.List;
import nl.mpi.tg.eg.frinex.common.model.Stimulus.Tag;

/**
 *
 * @author olhshk
 */
public class UtilsJSONdialect {

    public static Tag[] toTagArray(Object obj) {
        if (obj == null) {
            return null;
        }

        List<Object> tagsBuffer = (List<Object>) obj;
        Tag[] tgs = new Tag[tagsBuffer.size()];
        int i = 0;
        for (Object objTag : tagsBuffer) {
            Tag tg = (Tag) objTag;
            tgs[i] = tg;
        }
        return tgs;
    }

    public static Object stringToObject(String str) throws Exception {

        if (str == null) {
            return null;
        }

        if (str.trim().equals("null")) {
            return null;
        }

        String buffer = str.trim();

        if (buffer.substring(0, 1).equals("[")) { //is a generic list
            return stringToList(buffer);
        }

        int ind = buffer.indexOf("fields=");
        if (ind > -1) {
            String fields = getKey(buffer, "fields");
            if (fields != null) { // a map epresenting an object
                Object object = stringToObject(fields);
                String[] flds = objectToArrayString(object);
                return stringToObjectMap(buffer, flds);
            }
        }

        if (buffer.substring(0, 1).equals("{")) { // is a generic map
            return stringToMap(buffer);
        }

        return str;
    }

    public static ArrayList<Integer> objectToListInteger(Object obj) {
        if (obj == null) {
            return null;
        }
        List<Object> objs = (List<Object>) obj;
        ArrayList<Integer> retVal = new ArrayList<Integer>(objs.size());
        for (Object element : objs) {
            String tmp = element.toString();
            Integer val = Integer.parseInt(tmp.trim());
            retVal.add(val);
        }
        return retVal;
    }

    public static Integer[] objectToArrayInteger(Object obj) {
        if (obj == null) {
            return null;
        }
        List<Object> objs = (List<Object>) obj;
        Integer[] retVal = new Integer[objs.size()];
        for (int i = 0; i < objs.size(); i++) {
            String tmp = objs.get(i).toString();
            int val = Integer.parseInt(tmp.trim());
            retVal[i] = val;
        }
        return retVal;
    }

    public static String[] objectToArrayString(Object obj) {
        if (obj == null) {
            return null;
        }
        List<Object> objs = (List<Object>) obj;
        String[] retVal = new String[objs.size()];
        for (int i = 0; i < objs.size(); i++) {
            String tmp = objs.get(i).toString();
            retVal[i] = tmp;
        }
        return retVal;
    }

    public static ArrayList<Double> objectToListDouble(Object obj) {
        if (obj == null) {
            return null;
        }
        List<Object> objs = (List<Object>) obj;
        ArrayList<Double> retVal = new ArrayList<Double>(objs.size());
        for (int i = 0; i < objs.size(); i++) {
            String tmp = objs.get(i).toString();
            Double val = Double.parseDouble(tmp);
            retVal.add(i, val);
        }
        return retVal;
    }

    private static String getKey(String jsonStringInit, String key) throws Exception {

        if (jsonStringInit == null) {
            System.out.println("Warning: looking for key " + key + " in the empty string.");
            return null;
        }
        if (key == null) {
            return jsonStringInit;
        }

        String jsonString = jsonStringInit.trim();
        int lg = jsonString.length();
        if (jsonString.substring(0, 1).equals("{") && jsonString.substring(lg - 1, lg).equals("}")) {
            jsonString = removeFirstAndLast(jsonString);
        } else {
            if (jsonStringInit.substring(0, 1).equals("[") && jsonString.substring(lg - 1, lg).equals("]")) {
                jsonString = removeFirstAndLast(jsonString);
            }
        }

        String regExp = "(^" + key + "=)|( " + key + "=)";

        String[] parts = jsonString.split(regExp, 2);
        if (parts.length < 2) {
            System.out.println("Warning. Parser: cannot find the value for the key " + key + " in the string " + jsonString);
            return null;
        }
        String buffer = parts[1].trim();
        StringBuilder val = new StringBuilder();
        char current = buffer.charAt(0);
        if (current != '{' && current != '[') { // the value is neither a list not a structure, return the sequence till the first comma
            String[] parts2 = buffer.split(",");
            return parts2[0].trim();
        } else { // the value is either a list or a structure
            char openingBracket = current;
            char closingBracket = (openingBracket == '{') ? '}' : ']';
            int openBrackets = 1;
            val.append(openingBracket);
            for (int i = 1; i < buffer.length(); i++) {
                current = buffer.charAt(i);
                val.append(current);
                if (current == closingBracket) {
                    if (openBrackets == 1) {
                        return val.toString();
                    }
                    openBrackets--;
                } else {
                    if (current == openingBracket) {
                        openBrackets++;
                    }
                }
            }
            String message = "Cannot get the value for key " + key + "from string parsing error, no matching } or  ]";
            System.out.println(message);
            throw new Exception(message);
        }

    }

    private static String removeFirstAndLast(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() < 2) {
            return str;
        }
        String retVal = str.substring(1, str.length() - 1);
        return retVal;
    }

    private static List<Object> stringToList(String listStr) throws Exception {
        if (listStr == null) {
            return null;
        }

        if (listStr.trim().equals("null") || listStr.trim().equals("{null}")) {
            return null;
        }

        if (listStr.trim().isEmpty() || listStr.trim().equals("[]")) {
            return new ArrayList<Object>();
        }

        String buffer = removeFirstAndLast(listStr.trim());

        // mark begin and end to avoid case analysis
        buffer = ", " + buffer + ", ";

        List<Integer> allowedPositions = positionsNotWithinParentheses(buffer);
        List<Integer> commaPositions = new ArrayList<Integer>();
        for (Integer pos : allowedPositions) {
            if (buffer.charAt(pos) == ',') {
                commaPositions.add(pos);
            }
        }
        List<Object> retVal = new ArrayList<Object>();
        for (int i = 0; i < commaPositions.size() - 1; i++) {
            String valStr = buffer.substring(commaPositions.get(i) + 1, commaPositions.get(i + 1)).trim();
            Object val = stringToObject(valStr);
            retVal.add(val);
        }

        return retVal;
    }

    public static LinkedHashMap<String, Object> stringToObjectMap(String mapStr, String[] fields) throws Exception {
        if (mapStr == null) {
            return null;
        }

        if (mapStr.trim().equals("null") || mapStr.trim().equals("{null}")) {
            return null;
        }

        if (mapStr.trim().isEmpty() || mapStr.trim().equals("{}")) {
            return new LinkedHashMap<String, Object>();
        }

        LinkedHashMap<String, Object> retVal = new LinkedHashMap<String, Object>();
        for (int i = 0; i < fields.length; i++) {
            String key = fields[i].trim();
            String current = getKey(mapStr, key);
            Object object = stringToObject(current);
            retVal.put(key, object);
        }
        return retVal;
    }

    private static LinkedHashMap<String, Object> stringToMap(String mapStr) throws Exception {
        if (mapStr == null) {
            return null;
        }

        if (mapStr.trim().equals("null") || mapStr.trim().equals("{null}")) {
            return null;
        }

        if (mapStr.trim().isEmpty() || mapStr.trim().equals("{}")) {
            return new LinkedHashMap<String, Object>();
        }

        LinkedHashMap<String, Object> retVal = new LinkedHashMap<String, Object>();

        List<String> keys = findKeys(mapStr);
        for (String key : keys) {
            String keyTr = key.trim();
            String current = getKey(mapStr, keyTr);
            Object object = stringToObject(current);
            retVal.put(keyTr, object);
        }

        return retVal;

    }

    public static List<Integer> positionsNotWithinParentheses(String str) {
        List<Integer> retVal = new ArrayList<Integer>();
        int openBracket = 0;
        int openCurly = 0;
        for (int i = 0; i < str.length(); i++) {
            char current = str.charAt(i);
            switch (current) {
                case '[':
                    openBracket++;
                    break;
                case ']':
                    openBracket--;
                    break;
                case '{':
                    openCurly++;
                    break;
                case '}':
                    openCurly--;
                    break;
                default:
                    if (openBracket == 0 && openCurly == 0) {
                        retVal.add(i);
                    }
                    break;
            }
        }
        return retVal;
    }

    private static List<String> findKeys(String str) {
        if (str == null) {
            return null;
        }

        String buffer = str.trim();
        int lg = buffer.length();
        if (buffer.substring(0, 1).equals("{") && buffer.substring(lg - 1, lg).equals("}")) {
            buffer = removeFirstAndLast(buffer);
        }
        List<Integer> allowedPosition = positionsNotWithinParentheses(buffer);
        List<Integer> eqPositions = new ArrayList<Integer>();
        for (Integer pos : allowedPosition) {
            if (buffer.charAt(pos) == '=') {
                eqPositions.add(pos);
            }
        }

        char[] test = buffer.toCharArray();

        List<String> retVal = new ArrayList<String>();
        for (int i = 0; i < eqPositions.size(); i++) {
            int firstSpaceBack = findFirstSeparatorIndexBackwards(buffer, eqPositions.get(i) - 1, ' ');
            String key = buffer.substring(firstSpaceBack + 1, eqPositions.get(i));
            retVal.add(key);
        }

        return retVal;
    }

    private static int findFirstSeparatorIndexBackwards(String str, int from, char sep) {
        if (str == null) {
            return -1;
        }
        for (int i = from; i > -1; i--) {
            if (str.charAt(i) == sep) {
                return i;
            }
        }
        return -1;
    }
}
