package org.pentaho.test.dataflow.runner;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.pentaho.beam.app.TransformationMain;
import org.pentaho.beam.app.WordCountMain;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by ccaspanello on 5/9/18.
 */
public class DataflowRunnerTest {


  @Test
  public void test() {
    String output = System.getProperty( "buildDirectory" ) + "/test/apex/counts";
    WordCountMain.main( new String[] {
      "--runner=DataflowRunner",
      "--project=dataflow-etl",
      "--gcpTempLocation=gs://pentaho-beam-app/temp/",
      "--inputFile=gs://apache-beam-samples/shakespeare/*",
      "--output=gs://pentaho-beam-app/counts"});
  }

  /** TODO Tailor for Dataflow
   *
   * @throws Exception
   */
  @Test
  public void testBasic() throws Exception {
    File buildDirectory = new File( System.getProperty( "buildDirectory" ) );
    File testsDir = new File(buildDirectory.getParentFile().getParentFile(),"tests");

    File ktr = new File( testsDir, "basic.ktr" );
    File input = new File( testsDir, "movies.csv" );
    File output = new File( buildDirectory + "/dataflow-output/basic" );

    FileUtils.deleteQuietly( output.getParentFile() );

    Map<String, String> parameters = new HashMap<>();
    parameters.put("input", input.getAbsolutePath());
    parameters.put("output", output.getAbsolutePath());

    Gson gson = new Gson();
    String sParameters = gson.toJson(parameters);
    TransformationMain.main( new String[] { "--runner=DataflowRunner","--transformationFile=" + ktr, "--parameters="+ sParameters } );

    int count = IOUtils.readLines( new FileReader( output ) ).size();
    assertEquals(40, count);
  }

}
