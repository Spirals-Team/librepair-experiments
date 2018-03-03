package sci.modules;

import java.util.ArrayList;

/**
 * Default module which only exists for debugging purposes.
 * It responds with "pong" when the command "ping" is invoked.
 * @author Auxiliatrix
 *
 */
public class PingModule implements Module {
	
	/**
	 * Creates a PingModule.
	 */
	public PingModule() {};
	
	@Override
	public String[] getInvokers() {return new String[]{"ping"};}

	@Override
	public String getName() {return "Ping Module";}

	@Override
	public String getHelpDoc() {
		String response = "";
		response += "[Ping] Usage:";
		response += "\nThe 'ping' command returns \"pong\" when executed.";
		response += "\n";
		return response;
	}
	
	@Override
	public String process(String line, ArrayList<String> tokens) {
		return "pong";
	}

}
