// code by jph
package ch.ethz.idsc.retina.lcm.davis;

import ch.ethz.idsc.gokart.gui.GokartLcmChannel;
import junit.framework.TestCase;

public class DavisDvsBlockPublisherTest extends TestCase {
  public void testSimple() {
    String channel = DavisDvsBlockPublisher.channel(GokartLcmChannel.DAVIS_OVERVIEW);
    assertEquals(channel, "davis240c.overview.dvs");
  }
}
