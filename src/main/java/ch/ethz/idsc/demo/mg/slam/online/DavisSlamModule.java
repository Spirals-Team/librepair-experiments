// code by mg
package ch.ethz.idsc.demo.mg.slam.online;

import java.util.Optional;

import ch.ethz.idsc.gokart.core.pure.WaypointPurePursuitModule;
import ch.ethz.idsc.retina.sys.AbstractClockedModule;
import ch.ethz.idsc.retina.util.math.SI;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.qty.Quantity;

public class DavisSlamModule extends AbstractClockedModule {
  private final OnlineSlamWrap onlineSlamWrap = new OnlineSlamWrap();
  private final WaypointPurePursuitModule purePursuitModule = new WaypointPurePursuitModule();

  @Override // from AbstractModule
  protected void first() throws Exception {
    onlineSlamWrap.start();
    // ---
    purePursuitModule.launch();
  }

  @Override // from AbstractModule
  protected void last() {
    onlineSlamWrap.stop();
    // ---
    purePursuitModule.terminate();
  }

  @Override // from AbstractClockedModule
  protected void runAlgo() {
    Optional<Tensor> lookAhead = onlineSlamWrap.getSlamContainer().getLookAhead();
    purePursuitModule.setLookAhead(lookAhead);
  }

  @Override // from AbstractClockedModule
  protected Scalar getPeriod() {
    return Quantity.of(0.1, SI.SECOND);
  }

  /***************************************************/
  public static void standalone() throws Exception {
    DavisSlamModule slamModule = new DavisSlamModule();
    slamModule.launch();
  }

  public static void main(String[] args) throws Exception {
    standalone();
  }
}
