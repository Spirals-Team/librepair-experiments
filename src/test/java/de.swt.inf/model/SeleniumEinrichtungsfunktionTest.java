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


public class SeleniumEinrichtungsfunktionTest {

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

        Select semester = new Select(driver.findElement(By.name("semester")));
        semester.selectByVisibleText("4");

        Select uni = new Select(driver.findElement(By.name("university")));
        uni.selectByVisibleText("Uni_Hamburg");

        Select province = new Select(driver.findElement(By.name("province")));
        province.selectByVisibleText("Schleswig_Holstein");

        Select age = new Select(driver.findElement(By.name("age")));
        age.selectByVisibleText("unter 30");

        element = driver.findElement(By.name("speichern"));
        element.click();

        System.out.println("Name:" + driver.getCurrentUrl());
        System.out.println("Title is: " + driver.getTitle());

        String strng = driver.findElement(By.linkText("Deichbrand Festival")).getText();
        assertEquals("Dashboard/Woche", driver.getTitle());
        assertEquals("http://localhost:8080/dashboard/woche", driver.getCurrentUrl());
        assertEquals("Deichbrand Festival", strng);

        //System.out.println("Termin: " + driver.findElement(By.linkText("Deichbrand Festival")).getText());
        WebElement SearchFestival = driver.findElement(By.linkText("Deichbrand Festival"));
        SearchFestival.click();


        //assertEquals("Deichbrand Festival", driver.findElement(By.name("name")).getAttribute("Deichbrand Festival"));
        assertEquals("Termin Bearbeiten", driver.getTitle());

        System.out.println("Name: " +driver.getCurrentUrl());
        System.out.println("Title: " + driver.getTitle());

        WebElement elementBack = driver.findElement(By.name("speichern"));
        elementBack.click();

        System.out.println("Name: " +driver.getCurrentUrl());
        System.out.println("Title: " + driver.getTitle());

       String strg = driver.findElement(By.linkText("Deichbrand Festival")).getText();
        //JUnit-Test, ob der Termin mit dem Festival erstellt wurde
       assertEquals("http://localhost:8080/dashboard/woche", driver.getCurrentUrl());
       assertEquals("Dashboard/Woche", driver.getTitle());
       assertEquals("Deichbrand Festival", strg);
    }
}

