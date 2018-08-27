package PageObjects;




import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import uy.com.cam.pagesObjects.POPageObject;

/**
 *
 * @author w8a
 */
public class POUniversity extends POPageObject{
	
	protected WebDriver seleniumDriver;

    public WebDriver getWebDriver(){
        return seleniumDriver;
    }
    
    public void forceWait(long i){
        try {
            Thread.sleep(i);
        } catch (InterruptedException ex) {
           // Logger.getLogger(POCertificateManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void refreshPage(){
        seleniumDriver.navigate().refresh();
    }
}
