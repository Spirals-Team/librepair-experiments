package com.orasi.web.webelements;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.orasi.DriverManager;
import com.orasi.DriverType;
import com.orasi.utils.Sleeper;
import com.orasi.web.OrasiDriver;
import com.orasi.web.WebBaseTest;
import com.orasi.web.webelements.impl.LinkImpl;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestLink extends WebBaseTest {
    private OrasiDriver driver;

    @BeforeClass(groups = { "regression", "interfaces", "link", "dev" })
    public void setup() {
        setApplicationUnderTest("Test Site");
        setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/link.html");
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
    @Stories("Link")
    @Title("constructor")
    @Test(groups = { "regression", "interfaces", "link" })
    public void constructorWithElement() {
        driver = testStart("TestLink");
        Assert.assertNotNull((new LinkImpl(driver, By.xpath("//a[@href='testLinks.html']"))));
        Assert.assertNotNull((new LinkImpl(driver, By.xpath("//a[@href='testLinks.html']"))));
    }

    @Features("Element Interfaces")
    @Stories("Link")
    @Title("click")
    @Test(groups = { "regression", "interfaces", "link" }, dependsOnMethods = "constructorWithElement")
    public void click() {
        Link link = driver.findLink(By.xpath("//a[@href='testLinks.html']"));
        link.click();
        Assert.assertTrue(driver.findElement(By.xpath("//a[@href='link.html']")).syncVisible());
    }

    @Features("Element Interfaces")
    @Stories("Link")
    @Title("clickNegative")
    @Test(groups = { "regression", "interfaces", "link" }, dependsOnMethods = "click")
    public void clickNegative() {

        if (DriverType.INTERNETEXPLORER.equals(driver.getDriverType()) || getBrowserUnderTest().isEmpty()) {
            throw new SkipException("Test not valid for " + driver.getDriverType());
        }
        Link link = driver.findLink(By.xpath("//a[@href='hiddenLink.html']"));
        boolean valid = false;
        try {
            link.click();
        } catch (RuntimeException rte) {
            valid = true;
        }
        Assert.assertTrue(valid);
    }

    @Features("Element Interfaces")
    @Stories("Link")
    @Title("jsClick")
    @Test(groups = { "regression", "interfaces", "link" }, dependsOnMethods = "click")
    public void jsClick() {
        Link link = driver.findLink(By.xpath("//a[@href='link.html']"));
        link.jsClick();
        Assert.assertTrue(driver.findLink(By.xpath("//a[@href='testLinks.html']")).syncVisible());
    }

    @Features("Element Interfaces")
    @Stories("Link")
    @Title("jsClickNegative")
    // @Test(groups = { "regression", "interfaces", "link" }, dependsOnMethods = "jsClick")
    public void jsClickNegative() {
        if (driver.getDriverCapability().browserName().contains("explorer")) {
            throw new SkipException("Test not valid for Internet Explorer");
        }
        Link link = driver.findLink(By.xpath("//a[@href='hiddenLink.html']"));
        Sleeper.sleep(1000);
        driver.findLink(By.xpath("//a[@href='testLinks.html']")).click();
        boolean valid = false;
        try {
            link.jsClick();
        } catch (RuntimeException rte) {
            valid = true;
        }
        Assert.assertTrue(valid);
    }

    @Features("Element Interfaces")
    @Stories("Link")
    @Title("getURL")
    @Test(groups = { "regression", "interfaces", "link" }, dependsOnMethods = "jsClick")
    public void getURL() {
        Link link = driver.findLink(By.xpath("//a[@href='testLinks.html']"));
        Assert.assertTrue(link.getURL().contains("testLinks.html"));
    }
}
