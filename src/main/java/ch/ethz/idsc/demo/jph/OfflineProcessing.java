// code by jph
package ch.ethz.idsc.demo.jph;

import java.io.File;
import java.io.IOException;

import ch.ethz.idsc.gokart.offline.api.OfflineTableSupplier;
import ch.ethz.idsc.owl.bot.util.UserHome;
import ch.ethz.idsc.retina.lcm.OfflineLogPlayer;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.MatrixQ;
import ch.ethz.idsc.tensor.io.CsvFormat;
import ch.ethz.idsc.tensor.io.Export;

enum OfflineProcessing {
  ;
  public static void single(File file, OfflineTableSupplier offlineTableSupplier, String title) throws IOException {
    OfflineLogPlayer.process(file, offlineTableSupplier);
    Tensor table = offlineTableSupplier.getTable();
    if (Tensors.isEmpty(table)) {
      System.err.println("skip export: table is empty");
      return;
    }
    if (!MatrixQ.of(table))
      System.err.println("export does not have matrix structure");
    Export.of(UserHome.file(title + ".csv"), table.map(CsvFormat.strict()));
  }
}
