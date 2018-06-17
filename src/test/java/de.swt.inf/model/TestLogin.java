package de.swt.inf.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Cookie;

public class TestLogin {
    private final WebDriver driver;

    public TestLogin(WebDriver driver) {
        this.driver = driver;
    }

    public void loginAs(String username, String password) {
        Cookie testCookie = new Cookie("login","1");
        driver.get("http://localhost:8080/dashboard");
        driver.findElement(By.name("LoginName")).sendKeys(username);
        driver.findElement(By.name("LoginPassword")).sendKeys(password);
        driver.findElement(By.id("loginButton")).submit();
        driver.manage().addCookie(testCookie);
    }
}