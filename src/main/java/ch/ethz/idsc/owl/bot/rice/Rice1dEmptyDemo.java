// code by jph
package ch.ethz.idsc.owl.bot.rice;

import java.util.Collection;

import ch.ethz.idsc.owl.bot.util.DemoInterface;
import ch.ethz.idsc.owl.glc.adapter.EmptyObstacleConstraint;
import ch.ethz.idsc.owl.gui.ani.TrajectoryEntity;
import ch.ethz.idsc.owl.gui.win.MouseGoal;
import ch.ethz.idsc.owl.gui.win.OwlyAnimationFrame;
import ch.ethz.idsc.owl.math.flow.Flow;
import ch.ethz.idsc.owl.math.state.EuclideanTrajectoryControl;
import ch.ethz.idsc.owl.math.state.TrajectoryControl;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;

public class Rice1dEmptyDemo implements DemoInterface {
  @Override
  public OwlyAnimationFrame start() {
    OwlyAnimationFrame owlyAnimationFrame = new OwlyAnimationFrame();
    Scalar mu = RealScalar.ZERO;
    Collection<Flow> controls = Rice2Controls.create1d(mu, 15);
    TrajectoryControl trajectoryControl = EuclideanTrajectoryControl.INSTANCE;
    TrajectoryEntity trajectoryEntity = //
        new Rice1dEntity(mu, Tensors.vector(0, 0), trajectoryControl, controls);
    owlyAnimationFrame.add(trajectoryEntity);
    MouseGoal.simple(owlyAnimationFrame, trajectoryEntity, EmptyObstacleConstraint.INSTANCE);
    return owlyAnimationFrame;
  }

  public static void main(String[] args) {
    new Rice1dEmptyDemo().start().jFrame.setVisible(true);
  }
}
