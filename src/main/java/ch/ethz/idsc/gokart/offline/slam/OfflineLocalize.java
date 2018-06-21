// code by jph
package ch.ethz.idsc.gokart.offline.slam;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import ch.ethz.idsc.gokart.core.slam.SlamScore;
import ch.ethz.idsc.gokart.gui.top.ImageScore;
import ch.ethz.idsc.owl.math.map.Se2Utils;
import ch.ethz.idsc.retina.dev.davis.data.DavisImuFrame;
import ch.ethz.idsc.retina.dev.davis.data.DavisImuFrameListener;
import ch.ethz.idsc.retina.dev.lidar.LidarRayBlockListener;
import ch.ethz.idsc.retina.util.math.SI;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.SquareMatrixQ;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.sca.Clip;

/** functionality is strictly for offline processing
 * do not use during live operation: memory consumption is not bounded */
public abstract class OfflineLocalize implements LidarRayBlockListener, DavisImuFrameListener {
  private static final Scalar ZERO_RATE = Quantity.of(0, SI.ANGULAR_RATE);
  // ---
  protected final SlamScore slamScore;
  private final List<LocalizationResultListener> listeners = new LinkedList<>();
  public final Tensor skipped = Tensors.empty();
  /** 3x3 matrix */
  private Tensor gyro_y = Tensors.empty();
  protected Tensor model;
  private Scalar time;

  public OfflineLocalize(BufferedImage map_image, Tensor model) {
    if (!SquareMatrixQ.of(model))
      throw new RuntimeException();
    this.model = model;
    // ---
    if (map_image.getType() != BufferedImage.TYPE_BYTE_GRAY)
      throw new RuntimeException();
    slamScore = ImageScore.of(map_image);
  }

  public final void addListener(LocalizationResultListener localizationResultListener) {
    listeners.add(localizationResultListener);
  }

  public final void setTime(Scalar time) {
    this.time = time;
  }

  public final Tensor getPositionVector() {
    return Se2Utils.fromSE2Matrix(model);
  }

  @Override // from DavisImuFrameListener
  public void imuFrame(DavisImuFrame davisImuFrame) {
    Scalar rate = davisImuFrame.gyroImageFrame().Get(1); // image - y axis
    gyro_y.append(rate);
  }

  protected final Scalar getGyroAndReset() {
    Scalar mean = Tensors.isEmpty(gyro_y) ? ZERO_RATE : Mean.of(gyro_y).Get();
    gyro_y = Tensors.empty();
    return mean;
  }

  protected final void appendRow(Scalar ratio, int sum, double duration) {
    LocalizationResult localizationResult = new LocalizationResult( //
        time, Se2Utils.fromSE2Matrix(model), Clip.unit().requireInside(ratio));
    listeners.forEach(listener -> listener.localizationCallback(localizationResult));
  }

  protected final void skip() {
    skipped.append(time);
    System.err.println("skip " + time);
  }
}
