package org.pentaho.beam.app.api.plugin;


import org.pentaho.beam.app.api.step.Step;
import org.pentaho.beam.app.api.stepmeta.StepMeta;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ccaspanello on 5/10/18.
 */
public class Steps {
  private Map<Class<? extends StepMeta>, Class<? extends Step>> steps;

  public Steps(){
    this.steps = new HashMap<>( );
  }

  public Map<Class<? extends StepMeta>, Class<? extends Step>> getSteps() {
    return steps;
  }

  public void setSteps(
    Map<Class<? extends StepMeta>, Class<? extends Step>> steps ) {
    this.steps = steps;
  }
}
