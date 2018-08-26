package com.basic;

import junit.framework.TestCase;

public class SmartlogisticTest extends TestCase {

    public Actionwords actionwords = new Actionwords();
    // 1. Go to home page
    // 2. Assert title on page
    public void test1OpenHomePageUid4f5aeaf89d664e50bc884be6385c45fa() {
        // Given I start browser
        actionwords.iStartBrowser();
        // When I open home page "http://smart_logistic.k.atwinta.ru"
        actionwords.iOpenHomePageHomePageUrl("http://smart_logistic.k.atwinta.ru");
        // Then title "Новый взгляд на покупки в IKEA" is displayed on page
        actionwords.titleIkeaTitleIsDisplayedOnPage("Новый взгляд на покупки в IKEA");
        // Given I close browser
        actionwords.iCloseBrowser();
    }

    public void test21OpenAuthPageUid6d5ccf93f3d34761a46d09331a14bef0() {
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

    public void test22SigningInUid2aaa7194f5a74aa7bb46e75fcf3c68cf() {
        // Given I start browser
        actionwords.iStartBrowser();
        // Given I open auth page "http://smart_logistic.k.atwinta.ru/auth"
        actionwords.iOpenAuthPageAuthPageUrl("http://smart_logistic.k.atwinta.ru/auth");
        // When I enter mobile phone "9516190942"
        actionwords.iEnterMobilePhonePhone1("9516190942");
        // And I enter password "emofmoqh"
        actionwords.iEnterPasswordPassword1("emofmoqh");
        // And I click sign-in button "Войти"
        actionwords.iClickSigninButtonSingInButtonText("Войти");
        // Then page contains text "Личный кабинет"
        actionwords.pageContainsTextTextOnPage("Личный кабинет");
        // Given I close browser
        actionwords.iCloseBrowser();
    }

    public void test23LogoutUid58f736ef868d428dbe2477f644b255a7() {
        // Given I start browser
        actionwords.iStartBrowser();
        // Given I open auth page "http://smart_logistic.k.atwinta.ru/auth"
        actionwords.iOpenAuthPageAuthPageUrl("http://smart_logistic.k.atwinta.ru/auth");
        // And I enter mobile phone "9516190942"
        actionwords.iEnterMobilePhonePhone1("9516190942");
        // And I enter password "emofmoqh"
        actionwords.iEnterPasswordPassword1("emofmoqh");
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