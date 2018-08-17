package org.corfudb.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.corfudb.runtime.CorfuRuntime;
import org.corfudb.runtime.collections.CorfuTable;
import org.corfudb.runtime.exceptions.unrecoverable.UnrecoverableCorfuError;
import org.corfudb.util.NodeLocator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This test suit exercises the ability to enable TLS on Corfu servers and runtime
 *
 * Created by Sam Behnam on 8/13/18.
 */
@Slf4j
public class SecurityIT extends AbstractIT {
    private String corfuSingleNodeHost;
    private int corfuStringNodePort;
    private String singleNodeEndpoint;

    private boolean tlsEnabled;
    private String serverPathToKeyStore;
    private String serverPathToKeyStorePassword;
    private String serverPathToTrustStore;
    private String serverPathToTrustStorePassword;
    private String runtimePathToKeyStore;
    private String runtimePathToKeyStorePass;
    private String runtimePathToTrustStore;
    private String runtimePathToTrustStorePass;

    /* A helper method that start a single TLS enabled server and returns a process. */
    private Process runSinglePersistentServerTls() throws IOException {
        return new AbstractIT.CorfuServerRunner()
                .setHost(corfuSingleNodeHost)
                .setPort(corfuStringNodePort)
                .setTlsEnabled(tlsEnabled)
                .setKeyStore(serverPathToKeyStore)
                .setKeyStorePassword(serverPathToKeyStorePassword)
                .setTrustStore(serverPathToTrustStore)
                .setTrustStorePassword(serverPathToTrustStorePassword)
                .setLogPath(getCorfuServerLogPath(corfuSingleNodeHost, corfuStringNodePort))
                .setSingle(true)
                .runServer();
    }

    /**
     * This is a helper method that load the properties required for creating corfu servers before
     * each test by converting them from the values provided in CorfuDB.properties. Note that it
     * will throw {@link NumberFormatException} if the numerical properties provided in CorfuDB.properties
     * are not well-formed.
     */
    @Before
    public void loadProperties() {
        // Load host and port properties
        corfuSingleNodeHost = PROPERTIES.getProperty("corfuSingleNodeHost");
        corfuStringNodePort = Integer.parseInt(PROPERTIES.getProperty("corfuSingleNodePort"));

        singleNodeEndpoint = String.format("%s:%d",
                corfuSingleNodeHost,
                corfuStringNodePort);

        // Load TLS configuration, keystore and truststore properties
        tlsEnabled = Boolean.valueOf(PROPERTIES.getProperty("tlsEnabledSecurityIT"));
        serverPathToKeyStore = getPropertyAbsolutePath("serverPathToKeyStore");
        serverPathToKeyStorePassword = getPropertyAbsolutePath("serverPathToKeyStorePassword");
        serverPathToTrustStore = getPropertyAbsolutePath("serverPathToTrustStore");
        serverPathToTrustStorePassword = getPropertyAbsolutePath("serverPathToTrustStorePassword");
        runtimePathToKeyStore = getPropertyAbsolutePath("runtimePathToKeyStore");
        runtimePathToKeyStorePass = getPropertyAbsolutePath("runtimePathToKeyStorePass");
        runtimePathToTrustStore = getPropertyAbsolutePath("runtimePathToTrustStore");
        runtimePathToTrustStorePass = getPropertyAbsolutePath("runtimePathToTrustStorePass");
    }

    // Take the property string provided int CorfuDB.properties and return the absolute path string
    private String getPropertyAbsolutePath(String pathProperty) {
        return Paths.get(PROPERTIES.getProperty(pathProperty)).toAbsolutePath().toString();
    }

    /**
     * This test creates Corfu runtime and a single Corfu server according to the configuration
     * provided in CorfuDB.properties. Corfu runtime configures TLS related parameters using
     * {@link CorfuRuntime}'s API and then asserts that operations on a CorfuTable is executed
     * as Expected.
     *
     * @throws Exception
     */
    @Test
    public void testServerRuntimeTlsEnabledMethod() throws Exception {
        // Run a corfu server
        Process corfuServer = runSinglePersistentServerTls();

        // Start a Corfu runtime
        CorfuRuntime corfuRuntime = new CorfuRuntime(singleNodeEndpoint)
                                    .enableTls(runtimePathToKeyStore,
                                               runtimePathToKeyStorePass,
                                               runtimePathToTrustStore,
                                               runtimePathToTrustStorePass)
                                    .setCacheDisabled(true)
                                    .connect();

        // Create CorfuTable
        CorfuTable testTable = corfuRuntime
                .getObjectsView()
                .build()
                .setTypeToken(new TypeToken<CorfuTable<String, Object>>() {})
                .setStreamName("volbeat")
                .open();

        // CorfuTable stats before usage
        final int initialSize = testTable.size();

        // Put key values in CorfuTable
        final int count = 100;
        final int entrySize = 1000;
        for (int i = 0; i < count; i++) {
            testTable.put(String.valueOf(i), new byte[entrySize]);
        }

        // Assert that put operation was successful
        final int sizeAfterPuts = testTable.size();
        assertThat(sizeAfterPuts).isGreaterThanOrEqualTo(initialSize);
        log.info("Initial Table Size: {} - FinalTable Size:{}", initialSize, sizeAfterPuts);

        // Assert that table has correct size (i.e. count) and and server is shutdown
        assertThat(testTable.size()).isEqualTo(count);
        assertThat(shutdownCorfuServer(corfuServer)).isTrue();
    }

    /**
     * This test creates Corfu runtime and a single Corfu server according to the configuration
     * provided in CorfuDB.properties. Corfu runtime configures TLS related parameters using
     * {@link org.corfudb.runtime.CorfuRuntime.CorfuRuntimeParameters} and then asserts that
     * operations on a CorfuTable is executed as Expected.
     *
     * @throws Exception
     */
    @Test
    public void testServerRuntimeTlsEnabledByParameter() throws Exception {
        // Run a corfu server
        Process corfuServer = runSinglePersistentServerTls();

        // Create Runtime parameters for enabling TLS
        final CorfuRuntime.CorfuRuntimeParameters runtimeParameters = CorfuRuntime.CorfuRuntimeParameters
                .builder()
                .layoutServer(NodeLocator.parseString(singleNodeEndpoint))
                .tlsEnabled(tlsEnabled)
                .keyStore(runtimePathToKeyStore)
                .ksPasswordFile(runtimePathToKeyStorePass)
                .trustStore(runtimePathToTrustStore)
                .tsPasswordFile(runtimePathToTrustStorePass)
                .build();

        // Start a Corfu runtime from parameters
        CorfuRuntime corfuRuntime = CorfuRuntime
                .fromParameters(runtimeParameters)
                .connect();

        // Create CorfuTable
        CorfuTable testTable = corfuRuntime
                .getObjectsView()
                .build()
                .setTypeToken(new TypeToken<CorfuTable<String, Object>>() {})
                .setStreamName("volbeat")
                .open();

        // CorfuTable stats before usage
        final int initialSize = testTable.size();

        // Put key values in CorfuTable
        final int count = 100;
        final int entrySize = 1000;
        for (int i = 0; i < count; i++) {
            testTable.put(String.valueOf(i), new byte[entrySize]);
        }

        // Assert that put operation was successful
        final int sizeAfterPuts = testTable.size();
        assertThat(sizeAfterPuts).isGreaterThanOrEqualTo(initialSize);
        log.info("Initial Table Size: {} - FinalTable Size:{}", initialSize, sizeAfterPuts);

        // Assert that table has correct size (i.e. count) and and server is shutdown
        assertThat(testTable.size()).isEqualTo(count);
        assertThat(shutdownCorfuServer(corfuServer)).isTrue();
    }

    /**
     * Testing that configuring incorrect TLS parameters will lead to throwing
     * {@link UnrecoverableCorfuError} exception.
     *
     * @throws Exception
     */
    @Test(expected = UnrecoverableCorfuError.class)
    public void testIncorrectKeyStoreException() throws Exception {
        // Run a corfu server with incorrect truststore
        Process corfuServer = runSinglePersistentServerTls();

        // Start a Corfu runtime with incorrect truststore
        final CorfuRuntime.CorfuRuntimeParameters runtimeParameters = CorfuRuntime.CorfuRuntimeParameters
                .builder()
                .layoutServer(NodeLocator.parseString(singleNodeEndpoint))
                .tlsEnabled(tlsEnabled)
                .keyStore(runtimePathToKeyStore)
                .ksPasswordFile(runtimePathToKeyStorePass)
                .trustStore(runtimePathToKeyStore)
                .tsPasswordFile(runtimePathToTrustStorePass)
                .build();

        // Connecting to runtime
        CorfuRuntime corfuRuntime = CorfuRuntime
                .fromParameters(runtimeParameters)
                .connect();
    }
}
