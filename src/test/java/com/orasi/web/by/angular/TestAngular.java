package com.orasi.web.by.angular;

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

public class TestAngular extends WebBaseTest {
    private OrasiDriver driver;

    @FindByNG(ngController = "myCtrl")
    public Label findByController;

    @FindByNG(ngModel = "firstName")
    public Label findByModel;

    @FindByNG(ngRepeater = "x in names | orderBy:'country'")
    public Label findByRepeater;

    @FindByNG(ngShow = "myVar")
    public Label findByShow;

    @FindByNG()
    public Label findByNull;

    @BeforeClass(groups = { "regression", "interfaces", "angular", "dev" })
    public void setup() {
        setApplicationUnderTest("Test Site");
        setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/angular/angularPage.html");
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult testResults) {
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("driverFindNGController")
    @Test(groups = { "regression", "interfaces" })
    public void driverFindNGController() {
        driver = testStart("TestAngular");
        Element controller = driver.findElement(ByNG.controller("myCtrl"));
        Assert.assertTrue(controller.elementWired());
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("driverFindNGControllerNullSearchBy")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "driverFindNGController", expectedExceptions = IllegalArgumentException.class)
    public void driverFindNGControllerNullSearchBy() {
        driver.findElement(ByNG.controller(""));
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("driverFindNGModel")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "driverFindNGController")
    public void driverFindNGModel() {
        Element model = driver.findElement(ByNG.model("lastName"));
        Assert.assertTrue(model.elementWired());

    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("driverFindNGModelNullSearchBy")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "driverFindNGController", expectedExceptions = IllegalArgumentException.class)
    public void driverFindNGModelNullSearchBy() {
        driver.findElement(ByNG.model(""));
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("driverFindNGRepeater")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "driverFindNGController")
    public void driverFindNGRepeater() {
        Element repeater = driver.findElement(ByNG.repeater("x in names | orderBy:'country'"));
        Assert.assertTrue(repeater.elementWired());
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("driverFindNGRepeaterNullSearchBy")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "driverFindNGController", expectedExceptions = IllegalArgumentException.class)
    public void driverFindNGRepeaterNullSearchBy() {
        driver.findElement(ByNG.repeater(""));
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("driverFindNGShow")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "driverFindNGController")
    public void driverFindNGShow() {
        Element show = driver.findElement(ByNG.show("myVar"));
        Assert.assertTrue(show.elementWired());
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("driverFindNGShowNullSearchBy")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "driverFindNGController", expectedExceptions = IllegalArgumentException.class)
    public void driverFindNGShowNullSearchBy() {
        driver.findElement(ByNG.show(""));
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("pageFactoryFindNGNull")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "driverFindNGController", expectedExceptions = IllegalArgumentException.class)
    public void pageFactoryFindNGNull() {
        ElementFactory.initElements(driver, this);
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("pageFactoryFindNGController")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "pageFactoryFindNGNull")
    public void pageFactoryFindNGController() {
        Assert.assertTrue(findByController.elementWired());
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("pageFactoryFindNGModel")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "pageFactoryFindNGController")
    public void pageFactoryFindNGModel() {
        Assert.assertTrue(findByModel.elementWired());
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("pageFactoryFindNGRepeater")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "pageFactoryFindNGController")
    public void pageFactoryFindNGRepeater() {
        Assert.assertTrue(findByRepeater.elementWired());
    }

    @Features("Element Interfaces")
    @Stories("Angular")
    @Title("pageFactoryFindNGShow")
    @Test(groups = { "regression", "interfaces" }, dependsOnMethods = "pageFactoryFindNGController")
    public void pageFactoryFindNGShow() {
        Assert.assertTrue(findByShow.elementWired());
    }
}
