package common;

import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.WebDriver;

final class FirefoxDriver extends Driver
{
    private FirefoxDriver()
    {
        
    }
    
    public static WebDriver getDriver()
    {
        WebDriver webDriver =  null;
        
        String property = "webdriver.gecko.driver";
        
        if(SystemUtils.IS_OS_LINUX)
        {
            webDriver = getLinuxDriver("geckodriver", property);
        }
        else if(SystemUtils.IS_OS_WINDOWS)
        {
            webDriver = getWindowsDriver("geckodriver.exe", property);
        }
        else if(SystemUtils.IS_OS_MAC)
        {
            webDriver = getMacDriver("geckodriver", property);
        }
        
        return webDriver;
    }
}
