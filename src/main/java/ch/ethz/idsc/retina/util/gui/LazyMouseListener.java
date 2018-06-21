// code by jph
package ch.ethz.idsc.retina.util.gui;

import java.awt.event.MouseEvent;

public interface LazyMouseListener {
  void lazyClicked(MouseEvent myMouseEvent);

  default void lazyDragged(MouseEvent myMouseEvent) {
    // empty by default
  }
}
