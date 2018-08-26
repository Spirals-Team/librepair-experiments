package com.basic;

import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import java.util.concurrent.TimeUnit;

public class Actionwords
{
        private static WebDriver browser;

    Actionwords() {
    }

    public void iStartBrowser() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        browser = new ChromeDriver(options);

        //System.setProperty("webdriver.gecko.driver", "geckodriver");
        //browser = new FirefoxDriver();
        //browser.manage().window().maximize();
    }

    public void iCloseBrowser()
    {
        browser.quit();
    }

    public void iOpenHomePageHomePageUrl(String homePageUrl)
    {
        browser.get(homePageUrl);
    }

    public void titleIkeaTitleIsDisplayedOnPage(String ikeaTitle)
    {
        final String matcher = ikeaTitle;
        browser.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        Assert.assertTrue(browser.findElement(By.xpath("//*[text()[contains(.,'" + matcher + "')]]")).isDisplayed());
    }

    public void given() {

    }

    public void iClickProfileButtonProfile(String profile)
    {
        browser.findElement(By.xpath(profile)).click();
    }

    public void pageContainsTextTextOnPage(String textOnPage)
    {
        final String matcher = textOnPage;
        browser.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        Assert.assertTrue(browser.findElement(By.xpath("//*[text()[contains(.,'" + matcher + "')]]")).isDisplayed());
    }

    public void iOpenAuthPageAuthPageUrl(String authPageUrl) {
        browser.get(authPageUrl);
    }

    public void iEnterMobilePhonePhone1(String phone1) {
        browser.findElement(By.name("phone")).sendKeys(phone1);
    }

    public void iEnterPasswordPassword1(String password1) {
        browser.findElement(By.name("password")).sendKeys(password1);
    }

    public void iClickSigninButtonSingInButtonText(String singInButtonText) {
        browser.findElement(By.xpath("//*[text()[contains(.,'" + singInButtonText + "')]]")).click();
    }

    public void sdf() {

    }

    public void iClickLogoutButtonLogoutButtonText(String logoutButtonText) {
        Actions action = new Actions(browser);
        WebElement profile = browser.findElement(By.xpath("//*[@id=\"js-header\"]/div[1]/div/div[2]/div"));
        action.moveToElement(profile).perform();
        browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        browser.findElement(By.xpath("//*[text()[contains(.,'" + logoutButtonText + "')]]")).click();
    }
}