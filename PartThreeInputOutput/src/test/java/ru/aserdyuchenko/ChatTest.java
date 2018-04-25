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
 * @version 2
 * @since 06.01.2017
 */

public class ChatTest {
/**
 * @throws Exception - Exception.
 */
	@Test
	public void whenYouTalkWithBot() throws Exception {
		Chat chat = new Chat();
		String myPhrase = "a b stop c continue d end";
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		InputStream input = new ByteArrayInputStream(myPhrase.getBytes());
		chat.start(input);
		assertThat(out.toString(), is("end"));
	}
}
