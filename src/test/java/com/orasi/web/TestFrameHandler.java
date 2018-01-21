package com.orasi.web;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.orasi.DriverManager;
import com.orasi.DriverType;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestFrameHandler extends WebBaseTest {
    OrasiDriver driver = null;

    @BeforeClass(groups = { "regression", "utils", "dev", "framehandler" })
    public void setup() {
        setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/utils/frameHandler.html");
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
    @Stories("FrameHandler")
    @Title("findAndSwitchToFrameFromOutsideFrame")
    @SuppressWarnings("deprecation")
    @Test(groups = { "regression", "utils", "dev", "framehandler" })
    public void findAndSwitchToFrameFromOutsideFrame() {
        driver = testStart("TestFrame");
        FrameHandler.findAndSwitchToFrame(driver, "menu_page");
        Assert.assertTrue("Link was not found in 'menu_page'",
                driver.findElement(By.id("googleLink")).isDisplayed());
    }

    @Features("Utilities")
    @Stories("FrameHandler")
    @Title("testGetCurrentFrameName")
    @Test(groups = { "regression", "utils", "dev",
            "framehandler" }, dependsOnMethods = "findAndSwitchToFrameFromOutsideFrame")
    public void testGetCurrentFrameName() {
        Assert.assertTrue("Frame name was not 'menu_page' ",
                FrameHandler.getCurrentFrameName(driver).equals("menu_page"));
    }

    @Features("Utilities")
    @Stories("FrameHandler")
    @Title("testGetDefaultContent")
    @Test(groups = { "regression", "utils", "dev" }, dependsOnMethods = "testGetCurrentFrameName")
    public void testGetDefaultContent() {
        FrameHandler.moveToDefaultContext(driver);
        Assert.assertNull("Failed to move to default Content", FrameHandler.getCurrentFrameName(driver));
    }

    @Features("Utilities")
    @Stories("FrameHandler")
    @Title("testMoveToChildFrameWithName")
    @Test(groups = { "regression", "utils", "dev" }, dependsOnMethods = "testGetDefaultContent")
    public void testMoveToChildFrameWithName() {
        FrameHandler.moveToChildFrame(driver, "main_page");
        Assert.assertTrue("Button was not found in frame 'main_page'",
                FrameHandler.getCurrentFrameName(driver).equals("main_page"));
    }

    @Features("Utilities")
    @Stories("FrameHandler")
    @Title("testMoveToChildFrameWithLocator")
    @Test(groups = { "regression", "utils", "dev" }, dependsOnMethods = "testMoveToChildFrameWithName")
    public void testMoveToChildFrameWithLocator() {
        By locator = By.name("main_frame1");
        FrameHandler.moveToChildFrame(driver, locator);
        Assert.assertTrue("Button was not found in frame 'main_frame1'",
                FrameHandler.getCurrentFrameName(driver).equals("main_frame1"));
    }

    @Features("Utilities")
    @Stories("FrameHandler")
    @Title("testMoveToParentFrame")
    @Test(groups = { "regression", "utils", "dev" }, dependsOnMethods = "testMoveToChildFrameWithLocator")
    public void testMoveToParentFrame() {
        if (DriverType.SAFARI == driver.getDriverType()) {
            throw new SkipException("Test not valid for SafariDriver");
        }
        FrameHandler.moveToParentFrame(driver);
        Assert.assertTrue("Button was not found in frame 'main_page'",
                FrameHandler.getCurrentFrameName(driver).equals("main_page"));
    }

    @Features("Utilities")
    @Stories("FrameHandler")
    @Title("testMoveToSiblingFrameWithName")
    @Test(groups = { "regression", "utils", "dev" }, dependsOnMethods = "testMoveToParentFrame")
    public void testMoveToSiblingFrameWithName() {
        if (DriverType.SAFARI == driver.getDriverType()) {
            throw new SkipException("Test not valid for SafariDriver");
        }
        FrameHandler.moveToChildFrame(driver, "main_frame1");
        FrameHandler.moveToSiblingFrame(driver, "main_frame2");
        Assert.assertTrue("Button was not found in frame 'main_page'",
                FrameHandler.getCurrentFrameName(driver).equals("main_frame2"));
    }

    @Features("Utilities")
    @Stories("FrameHandler")
    @Title("testMoveToSiblingFrameWithLocator")
    @Test(groups = { "regression", "utils", "dev" }, dependsOnMethods = "testMoveToSiblingFrameWithName")
    public void testMoveToSiblingFrameWithLocator() {
        if (DriverType.SAFARI == driver.getDriverType()) {
            throw new SkipException("Test not valid for SafariDriver");
        }
        By locator = By.name("main_frame1");
        FrameHandler.moveToSiblingFrame(driver, locator);
        Assert.assertTrue("Button was not found in frame 'main_frame1'",
                FrameHandler.getCurrentFrameName(driver).equals("main_frame1"));
    }

    @Features("Utilities")
    @Stories("FrameHandler")
    @Title("testMoveToMultiChildFrameWithName")
    @Test(groups = { "regression", "utils", "dev" }, dependsOnMethods = "testMoveToSiblingFrameWithLocator")
    public void testMoveToMultiChildFrameWithName() {
        FrameHandler.moveToDefaultContext(driver);
        FrameHandler.moveToChildFrame(driver, new String[] { "main_page", "main_frame1" });
        Assert.assertTrue("Button was not found in frame 'main_frame1'",
                FrameHandler.getCurrentFrameName(driver).equals("main_frame1"));
    }

    @Features("Utilities")
    @Stories("FrameHandler")
    @Title("testMoveToMultiChildFrameWithLocator")
    @Test(groups = { "regression", "utils", "dev" }, dependsOnMethods = "testMoveToMultiChildFrameWithName")
    public void testMoveToMultiChildFrameWithLocator() {
        By locatorParentFrame = By.name("main_page");
        By locatorChildFrame = By.name("main_frame1");
        FrameHandler.moveToDefaultContext(driver);
        FrameHandler.moveToChildFrame(driver, new By[] { locatorParentFrame, locatorChildFrame });
        Assert.assertTrue("Button was not found in frame 'main_frame1'",
                FrameHandler.getCurrentFrameName(driver).equals("main_frame1"));
    }

}
