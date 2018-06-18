package de.swt.inf.model;

import de.swt.inf.database.DaoFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SeleniumWiederholungTest {

  @BeforeClass
    public static void beforeClass() {
        DaoFactory.test = true;
        BeforSelenium.beforSeleniumTests();
    }

    @AfterClass
    public static void afterClass() {
        DaoFactory.test = false;
    }

    @Test
    public void WdhTestTaeglich(){

        java.util.Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        //Webdriver erstellen
        WebDriver driver = new HtmlUnitDriver();
        // Navigieren zum Dashboard
        driver.navigate().to("http://localhost:8080/dashboard");
        System.out.println("Go to Dashboard: " + driver.getCurrentUrl());
        //In neuen Termin erstellen gehen
        WebElement NeuerTerminElement = driver.findElement(By.linkText("Neuer Termin"));
        NeuerTerminElement.click();
        //Neuen Termin erstellen
        WebElement element = driver.findElement(By.name("name"));
        element.sendKeys("Test täglich");
        element= driver.findElement(By.name("startT"));
        element.sendKeys("19:00");
        element= driver.findElement(By.name("start"));
        element.sendKeys(df.format(c.getTime()));
        element = driver.findElement(By.name("ort"));
        element.sendKeys("Lübeck");
        element = driver.findElement(By.name("endT"));
        element.sendKeys("20:00");
        element = driver.findElement(By.name("end"));
        element.sendKeys(df.format(c.getTime()));
        element = driver.findElement(By.name("repeat"));
        element.click();
        Select dropdown = new Select(driver.findElement(By.name("repeatTime")));
        dropdown.selectByVisibleText("täglich");

        //Termin speichern
        element = driver.findElement(By.name("speichern"));
        element.click();

        System.out.println("Termin gespeichert: " + driver.getCurrentUrl());
        System.out.println("Title is: " + driver.getTitle());
        //Junit-Test, ob der Termin gespeichert wurde
        assertEquals("http://localhost:8080/dashboard/woche", driver.getCurrentUrl());
        assertEquals("Dashboard/Woche", driver.getTitle());

        // In den angelegten Termin gehen
        WebElement element2 = driver.findElement(By.linkText("Test täglich"));
        element2.click();

        System.out.println("Name:" + driver.getCurrentUrl());
        System.out.println("Title is: " + driver.getTitle());
        //JUnit-Test, ob der Termin richtig mit täglich angelegt wurde
        //JUnit-Test, run WdhTestTagelich
        //assertEquals("http://localhost:8080/edit?id=1", driver.getCurrentUrl());
        //Junit-Test, run SeleniumWiederholungTest
        assertTrue(driver.getCurrentUrl().contains("http://localhost:8080/edit?id="));
        assertEquals("Termin Bearbeiten", driver.getTitle());

        String searchOption = new Select(driver.findElement(By.name("repeatTime"))).getFirstSelectedOption().getText();

        System.out.println("Select: " +searchOption);
        assertEquals("täglich", searchOption);
    }

    @Test
    public void WdhTestStuendlich() {

        java.util.Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        //Webdriver erstellen
        WebDriver driver = new HtmlUnitDriver();
        //Auf das Daschboard navigieren
        driver.navigate().to("http://localhost:8080/dashboard");
        System.out.println("Go to Dashboard: " + driver.getCurrentUrl());
        //Neuen Termin anklicken
        WebElement testElement = driver.findElement(By.linkText("Neuer Termin"));
        testElement.click();
        System.out.println("Go to Neuer Termin: " + driver.getCurrentUrl());
        //Neuen Termin erstellen
        WebElement element = driver.findElement(By.name("name"));
        element.sendKeys("Wiederholung Std");
        element= driver.findElement(By.name("startT"));
        element.sendKeys("18:00");
        element= driver.findElement(By.name("start"));
        element.sendKeys(df.format(c.getTime()));
        element = driver.findElement(By.name("ort"));
        element.sendKeys("Lübeck");
        element = driver.findElement(By.name("endT"));
        element.sendKeys("19:00");
        element = driver.findElement(By.name("end"));
        element.sendKeys(df.format(c.getTime()));
        element = driver.findElement(By.name("repeat"));
        element.click();
        Select dropdown = new Select(driver.findElement(By.name("repeatTime")));
        dropdown.selectByVisibleText("stündlich");
        //Termin speichern
        element = driver.findElement(By.name("speichern"));
        element.click();

        System.out.println("Termin gespeichert: " + driver.getCurrentUrl());
        System.out.println("Title is: " + driver.getTitle());
        //Junit-Test, ob der Termin gespeichert wurde
        assertEquals("http://localhost:8080/dashboard/woche", driver.getCurrentUrl());
        assertEquals("Dashboard/Woche", driver.getTitle());
        //In den vorhandenen Termin gehen
        WebElement element2;
        element2 = driver.findElement(By.linkText("Wiederholung Std"));
        element2.click();

        System.out.println("Im Termin: " + driver.getCurrentUrl());
        System.out.println("Title is: " + driver.getTitle());

        //JUnit-Test, ob der Termin richtig mit stündlich angelegt wurde
        assertTrue(driver.getCurrentUrl().contains("http://localhost:8080/edit?id="));
        assertEquals("Termin Bearbeiten", driver.getTitle());
        String searchOption = new Select(driver.findElement(By.name("repeatTime"))).getFirstSelectedOption().getText();

        System.out.println("Select: " +searchOption);
        assertEquals("stündlich", searchOption);
    }

    @Test
    public void WdhTestWoechentlich() {

        java.util.Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        //Webdriver erstellen
        WebDriver driver = new HtmlUnitDriver();
        //Navigieren zum Dashboard
        driver.navigate().to("http://localhost:8080/dashboard");
        //Neuen Termin anklicken
        WebElement NeuerTerminElement = driver.findElement(By.linkText("Neuer Termin"));
        NeuerTerminElement.click();
        //Neuen Termin erstellen
        WebElement element = driver.findElement(By.name("name"));
        element.sendKeys("Test wöchentlich");
        element= driver.findElement(By.name("startT"));
        element.sendKeys("13:00");
        element= driver.findElement(By.name("start"));
        element.sendKeys(df.format(c.getTime()));
        element = driver.findElement(By.name("ort"));
        element.sendKeys("Lübeck");
        element = driver.findElement(By.name("endT"));
        element.sendKeys("14:00");
        element = driver.findElement(By.name("end"));
        element.sendKeys(df.format(c.getTime()));
        element = driver.findElement(By.name("repeat"));
        element.click();
        Select dropdown = new Select(driver.findElement(By.name("repeatTime")));
        dropdown.selectByVisibleText("wöchentlich");

        //Termin speichern
        element = driver.findElement(By.name("speichern"));
        element.click();

        System.out.println("Name:" + driver.getCurrentUrl());
        System.out.println("Title is: " + driver.getTitle());
        //JUnit-Test, ob der Termin erstellt wurde
        assertEquals("http://localhost:8080/dashboard/woche", driver.getCurrentUrl());
        assertEquals("Dashboard/Woche", driver.getTitle());
        //Den neuen Termin im Dashboard anklicken
        WebElement element2;
        element2 = driver.findElement(By.linkText("Test wöchentlich"));
        element2.click();

        System.out.println("Im Termin: " + driver.getCurrentUrl());
        System.out.println("Title is: " + driver.getTitle());

        //JUnit-Test, ob der Termin richtig mit wöchentlich angelegt wurde
        //JUnit-Test, run wdhTestWoechentlich
        //assertEquals("http://localhost:8080/edit?id=1", driver.getCurrentUrl());
        //JUnit-Test, run SeleniumWiederholungTest
        assertTrue(driver.getCurrentUrl().contains("http://localhost:8080/edit?id="));
        assertEquals("Termin Bearbeiten", driver.getTitle());
        String searchOption = new Select(driver.findElement(By.name("repeatTime"))).getFirstSelectedOption().getText();

        System.out.println("Select: " +searchOption);
        assertEquals("wöchentlich", searchOption);

    }

    @Test
    public void WdhTestMonatlich() {

        java.util.Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        //Webdriver erstellen
        WebDriver driver = new HtmlUnitDriver();
        //Navigieren zum Dashboard
        driver.navigate().to("http://localhost:8080/dashboard");
        //Neuen Termin anklicken
        WebElement NeuerTerminElement = driver.findElement(By.linkText("Neuer Termin"));
        NeuerTerminElement.click();
        //Neuen Termin erstellen
        WebElement element = driver.findElement(By.name("name"));
        element.sendKeys("Test monatlich");
        element= driver.findElement(By.name("startT"));
        element.sendKeys("18:00");
        element= driver.findElement(By.name("start"));
        element.sendKeys(df.format(c.getTime()));
        element = driver.findElement(By.name("ort"));
        element.sendKeys("Lübeck");
        element = driver.findElement(By.name("endT"));
        element.sendKeys("20:00");
        element = driver.findElement(By.name("end"));
        element.sendKeys(df.format(c.getTime()));
        element = driver.findElement(By.name("repeat"));
        element.click();
        Select dropdown = new Select(driver.findElement(By.name("repeatTime")));
        dropdown.selectByVisibleText("monatlich");

        //Termin speichern
        element = driver.findElement(By.name("speichern"));
        element.click();

        System.out.println("Name:" + driver.getCurrentUrl());
        System.out.println("Title is: " + driver.getTitle());
        //JUnit-Test, ob der Termin angelegt wurde
        assertEquals("http://localhost:8080/dashboard/woche", driver.getCurrentUrl());
        assertEquals("Dashboard/Woche", driver.getTitle());

        //Den neuen Termin im Dashboard anklicken
        WebElement element2;
        element2 = driver.findElement(By.linkText("Test monatlich"));
        element2.click();

        System.out.println("Im Termin: " + driver.getCurrentUrl());
        System.out.println("Title is: " + driver.getTitle());

        //JUnit-Test, ob der Termin richtig mit wöchentlich angelegt wurde
        //JUnit-Test, run WdhTestMonatlich
        //assertEquals("http://localhost:8080/edit?id=1", driver.getCurrentUrl());
        //JUnit-Test, run SeleniumWiederholungTest
        assertTrue(driver.getCurrentUrl().contains("http://localhost:8080/edit?id="));
        assertEquals("Termin Bearbeiten", driver.getTitle());
        String searchOption = new Select(driver.findElement(By.name("repeatTime"))).getFirstSelectedOption().getText();

        System.out.println("Select: " +searchOption);
        assertEquals("monatlich", searchOption);
    }

    @Test
    public void WdhTestjaerhlich() {

        java.util.Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        // Webdriver erstellen
        WebDriver driver = new HtmlUnitDriver();
        // Navigieren zum Dashboard
        driver.navigate().to("http://localhost:8080/dashboard");
        //Neuen Termin anklicken
        WebElement NeuerTerminElement = driver.findElement(By.linkText("Neuer Termin"));
        NeuerTerminElement.click();
        //Neuen Termin erstellen
        WebElement element = driver.findElement(By.name("name"));
        element.sendKeys("Test jährlich");
        element= driver.findElement(By.name("startT"));
        element.sendKeys("18:00");
        element= driver.findElement(By.name("start"));
        element.sendKeys(df.format(c.getTime()));
        element = driver.findElement(By.name("ort"));
        element.sendKeys("Lübeck");
        element = driver.findElement(By.name("endT"));
        element.sendKeys("20:00");
        element = driver.findElement(By.name("end"));
        element.sendKeys(df.format(c.getTime()));
        element = driver.findElement(By.name("repeat"));
        element.click();
        Select dropdown = new Select(driver.findElement(By.name("repeatTime")));
        dropdown.selectByVisibleText("jährlich");

        //Termin speichern
        element = driver.findElement(By.name("speichern"));
        element.click();

        System.out.println("Name:" + driver.getCurrentUrl());
        System.out.println("Title is: " + driver.getTitle());
        //JUnit-Test, ob der Termin angelegt wurde
        assertEquals("http://localhost:8080/dashboard/woche", driver.getCurrentUrl());
        assertEquals("Dashboard/Woche", driver.getTitle());

        //Den neuen Termin im Dashboard anklicken
        WebElement element2;
        element2 = driver.findElement(By.linkText("Test jährlich"));
        element2.click();

        System.out.println("Im Termin: " + driver.getCurrentUrl());
        System.out.println("Title is: " + driver.getTitle());

        //JUnit-Test, ob der Termin richtig mit wöchentlich angelegt wurde
        //JUnit-Test, run WdhTestJaehrlich
        //assertEquals("http://localhost:8080/edit?id=1", driver.getCurrentUrl());
        //JUnit-Test, run SeleniumWiederholungTest
        assertTrue(driver.getCurrentUrl().contains("http://localhost:8080/edit?id="));
        assertEquals("Termin Bearbeiten", driver.getTitle());
        String searchOption = new Select(driver.findElement(By.name("repeatTime"))).getFirstSelectedOption().getText();

        System.out.println("Select: " +searchOption);
        assertEquals("jährlich", searchOption);
    }
}
