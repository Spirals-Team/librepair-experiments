package common;

import java.util.List;

import org.openqa.selenium.By;

import common.Table.Row;

public final class UserInterface
{
    private UserInterface()
    {
        
    }
    
    public static class Action
    {
        private Action()
        {
            
        }
        
        public static <T> void fillField(By location, T value)
        {
            Common.Action.fillField(location, value);
        }
        
        public static void clickElement(By location)
        {
            Common.Action.clickElement(location);
        }
        
        public static void doubleClickElement(By location)
        {
            Common.Action.doubleClickElement(location);
        }
        
        public static void clickRadioButton(By location)
        {
            Common.Action.clickRadioButton(location);
        }
        
        public static void checkCheckbox(By location)
        {
            Common.Action.checkCheckbox(location);
        }
        
        public static void uncheckCheckbox(By location)
        {
            Common.Action.uncheckCheckbox(location);
        }
        
        public static void chooseDropDownItem(By location, String item)
        {
            Common.Action.chooseDropDownItem(location, item);
        }
        
        public static void moveSlider(By location, int pixel) 
        {
            Common.Action.moveSlider(location, pixel);
        }
        
        public static void moveSlider(By location, float percent) 
        {
            Common.Action.moveSlider(location, percent);
        }
        
        public static void mouseOver(By location)
        {
            Common.Action.mouseOver(location);
        }
        
        public static void pressEnter()
        {
            Common.Action.pressEnter();
        }
        
        public static void pressEscape()
        {
            Common.Action.pressEscape();
        }
        
        public static void selectDefaultFrame()
        {
            Common.Action.selectDefaultFrame();
        }
        
        public static void selectFrame(String iFrameNameOrId)
        {
            Common.Action.selectFrame(iFrameNameOrId);
        }
        
        public static void visitUrl(String url)
        {
            Common.Action.visitUrl(url);
        }
    }
    
    public static class Verify
    {
        private Verify()
        {
            
        }
        
        public static void appeared(By location)
        {
            Common.Verify.appeared(location);
        }
        
        public static void disappeared(By location)
        {
            Common.Verify.disappeared(location);
        }
        
        public static void available(By location)
        {
            Common.Verify.available(location);
        }
        
        public static void unavailable(By location)
        {
            Common.Verify.unavailable(location);
        }
        
        public static void enabled(By location)
        {
            Common.Verify.enabled(location);
        }
        
        public static void disabled(By location)
        {
            Common.Verify.disabled(location);
        }
        
        public static void checked(By checkbox)
        {
            Common.Verify.selected(checkbox);
        }
        
        public static void unchecked(By checkbox)
        {
            Common.Verify.unselected(checkbox);
        }
        
        public static void selected(By location)
        {
            Common.Verify.selected(location);
        }
        
        public static void unselected(By location)
        {
            Common.Verify.unselected(location);
        }
        
        public static void selected(By dropdownMenu, String text)
        {
            Common.Verify.selected(dropdownMenu, text);
        }
        
        public static void url(String url)
        {
            Common.Verify.url(url);
        }
        
        public static void appearedCount(By location, int expected)
        {
            Common.Verify.visibleCount(location, expected);
        }
        
        public static void availableCount(By location, int expected)
        {
            Common.Verify.presentCount(location, expected);
        }
        
        public static void table(By actualTableLocation, Table expectedTable)
        {
            Common.Verify.table(actualTableLocation, expectedTable);
        }
        
        public static void tableRow(By actualTable, Row expectedRow)
        {
            Common.Verify.tableRow(actualTable, expectedRow);
        }
        
        public static void text(By location, String expectedText)
        {
            Common.Verify.text(location, expectedText);
        }
        
        public static void texts(By location, List<String> expectedTexts)
        {
            Common.Verify.texts(location, expectedTexts);
        }
        
        public static void attributeValue(By location, String attributeName, String expectedValue)
        {
            Common.Verify.attributeValue(location, attributeName, expectedValue);
        }
        
        public static void attributeValues(By location, String attributeName, List<String> expectedValues)
        {
            Common.Verify.attributeValues(location, attributeName, expectedValues);
        }
    }
    
    public static class DataReceive
    {
        private DataReceive()
        {
            
        }
        
        public static boolean isAppeared(By location)
        {
            return Common.DataReceive.isAppeared(location);
        }
        
        public static boolean isDisappeared(By location)
        {
            return Common.DataReceive.isDisappeared(location);
        }
        
        public static boolean isAvailable(By location)
        {
            return Common.DataReceive.isAvailable(location);
        }
        
        public static boolean isUnavailable(By location)
        {
            return Common.DataReceive.isUnavailable(location);
        }
        
        public static boolean isEnabled(By location)
        {
            return Common.DataReceive.isEnabled(location);
        }
        
        public static boolean isDisabled(By location)
        {
            return Common.DataReceive.isDisabled(location);
        }
        
        public static boolean isChecked(By location)
        {
            return Common.DataReceive.isChecked(location);
        }
        
        public static boolean isUnchecked(By location)
        {
            return Common.DataReceive.isUnchecked(location);
        }
        
        public static boolean isSelected(By location)
        {
            return Common.DataReceive.isSelected(location);
        }
        
        public static boolean isUnselected(By location)
        {
            return Common.DataReceive.isUnselected(location);
        }
        
        public static String getSelection(By dropdownMenu)
        {
            return Common.DataReceive.getSelection(dropdownMenu);
        }
        
        public static String getUrl()
        {
            return Common.DataReceive.getUrl();
        }
        
        public static int getAppearedCount(By location)
        {
            return Common.DataReceive.getVisibleCount(location);
        }
        
        public static int getAvailableCount(By location)
        {
            return Common.DataReceive.getPresentCount(location);
        }
        
        public static Table getTable(By tableLocation)
        {
            return Common.DataReceive.getTable(tableLocation);
        }
        
        public static Row getTableRow(By tableLocation, int rowIndex)
        {
            return Common.DataReceive.getTableRow(tableLocation, rowIndex);
        }
        
        public static String getText(By location)
        {
            return Common.DataReceive.getText(location);
        }
        
        public static List<String> getTexts(By location)
        {
            return Common.DataReceive.getTexts(location);
        }
        
        public static String getAttributeValue(By location, String attributeName)
        {
            return Common.DataReceive.getAttributeValue(location, attributeName);
        }
        
        public static List<String> getAttributeValues(By location, String attributeName)
        {
            return Common.DataReceive.getAttributeValues(location, attributeName);
        }
    }
    
    public static class Compare
    {
        private Compare()
        {
            
        }
        
        public static void _true(boolean condition)
        {
            Common.Compare._true(condition);
        }
        
        public static void _false(boolean condition)
        {
            Common.Compare._false(condition);
        }
        
        public static <T> void equals(T expected, T actual)
        {
            Common.Compare.equals(expected, actual);
        }
        
        public static <T> void notEquals(T unexpected, T actual)
        {
            Common.Compare.notEquals(unexpected, actual);
        }
        
        public static <T> void empty(List<T> values)
        {
            Common.Compare.empty(values);
        }
        
        public static <T> void notEmpty(List<T> values)
        {
            Common.Compare.notEmpty(values);
        }
        
        public static void contains(String containment, String actualValue)
        {
            Common.Compare.contains(containment, actualValue);
        }
        
        public static void contains(String expectedValue, List<String> actualValues)
        {
            Common.Compare.contains(expectedValue, actualValues);
        }
        
        public static void contains(List<String> expectedValues, List<String> actualValues)
        {
            Common.Compare.contains(expectedValues, actualValues);
        }
    }
    
    public static class Setting
    {
        private Setting()
        {
            
        }
        
        public static void setTimeout(int seconds)
        {
            Common.Setting.setTimeout(seconds);
        }
        
        public static void resetTimeout()
        {
            Common.Setting.resetTimeout();
        }
    }
}
