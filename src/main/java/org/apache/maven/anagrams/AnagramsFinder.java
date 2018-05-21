package org.apache.maven.anagrams;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * The AnagramsFinder class is the core of the program. It reads and processes the words to find the anagrams.
 * 
 * @author Valerio De Dominicis
 * @version 1.0
 */
public class AnagramsFinder {

	private Logger logger = null;
	private AnagramsUtils anagramsUtils = null;
	private FileUtils fileUtils = null;
	
	/**
	 * The launcher of the program.
	 * 
	 * @param args no arguments are required
	 */
	public static void main(String args[]) {
		new AnagramsFinder().start();
	}
	
	/**
	 * Default constructor. It initialize the state of the instance
	 */
	public AnagramsFinder() {
		this.anagramsUtils = new AnagramsUtils();
		this.fileUtils = new FileUtils();
		this.logger = LogManager.getLogger("debugLogger");
	}
	
	/**
	 * The starter methods. It calls all the other methods to find the anagrams groups
	 */
	protected void start() {
		
		try {
			//We try to read the words from the file and, if no exception occurs, we detect the groups of anagrams
			Collection<List<String>> anagramGroups = this.anagramsUtils.findAnagrams(this.fileUtils.readFile(this.fileUtils.getWordFilePath()));
			//When all anagrams are grouped we print them
			this.printAnagrams(anagramGroups);
		}
		catch(NullPointerException | IOException | URISyntaxException ex) {
			//If the words or the configuration files were not found we log the error and the program ends
			this.logger.error("AnagramsFinder - start - File not found, execution will be stopped", ex);
		}
		
	}
	
	/**
	 * Print the groups of anagrams
	 * 
	 * @param anagramsGroups A collection containing anagrams groups
	 */
	public void printAnagrams(Collection<List<String>> anagramsGroups) {
		this.logger.debug("AnagramsFinder - printAnagrams - Found anagrams: ");
		
		//If there are no anagrams groups we avoid the print phase
		if(anagramsGroups == null)
			return;

		anagramsGroups	.stream()
						//We filter the groups with only 1 element (for whom no anagrams were found)
						.filter((List<String> currentAnagrams) -> currentAnagrams.size() > 1)
						//We iterate on the remaining groups to print the found anagrams
						.forEach(currentAnagrams -> logger.debug(this.anagramsUtils.toString(currentAnagrams)));
		
	}
	
}
