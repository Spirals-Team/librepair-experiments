// code by jph
// adapted from document by Tobias Ewald
package ch.ethz.idsc.owl.subdiv.curve;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** 2005 Malcolm A. Sabin, Neil A. Dodgson:
 * A Circle-Preserving Variant of the Four-Point Subdivision Scheme
 * 
 * reproduces circles
 * 
 * control points are in R^2
 * 
 * subdivision along geodesics in metric spaces other than Euclidean is not defined */
public enum DodgsonSabinCurveSubdivision implements CurveSubdivision {
  INSTANCE;
  // --
  @Override
  public Tensor cyclic(Tensor tensor) {
    Tensor curve = Tensors.empty();
    for (int index = 0; index < tensor.length(); ++index) {
      curve.append(tensor.get(index));
      Tensor a = tensor.get((index - 1 + tensor.length()) % tensor.length());
      Tensor b = tensor.get((index + 0 + tensor.length()) % tensor.length());
      Tensor c = tensor.get((index + 1 + tensor.length()) % tensor.length());
      Tensor d = tensor.get((index + 2 + tensor.length()) % tensor.length());
      curve.append(DodgsonSabinHelper.midpoint(a, b, c, d));
    }
    return curve;
  }

  @Override
  public Tensor string(Tensor tensor) {
    int last = tensor.length() - 1;
    if (last < 2)
      return DodgsonSabinHelper.BSPLINE3_EUCLIDEAN.string(tensor);
    // ---
    Tensor curve = Tensors.empty();
    curve.append(tensor.get(0));
    curve.append(DodgsonSabinHelper.midpoint(tensor.get(0), tensor.get(1), tensor.get(2)));
    // ---
    for (int index = 1; index < last - 1; ++index) {
      curve.append(tensor.get(index));
      curve.append(DodgsonSabinHelper.midpoint( //
          tensor.get(index - 1), //
          tensor.get(index + 0), //
          tensor.get(index + 1), //
          tensor.get(index + 2)));
    }
    curve.append(tensor.get(last - 1));
    curve.append(DodgsonSabinHelper.midpoint(tensor.get(last), tensor.get(last - 1), tensor.get(last - 2)));
    curve.append(tensor.get(last));
    return curve;
  }
}
