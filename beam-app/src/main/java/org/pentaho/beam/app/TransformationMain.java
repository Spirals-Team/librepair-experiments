package org.pentaho.beam.app;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.pentaho.beam.app.api.gson.TransMetaConverter;
import org.pentaho.beam.app.api.plugin.StepRegistry;
import org.pentaho.beam.app.api.trans.Transformation;
import org.pentaho.beam.app.api.transmeta.TransContext;
import org.pentaho.beam.app.api.transmeta.TransMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class TransformationMain {

  private static final Logger LOG = LoggerFactory.getLogger( TransformationMain.class );

  public static void main( String[] args ) {


    TransformationOptions options = PipelineOptionsFactory
      .fromArgs( args )
      .withValidation()
      .as( TransformationOptions.class );
    Pipeline p = Pipeline.create( options );

    StepRegistry stepRegistry = new StepRegistry();
    stepRegistry.init();
    TransContext transContext = new TransContext( p, stepRegistry );

    String transformationFile = options.getTransformationFile();
    TransMetaConverter converter = new TransMetaConverter(stepRegistry);
    TransMeta transMeta = converter.fromJson( new File( transformationFile ), options.getParameters() );
    Transformation transformation = new Transformation( transContext, transMeta );
    transformation.execute();


    LOG.error( "-------------------------------------" );
    //LOG.info( "debug: {}", data3.toString() );
    LOG.error( "-------------------------------------" );

    p.run().waitUntilFinish();
  }


}
