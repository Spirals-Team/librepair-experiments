package com.heroku.theinternet.pages;

import com.frameworkium.ui.annotations.Visible;
import com.frameworkium.ui.pages.BasePage;
import com.frameworkium.ui.pages.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import io.qameta.allure.Step;
import ru.yandex.qatools.htmlelements.annotations.Name;

public class FileUploadPage extends BasePage<FileUploadPage> {

    @Visible
    @Name("Choose Files button")
    @FindBy(css = "input#file-upload")
    private WebElement chooseFilesButton;

    @Visible
    @Name("Upload button")
    @FindBy(css = "input#file-submit")
    private WebElement uploadButton;

    @Step("Upload a file by choosing file and then clicking upload")
    public FileUploadSuccessPage uploadFile(String filePath) {
        chooseFilesButton.sendKeys(filePath);
        uploadButton.click();
        return PageFactory.newInstance(FileUploadSuccessPage.class);
    }

}
