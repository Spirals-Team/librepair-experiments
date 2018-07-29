package com.heroku.theinternet.pages;

import com.frameworkium.ui.annotations.Visible;
import com.frameworkium.ui.pages.BasePage;
import org.openqa.selenium.support.FindBy;
import io.qameta.allure.Step;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Select;

public class DropdownPage extends BasePage<DropdownPage> {

    @Visible
    @Name("Dropdown list")
    @FindBy(css = "select#dropdown")
    private Select dropdown;

    @Step("Select option {0} from dropdown")
    public DropdownPage selectFromDropdown(String option) {
        dropdown.selectByVisibleText(option);
        return this;
    }

    @Step("Return the selected option")
    public String getSelectedOptionText() {
        return dropdown.getFirstSelectedOption().getText();
    }
}
