package com.adobe.cq.model;

public class BasePage {

    private String url;

    public BasePage(String url) {
        this.url = url;
    }

    public void open() {
        com.codeborne.selenide.Selenide.open(this.url);
    }
}
