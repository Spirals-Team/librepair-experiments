// code by ynager
package ch.ethz.idsc.gokart.core.map;

import ch.ethz.idsc.subare.util.GlobalAssert;

enum StaticHelper {
  ;
  /** @param p from the open interval (0, 1)
   * @return */
  static double pToLogOdd(double p) {
    GlobalAssert.that(p < 1);
    return Math.log(p / (1 - p));
  }
}
