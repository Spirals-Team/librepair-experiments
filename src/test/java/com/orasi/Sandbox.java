package com.orasi;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.orasi.utils.dataProviders.CSVDataProvider;
import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Textbox;

public class Sandbox {
    URL url = null;
    OrasiDriver localDriver = null;

    @BeforeSuite
    public void setup() {
        try {
            url = new URL("http://OrasiTesting:f0a63584-f52e-4d4b-9002-d7aeed40e4c3@ondemand.saucelabs.com:80/wd/hub");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @BeforeClass
    public void beforeMethod() {
        DriverOptionsManager options = new DriverOptionsManager();
        options.setBrowserVersion(DriverType.CHROME, "60.0");
        options.setPlatform(DriverType.CHROME, "windows 10");
        DriverManagerFactory.getManager(DriverType.CHROME, options).initalizeDriver(url);
    }

    // @DataProvider(name = "provider", parallel = true)
    public Object[][] provider() {
        return CSVDataProvider.getData("/excelsheets/TestData_csv.csv");
    }

    // @Test(invocationCount = 3, singleThreaded = false, threadPoolSize = 3)
    public void chrome() {
        DriverManagerFactory.getManager(DriverType.CHROME).initalizeDriver();
        script();
    }

    @Test // (invocationCount = 3, singleThreaded = false, threadPoolSize = 3)
    public void chromeOptions() {

        // DriverOptionsManager options = new DriverOptionsManager();
        // options.getChromeOptions().setHeadless(true);
        // DriverManagerFactory.getManager(DriverType.CHROME, options).initalizeDriver();
        script();
    }

    // @Test
    public void edge() {
        DriverManagerFactory.getManager(DriverType.EDGE).initalizeDriver();
        script();
    }

    // @Test // (invocationCount = 3, singleThreaded = false, threadPoolSize = 3)
    public void firefox() {
        DriverManagerFactory.getManager(DriverType.FIREFOX).initalizeDriver(url);
        script();
    }

    // @Test(invocationCount = 3, singleThreaded = false, threadPoolSize = 3)
    public void firefoxOptions() {
        DriverOptionsManager options = new DriverOptionsManager();
        options.getFirefoxOptions().setHeadless(true);
        options.getFirefoxOptions().setPageLoadStrategy(PageLoadStrategy.NONE);
        DriverManagerFactory.getManager(DriverType.FIREFOX, options).initalizeDriver(url);
        script();
    }

    // @Test(dataProvider = "provider")
    public void dataprovider(String column1, String column2, String column3, String column4, String column5) {
        // DriverManagerFactory.getManager(DriverType.FIREFOX).initalizeDriver();
        OrasiDriver driver = DriverManager.getDriver();
        driver.get("http://www.yahoo.com");

        driver.findTextbox(By.id("uh-search-box")).set(column1);
        driver.findButton(By.id("uh-search-button")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Assert.assertTrue(wait.until(ExpectedConditions.titleContains(column1 + " - Yahoo Search Results")));
    }

    // @Test(invocationCount = 3, singleThreaded = false, threadPoolSize = 3)
    public void beforeMethodTest() {
        script();
    }

    // @Test // (invocationCount = 3, singleThreaded = false, threadPoolSize = 3)
    public void shareDriverSession1() {
        script();
        localDriver = DriverManager.getDriver();
    }

    // @Test(dependsOnMethods = { "shareDriverSession1" })
    public void shareDriverSession2() {
        DriverManager.setDriver(localDriver);

        OrasiDriver driver = DriverManager.getDriver();
        Textbox search = driver.findTextbox(By.xpath("//input[@type='text']"));
        search.syncEnabled();
        search.set("New Test");
        driver.findButton(By.xpath("//input[@type='submit']")).click();

    }

    private void script() {
        OrasiDriver driver = DriverManager.getDriver();
        driver.get("http://www.yahoo.com");

        driver.findTextbox(By.id("uh-search-box")).set("Orasi");
        driver.findButton(By.id("uh-search-button")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Assert.assertTrue(wait.until(ExpectedConditions.titleContains("Orasi - Yahoo Search Results")));
    }

    @AfterClass(alwaysRun = true)
    public void afterTest() {
        DriverManager.quitDriver();
    }
}