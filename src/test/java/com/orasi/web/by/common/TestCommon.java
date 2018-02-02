package com.orasi.web.by.common;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.orasi.web.OrasiDriver;
import com.orasi.web.WebBaseTest;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.impl.internal.ElementFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestCommon extends WebBaseTest {
    private OrasiDriver driver;

    @FindByCommon(textValue = "Element test page")
    public Label findTextValue;

    @FindByCommon(textValueContains = "Element")
    public Label findTextValueContains;

    @FindByCommon()
    public Label findByNull;

    @BeforeClass(groups = { "regression", "interfaces", "common", "dev" })
    public void setup() {
        setApplicationUnderTest("Test Site");
        setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/element.html");
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult testResults) {
    }

    @Features("Element Interfaces")
    @Stories("ByCommon")
    @Title("driverFindTextValue")
    @Test(groups = { "regression", "interfaces" })
    public void driverFindTextValue() {
        driver = testStart("TestCommon");
        Element element = driver.findElement(ByCommon.textValue("Element test page"));
        Assert.assertTrue(element.elementWired());
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("driverFindNGControllerNullSearchBy")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "driverFindTextValue", expectedExceptions = IllegalArgumentException.class)
    public void driverFindTextValueNullSearchBy() {
        driver.findElement(ByCommon.textValue(""));
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("driverFindNGModel")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "driverFindTextValue")
    public void driverFindTextValueContains() {
        Element element = driver.findElement(ByCommon.textValueContains("Element"));
        Assert.assertTrue(element.elementWired());
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("driverFindNGModelNullSearchBy")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "driverFindTextValue", expectedExceptions = IllegalArgumentException.class)
    public void driverFindTextValueContainsNullSearchBy() {
        driver.findElement(ByCommon.textValueContains(""));
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("pageFactoryFindCommonNull")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "driverFindTextValue", expectedExceptions = IllegalArgumentException.class)
    public void pageFactoryFindCommonNull() {
        ElementFactory.initElements(driver, this);
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("pageFactoryFindNGController")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "pageFactoryFindCommonNull")
    public void pageFactoryFindCommonTextValue() {
        Assert.assertTrue(findTextValue.elementWired());
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("pageFactoryFindCommonTextValueContains")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "pageFactoryFindCommonTextValue")
    public void pageFactoryFindCommonTextValueContains() {
        Assert.assertTrue(findTextValueContains.elementWired());
    }
}
