// code by jph
package ch.ethz.idsc.retina.dev.lidar;

import junit.framework.TestCase;

public class LidarRotationProviderTest extends TestCase {
  public void testSimple() {
    assertEquals(LidarRotationProvider.ROTATIONAL_INIT, 0);
  }
}
