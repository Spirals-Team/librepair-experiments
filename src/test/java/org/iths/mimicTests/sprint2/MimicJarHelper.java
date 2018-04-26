package org.iths.mimicTests.sprint2;

import org.iths.mimicTests.HttpServiceCaller;

public class MimicJarHelper {

	private HttpServiceCaller service;
	private final static String host="https://proud-quasar-201909.appspot.com/"; 
	
	public boolean jarIsRunning() {
		service = new HttpServiceCaller();
		if(!service.executeGetRequest(host).equals("Error")) {
			killMimic();
			return true;
		}else if(service.executeGetRequest(host).equals("Error")){
			wait(1000);
			if(!service.executeGetRequest(host).equals("Error")) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	public void killMimic() {
		service = new HttpServiceCaller();
		service.executeGetRequest(host+"KillMimic");
		wait(1000);
	}
	
	public String errorString() {
		return "Please check with admin for test server availability";
	}
	
	public void wait(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
