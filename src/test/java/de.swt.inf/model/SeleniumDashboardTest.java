package de.swt.inf.model;

import de.swt.inf.database.DaoFactory;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class SeleniumDashboardTest {

    @BeforeClass
    public static void beforeClass(){
        DaoFactory.test = true;
        BeforSelenium.beforSeleniumTests();
    }

    @AfterClass
    public static void afterClass(){
        DaoFactory.test = false;
    }

    @Test
    public void dashboardWocheSeleniumTest(){
        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        WebDriver driver = new HtmlUnitDriver();
        driver.navigate().to("http://localhost:8080/dashboard");

        WebElement elementKlick = driver.findElement(By.linkText("Neuer Termin"));
        elementKlick.click();

        WebElement element = driver.findElement(By.name("name"));
        element.sendKeys("Vorlesung");
        element= driver.findElement(By.name("startT"));
        element.sendKeys("13:00");
        element= driver.findElement(By.name("start"));
        element.sendKeys(df.format(c.getTime()));
        element = driver.findElement(By.name("ort"));
        element.sendKeys("Lübeck");
        element = driver.findElement(By.name("endT"));
        element.sendKeys("20:00");
        element = driver.findElement(By.name("end"));
        element.sendKeys(df.format(c.getTime()));
        //Termin speichern
        element = driver.findElement(By.name("speichern"));
        element.click();

        assertEquals("http://localhost:8080/dashboard/woche", driver.getCurrentUrl());
        assertEquals("Vorlesung",driver.findElement(By.linkText("Vorlesung")).getText());

        WebElement testElement = driver.findElement(By.linkText("Nächste Woche"));
        testElement.click();

        boolean present;
        try {
            driver.findElement(By.linkText("Vorlesung"));
            present = true;
        } catch (NoSuchElementException e) {
            present = false;
        }

        assertEquals(false, present);

        testElement = driver.findElement(By.linkText("letzte Woche"));
        testElement.click();

        assertEquals("Vorlesung", driver.findElement(By.linkText("Vorlesung")).getText());

        testElement = driver.findElement(By.linkText("letzte Woche"));
        testElement.click();

        try {
            driver.findElement(By.linkText("Vorlesung"));
            present = true;
        } catch (NoSuchElementException e) {
            present = false;
        }

        assertEquals(false, present);

        testElement = driver.findElement(By.linkText("Nächste Woche"));
        testElement.click();

        assertEquals("Vorlesung", driver.findElement(By.linkText("Vorlesung")).getText());
    }


    @Test
    public void dashboardMonatSeleniumTest(){

    }

    @Test
    public void dashboardJahrSeleniumTest(){


    }

    @Test
    public void dashboardTagSeleniumTest(){

        
    }

}
