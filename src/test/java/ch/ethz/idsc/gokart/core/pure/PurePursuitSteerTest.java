// code by jph
package ch.ethz.idsc.gokart.core.pure;

import java.util.Optional;

import ch.ethz.idsc.owl.math.state.ProviderRank;
import ch.ethz.idsc.retina.dev.steer.SteerColumnAdapter;
import ch.ethz.idsc.retina.dev.steer.SteerPutEvent;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class PurePursuitSteerTest extends TestCase {
  public void testRanks() {
    assertEquals(new PurePursuitSteer().getProviderRank(), ProviderRank.AUTONOMOUS);
    assertEquals(new PurePursuitRimo().getProviderRank(), ProviderRank.AUTONOMOUS);
  }

  public void testControl() {
    PurePursuitSteer pps = new PurePursuitSteer();
    Optional<SteerPutEvent> optional;
    optional = pps.private_putEvent(new SteerColumnAdapter(false, Quantity.of(0.3, "SCE")));
    assertFalse(optional.isPresent());
    optional = pps.control(new SteerColumnAdapter(true, Quantity.of(0.3, "SCE")));
    assertTrue(optional.isPresent());
    pps.setHeading(Quantity.of(-0.2, "rad"));
    pps.control(new SteerColumnAdapter(true, Quantity.of(0.2, "SCE")));
    pps.setHeading(Quantity.of(-0.1, "rad"));
    pps.control(new SteerColumnAdapter(true, Quantity.of(0.1, "SCE")));
  }

  public void testNotCalibrated2() {
    PurePursuitSteer pps = new PurePursuitSteer();
    pps.setOperational(true);
    assertFalse(pps.putEvent().isPresent());
    Optional<SteerPutEvent> optional;
    optional = pps.private_putEvent(new SteerColumnAdapter(false, Quantity.of(0.3, "SCE")));
    assertFalse(optional.isPresent());
  }
}
