package org.corfudb.security.tls;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Enumeration;
import javax.net.ssl.SSLException;
import org.corfudb.test.CorfuTest;
import org.junit.Test;
import sun.misc.BASE64Encoder;
import sun.security.provider.X509Factory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@CorfuTest
public class TlsUtilsTest {
    private String PASSWORD_FILE = "src/test/resources/security/reload/password";
    private String KEY_STORE_FILE = "src/test/resources/security/reload/client_trust_with_server.jks";

    @CorfuTest
    public void testGetPassword() throws Exception {
        String password = TlsUtils.getKeyStorePassword(PASSWORD_FILE);
        assertEquals("password", password);
    }

    @CorfuTest
    public void testGetEmptyPassword() throws Exception {
        String password = TlsUtils.getKeyStorePassword(null);
        assertEquals("", password);
    }

    @CorfuTest
    public void testBadPasswordFile() throws Exception {
        try {
            TlsUtils.getKeyStorePassword("definitely fake location");
            assertFalse(true);
        } catch (SSLException e) {
            assertTrue(e.getMessage().startsWith("Unable to read password file"));
        }
    }

    @CorfuTest
    public void testOpenKeyStore() throws Exception {
        String password = TlsUtils.getKeyStorePassword(PASSWORD_FILE);
        KeyStore keyStore = TlsUtils.openKeyStore(KEY_STORE_FILE, password);
        assertEquals(2, keyStore.size());
    }

    @CorfuTest
    public void testOpenKeyStoreBadPassword() throws Exception {
        try {
            TlsUtils.openKeyStore(KEY_STORE_FILE, "fake password");
        } catch (SSLException e) {
            assertEquals("Keystore was tampered with, or password was incorrect",
                    e.getCause().getMessage());
        }
    }

    @CorfuTest
    public void testOpenKeyStoreBadLocation() throws Exception {
        try {
            TlsUtils.openKeyStore("definitely fake location", "fake password");
        } catch (SSLException e) {
            assertTrue(e.getMessage().endsWith("doesn't exist."));
        }
    }
}