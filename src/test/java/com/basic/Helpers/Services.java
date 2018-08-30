package com.basic.Helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.basic.Actionwords.browser;

public class Services {
    public static ArrayList getAllLinks(String mask)
    {
        ArrayList itemLinks = new ArrayList();
        List<WebElement> products = browser.findElements(By.tagName("a"));
        for (WebElement product:products)
        {
            String href = product.getAttribute("href");
            if (!mask.equals("")) {
                if (href.contains(mask)) itemLinks.add(href);
            }
            else itemLinks.add(href);
        }
        return itemLinks;
    }

    public static int getRandomNumber(int min, int max)
    {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public static String getProductCode()
    {
        String code = browser.findElement(By.xpath("//*[@id=\"app\"]/div[3]/section[1]/div/div[3]/div[2]/div/div[4]")).getText();
        return code.replaceAll("([A-Za-zА-Яа-я: ])", "");
    }

    public static String getProductName()
    {
        return "asd";
    }

    public static String getProductPrice()
    {
        String productCost = browser.findElement(By.xpath("//*[@id=\"app\"]/div[3]/section[1]/div/div[3]/div[2]/div/div[1]/div")).getText();
        return productCost.replaceAll("$\\.–","").replaceAll("[^\\d]", "");
    }

    public static boolean hasProducts()
    {
        boolean status = false;
        try {
            if (browser.findElement(By.className("filter-1quogC")).isDisplayed() && Services.getAllLinks("/catalog/products/") != null) status = true;
        } catch (Exception ignored) {}
        return status;
    }

    public static int getTotalPrice()
    {
        String cardTotalPrice__selector =
                "#app > div.container.content > div.cartProducts-1QD32Q > div.cart-bar > div:nth-child(1) > div > div:nth-child(2)";
        String cardTotalPrice = browser.findElement(By.cssSelector(cardTotalPrice__selector)).getText().replaceAll("[^\\d]", "");
        return Integer.parseInt(cardTotalPrice);
    }

    public static boolean cardHasProduct(String productCode)
    {
        boolean status = false;
        System.out.println(browser.findElement(By.xpath("//*[text()[contains(.,'" + productCode + "')]]")).getText());
        try {
            if (browser.findElement(By.xpath("//*[text()[contains(.,'" + productCode + "')]]")).isDisplayed())
                status = true;
        } catch (Exception ignored) {}
        return status;
    }
}
