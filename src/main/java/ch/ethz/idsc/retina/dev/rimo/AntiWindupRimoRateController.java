// concept and code by az
package ch.ethz.idsc.retina.dev.rimo;

import ch.ethz.idsc.gokart.gui.GokartLcmChannel;
import ch.ethz.idsc.retina.lcm.BinaryBlobPublisher;
import ch.ethz.idsc.retina.lcm.VectorFloatBlob;
import ch.ethz.idsc.retina.util.math.NonSI;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;

/** two instance of this class are used for left and right rear motors
 * @see RimoRateControllerDuo
 * 
 * Kp with unit "ARMS*rad^-1*s"
 * Ki with unit "ARMS*rad^-1"
 * Kawu with unit "ARMS*rad^-1" */
/* package */ class AntiWindupRimoRateController implements RimoRateController {
  static final Scalar DT = RimoSocket.INSTANCE.getPutPeriod();
  // ---
  private final BinaryBlobPublisher binaryBlobPublisher = new BinaryBlobPublisher(GokartLcmChannel.RIMO_CONTROLLER_AW);
  // ---
  /** pos error initially incorrect in the first iteration */
  private Scalar lastWindUp = Quantity.of(0, NonSI.ARMS); // unit "ARMS"
  private Scalar lastIPart = Quantity.of(0, NonSI.ARMS);

  @Override // from RimoRateController
  public Scalar iterate(final Scalar vel_error) {
    final Scalar pPart = vel_error.multiply(RimoConfig.GLOBAL.Kp);
    // System.out.println("pPart=" + pPart);
    final Scalar iPart = vel_error.multiply(RimoConfig.GLOBAL.Ki).multiply(DT).add(lastIPart);
    // System.out.println("iPart=" + iPart);
    // System.out.println("lastW=" + lastWindUp);
    final Scalar TEMP_LWU = lastWindUp.multiply(RimoConfig.GLOBAL.Kawu);
    // System.out.println("teLwu=" + TEMP_LWU);
    final Scalar tor_value = pPart.add(iPart).add(TEMP_LWU);
    // System.out.println(tor_value);
    final Scalar satTor_value = RimoConfig.GLOBAL.torqueLimitClip().apply(tor_value); // actuator-saturation
    // update integral and anti-wind-up reset
    lastIPart = iPart;
    lastWindUp = satTor_value.subtract(tor_value);
    // TODO preliminary for debugging: publish ctrl internals
    binaryBlobPublisher.accept(VectorFloatBlob.encode(Tensors.of( //
        vel_error, pPart, iPart, TEMP_LWU, satTor_value)));
    return satTor_value;
  }
}
