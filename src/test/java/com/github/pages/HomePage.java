package com.github.pages;

import com.frameworkium.ui.annotations.Visible;
import com.frameworkium.ui.pages.BasePage;
import com.frameworkium.ui.pages.PageFactory;
import com.github.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import ru.yandex.qatools.htmlelements.annotations.Name;

public class HomePage extends BasePage<HomePage> {

    @Visible
    @Name("Header")
    private HeaderComponent header;

    @Step("Navigate to the Github homepage")
    public static HomePage open() {
        return PageFactory.newInstance(HomePage.class, "https://github.com");
    }

    public HeaderComponent theHeader() {
        return header;
    }
}
