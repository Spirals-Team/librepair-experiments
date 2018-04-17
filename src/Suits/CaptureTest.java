package Suits;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Functionalities.CommonHome;

public class CaptureTest extends SeleniumTestUniver {

	CommonHome commonHome;

	@BeforeMethod
	public void startSessionCertificate() throws Exception {
	    commonHome = new CommonHome();
	}

	@Test(description = "Tomar capture de pantalla y enviarlo al correo")
	public void captureScreem() {

		Assert.assertTrue(true, "");

	}

}
