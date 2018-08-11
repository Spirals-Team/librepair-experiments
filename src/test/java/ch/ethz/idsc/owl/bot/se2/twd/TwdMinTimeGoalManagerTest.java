// code by jph
package ch.ethz.idsc.owl.bot.se2.twd;

import java.util.Collection;

import ch.ethz.idsc.owl.bot.se2.Se2ComboRegion;
import ch.ethz.idsc.owl.bot.se2.Se2MinTimeGoalManager;
import ch.ethz.idsc.owl.glc.core.HeuristicQ;
import ch.ethz.idsc.owl.math.flow.Flow;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class TwdMinTimeGoalManagerTest extends TestCase {
  public void testSimple() {
    TwdDuckieFlows twdConfig = new TwdDuckieFlows(RealScalar.of(1), RealScalar.of(1));
    Collection<Flow> controls = twdConfig.getFlows(8);
    Se2ComboRegion se2ComboRegion = Se2ComboRegion.spherical(Tensors.vector(10, 0, Math.PI), Tensors.vector(1, 1, 1));
    Se2MinTimeGoalManager se2MinTimeGoalManager = new Se2MinTimeGoalManager(se2ComboRegion, controls);
    assertTrue(HeuristicQ.of(se2MinTimeGoalManager));
    Scalar cost = se2MinTimeGoalManager.minCostToGoal(Tensors.vector(0, 0, 0));
    assertTrue(Scalars.lessEquals(RealScalar.of(9), cost));
    assertTrue(se2MinTimeGoalManager.isMember(Tensors.vector(10, 0, Math.PI + 0.9)));
    assertFalse(se2MinTimeGoalManager.isMember(Tensors.vector(10, 0, Math.PI + 1.1)));
    assertTrue(se2MinTimeGoalManager.isMember(Tensors.vector(10, 0, Math.PI + 2 * Math.PI + 0.9)));
    assertFalse(se2MinTimeGoalManager.isMember(Tensors.vector(10, 0, Math.PI + 2 * Math.PI + 1.1)));
  }

  public void testAllAngles() {
    TwdDuckieFlows twdConfig = new TwdDuckieFlows(RealScalar.of(1), RealScalar.of(1));
    Collection<Flow> controls = twdConfig.getFlows(8);
    Se2ComboRegion se2ComboRegion = Se2ComboRegion.spherical(Tensors.vector(0, 0, Math.PI), Tensors.vector(1, 1, 4));
    Se2MinTimeGoalManager se2MinTimeGoalManager = new Se2MinTimeGoalManager(se2ComboRegion, controls);
    for (int index = -100; index < 100; ++index) {
      assertTrue(se2MinTimeGoalManager.isMember(Tensors.vector(0, 0, index)));
      assertFalse(se2MinTimeGoalManager.isMember(Tensors.vector(2, 0, index)));
    }
  }
}
