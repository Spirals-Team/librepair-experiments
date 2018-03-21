package com.objectia.twostep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import com.objectia.Twostep;
import com.objectia.twostep.exception.APIConnectionException;
import com.objectia.twostep.exception.APIException;
import com.objectia.twostep.exception.TwostepException;
import com.objectia.twostep.model.Sms;
import com.objectia.twostep.model.User;
import com.objectia.twostep.model.Users;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TwostepTest {

    @Test
    public void testCreateUser() throws TwostepException {

        try {
            User user = Users.createUser("joe@java.org", "+12125551234", 1);
            assertNotNull(user);
            assertEquals("joe@java.org", user.getEmail());
        } catch (APIException ex) {
            fail("API: " + ex.getMessage());
        } catch (APIConnectionException ex) {
            fail("Connect: " + ex.getMessage());
        }
    }

    @Test
    public void testRequestSms() throws TwostepException {
        Map<String, Object> params = new HashMap<String, Object>();

        // Valid user
        params.put("force", false);
        Sms res = Users.requestSms("123456", params);
        assertNotNull(res);

        /*  // Valid user with force
        params.put("force", true);
        res = Users.requestSms("123456", params); 
        assertNotNull(res);
        //FIXME: check ignored
        
        // Invalid user
        try {
            params.put("force", false);
            res = Users.requestSms("000000", params); 
        } catch (TwostepException ex) {
            fail(ex.getMessage());
        }*/
    }

    @BeforeClass
    public static void setUp() {
        Twostep.clientId = System.getenv("TWOSTEP_CLIENT_ID");
        Twostep.clientSecret = System.getenv("TWOSTEP_CLIENT_SECRET");
        
        Twostep.setTokenStore(new MyTokenStore());

        // Avoid errors for self-signed cert
        TrustManager[] trustAllCerts = new TrustManager[] { new TrustAllManager() };

        // Ignore differences between given hostname and certificate hostname
        HostnameVerifier verifier = new TrustAllHostnameVerifier();

        // Install the all-trusting trust manager
        try {
            SSLContext context = SSLContext.getInstance("SSL");
            context.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(verifier);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {

    }
}