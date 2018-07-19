// code by mg
package ch.ethz.idsc.demo.mg.slam;

import ch.ethz.idsc.demo.mg.util.SlamParticleUtil;
import ch.ethz.idsc.gokart.core.pos.GokartPoseInterface;
import ch.ethz.idsc.tensor.Tensor;

/** executes the localization step of the SLAM algorithm */
class SlamLocalizationStep {
  private final SlamEstimatedPose estimatedPose;
  private final boolean odometryStatePropagation;
  private final double resampleRate;
  private final double statePropagationRate;
  private final double rougheningLinVelStd;
  private final double rougheningAngVelStd;
  private final double linVelAvg;
  private final double linVelStd;
  private final double angVelStd;
  private final double lookAheadDistance;
  private final double alpha;
  private double lastResampleTimeStamp;
  private double lastPropagationTimeStamp;

  SlamLocalizationStep(SlamConfig slamConfig) {
    estimatedPose = new SlamEstimatedPose();
    resampleRate = slamConfig.resampleRate.number().doubleValue();
    odometryStatePropagation = slamConfig.odometryStatePropagation;
    statePropagationRate = slamConfig.statePropagationRate.number().doubleValue();
    rougheningLinVelStd = slamConfig.rougheningLinAccelStd.number().doubleValue();
    rougheningAngVelStd = slamConfig.rougheningAngAccelStd.number().doubleValue();
    linVelAvg = slamConfig.linVelAvg.number().doubleValue();
    linVelStd = slamConfig.linVelStd.number().doubleValue();
    angVelStd = slamConfig.angVelStd.number().doubleValue();
    lookAheadDistance = slamConfig.lookAheadDistance.number().doubleValue();
    alpha = slamConfig.alpha.number().doubleValue();
  }

  public void initialize(SlamParticle[] slamParticles, Tensor initPose, double initTimeStamp) {
    SlamParticleUtil.setInitialDistribution(slamParticles, initPose, linVelAvg, linVelStd, angVelStd);
    estimatedPose.setPose(initPose);
    lastResampleTimeStamp = initTimeStamp;
    lastPropagationTimeStamp = initTimeStamp;
  }

  public void localizationStep(SlamParticle[] slamParticles, MapProvider map, Tensor odometryVel, double[] eventGokartFrame, double currentTimeStamp) {
    if ((currentTimeStamp - lastPropagationTimeStamp) > statePropagationRate) {
      double dT = currentTimeStamp - lastPropagationTimeStamp;
      if (!odometryStatePropagation) {
        SlamParticleUtil.propagateStateEstimate(slamParticles, dT);
      } else {
        SlamParticleUtil.propagateStateEstimateOdometry(slamParticles, odometryVel, dT);
      }
      lastPropagationTimeStamp = currentTimeStamp;
    }
    if ((currentTimeStamp - lastResampleTimeStamp) > resampleRate) {
      double dT = currentTimeStamp - lastResampleTimeStamp;
      SlamParticleUtil.resampleParticles(slamParticles, dT, rougheningLinVelStd, rougheningAngVelStd);
      lastResampleTimeStamp = currentTimeStamp;
    }
    if (eventGokartFrame[0] < lookAheadDistance) {
      SlamParticleUtil.updateLikelihoods(slamParticles, map, eventGokartFrame, alpha);
    }
    estimatedPose.setPose(SlamParticleUtil.getAveragePose(slamParticles, 3));
  }

  public void setPose(Tensor pose) {
    estimatedPose.setPose(pose);
  }

  public GokartPoseInterface getPoseInterface() {
    return estimatedPose;
  }
}
