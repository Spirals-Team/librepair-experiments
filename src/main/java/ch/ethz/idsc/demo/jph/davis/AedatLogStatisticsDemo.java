// code by jph
package ch.ethz.idsc.demo.jph.davis;

import ch.ethz.idsc.retina.dev.davis.app.AedatLogStatistics;

enum AedatLogStatisticsDemo {
  ;
  public static void main(String[] args) throws Exception {
    AedatLogStatistics.of(Aedat.LOG_04.file);
  }
}
