package tests;

import org.junit.Test;
import org.openqa.selenium.By;

import common.TestBase;
import common.UserInterface;

public class Slider extends TestBase
{
    private final static String SLIDER_URL = "https://www.w3schools.com/howto/howto_js_rangeslider.asp";
    
    private final static By SLIDER = By.xpath("//*[@id='id2']");
    
    @Test
    public void moveSliderInPixel()
    {
        UserInterface.Action.visitUrl(SLIDER_URL);
        UserInterface.Action.moveSlider(SLIDER, 30);
    }
    
    @Test
    public void moveSliderInPercent()
    {
        UserInterface.Action.visitUrl(SLIDER_URL);
        UserInterface.Action.moveSlider(SLIDER, -30.0f);
    }
}
