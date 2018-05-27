package tests;

import org.junit.Test;
import org.openqa.selenium.By;

import common.TestBase;
import common.UserInterface;

public class Enabled extends TestBase
{
    private final static String ENABLED_URL = "https://www.w3schools.com/cssref/tryit.asp?filename=trycss3_enabled_disabled";
    private final static String RESULT_IFRAME = "iframeResult";
    
    private final static By ENABLED = By.xpath("/html/body/form/input[2]");
    private final static By DISABLED = By.xpath("/html/body/form/input[3]");
    
    @Test
    public void enabled()
    {
        UserInterface.Action.visitUrl(ENABLED_URL);
        UserInterface.Action.selectFrame(RESULT_IFRAME);
        
        UserInterface.Verify.enabled(ENABLED);
        UserInterface.Verify.disabled(DISABLED);
    }
}
