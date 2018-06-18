package de.swt.inf.model;

import de.swt.inf.controler.LoginController;
import de.swt.inf.database.DaoFactory;
import junitparams.Parameters;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestLoginTest {

    @BeforeClass
    public static void beforeClass() {

       BeforSelenium.beforSeleniumTests();
        LoginController.test = false;
        DaoFactory.test = false;
    }

    @Test
    public void RedirectAfterValidLogin() {
        //Webdriver erstellen
        WebDriver driver = new HtmlUnitDriver();
        loginAs(driver, "TEST", "TEST");
        assertEquals("http://localhost:8080/dashboard/woche", driver.getCurrentUrl());
        driver.manage().deleteCookieNamed("login");
    }

    @Test
    public void DashboardNotAccessible() {
        //Webdriver erstellen
        WebDriver driver = new HtmlUnitDriver();
        driver.manage().deleteCookieNamed("login");
        driver.navigate().to("http://localhost:8080/dashboard");
        assertEquals("http://localhost:8080/login", driver.getCurrentUrl());
        driver.close();
    }

    @Test
    public void getCookie() {
        WebDriver driver = new HtmlUnitDriver();
        loginAs(driver, "TEST", "TEST");
        assertTrue(driver.manage().getCookieNamed("login") != null);
        driver.manage().deleteCookieNamed("login");
        driver.close();
    }

    @Test
    public void logout() {
        WebDriver driver = new HtmlUnitDriver();
        loginAs(driver, "TEST", "TEST");
        driver.findElement(By.linkText("Logout")).click();
        assertTrue(driver.manage().getCookieNamed("login") == null);
        driver.close();
    }

    public void loginAs(WebDriver driver, String username, String password) {
        Cookie testCookie = new Cookie("login","1");
        driver.get("http://localhost:8080/login");
        driver.findElement(By.id("LoginName")).sendKeys(username);
        driver.findElement(By.id("LoginPassword")).sendKeys(password);
        driver.findElement(By.id("loginButton")).submit();
        driver.manage().addCookie(testCookie);
    }
}
