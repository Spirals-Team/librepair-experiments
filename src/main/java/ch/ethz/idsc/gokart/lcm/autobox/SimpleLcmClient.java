// code by jph
package ch.ethz.idsc.gokart.lcm.autobox;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class SimpleLcmClient<L> extends BinaryLcmClient {
  protected final List<L> listeners = new CopyOnWriteArrayList<>();

  public final void addListener(L listener) {
    listeners.add(listener);
  }
}
