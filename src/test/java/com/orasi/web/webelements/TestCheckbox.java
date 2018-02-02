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

public class TestCheckbox extends WebBaseTest {
    private OrasiDriver driver;

    @BeforeClass(groups = { "regression", "interfaces", "checkbox", "dev" })
    public void setup() {
        setApplicationUnderTest("Test Site");
        setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/checkbox.html");
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
    @Stories("Checkbox")
    @Title("isCheck")
    @Test(groups = { "regression", "interfaces", "checkbox" })
    public void isChecked() {
        driver = testStart("TestCheckbox");
        Checkbox checkbox = driver.findCheckbox(By.name("checkbox"));
        Assert.assertFalse(checkbox.isChecked());
    }

    @Features("Element Interfaces")
    @Stories("Checkbox")
    @Title("check")
    @Test(groups = { "regression", "interfaces", "checkbox" }, dependsOnMethods = "isChecked")
    public void check() {
        Checkbox checkbox = driver.findCheckbox(By.name("checkbox"));
        checkbox.check();
        Assert.assertTrue(checkbox.isChecked());
    }

    @Features("Element Interfaces")
    @Stories("Checkbox")
    @Title("toggle")
    @Test(groups = { "regression", "interfaces", "checkbox" }, dependsOnMethods = "jsToggle")
    public void toggle() {
        Checkbox checkbox = driver.findCheckbox(By.name("checkbox"));
        checkbox.toggle();
        Assert.assertFalse(checkbox.isChecked());
    }

    @Features("Element Interfaces")
    @Stories("Checkbox")
    @Title("uncheck")
    @Test(groups = { "regression", "interfaces", "checkbox" }, dependsOnMethods = "check")
    public void uncheck() {
        Checkbox checkbox = driver.findCheckbox(By.name("checkbox"));
        checkbox.uncheck();
        Assert.assertFalse(checkbox.isChecked());
    }

    @Features("Element Interfaces")
    @Stories("Checkbox")
    @Title("jsToggle")
    @Test(groups = { "regression", "interfaces", "checkbox" }, dependsOnMethods = "uncheck")
    public void jsToggle() {
        Checkbox checkbox = driver.findCheckbox(By.name("checkbox"));
        checkbox.jsToggle();
        Assert.assertTrue(checkbox.isChecked());
    }
}
