package guru.bonacci.oogway.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MyFileUtilsTests {

	@Test
	public void shouldReadFileToList() throws IOException {
		List<String> output = MyFileUtils.readToList("read-me-test.txt");
		assertThat(output, hasSize(4));
	}
	
	@Test
	public void shouldReadFileToString() throws IOException {
		String output = MyFileUtils.readToString("read-me-test.txt");
		String expectedOutput = "a line\na phrase\na saying\na poem";
		assertThat(output, is(expectedOutput));
	}
}

