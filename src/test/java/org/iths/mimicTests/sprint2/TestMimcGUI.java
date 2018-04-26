package org.iths.mimicTests.sprint2;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class TestMimcGUI {

	private String host = "https://proud-quasar-201909.appspot.com/";
	private MimicJarHelper helper = new MimicJarHelper();
	private MimicGuiSelenium driver;

	/**
	 * Makes a request for a not stored request, uses the
	 * response form to set a response and requests again
	 * to check that the response has been stored.
	 */
	//@Test
	public void TestMimicGUI111learnReqResp() {
		if(helper.jarIsRunning()) {

			driver = new MimicGuiSelenium();
			driver.openURL(host+"2+2");
			driver.sendText("4");
			driver.clickLearn();
			driver.openURL(host+"2+2");
			Assert.assertEquals("4", driver.getValue());
			driver.openURL(host+"unlearn");

		}else {
			fail(helper.errorString());
		}
	}	
	
	/**
	 * Stores a request with wrong response using the 
	 * response form, relearns that same request and checks
	 * that the new response is stored for the request. 
	 */
	//@Test
	public void TestMimicGUI112changeResp() {
		if(helper.jarIsRunning()) {
			driver = new MimicGuiSelenium();
			driver.openURL(host+"1+1"); 
			driver.sendText("3");
			driver.clickLearn();
			driver.openURL(host+"1+1");
			Assert.assertEquals("3", driver.getValue());
			driver.openURL(host+"LearnNextResponse?text=2");
			driver.openURL(host+"1+1");
			Assert.assertEquals("2", driver.getValue());
			driver.openURL(host+"unlearn");

		}else {
			fail(helper.errorString());
		}
	}	

	/**
	 * Stores a request using the response form and
	 * immediately tries to unlearn it. 
	 */
	//@Test
	public void TestMimicGUI113unlearnWithoutRequest() {
		if(helper.jarIsRunning()) {

			driver = new MimicGuiSelenium();
			driver.openURL(host+"5+5"); 
			driver.sendText("10");
			driver.clickLearn();
			driver.openURL(host+"unlearn");
			Assert.assertEquals("OK", driver.getValue());
			driver.openURL(host+"5+5"); 
			driver.openURL(host+"unlearn");

		}else {
			fail(helper.errorString());
		}
	}
	
	/**
	 * Stores a request using the response form, makes
	 * that sam request again and then unlearns it.
	 */
	//@Test
	public void TestMimicGUI114unlearnWithRequest() {
		if(helper.jarIsRunning()) {

			driver = new MimicGuiSelenium();
			driver.openURL(host+"5+5"); 
			driver.sendText("10");
			driver.clickLearn();
			driver.openURL(host+"5+5"); 
			driver.openURL(host+"unlearn");
			Assert.assertEquals("OK", driver.getValue());

		}else {
			fail(helper.errorString());
		}
	}
	
	/**
	 * Stores a request with xml format using the response
	 * form and checks that the request has been identified 
	 * as xml.
	 */
	//@Test
	public void TestMimicGUI115storeXmlRequest() {
		if(helper.jarIsRunning()) {

			driver = new MimicGuiSelenium();
			driver.openURL(host+"xml"); 
			driver.sendText("<value>1</value>");
			driver.clickLearn();
			driver.openURL(host+"xml"); 
			Assert.assertTrue(driver.getValue().contains("This XML file"));

		}else {
			fail(helper.errorString());
		}
	}
	
	//@After
	public void teardown() {
		driver.quitSelenium();
	}

}
