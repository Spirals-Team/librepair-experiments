package com.objectia.twostep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import com.objectia.Twostep;
import com.objectia.twostep.exception.TwostepException;
import com.objectia.twostep.model.Response2;
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
            assertEquals("joe@java.org", user.getEmail());
            //assertEquals(true, res.getIgnored()); //FIXME

        } catch (TwostepException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testRequestSms() throws TwostepException {
        // Valid user
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("force", false);
    
            Response2 res = Users.requestSms("123456", params); 
            assertEquals(200, res.getStatus());
            //assertEquals(true, res.getIgnored()); //FIXME

        } catch (TwostepException ex) {
            fail(ex.getMessage());
        }

        // Valid user with force
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("force", true);
    
            Response2 res = Users.requestSms("123456", params);
            assertEquals(200, res.getStatus());
            //assertEquals(false, res.getIgnored()); //FIXME
        } catch (TwostepException ex) {
            fail(ex.getMessage());
        }
        
        // Invalid user
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("force", false);
    
            Response2 res = Users.requestSms("000000", params);
            assertEquals(405, res.getStatus());
        } catch (TwostepException ex) {
            fail(ex.getMessage());
        }
    }

    @BeforeClass
    public static void setUp() {
        // Init client
        Twostep.clientId = System.getenv("TWOSTEP_CLIENT_ID");
        Twostep.clientSecret = System.getenv("TWOSTEP_CLIENT_SECRET");;

        Twostep.setTokenStore(new MyTokenStore());


        // Avoid errors for self-seigned cert
        TrustManager[] trustAllCerts = new TrustManager[] { new TrustAllManager() };

        // Ignore differences between given hostname and certificate hostname
        HostnameVerifier hv = new TrustAllHostnameVerifier();

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL"); //TLS?
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {
        
    }
}