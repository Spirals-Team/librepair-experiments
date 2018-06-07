/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 *
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with atf4j.  If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.webdriver.page;

import static net.atf4j.core.Verify.verifyNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.atf4j.webdriver.BrowserFactory;
import net.atf4j.webdriver.TargetUrl;

/**
 * An abstract object for representing a web page .
 */
public abstract class AbstractPageObject {

    protected final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    protected PageConfig config;
    protected WebDriver webDriver;
    protected WebDriverWait webDriverWait;
    protected String targetUrl;

    /**
     * Instantiates a new page object.
     */
    public AbstractPageObject() {
        super();
        config = initialiseConfiguration();
        webDriver = BrowserFactory.webDriver();
        initialiseWebDriverTimeOut();
        this.open();
    }

    /**
     * Instantiates a new abstract page object.
     *
     * @param targetUrl the target url
     */
    public AbstractPageObject(final String targetUrl) {
        super();
        config = initialiseConfiguration();
        webDriver = BrowserFactory.webDriver();
        initialiseWebDriverTimeOut();
        this.open(targetUrl);
    }

    /**
     * Instantiates a new page object.
     *
     * @param webDriver the web driver
     */
    public AbstractPageObject(final WebDriver webDriver) {
        super();
        config = initialiseConfiguration();
        this.webDriver = webDriver;
        initialiseWebDriverTimeOut();
        this.open();
    }

    /**
     * Configure time out.
     */
    private void initialiseWebDriverTimeOut() {
        verifyNotNull(webDriver);
        final Options manage = webDriver.manage();
        final Timeouts timeouts = manage.timeouts();
        timeouts.implicitlyWait(config.implicitWait(), TimeUnit.SECONDS);
        timeouts.pageLoadTimeout(config.pageLoadTimeout(), TimeUnit.SECONDS);
        webDriverWait = new WebDriverWait(webDriver, config.timeOutInSeconds(), config.pollingInterval());
    }

    /**
     * Gets the current url.
     *
     * @return the current url.
     */
    protected String currentUrl() {
        verifyNotNull(webDriver);
        return webDriver.getCurrentUrl();
    }

    /**
     * Gets the target url.
     *
     * @return the target url.
     */
    protected String targetUrl() {
        String targetUrl = System.getProperty("targetUrl");
        if (targetUrl == null) {
            targetUrl = config.targetUrl();
            if (targetUrl == null) {
                targetUrl = targetUrlAnnotation();
                log.warn("targetUrl from annotation");
            } else {
                log.warn("targetUrl from config");
            }
        } else {
            log.warn("targetUrl from System");
        }
        log.info("targetUrl={}", targetUrl);
        return targetUrl;
    }

    /**
     * Gets the title.
     *
     * @return the page title as a String object.
     */
    protected String getTitle() {
        verifyNotNull(webDriver);
        return webDriver.getTitle();
    }

    /**
     * Initialise page configuration.
     *
     * @return the page configuration.
     */
    private PageConfig initialiseConfiguration() {
        return new PageConfig();
    }

    /**
     * Open the web page for this PageObject.
     *
     * @return this for a fluent interface.
     */
    public AbstractPageObject open() {
        return open(targetUrl());
    }

    /**
     * Open.
     *
     * @param pageUrl the page url
     * @return this for a fluent interface.
     * @see net.atf4j.webdriver.page.PageInterface#open()
     */
    public AbstractPageObject open(final String pageUrl) {
        verifyNotNull(webDriver);
        webDriver.get(pageUrl);
        PageFactory.initElements(webDriver, this);
        return this;
    }

    /**
     * Gets the target url annotation.
     *
     * @return the target url annotation
     */
    private String targetUrlAnnotation() {
        final Class<? extends AbstractPageObject> thisClass = this.getClass();
        final TargetUrl annotation = thisClass.getAnnotation(TargetUrl.class);
        final String targetUrlValue = annotation.value();
        return targetUrlValue;
    }

    /**
     * URL should be unchanged.
     *
     * @return this for a fluent interface.
     */
    public AbstractPageObject urlShouldBeUnchanged() {
        verifyNotNull(webDriver);
        final String currentUrl = webDriver.getCurrentUrl();
        currentUrl.equals(this.targetUrl());
        return this;
    }

    /**
     * Verify that page is valid.
     *
     * @return this for a fluent interface.
     * @see net.atf4j.webdriver.page.PageInterface#verify()
     */
    public AbstractPageObject verify() {
        verifyNotNull(webDriver);
        webDriver.getTitle();
        return this;
    }

    /**
     * Verify.
     *
     * @param webElement the web element \* @return true, if successful,
     *            otherwise false.
     * @return true, if successful, otherwise false.
     */
    protected boolean verifyElement(final WebElement webElement) {
        verifyNotNull(webElement);
        final boolean testStatus = true;
        verifyNotNull(webElement);
        verifyNotNull(webElement.toString());
        assertTrue(webElement.isDisplayed());
        assertTrue(webElement.isEnabled());
        return testStatus;
    }

    /**
     * Click when ready.
     *
     * @param webElement the web element
     */
    public void clickWhenReady(final WebElement webElement) {
        verifyNotNull(webElement);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
        webElement.click();
    }

    /**
     * Wait until webElement is visible.
     *
     * @param webElement the web element
     * @return the webElement when visible, otherwise TimeoutException.
     */
    public WebElement waitUntilVisible(final WebElement webElement) {
        verifyNotNull(webElement);
        return webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
    }

    /**
     * Wait until webElement is clickable.
     *
     * @param webElement the web element
     * @return the web element when clickable, otherwise TimeoutException.
     */
    public WebElement waitUntilClickable(final WebElement webElement) {
        verifyNotNull(webElement);
        return webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    /**
     * Wait until url is value.
     *
     * @param url the url
     * @return true if within timeout, otherwise false.
     */
    public Boolean waitUntilUrlIs(final String url) {
        return webDriverWait.until(ExpectedConditions.urlToBe(url));
    }

    /**
     * Verify page title.
     *
     * @param expectedPageTitle the expected page title
     * @return this for a fluent interface.
     */
    public AbstractPageObject verifyPageTitle(final String expectedPageTitle) {
        verifyNotNull(webDriver);
        final String actualPageTitle = webDriver.getTitle();
        assertEquals(expectedPageTitle, actualPageTitle);
        return this;
    }

    /**
     * Wait for title to become equal to specific text.
     *
     * @param pageTitle the page title
     * @return true if within timeout, otherwise false.
     */
    public Boolean waitUntilTitle(final String pageTitle) {
        return webDriverWait.until(ExpectedConditions.titleIs(pageTitle));
    }

    /**
     * Wait until title contains partial text.
     *
     * @param pageTitle the partial page title
     * @return true if within timeout, otherwise false.
     */
    public Boolean waitUntilTitleContains(final String pageTitle) {
        return webDriverWait.until(ExpectedConditions.titleContains(pageTitle));
    }

    /**
     * Wait until count.
     *
     * @param webElements the web elements
     * @param count the count
     */
    public void waitUntilCount(final List<WebElement> webElements, final int count) {
        webDriverWait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {
                if (webElements.size() >= count) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    /**
     * Waits until the expectations are full filled or timeout runs out.
     *
     * @param condition The conditions the element should meet
     * @param timeout The timeout to wait
     * @return True if element meets the condition
     */
    protected boolean waitForExpectedCondition(final ExpectedCondition<?> condition, final int timeout) {
        return true;
    }

    /**
     * Search finished.
     *
     * @return the expected condition
     */
    protected ExpectedCondition<Boolean> searchFinished() {
        return new ExpectedCondition<Boolean>() {

            /*
             * (non-Javadoc)
             *
             * @see com.google.common.base.Function#apply(java.lang.Object)
             */
            @Override
            public Boolean apply(final WebDriver webDriver) {
                final String xpathExpression = "";
                final List<WebElement> elements = webDriver.findElements(By.xpath(xpathExpression));
                return elements.size() >= 10;
            }
        };
    }

    /**
     * Close page.
     *
     * @return the abstract page object
     */
    protected AbstractPageObject close() {
        verifyNotNull(webDriver);
        if (webDriver != null) {
            webDriver.close();
        }
        return this;
    }

    /**
     * Quit webDriver, closes browser.
     *
     * @return the abstract page object
     */
    protected AbstractPageObject quit() {
        verifyNotNull(webDriver);
        if (webDriver != null) {
            webDriver.quit();
        }
        return this;
    }

}
