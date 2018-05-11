package org.pentaho.beam.app;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.StreamingOptions;
import org.apache.beam.sdk.options.Validation;

import java.util.Map;

/**
 * Created by ccaspanello on 5/9/18.
 */
public interface TransformationOptions extends PipelineOptions, StreamingOptions {

  @Description("Path of the transformation file")
  @Validation.Required
  String getTransformationFile();
  void setTransformationFile(String transformationFile);

  @Description("Runtime parameters")
  Map<String, String> getParameters();
  void setParameters(Map<String, String> parameters);
}
