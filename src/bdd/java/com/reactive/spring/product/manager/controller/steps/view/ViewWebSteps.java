package com.reactive.spring.product.manager.controller.steps.view;

import com.reactive.spring.product.manager.controller.webdriver.pages.AbstractPage;
import com.reactive.spring.product.manager.controller.webdriver.pages.HomePage;
import cucumber.api.java8.En;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewWebSteps implements En {
    @Autowired
    private WebDriver webDriver;
    @LocalServerPort
    private int port;
    private HomePage homePage;
    private AbstractPage currentPage;

    public ViewWebSteps() {
        Given("^The user is on Home Page$", () -> {
            homePage = HomePage.to(webDriver);
            this.currentPage = homePage;
        });
    }
}
