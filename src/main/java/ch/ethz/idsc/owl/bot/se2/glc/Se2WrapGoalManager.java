// code by jph
package ch.ethz.idsc.owl.bot.se2.glc;

import java.io.Serializable;
import java.util.List;

import ch.ethz.idsc.owl.bot.se2.Se2Wrap;
import ch.ethz.idsc.owl.glc.adapter.CatchyTrajectoryRegionQuery;
import ch.ethz.idsc.owl.glc.adapter.GoalAdapter;
import ch.ethz.idsc.owl.glc.adapter.StateTimeTrajectories;
import ch.ethz.idsc.owl.glc.core.CostFunction;
import ch.ethz.idsc.owl.glc.core.GlcNode;
import ch.ethz.idsc.owl.glc.core.GoalInterface;
import ch.ethz.idsc.owl.math.TensorMetric;
import ch.ethz.idsc.owl.math.flow.Flow;
import ch.ethz.idsc.owl.math.region.Region;
import ch.ethz.idsc.owl.math.state.StateTime;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Ramp;

/** minimizes driving time (=distance, since unit speed)
 * 
 * {@link Se2WrapGoalManager} works with {@link Se2Wrap} as well as with {@link TnIdentityWrap} */
/* package */ class Se2WrapGoalManager implements Region<Tensor>, CostFunction, Serializable {
  private final TensorMetric tensorMetric;
  private final Tensor center;
  private final Scalar radius;

  /** @param tensorMetric
   * @param center consists of x,y,theta
   * @param radius */
  public Se2WrapGoalManager(TensorMetric tensorMetric, Tensor center, Scalar radius) {
    this.tensorMetric = tensorMetric;
    this.center = center;
    this.radius = radius;
  }

  @Override
  public Scalar costIncrement(GlcNode glcNode, List<StateTime> trajectory, Flow flow) {
    return StateTimeTrajectories.timeIncrement(glcNode, trajectory);
  }

  @Override
  public Scalar minCostToGoal(Tensor x) {
    return Ramp.of(tensorMetric.distance(x, center).subtract(radius));
  }

  @Override
  public boolean isMember(Tensor x) {
    return Scalars.isZero(minCostToGoal(x));
  }

  public GoalInterface getGoalInterface() {
    return new GoalAdapter(CatchyTrajectoryRegionQuery.timeInvariant(this), this);
  }
}