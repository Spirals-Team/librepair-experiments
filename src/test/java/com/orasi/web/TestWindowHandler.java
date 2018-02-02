package com.orasi.web;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.orasi.DriverManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestWindowHandler extends WebBaseTest {
    OrasiDriver driver = null;

    @BeforeClass(groups = { "regression", "utils", "dev" })
    public void setup() {
        setApplicationUnderTest("Test Site");
        setPageURL("http://google.com");
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult testResults) {
    }

    @Override
    @AfterClass(alwaysRun = true)
    public void afterClass(ITestContext testResults) {
        DriverManager.setDriver(driver);
        endTest(getTestName(), testResults);
    }

    @Features("Utilities")
    @Stories("WindowHandler")
    @Title("waitUntilNumberOfWindowsAre")
    @Test(groups = { "regression", "smoke" })
    public void waitUntilNumberOfWindowsAre() {
        driver = testStart("TestWindowHandler");
        driver.executeJavaScript("window.open('http://bluesourcestaging.herokuapp.com', 'BLAH', 'height=800,width=800');");
        Assert.assertTrue(WindowHandler.waitUntilNumberOfWindowsAre(driver, 2));
    }

    @Features("Utilities")
    @Stories("WindowHandler")
    @Title("waitUntilWindowExistsWithTitle")
    @Test(groups = { "regression", "smoke" }, dependsOnMethods = "waitUntilNumberOfWindowsAre")
    public void waitUntilWindowExistsWithTitle() {
        Assert.assertTrue(WindowHandler.waitUntilWindowExistsWithTitle(driver, "BlueSource"));
    }

    @Features("Utilities")
    @Stories("WindowHandler")
    @Title("waitUntilWindowExistsTitleContains")
    @Test(groups = { "regression", "smoke" }, dependsOnMethods = "waitUntilNumberOfWindowsAre")
    public void waitUntilWindowExistsTitleContains() {
        Assert.assertTrue(WindowHandler.waitUntilWindowExistsTitleContains(driver, "Blue"));
    }

    @Features("Utilities")
    @Stories("WindowHandler")
    @Title("waitUntilWindowExistsTitleMatches")
    @Test(groups = { "regression", "smoke" }, dependsOnMethods = "waitUntilNumberOfWindowsAre")
    public void waitUntilWindowExistsTitleMatches() {
        Assert.assertTrue(WindowHandler.waitUntilWindowExistsTitleMatches(driver, "(?i:.*source)"));
    }

}
