// code by mg
package ch.ethz.idsc.demo.mg.slam.algo;

import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_imgproc;

import ch.ethz.idsc.demo.mg.slam.MapProvider;

/* package */ enum SlamMapProcessingUtil {
  ;
  private static final Point POINT = new Point(-1, -1);

  /** finds way points through threshold operation, morphological processing and connected component labeling
   * 
   * calls
   * opencv_imgproc#connectedComponentsWithStats
   * 
   * @param thresholdMap input object containing binary map
   * @param labels map with labeled connected components
   * @param dilateKernel parameter for dilate morphological operation
   * @param erodeKernel parameter for erode morphological operation
   * @param cornerX [m]
   * @param cornerY [m]
   * @param cellDim [m]
   * @return worldWayPoints [m] detected way points in world frame */
  public static List<double[]> findWayPoints( //
      MapProvider thresholdMap, Mat labels, Mat dilateKernel, Mat erodeKernel, double mapThreshold, double cornerX, double cornerY, double cellDim) {
    Mat processedMap = mapProviderToBinaryMat(thresholdMap, mapThreshold);
    // opening
    opencv_imgproc.dilate(processedMap, processedMap, dilateKernel, POINT, 1, opencv_core.BORDER_CONSTANT, null);
    opencv_imgproc.erode(processedMap, processedMap, erodeKernel, POINT, 1, opencv_core.BORDER_CONSTANT, null);
    // connected components labeling and centroid extraction
    Mat centroid = new Mat(opencv_core.CV_64F);
    Mat stats = new Mat();
    opencv_imgproc.connectedComponentsWithStats(processedMap, labels, stats, centroid, 8, opencv_core.CV_16UC1);
    List<double[]> worldWayPoints = new ArrayList<>(centroid.rows() - 1);
    // start at 1 because 0 is background label
    for (int index = 1; index < centroid.rows(); ++index) {
      double[] newWayPoint = { //
          centroid.row(index).arrayData().getDouble(0), //
          centroid.row(index).arrayData().getDouble(Double.BYTES) };
      worldWayPoints.add(index - 1, frameToWorld(newWayPoint, cornerX, cornerY, cellDim));
    }
    processedMap.release(); // probably obsolete because underlying array was created in java
    centroid.release();
    stats.release();
    return worldWayPoints;
  }

  /** convert mapProvider to binary Mat object by invoking threshold operation.
   * 
   * @param mapProvider
   * @param mapThreshold value in [0,1] that indicates the threshold relative to maxValue of mapProvider
   * @return binary Mat object */
  private static Mat mapProviderToBinaryMat(MapProvider mapProvider, double mapThreshold) {
    double[] mapArray = mapProvider.getMapArray();
    byte[] byteArray = new byte[mapArray.length];
    double maxValueScaled = mapProvider.getMaxValue() * mapThreshold;
    Mat mat = new Mat(mapProvider.getMapWidth(), mapProvider.getMapHeight(), opencv_core.CV_8UC1);
    for (int index = 0; index < byteArray.length; ++index)
      byteArray[index] = mapArray[index] >= maxValueScaled ? (byte) 1 : 0;
    mat.data().put(byteArray);
    return mat;
  }

  /** coordinate transformation between pixel coordinates and world frame coordinates
   * 
   * @param framePos [pixel] way point position in frame
   * @param cornerX [m]
   * @param cornerY [m]
   * @param cellDim [m]
   * @return worldPos [m] way point position in world coordinate system */
  private static double[] frameToWorld(double[] framePos, double cornerX, double cornerY, double cellDim) {
    return new double[] { //
        cornerX + framePos[0] * cellDim, //
        cornerY + framePos[1] * cellDim };
  }

  /** @param inputMap
   * @param resizeFactor [-]
   * @return outputMap same type as inputMap */
  // TODO could be used in the future to reduce computational load
  private static Mat resizeMat(Mat inputMap, double resizeFactor) {
    int newHeight = (int) (inputMap.rows() / resizeFactor);
    int newWidth = (int) (inputMap.cols() / resizeFactor);
    Mat outputMap = new Mat(newHeight, newWidth, inputMap.type());
    opencv_imgproc.resize(inputMap, outputMap, outputMap.size());
    return outputMap;
  }
}
