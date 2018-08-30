// code by mg
package ch.ethz.idsc.demo.mg.slam.algo;

import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.demo.mg.slam.GokartPoseOdometryDemo;
import ch.ethz.idsc.demo.mg.slam.SlamConfig;
import ch.ethz.idsc.demo.mg.slam.SlamContainer;
import ch.ethz.idsc.gokart.core.pos.GokartPoseInterface;
import ch.ethz.idsc.retina.dev.davis.DavisDvsListener;

/** SLAM algorithm module configuration. The order in the list is the order of the respective callback method calls */
/* package */ enum SlamAlgoConfig {
  ;
  public static final List<DavisDvsListener> getListeners(SlamContainer slamContainer, SlamConfig slamConfig, //
      GokartPoseInterface gokartLidarPose, GokartPoseOdometryDemo gokartPoseOdometry) {
    System.out.println(slamConfig.slamAlgoConfig());
    switch (slamConfig.slamAlgoConfig()) {
    case standardMode:
      return standardMode(slamContainer, slamConfig);
    case standardReactiveMode:
      return reactiveMapMode(slamContainer, slamConfig);
    case lidarMode:
      return externalPoseMode(slamContainer, slamConfig, gokartLidarPose);
    case lidarReactiveMode:
      return externalPoseReactiveMode(slamContainer, slamConfig, gokartLidarPose);
    case odometryMode:
      return externalPoseMode(slamContainer, slamConfig, gokartPoseOdometry);
    case odometryReactiveMode:
      return externalPoseReactiveMode(slamContainer, slamConfig, gokartPoseOdometry);
    case localizationMode:
      return localizationMode(slamContainer, slamConfig);
    }
    throw new RuntimeException();
  }

  /** standardMode: the particle velocity state is used for state propagation */
  private static final List<DavisDvsListener> standardMode(SlamContainer slamContainer, SlamConfig slamConfig) {
    return Arrays.asList( //
        new SlamImageToGokart(slamContainer, slamConfig), //
        new SlamLikelihoodStep(slamContainer, slamConfig.alpha), //
        new SlamPropagationStep(slamContainer, slamConfig), //
        new SlamResamplingStep(slamContainer, slamConfig), //
        new SlamOccurrenceMapStep(slamContainer, slamConfig.relevantParticles), //
        new SlamMapProcessing(slamContainer, slamConfig), //
        new SlamPoseMapReset(slamContainer, slamConfig));
  }

  /** externalPoseMode: Instead of using a particle filter, the pose is provided by an external module like the lidar
   * or odometry. The occurrence map is then updated using this pose */
  private static final List<DavisDvsListener> externalPoseMode( //
      SlamContainer slamContainer, SlamConfig slamConfig, GokartPoseInterface gokartPoseInterface) {
    return Arrays.asList( //
        new SlamImageToGokart(slamContainer, slamConfig), //
        new SlamLocalizationStep(slamContainer, slamConfig, gokartPoseInterface), //
        new SlamMappingStep(slamContainer), //
        new SlamMapProcessing(slamContainer, slamConfig));
  }

  /** reactiveMapMode: In comparison with standardConfig, the part of the map which is currently not seen by the vehicle
   * is cleared. This results in a "local" localization */
  private static final List<DavisDvsListener> reactiveMapMode(SlamContainer slamContainer, SlamConfig slamConfig) {
    return Arrays.asList( //
        new SlamImageToGokart(slamContainer, slamConfig), //
        new SlamLikelihoodStep(slamContainer, slamConfig.alpha), //
        new SlamPropagationStep(slamContainer, slamConfig), //
        new SlamResamplingStep(slamContainer, slamConfig), //
        new SlamOccurrenceMapStep(slamContainer, slamConfig.relevantParticles), //
        new SlamReactiveMapStep(slamConfig, slamContainer), //
        new SlamMapProcessing(slamContainer, slamConfig), //
        new SlamWaypointSelection(slamContainer, slamConfig), //
        new SlamPoseMapReset(slamContainer, slamConfig));
  }

  /** externalPoseReactiveMode: Identical to externalPose mode but the part of the map behind the vehicle is cleared. This allows the addition
   * of a way point selection module */
  private static final List<DavisDvsListener> externalPoseReactiveMode( //
      SlamContainer slamContainer, SlamConfig slamConfig, GokartPoseInterface gokartPoseInterface) {
    return Arrays.asList( //
        new SlamImageToGokart(slamContainer, slamConfig), //
        new SlamLocalizationStep(slamContainer, slamConfig, gokartPoseInterface), //
        new SlamMappingStep(slamContainer), //
        new SlamReactiveMapStep(slamConfig, slamContainer), //
        new SlamMapProcessing(slamContainer, slamConfig), //
        new SlamWaypointSelection(slamContainer, slamConfig), //
        new SlamPoseMapReset(slamContainer, slamConfig));
  }

  /** localizationMode: the mapping mode of the algorithm is replaced by using a previously saved "ground truth" map */
  private static List<DavisDvsListener> localizationMode(SlamContainer slamContainer, SlamConfig slamConfig) {
    return Arrays.asList( //
        new SlamImageToGokart(slamContainer, slamConfig), //
        new SlamLikelihoodStep(slamContainer, slamConfig.alpha), //
        new SlamPropagationStep(slamContainer, slamConfig), //
        new SlamResamplingStep(slamContainer, slamConfig));
  }
}
