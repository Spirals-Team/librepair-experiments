// code by jph
package ch.ethz.idsc.owl.subdiv.curve;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Reverse;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class CurveSubdivisionTest extends TestCase {
  private static void _checkSym(CurveSubdivision cs, Tensor tensor) {
    Tensor forward = cs.string(tensor);
    Tensor reverse = cs.string(Reverse.of(tensor));
    assertTrue(Chop._12.close(Reverse.of(forward), reverse));
  }

  public void testSymmetric() {
    Distribution distribution = UniformDistribution.of(-2, 3);
    for (int length = 0; length < 10; ++length) {
      Tensor tensor = RandomVariate.of(distribution, length, 2);
      _checkSym(new BSpline1CurveSubdivision(RnGeodesic.INSTANCE), tensor);
      _checkSym(new BSpline2CurveSubdivision(RnGeodesic.INSTANCE), tensor);
      _checkSym(new BSpline3CurveSubdivision(RnGeodesic.INSTANCE), tensor);
      _checkSym(BSpline4CurveSubdivision.of(RnGeodesic.INSTANCE), tensor);
      _checkSym(new FourPointCurveSubdivision(RnGeodesic.INSTANCE), tensor);
      _checkSym(DodgsonSabinCurveSubdivision.INSTANCE, tensor);
      _checkSym(ThreePointCurveSubdivision.hormannSabin(RnGeodesic.INSTANCE), tensor);
    }
  }
}
