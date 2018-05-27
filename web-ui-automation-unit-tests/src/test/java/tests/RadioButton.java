package tests;

import org.junit.Test;
import org.openqa.selenium.By;

import common.TestBase;
import common.UserInterface;

public class RadioButton extends TestBase
{
    private final static String RADIOBUTTON_URL = "https://www.w3schools.com/html/tryit.asp?filename=tryhtml_radio";
    private final static String RESULT_IFRAME = "iframeResult";
    
    private final static By RADIOBUTTON = By.xpath("/html/body/form/input[2]");
    
    @Test
    public void clickRadioButton()
    {
        UserInterface.Action.visitUrl(RADIOBUTTON_URL);
        UserInterface.Action.selectFrame(RESULT_IFRAME);
        UserInterface.Action.clickRadioButton(RADIOBUTTON);
    }
}
