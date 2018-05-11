package org.pentaho.beam.app;

import org.apache.beam.sdk.options.PipelineOptions;
import org.pentaho.beam.app.wordcount.CountWords;
import org.pentaho.beam.app.wordcount.FormatAsTextFn;
import org.pentaho.beam.app.wordcount.WordCountOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO Replace tutorial code with ETL program
 */
public class WordCountMain {

  private static final Logger LOG = LoggerFactory.getLogger( WordCountMain.class );

  public static void main( String[] args ) {
    WordCountOptions options = PipelineOptionsFactory.fromArgs( args ).withValidation().as( WordCountOptions.class );
    Pipeline p = Pipeline.create( options );

    String input = options.getInputFile();
    String output = options.getOutput();

    PCollection<String> data = p.apply( "ReadLines", TextIO.read().from( input ) );
    PCollection<KV<String, Long>> data2 = data.apply( "Count Words", new CountWords() );
    PCollection<String> data3 = data2.apply( "Format as Text", MapElements.via( new FormatAsTextFn() ) );
    data3.apply( "WriteCounts", TextIO.write().to( output ) );

    LOG.error( "-------------------------------------" );
    LOG.info( "debug: {}", data3.toString() );
    LOG.error( "-------------------------------------" );

    p.run().waitUntilFinish();
  }
}
