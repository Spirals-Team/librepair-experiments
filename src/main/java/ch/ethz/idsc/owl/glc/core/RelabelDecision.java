// code by ynager
package ch.ethz.idsc.owl.glc.core;

/** TODO is this interface simply a (semi-)order relation: irreflexible, ... */
public interface RelabelDecision {
  /** @param newNode
   * @param oldNode
   * @return */
  boolean doRelabel(GlcNode newNode, GlcNode oldNode);
}
