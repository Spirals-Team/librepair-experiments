// code by jph
package ch.ethz.idsc.demo.mg.blobtrack;

import java.io.File;
import java.io.IOException;

import ch.ethz.idsc.owl.bot.util.UserHome;
import ch.ethz.idsc.retina.util.data.TensorProperties;
import junit.framework.TestCase;

public class BlobTrackConfigTest extends TestCase {
  public void testSimple() throws IOException {
    BlobTrackConfig test = new BlobTrackConfig();
    File file = UserHome.file("__" + BlobTrackConfigTest.class.getSimpleName() + "__.properties");
    TensorProperties.manifest(file, test);
    assertTrue(file.isFile());
    file.delete();
  }
}
