// code by jph
package ch.ethz.idsc.retina.dev.steer;

import java.util.Optional;

import ch.ethz.idsc.owl.math.state.ProviderRank;
import ch.ethz.idsc.retina.dev.misc.MiscGetEvent;
import ch.ethz.idsc.retina.dev.misc.MiscGetListener;
import ch.ethz.idsc.tensor.Scalars;

/** the steering battery is charged from time to time.
 * during charing, the steering motor should be passive.
 * otherwise, the steering battery may overcharge. */
public enum SteerBatteryCharger implements MiscGetListener, SteerPutProvider {
  INSTANCE;
  // ---
  private boolean isCharging = true;

  @Override // from MiscGetListener
  public void getEvent(MiscGetEvent miscGetEvent) {
    isCharging = Scalars.lessThan(SteerConfig.GLOBAL.voltageHi, miscGetEvent.getSteerBatteryVoltage());
  }

  /***************************************************/
  @Override // from SteerPutProvider
  public ProviderRank getProviderRank() {
    return ProviderRank.HARDWARE;
  }

  @Override // from SteerPutProvider
  public Optional<SteerPutEvent> putEvent() {
    return Optional.ofNullable(isCharging ? SteerPutEvent.PASSIVE_MOT_TRQ_0 : null);
  }
}
