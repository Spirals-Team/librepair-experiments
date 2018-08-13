// code by jph
package ch.ethz.idsc.gokart.gui;

import ch.ethz.idsc.retina.dev.steer.SteerColumnInterface;
import ch.ethz.idsc.retina.dev.steer.SteerSocket;
import ch.ethz.idsc.retina.lcm.BinaryBlobPublisher;
import ch.ethz.idsc.retina.sys.AbstractClockedModule;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;

/** server to publish absolute steering column angle */
public class GokartStatusLcmModule extends AbstractClockedModule {
  /** high rate in order to reconstruct steer angle in post processing */
  private static final Scalar PERIOD = Quantity.of(100, "Hz").reciprocal();
  // ---
  private final SteerColumnInterface steerColumnInterface = SteerSocket.INSTANCE.getSteerColumnTracker();
  private final BinaryBlobPublisher binaryBlobPublisher = new BinaryBlobPublisher(GokartLcmChannel.STATUS);

  @Override // from AbstractClockedModule
  protected void first() throws Exception {
    // ---
  }

  @Override // from AbstractClockedModule
  protected void runAlgo() {
    boolean isCalibrated = steerColumnInterface.isSteerColumnCalibrated();
    float steeringAngle = isCalibrated //
        ? steerColumnInterface.getSteerColumnEncoderCentered().number().floatValue()
        : Float.NaN;
    GokartStatusEvent gokartStatusEvent = new GokartStatusEvent(steeringAngle);
    binaryBlobPublisher.accept(gokartStatusEvent.asArray());
  }

  @Override // from AbstractClockedModule
  protected Scalar getPeriod() {
    return PERIOD;
  }

  @Override // from AbstractClockedModule
  protected void last() {
    // ---
  }
}
