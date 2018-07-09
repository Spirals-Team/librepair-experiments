
package automation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

import static org.junit.Assume.assumeTrue;

import coaching.csv.CsvFile;
import coaching.csv.CsvRecord;

public class CredentialsFactory implements ExpectedDataInterface {

    private static final Logger LOG = LoggerFactory.getLogger(CredentialsFactory.class);
    private CsvFile csvFile;
    private String platform;

    /**
     * On.
     *
     * @param platform the platform
     * @return the credentials factory
     */
    public static CredentialsFactory on(final String platform) {
        return new CredentialsFactory(platform);
    }

    /**
     * The Constructor.
     */
    public CredentialsFactory() {
        super();
        loadFrom(pathForPlatform());
    }

    /**
     * The Constructor.
     *
     * @param platform the platform
     */
    public CredentialsFactory(final String platform) {
        super();
        this.platform = platform;
        loadFrom(pathForPlatform());
    }

    /**
     * Load from.
     *
     * @param credentialsFilename the credentials filename
     */
    private void loadFrom(final String credentialsFilename) {
        csvFile = new CsvFile(credentialsFilename);
    }

    /**
     * Path for platform.
     *
     * @return the string
     */
    private String pathForPlatform() {
        final String platform = System.getProperty("platform");
        if (platform == null) {
            return defaultPath();
        }
        return pathFor(platform);
    }

    /**
     * Path for.
     *
     * @param platform the platform
     * @return the string
     */
    private String pathFor(final String platform) {
        return String.format("/data/%s/Credentials.csv", platform);
    }

    /**
     * Default path.
     *
     * @return the string
     */
    private String defaultPath() {
        return "/data/Credentials.csv";
    }

    /*
     * (non-Javadoc)
     * @see automation.ExpectedDataInterface#onPlatform(java.lang.String)
     */
    @Override
    public CredentialsFactory onPlatform(final String platform) {
        this.platform = platform;
        return this;
    }

    /*
     * (non-Javadoc)
     * @see automation.ExpectedDataInterface#tagged(java.lang.String)
     */
    @Override
    public Actor tagged(final String tag) {
        assumeTrue(csvFile.isLoaded());
        final int rowCount = csvFile.rowCount();
        for (int index = 0; index < rowCount; index++) {
            final CsvRecord record = csvFile.getRecord(index);
            assertNotNull(record);
            LOG.trace(record.toString());
            if (record.getColumn(0).contains(tag)) {
                final Actor credentials = new Actor();
                assertNotNull(credentials);
                credentials.setUsername(record.getColumn(1));
                credentials.setEmail(record.getColumn(2));
                credentials.setPassword(record.getColumn(3));
                LOG.trace(record.toString());
                return credentials;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String
            .format("%s [platform=%s, csvFile=%s]", this.getClass().getSimpleName(), platform, csvFile);
    }

}
