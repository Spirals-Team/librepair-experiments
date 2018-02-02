package com.orasi.web.webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
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
import com.orasi.web.exceptions.OptionNotInListboxException;
import com.orasi.web.exceptions.SelectElementNotFoundException;
import com.orasi.web.webelements.impl.ListboxImpl;
import com.orasi.web.webelements.impl.internal.ElementFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestListbox extends WebBaseTest {
    @FindBy(id = "wrongID")
    Listbox badSelect;
    @FindBy(id = "singleSelect")
    Listbox listbox;

    OrasiDriver driver = null;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        setApplicationUnderTest("Test Site");
        setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/listbox.html");
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
    @Stories("Listbox")
    @Title("constructor")
    @Test(groups = { "regression", "interfaces", "listbox" })
    public void constructorWithElement() {
        driver = testStart("TestListbox");
        Assert.assertNotNull((new ListboxImpl(driver, (By.id("singleSelect")))));
    }

    @Features("Element Interfaces")
    @Stories("Listbox")
    @Title("isMultiple")
    @Test(groups = { "regression", "interfaces", "listbox" }, dependsOnMethods = "constructorWithElement")
    public void isMultiple() {
        Listbox listbox = driver.findListbox(By.id("multiSelect"));
        Assert.assertTrue(listbox.isMultiple());
    }

    @Features("Element Interfaces")
    @Stories("Listbox")
    @Title("select")
    @Test(groups = { "regression", "interfaces", "listbox", "mustard" }, dependsOnMethods = "constructorWithElement")
    public void select() {
        Listbox listbox = driver.findListbox(By.id("singleSelect"));
        listbox.select("Sports");
        Assert.assertTrue(listbox.getFirstSelectedOption().getText().equals("Sports"));
    }

    @Features("Element Interfaces")
    @Stories("Listbox")
    @Title("selectNoText")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "select")
    public void selectNoText() {
        Listbox listbox = driver.findListbox(By.id("singleSelect"));
        listbox.select("");
        Assert.assertTrue(listbox.getAttribute("value").equals("Sports"));
    }

    @Features("Element Interfaces")
    @Stories("Listbox")
    @Title("selectNegative")
    @Test(groups = { "regression", "interfaces", "textbox" }, dependsOnMethods = "select", expectedExceptions = OptionNotInListboxException.class)
    public void selectNegative() {
        Listbox listbox = driver.findListbox(By.id("singleSelect"));
        listbox.select("text");
        Assert.assertTrue(false);
    }

    @Features("Element Interfaces")
    @Stories("Listbox")
    @Title("selectValue")
    @Test(groups = { "regression", "interfaces", "listbox" }, dependsOnMethods = "constructorWithElement")
    public void selectValue() {
        Listbox listbox = driver.findListbox(By.id("singleSelect"));
        listbox.selectValue("Sports");
        Assert.assertTrue(listbox.getFirstSelectedOption().getText().equals("Sports"));
    }

    @Features("Element Interfaces")
    @Stories("Listbox")
    @Title("selectValueNoText")
    @Test(groups = { "regression", "interfaces", "listbox" }, dependsOnMethods = "select")
    public void selectValueNoText() {
        Listbox listbox = driver.findListbox(By.id("singleSelect"));
        listbox.selectValue("");
        Assert.assertTrue(listbox.getAttribute("value").equals("Sports"));
    }

    @Features("Element Interfaces")
    @Stories("Listbox")
    @Title("selectValueNegative")
    @Test(groups = { "regression", "interfaces", "listbox" }, dependsOnMethods = "select", expectedExceptions = OptionNotInListboxException.class)
    public void selectValueNegative() {
        Listbox listbox = driver.findListbox(By.id("singleSelect"));
        listbox.selectValue("text");
        Assert.assertTrue(false);
    }

    @Features("Element Interfaces")
    @Stories("Listbox")
    @Title("getAllOptions")
    @Test(groups = { "regression", "interfaces", "listbox" }, dependsOnMethods = "select")
    public void getAllOptions() {
        Listbox listbox = driver.findListbox(By.id("singleSelect"));
        Assert.assertTrue(listbox.getOptions().get(0).getText().equals("Other"));
    }

    @Features("Element Interfaces")
    @Stories("Listbox")
    @Title("getAllOptionsSelected")
    @Test(groups = { "regression", "interfaces", "listbox" }, dependsOnMethods = "select")
    public void getAllSelectedOptions() {
        Listbox listbox = driver.findListbox(By.id("singleSelect"));
        Assert.assertTrue(listbox.getAllSelectedOptions().get(0).getText().equals("Sports"));
    }

    @Features("Element Interfaces")
    @Stories("Listbox")
    @Title("isSelected")
    @Test(groups = { "regression", "interfaces", "listbox" }, dependsOnMethods = "getAllSelectedOptions")
    public void isSelected() {
        Listbox listbox = driver.findListbox(By.id("singleSelect"));
        Assert.assertTrue(listbox.isSelected("Sports"));
    }

    @Features("Element Interfaces")
    @Stories("Listbox")
    @Title("deselectByVisibleText")
    @Test(groups = { "regression", "interfaces", "listbox" }, dependsOnMethods = "isSelected")
    public void deselectByVisibleText() {
        Listbox listbox = driver.findListbox(By.id("multiSelect"));
        listbox.select("Baseball");
        Assert.assertTrue(listbox.isSelected("Baseball"));
        listbox.deselectByVisibleText("Baseball");
        Assert.assertFalse(listbox.isSelected("Baseball"));
    }

    @Features("Element Interfaces")
    @Stories("Listbox")
    @Title("deselectAll")
    @Test(groups = { "regression", "interfaces", "listbox" }, dependsOnMethods = "deselectByVisibleText")
    public void deselectAll() {
        Listbox listbox = driver.findListbox(By.id("multiSelect"));
        listbox.select("Basketball");
        listbox.select("Soccer");
        listbox.deselectAll();
        Assert.assertNull(listbox.getFirstSelectedOption());
    }

    @Features("Element Interfaces")
    @Stories("Listbox")
    @Title("select via page factory negative scenario")
    @Test(groups = { "regression", "interfaces", "listbox", "mustard" }, dependsOnMethods = "deselectByVisibleText", expectedExceptions = SelectElementNotFoundException.class)
    public void negativePageFactoryTest() {
        ElementFactory.initElements(driver, this);
        badSelect.select("Sports");
    }

    @Features("Element Interfaces")
    @Stories("Listbox")
    @Title("select via page factory")
    @Test(groups = { "regression", "interfaces", "listbox", "mustard" }, dependsOnMethods = "deselectByVisibleText")
    public void pageFactoryTest() {
        ElementFactory.initElements(driver, this);
        listbox.select("Sports");
        Assert.assertTrue(listbox.getFirstSelectedOption().getText().equals("Sports"));
    }

}
