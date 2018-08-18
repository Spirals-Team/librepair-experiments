// code by jph
package ch.ethz.idsc.gokart.core.fuse;

import java.io.Serializable;

import ch.ethz.idsc.gokart.core.perc.SimpleSpacialObstaclePredicate;
import ch.ethz.idsc.gokart.core.perc.SpacialXZObstaclePredicate;
import ch.ethz.idsc.gokart.gui.GokartStatusEvent;
import ch.ethz.idsc.gokart.gui.top.ChassisGeometry;
import ch.ethz.idsc.gokart.gui.top.SensorsConfig;
import ch.ethz.idsc.owl.car.math.CircleClearanceTracker;
import ch.ethz.idsc.owl.car.math.ClearanceTracker;
import ch.ethz.idsc.owl.car.math.EmptyClearanceTracker;
import ch.ethz.idsc.retina.dev.steer.SteerConfig;
import ch.ethz.idsc.retina.sys.AppResources;
import ch.ethz.idsc.retina.util.math.Magnitude;
import ch.ethz.idsc.retina.util.math.SI;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Clip;

/**  */
public class SafetyConfig implements Serializable {
  public static final SafetyConfig GLOBAL = AppResources.load(new SafetyConfig());
  /***************************************************/
  public Scalar clearance_XLo = Quantity.of(0.2, SI.METER);
  /** obstacles on path within clearance range may cause
   * gokart to deactivate motor torque
   * 20171218: changed from 3.3[m] to 4.3[m]
   * 20180607: changed from 4.3[m] to 7.0[m]
   * @see Vlp16ClearanceModule */
  public Scalar clearance_XHi = Quantity.of(7.0, SI.METER);
  /** 20180226: changed from -1.0[m] to -0.9[m] because the sensor rack was lowered by ~8[cm] */
  public Scalar vlp16_ZLo = Quantity.of(-0.9, SI.METER);
  public Scalar vlp16_ZHi = Quantity.of(+0.1, SI.METER);

  /***************************************************/
  /** @return */
  public Clip vlp16_ZClip() {
    return Clip.function( //
        Magnitude.METER.apply(vlp16_ZLo), //
        Magnitude.METER.apply(vlp16_ZHi));
  }

  /** @return */
  public Clip getClearanceClip() {
    return Clip.function( //
        Magnitude.METER.apply(clearance_XLo), //
        Magnitude.METER.apply(clearance_XHi));
  }

  /** @param speed
   * @param gokartStatusEvent non-null
   * @return */
  public ClearanceTracker getClearanceTracker(Scalar speed, GokartStatusEvent gokartStatusEvent) {
    if (gokartStatusEvent.isSteerColumnCalibrated()) {
      Scalar angle = SteerConfig.GLOBAL.getAngleFromSCE(gokartStatusEvent);
      Scalar half = ChassisGeometry.GLOBAL.yHalfWidthMeter();
      return new CircleClearanceTracker(speed, half, angle, SensorsConfig.GLOBAL.vlp16, getClearanceClip());
    }
    return EmptyClearanceTracker.INSTANCE;
  }

  /** convenient way for the application layer to obtain an instance
   * without having to specify the geometric configuration
   * 
   * @return */
  public SpacialXZObstaclePredicate createSpacialXZObstaclePredicate() {
    return new SimpleSpacialObstaclePredicate(vlp16_ZClip(), SensorsConfig.GLOBAL.vlp16_incline);
  }
}
