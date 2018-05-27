package common;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.unitils.reflectionassert.ReflectionAssert;

import common.Table.Row;

final class Common
{
    private static WebDriver driver;
    private static FluentWait<WebDriver> wait;
    
    private final static int STANDARD_TIMEOUT_IN_SECONDS = 10;
    private final static int INTERVALL_IN_MILLISECONDS = 200;
    
    private Common()
    {
        
    }
    
    public static class Action
    {
        private Action()
        {
            
        }
        
        public static void visitUrl(String url)
        {
            ExceptionHandler.apply((Consumer<String>)driver::get, ExceptionHandler::getTimeoutMessage, url);
        }
        
        public static <T> void fillField(By location, T value)
        {
            Common.getVisibleWebElement(location).sendKeys(String.valueOf(value));
        }
        
        public static void clickElement(By location)
        {
            Common.getClickableWebElement(location).click();
        }
        
        public static void doubleClickElement(By location)
        {
            new Actions(driver).doubleClick(Common.getClickableWebElement(location)).perform();
        }
        
        public static void chooseDropDownItem(By location, String item)
        {
            Select select =  ExceptionHandler.apply((WebElement webElement) -> new Select(webElement), ExceptionHandler::getUnexpectedTagNameMessage, Common.getClickableWebElement(location));
            ExceptionHandler.apply(select::selectByVisibleText, ExceptionHandler::getNoSuchElementMessage, item);
        }
        
        public static void selectDefaultFrame()
        {
            driver.switchTo().defaultContent();
        }
        
        public static void selectFrame(String iFrameNameOrId)
        {
            ExceptionHandler.apply((Function<ExpectedCondition<WebDriver>, WebDriver>)wait::until, ExceptionHandler::getTimeoutMessage, ExpectedConditions.frameToBeAvailableAndSwitchToIt(iFrameNameOrId));
        }
        
        public static void clickRadioButton(By location)
        {
            Common.getClickableWebElement(location).click();
        }
        
        public static void pressEnter()
        {
            Common.pressKey(Keys.ENTER);
        }
        
        public static void pressEscape()
        {
            Common.pressKey(Keys.ESCAPE);
        }
        
        public static void checkCheckbox(By location)
        {
            if(!DataReceive.isChecked(location))
            {
                getClickableWebElement(location).click();
            }
        }
        
        public static void uncheckCheckbox(By location)
        {
            if(DataReceive.isChecked(location))
            {
                getClickableWebElement(location).click();
            }
        }
        
        public static void moveSlider(By location, int pixel)
        {
            new Actions(driver).clickAndHold(getVisibleWebElement(location)).moveByOffset(pixel, 0).release().perform();
        }
        
        public static void mouseOver(By location)
        {
            new Actions(driver).moveToElement(driver.findElement(location)).build().perform();
        }
        
        public static void moveSlider(By location, float percent)
        {
            int pixel = Math.round((((float)getVisibleWebElement(location).getSize().width / 100) * percent));
            
            moveSlider(location, pixel);
        }
    }
    
    public static class Verify
    {
        private Verify()
        {
            
        }
        
        public static void appeared(By location)
        {
            Common.getVisibleWebElement(location);
        }
        
        public static void disappeared(By location)
        {
            ExceptionHandler.apply((Function<ExpectedCondition<Boolean>, Boolean>)wait::until, ExceptionHandler::getTimeoutMessage, ExpectedConditions.invisibilityOfElementLocated(location));
        }
        
        public static void available(By location)
        {
            Common.getPresentWebElement(location);
        }
        
        public static void unavailable(By location)
        {
            ExceptionHandler.apply((Function<ExpectedCondition<Boolean>, Boolean>)wait::until, ExceptionHandler::getTimeoutMessage, ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(location)));
        }
        
        public static void url(String url)
        {
            Common.equals(url, driver.getCurrentUrl());
        }
        
        public static void selected(By location)
        {
            ExceptionHandler.apply((Function<ExpectedCondition<Boolean>, Boolean>)wait::until, ExceptionHandler::getTimeoutMessage, ExpectedConditions.elementToBeSelected(location));
        }
        
        public static void unselected(By location)
        {
            ExceptionHandler.apply((Function<ExpectedCondition<Boolean>, Boolean>)wait::until, ExceptionHandler::getTimeoutMessage, ExpectedConditions.not(ExpectedConditions.elementToBeSelected(location)));
        }
        
        public static void enabled(By location)
        {
            ExceptionHandler.apply((Function<ExpectedCondition<WebElement>, WebElement>)wait::until, ExceptionHandler::getTimeoutMessage, ExpectedConditions.elementToBeClickable(location));
        }
        
        public static void disabled(By location)
        {
            ExceptionHandler.apply((Function<ExpectedCondition<Boolean>, Boolean>)wait::until, ExceptionHandler::getTimeoutMessage, ExpectedConditions.not(ExpectedConditions.elementToBeClickable(location)));
        }
        
        public static void visibleCount(By location, int expected)
        {
            Common.equals(expected, DataReceive.getVisibleCount(location));
        }
        
        public static void presentCount(By location, int expected)
        {
            Common.equals(expected, DataReceive.getPresentCount(location));
        }
        
        public static void table(By actualTableLocation, Table expectedTable)
        {
            ExceptionHandler.apply((Function<ExpectedCondition<Boolean>, Boolean>)wait::until, ExceptionHandler::getTimeoutMessage, common.ExpectedConditions.tableToBe(actualTableLocation, expectedTable));
        }
        
        public static void tableRow(By actualTableLocation, Row expectedRow)
        {
            ExceptionHandler.apply((Function<ExpectedCondition<Boolean>, Boolean>)wait::until, ExceptionHandler::getTimeoutMessage, common.ExpectedConditions.tableRowToBe(actualTableLocation, expectedRow));
        }
        
        public static void text(By location, String expectedText)
        {
            ExceptionHandler.apply((Function<ExpectedCondition<Boolean>, Boolean>)wait::until, ExceptionHandler::getTimeoutMessage, ExpectedConditions.textToBe(location, expectedText));
        }
        
        public static void texts(By location, List<String> expectedTexts)
        {
            for(String expectedText : expectedTexts)
            {
                text(location, expectedText);
            }
        }
        
        public static void attributeValue(By location, String attributeName, String expectedValue)
        {
            ExceptionHandler.apply((Function<ExpectedCondition<Boolean>, Boolean>)wait::until, ExceptionHandler::getTimeoutMessage, ExpectedConditions.attributeContains(location, attributeName, expectedValue));
        }
        
        public static void attributeValues(By location, String attributeName, List<String> expectedValues)
        {
            for(String expectedValue : expectedValues)
            {
                attributeValue(location, attributeName, expectedValue);
            }
        }
        
        public static void selected(By dropdownMenuLocation, String text)
        {
            ExceptionHandler.apply((Function<ExpectedCondition<Boolean>, Boolean>)wait::until, ExceptionHandler::getTimeoutMessage, common.ExpectedConditions.selectionToBe(dropdownMenuLocation, text));
        }
    }
    
    public static class DataReceive
    {
        private DataReceive()
        {
            
        }
        
        public static int getVisibleCount(By location)
        {
            return getVisibleWebElements(location).size();
        }
        
        public static int getPresentCount(By location)
        {
            return getPresentWebElements(location).size();
        }
        
        public static String getText(By location)
        {
            return Common.getText(getVisibleWebElement(location));
        }
        
        public static Table getTable(By tableLocation)
        {
            Table table = new Table();
            
            WebElement tableWebElement = getVisibleWebElement(tableLocation);
            List<WebElement> rowWebElements = tableWebElement.findElements(By.xpath(StringCollection.XPath.ROW_SELECTOR_INSIDE));
            
            for(WebElement rowWebElement : rowWebElements)
            {
                table.add(getRow(rowWebElement));
            }
            
            return table;
        }
        
        public static List<String> getTexts(By location)
        {
            List<String> results = new ArrayList<>();
            
            for(WebElement webElement : getVisibleWebElements(location))
            {
                results.add(Common.getText(webElement));
            }
            
            return results;
        }
        
        public static String getAttributeValue(By location, String attributeName)
        {
            return Common.getAttributeValue(getPresentWebElement(location), attributeName);
        }
        
        public static List<String> getAttributeValues(By location, String attributeName)
        {
            List<String> results = new ArrayList<>();
            
            for(WebElement webElement : getPresentWebElements(location))
            {
                results.add(Common.getAttributeValue(webElement, attributeName));
            }
            
            return results;
        }
        
        public static boolean isAppeared(By location)
        {
            return getVisibleWebElement(location).isDisplayed();
        }
        
        public static boolean isDisappeared(By location)
        {
            return !isAppeared(location);
        }
        
        public static Row getTableRow(By table, int rowIndex)
        {
            return getTable(table).get(rowIndex);
        }
        
        public static boolean isEnabled(By location)
        {
            return getVisibleWebElement(location).isEnabled();
        }
        
        public static boolean isDisabled(By location)
        {
            return !isEnabled(location);
        }
        
        public static boolean isAvailable(By location)
        {
            return getPresentCount(location) != 0;
        }
        
        public static boolean isUnavailable(By location)
        {
            return !isAvailable(location);
        }
        
        public static String getUrl()
        {
            return driver.getCurrentUrl();
        }
        
        public static boolean isSelected(By location)
        {
            return Common.getVisibleWebElement(location).isSelected();
        }
        
        public static boolean isUnselected(By location)
        {
            return !isSelected(location);
        }
        
        public static boolean isChecked(By location)
        {
            return isSelected(location);
        }
        
        public static boolean isUnchecked(By location)
        {
            return !isSelected(location);
        }
        
        public static String getSelection(By dropdownMenu)
        {
            return new Select(getVisibleWebElement(dropdownMenu)).getFirstSelectedOption().getText();
        }
    }
    
    public static class Compare
    {
        private Compare()
        {
            
        }
        
        public static void _true(boolean condition)
        {
            Common.equals(true, condition);
        }
        
        public static void _false(boolean condition)
        {
            Common.equals(false, condition);
        }
        
        public static <T> void empty(List<T> values)
        {
            equals(0, values.size());
        }
        
        public static <T> void notEmpty(List<T> values)
        {
            notEquals(0, values.size());
        }
        
        public static void contains(String expectedValue, List<String> actualValues)
        {
            if(!actualValues.contains(expectedValue))
            {
                ExceptionHandler.errorMessage(ExceptionHandler.getNotContainsMessage(expectedValue, actualValues));
            }
        }
        
        public static void contains(List<String> expectedValues, List<String> actualValues)
        {
            for(String expectedValue : actualValues)
            {
                if(!actualValues.contains(expectedValue))
                {
                    ExceptionHandler.errorMessage(ExceptionHandler.getNotContainsMessage(expectedValue, actualValues));
                }
            }
        }
        
        public static void contains(String containmentText, String actualText)
        {
            if(!actualText.contains(containmentText))
            {
                ExceptionHandler.errorMessage(ExceptionHandler.getNotContainsMessage(containmentText, actualText));
            }
        }
        
        public static <T> void equals(T expected, T actual)
        {
            Common.equals(expected, actual);
        }
        
        public static <T> void notEquals(T unexpected, T actual)
        {
            Assert.assertNotEquals(unexpected, actual);
        }
    }
    
    public static class Setting
    {
        private Setting()
        {
            
        }
        
        public static void setup(WebDriver driver)
        {
            Common.driver = driver;
            Common.driver.manage().deleteAllCookies();
            
            Common.wait = new WebDriverWait(driver, 0);
            
            setTimeout(Common.STANDARD_TIMEOUT_IN_SECONDS);
        }
        
        public static void setTimeout(int seconds)
        {
            Common.driver.manage().timeouts().pageLoadTimeout(seconds, TimeUnit.SECONDS);
            Common.wait.withTimeout(Duration.ofSeconds(seconds)).pollingEvery(Duration.ofMillis(Common.INTERVALL_IN_MILLISECONDS));
        }
        
        public static void resetTimeout()
        {
            setTimeout(Common.STANDARD_TIMEOUT_IN_SECONDS);
        }
    }
    
    private static void pressKey(Keys key)
    {
        new Actions(driver).sendKeys(key).perform();
    }
    
    private static <T> void equals(T expectedValue, T actualValue)
    {
        ReflectionAssert.assertReflectionEquals(expectedValue, actualValue);
    }
    
    private static String getText(WebElement webElement)
    {
        String result = null;
        
        String inputType = webElement.getTagName();
        
        switch(inputType)
        {
            case StringCollection.ControlType.INPUT:
            case StringCollection.ControlType.TEXTAREA:
                result = webElement.getAttribute(StringCollection.AttributeType.VALUE);
            break;
            
            case StringCollection.ControlType.SELECT:
                result = new Select(webElement).getFirstSelectedOption().getText();
            break;
            
            default:
                result = webElement.getText();
            break;
        }
        
        return result;
    }
    
    private static String getAttributeValue(WebElement webElement, String attributeName)
    {
        return webElement.getAttribute(attributeName);
    }
    
    private static Row getRow(WebElement rowWebElement)
    {
        Row row = new Row();
        
        List<WebElement> colWebElements = rowWebElement.findElements(By.xpath(StringCollection.XPath.CELL_SELECTOR));
        
        for(WebElement colWebElement : colWebElements)
        {
            row.add(getCol(colWebElement));
        }
        
        return row;
    }
    
    private static String getCol(WebElement colWebElement)
    {
        return colWebElement.getText();
    }
    
    private static WebElement getPresentWebElement(By location)
    {
        return ExceptionHandler.apply((Function<ExpectedCondition<WebElement>, WebElement>)wait::until, ExceptionHandler::getTimeoutMessage, ExpectedConditions.presenceOfElementLocated(location));
    }
    
    private static List<WebElement> getPresentWebElements(By location)
    {
        return ExceptionHandler.apply((Function<ExpectedCondition<List<WebElement>>, List<WebElement>>)wait::until, ExceptionHandler::getTimeoutMessage, ExpectedConditions.presenceOfAllElementsLocatedBy(location));
    }
    
    private static WebElement getVisibleWebElement(By location)
    {
        return ExceptionHandler.apply((Function<ExpectedCondition<WebElement>, WebElement>)wait::until, ExceptionHandler::getTimeoutMessage, ExpectedConditions.visibilityOfElementLocated(location));
    }
    
    private static List<WebElement> getVisibleWebElements(By location)
    {
        return ExceptionHandler.apply((Function<ExpectedCondition<List<WebElement>>, List<WebElement>>)wait::until, ExceptionHandler::getTimeoutMessage, ExpectedConditions.visibilityOfAllElementsLocatedBy(location));
    }
    
    private static WebElement getClickableWebElement(By location)
    {
        return ExceptionHandler.apply((Function<ExpectedCondition<WebElement>, WebElement>)wait::until, ExceptionHandler::getTimeoutMessage, ExpectedConditions.elementToBeClickable(location));
    }
}