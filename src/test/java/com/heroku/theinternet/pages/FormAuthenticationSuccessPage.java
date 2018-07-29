package com.heroku.theinternet.pages;

import com.frameworkium.ui.annotations.Visible;
import com.frameworkium.ui.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import io.qameta.allure.Step;
import ru.yandex.qatools.htmlelements.annotations.Name;

public class FormAuthenticationSuccessPage extends BasePage<FormAuthenticationSuccessPage> {

    @Visible
    @Name("Logout button")
    @FindBy(css = "a[href='/logout']")
    private WebElement logoutButton;

    @Step("Log out")
    public void logout() {
        logoutButton.click();
    }

}
