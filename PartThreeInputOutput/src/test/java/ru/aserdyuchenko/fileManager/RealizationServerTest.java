package ru.aserdyuchenko.fileManager;

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
public class RealizationServerTest {
/**
 * @param LINESEPARATOR			LINESEPARATOR.
 */
	private static final String LINESEPARATOR = System.getProperty("line.separator");
/**
 * Test.
 * @throws IOException	 		IOException.
 */
	@Test
	public void whenJustExitAndNothingOut() throws IOException {
		this.testServer("Exit", "");
	}
/**
 * Test.
 * @throws IOException	 		IOException.
 */
//	@Test
//	public void whenGoToResourcesThenGoHomeThenExit() throws IOException {
//		this.testServer(
//			Joiner.on(LINESEPARATOR).join(
//				"Go to",
//				"main",
//				"Exit in menu",
//				"Go home",
//				"Exit"
//			),
//			Joiner.on(LINESEPARATOR).join(
//				"[resources, java]",
//				"[main, test]",
//				""
//			)
//		);
//	}
	/**
	 * Test.
	 * @throws IOException	 		IOException.
	 */
		@Test
		public void whenDownloadFileAndExit() throws IOException {
			this.testServer(
				Joiner.on(LINESEPARATOR).join(
					"Download",
					"forTestFileManager.txt",
					"Exit"
				),
				Joiner.on(LINESEPARATOR).join(
					"test",
					""
				)
			);
		}
/**
 * Test.
 * @throws IOException	 		IOException.
 */
//	@Test
//	public void whenPushFileAndExit() throws IOException {
//		this.testServer(
//			Joiner.on(LINESEPARATOR).join(
//				"Push",
//				"Create new file",
//				"newFile.txt",
//				"test string",
//				"Exit in menu",
//				"Exit"
//			),
//			Joiner.on(LINESEPARATOR).join(
//				"File create",
//				"Write in file done"
//			)
//		);
//	}
/**
 * Test.
 * @throws IOException	 		IOException.
 */
//	@Test
//	public void whenUnsupportedAskThenDontUnderstand() throws IOException {
//		this.testServer(
//			Joiner.on(LINESEPARATOR).join(
//				"unsupported ask",
//				"Exit"
//			),
//			Joiner.on(LINESEPARATOR).join(
//				"I don`t understand.",
//				""
//			)
//		);
//	}
/**
 * Test.
 * @param input					input.
 * @param excepted				excepted.
 * @throws IOException	 		IOException.
 */
	private void testServer(String input, String excepted) throws IOException {
		Socket socket = mock(Socket.class);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(
			input.getBytes()
		);
		when(socket.getInputStream()).thenReturn(in);
		when(socket.getOutputStream()).thenReturn(out);
		RealizationServer server = new RealizationServer(socket);
		server.startServer("./src/");
		assertThat(out.toString(), is(excepted));
	}
}
