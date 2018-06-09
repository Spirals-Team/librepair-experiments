// code by jph
package ch.ethz.idsc.owl.bot.se2.glc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ch.ethz.idsc.owl.bot.util.FlowsInterface;
import ch.ethz.idsc.owl.math.flow.Flow;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.alg.VectorQ;

public class CarFlows implements FlowsInterface, Serializable {
  /** @param speed with unit [m*s^-1]
   * @param rate_max with unit [rad*m^-1], i.e. the amount of rotation [rad] performed per distance [m^-1]
   * @return */
  public static FlowsInterface standard(Scalar speed, Scalar rate_max) {
    return new CarFlows(Tensors.of(speed, speed.negate()), rate_max);
  }

  /** @param speed with unit [m*s^-1]
   * @param rate_max with unit [rad*m^-1], i.e. the amount of rotation [rad] performed per distance [m^-1]
   * @return */
  public static FlowsInterface forward(Scalar speed, Scalar rate_max) {
    return new CarFlows(Tensors.of(speed), rate_max);
  }

  /** @param speeds vector with unit [m*s^-1]
   * @param rate_max with unit [rad*m^-1], i.e. the amount of rotation [rad] performed per distance [m^-1]
   * @return */
  public static FlowsInterface of(Tensor speeds, Scalar rate_max) {
    return new CarFlows(speeds, rate_max);
  }
  // ---

  private final Tensor speeds;
  private final Scalar rate_max;

  private CarFlows(Tensor speeds, Scalar rate_max) {
    this.speeds = VectorQ.require(speeds);
    this.rate_max = rate_max;
  }

  @Override // from FlowsInterface
  public Collection<Flow> getFlows(int resolution) {
    if (resolution % 2 == 1)
      ++resolution;
    List<Flow> list = new ArrayList<>();
    for (Tensor angle : Subdivide.of(rate_max.negate(), rate_max, resolution))
      for (Tensor speed : speeds)
        list.add(CarHelper.singleton(speed.Get(), angle));
    return Collections.unmodifiableList(list);
  }
}
