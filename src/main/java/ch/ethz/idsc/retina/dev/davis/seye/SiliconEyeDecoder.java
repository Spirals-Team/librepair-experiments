// code by jph
package ch.ethz.idsc.retina.dev.davis.seye;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import ch.ethz.idsc.retina.dev.davis.Aedat31PolarityListener;
import ch.ethz.idsc.retina.dev.davis.io.Aedat31Decoder;

/** maps the chip raw dvs/aps data to the standard coordinate system (x,y) where
 * (0,0) corresponds to left-upper corner, and (x,0) parameterizes the first/top
 * row (0,y) parameterizes the first/left column */
public class SiliconEyeDecoder implements Aedat31Decoder {
  private final List<Aedat31PolarityListener> dvsDavisEventListeners = new LinkedList<>();

  @Override
  public void read(ByteBuffer byteBuffer) { // LITTLE_ENDIAN
  }

  @Override
  public void addPolarityListener(Aedat31PolarityListener listener) {
    dvsDavisEventListeners.add(listener);
  }
}
