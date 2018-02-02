package com.orasi.web.webelements;

import java.util.List;

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
import com.orasi.web.OrasiDriver;
import com.orasi.web.WebBaseTest;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestRadioButton extends WebBaseTest {
    private OrasiDriver driver;

    @BeforeClass(groups = { "regression", "interfaces", "radiogroup", "dev" })
    public void setup() {
        setApplicationUnderTest("Test Site");
        setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/radioGroup.html");
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

    /*
     * @Features("Element Interfaces")
     *
     * @Stories("RadioGroup")
     *
     * @Title("constructor")
     *
     * @Test(groups ={"regression", "interfaces", "textbox"})
     * public void constructorWithElement(){
     * Assert.assertNotNull((new RadioGroupImpl(driver.findWebElement((By.id("radioForm"))),driver)));
     * }
     */

    @Features("Element Interfaces")
    @Stories("RadioGroup")
    @Title("getNumberOfOptions")
    @Test(groups = { "regression", "interfaces", "radiogroup" })
    public void getNumberOfOptions() {

        driver = testStart("TestRadiogroup");
        RadioGroup radiogroup = driver.findRadioGroup(By.id("radioForm"));
        Assert.assertTrue(radiogroup.getNumberOfOptions() == 3);
    }

    /*
     * @Test(groups ={"regression", "interfaces", "radiogroup"})
     * public void getNumberOfRadioButtons(){
     * RadioGroup radiogroup = driver.findRadioGroup(By.id("radioForm"));
     * Assert.assertTrue(radiogroup.getNumberOfRadioButtons() == 2 );
     * }
     */
    @Features("Element Interfaces")
    @Stories("RadioGroup")
    @Title("getSelectedIndex")
    @Test(groups = { "regression", "interfaces", "radiogroup" }, dependsOnMethods = "getNumberOfOptions")
    public void getSelectedIndex() {
        RadioGroup radiogroup = driver.findRadioGroup(By.id("radioForm"));
        Assert.assertTrue(radiogroup.getSelectedIndex() == 1);
    }

    @Features("Element Interfaces")
    @Stories("RadioGroup")
    @Title("getSelectedOption")
    @Test(groups = { "regression", "interfaces", "radiogroup" }, dependsOnMethods = "getNumberOfOptions")
    public void getSelectedOption() {
        RadioGroup radiogroup = driver.findRadioGroup(By.id("radioForm"));
        Assert.assertTrue(radiogroup.getSelectedOption().equals("female"));
    }

    @Features("Element Interfaces")
    @Stories("RadioGroup")
    @Title("selectByIndex")
    @Test(groups = { "regression", "interfaces", "radiogroup" }, dependsOnMethods = "getSelectedIndex")
    public void selectByIndex() {
        RadioGroup radiogroup = driver.findRadioGroup(By.id("radioForm"));
        radiogroup.selectByIndex(1);
        Assert.assertTrue(radiogroup.getSelectedIndex() == 1);
    }

    @Features("Element Interfaces")
    @Stories("RadioGroup")
    @Title("selectIndexOutOfBounds")
    @Test(groups = { "regression", "interfaces", "radiogroup" }, dependsOnMethods = "selectByIndex")
    public void selectByIndexOutOfBounds() {
        RadioGroup radiogroup = driver.findRadioGroup(By.id("radioForm"));
        boolean valid = false;
        try {
            radiogroup.selectByIndex(3);
        } catch (RuntimeException rte) {
            valid = true;
        }
        Assert.assertTrue(valid);
    }

    @Features("Element Interfaces")
    @Stories("RadioGroup")
    @Title("selectByOption")
    @Test(groups = { "regression", "interfaces", "radiogroup" }, dependsOnMethods = "selectByIndex")
    public void selectByOption() {
        RadioGroup radiogroup = driver.findRadioGroup(By.id("radioForm"));
        radiogroup.selectByOption("male");
        Assert.assertTrue(radiogroup.getSelectedIndex() == 0);
    }

    @Features("Element Interfaces")
    @Stories("RadioGroup")
    @Title("selectByOptionNoText")
    @Test(groups = { "regression", "interfaces", "radiogroup" }, dependsOnMethods = "selectByOption")
    public void selectByOptionNoText() {
        if (DriverType.INTERNETEXPLORER.equals(driver.getDriverType())) {
            throw new SkipException("Test not valid for Internet Explorer");
        }
        RadioGroup radiogroup = driver.findRadioGroup(By.id("radioForm"));
        radiogroup.selectByOption("");
        Assert.assertTrue(radiogroup.getSelectedIndex() == 0);
    }

    @Features("Element Interfaces")
    @Stories("RadioGroup")
    @Title("selectByOptionNegative")
    @Test(groups = { "regression", "interfaces", "radiogroup" }, dependsOnMethods = "selectByOption")
    public void selectByOptionNegative() {
        if (DriverType.INTERNETEXPLORER.equals(driver.getDriverType())) {
            throw new SkipException("Test not valid for Internet Explorer");
        }
        RadioGroup radiogroup = driver.findRadioGroup(By.id("radioForm"));
        boolean valid = false;
        try {
            radiogroup.selectByOption("disabled");
        } catch (RuntimeException rte) {
            valid = true;
        }
        Assert.assertTrue(valid);
    }

    @Features("Element Interfaces")
    @Stories("RadioGroup")
    @Title("getAllOptions")
    @Test(groups = { "regression", "interfaces", "radiogroup" }, dependsOnMethods = "getNumberOfOptions")
    public void getAllOptions() {
        RadioGroup radiogroup = driver.findRadioGroup(By.id("radioForm"));
        List<String> group = radiogroup.getAllOptions();
        Assert.assertTrue(group.get(0).equals("male"));
        Assert.assertTrue(group.get(1).equals("female"));
    }
}
