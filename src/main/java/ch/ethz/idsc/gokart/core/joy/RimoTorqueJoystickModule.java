// code by jph
package ch.ethz.idsc.gokart.core.joy;

import java.util.Optional;

import ch.ethz.idsc.retina.dev.joystick.GokartJoystickInterface;
import ch.ethz.idsc.retina.dev.rimo.RimoPutEvent;
import ch.ethz.idsc.retina.dev.rimo.RimoPutHelper;
import ch.ethz.idsc.retina.dev.rimo.RimoPutTire;
import ch.ethz.idsc.retina.dev.rimo.RimoSocket;
import ch.ethz.idsc.retina.dev.steer.SteerColumnInterface;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

public class RimoTorqueJoystickModule extends GuideJoystickModule<RimoPutEvent> {
  private static final Scalar HALF = DoubleScalar.of(0.5);

  @Override // from AbstractModule
  void protected_first() {
    RimoSocket.INSTANCE.addPutProvider(this);
  }

  @Override // from AbstractModule
  void protected_last() {
    RimoSocket.INSTANCE.removePutProvider(this);
  }

  /***************************************************/
  @Override // from GuideJoystickModule
  Optional<RimoPutEvent> control( //
      SteerColumnInterface steerColumnInterface, GokartJoystickInterface joystick) {
    Scalar factor = joystick.getAheadAverage(); // [-1, 1]
    Tensor pair = joystick.getAheadPair_Unit(); // entries both in [0, 1]
    pair = pair.map(s -> s.add(factor)).multiply(HALF).multiply(JoystickConfig.GLOBAL.torqueLimit);
    pair = pair.map(RimoPutTire.MAGNITUDE_ARMS); // confim that units are correct
    short armsL_raw = (short) (-pair.Get(0).number().shortValue()); // sign left invert
    short armsR_raw = (short) (+pair.Get(1).number().shortValue()); // sign right id
    return Optional.of(RimoPutHelper.operationTorque(armsL_raw, armsR_raw));
  }
}
