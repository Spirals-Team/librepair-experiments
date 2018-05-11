package org.pentaho.beam.app.api.transmeta;

import org.pentaho.beam.app.api.hop.HopMeta;
import org.pentaho.beam.app.api.stepmeta.StepMeta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccaspanello on 5/10/18.
 */
public class TransMeta implements Serializable {

  private final String name;
  private final List<StepMeta> steps;
  private final List<HopMeta> hops;

  public TransMeta( String name ) {
    this.name = name;
    this.steps = new ArrayList<>();
    this.hops = new ArrayList<>();
  }

  //<editor-fold desc="Getters & Setters">
  public String getName() {
    return name;
  }

  public List<StepMeta> getSteps() {
    return steps;
  }

  public List<HopMeta> getHops() {
    return hops;
  }
  //</editor-fold>

}
