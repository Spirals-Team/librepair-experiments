package Suits;

import uy.com.cam.util.SeleniumTests.SeleniumTest;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeMethod;

import Functionalities.CommonLogin;
import Functionalities.CommonSecurity;

public class SeleniumTestUniver extends SeleniumTest {
	
	CommonSecurity commonSecurity;
	CommonLogin commonLogin;

	@BeforeMethod
	public void login(CommonLogin commonLogin) {
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	    driver.manage().window().maximize();
	    commonSecurity = new CommonSecurity(driver);
//		commonLogin.login(pars.get("USUARIO"), pars.get("PASS"));
	    commonLogin.login("usuario", "PASS");
	}

}

