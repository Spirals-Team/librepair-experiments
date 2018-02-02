package com.orasi.web;

import org.junit.Assert;
import org.openqa.selenium.By;
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

public class TestAlertHandler extends WebBaseTest {
    OrasiDriver driver = null;

    @BeforeClass(groups = { "regression", "utils", "dev", "framehandler" })
    public void setup() {
        setApplicationUnderTest("Test Site");
        setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/utils/alertHandler.html");
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
    @Stories("AlertHandler")
    @Title("constructor")
    @Test(groups = { "regression", "interfaces", "textbox" })
    public void constructorWithElement() {
        driver = testStart("TestAlert");
        Assert.assertNotNull((new AlertHandler()));
    }

    @Features("Utilities")
    @Stories("AlertHandler")
    @Title("isAlertPresent")
    @Test(groups = { "regression", "utils", "dev", "alertHandler" }, dependsOnMethods = "constructorWithElement")
    public void isAlertPresent() {
        Assert.assertTrue("Alert was not present", AlertHandler.isAlertPresent(driver, 1));
    }

    @Features("Utilities")
    @Stories("AlertHandler")
    @Title("handleAllAlerts")
    @Test(groups = { "regression", "utils", "dev", "alertHandler" }, dependsOnMethods = "isAlertPresent")
    public void handleAllAlerts() {
        AlertHandler.handleAllAlerts(driver, 1);
        Assert.assertFalse("Alert was not handled", AlertHandler.isAlertPresent(driver, 1));
    }

    @Features("Utilities")
    @Stories("AlertHandler")
    @Title("handleAlert")
    @Test(groups = { "regression", "utils", "dev", "alertHandler" }, dependsOnMethods = "handleAllAlerts")
    public void handleAlert() {
        driver.findElement(By.id("button")).click();
        AlertHandler.handleAlert(driver, 1);
        Assert.assertFalse("Alert was not handled", AlertHandler.isAlertPresent(driver, 1));
    }

}
