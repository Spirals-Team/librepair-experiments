// code by jph
package ch.ethz.idsc.owl.subdiv.curve;

import ch.ethz.idsc.owl.math.map.Se2GroupAction;
import ch.ethz.idsc.owl.math.map.Se2Integrator;
import ch.ethz.idsc.owl.math.map.Se2Utils;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Mod;

public enum Se2Geodesic implements GeodesicInterface {
  INSTANCE;
  // ---
  private static final int INDEX_ANGLE = 2;
  private static final Mod MOD_DISTANCE = Mod.function(Math.PI * 2, -Math.PI);

  @Override // from GeodesicInterface
  public Tensor split(Tensor p, Tensor q, Scalar scalar) {
    Tensor p_inv = new Se2GroupAction(p).inverse();
    Tensor delta = new Se2GroupAction(p_inv).circ(q);
    delta.set(MOD_DISTANCE, INDEX_ANGLE);
    Tensor x = Se2Utils.log(delta).multiply(scalar);
    return Se2Integrator.INSTANCE.spin(p, x);
  }
}
