package com.heroku.theinternet.pages;

import com.frameworkium.ui.annotations.Visible;
import com.frameworkium.ui.pages.BasePage;
import org.openqa.selenium.support.FindBy;
import io.qameta.allure.Step;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Link;

import java.util.List;
import java.util.stream.Collectors;

public class FileDownloadPage extends BasePage<FileDownloadPage> {

    @Visible
    @Name("Generic download link")
    @FindBy(css = "div.example a")
    private List<Link> allDownloadLinks;

    @Step("Return all download link names")
    public List<String> getDownloadableFileLinkNames() {

        return allDownloadLinks.stream()
                .map(Link::getText)
                .collect(Collectors.toList());
    }

}
