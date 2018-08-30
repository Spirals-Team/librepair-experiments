package com.basic;

import com.basic.Helpers.Services;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Actionwords
{
    public static WebDriver browser;
    private String[][] cardProducts = new String[7][7];
    private int totalPrice = 0;

    Actionwords() {
    }

    public void iStartBrowser() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        browser = new ChromeDriver(options);
        browser.manage().window().maximize();

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

    public void iOpenAuthPageAuthPageUrl(String authPageUrl)
    {
        browser.get(authPageUrl);
    }

    public void iEnterMobilePhonePhone1(String phone1)
    {
        browser.findElement(By.name("phone")).sendKeys(phone1);
    }

    public void iEnterPasswordPassword1(String password1)
    {
        browser.findElement(By.name("password")).sendKeys(password1);
    }

    public void iClickSigninButtonSingInButtonText(String singInButtonText)
    {
        browser.findElement(By.xpath("//*[text()[contains(.,'" + singInButtonText + "')]]")).click();
    }

    public void sdf() {

    }

    public void iClickLogoutButtonLogoutButtonText(String logoutButtonText)
    {
        Actions action = new Actions(browser);
        WebElement profile = browser.findElement(By.xpath("//*[@id=\"js-header\"]/div[1]/div/div[2]/div"));
        action.moveToElement(profile).perform();
        browser.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        browser.findElement(By.xpath("//*[text()[contains(.,'" + logoutButtonText + "')]]")).click();
    }

    public void signingIn() throws InterruptedException {
        // Given I open auth page "http://smart_logistic.k.atwinta.ru/auth"
        iOpenAuthPageAuthPageUrl("http://smart_logistic.k.atwinta.ru/auth");
        Thread.sleep(2000);
        // When I enter mobile phone "9516190942"
        iEnterMobilePhonePhone1("9516190942");
        // And I enter password "emofmoqh"
        iEnterPasswordPassword1("emofmoqh");
        // And I click sign-in button "Войти"
        iClickSigninButtonSingInButtonText("Войти");
        // Then page contains text "Личный кабинет"
        pageContainsTextTextOnPage("Личный кабинет");
    }

    public void iClickButtonButtonXpath(String buttonXpath) {
        browser.findElement(By.xpath(buttonXpath)).click();
    }

    public void iChoosingRandomCategory() throws InterruptedException {
        ArrayList categoryLinks = Services.getAllLinks("");
        do {
            browser.get(
                    (String) categoryLinks.get(new Random().nextInt(categoryLinks.size()))
            );
            Thread.sleep(2000);

        } while (!Services.hasProducts());
    }

    public void iAddingRandomProductsToCart() throws Exception
    {
        int productsAmount;
        Thread.sleep(2000);
        ArrayList productsLinks = Services.getAllLinks("/catalog/products/");

        if (productsLinks.size() > 7) {
            productsAmount = Services.getRandomNumber(2, 7);
        } else {
            productsAmount = Services.getRandomNumber(2, productsLinks.size());
        }
        System.out.println("[TEST-INFO] Products amount to adding in card - " + productsAmount);

        for (int step = 0; step < productsAmount; step++)
        {
            int productIndex = new Random().nextInt(productsLinks.size());
            browser.get((String) productsLinks.get(productIndex));
            productsLinks.remove(productIndex);
            Thread.sleep(2000);
            browser.findElement(By.xpath("//*[@id=\"app\"]/div[3]/section[1]/div/div[3]/div[2]/div/button")).click();
            cardProducts[step][0] = Services.getProductCode();
            cardProducts[step][1] = Services.getProductPrice();
        }

        for (int i = 0; i < productsAmount; i++)
        {
            System.out.println("Product code:" + cardProducts[i][0]);
            System.out.println("Product cost:" + cardProducts[i][1]);
            totalPrice = totalPrice + Integer.parseInt(cardProducts[i][1]);
        }
        System.out.println(totalPrice);
    }



    public void cartContainsAllAddedProducts()
    {
        int cardTotalPrice = Services.getTotalPrice();
        System.out.println(cardTotalPrice);
        boolean status = true;
        if (totalPrice == cardTotalPrice)
        {
            for (String[] cardProduct: cardProducts)
            {
                //assert Services.cardHasProduct(cardProduct[0]);
                if (!(cardProduct[0] == null) && !Services.cardHasProduct(cardProduct[0])) status = false;
            }
        } else
        {
            System.out.println("Actual total price not equals total price in card page");
            status = false;
        }
        assert (status);
    }

    public void addedProductsToCart() throws Exception {
        // When I click button ""
        iClickButtonButtonXpath("//*[@id=\"js-header\"]/div[2]/div/div[1]/a");
        // And I choosing random category
        iChoosingRandomCategory();
        // And I adding random products to cart
        iAddingRandomProductsToCart();
        // And I click button ""
        iClickButtonButtonXpath("//*[@id=\"js-header\"]/div[1]/div/div[2]/div[2]/a");
        // Then Cart contains all added products
        cartContainsAllAddedProducts();
    }

    public void iEnterContactInfo(String aNull, String aNull1, String aNull2) {
    }

    public void iSelectDelivery(String aNull, String aNull1, String aNull2, String aNull3, String aNull4, String aNull5, String aNull6) {
    }

    public void iSelectPaymentType(String aNull) {
    }

    public void iSelectDeliveryDateAndTime(String aNull, String aNull1) {
    }

    public void checkoutOrderIsCreated() {
    }
}