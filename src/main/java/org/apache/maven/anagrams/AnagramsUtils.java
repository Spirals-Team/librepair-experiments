package org.apache.maven.anagrams;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This class is a toolset to manage single words and groups of anagrams. 
 * It provides some functions to process a single word or a group of anagrams
 *
 * @author Valerio De Dominicis
 * @version 1.0
 */
public class AnagramsUtils {
	
	private Logger logger = null;
	//This map is initialized only once when an instance of AnagramUtils is created. It provides
	//the prime number associated to each letter in the alphabet
	private Map<Character, Long> lettersValues;
	
	/**
	 * Default constructor. It initialize the state of the instance
	 */
	public AnagramsUtils() {
		
		this.logger = LogManager.getLogger("debugLogger");
		
		//We associate to each letter a different prime number. 
		//value 1, even if it is not a prime number, 
		//is used to represent the empty or non-esisting word
		lettersValues = new HashMap<Character, Long>();
		lettersValues.put('a', new Long(2));
		lettersValues.put('b', new Long(3));
		lettersValues.put('c', new Long(5));
		lettersValues.put('d', new Long(7));
		lettersValues.put('e', new Long(11));
		lettersValues.put('f', new Long(13));
		lettersValues.put('g', new Long(17));
		lettersValues.put('h', new Long(19));
		lettersValues.put('i', new Long(23));
		lettersValues.put('j', new Long(29));
		lettersValues.put('k', new Long(31));
		lettersValues.put('l', new Long(37));
		lettersValues.put('m', new Long(41));
		lettersValues.put('n', new Long(43));
		lettersValues.put('o', new Long(47));
		lettersValues.put('p', new Long(53));
		lettersValues.put('q', new Long(59));
		lettersValues.put('r', new Long(61));
		lettersValues.put('s', new Long(67));
		lettersValues.put('t', new Long(71));
		lettersValues.put('u', new Long(73));
		lettersValues.put('v', new Long(79));
		lettersValues.put('x', new Long(83));
		lettersValues.put('y', new Long(89));
		lettersValues.put('w', new Long(97));
		lettersValues.put('z', new Long(101));
	}
	
	/**
	 * Return the numeric value associated to a word.
	 * The value associated to a word is obtained multiplying the prime numbers associated to the word's letters. 
	 * For instance, if we have the word "ab" and the associations
	 * a -> 2 and b->3, this method will return 6 
	 *  
	 * @param word The word to process
	 * @return the numeric value associated to the word
	 */
	public Long getValue(String word) {

		Long wordValue = new Long(1);
		
		//1 represent the empty (word = "") or non-existing (word == null) words. They are both invalid
		if(word == null)
			return wordValue;
		
		logger.debug("AnagramUtils - getValue - Calculating the value for the word : " + word);
		
		//We multiply the values associated to the word's letters to determine the word value
		for(char singleChar : word.toLowerCase().trim().toCharArray()) {
			wordValue = wordValue * lettersValues.get(singleChar);
		}
		
		return wordValue;
	}
	
	/**
	 * This method process a list of words and split it into anagrams sublists.
	 * 
	 * @param words The words we want to process
	 * @return A collection containing lists of anagrams
	 */
	public Collection<List<String>> findAnagrams(List<String> words) {
		
		/*
		 * To determine if two words are mutual anagrams we apply a mathematical method based on prime numbers.
		 * We assign to each letter in the alphabet a different prime number. This allow us to associate a 
		 * "word value" to each word, which is calculated multiplying the numbers associated to its letters. 
		 * If two words are mutual anagrams they will have the same word value. 
		 *
		 * The unique factorization theorem guarantee that two non-anagrams words can't have the same word value.
		 */
		
		Map<Long, List<String>> valueToAnagrams = new HashMap<Long, List<String>>();
		
		if(words == null) {
			return valueToAnagrams.values();
		}
		
		this.logger.debug("AnagramUtils - findAnagrams - Processing the words");
		
		Long currentWordValue = null;
		for(String currentWord : words) {
			//For each word we determine its value
			currentWordValue = this.getValue(currentWord);
			
			//The value 1 is used to represent the empty (word = "") or the unexisting (word = null) words, which are both invalid
			if(currentWordValue == 1)
				continue;
			
			//We group the words with same word value. If a new value is found we create a new group
			if(!valueToAnagrams.containsKey(currentWordValue)) {
				List<String> anagrams = new LinkedList<String>();
				anagrams.add(currentWord);
				valueToAnagrams.put(currentWordValue, anagrams);
			}
			else {
				valueToAnagrams.get(currentWordValue).add(currentWord);
			}
			
		}
		
		return valueToAnagrams.values();
		
	}
	
	/**
	 * Return the concatenation of a list of strings
	 * This method merge together all the strings contained in a list. The single strings are separated by spaces
	 * 
	 * @param anagrams The list of strings we want to merge
	 * @return A string which is the concatenation of all the strings into the list
	 */
	public String toString(List<String> anagrams) {
		String[] mergedAnagrams = {""};
		
		//We iterate on the anagrams merging them all together in a single strings
		anagrams.stream().forEach(anagram -> mergedAnagrams[0] += anagram + " ");		
		
		return mergedAnagrams[0].trim();
	}
	
}
