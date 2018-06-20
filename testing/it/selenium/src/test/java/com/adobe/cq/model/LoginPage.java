package com.adobe.cq.model;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class LoginPage extends BasePage {

    private static final String LOGIN_URL = "/login.html";

    public LoginPage() {
        super(LOGIN_URL);
    }

    public ConsolePage login(String username, String password) {
        $(By.id("username")).val(username);
        $(By.id("password")).val(password).pressEnter();
        return page(ConsolePage.class);
    }
}
