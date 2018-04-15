package ru.aserdyuchenko;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class FiveLettersTest {
/**
 * Test for check, this word is palindrome?
 * Anton is palindrome.
 * @throws Exception - Exception.
 */
	@Test
	public void whenBobIsPalindrome() throws Exception {

		String wordForCheck = "Bob";
		InputStream input = new ByteArrayInputStream(wordForCheck.getBytes());
		FiveLetters word = new FiveLetters();
		boolean result = word.checkWord(input);
		assertThat(result, is(true));
	}
/**
 * Test for check, this word is palindrome?
 * Hello is not palindrome.
 * @throws Exception - Exception.
 */
	@Test
	public void whenHelloIsNotPalindrome() throws Exception {
		String wordForCheck = "Hello";
		InputStream input = new ByteArrayInputStream(wordForCheck.getBytes());
		FiveLetters word = new FiveLetters();
		boolean result = word.checkWord(input);
		assertThat(result, is(false));
	}
/**
 * Test for check, this word is palindrome?
 * bob is palidrom.
 */
	@Test
	public void whenBobPalidromForIsPalidromMethod() {
		FiveLetters word = new FiveLetters();
		boolean result = word.isPalidrom("Bob");
		assertThat(result, is(true));
	}
/**
 * Test for check, this word is palindrome?
 * Hello is palidrom.
 */
	@Test
	public void whenHelloNotPalidromForIsPalidromMethod() {
		FiveLetters word = new FiveLetters();
		boolean result = word.isPalidrom("Hello");
		assertThat(result, is(false));
	}
}
