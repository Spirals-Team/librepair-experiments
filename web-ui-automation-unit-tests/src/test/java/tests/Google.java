package tests;

import org.junit.Test;
import org.openqa.selenium.By;

import common.TestBase;
import common.UserInterface;

public class Google extends TestBase
{
    private final static String GOOGLE_URL = "https://www.google.com/";
    
    private final static By SEARCH_FIELD = By.xpath("//input[@id='lst-ib']");
    private final static By AUTO_COMPLETION_LIST_BOX = By.xpath("//*[@id='sbtc']/div[2][not(contains(@style,'none'))]");
    private final static By SEARCH_BUTTON = By.xpath("//input[@name='btnK']");
    private final static By TITLE_PIC = By.xpath("//*[@id='hplogo']");
    
    @Test
    public void weatherSearch()
    {
        UserInterface.Action.visitUrl(GOOGLE_URL);
        UserInterface.Action.fillField(SEARCH_FIELD, "weather");
        UserInterface.Verify.appeared(AUTO_COMPLETION_LIST_BOX);
        UserInterface.Action.pressEscape();
        UserInterface.Action.clickElement(SEARCH_BUTTON);
        UserInterface.Verify.disappeared(TITLE_PIC);
        
        String searchValue = UserInterface.DataReceive.getText(SEARCH_FIELD);
        
        UserInterface.Compare.equals("weather", searchValue);
    }
}
