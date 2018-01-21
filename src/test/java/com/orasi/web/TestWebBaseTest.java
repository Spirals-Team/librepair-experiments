package com.orasi.web;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.orasi.DriverManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestWebBaseTest extends WebBaseTest {
    private String application = "application";
    private String browserUnderTest = "firefox";
    private String browserVersion = "57";
    private String operatingSystem = "Win10";
    private String runLocation = "local";
    private String testingEnvironment = "stage";
    private String testingName = "TestEnvironment";
    private String pageURL = "http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/element.html";

    @Override
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult testResults) {
    }

    @Features("Utilities")
    @Stories("TestEnvironment")
    @Title("testEnviroment")
    @Test(groups = "regression")
    public void testEnviroment() {
        WebBaseTest te = new WebBaseTest();
        Assert.assertNotNull(te);
    }

    @Features("Utilities")
    @Stories("TestEnvironment")
    @Title("testEnviromentWithStringConstructor")
    @Test(groups = "regression")
    public void testEnviromentWithStringConstructor() {
        WebBaseTest te = new WebBaseTest(application, browserUnderTest, browserVersion, operatingSystem,
                runLocation, testingEnvironment);
        Assert.assertNotNull(te);
    }

    @Features("Utilities")
    @Stories("TestEnvironment")
    @Title("applicationUnderTest")
    @Test(groups = "regression")
    public void applicationUnderTest() {
        WebBaseTest te = new WebBaseTest();
        te.setApplicationUnderTest(application);
        Assert.assertTrue(te.getApplicationUnderTest().equals(application));
    }

    @Features("Utilities")
    @Stories("TestEnvironment")
    @Title("browserUnderTest")
    @Test(groups = "regression")
    public void browserUnderTest() {
        WebBaseTest te = new WebBaseTest();
        te.setBrowserUnderTest(browserUnderTest);
        Assert.assertTrue(te.getBrowserUnderTest().equals(browserUnderTest));
    }

    @Features("Utilities")
    @Stories("TestEnvironment")
    @Title("browserVersion")
    @Test(groups = "regression")
    public void browserVersion() {
        WebBaseTest te = new WebBaseTest();
        te.setBrowserVersion(browserVersion);
        Assert.assertTrue(te.getBrowserVersion().equals(browserVersion));
    }

    @Features("Utilities")
    @Stories("TestEnvironment")
    @Title("operatingSystem")
    @Test(groups = "regression")
    public void operatingSystem() {
        WebBaseTest te = new WebBaseTest();
        te.setOperatingSystem(operatingSystem);
        Assert.assertTrue(te.getOperatingSystem().equals(operatingSystem));
    }

    @Features("Utilities")
    @Stories("TestEnvironment")
    @Title("runLocation")
    @Test(groups = "regression")
    public void runLocation() {
        setRunLocation(runLocation);
        Assert.assertTrue(getRunLocation().equals(runLocation));
    }

    @Features("Utilities")
    @Stories("TestEnvironment")
    @Title("testEnvironment")
    @Test(groups = "regression")
    public void testEnvironment() {
        setEnvironment(testingEnvironment);
        Assert.assertTrue(getEnvironment().equals(testingEnvironment));
    }

    @Features("Utilities")
    @Stories("TestEnvironment")
    @Title("testName")
    @Test(groups = "regression")
    public void testName() {
        setTestName(testingName);
        Assert.assertTrue(getTestName().equals(testingName));
    }

    @Features("Utilities")
    @Stories("TestEnvironment")
    @Title("pageURL")
    @Test(groups = "regression")
    public void pageURL() {
        WebBaseTest te = new WebBaseTest();
        te.setPageURL(pageURL);
        Assert.assertTrue(te.getPageURL().equals(pageURL));
    }

    @Features("Utilities")
    @Stories("TestEnvironment")
    @Title("testStart")
    @Test(groups = "regression")
    public void testTestStart() {
        WebBaseTest te = new WebBaseTest(application, browserUnderTest, browserVersion, operatingSystem,
                runLocation, testingEnvironment);
        te.setPageURL(pageURL);
        te.testStart(testingName);
        while (DriverManager.getDriver().getTitle().isEmpty()) {
        }

        String title = DriverManager.getDriver().getTitle();
        DriverManager.quitDriver();
        Assert.assertTrue(title.equalsIgnoreCase("Unit test site"));
    }

}