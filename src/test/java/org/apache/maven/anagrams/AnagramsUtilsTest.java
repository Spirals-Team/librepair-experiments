package org.apache.maven.anagrams;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import org.apache.maven.anagrams.AnagramsUtils;

/**
 * The test class for the AnagramsUtils Class.
 * It tests all AnagramsUtils methods in different scenarios.
 * 
 * @author valerio
 * @version 1.0
 */
public class AnagramsUtilsTest {
	
	//The instance to be testes
	private AnagramsUtils anagramUtils = null;
	
	/**
	 * Initializator of the instance of AnagramsUtils on whom we will run the tests
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		anagramUtils = new AnagramsUtils();
	}
	
	/**
	 * We test that the word value is calculated properly for the empty word
	 */
	@Test
	public void getWordValueTestEmptyWord() {
		assertEquals(new Long(1).longValue(), anagramUtils.getValue("").longValue());
	}
	
	/**
	 * We test that the word value is calculated properly for the null word
	 */
	@Test
	public void getWordValueTestNullWord() {
		assertEquals(new Long(1).longValue(), anagramUtils.getValue(null).longValue());
	}
	
	/**
	 * We test that the word value is calculated properly for an arbitrary word
	 */
	@Test
	public void getWordValueTestArbitraryWord() {
		assertEquals(new Long(30).longValue(), anagramUtils.getValue("abc").longValue());
	}

	/**
	 * We test that the anagrams groups are build properly providing well known sets of anagrams.
	 * This test verify the number of anagrams groups that the program build and compare it to the expected value.
	 * Because we are using well known sets of anagrams we can predict for each step how many anagrams groups should be returned from the program
	 * and what each group must contain
	 */
	@Test
	public void anagramsGroupShouldBeCreated() {
		List<String> words = new LinkedList<String>();
		
		//Empty set - 0 groups
		assertEquals(0, anagramUtils.findAnagrams(words).size());
		//Only one word - 1 group
		words.add("word");
		assertEquals(1, anagramUtils.findAnagrams(words).size());
		//Anagram of "word" - Still 1 group
		words.add("drow");
		assertEquals(1, anagramUtils.findAnagrams(words).size());
		//New word, 2 groups
		words.add("sword");
		assertEquals(2, anagramUtils.findAnagrams(words).size());
		//New word, 3 groups
		words.add("dawn");
		assertEquals(3, anagramUtils.findAnagrams(words).size());
		//Anagram of "dawn" - Still 3 groups
		words.add("wand");
		assertEquals(3, anagramUtils.findAnagrams(words).size());
		//We remove the only word with no anagrams - 2 groups
		words.remove("sword");
		assertEquals(2, anagramUtils.findAnagrams(words).size());
		//We remove 2 words which are mutual anagrams - 1 group
		words.remove("drow");
		words.remove("word");
		assertEquals(1, anagramUtils.findAnagrams(words).size());
	}
		
	/**
	 * We test if all anagrams in a group are concatenated together properly in only one string
	 */
	@Test
	public void anagramSholdBeConcatenated(){
		List<String> words = new ArrayList<String>();
		words.add("race");
		words.add("care");
		words.add("acre");
		assertEquals("race care acre", anagramUtils.toString(words));
	}
	
}
