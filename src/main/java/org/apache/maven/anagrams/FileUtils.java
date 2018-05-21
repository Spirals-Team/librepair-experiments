package org.apache.maven.anagrams;
//te
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This class is a toolset to interact with the configuration and the words files.
 * It contains the methods to retrieve the file name and to read its content
 * 
 * @author Valerio De Dominicis
 * @version 1.0
 */
public class FileUtils {
	
	private static final String PROPERTIES_FILE_NAME = "anagram.properties";
	private static final String PROPERTIES_PATH = System.getProperty("user.dir");
	private Logger logger = null;
	
	/**
	 * Default constructor. It initialize the state of the instance
	 */
	public FileUtils() {
		this.logger = LogManager.getLogger("debugLogger");
	}
	
	/**
	 * Returns the name of the file containing the words we want to process.
	 * This name may be configured in the anagram.properties file
	 * 
	 * @return The name of the file with the words we want to process
	 * @throws IOException If anagram.properties file is not found
	 */
	public String getFileName() throws IOException {
		logger.debug("FileUtils - getFileName - Looking for the configuration file: " + PROPERTIES_PATH + "\\" + PROPERTIES_FILE_NAME);
		//We try to read the name of the words file from anagram.properties
		Properties configFile = new Properties();
		configFile.load(new FileInputStream(PROPERTIES_PATH + "\\" + PROPERTIES_FILE_NAME));
		logger.debug("FileUtils - getFileName - Getting the file name from the configuration file");
		return configFile.getProperty("WORDS_FILE_NAME");
	}
	
	/**
	 * Returns the path of the file containing the words we want to process.
	 * 
	 * @return The path of the file with the words we want to process
	 * @throws IOException If .properties file is not found
	 */
	public String getWordFilePath() throws IOException, URISyntaxException {
		String fileName = this.getFileName();
		logger.debug("FileUtils - getWordFilePath - Getting the word file path " + PROPERTIES_PATH + "\\" + fileName);
		return PROPERTIES_PATH + "\\" + fileName;
	}
	
	/**
	 * Returns the words read from the file.
	 * 
	 * @param fileName The name of the file containing the words
	 * @return A list of string representing the words read from the file
	 * @throws FileNotFoundException If the words file is not found
	 * @throws NullPointerException If the filename is null
	 */
	public List<String> readFile(String filePath) throws FileNotFoundException, NullPointerException {
        List<String> words = new LinkedList<String>();
        
        logger.debug("FileUtils - readFile - Reading the words in the file " + filePath);
        //We read the file row-by-row to get the words we have to process
        Scanner s = new Scanner(new File(filePath));
        while (s.hasNext()) {
            words.add(s.next());
        }
        s.close();
        
		return words;
	}

	//------------------- GETTERS AND SETTERS
	
	public String getPropertiesPath() {
		return PROPERTIES_PATH;
	}
	
}
