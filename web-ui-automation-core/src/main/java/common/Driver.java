package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public abstract class Driver
{
    private static WebDriver instance;
    
    public Driver()
    {
        
    }
    
    public static void setup(WebDriver webdriver)
    {
        Driver.instance = webdriver;
        
        Common.Setting.setup(Driver.instance);
    }
    
    public static void quit()
    {
        Driver.instance.quit();
    }
    
    protected static WebDriver getLinuxDriver(String driverName, String property)
    {
        return getDriver("linux", driverName, property);
    }
    
    protected static WebDriver getWindowsDriver(String driverName, String property)
    {
        return getDriver("windows", driverName, property);
    }
    
    protected static WebDriver getMacDriver(String driverName, String property)
    {
        return getDriver("mac", driverName, property);
    }
    
    private static WebDriver getDriver(String driverPath, String driverName, String property)
    {
        String driverLocation = driverPath + "/" + driverName;
        
        if(!isResourceInChildProject(driverLocation))
        {
            copyResourceFileToChildProject(driverLocation);
            
            if(SystemUtils.IS_OS_LINUX)
            {
                setExecutePermission(driverLocation);
            }
        }
        
        System.setProperty(property, driverLocation);
        
        return getDriver(property);
    }
    
    private static boolean isResourceInChildProject(String pathname)
    {
        return new File(pathname).isFile();
    }
    
    private static WebDriver getDriver(String property)
    {
        WebDriver webDriver = null;
        
        if(property.contains("chrome"))
        {
            webDriver = new ChromeDriver(getChromeOptions());
        }
        else if(property.contains("gecko"))
        {
            webDriver = new FirefoxDriver();
        }
        else if(property.contains("ie"))
        {
            webDriver = new InternetExplorerDriver();
        }
        else if(property.contains("edge"))
        {
            webDriver = new EdgeDriver();
        }
        
        return webDriver;
    }
    
    private static ChromeOptions getChromeOptions()
    {
        ChromeOptions options = new ChromeOptions();
        
        String property = System.getProperty("headlessChrome");
        
        if((property != null) && (property.equals("true")))
        {
            options.addArguments("headless");
        }
        
        return options;
    }

    private static void copyResourceFileToChildProject(String filename)
    {
        try
        {
            Path temp = Files.createTempFile("temp", ".tmp");
            Files.copy(Driver.class.getClassLoader().getResourceAsStream(filename), temp, StandardCopyOption.REPLACE_EXISTING);
            FileInputStream inputStream = new FileInputStream(temp.toFile());
            
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            
            File file = new File(filename);
            file.getParentFile().mkdirs();
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private static void setExecutePermission(String driver)
    {
        setExecutePermission(new File(driver));
    }
    
    private static void setExecutePermission(File file)
    {
        Set<PosixFilePermission> perms = new HashSet<>();
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        
        try
        {
            Files.setPosixFilePermissions(file.toPath(), perms);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
