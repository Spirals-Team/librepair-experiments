// code by jph
package ch.ethz.idsc.gokart.core.pure;

import ch.ethz.idsc.retina.util.curve.CurveSubdivision;
import ch.ethz.idsc.retina.util.curve.FourPointSubdivision;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Reverse;
import ch.ethz.idsc.tensor.io.ResourceData;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;
import ch.ethz.idsc.tensor.red.Nest;

public enum DubendorfCurve {
  ;
  public static final Tensor HYPERLOOP_EIGHT = hyperloop_eight();
  public static final Tensor HYPERLOOP_EIGHT_REVERSE = Reverse.of(HYPERLOOP_EIGHT);
  public static final Tensor HYPERLOOP_OVAL = hyperloop_oval();
  public static final Tensor HYPERLOOP_DUCTTAPE = hyperloop_ducttape();
  // ---
  public static final Tensor DEMODAY_EIGHT = eight_demoday();
  public static final Tensor DEMODAY_OVAL = oval_demoday();
  /** the shifted oval was created for the test on 2018-03-05
   * due to the safety barriers put into place on 2018-02-26 */
  public static final Tensor OVAL_SHIFTED = oval_shifted();
  /** CURVE "OVAL" IS USED IN TESTS
   * DONT MODIFY COORDINATES - INSTEAD CREATE A NEW CURVE */
  public static final Tensor OVAL = oval();
  public static final Tensor REVERSE_OVAL = Reverse.of(oval());

  /** CURVE "OVAL" IS USED IN TESTS
   * DONT MODIFY COORDINATES - INSTEAD CREATE A NEW CURVE */
  private static Tensor oval() {
    Tensor poly = Tensors.of( //
        Tensors.vector(35.200, 44.933), //
        Tensors.vector(49.867, 59.200), //
        Tensors.vector(57.200, 54.800), //
        Tensors.vector(49.200, 45.067), //
        Tensors.vector(40.800, 37.333));
    TensorUnaryOperator unaryOperator = CurveSubdivision.of(FourPointSubdivision.SCHEME);
    return Nest.of(unaryOperator, poly, 6).unmodifiable();
  }

  private static Tensor oval_shifted() {
    Tensor poly = Tensors.of( //
        Tensors.vector(37.200, 46.933), //
        Tensors.vector(50.867, 60.200), //
        Tensors.vector(58.200, 55.800), //
        Tensors.vector(51.200, 47.067), //
        Tensors.vector(42.800, 40.333));
    TensorUnaryOperator unaryOperator = CurveSubdivision.of(FourPointSubdivision.SCHEME);
    return Nest.of(unaryOperator, poly, 6).unmodifiable();
  }

  private static Tensor oval_demoday() {
    Tensor poly = Tensors.of( //
        Tensors.vector(42.000, 38.533), //
        Tensors.vector(36.133, 45.200), //
        Tensors.vector(51.633, 59.400), //
        Tensors.vector(57.067, 54.133));
    TensorUnaryOperator unaryOperator = CurveSubdivision.of(FourPointSubdivision.SCHEME);
    return Nest.of(unaryOperator, poly, 6).unmodifiable();
  }

  private static Tensor eight_demoday() {
    Tensor poly_pre = Tensors.of( //
        Tensors.vector(42.000, 38.533), //
        Tensors.vector(37.733, 40.533), // mid
        Tensors.vector(36.133, 45.200), //
        Tensors.vector(40.267, 49.600), // ins
        Tensors.vector(54.000, 50.533), // ins
        Tensors.vector(57.067, 54.133), //
        Tensors.vector(55.867, 58.267), // mid
        Tensors.vector(51.633, 59.400), //
        Tensors.vector(48.400, 56.533), // ins
        Tensors.vector(46.667, 43.467) // ins 48.133, 44.800
    );
    // careful: shift is subtracted
    Tensor shift = Tensors.vector(0.71, 0.71); // 1[m] away from balloon
    Tensor poly = Tensor.of(poly_pre.stream().map(point -> point.subtract(shift)));
    TensorUnaryOperator unaryOperator = CurveSubdivision.of(FourPointSubdivision.SCHEME);
    return Nest.of(unaryOperator, poly, 6).unmodifiable();
  }

  private static Tensor hyperloop_eight() {
    Tensor poly = ResourceData.of("/map/dubendorf/hangar/20180603eight.csv");
    TensorUnaryOperator unaryOperator = CurveSubdivision.of(FourPointSubdivision.SCHEME);
    return Nest.of(unaryOperator, poly, 6).unmodifiable();
  }

  private static Tensor hyperloop_oval() {
    Tensor poly = ResourceData.of("/map/dubendorf/hangar/20180502oval.csv");
    TensorUnaryOperator unaryOperator = CurveSubdivision.of(FourPointSubdivision.SCHEME);
    return Nest.of(unaryOperator, poly, 6).unmodifiable();
  }

  private static Tensor hyperloop_ducttape() {
    Tensor poly = ResourceData.of("/map/dubendorf/hangar/20180514ducttape.csv");
    TensorUnaryOperator unaryOperator = CurveSubdivision.of(FourPointSubdivision.SCHEME);
    return Nest.of(unaryOperator, poly, 5).unmodifiable();
  }
}
