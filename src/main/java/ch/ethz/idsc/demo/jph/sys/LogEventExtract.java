// code by jph
package ch.ethz.idsc.demo.jph.sys;

import java.io.File;

import ch.ethz.idsc.demo.GokartLogFile;
import ch.ethz.idsc.retina.lcm.MessageConsistency;
import ch.ethz.idsc.retina.lcm.OfflineLogPlayer;
import ch.ethz.idsc.subare.util.UserHome;
import idsc.BinaryBlob;
import lcm.logging.Log;
import lcm.logging.Log.Event;
import lcm.logging.LogEventWriter;

enum LogEventExtract {
  ;
  public static void main(String[] args) throws Exception {
    File src = new File("/media/datahaki/media/ethz/gokartlogs", "20180112T113153_9e1d3699.lcm.00");
    src = UserHome.file("temp/20180108T165210_manual.lcm");
    src = UserHome.file("gokartlogs/20180418/20180418T132333_bca165ae.lcm.00");
    src = DatahakiLogFileLocator.file(GokartLogFile._20180604T150508_15e65bba);
    File dst = null;
    dst = UserHome.file("20180604T150508.lcm");
    if (dst.exists()) {
      System.out.println("deleting: " + dst);
      dst.delete();
    }
    int lo = 406828;
    int hi = 724864;
    // ---
    Log log = new Log(src.toString(), "r");
    LogEventWriter logWriter = new LogEventWriter(dst);
    try {
      // int count = 0;
      while (true) {
        Event event = log.readNext();
        if (lo <= event.eventNumber && event.eventNumber < hi) {
          try {
            new BinaryBlob(event.data);
            logWriter.write(event);
          } catch (Exception exception) {
            // ---
            exception.printStackTrace();
          }
        }
      }
    } catch (Exception exception) {
      System.err.println(exception.getMessage());
      // ---
    }
    logWriter.close();
    // ---
    OfflineLogPlayer.process(dst, MessageConsistency.INSTANCE);
  }
}
