// code by jph
package ch.ethz.idsc.demo.yt.prc;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import ch.ethz.idsc.owl.bot.util.UserHome;
import ch.ethz.idsc.retina.dev.davis.DavisDvsListener;
import ch.ethz.idsc.retina.dev.davis._240c.DavisDvsEvent;
import ch.ethz.idsc.retina.dev.davis.data.DavisDvsDatagramDecoder;
import ch.ethz.idsc.retina.lcm.OfflineLogListener;
import ch.ethz.idsc.retina.lcm.OfflineLogPlayer;
import ch.ethz.idsc.tensor.Scalar;

/** class adapted for gioele
 * extract all dvs events */
class NewTrajectoryProcess implements OfflineLogListener, DavisDvsListener {
  private final DavisDvsDatagramDecoder davisDvsDatagramDecoder = new DavisDvsDatagramDecoder();
  int[] polairty_count = new int[2];

  public NewTrajectoryProcess() {
    davisDvsDatagramDecoder.addDvsListener(this);
  }

  @Override
  public void event(Scalar time, String channel, ByteBuffer byteBuffer) {
    // System.out.println(channel);
    if (channel.equals("davis240c.overview.dvs")) {
      davisDvsDatagramDecoder.decode(byteBuffer);
    }
  }

  @Override
  public void davisDvs(DavisDvsEvent davisDvsEvent) {
    // TODO GZ determine polairty_count for every 1[s]
    // ... and after 1[s] you can reset the polairty_count
    // ... we can export this to a table and then make a plot
    // System.out.println(davisDvsEvent.toString());
    ++polairty_count[davisDvsEvent.i];
  }

  public static void main(String[] args) throws IOException {
    File file = UserHome.file("gokart/twist/20180108T165210_4/log.lcm");
    NewTrajectoryProcess newTrajectoryProcess = new NewTrajectoryProcess();
    newTrajectoryProcess.davisDvsDatagramDecoder.addDvsListener(newTrajectoryProcess);
    OfflineLogPlayer.process(file, newTrajectoryProcess);
    System.out.println("#0=" + newTrajectoryProcess.polairty_count[0]);
    System.out.println("#1=" + newTrajectoryProcess.polairty_count[1]);
  }
}
