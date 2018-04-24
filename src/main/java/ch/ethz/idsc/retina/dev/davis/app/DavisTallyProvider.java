// code by jph
package ch.ethz.idsc.retina.dev.davis.app;

import java.util.Objects;

import ch.ethz.idsc.retina.dev.davis.DavisDvsListener;
import ch.ethz.idsc.retina.util.ColumnTimedImage;
import ch.ethz.idsc.retina.util.ColumnTimedImageListener;

/** in the presence of APS images, one period is defined as the frame rate reciprocal */
public class DavisTallyProvider {
  private final DavisTallyListener davisTallyListener;
  private DavisTallyEvent davisTallyEvent;
  private int shift = 8;
  public final ColumnTimedImageListener sigListener = new ColumnTimedImageListener() {
    @Override
    public void columnTimedImage(ColumnTimedImage columnTimedImage) {
      if (isTriggered()) {
        davisTallyEvent.setMax(columnTimedImage.time[0]);
        davisTallyListener.tallyEvent(davisTallyEvent);
      }
      davisTallyEvent = new DavisTallyEvent(columnTimedImage.time[0], shift);
      davisTallyEvent.setImageBlock(columnTimedImage.time[0], columnTimedImage.time[columnTimedImage.time.length - 1]);
    }
  };
  public final ColumnTimedImageListener rstListener = new ColumnTimedImageListener() {
    @Override
    public void columnTimedImage(ColumnTimedImage columnTimedImage) {
      if (isTriggered())
        davisTallyEvent.setResetBlock(columnTimedImage.time[0], columnTimedImage.time[columnTimedImage.time.length - 1]);
    }
  };
  public final DavisDvsListener dvsListener = davisDvsEvent -> {
    if (isTriggered())
      davisTallyEvent.register(davisDvsEvent.time);
  };

  public DavisTallyProvider(DavisTallyListener davisTallyListener) {
    this.davisTallyListener = davisTallyListener;
  }

  public void setShift(int shift) {
    this.shift = shift;
  }

  public int getShift() {
    return shift;
  }

  /* package */ boolean isTriggered() { // 2017
    return Objects.nonNull(davisTallyEvent);
  }
}
