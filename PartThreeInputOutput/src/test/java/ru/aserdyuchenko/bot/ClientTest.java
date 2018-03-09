package ru.aserdyuchenko.bot;

import static org.junit.Assert.assertThat;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import static org.hamcrest.core.Is.is;
import org.junit.Test;
import java.net.Socket;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.google.common.base.Joiner;

/**
 * @author Anton Serdyuchenko (anton415@gmail.com)
 */
public class ClientTest {
/**
 * @param LINESEPARATOR			LINESEPARATOR.
 */
	private static final String LINESEPARATOR = System.getProperty("line.separator");
/**
 * Test.
 * @throws IOException	 		IOException.
 */
	@Test
	public void whenSayHelloAndExit() throws IOException {
		this.testClient(
			Joiner.on(LINESEPARATOR).join(
				"Hello oracle",
				"exit"
			),
			Joiner.on(LINESEPARATOR).join(
				"Hello oracle",
				"exit",
				""
			)
		);
	}
/**
 * Test.
 * @param input					input.
 * @param excepted				excepted.
 * @throws IOException	 		IOException.
 */
	private void testClient(String input, String excepted) throws IOException {
		Socket socket = mock(Socket.class);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(
			input.getBytes()
		);
		when(socket.getInputStream()).thenReturn(in);
		when(socket.getOutputStream()).thenReturn(out);
		Client client = new Client(socket);
		client.startClient();
		assertThat(out.toString(), is(excepted));
	}
}
