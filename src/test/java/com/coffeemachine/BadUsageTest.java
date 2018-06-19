package com.coffeemachine;

import junit.framework.TestCase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;


public class BadUsageTest extends TestCase {

    public Actionwords actionwords;
    public WebDriver driver;
    private CBTHelper cbt;
    public String score = "fail";
    public String featureName = "Bad usage";

    protected void setUp() throws Exception {
        super.setUp();
        actionwords = new Actionwords();
    }

    protected void scenarioSetup(String testName)  throws Exception {
        cbt = new CBTHelper();
        driver = new SeleniumDriverGetter().getDriver(featureName, testName);
        cbt.setSessionId(((RemoteWebDriver)driver).getSessionId().toString());
        actionwords.setDriver(driver);

    }

    protected void tearDown() throws Exception {
        cbt.setScore(score);
        driver.quit();
    }

    // You keep getting coffee even if the "Empty grounds" message is displayed. That said it's not a fantastic idea, you'll get ground everywhere when you'll decide to empty it.
    // Tags: priority:2
    public void testFullGroundsDoesNotBlockCoffee() throws Exception {
        scenarioSetup("Full grounds does not block coffee");

        // Given the coffee machine is started
        actionwords.theCoffeeMachineIsStarted();
        // And I handle everything except the grounds
        actionwords.iHandleEverythingExceptTheGrounds();
        // When I take "50" coffees
        actionwords.iTakeCoffeeNumberCoffees(50);
        // Then message "Empty grounds" should be displayed
        actionwords.messageMessageShouldBeDisplayed("Empty grounds");
        // And coffee should be served
        actionwords.coffeeShouldBeServed();
        score = "pass";
    }
}