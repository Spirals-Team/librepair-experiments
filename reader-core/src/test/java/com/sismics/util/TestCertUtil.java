package com.sismics.util;

import com.sismics.reader.core.util.http.ReaderHttpClient;
import com.sismics.util.cert.CertUtil;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.InputStream;
import java.net.URL;

/**
 * Test of the certificate utilities.
 * 
 * @author jtremeaux
 */
@Ignore
public class TestCertUtil {

    @Test
    public void sslFailTest() throws Exception {
        // This fails at the time of test, due to Startcom certificate not included in the JDK
        // javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed:
        // sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
        try {
            CertUtil.testSsl("lescastcodeurs.com", 443);
            Assert.fail();
        } catch (Exception e) {
            // NOP
        }
    }

    @Test
    public void sslTrustAllCertificateTest() throws Exception {
        new ReaderHttpClient() {
            @Override
            public void process(InputStream is) throws Exception {
                // NOP
            }
        }.open(new URL("https://lescastcodeurs.com"));
    }
}
