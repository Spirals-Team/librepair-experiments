package tests;

import org.junit.Test;
import org.openqa.selenium.By;

import common.Table;
import common.TestBase;
import common.UserInterface;
import common.Table.Row;

public class DataReceive extends TestBase
{
    private final static String TABLE_URL = "https://www.w3schools.com/html/html_tables.asp";
    private final static String INNER_TEXT_URL = "https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_node_innertext";
    
    private final static By TABLE = By.xpath("//*[@id='customers']");
    private final static By TRY_IT_BUTTON = By.xpath("//*[@id='myBtn']");
    
    @Test
    public void getTable1()
    {
        UserInterface.Action.visitUrl(TABLE_URL);
        
        Table expectedTable = Table.create(Row.create("Company", "Contact", "Country"),
                                           Row.create("Alfreds Futterkiste", "Maria Anders", "Germany"),
                                           Row.create("Centro comercial Moctezuma", "Francisco Chang", "Mexico"),
                                           Row.create("Ernst Handel", "Roland Mendel", "Austria"),
                                           Row.create("Island Trading", "Helen Bennett", "UK"),
                                           Row.create("Laughing Bacchus Winecellars", "Yoshi Tannamuri", "Canada"),
                                           Row.create("Magazzini Alimentari Riuniti", "Giovanni Rovelli", "Italy"));
        
        Table actualTable = UserInterface.DataReceive.getTable(TABLE);
        
        UserInterface.Compare.equals(expectedTable, actualTable);
    }
    
    @Test
    public void getTable2()
    {
        UserInterface.Action.visitUrl(TABLE_URL);
        
        Table expectedTable = Table.create(Row.create("Company", "Contact", "Country"),
                                           Row.create("Alfreds Futterkiste", "Maria Anders", "Germany"),
                                           Row.create("Centro comercial Moctezuma", "Francisco Chang", "Mexico"),
                                           Row.create("Ernst Handel", "Roland Mendel", "Austria"),
                                           Row.create("Island Trading", "Helen Bennett", "UK"),
                                           Row.create("Laughing Bacchus Winecellars", "Yoshi Tannamuri", "Canada"),
                                           Row.create("Magazzini Alimentari Riuniti", "Giovanni Rovelli", "Italy"));
        
        UserInterface.Verify.table(TABLE, expectedTable);
    }
    
    @Test
    public void getTable3()
    {
        UserInterface.Action.visitUrl(TABLE_URL);
        
        Row expectedRow = Row.create("Island Trading", "Helen Bennett", "UK");
        
        UserInterface.Verify.tableRow(TABLE, expectedRow);
    }
    
    @Test
    public void getTable4()
    {
        UserInterface.Action.visitUrl(TABLE_URL);
        
        Table table = UserInterface.DataReceive.getTable(TABLE);
        String actualValue = table.get(3).get(2);
        
        UserInterface.Compare.equals("Austria", actualValue);
    }
    
    @Test
    public void getCount()
    {
        UserInterface.Action.visitUrl(TABLE_URL);
        
        int count = UserInterface.DataReceive.getAppearedCount(TABLE);
        
        UserInterface.Compare.equals(1, count);
        
    }
    
    @Test
    public void getText()
    {
        UserInterface.Action.visitUrl(INNER_TEXT_URL);
        UserInterface.Action.selectFrame("iframeResult");
        
        String buttonText = UserInterface.DataReceive.getText(TRY_IT_BUTTON);
        
        UserInterface.Compare.equals("Try it", buttonText);
        
        UserInterface.Verify.text(TRY_IT_BUTTON, "Try it");
    }
    
    @Test
    public void getValue()
    {
        UserInterface.Action.visitUrl(INNER_TEXT_URL);
        UserInterface.Action.selectFrame("iframeResult");
        
        String onClickValue = UserInterface.DataReceive.getAttributeValue(TRY_IT_BUTTON, "onclick");
        
        UserInterface.Compare.equals("myFunction()", onClickValue);
    }
}
