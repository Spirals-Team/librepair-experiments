/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Chris Caspanello
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.pentaho.test.direct.runner;

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
 * Created by ccaspanello on 4/10/18.
 */
public class DirectRunnerTest {

  @Test
  public void test() {
    String output = System.getProperty( "buildDirectory" ) + "/test/direct/counts";
    WordCountMain.main( new String[] { "--inputFile=pom.xml", "--output=" + output } );
  }

  @Test
  public void testBasic() throws Exception {
    File buildDirectory = new File( System.getProperty( "buildDirectory" ) );
    File testsDir = new File(buildDirectory.getParentFile().getParentFile(),"tests");

    File ktr = new File( testsDir, "basic.ktr" );
    File input = new File( testsDir, "movies.csv" );
    File output = new File( buildDirectory + "/direct-output/basic" );

    FileUtils.deleteQuietly( output.getParentFile() );

    Map<String, String> parameters = new HashMap<>();
    parameters.put( "input", input.getAbsolutePath() );
    parameters.put( "output", output.getAbsolutePath() );

    Gson gson = new Gson();
    String sParameters = gson.toJson( parameters );
    TransformationMain.main( new String[] { "--transformationFile=" + ktr, "--parameters=" + sParameters } );

    int count = IOUtils.readLines( new FileReader( output ) ).size();
    assertEquals(40, count);
  }
}
