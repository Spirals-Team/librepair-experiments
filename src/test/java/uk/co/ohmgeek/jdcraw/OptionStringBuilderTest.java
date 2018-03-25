package uk.co.ohmgeek.jdcraw;

import junit.framework.JUnit4TestCaseFacade;
import junit.framework.TestCase;
import uk.co.ohmgeek.jdcraw.*;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 29/06/17.
 */
public class OptionStringBuilderTest extends TestCase {
    public void testOptionBuilderWorksAsExpected() throws Exception {
        List<String> testArgs = new ArrayList<String>();
        testArgs.add("-w");
        testArgs.add("-o");
        File f = new File("~/Documents/img.jpg");

        // get the output from the module
        List<String> result = OptionStringBuilder.build(testArgs, f);

        // now build what we expect
        // we expect the following output:
        // dcraw -w -o <PATH TO FILE>
        List<String> expectedResults = new ArrayList<String>();
        expectedResults.add("dcraw");
        expectedResults.add("-w");
        expectedResults.add("-o");
        expectedResults.add(f.getPath());

        assertEquals(result, expectedResults);
    }
}
