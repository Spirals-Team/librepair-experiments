package tests;

import org.junit.Test;
import org.openqa.selenium.By;

import common.TestBase;
import common.UserInterface;

public class Dropdown extends TestBase
{
    private final static String DROPDOWN_URL = "https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_select";
    private final static String RESULT_IFRAME = "iframeResult";
    
    private final static By DROPDOWN_MENU = By.xpath("/html/body/select");
    
    @Test
    public void chooseDropDownItem()
    {
        UserInterface.Action.visitUrl(DROPDOWN_URL);
        UserInterface.Action.selectFrame(RESULT_IFRAME);
        UserInterface.Action.chooseDropDownItem(DROPDOWN_MENU, "Audi");
        UserInterface.Verify.selected(DROPDOWN_MENU, "Audi");
    }
}
