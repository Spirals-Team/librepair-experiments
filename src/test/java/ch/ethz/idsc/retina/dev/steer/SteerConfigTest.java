// code by jph
package ch.ethz.idsc.retina.dev.steer;

import ch.ethz.idsc.gokart.gui.top.ChassisGeometry;
import ch.ethz.idsc.retina.util.math.Magnitude;
import ch.ethz.idsc.retina.util.math.SI;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.QuantityMagnitude;
import ch.ethz.idsc.tensor.qty.Unit;
import ch.ethz.idsc.tensor.qty.UnitSystem;
import ch.ethz.idsc.tensor.qty.Units;
import ch.ethz.idsc.tensor.sca.Clip;
import junit.framework.TestCase;

public class SteerConfigTest extends TestCase {
  public void testSimple() {
    Scalar q = Quantity.of(2, "km*NOU");
    Scalar r = QuantityMagnitude.SI().in(Unit.of("m*NOU")).apply(q);
    assertEquals(r, RealScalar.of(2000));
  }

  public void testSCE() {
    assertEquals(Units.of(SteerConfig.GLOBAL.columnMax), Unit.of("SCE"));
  }

  public void testSCEfromAngle() {
    Scalar q = SteerConfig.GLOBAL.getSCEfromAngle(Quantity.of(1, "rad"));
    assertEquals(Units.of(q), Unit.of("SCE"));
    assertTrue(1.1 < q.number().doubleValue());
  }

  public void testAngleLimit() {
    Clip clip = SteerConfig.GLOBAL.getAngleLimit();
    assertEquals(clip.min(), clip.max().negate());
  }

  public void testConversion() {
    Scalar radius = UnitSystem.SI().apply(SteerConfig.GLOBAL.turningRatioMax.reciprocal());
    Clip clip = Clip.function(Quantity.of(2.4, SI.METER), Quantity.of(2.5, SI.METER));
    assertTrue(clip.isInside(radius));
  }

  public void testTurningAtLimit() {
    // according to our model
    Scalar ratio_unitless = Magnitude.PER_METER.apply(SteerConfig.GLOBAL.turningRatioMax);
    Scalar angle = ChassisGeometry.GLOBAL.steerAngleForTurningRatio(ratio_unitless);
    // angle == 0.4521892315592385[rad]
    Scalar encoder = SteerConfig.GLOBAL.getSCEfromAngle(angle);
    // encoder == 0.7536487192653976[SCE]
    // our simple, linear steering model tells us an encoder value outside the max range
    // conclusion: we should build a more accurate model that maps [encoder <-> effective steering angle]
    Clip clip = Clip.function(Quantity.of(0.5, SteerPutEvent.UNIT_ENCODER), Quantity.of(0.8, SteerPutEvent.UNIT_ENCODER));
    assertTrue(clip.isInside(encoder));
  }
}
