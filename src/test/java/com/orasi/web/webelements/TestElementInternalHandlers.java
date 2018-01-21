package com.orasi.web.webelements;

import java.util.List;

import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.orasi.DriverManager;
import com.orasi.web.OrasiDriver;
import com.orasi.web.WebBaseTest;
import com.orasi.web.by.angular.FindByNG;
import com.orasi.web.webelements.impl.internal.ElementFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestElementInternalHandlers extends WebBaseTest {
    private OrasiDriver driver;
    @FindBy(xpath = "//*[@ng-model='firstName']")
    private Button btn1;
    @FindBys({ @FindBy(xpath = "//*[@ng-model='firstName']"), @FindBy(xpath = "//*[@ng-model='firstName']") })
    private List<Textbox> btn2;
    @FindByNG(ngModel = "firstName")
    private Textbox btn3;
    @FindAll({ @FindBy(tagName = "input"), @FindBy(tagName = "input") })
    private Textbox btn4;

    @BeforeClass(groups = { "regression", "interfaces", "internal", "dev" })
    public void setup() {
        setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/angular/angularPage.html");
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
    @Stories("ElementInternalHandlers")
    @Title("factory")
    @Test(groups = { "regression", "interfaces", "button" })
    public void factory() {
        driver = testStart("TestElementInternalHandlers");
        ElementFactory.initElements(driver, this);
        btn3.getWrappedElement();
        btn3.click();
    }

    @Features("Element Interfaces")
    @Stories("ElementInternalHandlers")
    @Title("factory")
    @Test(groups = { "regression", "interfaces", "button" }, dependsOnMethods = "factory")
    public void elementList() {
        btn2.get(0).getWrappedElement();
        btn2.get(0).highlight();
    }
}
