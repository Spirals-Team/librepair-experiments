// code by jph
package ch.ethz.idsc.owl.bot.lv;

import java.util.Collection;

import ch.ethz.idsc.owl.bot.util.DemoInterface;
import ch.ethz.idsc.owl.glc.adapter.EmptyObstacleConstraint;
import ch.ethz.idsc.owl.gui.ani.TrajectoryEntity;
import ch.ethz.idsc.owl.gui.ren.VectorFieldRender;
import ch.ethz.idsc.owl.gui.win.MouseGoal;
import ch.ethz.idsc.owl.gui.win.OwlyAnimationFrame;
import ch.ethz.idsc.owl.math.StateSpaceModel;
import ch.ethz.idsc.owl.math.VectorFields;
import ch.ethz.idsc.owl.math.flow.Flow;
import ch.ethz.idsc.owl.math.flow.Integrator;
import ch.ethz.idsc.owl.math.flow.RungeKutta45Integrator;
import ch.ethz.idsc.owl.math.sample.BoxRandomSample;
import ch.ethz.idsc.owl.math.sample.RandomSample;
import ch.ethz.idsc.owl.math.sample.RandomSampleInterface;
import ch.ethz.idsc.owl.math.state.EpisodeIntegrator;
import ch.ethz.idsc.owl.math.state.EuclideanTrajectoryControl;
import ch.ethz.idsc.owl.math.state.SimpleEpisodeIntegrator;
import ch.ethz.idsc.owl.math.state.StateTime;
import ch.ethz.idsc.owl.math.state.TrajectoryControl;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;

public class LvAnimationDemo implements DemoInterface {
  @Override
  public OwlyAnimationFrame start() {
    OwlyAnimationFrame owlyAnimationFrame = new OwlyAnimationFrame();
    StateSpaceModel stateSpaceModel = LvStateSpaceModel.of(1, 2);
    Collection<Flow> controls = LvControls.create(stateSpaceModel, 2);
    Integrator INTEGRATOR = RungeKutta45Integrator.INSTANCE;
    EpisodeIntegrator episodeIntegrator = new SimpleEpisodeIntegrator(stateSpaceModel, INTEGRATOR, //
        new StateTime(Tensors.vector(2, 0.3), RealScalar.ZERO));
    TrajectoryControl trajectoryControl = EuclideanTrajectoryControl.INSTANCE;
    TrajectoryEntity trajectoryEntity = new LvEntity(episodeIntegrator, trajectoryControl, controls);
    owlyAnimationFrame.add(trajectoryEntity);
    MouseGoal.simple(owlyAnimationFrame, trajectoryEntity, EmptyObstacleConstraint.INSTANCE);
    // ---
    Tensor range = Tensors.vector(6, 5);
    VectorFieldRender vectorFieldRender = new VectorFieldRender();
    RandomSampleInterface sampler = new BoxRandomSample(Tensors.vector(0, 0), range);
    Tensor points = Tensor.of(RandomSample.of(sampler, 1000).stream());
    vectorFieldRender.uv_pairs = //
        VectorFields.of(stateSpaceModel, points, Array.zeros(1), RealScalar.of(0.04));
    owlyAnimationFrame.addBackground(vectorFieldRender);
    // ---
    return owlyAnimationFrame;
  }

  public static void main(String[] args) {
    new LvAnimationDemo().start().jFrame.setVisible(true);
  }
}
