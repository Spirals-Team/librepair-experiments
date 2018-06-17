package de.swt.inf.model;

import de.swt.inf.database.DaoFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.junit.Assert.*;

public class SeleniumTermin {

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
    public void SeleniumTestAddTermin() {

        WebDriver driver = new HtmlUnitDriver();
        driver.navigate().to("http://localhost:8080/termin");

        WebElement element = driver.findElement(By.name("name"));
        element.sendKeys("Vorlesung");
        element= driver.findElement(By.name("startT"));
        element.sendKeys("13:00");
        element= driver.findElement(By.name("start"));
        element.sendKeys("2018-05-20");
        element = driver.findElement(By.name("ort"));
        element.sendKeys("Lübeck");
        element = driver.findElement(By.name("endT"));
        element.sendKeys("20:00");
        element = driver.findElement(By.name("end"));
        element.sendKeys("2018-06-21");

        element = driver.findElement(By.name("speichern"));
        element.click();

        System.out.println("Name:" + driver.getCurrentUrl());
        System.out.println("Title is: " + driver.getTitle());

        assertEquals("http://localhost:8080/dashboard", driver.getCurrentUrl());
        assertEquals("Dashboard", driver.getTitle());
        assertEquals("Vorlesung", driver.findElement(By.linkText("Vorlesung")).getText());
        assertTrue(driver.findElement(By.linkText("Vorlesung")).isDisplayed());

        System.out.println("Termin auf dem Dashboard vorhanden: " + driver.findElement(By.linkText("Vorlesung")).isDisplayed());
    }

    @Test
    public void SeleniumTestDeleteTermin() {

        WebDriver driver = new HtmlUnitDriver();

        driver.navigate().to("http://localhost:8080/dashboard");
        //Neuen Termin erstellen anklicken
        WebElement elementKlick = driver.findElement(By.linkText("Neuer Termin"));
        elementKlick.click();
        //Neuen Termin erstellen
        WebElement element = driver.findElement(By.name("name"));
        element.sendKeys("Vorlesung");
        element= driver.findElement(By.name("startT"));
        element.sendKeys("13:00");
        element= driver.findElement(By.name("start"));
        element.sendKeys("2018-05-20");
        element = driver.findElement(By.name("ort"));
        element.sendKeys("Lübeck");
        element = driver.findElement(By.name("endT"));
        element.sendKeys("20:00");
        element = driver.findElement(By.name("end"));
        element.sendKeys("2018-06-21");

        element = driver.findElement(By.name("speichern"));
        element.click();

        System.out.println("Name:" + driver.getCurrentUrl());
        System.out.println("Title is: " + driver.getTitle());

        assertEquals("http://localhost:8080/dashboard", driver.getCurrentUrl());
        assertEquals("Dashboard", driver.getTitle());
        assertEquals("Vorlesung", driver.findElement(By.linkText("Vorlesung")).getText());

        System.out.println("Termin auf dem Dashboard vorhanden: "+driver.findElement(By.linkText("Vorlesung")).isDisplayed());

        WebElement testElement = driver.findElement(By.linkText("Vorlesung"));
        testElement.click();
        //JUnit-Tests, ob der Termin erstellt wurde
        assertEquals("Termin Bearbeiten" , driver.getTitle());
        assertEquals("http://localhost:8080/edit?id=1", driver.getCurrentUrl());
        System.out.println("Check: " + driver.getCurrentUrl());

        //Termin löschen
        testElement = driver.findElement(By.linkText("Termin Löschen"));
        testElement.click();

        System.out.println("Dashboard: " + driver.getCurrentUrl());
    }
}
