// code by ynager
package ch.ethz.idsc.owl.mapping;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;

import ch.ethz.idsc.owl.bot.util.RegionRenders;
import ch.ethz.idsc.owl.gui.win.GeometricLayer;
import ch.ethz.idsc.owl.math.region.ImageRegion;
import ch.ethz.idsc.owl.math.state.StateTime;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.mat.DiagonalMatrix;

abstract class ShadowMap {
  protected final Tensor pixel2world;
  protected final Tensor world2pixel;
  protected final GeometricLayer world2pixelLayer;
  protected final Scalar pixelDim;
  protected final BufferedImage bufferedImage;

  public ShadowMap(ImageRegion imageRegion) {
    bufferedImage = RegionRenders.image(imageRegion.image());
    Tensor scale = imageRegion.scale();
    Scalar height = RealScalar.of(bufferedImage.getHeight());
    pixelDim = scale.Get(0).reciprocal(); // meters per pixel
    world2pixel = DiagonalMatrix.of(scale.Get(0), scale.Get(1).negate(), RealScalar.ONE);
    world2pixel.set(height, 1, 2);
    pixel2world = DiagonalMatrix.of( //
        scale.Get(0).reciprocal(), scale.Get(1).negate().reciprocal(), RealScalar.ONE);
    pixel2world.set(height.multiply(scale.Get(1).reciprocal()), 1, 2);
    world2pixelLayer = GeometricLayer.of(world2pixel);
  }

  abstract void updateMap(StateTime stateTime, float timeDelta);

  abstract Mat getInitMap();

  abstract float getMinTimeDelta();

  public Point state2pixel(Tensor state) {
    GeometricLayer layer = GeometricLayer.of(world2pixel);
    Point2D point2D = layer.toPoint2D(state);
    return new Point( //
        (int) point2D.getX(), //
        (int) point2D.getY());
  }
}
