// code by jph
package ch.ethz.idsc.retina.dev.lidar;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** events generated by lidar */
public class LidarSpacialEvent {
  /** timestamp of event in [us] */
  public final int usec;
  /** spacial coordinates in [m] when coords.length == 3 then coordinates are x,y,z
   * when coords.length == 2 then coordinates are x,y */
  public final float[] coords;
  /** intensity of reflection [0, 1, ..., 255] 255 == most intensive return */
  public final byte intensity;

  public LidarSpacialEvent(int usec, float[] coords, byte intensity) {
    this.usec = usec;
    this.coords = coords;
    this.intensity = intensity;
  }

  public Tensor getXY() {
    return Tensors.vectorDouble(coords[0], coords[1]);
  }
}
