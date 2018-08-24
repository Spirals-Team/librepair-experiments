// code by jph
package ch.ethz.idsc.owl.bot.lv;

import java.util.Collection;

import ch.ethz.idsc.owl.glc.adapter.EtaRaster;
import ch.ethz.idsc.owl.glc.core.GoalInterface;
import ch.ethz.idsc.owl.glc.core.PlannerConstraint;
import ch.ethz.idsc.owl.glc.core.StateTimeRaster;
import ch.ethz.idsc.owl.glc.core.TrajectoryPlanner;
import ch.ethz.idsc.owl.glc.std.StandardTrajectoryPlanner;
import ch.ethz.idsc.owl.gui.ani.AbstractCircularEntity;
import ch.ethz.idsc.owl.math.flow.Flow;
import ch.ethz.idsc.owl.math.flow.Integrator;
import ch.ethz.idsc.owl.math.flow.RungeKutta45Integrator;
import ch.ethz.idsc.owl.math.state.EpisodeIntegrator;
import ch.ethz.idsc.owl.math.state.FallbackControl;
import ch.ethz.idsc.owl.math.state.FixedStateIntegrator;
import ch.ethz.idsc.owl.math.state.TrajectoryControl;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.red.Norm2Squared;

/* package */ class LvEntity extends AbstractCircularEntity {
  private static final Tensor PARTITION_SCALE = Tensors.vector(8, 8).unmodifiable();
  private static final Integrator INTEGRATOR = RungeKutta45Integrator.INSTANCE;
  private static final FixedStateIntegrator FIXED_STATE_INTEGRATOR = //
      FixedStateIntegrator.create(INTEGRATOR, RationalScalar.of(1, 12), 4);
  // ---
  private final Collection<Flow> controls;

  /** @param state initial position of entity */
  public LvEntity(EpisodeIntegrator episodeIntegrator, TrajectoryControl trajectoryControl, Collection<Flow> controls) {
    super(episodeIntegrator, trajectoryControl);
    add(new FallbackControl(Array.zeros(1)));
    this.controls = controls;
  }

  @Override // from TensorMetric
  public final Scalar distance(Tensor x, Tensor y) {
    return Norm2Squared.between(x, y); // non-negative
  }

  @Override
  public Scalar delayHint() {
    return RealScalar.ONE;
  }

  @Override
  public final TrajectoryPlanner createTrajectoryPlanner(PlannerConstraint plannerConstraint, Tensor goal) {
    GoalInterface goalInterface = LvGoalInterface.create(goal.extract(0, 2), Tensors.vector(0.2, 0.2));
    StateTimeRaster stateTimeRaster = EtaRaster.state(PARTITION_SCALE);
    return new StandardTrajectoryPlanner( //
        stateTimeRaster, FIXED_STATE_INTEGRATOR, controls, plannerConstraint, goalInterface);
  }
}
