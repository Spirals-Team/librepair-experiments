// code by jph
package ch.ethz.idsc.owl.subdiv.curve;

import java.util.function.Function;

import ch.ethz.idsc.owl.math.map.LieExponential;
import ch.ethz.idsc.owl.math.map.LieGroupAction;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

public class LieGroupGeodesic implements GeodesicInterface {
  private final Function<Tensor, LieGroupAction> function;
  private final LieExponential lieExponential;

  public LieGroupGeodesic(Function<Tensor, LieGroupAction> function, LieExponential lieExponential) {
    this.function = function;
    this.lieExponential = lieExponential;
  }

  @Override
  public Tensor split(Tensor p, Tensor q, Scalar scalar) {
    LieGroupAction lieGroupAction = function.apply(p);
    Tensor delta = lieGroupAction.inverse().combine(q);
    Tensor x = lieExponential.log(delta).multiply(scalar);
    Tensor m = lieExponential.exp(x);
    return lieGroupAction.combine(m);
  }
}
