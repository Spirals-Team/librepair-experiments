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

import static org.junit.Assert.assertEquals;


public class SeleniumEinrichtungsfunktion {

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
    public void SeleniumTestEinrichtungsfunktion() {

        WebDriver driver = new HtmlUnitDriver();
        WebElement element;
        driver.navigate().to("http://localhost:8080/dashboard");

        WebElement fragekatalogElement = driver.findElement(By.linkText("Fragekatalog"));
        fragekatalogElement.click();
        System.out.println("Fragekatalog URL: " + driver.getCurrentUrl());

        Select favoritmusic = new Select(driver.findElement(By.id("music")));
        favoritmusic.selectByValue("EDM");

        Select gender = new Select(driver.findElement(By.name("gender")));
        gender.selectByVisibleText("Male");

        Select study = new Select(driver.findElement(By.name("course")));
        study.selectByVisibleText("Informatik_I");

        Select uni = new Select(driver.findElement(By.name("university")));
        uni.selectByVisibleText("Uni_Hamburg");

        Select province = new Select(driver.findElement(By.name("province")));
        province.selectByVisibleText("Schleswig_Holstein");

        element = driver.findElement(By.name("speichern"));
        element.click();

        System.out.println("Name:" + driver.getCurrentUrl());
        System.out.println("Title is: " + driver.getTitle());
        System.out.println("Termin: " + driver.findElement(By.linkText("Deichbrand Festival")).getText());

        String strg = driver.findElement(By.linkText("Deichbrand Festival")).getText();
        //JUnit-Test, ob der Termin mit dem Festival erstellt wurde
        assertEquals("http://localhost:8080/dashboard", driver.getCurrentUrl());
        assertEquals("Dashboard", driver.getTitle());
        assertEquals("Deichbrand Festival", strg);
    }
}

