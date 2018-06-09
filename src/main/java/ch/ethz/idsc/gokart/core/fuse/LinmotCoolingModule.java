// code by jph
package ch.ethz.idsc.gokart.core.fuse;

import java.util.Optional;

import ch.ethz.idsc.retina.dev.linmot.LinmotConfig;
import ch.ethz.idsc.retina.dev.linmot.LinmotGetEvent;
import ch.ethz.idsc.retina.dev.linmot.LinmotGetListener;
import ch.ethz.idsc.retina.dev.linmot.LinmotSocket;
import ch.ethz.idsc.retina.dev.rimo.RimoPutEvent;
import ch.ethz.idsc.retina.dev.rimo.RimoSocket;
import ch.ethz.idsc.tensor.Scalar;

/** linmot winding module does not allow driving
 * when the linmot winding temperature is not operation safe */
public final class LinmotCoolingModule extends EmergencyModule<RimoPutEvent> implements LinmotGetListener {
  /** true, if linmot winding temperature cooling is required
   * during which time gokart should not accelerate further */
  private boolean isCoolingRequired = true;

  @Override // from AbstractModule
  protected void first() throws Exception {
    LinmotSocket.INSTANCE.addGetListener(this);
    RimoSocket.INSTANCE.addPutProvider(this);
  }

  @Override // from AbstractModule
  protected void last() {
    RimoSocket.INSTANCE.removePutProvider(this);
    LinmotSocket.INSTANCE.removeGetListener(this);
  }

  /***************************************************/
  @Override // from LinmotGetListener
  public void getEvent(LinmotGetEvent linmotGetEvent) {
    Scalar temperature = linmotGetEvent.getWindingTemperatureMax();
    isCoolingRequired = !LinmotConfig.GLOBAL.isTemperatureOperationSafe(temperature);
  }

  /***************************************************/
  @Override // from RimoPutProvider
  public Optional<RimoPutEvent> putEvent() {
    return Optional.ofNullable(isCoolingRequired ? RimoPutEvent.PASSIVE : null); // deactivate throttle
  }
}
