// code by jph
package ch.ethz.idsc.gokart.offline.api;

import java.util.Properties;

import ch.ethz.idsc.retina.util.data.TensorProperties;
import ch.ethz.idsc.tensor.io.ResourceData;
import junit.framework.TestCase;

public class GokartLogConfigTest extends TestCase {
  public void testResources() {
    Properties properties = ResourceData.properties("/offline/20180419T124700_fast/GokartLogConfig.properties");
    GokartLogConfig gokartLogConfig = TensorProperties.insert(properties, new GokartLogConfig());
    String driver = gokartLogConfig.driver;
    assertEquals(driver, "abc");
  }
}
