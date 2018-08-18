// code by mg
package ch.ethz.idsc.demo.mg;

import java.io.IOException;

import ch.ethz.idsc.demo.mg.slam.SlamConfig;
import ch.ethz.idsc.demo.mg.slam.online.SlamModule;
import lcm.logging.LogPlayer;
import lcm.logging.LogPlayerConfig;

// to test live version of SLAM algorithm
enum GokartLcmLogPlayer {
  ;
  public static void main(String[] args) throws IOException {
    LogPlayerConfig cfg = new LogPlayerConfig();
    SlamConfig slamConfig = new SlamConfig();
    cfg.logFile = slamConfig.davisConfig.getLogFile().toString();
    LogPlayer.create(cfg);
    try {
      SlamModule.standalone();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
