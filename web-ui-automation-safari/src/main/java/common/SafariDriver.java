package common;

import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.WebDriver;

final class SafariDriver extends Driver
{
    private SafariDriver()
    {
        
    }
    
    public static WebDriver getDriver()
    {
        WebDriver webDriver = null;
        
        if(SystemUtils.IS_OS_MAC)
        {
            webDriver = new org.openqa.selenium.safari.SafariDriver();
        }
        
        return webDriver;
    }
}
