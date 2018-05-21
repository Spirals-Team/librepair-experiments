package org.apache.maven.anagrams;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import org.apache.maven.anagrams.FileUtils;

/**
 * The test class for the FileUtils Class.
 * It tests all FileUtils methods in different scenarios.
 * 
 * @author valerio
 * @version 1.0
 */
public class FileUtilsTest {
	
	//The instance to be testes
	private FileUtils fileUtils = null;
	
	/**
	 * Initializator of the instance of FileUtils on whom we will run the tests
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		fileUtils = new FileUtils();
	}
	
	/**
	 * We verify if the filename is not null
	 */
	@Test
	public void wordsFileNameShouldNotBeNull() {
		try { 
			assertNotNull(fileUtils.getFileName()); 
		} 
		catch (IOException ex) { 
			fail("Exception while reading the file name in path " + fileUtils.getPropertiesPath() + ": " + ex.getStackTrace().toString()); 
		}
	}
	
	/**
	 * We verify if the filename is correctly read from the configuration file
	 */
	@Test
	public void wordsFileNameShouldBeRead() {
		try { 
			assertNotNull(fileUtils.getWordFilePath()); 
		} 
		catch (IOException | URISyntaxException ex) { 
			fail("Exception while reading the file name in path " + fileUtils.getPropertiesPath() + ": " + ex.getStackTrace().toString()); 
		}
	}
	
	/**
	 * We verify that the fileUtils instance throws an exception in case we try to read an unexisting file
	 * 
	 * @throws FileNotFoundException This exception is expected to be thrown becase we are passing a wrong file name to test the method
	 */
	@Test (expected = FileNotFoundException.class)
	public void wordsFileShouldNotBeFound() throws FileNotFoundException  {
		assertNull(fileUtils.readFile(fileUtils.getPropertiesPath() + "\\fakeFile.txt"));
	}
	
	/**
	 * We verify that the fileUtils instance throws an exception in case we try to read a null file
	 */
	@Test (expected = NullPointerException.class)
	public void nullWordsFilenameShouldRaiseException() {
		try { 
			assertNull(fileUtils.readFile(null)); 
		} 
		catch (IOException ex) { 
			ex.printStackTrace();
			fail("Exception while reading the null file"); 
		}
	}
	
	/**
	 * We test that the words were read properly from the file
	 */
	@Test
	public void wordsFileShouldBeRead1()  {
		try {
			List<String> readWords = fileUtils.readFile(fileUtils.getWordFilePath());
			assertNotNull(readWords);
		}
		catch (NullPointerException | IOException | URISyntaxException ex) {
			ex.printStackTrace();
			fail("Exception while reading the file name in path"); 
		}
	}
	
	/**
	 * We test that the words were read properly from the file
	 */
	@Test
	public void wordsFileShouldBeRead2()  {
		try {
			List<String> readWords = fileUtils.readFile(fileUtils.getWordFilePath());
			assertTrue(readWords.size() > 0);
		}
		catch (NullPointerException | IOException | URISyntaxException ex) {
			ex.printStackTrace();
			fail("Exception while reading the file name"); 
		}
	}
	
}
