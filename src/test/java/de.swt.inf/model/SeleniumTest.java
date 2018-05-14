package de.swt.inf.model;


import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import static org.junit.Assert.assertEquals;

public class SeleniumTest {


    @Test
    public void SeleniumTest() {

        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("/path/to/google-chrome-stable");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--disable-gpu");

        final DesiredCapabilities dc = new DesiredCapabilities();
        dc.setJavascriptEnabled(true);
        dc.setCapability(
                ChromeOptions.CAPABILITY, chromeOptions
        );

        WebDriver driver = new ChromeDriver(dc);

        /* //Pfad zum Chrome Driver
        System.setProperty("webdriver.chrome.driver", "C:/Users/Nina/Downloads/chromedriver_win32/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
       */
        //URL zum Kalender
        driver.get("localhost:8080/termin");
        //Browserfenster maximieren
        driver.manage().window().maximize();
        //Termin automatisch erstellen
        driver.findElement(By.name("name")).sendKeys("Vorlesung");
        driver.findElement(By.name("startT")).sendKeys("13:00");
        driver.findElement(By.name("start")).sendKeys("20.05.2018");
        driver.findElement(By.name("ort")).sendKeys("Lübeck");
        driver.findElement(By.name("endT")).sendKeys("20:00");
        driver.findElement(By.name("end")).sendKeys("20.05.2018");
        driver.findElement(By.name("repeat")).click();
        Select sel = new Select(driver.findElement(By.name("repeatTime")));
        sel.selectByIndex(2);
        driver.findElement(By.name("speichern")).sendKeys(Keys.ENTER);

        //jUnit Tests
        assertEquals("Dashboard", driver.getTitle());
        assertEquals("http://localhost:8080/dashboard", driver.getCurrentUrl());
    }
}
