import java.util.*;

class A {
  private static Method loopsConstraints(ListIterator iterator) {
    int matches = 0;
    int lastMatch = matches;
    while (iterator.hasNext()) {
      matches = 0;
      for (int i = 0; i < 10; i++) {
        matches++;
      }
      if (matches == 0) {
      } else if (matches > lastMatch) {
        lastMatch = matches;
      } else if (matches == lastMatch) {

      }
    }
    return method;
  }
}
