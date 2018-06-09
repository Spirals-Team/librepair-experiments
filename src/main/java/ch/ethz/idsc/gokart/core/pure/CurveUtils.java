// code by jph
package ch.ethz.idsc.gokart.core.pure;

import java.util.Optional;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.qty.Degree;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.ArcTan;

public enum CurveUtils {
  ;
  private static final int NO_MATCH = -1;
  /** 28 is half of 42 therefore this is the answer (joke) */
  private static final Scalar ANGLE_LIMIT = Degree.of(28);

  /** @param curve_local with or without intersections
   * @param dist
   * @return TODO documentation */
  public static Optional<Tensor> getAheadTrail(Tensor curve_local, Scalar dist) {
    int index = closestParallelThan(curve_local, dist);
    if (index != NO_MATCH) {
      int length = curve_local.length();
      return Optional.of(Tensor.of( //
          IntStream.range(index, index + length / 2) // at most half of the curve
              .map(i -> i % length) //
              .mapToObj(curve_local::get)));
    }
    return Optional.empty();
  }

  /** @param curve_local in robot coordinates
   * @param dist
   * @return */
  /* package */ static int closestParallelThan(Tensor curve_local, Scalar dist) {
    int best = NO_MATCH;
    for (int index = 0; index < curve_local.length(); ++index) {
      final Tensor p0 = curve_local.get(index);
      Scalar norm = Norm._2.of(p0); // vector in local coordinates
      if (Scalars.lessThan(norm, dist)) {
        int next = index + 1;
        next %= curve_local.length();
        Tensor p1 = curve_local.get(next);
        Tensor dif = p1.subtract(p0);
        Scalar angle = ArcTan.of(dif.Get(0), dif.Get(1));
        if (Scalars.lessThan(angle.abs(), ANGLE_LIMIT)) {
          dist = norm;
          best = index;
        }
      }
    }
    return best;
  }

  /** FUNCTION IS NOT IN USE
   * 
   * @param curve_local without any intersections
   * @param dist
   * @return */
  /* package */ static int closestCloserThan(Tensor curve_local, Scalar dist) {
    int best = NO_MATCH;
    for (int index = 0; index < curve_local.length(); ++index) {
      Scalar norm = Norm._2.of(curve_local.get(index)); // vector in local coordinates
      if (Scalars.lessThan(norm, dist)) {
        dist = norm;
        best = index;
      }
    }
    return best;
  }
}
