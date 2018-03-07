package fr.inria.diversify.dspot;

import fr.inria.diversify.Utils;
import fr.inria.diversify.automaticbuilder.AutomaticBuilderFactory;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;

import java.io.File;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inriAVa.fr
 * on 1/5/17
 */
public abstract class MavenAbstractTest {

    public final String pathToPropertiesFile = getPathToPropertiesFile();

    public static final String nl = System.getProperty("line.separator");

    @Before
    public void setUp() throws Exception {
        Utils.reset();
        try {
            FileUtils.forceDelete(new File("target/dspot/"));
        } catch (Exception ignored) {

        }
    }

    public abstract String getPathToPropertiesFile();

}
