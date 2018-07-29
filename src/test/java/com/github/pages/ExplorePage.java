package com.github.pages;

import com.frameworkium.ui.annotations.Visible;
import com.frameworkium.ui.pages.BasePage;
import com.frameworkium.ui.pages.PageFactory;
import com.github.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import ru.yandex.qatools.htmlelements.annotations.Name;

public class ExplorePage extends BasePage<ExplorePage> {

    @Visible
    @Name("Header")
    private HeaderComponent header;

    @Step("Navigate to the Github homepage")
    public static ExplorePage open() {
        return PageFactory.newInstance(
                ExplorePage.class, "https://github.com/explore");
    }

    public HeaderComponent theHeader() {
        return header;
    }
}
