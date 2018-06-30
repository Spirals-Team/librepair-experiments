// code by jph
package ch.ethz.idsc.owl.rrts.core;

public interface TransitionRegionQuery {
  /** @param transition
   * @return true, if the transition does not intersect this region */
  boolean isDisjoint(Transition transition);
}
