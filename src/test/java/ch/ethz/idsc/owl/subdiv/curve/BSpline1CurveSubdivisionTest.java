// code by jph
package ch.ethz.idsc.owl.subdiv.curve;

import java.io.IOException;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.lie.CirclePoints;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;
import ch.ethz.idsc.tensor.sca.Rationalize;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import junit.framework.TestCase;

public class BSpline1CurveSubdivisionTest extends TestCase {
  public void testSimple() {
    BSpline1CurveSubdivision subdivision = new BSpline1CurveSubdivision(EuclideanGeodesic.INSTANCE);
    ScalarUnaryOperator operator = Rationalize.withDenominatorLessEquals(100);
    Tensor tensor = CirclePoints.of(4).map(operator);
    Tensor actual = subdivision.cyclic(tensor);
    assertTrue(ExactScalarQ.all(actual));
    Tensor expected = Tensors.fromString("{{1, 0}, {1/2, 1/2}, {0, 1}, {-1/2, 1/2}, {-1, 0}, {-1/2, -1/2}, {0, -1}, {1/2, -1/2}}");
    assertEquals(expected, actual);
  }

  public void testSerializable() throws ClassNotFoundException, IOException {
    TensorUnaryOperator fps = new BSpline1CurveSubdivision(EuclideanGeodesic.INSTANCE)::cyclic;
    TensorUnaryOperator copy = Serialization.copy(fps);
    assertEquals(copy.apply(CirclePoints.of(10)), fps.apply(CirclePoints.of(10)));
  }
}
