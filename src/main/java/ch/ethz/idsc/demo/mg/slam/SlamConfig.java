// code by mg
package ch.ethz.idsc.demo.mg.slam;

import ch.ethz.idsc.demo.mg.DavisConfig;
import ch.ethz.idsc.demo.mg.util.calibration.GokartToImageLookup;
import ch.ethz.idsc.retina.util.math.Magnitude;
import ch.ethz.idsc.retina.util.math.NonSI;
import ch.ethz.idsc.retina.util.math.SI;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.UnitSystem;

/** defines all parameters of the SLAM algorithm and optionally saves them to a .properties file */
public class SlamConfig {
  // general parameters
  public final DavisConfig davisConfig = new DavisConfig(); // main/resources/
  // SLAM algorithm configuration
  /* in onlineMode, periodic tasks run as timedTasks while in offline mode they run periodic relative to event stream timestamps */
  public Boolean onlineMode = false;
  // SlamMappingStep
  /** in localization mode, a previously saved map is used */
  public final Boolean localizationMode = false;
  /** in reactive mode, only the part of the map around the go kart is kept */
  public final Boolean reactiveMappingMode = true;
  /** pose provided by lidar instead of particle filter */
  public final Boolean lidarMappingMode = false; //
  /** state propagation using odometry instead of estimated velocities */
  public final Boolean odometryStatePropagation = false;
  /** saves occurrence map. To be used to save ground truth map obtained with lidar pose */
  public final Boolean saveSlamMap = false;
  // further parameters
  public final Scalar alpha = RealScalar.of(0.4); // [-] for update of state estimate
  public final Scalar numberOfParticles = RealScalar.of(20); // [-]
  public final Scalar relevantParticles = RealScalar.of(5); // only these particles are used for occurrence map update
  /** [m] events further away are neglected */
  public final Scalar _lookAheadDistance = Quantity.of(8, SI.METER);
  /** [m] for reactive mapping mode */
  public final Scalar _lookBehindDistance = Quantity.of(-3, SI.METER);
  // SlamLocalizationStep
  // update rates
  public final Scalar _resampleRate = Quantity.of(0.05, SI.SECOND);
  public final Scalar _statePropagationRate = Quantity.of(5, NonSI.MILLI_SECOND);
  public final Scalar _reactiveUpdateRate = Quantity.of(0.5, SI.SECOND);
  public final Scalar _normalizationUpdateRate = Quantity.of(0.05, SI.SECOND);
  public final Scalar _wayPointUpdateRate = Quantity.of(0.1, SI.SECOND);
  public final Scalar _trajectoryUpdateRate = Quantity.of(0.1, SI.SECOND);
  // particle initialization
  public final Scalar _linVelAvg = Quantity.of(3, SI.VELOCITY); // [m/s] for initial particle distribution
  public final Scalar _linVelStd = Quantity.of(1, SI.VELOCITY); // [m/s] for initial particle distribution
  public final Scalar _angVelStd = Quantity.of(0.1, SI.PER_SECOND); // [rad/s] for initial particle distribution
  // particle roughening
  public final Scalar _rougheningLinAccelStd = Quantity.of(8, SI.ACCELERATION); // [m/s²]
  public final Scalar _rougheningAngAccelStd = Quantity.of(10, "rad*s^-2"); // [rad/s²]
  // SLAM map parameters
  public final Scalar _cellDim = Quantity.of(0.025, SI.METER); // [m] single cell dimension
  /** [m] x 'width' of map */
  public final Scalar _dimX = Quantity.of(35, SI.METER);
  /** [m] y 'height' of map */
  public final Scalar _dimY = Quantity.of(35, SI.METER);

  public final int mapWidth() {
    return Magnitude.ONE.toInt(_dimX.divide(_cellDim));
  }

  public final int mapHeight() {
    return Magnitude.ONE.toInt(_dimY.divide(_cellDim));
  }

  // [m] coordinates of lower left point in map
  public final Tensor _corner = Tensors.of( //
      Quantity.of(30, SI.METER), Quantity.of(30, SI.METER)).map(UnitSystem.SI());

  public Tensor cornerHigh() {
    return _corner.add(Tensors.of(_dimX, _dimY).map(UnitSystem.SI()));
  }

  // SLAM visualization parameters
  public final Boolean saveSlamFrame = false;
  public final Scalar frameWidth = RealScalar.of(600); // [pixel]
  // SlamViewer
  public final Scalar _savingInterval = Quantity.of(0.2, SI.SECOND); // [s]
  public final Scalar _visualizationInterval = Quantity.of(0.2, SI.SECOND); // [s]
  public final Scalar _kartSize = Quantity.of(1.5, SI.METER); // [m]

  public final int kartLength() {
    return Magnitude.ONE.toInt(_kartSize.divide(_cellDim));
  }

  public final Scalar wayPointRadius = RealScalar.of(10); // [pixel]
  // map processing parameters
  public final Scalar mapThreshold = RealScalar.of(0.3); // [-]
  // trajectory planning parameters
  public final Scalar _initialDelay = Quantity.of(0.5, SI.SECOND); // [s] initial delay before waypoints are extracted
  public final Scalar _visibleBoxXMin = Quantity.of(0, SI.METER); // [m] in go kart frame
  public final Scalar _visibleBoxXMax = Quantity.of(10, SI.METER); // [m] in go kart frame

  /** @return new instance of {@link GokartToImageLookup} */
  public GokartToImageLookup createGokartToImageUtilLookup() {
    return GokartToImageLookup.fromMatrix(davisConfig.logFileLocations.calibration(), //
        davisConfig.unitConversion, _cellDim, _lookAheadDistance, davisConfig.width);
  }
}
