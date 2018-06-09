// code by jph
package ch.ethz.idsc.retina.util.math;

import ch.ethz.idsc.owl.math.planar.PolygonArea;
import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.RotateLeft;
import junit.framework.TestCase;

public class PolygonIntersectionTest extends TestCase {
  public static boolean equalsCycle(Tensor cycle1, Tensor cycle2) {
    if (cycle1.length() == cycle2.length())
      for (int index = 0; index < cycle2.length(); ++index)
        if (cycle1.get(0).equals(cycle2.get(index)))
          return cycle1.equals(RotateLeft.of(cycle2, index));
    return false;
  }

  public void testEmpty() {
    Tensor a = Tensors.empty();
    Tensor b = Tensors.empty();
    Tensor result = PolygonIntersection.of(a, b);
    assertTrue(Tensors.isEmpty(result));
    result.append(RealScalar.ZERO);
    assertTrue(Tensors.isEmpty(a));
    assertTrue(Tensors.isEmpty(b));
  }

  public void testSimple() {
    // {{2.0, 1.0}, {1.0, 1.0}, {1.0, 2.0}}
    // {{2.0, 1.0}, {1.0, 1.0}, {1.0, 1.5}, {1.3333333333333333, 1.6666666666666667}}
    {
      Tensor poly1 = Tensors.fromString("{{1,1},{2,1},{1,2}}");
      Tensor poly2 = Tensors.fromString("{{1,1},{2,1},{2,3},{1,2}}");
      Tensor result = PolygonIntersection.of(poly1, poly2);
      assertTrue(equalsCycle(poly1, result));
    }
    {
      Tensor poly1 = Tensors.fromString("{{1,1},{2,1},{1,2}}");
      Tensor poly2 = Tensors.fromString("{{0,1},{2,1},{2,2}}");
      Tensor result = PolygonIntersection.of(poly1, poly2);
      assertTrue(equalsCycle(result, Tensors.fromString("{{4/3, 5/3}, {1, 3/2}, {1, 1}, {2, 1}}")));
    }
  }

  public void testMore() {
    Tensor clipper = Tensors.fromString("{{0,0},{1,0},{1,1},{0,1}}");
    Tensor subject = Tensors.fromString("{{0,0},{1,0},{1/2,1/2}}");
    Tensor result = PolygonIntersection.of(clipper, subject);
    assertTrue(equalsCycle(subject, result));
    Scalar area = PolygonArea.FUNCTION.apply(result);
    // System.out.println(area);
    assertEquals(area, RationalScalar.of(1, 4));
    assertTrue(ExactScalarQ.of(area));
  }

  public void testMore2() {
    Tensor clip = Tensors.fromString("{{0,0},{1,0},{1,1},{0,1}}");
    Tensor subj = Tensors.fromString("{{0,0},{2,0},{1/2,1/2}}");
    Tensor result = PolygonIntersection.of(clip, subj);
    assertTrue(equalsCycle(result, Tensors.fromString("{{0, 0}, {1, 0}, {1, 1/3}, {1/2, 1/2}}")));
    Scalar area = PolygonArea.FUNCTION.apply(result);
    assertTrue(ExactScalarQ.of(area));
    assertEquals(area, RationalScalar.of(1, 3));
  }

  public void testMore3() {
    Tensor clip = Tensors.fromString("{{0,0},{1,0},{1,1},{0,1}}");
    Tensor subj = Tensors.fromString("{{0,0},{2,0},{1/2,1/2}}");
    Tensor result = PolygonIntersection.of(subj, clip);
    assertTrue(equalsCycle(result, Tensors.fromString("{{1/2, 1/2}, {0, 0}, {1, 0}, {1, 1/3}}")));
    Scalar area = PolygonArea.FUNCTION.apply(result);
    assertTrue(ExactScalarQ.of(area));
    assertEquals(area, RationalScalar.of(1, 3));
  }

  public void testLine() {
    Tensor tensor = PolygonIntersection.intersection( //
        Tensors.vector(1, 0), //
        Tensors.vector(2, 0), //
        Tensors.vector(3, 3), //
        Tensors.vector(3, 2));
    assertEquals(tensor, Tensors.vector(3, 0));
    assertTrue(ExactScalarQ.all(tensor));
  }
}
