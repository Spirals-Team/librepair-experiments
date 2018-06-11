// code by ynager
package ch.ethz.idsc.owl.bot.se2.glc;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import ch.ethz.idsc.owl.bot.se2.Se2ComboRegion;
import ch.ethz.idsc.owl.bot.se2.Se2TimeCost;
import ch.ethz.idsc.owl.glc.adapter.VectorCostGoalAdapter;
import ch.ethz.idsc.owl.glc.core.CostFunction;
import ch.ethz.idsc.owl.glc.core.GoalInterface;
import ch.ethz.idsc.owl.glc.core.TrajectoryPlanner;
import ch.ethz.idsc.owl.glc.std.LexicographicGlcRelabelDecision;
import ch.ethz.idsc.owl.glc.std.PlannerConstraint;
import ch.ethz.idsc.owl.glc.std.StandardTrajectoryPlanner;
import ch.ethz.idsc.owl.math.StateTimeTensorFunction;
import ch.ethz.idsc.owl.math.region.So2Region;
import ch.ethz.idsc.owl.math.state.StateTime;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.io.ResourceData;

public class GokartVecEntity extends GokartEntity {
  public float radius = 10;

  public GokartVecEntity(StateTime stateTime) {
    super(stateTime);
  }

  @Override
  public TrajectoryPlanner createTrajectoryPlanner(PlannerConstraint plannerConstraint, Tensor goal) {
    goalRegion = getGoalRegionWithDistance(goal);
    Se2ComboRegion se2ComboRegion = new Se2ComboRegion(goalRegion, new So2Region(goal.Get(2), goalRadius.Get(2)));
    //  ---
    // costs with higher priority come first
    // TODO: add costs / slack from within demo
    List<CostFunction> costs = new ArrayList<>();
    Tensor waypoints = ResourceData.of("/demo/dubendorf/hangar/20180425waypoints.csv");
    // magic constants specific for track
    costs.add(new WaypointDistanceCost(waypoints, Tensors.vector(85.33, 85.33), radius, new Dimension(640, 640)));
    costs.add(Se2TimeCost.of(se2ComboRegion, controls));
    // ---
    GoalInterface goalInterface = //
        new VectorCostGoalAdapter(costs, se2ComboRegion, controls);
    TrajectoryPlanner trajectoryPlanner = new StandardTrajectoryPlanner( //
        eta(), FIXEDSTATEINTEGRATOR, controls, plannerConstraint, goalInterface);
    trajectoryPlanner.represent = StateTimeTensorFunction.state(SE2WRAP::represent);
    //  ---
    Tensor slack = Array.zeros(costs.size()); // slack equal to zero for now
    trajectoryPlanner.relabelDecision = new LexicographicGlcRelabelDecision(slack);
    // ---
    return trajectoryPlanner;
  }
}
