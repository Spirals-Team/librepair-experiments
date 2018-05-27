package tests;

import org.junit.Test;
import org.openqa.selenium.By;

import common.TestBase;
import common.UserInterface;

public class Checkbox extends TestBase
{
    private final static String CHECKBOXBUTTON_URL = "https://www.w3schools.com/html/tryit.asp?filename=tryhtml_Checkbox";
    private final static String RESULT_IFRAME = "iframeResult";
    
    private final static By CHECKBOX = By.xpath("/html/body/form/input[2]");
    
    @Test
    public void checkCheckbox()
    {
        UserInterface.Action.visitUrl(CHECKBOXBUTTON_URL);
        UserInterface.Action.selectFrame(RESULT_IFRAME);
        
        UserInterface.Action.checkCheckbox(CHECKBOX);
        UserInterface.Verify.checked(CHECKBOX);
        
        UserInterface.Action.uncheckCheckbox(CHECKBOX);
        UserInterface.Verify.unchecked(CHECKBOX);
    }
}
