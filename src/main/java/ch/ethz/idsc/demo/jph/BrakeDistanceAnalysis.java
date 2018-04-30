// code by jph
package ch.ethz.idsc.demo.jph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import ch.ethz.idsc.gokart.offline.api.GokartLogAdapter;
import ch.ethz.idsc.gokart.offline.api.GokartLogInterface;
import ch.ethz.idsc.gokart.offline.api.OfflineIndex;
import ch.ethz.idsc.gokart.offline.tab.BrakeDistanceTable;
import ch.ethz.idsc.gokart.offline.tab.RimoRateTable;
import ch.ethz.idsc.retina.lcm.OfflineLogPlayer;
import ch.ethz.idsc.subare.util.UserHome;
import ch.ethz.idsc.tensor.io.CsvFormat;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.qty.Quantity;

/** Post processing to determine emergency braking distance.
 * 
 * https://github.com/idsc-frazzoli/retina/files/1801717/20180217_emergency_braking.pdf */
enum BrakeDistanceAnalysis {
  ;
  static void brakeAnalysis() throws FileNotFoundException, IOException {
    for (File folder : OfflineIndex.folders(UserHome.file("gokart/BrakeDistanceAnalysis"))) {
      System.out.println(folder);
      GokartLogInterface olr = GokartLogAdapter.of(folder);
      // ---
      BrakeDistanceTable brakeDistanceAnalysis = new BrakeDistanceTable(olr);
      OfflineLogPlayer.process(olr.file(), brakeDistanceAnalysis);
      Export.of(UserHome.file(folder.getName() + ".csv"), brakeDistanceAnalysis.getTable().map(CsvFormat.strict()));
    }
  }

  static void rimo() throws IOException {
    RimoRateTable rimoTable = new RimoRateTable(Quantity.of(0.05, "s"));
    File file = UserHome.file("temp/20180108T165210_manual.lcm");
    OfflineLogPlayer.process(file, rimoTable);
    Export.of(UserHome.file("maxtorque.csv"), rimoTable.getTable().map(CsvFormat.strict()));
  }

  public static void main(String[] args) throws IOException {
    brakeAnalysis();
    // rimo();
  }
}
