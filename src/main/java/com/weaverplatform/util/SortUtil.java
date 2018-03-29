package com.weaverplatform.util;

/**
 * @author bastbijl, Sysunite 2018
 */
public class SortUtil {

  public static int compareReverseIndex(String key1, String key2, int reverseIndex) {
    int i1 = key1.length() - reverseIndex - 1;
    int i2 = key2.length() - reverseIndex - 1;
    if(i1 > -1 && i2 > -1) {
      char c1 = key1.charAt(i1);
      char c2 = key2.charAt(i2);
      if(c1 == c2) {
        return compareReverseIndex(key1, key2, reverseIndex + 1);

      } else {
        return compareChar(c1, c2);
      }
    }
    if(i1 == -1 && i2 == -1) {
      return 0;
    }
    if(i1 == -1) {
      return -1;
    }
    return 1;
  }

  public static int compareChar(char c1, char c2) {
    if(c1 == c2) {
      return 0;

      // Special case for ':'
    } else if(c1 == ':') {
      return -1;
    } else if(c2 == ':') {
      return 1;

    } else {
      return c1 - c2;
    }
  }
}
