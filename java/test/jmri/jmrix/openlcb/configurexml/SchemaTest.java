package jmri.jmrix.openlcb.configurexml;

import java.io.File;
import java.util.ArrayList;
import jmri.configurexml.SchemaTestBase;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Checks of JMRI XML Schema for OpenLCB files.
 *
 * @author Bob Jacobsen Copyright 2018
 * @since 2.9.3
 */
@RunWith(Parameterized.class)
public class SchemaTest extends SchemaTestBase {

    @Parameters(name = "{0} (pass={1})")
    public static Iterable<Object[]> data() {
        ArrayList<Object[]> files = new ArrayList<>();
        // check that the schema passes useful constructs
        files.addAll(getFiles(new File("java/test/jmri/jmrix/openlcb/configurexml/load"), true, true));
        return files;
    }

    public SchemaTest(File file, boolean pass) {
        super(file, pass);
    }
}
