package io.ebean.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Utility String class that supports String manipulation functions.
 */
public class StringHelper {

  private static final char SINGLE_QUOTE = '\'';

  private static final char DOUBLE_QUOTE = '"';

  private static final Pattern SPLIT_NAMES = Pattern.compile("[\\s,;]+");

  private static final String[] EMPTY_STRING_ARRAY = new String[0];

  /**
   * parses a String of the form name1='value1' name2='value2'. Note that you
   * can use either single or double quotes for any particular name value pair
   * and the end quote must match the begin quote.
   */
  public static HashMap<String, String> parseNameQuotedValue(String tag) throws RuntimeException {

    if (tag == null || tag.length() < 1) {
      return null;
    }

    // make sure that the quotes are matched...
    // int remainer = countOccurances(tag, ""+quote) % 2;
    // if (remainer == 1) {
    // dp("remainder = "+remainer);
    // throw new StringParsingException("Unmatched quote in "+tag);
    // }

    // make sure that th last character is not an equals...
    // (check now so I don't need to check this every time..)
    if (tag.charAt(tag.length() - 1) == '=') {
      throw new RuntimeException("missing quoted value at the end of " + tag);
    }

    HashMap<String, String> map = new HashMap<>();
    // recursively parse out the name value pairs...
    return parseNameQuotedValue(map, tag, 0);
  }

  /**
   * recursively parse out name value pairs (where the value is quoted, with
   * either single or double quotes).
   */
  private static HashMap<String, String> parseNameQuotedValue(HashMap<String, String> map,
                                                              String tag, int pos) throws RuntimeException {
    while (true) {

      int equalsPos = tag.indexOf('=', pos);
      if (equalsPos > -1) {
        // check for begin quote...
        char firstQuote = tag.charAt(equalsPos + 1);
        if (firstQuote != SINGLE_QUOTE && firstQuote != DOUBLE_QUOTE) {
          throw new RuntimeException("missing begin quote at " + (equalsPos) + "["
            + tag.charAt(equalsPos + 1) + "] in [" + tag + "]");
        }

        // check for end quote...
        int endQuotePos = tag.indexOf(firstQuote, equalsPos + 2);
        if (endQuotePos == -1) {
          throw new RuntimeException("missing end quote [" + firstQuote + "] after " + pos + " in [" + tag + "]");
        }

        // we have a valid name and value...
        // dp("pos="+pos+" equalsPos="+equalsPos+"
        // endQuotePos="+endQuotePos);
        String name = tag.substring(pos, equalsPos);
        // dp("name="+name+"; value="+value+";");

        // trim off any whitespace from the front of name...
        name = trimFront(name, " ");
        if ((name.indexOf(SINGLE_QUOTE) > -1) || (name.indexOf(DOUBLE_QUOTE) > -1)) {
          throw new RuntimeException("attribute name contains a quote [" + name + "]");
        }

        String value = tag.substring(equalsPos + 2, endQuotePos);
        map.put(name, value);

        pos = endQuotePos + 1;

      } else {
        // no more equals... stop parsing...
        return map;
      }
    }
  }

  /**
   * Returns the number of times a particular String occurs in another String.
   * e.g. count the number of single quotes.
   */
  public static int countOccurances(String content, String occurs) {
    return countOccurances(content, occurs, 0, 0);
  }

  private static int countOccurances(String content, String occurs, int pos, int countSoFar) {
    while (true) {
      int equalsPos = content.indexOf(occurs, pos);
      if (equalsPos > -1) {
        countSoFar += 1;
        pos = equalsPos + occurs.length();
        // dp("countSoFar="+countSoFar+" pos="+pos);
      } else {
        return countSoFar;
      }
    }
  }

  /**
   * Parses out a list of Name Value pairs that are delimited together. Will
   * always return a StringMap. If allNameValuePairs is null, or no name values
   * can be parsed out an empty StringMap is returned.
   *
   * @param allNameValuePairs  the entire string to be parsed.
   * @param listDelimiter      (typically ';') the delimited between the list
   * @param nameValueSeparator (typically '=') the separator between the name and value
   */
  public static Map<String, String> delimitedToMap(String allNameValuePairs,
                                                   String listDelimiter, String nameValueSeparator) {

    HashMap<String, String> params = new HashMap<>();
    if ((allNameValuePairs == null) || (allNameValuePairs.isEmpty())) {
      return params;
    }
    // trim off any leading listDelimiter...
    allNameValuePairs = trimFront(allNameValuePairs, listDelimiter);
    return getKeyValue(params, 0, allNameValuePairs, listDelimiter, nameValueSeparator);
  }

  /**
   * Trims off recurring strings from the front of a string.
   *
   * @param source the source string
   * @param trim   the string to trim off the front
   */
  public static String trimFront(String source, String trim) {
    while (true) {
      if (source == null) {
        return null;
      }
      if (source.indexOf(trim) == 0) {
        // dp("trim ...");
        source = source.substring(trim.length());
      } else {
        return source;
      }
    }
  }

  /**
   * Return true if the value is null or an empty string.
   */
  public static boolean isNull(String value) {
    return value == null || value.trim().isEmpty();
  }

  /**
   * Recursively pulls out the key value pairs from a raw string.
   */
  private static HashMap<String, String> getKeyValue(HashMap<String, String> map, int pos,
                                                     String allNameValuePairs, String listDelimiter, String nameValueSeparator) {
    while (true) {

      if (pos >= allNameValuePairs.length()) {
        // dp("end as "+pos+" >= "+allNameValuePairs.length() );
        return map;
      }

      int equalsPos = allNameValuePairs.indexOf(nameValueSeparator, pos);
      int delimPos = allNameValuePairs.indexOf(listDelimiter, pos);

      if (delimPos == -1) {
        delimPos = allNameValuePairs.length();
      }
      if (equalsPos == -1) {
        // dp("no more equals...");
        return map;
      }
      if (delimPos == (equalsPos + 1)) {
        // dp("Ignoring as nothing between delim and equals...
        // delim:"+delimPos+" eq:"+equalsPos);
        pos = delimPos + 1;
        continue;
      }
      if (equalsPos > delimPos) {
        // there is a key without a value?
        String key = allNameValuePairs.substring(pos, delimPos);
        key = key.trim();
        if (!key.isEmpty()) {
          map.put(key, null);
        }
        pos = delimPos + 1;
        continue;

      }
      String key = allNameValuePairs.substring(pos, equalsPos);

      if (delimPos > -1) {
        String value = allNameValuePairs.substring(equalsPos + 1, delimPos);
        // dp("cont "+key+","+value+" pos:"+pos+"
        // len:"+allNameValuePairs.length());
        key = key.trim();

        map.put(key, value);
        pos = delimPos + 1;

        // recurse the rest of the values...

      } else {
        // dp("ERROR: delimPos < 0 ???");
        return map;
      }
    }
  }

  /**
   * Convert a string that has delimited values (say comma delimited) in a
   * String[]. You must explicitly choose whether or not to include empty values
   * (say two commas that a right beside each other.
   * <p>
   * e.g. "alpha,beta,,theta"<br>
   * With keepEmpties true, this results in a String[] of size 4 with the third
   * one having a String of 0 length. With keepEmpties false, this results in a
   * String[] of size 3.
   * <p>
   * e.g. ",alpha,beta,,theta,"<br>
   * With keepEmpties true, this results in a String[] of size 6 with the
   * 1st,4th and 6th one having a String of 0 length. With keepEmpties false,
   * this results in a String[] of size 3.
   */
  public static String[] delimitedToArray(String str, String delimiter, boolean keepEmpties) {

    ArrayList<String> list = new ArrayList<>();
    int startPos = 0;
    delimiter(str, delimiter, keepEmpties, startPos, list);
    String[] result = new String[list.size()];
    return list.toArray(result);
  }

  private static void delimiter(String str, String delimiter, boolean keepEmpties, int startPos,
                                ArrayList<String> list) {

    int endPos = str.indexOf(delimiter, startPos);
    if (endPos == -1) {
      if (startPos <= str.length()) {
        String lastValue = str.substring(startPos, str.length());
        if (keepEmpties || !lastValue.isEmpty()) {
          list.add(lastValue);
        }
      }
      // we have finished parsing the string...

    } else {
      // get the delimited value... add it..
      String value = str.substring(startPos, endPos);
      if (keepEmpties || !value.isEmpty()) {
        list.add(value);
      }
      // recursively search as we are not at the end yet...
      delimiter(str, delimiter, keepEmpties, endPos + 1, list);
    }
  }

  /**
   * This method takes a String and will replace all occurrences of the match
   * String with that of the replace String.
   *
   * @param source  the source string
   * @param match   the string used to find a match
   * @param replace the string used to replace match with
   * @return the source string after the search and replace
   */
  public static String replaceString(String source, String match, String replace) {
    if (source == null) {
      return null;
    }
    if (replace == null) {
      return source;
    }
    if (match == null) {
      throw new NullPointerException("match is null?");
    }
    if (match.equals(replace)) {
      return source;
    }
    return replaceString(source, match, replace, 30, 0, source.length());
  }

  /**
   * Additionally specify the additionalSize to add to the buffer. This will
   * make the buffer bigger so that it doesn't have to grow when replacement
   * occurs.
   */
  public static String replaceString(String source, String match, String replace,
                                     int additionalSize, int startPos, int endPos) {

    if (source == null) {
      return null;
    }

    char match0 = match.charAt(0);

    int matchLength = match.length();

    if (matchLength == 1 && replace.length() == 1) {
      char replace0 = replace.charAt(0);
      return source.replace(match0, replace0);
    }
    if (matchLength >= replace.length()) {
      additionalSize = 0;
    }

    int sourceLength = source.length();
    int lastMatch = endPos - matchLength;

    StringBuilder sb = new StringBuilder(sourceLength + additionalSize);

    if (startPos > 0) {
      sb.append(source.substring(0, startPos));
    }

    char sourceChar;
    boolean isMatch;
    int sourceMatchPos;

    for (int i = startPos; i < sourceLength; i++) {
      sourceChar = source.charAt(i);
      if (i > lastMatch || sourceChar != match0) {
        sb.append(sourceChar);

      } else {
        // check to see if this is a match
        isMatch = true;
        sourceMatchPos = i;

        // check each following character...
        for (int j = 1; j < matchLength; j++) {
          sourceMatchPos++;
          if (source.charAt(sourceMatchPos) != match.charAt(j)) {
            isMatch = false;
            break;
          }
        }
        if (isMatch) {
          i = i + matchLength - 1;
          sb.append(replace);
        } else {
          // was not a match
          sb.append(sourceChar);
        }
      }
    }

    return sb.toString();
  }

  /**
   * A search and replace with multiple matching strings.
   * <p>
   * Useful when converting CRNL CR and NL all to a BR tag for example.
   * <pre>{@code
   *
   * String[] multi = { "\r\n", "\r", "\n" };
   * content = StringHelper.replaceStringMulti(content, multi, "<br/>");
   *
   * }</pre>
   */
  public static String replaceStringMulti(String source, String[] match, String replace) {
    if (source == null) {
      return null;
    }
    return replaceStringMulti(source, match, replace, 30, 0, source.length());
  }

  /**
   * Additionally specify an additional size estimate for the buffer plus start
   * and end positions.
   * <p>
   * The start and end positions can limit the search and replace. Otherwise
   * these default to startPos = 0 and endPos = source.length().
   * </p>
   */
  public static String replaceStringMulti(String source, String[] match, String replace,
                                          int additionalSize, int startPos, int endPos) {
    if (source == null) {
      return null;
    }
    int shortestMatch = match[0].length();

    char[] match0 = new char[match.length];
    for (int i = 0; i < match0.length; i++) {
      match0[i] = match[i].charAt(0);
      if (match[i].length() < shortestMatch) {
        shortestMatch = match[i].length();
      }
    }

    StringBuilder sb = new StringBuilder(source.length() + additionalSize);

    char sourceChar;

    int len = source.length();
    int lastMatch = endPos - shortestMatch;

    if (startPos > 0) {
      sb.append(source.substring(0, startPos));
    }

    int matchCount;

    for (int i = startPos; i < len; i++) {
      sourceChar = source.charAt(i);
      if (i > lastMatch) {
        sb.append(sourceChar);
      } else {
        matchCount = 0;
        for (int k = 0; k < match0.length; k++) {
          if (matchCount == 0 && sourceChar == match0[k]) {
            if (match[k].length() + i <= len) {

              ++matchCount;
              int j = 1;
              for (; j < match[k].length(); j++) {
                if (source.charAt(i + j) != match[k].charAt(j)) {
                  --matchCount;
                  break;
                }
              }
              if (matchCount > 0) {
                i = i + j - 1;
                sb.append(replace);
                break;
              }
            }
          }
        }
        if (matchCount == 0) {
          sb.append(sourceChar);
        }
      }
    }

    return sb.toString();
  }

  /**
   * Splits at any whitespace "," or ";" and trims the result.
   * It does not return empty entries.
   */
  public static String[] splitNames(String names) {
    if (names == null || names.isEmpty()) {
      return EMPTY_STRING_ARRAY;
    }
    String[] result = SPLIT_NAMES.split(names);
    if (result.length == 0) {
      return EMPTY_STRING_ARRAY; // don't know if this ever can happen
    }
    if ("".equals(result[0])) { //  = input string starts with whitespace
      if (result.length == 1) { //  = input string contains only whitespace
        return EMPTY_STRING_ARRAY;
      } else {
        String ret[] = new String[result.length-1]; // remove first entry
        System.arraycopy(result, 1, ret, 0, ret.length);
        return ret;
      }
    } else {
      return result;
    }
  }
}
