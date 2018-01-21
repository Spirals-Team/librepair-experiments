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
import com.orasi.web.webelements.impl.LabelImpl;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestLabel extends WebBaseTest {
    private OrasiDriver driver;

    @BeforeClass(groups = { "regression", "interfaces", "label", "dev" })
    public void setup() {
        setApplicationUnderTest("Test Site");
        setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/label.html");
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
    @Stories("Label")
    @Title("constructor")
    @Test(groups = { "regression", "interfaces", "label" })
    public void constructorWithElement() {
        driver = testStart("TestLabel");
        Assert.assertNotNull((new LabelImpl(driver, (By.xpath("//*[@id='radioForm']/label[1]")))));
    }

    @Features("Element Interfaces")
    @Stories("Label")
    @Title("getFor")
    @Test(groups = { "regression", "interfaces", "label" }, dependsOnMethods = "constructorWithElement")
    public void getFor() {
        Label label = driver.findLabel(By.xpath("//*[@id='radioForm']/label[1]"));
        Assert.assertTrue(label.getFor().equals("genderm"));
    }

}
