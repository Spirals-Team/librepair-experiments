package mastermind;

import java.io.IOException;

public class MasterMindService {

	private HttpConnector httpConnector;

	public MasterMindService() {
		this.httpConnector = new HttpConnector();
	}

	public Integer start() throws IOException {
		httpConnector.start();
		return httpConnector.getSize();
	}

	public MasterMindReturn test(String testValue) throws IOException {
		int[] values = httpConnector.test(testValue);
		final MasterMindReturn masterMindReturn = new MasterMindReturn(values[1], values[0]);
		return masterMindReturn;
	}

}
