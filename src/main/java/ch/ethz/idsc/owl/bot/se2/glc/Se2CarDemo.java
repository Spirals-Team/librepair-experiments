// code by jph
package ch.ethz.idsc.owl.bot.se2.glc;

import ch.ethz.idsc.owl.bot.se2.Se2PointsVsRegions;
import ch.ethz.idsc.owl.glc.adapter.RegionConstraints;
import ch.ethz.idsc.owl.glc.core.PlannerConstraint;
import ch.ethz.idsc.owl.math.region.Region;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/* package */ abstract class Se2CarDemo extends Se2Demo {
  // TODO these values are specific for small car
  private static final Tensor PROBE_X = Tensors.vector(0.2, 0.1, 0, -0.1);

  static Region<Tensor> line(Region<Tensor> region) {
    return Se2PointsVsRegions.line(PROBE_X, region);
  }

  static PlannerConstraint createConstraint(Region<Tensor> region) {
    return RegionConstraints.timeInvariant(line(region));
  }
}
