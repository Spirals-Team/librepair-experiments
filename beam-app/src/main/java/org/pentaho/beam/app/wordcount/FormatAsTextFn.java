package org.pentaho.beam.app.wordcount;

import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;

/**
 * Created by ccaspanello on 4/10/18.
 */
public class FormatAsTextFn extends SimpleFunction<KV<String, Long>, String> {
  @Override
  public String apply(KV<String, Long> input) {
    return input.getKey() + ": " + input.getValue();
  }
}