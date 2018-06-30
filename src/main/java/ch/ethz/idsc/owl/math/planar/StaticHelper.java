// code by jph
package ch.ethz.idsc.owl.math.planar;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

enum StaticHelper {
  ;
  static Scalar det(Tensor p, Tensor q) {
    return Cross2D.of(p).dot(q).Get();
  }
}
