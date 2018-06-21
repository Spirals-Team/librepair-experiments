// code by jph
package ch.ethz.idsc.retina.dev.rimo;

import java.io.Serializable;

import ch.ethz.idsc.retina.sys.AppResources;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Clip;

/** parameters for PI controller of torque control */
public class RimoConfig implements Serializable {
  public static final RimoConfig GLOBAL = AppResources.load(new RimoConfig());
  /***************************************************/
  /** parameters for {@link RimoRateController}
   * rateLimit, Kp, Ki */
  public Scalar rateLimit = Quantity.of(20, "rad*s^-1"); // <- DEPRECATED
  public Scalar Kp = Quantity.of(40, "ARMS*rad^-1*s"); // 40
  public Scalar Ki = Quantity.of(10, "ARMS*rad^-1"); // 15
  /** the physical maximum torque limit is 2316[ARMS]
   * the torque limit is used in RimoTorqueJoystickModule */
  public Scalar torqueLimit = Quantity.of(1500, "ARMS");

  /***************************************************/
  /** @return clip interval for permitted torque */
  public Clip torqueLimitClip() {
    return Clip.function(torqueLimit.negate(), torqueLimit);
  }
}
