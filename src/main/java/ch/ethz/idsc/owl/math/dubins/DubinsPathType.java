// code by jph
package ch.ethz.idsc.owl.math.dubins;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

public enum DubinsPathType {
  LSR(+1, +0, -1, Steer2TurnsDiffSide.INSTANCE), //
  RSL(-1, +0, +1, Steer2TurnsDiffSide.INSTANCE), //
  LSL(+1, +0, +1, Steer2TurnsSameSide.INSTANCE), //
  RSR(-1, +0, -1, Steer2TurnsSameSide.INSTANCE), //
  LRL(+1, -1, +1, Steer3Turns.INSTANCE), //
  RLR(-1, +1, -1, Steer3Turns.INSTANCE), //
  ;
  // ---
  private final Tensor scaSign;
  private final boolean isFirstTurnRight;
  private final boolean isFirstEqualsLast;
  private final DubinsSteer dubinsSteer;

  private DubinsPathType(int s0s, int s1s, int s2s, DubinsSteer dubinsSteer) {
    scaSign = Tensors.vector(s0s, s1s, s2s).unmodifiable();
    isFirstTurnRight = s0s == -1;
    isFirstEqualsLast = s0s == s2s;
    this.dubinsSteer = dubinsSteer;
  }

  public boolean isFirstTurnRight() {
    return isFirstTurnRight;
  }

  public boolean isFirstEqualsLast() {
    return isFirstEqualsLast;
  }

  public Tensor tangent(int index, Scalar radius) {
    return Tensors.of(RealScalar.ONE, RealScalar.ZERO, scaSign.Get(index).divide(radius));
  }

  public DubinsSteer dubinsSteer() {
    return dubinsSteer;
  }
}
