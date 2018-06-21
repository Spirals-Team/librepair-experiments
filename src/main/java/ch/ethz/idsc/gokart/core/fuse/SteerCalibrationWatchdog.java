// code by jph
package ch.ethz.idsc.gokart.core.fuse;

import java.util.Optional;

import ch.ethz.idsc.retina.dev.rimo.RimoPutEvent;
import ch.ethz.idsc.retina.dev.rimo.RimoSocket;
import ch.ethz.idsc.retina.dev.steer.SteerColumnTracker;
import ch.ethz.idsc.retina.dev.steer.SteerSocket;
import ch.ethz.idsc.retina.sys.SafetyCritical;

/** sends stop command if steer angle is not calibrated or steer angle tracking is unhealthy */
@SafetyCritical
public final class SteerCalibrationWatchdog extends EmergencyModule<RimoPutEvent> {
  @Override // from AbstractModule
  protected void first() throws Exception {
    RimoSocket.INSTANCE.addPutProvider(this);
  }

  @Override // from AbstractModule
  protected void last() {
    RimoSocket.INSTANCE.removePutProvider(this);
  }

  /***************************************************/
  @Override // from RimoPutProvider
  public Optional<RimoPutEvent> putEvent() {
    return create(SteerSocket.INSTANCE.getSteerColumnTracker());
  }

  static Optional<RimoPutEvent> create(SteerColumnTracker steerColumnTracker) {
    boolean isOperational = steerColumnTracker.isCalibratedAndHealthy();
    return Optional.ofNullable(isOperational ? null : RimoPutEvent.PASSIVE); // deactivate throttle
  }
}
