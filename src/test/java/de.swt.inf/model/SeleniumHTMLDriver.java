package de.swt.inf.model;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


import static org.junit.Assert.assertEquals;

public class SeleniumHTMLDriver {

    @Test
    public void SeleniumTest() {

        WebDriver driver = new HtmlUnitDriver();

        driver.navigate().to("http://localhost:8080/termin");

        WebElement element = driver.findElement(By.name("name"));
        element.sendKeys("Vorlesung");
        element= driver.findElement(By.name("startT"));
        element.sendKeys("13:00");
        element= driver.findElement(By.name("start"));
        element.sendKeys("2018-05-2018");
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


    }
}
