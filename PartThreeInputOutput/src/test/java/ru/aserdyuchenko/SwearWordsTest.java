package ru.aserdyuchenko;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.apache.commons.io.output.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class SwearWordsTest {
/**
 * Test for remove swear words.
 * 12 is even.
 * @throws Exception - Exception.
 */
	@Test
	public void whenRemoveSwearWords() throws Exception {
		String dirtyStream = "a b c d";
		String[] abuse = {"a", "d", "c"};
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		InputStream input = new ByteArrayInputStream(dirtyStream.getBytes());
		SwearWords words = new SwearWords();
		words.dropAbuses(input, out, abuse);
		assertThat(out.toString(), is("b"));
	}
}
