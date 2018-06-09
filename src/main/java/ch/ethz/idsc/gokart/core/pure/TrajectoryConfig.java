// code by ynager
package ch.ethz.idsc.gokart.core.pure;

import java.io.Serializable;

import ch.ethz.idsc.retina.sys.AppResources;
import ch.ethz.idsc.retina.util.math.SI;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.io.ResourceData;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Ramp;

public class TrajectoryConfig implements Serializable {
  public static final TrajectoryConfig GLOBAL = AppResources.load(new TrajectoryConfig());
  /***************************************************/
  public Scalar planningPeriod = Quantity.of(1, SI.SECOND); // 1[s] == 1[Hz]
  public Scalar planningOffset = Quantity.of(2.5, SI.METER);
  /** horizonDistance is unit-less because it entails all three: x, y, heading using Se2Wrap */
  public Scalar horizonDistance = RealScalar.of(8);
  /** number of different steering angles for path planning */
  public Scalar controlResolution = RealScalar.of(9);
  /** rotation per meter driven is at least 23[deg/m]
   * 20180429_minimum_turning_radius.pdf
   * 20180517 reduced value to 20[deg/m] to be more conservative and avoid extreme steering */
  public Scalar maxRotation = Quantity.of(20, "deg*m^-1");
  /** half angle of conic goal region */
  public Scalar coneHalfAngle = RealScalar.of(Math.PI / 10);
  // public static final Tensor WAYPOINTS = //

  /***************************************************/
  /** @param tangentSpeed with unit "m*s^-1"
   * @return non-negative */
  public Scalar getCutoffDistance(Scalar tangentSpeed) {
    return Ramp.FUNCTION.apply(tangentSpeed) //
        .multiply(planningPeriod) // for instance 1[s]
        .add(planningOffset); // for instance 2.5[m]
  }

  public Tensor getWaypoints() {
    // ResourceData.of("/demo/dubendorf/hangar/20180425waypoints.csv").unmodifiable();
    return ResourceData.of("/map/dubendorf/hangar/20180604waypoints.csv").unmodifiable();
  }
}
