package com.github.beam.etl;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.pentaho.beam.app.TransformationMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ccaspanello on 4/12/18.
 */
public class TransformationMainTest {

  private static final Logger LOG = LoggerFactory.getLogger( TransformationMainTest.class );

  @Test
  public void endToEndTest() throws Exception {

    String ktr = TransformationMainTest.class.getClassLoader().getResource( "basic.ktr").getFile();
    String input = TransformationMainTest.class.getClassLoader().getResource( "movies.csv" ).getFile();
    File output = new File(System.getProperty( "buildDirectory" )+"/test/output/basic");

    FileUtils.deleteQuietly( output.getParentFile() );

    Map<String, String> parameters = new HashMap<>();
    parameters.put("input", input);
    parameters.put("output", output.getAbsolutePath());

    Gson gson = new Gson();
    String sParameters = gson.toJson(parameters);
    TransformationMain.main( new String[] { "--transformationFile=" + ktr, "--parameters="+ sParameters } );
  }

}
