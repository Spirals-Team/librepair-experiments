package org.pentaho.beam.app.api.step;

import org.apache.beam.sdk.Pipeline;
import org.pentaho.beam.app.api.hop.Hop;
import org.pentaho.beam.app.api.plugin.StepRegistry;
import org.pentaho.beam.app.api.stepmeta.StepMeta;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by ccaspanello on 5/10/18.
 */
public interface Step extends Serializable {

  /**
   * Executes Step Logic
   */
  void apply();

  //<editor-fold desc="Getters & Setters">
  void setPipeline( Pipeline pipeline );

  Pipeline getPipeline();

  void setStepRegistry( StepRegistry stepRegistry );

  StepRegistry getStepRegistry();

  StepMeta getStepMeta();

  Set<Hop> getIncoming();

  Set<Hop> getOutgoing();

  Set<String> getResultFiles();

  boolean isTerminating();

  //</editor-fold>
}
