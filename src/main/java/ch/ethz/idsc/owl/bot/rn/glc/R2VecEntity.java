// code by jph
package ch.ethz.idsc.owl.bot.rn.glc;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import ch.ethz.idsc.owl.bot.rn.RnMinTimeGoalManager;
import ch.ethz.idsc.owl.glc.adapter.VectorCostGoalAdapter;
import ch.ethz.idsc.owl.glc.core.CostFunction;
import ch.ethz.idsc.owl.glc.core.GoalInterface;
import ch.ethz.idsc.owl.glc.core.TrajectoryPlanner;
import ch.ethz.idsc.owl.glc.std.LexicographicRelabelDecision;
import ch.ethz.idsc.owl.glc.std.PlannerConstraint;
import ch.ethz.idsc.owl.glc.std.StandardTrajectoryPlanner;
import ch.ethz.idsc.owl.gui.win.GeometricLayer;
import ch.ethz.idsc.owl.math.flow.Flow;
import ch.ethz.idsc.owl.math.state.EpisodeIntegrator;
import ch.ethz.idsc.owl.math.state.TrajectoryControl;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;

public class R2VecEntity extends R2Entity {
  public R2VecEntity(EpisodeIntegrator episodeIntegrator, TrajectoryControl trajectoryControl) {
    super(episodeIntegrator, trajectoryControl);
  }

  @Override
  public final TrajectoryPlanner createTrajectoryPlanner(PlannerConstraint plannerConstraint, Tensor goal) {
    System.out.println("goal=" + goal);
    Collection<Flow> controls = createControls(); // TODO design no good
    goalRegion = getGoalRegionWithDistance(goal);
    GoalInterface minTimeGoal = RnMinTimeGoalManager.create(goalRegion, controls); //
    List<CostFunction> costs = new ArrayList<>();
    getPrimaryCost().map(costs::add);
    costs.add(minTimeGoal);
    GoalInterface goalInterface = new VectorCostGoalAdapter(costs, goalRegion);
    StandardTrajectoryPlanner trajectoryPlanner = new StandardTrajectoryPlanner( //
        stateTimeRaster(), FIXEDSTATEINTEGRATOR, controls, //
        plannerConstraint, goalInterface);
    //  ---
    Tensor slack = Array.zeros(costs.size()); // slack equal to zero for now
    trajectoryPlanner.relabelDecision = new LexicographicRelabelDecision(slack);
    // ---
    return trajectoryPlanner;
  }

  @Override
  public void render(GeometricLayer geometricLayer, Graphics2D graphics) {
    super.render(geometricLayer, graphics);
    // ---
  }

  public Optional<CostFunction> getPrimaryCost() {
    return Optional.empty();
  }
}
