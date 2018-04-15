package ru.aserdyuchenko;

import java.io.File;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.apache.commons.io.output.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version 1
 * @since 17.12.2016
 */

public class FileSortTest {

/**
 * Test sort line in file.
 */
	@Test
	public void whenSortLineInFile() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
        FileSort file = new FileSort();
		File distFile = new File("dist.txt");
		File sourceFile = new File("source.txt");
		file.sort(sourceFile, distFile);
		assertThat(out.toString(), is("sort files."));
	}
}
