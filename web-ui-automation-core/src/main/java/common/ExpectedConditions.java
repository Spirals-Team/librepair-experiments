package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import common.Table.Row;

final class ExpectedConditions
{
    public static ExpectedCondition<Boolean> selectionToBe(final By actualDropdownLocation, final String expectedText)
    {
        return new ExpectedCondition<Boolean>()
        {
            @Override
            public Boolean apply(WebDriver driver)
            {
                return isEquals(actualDropdownLocation, expectedText);
            }
            
            @Override
            public String toString()
            {
                return System.lineSeparator() + StringCollection.Identifier.ACTUAL_VALUE + System.lineSeparator() + Common.DataReceive.getSelection(actualDropdownLocation) +
                       System.lineSeparator() + StringCollection.Identifier.EXPECTED_VALUE + System.lineSeparator() + expectedText;
            }
        };
    }
    
    public static ExpectedCondition<Boolean> tableToBe(final By actualTableLocation, final Table expectedTable)
    {
        return new ExpectedCondition<Boolean>()
        {
            @Override
            public Boolean apply(WebDriver driver)
            {
                return isEquals(actualTableLocation, expectedTable);
            }
            
            @Override
            public String toString()
            {
                Table actualTable = Common.DataReceive.getTable(actualTableLocation);
                
                return System.lineSeparator() + StringCollection.Identifier.ACTUAL_TABLE_HEADER + System.lineSeparator() + actualTable.toString() +
                       System.lineSeparator() + StringCollection.Identifier.EXPECTED_TABLE_HEADER + System.lineSeparator() + expectedTable.toString();
            }
        };
    }
    
    public static ExpectedCondition<Boolean> tableRowToBe(final By actualTableLocation, final Row expectedRow)
    {
        return new ExpectedCondition<Boolean>()
        {
            @Override
            public Boolean apply(WebDriver driver)
            {
                return isEquals(actualTableLocation, expectedRow);
            }
            
            @Override
            public String toString()
            {
                Table actualTable = Common.DataReceive.getTable(actualTableLocation);
                
                return System.lineSeparator() + StringCollection.Identifier.ACTUAL_TABLE_HEADER + System.lineSeparator() + actualTable.toString() +
                       System.lineSeparator() + StringCollection.Identifier.EXPECTED_ROW_HEADER + System.lineSeparator() + expectedRow.toString();
            }
        };
    }
    
    private static boolean isEquals(By actualTableLocation, Table expectedTable)
    {
        return Common.DataReceive.getTable(actualTableLocation).equals(expectedTable);
    }
    
    private static boolean isEquals(By actualTableLocation, Row expectedRow)
    {
        Table actualTable = Common.DataReceive. getTable(actualTableLocation);
        
        for(Row row : actualTable)
        {
            if(row.equals(expectedRow))
            {
                return true;
            }
        }
        
        return false;
    }
    
    private static boolean isEquals(By location, String text)
    {
        return Common.DataReceive.getSelection(location).equals(text);
    }
}
