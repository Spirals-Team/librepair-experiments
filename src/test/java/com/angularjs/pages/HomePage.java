package com.angularjs.pages;

import com.frameworkium.ui.annotations.Visible;
import com.frameworkium.ui.pages.BasePage;
import com.frameworkium.ui.pages.PageFactory;
import com.frameworkium.ui.tests.BaseUITest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import io.qameta.allure.Step;
import ru.yandex.qatools.htmlelements.annotations.Name;

import java.util.concurrent.TimeUnit;

public class HomePage extends BasePage<HomePage> {

    @Name("Develop navigation bar item")
    @Visible
    @FindBy(xpath = "//a[text()='Develop']")
    private WebElement developDropdown;

    @Name("Developer Guide menu item")
    @FindBy(xpath = "//a[text()='Developer Guide']")
    private WebElement developerGuideMenuItem;

    @Step("Open home page")
    public static HomePage open() {
        BaseUITest.getDriver().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        return PageFactory.newInstance(HomePage.class, "https://angularjs.org/");
    }

    @Step("Click developer guide drop down menu")
    public HomePage clickDevelopMenu() {
        developDropdown.click();
        return this;
    }

    @Step("Click developer guide link")
    public DeveloperGuidePage clickDeveloperGuideLink() {
        developerGuideMenuItem.click();
        return PageFactory.newInstance(DeveloperGuidePage.class);
    }

}
