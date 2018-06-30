// code by ynager
package ch.ethz.idsc.owl.bot.r2;

import java.awt.Dimension;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.ResourceData;
import junit.framework.TestCase;

public class WaypointDistanceCostTest extends TestCase {
  public void testSimple() {
    Tensor waypoints = ResourceData.of("/dubilab/waypoints/20180425.csv");
    ImageCostFunction wdc = WaypointDistanceCost.linear(waypoints, Tensors.vector(85.33, 85.33), 10.0f, new Dimension(640, 640));
    for (int i = 0; i < waypoints.length(); i++)
      assertEquals(wdc.flipYXTensorInterp.at(waypoints.get(i)), RealScalar.ZERO);
    assertEquals(wdc.flipYXTensorInterp.at(Tensors.vector(10, 10)), RealScalar.ONE);
  }
}
