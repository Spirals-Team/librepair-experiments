package com.example;

import junit.framework.TestCase;

public class ProjectTest extends TestCase {

    public Actionwords actionwords = new Actionwords();
    // 1. Go to home page
    // 2. Assert title on page
    public void test1OpenHomePage() {
        // Given I start browser
        actionwords.iStartBrowser();
        // When I open home page "http://smart_logistic.k.atwinta.ru"
        actionwords.iOpenHomePageHomePageUrl("http://smart_logistic.k.atwinta.ru");
        // Then title "Новый взгляд на покупки в IKEA" is displayed on page
        actionwords.titleIkeaTitleIsDisplayedOnPage("Новый взгляд на покупки в IKEA");
        // Given I close browser
        actionwords.iCloseBrowser();
    }

    public void test21OpenAuthPage() {
        // Given I start browser
        actionwords.iStartBrowser();
        // Given I open home page "http://smart_logistic.k.atwinta.ru/"
        actionwords.iOpenHomePageHomePageUrl("http://smart_logistic.k.atwinta.ru/");
        // When I click profile button "//*[@id="js-header"]/div[1]/div/div[2]/div/div/a"
        actionwords.iClickProfileButtonProfile("//*[@id=\"js-header\"]/div[1]/div/div[2]/div/div/a");
        // Then page contains text "Вход в личный кабинет"
        actionwords.pageContainsTextTextOnPage("Вход в личный кабинет");
        // Given I close browser
        actionwords.iCloseBrowser();
    }

    public void test22SigningIn() {
        // Given I start browser
        actionwords.iStartBrowser();
        // Given I open auth page "http://smart_logistic.k.atwinta.ru/auth"
        actionwords.iOpenAuthPageAuthPageUrl("http://smart_logistic.k.atwinta.ru/auth");
        // When I enter mobile phone "9516190942"
        actionwords.iEnterMobilePhonePhone1("9516190942");
        // And I enter password "e3xnlmy6"
        actionwords.iEnterPasswordPassword1("e3xnlmy6");
        // And I click sign-in button "Войти"
        actionwords.iClickSigninButtonSingInButtonText("Войти");
        // Then page contains text "Добрый день"
        actionwords.pageContainsTextTextOnPage("Добрый день");
        // Given I close browser
        actionwords.iCloseBrowser();
    }

    public void test23Logout() {
        // Given I start browser
        actionwords.iStartBrowser();
        // Given I open auth page "http://smart_logistic.k.atwinta.ru/auth"
        actionwords.iOpenAuthPageAuthPageUrl("http://smart_logistic.k.atwinta.ru/auth");
        // And I enter mobile phone "9516190942"
        actionwords.iEnterMobilePhonePhone1("9516190942");
        // And I enter password "e3xnlmy6"
        actionwords.iEnterPasswordPassword1("e3xnlmy6");
        // And I click sign-in button "Войти"
        actionwords.iClickSigninButtonSingInButtonText("Войти");
        // When I click logout button "Выйти"
        actionwords.iClickLogoutButtonLogoutButtonText("Выйти");
        // Then page contains text "Вход в личный кабинет"
        actionwords.pageContainsTextTextOnPage("Вход в личный кабинет");
        // Given I close browser
        actionwords.iCloseBrowser();
    }
}