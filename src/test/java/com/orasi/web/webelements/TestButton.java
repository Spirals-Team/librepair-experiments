package com.orasi.web.webelements;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.orasi.DriverManager;
import com.orasi.web.OrasiDriver;
import com.orasi.web.WebBaseTest;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestButton extends WebBaseTest {
    private OrasiDriver driver;

    @BeforeClass(groups = { "regression", "interfaces", "button", "dev" })
    public void setup() {
        setApplicationUnderTest("Test App");
        setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/button.html");
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

    @Features("Element Interfaces")
    @Stories("Button")
    @Title("click")
    @Test(groups = { "regression", "interfaces", "button" })
    public void click() {
        driver = testStart("TestButton");
        Button button = driver.findButton(By.id("click"));
        button.click();
        Assert.assertTrue(driver.findElement(By.id("testClick")).getText().equals("Successful"));
    }

    @Features("Element Interfaces")
    @Stories("Button")
    @Title("jsClick")
    @Test(groups = { "regression", "interfaces", "button" }, dependsOnMethods = "click")
    public void jsClick() {
        Button button = driver.findButton(By.id("jsClick"));
        button.jsClick();
        Assert.assertTrue(driver.findButton(By.id("testJsClick")).getText().equals("Successful"));
    }
}
