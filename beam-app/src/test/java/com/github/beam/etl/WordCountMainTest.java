package com.github.beam.etl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.pentaho.beam.app.WordCountMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by ccaspanello on 4/12/18.
 */
public class WordCountMainTest {

  private static final Logger LOG = LoggerFactory.getLogger( WordCountMainTest.class );

  @Test
  public void endToEndTest() throws Exception {

    File output = new File(System.getProperty( "buildDirectory" )+"/test/direct/counts");
    String input = WordCountMain.class.getClassLoader().getResource( "shakespeare.txt" ).getFile();

    FileUtils.deleteQuietly( output.getParentFile() );

    WordCountMain.main( new String[] { "--inputFile=" + input, "--output=" + output.getAbsolutePath() } );

    LOG.info( "Input: {}", input );
    LOG.info( "Output: {}", output.getAbsolutePath() );

    // Convert output to map for easy assertions
    Map<String, Integer> result = new HashMap<>();
    for ( File file : output.getParentFile().listFiles() ) {
      IOUtils.readLines( new FileInputStream( file ), Charset.defaultCharset() ).stream().forEach( line -> {
        String[] parts = line.split(": ");
        result.put(parts[0], Integer.parseInt( parts[1]));
      } );
    }

    // Assert 29 unique words
    assertEquals( 29, result.size() );

    // Spot check a couple
    assertEquals(1, result.get("To").intValue());
    assertEquals(2, result.get("be").intValue());
    assertEquals(1, result.get("or").intValue());
    assertEquals(1, result.get("not").intValue());
    assertEquals(3, result.get("to").intValue());
  }
}
