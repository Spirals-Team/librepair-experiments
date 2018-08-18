// code by mg
package ch.ethz.idsc.demo.mg.slam.algo;

import ch.ethz.idsc.demo.mg.slam.MapProvider;
import ch.ethz.idsc.demo.mg.slam.SlamConfig;
import ch.ethz.idsc.demo.mg.slam.SlamFileLocations;
import ch.ethz.idsc.demo.mg.slam.SlamParticle;
import ch.ethz.idsc.retina.util.io.PrimitivesIO;
import ch.ethz.idsc.retina.util.math.Magnitude;
import ch.ethz.idsc.tensor.Tensor;

/** executes the mapping step of the SLAM algorithm */
/* package */ class SlamMappingStep {
  private final MapProvider[] eventMaps = new MapProvider[3];
  private final String imagePrefix;
  private final boolean localizationMode;
  private final boolean reactiveMappingMode;
  private final boolean onlineMode;
  private final double lookAheadDistance;
  private final double lookBehindDistance;
  private final double reactiveUpdateRate;
  private final int relevantParticles;
  // ---
  private double lastReactiveUpdateTimeStamp;

  SlamMappingStep(SlamConfig slamConfig) {
    for (int i = 0; i < eventMaps.length; ++i)
      eventMaps[i] = new MapProvider(slamConfig);
    imagePrefix = slamConfig.davisConfig.logFilename();
    localizationMode = slamConfig.localizationMode;
    reactiveMappingMode = slamConfig.reactiveMappingMode;
    onlineMode = slamConfig.onlineMode;
    lookAheadDistance = Magnitude.METER.toDouble(slamConfig._lookAheadDistance);
    lookBehindDistance = Magnitude.METER.toDouble(slamConfig._lookBehindDistance);
    reactiveUpdateRate = Magnitude.SECOND.toDouble(slamConfig._reactiveUpdateRate);
    relevantParticles = slamConfig.relevantParticles.number().intValue();
  }

  public void initialize(double initTimeStamp) {
    lastReactiveUpdateTimeStamp = initTimeStamp;
    if (localizationMode) {
      double[] mapArray = PrimitivesIO.loadFromCSV(SlamFileLocations.recordedMaps(imagePrefix));
      if (mapArray.length != eventMaps[0].getNumberOfCells())
        throw new RuntimeException("FATAL: bad size");
      eventMaps[0].setMapArray(mapArray);
    }
  }

  /** updates occurrence map
   * 
   * @param slamParticles
   * @param gokartPose unitless representation
   * @param eventGokartFrame [m]
   * @param currentTimeStamp [s] */
  public void mappingStep(SlamParticle[] slamParticles, Tensor gokartPose, double[] eventGokartFrame, double currentTimeStamp) {
    if (eventGokartFrame[0] < lookAheadDistance) {
      if (!localizationMode)
        SlamMappingStepUtil.updateOccurrenceMap(slamParticles, eventMaps[0], eventGokartFrame, relevantParticles);
    }
    if (!onlineMode && reactiveMappingMode) {
      if (currentTimeStamp - lastReactiveUpdateTimeStamp > reactiveUpdateRate) {
        SlamMappingStepUtil.updateReactiveOccurrenceMap(gokartPose, eventMaps[0], lookBehindDistance);
        lastReactiveUpdateTimeStamp = currentTimeStamp;
      }
    }
    // here we would update normalization map on a periodic basis (if implemented)
  }

  /** updates occurrence map using pose provided by lidar
   * 
   * @param gokartPose unitless representation
   * @param eventGokartFrame [m]
   * @param currentTimeStamp [s] */
  public void mappingStepWithLidar(Tensor gokartPose, double[] eventGokartFrame, double currentTimeStamp) {
    // just to make sure
    if (localizationMode)
      System.out.println("FATAL: when mapping with lidar pose, localization mode should be false");
    if (eventGokartFrame[0] < lookAheadDistance)
      SlamMappingStepUtil.updateOccurrenceMapLidar(gokartPose, eventMaps[0], eventGokartFrame);
  }

  public MapProvider getMap(int mapID) {
    return eventMaps[mapID];
  }
}
