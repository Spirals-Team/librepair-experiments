// code by jph
package ch.ethz.idsc.owl.subdiv.curve;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.opt.ScalarTensorFunction;

public class BezierCurve {
  private final GeodesicInterface geodesicInterface;

  public BezierCurve(GeodesicInterface geodesicInterface) {
    this.geodesicInterface = geodesicInterface;
  }

  public ScalarTensorFunction evaluation(Tensor points) {
    return new DeCasteljau(geodesicInterface, points);
  }

  public Tensor refine(Tensor points, int number) {
    return Subdivide.of(0, 1, number).map(evaluation(points));
  }
}
