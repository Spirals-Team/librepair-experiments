package org.pentaho.beam.app.api.transmeta;


import org.apache.beam.sdk.Pipeline;
import org.pentaho.beam.app.api.plugin.StepRegistry;

/**
 * TODO Make Thread Safe if used inside executors
 * <p>
 * Created by ccaspanello on 2/4/18.
 */
public class TransContext {

  private final Pipeline pipeline;
  private final StepRegistry stepRegistry;

  public TransContext( Pipeline pipeline, StepRegistry stepRegistry ) {
    this.pipeline = pipeline;
    this.stepRegistry = stepRegistry;
  }

  public Pipeline getPipeline() {
    return pipeline;
  }

  public StepRegistry getStepRegistry() {
    return stepRegistry;
  }
}
