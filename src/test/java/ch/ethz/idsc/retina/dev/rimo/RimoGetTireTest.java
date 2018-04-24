// code by jph
package ch.ethz.idsc.retina.dev.rimo;

import java.util.Objects;

import junit.framework.TestCase;

public class RimoGetTireTest extends TestCase {
  public void testSimple() {
    assertEquals(RimoGetTire.LENGTH, 24);
  }

  public void testConstructor() {
    RimoGetEvent rimoGetEvent = RimoGetEvents.create(600, 300);
    assertTrue(Objects.nonNull(rimoGetEvent.getTireL.toInfoString()));
    assertEquals(rimoGetEvent.getTireL.getErrorCodeMasked() & 0xff000000, 0);
    assertEquals(rimoGetEvent.getTireL.vector_raw().length(), 7);
  }
}
