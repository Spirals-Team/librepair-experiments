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
import com.orasi.web.webelements.impl.TextboxImpl;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestTextbox extends WebBaseTest {
    OrasiDriver driver = null;

    @BeforeClass(groups = { "regression", "interfaces", "textbox", "dev" })
    public void setup() {
        setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/textbox.html");
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
    @Stories("Textbox")
    @Title("constructor")
    @Test(groups = { "regression", "interfaces", "textbox" })
    public void constructorWithElement() {
        driver = testStart("TestTextbox");
        Assert.assertNotNull((new TextboxImpl(driver, (By.id("text1")))));
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("constructorWithElement")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "constructorWithElement")
    public void constructorWithElementAndDriver() {
        Assert.assertNotNull(new TextboxImpl(driver, By.id("text1")));
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("getText")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "constructorWithElement")
    public void getText() {
        Textbox textbox = driver.findTextbox(By.id("text1"));
        Assert.assertTrue(textbox.getText().equals("Testing methods"));
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("set")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "setNoText")
    public void set() {
        Textbox textbox = driver.findTextbox(By.id("text1"));
        textbox.set("set");
        Assert.assertTrue(textbox.getAttribute("value").equals("set"));
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("setNoText")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "getText")
    public void setNoText() {
        Textbox textbox = driver.findTextbox(By.id("text1"));
        textbox.set("");
        Assert.assertTrue(textbox.getAttribute("value").equals("Testing methods"));
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("setNegative")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "set")
    public void setNegative() {
        Textbox textbox = driver.findTextbox(By.name("lname"));
        boolean valid = false;
        try {
            textbox.set("text");
        } catch (RuntimeException rte) {
            valid = true;
        }
        Assert.assertTrue(valid);
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("scrollAndSet")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "set")
    public void scrollAndSet() {
        Textbox textbox = driver.findTextbox(By.id("text1"));
        textbox.scrollAndSet("setScrollIntoView");
        Assert.assertTrue(textbox.getAttribute("value").equals("setScrollIntoView"));
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("scrollAndSetNoText")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "scrollAndSet")
    public void scrollAndSetNoText() {
        Textbox textbox = driver.findTextbox(By.id("text1"));
        textbox.scrollAndSet("");
        Assert.assertTrue(textbox.getAttribute("value").equals("setScrollIntoView"));
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("scrollAndSetNegative")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "scrollAndSet")
    public void scrollAndSetNegative() {
        Textbox textbox = driver.findTextbox(By.name("lname"));
        boolean valid = false;
        try {
            textbox.scrollAndSet("text");
        } catch (RuntimeException rte) {
            valid = true;
        }
        Assert.assertTrue(valid);
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("clear")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "scrollAndSetNoText")
    public void clear() {
        Textbox textbox = driver.findTextbox(By.id("text1"));
        textbox.clear();
        Assert.assertTrue(textbox.getAttribute("value").equals(""));
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("clearNegative")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "clear")
    public void clearNegative() {
        Textbox textbox = driver.findTextbox(By.name("lname"));
        boolean valid = false;
        try {
            textbox.clear();
        } catch (RuntimeException rte) {
            valid = true;
        }
        Assert.assertTrue(valid);
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("safeSet")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "clear")
    public void safeSet() {
        Textbox textbox = driver.findTextbox(By.id("text1"));
        textbox.safeSet("safeSet");
        Assert.assertTrue(textbox.getAttribute("value").contains("safeSet"));
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("safeSetNoText")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "safeSet")
    public void safeSetNoText() {
        Textbox textbox = driver.findTextbox(By.id("text1"));
        textbox.safeSet("");
        Assert.assertTrue(textbox.getAttribute("value").contains("safeSet"));
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("safeSetNegative")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "safeSet")
    public void safeSetNegative() {
        Textbox textbox = driver.findTextbox(By.name("page"));
        boolean valid = false;
        try {
            textbox.safeSet("text");
        } catch (RuntimeException rte) {
            valid = true;
        }
        Assert.assertTrue(valid);
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("setSecure")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "safeSet")
    public void setSecure() {
        Textbox textbox = driver.findTextbox(By.id("text1"));
        textbox.setSecure("c2V0U2VjdXJl");
        Assert.assertTrue(textbox.getAttribute("value").contains("safeSetsetSecure"));
        textbox.clear();
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("setSecureNoText")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "setSecure")
    public void setSecureNoText() {
        Textbox textbox = driver.findTextbox(By.id("text1"));
        textbox.setSecure("");
        Assert.assertTrue(textbox.getAttribute("value").contains(""));
        textbox.clear();
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("setSecureNegative")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "setSecureNoText")
    public void setSecureNegative() {
        Textbox textbox = driver.findTextbox(By.name("lname"));
        boolean valid = false;
        try {
            textbox.setSecure("tex");
        } catch (RuntimeException rte) {
            valid = true;
        }
        Assert.assertTrue(valid);
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("safeSetSecure")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "setSecure")
    public void safeSetSecure() {
        Textbox textbox = driver.findTextbox(By.id("text1"));
        textbox.clear();
        textbox.safeSetSecure("c2V0U2VjdXJl");
        Assert.assertTrue(textbox.getAttribute("value").contains("setSecure"));
        textbox.clear();
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("safeSetSecureNoText")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "safeSetSecure")
    public void safeSetSecureNoText() {
        Textbox textbox = driver.findTextbox(By.id("text1"));
        textbox.safeSetSecure("");
        Assert.assertTrue(textbox.getAttribute("value").contains(""));
        textbox.clear();
    }

    @Features("Element Interfaces")
    @Stories("Textbox")
    @Title("safeSetSecureNegative")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "safeSetSecure")
    public void safeSetSecureNegative() {
        Textbox textbox = driver.findTextbox(By.name("lname"));
        boolean valid = false;
        try {
            textbox.safeSetSecure("tex");
        } catch (RuntimeException rte) {
            valid = true;
        }
        Assert.assertTrue(valid);
    }
}
