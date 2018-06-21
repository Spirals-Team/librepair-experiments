// code by jph
package ch.ethz.idsc.retina.dev.linmot;

import java.util.Optional;

import ch.ethz.idsc.gokart.core.fuse.LinmotEmergencyModule;
import ch.ethz.idsc.owl.math.state.ProviderRank;
import ch.ethz.idsc.retina.util.data.PenaltyCards;
import ch.ethz.idsc.tensor.Scalar;

/** when the winding temperature of the linmot brake surpasses the critical threshold
 * (for instance 110[degC]), the linmot brake is commanded to fallback position until
 * the winding temperature falls below a non-critical threshold, for instance 85[degC].
 * 
 * {@link LinmotEmergencyModule} ensures that for temperatures above the non-critical
 * threshold no acceleration of the motors takes place. */
public enum LinmotFireFighter implements LinmotGetListener, LinmotPutProvider {
  INSTANCE;
  // ---
  private final PenaltyCards penaltyCards = new PenaltyCards();
  private final LinmotPutEvent offMode = LinmotPutOperation.INSTANCE.offMode();

  @Override // from LinmotGetListener
  public void getEvent(LinmotGetEvent linmotGetEvent) {
    Scalar temperature = linmotGetEvent.getWindingTemperatureMax();
    penaltyCards.evaluate( //
        !LinmotConfig.GLOBAL.isTemperatureOperationSafe(temperature), // issue yellow card ?
        !LinmotConfig.GLOBAL.isTemperatureHardwareSafe(temperature)); // issue red card ?
  }

  /***************************************************/
  @Override // from LinmotPutProvider
  public ProviderRank getProviderRank() {
    return ProviderRank.HARDWARE;
  }

  @Override // from LinmotPutProvider
  public Optional<LinmotPutEvent> putEvent() {
    return Optional.ofNullable(penaltyCards.isPenalty() ? offMode : null);
  }
}
