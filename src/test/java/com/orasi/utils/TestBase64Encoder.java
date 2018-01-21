package com.orasi.utils;

import java.util.Arrays;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.orasi.web.WebBaseTest;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestBase64Encoder extends WebBaseTest {
    char[] testByteChars = null;
    byte[] testByteBytes = null;
    byte[] testByteArraytoTest = new byte[] { 43, 23, 64, 78, 24, 14, 124, 12, 23, 47, 43, 21, 23, 14, 45, 68, 84, 93, 2, 23, 114, 45, 35, 35, 23, 3, 32, 34 };

    @BeforeTest
    public void setup() {
        setReportToMustard(false);
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult testResults) {
    }

    @Features("Utilities")
    @Stories("Base64Coder")
    @Title("encodeByte")
    @Test(groups = { "regression", "smoke" })
    public void encodeByte() {
        testByteChars = Base64Coder.encode(testByteArraytoTest);
        Assert.assertTrue(testByteChars.length > 0);
        Assert.assertTrue(Arrays.equals(testByteChars, "KxdAThgOfAwXLysVFw4tRFRdAhdyLSMjFwMgIg==".toCharArray()));
    }

    @Features("Utilities")
    @Stories("Base64Coder")
    @Title("decodeByte")
    @Test(groups = "regression", dependsOnMethods = "encodeByte")
    public void decodeByte() {
        testByteBytes = Base64Coder.decode(testByteChars);
        Assert.assertTrue(Arrays.equals(testByteBytes, testByteArraytoTest));
    }

    @Features("Utilities")
    @Stories("Base64Coder")
    @Title("encodeSizes")
    @Test(groups = "regression")
    public void encodeSizes() {
        Random random = new Random();
        byte[] orig1024 = new byte[1024];
        random.nextBytes(orig1024);

        byte[] orig2048 = new byte[2048];
        random.nextBytes(orig2048);

        byte[] orig4096 = new byte[4096];
        random.nextBytes(orig4096);

        byte[] orig8192 = new byte[8192];
        random.nextBytes(orig8192);

        char[] enc1024 = Base64Coder.encode(orig1024);
        char[] enc2048 = Base64Coder.encode(orig2048);
        char[] enc4096 = Base64Coder.encode(orig4096);
        char[] enc8192 = Base64Coder.encode(orig8192);

        byte[] dec1024 = Base64Coder.decode(enc1024);
        byte[] dec2048 = Base64Coder.decode(enc2048);
        byte[] dec4096 = Base64Coder.decode(enc4096);
        byte[] dec8192 = Base64Coder.decode(enc8192);

        Assert.assertTrue(Arrays.equals(orig1024, dec1024));
        Assert.assertTrue(Arrays.equals(orig2048, dec2048));
        Assert.assertTrue(Arrays.equals(orig4096, dec4096));
        Assert.assertTrue(Arrays.equals(orig8192, dec8192));
    }

    @Features("Utilities")
    @Stories("Base64Coder")
    @Title("encodeString")
    @Test(groups = "regression")
    public void encodeString() {
        char base64_encoded_test[] = ("U28/PHA+VGhpcyA0LCA1LCA2LCA3LCA4LCA5LCB6LCB7LCB8LCB9IHRlc3RzIEJhc2U2NCBlbmNv" +
                "ZGVyLiBTaG93IG1lOiBALCBBLCBCLCBDLCBELCBFLCBGLCBHLCBILCBJLCBKLCBLLCBMLCBNLCBO" +
                "LCBPLCBQLCBRLCBSLCBTLCBULCBVLCBWLCBXLCBYLCBZLCBaLCBbLCBcLCBdLCBeLCBfLCBgLCBh" +
                "LCBiLCBjLCBkLCBlLCBmLCBnLCBoLCBpLCBqLCBrLCBsLCBtLCBuLCBvLCBwLCBxLCByLCBzLg==").toCharArray();

        String test_string = "So?<p>" +
                "This 4, 5, 6, 7, 8, 9, z, {, |, } tests Base64 encoder. " +
                "Show me: @, A, B, C, D, E, F, G, H, I, J, K, L, M, " +
                "N, O, P, Q, R, S, T, U, V, W, X, Y, Z, [, \\, ], ^, _, `, " +
                "a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s.";

        Assert.assertTrue(Arrays.equals(Base64Coder.encodeString(test_string).toCharArray(), base64_encoded_test));
    }

    @Features("Utilities")
    @Stories("Base64Coder")
    @Title("decodeString")
    @Test(groups = "regression")
    public void decodeString() {
        String base64_decoded_test = "U28/PHA+VGhpcyA0LCA1LCA2LCA3LCA4LCA5LCB6LCB7LCB8LCB9IHRlc3RzIEJhc2U2NCBlbmNv" +
                "ZGVyLiBTaG93IG1lOiBALCBBLCBCLCBDLCBELCBFLCBGLCBHLCBILCBJLCBKLCBLLCBMLCBNLCBO" +
                "LCBPLCBQLCBRLCBSLCBTLCBULCBVLCBWLCBXLCBYLCBZLCBaLCBbLCBcLCBdLCBeLCBfLCBgLCBh" +
                "LCBiLCBjLCBkLCBlLCBmLCBnLCBoLCBpLCBqLCBrLCBsLCBtLCBuLCBvLCBwLCBxLCByLCBzLg==";

        char test_string[] = ("So?<p>" +
                "This 4, 5, 6, 7, 8, 9, z, {, |, } tests Base64 encoder. " +
                "Show me: @, A, B, C, D, E, F, G, H, I, J, K, L, M, " +
                "N, O, P, Q, R, S, T, U, V, W, X, Y, Z, [, \\, ], ^, _, `, " +
                "a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s.").toCharArray();

        Assert.assertTrue(Arrays.equals(Base64Coder.decodeString(base64_decoded_test).toCharArray(), test_string));
    }

    @Features("Utilities")
    @Stories("Base64Coder")
    @Title("encodeLine")
    @Test(groups = "regression")
    public void encodeLine() {
        String test_string = "So?<p>" +
                "This 4, 5, 6, 7, 8, 9, z, {, |, } tests Base64 encoder. " +
                "Show me: @, A, B, C, D, E, F, G, H, I, J, K, L, M, " +
                "N, O, P, Q, R, S, T, U, V, W, X, Y, Z, [, \\, ], ^, _, `, " +
                "a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s.";
        byte[] bytesEncoded = Base64.encodeBase64(test_string.getBytes());
        Assert.assertNotNull(Base64Coder.encodeLines(bytesEncoded));
    }

    @Features("Utilities")
    @Stories("Base64Coder")
    @Title("decodeLine")
    @Test(groups = "regression")
    public void decodeLine() {
        String test_string = "VTI4L1BIQStWR2hwY3lBMExDQTFMQ0EyTENBM0xDQTRMQ0E1TENCNkxDQjdMQ0I4TENCOUlIUmxj\n" +
                "M1J6SUVKaGMyVTJOQ0JsYm1OdlpHVnlMaUJUYUc5M0lHMWxPaUJBTENCQkxDQkNMQ0JETENCRUxD\n" +
                "QkZMQ0JHTENCSExDQklMQ0JKTENCS0xDQkxMQ0JNTENCTkxDQk9MQ0JQTENCUUxDQlJMQ0JTTENC\n" +
                "VExDQlVMQ0JWTENCV0xDQlhMQ0JZTENCWkxDQmFMQ0JiTENCY0xDQmRMQ0JlTENCZkxDQmdMQ0Jo\n" +
                "TENCaUxDQmpMQ0JrTENCbExDQm1MQ0JuTENCb0xDQnBMQ0JxTENCckxDQnNMQ0J0TENCdUxDQnZM\n" +
                "Q0J3TENCeExDQnlMQ0J6TGc9PQ==";
        Assert.assertNotNull(Base64Coder.decodeLines(test_string));
    }

    @Features("Utilities")
    @Stories("Base64Coder")
    @Title("constructor")
    @Test(groups = "regression")
    public void constructor() {
        Assert.assertNotNull(new Base64Coder());
    }

}
