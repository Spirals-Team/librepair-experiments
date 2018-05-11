package org.pentaho.beam.app.wordcount;

import org.apache.beam.sdk.metrics.Counter;
import org.apache.beam.sdk.metrics.Distribution;
import org.apache.beam.sdk.metrics.Metrics;
import org.apache.beam.sdk.transforms.DoFn;

/**
 * Created by ccaspanello on 4/10/18.
 */
public class ExtractWordsFn extends DoFn<String, String> {
  public static final String TOKENIZER_PATTERN = "[^\\p{L}]+";
  private final Counter emptyLines = Metrics.counter(ExtractWordsFn.class, "emptyLines");
  private final Distribution lineLenDist = Metrics.distribution(
    ExtractWordsFn.class, "lineLenDistro");

  @ProcessElement
  public void processElement(ProcessContext c) {
    lineLenDist.update(c.element().length());
    if (c.element().trim().isEmpty()) {
      emptyLines.inc();
    }

    // Split the line into words.
    String[] words = c.element().split(TOKENIZER_PATTERN);

    // Output each word encountered into the output PCollection.
    for (String word : words) {
      if (!word.isEmpty()) {
        c.output(word);
      }
    }
  }
}