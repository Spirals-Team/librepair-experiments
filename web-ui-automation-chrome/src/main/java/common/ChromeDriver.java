package common;

import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.WebDriver;

import common.Driver;

final class ChromeDriver extends Driver
{
    private ChromeDriver()
    {
        
    }
    
    public static WebDriver getDriver()
    {
        WebDriver webDriver =  null;
        
        String property = "webdriver.chrome.driver";
        
        if(SystemUtils.IS_OS_LINUX)
        {
            webDriver = getLinuxDriver("chromedriver", property);
        }
        else if(SystemUtils.IS_OS_WINDOWS)
        {
            webDriver = getWindowsDriver("chromedriver.exe", property);
        }
        else if(SystemUtils.IS_OS_MAC)
        {
            webDriver = getMacDriver("chromedriver", property);
        }
        
        return webDriver;
    }
}
