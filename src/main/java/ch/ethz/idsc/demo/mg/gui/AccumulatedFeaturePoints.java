// code by mg
package ch.ethz.idsc.demo.mg.gui;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

// for visualization in PresenterLcmModule
// TODO for each blobID, only visualize most recent blob
public class AccumulatedFeaturePoints {
  private final List<Point2D> accumulatedPoints;
  private final List<Integer> blobIDList;

  public AccumulatedFeaturePoints() {
    accumulatedPoints = new ArrayList<>();
    blobIDList = new ArrayList<>();
  }

  public void addFeaturePoint(Point2D point2D, int blobID) {
    accumulatedPoints.add(point2D);
    blobIDList.add(blobID);
  }

  public List<Point2D> getAccumulatedPoints() {
    return accumulatedPoints;
  }

  public List<Integer> getBlobIDList() {
    return blobIDList;
  }
}
