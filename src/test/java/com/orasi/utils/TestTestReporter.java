package com.orasi.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.orasi.api.restServices.RestResponse;
import com.orasi.api.restServices.RestService;
import com.orasi.api.restServices.exceptions.RestException;
import com.orasi.api.soapServices.exceptions.SoapException;
import com.orasi.api.soapServices.helpers.GetInfoByZip;
import com.orasi.web.OrasiDriver;
import com.orasi.web.WebBaseTest;

public class TestTestReporter extends WebBaseTest {
    OrasiDriver driver = null;

    @AfterClass
    public void cleanup() {
        TestReporter.setDebugLevel(0);
        driver.quit();
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult testResults) {
    }

    @Test
    public void testAssertEquals() {
        String log = "testAssertEquals";
        TestReporter.assertEquals(true, true, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testAssertEqualsFail() {
        String log = "testAssertEqualsfail";
        TestReporter.assertEquals(false, true, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testSoftAssertEquals() {
        String log = "testSoftAssertEquals";
        TestReporter.softAssertEquals(true, true, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testSoftAssertEqualsFail() {
        String log = "testSoftAssertEqualsFail";
        TestReporter.softAssertEquals(false, true, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testAssertFalse() {
        String log = "testAssertFalse";
        TestReporter.assertFalse(false, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testAssertFalseFail() {
        String log = "testAssertFalse";
        TestReporter.assertFalse(true, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testSoftAssertFalse() {
        String log = "testSoftAssertFalse";
        TestReporter.softAssertFalse(false, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testSoftAssertFalseFail() {
        String log = "testSoftAssertFalseFail";
        TestReporter.softAssertFalse(true, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testSoftAssertTrue() {
        String log = "testSoftAssertTrue";
        TestReporter.softAssertTrue(true, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testSoftAssertTrueFail() {
        String log = "testSoftAssertTrueFail";
        TestReporter.softAssertTrue(false, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testAssertGreaterThanZeroInt() {
        String log = "Assert 1 is greater than zero";
        TestReporter.assertGreaterThanZero(1);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testAssertGreaterThanZeroIntFail() {
        String log = "Assert 0 is greater than zero";
        TestReporter.assertGreaterThanZero(0);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testAssertGreaterThanZeroFloat() {
        String log = "Assert 1 is greater than zero";
        TestReporter.assertGreaterThanZero(Float.valueOf("1.0"));
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testAssertGreaterThanZeroFloatFail() {
        String log = "Assert 0 is greater than zero";
        TestReporter.assertGreaterThanZero(Float.valueOf("0"));
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testAssertGreaterThanZeroDouble() {
        String log = "Assert 1 is greater than zero";
        TestReporter.assertGreaterThanZero(1.1);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testAssertGreaterThanZeroDoubleFail() {
        String log = "Assert 0 is greater than zero";
        TestReporter.assertGreaterThanZero(0);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testAssertNotEquals() {
        String log = "testAssertNotEquals";
        TestReporter.assertNotEquals(1, 2, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testAssertNotEqualsFail() {
        String log = "testAssertNotEquals";
        TestReporter.assertNotEquals(1, 1, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testAssertNotNull() {
        String log = "testAssertNotNull";
        TestReporter.assertNotNull(1, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testAssertNotNullFail() {
        String log = "testAssertNotNull";
        TestReporter.assertNotNull(null, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testSoftAssertNotNull() {
        String log = "testSoftAssertNotNull";
        TestReporter.softAssertNotNull(log, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testSoftAssertNotNullFail() {
        String log = "testSoftAssertNotNullFail";
        TestReporter.softAssertNotNull(null, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testAssertNull() {
        String log = "testAssertNull";
        TestReporter.assertNull(null, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testAssertNullFail() {
        String log = "testAssertNull";
        TestReporter.assertNull(log, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testSoftAssertNull() {
        String log = "testSoftAssertNull";
        TestReporter.softAssertNull(null, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testSoftAssertNullFail() {
        String log = "testSoftAssertNullFail";
        TestReporter.softAssertNull(log, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(dependsOnMethods = { "testSoftAssertNull", "testSoftAssertNullFail" }, expectedExceptions = AssertionError.class)
    public void testAssertAll() {
        TestReporter.assertAll();
    }

    @Test
    public void testAssertTrue() {
        String log = "testAssertTrue";
        TestReporter.assertTrue(true, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testAssertTrueFail() {
        String log = "testAssertTrue";
        TestReporter.assertTrue(false, log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testGetDebugLevel() {
        Assert.assertTrue(TestReporter.getDebugLevel() == 0);
    }

    @Test(dependsOnMethods = { "testGetDebugLevel" })
    public void testSetDebugLevel() {
        TestReporter.setDebugLevel(3);
        Assert.assertTrue(TestReporter.getDebugLevel() == 3);
    }

    @Test
    public void testGetPrintFullClassPath() {
        Assert.assertTrue(TestReporter.getPrintFullClassPath() == true);
    }

    @Test(dependsOnMethods = { "testGetPrintFullClassPath" })
    public void testSetPrintFullClassPath() {
        TestReporter.setPrintFullClassPath(false);
        Assert.assertTrue(TestReporter.getPrintFullClassPath() == false);
        TestReporter.setPrintFullClassPath(true);
    }

    @Test
    public void testGetPrintToConsole() {
        Assert.assertTrue(TestReporter.getPrintToConsole() == true);
    }

    @Test(dependsOnMethods = { "testGetPrintToConsole" })
    public void testSetPrintToConsole() {
        TestReporter.setPrintToConsole(false);
        Assert.assertTrue(TestReporter.getPrintToConsole() == false);
        TestReporter.setPrintToConsole(true);
    }

    @Test(dependsOnMethods = { "testSetDebugLevel" })
    public void testInterfaceLog() {
        TestReporter.setDebugLevel(2);
        String log = "testInterfaceLog";
        TestReporter.interfaceLog(log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(dependsOnMethods = { "testSetDebugLevel" })
    public void testInterfaceLogNoFail() {
        TestReporter.setDebugLevel(2);
        String log = "testInterfaceLognoFail";
        TestReporter.interfaceLog(log, false);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(dependsOnMethods = { "testSetDebugLevel" })
    public void testInterfaceLogFail() {
        TestReporter.setDebugLevel(2);
        String log = "testInterfaceLogFail";
        TestReporter.interfaceLog(log, true);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(dependsOnMethods = { "testSetDebugLevel" })
    public void testLog() {
        String log = "testLog";
        TestReporter.log(log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(dependsOnMethods = { "testSetDebugLevel" })
    public void testLogDebug() {
        String log = "testLogDebug";
        TestReporter.logDebug(log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(dependsOnMethods = { "testSetDebugLevel" })
    public void testLogFailure() {
        TestReporter.setDebugLevel(2);
        String log = "testLogFailure";
        TestReporter.logFailure(log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(dependsOnMethods = { "testSetDebugLevel" })
    public void testLogInfo() {
        String log = "testLogInfo";
        TestReporter.logInfo(log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testLogNoHtmlTrim() {
        String log = "<b>testLogNoHtmlTrim</b>";
        TestReporter.logNoHtmlTrim(log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(dependsOnMethods = { "testSetDebugLevel" })
    public void testLogNoXmlTrim() {
        String log = "<catalog><book id=\"bk101\"\\><\\catalog>";
        TestReporter.logNoXmlTrim(log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), "&lt;catalog&gt;&lt;book id=\"bk101\"\\&gt;&lt;\\catalog&gt;"));
    }

    @Test(dependsOnMethods = { "testSetDebugLevel" })
    public void testLogScenario() {
        String log = "Scenario: testLogScenario";
        TestReporter.logScenario(log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(dependsOnMethods = { "testSetDebugLevel" })
    public void testLogStep() {
        String log = "Step: testLogStep";
        TestReporter.logStep(log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(dependsOnMethods = { "testSetDebugLevel" })
    public void testLogTrace() {
        String log = "testLogTrace";
        TestReporter.logTrace(log);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void testLogScreenshot() {

        DesiredCapabilities caps = DesiredCapabilities.chrome();

        try {
            driver = new OrasiDriver(caps, new URL(sauceLabsURL));
        } catch (MalformedURLException e) {
        }
        driver.get("http://www.google.com");

        TestReporter.logScreenshot(driver, "TestScreenshot");
        if (StringUtils.isEmpty(System.getProperty("jenkinsJobUrl"))) {
            System.setProperty("jenkinsJobUrl", "demo");
            TestReporter.logScreenshot(driver, "TestScreenshot");
        }
    }

    @Test(dependsOnMethods = { "testLogScreenshot" })
    public void testLogConsoleLogsNoErrors() {
        String log = "NO ERRORS";
        TestReporter.logConsoleErrors(driver);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(dependsOnMethods = { "testLogScreenshot" })
    public void testLogConsoleLogsNullDriver() {
        String log = "Driver was null, could not capture console errors";
        TestReporter.logConsoleErrors(null);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test(dependsOnMethods = { "testLogConsoleLogsNoErrors" })
    public void testLogConsoleLogsWithErrors() {
        TestReporter.setDebugLevel(2);
        String log = "Chrome Browser Console errors";
        driver.executeJavaScript("console.error('blah')");
        TestReporter.logConsoleErrors(driver);
        Assert.assertTrue(logHelper(Reporter.getOutput(), log));
    }

    @Test
    public void logAPISoapPass() {
        String log = "logAPISoap";
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.sendRequest();
        TestReporter.logAPI(true, log, getInfo);
    }

    @Test(expectedExceptions = SoapException.class)
    public void logAPISoapFail() {
        String log = "logAPISoap";
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.sendRequest();
        TestReporter.logAPI(false, log, getInfo);
    }

    @Test
    public void logAPIRestPass() {
        String log = "logAPIRest";
        RestService rest = new RestService();
        RestResponse response = rest.sendGetRequest("https://jsonplaceholder.typicode.com/posts/1");
        TestReporter.logAPI(true, log, response);
    }

    @Test(expectedExceptions = RestException.class)
    public void logAPIRestFail() {
        String log = "logAPIRest";
        RestService rest = new RestService();
        RestResponse response = rest.sendGetRequest("https://jsonplaceholder.typicode.com/posts/1");
        TestReporter.logAPI(false, log, response);
    }

    private synchronized boolean logHelper(final List<String> logs, String log) {
        return logs.parallelStream().anyMatch(str -> str.contains(log));
    }
}
