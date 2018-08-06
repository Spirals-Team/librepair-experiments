// code by jph
package ch.ethz.idsc.owl.subdiv.curve;

import java.io.IOException;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.lie.CirclePoints;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;
import ch.ethz.idsc.tensor.sca.Rationalize;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import junit.framework.TestCase;

public class BSpline1CurveSubdivisionTest extends TestCase {
  public void testCyclic() {
    CurveSubdivision subdivision = new BSpline1CurveSubdivision(RnGeodesic.INSTANCE);
    ScalarUnaryOperator operator = Rationalize.withDenominatorLessEquals(100);
    Tensor tensor = CirclePoints.of(4).map(operator);
    Tensor actual = subdivision.cyclic(tensor);
    assertTrue(ExactScalarQ.all(actual));
    Tensor expected = Tensors.fromString("{{1, 0}, {1/2, 1/2}, {0, 1}, {-1/2, 1/2}, {-1, 0}, {-1/2, -1/2}, {0, -1}, {1/2, -1/2}}");
    assertEquals(expected, actual);
  }

  public void testString() {
    CurveSubdivision subdivision = new BSpline1CurveSubdivision(RnGeodesic.INSTANCE);
    Tensor string = subdivision.string(Tensors.fromString("{{0,10}, {1,12}}"));
    assertEquals(string, Tensors.fromString("{{0, 10}, {1/2, 11}, {1, 12}}"));
    assertTrue(ExactScalarQ.all(string));
  }

  public void testStringTwo() {
    Tensor curve = Tensors.vector(0, 1);
    CurveSubdivision subdivision = new BSpline1CurveSubdivision(RnGeodesic.INSTANCE);
    Tensor refined = subdivision.string(curve);
    assertEquals(refined, Tensors.fromString("{0, 1/2, 1}"));
    assertTrue(ExactScalarQ.all(refined));
  }

  public void testStringOne() {
    Tensor curve = Tensors.vector(8);
    CurveSubdivision subdivision = new BSpline1CurveSubdivision(RnGeodesic.INSTANCE);
    Tensor refined = subdivision.string(curve);
    assertEquals(refined, Tensors.fromString("{8}"));
    assertTrue(ExactScalarQ.all(refined));
  }

  public void testStringEmpty() {
    Tensor curve = Tensors.vector();
    CurveSubdivision subdivision = new BSpline1CurveSubdivision(RnGeodesic.INSTANCE);
    Tensor refined = subdivision.string(curve);
    assertTrue(Tensors.isEmpty(refined));
    assertTrue(ExactScalarQ.all(refined));
  }

  public void testSerializable() throws ClassNotFoundException, IOException {
    TensorUnaryOperator fps = new BSpline1CurveSubdivision(RnGeodesic.INSTANCE)::cyclic;
    TensorUnaryOperator copy = Serialization.copy(fps);
    assertEquals(copy.apply(CirclePoints.of(10)), fps.apply(CirclePoints.of(10)));
  }

  public void testScalarFail() {
    CurveSubdivision subdivision = new BSpline1CurveSubdivision(Se2Geodesic.INSTANCE);
    try {
      subdivision.string(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      subdivision.cyclic(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
