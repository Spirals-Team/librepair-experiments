package sci.modules;

import java.util.ArrayList;

/**
 * Interface of a Module object, which handles given user-inputted commands.
 * @author Auxiliatrix
 *
 */
public interface Module {
	
	/**
	 * Gets the command used to invoke this module.
	 * @return word used to invoke this module.
	 */
	public String[] getInvokers();
	
	/**
	 * Gets what this module will be referred to in the code.
	 * @return ID to use when referencing this Module in the code.
	 */
	public String getName();
	
	/**
	 * Gets the documentation for how this module should be used. Invoked by the default HelpModule.
	 * @return details on this module's usage.
	 */
	public String getHelpDoc();
	
	/**
	 * Processes the given user input to execute commands and produce a String output.
	 * @param line - String which represents the unaltered user input
	 * @param tokens - ArrayList of Strings for each 'word' in the user input, obtained by space-delimiting the input String
	 * @return response to output to the user.
	 */
	public String process(String line, ArrayList<String> tokens);
}
