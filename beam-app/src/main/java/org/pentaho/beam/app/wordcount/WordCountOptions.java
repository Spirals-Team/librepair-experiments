package org.pentaho.beam.app.wordcount;

import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.Validation;

public interface WordCountOptions extends PipelineOptions {

  @Description("Path of the file to read from")
  @Default.String("gs://apache-beam-samples/shakespeare/kinglear.txt")
  String getInputFile();
  void setInputFile(String value);

  @Description("Path of the file to write to")
  @Validation.Required
  String getOutput();
  void setOutput(String value);
}