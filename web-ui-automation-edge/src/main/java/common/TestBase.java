package common;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import common.Driver;

public abstract class TestBase
{
    @Before
    public void setup()
    {
        WebDriver webdriver = EdgeDriver.getDriver();
        
        if(webdriver == null)
        {
            throw new UnsupportedOperationException("Your operating system is not supporting Edge.");
        }
        
        Driver.setup(webdriver);
    }
    
    @After
    public void quit()
    {
        Driver.quit();
    }
}
