// code by mg
package ch.ethz.idsc.demo.mg.slam;

import ch.ethz.idsc.gokart.core.pos.GokartPoseInterface;
import ch.ethz.idsc.gokart.core.pos.GokartPoseLocal;
import ch.ethz.idsc.gokart.gui.top.ChassisGeometry;
import ch.ethz.idsc.owl.bot.se2.Se2CarIntegrator;
import ch.ethz.idsc.owl.bot.se2.Se2StateSpaceModel;
import ch.ethz.idsc.owl.math.StateSpaceModels;
import ch.ethz.idsc.owl.math.flow.Flow;
import ch.ethz.idsc.retina.dev.rimo.RimoGetEvent;
import ch.ethz.idsc.retina.dev.rimo.RimoGetListener;
import ch.ethz.idsc.retina.dev.rimo.RimoSocket;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.N;

/** odometry is the integration of the wheels speeds to obtain the pose of the go kart
 * due to the high quality of the wheel/motor encoders the odometry turns out to be
 * quite smooth and stable.
 * 
 * <p>Naturally, any tire slip results in a loss of tracking accuracy. */
// DEMO class which provides velocity such that it can be integrated into the SLAM algorithm
// rad 0.14, ytir = 0.65 very good rotation tracking! but speed not accurate
// rad 0.12, ytir = 0.54 good speed tracking, rotation ok
class GokartPoseOdometryDemo implements GokartPoseInterface, RimoGetListener {
  public static GokartPoseOdometryDemo create(Tensor state) {
    return new GokartPoseOdometryDemo(state);
  }

  /** @return with initial pose {0[m], 0[m], 0} */
  public static GokartPoseOdometryDemo create() {
    return create(GokartPoseLocal.INSTANCE.getPose());
  }

  // ---
  private final Scalar dt = RimoSocket.INSTANCE.getGetPeriod(); // 1/250[s] update period
  // ---
  private Tensor state;
  /** velocity is the tangent of the state {vx[m/s], 0[m/s], angular_rate[]} */
  private Tensor velocity;

  private GokartPoseOdometryDemo(Tensor state) {
    this.state = state.copy();
  }

  public void initializePose(Tensor pose) {
    this.state = pose.copy();
  }

  @Override // from RimoGetListener
  public void getEvent(RimoGetEvent rimoGetEvent) {
    step(rimoGetEvent.getAngularRate_Y_pair());
  }

  /** @param angularRate_Y_pair */
  /* package */ synchronized void step(Tensor angularRate_Y_pair) {
    velocity = ChassisGeometry.GLOBAL.odometryVelocity(angularRate_Y_pair);
    Flow flow = GokartPoseOdometryDemo.singleton(velocity);
    state = Se2CarIntegrator.INSTANCE.step(flow, state, dt);
  }

  private static Flow singleton(Tensor velocity) {
    return StateSpaceModels.createFlow(Se2StateSpaceModel.INSTANCE, N.DOUBLE.of(velocity));
  }

  public Tensor getVelocity() {
    return velocity;
  }

  @Override // from GokartPoseInterface
  public Tensor getPose() {
    return state.unmodifiable();
  }
}
