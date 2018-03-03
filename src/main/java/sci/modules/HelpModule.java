package sci.modules;

import java.util.ArrayList;

import sci.SCI;

/**
 * <p>Default module which allows the user to see a list of possible commands.</p>
 * 
 * Also provides in-depth explanations of a module if prompted.
 * This module is dynamic, and will display all possible commands
 * for modules within the <code>sci.modules</code> package.
 * @author Auxiliatrix
 *
 */
public class HelpModule implements Module {
	
	/**
	 * Creates a HelpModule.
	 */
	public HelpModule() {}

	@Override
	public String[] getInvokers() {return new String[]{"help"};}
	
	@Override
	public String getName() {return "Help Module";}

	@Override
	public String getHelpDoc() {
		String response = "";
		response += "[Help] Usage:";
		response += "\nThe 'help' command gives details on how to use the ScoutingComputerInterface.";
		response += "\n\thelp\t\tReturns a list of valid commands.";
		response += "\n\thelp [command]\tGives detailed information about a command.";
		response += "\n";
		return response;
	}
	
	@Override
	public String process(String line, ArrayList<String> tokens) {
		String response = "";
		if( tokens.size() == 0 ) {
			response += "Here is a list of commands.";
			for( String s : SCI.modules.keySet() ) {
				response += "\n\t" + s;
			}
			response += "\nFor more information on a command, type \"help <command>\".";
		} else {
			String command = tokens.get(0);
			for( String s : SCI.modules.keySet() ) {
				if( command.equalsIgnoreCase(s) ) {
					response = SCI.modules.get(s).getHelpDoc();
				}
			}
			if( response.equals("") ) {
				response += "Command \"" + command + "\" does not exist.";
				response += "\nType \"help\" to see a list of commands.";
			}
		}
		return response;
	}
}
