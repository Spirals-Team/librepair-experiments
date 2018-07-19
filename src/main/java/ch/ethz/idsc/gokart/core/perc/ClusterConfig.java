// code by vc
package ch.ethz.idsc.gokart.core.perc;

import java.io.Serializable;

import ch.ethz.idsc.retina.sys.AppResources;
import ch.ethz.idsc.retina.util.math.Magnitude;
import ch.ethz.idsc.retina.util.math.SI;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.qty.Quantity;

/**  */
public class ClusterConfig implements Serializable {
  public static final ClusterConfig GLOBAL = AppResources.load(new ClusterConfig());
  /***************************************************/
  public Scalar epsilon = Quantity.of(0.03, SI.METER);
  public Scalar minPoints = RealScalar.of(5);
  public Scalar scanCount = RealScalar.of(6);

  /***************************************************/
  public double getEpsilon() {
    return Magnitude.METER.apply(epsilon).number().doubleValue();
  }

  public int getMinPoints() {
    return Scalars.intValueExact(minPoints);
  }

  public int getScanCount() {
    return Scalars.intValueExact(scanCount);
  }

  public double dbscanTracking(ClusterCollection collection, Tensor matrix) {
    double noiseRatio = ClustersTracking.elkiDBSCAN(collection, matrix, getEpsilon(), getMinPoints());
    collection.decompose();
    return noiseRatio;
  }
}
