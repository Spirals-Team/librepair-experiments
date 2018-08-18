// code by mg
package ch.ethz.idsc.demo.mg.blobtrack.vis;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

import ch.ethz.idsc.demo.mg.blobtrack.algo.BlobTrackProvider;

/** blob tracking algorithm static methods */
/* package */ enum StaticHelper {
  ;
  private static final byte GRAY_BYTE = (byte) 240;

  /** overlays tracked blobs on accumulatedEventFrame
   * 
   * @param eventFrames accumulatedEventFrames
   * @param physicalFrames for visualization in go kart frame
   * @param blobTrackProvider to get tracked blobs
   * @param calibrationAvailable
   * @return array of lenght 6 containing frames */
  public static BufferedImage[] constructFrames(AccumulatedEventFrame[] eventFrames, PhysicalBlobFrame[] physicalFrames, BlobTrackProvider blobTrackProvider,
      boolean calibrationAvailable) {
    BufferedImage[] combinedFrames = new BufferedImage[6];
    combinedFrames[0] = eventFrames[0].getAccumulatedEvents();
    combinedFrames[1] = eventFrames[1].overlayActiveBlobs(blobTrackProvider.getBlobSelector().getProcessedBlobs(), Color.GREEN, Color.RED);
    combinedFrames[2] = eventFrames[2].overlayHiddenBlobs(blobTrackProvider.getBlobTracking().getHiddenBlobs(), Color.GRAY);
    if (calibrationAvailable) {
      combinedFrames[3] = physicalFrames[0].overlayPhysicalBlobs((blobTrackProvider.getPhysicalblobs()));
    }
    return combinedFrames;
  }

  /** resets the whole frame to GRAY_BYTE value
   * 
   * @param eventFrames */
  public static void resetFrames(AccumulatedEventFrame[] eventFrames) {
    for (int i = 0; i < eventFrames.length; i++) {
      byte[] bytes = eventFrames[i].getBytes();
      IntStream.range(0, bytes.length).forEach(j -> bytes[j] = GRAY_BYTE);
    }
  }
}
