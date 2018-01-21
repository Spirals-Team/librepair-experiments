package com.orasi.web.webelements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.orasi.DriverManager;
import com.orasi.DriverType;
import com.orasi.web.OrasiDriver;
import com.orasi.web.WebBaseTest;
import com.orasi.web.exceptions.ElementAttributeValueNotMatchingException;
import com.orasi.web.exceptions.ElementCssValueNotMatchingException;
import com.orasi.web.exceptions.ElementNotDisabledException;
import com.orasi.web.exceptions.ElementNotEnabledException;
import com.orasi.web.exceptions.ElementNotHiddenException;
import com.orasi.web.exceptions.ElementNotVisibleException;
import com.orasi.web.exceptions.TextInElementNotPresentException;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestElement extends WebBaseTest {
    private OrasiDriver driver;

    @BeforeClass(groups = { "regression", "interfaces", "element", "dev" })
    public void setup() {
        setApplicationUnderTest("Test Site");
        setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/element.html");
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult testResults) {
    }

    @Override
    @AfterClass(alwaysRun = true)
    public void afterClass(ITestContext testResults) {
        DriverManager.setDriver(driver);
        endTest(getTestName(), testResults);
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("reload")
    @Test(groups = { "regression", "interfaces", "element" })
    public void reload() {
        driver = testStart("TestElement");
        driver.findWebElement(By.id("text1")).sendKeys("blah");
        Element element = driver.findElement(By.id("text1"));
        driver.get(getPageURL());
        driver.debug().setHighlightOnSync(true);
        Assert.assertTrue(element.isEnabled());
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("clear")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void clear() {
        Element element = driver.findElement(By.id("text1"));
        element.clear();
        Assert.assertTrue(element.getAttribute("value").equals(""));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("findElement")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void findElement() {
        Element element = driver.findElement(By.id("radiogroup")).findElement(By.name("sex"));
        element.highlight();
        Assert.assertTrue(element instanceof Element);
    }
    
    @Features("Element Interfaces")
    @Stories("Element")
    @Title("findWebElement")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void findWebElement() {
        WebElement webElement = getDriver().findWebElement(By.id("radiogroup"));
        Assert.assertTrue(webElement instanceof WebElement);
    }
    
    @Features("Element Interfaces")
    @Stories("Element")
    @Title("findElements")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void findElements() {
        List<Element> elements = getDriver().findElement(By.id("radiogroup")).findElements(By.name("sex"));
        Assert.assertTrue(elements.get(0) instanceof Element);
        Assert.assertTrue(elements.size() == 2);
    }
    
    @Features("Element Interfaces")
    @Stories("Element")
    @Title("findWebElements")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void findWebElements() {
        List<WebElement> webElements = getDriver().findWebElements(By.name("sex"));
        Assert.assertTrue(webElements.get(0) instanceof WebElement);
        Assert.assertTrue(webElements.size() == 2);
    }
    
    @Features("Element Interfaces")
    @Stories("Element")
    @Title("findElement Negative")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true, expectedExceptions = NoSuchElementException.class)
    public void negativeFindElement() {
        Element element = getDriver().findElement(By.id("radiogroup")).findElement(By.name("foolocator123"));
    }
    
    @Features("Element Interfaces")
    @Stories("Element")
    @Title("findWebElement Negative")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true, expectedExceptions = NoSuchElementException.class)
    public void negativeFindWebElement() {
        WebElement webElement = getDriver().findWebElement(By.id("foolocator123"));
    }
    
    @Features("Element Interfaces")
    @Stories("Element")
    @Title("findElements Negative")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void negativeFindElements() {
        List<Element> elements = getDriver().findElement(By.id("radiogroup")).findElements(By.name("foolocator123"));
        Assert.assertTrue(elements.isEmpty());
    }
    
    @Features("Element Interfaces")
    @Stories("Element")
    @Title("findWebElements Negative")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void negativeFindWebElements() {
        List<WebElement> webElements = getDriver().findWebElements(By.name("foolocator123"));
        Assert.assertTrue(webElements.isEmpty());
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("click")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "clear", alwaysRun = true)
    public void click() {
        Element element = driver.findElement(By.id("buttonForText1"));
        if (DriverType.EDGE.equals(driver.getDriverType())) {
            element.jsClick();
        } else {
            element.click();
        }
        // temporary fix for edge click
        Assert.assertTrue(driver.findElement(By.id("text1")).getAttribute("value").equals("Clicked button"));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("elementWired")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "click", alwaysRun = true)
    public void elementWired() {
        Element element = driver.findElement(By.id("text1"));
        Assert.assertTrue(element.elementWired());
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("getAttribute")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void getAttribute() {
        Element element = driver.findElement(By.xpath("//input[@value='female']"));
        Assert.assertTrue(element.getAttribute("type").equals("radio"));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("getCssValue")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void getCssValue() {
        Element element = driver.findElement(By.id("buttonForText1"));
        if (DriverType.HTML.equals(driver.getDriverType())) {
            Assert.assertTrue(element.getCssValue("font-family").equals("verdana"));
        } else {
            Assert.assertTrue(!element.getCssValue("font-family").isEmpty());
        }
    }

    /*
     * @Features("Element Interfaces")
     *
     * @Stories("Element")
     *
     * @Title("getElementLocator")
     *
     * @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
     * public void getElementLocator() {
     * Element element = driver.findElement(By.id("text1"));
     * Assert.assertNotNull(element.getElementLocator());
     * }
     */
    @Features("Element Interfaces")
    @Stories("Element")
    @Title("getElementLocatorInfo")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void getElementLocatorInfo() {
        Element element = driver.findElement(By.id("text1"));
        Assert.assertNotNull(element.getElementLocatorInfo());
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("getLocation")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void getLocation() {
        Element element = driver.findElement(By.id("text1"));
        try {
            Assert.assertTrue(element.getLocation().getX() > 0);
            Assert.assertTrue(element.getLocation().getY() > 0);
        } catch (WebDriverException wde) {
            if (DriverType.EDGE.equals(driver.getDriverType())) {
                throw new AssertionError("getLocation not supported by EdgeDriver");
            }
        }
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("getSize")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void getSize() {
        Element element = driver.findElement(By.id("text1"));
        Assert.assertTrue(element.getSize().getHeight() > 0);
        Assert.assertTrue(element.getSize().getWidth() > 0);
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("getTagName")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void getTagName() {
        Element element = driver.findElement(By.id("text1"));
        Assert.assertTrue(element.getTagName().equals("input"));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("getText")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void getText() {
        Element element = driver.findElement(By.id("pageheader"));
        Assert.assertTrue(element.getText().equals("Element test page"));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("highlight")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "getAttribute", alwaysRun = true)
    public void highlight() {
        Element element = driver.findElement(By.id("buttonForText1"));
        element.highlight();
        Assert.assertTrue(element.getAttribute("style").toLowerCase().contains("red"));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("isDisplayed")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void isDisplayed() {
        Element element = driver.findElement(By.id("text1"));
        Assert.assertTrue(element.isDisplayed());
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("isEnabled")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void isEnabled() {
        Element element = driver.findElement(By.id("text1"));
        Assert.assertTrue(element.isEnabled());
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("isSelected")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void isSelected() {
        Element element = driver.findElement(By.xpath("//*[@id='radiogroup']/input[1]"));
        Assert.assertTrue(element.isSelected());
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("isSelectedNegative")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void isSelectedNegative() {
        Element element = driver.findElement(By.xpath("//*[@id='radiogroup']/input[2]"));
        Assert.assertFalse(element.isSelected());
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("jsClick")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "click", alwaysRun = true)
    public void jsClick() {
        driver.findElement(By.id("text1")).sendKeys("blah");
        Element element = driver.findElement(By.id("buttonForText1"));
        element.jsClick();
        Assert.assertTrue(driver.findElement(By.id("text1")).getAttribute("value").equals("Clicked button"));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("sendKeys")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "jsClick", alwaysRun = true)
    public void sendKeys() {
        Element element = driver.findElement(By.id("text1"));
        element.sendKeys("testing");
        Assert.assertTrue(element.getAttribute("value").equals("Clicked buttontesting"));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncDisabledBasic")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void syncDisabledBasic() {
        Element element = driver.findElement(By.id("disable"));
        Assert.assertTrue(element.syncDisabled());
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncDisabledBasicNegative")
    @Test(groups = { "regression", "element" }, dependsOnMethods = { "syncDisabledBasic" }, alwaysRun = true, expectedExceptions = ElementNotDisabledException.class)
    public void syncDisabledBasicNegative() {
        Element element = driver.findElement(By.id("text1"));
        element.syncDisabled();
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncDisabledAdvanced")
    @Test(groups = { "regression", "element" }, dependsOnMethods = { "syncDisabledBasic" }, alwaysRun = true)
    public void syncDisabledAdvanced() {
        Element element = driver.findElement(By.id("disable"));
        Assert.assertTrue(element.syncDisabled(5, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncDisabledAdvancedNegative")
    @Test(groups = { "regression", "element" }, dependsOnMethods = { "syncDisabledBasic" }, alwaysRun = true)
    public void syncDisabledAdvancedNegative() {
        Element element = driver.findElement(By.id("text1"));
        Assert.assertFalse(element.syncDisabled(1, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncHiddenBasic")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void syncHiddenBasic() {
        Element element = driver.findElement(By.id("hidden"));
        Assert.assertTrue(element.syncHidden());
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncHiddenBasicNegative")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "elementWired", alwaysRun = true, expectedExceptions = ElementNotHiddenException.class)
    public void syncHiddenBasicNegative() {
        Element element = driver.findElement(By.id("text1"));
        element.syncHidden();
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncHiddenAdvanced")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void syncHiddenAdvanced() {
        Element element = driver.findElement(By.id("hidden"));
        Assert.assertTrue(element.syncHidden(5, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncHiddenAdvancedNegative")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void syncHiddenAdvancedNegative() {
        Element element = driver.findElement(By.id("text1"));
        Assert.assertFalse(element.syncHidden(1, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncVisibleBasic")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void syncVisibleBasic() {
        Textbox element = driver.findTextbox(By.id("text1"));
        Assert.assertTrue(element.syncVisible());
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncVisibleBasicNegative")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "elementWired", alwaysRun = true, expectedExceptions = ElementNotVisibleException.class)
    public void syncVisibleBasicNegative() {
        Element element = driver.findElement(By.id("hidden"));
        element.syncVisible();
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncVisibleAdvanced")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void syncVisibleAdvanced() {
        Element element = driver.findElement(By.id("text1"));
        Assert.assertTrue(element.syncVisible(5, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncVisibleAdvancedNegative")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void syncVisibleAdvancedNegative() {
        Element element = driver.findElement(By.id("hidden"));
        Assert.assertFalse(element.syncVisible(1, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncEnabledBasic")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void syncEnabledBasic() {
        Element element = driver.findElement(By.id("text1"));
        Assert.assertTrue(element.syncEnabled());
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncEnabledBasicNegative")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun = true, expectedExceptions = ElementNotEnabledException.class)
    public void syncEnabledBasicNegative() {
        Element element = driver.findElement(By.id("disable"));
        element.syncEnabled();
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncEnabledAdvanced")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "elementWired", alwaysRun = true)
    public void syncEnabledAdvanced() {
        Element element = driver.findElement(By.id("text1"));
        Assert.assertTrue(element.syncEnabled(5, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncEnabledAdvancedNegative")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void syncEnabledAdvancedNegative() {
        Element element = driver.findElement(By.id("disable"));
        Assert.assertFalse(element.syncEnabled(1, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncTextInElementBasic")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void syncTextInElementBasic() {
        Element element = driver.findElement(By.id("pageheader"));
        Assert.assertTrue(element.syncTextInElement("Element test page"));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncTextInElementBasicNegative")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true, expectedExceptions = TextInElementNotPresentException.class)
    public void syncTextInElementBasicNegative() {
        Element element = driver.findElement(By.id("pageheader"));
        element.syncTextInElement("Loading");
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncTextInElementAdvanced")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void syncTextInElementAdvanced() {
        Element element = driver.findElement(By.id("pageheader"));
        Assert.assertTrue(element.syncTextInElement("Element test page", 5, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncTextInElementAdvancedNegative")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void syncTextInElementAdvancedNegative() {
        Element element = driver.findElement(By.id("pageheader"));
        Assert.assertFalse(element.syncTextInElement("negative", 2, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncTextMatchesInElementBasic")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void syncTextMatchesInElementBasic() {
        Element element = driver.findElement(By.id("pageheader"));
        Assert.assertTrue(element.syncTextMatchesInElement("(.*test page)"));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncTextMatchesInElementBasicNegative")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true, expectedExceptions = TextInElementNotPresentException.class)
    public void syncTextMatchesInElementBasicNegative() {
        Element element = driver.findElement(By.id("pageheader"));
        element.syncTextMatchesInElement("(.*tst pge)");
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncTextMatchesInElementAdvanced")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void syncTextMatchesInElementAdvanced() {
        Element element = driver.findElement(By.id("pageheader"));
        Assert.assertTrue(element.syncTextMatchesInElement("(.*test page)", 5, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncTextMatchesInElementAdvancedNegative")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void syncTextMatchesInElementAdvancedNegative() {
        Element element = driver.findElement(By.id("pageheader"));
        Assert.assertFalse(element.syncTextMatchesInElement("(.*tst pge)", 2, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncAttributeContainsValueBasic")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void syncAttributeContainsValueBasic() {
        Element element = driver.findElement(By.xpath("//input[@value='female']"));
        Assert.assertTrue(element.syncAttributeContainsValue("type", "radio"));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncAttributeContainsValueBasicNegative")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true, expectedExceptions = ElementAttributeValueNotMatchingException.class)
    public void syncAttributeContainsValueBasicNegative() {
        Element element = driver.findElement(By.xpath("//input[@value='female']"));
        element.syncAttributeContainsValue("type", "Radio");
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncAttributeContainsValueAdvanced")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void syncAttributeContainsValueAdvanced() {
        Element element = driver.findElement(By.xpath("//input[@value='female']"));
        Assert.assertTrue(element.syncAttributeContainsValue("type", "radio", 5, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncAttributeContainsValueAdvancedNegative")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void syncAttributeContainsValueAdvancedNegative() {
        Element element = driver.findElement(By.xpath("//input[@value='female']"));
        Assert.assertFalse(element.syncAttributeContainsValue("type", "Rao", 2, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncAttributeMatchesValueBasic")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void syncAttributeMatchesValueBasic() {
        Element element = driver.findElement(By.xpath("//input[@value='female']"));
        Assert.assertTrue(element.syncAttributeMatchesValue("type", "(.*adi.*)"));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncAttributeMatchesValueBasicNegative")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun = true, expectedExceptions = ElementAttributeValueNotMatchingException.class)
    public void syncAttributeMatchesValueBasicNegative() {
        Element element = driver.findElement(By.xpath("//input[@value='female']"));
        element.syncAttributeMatchesValue("type", "(.*adi)");
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncAttributeMatchesValueAdvanced")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void syncAttributeMatchesValueAdvanced() {
        Element element = driver.findElement(By.xpath("//input[@value='female']"));
        Assert.assertTrue(element.syncAttributeMatchesValue("type", "(.*adi.*)"));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncAttributeMatchesValueAdvancedNegative")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void syncAttributeMatchesValueAdvancedNegative() {
        Element element = driver.findElement(By.xpath("//input[@value='female']"));
        Assert.assertFalse(element.syncAttributeMatchesValue("type", "(.*adi)", 2, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncCssPropertyContainsValueBasic")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = { "reload", "highlight" }, alwaysRun = true)
    public void syncCssPropertyContainsValueBasic() {
        Element element = driver.findElement(By.id("buttonForText1"));
        Assert.assertTrue(element.syncCssPropertyContainsValue("display", "inline"));

    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncCssPropertyContainsValueBasicNegative")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods ={ "reload", "highlight" }, alwaysRun = true, expectedExceptions = ElementCssValueNotMatchingException.class)
    public void syncCssPropertyContainsValueBasicNegative() {
        Element element = driver.findElement(By.id("buttonForText1"));
        element.syncCssPropertyContainsValue("display", "Inline");
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncCssPropertyContainsValueAdvanced")
    @Test(groups = { "regression", "element" }, dependsOnMethods = { "reload", "highlight" }, alwaysRun = true)
    public void syncCssPropertyContainsValueAdvanced() {
        Element element = driver.findElement(By.id("buttonForText1"));
        Assert.assertTrue(element.syncCssPropertyContainsValue("display", "inline", 5, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncCssPropertyContainsValueAdvancedNegative")
    @Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun = true)
    public void syncCssPropertyContainsValueAdvancedNegative() {
        Element element = driver.findElement(By.id("buttonForText1"));
        Assert.assertFalse(element.syncCssPropertyContainsValue("display", "Inline", 2, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncCssPropertyMatchesValueBasic")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods ={ "reload", "highlight" }, alwaysRun = true)
    public void syncCssPropertyMatchesValueBasic() {
        Element element = driver.findElement(By.id("buttonForText1"));
        Assert.assertTrue(element.syncCssPropertyMatchesValue("display", "(.*inline.*)"));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncCssPropertyMatchesValueBasicNegative")
    @Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods ={ "reload", "highlight" }, alwaysRun = true, expectedExceptions = ElementCssValueNotMatchingException.class)
    public void syncCssPropertyMatchesValueBasicNegative() {
        Element element = driver.findElement(By.id("buttonForText1"));
        element.syncCssPropertyMatchesValue("display", "(.*Inline.*)");
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncCssPropertyMatchesValueAdvanced")
    @Test(groups = { "regression", "element" }, dependsOnMethods = { "reload", "highlight" }, alwaysRun = true)
    public void syncCssPropertyMatchesValueAdvanced() {
        Element element = driver.findElement(By.id("buttonForText1"));
        Assert.assertTrue(element.syncCssPropertyMatchesValue("display", "(.*inline.*)", 2, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncCssPropertyMatchesValueAdvancedNegative")
    @Test(groups = { "regression", "element" }, dependsOnMethods = { "reload", "highlight" }, alwaysRun = true)
    public void syncCssPropertyMatchesValueAdvancedNegative() {
        Element element = driver.findElement(By.id("buttonForText1"));
        Assert.assertFalse(element.syncCssPropertyMatchesValue("display", "(.*Inline-Block.*)", 2, false));
    }

    @Features("Element Interfaces")
    @Stories("Element")
    @Title("syncInFrame")
    @Test(groups = { "regression" }, dependsOnGroups = "element", alwaysRun = true)
    public void syncInFrame() {
        driver.get("http://orasi.github.io/Chameleon/sites/unitTests/orasi/utils/frameHandler.html");
        driver.findElement(By.id("button_frame1")).syncInFrame();
    }

}
