package org.pentaho.beam.app.api.stepmeta;

/**
 * Created by ccaspanello on 5/10/18.
 */
public class BaseStepMeta implements StepMeta {

  private final String name;

  public BaseStepMeta(String name) {
    this.name = name;
  }

  //<editor-fold desc="Getters & Setters">
  @Override
  public String getName() {
    return name;
  }
  //</editor-fold>
}
