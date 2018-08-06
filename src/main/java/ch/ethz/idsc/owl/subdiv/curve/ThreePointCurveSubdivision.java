// code by jph
package ch.ethz.idsc.owl.subdiv.curve;

import java.io.Serializable;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** dual scheme */
public class ThreePointCurveSubdivision implements CurveSubdivision, Serializable {
  private static final Scalar OMEGA = RationalScalar.of(1, 32);
  private static final Scalar _1_4 = RationalScalar.of(1, 4);

  /** @param geodesicInterface
   * @return three-point scheme Hormann/Sabin 2008 */
  public static CurveSubdivision hormannSabin(GeodesicInterface geodesicInterface) {
    return new ThreePointCurveSubdivision(geodesicInterface, OMEGA);
  }

  // ---
  private final GeodesicInterface geodesicInterface;
  private final Scalar pq_f;
  private final Scalar qr_f;

  public ThreePointCurveSubdivision(GeodesicInterface geodesicInterface, Scalar omega) {
    this.geodesicInterface = geodesicInterface;
    pq_f = RationalScalar.HALF.add(RealScalar.of(6).multiply(omega));
    qr_f = RealScalar.of(6).multiply(omega).negate();
  }

  @Override // from CurveSubdivision
  public Tensor cyclic(Tensor tensor) {
    ScalarQ.thenThrow(tensor);
    Tensor curve = Tensors.empty();
    for (int index = 0; index < tensor.length(); ++index) {
      Tensor p = tensor.get((index - 1 + tensor.length()) % tensor.length());
      Tensor q = tensor.get(index);
      Tensor r = tensor.get((index + 1) % tensor.length());
      curve.append(lo(p, q, r)).append(lo(r, q, p));
    }
    return curve;
  }

  @Override // from CurveSubdivision
  public Tensor string(Tensor tensor) {
    ScalarQ.thenThrow(tensor);
    switch (tensor.length()) {
    case 0:
    case 1:
      return tensor.copy();
    }
    // ---
    Tensor curve = Tensors.empty();
    {
      Tensor p = tensor.get(0);
      Tensor q = tensor.get(1);
      curve.append(lo(p, q)); // TODO there should be a better formula here for BSpline4
    }
    int last = tensor.length() - 1;
    for (int index = 1; index < last; ++index) {
      Tensor p = tensor.get(index - 1);
      Tensor q = tensor.get(index);
      Tensor r = tensor.get(index + 1);
      curve.append(lo(p, q, r)).append(lo(r, q, p));
    }
    {
      Tensor p = tensor.get(last - 1);
      Tensor q = tensor.get(last);
      curve.append(lo(q, p));
    }
    return curve;
  }

  // point between p and q but more towards q
  private Tensor lo(Tensor p, Tensor q, Tensor r) {
    Tensor pq = geodesicInterface.split(p, q, pq_f);
    Tensor qr = geodesicInterface.split(q, r, qr_f);
    return geodesicInterface.split(pq, qr, RationalScalar.HALF);
  }

  // point between p and q but more towards p
  private Tensor lo(Tensor p, Tensor q) {
    return geodesicInterface.split(p, q, _1_4);
  }
}
