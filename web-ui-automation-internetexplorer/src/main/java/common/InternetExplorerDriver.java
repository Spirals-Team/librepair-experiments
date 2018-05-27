package common;

import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.WebDriver;

final class InternetExplorerDriver extends Driver
{
    private InternetExplorerDriver()
    {
        
    }
    
    public static WebDriver getDriver()
    {
        WebDriver webDriver =  null;
        
        String property = "webdriver.ie.driver";
        
        if(SystemUtils.IS_OS_WINDOWS)
        {
            webDriver = getWindowsDriver("IEDriverServer.exe", property);
        }
        
        return webDriver;
    }
}
