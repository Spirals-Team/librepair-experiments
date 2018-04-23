// code by jph
package ch.ethz.idsc.gokart.core.pure;

import java.util.Optional;

import ch.ethz.idsc.gokart.core.PutProvider;
import ch.ethz.idsc.owl.math.state.ProviderRank;
import ch.ethz.idsc.retina.dev.steer.SteerColumnInterface;
import ch.ethz.idsc.retina.dev.steer.SteerSocket;
import ch.ethz.idsc.retina.util.StartAndStoppable;

/** base class for pure pursuit trajectory following motor and steering control */
abstract class PurePursuitBase<PE> implements StartAndStoppable, PutProvider<PE> {
  private final SteerColumnInterface steerColumnInterface = SteerSocket.INSTANCE.getSteerColumnTracker();
  /** status default false */
  private boolean status = false;

  public final void setOperational(boolean status) {
    this.status = status;
  }

  final boolean private_isOperational() {
    return status;
  }

  @Override // from RimoPutProvider
  public final ProviderRank getProviderRank() {
    return ProviderRank.AUTONOMOUS;
  }

  @Override // from RimoPutProvider
  public final Optional<PE> putEvent() {
    return private_putEvent(steerColumnInterface);
  }

  // function non-private for testing only
  final Optional<PE> private_putEvent(SteerColumnInterface steerColumnInterface) {
    if (private_isOperational() && steerColumnInterface.isSteerColumnCalibrated())
      return control(steerColumnInterface);
    return Optional.empty();
  }

  /** @param steerColumnInterface guaranteed to be calibrated
   * @return */
  abstract Optional<PE> control(SteerColumnInterface steerColumnInterface);
}
